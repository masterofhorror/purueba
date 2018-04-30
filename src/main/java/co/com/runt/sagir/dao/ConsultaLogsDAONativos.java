package co.com.runt.sagir.dao;


import static co.com.runt.sagir.dao.BaseDAO.getConnection;
import co.com.runt.sagir.dto.ConsultaLogsInDTO;
import co.com.runt.sagir.dto.ConsultaLogsOutDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Dospina
 */
public class ConsultaLogsDAONativos extends BaseDAO {

    private static final String CONSTANTE_GRUPO = "CONSULTAS";

    private static final String CONSTANTE_NOMBRE = "MAX_REGISTROS_CONSULTA_LOGS";

    private static final String SELECT_INICIO = "SELECT * FROM( ";

    private static final String SELECT_FIN = ""
            + " order by C.CONSULTA_FECHA desc )\n"
            + " WHERE ROWNUM <= ? ";

    private static final String SELECT_LOGS = ""
            + "select \n"
            + "C.CONSULTA_FECHA AS FECHA,\n"
            + "TC.TIPOCONSULTA_NOMBRE AS CONSULTA,\n"
            + "AP.APLICACION_NOMBRE AS APLICACION,\n"
            + "C.CONSULTA_ENTRADA AS ENTRADA_CONSULTA,\n"
            + "C.CONSULTA_AUTORIDAD AS AUTORIDAD,\n"
            + "C.CONSULTA_USUARIO AS USUARIO,\n"
            + "C.CONSULTA_IP AS IP\n"
            + "from CSWCONSULTAS.LOG_CONSULTA C\n"
            + "INNER JOIN CSWCONSULTAS.PA_TIPOCONSULTA TC ON TC.TIPOCONSULTA_CODIGO = C.CONSULTA_TIPOCONSULTA_CODIGO\n"
            + "INNER JOIN CSWCONSULTAS.PA_APLICACION AP ON AP.APLICACION_CODIGO = C.CONSULTA_APLICACION_CODIGO \n"
            + "WHERE C.CONSULTA_FECHA BETWEEN ? AND TO_DATE(?, 'DD/MM/YYYY Hh24:Mi:Ss') \n";

    private static final String FILTRO_AUTORIDAD = " AND C.CONSULTA_AUTORIDAD = ? ";

    private static final String FILTRO_USUARIO = " AND C.CONSULTA_USUARIO = ? ";

    private static final String FILTRO_IP = " AND C.CONSULTA_IP = ? ";

    private static final String FILTRO_COD_APLICACION = " AND C.CONSULTA_APLICACION_CODIGO = ? ";

    private static final String FILTRO_COD_TIPOCONSULTA = " AND C.CONSULTA_TIPOCONSULTA_CODIGO = ? ";

    private static final String HORA_FIN = " 23:59:59";

    private static final String SELECT_CONSTANTE = "SELECT "
            + "  CONSTANTE_VALOR               AS VALOR  \n"
            + "  from RUNTPROD.COM_CONSTANTE             \n"
            + "  ";
    /**
     * Condicion Where.
     */
    private static final String WHERE_CONSTANTE = " WHERE CONSTANTE_GRUPO = ? "
            + "AND  CONSTANTE_NOMBRE = ? ";

    public static String getValorConstante(final String grupo, final String constante) {

        try (Connection con = getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_CONSTANTE + WHERE_CONSTANTE)) {
                preparedStatement.setString(1, grupo);
                preparedStatement.setString(2, constante);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        return rs.getString("VALOR");
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(ConsultaLogsDAONativos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static String generarConsultaSql(final ConsultaLogsInDTO consulta) {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_INICIO + SELECT_LOGS);
        if (consulta.getAutoridad() != null) {
            sql.append(FILTRO_AUTORIDAD);
        }
        if (consulta.getUsuario() != null) {
            sql.append(FILTRO_USUARIO);
        }
        if (consulta.getIp() != null) {
            sql.append(FILTRO_IP);
        }
        if (consulta.getAplicacion() != null && consulta.getAplicacion().getAplicacionCodigo() != null) {
            sql.append(FILTRO_COD_APLICACION);
        }
        if (consulta.getTipoconsulta() != null && consulta.getTipoconsulta().getTipoconsultaCodigo() != null) {
            sql.append(FILTRO_COD_TIPOCONSULTA);
        }
        sql.append(SELECT_FIN);
        return sql.toString();
    }

    public static List<ConsultaLogsOutDTO> consultarLog(final ConsultaLogsInDTO consulta) throws SQLException, NamingException {
        try (Connection con = getConnection()) {
            String sql = generarConsultaSql(consulta);
            int paramNum = 0;
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, consulta.getFechaInicio());
                preparedStatement.setString(2, consulta.getFechaFin() + HORA_FIN);
                paramNum = 2;
                if (consulta.getAutoridad() != null) {
                    paramNum++;
                    int num = paramNum;
                    preparedStatement.setLong(num, consulta.getAutoridad());

                }
                if (consulta.getUsuario() != null) {
                    paramNum++;
                    int num = paramNum;
                    preparedStatement.setString(num, consulta.getUsuario());
                }
                if (consulta.getIp() != null) {
                    paramNum++;
                    int num = paramNum;
                    preparedStatement.setString(num, consulta.getIp());
                }
                if (consulta.getAplicacion() != null && consulta.getAplicacion().getAplicacionCodigo() != null) {
                    paramNum++;
                    int num = paramNum;
                    preparedStatement.setString(num, consulta.getAplicacion().getAplicacionCodigo());
                }
                if (consulta.getTipoconsulta() != null && consulta.getTipoconsulta().getTipoconsultaCodigo() != null) {
                    paramNum++;
                    int num = paramNum;
                    preparedStatement.setString(num, consulta.getTipoconsulta().getTipoconsultaCodigo());
                }
                
                paramNum++;
                int num = paramNum;
                preparedStatement.setString(num, getValorConstante(CONSTANTE_GRUPO, CONSTANTE_NOMBRE));
                
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    return mapListLog(rs);
                }
            }
        }
    }

    private static List<ConsultaLogsOutDTO> mapListLog(final ResultSet rs) throws SQLException {
        final List<ConsultaLogsOutDTO> resp = new LinkedList<>();
        while (rs.next()) {
            resp.add(mapLog(rs));
        }
        return resp;
    }

    private static ConsultaLogsOutDTO mapLog(final ResultSet rs) throws SQLException {
        final ConsultaLogsOutDTO item = new ConsultaLogsOutDTO();
        item.setFecha(rs.getString("FECHA"));
        item.setTipoconsulta(rs.getString("CONSULTA"));
        item.setAplicacion(rs.getString("APLICACION"));
        item.setEntrada(jsonToString(rs.getString("ENTRADA_CONSULTA")));
        item.setAutoridad(rs.getLong("AUTORIDAD"));
        item.setUsuario(rs.getString("USUARIO"));
        item.setIp(rs.getString("IP"));

        return item;
    }

    private static String jsonToString(String json) {
        json = json.replaceAll("\\,", "\n")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "");
        return json;
    }

}
