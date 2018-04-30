/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.common.CargueArchivoCommon;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.AutoridadTransitoHQ;
import co.com.runt.sagir.entities.ComConstantes;
import co.com.runt.sagir.entities.HiAutomotor;
import co.com.runt.sagir.entities.LogDatosArchivo;
import co.com.runt.sagir.entities.MgActualizacionRna;
import co.com.runt.sagir.entities.MigraActualizacionRna;
import co.com.runt.sagir.entities.OrganismosTransitoMigrunt;
import co.com.runt.sagir.entities.PaNitCia;
import co.com.runt.sagir.entities.Propietario;
import co.com.runt.sagir.entities.PropietariosVehiculo;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.entities.TblLicTto;
import co.com.runt.sagir.entities.TipoServicio;
import co.com.runt.sagir.entities.Tramites;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class ConstanteDAO {

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;
    private static final Logger LOGGER = Logger.getLogger(ConstanteDAO.class.getName());

    /**
     *
     * @param contanteGrupo
     * @param nombre
     * @return
     */
    public ComConstantes consultarPorGrupoYNombre(final String contanteGrupo, final String nombre) {
        try {
            return (ComConstantes) entityManager.createNamedQuery("ComConstantes.findConstante").
                    setParameter(Constantes.GRUPO, contanteGrupo).
                    setParameter(Constantes.NOMBRE, nombre).
                    getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Error{0}", e);
        }
        return null;
    }

    /**
     *
     * @param contanteGrupo
     * @return
     */
    public List<ComConstantes> getConstanteByGrupo(final String contanteGrupo) {
        try {
            return entityManager.createNamedQuery("ComConstantes.findByGrupo").
                    setParameter(Constantes.GRUPO, contanteGrupo).
                    getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Error{0}", e);
        }
        return Collections.emptyList();
    }

    public CargueArchivoCommon consultarPorGrupoYNombre(String grupoCargueArchivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer validaOtHomologa(final Long oTNombreArchivo, final Long autoridadSesion) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Sedes.findByIdSede")
                    .setParameter("idSede", oTNombreArchivo)
                    .setParameter("idSecretaria", autoridadSesion)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return -1;
    }

    public Integer validaOt(final Long oTNombreArchivo) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("AutoridadTransitoHQ.findById")
                    .setParameter("idautoridad", oTNombreArchivo)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return -1;
    }

    public boolean guardarLogDatoArchivo(LogDatosArchivo entrada) {
        try {
            entityManager.persist(entrada);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }

    public Integer validaEstadoCarga(final Integer idCarga) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("PropietariosVehiculo.findByCountCodCarga")
                    .setParameter(Constantes.CODCARGA, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.OFF, e.getMessage());
        }
        return 0;
    }

    public boolean actualizarEstadoCargue(SgCarguearchivos carguearchivos) {
        try {
            entityManager.merge(carguearchivos);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }

    public String getByID(final String contanteGrupo, final String constanteValor) {
        try {
            return (String) entityManager.createNamedQuery("ComConstantes.findValorByIdGrupoConstante").
                    setParameter("constanteValor", constanteValor).
                    setParameter("constanteGrupo", contanteGrupo).
                    getSingleResult();
        } catch (EntityNotFoundException e) {
            LOGGER.log(Level.SEVERE, "{0}", e.getLocalizedMessage());
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "{0}", e.getLocalizedMessage());

        }
        return null;
    }

    public ComConstantes getByGrupoNombre(final String grupo, final String nombre) {
        try {
            return (ComConstantes) entityManager.createNamedQuery("ComConstantes.findConstante").
                    setParameter(Constantes.GRUPO, grupo).
                    setParameter(Constantes.NOMBRE, nombre).
                    getSingleResult();
        } catch (EntityNotFoundException e) {
            LOGGER.log(Level.SEVERE, "{0}", e.getLocalizedMessage());
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "{0}", e.getLocalizedMessage());

        }
        return null;
    }

    public Automotor consultaVehiculo(final String placa) {
        try {
            return (Automotor) entityManager.createNamedQuery("Automotor.findByPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    public Automotor consultaVehiculoByNroRegistro(final Long nroRegVehiculo) {
        try {
            return (Automotor) entityManager.createNamedQuery("Automotor.findById")
                    .setParameter(Constantes.IDAUTOMOTOR, nroRegVehiculo)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    public Integer validaSolicitudPlaca(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Tramites.findCountByPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    public Integer cantidadSolicitudesPorPlaca(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Tramites.findByCountPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    public List<Tramites> solicitudPlaca(final String placa) {
        try {
            return (List<Tramites>) entityManager.createNamedQuery("Tramites.findByConsulta")
                    .setParameter(Constantes.PLACA, placa)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public Integer validaVehiculo(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Automotor.findByCountPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    public Integer validaRegistroProceso(final String placa, final Integer tipoProceso) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("MgActualizacionRna.findByPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .setParameter(Constantes.TIPOPROCESO, tipoProceso)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    public String estadoVehiculo(final Integer idEstado) {
        String estadoVehiculo;
        if (idEstado == 1) {
            estadoVehiculo = Constantes.ESTADO_VEHICULO;
        } else {
            if (idEstado == 2) {
                estadoVehiculo = Constantes.ESTADO_CANCELADO;
            } else {
                estadoVehiculo = Constantes.ESTADO_TRASLADO;
            }
        }
        return estadoVehiculo;
    }

    public Integer idEstadoVehiculo(final String estado) {
        Integer estadoVehiculo;
        if (estado.equals(Constantes.ESTADO_VEHICULO)) {
            estadoVehiculo = 1;
        } else {
            if (estado.equals(Constantes.ESTADO_CANCELADO)) {
                estadoVehiculo = 2;
            } else {
                estadoVehiculo = 3;
            }
        }
        return estadoVehiculo;
    }

    public String tipoDocumento(final Long idDocumento) {
        String tipoDocumento;
        if (idDocumento == 1) {
            tipoDocumento = Constantes.TIPO_DOCUMENTO;
        } else {
            tipoDocumento = Constantes.TIPO_LICENCIA;
        }
        return tipoDocumento;
    }

    public boolean registrarLog(HiAutomotor automotor) {
        try {
            entityManager.persist(automotor);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return false;
    }

    public List<TblLicTto> consultaLicTto(final String placa) {
        try {
            return (List<TblLicTto>) entityManager.createNamedQuery("TblLicTto.findByNroPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public TblLicTto consultaLicTtoByPlaca(final String placa, final Integer codCarga) {
        try {
            return (TblLicTto) entityManager.createNamedQuery("TblLicTto.findByNroPlacaConCarga")
                    .setParameter(Constantes.PLACA, placa)
                    .setParameter(Constantes.CODCARGA, codCarga)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    public boolean actualizaEstadoVehiculo(Automotor automotor) {
        try {
            entityManager.merge(automotor);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return false;
    }

    public List<Propietario> consultaPropietarios(final Long idVehiculo) {
        try {
            return (List<Propietario>) entityManager.createNamedQuery("Propietario.findByPropietario")
                    .setParameter(Constantes.IDAUTOMOTOR, idVehiculo)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public boolean marcaPropietario(Propietario propietario) {
        try {
            entityManager.merge(propietario);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return false;
    }

    public SgCarguearchivos consultaById(final Long carguearchivosId) {
        try {
            return (SgCarguearchivos) entityManager.createNamedQuery("SgCarguearchivos.findByCarguearchivosId")
                    .setParameter(Constantes.CODCARGA, carguearchivosId)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Metodo que consulta si existe un archivo con el número de ticket
     *
     * @param nrtoTicket
     * @return
     */
    public SgCarguearchivos consultaByTicket(final String nrtoTicket) {
        try {
            return (SgCarguearchivos) entityManager.createNamedQuery("SgCarguearchivos.findByTicket")
                    .setParameter(Constantes.NROTICKET, nrtoTicket)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Metodo que valida si existe un registro con el mismo número de ticket
     * ingresado en la tabla CSWSAGIR.TBL_CARGUEARCHIVOS
     *
     * @param nrtoTicket
     * @return
     */
    public Integer countCantidadTickets(final String nrtoTicket) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("SgCarguearchivos.findCountByTicket")
                    .setParameter(Constantes.NROTICKET, nrtoTicket)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    /**
     * Metodo que enlista los organismos de tránsito
     *
     * @return
     */
    public List<OrganismosTransitoMigrunt> listarOt() {
        try {
            return (List<OrganismosTransitoMigrunt>) entityManager.createNamedQuery("OrganismosTransitoMigrunt.findByAll")
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Metodo que consulta en la tabla MIGRACIONQX.PROPIETARIOS_VEHICULO por
     * código de carga
     *
     * @param idCarga
     * @return
     */
    public List<PropietariosVehiculo> consultaPropietariosByCargue(final Integer idCarga) {
        try {
            return (List<PropietariosVehiculo>) entityManager.createNamedQuery("PropietariosVehiculo.findByCodCarga")
                    .setParameter(Constantes.CODCARGA, idCarga)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Metodo que registra el proceso en la tabla
     * MIGRACIONQX.MG_ACTUALIZACION_RNA
     *
     * @param registrar
     * @return
     */
    public boolean registraProceso(MigraActualizacionRna registrar) {
        try {
            entityManager.persist(registrar);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }

    /**
     * Metodo que actualiza el proceso en la tabla
     * MIGRACIONQX.MG_ACTUALIZACION_RNA
     *
     * @param actualiza
     * @return
     */
    public boolean actualizaProceso(MigraActualizacionRna actualiza) {
        try {
            entityManager.merge(actualiza);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }

    /**
     * Metodo que consulta la cantidad de registros que tiene un código de carga
     *
     * @param codCarga
     * @return
     */
    public Integer consultaMigraActualizacionRnaByCarga(final Integer codCarga) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("MigraActualizacionRna.findByCountCodCarga")
                    .setParameter(Constantes.CODCARGA, codCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    public List<MigraActualizacionRna> consultaRegistrosMigraActualizacionRna(final Integer codCarga) {
        try {
            return (List<MigraActualizacionRna>) entityManager.createNamedQuery("MigraActualizacionRna.findByCodCarga")
                    .setParameter(Constantes.CODCARGA, codCarga)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return Collections.emptyList();
    }

    public Integer consultaActualizacionRna(final String placa, final Integer codCarga) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("MigraActualizacionRna.findByCountPlacaCodCarga")
                    .setParameter(Constantes.PLACA, placa)
                    .setParameter(Constantes.CODCARGA, codCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    public MigraActualizacionRna consultaDetalleActualizacionRna(final String placa, final Integer codCarga) {
        try {
            return (MigraActualizacionRna) entityManager.createNamedQuery("MigraActualizacionRna.findByPlacaCodCarga")
                    .setParameter(Constantes.PLACA, placa)
                    .setParameter(Constantes.CODCARGA, codCarga)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    public TblLicTto consultaEstadoCarga(final String placa, final Integer codCarga) {
        try {
            return (TblLicTto) entityManager.createNamedQuery("TblLicTto.findByNroPlacaConCarga")
                    .setParameter(Constantes.PLACA, placa)
                    .setParameter(Constantes.CODCARGA, codCarga)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    public Integer validaCodCarga(final Integer codCarga) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Carga.findByCountCodCarga")
                    .setParameter(Constantes.CODCARGA, codCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    /**
     * Metodo que consulta en la tabla MIGRACIONQX.LIC_TTO por número de placa,
     * código de carga y id de la secretaria
     *
     * @param placa
     * @param codCarga
     * @param idSecretaria
     * @return
     */
    public TblLicTto consultaPlacaCodCargaIdSecretaria(final String placa, final Integer codCarga, final Integer idSecretaria) {
        try {
            return (TblLicTto) entityManager.createNamedQuery("TblLicTto.findByPlacaCodCargaIdSecretaria")
                    .setParameter(Constantes.PLACA, placa)
                    .setParameter(Constantes.CODCARGA, codCarga)
                    .setParameter(Constantes.IDSECRETARIA, idSecretaria)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    /**
     * Metodo que registra el log de la modificación realizada
     *
     * @param mgActualizacionRna
     * @return
     */
    public boolean registroMgActualizacionRna(MgActualizacionRna mgActualizacionRna) {
        try {
            entityManager.persist(mgActualizacionRna);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Metodo que verifica que el número de placa ingresado exista en la tabla
     * LIC_TTO
     *
     * @param placa
     * @return
     */
    public Integer countLicTto(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("TblLicTto.findByCountNroPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    /**
     * Metodo que valida si el vehículo registra en la tabla
     * MIGRACIONQX.RA_REGNACCAR
     *
     * @param placa
     * @return
     */
    public Integer countRegnaccar(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Regnaccar.findByCountPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    /**
     * Metodo que valida si el vehículo es camión o tracto camión
     *
     * @param placa
     * @return
     */
    public Integer validaCamionTracto(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Automotor.findByCountVehiculoRNTC")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    /**
     * Consulta que valida si el vehículo se encuentra en la tabla
     * RUNTPROD.GE_POSTULACI
     *
     * @param placa
     * @return
     */
    public Integer countPostulacionByPlaca(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Postulacion.findByCountPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    /**
     * Consulta que valida si el vehículo tiene trámites aprobados o autorizados
     *
     * @param placa
     * @return
     */
    public Integer countTramitesByPlaca(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Tramites.findCountByPlaca")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }

    /**
     * Consulta que enlista la tabla RUNTPROD.PA_TIPOSERVI
     *
     * @return
     */
    public List<TipoServicio> tipoServicio() {
        try {
            return (List<TipoServicio>) entityManager.createNamedQuery("TipoServicio.findAll")
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Metodo que consulta en la tabla MIGRACIONQX.LIC_TTO donde el código de
     * criterio es NULL
     *
     * @param placa
     * @return
     */
    public TblLicTto consultaVehiculoLicTto(final String placa) {
        try {
            return (TblLicTto) entityManager.createNamedQuery("TblLicTto.findByPlacaCodCarga")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Metodo que consulta por Idauttra el organismo de tránsito
     *
     * @param idAuttra
     * @return
     */
    public AutoridadTransitoHQ consultaOT(final Long idAuttra) {
        try {
            return (AutoridadTransitoHQ) entityManager.createNamedQuery("AutoridadTransitoHQ.findByAutotransIdauttra")
                    .setParameter(Constantes.IDSECRETARIA, idAuttra)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    public List<PaNitCia> listarCia() {
        try {
            return (List<PaNitCia>) entityManager.createNamedQuery("PaNitCia.findAll")
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public Integer validaRnrys(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Automotor,findByValidaRnrys")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    public Integer validaRnma(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("Automotor,findByValidaRnma")
                    .setParameter(Constantes.PLACA, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    public Integer validaCambioFormatoPlaca(final String placa, final Integer tipoProceso) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("MgActualizacionRna.findByValidaCambio")
                    .setParameter(Constantes.PLACA, placa)
                    .setParameter(Constantes.TIPOPROCESO, tipoProceso)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return 0;
    }
    
    public OrganismosTransitoMigrunt organimoTransitoMigrunt (final Long idSecretaria){
        try {
            return (OrganismosTransitoMigrunt) entityManager.createNamedQuery("OrganismosTransitoMigrunt.findByIdSecretaria")
                    .setParameter("idSecretaria", idSecretaria)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }
}
