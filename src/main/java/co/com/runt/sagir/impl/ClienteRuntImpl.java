package co.com.runt.sagir.impl;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.logconsultas.api.MensajeDTO;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import javax.ws.rs.core.MediaType;


/**
 *
 * @author Concesión RUNT
 */
class ClienteRuntImpl implements ClienteRunt {

    private final Client client;
    private final WebResource target;
    private static final String URL_GUARDAR_LOGS_CONSULTA = "/registroLogs";
    
    @Context
    private HttpServletRequest request;

    public ClienteRuntImpl(final String url, final int port, final String contexo) {
        client = Client.create();
        if (port == 443) {
            target = client.resource("https://" + url + ":" + port + "/" + contexo + "/webresources/");
        } else {
            target = client.resource("http://" + url + ":" + port + "/" + contexo + "/webresources/");
        }
    }

    @Override
     public MensajeDTO guardarLog(LogConsultaDTO consulta) {

        Gson gson = new Gson();
        String result = target.path(URL_GUARDAR_LOGS_CONSULTA)
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(String.class, gson.toJson(consulta));

        MensajeDTO salida = gson.fromJson(result, MensajeDTO.class);
        return salida;
    }

    @Override
    public void terminar() {

    }

    
}
