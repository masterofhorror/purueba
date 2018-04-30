package co.com.runt.sagir.dao;


import co.com.runt.sagir.entities.PaTipoconsulta;
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
public class PaTipoconsultaDAO extends GenericDAO{
        
    private static final Logger LOGGER = Logger.getLogger(PaTipoconsultaDAO.class.getSimpleName());
    
    public PaTipoconsulta getByCodigo(String tipoConsulta) {
        try {
            return (PaTipoconsulta)em.createNamedQuery("PaTipoconsulta.findByTipoconsultaCodigo")
                    .setParameter("tipoconsultaCodigo", tipoConsulta)                 
                    .getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOGGER.log(Level.INFO, "{0}", ex.getLocalizedMessage());
            return null;
        }
    }
    
    public List<PaTipoconsulta> getAll() {
        try {
            return (List<PaTipoconsulta>)em.createNamedQuery("PaTipoconsulta.findAll")                            
                    .getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOGGER.log(Level.INFO, "{0}", ex.getLocalizedMessage());
            return Collections.emptyList();
        }
    }

}
