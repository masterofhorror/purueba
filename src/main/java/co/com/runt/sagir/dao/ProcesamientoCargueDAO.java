/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.InfoFolio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class ProcesamientoCargueDAO {

    private static final Logger LOGGER = Logger.getLogger(ProgramacionCargueDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    private static final String QUERY_FOLIO = "	SELECT id_folio, to_char(is_fechaenv, 'yyyymmdd') as is_fechaenv FROM (\n"
            + "	SELECT car_archmigra.archmigra_identific as id_folio,   \n"
            + "				car_archmigra.archmigra_fechenvio as is_fechaenv,   \n"
            + "				car_archmigra.archmigra_autotrans_idauttra,   \n"
            + "				car_archmigra.archmigra_nombarchi,   \n"
            + "				car_archmigra.estado_proceso  \n"
            + "		 FROM migracionqx.car_archmigra  \n"
            + "		WHERE ( car_archmigra.archmigra_tiparcmig_id = ?) AND\n"
            + "				( car_archmigra.archmigra_fechenvio >= TO_DATE('01/04/2011','DD/MM/YYYY') ) AND\n"
            + "				( car_archmigra.estado_proceso = 33 ) AND\n"
            + "				( car_archmigra.archmigra_autotrans_idauttra = ?) AND\n"
            + "				( lower(car_archmigra.archmigra_nombarchi) LIKE ? )\n"
            + "	ORDER BY archmigra_fechenvio, archmigra_identific) TABLA\n"
            + "	WHERE ROWNUM = 1";

    public InfoFolio buscarInformacionFolio(final Integer tipoArchivo, final Long organismoTransito, final String archivo) {

        try {
            Query query = entityManager.createNativeQuery(QUERY_FOLIO, "consultaInfoFolio");
            query.setParameter(1, tipoArchivo);
            query.setParameter(2, organismoTransito);
            query.setParameter(3, archivo);

            return (InfoFolio) query.getSingleResult();
        } catch (NoResultException e) {
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return null;
    }

}
