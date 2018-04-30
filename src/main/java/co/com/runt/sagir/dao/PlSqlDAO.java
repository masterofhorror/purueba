/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.ProcedimientosAlmacenados;
import co.com.runt.sagir.common.SQLTools;
import co.com.runt.sagir.dto.ConsultaDianDTO;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PlSqlDAO {

    @Resource(name = "jdbc/SAGIR")

    private DataSource dataSource;

    private static final Logger LOGGER = Logger.getLogger(CambioPropietarioAutomotorDAO.class.getSimpleName());

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void poblamientoRNAProp(int idCarga) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_POBLAMIENTO_RNA_PROP);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setInt(++pos, idCarga);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void eliminaFinalRnaMigra(String placa) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_ELIMINAR_PLACA_FINAL_RNA);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setString(++pos, placa);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void eliminaMigraRna(String placa) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_ELIMINAR_PLACA_MIG_RNA);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setString(++pos, placa);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void aplicaCriterios(int idCarga, Long idSecretaria) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_APLICA_CRITERIOS_RNA_PROP);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setInt(++pos, idCarga);
            cs.setLong(++pos, idSecretaria);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable  
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void verificaPropietario(int idCarga) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_VERIFICA_PROPIETARIO_PROD);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setInt(++pos, idCarga);
            // Registramos los parametro de salida OUT
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void cargaPropietario(int idCarga) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_CARGAR_PROPIETARIO_PROD);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setInt(++pos, idCarga);
            // Registramos los parametro de salida OUT
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String prMgActualizarPlaca24RNA(final Long nroRegistroVehiculo, final String nroTicket) {
        Connection con = null;
        String resultado = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.PK_MG_RA_AUTOMOTOR);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setLong(++pos, nroRegistroVehiculo);
            cs.setString(++pos, nroTicket);
            // Registramos los parametro de salida OUT
            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable   
            if (cs.getObject(3) == null) {
                resultado = Constantes.VALIDADOR;
            } else {
                resultado = cs.getObject(3).toString();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
        return resultado;
    }

    /**
     * Metodo que invoca la función que valida que el formato de placa sea
     * valido
     *
     * @param placa
     * @return
     */
    public Integer functionValidaFormatoPlaca(final String placa) {
        Integer resultado = 0;
        CallableStatement proc;
        try {
            proc = dataSource.getConnection()
                    .prepareCall(ProcedimientosAlmacenados.FUNCTION_VALIDA_FORMATO_PLACA);
            int pos = 0;
            proc.registerOutParameter(++pos, OracleTypes.INTEGER);
            proc.setString(++pos, placa);

            proc.execute();
            resultado = proc.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return resultado;
    }

    /**
     * Metodo que invoca la función que realiza el cambio sobre la placa de
     * formato antigüo a nuevo
     *
     * @param placa
     * @param constanteFormato
     * @return
     */
    public String functionCambioPlaca(final String placa, final Integer constanteFormato) {
        String resultado = null;
        CallableStatement proc;
        try {
            proc = dataSource.getConnection()
                    .prepareCall(ProcedimientosAlmacenados.FUNCTION_CAMBIO_PLACA_FORMATO_NUEVO);
            int pos = 0;
            proc.registerOutParameter(++pos, OracleTypes.VARCHAR);
            proc.setString(++pos, placa);
            proc.setInt(++pos, constanteFormato);

            proc.execute();
            resultado = proc.getString(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return resultado;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pActualizarEstadoRnaPlaca(String placa, int idCarga) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_ACTUALIZAR_ESTADO_RNA_PLACA);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setString(++pos, placa);
            cs.setInt(++pos, idCarga);
            // Registramos los parametro de salida OUT
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void actualizaResidenciaPlaca(int idCarga, String placa) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_ACT_VEHI_CAMBIO_RES_PLACA);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setInt(++pos, idCarga);
            cs.setString(++pos, placa);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable  
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void actPropietarCambResPlaca(int idCarga, String placa) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_ACT_PROPIET_CAMBIO_RES_PLACA);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setInt(++pos, idCarga);
            cs.setString(++pos, placa);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable  
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pConsultarRangosPlacas(String placaIni, String placaFin) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_RANGOS_PLACAS_CONSULTA);
            int pos = 0;
            cs.setString(++pos, placaIni);
            cs.setString(++pos, placaFin);
            cs.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado P_VALIDAR_CARGUE_REPOTENCIA
     *
     * @param idCarga
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesarRepotenciacion(Integer idCarga) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_VALIDAR_CARGUE_REPOTENCIADOS);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setInt(++pos, idCarga);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ConsultaDianDTO pConsultaDeclaImpor(String numDecla) {

        Connection con;
        CallableStatement cs;
        ConsultaDianDTO listaDianDTO = new ConsultaDianDTO();

        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_CONSULTAR_DEC_IMP_DIAN);
            int pos = 0;
            // Cargamos los parametros de entrada IN
            cs.setString(++pos, numDecla);

            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);
            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);
            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);
            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);
            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);

            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable 

            listaDianDTO.setNumLevante(cs.getObject(2) == null ? null : cs.getObject(2).toString());
            listaDianDTO.setFecLevante(cs.getObject(3) == null ? null : cs.getObject(3).toString());
            listaDianDTO.setNumIdentificacionImpo(cs.getObject(4) == null ? null : cs.getObject(4).toString());
            listaDianDTO.setTipIdentificacionImpo(cs.getObject(5) == null ? null : cs.getObject(5).toString());
            listaDianDTO.setNumSubpartida(cs.getObject(6) == null ? null : cs.getObject(6).toString());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }

        return listaDianDTO;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String carguePuntual(String numPlaca) {
        Connection con;
        String resultado = null;
        CallableStatement cs;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_CARGUE_PUNTUAL);
            int pos = 0;

            cs.setString(++pos, numPlaca);

            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);

            cs.execute();

            resultado = cs.getObject(2) == null ? null : cs.getObject(2).toString();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return resultado;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String marcarCarguePuntual(String numPlaca) {
        Connection con;
        String resultado = null;
        CallableStatement cs;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_MARCAR_CARGUE_PUNTUAL);
            int pos = 0;

            cs.setString(++pos, numPlaca);

            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);

            cs.execute();

            resultado = cs.getObject(2) == null ? null : cs.getObject(2).toString();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }

        return resultado;
    }

    /**
     * Metodo que invoca el procedimiento almacenado
     * CARGUES.P_SINCRONIZAR_MASIVO_RNA_PROD
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void sincronizarMasivoRna() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_SINCRONIZAR_MASIVO_RNA_PROD);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado
     * CARGUES.P_SINCRONIZAR_MASIVO_RNC_PROD
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void sincronizarMasivoRnc() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_SINCRONIZAR_MASIVO_RNC_PROD);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado
     * CARGUES.P_VALIDAR_PERSONA_RNC
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void validaPersonaRnc() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection(); // Conexión a la unidad de persistencia
            cs = con.prepareCall(ProcedimientosAlmacenados.P_VALIDAR_PERSONA_RNC);
            // Ejecutamos
            cs.execute();
            // Se guarda el resultado del PL en una variable      

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void migrarRna() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_MIGRAR_RNA);

            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void migrarRnaPlaca(BigDecimal regVehic) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_MIGRAR_RNA_PLACA);
            int pos = 0;

            cs.setBigDecimal(++pos, regVehic);
            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void sincronizarRnaPlaca(String numPlaca) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_SINCRONIZAR_RNA_PLACA);

            int pos = 0;

            cs.setString(++pos, numPlaca);

            cs.execute();

        } catch (SQLException e) {
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void eliminarExportRna() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_ELIMINAR_EXPORT_RNA);

            cs.execute();

        } catch (SQLException e) {
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado P_BOLETIN_RNA_CONSOLIDADO
     * el cual se encarga de cargar la tabla para los boletines de RNA
     *
     * @param codCarga
     * @param idSecretaria
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void boletinRNA(final String codCarga, final String idSecretaria) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_BOLETIN_RNA_CONSOLIDADO);

            int pos = 0;

            cs.setString(++pos, codCarga);
            cs.setString(++pos, idSecretaria);
            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que se encarga de invocar el procedimiento almacenado
     * P_BOLETIN_RNC_CONSOLIDADO el cual se encarga de poblar la tabla para los
     * boletines de RNC
     *
     * @param codCarga
     * @param idSecretaria
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void boletinRNC(final String codCarga, final String idSecretaria) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_BOLETIN_RNC_CONSOLIDADO);

            int pos = 0;

            cs.setString(++pos, codCarga);
            cs.setString(++pos, idSecretaria);
            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado GMARTINEZ.P_ACT_ALL_MQX
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pExport() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_ACT_ALL_MQX);

            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado
     * MIGRACIONQX.P_SINCRONIZAR_RNA
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void sincronizarRNA() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_SINCRONIZAR_RNA);

            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado GMARTINEZ.P_MIGRAR_RNA
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pMigrarRNA() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_MIGRAR_RNA);
            
            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado GMARTINEZ.P_MIGRAR_RNA
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pMigrarRNC() {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_MIGRAR_RNC);

            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String pValidarCargueCia(String codCarga, String nomArc) {
        Connection con;
        CallableStatement cs;
        String resultado = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_VALIDAR_CARGUE_CIA);
            int pos = 0;

            cs.setString(++pos, codCarga);
            cs.registerOutParameter(++pos, OracleTypes.VARCHAR);
            cs.setString(++pos, nomArc);

            cs.execute();

            resultado = cs.getObject(2) == null ? null : cs.getObject(2).toString();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return resultado;
    }

    /**
     * Metodo que invoca el procedimiento almacenado MIGRACIONQX.POBLAMIENTO_RNC
     *
     * @param codCarga
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void poblamientoRNC(final String codCarga) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.POBLAMIENTO_RNC);

            int pos = 0;

            cs.setString(++pos, codCarga);
            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado
     * MIGRACIONQX.P_CRITERIOS_RNC2
     *
     * @param codCarga
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pCriteriosRNC2(final String codCarga) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.P_CRITERIOS_RNC2);

            int pos = 0;

            cs.setString(++pos, codCarga);
            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    /**
     * Metodo que invoca el procedimiento almacenado
     * MIGRACIONQX.MIGRACIONQX_P_MIGRAR_RNC
     *
     * @param codCarga
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void migracionQxPMigrarRNC(final String codCarga) {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_P_MIGRAR_RNC);

            int pos = 0;

            cs.setString(++pos, codCarga);
            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            SQLTools.close(rs, cs, con);
        }
    }

    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pPruntradi(Long codCarga, Long idSecretaria) {
        Connection con;
        CallableStatement cs;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_P_RUNTRADI);
            int pos = 0;

            cs.setLong(++pos, codCarga);
            cs.setLong(++pos, idSecretaria);

            cs.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pRuntMedidasCautelares(Long codCarga, Long idSecretaria) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_P_RUNT_MEDIDAS_CAUTELARES);
            int pos = 0;

            cs.setLong(++pos, codCarga);
            cs.setLong(++pos, idSecretaria);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pRuntpren(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_P_RUNTPREN);
            int pos = 0;

            cs.setLong(++pos, codCarga);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pRuntRADImT(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_P_RUNTRADI_MT);
            int pos = 0;

            cs.setLong(++pos, codCarga);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void poblamientoRna(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_POBLAMIENTO_RNA);
            int pos = 0;

            cs.setLong(++pos, codCarga);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pTtamcaut(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_P_TTAMCAUT);
            int pos = 0;

            cs.setLong(++pos, codCarga);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pAplicacionCriteriosRna(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_P_APLICACION_CRITERIOS_RNA);
            int pos = 0;

            cs.setLong(++pos, codCarga);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pMigrarRnaMigracionqx(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.MIGRACIONQX_P_MIGRAR_RNA);
            int pos = 0;

            cs.setLong(++pos, codCarga);
            cs.setString(++pos, null);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pActVehiProdCambioRes(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.CARGUES_P_ACT_VEHI_PROD_CAMBIO_RES);
            int pos = 0;

            cs.setLong(++pos, codCarga);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pActPropietProdCambioRes(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.CARGUES_P_ACT_PROPIET_PROD_CAMBIO_RES);
            int pos = 0;

            cs.setLong(++pos, codCarga);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void pSincronizarCargueProd(Long codCarga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(ProcedimientosAlmacenados.CARGUES_P_SINCRONIZAR_CARGUE_PROD);
            int pos = 0;

            cs.setLong(++pos, codCarga);

            cs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
