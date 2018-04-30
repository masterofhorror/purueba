/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.CivPropietarios;
import co.com.runt.sagir.entities.CivVehiculos;
import co.com.runt.sagir.entities.PoblamientoPropietarios;
import co.com.runt.sagir.entities.PoblamientoVehiculos;
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
public class BoletinesTxtRnaDAO {

    @PersistenceContext(unitName = "sagirDS")

    EntityManager entityManager;

    /**
     * ------------------------Validaciones de poblamiento
     */
    private static final String COUNTPOBLAMIENTOVEHICULO = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.ARC_CAR AR\n"
            + "INNER JOIN MIGRACIONQX.ERROR_HOMOLOGACION  HOMOL ON HOMOL.ERROR_ORACLE  = AR.DESERR\n"
            + "WHERE AR.COD_CARGA = ?";

    private static final String COUNTPOBLAMIENTOPROPIETARIO = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.ARC_CAR AR \n"
            + "INNER JOIN MIGRACIONQX.ERROR_HOMOLOGACION HOMOL  ON HOMOL.ERROR_ORACLE= AR.DESERR\n"
            + "WHERE AR.TIPOARC = 1 \n"
            + "AND AR.CODESTADO = 0 \n"
            + "AND  AR.COD_CARGA = ?";

    /**
     * ------------------------Validaciones de CIV
     */
    public static final String COUNTCIVPROPIETARIO = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.RVBD_MIGRACION ERR\n"
            + "INNER JOIN MIGRUNT1.RVDB_CRITERIO_INTEGRIDAD CRIT ON ERR.COD_CRITERIO= CRIT.COD_CRITERIO \n"
            + "WHERE ERR.COD_CRITERIO IN(633,645,624,90004,90012) \n"
            + "AND ERR.COD_CARGA = ?\n"
            + "AND ERR.ID_SECRETARIA = ?";

    public static final String COUNTCIVVEHICULO = "SELECT COUNT(1) \n"
            + "FROM MIGRACIONQX.RVBD_MIGRACION ERR \n"
            + "INNER JOIN MIGRUNT1.RVDB_CRITERIO_INTEGRIDAD CRIT ON ERR.COD_CRITERIO = CRIT.COD_CRITERIO \n"
            + "WHERE ERR.COD_CRITERIO IN(622,600,185,187,603,604,601,602,192,637,632,638,605,526,211,198,251,90010,196,203,614,190,242,199,200,201,227,229,634,90005,606,424,646,647,648,649,650,651) \n"
            + "AND ERR.COD_CARGA = ?\n"
            + "AND ERR.ID_SECRETARIA = ?";

    /**
     * ------------------------Consultas de poblamiento
     */
    private static final String POBLAMIENTOPROPIETARIO = "SELECT ROWNUM AS LINEA,\n"
            + "SUBSTR(AR.DESREG,1,8) AS SECRETARIA,  \n"
            + "SUBSTR(AR.DESREG,9,6) AS PLACA,    \n"
            + "SUBSTR(AR.DESREG,15,1) AS TIP_DOCUMENTO,  \n"
            + "TRIM(SUBSTR(AR.DESREG,16,11)) AS NRO_DOCUMENTO,  \n"
            + "SUBSTR(AR.DESREG,28,8) AS FECHA,  \n"
            + "HOMOL.COD_CRITERIO AS COD_CRITERIO,  \n"
            + "HOMOL.ERROR_ESTANDAR AS DESCRIPCION_DEL_ERROR\n"
            + "FROM MIGRACIONQX.ARC_CAR AR \n"
            + "INNER JOIN MIGRACIONQX.ERROR_HOMOLOGACION HOMOL  ON HOMOL.ERROR_ORACLE= AR.DESERR\n"
            + "WHERE AR.TIPOARC = 1 \n"
            + "AND AR.CODESTADO = 0 \n"
            + "AND AR.COD_CARGA = ?\n"
            + "UNION ALL\n"
            + "SELECT ROWNUM,\n"
            + "TO_CHAR(ID_SECRETARIA), \n"
            + "NRO_PLACA, \n"
            + "ID_DOCUMENTO, \n"
            + "ID_USUARIO, TO_CHAR(FECHA,'RRRRMMDD'), \n"
            + "3065,\n"
            + "'Novedad en Propietarios: El propietario ya fue reportado'\n"
            + "FROM MIGRACIONQX.PROPIETARIOS_VEHICULO \n"
            + "WHERE COD_CARGA = ?\n"
            + "AND NOVEDAD='S' \n"
            + "ORDER BY DESCRIPCION_DEL_ERROR";

