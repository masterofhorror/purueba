/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.autenticacionrunt.LoginDTO;
import co.com.runt.autenticacionrunt.RespuestaAutenticacionDTO;
import co.com.runt.clienteautenticacion.ClienteAutenticacion;
import co.com.runt.clienteenviocorreo.ClienteEnvioCorreo;
import co.com.runt.clienteenviocorreo.dto.EnvioCorreoDTO;
import co.com.runt.clienteoid.ClienteOID;
import co.com.runt.clienteoid.excepciones.OIDException;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.UsuarioDTO;
import co.com.runt.sagir.entities.ComConstantes;
import co.com.runt.utilities.EmailUtil;
import co.com.runt.utilities.FechaUtil;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class UsuarioLogica {

    private static final Logger LOGGER = Logger.getLogger(UsuarioLogica.class.getSimpleName());

    @EJB
    private ConstanteDAO constanteDAO;

    public UsuarioDTO consultarUsuario(final InfoUsuarioDTO usuario) {
        UsuarioDTO respuesta = new UsuarioDTO();

        try {
            ClienteAutenticacion clienteAutenticacion = new ClienteAutenticacion(Constantes.URL_SERVICIO_AUTENTICACION);

            RespuestaAutenticacionDTO respuestaAutenticacionDTO = clienteAutenticacion.obternerInfoCompleta(usuario.getLogin());

            respuesta.setIdOrganizacion(respuestaAutenticacionDTO.getIdOrganizacion());
            respuesta.setDocumento(respuestaAutenticacionDTO.getDocumento());
            respuesta.setNombres(respuestaAutenticacionDTO.getNombres());
            respuesta.setApellidos(respuestaAutenticacionDTO.getApellidos());
            respuesta.setDireccion(respuestaAutenticacionDTO.getDireccion());
            respuesta.setTelefono(respuestaAutenticacionDTO.getTelefono());
            respuesta.setGenero(respuestaAutenticacionDTO.getGenero());
            respuesta.setFechaNacimiento(respuestaAutenticacionDTO.getFechaNacimiento());
            respuesta.setCorreos(respuestaAutenticacionDTO.getCorreos());
            respuesta.setTipoDocumento("C");

        } catch (UniformInterfaceException e) {
            LOGGER.log(Level.SEVERE, "Error consultando usuario {0}", e);
        }
        return respuesta;
    }

    public boolean validarClaveActual(final LoginDTO login) {
        try {
            ClienteAutenticacion cliente = new ClienteAutenticacion(Constantes.URL_SERVICIO_AUTENTICACION);
            cliente.obtenerLogin(login);
            return true;
        } catch (UniformInterfaceException e) {
            LOGGER.log(Level.SEVERE, "Error validando autenticacion {0}", e);
            return false;
        }
    }

    public MensajeDTO actualizarUsuario(final UsuarioDTO usuario) {
        MensajeDTO respuesta = new MensajeDTO();
        respuesta.setCodmensaje(Mensajes.ERROR);

        Date fechaActual = new Date();

        try {
            ComConstantes constanteUsuario = constanteDAO.consultarPorGrupoYNombre(Constantes.CONSTANTE_GRUPO_COMMON, Constantes.CONSTANTE_NOMBRE_ADMIN_CIUDADANO);
            ComConstantes constantePassword = constanteDAO.consultarPorGrupoYNombre(Constantes.CONSTANTE_GRUPO_COMMON, Constantes.CONSTANTE_NOMBRE_CONTRASENA_CIUDADANO);

            ClienteOID clienteOID = new ClienteOID(constanteUsuario.getConstanteValor(), constantePassword.getConstanteValor(), ClienteOID.TipoUsuario.ADMIN);

            boolean resultado = clienteOID.actualizarUsuario("",
                    usuario.getDocumento(),
                    usuario.getApellidos(),
                    usuario.getNombres(),
                    usuario.getDireccion(),
                    usuario.getTelefono(),
                    usuario.getGenero(),
                    usuario.getFechaNacimiento(),
                    usuario.getCorreos(),
                    ClienteOID.TipoUsuario.HQ);

            if (resultado) {
                respuesta.setCodmensaje(Mensajes.OK);
                respuesta.setMensaje("Actualización del usuario exitosa.");

                //Envio email de creación de usuario al ciudadano
                HashMap<String, String> entrada = new HashMap<>();
                entrada.put("[fecha]", FechaUtil.obtenerFechaEnTexto(fechaActual));
//                enviarNotificacionEmailPersona(usuario.getCorreos(), entrada);
            } else {
                respuesta.setMensaje("El servicio para actualizaci\u00f3n de datos no esta disponible. Por favor intente mas tarde.");
            }
        } catch (Exception e) {
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje("Error actualizando la informaci\u00f3n del usuario. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error actualizando la informaci\u00f3n del usuario {0}", e);
        }
        return respuesta;
    }

    public MensajeDTO actualizarContrasenia(final UsuarioDTO usuario) {
        MensajeDTO respuesta = new MensajeDTO();
        respuesta.setCodmensaje(Mensajes.ERROR);

        try {
            if (!usuario.getContrasena().matches(".*[A-Z].*") || !usuario.getContrasena().matches(".*[!-/].*")) {
                respuesta.setMensaje("La contraseña debe contener caracteres alfanuméricos, por lo menos un carácter especial y una letra mayúscula");
            } else {
                ComConstantes constanteUsuario = constanteDAO.consultarPorGrupoYNombre(Constantes.CONSTANTE_GRUPO_COMMON, Constantes.CONSTANTE_NOMBRE_ADMIN_CIUDADANO);
                ComConstantes constantePassword = constanteDAO.consultarPorGrupoYNombre(Constantes.CONSTANTE_GRUPO_COMMON, Constantes.CONSTANTE_NOMBRE_CONTRASENA_CIUDADANO);

                try {
                    ClienteOID clienteOID = new ClienteOID(constanteUsuario.getConstanteValor(), constantePassword.getConstanteValor(), ClienteOID.TipoUsuario.ADMIN);
                    clienteOID.actualizarPassword("", usuario.getDocumento(), ClienteOID.TipoUsuario.HQ, usuario.getContrasena());

                    respuesta.setCodmensaje(Mensajes.OK);
                    respuesta.setMensaje("Actualización de la contraseña del usuario exitosa.");
                } catch (OIDException e) {
                    respuesta.setMensaje("Error actualizando la contraseña del usuario. Por favor intente mas tarde.");
                }
            }
        } catch (Exception e) {
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje("Error actualizando la informaci\u00f3n del usuario. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error actualizando la informaci\u00f3n del usuario {0}", e);
        }
        return respuesta;
    }

    public boolean enviarNotificacionEmailPersona(final List<String> destinatarios, final HashMap<String, String> datos) {
        boolean enviado = false;
        ClienteEnvioCorreo clienteEnvioCorreo;
        EnvioCorreoDTO envioCorreoDTO;
        String contenido;

        String contenidoEmail = constanteDAO.consultarPorGrupoYNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.CONSTANTE_EMAIL_MODIFICACION_USUARIO).getConstanteValor();

        if (contenidoEmail != null) {
            try {
                //se procesa el cotenido con los comodines
                contenido = EmailUtil.contenidoEmailHtml("Confirmaci\u00f3n de actualizaci\u00f3n de datos de usuario RUNT", "ciudadano", procesarContenidoCorreo(contenidoEmail, datos), "");

                envioCorreoDTO = new EnvioCorreoDTO();
                clienteEnvioCorreo = new ClienteEnvioCorreo(Constantes.URL_SERVICIO_CORREO);
                envioCorreoDTO.setPara(destinatarios);
                envioCorreoDTO.setAsunto("Confirmaci\u00f3n de actualizaci\u00f3n de datos de usuario RUNT");
                envioCorreoDTO.setContenido(contenido);

                clienteEnvioCorreo.enviarCorreo(envioCorreoDTO);

                enviado = true;
            } catch (Exception e) {
                Logger.getLogger(UsuarioLogica.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return enviado;
    }

    private String procesarContenidoCorreo(String contenido, final HashMap<String, String> datos) {
        String aux;
        if (datos != null && !datos.isEmpty()) {
            for (Map.Entry<String, String> comodin : datos.entrySet()) {
                if (contenido.contains(comodin.getKey())) {
                    aux = contenido.replace(comodin.getKey(), comodin.getValue());
                    contenido = aux;
                }
            }
        }
        return contenido;
    }

}
