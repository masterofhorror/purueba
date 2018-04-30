/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConsultaRNAMigraDAO;
import co.com.runt.sagir.dto.ConsultaBoletinRNAMigraDTO;
import co.com.runt.sagir.dto.ConsultaIntermRNAMigraDTO;
import co.com.runt.sagir.dto.ConsultaRNAFinalMigraDTO;
import co.com.runt.sagir.dto.ConsultaRNAMigraDTO;
import co.com.runt.sagir.dto.ConsultaRechazoRNAMigraDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ConsultaRNAFinalMigra;
import co.com.runt.sagir.entities.ConsultaRNAMigra;
import co.com.runt.sagir.utils.TransformacionDozer;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Dsalamanca
 */
@Stateless
public class ConsultaRNAMigraLogica {

    @EJB
    private ConsultaRNAMigraDAO aMigraDAO;

    @EJB
    private LogsConsultasLogica guardarLogLogica;

    @EJB
    private CommonLogica commonLogica;

    public MensajeDTO consultaPlacaMigra(final String placa, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            List<ConsultaRNAMigraDTO> listDTO;
            List<ConsultaRNAMigra> aMigras = aMigraDAO.consultaRNAMigracion(placa);
            //guardado
            JsonObject json = new JsonObject();
            json.addProperty(Constantes.PLACA, placa);
            tipoConsulta = Constantes.TIPO_CONSULTA_RNA_MIGRA;
            Long idauttra = usuario.getIdOrganizacion();
            String datoUsuario = usuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
            if (aMigras != null && !aMigras.isEmpty()) {
                listDTO = TransformacionDozer.transformar(aMigras, ConsultaRNAMigraDTO.class);
                salida.setCodmensaje(Mensajes.OK);
                salida.setObject(listDTO);
            } else {
                mensaje = "No se encontraron datos para la placa";
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

    public MensajeDTO consultaIntermediaRNA(final String placa, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            List<ConsultaIntermRNAMigraDTO> listDTO = new ArrayList<>();
            List<Object[]> intermediaRNA = aMigraDAO.consultaIntermediaMigra(placa);
            //guardado
            JsonObject json = new JsonObject();
            json.addProperty("placa", placa);
            tipoConsulta = Constantes.TIPO_CONSULTA_RNA_INTER;
            Long idauttra = usuario.getIdOrganizacion();
            String datoUsuario = usuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
            if (intermediaRNA != null && !intermediaRNA.isEmpty()) {
                salida.setCodmensaje(Mensajes.OK);
                for (Object[] cirna : intermediaRNA) {
                    ConsultaIntermRNAMigraDTO cast = new ConsultaIntermRNAMigraDTO();
                    cast.setIdVehic(cirna[0].toString());
                    cast.setPlaca(cirna[1].toString());
                    cast.setAutoRegistra(cirna[2].toString());
                    cast.setSecretaria(cirna[3].toString());
                    cast.setFechaMigra(cirna[4].toString());
                    cast.setCodCarga(cirna[5].toString());
                    cast.setEstadoVehic(cirna[6].toString());
                    cast.setMigrado(cirna[7].toString());
                    if (cirna[8]==null) {
                        cast.setDesError("NA");
                    } else {
                        cast.setDesError(cirna[8].toString());
                    }
                    listDTO.add(cast);
                    salida.setObject(listDTO);
                }
            } else {
                mensaje = "No se encontraron datos para la placa";
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

    public MensajeDTO consultaBoletinRNAMigra(final String placa, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            List<ConsultaBoletinRNAMigraDTO> listDTO = new ArrayList<>();
            List<Object[]> boletin = aMigraDAO.consultaBoletinRNAMigra(placa);
            //guardado
            JsonObject json = new JsonObject();
            json.addProperty("placa", placa);
            tipoConsulta = Constantes.TIPO_CONSULTA_RNA_BOLETIN;
            Long idauttra = usuario.getIdOrganizacion();
            String datoUsuario = usuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
            if (boletin != null && !boletin.isEmpty()) {
                salida.setCodmensaje(Mensajes.OK);
                for (Object[] cb : boletin) {
                    ConsultaBoletinRNAMigraDTO cast = new ConsultaBoletinRNAMigraDTO();
                    if(cb[0] == null){
                        cast.setPlaca("NO REGISTRA");
                    }else{
                        cast.setPlaca(cb[0].toString());
                    }
                    if(cb[1] == null){
                        cast.setSecretaria("NO REGISTRA");
                    }else{
                        cast.setSecretaria(cb[1].toString());
                    }
                    if(cb[2] == null){
                        cast.setFechaCarga("NO REGISTRA");
                    }else{
                        cast.setFechaCarga(cb[2].toString());
                    }
                    if(cb[3] == null){
                        cast.setFolio("NO REGISTRA");
                    }else{
                        cast.setFolio(cb[3].toString());
                    }
                    if(cb[4] == null){
                        cast.setBoletin("NO REGISTRA");
                    }else{
                        cast.setBoletin(cb[4].toString());
                    }
                    if(cb[5] == null){
                        cast.setCodCriterio("NO REGISTRA");
                    }else{
                        cast.setCodCriterio(cb[5].toString());
                    }
                    if(cb[6] == null){
                        cast.setDescripcion("NO REGISTRA");
                    }else{
                        cast.setDescripcion(cb[6].toString());
                    }
                    listDTO.add(cast);
                    salida.setObject(listDTO);
                }
            } else {
                mensaje = "No se encontraron datos para la placa";
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

    public MensajeDTO consultaRechazoRNAMigra(final String placa, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            List<ConsultaRechazoRNAMigraDTO> listDTO = new ArrayList<>();
            List<Object[]> rechazo = aMigraDAO.ConsultaRechazoRNAMigra(placa);
            //guardado
            JsonObject json = new JsonObject();
            json.addProperty("placa", placa);
            tipoConsulta = Constantes.TIPO_CONSULTA_RNA_RECHAZO;
            Long idauttra = usuario.getIdOrganizacion();
            String datoUsuario = usuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
            if (rechazo != null && !rechazo.isEmpty()) {
                salida.setCodmensaje(Mensajes.OK);
                for (Object[] ob : rechazo) {
                    ConsultaRechazoRNAMigraDTO cast = new ConsultaRechazoRNAMigraDTO();
                    cast.setPlaca(ob[0].toString());
                    cast.setSecretaria(ob[1].toString());
                    cast.setCarga(ob[2].toString());
                    cast.setCodCriterio(ob[3].toString());
                    cast.setCriterio(ob[4].toString());
                    listDTO.add(cast);
                    salida.setObject(listDTO);
                }
            } else {
                mensaje = "No se encontraron datos para la placa";
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

    public MensajeDTO consultarRNAFinalMigra(final String placa, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            List<ConsultaRNAFinalMigraDTO> listDTO = new ArrayList<>();
            List<ConsultaRNAFinalMigra> consultaRNA = aMigraDAO.consultaRNAFinalMigra(placa);
            //guardado
            JsonObject json = new JsonObject();
            json.addProperty("placa", placa);
            tipoConsulta = Constantes.TIPO_CONSULTA_RNA_SAGIR;
            Long idauttra = usuario.getIdOrganizacion();
            String datoUsuario = usuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
            if (consultaRNA != null && !consultaRNA.isEmpty()) {
                listDTO = TransformacionDozer.transformar(consultaRNA, ConsultaRNAFinalMigraDTO.class);
                salida.setCodmensaje(Mensajes.OK);
                salida.setObject(listDTO);
            } else {
                mensaje = "No se encontraron datos para la placa";
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
