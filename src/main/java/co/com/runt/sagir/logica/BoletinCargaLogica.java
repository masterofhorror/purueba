/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.BoletinCambioPropietarioDAO;
import co.com.runt.sagir.dao.CambioPropietarioAutomotorDAO;
import co.com.runt.sagir.dto.BoletinCambioPropietarioDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.ListarBoletinPorFechasDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.SgCarguearchivosDTO;
import co.com.runt.sagir.entities.BoletinCambioPropietario;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class BoletinCargaLogica {

    private static final Logger LOG = Logger.getLogger(BoletinCargaLogica.class.getName());

    @EJB
    private BoletinCambioPropietarioDAO boletinCambioPropietarioDAO;

    @EJB
    private CambioPropietarioAutomotorDAO cambioPropietarioDAO;

    public MensajeDTO consultaBoletinCambioProp(final Long idCarga) {
        LOG.log(Level.INFO, "=============validando carga archivo=========");
        MensajeDTO salida = new MensajeDTO();
        try {
            SgCarguearchivos scdto = boletinCambioPropietarioDAO.consultaIdCarga(idCarga);
            if (scdto.getIdCarga() != null) {
                String idCargaProceso = scdto.getIdCarga().toString();
                List<BoletinCambioPropietarioDTO> listDTO;
                List<BoletinCambioPropietario> list = boletinCambioPropietarioDAO.consultaBoletinCambioPropietario(Long.parseLong(idCargaProceso));
                if (list != null && !list.isEmpty()) {
                    listDTO = TransformacionDozer.transformar(list, BoletinCambioPropietarioDTO.class);
                    salida.setCodmensaje(Mensajes.OK);
                    salida.setObject(listDTO);
                }
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("El archivo aún no ha sido procesado");
            }
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return salida;
    }

    public MensajeDTO consultarHistoricoCargues(final InfoUsuarioDTO usuario, final ListarBoletinPorFechasDTO fechaDTO) {
        MensajeDTO salida = new MensajeDTO();
        //Se debe recuperar el cÃ³digo Idautra del metodo de login para que filtre por OT
        String idautra = usuario.getIdOrganizacion().toString();
        List<SgCarguearchivosDTO> listDTO = new ArrayList<>();
        List<SgCarguearchivos> lista = cambioPropietarioDAO.consultaHistoricoCargue(idautra, fechaDTO.getFechaInicial());
        if (!lista.isEmpty()) {
            for(SgCarguearchivos sg : lista){
                SgCarguearchivosDTO cast = new SgCarguearchivosDTO();
                cast.setCarguearchivosId(sg.getCarguearchivosId());
                String nroTicket = sg.getCarguearchivosDatos();
                int index = nroTicket.indexOf(Constantes.INC);
                if(index > 0){
                    int finalSubstring = index + 15;
                    cast.setCarguearchivosDatos(nroTicket.substring(index, finalSubstring));
                }
                cast.setCarguearchivosNombreDatos(sg.getCarguearchivosNombreDatos());
                cast.setCarguearchivosFecha(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(sg.getCarguearchivosFecha()));
                cast.setCarguearchivosEstado(sg.getCarguearchivosEstado());
                listDTO.add(cast);
                salida.setObject(listDTO);
                salida.setCodmensaje(Mensajes.OK);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("No registra cargues para el día seleccionado");
        }
        return salida;
    }
}
