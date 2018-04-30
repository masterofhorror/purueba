/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

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
public class ProgramacionCargueDAO {

    private static final Logger LOGGER = Logger.getLogger(ProgramacionCargueDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    public void migrarAutomotoresRna() {
        try {
            Query query = entityManager.createNativeQuery("call migracionqx.p_migrar_rna_autom_java(?)");
            query.setParameter(1, 0);
            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }

    public Long getCodigoProceso() {
        try {
            Query query = entityManager.createNativeQuery("SELECT migracionqx.seq_programar_cargue.nextval FROM DUAL");

            return ((BigDecimal) query.getSingleResult()).longValue();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }


}
