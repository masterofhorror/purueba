package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.PaAplicacion;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 *
 * @author Dospina
 */
@Stateless
public class PaAplicacionDAO extends GenericDAO{
        
    private static final Logger LOGGER = Logger.getLogger(PaAplicacionDAO.class.getSimpleName());
    
    public PaAplicacion getByCodigo(String aplicacion) {
        try {
            return (PaAplicacion)em.createNamedQuery("PaAplicacion.findByAplicacionCodigo")
                    .setParameter("aplicacionCodigo", aplicacion)                 
                    .getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOGGER.log(Level.INFO, "{0}", ex.getLocalizedMessage());
            return null;
        }
    }
    
    public List<PaAplicacion> getAll() {
        try {
            return (List<PaAplicacion>)em.createNamedQuery("PaAplicacion.findAll")                                   
                    .getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOGGER.log(Level.INFO, "{0}", ex.getLocalizedMessage());
            return Collections.emptyList();
        }
    }

}
