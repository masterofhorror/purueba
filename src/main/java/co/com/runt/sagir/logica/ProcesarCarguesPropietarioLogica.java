/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CambioPropietarioAutomotorDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.CargueArchivoRespuestaDTO;
import co.com.runt.sagir.dto.ConfiguracionProcesamientoDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.SgCarguearchivosDTO;
import co.com.runt.sagir.entities.ComConstantes;
import co.com.runt.sagir.entities.SgCarguearchivos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 *
 * @author dsalamanca
 */
@Singleton
public class ProcesarCarguesPropietarioLogica {

    private static final Logger LOG = Logger.getLogger(ProcesarCarguesPropietarioLogica.class.getName());
    
    @EJB
    private ConstanteDAO constanteDAO;
    
    @EJB
    private CambioPropietarioAutomotorDAO automotorDAO;
    
    @EJB
    private ProcesarCarguesPendientesLogica procesarCarguesPendientesLogica;
    
    @EJB
    private PoblarTablasPropietarioLogica tablasPropietario;
    
    private ConcurrentHashMap<String, Integer> hilos = new ConcurrentHashMap<>();
    private ArrayList<SgCarguearchivosDTO> cargues = new ArrayList<>();
    
    @Lock(LockType.WRITE)
    public List<SgCarguearchivosDTO> cargarTareasIniciales(ConfiguracionProcesamientoDTO parametrosEjecucion){
        hilos = new ConcurrentHashMap<>();
        cargues = new ArrayList<>();
        int tamano = parametrosEjecucion.getHilos();
        List<SgCarguearchivosDTO> carguesIniciales = new ArrayList<>(tamano);
        consultarCargues();
        HashMap<String,String> autoridades = new HashMap<>();
        int total = 0;
        //System.out.println("Tamano " + cargues.size());
        for ( SgCarguearchivosDTO cargue : cargues ) {
            //System.out.println(cargue.getCargueId());
            String idAutoridad = cargue.getCarguearchivosIdautra();
            if ( !autoridades.containsKey(idAutoridad)) {
                autoridades.put(idAutoridad,idAutoridad);
                carguesIniciales.add(cargue);
                total++;
            }
            if ( total == tamano ) {
                break;
            }
        }
        return carguesIniciales;
    }

    @Lock(LockType.WRITE)
    public SgCarguearchivosDTO obtenerSiguienteCargue(int numHilo) {
        for (SgCarguearchivosDTO cargue : cargues) {
            if (cargue.getCarguearchivosEstado().equals(CargueArchivoMensajes.SIN_PROCESAR.name())) {
                String idAutoridad = cargue.getCarguearchivosIdautra();
                //System.out.println("Autoridad cargue " + idAutoridad);
                if (!hilos.containsKey(idAutoridad)) {
                    hilos.put(idAutoridad, numHilo);
                    return cargue;
                } else {
                    if (numHilo == hilos.get(idAutoridad)) {
                        return cargue;
                    }
                }
            }
        }
        return null;
    }
    
