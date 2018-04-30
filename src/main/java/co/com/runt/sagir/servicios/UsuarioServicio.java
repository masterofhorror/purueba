/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.autenticacionrunt.LoginDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.UsuarioDTO;
import co.com.runt.sagir.logica.UsuarioLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Hmoreno
 */
@Path("/usuario")
@Stateless
public class UsuarioServicio extends ManejadorServicio {

    @EJB
    private UsuarioLogica usuarioLogica;

    @GET
    public UsuarioDTO consultarUsuario() {
        return usuarioLogica.consultarUsuario(getUsuario());
    }

    @POST
    @Path("/validarClave")
    public boolean validarClaveActual(LoginDTO login) {
        return usuarioLogica.validarClaveActual(login);
    }

    /**
     *
     * @param usuario
     * @return
     */
    @POST
    @Path("/actualizar")
    public MensajeDTO actualizarUsuario(final UsuarioDTO usuario) {
        return usuarioLogica.actualizarUsuario(usuario);
    }

    /**
     *
     * @param usuario
     * @return
     */
    @POST
    @Path("/actualizarContrasena")
    public MensajeDTO actualizarContrasenia(final UsuarioDTO usuario) {
        return usuarioLogica.actualizarContrasenia(usuario);
    }

}
