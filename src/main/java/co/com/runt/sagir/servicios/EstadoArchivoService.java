/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.SgCarguearchivosDTO;
import co.com.runt.sagir.common.CommonPropietarioLogica;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dsalamanca
 */
@Path("/estadocargue")
@Stateless
public class EstadoArchivoService extends ManejadorServicio{
    
    @EJB
    private CommonPropietarioLogica cambioPropietarioLogica;
    
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public List<SgCarguearchivosDTO> consultarPorPlaca (){
        return cambioPropietarioLogica.consultarCarguesPendientes(getUsuario());
    }        
}