    private void consultarCargues(){
        List<SgCarguearchivos> carguesBD = automotorDAO
                    .consultarCargues(CargueArchivoMensajes.SIN_PROCESAR.toString());
        //System.out.println("Cargue bd " + carguesBD.size());
        if(carguesBD != null && !carguesBD.isEmpty()){
            
            for(SgCarguearchivos cargue : carguesBD){
                SgCarguearchivosDTO cargueDTO = new SgCarguearchivosDTO();
                cargueDTO.setCarguearchivosId(cargue.getCarguearchivosId());
                cargueDTO.setCarguearchivosDatos(cargue.getCarguearchivosDatos());
                cargueDTO.setCarguearchivosEstado(cargue.getCarguearchivosEstado());
                cargueDTO.setCarguearchivosFecha(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(cargue.getCarguearchivosFecha()));
                cargueDTO.setCarguearchivosIdautra(cargue.getCarguearchivosIdautra());
                cargueDTO.setCarguearchivosIp(cargue.getCarguearchivosIp());
                cargueDTO.setCarguearchivosNombreDatos(cargue.getCarguearchivosNombreDatos());
                cargueDTO.setCarguearchivosUsuario(cargue.getCarguearchivosUsuario());
                this.cargues.add(cargueDTO);
            }
        }
    }
    
//    @Schedule(second = "0", minute = "0", hour = "*", persistent = false)
//    public void procesarPropietarios(){
//        LOG.log(Level.INFO, "Procesamiento de actualizacion de propietarios --- Inicia ejecucion tarea programada.....");
//        ComConstantes nodoConstante =  constanteDAO.getByGrupoNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.NODO_EJECUCION_PROCESAMIENTO_PROPIETARIO);
//        String nodo = nodoConstante.getConstanteValor();//Constantes.NODO;
//        if ((nodo).compareTo(System.getProperty("weblogic.Name")) == 0) {
//            Calendar c = Calendar.getInstance();            
//            final int horaSistema = c.get(Calendar.HOUR_OF_DAY);
//            ConfiguracionProcesamientoDTO parametrosEjecucion = validarHoraEjecucion(horaSistema);
//            LOG.log(Level.INFO, "Procesamiento de actualizacion de propietarios --- Hora Sistema {0}", new Object[]{horaSistema});
//            if (parametrosEjecucion != null && (parametrosEjecucion.getHora() == horaSistema)) {
//                iniciarProceso(parametrosEjecucion);
//            } else {
//                LOG.log(Level.INFO, "Procesamiento de actualizacion de propietarios --- Hora de ejecuciOn invalida.....");
//            }
//        } else {
//            LOG.log(Level.INFO, "Procesamiento de actualizacion de propietarios --- Nodo de ejecuciOn invalido.....");
//        }
//    }
    
    
    /**
     * 
     * @param horaSistema
     * @return 
     */
    private ConfiguracionProcesamientoDTO validarHoraEjecucion(int horaSistema) {
        ConfiguracionProcesamientoDTO config = new ConfiguracionProcesamientoDTO();   
        ComConstantes constante = constanteDAO.getByGrupoNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.HORA_PROCESAMIENTO_CARGUES_PROPIETARIO); 
        final String parametrizacionTotal = constante.getConstanteValor();
        if (parametrizacionTotal != null) {
            String[] parmetrizacionGrupo = parametrizacionTotal.split(";");
            for (String parametros : parmetrizacionGrupo) {
                config = validarParametros(horaSistema, parametros);
                if(config != null){
                    break;
                }
            }            
        }
        return config;
    }
    
    public void iniciarProceso(ConfiguracionProcesamientoDTO parametrosEjecucion){
        List<SgCarguearchivosDTO> carguesIniciales = cargarTareasIniciales(parametrosEjecucion);
        if(carguesIniciales == null || carguesIniciales.isEmpty()){
            //System.out.println("No se encontraron cargues de propietarios para procesar");
            LOG.log(Level.INFO, "Procesamiento de actualización de propietario --- No se encontraron cargues de actualización de propietarios para procesar.....");
            return;
        }
        Calendar c = Calendar.getInstance();
        long horaIniMillis = System.currentTimeMillis();
        int horasMax = parametrosEjecucion.getHorasMax();
        long horasMaxMillis = horasMax*60*60*1000L;
        
        int i = 1;
        for ( SgCarguearchivosDTO cargue: carguesIniciales){
            if((c.get(Calendar.HOUR_OF_DAY)-parametrosEjecucion.getHora())>=horasMax){
                LOG.log(Level.INFO, "Procesamiento de actualización de propietario --- Se excedio el tiempo maximo de ejecucion de la tarea, proceso suspendido.....");
                break;
            }
            procesarCarguesPendientesLogica.procesarArchivosPendientes(cargue, i, horaIniMillis, horasMaxMillis);            
            i++;
        }        
    }
    
    
    /**
     * 
     * @param horaSistema
     * @param parametros
     * @return 
     */
    private ConfiguracionProcesamientoDTO validarParametros(int horaSistema, String parametros){
        ConfiguracionProcesamientoDTO config = null;        
        String[] parametrosArray = parametros.split(",");
        if(parametrosArray == null){
            return config;
        }
        int valorDefecto = 2;
        int maxHorasProceso = 12;
        if(parametrosArray[0]!= null && Integer.parseInt(parametrosArray[0])==horaSistema){
            config = new ConfiguracionProcesamientoDTO();
            config.setHora(horaSistema);
            if(parametrosArray.length == 3){
                if(parametrosArray[1]!= null){
                    config.setHilos(Integer.parseInt(parametrosArray[1]));
                }else{
                    config.setHilos(valorDefecto);
                }                
                if(parametrosArray[2]!= null && (Integer.parseInt(parametrosArray[2]) <= maxHorasProceso)){
                    config.setHorasMax(Integer.parseInt(parametrosArray[2]));
                }else{
                    config.setHorasMax(valorDefecto);
                }
            }else if(parametrosArray.length == 2){
                if(parametrosArray[1]!= null){
                    config.setHilos(Integer.parseInt(parametrosArray[1]));
                }else{
                    config.setHilos(valorDefecto);
                }
                config.setHorasMax(valorDefecto);
            }else if(parametrosArray.length == 1){
                config.setHilos(valorDefecto);
                config.setHorasMax(valorDefecto);
            }
        }        
        return config;
    }

    public MensajeDTO procesarPropietario (final String nroTicket, final InfoUsuarioDTO usuario){
        MensajeDTO salida = new MensajeDTO();
        SgCarguearchivos archivo = constanteDAO.consultaByTicket(nroTicket);
        if(archivo == null){
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("No existe un cargue pendiente para el número de ticket ingresado");
            return salida;
        }
        CargueArchivoRespuestaDTO procesoCorrecto = tablasPropietario.procesarCambioPropietario(archivo.getCarguearchivosNombreDatos(), archivo.getCarguearchivosDatos(), usuario.getLogin());
        if(!Constantes.PROCESADO.equals(procesoCorrecto.getMensaje())){
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(procesoCorrecto.getMensaje());
            return salida;
        }
        if(Constantes.PROCESADO.equals(procesoCorrecto.getMensaje())){
            salida.setCodmensaje(Mensajes.OK);
            salida.setMensaje(procesoCorrecto.getMensaje());
            return salida;
        }
        return salida;
    }
}
