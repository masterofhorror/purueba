package co.com.runt.sagir.dto;

import co.com.runt.sagir.entities.PaAplicacion;
import co.com.runt.sagir.entities.PaTipoconsulta;


/**
 *
 * @author Dospina
 */
public class ConsultaLogsInDTO {
    
    private String fechaInicio;
    
    private String fechaFin;
        
    private String entrada;
    
    private Long autoridad;
    
    private String usuario;
    
    private String ip;
        
    private PaAplicacion aplicacion;
    
    private PaTipoconsulta tipoconsulta;

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public Long getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(Long autoridad) {
        this.autoridad = autoridad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public PaAplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(PaAplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public PaTipoconsulta getTipoconsulta() {
        return tipoconsulta;
    }

    public void setTipoconsulta(PaTipoconsulta tipoconsulta) {
        this.tipoconsulta = tipoconsulta;
    }

    
}
