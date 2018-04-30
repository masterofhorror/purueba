/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.RangosEspeciesRnaConsol;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class RangosEspeciesRnaConsolDAO {

    private static final Logger LOGGER = Logger.getLogger(RangosEspeciesRnaConsolDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    public Long buscarMaximo() {
        try {
            return entityManager.createNamedQuery("RangosEspeciesRnaConsol.findMaximoConsecutivo", Long.class).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return 0L;
    }

    public boolean save(RangosEspeciesRnaConsol entity) {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean callRangosRnaExcel(final Long organismoTransito) {
        try {
            Query query = entityManager.createNativeQuery("call MIGRUNT1.P_RANGOS_RNA_EXCEL(?, ?)");
            query.setParameter(1, organismoTransito);
            query.setParameter(2, 1);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean callRangosRna(final Long organismoTransito, final String observaciones) {
        try {
            Query query = entityManager.createNativeQuery("call MIGRUNT1.P_RANGOS_RNA(?, ?)");
            query.setParameter(1, organismoTransito);
            query.setParameter(2, observaciones);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean callSrc0020206520048(final Long organismoTransito, final String observaciones) {
        try {
            Query query = entityManager.createNativeQuery("call MIGRACIONQX.P_SRC_002_02065_20048(?, ?)");
            query.setParameter(1, organismoTransito);
            query.setParameter(2, observaciones);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean callMigracionEvPlaca(final Long organismoTransito) {
        try {
            Query query = entityManager.createNativeQuery("call MIGRACIONQX.P_MIGRACION_EV_PLACA(?)");
            query.setParameter(1, organismoTransito);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return false;
    }

}
