/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CargueArchivoDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.ArchivosProcesoMigracionRncDTO;
import co.com.runt.sagir.dto.CargueArchivoProcesoMigracionDTO;
import co.com.runt.sagir.dto.CargueArchivoRespuestaDTO;
import co.com.runt.sagir.dto.CargueRncDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.PathDTO;
import co.com.runt.sagir.entities.ComConstantes;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.utils.ArchivosUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CargueArchivoProcesoMigracionRNCLogica {

    public static final String VALIDAR_FIRMA = "http://weblogicInterno/ServiciosFirma/webresources/validacionFirma";

    @Resource
    protected EJBContext context;

    @EJB
    private CargueArchivoDAO cargueArchivoDAO;

    @EJB
    private ArchivosUtil archivosUtil;

    @EJB
    private ProcesarArchivosMigracionRncLogica archivosMigracionRncLogica;
    
    @EJB
    private ConstanteDAO constanteDAO;

    private static final Logger LOG = Logger.getLogger(CargueArchivoPropLogica.class.getSimpleName());

    public CargueArchivoProcesoMigracionDTO guardar(final CargueRncDTO cargueRncDTO, final InfoUsuarioDTO usuario, final String ip) throws Exception {
        CargueArchivoProcesoMigracionDTO respuesta = new CargueArchivoProcesoMigracionDTO();
        String mensaje = null;
        Long idSecretaria = cargueRncDTO.getOrganismoTransito().getIdSecretaria();
        //Valida si el número de ticket ingresado existe para otro registro
        Integer validaNroTicket = cargueArchivoDAO.validaNroTicket(cargueRncDTO.getNroTicket());
        if (validaNroTicket != 0) {
            mensaje = "Ya existe un registro con el número de ticket ingresado.";
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje(mensaje);
            return respuesta;
        }
        //Valida que la extensión del archivo sea .zip
        int index = cargueRncDTO.getNombreArchivoEmpaquetado().indexOf(".zip");
        if (index == 0) {
            mensaje = "La extensión del archivo es diferente a la permitida.";
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje(mensaje);
            return respuesta;
        }
        ArchivosProcesoMigracionRncDTO validaArchivos = validarArchivoDat(cargueRncDTO, idSecretaria.toString(), usuario);
        if (validaArchivos.getMensaje() == null) {
            respuesta.setCodmensaje(Mensajes.ERROR);
            mensaje = "Error al registrar el archivo .zip";
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje(mensaje);
            return respuesta;
        }
        ArchivosProcesoMigracionRncDTO archivos = countArchivos(validaArchivos);
        if (!archivos.getMensaje().equals(Constantes.ESTADO_OK)) {
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje(archivos.getMensaje());
            return respuesta;
        }
        if (mensaje == null) {
            PathDTO guardarArchivoZip = guardarArchivoZip(cargueRncDTO, idSecretaria, usuario, ip);
            if (!guardarArchivoZip.getEstado().equals(CargueArchivoMensajes.REGISTRADO)) {
                respuesta.setCodmensaje(Mensajes.ERROR);
                mensaje = "Error al guardar el archivo .zip";
                respuesta.setMensaje(mensaje);
                return respuesta;
            } else {
                String descomprimeArchivo = archivosUtil.descomprimirArchivo(guardarArchivoZip.getPathFile(), guardarArchivoZip.getPathGeneral());
                if (descomprimeArchivo == null) {
                    respuesta.setCodmensaje(Mensajes.ERROR);
                    respuesta.setMensaje(descomprimeArchivo);
                    return respuesta;
                } else {
                    ArchivosProcesoMigracionRncDTO procesoArchivo = archivosMigracionRncLogica.desfirmaArchivos(guardarArchivoZip, validaArchivos, usuario.getLogin(), idSecretaria);
                    if (procesoArchivo.getMensaje() != null) {
                        respuesta.setCodmensaje(Mensajes.ERROR);
                        respuesta.setMensaje("Error al validar la firma de los archivos.");
                        return respuesta;
                    } else {
                        CargueArchivoProcesoMigracionDTO proceso = archivosMigracionRncLogica.procesarArchivos(procesoArchivo, cargueRncDTO, usuario, guardarArchivoZip);
                        if (proceso.getCodmensaje().equals(Mensajes.OK)) {
                            String borrarArchivos = borrarFicheros(cargueRncDTO, guardarArchivoZip);
                            if (borrarArchivos == null) {
                                boolean actualizaEstadoArchivoZip = actualizaCarga(guardarArchivoZip.getPathFile(), proceso.getCodCarga(), Constantes.ESTADO_PROCESARO);
                                if (actualizaEstadoArchivoZip) {
                                    respuesta.setCodmensaje(Mensajes.OK);
                                    respuesta.setMensaje("Se proceso de forma correcta el proceso de migración del registro RNC");
                                    respuesta.setCodCarga(proceso.getCodCarga());
                                } else {
                                    respuesta.setCodmensaje(Mensajes.ERROR);
                                    respuesta.setMensaje("Error al actualizar el estado del archivo .Zip");
                                    return respuesta;
                                }
                            } else {
                                boolean actualizaEstadoArchivoZip = actualizaCarga(guardarArchivoZip.getPathFile(), proceso.getCodCarga(), Constantes.ESTADO_RECHAZADO);
                                if (actualizaEstadoArchivoZip) {
                                    respuesta.setCodmensaje(Mensajes.ERROR);
                                    respuesta.setMensaje(borrarArchivos);
                                } else {
                                    respuesta.setCodmensaje(Mensajes.ERROR);
                                    respuesta.setMensaje("Error al actualizar el estado del archivo .Zip");
                                    return respuesta;
                                }
                            }
                        } else {
                            respuesta.setCodmensaje(Mensajes.ERROR);
                            respuesta.setMensaje(proceso.getMensaje());
                            borrarFicheros(cargueRncDTO, guardarArchivoZip);
                            return respuesta;
                        }
                    }
                }
            }
        } else {
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje(mensaje);
            return respuesta;
        }
        return respuesta;
    }

    /**
     * Actualiza el estado del registro en la tabla CSWSAGIR.TBL_CARGUEARCHIVOS
     *
     * @param path
     * @param codCarga
     * @param estado
     * @return
     */
    public boolean actualizaCarga(final String path, final Integer codCarga, final String estado) {
        SgCarguearchivos archivo = new SgCarguearchivos();
        SgCarguearchivos consultaArchivoZip = cargueArchivoDAO.consultaArchivoZip(path);
        if (consultaArchivoZip != null) {
            archivo.setCarguearchivosEstado(estado);
            archivo.setIdCarga(codCarga);
            archivo.setCarguearchivosDatos(consultaArchivoZip.getCarguearchivosDatos());
            archivo.setCarguearchivosFecha(consultaArchivoZip.getCarguearchivosFecha());
            archivo.setCarguearchivosId(consultaArchivoZip.getCarguearchivosId());
            archivo.setCarguearchivosIdautra(consultaArchivoZip.getCarguearchivosIdautra());
            archivo.setCarguearchivosIp(consultaArchivoZip.getCarguearchivosIp());
            archivo.setCarguearchivosNombreDatos(consultaArchivoZip.getCarguearchivosNombreDatos());
            archivo.setCarguearchivosUsuario(consultaArchivoZip.getCarguearchivosUsuario());
            archivo.setTipoCargue(consultaArchivoZip.getTipoCargue());
            boolean actualizaEstado = cargueArchivoDAO.actualizarEstadoCargueArchivo(archivo);
            if (actualizaEstado) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida que los archivos que son obligatorios se encuentren en el archivo
     * .zip
     *
     * @param archivos
     * @return
     */
    public ArchivosProcesoMigracionRncDTO countArchivos(final ArchivosProcesoMigracionRncDTO archivos) {
        String mensaje;
        if (archivos.getNombreLicencias() == null) {
            mensaje = "El archivo de licencias es obligatorio.";
            archivos.addMensaje(mensaje);
        }
        if (archivos.getNombreResidencias() == null) {
            mensaje = "El archivo de residencias es obligatorio.";
            archivos.addMensaje(mensaje);
        }
        if (archivos.getNombrePersonas() == null) {
            mensaje = "El archivo de personas es obligatorio.";
            archivos.addMensaje(mensaje);
        }
        return archivos;
    }

    /**
     * Validación de cada uno de los archivos del proceso de migración de RNC
     *
     * @param cargueRncDTO
     * @param organismoTransito
     * @param usuario
     * @return
     * @throws Exception
     */
    public ArchivosProcesoMigracionRncDTO validarArchivoDat(CargueRncDTO cargueRncDTO, final String organismoTransito, final InfoUsuarioDTO usuario) throws Exception {
        ArchivosProcesoMigracionRncDTO respuesta = new ArchivosProcesoMigracionRncDTO();
        String mensaje = null;
        String otEntrada = organismoTransito.substring(0, 6);
        Pattern regExpSecretaria = Pattern.compile(Constantes.EXPRESION_ID_SECRETARIA);
        try {
            respuesta.setZip(Base64.decodeBase64(cargueRncDTO.getArchivoEmpaquetado()));
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(respuesta.getZip()));
            String nombreArchivo;
            ZipEntry entrada;
            while (null != (entrada = zis.getNextEntry())) {
                nombreArchivo = FilenameUtils.getBaseName(entrada.getName());
                int index = nombreArchivo.indexOf("mlice");
                if (index > 0) {
                    Matcher ot = regExpSecretaria.matcher(nombreArchivo);
                    if (!ot.find()) {
                        mensaje = "El nombre archivo de licencias no cumple con el estandar de migración.";
                        respuesta.addMensaje(mensaje);
                        return respuesta;
                    } else {
                        String idSecretaria = ot.group(1);
                        int otArchivo = idSecretaria.indexOf(otEntrada);
                        if (otArchivo < 0) {
                            mensaje = "El Organismo de Tránsito seleccionado no corresponde con el que reporta el archivo.";
                            respuesta.addMensaje(mensaje);
                            return respuesta;
                        } else {
                            Pattern patron = Pattern.compile(Constantes.EXPRESION_ARCHIVO_LICENCIAS);
                            Matcher validacion = patron.matcher(nombreArchivo);
                            if (!validacion.find()) {
                                mensaje = "El archivo de licencias no cumple con los parametros establecidos.";
                                respuesta.addMensaje(mensaje);
                                return respuesta;
                            } else {
                                respuesta.setNombreLicencias(FilenameUtils.getName(entrada.getName()));
                            }
                        }
                    }
                }
                index = nombreArchivo.indexOf("mresi");
                if (index > 0) {
                    Matcher ot = regExpSecretaria.matcher(nombreArchivo);
                    if (!ot.find()) {
                        mensaje = "El nombre archivo de residencias no cumple con el estandar de migración.";
                        respuesta.addMensaje(mensaje);
                        return respuesta;
                    } else {
                        String idSecretaria = ot.group(1);
                        int otArchivo = idSecretaria.indexOf(otEntrada);
                        if (otArchivo < 0) {
                            mensaje = "El Organismo de Tránsito seleccionado no corresponde con el que reporta el archivo.";
                            respuesta.addMensaje(mensaje);
                            return respuesta;
                        } else {
                            Pattern patron = Pattern.compile(Constantes.EXPRESION_ARCHIVO_RESIDENCIAS);
                            Matcher validacion = patron.matcher(nombreArchivo);
                            if (!validacion.find()) {
                                mensaje = "El archivo de residencias no cumple con los parametros establecidos.";
                                respuesta.addMensaje(mensaje);
                                return respuesta;
                            } else {
                                respuesta.setNombreResidencias(FilenameUtils.getName(entrada.getName()));
                            }
                        }
                    }
                }
                index = nombreArchivo.indexOf("mpers");
                if (index > 0) {
                    Matcher ot = regExpSecretaria.matcher(nombreArchivo);
                    if (!ot.find()) {
                        mensaje = "El nombre archivo de residencias no cumple con el estandar de migración.";
                        respuesta.addMensaje(mensaje);
                        return respuesta;
                    } else {
                        String idSecretaria = ot.group(1);
                        int otArchivo = idSecretaria.indexOf(otEntrada);
                        if (otArchivo < 0) {
                            mensaje = "El Organismo de Tránsito seleccionado no corresponde con el que reporta el archivo.";
                            respuesta.addMensaje(mensaje);
                            return respuesta;
                        } else {
                            Pattern patron = Pattern.compile(Constantes.EXPRESION_ARCHIVO_PERSONAS);
                            Matcher validacion = patron.matcher(nombreArchivo);
                            if (!validacion.find()) {
                                mensaje = "El archivo de restricciones no cumple con los parametros establecidos.";
                                respuesta.addMensaje(mensaje);
                                return respuesta;
                            } else {
                                respuesta.setNombrePersonas(FilenameUtils.getName(entrada.getName()));
                            }
                        }
                    }
                }
                index = nombreArchivo.indexOf("mresl");
                if (index > 0) {
                    Matcher ot = regExpSecretaria.matcher(nombreArchivo);
                    if (ot.find()) {
                        String idSecretaria = ot.group(1);
                        int otArchivo = idSecretaria.indexOf(otEntrada);
                        if (otArchivo < 0) {
                            mensaje = "El Organismo de Tránsito seleccionado no corresponde con el que reporta el archivo.";
                            respuesta.addMensaje(mensaje);
                            return respuesta;
                        } else {
                            Pattern patron = Pattern.compile(Constantes.EXPRESION_ARCHIVO_RESTRICCIONES);
                            Matcher validacion = patron.matcher(nombreArchivo);
                            if (!validacion.find()) {
                                mensaje = "El archivo de restricciones no cumple con los parametros establecidos.";
                                respuesta.addMensaje(mensaje);
                                return respuesta;
                            } else {
                                respuesta.setNombreRestricciones(FilenameUtils.getName(entrada.getName()));
                            }
                        }
                    }
                }
            }
            if (mensaje == null) {
                mensaje = Constantes.ESTADO_OK;
                respuesta.setMensaje(mensaje);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    /**
     * Metodo que se encarga de guardar el archivo comprimido que contiene los
     * archivos del proceso de migración de RNC
     *
     * @param data
     * @param idsecretaria
     * @param infoUsuario
     * @param ip
     * @return
     */
    public PathDTO guardarArchivoZip(final CargueRncDTO data, final Long idsecretaria, final InfoUsuarioDTO infoUsuario, final String ip) {
        PathDTO salida = new PathDTO();

        SgCarguearchivos cargue = new SgCarguearchivos();
        CargueArchivoRespuestaDTO respuestaDTO = new CargueArchivoRespuestaDTO();
        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            //cargue.setCarguearchivosDatos(data.getArchivo());
            cargue.setCarguearchivosEstado(CargueArchivoMensajes.SIN_PROCESAR.toString());
            cargue.setCarguearchivosFecha(new Date());
            cargue.setCarguearchivosNombreDatos(data.getNombreArchivoEmpaquetado());
            cargue.setTipoCargue(data.getTipoCargue());
            //sesion
            cargue.setCarguearchivosUsuario(infoUsuario.getLogin());
            cargue.setCarguearchivosIdautra(infoUsuario.getIdOrganizacion().toString());

            cargue.setCarguearchivosIp(ip);

            cargueArchivoDAO.insertCargue(cargue);
            Long idCargue = cargue.getCarguearchivosId();
            ut.commit();
            respuestaDTO.setIdCargue(idCargue);
            respuestaDTO.setEstado(CargueArchivoMensajes.SIN_PROCESAR);
            respuestaDTO.setMensaje(Constantes.SIN_PROCESAR);

            PathDTO pathArchivo = guardarArchivosAdjuntos(data, cargue.getCarguearchivosId());
            cargue.setCarguearchivosDatos(pathArchivo.getPathFile());

            cargueArchivoDAO.actualizarPathFile(cargue);
            if (pathArchivo.getPathGeneral() != null) {
                salida.setPathFile(pathArchivo.getPathFile());
                salida.setPathGeneral(pathArchivo.getPathGeneral());
                //salida.setPathGeneral("C:\\Users\\dsalamanca\\Desktop\\prueba");
                salida.setEstado(CargueArchivoMensajes.REGISTRADO);
            } else {
                salida.setEstado(CargueArchivoMensajes.RECHAZADO);
                String mensaje = null;
                salida.setMensaje(mensaje);
            }
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException e) {
            respuestaDTO.setEstado(CargueArchivoMensajes.RECHAZADO);
            Logger.getLogger(CargueArchivoPropLogica.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotSupportedException ex) {
            Logger.getLogger(CargueArchivoPropLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salida;
    }

    /**
     * Metodo que se encarga de crear el path para el archivo comprimido y para
     * los archivos firmados
     *
     * @param listaAdjuntos
     * @param idCargue
     * @return
     */
    private PathDTO guardarArchivosAdjuntos(CargueRncDTO listaAdjuntos, Long idCargue) {
        PathDTO path = new PathDTO();
        ComConstantes constante = constanteDAO.consultarPorGrupoYNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.CONSTANTE_PATH_FILE_SYSTEM_CARGUE_ARCHIVO);
        Date fechaSistema = new Date();
        String formatoFecha = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
        String fechaPath = sdf.format(fechaSistema);
        StringBuilder pathCarpeta = new StringBuilder();
        String pathBase;
        String pathFile;

        if (constante != null) {
            try {
                pathBase = constante.getConstanteValor();
                pathCarpeta.append(pathBase);
                File fileSystem = new File(pathCarpeta.toString());
                // Se crea el directorio principal
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                pathCarpeta.append("/").append(listaAdjuntos.getTipoCargue());
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                pathCarpeta.append("/").append(String.valueOf(fechaPath));
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                pathCarpeta.append("/").append(listaAdjuntos.getNroTicket());
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                pathCarpeta.append("/").append(idCargue.toString());
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                path.setPathGeneral(pathCarpeta.toString());

                LOG.log(Level.INFO, "=== PATH CARPETA SOLICITUD : {0}", pathCarpeta.toString());

                if (listaAdjuntos != null) {

                    pathFile = pathCarpeta.toString() + "/" + listaAdjuntos.getNombreArchivoEmpaquetado();

                    path.setPathFile(pathFile);
                    LOG.log(Level.INFO, "=== PATH ARCHIVO : {0}", pathFile);

                    byte[] file = Base64.decodeBase64(listaAdjuntos.getArchivoEmpaquetado());
                    FileUtils.writeByteArrayToFile(new File(pathFile), file);

                }
            } catch (IOException e) {
                LOG.log(Level.INFO, "Error en guardarArchivosAdjuntos : {0}", e.getCause());
            }
        }
        return path;
    }

    public String borrarFicheros(final CargueRncDTO cargueRncDTO, final PathDTO pathFile) {
        ArchivosProcesoMigracionRncDTO respuesta = new ArchivosProcesoMigracionRncDTO();
        String mensaje = null;
        try {
            respuesta.setZip(Base64.decodeBase64(cargueRncDTO.getArchivoEmpaquetado()));
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(respuesta.getZip()));
            String nombreArchivo;
            ZipEntry entrada;
            while (null != (entrada = zis.getNextEntry())) {
                nombreArchivo = FilenameUtils.getBaseName(entrada.getName());
                int index = nombreArchivo.indexOf("mlice");
                if (index > 0) {
                    String path = pathFile.getPathGeneral() + "/" + nombreArchivo + ".p7z";
                    boolean deleteFile = archivosUtil.deleteFile(path);
                    if (!deleteFile) {
                        mensaje = "Error al borrar el archivo";
                    }
                }
                index = nombreArchivo.indexOf("mresi");
                if (index > 0) {
                    String path = pathFile.getPathGeneral() + "/" + nombreArchivo + ".p7z";
                    boolean deleteFile = archivosUtil.deleteFile(path);
                    if (!deleteFile) {
                        mensaje = "Error al borrar el archivo";
                    }
                }
                index = nombreArchivo.indexOf("mpers");
                if (index > 0) {
                    String path = pathFile.getPathGeneral() + "/" + nombreArchivo + ".p7z";
                    boolean deleteFile = archivosUtil.deleteFile(path);
                    if (!deleteFile) {
                        mensaje = "Error al borrar el archivo";
                    }
                }
                index = nombreArchivo.indexOf("mresl");
                if (index > 0) {
                    String path = pathFile.getPathGeneral() + "/" + nombreArchivo + ".p7z";
                    boolean deleteFile = archivosUtil.deleteFile(path);
                    if (!deleteFile) {
                        mensaje = "Error al borrar el archivo";
                    }
                }
            }
        } catch (IOException e) {
        }
        return mensaje;
    }

}
