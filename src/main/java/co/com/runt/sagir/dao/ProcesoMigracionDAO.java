/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.dto.ConsultaFolio;
import co.com.runt.sagir.entities.InfoFolio;
import co.com.runt.sagir.entities.ArcCar;
import co.com.runt.sagir.entities.AutoridadTransitoHQ;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.ConfArcCar;
import co.com.runt.sagir.entities.LogArchivoFolio;
import co.com.runt.sagir.entities.MgProgramarCargue;
import co.com.runt.sagir.entities.SgCarguearchivos;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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
 * @author dsalamanca
 */
@Stateless
public class ProcesoMigracionDAO {

    private static final Logger LOGGER = Logger.getLogger(ProgramacionCargueDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    private static final String CONSULTA_CAR_ARCHMIGRA_NORMAL = "SELECT IDENT AS ARCHMIGRA_IDENTIFIC,\n"
            + "    TO_CHAR(ARCHMIGRA_FECHENVIO, 'yyyymmdd') AS ARCHMIGRA_FECHENVIO\n"
            + "FROM\n"
            + "    (SELECT CAR_ARCHMIGRA.ARCHMIGRA_IDENTIFIC IDENT,\n"
            + "        CAR_ARCHMIGRA.ARCHMIGRA_FECHENVIO,\n"
            + "        CAR_ARCHMIGRA.ARCHMIGRA_AUTOTRANS_IDAUTTRA,\n"
            + "        CAR_ARCHMIGRA.ARCHMIGRA_NOMBARCHI,\n"
            + "        CAR_ARCHMIGRA.ESTADO_PROCESO\n"
            + "    FROM MIGRACIONQX.CAR_ARCHMIGRA\n"
            + "    WHERE ( CAR_ARCHMIGRA.ARCHMIGRA_TIPARCMIG_ID = ? )\n"
            + "    AND ( CAR_ARCHMIGRA.ARCHMIGRA_FECHENVIO >= TO_DATE('03/11/2009','DD/MM/YYYY') )\n"
            + "    AND ( CAR_ARCHMIGRA.ESTADO_PROCESO = 33 )\n"
            + "    AND ( LOWER(CAR_ARCHMIGRA.ARCHMIGRA_NOMBARCHI) LIKE ? )\n"
            + "    ORDER BY ARCHMIGRA_FECHENVIO,\n"
            + "        ARCHMIGRA_IDENTIFIC\n"
            + "    ) TABLA\n"
            + "WHERE ROWNUM = 1";

    /**
     * Metodo que valida la cantidad de registro por nombre de archivo en la
     * tabla MIGRACIONQX.CONF_ARC_CAR
     *
     * @param nombreArchivo
     * @return
     */
    public Integer validaConfArcCar(final String nombreArchivo) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("ConfArcCar.findCountByNomArcOriginal")
                    .setParameter("nomArcOriginal", nombreArchivo)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    /**
     * Metodo que valida la cantidad de registro por nombre de archivo en la
     * tabla MIGRACIONQX.MG_PROGRAMAR_CARGUE
     *
     * @param nombreArchivo
     * @return
     */
    public Integer validaMgProgramarCargue(final String nombreArchivo) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("MgProgramarCargue.CountByNombreArchivo")
                    .setParameter("nombreArchivo", nombreArchivo)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    /**
     * Metodo que arroja el tipo de folio dependiendo del tipo de cargue
     *
     * @param tipoRegistro
     * @return
     */
    public Integer tipoRegistroRNC(final Integer tipoRegistro) {
        Integer folio = 0;
        if (tipoRegistro == 1) {//normal
            folio = 998;
        } else {
            if (tipoRegistro == 2) {//CD
                folio = 998;
            } else {
                if (tipoRegistro == 3) {//Tutela
                    folio = 1000;
                }
            }
        }
        return folio;
    }

    public ConsultaFolio consultarFolioRNA(final String nombreArchivo, final Integer tipoCargue, final Integer tutela, final Integer tipoRegistro) {
        ConsultaFolio consultaFolioDTO = new ConsultaFolio();
        InfoFolio infoFolio;
        Integer folio = 0;
        String fecha = "yyyyMMdd";
        SimpleDateFormat fechaCon = new SimpleDateFormat(fecha);

        if (tutela == 1) {
            folio = 1000;
            String fechaFolio = fechaCon.format(new Date());
            consultaFolioDTO.setIdFolio(folio);
            consultaFolioDTO.setFechaFolio(fechaFolio);

            return consultaFolioDTO;
        } else {
            if (tipoCargue == 3) {//CD
                folio = 999;
                String fechaFolio = fechaCon.format(new Date());
                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(fechaFolio);

                return consultaFolioDTO;
            }

            if (tipoCargue == 6) {//Imp. Temporal
                folio = 997;
                String fechaFolio = fechaCon.format(new Date());
                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(fechaFolio);

                return consultaFolioDTO;
            }

            if (tipoCargue == 4) {//Reproceso
                folio = 1001;
                String fechaFolio = fechaCon.format(new Date());
                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(fechaFolio);

                return consultaFolioDTO;
            }

            if (folio == 0) {
                infoFolio = buscarInformacionFolioNormal(tipoRegistro, nombreArchivo);
                Long retornoFolio = infoFolio.getIdFolio();
                folio = retornoFolio.intValue();

                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(infoFolio.getFechaEnvio());

                return consultaFolioDTO;
            }
        }

        return consultaFolioDTO;
    }

    /**
     * Metodo que se encarga de registrar en la tabla MIGRACIONQX.CARGA
     *
     * @param carga
     * @return
     */
    public boolean registroMigracionQxCarga(Carga carga) {
        try {
            entityManager.persist(carga);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    public AutoridadTransitoHQ consultaAutoridad(final Long idSecretaria) {
        try {
            return (AutoridadTransitoHQ) entityManager.createNamedQuery("AutoridadTransitoHQ.findByAutotransIdauttra")
                    .setParameter("idSecretaria", idSecretaria)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Metodo que se encarga de registrar en la tabla
     * MIGRACIONQX.LOG_ARCHIVO_FOLIO
     *
     * @param archivoFolio
     * @return
     */
    public boolean registroLogArchivoFolio(LogArchivoFolio archivoFolio) {
        try {
            entityManager.persist(archivoFolio);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Metodo que se encarga de registrar en la tabla MIGRACIONQX.CONF_ARC_CAR
     *
     * @param confArcCar
     * @return
     */
    public boolean registrarConfArcCar(ConfArcCar confArcCar) {
        try {
            entityManager.persist(confArcCar);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Metodo que devuelve la lista de los archivos pendientes por procesar del
     * proceso de migración de RNC
     *
     * @param idSecretaria
     * @return
     */
    public List<SgCarguearchivos> archivosPendientes(final String idSecretaria) {
        try {
            return (List<SgCarguearchivos>) entityManager.createNamedQuery("SgCarguearchivos.findByProcMigra")
                    .setParameter("idSecretaria", idSecretaria)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Metodo que actualiza el estado en la tabla SCWSAGIR.TBL_CARGUEARCHIVOS
     *
     * @param sc
     * @return
     */
    public boolean actualizaEstadoArchivo(SgCarguearchivos sc) {
        try {
            entityManager.merge(sc);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Metodo que consulta el archivo por nombre y que este en estado
     * SIN_PROCESAR
     *
     * @param nombreArchivo
     * @return
     */
    public SgCarguearchivos consultaArchivo(final String nombreArchivo) {
        try {
            return (SgCarguearchivos) entityManager.createNamedQuery("SgCarguearchivos.findProcMigraByNombreArchivo")
                    .setParameter("nombreArchivo", nombreArchivo)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public boolean registraArcCar(ArcCar arcCar) {
        try {
            entityManager.persist(arcCar);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    public boolean save(MgProgramarCargue entity) {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    public static final String VALIDA_EXISTE_CAR_ARC_MIGRA = "SELECT COUNT(*)\n"
            + "FROM MIGRACIONQX.CAR_ARCHMIGRA \n"
            + "WHERE ARCHMIGRA_TIPARCMIG_ID = ?\n"
            + "AND ARCHMIGRA_NOMBARCHI LIKE '%'||?||'%'\n"
            + "AND ARCHMIGRA_FECHENVIO >= TO_DATE('03/11/2009','DD/MM/YYYY')\n"
            + "AND ESTADO_PROCESO = 33";

    public Integer validacionExisteCarArcMigra(final Integer tipoRegistro, final String nombreArchivo) {
        try {
            Query query = entityManager.createNativeQuery(VALIDA_EXISTE_CAR_ARC_MIGRA);
            query.setParameter(1, tipoRegistro);
            query.setParameter(2, nombreArchivo);

            return ((BigDecimal) query.getSingleResult()).intValue();
        } catch (NoResultException e) {
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }

    public ConsultaFolio consultarFolioRNA(final String nombreArchivo, final Integer tipoCargue, final Integer tutela, final Long idSecretaria, final Integer tipoRegistro) {
        ConsultaFolio consultaFolioDTO = new ConsultaFolio();
        InfoFolio infoFolio = new InfoFolio();
        Integer folio = 0;
        String fecha = "yyyyMMdd";
        SimpleDateFormat fechaCon = new SimpleDateFormat(fecha);

        if (tutela == 1) {
            folio = 1000;
            String fechaFolio = fechaCon.format(new Date());
            consultaFolioDTO.setIdFolio(folio);
            consultaFolioDTO.setFechaFolio(fechaFolio);

            return consultaFolioDTO;
        } else {
            if (tipoCargue == 3) {//CD
                folio = 999;
                String fechaFolio = fechaCon.format(new Date());
                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(fechaFolio);

                return consultaFolioDTO;
            }

            if (tipoCargue == 6) {//Imp. Temporal
                folio = 997;
                String fechaFolio = fechaCon.format(new Date());
                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(fechaFolio);

                return consultaFolioDTO;
            }

            if (tipoCargue == 4) {//Reproceso
                folio = 1001;
                String fechaFolio = fechaCon.format(new Date());
                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(fechaFolio);

                return consultaFolioDTO;
            }
            
            if (tipoCargue == 7) {//RNTC_VEHICULOS_CARGA
                folio = 999;
                String fechaFolio = fechaCon.format(new Date());
                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(fechaFolio);

                return consultaFolioDTO;
            }
            
            if (tipoCargue == 8) {//VEHICULOS_SEGURIDAD_ESTADO
                folio = 999;
                String fechaFolio = fechaCon.format(new Date());
                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(fechaFolio);

                return consultaFolioDTO;
            }

            if (folio == 0) {
                infoFolio = buscarInformacionFolioNormal(tipoRegistro, nombreArchivo);
                Long retornoFolio = infoFolio.getIdFolio();
                folio = retornoFolio.intValue();

                consultaFolioDTO.setIdFolio(folio);
                consultaFolioDTO.setFechaFolio(infoFolio.getFechaEnvio());

                return consultaFolioDTO;
            }
        }

        return consultaFolioDTO;
    }

    public InfoFolio buscarInformacionFolioNormal(final Integer tipoRegistro, final String nombreArchivo) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_CAR_ARCHMIGRA_NORMAL, "consultaInfoFolio");
            query.setParameter(1, tipoRegistro);
            query.setParameter(2, nombreArchivo);

            return (InfoFolio) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static final String CONSULTA_DESCRIPCION_CORTA = "SELECT DESCRIPCION_CORTA\n"
            + "FROM MIGRUNT1.ORGANISMOS_TRANSITO\n"
            + "WHERE ID_SECRETARIA = ?\n"
            + "AND ROWNUM = 1";
    
    public String descripcionCortaOt(final Long idSecretaria) {
        try {
            Query query = entityManager.createNativeQuery(CONSULTA_DESCRIPCION_CORTA);
            query.setParameter(1, idSecretaria);

            return ((String) query.getSingleResult());
        } catch (NoResultException e) {
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return null;
    }
}
