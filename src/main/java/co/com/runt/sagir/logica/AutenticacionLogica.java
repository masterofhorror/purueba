package co.com.runt.sagir.logica;

import co.com.runt.autenticacionrunt.LoginDTO;
import co.com.runt.autenticacionrunt.RespuestaAutenticacionDTO;
import co.com.runt.clienteautenticacion.ClienteAutenticacion;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ComConstantes;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class AutenticacionLogica {

    private static final Logger LOGGER = Logger.getLogger(AutenticacionLogica.class.getSimpleName());

    @EJB
    private ConstanteDAO constanteDAO;

    public MensajeDTO autenticarUsuario(final LoginDTO loginDTO) {
        MensajeDTO respuesta = new MensajeDTO();
        try {
//            ComConstantes constanteUsuario = constanteDAO.consultarPorGrupoYNombre(Constantes.CONSTANTE_GRUPO_COMMON, Constantes.CONSTANTE_NOMBRE_ADMIN_CIUDADANO);
//            ComConstantes constantePassword = constanteDAO.consultarPorGrupoYNombre(Constantes.CONSTANTE_GRUPO_COMMON, Constantes.CONSTANTE_NOMBRE_CONTRASENA_CIUDADANO);

//            ClienteOID clienteOID = new ClienteOID(constanteUsuario.getConstanteValor(), constantePassword.getConstanteValor(), ClienteOID.TipoUsuario.ADMIN);

            ClienteAutenticacion clienteAutenticacion = new ClienteAutenticacion(Constantes.URL_SERVICIO_AUTENTICACION);

            RespuestaAutenticacionDTO respuestaAutenticacion = null;
            try {
                respuestaAutenticacion = clienteAutenticacion.obtenerLogin(loginDTO);

            } catch (UniformInterfaceException e) {
//                clienteOID.modificarHoraFallaIngresoOID(loginDTO.getLogin(), ClienteOID.TipoUsuario.HQ);

                respuesta.setCodmensaje(Mensajes.ERROR);
                respuesta.setMensaje("Usuario o contraseña incorrectos.");
            }

            if (respuestaAutenticacion != null) {
                InfoUsuarioDTO usuario = new InfoUsuarioDTO();

                usuario.setIdOrganizacion(respuestaAutenticacion.getIdOrganizacion());
                usuario.setLogin(respuestaAutenticacion.getLogin());
                usuario.setNombres(respuestaAutenticacion.getNombreCompleto());
                usuario.setFechaSistema(new SimpleDateFormat(Constantes.FORMATO_FECHA, Locale.getDefault()).format(new Date()));

//                clienteOID.modificarHoraIngresoOID(loginDTO.getLogin(), ClienteOID.TipoUsuario.HQ);

                respuesta.setCodmensaje(Mensajes.OK);
                respuesta.setObject(usuario);
            }

        } catch (Exception e) {
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje("Error validando la autenticación. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error validando autenticacion {0}", e);
        }
        return respuesta;
    }

}
