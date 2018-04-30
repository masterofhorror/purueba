/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.CivLicencias;
import co.com.runt.sagir.entities.CivPersonas;
import co.com.runt.sagir.entities.CivResidencias;
import co.com.runt.sagir.entities.CivRestricciones;
import co.com.runt.sagir.entities.PoblamientoLicencias;
import co.com.runt.sagir.entities.PoblamientoPersonas;
import co.com.runt.sagir.entities.PoblamientoResidencias;
import co.com.runt.sagir.entities.PoblamientoRestricciones;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class BoletinesTxtRncDAO {

    @PersistenceContext(unitName = "sagirDS")

    private EntityManager entityManager;

    /**
     * ------------------------Validaciones de poblamiento
     */
    private static final String COUNTPOBLAMIENTOLICENCIAS = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_RNC ER\n"
            + "WHERE ER.COD_CARGA = ?";

    private static final String COUNTPOBLAMIENTOPERSONAS = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_RNC ER\n"
            + "WHERE ER.COD_CARGA = ?";

    private static final String COUNTPOBLAMIENTORESIDENCIAS = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_RNC ER\n"
            + "WHERE ER.COD_CARGA = ?";

    private static final String COUNTPOBLAMIENTORESTRICCIONES = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_RNC ER\n"
            + "WHERE ER.COD_CARGA = ?";

    /**
     * ------------------------Validaciones de CIV
     */
    private static final String COUNTCIVLICENCIAS = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_LICENCIAS ER\n"
            + "WHERE ER.LICCODCARGA = ?";

    private static final String COUNTCIVPERSONAS = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_USUARIOS_RNC ER\n"
            + "WHERE ER.PERCODCARGA = ?";

    private static final String COUNTCIVRESIDENCIAS = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_RESIDENCIAS ER\n"
            + "WHERE ER.RESCODCARGA = ?";

    private static final String COUNTCIVRESTRICCIONES = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_RESTRICCIONES ER\n"
            + "WHERE ER.RSLCODCARGA = ?";

    /**
     * ------------------------Consultas de poblamiento
     */
    private static final String POBLAMIENTOLICENCIAS = "SELECT ROWNUM AS LINEA,\n"
            + "PE.COD_CARGA AS CODCARGA,\n"
            + "PE.CAMPO1 AS LICECONDU,   \n"
            + "PE.COD_CRITERIO AS COD_CRITERIO,  \n"
            + "ER.ERROR_ESTANDAR AS DESCRIPCION_DEL_ERROR\n"
            + "FROM MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_RNC PE \n"
            + "INNER JOIN MIGRACIONQX.ERROR_HOMOLOGACION_RNC ER ON PE.COD_CRITERIO= ER.COD_CRITERIO\n"
            + "WHERE PE.TIPO_ARCHIVO = 9 \n"
            + "AND PE.COD_CARGA = ?\n"
            + "ORDER BY PE.COD_CRITERIO";

    private static final String POBLAMIENTOPERSONAS = "SELECT ROWNUM AS LINEA,\n"
            + "P.COD_CARGA AS CODCARGA,\n"
            + "P.CAMPO2 AS TIPIDENT, \n"
            + "P.CAMPO3 AS NROIDENT,    \n"
            + "P.COD_CRITERIO AS COD_CRITERIO , \n"
            + "E.ERROR_ESTANDAR AS DESCRIPCION_DEL_ERROR    \n"
            + "FROM MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_RNC P\n"
            + "INNER JOIN MIGRACIONQX.ERROR_HOMOLOGACION_RNC E ON P.COD_CRITERIO= E.COD_CRITERIO \n"
            + "WHERE P.TIPO_ARCHIVO=14  \n"
            + "AND P.COD_CARGA =  ?\n"
            + "ORDER BY P.COD_CRITERIO";

    private static final String POBLAMIENTORESIDENCIAS = "SELECT ROWNUM AS LINEA,\n"
            + "P.COD_CARGA AS CODCARGA,\n"
            + "P.CAMPO2 AS TIPIDENT, \n"
            + "P.CAMPO3 AS NROIDENT,    \n"
            + "P.COD_CRITERIO AS COD_CRITERIO , \n"
            + "E.ERROR_ESTANDAR AS DESCRIPCION_DEL_ERROR    \n"
            + "FROM MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_RNC P\n"
            + "INNER JOIN MIGRACIONQX.ERROR_HOMOLOGACION_RNC E ON P.COD_CRITERIO = E.COD_CRITERIO \n"
            + "WHERE P.TIPO_ARCHIVO = 5 \n"
            + "AND P.COD_CARGA = ?\n"
            + "ORDER BY P.COD_CRITERIO";

    private static final String POBLAMIENTORESTRICCIONES = "SELECT ROWNUM AS LINEA,\n"
            + "P.COD_CARGA AS CODCARGA,\n"
            + "P.CAMPO1 AS LICECONDU,   \n"
            + "P.COD_CRITERIO AS COD_CRITERIO,  \n"
            + "E.ERROR_ESTANDAR AS DESCRIPCION_DEL_ERROR    \n"
            + "FROM MIGRACIONQX.RVBD_ERRORES_POBLAMIENTO_RNC P\n"
            + "INNER JOIN MIGRACIONQX.ERROR_HOMOLOGACION_RNC E ON P.COD_CRITERIO= E.COD_CRITERIO\n"
            + "WHERE P.TIPO_ARCHIVO = 6\n"
            + "AND P.COD_CARGA = ? \n"
            + "ORDER BY P.COD_CRITERIO";

    /**
     * ------------------------Consultas de CIV
     */
    private static final String CIVLICENCIAS = "SELECT ROWNUM AS LINEA,\n"
            + "LICE.LICCODCARGA AS CODCARGA,\n"
            + "LICE.LICNUMLI AS LICENCIA, \n"
            + "LICE.LICCODCRITERIO AS CODCRITERIO, \n"
            + "CRIT.DESCRIPCION AS DESCRIPCION\n"
            + "FROM MIGRACIONQX.RVBD_LICENCIAS LICE\n"
            + "INNER JOIN MIGRACIONQX.RVDB_CRITERIO_INTEGRIDAD CRIT ON LICE.LICCODCRITERIO= CRIT.COD_CRITERIO\n"
            + "WHERE LICE.LICCODCARGA= ?";

    private static final String CIVPERSONAS = "SELECT ROWNUM AS LINEA,\n"
            + "PERS.PERCODCARGA AS CODCARGA,\n"
            + "PERS.PERTIPID AS PERTIPID,"
            + "PERS.PERIDENT AS PERIDENT, \n"
            + "PERS.PERCODCRIT AS PERCODCRIT, \n"
            + "CRIT.DESCRIPCION AS DESCRIPCION \n"
            + "FROM MIGRACIONQX.RVBD_USUARIOS_RNC PERS\n"
            + "INNER JOIN MIGRACIONQX.RVDB_CRITERIO_INTEGRIDAD CRIT ON PERS.PERCODCRIT= CRIT.COD_CRITERIO\n"
            + "WHERE PERS.PERCODCARGA= ?";

    private static final String CIVRESIDENCIAS = "SELECT ROWNUM AS LINEA,\n"
            + "RESI.RESCODCARGA AS CODCARGA,\n"
            + "RESI.RESTIPID AS RESTIPID, \n"
            + "RESI.RESNIT AS RESNIT, \n"
            + "RESI.RESCODCRITERIO AS RESCODCRITERIO, \n"
            + "CRIT.DESCRIPCION AS DESCRIPCION \n"
            + "FROM MIGRACIONQX.RVBD_RESIDENCIAS RESI \n"
            + "INNER JOIN MIGRACIONQX.RVDB_CRITERIO_INTEGRIDAD CRIT ON RESI.RESCODCRITERIO= CRIT.COD_CRITERIO\n"
            + "WHERE RESI.RESCODCARGA = ?";

    private static final String CIVRESTRICCIONES = "SELECT ROWNUM AS LINEA,\n"
            + "RESL.RSLCODCARGA AS CODCARGA,\n"
            + "RESL.RSLNUMLI AS NUMLICE, \n"
            + "RESL.RSLCODCRITERIO AS CODCRITERIO,\n"
            + "CRIT.DESCRIPCION AS DESCRIPCION \n"
            + "FROM  MIGRACIONQX.RVBD_RESTRICCIONES RESL \n"
            + "INNER JOIN MIGRACIONQX.RVDB_CRITERIO_INTEGRIDAD CRIT ON RESL.RSLCODCRITERIO= CRIT.COD_CRITERIO\n"
            + "WHERE RESL.RSLCODCARGA = ?";

    public Integer countPobLicencias(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTPOBLAMIENTOLICENCIAS)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countCivLice(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTCIVLICENCIAS)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countPobPersonas(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTPOBLAMIENTOPERSONAS)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countCivPers(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTCIVPERSONAS)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countPobResidencias(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTPOBLAMIENTORESIDENCIAS)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countCivResi(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTCIVRESIDENCIAS)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countPobRestricciones(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTPOBLAMIENTORESTRICCIONES)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countCivRestri(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTCIVRESTRICCIONES)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public List<PoblamientoLicencias> poblaLicencias(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(POBLAMIENTOLICENCIAS, "poblamientoLicencias");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<PoblamientoRestricciones> poblaRestricciones(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(POBLAMIENTORESTRICCIONES, "poblamientoRestricciones");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<CivLicencias> civLice(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(CIVLICENCIAS, "civLicencias");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<CivPersonas> civPers(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(CIVPERSONAS, "civPersonas");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<CivResidencias> civResi(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(CIVRESIDENCIAS, "civResidencias");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<CivRestricciones> civRestri(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(CIVRESTRICCIONES, "civRestricciones");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<PoblamientoResidencias> poblaResidencias(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(POBLAMIENTORESIDENCIAS, "poblamientoResidencias");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<PoblamientoPersonas> poblaPersonas(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(POBLAMIENTOPERSONAS, "poblamientoPersonas");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

}
