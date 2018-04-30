/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.Carga;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
public class ListarBoletinDAO {

    private static final Logger LOGGER = Logger.getLogger(ConsultaRNAMigraDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")

    private EntityManager entityManager;

    /**
     * Metodo que enlista los boletines por código de carga
     *
     * @param codCarga
     * @return
     */
    public List<Carga> listarByCodCarga(final Integer codCarga) {
        try {
            return (List<Carga>) entityManager.createNamedQuery("Carga.findByCodCarga")
                    .setParameter("codCarga", codCarga)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Metodo que enlista los boletines por tipo de registro 1. RNA - 2. RNC
     *
     * @param tipoRegistro
     * @return
     */
    public List<Carga> listarByTipoRegisgtro(final Integer tipoRegistro) {
        try {
            return (List<Carga>) entityManager.createNamedQuery("Carga.findByTipoRegistro")
                    .setParameter("tipoRegistro", tipoRegistro)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Metodo que enlista los boletines por código de secretaria
     *
     * @param idAutoridad
     * @return
     */
    public List<Carga> listaByIdSecretaria(final Long idAutoridad) {
        try {
            return (List<Carga>) entityManager.createNamedQuery("Carga.findByIdSecretaria")
                    .setParameter("idAutoridad", idAutoridad)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Metodo que enlista los boletines de acuerdo a un rango de fecha
     *
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public List<Carga> listaByRangoFecha(final Date fechaInicio, final Date fechaFin) {
        try {
            return (List<Carga>) entityManager.createNamedQuery("Carga.findByFechaCarga")
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Metodo que consulta por código de carga
     *
     * @param idCarga
     * @return
     */
    public Carga consultaBoletin(final Long idCarga) {
        try {
            return (Carga) entityManager.createNamedQuery("Carga.findByCodCarga")
                    .setParameter("codCarga", idCarga)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Consulta de la tabla CSWSAGIR.TEMPORARY_BOLETIN_RNA_CONSOLID
     *
     * @param idCarga
     * @return
     */
    public Integer countBoletinByCodCargaRNA(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("BoletinRNA.findByCountCodCarga")
                    .setParameter("regCodCarga", idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Consulta de la tabla CSWSAGIR.TEMPORARY_BOLETIN_RNC_CONSOLID
     *
     * @param idCarga
     * @return
     */
    public Integer countBoletinByCodCargaRNC(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("BoletinRNC.findCountCodCarga")
                    .setParameter("codCarga", idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public Carga consultaAutoridadPorCodCarga(final Long codCarga) {
        try {
            return (Carga) entityManager.createNamedQuery("Carga.findAutoridadByCarga")
                    .setParameter("codCarga", codCarga)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

}
