/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CambioPropietarioAutomotorDAO;
import co.com.runt.sagir.dao.ProcesoMigracionDAO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.SgCarguearchivosDTO;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.servicios.CargueArchivoService;
import co.com.runt.sagir.servicios.ManejadorServicio;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcesarCarguesPendientesLogica extends ManejadorServicio {

    private static final Logger LOG = Logger.getLogger(ProcesarCarguesPendientesLogica.class.getName());

    @EJB
    private CambioPropietarioAutomotorDAO automotorDAO;

    @EJB
    private PoblarTablasPropietarioLogica tablasPropietario;

    @EJB
    private ProcesarCarguesPropietarioLogica procesarCarguesLogica;

    @EJB
    private ProcesoMigracionDAO procesoMigracionDAO;

    @Resource
    protected EJBContext context;

    @EJB
    private CargueArchivoService cargueArchivosServices;

    @Asynchronous
    public void procesarArchivosPendientes(SgCarguearchivosDTO cargueDTO, int hilo, long horaIniMillis, long horasMaxMillis) {
        long horaActualMillis;
        try {
            boolean procesoCorrecto;
            MensajeDTO salida = new MensajeDTO();
            List<SgCarguearchivos> lista = automotorDAO
                    .consultarCargues(CargueArchivoMensajes.SIN_PROCESAR.toString());
            if (lista != null && !lista.isEmpty()) {
                for (SgCarguearchivos sc : lista) {
                    String ruta = sc.getCarguearchivosDatos();
                    String nombreArchivo = sc.getCarguearchivosNombreDatos();
                    Long idSecretaria = Long.parseLong(sc.getCarguearchivosIdautra());
                    String usuario = sc.getCarguearchivosUsuario();
                    procesoCorrecto = tablasPropietario.poblarTablasMigra(nombreArchivo, ruta, idSecretaria, usuario);
                    if (procesoCorrecto) {
                        LOG.log(Level.SEVERE, "Se ejecuto corretamente el cargue {0}", cargueDTO.getCarguearchivosId());
                    } else {
                        LOG.log(Level.SEVERE, "Se debe parar el procesamiento por un error interno en el cargue {0}", cargueDTO.getCarguearchivosId());
                    }
                    cargueDTO = procesarCarguesLogica.obtenerSiguienteCargue(hilo);

                    horaActualMillis = System.currentTimeMillis();
                    LOG.log(Level.INFO, "Tiempo de procesamiento actual: ", horaActualMillis);
                    if ((horaActualMillis - horaIniMillis) >= horasMaxMillis) {
                        cargueDTO = null;
                        LOG.log(Level.INFO, "Procesamiento de cargues de actualización de propietarios --- Se excedio el tiempo maximo de ejecucion de la tarea, proceso suspendido.....");
                    }
                    LOG.log(Level.INFO, "Tiempo fin cargue {0}", System.currentTimeMillis());
                }
                LOG.log(Level.INFO, "*********************************************************************************");
                LOG.log(Level.INFO, "Actualización propietario ----- finalizo el procesamiento del hilo {0}", hilo);
                LOG.log(Level.INFO, "*********************************************************************************");
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("No existen archivos pendientes por procesar.");
            }
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

    @Asynchronous
    public void procesarCargueProcesoMigracionRNC(final Long idSecretaria) {
        try {
            MensajeDTO salida = new MensajeDTO();
            List<SgCarguearchivos> archivos = procesoMigracionDAO.archivosPendientes(idSecretaria.toString());
            if (archivos != null && !archivos.isEmpty()) {
                for (SgCarguearchivos sc : archivos) {
                    String ruta = sc.getCarguearchivosDatos();
                    String nombreArchivo = sc.getCarguearchivosNombreDatos();
                    String usuario = sc.getCarguearchivosUsuario();
                    byte[] validaFirma = desfirmaArchivo(ruta, nombreArchivo, usuario, idSecretaria);
                    if (validaFirma != null) {

                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        salida.setMensaje("No se puede validar la firma del archivo o esta mal firmado.");
                    }
                }
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("No existen archivos pendientes por procesar.");
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }

    /**
     * Metodo que se encarga de validar la firma y desfirmar el archivo
     *
     * @param ruta
     * @param nombreArchivo
     * @param usuario
     * @param idSecretaria
     * @return
     */
    public byte[] desfirmaArchivo(final String ruta, final String nombreArchivo, final String usuario, final Long idSecretaria) {
        try {
            File dir = new File(ruta);
            byte[] data;
            data = convertirArchivo(dir);

            if (data == null) {
                System.out.println("Error en el archivo...");
            }
            SgCarguearchivos cargue = procesoMigracionDAO.consultaArchivo(nombreArchivo);
            try {
                try {
                    byte[] contenido = cargueArchivosServices.validarCertificado(usuario, Base64.encodeBase64String(data), idSecretaria);
                    if (contenido == null) {
                        UserTransaction ut = context.getUserTransaction();
                        ut.begin();
                        cargue.setCarguearchivosEstado(CargueArchivoMensajes.RECHAZADO.toString());
                        procesoMigracionDAO.actualizaEstadoArchivo(cargue);
                        ut.commit();
                    } else {
                        return contenido;
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, ex.getLocalizedMessage());
                }
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getLocalizedMessage());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }

       return new byte[0];
    }

    public byte[] convertirArchivo(File ficheros) {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) ficheros.length()];

        try {

            fileInputStream = new FileInputStream(ficheros);
            fileInputStream.read(bFile);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        } finally{
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(ProcesarCarguesPendientesLogica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return bFile;
   }
}
