/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ConsultaRNAFinalMigra;
import co.com.runt.sagir.entities.ConsultaRNAMigra;
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
 * @author Dsalamanca
 */
@Stateless
public class ConsultaRNAMigraDAO {

    private static final Logger LOGGER = Logger.getLogger(ConsultaRNAMigraDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")

    private EntityManager entityManager;

    private static final String QUERYCONSULTARNAMIGRACION = "SELECT MXLC.ID_SECRETARIA AS IDSECRETARI,\n"
            + "M1OT.DESCRIPCION_CORTA AS DESCRISECRE,\n"
            + "MXLC.NRO_PLACA AS PLACA,\n"
            + "case when MXLC.estado_vehiculo = 1 then 'ACTIVO' WHEN MXLC.estado_vehiculo = 2 THEN  'CANCELADO' WHEN MXLC.estado_vehiculo = 3 THEN  'TRASLADO'  ELSE 'NO + PARAMETRIZADO' END AS ESTADOVEHIC,\n"
            + "CASE WHEN MXLC.ind_valido = 1 THEN 'APROBADO' ELSE 'RECHAZADO' END AS ESTADOCARGUE,\n"
            + "MXLC.COD_CARGA AS CODCARGA,\n"
            + "MXLC.DES_MIGRADO AS MIGRADO,  \n"
            + "MXLC.RADICADO AS RADICADO,\n"
            + "TO_CHAR(MXCR.FECHA_CARGA, 'DD/MM/YYYY') AS FECHCARGA,\n"
            + "MXCR.ID_FOLIO AS FOLIO,\n"
            + "MXCR.ID_BOLETIN AS BOLETIN,\n"
            + "MXLC.ID_MARCA AS MARCA,\n"
            + "MXLC.ID_LINEA AS LINEA,\n"
            + "MXLC.ID_CLASE AS IDCLASE,\n"
            + "M1CV.DESC_CLASE AS CLASE,\n"
            + "MXLC.ID_CARROCERIA AS IDCARROCERI,\n"
            + "TC.TIPOCARRO_NOMBRE AS CARROCERIA,\n"
            + "MXLC.ID_MODALIDAD AS IDMODALIDAD,\n"
            + "M1TM.DESC_MODALIDAD AS MODALIDAD,\n"
            + "MXLC.CILINDRAJE AS CILINDRAJE,      \n"
            + "MXLC.MODELO AS MODELO,           \n"
            + "MXLC.NRO_MOTOR AS MOTOR,\n"
            + "MXLC.NRO_SERIE AS SERIE,   \n"
            + "MXLC.NRO_CHASIS AS CHASIS,    \n"
            + "MXLC.CAP_TONELADAS AS TONELADAS, \n"
            + "MXLC.CAP_PASAJEROS AS PASAJEROS    \n"
            + "FROM MIGRACIONQX.LIC_TTO MXLC\n"
            + "LEFT JOIN MIGRUNT1.ORGANISMOS_TRANSITO M1OT ON M1OT.ID_SECRETARIA = MXLC.ID_SECRETARIA\n"
            + "LEFT JOIN MIGRACIONQX.CARGA MXCR ON MXCR.COD_CARGA = MXLC.COD_CARGA\n"
            + "LEFT JOIN MIGRUNT1.CLASE_VEHICULOS M1CV ON M1CV.ID_CLASE = MXLC.ID_CLASE\n"
            + "LEFT JOIN MIGRARUNT.PA_TIPOCARRO TC ON TC.TIPOCARRO_CLASVEHIC_IDCLASE = MXLC.ID_CLASE\n"
            + "                                    AND TC.TIPOCARRO_IDCARROCE = MXLC.ID_CARROCERIA\n"
            + "LEFT JOIN MIGRUNT1.TIPO_MODALIDAD M1TM ON M1TM.ID_MODALIDAD = MXLC.ID_MODALIDAD \n"
            + "WHERE MXLC.NRO_PLACA = ? \n"
            + "ORDER BY MXCR.FECHA_CARGA ASC";
    
    private static final String QUERYINTERMEDIAMIGRACION = "SELECT  \n"
            + "MG.AUTOMOTOR_NROREGVEH AS IDVEHIC,           \n"
            + "MG.AUTOMOTOR_PLACA_NUMPLACA AS PLACA, \n"
            + "(SELECT OT.DESCRIPCION_CORTA\n"
            + "    FROM MIGRUNT1.ORGANISMOS_TRANSITO OT\n"
            + "    WHERE OT.ID_SECRETARIA = MG.AUTOMOTOR_AUTOTRANS_IDAUTTRA) AS AUTOREGISTRA, \n"
            + "(SELECT OT.DESCRIPCION_CORTA\n"
            + "    FROM MIGRUNT1.ORGANISMOS_TRANSITO OT\n"
            + "    WHERE OT.ID_SECRETARIA = MG.AUTOMOTOR_AUTOTRANS_IDAUTTRA) AS SECRETARIA, \n"
            + "MG.FEC_MIGRA AS FECHAMIGRA,    \n"
            + "MG.COD_CARGA AS CODCARGA,\n"
            + "MG.AUTOMOTOR_ESTAVEHIC_NOMBRE AS ESTADOVEHIC,           \n"
            + "CASE MG.AUTOMOTOR_MIGRADO WHEN 'M' THEN 'Migrado' WHEN 'N' THEN 'No migrado' ELSE AUTOMOTOR_MIGRADO END MIGRADO,                             \n"
            + "MG.DESC_MIGRADO AS DESERROR    \n"
            + "FROM MIGRACIONQX.MIG_RA_AUTOMOTOR MG      \n"
            + "WHERE (MG.AUTOMOTOR_PLACA_NUMPLACA = ?)  \n"
            + "ORDER BY MG.AUTOMOTOR_AUTOTRANS_IDAUTTRA ASC,           \n"
            + "MG.COD_CARGA ASC,           \n"
            + "MG.FEC_MIGRA ASC";

    private static final String QUERYRECHAZOSCARGUERNA = "SELECT DISTINCT MXMI.CAMPO1 AS PLACA,\n"
            + "M1OT.DESCRIPCION_CORTA AS SECRETARIA,        \n"
            + "MXMI.COD_CARGA AS CARGA,           \n"
            + "M1IN.COD_CRITERIO AS CODCRITERIO,           \n"
            + "M1IN.DESCRIPCION AS CRITERIO    \n"
            + "FROM MIGRACIONQX.RVBD_MIGRACION MXMI\n"
            + "LEFT JOIN MIGRUNT1.RVDB_CRITERIO_INTEGRIDAD M1IN ON M1IN.COD_CRITERIO = MXMI.COD_CRITERIO\n"
            + "LEFT JOIN MIGRUNT1.ORGANISMOS_TRANSITO M1OT ON M1OT.ID_SECRETARIA = MXMI.ID_SECRETARIA\n"
            + "WHERE MXMI.CAMPO1 = ? \n"
            + "and  ( M1IN.COD_CRITERIO < 1000 ) \n"
            + "ORDER BY MXMI.COD_CARGA ASC";

    public static final String QUERYFINALRNAMIGRA = "SELECT RA.AUTOMOTOR_AUTOTRANS_IDAUTTRA AS IDOT, \n"
            + "     ot.empresa_razosocia AS NOMBREOT,           \n"
            + "     RA.AUTOMOTOR_NROREGVEH AS IDVEHICULO,		  \n"
            + "     RA.AUTOMOTOR_PLACA_NUMPLACA AS PLACA,	\n"
            + "     ra.automotor_migrado AS MIGRADO,\n"
            + "     TO_CHAR(RA.AUTOMOTOR_FECMIGRA, 'DD/MM/YYYY') as FECMIGRA,\n"
            + "     CV.CLASVEHIC_NOMBRE AS CLASE,\n"
            + "     CL.COLOR_PRIMARIO AS COLOR,\n"
            + "     LN.LINEA_NOMBRE AS LINEA,\n"
            + "     MR.MARCA_NOMBRE AS MARCA,   \n"
            + "     MS.MODASERVI_NOMBRE AS MODASERVI,\n"
            + "     TC.TIPOCARRO_NOMBRE AS CARROCERIA,  \n"
            + "     RA.AUTOMOTOR_MODELO AS MODELO,   \n"
            + "     RA.AUTOMOTOR_NROCHASIS AS NROCHASIS,\n"
            + "     RA.AUTOMOTOR_NROMOTOR AS NROMOTOR,   \n"
            + "     RA.AUTOMOTOR_NROSERIE AS NROSERIE,   \n"
            + "     RA.AUTOMOTOR_CILINDRAJ AS CILINDRAJE,   \n"
            + "     RA.AUTOMOTOR_ESTAVEHIC_NOMBRE AS ESTADOVEHIC  \n"
            + "FROM MIGRARUNT.RA_AUTOMOTOR RA\n"
            + "LEFT JOIN MIGRARUNT.ge_autotrans AUT ON aut.autotrans_idauttra = RA.AUTOMOTOR_AUTOTRANS_IDAUTTRA\n"
            + "LEFT JOIN MIGRARUNT.GE_EMPRESA OT ON ot.empresa_persona_idpersona = aut.autotrans_empresa_persona\n"
            + "LEFT JOIN MIGRARUNT.PA_CLASVEHIC CV ON CV.CLASVEHIC_IDCLASE = RA.AUTOMOTOR_CLASVEHIC_IDCLASE\n"
            + "LEFT JOIN MIGRARUNT.PA_COLOR CL ON CL.COLOR_IDCOLOR = RA.AUTOMOTOR_COLOR_IDCOLOR\n"
            + "LEFT JOIN MIGRARUNT.PA_LINEA LN ON LN.LINEA_MARCA_IDMARCA = RA.AUTOMOTOR_MARCA_IDMARCA\n"
            + "                                AND LN.LINEA_IDLINEA = RA.AUTOMOTOR_LINEA_IDLINEA\n"
            + "LEFT JOIN MIGRARUNT.PA_MARCA MR ON MR.MARCA_IDMARCA = RA.AUTOMOTOR_MARCA_IDMARCA\n"
            + "LEFT JOIN MIGRARUNT.PA_MODASERVI MS ON MS.MODASERVI_IDEMODSER = RA.AUTOMOTOR_MODASERVI_IDEMODSER\n"
            + "LEFT JOIN MIGRARUNT.PA_TIPOCARRO TC ON TC.TIPOCARRO_CLASVEHIC_IDCLASE = RA.AUTOMOTOR_CLASVEHIC_IDCLASE\n"
            + "                                    AND TC.TIPOCARRO_IDCARROCE = RA.AUTOMOTOR_TIPOCARRO_IDCARROCE\n"
            + "WHERE RA.automotor_placa_numplaca = ?";

    private static final String QUERYBOLETINRNA = "SELECT DISTINCT \n"
            + "MXMI.CAMPO1 AS PLACA,\n"
            + "M1OT.DESCRIPCION_CORTA AS SECRETARIA, \n"
            + "TO_CHAR(MXCR.FECHA_CARGA, 'DD/MM/YYYY') AS FECHACARGA,           \n"
            + "MXCR.ID_FOLIO AS FOLIO,           \n"
            + "MXCR.ID_BOLETIN AS BOLETIN,                                     \n"
            + "M1CR.COD_CRITERIO AS CODCRITERIO ,           \n"
            + "M1CR.DESCRIPCION AS DESCRIPCION    \n"
            + "FROM MIGRACIONQX.RVBD_MIGRACION MXMI\n"
            + "LEFT JOIN MIGRACIONQX.CARGA MXCR ON MXCR.COD_CARGA = MXMI.COD_CARGA\n"
            + "                                  AND MXCR.ID_SECRETARIA = MXMI.ID_SECRETARIA\n"
            + "LEFT JOIN MIGRUNT1.ORGANISMOS_TRANSITO M1OT ON M1OT.ID_SECRETARIA = MXMI.ID_SECRETARIA\n"
            + "LEFT JOIN MIGRUNT1.RVDB_CRITERIO_INTEGRIDAD M1CR ON M1CR.COD_CRITERIO = MXMI.COD_CRITERIO\n"
            + "WHERE MXMI.CAMPO1 = ? \n"
            + "ORDER BY TO_CHAR(MXCR.FECHA_CARGA, 'DD/MM/YYYY') DESC";

    

    /**
     * Metodo encargado de consultar la placa en las tablas de migración
     *
     * @param placa
     * @return
     */
    public List<ConsultaRNAMigra> consultaRNAMigracion(final String placa) {

        try {
            Query query = entityManager.createNativeQuery(QUERYCONSULTARNAMIGRACION, "consultaRNAMigracion");
            query.setParameter(1, placa);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<Object[]> consultaIntermediaMigra(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(QUERYINTERMEDIAMIGRACION);
            query.setParameter(1, placa);
            return (List<Object[]>) query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<Object[]> consultaBoletinRNAMigra(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(QUERYBOLETINRNA);
            query.setParameter(1, placa);
            return (List<Object[]>) query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<Object[]> ConsultaRechazoRNAMigra(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(QUERYRECHAZOSCARGUERNA);
            query.setParameter(1, placa);
            return (List<Object[]>) query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<ConsultaRNAFinalMigra> consultaRNAFinalMigra(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(QUERYFINALRNAMIGRA, "consultaRNAFinalMigra");
            query.setParameter(1, placa);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }
}
