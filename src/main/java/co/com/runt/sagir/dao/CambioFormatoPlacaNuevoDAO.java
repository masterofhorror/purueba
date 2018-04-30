/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.entities.LicTto;
import co.com.runt.sagir.entities.MgActualizacionRna;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class CambioFormatoPlacaNuevoDAO {

    private static final Logger LOGGER = Logger.getLogger(ConsultaRNAMigraDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")

    EntityManager entityManager;

    public static final String GEPOSTULACION = "SELECT COUNT(1)\n"
            + "FROM RUNTPROD.GE_POSTULACI GE,\n"
            + "RUNTPROD.RA_AUTOMOTOR RA\n"
            + "WHERE RA.AUTOMOTOR_NROREGVEH = GE.POSTULACI_AUTOMOTOR_NROREGVEH\n"
            + "AND GE.POSTULACI_ESTAPOSTU_NOMBRE IN ('ACEPTADA', 'DISPONIBILIDAD_EXISTENTE', 'INCENTIVO RECONOCIDO', 'INCENTIVO PAGADO')\n"
            + "AND RA.AUTOMOTOR_PLACA_NUMPLACA = ?";

    public Integer verificaPostulacion(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(GEPOSTULACION)
                    .setParameter(1, placa)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    public static final String CONSULTACAMBIOPLACA = "SELECT DISTINCT LIC.ID_SECRETARIA AS IDSECRETARIA, \n"
            + "         OT.DESCRIPCION_CORTA AS SECRETARIA,      \n"
            + "         LIC.NRO_PLACA AS PLACA,   \n"
            + "         LIC.ID_MARCA AS MARCA,   \n"
            + "         LIC.ID_LINEA AS LINEA,   \n"
            + "         CV.DESC_CLASE AS CLASE,   \n"
            + "         PT.TIPOCARRO_NOMBRE AS CARROCERIA,   \n"
            + "         TM.DESC_MODALIDAD AS MODALIDAD,   \n"
            + "         LIC.CILINDRAJE AS CILINDRAJE,   \n"
            + "         LIC.MODELO AS MODELO,   \n"
            + "         LIC.NRO_MOTOR AS MOTOR,   \n"
            + "         LIC.NRO_SERIE AS SERIE,   \n"
            + "         LIC.NRO_CHASIS AS CHASIS,   \n"
            + "         LIC.CAP_TONELADAS AS CAPACIDADTONELADA,   \n"
            + "         LIC.CAP_PASAJEROS AS CAPACIDADPASAJ,   \n"
            + "         LIC.ESTADO_VEHICULO AS ESTADO,   \n "
            + "         RA.AUTOMOTOR_ESTAVEHIC_NOMBRE AS ESTADOACTUAL, \n"
            + "         CASE LIC.DES_MIGRADO WHEN 'M' THEN 'Migrado' WHEN 'N' THEN 'No migrado' ELSE LIC.DES_MIGRADO END AS MIGRADO,   \n"
            + "         LIC.COD_CARGA AS CODCARGA,   \n"
            + "         LIC.RADICADO AS RADICADO,   \n"
            + "         CG.FECHA_CARGA AS FECHACARGA,   \n"
            + "         CG.ID_FOLIO AS IDFOLIO,   \n"
            + "         CASE WHEN LIC.IND_VALIDO = 1 THEN 'APROBADO' ELSE 'RECHAZADO' END AS ESTADOCARGUE,   \n"
            + "         CG.ID_BOLETIN AS BOLETIN,   \n"
            + "         CV.DESC_CLASE AS DESCCLASE,   \n"
            + "         TM.DESC_MODALIDAD AS DESCMODALIDAD,   \n"
            + "         LIC.IND_VALIDO AS INDVALIDO,   \n"
            + "         CASE WHEN LIC.ESTADO_VEHICULO = 1 THEN 'ACTIVO' WHEN LIC.ESTADO_VEHICULO = 2 THEN  'CANCELADO' WHEN LIC.ESTADO_VEHICULO = 3 THEN  'TRASLADO'  ELSE 'NO PARAMETRIZADO' END AS DESCESTADO,   \n"
            + "         PT.TIPOCARRO_NOMBRE AS TIPOCARRO,   \n"
            + "         'N' ACTUALIZA,   \n"
            + "         'N' CAMBIOESTADO,   \n"
            + "         'N' CAMBIOFORMATO,   \n"
            + "         'N' CAMBIOSERVICIO,   \n"
            + "         LIC.ID_SERVICIO AS IDSERVICIO\n"
            + "FROM MIGRACIONQX.LIC_TTO LIC\n"
            + "INNER JOIN MIGRACIONQX.CARGA CG ON CG.COD_CARGA = LIC.COD_CARGA\n"
            + "                                AND CG.ID_SECRETARIA = LIC.ID_SECRETARIA\n"
            + "INNER JOIN MIGRUNT1.ORGANISMOS_TRANSITO OT ON OT.ID_SECRETARIA = LIC.ID_SECRETARIA\n"
            + "INNER JOIN MIGRUNT1.CLASE_VEHICULOS CV ON CV.ID_CLASE = LIC.ID_CLASE\n"
            + "LEFT JOIN MIGRUNT1.TIPO_MODALIDAD TM ON TM.ID_MODALIDAD = LIC.ID_MODALIDAD\n"
            + "INNER JOIN RUNTPROD.PA_TIPOCARRO PT ON PT.TIPOCARRO_CARROCERI_ID = LIC.ID_CARROCERIA\n "
            + "LEFT JOIN RUNTPROD.RA_AUTOMOTOR RA ON RA.AUTOMOTOR_PLACA_NUMPLACA = LIC.NRO_PLACA\n "
            + "WHERE LIC.NRO_PLACA = ? ";

    public List<LicTto> consultaCambioFormatoAntigANuevo(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTACAMBIOPLACA + Constantes.ORDERBY, Constantes.CONSULTACAMBIOFORMATO);
            query.setParameter(1, placa);
            return query.getResultList();
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<LicTto> consultaVehiculoEstadoCargue(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTACAMBIOPLACA + Constantes.ESTADO_CARGUE + Constantes.ORDERBY, Constantes.CONSULTACAMBIOFORMATO);
            query.setParameter(1, placa);
            return query.getResultList();
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<LicTto> consultaCambioFormatoDetalle(final String placa, final String boletin) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTACAMBIOPLACA + Constantes.BOLETIN, Constantes.CONSULTACAMBIOFORMATO);
            query.setParameter(1, placa);
            query.setParameter(2, boletin);
            return query.getResultList();
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return Collections.emptyList();
    }

    public boolean consultaPlacaCargada(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTACAMBIOPLACA + Constantes.ESTADOCARGUE, Constantes.CONSULTACAMBIOFORMATO);
            query.setParameter(1, placa);
            return true;
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }

    public boolean consultaVehiculoMigrado(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTACAMBIOPLACA + Constantes.MIGRADOSQL, Constantes.CONSULTACAMBIOFORMATO);
            query.setParameter(1, placa);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }

    public LicTto consultaVehiculo(final String placa) {
        try {
            return (LicTto) entityManager.createNativeQuery(CONSULTACAMBIOPLACA + Constantes.MIGRADOSQL, Constantes.CONSULTACAMBIOFORMATO)
                    .setParameter(1, placa)
                    .getSingleResult();
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public boolean registraProceso(MgActualizacionRna mgActualizacionRna) {
        try {
            entityManager.persist(mgActualizacionRna);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }
}
