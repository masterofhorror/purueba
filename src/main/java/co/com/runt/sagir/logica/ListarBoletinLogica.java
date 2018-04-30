/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.BaseDAO;
import co.com.runt.sagir.dao.BoletinesTxtRnaDAO;
import co.com.runt.sagir.dao.BoletinesTxtRncDAO;
import co.com.runt.sagir.dao.ListarBoletinDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.CargaDTO;
import co.com.runt.sagir.dto.DatosBoletinTxtRncDTO;
import co.com.runt.sagir.dto.ListarBoletinPorFechasDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.PathDTO;
import co.com.runt.sagir.entities.AutoridadTransitoHQ;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.CivLicencias;
import co.com.runt.sagir.entities.CivPersonas;
import co.com.runt.sagir.entities.CivPropietarios;
import co.com.runt.sagir.entities.CivResidencias;
import co.com.runt.sagir.entities.CivRestricciones;
import co.com.runt.sagir.entities.CivVehiculos;
import co.com.runt.sagir.entities.PoblamientoLicencias;
import co.com.runt.sagir.entities.PoblamientoPersonas;
import co.com.runt.sagir.entities.PoblamientoPropietarios;
import co.com.runt.sagir.entities.PoblamientoResidencias;
import co.com.runt.sagir.entities.PoblamientoRestricciones;
import co.com.runt.sagir.entities.PoblamientoVehiculos;
import co.com.runt.sagir.utils.ArchivosUtil;
import co.com.runt.sagir.utils.ListarBoletinException;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class ListarBoletinLogica {

    @EJB
    private ListarBoletinDAO boletinDAO;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private BoletinesTxtRncDAO boletinesTxtRnc;

    @EJB
    private BoletinesTxtRnaDAO boletinesTxtRna;

    @EJB
    private ArchivosUtil archivosUtil;

    private static final Logger LOG = Logger.getLogger(ListarBoletinLogica.class.getName());

    /**
     * Metodo que lista los boletines por cÃ³digo de carga
     *
     * @param codCarga
     * @return
     */
    public MensajeDTO listarByCodCarga(final Integer codCarga) {
        MensajeDTO salida = new MensajeDTO();
        List<CargaDTO> cargaDTO;
        List<Carga> consulta = boletinDAO.listarByCodCarga(codCarga);
        if (consulta != null && !consulta.isEmpty()) {
            salida.setCodmensaje(Mensajes.OK);
            cargaDTO = TransformacionDozer.transformar(consulta, CargaDTO.class);
            salida.setObject(cargaDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("No existe un boletín con el código de carga ingresado");
        }
        return salida;
    }

    /**
     * Metodo que lista los boletines con el tipo de registro RNA - RNC
     *
     * @param tipoRegistro
     * @return
     */
    public MensajeDTO listarByTipoRegisgtro(final Integer tipoRegistro) {
        MensajeDTO salida = new MensajeDTO();
        List<CargaDTO> cargaDTO;
        List<Carga> consulta = boletinDAO.listarByTipoRegisgtro(tipoRegistro);
        if (consulta != null && !consulta.isEmpty()) {
            salida.setCodmensaje(Mensajes.OK);
            cargaDTO = TransformacionDozer.transformar(consulta, CargaDTO.class);
            salida.setObject(cargaDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("No se encontraron boletines con el tipo de registro ingresado");
        }
        return salida;
    }

    /**
     * Metodo de enlista los boletines por el cÃ³digo del organismo de trÃ¡nsito
     *
     * @param idSecretaria
     * @return
     */
    public MensajeDTO listaByIdSecretaria(final Long idSecretaria) {
        MensajeDTO salida = new MensajeDTO();
        List<CargaDTO> cargaDTO;
        List<Carga> consulta = boletinDAO.listaByIdSecretaria(idSecretaria);
        if (consulta != null && !consulta.isEmpty()) {
            salida.setCodmensaje(Mensajes.OK);
            cargaDTO = TransformacionDozer.transformar(consulta, CargaDTO.class);
            salida.setObject(cargaDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("No existe un boletín con el código de secretaria ingresado");
        }
        return salida;
    }

    /**
     * Metodo que enlista los boletines por un rango de fecha
     *
     * @param fechasDTO
     * @return
     */
    public MensajeDTO listaByRangoFecha(ListarBoletinPorFechasDTO fechasDTO) {
        MensajeDTO salida = new MensajeDTO();
        List<CargaDTO> cargaDTO;
        List<Carga> consulta = boletinDAO.listaByRangoFecha(fechasDTO.getFechaInicial(), fechasDTO.getFechaFinal());
        if (consulta != null && !consulta.isEmpty()) {
            salida.setCodmensaje(Mensajes.OK);
            cargaDTO = TransformacionDozer.transformar(consulta, CargaDTO.class);
            salida.setObject(cargaDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("No existe un boletín con el rango de fecha ingresado");
        }
        return salida;
    }

    /**
     * Metodo que se encarga de descargar el archivo ZIP
     *
     * @param idCarga
     * @return
     */
    public byte[] descargarBoletinZip(final Long idCarga) {
        byte[] archivoZip = null;
        //Se valida si el registro es RNA o RNC
        Carga consulta = boletinDAO.consultaBoletin(idCarga);
        //Si el registro es RNA
        if (consulta.getTipoRegistro() == 1) {
            try {
                archivoZip = generarBoletinRnaTxt(idCarga);
            } catch (ListarBoletinException e) {
                e.getLocalizedMessage();
            }
            //Si el registro es RNC
        } else {
            try {
                Carga autoridad = boletinDAO.consultaAutoridadPorCodCarga(idCarga);
                archivoZip = generarBoletinRncTxt(idCarga, autoridad.getIdSecretaria().getIdSecretaria());
            } catch (ListarBoletinException ex) {
                ex.getLocalizedMessage();
            }
        }
        return archivoZip;
    }

    /**
     * Metodo que se encarga de generar los boletines
     *
     * @param zos
     * @param idCarga
     * @return
     * @throws ListarBoletinException
     */
    public ZipOutputStream generarBoletinPdf(final Long idCarga, ZipOutputStream zos) throws ListarBoletinException {
        Connection connection = null;
        Carga consulta = boletinDAO.consultaBoletin(idCarga);
        Long autoridad = consulta.getIdSecretaria().getIdSecretaria();
        //Si el tipo de registro es 1 - RNA
        try {
            if (consulta.getTipoRegistro() == 1) {
                almacenados.boletinRNA(idCarga.toString(), autoridad.toString());
                Integer validaBoletin = boletinDAO.countBoletinByCodCargaRNA(idCarga);
                if (validaBoletin != 0) {
                    //Se cargan los recursos del reporte
                    InputStream is = ListarBoletinLogica.class.getResourceAsStream("ReporteRNA.jasper");
                    Map<String, Object> parameters = new HashMap<>();
                    //CÃ³digo de la carga
                    parameters.put("P_COD_CARGA", idCarga.toString());
                    //id del Organismo de transito
                    parameters.put("P_ID_SECRETARIA", autoridad.toString());
                    //Imagen
                    //Se envian los subreportes como parÃ¡metro:
                    //Reporte general
                    parameters.put("P_SUB_ENCABEZADO_RNA", ListarBoletinLogica.class.getResourceAsStream("SubEncabezado.jasper"));
                    parameters.put("P_SUB_DETALLE__RNA", ListarBoletinLogica.class.getResourceAsStream("SubDescripcion.jasper"));
                    parameters.put("P_VEHICULO_REPORTE_RNA", ListarBoletinLogica.class.getResourceAsStream("SubVehiculoReporte.jasper"));
                    parameters.put("P_SUB_DETALLE_VEHICULO_REPORTE", ListarBoletinLogica.class.getResourceAsStream("SubDetalleVehiculoReporte.jasper"));
                    parameters.put("P_PROPIETARIOS_RNA", ListarBoletinLogica.class.getResourceAsStream("SubReportePropietarioRNA.jasper"));
                    parameters.put("P_DETALLE_PROPIETARIO_RNA", ListarBoletinLogica.class.getResourceAsStream("SubReportePropietarioDetalleRNA.jasper"));
                    parameters.put("P_MENSAJE_RNA", ListarBoletinLogica.class.getResourceAsStream("MensajeRNA.jasper"));
                    //Se establece la conexiÃ³n
                    connection = BaseDAO.getConnection();
                    //Se genera el reporte
                    JasperPrint jasperPrint = JasperFillManager.fillReport(is, parameters, connection);
                    //Se exporta el reporte como un pdf y se asigna aun arreglo de bytes
                    byte[] archivo = JasperExportManager.exportReportToPdf(jasperPrint);

                    //Se retorna el arreglo de bytes con el reporte generado
                    guardarArchivosAdjuntos(archivo, idCarga, idCarga.toString() + ".pdf");

                    //Agregar al zip
                    zos = comprimirArchivoZip(zos, archivo, idCarga.toString() + ".pdf");
                } else {
                    LOG.log(Level.SEVERE, "Error al poblar el boletín");
                }
                //Tipo de registro 2 = RNC
            } else {
                almacenados.boletinRNC(idCarga.toString(), autoridad.toString());
                Integer validaBoletinRNC = boletinDAO.countBoletinByCodCargaRNC(idCarga);
                if (validaBoletinRNC != 0) {
                    InputStream is = ListarBoletinLogica.class.getResourceAsStream("ReporteRNC.jasper");
                    Map<String, Object> parameters = new HashMap<>();
                    //CÃ³digo de la carga
                    parameters.put("P_COD_CARGA", idCarga.toString());
                    //id del Organismo de transito
                    parameters.put("P_ID_SECRETARIA", autoridad.toString());
                    //Imagen
                    //Se envian los subreportes como parÃ¡metro:
                    //Reporte general
                    parameters.put("P_SUB_ENCABEZADO_RNC", ListarBoletinLogica.class.getResourceAsStream("SubEncabezadoRNC.jasper"));
                    parameters.put("P_SUB_GENERAL_REPORTE_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReporteGeneralRNC.jasper"));
                    parameters.put("P_SUB_LICENCIAS_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReporteLicenciasRNC.jasper"));
                    parameters.put("P_SUB_DETALLE_LICENCIAS_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReporteDetalleLicenciaRNC.jasper"));
                    parameters.put("P_SUB_PERSONAS_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReportePersonasRNC.jasper"));
                    parameters.put("P_SUB_DETALLE_PERSONAS_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReporteDetallePersonasRNC.jasper"));
                    parameters.put("P_SUB_RESIDENCIA_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReporteResidenciaRNC.jasper"));
                    parameters.put("P_SUB_DETALLE_RESIDENCIA_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReporteDetalleResidenciaRNC.jasper"));
                    parameters.put("P_SUB_RESTRICCIONES_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReporteRestriccionesRNC.jasper"));
                    parameters.put("P_SUB_DETALLE_RESTRICCIONES_RNC", ListarBoletinLogica.class.getResourceAsStream("SubReporteDetalleRestriccionesRNC.jasper"));
                    parameters.put("P_MENSAJE", ListarBoletinLogica.class.getResourceAsStream("MensajeRNC.jasper"));

                    connection = BaseDAO.getConnection();
                    //Se genera el reporte
                    JasperPrint jasperPrint = JasperFillManager.fillReport(is, parameters, connection);
                    //Se exporta el reporte como un pdf y se asigna aun arreglo de bytes
                    byte[] archivo = JasperExportManager.exportReportToPdf(jasperPrint);

                    //Se retorna el arreglo de bytes con el reporte generado
                    guardarArchivosAdjuntos(archivo, idCarga, idCarga.toString() + ".pdf");

                    //Agregar al zip
                    zos = comprimirArchivoZip(zos, archivo, idCarga.toString() + ".pdf");

                } else {
                    LOG.log(Level.SEVERE, "Error al poblar el boletín");
                }
            }
        } catch (SQLException | JRException | NamingException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new ListarBoletinException(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }
        return zos;
    }

    public byte[] generarBoletinRnaTxt(final Long idCarga) throws ListarBoletinException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PathDTO pathDTO = new PathDTO();
        Carga carga = boletinDAO.consultaBoletin(idCarga);
        Long autoridad = carga.getIdSecretaria().getIdSecretaria();
        if (carga != null) {
            //Crear el zip
            try {
                ZipOutputStream zos = new ZipOutputStream(baos);
                //----------------------PROPIETARIOS----------------------------
                //Poblamiento Propietarios
                Integer countPoblamientoPropietario = boletinesTxtRna.countPoblamientoPropietarios(idCarga);
                if (countPoblamientoPropietario != 0) {
                    String nombreArc = "Boletin_RNA_" + carga.getIdSecretaria() + "_Detalle_Poblamiento_Prop.txt";
                    List<PoblamientoPropietarios> consultaPoblamiento = boletinesTxtRna.poblamientoPropietarios(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaPoblamiento.isEmpty()) {
                        for (PoblamientoPropietarios pl : consultaPoblamiento) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setSecretaria(Long.parseLong(pl.getSecretaria()));
                            cast.setPlaca(pl.getPlaca());
                            cast.setTipoDoc(pl.getTipoDocumento());
                            cast.setNroLicencias(pl.getNroDocumento());
                            cast.setFecha(pl.getFecha());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                        String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                        byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                        if (archivo != null) {
                            pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                            //se agrega al zip
                            zos = comprimirArchivoZip(zos, archivo, nombreArc);
                        } else {
                            pathDTO.setMensaje(Constantes.ERRORGUARDARARCHIVO);
                        }
                    }
                }
                //Civ Propietarios
                Integer countCivPropietario = boletinesTxtRna.countCivPropietarios(idCarga, autoridad);
                if (countCivPropietario != 0) {
                    String nombreArc = "Boletin_RNA_" + autoridad + "_Detalle_CIV_Prop.txt";
                    List<CivPropietarios> consultaCiv = boletinesTxtRna.civPropietarios(idCarga, autoridad);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaCiv.isEmpty()) {
                        for (CivPropietarios pl : consultaCiv) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setPlaca(pl.getPlaca());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            cast.setSecretaria(Long.parseLong(pl.getSecretaria()));
                            cast.setCodCarga(pl.getCodCarga());
                            listDTO.add(cast);
                        }
                        String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                        byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                        if (archivo != null) {
                            pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                            //se agrega al zip
                            zos = comprimirArchivoZip(zos, archivo, nombreArc);
                        } else {
                            pathDTO.setMensaje(Constantes.ERRORGUARDARARCHIVO);
                        }

                    }
                }
                //-------------------CIERRE PROPIETARIOS------------------------
                //------------------------VEHICULOS-----------------------------
                Integer countPoblamientoVehiculos = boletinesTxtRna.countPoblamientoVehiculos(idCarga);
                //Poblamiento Vehículos
                if (countPoblamientoVehiculos != 0) {
                    String nombreArc = "Boletin_RNA_" + autoridad + "_Detalle_Poblamiento_Vehi.txt";
                    List<PoblamientoVehiculos> consultaPoblamiento = boletinesTxtRna.poblamientoVehiculos(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaPoblamiento.isEmpty()) {
                        for (PoblamientoVehiculos pl : consultaPoblamiento) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setPlaca(pl.getPlaca());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                        String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                        byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                        if (archivo != null) {
                            pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                            //se agrega al zip
                            zos = comprimirArchivoZip(zos, archivo, nombreArc);
                        } else {
                            pathDTO.setMensaje("Error al guardar el archivo");
                        }
                    }
                }
                //Civ Vehículos
                Integer countCivVehiculos = boletinesTxtRna.countCivVehiculos(idCarga, autoridad);
                if (countCivVehiculos != 0) {
                    String nombreArc = "Boletin_RNA_" + autoridad + "_Detalle_CIV_Vehi.txt";
                    List<CivVehiculos> consultaCiv = boletinesTxtRna.civVehiculos(idCarga, autoridad);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaCiv.isEmpty()) {
                        for (CivVehiculos pl : consultaCiv) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setPlaca(pl.getPlaca());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                        String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                        byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                        if (archivo != null) {
                            pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                            //se agrega al zip
                            zos = comprimirArchivoZip(zos, archivo, nombreArc);
                        } else {
                            pathDTO.setMensaje(Constantes.ERRORGUARDARARCHIVO);
                        }
                    }
                }
                if (pathDTO.getMensaje() == null) {
                    zos = generarBoletinPdf(idCarga, zos);
                    if (pathDTO.getPathGeneral() != null) {
                        boolean borrarFichero = archivosUtil.deleteFile(pathDTO.getPathGeneral());
                        if (!borrarFichero) {
                            LOG.log(Level.SEVERE, "Error al borrar los archivos");
                        }
                    } else {
                        String constante = System.getProperty("java.io.tmpdir") + idCarga;
                        boolean borrarFichero = archivosUtil.deleteFile(constante);
                        if (!borrarFichero) {
                            LOG.log(Level.SEVERE, "Error al borrar los archivos");
                        }
                    }
                }
                zos.close();
                //---------------------CIERRE VEHICULOS-------------------------
            } catch (IOException | NumberFormatException e) {
                LOG.log(Level.SEVERE, "Error al comprimir la información en el zip {0}", e.getLocalizedMessage());
            }
        }
        return baos.toByteArray();
    }

    /**
     * Metodo que genera los archivos del boletín del registro de RNC
     *
     * @param idCarga
     * @param autoridad
     * @return
     * @throws ListarBoletinException
     */
    public byte[] generarBoletinRncTxt(final Long idCarga, final Long autoridad) throws ListarBoletinException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PathDTO pathDTO = new PathDTO();
        Carga carga = boletinDAO.consultaBoletin(idCarga);
        if (carga != null) {

            //Crear el zip
            try {

                ZipOutputStream zos = new ZipOutputStream(baos);

                //----------------------LICENCIAS-----------------------------------
                //Civ Licencias
                Integer countCivLicencias = boletinesTxtRnc.countCivLice(idCarga);
                if (countCivLicencias != 0) {
                    String nombreArc = "Boletin_RNC_" + autoridad + "_Detalle_CIV_Lice.txt";
                    List<CivLicencias> consultaCiv = boletinesTxtRnc.civLice(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaCiv.isEmpty()) {
                        for (CivLicencias pl : consultaCiv) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setNroLicencias(pl.getLicencias());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                    }
                    String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                    byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                    if (archivo != null) {
                        pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                        //se agrega al zip
                        zos = comprimirArchivoZip(zos, archivo, nombreArc);
                    } else {
                        pathDTO.setMensaje("Error al guardar el archivo");
                    }
                }
                //poblamiento licencias
                Integer countPoblamientoLicencias = boletinesTxtRnc.countPobLicencias(idCarga);
                if (countPoblamientoLicencias != 0) {
                    String nombreArc = "Boletin_RNC_" + autoridad + "_Detalle_Poblamiento_Lice.txt";
                    List<PoblamientoLicencias> consultaPoblamiento = boletinesTxtRnc.poblaLicencias(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaPoblamiento.isEmpty()) {
                        for (PoblamientoLicencias pl : consultaPoblamiento) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setNroLicencias(pl.getLicecondu());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                    }
                    String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                    byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                    if (archivo != null) {
                        pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                        zos = comprimirArchivoZip(zos, archivo, nombreArc);
                    } else {
                        pathDTO.setMensaje("Error al guardar el archivo");
                    }
                }
                //-------------------CIERRA LICENCIAS-------------------------------
                //-----------------------PERSONAS-----------------------------------
                //Civ Personas
                Integer countCivPersonas = boletinesTxtRnc.countCivPers(idCarga);
                if (countCivPersonas != 0) {
                    String nombreArc = "Boletin_RNC_" + autoridad + "_Detalle_CIV_Pers.txt";
                    List<CivPersonas> consultaCiv = boletinesTxtRnc.civPers(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaCiv.isEmpty()) {
                        for (CivPersonas pl : consultaCiv) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setTipoDoc(pl.getTipoDent());
                            cast.setNroLicencias(pl.getNroDent());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                    }
                    String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                    byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                    if (archivo != null) {
                        pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                        zos = comprimirArchivoZip(zos, archivo, nombreArc);
                    } else {
                        pathDTO.setMensaje("Error al guardar el archivo");
                    }
                }
                //Poblamiento Personas
                Integer countPoblamientoPersonas = boletinesTxtRnc.countPobPersonas(idCarga);
                if (countPoblamientoPersonas != 0) {
                    String nombreArc = "Boletin_RNC_" + autoridad + "_Detalle_Poblamiento_Pers.txt";
                    List<PoblamientoPersonas> consultaPoblamiento = boletinesTxtRnc.poblaPersonas(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaPoblamiento.isEmpty()) {
                        for (PoblamientoPersonas pl : consultaPoblamiento) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setTipoDoc(pl.getTipoDent());
                            cast.setNroLicencias(pl.getNroDent());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                    }
                    String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                    byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                    if (archivo != null) {
                        pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                        zos = comprimirArchivoZip(zos, archivo, nombreArc);
                    } else {
                        pathDTO.setMensaje("Error al guardar el archivo");
                    }
                }
                //--------------------CIERRA PERSONAS-------------------------------
                //----------------------RESIDENCIAS---------------------------------
                //Civ Residencias
                Integer countCivResidencias = boletinesTxtRnc.countCivResi(idCarga);
                if (countCivResidencias != 0) {
                    String nombreArc = "Boletin_RNC_" + autoridad + "_Detalle_CIV_Resi.txt";
                    List<CivResidencias> consultaCiv = boletinesTxtRnc.civResi(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaCiv.isEmpty()) {
                        for (CivResidencias pl : consultaCiv) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setTipoDoc(pl.getTipoDent());
                            cast.setNroLicencias(pl.getNroDent());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                    }
                    String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                    byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                    if (archivo != null) {
                        pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                        zos = comprimirArchivoZip(zos, archivo, nombreArc);
                    } else {
                        pathDTO.setMensaje("Error al guardar el archivo");
                    }
                }
                //Poblamiento Residencias
                Integer countPoblamientoResidencias = boletinesTxtRnc.countPobResidencias(idCarga);
                if (countPoblamientoResidencias != 0) {
                    String nombreArc = "Boletin_RNC_" + autoridad + "_Detalle_Poblamiento_Resi.txt";
                    List<PoblamientoResidencias> consultaPoblamiento = boletinesTxtRnc.poblaResidencias(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaPoblamiento.isEmpty()) {
                        for (PoblamientoResidencias pl : consultaPoblamiento) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setTipoDoc(pl.getTipoDent());
                            cast.setNroLicencias(pl.getNroDent());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                    }
                    String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                    byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                    if (archivo != null) {
                        pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                        zos = comprimirArchivoZip(zos, archivo, nombreArc);
                    } else {
                        pathDTO.setMensaje("Error al guardar el archivo");
                    }
                }
                //-------------------CIERRA RESIDENCIAS-----------------------------
                //---------------------RESTRICCIONES--------------------------------
                //Civ Restricciones
                Integer countCivRestricciones = boletinesTxtRnc.countCivRestri(idCarga);
                if (countCivRestricciones != 0) {
                    String nombreArc = "Boletin_RNC_" + autoridad + "_Detalle_CIV_Rest.txt";
                    List<CivRestricciones> consultaCiv = boletinesTxtRnc.civRestri(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaCiv.isEmpty()) {
                        for (CivRestricciones pl : consultaCiv) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setNroLicencias(pl.getLicecondu());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                    }
                    String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                    byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                    if (archivo != null) {
                        pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                        zos = comprimirArchivoZip(zos, archivo, nombreArc);
                    } else {
                        pathDTO.setMensaje("Error al guardar el archivo");
                    }
                }
                //Poblamiento Restricciones
                Integer countPoblamientoRestricciones = boletinesTxtRnc.countPobRestricciones(idCarga);
                if (countPoblamientoRestricciones != 0) {
                    String nombreArc = "Boletin_RNC_" + autoridad + "_Detalle_Poblamiento_Rest.txt";
                    List<PoblamientoRestricciones> consultaPoblamiento = boletinesTxtRnc.poblaRestricciones(idCarga);
                    List<DatosBoletinTxtRncDTO> listDTO = new ArrayList<>();
                    if (!consultaPoblamiento.isEmpty()) {
                        for (PoblamientoRestricciones pl : consultaPoblamiento) {
                            DatosBoletinTxtRncDTO cast = new DatosBoletinTxtRncDTO();
                            cast.setNroLicencias(pl.getLiceCondu());
                            cast.setCodError(pl.getCodCriterio());
                            cast.setDescripcion(pl.getDescripcionError());
                            listDTO.add(cast);
                        }
                    }
                    String datosArc = escribirArchivoPlanoListarBoletin(listDTO);
                    byte[] archivo = getArchivoPlano(nombreArc, datosArc);
                    if (archivo != null) {
                        pathDTO = guardarArchivosAdjuntos(archivo, idCarga, nombreArc);
                        zos = comprimirArchivoZip(zos, archivo, nombreArc);
                    } else {
                        pathDTO.setMensaje("Error al guardar el archivo");
                    }
                }
                if (pathDTO.getMensaje() == null) {
                    zos = generarBoletinPdf(idCarga, zos);
                    if (pathDTO.getPathGeneral() != null) {
                        boolean borrarFichero = archivosUtil.deleteFile(pathDTO.getPathGeneral());
                        if (!borrarFichero) {
                            LOG.log(Level.SEVERE, "Error al borrar los archivos");
                        }
                    } else {
                        String constante = System.getProperty("java.io.tmpdir") + idCarga;
                        boolean borrarFichero = archivosUtil.deleteFile(constante);
                        if (!borrarFichero) {
                            LOG.log(Level.SEVERE, "Error al borrar los archivos");
                        }
                    }
                }
                zos.close();
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Error al comprimir la información en el zip {0}", e.getLocalizedMessage());
            }
            //------------------CIERRA RESTRICCIONES----------------------------
        }
        return baos.toByteArray();
    }

    private ZipOutputStream comprimirArchivoZip(ZipOutputStream zip, byte[] archivo, String nombre) {
        try {
            ZipEntry ze = new ZipEntry(nombre);
            zip.putNextEntry(ze);
            zip.write(archivo);
            zip.closeEntry();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error al guardar contenido en el zip {0}", e.getLocalizedMessage());
        }
        return zip;
    }

    private String escribirArchivoPlanoListarBoletin(List<DatosBoletinTxtRncDTO> contenido) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constantes.NUEVA_LINEA_ARCHIVO_TXT);
        if (contenido != null) {
            for (DatosBoletinTxtRncDTO datos : contenido) {
                if (datos.getCodCarga() != null) {
                    sb.append(datos.getCodCarga());
                    sb.append(Constantes.SEPARADOR_ARCHIVO_TXT);
                }
                if (datos.getSecretaria() != null) {
                    sb.append(datos.getSecretaria());
                    sb.append(Constantes.SEPARADOR_ARCHIVO_TXT);
                }
                if (datos.getTipoDoc() != null) {
                    sb.append(datos.getTipoDoc());
                    sb.append(Constantes.SEPARADOR_ARCHIVO_TXT);
                }
                if (datos.getNroLicencias() != null) {
                    sb.append(datos.getNroLicencias());
                    sb.append(Constantes.SEPARADOR_ARCHIVO_TXT);
                }
                if (datos.getFecha() != null) {
                    sb.append(datos.getFecha());
                    sb.append(Constantes.SEPARADOR_ARCHIVO_TXT);
                }
                if (datos.getPlaca() != null) {
                    sb.append(datos.getPlaca());
                    sb.append(Constantes.SEPARADOR_ARCHIVO_TXT);
                }

                if (datos.getCodError() != null) {
                    sb.append(datos.getCodError());
                    sb.append(Constantes.SEPARADOR_ARCHIVO_TXT);
                }
                if (datos.getDescripcion() != null) {
                    sb.append(datos.getDescripcion());
                    sb.append(Constantes.SEPARADOR_ARCHIVO_TXT);
                }
                sb.append(Constantes.NUEVA_LINEA_ARCHIVO_TXT);
            }
        }
        return sb.toString();
    }

    private byte[] getArchivoPlano(String nombre, String contenido) {
        File file = new File(nombre);
        try (FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.write(contenido);

            printWriter.close();
            bufferedWriter.close();

            InputStream inputstream = new FileInputStream(file);

            return IOUtils.toByteArray(inputstream);
        } catch (IOException ex) {
            Logger.getLogger(CargueCiaLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new byte[0];
    }

    private PathDTO guardarArchivosAdjuntos(byte[] archivo, Long idCargue, String nombreArchivo) {
        PathDTO path = new PathDTO();
        String constante = System.getProperty("java.io.tmpdir");
        StringBuilder pathCarpeta = new StringBuilder();
        String pathBase;
        String pathFile;
        if (constante != null) {
            try {
                pathBase = constante + idCargue;//constante.getConstanteValor();
                pathCarpeta.append(pathBase);
                File fileSystem = new File(pathCarpeta.toString());
                // Se crea el directorio principal
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                path.setPathGeneral(pathCarpeta.toString());

                LOG.log(Level.INFO, "=== PATH CARPETA SOLICITUD : {0}", pathCarpeta.toString());

                if (archivo != null) {

                    pathFile = pathCarpeta.toString() + "/" + nombreArchivo;
                    LOG.log(Level.INFO, "=== PATH CARPETA GUARDA : {0}", pathFile);
                    FileUtils.writeByteArrayToFile(new File(pathFile), archivo);

                }
            } catch (IOException e) {
                LOG.log(Level.INFO, "Error en guardarArchivosAdjuntos : {0}", e.getCause());
            }
        }
        return path;
    }

}
