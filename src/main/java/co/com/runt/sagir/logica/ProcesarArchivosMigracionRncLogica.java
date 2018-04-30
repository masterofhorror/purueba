/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dao.ProcesoMigracionDAO;
import co.com.runt.sagir.dao.ProgramacionCargueDAO;
import co.com.runt.sagir.dto.ArchivosProcesoMigracionRncDTO;
import co.com.runt.sagir.dto.CargueArchivoProcesoMigracionDTO;
import co.com.runt.sagir.dto.CargueRncDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.PathDTO;
import co.com.runt.sagir.dto.RegistrosArchivoDTO;
import co.com.runt.sagir.entities.ArcCar;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.ConfArcCar;
import co.com.runt.sagir.entities.LogArchivoFolio;
import co.com.runt.sagir.entities.MgProgramarCargue;
import co.com.runt.sagir.utils.ArchivosUtil;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcesarArchivosMigracionRncLogica {

    @Resource
    protected EJBContext context;

    @EJB
    private ProcesoMigracionDAO procesoMigracionDAO;

    @EJB
    private ArchivosUtil archivosUtil;

    @EJB
    private ProgramacionCargueDAO programacionCargueDAO;

    @EJB
    private PlSqlDAO almacenados;

    public CargueArchivoProcesoMigracionDTO procesarArchivos(final ArchivosProcesoMigracionRncDTO migracionRncDTO, final CargueRncDTO cargueRncDTO, final InfoUsuarioDTO usuario, final PathDTO pathDto) {
        CargueArchivoProcesoMigracionDTO salida = new CargueArchivoProcesoMigracionDTO();
        String mensaje = null;
        String validaConfArcCar = validaConfArcCar(migracionRncDTO);
        if (validaConfArcCar != null) {
            mensaje = validaConfArcCar;
        }
        String validaMgProgramarCarga = validaMgProgramaCargue(migracionRncDTO);
        if (validaMgProgramarCarga != null) {
            mensaje = validaMgProgramarCarga;
        }
        if (mensaje == null) {
            Integer tipoRegistro = Integer.parseInt(cargueRncDTO.getTipoRegistro());
            Integer idFolio = procesoMigracionDAO.tipoRegistroRNC(tipoRegistro);
            String fecha = "yyyyMMdd";
            SimpleDateFormat fechaFolio = new SimpleDateFormat(fecha);
            String folio = "RNC" + idFolio + "_" + fechaFolio.format(new Date()) + "_" + cargueRncDTO.getOrganismoTransito().getIdSecretaria();
            Long autoridad = cargueRncDTO.getOrganismoTransito().getIdSecretaria();
            if (autoridad != null) {
                try {
                    Carga carga = new Carga();
                    carga.setCodEstado(0);
                    carga.setTipoRegistro(2);
                    carga.setFechaCarga(new Date());
                    carga.setIdSecretaria(cargueRncDTO.getOrganismoTransito());
                    carga.setIdFolio(folio);
                    UserTransaction ut = context.getUserTransaction();
                    ut.begin();
                    boolean registroCarga = procesoMigracionDAO.registroMigracionQxCarga(carga);
                    ut.commit();
                    if (registroCarga) {
                        String registroTablasControl = registroTablasControl(migracionRncDTO, folio, carga, autoridad, tipoRegistro, usuario.getLogin(), pathDto, cargueRncDTO);
                        if (registroTablasControl == null) {
                            almacenados.poblamientoRNC(carga.getCodCarga().toString());
                            almacenados.pCriteriosRNC2(carga.getCodCarga().toString());
                            almacenados.migracionQxPMigrarRNC(carga.getCodCarga().toString());
                            almacenados.sincronizarMasivoRnc();
                            salida.setCodCarga(carga.getCodCarga());
                            salida.setCodmensaje(Mensajes.OK);
                        } else {
                            salida.setCodmensaje(Mensajes.ERROR);
                            salida.setMensaje(registroTablasControl);
                            ut.rollback();
                        }
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "Error al registrar el código de carga en la tabla MIGRACIONQX.CARGA";
                        salida.setMensaje(mensaje);
                    }
                } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
                }

            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = "Error al consultar la autoridad de tránsito.";
                salida.setMensaje(mensaje);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public String registroArCar() {
        String mensaje = null;

        return mensaje;
    }

    /**
     * Metodo que se encarga de registrar en las tablas de control de migración
     *
     * @param migracionRncDTO
     * @param folio
     * @param carga
     * @param idSecretaria
     * @param tipoRegistro
     * @param usuario
     * @param pathDTO
     * @param cargueRncDTO
     * @return
     */
    public String registroTablasControl(final ArchivosProcesoMigracionRncDTO migracionRncDTO, final String folio, final Carga carga, final Long idSecretaria, final Integer tipoRegistro, final String usuario, final PathDTO pathDTO, final CargueRncDTO cargueRncDTO) {
        String mensaje = null;
        Long idProceso = programacionCargueDAO.getCodigoProceso();
        try {
            migracionRncDTO.setZip(Base64.decodeBase64(cargueRncDTO.getArchivoEmpaquetado()));
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(migracionRncDTO.getZip()));
            String nombreArchivo;
            ZipEntry entrada;
            while (null != (entrada = zis.getNextEntry())) {
                nombreArchivo = FilenameUtils.getBaseName(entrada.getName());
                int index = nombreArchivo.indexOf("mlice");
                if (index > 0) {
                    UserTransaction ut = context.getUserTransaction();
                    ut.begin();
                    boolean registroLogArchivo = registraLogArchivo(tipoRegistro, folio, migracionRncDTO.getNombreLicencias(), migracionRncDTO);
                    if (registroLogArchivo) {
                        ConfArcCar confArcCar = castConfArcCar(migracionRncDTO.getNombreLicencias(), carga, idSecretaria, Constantes.TIPO_ARCHIVO_LICENCIAS);
                        boolean registroLicencias = procesoMigracionDAO.registrarConfArcCar(confArcCar);
                        if (!registroLicencias) {
                            mensaje = ("Error al registrar el código de carga en la tabla MIGRACIONQX.CONF_ARC_CAR");
                        } else {
                            MgProgramarCargue castMgProgramarCargue = castMgProgramarCargue(Integer.parseInt(idProceso.toString()), 1, idSecretaria, Constantes.TIPO_ARCHIVO_LICENCIAS, carga, usuario, pathDTO.getPathGeneral() + "/" + migracionRncDTO.getNombreLicencias(), pathDTO.getPathFile());
                            boolean registroMgProgramarCarga = procesoMigracionDAO.save(castMgProgramarCargue);
                            ut.commit();
                            if (registroMgProgramarCarga) {
                                List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(migracionRncDTO.getLicencias());
                                if (list != null && !list.isEmpty()) {
                                    for (RegistrosArchivoDTO dato : list) {
                                        ArcCar castArcCar = castArcCar(tipoRegistro, migracionRncDTO.getNombreLicencias(), idSecretaria, confArcCar, carga, dato.getDato());
                                        System.out.println("Datos licencias " + dato.getDato());
                                        boolean registraArcCar = procesoMigracionDAO.registraArcCar(castArcCar);
                                        if (!registraArcCar) {
                                            mensaje = "Error al registrar en la tabla MIGRACIONQX.ARC_CAR.";
                                            break;
                                        }
                                    }
                                }
                            } else {
                                mensaje = ("Error al registrar en la tabla MIGRACIONQX.MG_PROGRAMAR_CARGUE");
                                ut.rollback();
                                break;
                            }
                        }
                    } else {
                        mensaje = "Error al registrar el log en la tabla MIGRACIONQX.LOG_ARCHIVO_FOLIO";
                        break;
                    }
                }
                index = nombreArchivo.indexOf("mresi");
                if (index > 0) {
                    UserTransaction ut = context.getUserTransaction();
                    ut.begin();
                    boolean registroLogArchivo = registraLogArchivo(tipoRegistro, folio, migracionRncDTO.getNombreResidencias(), migracionRncDTO);
                    if (registroLogArchivo) {
                        ConfArcCar confArcCar = castConfArcCar(migracionRncDTO.getNombreResidencias(), carga, idSecretaria, Constantes.TIPO_ARCHIVO_RESIDENCIAS);
                        boolean registroResidencias = procesoMigracionDAO.registrarConfArcCar(confArcCar);
                        if (!registroResidencias) {
                            mensaje = ("Error al registrar el código de carga en la tabla MIGRACIONQX.CONF_ARC_CAR");
                            break;
                        } else {
                            MgProgramarCargue castMgProgramarCargue = castMgProgramarCargue(Integer.parseInt(idProceso.toString()), 2, idSecretaria, Constantes.TIPO_ARCHIVO_LICENCIAS, carga, usuario, pathDTO.getPathGeneral() + "/" + migracionRncDTO.getNombreResidencias(), pathDTO.getPathFile());
                            boolean registroMgProgramarCarga = procesoMigracionDAO.save(castMgProgramarCargue);
                            ut.commit();
                            if (registroMgProgramarCarga) {
                                List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(migracionRncDTO.getResidencias());
                                if (list != null && !list.isEmpty()) {
                                    for (RegistrosArchivoDTO dato : list) {
                                        ArcCar castArcCar = castArcCar(tipoRegistro, migracionRncDTO.getNombreResidencias(), idSecretaria, confArcCar, carga, dato.getDato());
                                        System.out.println("Datos residencias " + dato.getDato());
                                        boolean registraArcCar = procesoMigracionDAO.registraArcCar(castArcCar);
                                        if (!registraArcCar) {
                                            mensaje = "Error al registrar en la tabla MIGRACIONQX.ARC_CAR.";
                                            break;
                                        }
                                    }
                                }
                            } else {
                                mensaje = ("Error al registrar en la tabla MIGRACIONQX.MG_PROGRAMAR_CARGUE");
                                ut.rollback();
                                break;
                            }
                        }
                    } else {
                        mensaje = "Error al registrar el log en la tabla MIGRACIONQX.LOG_ARCHIVO_FOLIO";
                        break;
                    }
                }
                index = nombreArchivo.indexOf("mpers");
                if (index > 0) {
                    UserTransaction ut = context.getUserTransaction();
                    ut.begin();
                    boolean registroLogArchivo = registraLogArchivo(tipoRegistro, folio, migracionRncDTO.getNombrePersonas(), migracionRncDTO);
                    if (registroLogArchivo) {
                        ConfArcCar confArcCar = castConfArcCar(migracionRncDTO.getNombrePersonas(), carga, idSecretaria, Constantes.TIPO_ARCHIVO_PERSONAS);
                        boolean registroPersonas = procesoMigracionDAO.registrarConfArcCar(confArcCar);
                        if (!registroPersonas) {
                            mensaje = ("Error al registrar el código de carga en la tabla MIGRACIONQX.CONF_ARC_CAR");
                            break;
                        } else {
                            MgProgramarCargue castMgProgramarCargue = castMgProgramarCargue(Integer.parseInt(idProceso.toString()), 3, idSecretaria, Constantes.TIPO_ARCHIVO_LICENCIAS, carga, usuario, pathDTO.getPathGeneral() + "/" + migracionRncDTO.getNombrePersonas(), pathDTO.getPathFile());
                            boolean registroMgProgramarCarga = procesoMigracionDAO.save(castMgProgramarCargue);
                            ut.commit();
                            if (registroMgProgramarCarga) {
                                List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(migracionRncDTO.getPersonas());
                                if (list != null && !list.isEmpty()) {
                                    for (RegistrosArchivoDTO dato : list) {
                                             ArcCar castArcCar = castArcCar(tipoRegistro, migracionRncDTO.getNombrePersonas(), idSecretaria, confArcCar, carga, dato.getDato());
                                        System.out.println("Datos personas " + dato.getDato());
                                        boolean registraArcCar = procesoMigracionDAO.registraArcCar(castArcCar);
                                        if (!registraArcCar) {
                                            mensaje = "Error al registrar en la tabla MIGRACIONQX.ARC_CAR.";
                                            break;
                                        }
                                    }
                                }
                            } else {
                                mensaje = ("Error al registrar en la tabla MIGRACIONQX.MG_PROGRAMAR_CARGUE");
                                ut.rollback();
                                break;
                            }
                        }
                    } else {
                        mensaje = "Error al registrar el log en la tabla MIGRACIONQX.LOG_ARCHIVO_FOLIO";
                        break;
                    }
                }
                index = nombreArchivo.indexOf("mresl");
                if (index > 0) {
                    UserTransaction ut = context.getUserTransaction();
                    ut.begin();
                    boolean registroLogArchivo = registraLogArchivo(tipoRegistro, folio, migracionRncDTO.getNombreRestricciones(), migracionRncDTO);
                    if (registroLogArchivo) {
                        ConfArcCar confArcCar = castConfArcCar(migracionRncDTO.getNombreRestricciones(), carga, idSecretaria, Constantes.TIPO_ARCHIVO_RESTRICCIONES);
                        boolean registroRestricciones = procesoMigracionDAO.registrarConfArcCar(confArcCar);
                        if (!registroRestricciones) {
                            mensaje = ("Error al registrar el código de carga en la tabla MIGRACIONQX.CONF_ARC_CAR");
                            break;
                        } else {
                            MgProgramarCargue castMgProgramarCargue = castMgProgramarCargue(Integer.parseInt(idProceso.toString()), 4, idSecretaria, Constantes.TIPO_ARCHIVO_LICENCIAS, carga, usuario, pathDTO.getPathGeneral() + "/" + migracionRncDTO.getNombreRestricciones(), pathDTO.getPathFile());
                            ut.commit();
                            boolean registroMgProgramarCarga = procesoMigracionDAO.save(castMgProgramarCargue);
                            if (registroMgProgramarCarga) {
                                List<RegistrosArchivoDTO> list = archivosUtil.datosArchivo(migracionRncDTO.getRestricciones());
                                if (list != null && !list.isEmpty()) {
                                    for (RegistrosArchivoDTO dato : list) {
                                        ArcCar castArcCar = castArcCar(tipoRegistro, migracionRncDTO.getNombreRestricciones(), idSecretaria, confArcCar, carga, dato.getDato());
                                        System.out.println("Datos restricciones " + dato.getDato());
                                        boolean registraArcCar = procesoMigracionDAO.registraArcCar(castArcCar);
                                        if (!registraArcCar) {
                                            mensaje = "Error al registrar en la tabla MIGRACIONQX.ARC_CAR.";
                                            break;
                                        }
                                    }
                                }
                            } else {
                                mensaje = ("Error al registrar en la tabla MIGRACIONQX.MG_PROGRAMAR_CARGUE");
                                ut.rollback();
                                break;
                            }
                        }
                    } else {
                        mensaje = "Error al registrar el log en la tabla MIGRACIONQX.LOG_ARCHIVO_FOLIO";
                        break;
                    }
                }
            }
        } catch (IOException | IllegalStateException | NumberFormatException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
        }
        return mensaje;
    }

    public boolean registraLogArchivo(final Integer tipoRegistro, final String folio, final String nombreArchivo, final ArchivosProcesoMigracionRncDTO migracionRncDTO) {
        if (tipoRegistro == 1 || tipoRegistro == 2) {
            LogArchivoFolio logArchivo = new LogArchivoFolio();
            logArchivo.setCodEstado(0);
            logArchivo.setIdFolio(folio);
            logArchivo.setNomArcOriginal(nombreArchivo);
            boolean registroLogArchivo = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
            if (registroLogArchivo) {
                return true;
            }
        } else {
            LogArchivoFolio logArchivo = new LogArchivoFolio();
            logArchivo.setCodEstado(0);
            logArchivo.setIdFolio(folio);
            logArchivo.setNomArcOriginal(migracionRncDTO.getNombreLicencias());
            boolean registroLogArchivoLicencias = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
            if (registroLogArchivoLicencias) {
                logArchivo.setNomArcOriginal(migracionRncDTO.getNombreResidencias());
                boolean registroLogResidencias = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
                if (registroLogResidencias) {
                    logArchivo.setNomArcOriginal(migracionRncDTO.getNombrePersonas());
                    boolean registroLogPersonas = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
                    if (registroLogPersonas) {
                        if (migracionRncDTO.getNombreRestricciones() != null) {
                            logArchivo.setNomArcOriginal(migracionRncDTO.getNombreRestricciones());
                            boolean registroLogRestricciones = procesoMigracionDAO.registroLogArchivoFolio(logArchivo);
                            return registroLogRestricciones;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ArcCar castArcCar(final Integer tipoArchivo, final String rutaArchivo, final Long idSecretaria,
            final ConfArcCar codProceso, final Carga codCarga, final String datoArchivo) {
        ArcCar registro = new ArcCar();
        registro.setTipoArc(tipoArchivo);
        registro.setCodestado(0);
        registro.setNomarc(rutaArchivo);
        registro.setNomot(idSecretaria.toString());
        registro.setCodProceso(codProceso);
        registro.setFecProceso(new Date());
        registro.setCodCarga(codCarga.getCodCarga());
        registro.setDesreg(datoArchivo);
        return registro;
    }

    public MgProgramarCargue castMgProgramarCargue(final Integer idProceso, final Integer orden, final Long idSecretaria,
            final Integer tipoArchivo, final Carga carga, final String usuario, final String rutaArchivo, final String rutaComprimido) {
        MgProgramarCargue cargue = new MgProgramarCargue();
        cargue.setIdProceso(idProceso);
        cargue.setIdOrden(orden);
        cargue.setIdSecretaria(idSecretaria);
        cargue.setCodEstandar(Constantes.CODIGO_ESTANDAR);
        cargue.setTipoRegistro(Constantes.TIPO_REGISTRO_RNC);
        cargue.setTipoArc(tipoArchivo);
        cargue.setFechaProgramacion(new Date());
        cargue.setFechaEjecucion(new Date());
        cargue.setCodEstado(9);
        cargue.setCodCarga(carga.getCodCarga());
        cargue.setNombreArchivo(rutaArchivo);
        cargue.setNombreArcOriginal(rutaComprimido);
        cargue.setResolucion("N");
        cargue.setIdUsuario(Long.parseLong(usuario));
        return cargue;
    }

    public ConfArcCar castConfArcCar(final String nombreArchivo, final Carga carga, final Long idSecretaria, final Integer tipoArchivo) {
        ConfArcCar confArcCar = new ConfArcCar();
        confArcCar.setCodEstandar(4);
        confArcCar.setCodEstado(0);
        confArcCar.setIdSecretaria(idSecretaria);
        confArcCar.setFecProceso(new Date());
        confArcCar.setCodBoletin(0);
        confArcCar.setCodCarga(carga);
        confArcCar.setNomArchivo(nombreArchivo);
        confArcCar.setTipoArchivo(tipoArchivo);
        confArcCar.setNomArcOriginal(nombreArchivo);
        confArcCar.setTipoRegistro(2);
        return confArcCar;
    }

    /**
     * Valida si los archivos enviados se encuentran registrados en la tabla
     * MIGRACIONQX.MG_PROGRAMAR_CARGUE
     *
     * @param migracionRncDTO
     * @return
     */
    public String validaMgProgramaCargue(ArchivosProcesoMigracionRncDTO migracionRncDTO) {
        String mensaje = null;
        if (migracionRncDTO.getNombreLicencias() != null) {
            Integer validaArchivoLicencia = procesoMigracionDAO.validaMgProgramarCargue(migracionRncDTO.getNombreLicencias());
            if (validaArchivoLicencia != 0) {
                mensaje = ("Ya existe una carga para este archivo " + migracionRncDTO.getNombreLicencias());
            }
        }
        if (migracionRncDTO.getNombreResidencias() != null) {
            Integer validaArchivoResidencias = procesoMigracionDAO.validaMgProgramarCargue(migracionRncDTO.getNombreResidencias());
            if (validaArchivoResidencias != 0) {
                mensaje = ("Ya existe una carga para este archivo " + migracionRncDTO.getNombreResidencias());
            }
        }
        if (migracionRncDTO.getNombrePersonas() != null) {
            Integer validaArchivoPersonas = procesoMigracionDAO.validaMgProgramarCargue(migracionRncDTO.getNombrePersonas());
            if (validaArchivoPersonas != 0) {
                mensaje = ("Ya existe una carga para este archivo " + migracionRncDTO.getNombrePersonas());
            }
        }
        if (migracionRncDTO.getNombreRestricciones() != null) {
            Integer validaArchivoRestricciones = procesoMigracionDAO.validaConfArcCar(migracionRncDTO.getNombreRestricciones());
            if (validaArchivoRestricciones != 0) {
                mensaje = ("Ya existe una carga para este archivo " + migracionRncDTO.getNombrePersonas());
            }
        }
        return mensaje;
    }

    /**
     * Valida si los archivos enviados existen en la tabla
     * MIGRACIONQX.CONF_ARC_CAR
     *
     * @param migracionRncDTO
     * @return
     */
    public String validaConfArcCar(ArchivosProcesoMigracionRncDTO migracionRncDTO) {
        String mensaje = null;
        if (migracionRncDTO.getNombreLicencias() != null) {
            Integer validaArchivoLicencia = procesoMigracionDAO.validaConfArcCar(migracionRncDTO.getNombreLicencias());
            if (validaArchivoLicencia != 0) {
                mensaje = ("Ya existe una carga para este archivo " + migracionRncDTO.getNombreLicencias());
            }
        }
        if (migracionRncDTO.getNombreResidencias() != null) {
            Integer validaArchivoResidencias = procesoMigracionDAO.validaConfArcCar(migracionRncDTO.getNombreResidencias());
            if (validaArchivoResidencias != 0) {
                mensaje = ("Ya existe una carga para este archivo " + migracionRncDTO.getNombreResidencias());
            }
        }
        if (migracionRncDTO.getNombrePersonas() != null) {
            Integer validaArchivoPersonas = procesoMigracionDAO.validaConfArcCar(migracionRncDTO.getNombrePersonas());
            if (validaArchivoPersonas != 0) {
                mensaje = ("Ya existe una carga para este archivo " + migracionRncDTO.getNombrePersonas());
            }
        }
        if (migracionRncDTO.getNombreRestricciones() != null) {
            Integer validaArchivoRestricciones = procesoMigracionDAO.validaConfArcCar(migracionRncDTO.getNombreRestricciones());
            if (validaArchivoRestricciones != 0) {
                mensaje = ("Ya existe una carga para este archivo " + migracionRncDTO.getNombrePersonas());
            }
        }
        return mensaje;
    }

    /**
     * Recorreo cada archivo y lo desfirma
     *
     * @param path
     * @param archivos
     * @param usuario
     * @param idSecretaria
     * @return
     */
    public ArchivosProcesoMigracionRncDTO desfirmaArchivos(final PathDTO path, final ArchivosProcesoMigracionRncDTO archivos, final String usuario, final Long idSecretaria) {
        ArchivosProcesoMigracionRncDTO salida = new ArchivosProcesoMigracionRncDTO();
        String mensaje = null;
        ZipInputStream zis = null;
        try {
            String nombreArchivo;
            zis = new ZipInputStream(new FileInputStream(path.getPathFile()));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                nombreArchivo = FilenameUtils.getName(ze.getName());
                int index = nombreArchivo.indexOf("mlice");
                if (index > 0) {
                    byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);
                    if (validaFirma != null) {
                        salida.setNombreLicencias(nombreArchivo);
                        salida.setLicencias(validaFirma);
                        salida.setArchivoLicencias(Base64.encodeBase64String(validaFirma));
                    } else {
                        mensaje = "Error al desfirmar el archivo";
                    }
                }
                index = nombreArchivo.indexOf("mresi");
                if (index > 0) {
                    byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);
                    if (validaFirma != null) {
                        salida.setNombreResidencias(nombreArchivo);
                        salida.setResidencias(validaFirma);
                        salida.setArchivoResidencias(Base64.encodeBase64String(validaFirma));
                    } else {
                        mensaje = "Error al desfirmar el archivo";
                    }
                }
                index = nombreArchivo.indexOf("mpers");
                if (index > 0) {
                    byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);
                    if (validaFirma != null) {
                        salida.setNombrePersonas(nombreArchivo);
                        salida.setPersonas(validaFirma);
                        salida.setArchivoPersonas(Base64.encodeBase64String(validaFirma));
                    } else {
                        mensaje = "Error al desfirmar el archivo";
                    }
                }
                index = nombreArchivo.indexOf("mresl");
                if (index > 0) {
                    byte[] validaFirma = archivosUtil.desfirmaArchivo(path.getPathGeneral() + "/" + nombreArchivo, nombreArchivo, usuario, idSecretaria);
                    if (validaFirma != null) {
                        salida.setNombreRestricciones(nombreArchivo);
                        salida.setRestricciones(validaFirma);
                        salida.setArchivoRestricciones(Base64.encodeBase64String(validaFirma));
                    } else {
                        mensaje = "Error al desfirmar el archivo";
                    }
                }
                if (mensaje == null) {

                }
                ze = zis.getNextEntry();
            }
        } catch (IOException e) {

        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException ex) {
                    Logger.getLogger(ProcesarArchivosMigracionRncLogica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return salida;
    }
}
