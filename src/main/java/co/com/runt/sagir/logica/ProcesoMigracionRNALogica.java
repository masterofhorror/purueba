/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ArcCarAdicionalDAO;
import co.com.runt.sagir.dao.CargaAdicionalDAO;
import co.com.runt.sagir.dao.CargaDAO;
import co.com.runt.sagir.dao.ConfArcCarAdicionalDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dao.ProcesoMigracionDAO;
import co.com.runt.sagir.dao.ProgramacionCargueDAO;
import co.com.runt.sagir.dao.ProgramarCargueDAO;
import co.com.runt.sagir.dto.ArchivosCargueMigracionDTO;
import co.com.runt.sagir.dto.CargaAdicionalDTO;
import co.com.runt.sagir.dto.CargueRnaDTO;
import co.com.runt.sagir.dto.ConsultaFolio;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeCargaDTO;
import co.com.runt.sagir.dto.RegistrosArchivoDTO;
import co.com.runt.sagir.entities.ArcCar;
import co.com.runt.sagir.entities.ArcCarAdicional;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.CargaAdicional;
import co.com.runt.sagir.entities.ConfArcCar;
import co.com.runt.sagir.entities.ConfArcCarAdicional;
import co.com.runt.sagir.entities.LogArchivoFolio;
import co.com.runt.sagir.entities.MgProgramarCargue;
import co.com.runt.sagir.entities.OrganismosTransitoMigrunt;
import co.com.runt.sagir.utils.ArchivosUtil;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author APENA
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcesoMigracionRNALogica {

    private static final Logger LOGGER = Logger.getLogger(ProcesoMigracionRNALogica.class.getSimpleName());

    @EJB
    private ProcesoMigracionLogica procesoMigracionLogica;

    @EJB
    private ProcesoMigracionDAO procesoMigracionDAO;

    @EJB
    private ProgramarCargueDAO programarCargueDAO;

    @EJB
    private CargaAdicionalDAO cargaAdicionalDAO;

    @EJB
    private CargaDAO cargaDAO;

    @EJB
    private ConfArcCarAdicionalDAO confArcCarAdicionalDAO;

    @EJB
    private ProgramacionCargueDAO programacionCargueDAO;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private ArchivosUtil archivosUtil;

    @EJB
    private ProcesoMigracionDescomprimirLogica procesoMigracionDescomprimirLogica;

    @EJB
    private ArcCarAdicionalDAO arcCarAdicionalDAO;
    
    @EJB
    private ConstanteDAO constanteDAO;

    @Resource
    protected EJBContext context;

    public MensajeCargaDTO guardar(final CargueRnaDTO cargueRnaDTO, final InfoUsuarioDTO usuario, final String ip) {
        MensajeCargaDTO respuesta = new MensajeCargaDTO();
        ConsultaFolio consultaFolioDTO;

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
                Integer codEstadoMgProgramar = 9;

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
                            vConfArcCar = validaConfArcCar(archivosCargueMigracion);
                        }
                    }

                    idSecretariaFinal = organismoTransitoCargue(archivosCargueMigracion.getNombrePropietarios(), tipoCargue, idSecretaria);

                    if (vConfArcCar.isEmpty()) {
                        if (archivosCargueMigracion.getMensaje() == null) {
                            Integer idOrden = 0;
                            Integer existeCautelares = 0;
                            Long codCargaProgramarCargue = programacionCargueDAO.getCodigoProceso();
                            
                            consultaFolioDTO = procesoMigracionDAO.consultarFolioRNA(nombreArchivo, tipoCargue, tutela, idSecretaria, tipoRegistro);
                            String folio = "RNA" + consultaFolioDTO.getIdFolio() + "_" + consultaFolioDTO.getFechaFolio() + "_" + idSecretariaFinal;
                            
                            if (archivosCargueMigracion.getNombreRadicacionesCuenta() != null && !archivosCargueMigracion.getNombreRadicacionesCuenta().isEmpty()) {
                                idOrden++;
                                cargaArchivosRadicacion(archivosCargueMigracion.getRadicacionesCuenta(), idSecretariaFinal, archivosCargueMigracion.getNombreRadicacionesCuenta(), idOrden, usuarioLogin, codCargaProgramarCargue, tipoRegistro, archivosCargueMigracion.getNombreZip(), tutela, tipoCargue, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreInfoAdicionalMedidasCautelares() != null && !archivosCargueMigracion.getNombreInfoAdicionalMedidasCautelares().isEmpty()) {
                                idOrden++;
                                cargaArchivosAdicionalMedidasCautelares(archivosCargueMigracion.getInfoAdicionalMedidasCautelares(), idSecretariaFinal, archivosCargueMigracion.getNombreInfoAdicionalMedidasCautelares(), idOrden, usuarioLogin, codCargaProgramarCargue, tipoRegistro, archivosCargueMigracion.getNombreZip(), tutela, tipoCargue, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombrePrendas() != null && !archivosCargueMigracion.getNombrePrendas().isEmpty()) {
                                idOrden++;
                                cargaArchivosRuntpren(archivosCargueMigracion.getPrendas(), idSecretariaFinal, archivosCargueMigracion.getNombrePrendas(), idOrden, usuarioLogin, codCargaProgramarCargue, tipoRegistro, archivosCargueMigracion.getNombreZip(), tutela, tipoCargue, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreRadicadoMT() != null && !archivosCargueMigracion.getNombreRadicadoMT().isEmpty()) {
                                idOrden++;
                                cargaArchivosRuntradiMt(archivosCargueMigracion.getRadicadoMT(), idSecretariaFinal, archivosCargueMigracion.getNombreRadicadoMT(), idOrden, usuarioLogin, codCargaProgramarCargue, tipoRegistro, archivosCargueMigracion.getNombreZip(), tutela, tipoCargue, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            Carga cargaTablaDatos = cargaTabla(idSecretariaFinal, tipoRegistro, folio);
                            registraLogArchivoRnaTabla(archivosCargueMigracion, folio);

                            if (archivosCargueMigracion.getNombrePropietarios() != null && !archivosCargueMigracion.getNombrePropietarios().isEmpty()) {
                                idOrden++;
                                cargaArchivosPrincipales(archivosCargueMigracion.getPropietarios(), archivosCargueMigracion.getNombrePropietarios(), idSecretariaFinal, tipoRegistro, cargaTablaDatos, nombreArchivo/*ZIP*/, idOrden, usuarioLogin, codCargaProgramarCargue, tutela, tipoCargue, Constantes.TIPO_ARCHIVO_PROPIETARIOS, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreTramites() != null && !archivosCargueMigracion.getNombreTramites().isEmpty()) {
                                idOrden++;
                                cargaArchivosPrincipales(archivosCargueMigracion.getTramites(), archivosCargueMigracion.getNombreTramites(), idSecretariaFinal, tipoRegistro, cargaTablaDatos, nombreArchivo/*ZIP*/, idOrden, usuarioLogin, codCargaProgramarCargue, tutela, tipoCargue, Constantes.TIPO_ARCHIVO_TRAMITES, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreVehiculos() != null && !archivosCargueMigracion.getNombreVehiculos().isEmpty()) {
                                idOrden++;
                                cargaArchivosPrincipales(archivosCargueMigracion.getVehiculos(), archivosCargueMigracion.getNombreVehiculos(), idSecretariaFinal, tipoRegistro, cargaTablaDatos, nombreArchivo/*ZIP*/, idOrden, usuarioLogin, codCargaProgramarCargue, tutela, tipoCargue, Constantes.TIPO_ARCHIVO_VEHICULOS, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            if (archivosCargueMigracion.getNombreMedidasCautelares() != null && !archivosCargueMigracion.getNombreMedidasCautelares().isEmpty()) {
                                idOrden++;
                                existeCautelares++;
                                cargaArchivosPrincipales(archivosCargueMigracion.getMedidasCautelares(), archivosCargueMigracion.getNombreMedidasCautelares(), idSecretariaFinal, tipoRegistro, cargaTablaDatos, nombreArchivo/*ZIP*/, idOrden, usuarioLogin, codCargaProgramarCargue, tutela, tipoCargue, Constantes.TIPO_ARCHIVO_MEDIDAS_CAUTELARES, codEstadoMgProgramar, consultaFolioDTO.getFechaFolio());
                            }

                            //ejecutar los procedimientos almacenados_OK
                            Long codCargaFinal = cargaTablaDatos.getCodCarga().longValue();
                            ejecutarProcedimientosMigracionRna(codCargaFinal, existeCautelares);
                            respuesta.setCodCarga(codCargaFinal);
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

    public void cargaArchivosPrincipales(final byte[] archivo, final String nombreArchivo, final Long idSecretaria, final Integer tipoRegistro, final Carga carga, final String nombreZip, /**/ final Integer idOrden, final String usuarioLogin, final Long codCargaProgramarCargue, final Integer tutela, final Integer tipoCargue, final Integer tipoArchivoTabla, final Integer codEstadoMgProgramar, final String fechaFolioCargaMg) throws ParseException {
        String fecha = "dd/MM/yyyy";
        SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
        String fechaFolioMg = fechaFolio.format(new Date());
        Date fecha1 = fechaFolio.parse(fechaFolioMg);
        Long codCarga = carga.getCodCarga().longValue();
        
        String nomArchivoConRuta = rutaArchivoMigracion(tutela, tipoCargue, nombreArchivo, idSecretaria, fechaFolioCargaMg);
        String nomZipConRuta = rutaArchivoMigracion(tutela, tipoCargue, nombreZip, idSecretaria, fechaFolioCargaMg);

        ConfArcCar confArcCar = RegConfArcCar(nombreArchivo, idSecretaria, tipoRegistro, carga, nombreZip, tipoArchivoTabla, nomArchivoConRuta, nomZipConRuta);
        mgProgramarCargueTabla(codCargaProgramarCargue, idOrden, idSecretaria, tipoRegistro, tipoArchivoTabla, fecha1, nombreArchivo, nombreZip, codCarga, usuarioLogin, tutela, tipoCargue, codEstadoMgProgramar, nomArchivoConRuta, nomZipConRuta);

        List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(archivo);

        if (list != null && !list.isEmpty()) {
            for (RegistrosArchivoDTO dato : list) {
                ArcCar castArcCar = castArcCar(tipoArchivoTabla, nombreArchivo, idSecretaria, confArcCar, carga, dato.getDato());
                //System.out.println("Datos propietario " + dato.getDato());
                boolean registraArcCar = procesoMigracionDAO.registraArcCar(castArcCar);
                if (!registraArcCar) {
                    break;
                }
            }
            //actualiza en CONF_ARC_CAR los registros leidos de ARC_CAR.
            actualizaRegistrosLeidos(codCarga, confArcCar.getCodProceso().longValue());
        }
    }

    public void actualizaRegistrosLeidos(final Long codCarga, final Long codProceso) {
        Long numRegLeidos = programarCargueDAO.registrosLeidos(codCarga, codProceso);
        programarCargueDAO.actualizaRegLeidos(numRegLeidos, codCarga, codProceso);
    }

    public ArcCar castArcCar(final Integer tipoArchivo, final String rutaArchivo, final Long idSecretaria, final ConfArcCar codProceso, final Carga codCarga, final String datoArchivo) {
        ArcCar arcCar = new ArcCar();
        arcCar.setTipoArc(tipoArchivo);
        arcCar.setCodestado(0);
        arcCar.setNomarc(rutaArchivo);
        arcCar.setNomot(idSecretaria.toString());
        arcCar.setCodProceso(codProceso);
        arcCar.setFecProceso(new Date());
        arcCar.setCodCarga(codCarga.getCodCarga());
        arcCar.setDesreg(datoArchivo);
        return arcCar;
    }

    public ArcCarAdicional castArcCarAdicional(final Integer numReg, final Integer tipoArchivo, final String rutaArchivo, final Long idSecretaria, final ConfArcCarAdicional codProceso, final CargaAdicional codCarga, final String datoArchivo, final String nomArchivoArcCarAdicional) {
        ArcCarAdicional arcCarAdicional = new ArcCarAdicional();
        
        //colocar los datos para arcCarAdicional
        Short codEstado = 0;
//        String fecha = "dd/MM/yyyy";
//        SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
//        String fechaFolioMg = fechaFolio.format(new Date());
//        Date fecha1 = fechaFolio.parse(fechaFolioMg);
        
        arcCarAdicional.setNumreg(numReg.longValue());
        arcCarAdicional.setTipoarc(tipoArchivo.shortValue());
        arcCarAdicional.setCodestado(codEstado);
        arcCarAdicional.setNomarc(nomArchivoArcCarAdicional);
        //CAMPONULL
        arcCarAdicional.setNomot(idSecretaria.toString());
        arcCarAdicional.setDesreg(datoArchivo);
        arcCarAdicional.setCodProceso(codProceso.getCodProceso());
        arcCarAdicional.setFecProceso(new Date());
        arcCarAdicional.setCodCarga(BigInteger.valueOf(codCarga.getCodCarga()));
        //CAMPONULL
        return arcCarAdicional;
    }

    public ConfArcCar RegConfArcCar(final String nomArchivo, final Long idSecretaria, final Integer tipoRegistro, final Carga carga, final String nomZip, final Integer tipoArchivo, final String nomArchivoConRuta, final String nomZipConRuta) throws ParseException {
        ConfArcCar confArcCar = new ConfArcCar();
        String fecha = "dd/MM/yyyy";
        SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
        String fechaFolioMg = fechaFolio.format(new Date());
        Date fecha1 = fechaFolio.parse(fechaFolioMg);

        confArcCar.setCodEstandar(Constantes.CODIGO_ESTANDAR_RNA);
        confArcCar.setNomArchivo(nomArchivoConRuta);
        confArcCar.setCodEstado(0);
        confArcCar.setIdSecretaria(idSecretaria);
        confArcCar.setTipoArchivo(tipoArchivo);
        confArcCar.setFecProceso(fecha1);
        confArcCar.setCodBoletin(0);
        confArcCar.setCodCarga(carga);
        confArcCar.setNomArcOriginal(nomZipConRuta);
        confArcCar.setTipoRegistro(tipoRegistro);
        boolean registroConfArcCar = procesoMigracionDAO.registrarConfArcCar(confArcCar);

        return confArcCar;
    }

    public void registraLogArchivoRnaTabla(ArchivosCargueMigracionDTO datosMig, final String folio) {
        LogArchivoFolio logArchivo = new LogArchivoFolio();
        boolean registroLogArchivo = true;

        if (datosMig.getNombrePropietarios() != null) {
            logArchivo.setCodEstado(0);
            logArchivo.setIdFolio(folio);
            logArchivo.setNomArcOriginal(datosMig.getNombrePropietarios());
            registroLogArchivo = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
        }

        if (datosMig.getNombreTramites() != null) {
            logArchivo.setCodEstado(0);
            logArchivo.setIdFolio(folio);
            logArchivo.setNomArcOriginal(datosMig.getNombreTramites());
            registroLogArchivo = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
        }

        if (datosMig.getNombreVehiculos() != null) {
            logArchivo.setCodEstado(0);
            logArchivo.setIdFolio(folio);
            logArchivo.setNomArcOriginal(datosMig.getNombreVehiculos());
            registroLogArchivo = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
        }

        if (datosMig.getNombreMedidasCautelares() != null) {
            logArchivo.setCodEstado(0);
            logArchivo.setIdFolio(folio);
            logArchivo.setNomArcOriginal(datosMig.getNombreMedidasCautelares());
            registroLogArchivo = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
        }
    }

    public Carga cargaTabla(final Long idSecretaria, final Integer tipoRegistro, final String folio) throws ParseException {
        Carga carga = new Carga();

        String fecha = "dd/MM/yyyy";
        SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
        String fechaFolioMg = fechaFolio.format(new Date());
        Date fecha1 = fechaFolio.parse(fechaFolioMg);
        Integer CodEstado = 0;
        OrganismosTransitoMigrunt autoridad = constanteDAO.organimoTransitoMigrunt(idSecretaria);
        carga.setIdSecretaria(autoridad);
        carga.setFechaCarga(fecha1);
        carga.setTipoRegistro(tipoRegistro);
        carga.setIdFolio(folio);
        carga.setCodEstado(CodEstado);

        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            procesoMigracionDAO.registroMigracionQxCarga(carga);
            ut.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            LOGGER.log(Level.SEVERE, "Error realizando el insert en la tabla Carga.", e);
        }
        return carga;
    }

    public String validaConfArcCar(ArchivosCargueMigracionDTO migracionRnaDTO) {
        String mensaje = "";

        if (migracionRnaDTO.getNombreZip() != null) {
            Integer validaArchivoComprimido = procesoMigracionDAO.validaConfArcCar(migracionRnaDTO.getNombreZip());
            if (validaArchivoComprimido != 0) {
                mensaje = ("Ya existe una carga para el archivo " + migracionRnaDTO.getNombreZip());
            }
        }

        return mensaje;
    }

    public Long organismoTransitoCargue(final String nombreArchivo, final Integer tipoCargue, final Long idSecretaria) {
        long idSecretariaFinal;

        //se debe calcular el OT a registrar en las tablas de configuracion_OK
        //cuando es sede operativa se llenan las tablas con el que envian en el nombre de los archivos planos (cortado)_OK
        //cuando es resolucion se llenan las tablas con el que se escoje en sagir (organismo de transito) OJO, NO IMPORTA SI NO ES EL MISMO EN EL NOMBRE DE LOS ARCHIVOS_OK
        idSecretariaFinal = idSecretaria;

        if (tipoCargue == 5) {
            int index = nombreArchivo.indexOf("mprop");
            String ot = nombreArchivo.substring(0, index);
            idSecretariaFinal = Long.parseLong(ot);
        }

        return idSecretariaFinal;
    }

    public void mgProgramarCargueTabla(final Long codCargaProgramarCargue, final Integer idOrden, final Long idSecretaria, final Integer tipoRegistro, final Integer tipoArchivoTabla,
            final Date fechaPrograma, final String nombreArchivo, final String nombreZip, final Long codCargaCarga, final String usuarioLogin, final Integer tutela, final Integer tipoCargue, final Integer codEstadoMgProgramar,
            final String nomArchivoConRuta, final String nomZipConRuta) {
        MgProgramarCargue mgProgramarCargue = new MgProgramarCargue();
        Integer codEstandar = Constantes.CODIGO_ESTANDAR_RNA;
        //Integer codEstado = 9;
        String varResolucion = "N";

        if (tutela == 1) {
            varResolucion = "TU";
        } else {
            if (tipoCargue == 2) {
                varResolucion = "S";
            }

            if (tipoCargue == 3) {
                varResolucion = "CD";
            }

            if (tipoCargue == 6) {
                varResolucion = "IT";
            }

            if (tipoCargue == 4) {
                varResolucion = "RE";
            }

            if (tipoCargue == 5) {
                varResolucion = "ES";
            }
        }

        mgProgramarCargue.setIdProceso(codCargaProgramarCargue.intValue());
        mgProgramarCargue.setIdOrden(idOrden);
        mgProgramarCargue.setIdSecretaria(idSecretaria);
        mgProgramarCargue.setCodEstandar(codEstandar);
        mgProgramarCargue.setTipoRegistro(tipoRegistro);
        mgProgramarCargue.setTipoArc(tipoArchivoTabla);
        mgProgramarCargue.setFechaProgramacion(fechaPrograma);
        mgProgramarCargue.setFechaEjecucion(null);
        mgProgramarCargue.setNombreArchivo(nomArchivoConRuta);
        mgProgramarCargue.setNombreArcOriginal(nomZipConRuta);
        mgProgramarCargue.setCodEstado(codEstadoMgProgramar);
        mgProgramarCargue.setObservaciones(null);
        mgProgramarCargue.setCodCarga(codCargaCarga.intValue());
        mgProgramarCargue.setIdUsuario(Long.valueOf(usuarioLogin));
        mgProgramarCargue.setResolucion(varResolucion);

        boolean tablaMgProgramarCargue = procesoMigracionDAO.save(mgProgramarCargue);

    }

    public void cargaArchivosRadicacion(final byte[] archivo, final Long idSecretaria, final String nombreArchivo, final Integer idOrden, final String usuarioLogin, final Long codCargaProgramarCargue, final Integer tipoRegistro, final String nombreZip, final Integer tutela, final Integer tipoCargue, final Integer codEstadoMgProgramar, final String fechaFolioCargaMg) throws ParseException {
        CargaAdicionalDTO cargaAdicionalDTO = new CargaAdicionalDTO();

        Long codCargaAdicional = cargaAdicionalDAO.getCodigoCarga();
        Long codCargaProceso = confArcCarAdicionalDAO.getCodigoCarga();
        String fecha = "dd/MM/yyyy";
        SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
        String fechaFolioMg = fechaFolio.format(new Date());
        Date fecha1 = fechaFolio.parse(fechaFolioMg);
        Integer tipoArchivoTabla = Constantes.TIPO_ARCHIVO_RADICACIONES;

        CargaAdicional cargaAdicional = new CargaAdicional();

        cargaAdicional.setCodCarga(codCargaAdicional);
        cargaAdicional.setIdSecretaria(idSecretaria);
        cargaAdicional.setFechaCarga(fecha1);
        cargaAdicional.setTipoRegistro(tipoArchivoTabla);
        cargaAdicional.setIdFolio("1");
        cargaAdicional.setCodEstado(0);
        cargaAdicional.setIdBoletin(codCargaAdicional);

        boolean tablaCargaAdicional = false;
        tablaCargaAdicional = cargaAdicionalDAO.save(cargaAdicional);

        ConfArcCarAdicional confArcCarAdicional = new ConfArcCarAdicional();
        
        String nomArchivoConfArcCarAdicional = rutaArchivoMigracion(tutela, tipoCargue, nombreArchivo, idSecretaria, fechaFolioCargaMg);
        String nomZipConRuta = rutaArchivoMigracion(tutela, tipoCargue, nombreArchivo, idSecretaria, fechaFolioCargaMg);
        
        confArcCarAdicional.setCodProceso(codCargaProceso.intValue());
        confArcCarAdicional.setCodEstandar(Constantes.CODIGO_ESTANDAR_RNA);
        confArcCarAdicional.setNomArchivo(nomArchivoConfArcCarAdicional);//en el sagir se coloca el nombre con la ruta
        confArcCarAdicional.setCodEstado(0);
        confArcCarAdicional.setIdSecretaria(idSecretaria);
        confArcCarAdicional.setTipoArc(tipoArchivoTabla);
        confArcCarAdicional.setFecProceso(fecha1);
        confArcCarAdicional.setDesError(null);
        confArcCarAdicional.setNumRegleidos(0);
        confArcCarAdicional.setCodBoletin(codCargaAdicional);
        confArcCarAdicional.setCodCarga(codCargaAdicional);
        confArcCarAdicional.setCantError((long) 0);
        confArcCarAdicional.setNomArcOriginal(nombreArchivo);//seria bueno que solo se colocara el nombre del archivo como lo envia el ot
        confArcCarAdicional.setTipoRegistro((long) 3);

        boolean tablaConfArcCarAdicional = false;
        tablaConfArcCarAdicional = confArcCarAdicionalDAO.save(confArcCarAdicional);

        if (tablaCargaAdicional && tablaConfArcCarAdicional) {
            //METODO PARA INSERTAR EN LA TABLA ARC_CAR_ADICIONAL (generico)
            List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(archivo);

            if (list != null && !list.isEmpty()) {
                for (RegistrosArchivoDTO dato : list) {
                    Integer codArcCarAdicional = arcCarAdicionalDAO.getCodigoProceso().intValue();
                    ArcCarAdicional castArcCarAdicional = castArcCarAdicional(codArcCarAdicional, tipoArchivoTabla, nombreArchivo, idSecretaria, confArcCarAdicional, cargaAdicional, dato.getDato(), nomArchivoConfArcCarAdicional);

                    boolean registraArcCar = arcCarAdicionalDAO.registraArcCarAdicional(castArcCarAdicional);
                    if (!registraArcCar) {
                        break;
                    }
                }
            }
            //ejecutar el proceso MIGRACIONQX.P_RUNTRADI_OK
            almacenados.pPruntradi(codCargaAdicional, idSecretaria);
            //metodo para insertar en la tabla migracionqx.mg_programar_cargue_OK
            mgProgramarCargueTabla(codCargaProgramarCargue, idOrden, idSecretaria, tipoRegistro, tipoArchivoTabla, fecha1, nombreArchivo, nombreZip, codCargaAdicional, usuarioLogin, tutela, tipoCargue, codEstadoMgProgramar, nomArchivoConfArcCarAdicional, nomZipConRuta);
        }
    }

    public void cargaArchivosAdicionalMedidasCautelares(final byte[] archivo, final Long idSecretaria, final String nombreArchivo, final Integer idOrden, final String usuarioLogin, final Long codCargaProgramarCargue, final Integer tipoRegistro, final String nombreZip, final Integer tutela, final Integer tipoCargue, final Integer codEstadoMgProgramar, final String fechaFolioCargaMg) throws ParseException {

        Long codCargaAdicional = cargaAdicionalDAO.getCodigoCarga();
        Long codCargaProceso = confArcCarAdicionalDAO.getCodigoCarga();
        String fecha = "dd/MM/yyyy";
        SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
        String fechaFolioMg = fechaFolio.format(new Date());
        Date fecha1 = fechaFolio.parse(fechaFolioMg);
        Integer tipoArchivoTabla = Constantes.TIPO_ARCHIVO_ADICIONAL_MEDIDAS_CAUTELARES;

        CargaAdicional cargaAdicional = new CargaAdicional();

        cargaAdicional.setCodCarga(codCargaAdicional);
        cargaAdicional.setIdSecretaria(idSecretaria);
        cargaAdicional.setFechaCarga(fecha1);
        cargaAdicional.setTipoRegistro(3);
        cargaAdicional.setIdFolio("1");
        cargaAdicional.setCodEstado(0);
        cargaAdicional.setIdBoletin(codCargaAdicional);

        boolean tablaCargaAdicional = false;
        tablaCargaAdicional = cargaAdicionalDAO.save(cargaAdicional);

        ConfArcCarAdicional confArcCarAdicional = new ConfArcCarAdicional();
        
        String nomArchivoConfArcCarAdicional = rutaArchivoMigracion(tutela, tipoCargue, nombreArchivo, idSecretaria, fechaFolioCargaMg);
        String nomZipConRuta = rutaArchivoMigracion(tutela, tipoCargue, nombreZip, idSecretaria, fechaFolioCargaMg);
        
        confArcCarAdicional.setCodProceso(codCargaProceso.intValue());
        confArcCarAdicional.setCodEstandar(Constantes.CODIGO_ESTANDAR_RNA);
        confArcCarAdicional.setNomArchivo(nomArchivoConfArcCarAdicional);//en el sagir se coloca el nombre con la ruta
        confArcCarAdicional.setCodEstado(0);
        confArcCarAdicional.setIdSecretaria(idSecretaria);
        confArcCarAdicional.setTipoArc(tipoArchivoTabla);
        confArcCarAdicional.setFecProceso(fecha1);
        confArcCarAdicional.setDesError(null);
        confArcCarAdicional.setNumRegleidos(0);
        confArcCarAdicional.setCodBoletin(codCargaAdicional);
        confArcCarAdicional.setCodCarga(codCargaAdicional);
        confArcCarAdicional.setCantError((long) 0);
        confArcCarAdicional.setNomArcOriginal(nombreArchivo);//seria bueno que solo se colocara el nombre del archivo como lo envia el ot
        confArcCarAdicional.setTipoRegistro((long) 3);

        boolean tablaConfArcCarAdicional = false;
        tablaConfArcCarAdicional = confArcCarAdicionalDAO.save(confArcCarAdicional);

        if (tablaCargaAdicional && tablaConfArcCarAdicional) {
            //METODO PARA INSERTAR EN LA TABLA ARC_CAR_ADICIONAL (generico)
            List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(archivo);

            if (list != null && !list.isEmpty()) {
                for (RegistrosArchivoDTO dato : list) {
                    Integer codArcCarAdicional = arcCarAdicionalDAO.getCodigoProceso().intValue();
                    ArcCarAdicional castArcCarAdicional = castArcCarAdicional(codArcCarAdicional, tipoArchivoTabla, nombreArchivo, idSecretaria, confArcCarAdicional, cargaAdicional, dato.getDato(), nomArchivoConfArcCarAdicional);

                    boolean registraArcCar = arcCarAdicionalDAO.registraArcCarAdicional(castArcCarAdicional);
                    if (!registraArcCar) {
                        break;
                    }
                }
            }
            //ejecutar el proceso MIGRACIONQX.P_RUNT_MEDIDAS_CAUTELARES_OK
            almacenados.pRuntMedidasCautelares(codCargaAdicional, idSecretaria);
            //metodo para insertar en la tabla migracionqx.mg_programar_cargue_OK
            mgProgramarCargueTabla(codCargaProgramarCargue, idOrden, idSecretaria, tipoRegistro, tipoArchivoTabla, fecha1, nombreArchivo, nombreZip, codCargaAdicional, usuarioLogin, tutela, tipoCargue, codEstadoMgProgramar, nomArchivoConfArcCarAdicional, nomZipConRuta);
        }

    }

    public void cargaArchivosRuntpren(final byte[] archivo, final Long idSecretaria, final String nombreArchivo, final Integer idOrden, final String usuarioLogin, final Long codCargaProgramarCargue, final Integer tipoRegistro, final String nombreZip, final Integer tutela, final Integer tipoCargue, final Integer codEstadoMgProgramar, final String fechaFolioCargaMg) throws ParseException {

        Long codCargaAdicional = cargaAdicionalDAO.getCodigoCarga();
        Long codCargaProceso = confArcCarAdicionalDAO.getCodigoCarga();
        String fecha = "dd/MM/yyyy";
        SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
        String fechaFolioMg = fechaFolio.format(new Date());
        Date fecha1 = fechaFolio.parse(fechaFolioMg);
        Integer tipoArchivoTabla = Constantes.TIPO_ARCHIVO_PRENDAS;

        CargaAdicional cargaAdicional = new CargaAdicional();

        cargaAdicional.setCodCarga(codCargaAdicional);
        cargaAdicional.setIdSecretaria(idSecretaria);
        cargaAdicional.setFechaCarga(fecha1);
        cargaAdicional.setTipoRegistro(3);
        cargaAdicional.setIdFolio("1");
        cargaAdicional.setCodEstado(0);
        cargaAdicional.setIdBoletin(codCargaAdicional);

        boolean tablaCargaAdicional = false;
        tablaCargaAdicional = cargaAdicionalDAO.save(cargaAdicional);

        ConfArcCarAdicional confArcCarAdicional = new ConfArcCarAdicional();
        
        String nomArchivoConfArcCarAdicional = rutaArchivoMigracion(tutela, tipoCargue, nombreArchivo, idSecretaria, fechaFolioCargaMg);
        String nomZipConRuta = rutaArchivoMigracion(tutela, tipoCargue, nombreZip, idSecretaria, fechaFolioCargaMg);
        
        confArcCarAdicional.setCodProceso(codCargaProceso.intValue());
        confArcCarAdicional.setCodEstandar(Constantes.CODIGO_ESTANDAR_RNA);
        confArcCarAdicional.setNomArchivo(nomArchivoConfArcCarAdicional);//en el sagir se coloca el nombre con la ruta
        confArcCarAdicional.setCodEstado(0);
        confArcCarAdicional.setIdSecretaria(idSecretaria);
        confArcCarAdicional.setTipoArc(tipoArchivoTabla);
        confArcCarAdicional.setFecProceso(fecha1);
        confArcCarAdicional.setDesError(null);
        confArcCarAdicional.setNumRegleidos(0);
        confArcCarAdicional.setCodBoletin(codCargaAdicional);
        confArcCarAdicional.setCodCarga(codCargaAdicional);
        confArcCarAdicional.setCantError((long) 0);
        confArcCarAdicional.setNomArcOriginal(nombreArchivo);//seria bueno que solo se colocara el nombre del archivo como lo envia el ot
        confArcCarAdicional.setTipoRegistro((long) 3);

        boolean tablaConfArcCarAdicional = false;
        tablaConfArcCarAdicional = confArcCarAdicionalDAO.save(confArcCarAdicional);

        if (tablaCargaAdicional && tablaConfArcCarAdicional) {
            //METODO PARA INSERTAR EN LA TABLA ARC_CAR_ADICIONAL (generico)
            List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(archivo);

            if (list != null && !list.isEmpty()) {
                for (RegistrosArchivoDTO dato : list) {
                    Integer codArcCarAdicional = arcCarAdicionalDAO.getCodigoProceso().intValue();
                    ArcCarAdicional castArcCarAdicional = castArcCarAdicional(codArcCarAdicional, tipoArchivoTabla, nombreArchivo, idSecretaria, confArcCarAdicional, cargaAdicional, dato.getDato(), nomArchivoConfArcCarAdicional);

                    boolean registraArcCar = arcCarAdicionalDAO.registraArcCarAdicional(castArcCarAdicional);
                    if (!registraArcCar) {
                        break;
                    }
                }
            }
            //ejecutar el proceso MIGRACIONQX.P_RUNT_MEDIDAS_CAUTELARES_OK
            almacenados.pRuntpren(codCargaAdicional);
            //metodo para insertar en la tabla migracionqx.mg_programar_cargue_OK
            mgProgramarCargueTabla(codCargaProgramarCargue, idOrden, idSecretaria, tipoRegistro, tipoArchivoTabla, fecha1, nombreArchivo, nombreZip, codCargaAdicional, usuarioLogin, tutela, tipoCargue, codEstadoMgProgramar, nomArchivoConfArcCarAdicional, nomZipConRuta);
        }
    }

    public void cargaArchivosRuntradiMt(final byte[] archivo, final Long idSecretaria, final String nombreArchivo, final Integer idOrden, final String usuarioLogin, final Long codCargaProgramarCargue, final Integer tipoRegistro, final String nombreZip, final Integer tutela, final Integer tipoCargue, final Integer codEstadoMgProgramar, final String fechaFolioCargaMg) throws ParseException {

        Long codCargaAdicional = cargaAdicionalDAO.getCodigoCarga();
        Long codCargaProceso = confArcCarAdicionalDAO.getCodigoCarga();
        String fecha = "dd/MM/yyyy";
        SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
        String fechaFolioMg = fechaFolio.format(new Date());
        Date fecha1 = fechaFolio.parse(fechaFolioMg);
        Integer tipoArchivoTabla = Constantes.TIPO_ARCHIVO_RADICACIONES_MT;

        CargaAdicional cargaAdicional = new CargaAdicional();

        cargaAdicional.setCodCarga(codCargaAdicional);
        cargaAdicional.setIdSecretaria(idSecretaria);
        cargaAdicional.setFechaCarga(fecha1);
        cargaAdicional.setTipoRegistro(tipoArchivoTabla);
        cargaAdicional.setIdFolio("1");
        cargaAdicional.setCodEstado(0);
        cargaAdicional.setIdBoletin(codCargaAdicional);

        boolean tablaCargaAdicional = false;
        tablaCargaAdicional = cargaAdicionalDAO.save(cargaAdicional);

        ConfArcCarAdicional confArcCarAdicional = new ConfArcCarAdicional();
        
        String nomArchivoConfArcCarAdicional = rutaArchivoMigracion(tutela, tipoCargue, nombreArchivo, idSecretaria, fechaFolioCargaMg);
        String nomZipConRuta = rutaArchivoMigracion(tutela, tipoCargue, nombreZip, idSecretaria, fechaFolioCargaMg);
        
        confArcCarAdicional.setCodProceso(codCargaProceso.intValue());
        confArcCarAdicional.setCodEstandar(Constantes.CODIGO_ESTANDAR_RNA);
        confArcCarAdicional.setNomArchivo(nomArchivoConfArcCarAdicional);
        confArcCarAdicional.setCodEstado(0);
        confArcCarAdicional.setIdSecretaria(idSecretaria);
        confArcCarAdicional.setTipoArc(tipoArchivoTabla);
        confArcCarAdicional.setFecProceso(fecha1);
        confArcCarAdicional.setDesError(null);
        confArcCarAdicional.setNumRegleidos(0);
        confArcCarAdicional.setCodBoletin(codCargaAdicional);
        confArcCarAdicional.setCodCarga(codCargaAdicional);
        confArcCarAdicional.setCantError((long) 0);
        confArcCarAdicional.setNomArcOriginal(nombreArchivo);//seria bueno que solo se colocara el nombre del archivo como lo envia el ot
        confArcCarAdicional.setTipoRegistro((long) 3);

        boolean tablaConfArcCarAdicional = false;
        tablaConfArcCarAdicional = confArcCarAdicionalDAO.save(confArcCarAdicional);

        if (tablaCargaAdicional && tablaConfArcCarAdicional) {
            //METODO PARA INSERTAR EN LA TABLA ARC_CAR_ADICIONAL (generico)
            List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(archivo);

            if (list != null && !list.isEmpty()) {
                for (RegistrosArchivoDTO dato : list) {
                    Integer codArcCarAdicional = arcCarAdicionalDAO.getCodigoProceso().intValue();
                    ArcCarAdicional castArcCarAdicional = castArcCarAdicional(codArcCarAdicional, tipoArchivoTabla, nombreArchivo, idSecretaria, confArcCarAdicional, cargaAdicional, dato.getDato(), nomArchivoConfArcCarAdicional);

                    boolean registraArcCar = arcCarAdicionalDAO.registraArcCarAdicional(castArcCarAdicional);
                    if (!registraArcCar) {
                        break;
                    }
                }
            }
            //ejecutar el proceso MIGRACIONQX.P_RUNT_MEDIDAS_CAUTELARES_OK
            almacenados.pRuntRADImT(codCargaAdicional);
            //metodo para insertar en la tabla migracionqx.mg_programar_cargue_OK
            mgProgramarCargueTabla(codCargaProgramarCargue, idOrden, idSecretaria, tipoRegistro, tipoArchivoTabla, fecha1, nombreArchivo, nombreZip, codCargaAdicional, usuarioLogin, tutela, tipoCargue, codEstadoMgProgramar, nomArchivoConfArcCarAdicional, nomZipConRuta);
        }
    }

    public void ejecutarProcedimientosMigracionRna(final Long codCarga, final Integer existeCautelares) {

        almacenados.poblamientoRna(codCarga);
        if (existeCautelares != 0) {
            almacenados.pTtamcaut(codCarga);
        }
        almacenados.pAplicacionCriteriosRna(codCarga);
        almacenados.pMigrarRnaMigracionqx(codCarga);
        almacenados.pActVehiProdCambioRes(codCarga);
        almacenados.pActPropietProdCambioRes(codCarga);
        almacenados.pSincronizarCargueProd(codCarga);

        //actualzar el estado a 1 de confarc_car para el codigo de carga en proceso.
        programarCargueDAO.actualizarCodEstadoConfArcCar(codCarga);
    }

    public String rutaArchivoMigracion(final Integer tutela, final Integer tipoCargue, final String nombreArchivo, final Long idSecretariaFinal, final String fechaFolio) {
        String rutaFinal = "";
        String nombreCorto = "";
        String año = fechaFolio.substring(0, 4);
        String mes = fechaFolio.substring(5, 7);

        if (mes.equals("01")) {
            mes = "ENERO";
        }
        if (mes.equals("02")) {
            mes = "FEBRERO";
        }
        if (mes.equals("03")) {
            mes = "MARZO";
        }
        if (mes.equals("04")) {
            mes = "ABRIL";
        }
        if (mes.equals("05")) {
            mes = "MAYO";
        }
        if (mes.equals("06")) {
            mes = "JUNIO";
        }
        if (mes.equals("07")) {
            mes = "JULIO";
        }
        if (mes.equals("08")) {
            mes = "AGOSTO";
        }
        if (mes.equals("09")) {
            mes = "SEPTIEMBRE";
        }
        if (mes.equals("10")) {
            mes = "OCTUBRE";
        }
        if (mes.equals("11")) {
            mes = "NOVIEMBRE";
        }
        if (mes.equals("12")) {
            mes = "DICIEMBRE";
        }

        nombreCorto = procesoMigracionDAO.descripcionCortaOt(idSecretariaFinal);
        
        if (tutela == 1) {
            rutaFinal = Constantes.RUTA_TUTELA + año + "/" + mes + "/" + fechaFolio + "/" + nombreCorto + "/" + nombreArchivo;
        } else {;
            rutaFinal = Constantes.RUTA_NORMAL_RESOL_SO + año + "/" + mes + "/" + fechaFolio + "/" + nombreCorto + "/" + nombreArchivo;
            if(tipoCargue == 3){
                rutaFinal = Constantes.RUTA_CD + año + "/" + mes + "/" + fechaFolio + "/" + nombreCorto + "/" + nombreArchivo;
            }
            if (tipoCargue == 6){
                rutaFinal = Constantes.RUTA_IMPORTACION_TEMPORAL + año + "/" + mes + "/" + fechaFolio + "/" + nombreCorto + "/" + nombreArchivo;
            }
            if (tipoCargue == 7){
                rutaFinal = Constantes.RUTA_RNTC_VEHICULOS_CARGA + año + "/" + mes + "/" + fechaFolio + "/" + nombreCorto + "/" + nombreArchivo;
            }
            if (tipoCargue == 8){
                rutaFinal = Constantes.RUTA_VEHICULOS_SEGURIDAD_ESTADO + año + "/" + mes + "/" + fechaFolio + "/" + nombreCorto + "/" + nombreArchivo;
            }
        }
        return rutaFinal;
    }

}
