package co.com.runt.sagir.servicios;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.dto.InfoUsuarioDTO;

/**
 * La Class ManejadroServicio.
 *
 * @author Hmoreno
 */
public abstract class ManejadorServicio {

    /**
     * Atributo request.
     */
    @Context
    protected HttpServletRequest httpServletRequest;

    /**
     * Atributo request.
     */
    @Context
    protected Request request;

    /**
     * Metodo encargado de retornar el usuario en sesion.
     *
     * @return InfoUsuarioDTO
     */
    protected InfoUsuarioDTO getUsuario() {
        return (InfoUsuarioDTO) httpServletRequest.getSession().getAttribute(Constantes.SESSION_USUARIO);
    }

    /**
     * Metodo encargado de retornar la ip del usuario.
     *
     * @return String
     */
    protected String getIpCliente() {
        if (httpServletRequest.getHeader(Constantes.IP_HEADER) != null) {
            return httpServletRequest.getHeader(Constantes.IP_HEADER);
        } else {
            return httpServletRequest.getRemoteAddr();
        }
    }

}
