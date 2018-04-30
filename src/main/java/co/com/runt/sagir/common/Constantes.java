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
public class Constantes {

    /**
     * La constante SESSION_USUARIO.
     */
    public static final String SESSION_USUARIO = "SESSION.USUARIO";

    /**
     * La constante IP_HEADER.
     */
    public static final String IP_HEADER = "X-Forwarded-For";

    /**
     * La constante FORMATO_FECHA.
     */
    public static final String FORMATO_FECHA = "dd/MM/yyyy";

    /**
     * La constante CONSTANTE_GRUPO_COMMON.
     */
    public static final String CONSTANTE_GRUPO_COMMON = "COMMON";

    /**
     * La constante CONSTANTE_NOMBRE_ADMIN_CIUDADANO.
     */
    public static final String CONSTANTE_NOMBRE_ADMIN_CIUDADANO = "ADMIN_CIUDADANO";

    /**
     * La constante CONSTANTE_NOMBRE_PASSWORD_CIUDADANO.
     */
    public static final String CONSTANTE_NOMBRE_CONTRASENA_CIUDADANO = "PASSWORD_CIUDADANO";

    /**
     * La constante CONSTANTE_EMAIL_MODIFICACION_USUARIO.
     */
    public static final String CONSTANTE_EMAIL_MODIFICACION_USUARIO = "EMAIL_MODIFICACION_USUARIO";

    /**
     * La constante URL_SERVICIO_AUTENTICACION.
     */
    public static final String URL_SERVICIO_AUTENTICACION = "http://weblogicInterno/servicios/AutenticacionRunt/webresources/login";

    /**
     * La constante URL_SERVICIO_CORREO.
     */
    public static final String URL_SERVICIO_CORREO = "http://weblogicInterno/EnvioCorreo/webresources/envioCorreo";

    /**
     * La constante APLICACION_SAGIR.
     */
    public static final String APLICACION_SAGIR = "SAGIR";

    public enum ESTADOS_MESAJE {
        OK, ERROR
    }

    public static final String APLICATION_CONSULTA = "SAGIRAPP";
    /**
     * Constantes tipo consulta RNA
     */
    public static final String TIPO_CONSULTA_RNA_MIGRA = "MIGRARNA";
    public static final String TIPO_CONSULTA_RNA_INTER = "INTERMEDIA";
    public static final String TIPO_CONSULTA_RNA_RECHAZO = "RECHAZORNA";
    public static final String TIPO_CONSULTA_RNA_BOLETIN = "BOLETINRNA";
    public static final String TIPO_CONSULTA_RNA_SAGIR = "FINALRNA";
    public static final String TIPO_CONSULTA_RNA_PROD = "PRODRNA";
    public static final String TIPO_CONSULTA_REPROCESAR_RNA = "REPROCRNA";
    public static final String TIPO_CONSULTA_MODIFICACIONES = "MODIFICACIONES";
    public static final String EXPRESION_PLACA = "[A-Z]{2}[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]";
    public static final String EXPRESION_PLACA_NUEVA = "/[A-Z]{3}[0-9]{3}$";
    public static final String EXPRESION_PLACA_ANTIGUA = "/[A-Z]{2}[0-9]{4}$";
    public static final String EXPRESION_TICKET = "/^INC[0-9]{12}$";
    public static final String EXPRESION_ARCHIVO_LICENCIAS = "^[0-9]{7,8}(mlice)|^[0-9]{7,8}(mlice)|^[0-9]{7,8}(MLICE)|^[0-9]{7,8}(MLICE)|\\.p7z";
    public static final String EXPRESION_ARCHIVO_RESIDENCIAS = "^[0-9]{7,8}(mresi)|^[0-9]{7,8}(mresi)|^[0-9]{7,8}(MRESI)|^[0-9]{7,8}(MRESI)|\\.p7z";
    public static final String EXPRESION_ARCHIVO_PERSONAS = "^[0-9]{7,8}(mpers)|^[0-9]{7,8}(mpers)|^[0-9]{7,8}(MPERS)|^[0-9]{7,8}(MPERS)|\\.p7z";
    public static final String EXPRESION_ARCHIVO_RESTRICCIONES = "^[0-9]{7,8}(mresl)|^[0-9]{7,8}(mresl)|^[0-9]{7,8}(MRESL)|^[0-9]{7,8}(MRESL)|\\.p7z";
    public static final String EXPRESION_ID_SECRETARIA = "^([0-9]{7,8})|([a-z]{5})$|\\.dat";
    /**
     * Constantes tipo consulta RNC
     */
    public static final String TIPO_DOCUMENTO = "Documento de identidad";
    public static final String TIPO_LICENCIA = "Licencia de conducción";

