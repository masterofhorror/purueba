/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.CiaCargue;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.entities.ConsultaErroresTxtCia;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author APENA
 */
@Stateless
public class CargueCiaDAO {

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(CargueCiaDAO.class.getSimpleName());

    @Resource
    protected EJBContext context;

    public Long secuenciaCia() {
        try {
            Query query = entityManager.createNativeQuery("SELECT migracionqx.seq_cod_carga_otros_actores.nextval from dual");

            return ((BigDecimal) query.getSingleResult()).longValue();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public SgCarguearchivos consultaArchivo(final String nombreArchivo, final String ruta) {
        try {
            return (SgCarguearchivos) entityManager.createNamedQuery("SgCarguearchivos.findByNombreYRutaArchivo")
                    .setParameter("carguearchivosNombreDatos", nombreArchivo)
                    .setParameter("carguearchivosDatos", ruta)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public boolean guardarTblArchivoCia(CiaCargue entrada) {
        try {
            entityManager.persist(entrada);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    private static final String COUNT_ERROR_TXT = "SELECT COUNT(*)\n"
            + "FROM MIGRACIONQX.CIA_CARGUE CIA, MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_OTROS RVEPO, MIGRACIONQX.ERROR_HOMOLOGACION_OTROS_ACT EHOA\n"
            + "WHERE CIA.COD_CARGA = ?\n"
            + "AND CIA.DES_MIGRADO <> 'N'\n"
            + "AND CIA.COD_CARGA = RVEPO.COD_CARGA\n"
            + "AND CIA.NUM_REG = RVEPO.NUMREG\n"
            + "AND RVEPO.TIPO_ARCHIVO = 56\n"
            + "AND EHOA.COD_CRITERIO = RVEPO.COD_CRITERIO\n"
            + "ORDER BY cia.num_reg";

    private static final String CONSULTA_ERROR_TXT = "SELECT ROWNUM AS REG_CONSUL,\n"
            + "    CIA.NRO_COMPARENDO AS NRO_COMPARENDO,\n"
            + "    CIA.TIPO_DOC_ALUMNO AS TIPO_DOC_ALUMNO,\n"
            + "    CIA.NRO_DOC_ALUMNO AS NRO_DOC_ALUMNO,\n"
            + "    CIA.NRO_CERTIFICADO AS NRO_CERTIFICADO,\n"
            + "    SEC_VALIDACION AS SEC_VALIDACION,\n"
            + "    NOMBRE_ARCHIVO AS NOMBRE_ARCHIVO,\n"
            + "    RVEPO.COD_CRITERIO AS COD_CRITERIO,\n"
            + "    ERROR_ESTANDAR AS ERROR_ESTANDAR\n"
            + "FROM MIGRACIONQX.CIA_CARGUE CIA,\n"
            + "    MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_OTROS RVEPO,\n"
            + "    MIGRACIONQX.ERROR_HOMOLOGACION_OTROS_ACT EHOA\n"
            + "WHERE CIA.COD_CARGA = ?\n"
            + "AND CIA.DES_MIGRADO <> 'N'\n"
            + "AND CIA.COD_CARGA = RVEPO.COD_CARGA\n"
            + "AND CIA.NUM_REG = RVEPO.NUMREG\n"
            + "AND RVEPO.TIPO_ARCHIVO = 56\n"
            + "AND EHOA.COD_CRITERIO = RVEPO.COD_CRITERIO\n"
            + "ORDER BY CIA.NUM_REG";

    public Long countErroresTxt(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(COUNT_ERROR_TXT);
            query.setParameter(1, idCarga);
            return ((BigDecimal) query.getSingleResult()).longValue();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public List<ConsultaErroresTxtCia> consultaErroresTxt(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_ERROR_TXT, "consultaErroresTxtCia");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    private static final String CONSULTA_EXISTE_CARGA = "SELECT COUNT(*)\n"
            + "FROM MIGRACIONQX.CIA_CARGUE\n"
            + "WHERE COD_CARGA = ?";
    
    public Long countExisteCarga(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_EXISTE_CARGA);
            query.setParameter(1, idCarga);
            return ((BigDecimal) query.getSingleResult()).longValue();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }
}
