package co.com.runt.sagir.dao;


import co.com.runt.sagir.entities.LogConsulta;
import co.com.runt.sagir.entities.PaAplicacion;
import co.com.runt.sagir.entities.PaTipoconsulta;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Dospina
 */
@Stateless
public class LogConsultaDAO extends GenericDAO{
    
    private static final Logger LOGGER = Logger.getLogger(LogConsultaDAO.class.getSimpleName());        
    
    /**
     *
     * @param logConsulta
     */
    public void guardarLogConsulta(LogConsulta logConsulta){
        try{
            em.persist(logConsulta);
        }catch (Exception ex) {
            LOGGER.log(Level.INFO, "{0}", ex.getLocalizedMessage());            
        }
    }
    
    /**
     *
     * @param aplicacion
     * @param usuario
     * @param tipoConsulta
     * @param autoridad
     * @param ip
     * @param consulta
     * @return
     */
    public LogConsulta construirLog(
            final String usuario, final PaAplicacion aplicacion,
            final PaTipoconsulta tipoConsulta, final Long autoridad,
            final String ip, final String consulta
    ) {
        LogConsulta logConsulta = new LogConsulta();
        logConsulta.setConsultaId(UUID.randomUUID().toString());
        logConsulta.setConsultaFecha(new Date());
        logConsulta.setConsultaIp(ip);
        logConsulta.setConsultaTipoconsultaCodigo(tipoConsulta);
        logConsulta.setConsultaAplicacionCodigo(aplicacion);        
        logConsulta.setConsultaEntrada(consulta);
        logConsulta.setConsultaAutoridad(autoridad);
        logConsulta.setConsultaUsuario(usuario);
        
        return logConsulta;
    }
}
