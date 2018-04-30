/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.Mensaje;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class MensajeDAO {
    @PersistenceContext (unitName = "sagirDS")
    private EntityManager entityManager;
    
    private static final Logger LOGGER = Logger.getLogger(MensajeDAO.class.getSimpleName());
    public Mensaje buscarIdMensaje (final String idIdentificador){
        try{
          return (Mensaje) entityManager.createNamedQuery("Mensaje.detalle").setParameter("idIdentificador", idIdentificador).getSingleResult();          
        }catch (NoResultException | ClassCastException | NonUniqueResultException e) {
            LOGGER.log(Level.SEVERE, "Error {0}, {1}", new Object[]{idIdentificador, e});
        }
        return null;
    }
            
}
