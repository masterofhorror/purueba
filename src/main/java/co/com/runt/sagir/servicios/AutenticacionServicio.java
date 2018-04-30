package co.com.runt.sagir.servicios;

import co.com.runt.autenticacionrunt.LoginDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.AutenticacionLogica;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * La Class AutenticacionServicio.
 *
 * @author Hmoreno
 */
@Path("/autenticacion")
@Stateless
public class AutenticacionServicio extends ManejadorServicio {

    /**
     * La constante LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(AutenticacionServicio.class.getSimpleName());

    /**
     * Atributo autenticacion logica.
     */
    @EJB
    private AutenticacionLogica autenticacionLogica;

    /**
     * Login.
     *
     * @param request el request
     * @param loginDTO el login DTO
     * @return retorna mensaje DTO
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MensajeDTO login(@Context HttpServletRequest request, LoginDTO loginDTO) {
        MensajeDTO respuesta = autenticacionLogica.autenticarUsuario(loginDTO);

        if (respuesta.getCodmensaje().equals(Mensajes.OK)) {
            request.getSession().setAttribute(Constantes.SESSION_USUARIO, respuesta.getObject());
        }

        return respuesta;
    }

    /**
     * Logout.
     *
     * @param request el request
     */
    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public void logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (!session.isNew()) {
            session.invalidate();
            session = request.getSession();

            if (session != null) {
                LOGGER.info("Cierre de Session ... ok");
            }
        }
    }

}
