/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ReprocesarRna;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class ReprocesarRnaDAO {

    private static final Logger LOGGER = Logger.getLogger(ConsultaRNCMigraDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")

    EntityManager entityManager;

    private static final String CONSULTA_REPROCESAR_RNA = "SELECT MG.COD_CARGA AS CODCARGA,           \n"
            + "MG.NRO_PLACA AS PLACA,           \n"
            + "MG.ESTADO_PROCESO AS ESTADO,           \n"
            + "MG.FECHA_PROCESO AS FECHAPROCESO, \n"
            + "CASE LIC.DES_MIGRADO WHEN 'M' THEN 'Migrado' WHEN 'N' THEN 'No migrado' ELSE LIC.DES_MIGRADO END MIGRADO \n"
            + "FROM MIGRACIONQX.MG_ACTUALIZACION_RNA MG\n"
            + "INNER JOIN MIGRACIONQX.LIC_TTO LIC ON LIC.COD_CARGA = MG.COD_CARGA\n"
            + "                                   AND LIC.NRO_PLACA = MG.NRO_PLACA\n"
            + "WHERE LIC.IND_VALIDO = 1\n"
            + "AND MG.COD_CARGA = ?";

    
    public List<ReprocesarRna> consultaReprocesar (final Integer idCarga){
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_REPROCESAR_RNA, "reprocesarRna");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }
    
    
}
