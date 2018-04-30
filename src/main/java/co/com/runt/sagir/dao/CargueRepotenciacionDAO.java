/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.MgCarRepotenciacion;
import co.com.runt.sagir.entities.MgConfCarRepotenciacion;
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
public class CargueRepotenciacionDAO {

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;
    private static final Logger LOGGER = Logger.getLogger(CargueRepotenciacionDAO.class.getName());
    
    public boolean mgConfCarRepotenciacion(MgConfCarRepotenciacion repotenciacion){
        try {
            entityManager.persist(repotenciacion);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return false;
    }
    
    public MgConfCarRepotenciacion consultaMgRepotenciacion(final String ruta){
        try {
            return (MgConfCarRepotenciacion) entityManager.createNamedQuery("MgConfCarRepotenciacion.findByNombreArchivo")
                    .setParameter("nombreArchivo", ruta)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }
    
    public boolean mgCarRepotenciacion(MgCarRepotenciacion repotenciacion){
        try {
            entityManager.persist(repotenciacion);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return false;
    }
    
    public MgConfCarRepotenciacion consultaPorNroTicket (final String nroTicket){
        try {
            return (MgConfCarRepotenciacion) entityManager.createNamedQuery("MgConfCarRepotenciacion.findByNroTicket")
                    .setParameter("nroTicket", nroTicket)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }
}