    public static final String TIPO_CONSULTA_RNC_MIGRA = "MIGRARNC";
    public static final String TIPO_CONSULTA_RNC_INTER = "INTERRNC";
    public static final String TIPO_CONSULTA_RNC_RECHAZO = "RECHAZORNC";
    public static final String TIPO_CONSULTA_RNC_SAGIR = "FINALRNC";
    public static final String RANGO_PLACA = "RANGOPLACA";
    public static final String RANGO_PLACA_TOTAL = "RANGOPLACATOTAL";
    public static final String RANGO_PLACA_RNA = "RANGOPLACARNA";
    public static final String DATOS_MIGRADOS = "DATOSMIGRADOS";
    public static final String DECLARACION_IMPORTACION = "DECLAIMPORT";
    public static final String TIPODOCEMP = "N";
    public static final String SINREGISTRO = "SIN REGISTRO";
    public static final String PERSONA_NATURAL = "PERSONA NATURAL";
    public static final String PERSONA_JURIDICA = "PERSONA JURIDICA";

    /**
     * Variables validación cambio propietario
     */
    public static final String ESTADO_VEHICULO = "ACTIVO";
    public static final String ESTADO_CANCELADO = "CANCELADO";
    public static final String ESTADO_TRASLADO = "TRASLADO";
    public static final String CONSTANTE_PATH_FILE_SYSTEM_CARGUE_ARCHIVO = "PATH_PROPIETARIOS";

    public static final String SESION_VAR_USER = "usuario";
    public static final String SESION_VAR_AUTORIDAD = "autoridad";

    public static final String ESTADO_OK = "OK";
    public static final String ESTADO_PERSONA = "ACTIVA";
    public static final String ESTADO_CARGUE = "APROBADO";
    public static final String ESTADO_PROP_ACTUA = "INACTIVO";
    public static final String ESTADO_PROP_NUEVO = "ACTIVO";
    public static final String TIPO_PROPIETARIO = "1";
    public static final String PORCPROPI = "100";
    public static final String PROSEGEST = "NO";
    public static final String MIGRADO = "M";
    public static final String UTF8 = "UTF-8";
    public static final String PLACA = "placa";

    public static final String TIPO_REGISTRO_RNA = "1";

    public static final String REGISTRO_FOLIO = "RNA998";

    public static final String TIPO_ARCHIVO_PROPIETARIO = "CAMBIO PROPIETARIO";
    public static final String TIPO_ARCHIVO_REPOTENCIACION = "REPOTENCIACION";
    public static final String TIPO_ARCHIVO_CIA = "CIA";

    public static final String ARCMIGRAIDENTIFIC = "998";

    public static final String VALOR_CONSTANTE_CANTIDAD_ARCHIVOS = "CANTIDAD ARCHIVOS PERMITIDOS DE PROPIETARIO";

    public static final String VALIDACION_USUARIO = "VALIDACION_USUARIO";

    public static final String LOG_IP_HEADER = "X-Forwarded-For";

    public static final String NODO_EJECUCION_PROCESAMIENTO_PROPIETARIO = "NODO_EJECUCION_PROCESAMIENTO_PROPIETARIO";

    public static final String HORA_PROCESAMIENTO_CARGUES_PROPIETARIO = "HORA_PROCESAMIENTO_CARGUES_PROPIETARIO";

    public static final String GRUPO_CARGUE_ARCHIVO = "SAGIR";

    public static final String VALIDADOR = "VALIDADO";

    public static final String ESTADO_PROCESO_CAMBIO_RES = "P";
    public static final String ESTADO_PROCESO_RES_PLACA = "V";

    public static final Integer TIPO_PROCESO_CAMBIO_ESTADO = 1;
    public static final Integer TIPO_PROCESO_CAMBIO_SERVICIO = 4;
    public static final Integer TIPO_PROCESO_CAMBIO_FORMATO = 3;
    public static final String ESTADO_CAMBIO_RESIDENCIA = "INCONSISTENTE";

    public static final String CAMPO_ESTADO_VEHICULO = "AUTOMOTOR_ESTAVEHIC_NOMBRE";
    public static final String CAMPO_SERVICIO_VEHICULO = "AUTOMOTOR_TIPOSERVI_IDETIPSER";
    public static final String CAMPO_PLACA_VEHICULO = "AUTOMOTOR_PLACA_NUMPLACA";
    public static final String CAMPO_RESIDENCIA_VEHICULO = "AUTOMOTOR_AUTOTRANS_IDAUTTRA";

    public static final String NODO = "weblogic";

    public static final String ESTADO_ANULADO = "ANULADO";
    public static final String MARCA_PLACA = "-REP";
    public static final String ESTADO_INACTIVO_VEHIC = "INACTIVO";
    public static final String MARCA_VEHICULO = "-4";

