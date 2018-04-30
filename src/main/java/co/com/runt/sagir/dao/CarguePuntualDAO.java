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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author APENA
 */
@Stateless
public class CarguePuntualDAO extends BaseDAO {

    @PersistenceContext(name = "sagirDS")
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(CarguePuntualDAO.class.getSimpleName());

    private static final String CONSULTA_REGVEHI_MIGRA = "SELECT AUTOMOTOR_NROREGVEH\n"
            + "FROM MIGRARUNT.RA_AUTOMOTOR\n"
            + "WHERE DESC_MIGRADO = 'N'\n"
            + "AND AUTOMOTOR_PLACA_NUMPLACA = ?";

    private static final String CONSULTA_REGVEHI_MIGRA_MARCA = "SELECT AUTOMOTOR_NROREGVEH\n"
            + "FROM MIGRARUNT.RA_AUTOMOTOR\n"
            + "WHERE DESC_MIGRADO != 'N'\n"
            + "AND AUTOMOTOR_PLACA_NUMPLACA = ?";

    private static final String CONSULTA_VEHICULO_CARGA_RUNTPROD = "SELECT COUNT(*)\n"
            + "FROM RUNTPROD.RA_AUTOMOTOR\n"
            + "WHERE AUTOMOTOR_PLACA_NUMPLACA = ?\n"
            + "AND AUTOMOTOR_CLASVEHIC_IDCLASE IN (4,8)";

    private static final String CONSULTA_VEHICULO_CARGA_MIGRARUNT = "SELECT COUNT(*)\n"
            + "FROM MIGRARUNT.RA_AUTOMOTOR\n"
            + "WHERE AUTOMOTOR_PLACA_NUMPLACA = ?\n"
            + "AND AUTOMOTOR_CLASVEHIC_IDCLASE IN (4,8)";

    public BigDecimal vehiculoCargaRuntprod(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_VEHICULO_CARGA_RUNTPROD);
            query.setParameter(1, placa);
            return (BigDecimal) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar la placa", e);
        }
        return null;
    }
    
    public BigDecimal vehiculoCargaMigrarunt(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_VEHICULO_CARGA_MIGRARUNT);
            query.setParameter(1, placa);
            return (BigDecimal) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar la placa", e);
        }
        return null;
    }

    public BigDecimal registroVehiculoMigracion(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_REGVEHI_MIGRA);
            query.setParameter(1, placa);
            return (BigDecimal) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar la placa", e);
        }
        return null;
    }

    public BigDecimal registroVehiculoMigracionMarca(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_REGVEHI_MIGRA_MARCA);
            query.setParameter(1, placa);
            return (BigDecimal) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar la placa", e);
        }
        return null;
    }
}
