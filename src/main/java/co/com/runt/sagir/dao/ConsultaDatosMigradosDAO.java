/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ConsultaDatosMigrados;
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
 * @author APENA
 */
@Stateless
public class ConsultaDatosMigradosDAO {

    @PersistenceContext(name = "sagirDS")
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(ConsultaDatosMigradosDAO.class.getSimpleName());

    private static final String CONSULTA_DATOS_MIGRADOS = "SELECT LT.COD_CARGA AS ID_CARGA,\n"
            + "    LT.NRO_PLACA AS PLACA,\n"
            + "    OT.DESCRIPCION_CORTA AS SECRETARIA,\n"
            + "    LT.ID_MARCA AS MARCA,\n"
            + "    LT.ID_LINEA AS LINEA,\n"
            + "    CLS.CLASVEHIC_NOMBRE AS CLASE,\n"
            + "    LT.DES_COLOR AS COLOR,\n"
            + "    SER.TIPOSERVI_NOMBRE AS SERVICIO,\n"
            + "    CAR.CARROCERI_NOMBRE AS CARROCERIA,\n"
            + "    LT.CILINDRAJE AS CILINDRAJE,\n"
            + "    LT.MODELO AS MODELO,\n"
            + "    LT.NRO_MOTOR AS NUM_MOTOR,\n"
            + "    LT.NRO_SERIE AS NUM_SERIE,\n"
            + "    LT.NRO_CHASIS AS NUM_CHASIS,\n"
            + "    LT.CAP_TONELADAS AS TONELADAS,\n"
            + "    LT.CAP_PASAJEROS AS PASAJEROS,\n"
            + "    CASE LT.ESTADO_VEHICULO WHEN '1' THEN 'ACTIVO' WHEN '2' THEN 'CANCELADO' WHEN '3' THEN 'TRASLADO' ELSE 'NO VALIDO' END AS ESTADO,\n"
            + "    TO_CHAR(LT.FECHA_MATRICULA_INICIAL, 'DD/MM/YYYY') AS MATRICULA_INICIAL,\n"
            + "    TO_CHAR(CAR.FECHA_CARGA, 'DD/MM/YYYY') AS FECHA_CARGA,\n"
            + "    COM.TIPOCOMBU_DESCRIPCI AS COMBUSTIBLE,\n"
            + "    CASE WHEN LT.IND_VALIDO = 1 THEN 'APROBADO' ELSE 'RECHAZADO' END AS ESTADO_CARGUE\n"
            + "FROM MIGRACIONQX.LIC_TTO LT\n"
            + "INNER JOIN MIGRUNT1.ORGANISMOS_TRANSITO OT ON  LT.ID_SECRETARIA = OT.ID_SECRETARIA\n"
            + "INNER JOIN RUNTPROD.PA_CLASVEHIC CLS ON  LT.ID_CLASE = CLS.CLASVEHIC_IDCLASE\n"
            + "INNER JOIN MIGRACIONQX.MIG_HOMOL_TIPOSERVI HSER ON LT.ID_SERVICIO = HSER.ID_SERVICIO_MT\n"
            + "INNER JOIN RUNTPROD.PA_TIPOSERVI SER ON  HSER.TIPOSERVI_IDETIPSER_RUNT = SER.TIPOSERVI_IDETIPSER\n"
            + "INNER JOIN RUNTPROD.PA_CARROCERI CAR ON  LT.ID_CARROCERIA = CAR.CARROCERI_ID\n"
            + "INNER JOIN RUNTPROD.PA_TIPOCOMBU COM ON  LT.ID_COMBUSTIBLE = COM.TIPOCOMBU_NOMBRE\n"
            + "INNER JOIN MIGRACIONQX.CARGA CAR ON  LT.COD_CARGA = CAR.COD_CARGA\n"
            + "WHERE NRO_PLACA = ?\n"
            + "AND LT.COD_CARGA = ?\n"
            + "ORDER BY CAR.FECHA_CARGA";
    
    public List<ConsultaDatosMigrados> datosMigrados(final String placa, final Long idCarga){
        try{
            Query query = entityManager.createNativeQuery(CONSULTA_DATOS_MIGRADOS, "consultaDatosMigrados");
            query.setParameter(1, placa);
            query.setParameter(2, idCarga);
            return query.getResultList();
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "Error al consultar la placa", e);
        }
        return Collections.emptyList();
    }
    
}
