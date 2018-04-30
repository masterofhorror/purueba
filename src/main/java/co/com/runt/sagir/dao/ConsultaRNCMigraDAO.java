/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ConsultaRNCFinalMigra;
import co.com.runt.sagir.entities.ConsultaRNCMigra;
import co.com.runt.sagir.entities.ConsultaRNCMigraIntermedia;
import co.com.runt.sagir.entities.ConsultaRechazosRNCMigra;
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
 * @author Dsalamanca
 */
@Stateless
public class ConsultaRNCMigraDAO {

    private static final Logger LOGGER = Logger.getLogger(ConsultaRNCMigraDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")

    EntityManager entityManager;

    /**
     * *
     * Query que consulta la información en las tablas de migración
     */
    private static final String QUERY_CONSULTA_LC_MIGRA = "SELECT MXLC.LICTIPID AS TIPDOC,   \n"
            + "       MXLC.LICIDENT AS NRODOC,\n"
            + "       MXLC.LICNUMLI AS NROLIC,\n"
            + "       TO_CHAR(LC.LICECONDU_FECHEXPED, 'DD/MM/YYYY') AS FECHEXPED,\n"
            + "       TO_CHAR(MXLC.LICFECVEN, 'DD/MM/YYYY') AS FECHAVENCI,\n"
            + "       MXLC.LICCATEG AS CATEGORIA,\n"
            + "       MXLC.LICOFIEX AS IDAUTORI,\n"
            + "       M1OT.DESCRIPCION_CORTA AS AUTORIDAD,         \n"
            + "       MXLC.LICCODCARGA AS CARGA,   \n"
            + "       TO_CHAR(MXCG.FECHA_CARGA, 'DD/MM/YYYY') AS FECHACARGA,\n"
            + "       MXCG.ID_FOLIO AS FOLIO,   \n"
            + "       MXCG.ID_BOLETIN AS BOLETIN,\n"
            + "       (SELECT M1TP.DESC_TRAMITE\n"
            + "       FROM MIGRUNT1.TIPO_TRAMITE M1TP\n"
            + "       WHERE M1TP.ID_TRAMITE = MXLC.LICTIPTR) AS TIPOTRAMI,         \n"
            + "       TO_CHAR(MXLC.LICFECTR, 'DD/MM/YYYY') AS FECHATRAMI,   \n"
            + "       MXLC.LICNITES AS CODCEA,   \n"
            + "       MXLC.LICDIVIP AS DIVIPOLCEA,   \n"
            + "       MXLC.LICCERESC AS CERTCEA,\n"
            + "       MXLC.LICESTADO AS ESTADORANG,   \n"
            + "       MXLC.LICFACT AS COMPPAGO,   \n"
            + "       MXLC.LICCODIGO AS CODESPVEN,   \n"
            + "       MXLC.LICCERMEDICO AS CERTICRC,         \n"
            + "       MXLC.LICTIPID_ANT AS TIPOIDENTIANTIGUA,   \n"
            + "       MXLC.LICIDENT_ANT AS IDENTIANTIGUA,   \n"
            + "       MXLC.LICCATEG_NUE AS CATEGORINUEVA,         \n"
            + "       MXLC.LICSERVICIO AS SERVICIO,   \n"
            + "       MXLC.SIREV_CONSECUTIVO AS CONSECUTIVOSIREV,   \n"
            + "       (SELECT COUNT(1) FROM MIGRACIONQX.RNC_LICENCIAS MXRNC WHERE MXRNC.LICNUMLI = MXLC.LICNUMLI AND MXRNC.LICIDENT = MXLC.LICIDENT AND MXRNC.LICCODCARGA = MXLC.LICCODCARGA)  AS RECHAZO  \n"
            + "FROM MIGRACIONQX.LICENCIAS MXLC\n"
            + "LEFT JOIN MIGRACIONQX.CARGA MXCG ON MXCG.COD_CARGA = MXLC.LICCODCARGA\n"
            + "LEFT JOIN MIGRUNT1.ORGANISMOS_TRANSITO M1OT ON M1OT.ID_SECRETARIA = MXLC.LICOFIEX\n"
            + "LEFT JOIN MIGRARUNT.EV_LICECONDU LC ON LC.LICECONDU_NROLICENC = MXLC.LICNUMLI ";

    private static final String WHERENRODOCUMENTO = "WHERE MXLC.LICIDENT = ? ORDER BY MXLC.LICCODCARGA ASC";
    private static final String WHERENROLICENCIA = "WHERE MXLC.LICNUMLI = ? ORDER BY MXLC.LICCODCARGA ASC";

    /**
     * Query que consulta la información en las tablas intermedias
     */
    public static final String QUERY_RNC_INTERMEDIA = "SELECT MIQX.LICTIPID AS TIPODOC,   \n"
            + "        MIQX.LICIDENT AS NRODOC,\n"
            + "        MIQX.LICNUMLI AS NROLIC, \n"
            + "        MIQX.LICCODCARGA AS CARGA,   \n"
            + "        TO_CHAR(MIQX.LICFECTR, 'DD/MM/YYYY') AS FECHATRAMITE,\n"
            + "        (SELECT GE.EMPRESA_RAZOSOCIA\n"
            + "        FROM MIGRARUNT.GE_AUTOTRANS AUT,\n"
            + "        MIGRARUNT.GE_EMPRESA GE\n"
            + "        WHERE AUT.AUTOTRANS_IDAUTTRA = MIQX.LICOFIEX\n"
            + "        AND AUT.AUTOTRANS_EMPRESA_PERSONA = GE.EMPRESA_PERSONA_IDPERSONA) AS OFICINAEXPIDE,  \n"
            + "        (SELECT M1TP.DESC_TRAMITE\n"
            + "        FROM MIGRUNT1.TIPO_TRAMITE M1TP\n"
            + "        WHERE M1TP.ID_TRAMITE = MIQX.LICTIPTR) AS TIPOTRAMI, \n"
            + "        MIQX.LICCATEG AS CATEGORIA,   \n"
            + "        MIQX.LICNITES AS IDCEA,     \n"
            + "        (SELECT GE.EMPRESA_RAZOSOCIA\n"
            + "        FROM MIGRARUNT.GE_AUTOTRANS AUT,\n"
            + "        MIGRARUNT.GE_EMPRESA GE\n"
            + "        WHERE AUT.AUTOTRANS_IDAUTTRA = MIQX.LICDIVIP\n"
            + "        AND AUT.AUTOTRANS_EMPRESA_PERSONA = GE.EMPRESA_PERSONA_IDPERSONA) AS DIVIPOLCEA,  \n"
            + "        MIQX.LICCERESC AS NROCERCEA,   \n"
            + "        MIQX.LICESTADO AS ESTADOLC,   \n"
            + "        MIQX.LICFACT AS COMPROBPAGO,   \n"
            + "        MIQX.LICCODIGO AS CODESPVEN,   \n"
            + "        MIQX.LICTIPID_ANT AS TIPOIDENTANTIGUA,   \n"
            + "        MIQX.LICIDENT_ANT AS NROIDENTIANTIGUA,   \n"
            + "        MIQX.LICCATEG_NUE AS CATEGORIANUEVA,   \n"
            + "        MIQX.LICCERMEDICO AS CERTIFICADOCRC,   \n"
            + "        TO_CHAR(MIQX.LICFECVEN, 'DD/MM/YYYY') AS FECHAVENCI,   \n"
            + "        MIQX.LICSERVICIO AS SERVICIO,   \n"
            + "        MIQX.SIREV_CONSECUTIVO AS CONSECUTIVOSIREV,   \n"
            + "        MIQX.MIGRADO AS MIGRADO,   \n"
            + "        MIQX.CATEG_HOMOL AS HOMOLOGACION,   \n"
            + "        MIQX.LICCATEG_HOMOL1990 AS HOMOL1990\n"
            + " FROM MIGRACIONQX.RNC_LICENCIAS MIQX ";

    private static final String WHERENRODOCUMENTOINTER = "WHERE MIQX.LICIDENT = ?";
    private static final String WHERENROLICENCIAINTER = "WHERE MIQX.LICNUMLI = ?";

    /**
     * Consulta de los rechazos de RNC
     */
    public static final String QUERY_RNC_RECHAZOS = "SELECT MIQX.LICTIPID AS TIPODOC,   \n"
            + "       MIQX.LICIDENT AS NRODOC,\n"
            + "       MIQX.LICNUMLI AS NROLIC,\n"
            + "       MIQX.LICCODCARGA AS CARGA,\n"
            + "       MXCRI.COD_CRITERIO AS CODCRITERIO,   \n"
            + "       MXCRI.DESCRIPCION AS CRITERIO,   \n"
            + "       TO_CHAR(MIQX.LICFECTR, 'DD/MM/YYYY') AS FECHATRAMITE,   \n"
            + "       MIQX.LICCATEG AS CATEGORIA  \n"
            + "FROM MIGRACIONQX.RVBD_LICENCIAS MIQX \n"
            + "LEFT JOIN MIGRACIONQX.RVDB_CRITERIO_INTEGRIDAD MXCRI ON MXCRI.COD_CRITERIO = MIQX.LICCODCRITERIO ";

    public static final String WHERERECHAZONRODOC = "WHERE MIQX.LICIDENT = ?";
    public static final String WHERERECHAZONROLC = "WHERE MIQX.LICNUMLI = ?";

    public static final String QUERY_RNC_MIGRA_FINAL = "SELECT LC.LICECONDU_NROLICENC AS NROLC,   \n"
            + "     LC.LICECONDU_CATEGORIA_IDCATEGOR AS CATEGORIA,   \n"
            + "     LC.LICECONDU_AUTOTRANS_IDAUTTRA AS AUTORIDAD,\n"
            + "     M1OT.DESCRIPCION_CORTA AS OT,\n"
            + "     LC.LICECONDU_PERSNATUR_IDPERSONA AS IDPERSONA,\n"
            + "     LC.LICECONDU_PERSONA_IDTIPDOC AS TIPODOC,   \n"
            + "     LC.LICECONDU_PERSONA_NRODOCUME AS NRODOC,\n"
            + "     LC.LICECONDU_CERAPTFIS_NROCERTIF AS NROCERTCRC,   \n"
            + "     LC.LICECONDU_SOLICITUD_IDENSOLIC AS NROSOLICITUD,   \n"
            + "     LC.LICECONDU_MOTCANLIC_NOMBRE AS MOTIVOCANCELACION,   \n"
            + "     LC.LICECONDU_CERTENSEN_NROCERENS AS NROCERTCEA,   \n"
            + "     LC.LICECONDU_LICCODEXT_NROLICEXT AS LICEXTRANJ,   \n"
            + "     LC.LICECONDU_ESTAIMP AS ESTADOIMPRE,   \n"
            + "     TO_CHAR(LC.LICECONDU_FECHEXPED, 'DD/MM/YYYY') AS FECHAEXP,   \n"
            + "     TO_CHAR(LC.LICECONDU_FECHNOVED, 'DD/MM/YYYY') AS FECHANOVEDAD,   \n"
            + "     TO_CHAR(LC.LICECONDU_FECHVENCI, 'DD/MM/YYYY') AS FECHAVENCI,   \n"
            + "     LC.LICECONDU_ESTLICCON_NOMBRE AS ESTADO,   \n"
            + "     CASE LC.LICECONDU_MIGRADO WHEN 'M' THEN 'Migrado' WHEN 'N' THEN 'No migrado' ELSE LC.LICECONDU_MIGRADO END LICECONDUMIGRADO,   \n"
            + "     TO_CHAR(LC.LICECONDU_FECMIGRA, 'DD/MM/YYYY') AS FECHAMIGRA,   \n"
            + "     TO_CHAR(LC.LICECONDU_FECHACTUA, 'DD/MM/YYYY') AS FECHAACTUA,   \n"
            + "     TO_CHAR(LC.LICECONDU_FECHCANCE, 'DD/MM/YYYY') AS FECHACANCE,   \n"
            + "     LC.LICECONDU_LOGIN AS LOGIN,   \n"
            + "     TO_CHAR(LC.LICECONDU_FECHCAPTU, 'DD/MM/YYYY') AS FECHACAPTU,   \n"
            + "     LC.LICECONDU_CATEANTER AS CATEGORIANT,   \n"
            + "     CASE LC.DESC_MIGRADO WHEN 'M' THEN 'Migrado' WHEN 'N' THEN 'No migrado' ELSE LC.DESC_MIGRADO END DESCMIGRADO,   \n"
            + "     LC.LICECONDU_PERSNA_IDPER_MQX AS IDPERSONAPRODUCCION,   \n"
            + "     LC.LICECONDU_NROLICEC_RUNT AS NROLCPRODUCC,   \n"
            + "     TO_CHAR(LC.FECHA_QA, 'DD/MM/YYYY') AS FECHAQA\n"
            + "FROM MIGRARUNT.EV_LICECONDU LC\n"
            + "LEFT JOIN MIGRARUNT.TR_PERSONA TR ON TR.PERSONA_IDPERSONA = LC.LICECONDU_PERSNATUR_IDPERSONA\n"
            + "LEFT JOIN MIGRUNT1.ORGANISMOS_TRANSITO M1OT ON M1OT.ID_SECRETARIA = LC.LICECONDU_AUTOTRANS_IDAUTTRA ";

    public static final String WHEREFINALNRODOC = "WHERE TR.PERSONA_NRODOCUME = ?";
    public static final String WHEREFINALNROLC = "WHERE LC.LICECONDU_NROLICENC = ?";

    /**
     * Metodo encargado de consultar el número de documento en las tablas de
     * migración
     * @param nroDocumento
     * @return
     */
    public List<ConsultaRNCMigra> consultaRNCMigraNroDocu(final String nroDocumento) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_CONSULTA_LC_MIGRA + WHERENRODOCUMENTO, "consultaRNCMigracion");
            query.setParameter(1, nroDocumento);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el número de documento : {0}", nroDocumento);
        }
        return Collections.emptyList();
    }

    /**
     * Metodo encargado de consultar el número de licencia en las tablas de
     * migración
     *
     * @param nroLicencia
     * @return
     */
    public List<ConsultaRNCMigra> consultaRNCMigraNroLc(final String nroLicencia) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_CONSULTA_LC_MIGRA + WHERENROLICENCIA, "consultaRNCMigracion");
            query.setParameter(1, nroLicencia);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el número de Licencia : {0}", nroLicencia);
        }
        return Collections.emptyList();
    }

    /**
     * Metodo encargado de consultar el número de licencia en las tablas de
     * migración
     *
     * @param nroDocumento
     * @return 
     */
    public List<ConsultaRNCMigraIntermedia> consultaRNCIntermediaNroDoc(final String nroDocumento) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_RNC_INTERMEDIA + WHERENRODOCUMENTOINTER, "consultaRNCMigraIntermedia");
            query.setParameter(1, nroDocumento);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el número de documento : {0}", nroDocumento);
        }
        return Collections.emptyList();
    }

    /**
     * Metodo encargado de consultar el número de licencia en las tablas de
     * migración
     *
     * @param nroLicencia
     * @return 
     */
    public List<ConsultaRNCMigraIntermedia> consultaRNCIntermediaNroLic(final String nroLicencia) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_RNC_INTERMEDIA + WHERENROLICENCIAINTER, "consultaRNCMigraIntermedia");
            query.setParameter(1, nroLicencia);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el número de licencia : {0}", nroLicencia);
        }
        return Collections.emptyList();
    }

    /**
     * Metodo encargado de consultar los rechazos por número de documento
     *
     * @param nroDocumento
     * @return 
     */
    public List<ConsultaRechazosRNCMigra> consultaRechazosRNCNroDoc(final String nroDocumento) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_RNC_RECHAZOS + WHERERECHAZONRODOC, "consultaRechazosRNCMigra");
            query.setParameter(1, nroDocumento);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el número de documento : {0}", nroDocumento);
        }
        return Collections.emptyList();
    }

    /**
     * Metodo encargado de consultar los rechazos por número de licencia
     *
     * @param nroLicencia
     * @return 
     */
    public List<ConsultaRechazosRNCMigra> consultaRechazosRNCNroLc(final String nroLicencia) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_RNC_RECHAZOS + WHERERECHAZONROLC, "consultaRechazosRNCMigra");
            query.setParameter(1, nroLicencia);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el número de licencia : {0}", nroLicencia);
        }
        return Collections.emptyList();
    }

    /**
     * Metodo encargado de consultar el cargue final de las tablas de migración
     *
     * @param nroDocumento
     * @return 
     */
    public List<ConsultaRNCFinalMigra> consultaRNCFinalNroDoc(final String nroDocumento) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_RNC_MIGRA_FINAL + WHEREFINALNRODOC, "consultaFinalRNC");
            query.setParameter(1, nroDocumento);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el número de documento : {0}", nroDocumento);
        }
        return Collections.emptyList();
    }

    /**
     * Metodo encargado de consultar el cargue final de las tablas de migración
     *
     * @param nroLicencia
     * @return 
     */
    public List<ConsultaRNCFinalMigra> consultaRNCFinalNroLic(final String nroLicencia) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_RNC_MIGRA_FINAL + WHEREFINALNROLC, "consultaFinalRNC");
            query.setParameter(1, nroLicencia);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el número de licencia : {0}", nroLicencia);
        }
        return Collections.emptyList();
    }
}
