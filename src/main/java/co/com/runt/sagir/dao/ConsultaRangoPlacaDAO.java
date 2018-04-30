/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ConsultaRangoPlaca;
import co.com.runt.sagir.entities.ConsultaRangoPlacaRNA;
import co.com.runt.sagir.entities.ConsultaRangoPlacaTotal;
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
 * @author APENA
 */
@Stateless
public class ConsultaRangoPlacaDAO {

    @PersistenceContext(name = "sagirDS")
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(ConsultaRangoPlacaDAO.class.getSimpleName());

    private static final String CONSULTA_RANGO_PLACAS = "SELECT ROWNUM AS NROREGISTRO,\n"
            + "RANGOS_PLACAS_CONSULTA.PLACA AS PLACA,\n"
            + "OT.DESCRIPCION_CORTA AS ORGANISMOTRANSITO\n"
            + "FROM MIGRACIONQX.RANGOS_PLACAS_CONSULTA,\n"
            + "MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA ,\n"
            + "MIGRUNT1.ORGANISMOS_TRANSITO OT\n"
            + "WHERE MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA.ID_SECRETARIA = OT.ID_SECRETARIA\n"
            + "AND (RANGOS_PLACAS_CONSULTA.PLACA = MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA.PLACA)\n"
            + "ORDER BY MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA.ID_SECRETARIA,\n"
            + "RANGOS_PLACAS_CONSULTA.PLACA";

    private static final String CONSULTA_RANGO_PLACAS_TOTAL = "SELECT MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA.ID_SECRETARIA AS ID_SECRETARIA,\n"
            + "    OT.DESCRIPCION_CORTA AS ORGANISMOTRANSITO,\n"
            + "    COUNT(*) AS TOTAL\n"
            + "    FROM MIGRACIONQX.RANGOS_PLACAS_CONSULTA,\n"
            + "    MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA ,\n"
            + "    MIGRUNT1.ORGANISMOS_TRANSITO OT\n"
            + "WHERE MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA.ID_SECRETARIA = OT.ID_SECRETARIA\n"
            + "AND RANGOS_PLACAS_CONSULTA.PLACA = MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA.PLACA\n"
            + "GROUP BY MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA.ID_SECRETARIA, OT.DESCRIPCION_CORTA\n"
            + "ORDER BY MIGRUNT1.RANGOS_ESPECIES_SERIE_RNA.ID_SECRETARIA";

    private static final String CONSULTA_RANGO_PLACAS_RNA = "SELECT RA.AUTOMOTOR_AUTOTRANS_IDAUTTRA AS ID_SECRETARIA,\n"
            + "    EM.EMPRESA_RAZOSOCIA AS ORGANISMOTRANSITO,\n"
            + "    RA.AUTOMOTOR_PLACA_NUMPLACA AS PLACA\n"
            + "    FROM RUNTPROD.RA_AUTOMOTOR RA,\n"
            + "    RUNTPROD.GE_AUTOTRANS AU,\n"
            + "    RUNTPROD.GE_EMPRESA EM,\n"
            + "    MIGRACIONQX.RANGOS_PLACAS_CONSULTA RP\n"
            + "WHERE RA.AUTOMOTOR_AUTOTRANS_IDAUTTRA = AU.AUTOTRANS_IDAUTTRA\n"
            + "AND AU.AUTOTRANS_EMPRESA_PERSONA = EM.EMPRESA_PERSONA_IDPERSONA\n"
            + "AND RP.PLACA = RA.AUTOMOTOR_PLACA_NUMPLACA\n"
            + "ORDER BY PLACA";

    public List<ConsultaRangoPlaca> rangoPlacas() {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_RANGO_PLACAS, "consultaRangoPlaca");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<ConsultaRangoPlacaTotal> rangoPlacasTotal() {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_RANGO_PLACAS_TOTAL, "consultaRangoPlacaTotal");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<ConsultaRangoPlacaRNA> rangoPlacasRNA() {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_RANGO_PLACAS_RNA, "consultaRangoPlacaRNA");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public String maxRangoPlaca() {
        try {
            return (String) entityManager.createNamedQuery("RangosPlacasConsulta.findByPlacaMAX")
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public String minRangoPlaca() {
        try {
            return (String) entityManager.createNamedQuery("RangosPlacasConsulta.findByPlacaMIN")
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

}
