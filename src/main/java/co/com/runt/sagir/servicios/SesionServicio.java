package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MenuGrupoDTO;
import co.com.runt.sagir.logica.MenuLogica;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Hmoreno
 */
@Path("/sesion")
@Stateless
public class SesionServicio extends ManejadorServicio {

    /**
     * Atributo menu logica.
     */
    @EJB
    private MenuLogica menuLogica;

    /**
     * Servicio encargado de validar si existe session de usuario.
     *
     * @return boolean
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean estaUsuarioEnSesion() {
        return getUsuario() != null;
    }

    /**
     * Servicio encargado de retornar la informacion del usuario en sesion.
     *
     * @return InfoUsuarioDTO
     */
    @GET
    @Path("usuario")
    @Produces(MediaType.APPLICATION_JSON)
    public InfoUsuarioDTO getUsuarioSesion() {
        return getUsuario();
    }

    /**
     * Servicio encargado de retornar las opciones de menu habilitadas para el
     * usuario.
     *
     * @return List<MenuGrupoDTO>
     */
    @GET
    @Path("cargueMenu")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuGrupoDTO> getOpcionesMenu() {
        return menuLogica.cargarOpcionesMenu(getUsuario());
    }

}