    private static final String POBLAMIENTOVEHICULO = "SELECT ROWNUM AS LINEA,\n"
            + "SUBSTR(AR.DESREG,9,6) AS PLACA,   \n"
            + "HOMOL.COD_CRITERIO AS COD_CRITERIO,  \n"
            + "HOMOL.ERROR_ESTANDAR AS DESCRIPCION_DEL_ERROR    \n"
            + "FROM MIGRACIONQX.ARC_CAR AR\n"
            + "INNER JOIN MIGRACIONQX.ERROR_HOMOLOGACION  HOMOL ON HOMOL.ERROR_ORACLE  = AR.DESERR\n"
            + "WHERE CODESTADO = 0\n"
            + "AND AR.TIPOARC = 3 \n"
            + "AND COD_CARGA = 25552\n"
            + "ORDER BY HOMOL.ERROR_ESTANDAR";

    /**
     * ------------------------Consultas de CIV
     */
    private static final String CIVPROPIETARIO = "SELECT ROWNUM AS LINEA,\n"
            + "ERR.CAMPO1 AS PLACA, \n"
            + "CRIT.COD_CRITERIO AS CODCRITERIO,\n"
            + "CRIT.DESCRIPCION AS DESCRIPCION,\n"
            + "ERR.ID_SECRETARIA AS SECRETARIA,\n"
            + "ERR.COD_CARGA AS CODCARGA\n"
            + "FROM MIGRACIONQX.RVBD_MIGRACION ERR\n"
            + "INNER JOIN MIGRUNT1.RVDB_CRITERIO_INTEGRIDAD CRIT ON ERR.COD_CRITERIO= CRIT.COD_CRITERIO \n"
            + "WHERE ERR.COD_CRITERIO IN(633,645,624,90004,90012) \n"
            + "AND ERR.COD_CARGA = ?\n"
            + "AND ERR.ID_SECRETARIA = ?";

    private static final String CIVVEHICULO = "SELECT ROWNUM AS LINEA,\n"
            + "ERR.CAMPO1 AS PLACA, \n"
            + "CRIT.COD_CRITERIO AS CODCRITERIO,\n"
            + "CRIT.DESCRIPCION AS DESCRIPCION\n"
            + "FROM MIGRACIONQX.RVBD_MIGRACION ERR \n"
            + "INNER JOIN MIGRUNT1.RVDB_CRITERIO_INTEGRIDAD CRIT ON ERR.COD_CRITERIO = CRIT.COD_CRITERIO \n"
            + "WHERE ERR.COD_CRITERIO IN(622,600,185,187,603,604,601,602,192,637,632,638,605,526,211,198,251,90010,196,203,614,190,242,199,200,201,227,229,634,90005,606,424,646,647,648,649,650,651) \n"
            + "AND ERR.COD_CARGA = ?\n"
            + "AND ERR.ID_SECRETARIA = ?";

    public Integer countPoblamientoVehiculos(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTPOBLAMIENTOVEHICULO)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countPoblamientoPropietarios(final Long idCarga) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTPOBLAMIENTOPROPIETARIO)
                    .setParameter(1, idCarga)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countCivVehiculos(final Long idCarga, final Long idSecretaria) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTCIVVEHICULO)
                    .setParameter(1, idCarga)
                    .setParameter(2, idSecretaria)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public Integer countCivPropietarios(final Long idCarga, final Long idSecretaria) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(COUNTCIVPROPIETARIO)
                    .setParameter(1, idCarga)
                    .setParameter(2, idSecretaria)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public List<PoblamientoPropietarios> poblamientoPropietarios(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(POBLAMIENTOPROPIETARIO, "poblamientoPropietarios");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<PoblamientoVehiculos> poblamientoVehiculos(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(POBLAMIENTOVEHICULO, "poblamientoVehiculos");
            query.setParameter(1, idCarga);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<CivPropietarios> civPropietarios(final Long idCarga, final Long idSecretaria) {
        try {
            Query query = entityManager.createNativeQuery(CIVPROPIETARIO, "civPropietarios");
            query.setParameter(1, idCarga);
            query.setParameter(2, idSecretaria);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    public List<CivVehiculos> civVehiculos(final Long idCarga, final Long idSecretaria) {
        try {
            Query query = entityManager.createNativeQuery(CIVVEHICULO, "civVehiculos");
            query.setParameter(1, idSecretaria);
            query.setParameter(2, idSecretaria);
            return query.getResultList();
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }
}
