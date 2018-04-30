/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.common;

/**
 *
 * @author dsalamanca
 */
public class ProcedimientosAlmacenados {

    /**
     * Procedimientos almancenados
     */
    public static final String P_POBLAMIENTO_RNA_PROP
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_POBLAMIENTO_RNA_PROP", 1);

    public static final String P_APLICA_CRITERIOS_RNA_PROP
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_APLICA_CRITERIOS_RNA_PROP", 2);

    public static final String P_VERIFICA_PROPIETARIO_PROD
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_VERIFICAR_PROPIETARIO_PROD", 1);

    public static final String P_CARGAR_PROPIETARIO_PROD
            = SQLTools.buildProcedureCall("CARGUES.P_CARGAR_PROPIETARIO_PROD", 1);

    public static final String P_ACTUALIZAR_ESTADO_RNA_PLACA
            = SQLTools.buildProcedureCall("GMARTINEZ.P_ACTUALIZAR_ESTADO_RNA_PLACA", 2);

    public static final String P_ACT_VEHI_CAMBIO_RES_PLACA
            = SQLTools.buildProcedureCall("CARGUES.P_ACT_VEHI_CAMBIO_RES_PLACA", 2);

    public static final String P_ACT_PROPIET_CAMBIO_RES_PLACA
            = SQLTools.buildProcedureCall("CARGUES.P_ACT_PROPIET_CAMBIO_RES_PLACA", 2);

    public static final String P_ELIMINAR_PLACA_MIG_RNA
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_ELIMINAR_PLACA_MIG_RNA", 1);

    public static final String P_ELIMINAR_PLACA_FINAL_RNA
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_ELIMINAR_PLACA_FINAL_RNA", 1);

    public static final String P_RANGOS_PLACAS_CONSULTA
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_RANGOS_PLACAS_CONSULTA", 2);

    public static final String P_VALIDAR_CARGUE_REPOTENCIADOS
            = SQLTools.buildProcedureCall("CARGUES.P_VALIDAR_CARGUE_REPOTENCIADOS", 1);

    public static final String P_CONSULTAR_DEC_IMP_DIAN
            = SQLTools.buildProcedureCall("CARGUES.P_CONSULTAR_DEC_IMP_DIAN", 6);

    public static final String P_CARGUE_PUNTUAL
            = SQLTools.buildProcedureCall("CSWSAGIR.PR_MG_CARGUE_PUNTUTAL", 2);

    public static final String P_MARCAR_CARGUE_PUNTUAL
            = SQLTools.buildProcedureCall("CSWSAGIR.PR_MG_MARCAR_CARGUE_PUNTUAL", 2);

    public static final String P_VALIDAR_PERSONA_RNC
            = SQLTools.buildProcedureCall("CARGUES.P_VALIDAR_PERSONA_RNC", 0);

    public static final String P_SINCRONIZAR_MASIVO_RNC_PROD
            = SQLTools.buildProcedureCall("CARGUES.P_SINCRONIZAR_MASIVO_RNC_PROD", 0);

    public static final String P_SINCRONIZAR_MASIVO_RNA_PROD
            = SQLTools.buildProcedureCall("CARGUES.P_SINCRONIZAR_MASIVO_RNA_PROD", 0);

    public static final String P_MIGRAR_RNA
            = SQLTools.buildProcedureCall("GMARTINEZ.P_MIGRAR_RNA", 0);
    
    public static final String P_MIGRAR_RNC
            = SQLTools.buildProcedureCall("GMARTINEZ.P_MIGRAR_RNC", 0);

    public static final String P_MIGRAR_RNA_PLACA
            = SQLTools.buildProcedureCall("CSWSAGIR.P_MIGRAR_RNA_PLACA", 1);

    public static final String P_SINCRONIZAR_RNA_PLACA
            = SQLTools.buildProcedureCall("CARGUES.P_SINCRONIZAR_RNA_PLACA", 1);

