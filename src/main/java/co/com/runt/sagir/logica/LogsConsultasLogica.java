package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.logconsultas.api.MensajeDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.dao.ConsultaLogsDAONativos;
import co.com.runt.sagir.dao.LogConsultaDAO;
import co.com.runt.sagir.dao.PaAplicacionDAO;
import co.com.runt.sagir.dao.PaTipoconsultaDAO;
import co.com.runt.sagir.dto.ConsultaLogsInDTO;
import co.com.runt.sagir.dto.ConsultaLogsOutDTO;
import co.com.runt.sagir.entities.LogConsulta;
import co.com.runt.sagir.entities.PaAplicacion;
import co.com.runt.sagir.entities.PaTipoconsulta;
import com.google.gson.JsonObject;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Dospina
 */
@Stateless
public class LogsConsultasLogica {
    
    @EJB
    private LogsConsultasLogica guardarLogLogica;
    
    protected final static String LOG_IP_HEADER = "X-Forwarded-For";
    
    private final static String DATOS_CONSULTADOS = "DATOS_CONSULTADOS";
    
    private static final Logger LOG = Logger.getLogger(LogsConsultasLogica.class.getName());
    
    @EJB
    private LogConsultaDAO logConsultaDAO;
    
    @EJB
    private PaAplicacionDAO paAplicacionDAO;
    
    @EJB
    private PaTipoconsultaDAO paTipoconsultaDAO;
    
    
    
    public MensajeDTO guardarLog( final LogConsultaDTO entrada){        
                
        PaAplicacion app = paAplicacionDAO.getByCodigo(entrada.getAplicacion());
        PaTipoconsulta tipoCons = paTipoconsultaDAO.getByCodigo(entrada.getTipoConsulta());        
        LogConsulta log = logConsultaDAO.construirLog(entrada.getUsuario(), app, tipoCons, entrada.getAutoridad(), entrada.getIpCliente(), entrada.getConsulta());
        
        MensajeDTO mensaje = validarDatosLog(log);
        if(MensajeDTO.CodigoRespuesta.Exitoso.equals(mensaje.getCodigoResultado())
                || MensajeDTO.Severidad.INFO.equals(mensaje.getSeveridad())){
            logConsultaDAO.guardarLogConsulta(log);
        }else{
            LOG.log(Level.INFO, "{0}", "Error: "+mensaje.getCodigoResultado() + ", " + mensaje.getDescripcionRespuesta());
        }
        return mensaje;
    }
    
    private MensajeDTO validarDatosLog(LogConsulta log){
        MensajeDTO mensaje = new MensajeDTO();
        mensaje.setCodigoResultado(MensajeDTO.CodigoRespuesta.Exitoso);
        mensaje.setSeveridad(MensajeDTO.Severidad.INFO);
        
        StringBuilder error = new StringBuilder();
        
        if(log.getConsultaUsuario() == null || "".equals(log.getConsultaUsuario())){
            error.append("No se recibió la información del usuario. ");
            log.setConsultaUsuario("No Registrada");
        }
        if(log.getConsultaAplicacionCodigo() == null || "".equals(log.getConsultaAplicacionCodigo().getAplicacionCodigo())){
            error.append("El código de aplicación de consulta no se encuentra registrado. "); 
            mensaje.setSeveridad(MensajeDTO.Severidad.ERROR);
        }
        if(log.getConsultaTipoconsultaCodigo() == null || "".equals(log.getConsultaTipoconsultaCodigo().getTipoconsultaCodigo())){
            error.append("El código del tipo de consulta no se encuentra registrado. ");
            mensaje.setSeveridad(MensajeDTO.Severidad.ERROR);
        }
        if(log.getConsultaAutoridad() == null || "".equals(log.getConsultaAutoridad())){
            error.append("No se recibió la información de la autoridad que consulta. ");
            log.setConsultaAutoridad(0L);
        }
        if(log.getConsultaIp() == null || "".equals(log.getConsultaIp())){
            error.append("No se recibió la información de la IP de consulta. ");
            log.setConsultaIp("No Registrada.");
        }
        if(log.getConsultaEntrada() == null || "".equals(log.getConsultaEntrada())){
            error.append("No se recibió la información de los datos de entrada de la consulta. ");
            log.setConsultaEntrada("No se recuperaron los parametros de entrada.");
        }
        
        if(error.length()>0){
            mensaje.setCodigoResultado(MensajeDTO.CodigoRespuesta.Fallido);            
            mensaje.setDescripcionRespuesta(error.toString());
            mensaje.setFecha(new Date().toString());
        }
        
        return mensaje;
    }
    
    public List<PaAplicacion> getAllApps() {
        return paAplicacionDAO.getAll();
    }
    
