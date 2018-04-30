package co.com.runt.sagir.impl;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.logconsultas.api.MensajeDTO;
import co.com.runt.sagir.exception.ErrorConsultaLogException;


/**
 *
 * @author Concesión RUNT 
 */
public interface ClienteRunt {

    /**
     * Negocia una nueva llave con el servidor 
     * @param consulta
     * @return La llave negociada
     * @throws co.com.runt.sagir.exception.ErrorConsultaLogException
     */
    public MensajeDTO guardarLog(LogConsultaDTO consulta) throws ErrorConsultaLogException;
    
        
    /**
     * Termina cliente y liberar todos los recursos asociados
     */
    public void terminar();
    
}
