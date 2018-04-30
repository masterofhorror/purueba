/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConsultaRNAProduccionDAO;
import co.com.runt.sagir.dto.ConsultaRNASagirDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ConsultaRNA;
import co.com.runt.sagir.utils.TransformacionDozer;
import com.google.gson.JsonObject;
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
public class ConsultaRNALogica {

    private static final Logger LOG = Logger.getLogger(ConsultaRNALogica.class.getName());

    @EJB
    private ConsultaRNAProduccionDAO consultaRNAProduccionDAO;

    @EJB
    private LogsConsultasLogica guardarLogLogica;

    @EJB
    private CommonLogica commonLogica;

    /**
     * Metodo que permite consultar los registros de RNA por placa para sagir
     *
     * @param placa
     * @return
     */
    public MensajeDTO consultarRNASagir(final String placa, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String tipoConsulta;
        String mensaje;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            List<ConsultaRNASagirDTO> listDTO = new ArrayList<>();
            List<ConsultaRNA> consultaRNA = consultaRNAProduccionDAO.consultaRNASagir(placa);
            if (consultaRNA != null && !consultaRNA.isEmpty()) {
                listDTO = TransformacionDozer.transformar(consultaRNA, ConsultaRNASagirDTO.class);
                salida.setCodmensaje(Mensajes.OK);
                salida.setObject(listDTO);
                JsonObject json = new JsonObject();
                json.addProperty("placa", placa);
                tipoConsulta = Constantes.TIPO_CONSULTA_RNA_PROD;
                Long idauttra = usuario.getIdOrganizacion();
                String datoUsuario = usuario.getLogin();
                LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
                guardarLogLogica.guardarLog(log);
            } else {
                mensaje = ("No se encontraron datos para la placa");
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje(mensaje);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = validaRnrysRnma;
            salida.setMensaje(mensaje);
        }
        return salida;
    }

}