    public List<PaTipoconsulta> getAllTipoConsulta() {
        return paTipoconsultaDAO.getAll();
    }
    
    public List<ConsultaLogsOutDTO> getLogs(ConsultaLogsInDTO consulta, HttpServletRequest request){
        try {
            List<ConsultaLogsOutDTO> respuesta = ConsultaLogsDAONativos.consultarLog(consulta);
            request.getSession().setAttribute(DATOS_CONSULTADOS, respuesta);
            
            JsonObject json = new JsonObject();
            json.addProperty("fechaInicio", consulta.getFechaInicio());
            json.addProperty("fechaFin", consulta.getFechaFin());
            json.addProperty("entrada", consulta.getEntrada());
            json.addProperty("autoridad", consulta.getAutoridad());
            json.addProperty("usuario", consulta.getUsuario());
            json.addProperty("ip", consulta.getIp());
            json.addProperty("aplicacion", (consulta.getAplicacion() != null? consulta.getAplicacion().getAplicacionNombre() : null));
            json.addProperty("tipoconsulta", (consulta.getTipoconsulta() != null? consulta.getTipoconsulta().getTipoconsultaNombre() : null));  
            
            String ipCliente;
            if (request.getHeader(LOG_IP_HEADER) != null) {
                ipCliente = request.getHeader(LOG_IP_HEADER);
            } else {
                ipCliente = request.getRemoteAddr();
            }
            
            LogConsultaDTO log = new LogConsultaDTO();
            log.setAplicacion(null);
            log.setTipoConsulta(null);
            log.setConsulta(json.toString());
            log.setUsuario((String) request.getSession().getAttribute("usuario.cedula"));
            log.setAutoridad((Long) request.getSession().getAttribute("usuario.organizacion"));
            log.setIpCliente(ipCliente);
            
            guardarLogLogica.guardarLog(log);
            
            return respuesta;
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(LogsConsultasLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.emptyList();
    }
    
    public byte[] getReporte(HttpServletRequest request) {
        try {            
            final List<ConsultaLogsOutDTO> reporte = (List<ConsultaLogsOutDTO>)request.getSession().getAttribute(DATOS_CONSULTADOS);;

            LOG.log(Level.INFO, "Generando archivo excel {0}", new Object[] {reporte});

            // llenar la plantilla
            InputStream fis = LogsConsultasLogica.class.getResourceAsStream("ReporteLogs.xls");
            HSSFWorkbook workbook = new HSSFWorkbook(fis);

            // repetir por cada hoja
            HSSFSheet sheet1 = workbook.getSheetAt(0);


            List<Object[]> listaResultado = new ArrayList<>();
            Object[] object;			 			

            if (reporte != null && !reporte.isEmpty()) {

                    LOG.log(Level.INFO, "Generando archivo excel -> {0}", reporte.size());

                    for (ConsultaLogsOutDTO det : reporte) {
                            object = new Object[]{det.getFecha(), det.getAplicacion(), det.getTipoconsulta(),
                                            det.getEntrada(), det.getUsuario(), det.getAutoridad(), det.getIp()};
                            listaResultado.add(object);
                    }
            }

            try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //workbook.write(baos);
                    baos.close();
                    fis.close();
                    return baos.toByteArray();
            } catch (FileNotFoundException e) {
                    LOG.log(Level.SEVERE, null, e);
            } catch (IOException e) {
                    LOG.log(Level.SEVERE, null, e);
            }

//        } catch (FileNotFoundException ex) {
//                LOG.log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//                LOG.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LogsConsultasLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
    return new byte[0];
        
    }
    
    public LogConsultaDTO casteadorLogRNA(JsonObject json, final Long idauttra, final String usuario, final String ip, final String tipoConsulta) throws NumberFormatException {
        //guardado
        LogConsultaDTO log = new LogConsultaDTO();
        log.setAplicacion(Constantes.APLICATION_CONSULTA);
        log.setAutoridad(idauttra);
        log.setConsulta(json.toString());
        log.setIpCliente(ip);
        log.setTipoConsulta(tipoConsulta);
        log.setUsuario(usuario);
        return log;
    }

    public LogConsultaDTO casteadorLogRNC(JsonObject json, final Long idauttra, final String usuario, final String ip, final String tipoConsulta) throws NumberFormatException {
        //guardado
        LogConsultaDTO log = new LogConsultaDTO();
        log.setAplicacion(Constantes.APLICATION_CONSULTA);
        log.setAutoridad(idauttra);
        log.setConsulta(json.toString());
        log.setIpCliente(ip);
        log.setTipoConsulta(tipoConsulta);
        log.setUsuario(usuario);
        return log;
    }

}
