/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.autenticacionrunt.MenuDTO;
import co.com.runt.autenticacionrunt.MenuUsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class MenuDAO {

    private static final Logger LOGGER = Logger.getLogger(MenuDAO.class.getSimpleName());

    private final String SQL_CONSULTA_MENU = "SELECT TF.TIPOFUNC_CODIGO AS FUNCIONALIDAD, TF.TIPOFUNC_NOMBRE AS NOMBRE, MF.MENUFUNC_URL AS URL\n"
            + "  FROM CSWRUNT.PA_APLICACION AP\n"
            + "  INNER JOIN CSWRUNT.PA_TIPOFUNCIONALIDAD TF ON TF.TIPOFUNC_APLICACION_CODIGO = AP.APLICACION_CODIGO\n"
            + "  INNER JOIN CSWRUNT.PA_MENUFUNC MF ON MF.MENUFUNC_TIPOFUNC_CODIGO = TF.TIPOFUNC_CODIGO\n"
            + "WHERE AP.APLICACION_CODIGO = ?";

    public MenuUsuarioDTO getMenu(final String aplicacion) {
        MenuUsuarioDTO resultado = new MenuUsuarioDTO();

        try (Connection con = BaseDAO.getConnection()) {

            try (PreparedStatement preparedStatement = con.prepareStatement(SQL_CONSULTA_MENU)) {
                preparedStatement.setString(1, aplicacion);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    resultado.setMenus(mapearDTO(rs));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return resultado;
    }

    private String getGrupos(List<String> grupos) {
        String resultado = "";

        for (String grupo : grupos) {
            resultado += "'" + grupo + "', ";
        }

        if (resultado.length() > 0) {
            resultado = resultado.substring(0, resultado.length() - 2);
        }

        return resultado;
    }

    private List<MenuDTO> mapearDTO(final ResultSet rs) throws SQLException {
        final List<MenuDTO> respuesta = new ArrayList<>();

        MenuDTO menuDTO;
        while (rs.next()) {
            menuDTO = new MenuDTO();
            menuDTO.setFuncionalidad(rs.getString("FUNCIONALIDAD"));
            menuDTO.setNombre(rs.getString("NOMBRE"));
            menuDTO.setUrl(rs.getString("URL"));

            respuesta.add(menuDTO);
        }
        return respuesta;
    }

}
