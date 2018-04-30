/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.EvPlaca;
import co.com.runt.sagir.entities.TblLicTto;
import co.com.runt.sagir.entities.VehiculoMalCargadoMigra;
import co.com.runt.sagir.entities.VehiculoMalCargadoProd;
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
 * @author dsalamanca
 */
@Stateless
public class MarcarVehiculoDAO {

    private static final Logger LOGGER = Logger.getLogger(ConsultaRNAMigraDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")

    private EntityManager entityManager;

    private static final String VEHICULO_MAL_CAGADO_PROD = "SELECT RA.AUTOMOTOR_NROREGVEH AS IDVEHICULO ,\n"
            + "RA.AUTOMOTOR_PLACA_NUMPLACA AS PLACA,\n"
            + "AUT.AUTOTRANS_IDAUTTRA AS IDAUTTRA,\n"
            + "GE.EMPRESA_RAZOSOCIA AS AUTORIDAD,\n"
            + "RA.AUTOMOTOR_ESTAVEHIC_NOMBRE AS ESTADOVEHICULO\n"
            + "FROM RUNTPROD.RA_AUTOMOTOR RA\n"
            + "INNER JOIN RUNTPROD.GE_AUTOTRANS AUT ON AUT.AUTOTRANS_IDAUTTRA = RA.AUTOMOTOR_AUTOTRANS_IDAUTTRA\n"
            + "INNER JOIN RUNTPROD.GE_EMPRESA GE ON GE.EMPRESA_PERSONA_IDPERSONA = AUT.AUTOTRANS_EMPRESA_PERSONA\n"
            + "WHERE RA.AUTOMOTOR_PLACA_NUMPLACA = ?";

    private static final String VEHICULO_MAL_CARGADO_MIGRA = "SELECT RA.AUTOMOTOR_NROREGVEH AS IDVEHICULO,\n"
            + "     LIC.NRO_PLACA AS PLACA,\n"
            + "     LIC.ID_SECRETARIA AS IDAUTORIDAD, \n"
            + "     OT.DESCRIPCION_CORTA AS SECRETARIA,\n"
            + "     LIC.COD_CARGA AS CARGA \n"
            + "FROM MIGRACIONQX.LIC_TTO LIC\n"
            + "LEFT JOIN MIGRACIONQX.MIG_RA_AUTOMOTOR RA ON  RA.cod_carga = LIC.cod_carga\n"
            + "                                          AND RA.automotor_placa_numplaca = LIC.nro_placa    \n"
            + "INNER JOIN MIGRUNT1.ORGANISMOS_TRANSITO OT ON OT.ID_SECRETARIA = LIC.ID_SECRETARIA\n"
            + "WHERE LIC.nro_placa = ?\n"
            + "UNION\n"
            + "SELECT RA.AUTOMOTOR_NROREGVEH AS IDVEHICULO,\n"
            + "     LIC.NRO_PLACA AS PLACA,\n"
            + "     LIC.ID_SECRETARIA AS IDAUTORIDAD, \n"
            + "     OT.DESCRIPCION_CORTA AS SECRETARIA,\n"
            + "     LIC.COD_CARGA AS CARGA \n"
            + "FROM MIGRACIONQX.LIC_TTO LIC\n"
            + "LEFT JOIN MIGRACIONQX.MIG_RA_AUTOMOTOR RA ON  RA.cod_carga = LIC.cod_carga\n"
            + "                                          AND RA.automotor_placa_numplaca = LIC.nro_placa    \n"
            + "INNER JOIN MIGRUNT1.ORGANISMOS_TRANSITO OT ON OT.ID_SECRETARIA = LIC.ID_SECRETARIA\n"
            + "WHERE LIC.nro_placa = ?";

    public static final String MIGRACIONQXAUTOMOTOR = "SELECT COUNT(1)\n"
            + "FROM MIGRACIONQX.MIG_RA_AUTOMOTOR RAM\n"
            + "WHERE RAM.AUTOMOTOR_PLACA_NUMPLACA  = ?";

    public static final String MIGRARUNTAUTOMOTOR = "SELECT COUNT(1)\n"
            + "FROM MIGRARUNT.RA_AUTOMOTOR \n"
            + "WHERE AUTOMOTOR_PLACA_NUMPLACA = ?\n"
            + "AND AUTOMOTOR_PLACA_NUMPLACA IS NOT NULL";

    public Integer migraAutomotor(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(MIGRARUNTAUTOMOTOR)
                    .setParameter(1, placa).toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    public Integer automotorMigraQx(final String placa) {
        try {
            return Integer.parseInt(entityManager.createNativeQuery(MIGRACIONQXAUTOMOTOR)
                    .setParameter(1, placa).toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return 0;
    }

    public List<VehiculoMalCargadoProd> vehiculoMalCargadoProd(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(VEHICULO_MAL_CAGADO_PROD, "vehiculoMalCargadoProd");
            query.setParameter(1, placa);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public List<VehiculoMalCargadoMigra> vehiculoMalCargadoMigra(final String placa, final String nuevaPlaca) {
        try {
            Query query = entityManager.createNativeQuery(VEHICULO_MAL_CARGADO_MIGRA, "vehiculoMalCargadoMigra");
            query.setParameter(1, placa);
            query.setParameter(2, nuevaPlaca);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public boolean registraLogMarcaLicTto(TblLicTto licTto) {
        try {
            entityManager.merge(licTto);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }

    public EvPlaca consultaEvPlaca(final String placa) {
        try {
            return (EvPlaca) entityManager.createNamedQuery("EvPlaca.findByPlaca")
                    .setParameter("placa", placa)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    public boolean registraEvPlaca(EvPlaca evPlaca) {
        try {
            entityManager.persist(evPlaca);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return false;
    }
}
