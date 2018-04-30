/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CarguePuntualDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.CarguePuntualDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author APENA
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CarguePuntualLogica {

    @EJB
    private CarguePuntualDAO carguePuntualDAO;

    @EJB
    private LogsConsultasLogica guardarLogLogica;

    @EJB
    private PlSqlDAO almacenados;

    public MensajeDTO CarguePuntual(final String placa, InfoUsuarioDTO infUsuario, final String ip) {

        String mensaje = null;
        String resultado;
        MensajeDTO salida = new MensajeDTO();
        CarguePuntualDTO cPuntual = new CarguePuntualDTO();

        BigDecimal regVeh = carguePuntualDAO.registroVehiculoMigracion(placa);

        if (regVeh != null) {
            BigDecimal vehiculoCargaR = carguePuntualDAO.vehiculoCargaRuntprod(placa);
            BigDecimal vehiculoCargaM = carguePuntualDAO.vehiculoCargaMigrarunt(placa);

            if (vehiculoCargaR == BigDecimal.valueOf(0)) {
                if (vehiculoCargaM == BigDecimal.valueOf(0)) {
                    resultado = almacenados.carguePuntual(placa);

                    if (resultado != null) {
                        salida.setCodmensaje(Mensajes.ERROR);
                        salida.setMensaje(resultado);
                    } else {
                        almacenados.validaPersonaRnc();
                        almacenados.migrarRnaPlaca(regVeh);
                        almacenados.sincronizarRnaPlaca(placa);

                        salida.setCodmensaje(Mensajes.OK);
                        mensaje = ("El proceso se realizo correctamente.");
                        salida.setMensaje(mensaje);
                        cPuntual.setPlaca(placa);
                        cPuntual.setRegVeh(regVeh);
                        salida.setObject(cPuntual);
                    }
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = ("No se permite la actualizacion dado que el vehiculo es de carga.");
                    salida.setMensaje(mensaje);
                }

            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = ("No se permite la actualizacion dado que el vehiculo es de carga.");
                salida.setMensaje(mensaje);
            }

        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("La placa no posee datos a cargar.");
            salida.setMensaje(mensaje);
        }

        return salida;
    }

    public MensajeDTO MarcarCarguePuntual(final String placa, InfoUsuarioDTO infUsuario, final String ip) {
        String mensaje = null;
        String resultado;
        MensajeDTO salida = new MensajeDTO();
        CarguePuntualDTO cPuntual = new CarguePuntualDTO();

        BigDecimal regVeh = carguePuntualDAO.registroVehiculoMigracionMarca(placa);

        if (regVeh != null) {

            resultado = almacenados.marcarCarguePuntual(placa);

            if (resultado != null) {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje(resultado);
            } else {
                salida.setCodmensaje(Mensajes.OK);
                mensaje = ("La placa " + placa + " se marco exitosamente.");
                cPuntual.setPlaca(placa);
                cPuntual.setRegVeh(regVeh);
                salida.setObject(cPuntual);
                salida.setMensaje(mensaje);
            }

        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("La placa no posee datos a marcar.");
            salida.setMensaje(mensaje);
        }

        return salida;
    }

    public MensajeDTO EliminarExportRNA() {
        String mensaje = null;
        MensajeDTO salida = new MensajeDTO();

        try {
            almacenados.eliminarExportRna();
            salida.setCodmensaje(Mensajes.OK);
            mensaje = ("La informacion se elimino de manera correcta.");
            salida.setMensaje(mensaje);

        } catch (Exception e) {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("No se pudo eliminar la informacion.");
            salida.setMensaje(mensaje);
        }

        return salida;
    }
}
