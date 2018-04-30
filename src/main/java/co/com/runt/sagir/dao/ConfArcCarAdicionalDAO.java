/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ConfArcCarAdicional;
import java.math.BigDecimal;
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
public class ConfArcCarAdicionalDAO {

    private static final Logger LOGGER = Logger.getLogger(CargaAdicionalDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;
    
    public Long getCodigoCarga() {
        try {
            Query query = entityManager.createNativeQuery("SELECT migracionqx.seq_cod_proceso_adicional.nextval from dual");

            return ((BigDecimal) query.getSingleResult()).longValue();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public boolean save(ConfArcCarAdicional entity) {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return false;
    }

}
