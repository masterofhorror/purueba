/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CargueTemporalDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.CargueArchivoDTO;
import co.com.runt.sagir.dto.CargueTemporalDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.CargueTemporal;
import co.com.runt.sagir.entities.ComConstantes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author APENA
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CargueArchivoTempoLogica {

    @Resource
    protected EJBContext context;

    @EJB
    private CargueTemporalDAO cargueTemporalDAO;

    @EJB
    private ConstanteDAO constanteDAO;

    private static final Logger LOG = Logger.getLogger(CargueArchivoTempoLogica.class.getSimpleName());

    private String guardarArchivosTempo(CargueArchivoDTO listaAdjuntos, Long idCargue) {

        ComConstantes constante = constanteDAO.consultarPorGrupoYNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.CONSTANTE_PATH_FILE_SYSTEM_CARGUE_ARCHIVO);
        Date fechaSistema = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaSistema);
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        StringBuilder pathCarpeta = new StringBuilder();
        String pathBase;
        String pathFile = "";

        if (constante != null) {
            try {
                pathBase = constante.getConstanteValor();
                pathCarpeta.append(pathBase);
                File fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }
                pathCarpeta.append("/").append(String.valueOf(anio));
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }
                pathCarpeta.append("/").append(String.valueOf(mes));
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                pathCarpeta.append("/").append(listaAdjuntos.getTipoCargue());
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                if (listaAdjuntos == null) {
                    LOG.log(Level.SEVERE, "Error en el archivo adjunto");
                }

                pathFile = pathCarpeta.toString() + "/" + listaAdjuntos.getNombre();
                if (listaAdjuntos.getIdArchivo() == null) {
                    byte[] file = Base64.decodeBase64(listaAdjuntos.getArchivo());
                    File fileDatos = new File(pathFile);
                    FileUtils.writeByteArrayToFile(fileDatos, file);

                    try (BufferedReader br = new BufferedReader(new FileReader(fileDatos))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            LOG.log(Level.INFO, "la linea, '{'0'}'{0}", line);
                        }
                    }
                    if (!fileDatos.delete()) {
                        fileDatos.delete();
                    }

                    FileUtils.writeByteArrayToFile(new File(pathFile), file);
                }
            } catch (IOException e) {
                LOG.log(Level.INFO, "Error en guardarArchivosAdjuntos : {0}", e.getCause());
            }
        }
        return pathFile;
    }

    public MensajeDTO guardarArchivosAdjuntosTempo2(CargueTemporalDTO cargueTemporalDTO) {

        String pathTemp = System.getProperty("java.io.tmpdir");
        String pathFile = pathTemp + "/" + cargueTemporalDTO.getTempNombre();
        MensajeDTO salida = new MensajeDTO();
        //int index = nombreArchivo.indexOf("mprop");
        String nombreArchivo = cargueTemporalDTO.getTempNombre();
        int index = nombreArchivo.indexOf(".txt");

        if (index > 0) {
            LOG.log(Level.INFO, "=== PATH ARCHIVO : {0}", pathFile);
            if (cargueTemporalDTO.getTempArchivo() != null) {
                try {
                    byte[] file = Base64.decodeBase64(cargueTemporalDTO.getTempArchivo());
                    File fileDatos = new File(pathFile);
                    FileUtils.writeByteArrayToFile(fileDatos, file);
                    BufferedReader br = new BufferedReader(new FileReader(fileDatos));

                    String line;
                    while ((line = br.readLine()) != null) {
                        CargueTemporal tabla = new CargueTemporal();
                        tabla.setTempDato(line);
                        cargueTemporalDAO.insertDato(tabla);
                    }

                    if (!fileDatos.delete()) {
                        fileDatos.delete();
                    }

                    salida.setCodmensaje(Mensajes.OK);
                    salida.setMensaje("Archivo cargado exitosamente.");

                } catch (IOException ex) {
                    Logger.getLogger(CargueArchivoTempoLogica.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("No existen datos para cargar.");
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El archivo a cargar se debe encontrar en formato txt.");
        }
        return salida;
    }

    public MensajeDTO borrarCargueTemporal() {

        MensajeDTO salida = new MensajeDTO();

        int res = cargueTemporalDAO.borrarDatosTemporales();

        if (res == 1) {
            salida.setCodmensaje(Mensajes.OK);
            salida.setMensaje("La tabla se borro exitosamente.");
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("No se puede borrar la tabla cargue_temporal.");
        }

        return salida;
    }

}
