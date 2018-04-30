package co.com.runt.sagir.impl;


/**
 * Esta clase permite construir un cliente para la consulta de los servicio WEB.
 * <pre>
 * Se debe establecer por menos los parámetros de:
 * - IdCliente
 * - Servidor 
 * - LlavePublicaRunt 
 * - LlaveFirmaDigital
 * </pre>
 * <pre>
 * Un ejemplo del uso de esta clase cuando todavía no se ha negociado un llave con el servidor es el siguiente :
 * ClienteRuntBuilder builder = new ClienteRuntBuilder();
 * ClienteRunt clienteRunt = builder
 *                           .idCliente("123")
 *                           .servidor("localhost")
 *                           .llavePublicaRunt(llavePublicaRunt)
 *                           .llaveFirmaDigital(llaveFirmaDigital)
 *                           .build();
 * </pre>
 * <pre>
 * Un ejemplo del uso de esta clase cuando se ha negociado un llave con el servidor es el siguiente :
 * ClienteRuntBuilder builder = new ClienteRuntBuilder();
 * ClienteRunt clienteRunt = builder
 *                           .idCliente("123")
 *                           .servidor("localhost")
 *                           .llavePublicaRunt(llavePublicaRunt)
 *                           .llavePrivadaConsulta(llaveNegociadaConElServidor)
 *                           <b>.llaveFirmaDigital(llaveFirmaDigital)</b>
 *                           .build();
 * </pre>

*
 *
 *
 *
 *
 * @author Concesión RUNT
 */
public class ClienteRuntBuilder {

    private String servidor;
    private String contexto = "runt/app/consultas";
    private int puerto = 80;
    
    
    
    

    /**
     * Establece la IP o nombre del servidor donde se enviarán las perticiones
     *
     * @param servidor IP o nombre del servidor
     * @return
     */
    public ClienteRuntBuilder servidor(final String servidor) {
        this.servidor = servidor;
        return this;
    }

    /**
     * Establece el contexto de consulta del servicio
     *
     * @param contexto el contexto de consulta del servicio
     * @return
     */
    public ClienteRuntBuilder contexto(final String contexto) {
        this.contexto = contexto;
        return this;
    }

    /**
     * Establece el puerto donde se enviarán las perticiones
     *
     * @param puerto puerto
     * @return
     */
    public ClienteRuntBuilder puerto(final int puerto) {
        this.puerto = puerto;
        return this;
    }

    

    
   

    
    
    
    /**
     * Construye un nuevo cliente con los parámetros establecidos y lo retorna
     *
     * @return El cliente RUNT con los parámetros establecidos
     */
    public ClienteRunt build() {
            return new ClienteRuntImpl(servidor, puerto, contexto);
    }

}
