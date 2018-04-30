/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CargaAdicionalDAO;
import co.com.runt.sagir.dao.ConfArcCarAdicionalDAO;
import co.com.runt.sagir.dao.ProcesamientoCargueDAO;
import co.com.runt.sagir.dao.ProcesoMigracionDAO;
import co.com.runt.sagir.dao.ProgramacionCargueDAO;
import co.com.runt.sagir.dao.ProgramarCargueDAO;
import co.com.runt.sagir.dto.ArchivosCargueMigracionDTO;
import co.com.runt.sagir.dto.CargueRnaDTO;
import co.com.runt.sagir.dto.ConsultaFolio;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.MensajeProgramadoDTO;
import co.com.runt.sagir.dto.ProgramacionCargueDTO;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.CodigoCarga;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class ProgramacionCargueLogica {

    private static final Logger LOGGER = Logger.getLogger(ProgramacionCargueLogica.class.getSimpleName());

    @EJB
    private ProgramacionCargueDAO programacionCargueDAO;

    @EJB
    private ProgramarCargueDAO programarCargueDAO;

    @EJB
    private ProcesamientoCargueDAO procesamientoCargueDAO;

    @EJB
    private CargaAdicionalDAO cargaAdicionalDAO;

    @EJB
    private ConfArcCarAdicionalDAO confArcCarAdicionalDAO;

    @EJB
    private ProcesoMigracionRNALogica procesoMigracionRNALogica;
    
    @EJB
    private ProcesoMigracionDAO procesoMigracionDAO;
    
    @EJB
    private ProcesoMigracionLogica procesoMigracionLogica;
    
    @EJB
    private ProcesoMigracionDescomprimirLogica procesoMigracionDescomprimirLogica;

    //SI SE USA
    public MensajeDTO consultar() {
        MensajeDTO respuesta = new MensajeDTO();
        respuesta.setCodmensaje(Mensajes.ERROR);

        try {
            respuesta.setObject(TransformacionDozer.transformar(programarCargueDAO.buscarProgramacionCargues(), ProgramacionCargueDTO.class));
            respuesta.setCodmensaje(Mensajes.OK);
        } catch (Exception e) {
            respuesta.setMensaje("Error realizando la consulta de programación de cargues. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error realizando la consulta de programación de cargues.", e);
        }
        return respuesta;
    }

    //SI SE USA
    public MensajeProgramadoDTO procesar() {
        MensajeProgramadoDTO respuesta = new MensajeProgramadoDTO();
        //crear el dto para que devuelva adionalmente un arrayList de codigo de cargas.
        respuesta.setCodmensaje(Mensajes.ERROR);

        try {
            List<CodigoCarga> codCargaProgramado = programarCargueDAO.codigosCargaProceso();

            for (CodigoCarga dato : codCargaProgramado) {
                Long codCarga = dato.getCodCarga();
                Integer existeMediCaut = programarCargueDAO.existeMedidaCautelarCargue(codCarga);
                //System.out.println(codCarga + "_" + existeMediCaut);
                procesoMigracionRNALogica.ejecutarProcedimientosMigracionRna(codCarga, existeMediCaut);
            }
            
            respuesta.setCodCargaProgramado(codCargaProgramado);
            respuesta.setCodmensaje(Mensajes.OK);
            respuesta.setMensaje("Cargues procesados correctamente.");
        } catch (Exception e) {
            respuesta.setMensaje("Error realizando el procesamiento de cargues programados. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error realizando el procesamiento de cargues programados.", e);
        }
        return respuesta;
    }

    //SI LO USO
    public MensajeDTO guardar(final CargueRnaDTO cargueRnaDTO, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO respuesta = new MensajeDTO();
        ConsultaFolio consultaFolioDTO = new ConsultaFolio();

        respuesta.setCodmensaje(Mensajes.ERROR);

        try {
            String error = procesoMigracionLogica.validarNombreArchivoZip(cargueRnaDTO);

            if (error.isEmpty()) {
                String vConfArcCar = "";
                Integer tutela = Integer.parseInt(cargueRnaDTO.getTutela());
                Integer tipoCargue = Integer.parseInt(cargueRnaDTO.getTipoCargue());
                Long idSecretaria = cargueRnaDTO.getOrganismoTransito().getIdSecretaria();
                String idSecretariaString = idSecretaria.toString();
                String nombreArchivo = cargueRnaDTO.getNombreArchivoEmpaquetado();
                Integer tipoRegistro = Integer.parseInt(cargueRnaDTO.getTipoRegistro());//1-rna
                String usuarioLogin = usuario.getLogin();
                Integer existeArchiMigra = 1;
                Integer codEstadoMgProgramar = 0;
                

                if (tutela == 2) {
                    if (tipoCargue == 1 || tipoCargue == 2 || tipoCargue == 5) {
                        //validar si existe una carga en car_arch_migraen estado 33
                        existeArchiMigra = procesoMigracionDAO.validacionExisteCarArcMigra(tipoRegistro, nombreArchivo);
                    }
                }

                if (existeArchiMigra != 0) {

                    ArchivosCargueMigracionDTO archivosCargueMigracion = procesoMigracionDescomprimirLogica.validarArchivoDat(cargueRnaDTO, idSecretariaString, tipoCargue, ip, usuario);
                    archivosCargueMigracion.setNombreZip(nombreArchivo);
                    Long idSecretariaFinal = (long) 0;

                    if (tutela == 2) {
                        if (tipoCargue == 1 || tipoCargue == 2 || tipoCargue == 5) {
                            //valida si el archivo zip ya existe en conf_arc_car_OK
                            vConfArcCar = procesoMigracionRNALogica.validaConfArcCar(archivosCargueMigracion);
                        }
                    }

                    idSecretariaFinal = procesoMigracionRNALogica.organismoTransitoCargue(archivosCargueMigracion.getNombrePropietarios(), tipoCargue, idSecretaria);

                    if (vConfArcCar.isEmpty()) {
                        if (archivosCargueMigracion.getMensaje() == null) {
                            Integer idOrden = 0;
                            Long codCargaProgramarCargue = programacionCargueDAO.getCodigoProceso();
                            
                            consultaFolioDTO = procesoMigracionDAO.consultarFolioRNA(nombreArchivo, tipoCargue, tutela, idSecretaria, tipoRegistro);
                            String folio = "RNA" + consultaFolioDTO.getIdFolio() + "_" + consultaFolioDTO.getFechaFolio() + "_" + idSecretariaFinal;
                            
                            if (archivosCargueMigracion.getNombreRadicacionesCuenta() != null && !archivosCargueMigracion.getNombreRadicacionesCuenta().isEmpty()) {
                                idOrden++;
                                procesoMigracionRNALogica.cargaArchivosRadicacion(archivosCargueMigracion.getRadicacionesCuenta(), idSecretariaFinal, archivosCargueMigracion.getNombreRadicacionesCuenta(), idOrden, usuarioLogin, codCargaProgramarCargue, tipoRegistro, archivosCargueMigracion.getNombreZip(), tutela, tipoCargue, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreInfoAdicionalMedidasCautelares() != null && !archivosCargueMigracion.getNombreInfoAdicionalMedidasCautelares().isEmpty()) {
                                idOrden++;
                                procesoMigracionRNALogica.cargaArchivosAdicionalMedidasCautelares(archivosCargueMigracion.getInfoAdicionalMedidasCautelares(), idSecretariaFinal, archivosCargueMigracion.getNombreInfoAdicionalMedidasCautelares(), idOrden, usuarioLogin, codCargaProgramarCargue, tipoRegistro, archivosCargueMigracion.getNombreZip(), tutela, tipoCargue, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombrePrendas() != null && !archivosCargueMigracion.getNombrePrendas().isEmpty()) {
                                idOrden++;
                                procesoMigracionRNALogica.cargaArchivosRuntpren(archivosCargueMigracion.getPrendas(), idSecretariaFinal, archivosCargueMigracion.getNombrePrendas(), idOrden, usuarioLogin, codCargaProgramarCargue, tipoRegistro, archivosCargueMigracion.getNombreZip(), tutela, tipoCargue, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreRadicadoMT() != null && !archivosCargueMigracion.getNombreRadicadoMT().isEmpty()) {
                                idOrden++;
                                procesoMigracionRNALogica.cargaArchivosRuntradiMt(archivosCargueMigracion.getRadicadoMT(), idSecretariaFinal, archivosCargueMigracion.getNombreRadicadoMT(), idOrden, usuarioLogin, codCargaProgramarCargue, tipoRegistro, archivosCargueMigracion.getNombreZip(), tutela, tipoCargue, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }
                            
                            Carga cargaTablaDatos = procesoMigracionRNALogica.cargaTabla(idSecretariaFinal, tipoRegistro, folio);
                            procesoMigracionRNALogica.registraLogArchivoRnaTabla(archivosCargueMigracion, folio);

                            if (archivosCargueMigracion.getNombrePropietarios() != null && !archivosCargueMigracion.getNombrePropietarios().isEmpty()) {
                                idOrden++;
                                procesoMigracionRNALogica.cargaArchivosPrincipales(archivosCargueMigracion.getPropietarios(), archivosCargueMigracion.getNombrePropietarios(), idSecretariaFinal, tipoRegistro, cargaTablaDatos, nombreArchivo/*ZIP*/, idOrden, usuarioLogin, codCargaProgramarCargue, tutela, tipoCargue, Constantes.TIPO_ARCHIVO_PROPIETARIOS, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreTramites() != null && !archivosCargueMigracion.getNombreTramites().isEmpty()) {
                                idOrden++;
                                procesoMigracionRNALogica.cargaArchivosPrincipales(archivosCargueMigracion.getTramites(), archivosCargueMigracion.getNombreTramites(), idSecretariaFinal, tipoRegistro, cargaTablaDatos, nombreArchivo/*ZIP*/, idOrden, usuarioLogin, codCargaProgramarCargue, tutela, tipoCargue, Constantes.TIPO_ARCHIVO_TRAMITES, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreVehiculos() != null && !archivosCargueMigracion.getNombreVehiculos().isEmpty()) {
                                idOrden++;
                                procesoMigracionRNALogica.cargaArchivosPrincipales(archivosCargueMigracion.getVehiculos(), archivosCargueMigracion.getNombreVehiculos(), idSecretariaFinal, tipoRegistro, cargaTablaDatos, nombreArchivo/*ZIP*/, idOrden, usuarioLogin, codCargaProgramarCargue, tutela, tipoCargue, Constantes.TIPO_ARCHIVO_VEHICULOS, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreMedidasCautelares() != null && !archivosCargueMigracion.getNombreMedidasCautelares().isEmpty()) {
                                idOrden++;
                                procesoMigracionRNALogica.cargaArchivosPrincipales(archivosCargueMigracion.getMedidasCautelares(), archivosCargueMigracion.getNombreMedidasCautelares(), idSecretariaFinal, tipoRegistro, cargaTablaDatos, nombreArchivo/*ZIP*/, idOrden, usuarioLogin, codCargaProgramarCargue, tutela, tipoCargue, Constantes.TIPO_ARCHIVO_MEDIDAS_CAUTELARES, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            respuesta.setCodmensaje(Mensajes.OK);
                            respuesta.setMensaje("Archivo programado con exito.");
                        } else {
                            respuesta.setMensaje(archivosCargueMigracion.getMensaje());
                        }
                    } else {
                        respuesta.setMensaje(vConfArcCar);
                    }
                } else {
                    respuesta.setMensaje("El archivo no posee un cargue pendiente.");
                }
            } else {
                respuesta.setMensaje(error);
            }
        } catch (NumberFormatException | ParseException e) {
            respuesta.setMensaje("Error realizando el cargue RNA. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error realizando el cargue RNA.", e);
        }
        return respuesta;
    }
}