    public static final String P_ELIMINAR_EXPORT_RNA
            = SQLTools.buildProcedureCall("CARGUES.P_ELIMINA_EXPORT_RNA", 0);
    
    public static final String P_VALIDAR_CARGUE_CIA
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_VALIDAR_CARGUE_CIA", 3);

    public static final String P_BOLETIN_RNA_CONSOLIDADO
            = SQLTools.buildProcedureCall("CSWSAGIR.P_BOLETIN_RNA_CONSOLIDADO", 2);

    public static final String P_BOLETIN_RNC_CONSOLIDADO
            = SQLTools.buildProcedureCall("CSWSAGIR.P_BOLETIN_RNC_CONSOLIDADO", 2);
    
    public static final String P_ACT_ALL_MQX
            = SQLTools.buildProcedureCall("GMARTINEZ.P_ACT_ALL_MQX", 0);
    
    public static final String P_SINCRONIZAR_RNA
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_SINCRONIZAR_RNA", 0);
    
    public static final String POBLAMIENTO_RNC
            = SQLTools.buildProcedureCall("MIGRACIONQX.POBLAMIENTO_RNC", 1);
    
    public static final String P_CRITERIOS_RNC2
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_CRITERIOS_RNC2", 1);
    
    public static final String MIGRACIONQX_P_MIGRAR_RNC
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_MIGRAR_RNC", 1);
    
    public static final String MIGRACIONQX_P_RUNTRADI
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_RUNTRADI", 2);
    
    public static final String MIGRACIONQX_P_RUNT_MEDIDAS_CAUTELARES
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_RUNT_MEDIDAS_CAUTELARES", 2);
    
    public static final String MIGRACIONQX_P_RUNTPREN
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_RUNTPREN", 1);
    
    public static final String MIGRACIONQX_P_RUNTRADI_MT
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_RUNTRADI_MT", 1);
    
    public static String MIGRACIONQX_POBLAMIENTO_RNA
            = SQLTools.buildProcedureCall("MIGRACIONQX.POBLAMIENTO_RNA", 1);
    
    public static String MIGRACIONQX_P_TTAMCAUT
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_TTAMCAUT", 1);
    
    public static String MIGRACIONQX_P_APLICACION_CRITERIOS_RNA
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_APLICACION_CRITERIOS_RNA", 1);
    
    public static String MIGRACIONQX_P_MIGRAR_RNA
            = SQLTools.buildProcedureCall("MIGRACIONQX.P_MIGRAR_RNA", 2);
    
    public static String CARGUES_P_ACT_VEHI_PROD_CAMBIO_RES
            = SQLTools.buildProcedureCall("CARGUES.P_ACT_VEHI_PROD_CAMBIO_RES", 1);
    
    public static String CARGUES_P_ACT_PROPIET_PROD_CAMBIO_RES
            = SQLTools.buildProcedureCall("CARGUES.P_ACT_PROPIET_PROD_CAMBIO_RES", 1);
    
    public static String CARGUES_P_SINCRONIZAR_CARGUE_PROD
            = SQLTools.buildProcedureCall("CARGUES.P_SINCRONIZAR_CARGUE_PROD", 1);

    /**
     * Package
     */
    public static final String PK_MG_RA_AUTOMOTOR
            = SQLTools.buildPPackageCall("CSWMIGRACION.PK_MG_RA_AUTOMOTOR", "PR_MG_ACTUALIZAR_PLACA_2_4_RNA", 3);

    /*
     *Funciones
     */
    public static final String FUNCTION_VALIDA_FORMATO_PLACA
            = SQLTools.buildFunctionCall("MIGRACIONQX.F_VALIDAR_FORMATO_PLACA", 1);

    public static final String FUNCTION_CAMBIO_PLACA_FORMATO_NUEVO
            = SQLTools.buildFunctionCall("MIGRACIONQX.F_CAMBIAR_FORMATO_PLACA", 2);

}
