/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.InfoModificacionesRealizadasActualizacion;
import co.com.runt.sagir.entities.InfoModificacionesRealizadasCasosEspeciales;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class ModificacionesRealizadasDAO {

    private static final Logger LOGGER = Logger.getLogger(ModificacionesRealizadasDAO.class.getSimpleName());

    private static final String QUERY_ACTUALIZACIONES = "SELECT row_number() over (order by 1 desc) AS id,\n"
            + "     numero_placa,\n"
            + "     ot_cargado,\n"
            + "     ot_solicita,\n"
            + "     estado_vehiculo_cargado,\n"
            + "     estado_vehiculo_solicita,\n"
            + "     to_char(fecha_proceso, 'dd/mm/yyyy') as fecha_proceso,\n"
            + "     descripcion_estado,\n"
            + "     id_funcionario,\n"
            + "     umi_nombres,\n"
            + "     umi_apellido1,\n"
            + "     umi_apellido2\n"
            + "FROM GMARTINEZ.mg_actualizacion_rna "
            + "     LEFT JOIN MIGRARUNT.mg_usuarios ON id_funcionario = umi_id_usuario\n"
            + "WHERE numero_placa = ?";

    private static final String QUERY_CASOS_ESPECIALES = "SELECT row_number() over (order by 1 desc) AS id,\n"
            + "     placa,\n"
            + "     to_char(fecha_cargue, 'dd/mm/yyyy') as fecha_cargue,\n"
            + "     medio,\n"
            + "     desc_proc\n"
            + "FROM GMARTINEZ.casos_especiales_rna\n"
            + "WHERE placa = ?";

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    public List<InfoModificacionesRealizadasActualizacion> buscarModificacionesActualizacionPorPlaca(final String placa) {

        try {
            Query query = entityManager.createNativeQuery(QUERY_ACTUALIZACIONES, "consultaModificacionesRealizadasActualizacion");
            query.setParameter(1, placa);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, placa);
        }
        return new ArrayList<>();
    }

    public List<InfoModificacionesRealizadasCasosEspeciales> buscarModificacionesCasosEspecialesPorPlaca(final String placa) {

        try {
            Query query = entityManager.createNativeQuery(QUERY_CASOS_ESPECIALES, "consultaModificacionesRealizadasCasosEspeciales");
            query.setParameter(1, placa);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, placa);
        }
        return new ArrayList<>();
    }

}
