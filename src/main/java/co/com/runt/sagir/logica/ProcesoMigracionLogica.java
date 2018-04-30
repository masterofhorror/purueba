/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.clienteserviciosfirma.ClienteServiciosFirma;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaDTO;
//import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaOrganismoInDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.ProgramacionCargueDAO;
import co.com.runt.sagir.dao.ProgramarCargueDAO;
import co.com.runt.sagir.dto.ArchivosCargueMigracionDTO;
import co.com.runt.sagir.dto.CargueRnaDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ComConstantes;
import co.com.runt.sagir.entities.ProgramarCargue;
import co.com.runt.sagir.entities.ProgramarCarguePK;
import co.com.runt.sagir.utils.RegistroException;
import co.com.runt.sagir.utils.TipoArchivoCargue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class ProcesoMigracionLogica {

    private static final Logger LOGGER = Logger.getLogger(ProcesoMigracionLogica.class.getSimpleName());

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private ProgramacionCargueDAO programacionCargueDAO;

    @EJB
    private ProgramarCargueDAO programarCargueDAO;

    public MensajeDTO guardar(final CargueRnaDTO cargueRnaDTO, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO respuesta = new MensajeDTO();
        respuesta.setCodmensaje(Mensajes.ERROR);

        try {
            String error = validarNombreArchivoZip(cargueRnaDTO);

            if (error.isEmpty()) {
                Integer tipoCargue = Integer.parseInt(cargueRnaDTO.getTipoCargue());
                ArchivosCargueMigracionDTO archivosCargueMigracion = validarArchivoDat(cargueRnaDTO.getArchivoEmpaquetado(), cargueRnaDTO.getOrganismoTransito().getIdSecretaria().toString(), tipoCargue);

                if (archivosCargueMigracion.getMensaje() == null) {

                    programarCargues(cargueRnaDTO, archivosCargueMigracion, usuario.getLogin());

                    respuesta.setCodmensaje(Mensajes.OK);
                    respuesta.setMensaje("Cargue programado correctamente");
                } else {
                    respuesta.setMensaje(archivosCargueMigracion.getMensaje());
                }
            }

            if (!error.isEmpty()) {
                respuesta.setMensaje(error);
            }

        } catch (Exception e) {
            respuesta.setMensaje("Error registrando el cargue RNA. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error realizando el cargue RNA.", e);
        }
        return respuesta;
    }

    /**
     * *
     * valida el nombre del archivo .zip remitido por el OT
     *
     * @param cargueRnaDTO
     * @return
     */
    public String validarNombreArchivoZip(final CargueRnaDTO cargueRnaDTO) {
        String respuesta = "";

        String nombreArchivo = FilenameUtils.getBaseName(cargueRnaDTO.getNombreArchivoEmpaquetado());
        String[] archivo = nombreArchivo.split("-");

        if (archivo.length == 3) {
            //validar el ot con longitud de 7
            String organismoTransito = archivo[0];
            String organismoTrnasitoEntrante = cargueRnaDTO.getOrganismoTransito().getIdSecretaria().toString();
            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length() - 1);
            String organismoTrnasitoEntrante7 = organismoTrnasitoEntrante.substring(0, organismoTrnasitoEntrante.length() - 1);
            
            //if (organismoTransito.equals(cargueRnaDTO.getOrganismoTransito().getIdSecretaria().toString())) {
            if (organismoTransito7.equals(organismoTrnasitoEntrante7)){
                String tipoRegistro = archivo[1];
                if (tipoRegistro.equals("1")) {
                    String fecha = archivo[2];
                    
                    try {
                        if ((new SimpleDateFormat("DDMMYYYY").parse(fecha)).after(new Date())) {
                            respuesta = "El archivo: " + nombreArchivo + ", no contiene una fecha valida" + fecha + ".";
                        }
                    } catch (ParseException ex) {
                        respuesta = "El archivo: " + nombreArchivo + ", no contiene una fecha valida" + fecha + ".";
                    }
                } else {
                    respuesta = "El archivo: " + nombreArchivo + ", no corresponde a registro " + cargueRnaDTO.getTipoRegistro() + ".";
                }
            } else {
                respuesta = "El archivo " + nombreArchivo + ", no corresponde al organismo seleccionado.";
            }
        } else {
            respuesta = "El archivo " + nombreArchivo + ", no tiene el formato de nombre requerido.";
        }
        return respuesta;
    }

    /**
     * *
     * valida los que existan los archivos obligatorios (propietarios, tramites,
     * vehiculos)
     *
     * @param archivoZip
     * @param organismoTransito
     * @param tipoCargue
     * @return
     */
    public ArchivosCargueMigracionDTO validarArchivoDat(final String archivoZip, final String organismoTransito, final Integer tipoCargue) {
        ArchivosCargueMigracionDTO respuesta = cargarArchivosDat(archivoZip, organismoTransito, tipoCargue);

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

    /**
     * *
     * valida los nombres de los archivos encontrados en el .zip y devuelve los
     * archivos desfirmados
     *
     * @param archivoZip
     * @param organismoTransito
     * @return
     */
    private ArchivosCargueMigracionDTO cargarArchivosDat(final String archivoZip, final String organismoTransito, final Integer tipoCargue) {
        ArchivosCargueMigracionDTO respuesta = new ArchivosCargueMigracionDTO();
        Integer varSedeOpera = 5;
        Integer varResolucion = 2;

        try {
            respuesta.setZip(Base64.decodeBase64(archivoZip));

            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(respuesta.getZip()));

            ArchivosCargueMigracionDTO archivoCargueMigracion = new ArchivosCargueMigracionDTO();

            ByteArrayOutputStream streamBuilder;
            int bytesRead;
            byte[] archivo;
            String nombreArchivo;

            ZipEntry entrada;
            while (null != (entrada = zis.getNextEntry())) {

                nombreArchivo = FilenameUtils.getBaseName(entrada.getName());

                streamBuilder = new ByteArrayOutputStream();
                archivo = new byte[8192 * 2];

                while ((bytesRead = zis.read(archivo)) != -1) {
                    streamBuilder.write(archivo, 0, bytesRead);
                }

                if (!entrada.isDirectory()) {

                    int index = nombreArchivo.indexOf("mprop");
                    if (index > 0) {
                        String ot = nombreArchivo.substring(0, index);
                        if (tipoCargue != varSedeOpera || tipoCargue != varResolucion) {
                            String ot7 = nombreArchivo.substring(0, index -1);
                            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length()-1);
                            if (ot7.equals(organismoTransito7)) {
                                if (ot.concat("mprop").equals(nombreArchivo)) {
                                    try {
                                        archivoCargueMigracion.setPropietarios(getArchivoDesfirmado(organismoTransito, archivo));
                                    } catch (RegistroException e) {
                                        respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                    }
                                } else {
                                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Propietarios.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                            }
                        } else {
                            if (ot.concat("mprop").equals(nombreArchivo)) {
                                try {
                                    archivoCargueMigracion.setPropietarios(getArchivoDesfirmado(organismoTransito, archivo));
                                } catch (RegistroException e) {
                                    respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Propietarios.");
                            }
                        }
                        break;
                    }

                    index = nombreArchivo.indexOf("mtrve");
                    if (index > 0) {
                        String ot = nombreArchivo.substring(0, index);
                        if (tipoCargue != varSedeOpera || tipoCargue != varResolucion) {
                            String ot7 = nombreArchivo.substring(0, index -1);
                            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length()-1);
                            if (ot7.equals(organismoTransito7)) {
                                if (ot.concat("mtrve").equals(nombreArchivo)) {
                                    try {
                                        archivoCargueMigracion.setTramites(getArchivoDesfirmado(organismoTransito, archivo));
                                    } catch (RegistroException e) {
                                        respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                    }
                                } else {
                                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Trámites.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                            }
                        } else {
                            if (ot.concat("mtrve").equals(nombreArchivo)) {
                                try {
                                    archivoCargueMigracion.setTramites(getArchivoDesfirmado(organismoTransito, archivo));
                                } catch (RegistroException e) {
                                    respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Trámites.");
                            }
                        }
                        break;
                    }

                    index = nombreArchivo.indexOf("mvehi");
                    if (index > 0) {
                        String ot = nombreArchivo.substring(0, index);
                        if (tipoCargue != varSedeOpera || tipoCargue != varResolucion) {
                            String ot7 = nombreArchivo.substring(0, index -1);
                            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length()-1);
                            if (ot7.equals(organismoTransito7)) {
                                if (ot.concat("mvehi").equals(nombreArchivo)) {
                                    try {
                                        archivoCargueMigracion.setVehiculos(getArchivoDesfirmado(organismoTransito, archivo));
                                    } catch (RegistroException e) {
                                        respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                    }
                                } else {
                                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Vehículo.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                            }
                        } else {
                            if (ot.concat("mvehi").equals(nombreArchivo)) {
                                try {
                                    archivoCargueMigracion.setVehiculos(getArchivoDesfirmado(organismoTransito, archivo));
                                } catch (RegistroException e) {
                                    respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Vehículo.");
                            }
                        }
                        break;
                    }

                    index = nombreArchivo.indexOf("mcaut");
                    if (index > 0) {
                        String ot = nombreArchivo.substring(0, index);
                        if (tipoCargue != varSedeOpera || tipoCargue != varResolucion) {
                            String ot7 = nombreArchivo.substring(0, index -1);
                            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length()-1);
                            if (ot7.equals(organismoTransito7)) {
                                if (ot.concat("mcaut").equals(nombreArchivo)) {
                                    try {
                                        archivoCargueMigracion.setMedidasCautelares(getArchivoDesfirmado(organismoTransito, archivo));
                                    } catch (RegistroException e) {
                                        respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                    }
                                } else {
                                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Medidas Cautelares.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                            }
                        } else {
                            if (ot.concat("mcaut").equals(nombreArchivo)) {
                                try {
                                    archivoCargueMigracion.setMedidasCautelares(getArchivoDesfirmado(organismoTransito, archivo));
                                } catch (RegistroException e) {
                                    respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Medidas Cautelares.");
                            }
                        }
                        break;
                    }

                    index = nombreArchivo.indexOf("runtradi");
                    if (index > 0) {
                        String ot = nombreArchivo.substring(0, index);
                        if (tipoCargue != varSedeOpera || tipoCargue != varResolucion) {
                            String ot7 = nombreArchivo.substring(0, index -1);
                            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length()-1);
                            if (ot7.equals(organismoTransito7)) {
                                if (ot.concat("runtradi").equals(nombreArchivo)) {
                                    try {
                                        archivoCargueMigracion.setRadicacionesCuenta(getArchivoDesfirmado(organismoTransito, archivo));
                                    } catch (RegistroException e) {
                                        respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                    }
                                } else {
                                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Radicaciones de Cuenta.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                            }
                        } else {
                            if (ot.concat("runtradi").equals(nombreArchivo)) {
                                try {
                                    archivoCargueMigracion.setRadicacionesCuenta(getArchivoDesfirmado(organismoTransito, archivo));
                                } catch (RegistroException e) {
                                    respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información de Radicaciones de Cuenta.");
                            }
                        }

                        break;
                    }

                    index = nombreArchivo.indexOf("runtmedi");
                    if (index > 0) {
                        String ot = nombreArchivo.substring(0, index);
                        if (tipoCargue != varSedeOpera || tipoCargue != varResolucion) {
                            String ot7 = nombreArchivo.substring(0, index -1);
                            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length()-1);
                            if (ot7.equals(organismoTransito7)) {
                                if (ot.concat("runtmedi").equals(nombreArchivo)) {
                                    try {
                                        archivoCargueMigracion.setInfoAdicionalMedidasCautelares(getArchivoDesfirmado(organismoTransito, archivo));
                                    } catch (RegistroException e) {
                                        respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                    }
                                } else {
                                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información adicional de Medidas Cautelares.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                            }
                        } else {
                            if (ot.concat("runtmedi").equals(nombreArchivo)) {
                                try {
                                    archivoCargueMigracion.setInfoAdicionalMedidasCautelares(getArchivoDesfirmado(organismoTransito, archivo));
                                } catch (RegistroException e) {
                                    respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información adicional de Medidas Cautelares.");
                            }
                        }
                        break;
                    }

                    index = nombreArchivo.indexOf("runtpren");
                    if (index > 0) {
                        String ot = nombreArchivo.substring(0, index);
                        if (tipoCargue != varSedeOpera || tipoCargue != varResolucion) {
                            String ot7 = nombreArchivo.substring(0, index -1);
                            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length()-1);
                            if (ot7.equals(organismoTransito7)) {
                                if (ot.concat("runtpren").equals(nombreArchivo)) {
                                    try {
                                        archivoCargueMigracion.setPrendas(getArchivoDesfirmado(organismoTransito, archivo));
                                    } catch (RegistroException e) {
                                        respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                    }
                                } else {
                                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información adicional de Prendas.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                            }
                        } else {
                            if (ot.concat("runtpren").equals(nombreArchivo)) {
                                try {
                                    archivoCargueMigracion.setPrendas(getArchivoDesfirmado(organismoTransito, archivo));
                                } catch (RegistroException e) {
                                    respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información adicional de Prendas.");
                            }
                        }

                        break;
                    }

                    index = nombreArchivo.indexOf("mt_runtradi");
                    if (index > 0) {
                        String ot = nombreArchivo.substring(0, index);
                        if (tipoCargue != varSedeOpera || tipoCargue != varResolucion) {
                            String ot7 = nombreArchivo.substring(0, index -1);
                            String organismoTransito7 = organismoTransito.substring(0, organismoTransito.length()-1);
                            if (ot7.equals(organismoTransito7)) {
                                if (ot.concat("mt_runtradi").equals(nombreArchivo)) {
                                    try {
                                        archivoCargueMigracion.setRadicadoMT(getArchivoDesfirmado(organismoTransito, archivo));
                                    } catch (RegistroException e) {
                                        respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                    }
                                    //archivoCargueMigracion.setVehiculos(entrada.getExtra());
                                } else {
                                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información adicional de Radicado MT.");
                                }
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde al organismo seleccionado.");
                            }
                        } else {
                            if (ot.concat("mt_runtradi").equals(nombreArchivo)) {
                                try {
                                    archivoCargueMigracion.setRadicadoMT(getArchivoDesfirmado(organismoTransito, archivo));
                                } catch (RegistroException e) {
                                    respuesta.setMensaje("El archivo: " + nombreArchivo + ", presenta problemas al desfirmar.");
                                }
                                //archivoCargueMigracion.setVehiculos(entrada.getExtra());
                            } else {
                                archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", no corresponde a información adicional de Radicado MT.");
                            }
                        }
                        break;
                    }
                } else {
                    archivoCargueMigracion.addMensaje("El archivo: " + nombreArchivo + ", corresponde a una carpeta.");
                }

            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

    private byte[] getArchivoDesfirmado(final String organismoTransito, final byte[] archivo) throws RegistroException {
        ClienteServiciosFirma cliente = new ClienteServiciosFirma("http://weblogicInterno/ServiciosFirma/webresources/validacionFirma");

        //ValidacionFirmaOrganismoInDTO entrada = new ValidacionFirmaOrganismoInDTO();
//        entrada.setOrganismo(organismoTransito);
//        entrada.setDatosFirmados(Base64.encodeBase64String(archivo));
//
//        ValidacionFirmaDTO respuesta = cliente.validarFirmaOrganismoTransito(entrada);
//        if (respuesta == null) {
//            throw new RegistroException("Error al desfirmar el archivo para el usuario: " + organismoTransito);
//        }
//        return respuesta.getDatos();
        /* //ALEX
         ClienteServiciosFirma cliente = new ClienteServiciosFirma(VALIDAR_FIRMA);
         String parametroUsuario = constanteDAO.getByGrupoNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.VALIDACION_USUARIO).getConstanteValor();
         ValidacionFirmaInParamDTO entrada = new ValidacionFirmaInParamDTO();
         //ValidacionFirmaOrganismoInDTO entrada = new ValidacionFirmaOrganismoInDTO();
         AutoridadTransitoHQ organismo = constanteDAO.consultaOT(idSecretaria);
         String nitOrganismo = organismo.getEmpresaPersona().getPersona().getPersonaNrodocume();
         entrada.setCedulaUsuario(nitOrganismo);
         entrada.setDatosFirmados(firma);
         entrada.setParametro(parametroUsuario);
         //        entrada.setCedulaUsuario(usuario);
         //        entrada.setDatosFirmados(firma);
         //        entrada.setOrganismo(nitOrganismo);
         //        ValidacionFirmaDTO respuesta = cliente.validarFirmaOrganismoTransito(entrada);
         ValidacionFirmaDTO respuesta = cliente.validarFirmaParam(entrada);
         if (respuesta == null) {
         throw new RegistroException("Error al validar la firma para el usuario: " + usuario);
         }
         return respuesta.getDatos();
         */ //ALEX
        
        ValidacionFirmaDTO validacionFirmaDTO = cliente.validarFirma(null);
        return validacionFirmaDTO.getDatos();
    }

    private boolean programarCargues(final CargueRnaDTO cargueRnaDTO, final ArchivosCargueMigracionDTO archivosCargueMigracion, final String usuario) {
        Date fechaActual = new Date();

        ComConstantes constante = constanteDAO.consultarPorGrupoYNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.CONSTANTE_PATH_FILE_SYSTEM_CARGUE_ARCHIVO);

        Long codigoProceso = programacionCargueDAO.getCodigoProceso();
        Long orden = 0L;
        Long idSecretaria = cargueRnaDTO.getOrganismoTransito().getIdSecretaria();
        Integer tipoRegistro = Integer.parseInt(cargueRnaDTO.getTipoRegistro().toString());

        String rutaZip = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombreZip(), archivosCargueMigracion.getZip(), idSecretaria, codigoProceso);
        String ruta;

        if (archivosCargueMigracion.getMedidasCautelares() != null) {
            ruta = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombreMedidasCautelares(), archivosCargueMigracion.getMedidasCautelares(), idSecretaria, codigoProceso);
            programarCargue(codigoProceso, orden++, idSecretaria, tipoRegistro, TipoArchivoCargue.MEDIDAS_CAUTELARES.getCodigo(), fechaActual, ruta, rutaZip, null, null, Long.parseLong(usuario), "N");
        }

        if (archivosCargueMigracion.getRadicacionesCuenta() != null) {
            ruta = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombreRadicacionesCuenta(), archivosCargueMigracion.getRadicacionesCuenta(), idSecretaria, codigoProceso);
            programarCargue(codigoProceso, orden++, idSecretaria, tipoRegistro, TipoArchivoCargue.RADICADO.getCodigo(), fechaActual, ruta, rutaZip, null, null, Long.parseLong(usuario), "N");
        }

        if (archivosCargueMigracion.getInfoAdicionalMedidasCautelares() != null) {
            ruta = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombreInfoAdicionalMedidasCautelares(), archivosCargueMigracion.getInfoAdicionalMedidasCautelares(), idSecretaria, codigoProceso);
            programarCargue(codigoProceso, orden++, idSecretaria, tipoRegistro, TipoArchivoCargue.MEDIDAS_CAUTELARES_ADICIONAL.getCodigo(), fechaActual, ruta, rutaZip, null, null, Long.parseLong(usuario), "N");
        }

        if (archivosCargueMigracion.getPrendas() != null) {
            ruta = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombrePrendas(), archivosCargueMigracion.getPrendas(), idSecretaria, codigoProceso);
            programarCargue(codigoProceso, orden++, idSecretaria, tipoRegistro, TipoArchivoCargue.PRENDAS.getCodigo(), fechaActual, ruta, rutaZip, null, null, Long.parseLong(usuario), "N");
        }

        if (archivosCargueMigracion.getRadicadoMT() != null) {
            ruta = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombreRadicadoMT(), archivosCargueMigracion.getRadicadoMT(), idSecretaria, codigoProceso);
            programarCargue(codigoProceso, orden++, idSecretaria, tipoRegistro, TipoArchivoCargue.RADICADO_MT.getCodigo(), fechaActual, ruta, rutaZip, null, null, Long.parseLong(usuario), "N");
        }

        //archivos obligatorios
        if (archivosCargueMigracion.getPropietarios() != null) {
            ruta = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombrePropietarios(), archivosCargueMigracion.getPropietarios(), idSecretaria, codigoProceso);
            programarCargue(codigoProceso, orden++, idSecretaria, tipoRegistro, TipoArchivoCargue.PROPIETARIO.getCodigo(), fechaActual, ruta, rutaZip, null, null, Long.parseLong(usuario), "N");
        }

        if (archivosCargueMigracion.getVehiculos() != null) {
            ruta = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombreVehiculos(), archivosCargueMigracion.getVehiculos(), idSecretaria, codigoProceso);
            programarCargue(codigoProceso, orden++, idSecretaria, tipoRegistro, TipoArchivoCargue.VEHICULO.getCodigo(), fechaActual, ruta, rutaZip, null, null, Long.parseLong(usuario), "N");
        }

        if (archivosCargueMigracion.getTramites() != null) {
            ruta = guardarArchivoCargue(constante.getConstanteValor(), archivosCargueMigracion.getNombreTramites(), archivosCargueMigracion.getTramites(), idSecretaria, codigoProceso);
            programarCargue(codigoProceso, orden++, idSecretaria, tipoRegistro, TipoArchivoCargue.TRAMITE.getCodigo(), fechaActual, ruta, rutaZip, null, null, Long.parseLong(usuario), "N");
        }

        return true;
    }

    private String guardarArchivoCargue(final String ruta, final String nombreArchivo, final byte[] archivo, final Long organismoTransito, final Long codigoProceso) {
        try {
            File directorio = new File(ruta + System.getProperty(Constantes.FILE_SEPARATOR) + "CARGUES" + System.getProperty(Constantes.FILE_SEPARATOR) + organismoTransito + System.getProperty(Constantes.FILE_SEPARATOR) + codigoProceso);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String file = directorio + System.getProperty(Constantes.FILE_SEPARATOR) + nombreArchivo;

            FileUtils.writeByteArrayToFile(new File(file), archivo);

            return file;
        } catch (IOException ex) {
            Logger.getLogger(ProcesoMigracionLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void programarCargue(final Long idProceso, final Long idOrden, final Long idSecretaria, final Integer tipoRegistro, final Integer tipoArchivo,
            final Date fechaProgramacion, final String nombreArchivo, final String nombreArchivoOrginal, final String observaciones, final Long codigoCarga,
            final Long idUsuario, final String resolucion) {
        ProgramarCargue programarCargue = new ProgramarCargue();

        ProgramarCarguePK programarCarguePK = new ProgramarCarguePK();
        programarCarguePK.setIdProceso(idProceso);
        programarCarguePK.setIdOrden(idOrden);
        programarCargue.setId(programarCarguePK);

        programarCargue.setIdSecretaria(idSecretaria);
        programarCargue.setCodigoEstandar(4);//Codigo estandar final del migracion es 4
        programarCargue.setTipoRegistro(tipoRegistro);
        programarCargue.setTipoArchivo(tipoArchivo);
        programarCargue.setFechaProgramacion(fechaProgramacion);
        programarCargue.setNombreArchivo(nombreArchivo);
        programarCargue.setNombreArchivoOriginal(nombreArchivoOrginal);
        programarCargue.setCodigoEstado(0L);
        programarCargue.setObservaciones(observaciones);
        programarCargue.setCodigoCarga(codigoCarga);
        programarCargue.setIdUsuario(idUsuario);
        programarCargue.setResolucion(resolucion);

        programarCargueDAO.save(programarCargue);
    }

}
