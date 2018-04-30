/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ExportMigrados;
import co.com.runt.sagir.entities.ExportPendientesMigrar;
import co.com.runt.sagir.entities.ExportResultado;
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
public class ExportDAO {

    private static final Logger LOGGER = Logger.getLogger(ExportDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")

    private EntityManager entityManager;

    private static final String PENDIENTES_MIGRAR = "SELECT ' RA_AUTOMOTOR_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.RA_AUTOMOTOR_CARGUE WHERE AUTOMOTOR_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' RA_TIPCOMVEH_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.RA_TIPCOMVEH_CARGUE WHERE TIPCOMVEH_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' RA_TECNAUTOM_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.RA_TECNAUTOM_CARGUE WHERE TECNAUTOM_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' EV_LICETRANS_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.EV_LICETRANS_CARGUE WHERE LICETRANS_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' RC_RESTCONDU_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.RC_RESTCONDU_CARGUE WHERE RESTCONDU_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' TR_PERSONA_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.TR_PERSONA_CARGUE WHERE PERSONA_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' TR_PERSNATUR_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.TR_PERSNATUR_CARGUE WHERE PERSNATUR_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' TR_PERSDIREC_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.TR_PERSDIREC_CARGUE WHERE PERSDIREC_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' GE_EMPRESA_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.GE_EMPRESA_CARGUE WHERE EMPRESA_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' TR_EMPRDIREC_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.TR_EMPRDIREC_CARGUE WHERE EMPRDIREC_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' TR_PROPIETAR_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.TR_PROPIETAR_CARGUE WHERE PROPIETAR_ESTADO_CARGUE = 'P'\n"
            + "UNION\n"
            + "SELECT ' EV_LICECONDU_CARGUE' AS TABLA, COUNT(1) AS TOTAL FROM MIGRARUNT.EV_LICECONDU_CARGUE WHERE LICECONDU_ESTADO_CARGUE = 'P'";

    private static final String MIGRADOS = "select 're_ceraptcon' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RE_CERAPTCON where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ev_placa' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.EV_PLACA where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'tm_proimptem' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.TM_PROIMPTEM where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ev_lictraim' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.EV_LICTRAIMP where DESC_MIGRADO = 'M'\n"
            + "union	\n"
            + "select 'ra_decimpaut' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RA_DECIMPAUT where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 're_contarren' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RE_CONTARREN where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ev_tarjservi' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.EV_TARJSERVI where DESC_MIGRADO = 'M'\n"
            + "--UNION SELECT 'rs_polisegur' AS TABLA, COUNT(1) AS TOTAL FROM runtprodmqx1.rs_polisegur where desc_migrado = 'M'\n"
            + "--UNION SELECT 'ra_retemegas' AS TABLA, COUNT(1) AS TOTAL FROM runtprodmqx1.ra_retemegas where desc_migrado = 'M'\n"
            + "union\n"
            + "select 'ra_fichomveh' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RA_FICHOMVEH where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ra_medicaute' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RA_MEDICAUTE where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ra_medcaulev' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RA_MEDCAULEV where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ra_prenda' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RA_PRENDA where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'tr_propietar' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.TR_PROPIETAR where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'tr_emprdirec' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.TR_EMPRDIREC where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ge_empresa' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.GE_EMPRESA where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'tr_persdirec' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.TR_PERSDIREC where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'rc_restcondu' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RC_RESTCONDU where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ev_licecondu' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.EV_LICECONDU where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'rc_ceraptfis' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RC_CERAPTFIS where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'tr_persnatur' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.TR_PERSNATUR where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'tr_persona' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.TR_PERSONA where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ev_licetrans' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.EV_LICETRANS where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ra_tecnautom' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RA_TECNAUTOM where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ra_tipcomveh' AS TABLA, COUNT(1) AS TOTAL from RUNTPRODMQX1.RA_TIPCOMVEH where DESC_MIGRADO = 'M'\n"
            + "union\n"
            + "select 'ra_automotor' AS TABLA, COUNT(1)AS TOTAL from RUNTPRODMQX1.RA_AUTOMOTOR where DESC_MIGRADO = 'M'";

    private static final String RESULTADO = "SELECT TOTAL AS TOTAL, DESC_MIGRADO AS DESC_MIGRADO, TABLA AS TABLA\n"
            + "FROM\n"
            + "(SELECT COUNT(1) TOTAL, DESC_MIGRADO,'RA_AUTOMOTOR' AS TABLA\n"
            + "FROM RUNTPRODMQX1.RA_AUTOMOTOR \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'TR_PERSDIREC'\n"
            + "FROM RUNTPRODMQX1.TR_PERSDIREC \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'EV_LICECONDU' \n"
            + "FROM RUNTPRODMQX1.EV_LICECONDU \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'TR_PERSNATUR'\n"
            + "FROM RUNTPRODMQX1.TR_PERSNATUR \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'TR_PERSONA'\n"
            + "FROM RUNTPRODMQX1.TR_PERSONA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'TR_PROPIETAR' \n"
            + "FROM RUNTPRODMQX1.TR_PROPIETAR \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'EV_LICETRANS'\n"
            + "FROM RUNTPRODMQX1.EV_LICETRANS \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_TECNAUTOM' \n"
            + "FROM RUNTPRODMQX1.RA_TECNAUTOM \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RC_CERAPTFIS'\n"
            + "FROM RUNTPRODMQX1.RC_CERAPTFIS \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_TIPCOMVEH' \n"
            + "FROM RUNTPRODMQX1.RA_TIPCOMVEH \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "--union all (SELECT COUNT(1), desc_migrado,'RA_RETEMEGAS' FROM runtprodmqx1.RA_RETEMEGAS GROUP BY desc_migrado)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RE_CERAPTCON' \n"
            + "FROM RUNTPRODMQX1.RE_CERAPTCON \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), FICHOM.DESC_MIGRADO,'RA_FICHOMVEH' \n"
            + "FROM RUNTPRODMQX1.RA_FICHOMVEH FICHOM\n"
            + "GROUP BY FICHOM.DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_REPOENSAM'\n"
            + "FROM RUNTPRODMQX1.RA_REPOENSAM \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RC_RESTCONDU' \n"
            + "FROM RUNTPRODMQX1.RC_RESTCONDU \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_MEDICAUTE'\n"
            + "FROM RUNTPRODMQX1.RA_MEDICAUTE \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_PRENDA'\n"
            + "FROM RUNTPRODMQX1.RA_PRENDA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'GE_ENTRTESLI' \n"
            + "FROM RUNTPRODMQX1.GE_ENTRTESLI \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'GE_EMPRESA' \n"
            + "FROM RUNTPRODMQX1.GE_EMPRESA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_DECIMPAUT' \n"
            + "FROM RUNTPRODMQX1.RA_DECIMPAUT \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RH_VALITEFIC' \n"
            + "FROM RUNTPRODMQX1.RH_VALITEFIC \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'TR_EMPRDIREC' \n"
            + "FROM RUNTPRODMQX1.TR_EMPRDIREC \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'TR_REPRLEGAL' \n"
            + "FROM RUNTPRODMQX1.TR_REPRLEGAL \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_DECLIMPOR' \n"
            + "FROM RUNTPRODMQX1.RA_DECLIMPOR \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RH_FICTECHOM' \n"
            + "FROM RUNTPRODMQX1.RH_FICTECHOM \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_LEVMEDCAU'\n"
            + "FROM RUNTPRODMQX1.RA_LEVMEDCAU \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RP_PRESSERVI' \n"
            + "FROM RUNTPRODMQX1.RP_PRESSERVI \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RA_MEDCAULEV' \n"
            + "FROM RUNTPRODMQX1.RA_MEDCAULEV \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'EV_TARJSERVI' \n"
            + "FROM RUNTPRODMQX1.EV_TARJSERVI \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RE_CONTARREN' \n"
            + "FROM RUNTPRODMQX1.RE_CONTARREN \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RE_OPERARCDA' \n"
            + "FROM RUNTPRODMQX1.RE_OPERARCDA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'EV_RANGPLACA' \n"
            + "FROM RUNTPRODMQX1.EV_RANGPLACA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RC_CRCAUTMED' \n"
            + "FROM RUNTPRODMQX1.RC_CRCAUTMED \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RC_MEDICO' \n"
            + "FROM RUNTPRODMQX1.RC_MEDICO \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RE_EQUIPOCDA' \n"
            + "FROM RUNTPRODMQX1.RE_EQUIPOCDA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'EV_CATLICINS' \n"
            + "FROM RUNTPRODMQX1.EV_CATLICINS \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RP_SERVICIO' \n"
            + "FROM RUNTPRODMQX1.RP_SERVICIO \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'PA_EQUIPO' \n"
            + "FROM RUNTPRODMQX1.PA_EQUIPO \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'GE_RANGTESLI' \n"
            + "FROM RUNTPRODMQX1.GE_RANGTESLI \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RE_CENENSAUT' \n"
            + "FROM RUNTPRODMQX1.RE_CENENSAUT \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RE_ENSEAUTOR' \n"
            + "FROM RUNTPRODMQX1.RE_ENSEAUTOR \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO ,'PA_CATPORCDA'\n"
            + "FROM RUNTPRODMQX1.PA_CATPORCDA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'PA_MAREQUCDA' \n"
            + "FROM RUNTPRODMQX1.PA_MAREQUCDA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RC_CENRECCON' \n"
            + "FROM RUNTPRODMQX1.RC_CENRECCON \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RS_POLISEGUR' \n"
            + "FROM RUNTPRODMQX1.RS_POLISEGUR \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL \n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'EV_PLACA' \n"
            + "FROM RUNTPRODMQX1.EV_PLACA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RP_CDA' \n"
            + "FROM RUNTPRODMQX1.RP_CDA \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'EV_LICEINSTR' \n"
            + "FROM RUNTPRODMQX1.EV_LICEINSTR \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RE_INSTRUCTO' \n"
            + "FROM RUNTPRODMQX1.RE_INSTRUCTO \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RE_CATEINSTR' \n"
            + "FROM RUNTPRODMQX1.RE_CATEINSTR \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'EV_CATLICINS' \n"
            + "FROM RUNTPRODMQX1.EV_CATLICINS \n"
            + "GROUP BY DESC_MIGRADO)\n"
            + "UNION ALL\n"
            + "(SELECT COUNT(1), DESC_MIGRADO,'RC_RESTCONDU' \n"
            + "FROM RUNTPRODMQX1.RC_RESTCONDU \n"
            + "GROUP BY DESC_MIGRADO, 'RC_RESTCONDU')";

    public List<ExportPendientesMigrar> pendientesMigrar() {
        try {
            Query query = entityManager.createNativeQuery(PENDIENTES_MIGRAR, "pendietesMigrar");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<ExportMigrados> migrados() {
        try {
            Query query = entityManager.createNativeQuery(MIGRADOS, "migrados");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<ExportResultado> resultado() {
        try {
            Query query = entityManager.createNativeQuery(RESULTADO, "resultado");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }
}
