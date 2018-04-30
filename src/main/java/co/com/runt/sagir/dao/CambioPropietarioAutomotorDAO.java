/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ArcCar;
import co.com.runt.sagir.entities.SgCarguearchivos;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class CambioPropietarioAutomotorDAO {

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(CambioPropietarioAutomotorDAO.class.getSimpleName());

    @Resource
    protected EJBContext context;

    public List<SgCarguearchivos> consultarCargues(final String estado) {
        return (List<SgCarguearchivos>) entityManager.createNamedQuery("SgCarguearchivos.findByCarguearchivosEstado")
                .setParameter("carguearchivosEstado", estado)
                .getResultList();
    }

    public SgCarguearchivos consultaArchivo(final String nombreArchivo, final String ruta) {
        try {
            return (SgCarguearchivos) entityManager.createNamedQuery("SgCarguearchivos.findByNombreYRutaArchivo")
                    .setParameter("carguearchivosNombreDatos", nombreArchivo)
                    .setParameter("carguearchivosDatos", ruta)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }
    
    /**
     *
     * @param idautra
     * @param fecha
     * @return
     */
    public List<SgCarguearchivos> consultaEstadoCargue(final String idautra, final Date fecha) {
        return (List<SgCarguearchivos>) entityManager
                .createNamedQuery("SgCarguearchivos.findByCarguearchivosIdautra")
                .setParameter("carguearchivosIdautra", idautra)
                .setParameter("fecha", fecha)
                .getResultList();
    }
    
    public List<ArcCar> idCargaBoletin (final String ot ){
        try {
            return (List<ArcCar>) entityManager.createNamedQuery("ArcCar.findByNomot")
                    .setParameter("nomot", ot)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return Collections.emptyList();
    }
    
    public List<SgCarguearchivos> consultaHistoricoCargue(final String idautra, final Date fecha) {
        return (List<SgCarguearchivos>) entityManager
                .createNamedQuery("SgCarguearchivos.findByFecha")
                .setParameter("carguearchivosIdautra", idautra)
                .setParameter("fecha", fecha)
                .getResultList();
    }
}
