/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class CambioRNADAO {
    
    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;
    
    private static final Logger LOGGER = Logger.getLogger(CambioRNADAO.class.getName());

    public Integer consultaProceso (final String placa, final Long ot, final Integer tipoProceso){
        try {
            return Integer.parseInt(entityManager.createNamedQuery("MgActualizacionRna.findByValida")
                    .setParameter("placa", placa)
                    .setParameter("ot", ot)
                    .setParameter("tipoProceso", tipoProceso)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }
    
}