    //Tipos de Cargues
    public static final String CARGUE_PROPIETARIOS = "PROP";
    public static final String CARGUE_REPOTENCIACION = "REPO";
    public static final String CARGUE_CIA = "CIA";
    public static final String CARGUE_PROCESO_MIGRACION = "PROCMIGRA";

    public static final String ESTADO_PROCESARO = "PROCESADO";
    public static final String ESTADO_RECHAZADO = "RECHAZADO";
    public static final String SIN_PROCESAR = "SIN_PROCESAR";
    public static final String REGISTRADO = "REGISTRADO";

    public static final String ESTADO_REPROCESO_P = "P";

    public static final String FILE_SEPARATOR = "file.separator";

    public static final Integer TIPO_ARCHIVO_LICENCIAS = 9;
    public static final Integer TIPO_ARCHIVO_RESIDENCIAS = 5;
    public static final Integer TIPO_ARCHIVO_PERSONAS = 14;
    public static final Integer TIPO_ARCHIVO_RESTRICCIONES = 6;
    public static final Integer TIPO_REGISTRO_RNC = 2;
    public static final Integer CODIGO_ESTANDAR = 4;
    
    public static final Integer TIPO_ARCHIVO_PROPIETARIOS = 1;
    public static final Integer TIPO_ARCHIVO_TRAMITES = 2;
    public static final Integer TIPO_ARCHIVO_VEHICULOS = 3;
    public static final Integer TIPO_ARCHIVO_MEDIDAS_CAUTELARES = 12;
    public static final Integer TIPO_ARCHIVO_ADICIONAL_MEDIDAS_CAUTELARES = 112;
    public static final Integer TIPO_ARCHIVO_RADICACIONES = 15;
    public static final Integer TIPO_ARCHIVO_RADICACIONES_MT = 19;
    public static final Integer TIPO_ARCHIVO_PRENDAS = 16;
            
    public static final Integer CODIGO_ESTANDAR_RNA = 4;
    

    public static final String NUEVA_LINEA_ARCHIVO_TXT = "\r\n";
    public static final String SEPARADOR_ARCHIVO_TXT = " | ";

    public static final String CONSULTACAMBIOFORMATO = "consultaCambioFormatoAntiguoANuevo";
    public static final String TIPODOCUMENTO = "tipo documento";
    public static final String NRODOCUMENTO = "nroDocumento";
    public static final String GRUPO = "grupo";
    public static final String NOMBRE = "nombre";
    public static final String CODCARGA = "codCarga";
    public static final String IDAUTOMOTOR = "idautomotor";
    public static final String TIPOPROCESO = "tipoProceso";
    public static final String NROTICKET = "nroTicket";
    public static final String IDSECRETARIA = "idSecretaria";
    
    
    //Mensajes de error
    public static final String ERRORGUARDARARCHIVO = "Error al guardar el archivo";
    
    
    public static final String BOLETIN = "AND CG.ID_BOLETIN = ?";
    public static final String ESTADOCARGUE = "AND LIC.IND_VALIDO = 1";
    public static final String MIGRADOSQL = "AND LIC.DES_MIGRADO <> 'N'";
    public static final String ORDERBY = "ORDER BY LIC.IND_VALIDO DESC";
    
    public static final String PLACAINICIAL = "Placa Inical";
    public static final String PLACAFINAL = "Rango Final";
    
    //rutas migracion
    public static final String SEPARADOR_RUTA = "/";
    public static final String RUTA_TUTELA = "//MIGRACION_QX/GESTION/CARGUES/TUTELAS/PENDIENTES/";
    public static final String RUTA_NORMAL_RESOL_SO = "//MIGRACION_QX/GESTION/CARGUES/RNA/PENDIENTES/";
    public static final String RUTA_VEHICULOS_SEGURIDAD_ESTADO = "//MIGRACION_QX/GESTION/CARGUES/RNA_VEHICULOS_ESTADO/PENDIENTES/";
    public static final String RUTA_RNTC_VEHICULOS_CARGA = "//MIGRACION_QX/GESTION/CARGUES/RNTC_VEHICULOS_CARGA/PENDIENTES/";
    public static final String RUTA_IMPORTACION_TEMPORAL = "//MIGRACION_QX/GESTION/CARGUES/RNA_VEHICULOS_IMPORTACION_TEMPORAL/PENDIENTES/";
    public static final String RUTA_CD = "//MIGRACION_QX/GESTION/CARGUES/RNA_CD/PENDIENTES/";

    public static final String ERROR = "ERROR";
    public static final String PROCESADO = "PROCESADO";
    
    public static final String INC = "INC";
}

