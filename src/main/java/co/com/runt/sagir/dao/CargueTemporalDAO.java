/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.CargueTemporal;
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
public class CargueTemporalDAO extends BaseDAO {
    
    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;
    
    private static final Logger LOG = Logger.getLogger(CargueTemporalDAO.class.getSimpleName());    
    
    public CargueTemporal insertDato(CargueTemporal datos) {
        entityManager.persist(datos);
        return datos;
    }
    
    public static final String BORRAR_DATOS_TEMPORAL = "DELETE FROM CARGUE_TEMPORAL";
    
    public int borrarDatosTemporales (){
        try{
            Query query = entityManager.createNativeQuery(BORRAR_DATOS_TEMPORAL);
            query.executeUpdate();
            return 1;
        }catch(Exception e){
            LOG.log(Level.SEVERE, "Error al borrar la tabla cargue_temporal.", e);
        }
        return 0;
    }
   
}
