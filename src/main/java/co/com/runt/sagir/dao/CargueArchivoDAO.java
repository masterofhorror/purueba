/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ArcCar;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.ConfArcCar;
import co.com.runt.sagir.entities.LogArchivoFolio;
import co.com.runt.sagir.entities.SgCarguearchivos;
import java.util.Date;
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
public class CargueArchivoDAO extends BaseDAO {

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    private static final Logger LOG = Logger.getLogger(CargueArchivoDAO.class.getSimpleName());

    public SgCarguearchivos insertCargue(SgCarguearchivos archivos) {
        try {
            entityManager.persist(archivos);
            return archivos;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public SgCarguearchivos actualizarPathFile(SgCarguearchivos cargue) {
        entityManager.merge(cargue);
        return cargue;
    }

    public boolean registrarCarga(Carga carga) {
        try {
            entityManager.persist(carga);
            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    public boolean registrarLogArchivoInfo(LogArchivoFolio archivoFolio) {
        try {
            entityManager.persist(archivoFolio);
            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    public boolean registrarConfArcCar(ConfArcCar arcCar) {
        try {
            entityManager.persist(arcCar);
            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    public boolean registrarArcCar(ArcCar arcCar) {
        try {
            entityManager.persist(arcCar);
            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

//    private static final String NROARCHIVOSCARGADOS = "SELECT COUNT(1)\n"
//            + "FROM CSWSAGIR.TBL_CARGUEARCHIVOS SG\n"
//            + "WHERE SG.CARGUEARCHIVOS_NOMBRE_DATOS LIKE '?1%'\n"
//            + "AND TRUNC(SG.CARGUEARCHIVOS_FECHA) = TRUNC(SYSDATE)";

//    public Integer nroArchivosCargados(final String idAutoridad) {
//        try {
//            return Integer.parseInt(entityManager.createNativeQuery(NROARCHIVOSCARGADOS)
//                    .setParameter(1, idAutoridad)
//                    .getSingleResult().toString());
//        } catch (NumberFormatException e) {
//            LOG.log(Level.SEVERE, e.getLocalizedMessage());
//        }
//        return 0;
//    }
    
    public Integer nroArchivosCargados(final String autoridad, final Date fecha) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("SgCarguearchivos.findByAutoridad")
                    .setParameter("autoridad", autoridad)
                    .setParameter("fecha", fecha)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    public Integer validaNroTicket(final String nroTicket) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("SgCarguearchivos.findCountByTicketProcesoMigracionRNC")
                    .setParameter("nrtoTicket", nroTicket)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    public Integer validaCargueIdSecretaria(final String idSecretaria) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("SgCarguearchivos.findCountByTicketProcesoMigracionRNC")
                    .setParameter("idSecretaria", idSecretaria)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    public SgCarguearchivos consultaArchivoZip(final String path) {
        try {
            return (SgCarguearchivos) entityManager.createNamedQuery("SgCarguearchivos.findPartFile")
                    .setParameter("pathFile", path)
                    .getSingleResult();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public boolean actualizarEstadoCargueArchivo(SgCarguearchivos sgCarguearchivos) {
        try {
            entityManager.merge(sgCarguearchivos);
            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

}
