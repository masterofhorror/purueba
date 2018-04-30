/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.CodigoCarga;
import co.com.runt.sagir.entities.ProgramarCargue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class ProgramarCargueDAO {

    private static final Logger LOGGER = Logger.getLogger(ProgramarCargueDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    public List<ProgramarCargue> buscarProgramacionCargues() {

        try {
            Query query = entityManager.createNamedQuery("ProgramarCargue.findAll").setParameter("codigoEstado", 0L);

            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }

    private static final String CONSULTA_CODIGOS_DE_CARGA_PROCESO = "SELECT DISTINCT COD_CARGA AS COD_CARGA\n"
            + "FROM MIGRACIONQX.MG_PROGRAMAR_CARGUE\n"
            + "WHERE COD_ESTADO = 0\n"
            + "AND TIPOARC IN (1,2,3)\n"
            + "AND FECHA_PROGRAMACION >= SYSDATE -10";

    public List<CodigoCarga> codigosCargaProceso() {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_CODIGOS_DE_CARGA_PROCESO, "codigoCarga");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public boolean save(ProgramarCargue entity) {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return false;
    }

    private static final String CONSULTA_EXISTE_MEDICAUTE = "SELECT COUNT(*)\n"
            + "FROM MIGRACIONQX.MG_PROGRAMAR_CARGUE\n"
            + "WHERE COD_CARGA = ?\n"
            + "AND TIPOARC = 12";

    public Integer existeMedidaCautelarCargue(final Long codCarga) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_EXISTE_MEDICAUTE);
            query.setParameter(1, codCarga);

            return ((BigDecimal) query.getSingleResult()).intValue();
        } catch (NoResultException e) {
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    private static final String CONSULTA_REGISTROS_LEIDOS = "SELECT COUNT(*)\n"
            + "FROM MIGRACIONQX.ARC_CAR\n"
            + "WHERE COD_CARGA = ?\n"
            + "AND COD_PROCESO = ?";

    public Long registrosLeidos(final Long codCarga, final Long codProceso) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_REGISTROS_LEIDOS);
            query.setParameter(1, codCarga);
            query.setParameter(2, codProceso);

            return ((BigDecimal) query.getSingleResult()).longValue();
        } catch (NoResultException e) {
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    public void actualizaRegLeidos(final Long numRegLeidos, final Long CodCarga, final Long CodProceso) {
        try {
            Carga carga = new Carga();
            carga.setCodCarga(CodCarga.intValue());
            
            Query query = entityManager.createNamedQuery("ConfArcCar.updateNumRegLeidos");
            query.setParameter("numRegLeidos", numRegLeidos.intValue());
            query.setParameter("codProceso", CodProceso.intValue());
            query.setParameter("codCarga", carga);
            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }
    
    public void actualizarCodEstadoConfArcCar (final Long codCarga){
        try{
            Query query = entityManager.createNamedQuery("ConfArcCar.updateCodEstado");
            query.setParameter("codCarga", codCarga);
            query.executeUpdate();
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }
}
