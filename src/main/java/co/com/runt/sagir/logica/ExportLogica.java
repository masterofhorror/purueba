/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ExportDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.ExportMigradosDTO;
import co.com.runt.sagir.dto.ExportPendientesMigrarDTO;
import co.com.runt.sagir.dto.ExportResultadoDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ExportMigrados;
import co.com.runt.sagir.entities.ExportPendientesMigrar;
import co.com.runt.sagir.entities.ExportResultado;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class ExportLogica {

    @EJB
    private ExportDAO exportDAO;
    
    @EJB
    private PlSqlDAO almacenados;

    /**
     * Metodo que retorna la cantidad de datos pendientes por migrar
     *
     * @return
     */
    public MensajeDTO consultaPendientesMigrar() {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<ExportPendientesMigrarDTO> listDTO = new ArrayList<>();
        List<ExportPendientesMigrar> consulta = exportDAO.pendientesMigrar();
        if (consulta != null && !consulta.isEmpty()) {
            listDTO = TransformacionDozer.transformar(consulta, ExportPendientesMigrarDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("No se encuentran datos pendientes por migrar");
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    /**
     * Metodo que retorna la cantidad de datos migrados
     *
     * @return
     */
    public MensajeDTO consultaMigrados() {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<ExportMigradosDTO> listDTO = new ArrayList<>();
        List<ExportMigrados> consulta = exportDAO.migrados();
        if (consulta != null && !consulta.isEmpty()) {
            listDTO = TransformacionDozer.transformar(consulta, ExportMigradosDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("No se encuentran datos migrados");
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    /**
     * Metodo que retorna el resultado de cargue
     * @return 
     */
    public MensajeDTO consultaResultado() {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<ExportResultadoDTO> listDTO = new ArrayList<>();
        List<ExportResultado> consulta = exportDAO.resultado();
        if (consulta != null && !consulta.isEmpty()) {
            listDTO = TransformacionDozer.transformar(consulta, ExportResultadoDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("No se encuentran datos migrados");
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MensajeDTO procesar (final String codCarga){
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        almacenados.sincronizarMasivoRna();
        almacenados.sincronizarMasivoRnc();
        almacenados.validaPersonaRnc();
        almacenados.pMigrarRNA();
        almacenados.pMigrarRNC();
        almacenados.pExport();
        almacenados.sincronizarRNA();
        salida.setCodmensaje(Mensajes.OK);
        mensaje = ("Se proceso el cargue de forma exitosa");
        salida.setMensaje(mensaje);
        return salida;
    }
}
