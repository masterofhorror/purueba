/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CargueArchivoDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.CargueArchivoCiaDTO;
import co.com.runt.sagir.dto.CargueArchivoRespuestaDTO;
import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Casteador;
import co.com.runt.sagir.dao.BaseDAO;
import co.com.runt.sagir.dao.CargueCiaDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.ArchivoCiaDTO;
import co.com.runt.sagir.dto.ArchivoCiaSalidaDTO;
import co.com.runt.sagir.dto.CargueCiaDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.CiaCargue;
import co.com.runt.sagir.entities.ComConstantes;
import co.com.runt.sagir.entities.ConsultaErroresTxtCia;
import co.com.runt.sagir.entities.LogDatosArchivo;
import co.com.runt.sagir.entities.PaNitCia;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.servicios.CargueArchivoCiaService;
import co.com.runt.sagir.utils.ListarBoletinException;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author APENA
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CargueCiaLogica {

    @Resource
    protected EJBContext context;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private CargueCiaDAO cargueCiaDAO;

    @EJB
    private Casteador casteador;

    @EJB
    private CargueArchivoDAO cargueArchivoDAO;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private CargueArchivoCiaService cargueArchivoCiaService;

    private EntityManager entityManager;

    private static final Logger LOG = Logger.getLogger(CargueCiaLogica.class.getSimpleName());

    private static final String SEPARADOR_ARCHIVO_TXT = " | ";

    private static final String NUEVA_LINEA_ARCHIVO_TXT = "\r\n";

    public MensajeDTO validaArchivo(CargueArchivoCiaDTO data, final InfoUsuarioDTO usuario, final String ip) {
        CargueArchivoRespuestaDTO respuestaValidaDTO;
        MensajeDTO respuestaDTO = new MensajeDTO();
        String nitCia = data.getNitCia();
        String mensaje;
        String user = usuario.getLogin();
        Long autoridad = usuario.getIdOrganizacion();

        if (nitCia != null) {
            respuestaValidaDTO = cargarArchivo(data, user, autoridad.toString(), ip);

            Long idCargue = respuestaValidaDTO.getIdCargue();

            respuestaDTO = procesarCia(idCargue, nitCia, user);

        } else {
            mensaje = "Se genero un error al cargar el archivo plano.";
            respuestaDTO.setMensaje(mensaje);
            respuestaDTO.setCodmensaje(Mensajes.ERROR);
            Logger.getLogger(CargueCiaLogica.class.getName()).log(Level.SEVERE, null, mensaje);
        }
        return respuestaDTO;
    }

    private MensajeDTO procesarCia(final long idCargue, final String nitCia, final String user) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;

        try {
            SgCarguearchivos validaArchivo = constanteDAO.consultaById(idCargue);

            String ruta = validaArchivo.getCarguearchivosDatos();
            String archivo = validaArchivo.getCarguearchivosNombreDatos();

            if (validaArchivo != null) {
                if (Constantes.CARGUE_CIA.equals(validaArchivo.getTipoCargue())) {
                    MensajeDTO procesoCia = procesarArchivoCia(nitCia, ruta, archivo, user);
                    salida.setCodmensaje(procesoCia.getCodmensaje());
                    salida.setMensaje(procesoCia.getMensaje());
                    salida.setObject(procesoCia.getObject());
                }

            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = "Error al consultar la informacion relacionada con el archivo plano.";
                salida.setMensaje(mensaje);
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }

        return salida;
    }

    public MensajeDTO procesarArchivoCia(final String nitCia, final String ruta, final String nombreArc, final String user) {
        MensajeDTO salida = new MensajeDTO();
        boolean errores;
        Long userStr = Long.parseLong(user);
        List<ArchivoCiaDTO> listaDTO = datosArchivo(ruta, nombreArc, nitCia);
        ArchivoCiaSalidaDTO datosArchivoSalida = new ArchivoCiaSalidaDTO();

        String secCia = Long.toString(cargueCiaDAO.secuenciaCia());
        Date fechaCarga = new Date();
        String desMigrado = null;
        String observacion = null;
        String nombreArchivo = nombreArc;
        String estadoReporte = "ACTIVO";
        String codSede = null;
        datosArchivoSalida.setCodCarga(secCia);
        datosArchivoSalida.setNombreArchivo(nombreArchivo);

        if (secCia != null) {
            if (listaDTO != null && !listaDTO.isEmpty()) {
                boolean tablaCia = false;
                int procesoCorrecto = 0;
                for (ArchivoCiaDTO cast : listaDTO) {
                    cast.setCodCarga(secCia);
                    cast.setFechaCarga(fechaCarga);
                    cast.setDesMigrado(desMigrado);
                    cast.setObservacion(observacion);
                    cast.setNombreArchivo(nombreArchivo);
                    cast.setEstadoReporte(estadoReporte);
                    cast.setCodSede(codSede);

                    CiaCargue ciaCargue = TransformacionDozer.transformar(cast, CiaCargue.class);

                    //guardar log tbl_logdatosarchivos
                    String tipoArchivo = Constantes.TIPO_ARCHIVO_CIA;
                    LogDatosArchivo log = casteador.castLogDatosArchivo(cast.getLineaCompleta(), nombreArc, userStr, tipoArchivo);
                    boolean registroLog = constanteDAO.guardarLogDatoArchivo(log);

                    if (registroLog) {

                        errores = false;
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        salida.setMensaje("Registro de Log incorrecto");
                        errores = true;
                    }

                    if (!errores) {
                        //inserta en cia_cargue
                        tablaCia = cargueCiaDAO.guardarTblArchivoCia(ciaCargue);
                    }

                    if (tablaCia) {
                        procesoCorrecto++;
                    }

                }

                if (procesoCorrecto != 0) {
                    String pAlmacenado = almacenados.pValidarCargueCia(secCia, nombreArchivo);

                    BigDecimal resultadoAlmacenado = new BigDecimal(pAlmacenado);
                    BigDecimal aprobado = new BigDecimal(1);
                    int res = resultadoAlmacenado.compareTo(aprobado);
                    if (res == 0) {
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setMensaje("El proceso finalizo exitosamente.");
                        datosArchivoSalida.setEstado("PROCESADO");
                        salida.setObject(datosArchivoSalida);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        salida.setMensaje("No se Proceso el Archivo por CIA Inactivo.");
                        datosArchivoSalida.setEstado("NO PROCESADO");
                        salida.setObject(datosArchivoSalida);
                    }

                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    salida.setMensaje("Archivo mal formado o vacio.");
                }
            }
        }
        return salida;
    }

    public List<ArchivoCiaDTO> datosArchivo(final String ruta, final String nombreArc, final String nitCia) {
        List<ArchivoCiaDTO> listDTO = new ArrayList<>();

        try {
            byte[] archivo = desfirmaArchivo(ruta, nombreArc, nitCia);
            StringTokenizer st;

            st = new StringTokenizer(new String(archivo, "UTF-8"), "\n");
            if (st == null) {
                throw new RuntimeException("Archivo mal formado o vacio");
            }

            if (st.hasMoreElements()) {
                try {
                    int linea = 0;
                    String registro;
                    while (st.hasMoreTokens()) {
                        ArchivoCiaDTO cast = new ArchivoCiaDTO();
                        linea++;
                        String arcLinea = String.valueOf(linea);

                        registro = (String) st.nextElement() + " ";

                        String[] lista = registro.split("\\|");

                        if (lista.length > 0) {
                            String validaCampo1 = lista[0];
                            String arcCamp1;
                            String arcCamp24;
                            String validaCampo2 = validaCampo1.trim();

                            if (linea == 1) {
                                try {
                                    validaCampo2 = validaCampo2.substring(1);
                                } catch (Exception e) {
                                    validaCampo2 = validaCampo2;
                                }
                            }

                            if (validaCampo2.length() != 0) {

                                if (linea == 1) {
                                    arcCamp1 = validaCampo2;
                                } else {
                                    arcCamp1 = lista[0];
                                }

                                String arcCamp2 = lista[1];
                                String arcCamp3 = lista[2];
                                String arcCamp4 = lista[3];
                                String arcCamp5 = lista[4];
                                String arcCamp6 = lista[5];
                                String arcCamp7 = lista[6];
                                String arcCamp8 = lista[7];
                                String arcCamp9 = lista[8];
                                String arcCamp10 = lista[9];
                                String arcCamp11 = lista[10];
                                String arcCamp12 = lista[11];
                                String arcCamp13 = lista[12];
                                String arcCamp14 = lista[13];
                                String arcCamp15 = lista[14];
                                String arcCamp16 = lista[15];
                                String arcCamp17 = lista[16];
                                String arcCamp18 = lista[17];
                                String arcCamp19 = lista[18];
                                String arcCamp20 = lista[19];
                                String arcCamp21 = lista[20];
                                String arcCamp22 = lista[21];
                                String arcCamp23 = lista[22];

                                if (lista.length > 23) {
                                    arcCamp24 = lista[23];
                                } else {
                                    arcCamp24 = null;
                                }

                                cast.setSecuenciaValidacion(arcCamp1);
                                cast.setTipoCargue(arcCamp2);
                                cast.setIdentificacionSede(arcCamp3);
                                cast.setNumeroCertificadoCia(arcCamp4);
                                cast.setTipoDocumentoAlumno(arcCamp5);
                                cast.setNumeroDocumentoAlumno(arcCamp6);
                                cast.setNombresAlumno(arcCamp7);
                                cast.setApellidosAlumno(arcCamp8);
                                cast.setTipoSancion(arcCamp9);
                                cast.setNumeroComparendo(arcCamp10);
                                cast.setFechaComparendo(arcCamp11);
                                cast.setDepartamentoComparendo(arcCamp12);
                                cast.setCiudadComparendo(arcCamp13);
                                cast.setNumeroFallo(arcCamp14);
                                cast.setFechaFallo(arcCamp15);
                                cast.setEntidadEmiteFallo(arcCamp16);
                                cast.setFechaCurso(arcCamp17);
                                cast.setHoraInicioCurso(arcCamp18);
                                cast.setHoraFinCurso(arcCamp19);
                                cast.setValorRecaudoCia(arcCamp20);
                                cast.setTipoIdentificacionInstructor(arcCamp21);
                                cast.setNumeroIdentificacionInstructor(arcCamp22);
                                cast.setResultadoCurso(arcCamp23);
                                cast.setRegistroControl(arcCamp24);
                                cast.setNumReg(arcLinea);
                                if (linea == 1) {
                                    registro = registro.substring(1);
                                    cast.setLineaCompleta(registro);
                                } else {
                                    cast.setLineaCompleta(registro);
                                }
                                listDTO.add(cast);

                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, e.getMessage());
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CargueCiaLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDTO;
    }

    public byte[] desfirmaArchivo(final String ruta, final String archivo, final String nitCia) {
        try {
            File dir = new File(ruta);
            byte[] data;
            data = convertirArchivo(dir);

            if (data == null) {
                LOG.log(Level.SEVERE, "Error en el archivo...");
            }

            SgCarguearchivos cargue = cargueCiaDAO.consultaArchivo(archivo, ruta);

            try {
                byte[] contenido = cargueArchivoCiaService.validarCertificado(nitCia, Base64.encodeBase64String(data), nitCia);

                if (contenido == null) {
                    UserTransaction ut = context.getUserTransaction();
                    ut.begin();
                    cargue.setCarguearchivosEstado(CargueArchivoMensajes.RECHAZADO.toString());
                    entityManager.merge(cargue);
                    entityManager.flush();
                    ut.commit();
                } else {
                    return contenido;
                }

            } catch (Exception ex) {
                Logger.getLogger(CargueCiaLogica.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(CargueCiaLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public byte[] convertirArchivo(File ficheros) {
        byte[] bFile = new byte[(int) ficheros.length()];

        try (FileInputStream fileInputStream = new FileInputStream(ficheros)) {

            fileInputStream.read(bFile);

            for (int i = 0; i < bFile.length; i++) {
                System.out.print((char) bFile[i]);
            }

            System.out.println("Done");

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return bFile;
    }

    public CargueArchivoRespuestaDTO cargarArchivo(CargueArchivoCiaDTO data, final String user, final String autoridad, final String ip) {

        SgCarguearchivos cargue = new SgCarguearchivos();
        CargueArchivoRespuestaDTO respuestaDTO = new CargueArchivoRespuestaDTO();

        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();

            cargue.setCarguearchivosEstado(CargueArchivoMensajes.SIN_PROCESAR.toString());
            cargue.setCarguearchivosFecha(new Date());
            cargue.setCarguearchivosNombreDatos(data.getNombre());
            cargue.setTipoCargue(data.getTipoCargue());

            cargue.setCarguearchivosUsuario(user);
            cargue.setCarguearchivosIdautra(autoridad);

            cargue.setCarguearchivosIp(ip);

            cargueArchivoDAO.insertCargue(cargue);
            Long idCargue = cargue.getCarguearchivosId();
            ut.commit();

            respuestaDTO.setIdCargue(idCargue);

            String pathArchivo = guardarArchivosAdjuntos(data, cargue.getCarguearchivosId());
            cargue.setCarguearchivosDatos(pathArchivo);

            cargueArchivoDAO.actualizarPathFile(cargue);

        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException e) {
            respuestaDTO.setEstado(CargueArchivoMensajes.RECHAZADO);
            Logger.getLogger(CargueCiaLogica.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotSupportedException ex) {
            Logger.getLogger(CargueCiaLogica.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuestaDTO;
    }

    private String guardarArchivosAdjuntos(CargueArchivoCiaDTO listaAdjuntos, Long idCargue) {

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
                // Se crea el directorio principal
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

                if (listaAdjuntos != null) {

                    pathFile = pathCarpeta.toString() + "/" + listaAdjuntos.getNombre();
                    LOG.log(Level.INFO, "=== PATH ARCHIVO : {0}", pathFile);

                    if (listaAdjuntos.getIdArchivo() == null) {
                        byte[] file = Base64.decodeBase64(listaAdjuntos.getArchivo());
                        FileUtils.writeByteArrayToFile(new File(pathFile), file);
                    }
                }
            } catch (IOException e) {
                LOG.log(Level.INFO, "Error en guardarArchivosAdjuntos : {0}", e.getCause());
            }
        }

        return pathFile;
    }
    
    public MensajeDTO consultaExisteCargaCia(final Long CodCarga){
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        
        Long existeCarga = cargueCiaDAO.countExisteCarga(CodCarga);
        
        if(existeCarga != 0){
            salida.setCodmensaje(Mensajes.OK);
        }else{
            mensaje = "El codigo de carga no existe en CIA_CARGUE.";
            salida.setMensaje(mensaje);
            salida.setCodmensaje(Mensajes.ERROR);
        }
        return salida;
    }

    public MensajeDTO listarCia() {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<CargueCiaDTO> listDTO;
        List<PaNitCia> consulta = constanteDAO.listarCia();
        if (consulta != null && !consulta.isEmpty()) {
            listDTO = TransformacionDozer.transformar(consulta, CargueCiaDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = "Error al consultar las Cias parametrizadas";
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public byte[] descargarPDF(final Long codCarga) {
        try {
            return generarBoletinCiaPdf(codCarga);
        } catch (ListarBoletinException ex) {
            ex.getLocalizedMessage();
        }
        return new byte[0];
    }

    public byte[] generarBoletinCiaPdf(final Long codCarga) throws ListarBoletinException {
        Connection connection = null;

        if (codCarga != null) {
            try {
                InputStream is = CargueCiaLogica.class.getResourceAsStream("BOLETIN_CIA.jasper");
                Map<String, Object> parameters = new HashMap<>();

                parameters.put("P_CARGA", codCarga.toString());
                parameters.put("$P{P_SUB_ERRORES}", CargueCiaLogica.class.getResourceAsStream("BOLETIN_CIA_ERROR_PDF.jasper"));
                parameters.put("$P{P_CARGA}", codCarga.toString());

                connection = BaseDAO.getConnection();

                JasperPrint jasperPrint = JasperFillManager.fillReport(is, parameters, connection);

                byte[] archivo = JasperExportManager.exportReportToPdf(jasperPrint);

                return archivo;
            } catch (SQLException | JRException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new ListarBoletinException(ex);
            } catch (NamingException e) {
                LOG.log(Level.SEVERE, null, e);
                throw new ListarBoletinException(e);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        LOG.log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return new byte[0];
    }

    public byte[] generarBoletinCiaTxt(final Long codCarga) throws ListarBoletinException {
        Long existeError = cargueCiaDAO.countErroresTxt(codCarga);

        if (existeError != 0) {
            List<ConsultaErroresTxtCia> lista = cargueCiaDAO.consultaErroresTxt(codCarga);

            String datosArc = escribirArchivoPlanoConsultaCia(lista);
            String nombreArc = "ERRORES_CARGUE_" + codCarga + ".txt";

            byte[] archivo = getArchivoPlano(nombreArc, datosArc);

            return archivo;
        }
        return new byte[0];
    }

    private String escribirArchivoPlanoConsultaCia(List<ConsultaErroresTxtCia> contenido) {
        StringBuilder sb = new StringBuilder();

        sb.append("nro_comparendo");
        sb.append(SEPARADOR_ARCHIVO_TXT);
        sb.append("tipo_doc_alumno");
        sb.append(SEPARADOR_ARCHIVO_TXT);
        sb.append("nro_doc_alumno");
        sb.append(SEPARADOR_ARCHIVO_TXT);
        sb.append("nro_certificado");
        sb.append(SEPARADOR_ARCHIVO_TXT);
        sb.append("sec_validacion");
        sb.append(SEPARADOR_ARCHIVO_TXT);
        sb.append("nombre_archivo");
        sb.append(SEPARADOR_ARCHIVO_TXT);
        sb.append("cod_criterio");
        sb.append(SEPARADOR_ARCHIVO_TXT);
        sb.append("error_estandar");

        sb.append(NUEVA_LINEA_ARCHIVO_TXT);
        sb.append(NUEVA_LINEA_ARCHIVO_TXT);

        if (contenido != null) {
            for (ConsultaErroresTxtCia consultaErroresTxtCia : contenido) {
                sb.append(consultaErroresTxtCia.getNroComparendo());
                sb.append(SEPARADOR_ARCHIVO_TXT);
                sb.append(consultaErroresTxtCia.getTipoDocAlumno());
                sb.append(SEPARADOR_ARCHIVO_TXT);
                sb.append(consultaErroresTxtCia.getNroDocAlumno());
                sb.append(SEPARADOR_ARCHIVO_TXT);
                sb.append(consultaErroresTxtCia.getNroCertificado());
                sb.append(SEPARADOR_ARCHIVO_TXT);
                sb.append(consultaErroresTxtCia.getSecValidacion());
                sb.append(SEPARADOR_ARCHIVO_TXT);
                sb.append(consultaErroresTxtCia.getNombreArchivo());
                sb.append(SEPARADOR_ARCHIVO_TXT);
                sb.append(consultaErroresTxtCia.getCodCriterio());
                sb.append(SEPARADOR_ARCHIVO_TXT);
                sb.append(consultaErroresTxtCia.getErrorEstandar());
                sb.append(NUEVA_LINEA_ARCHIVO_TXT);

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

}
