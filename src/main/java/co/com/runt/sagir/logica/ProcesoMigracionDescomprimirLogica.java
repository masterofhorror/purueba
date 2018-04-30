/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.dao.CargueArchivoDAO;
import co.com.runt.sagir.dto.ArchivosCargueMigracionDTO;
import co.com.runt.sagir.dto.CargueArchivoRespuestaDTO;
import co.com.runt.sagir.dto.CargueRnaDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.PathDTO;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.utils.ArchivosUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author APENA
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcesoMigracionDescomprimirLogica {

    private static final Logger LOGGER = Logger.getLogger(ProcesoMigracionDescomprimirLogica.class.getSimpleName());

    @Resource
    protected EJBContext context;

    @EJB
    private ArchivosUtil archivosUtil;

    @EJB
    private CargueArchivoDAO cargueArchivoDAO;

    public ArchivosCargueMigracionDTO validarArchivoDat(final CargueRnaDTO datosUser, final String organismoTransito, final Integer tipoCargue, final String ip, final InfoUsuarioDTO infoUsuario) {
        ArchivosCargueMigracionDTO respuesta = cargarArchivosDat(datosUser, organismoTransito, tipoCargue, ip, infoUsuario);

        if (respuesta.getMensaje() == null) {
            if (respuesta.getPropietarios() == null) {
                respuesta.addMensaje("El archivo de Propietarios es obligatorio.");
            }
            if (respuesta.getVehiculos() == null) {
                respuesta.addMensaje("El archivo de Vehículos es obligatorio.");
            }
            if (respuesta.getTramites() == null) {
                respuesta.addMensaje("El archivo de Trámites es obligatorio.");
            }
        }

        return respuesta;
    }

    public ArchivosCargueMigracionDTO cargarArchivosDat(final CargueRnaDTO datosUser, final String organismoTransito, final Integer tipoCargue, final String ip, final InfoUsuarioDTO infoUsuario) {
        ArchivosCargueMigracionDTO respuesta = new ArchivosCargueMigracionDTO();

        PathDTO guardarArchivoZip = guardarArchivoZip(datosUser, organismoTransito, infoUsuario, ip);

        if (!guardarArchivoZip.getEstado().equals(CargueArchivoMensajes.REGISTRADO)) {
            respuesta.setMensaje("Error al guardar el archivo .zip");
        } else {
            String descomprimeArchivo = archivosUtil.descomprimirArchivo(guardarArchivoZip.getPathFile(), guardarArchivoZip.getPathGeneral());

            if (descomprimeArchivo == null) {
                respuesta.setMensaje(descomprimeArchivo);
            } else {
                Long organismoTransitoNum = Long.parseLong(organismoTransito);
                respuesta = desfirmaArchivos(guardarArchivoZip, infoUsuario.getLogin(), organismoTransitoNum, tipoCargue);
            }
        }

        return respuesta;
    }

    public ArchivosCargueMigracionDTO desfirmaArchivos(final PathDTO path, final String usuario, final Long idSecretaria, final Integer tipoCargue) {
        ArchivosCargueMigracionDTO respuesta = new ArchivosCargueMigracionDTO();
        String mensaje = null;
        Integer varSedeOpera = 5;
        Integer varResolucion = 2;
        String organismoTransito = idSecretaria.toString();

        try {
            String nombreArchivo;
            ZipInputStream zis = new ZipInputStream(new FileInputStream(path.getPathFile()));
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                nombreArchivo = FilenameUtils.getName(ze.getName());
                    int index = nombreArchivo.indexOf("mprop");
                if (index > 0) {
                    String ot = nombreArchivo.substring(0, index);
                    if (tipoCargue != varSedeOpera && tipoCargue != varResolucion) {
                        String ot7 = nombreArchivo.substring(0, index - 1);
                        String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
                        if (ot7.equals(organismoTransito7)) {
                            byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                            if (validaFirma != null) {
                                respuesta.setNombrePropietarios(nombreArchivo);
                                respuesta.setPropietarios(validaFirma);
                            } else {
                                respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                            }
                        } else {
                            respuesta.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                        }
                    } else {
                        byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                        if (validaFirma != null) {
                            respuesta.setNombrePropietarios(nombreArchivo);
                            respuesta.setPropietarios(validaFirma);
                        } else {
                            respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                        }
                    }
                }

                index = nombreArchivo.indexOf("mtrve");
                if (index > 0) {
                    String ot = nombreArchivo.substring(0, index);
                    if (tipoCargue != varSedeOpera && tipoCargue != varResolucion) {
                        String ot7 = nombreArchivo.substring(0, index - 1);
                        String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
                        if (ot7.equals(organismoTransito7)) {
                            byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                            if (validaFirma != null) {
                                respuesta.setNombreTramites(nombreArchivo);
                                respuesta.setTramites(validaFirma);
                            } else {
                                respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                            }
                        } else {
                            respuesta.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                        }
                    } else {
                        byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                        if (validaFirma != null) {
                            respuesta.setNombreTramites(nombreArchivo);
                            respuesta.setTramites(validaFirma);
                        } else {
                            respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                        }
                    }
                }

                index = nombreArchivo.indexOf("mvehi");
                if (index > 0) {
                    String ot = nombreArchivo.substring(0, index);
                    if (tipoCargue != varSedeOpera && tipoCargue != varResolucion) {
                        String ot7 = nombreArchivo.substring(0, index - 1);
                        String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
                        if (ot7.equals(organismoTransito7)) {
                            byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                            if (validaFirma != null) {
                                respuesta.setNombreVehiculos(nombreArchivo);
                                respuesta.setVehiculos(validaFirma);
                            } else {
                                respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                            }
                        } else {
                            respuesta.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                        }
                    } else {
                        byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                        if (validaFirma != null) {
                            respuesta.setNombreVehiculos(nombreArchivo);
                            respuesta.setVehiculos(validaFirma);
                        } else {
                            respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                        }
                    }
                }

                index = nombreArchivo.indexOf("mcaut");
                if (index > 0) {
                    String ot = nombreArchivo.substring(0, index);
                    if (tipoCargue != varSedeOpera && tipoCargue != varResolucion) {
                        String ot7 = nombreArchivo.substring(0, index - 1);
                        String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
                        if (ot7.equals(organismoTransito7)) {
                            byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                            if (validaFirma != null) {
                                respuesta.setNombreMedidasCautelares(nombreArchivo);
                                respuesta.setMedidasCautelares(validaFirma);
                            } else {
                                respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                            }
                        } else {
                            respuesta.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                        }
                    } else {
                        byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                        if (validaFirma != null) {
                            respuesta.setNombreMedidasCautelares(nombreArchivo);
                            respuesta.setMedidasCautelares(validaFirma);
                        } else {
                            respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                        }
                    }
                }

                index = nombreArchivo.indexOf("runtradi");
                if (index > 0) {
                    String ot = nombreArchivo.substring(0, index);
                    if (tipoCargue != varSedeOpera && tipoCargue != varResolucion) {
                        String ot7 = nombreArchivo.substring(0, index - 1);
                        String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
                        if (ot7.equals(organismoTransito7)) {
                            byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                            if (validaFirma != null) {
                                respuesta.setNombreRadicacionesCuenta(nombreArchivo);
                                respuesta.setRadicacionesCuenta(validaFirma);
                            } else {
                                respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                            }
                        } else {
                            respuesta.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                        }
                    } else {
                        byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                        if (validaFirma != null) {
                            respuesta.setNombreRadicacionesCuenta(nombreArchivo);
                            respuesta.setRadicacionesCuenta(validaFirma);
                        } else {
                            respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                        }
                    }
                }

                index = nombreArchivo.indexOf("runtmedi");
                if (index > 0) {
                    String ot = nombreArchivo.substring(0, index);
                    if (tipoCargue != varSedeOpera && tipoCargue != varResolucion) {
                        String ot7 = nombreArchivo.substring(0, index - 1);
                        String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
                        if (ot7.equals(organismoTransito7)) {
                            byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                            if (validaFirma != null) {
                                respuesta.setNombreInfoAdicionalMedidasCautelares(nombreArchivo);
                                respuesta.setInfoAdicionalMedidasCautelares(validaFirma);
                            } else {
                                respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                            }
                        } else {
                            respuesta.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                        }
                    } else {
                        byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                        if (validaFirma != null) {
                            respuesta.setNombreInfoAdicionalMedidasCautelares(nombreArchivo);
                            respuesta.setInfoAdicionalMedidasCautelares(validaFirma);
                        } else {
                            respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                        }
                    }
                }

                index = nombreArchivo.indexOf("runtpren");
                if (index > 0) {
                    String ot = nombreArchivo.substring(0, index);
                    if (tipoCargue != varSedeOpera && tipoCargue != varResolucion) {
                        String ot7 = nombreArchivo.substring(0, index - 1);
                        String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
                        if (ot7.equals(organismoTransito7)) {
                            byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                            if (validaFirma != null) {
                                respuesta.setNombrePrendas(nombreArchivo);
                                respuesta.setPrendas(validaFirma);
                            } else {
                                respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                            }
                        } else {
                            respuesta.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                        }
                    } else {
                        byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                        if (validaFirma != null) {
                            respuesta.setNombrePrendas(nombreArchivo);
                            respuesta.setPrendas(validaFirma);
                        } else {
                            respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                        }
                    }
                }

                index = nombreArchivo.indexOf("mt_runtradi");
                if (index > 0) {
                    String ot = nombreArchivo.substring(0, index);
                    if (tipoCargue != varSedeOpera && tipoCargue != varResolucion) {
                        String ot7 = nombreArchivo.substring(0, index - 1);
                        String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
                        if (ot7.equals(organismoTransito7)) {
                            byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                            if (validaFirma != null) {
                                respuesta.setNombreRadicadoMT(nombreArchivo);
                                respuesta.setRadicadoMT(validaFirma);
                            } else {
                                respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                            }
                        } else {
                            respuesta.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                        }
                    } else {
                        byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);

                        if (validaFirma != null) {
                            respuesta.setNombreRadicadoMT(nombreArchivo);
                            respuesta.setRadicadoMT(validaFirma);
                        } else {
                            respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                        }
                    }
                }
                ze = zis.getNextEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public PathDTO guardarArchivoZip(final CargueRnaDTO data, final String idsecretaria, final InfoUsuarioDTO infoUsuario, final String ip) {
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
            cargue.setCarguearchivosUsuario(infoUsuario.getLogin());

            //xfordware for
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
            Logger.getLogger(ProcesoMigracionDescomprimirLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salida;
    }

    private PathDTO guardarArchivosAdjuntos(CargueRnaDTO listaAdjuntos, Long idCargue) {
        PathDTO path = new PathDTO();
        //ComConstantes constante = constanteDAO.consultarPorGrupoYNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.CONSTANTE_PATH_FILE_SYSTEM_CARGUE_ARCHIVO);
        String constante = "C:\\Desarrollo\\Sagir";//"C:\\Users\\apena\\Downloads";
        Date fechaSistema = new Date();
        String formatoFecha = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
        String fechaPath = sdf.format(fechaSistema);
        StringBuilder pathCarpeta = new StringBuilder();
        String pathBase;
        String pathFile = "";

        if (constante != null) {
            try {
                pathBase = constante;//constante.getConstanteValor();
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

//                pathCarpeta.append("/").append(listaAdjuntos.getNroTicket());
//                fileSystem = new File(pathCarpeta.toString());
//                if (!fileSystem.exists()) {
//                    fileSystem.mkdir();
//                }
                pathCarpeta.append("/").append(idCargue.toString());
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                path.setPathGeneral(pathCarpeta.toString());

                LOGGER.log(Level.INFO, "=== PATH CARPETA SOLICITUD : {0}", pathCarpeta.toString());

                if (listaAdjuntos != null) {

                    pathFile = pathCarpeta.toString() + "/" + listaAdjuntos.getNombreArchivoEmpaquetado();

                    path.setPathFile(pathFile);
                    LOGGER.log(Level.INFO, "=== PATH ARCHIVO : {0}", pathFile);

                    byte[] file = Base64.decodeBase64(listaAdjuntos.getArchivoEmpaquetado());
                    FileUtils.writeByteArrayToFile(new File(pathFile), file);

                }
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Error en guardarArchivosAdjuntos : {0}", e.getCause());
            }
        }
        return path;
    }
}
