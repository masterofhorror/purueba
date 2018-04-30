/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.autenticacionrunt.MenuDTO;
import co.com.runt.autenticacionrunt.MenuUsuarioDTO;
import co.com.runt.clienteautenticacion.ClienteAutenticacion;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.dao.MenuDAO;
import co.com.runt.sagir.dao.MenuGrupoDAO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MenuGrupoDTO;
import co.com.runt.sagir.entities.MenuGrupo;
import co.com.runt.sagir.entities.TipoFuncionalidad;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class MenuLogica {

    private static final Logger LOGGER = Logger.getLogger(MenuLogica.class.getSimpleName());

    @EJB
    private MenuGrupoDAO menuGrupoDAO;

    @EJB
    private MenuDAO menuDAO;

    public List<MenuGrupoDTO> cargarOpcionesMenu(final InfoUsuarioDTO usuario) {
        List<MenuGrupoDTO> respuesta = new ArrayList<>();
        try {
//            ClienteAutenticacion clienteAutenticacion = new ClienteAutenticacion(Constantes.URL_SERVICIO_AUTENTICACION);

            //FIXME 
            //Ambiente produccion
            //MenuUsuarioDTO menusUsuario = clienteAutenticacion.getMenuPorUsuario(usuario.getLogin(), Constantes.APLICACION_SAGIR);
            //Ambiente desarrollo 
            MenuUsuarioDTO menusUsuario = menuDAO.getMenu(Constantes.APLICACION_SAGIR);

            MenuGrupoDTO menuGrupoDTO;

            List<MenuGrupo> listaMenuGrupos = menuGrupoDAO.buscarMenusGrupo();
            for (MenuGrupo menuGrupo : listaMenuGrupos) {
                menuGrupoDTO = TransformacionDozer.transformar(menuGrupo, MenuGrupoDTO.class);

                if (menuGrupo.getMenuGrupoId() != 1) {

                    if (menusUsuario.getMenus() != null) {

                        for (TipoFuncionalidad tipoFuncionalidad : menuGrupo.getFuncionalidades()) {
                            for (MenuDTO menuDTO : menusUsuario.getMenus()) {
                                if (tipoFuncionalidad.getTipofuncCodigo().equals(menuDTO.getFuncionalidad())) {
                                    menuDTO.setUrl("#" + menuDTO.getUrl().split("#")[1]);

                                    menuGrupoDTO.addOpcionMenu(menuDTO);
                                }
                            }
                        }
                    }
                } else {
                    menuGrupoDTO.setOpcionesMenu(getMenuPublico());
                }

                if (menuGrupoDTO.getOpcionesMenu() != null && !menuGrupoDTO.getOpcionesMenu().isEmpty()) {
                    respuesta.add(menuGrupoDTO);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generando el menú del usuario", e);
        }
        return respuesta;
    }

    private List<MenuDTO> getMenuPublico() {
        List<MenuDTO> respuesta = new ArrayList<>();

        MenuDTO menuHome = new MenuDTO();
        menuHome.setFuncionalidad(null);
        menuHome.setNombre("Inicio");
        menuHome.setUrl("#/home");

        respuesta.add(menuHome);

        MenuDTO menuModificarUsuario = new MenuDTO();
        menuModificarUsuario.setFuncionalidad(null);
        menuModificarUsuario.setNombre("Mi Perfil");
        menuModificarUsuario.setUrl("#/modificarPerfil");

        respuesta.add(menuModificarUsuario);

        return respuesta;
    }

}
