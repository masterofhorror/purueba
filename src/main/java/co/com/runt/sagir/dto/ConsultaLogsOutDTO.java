package co.com.runt.sagir.dto;

/**
 *
 * @author Dospina
 */
public class ConsultaLogsOutDTO {
    
    private String fecha;
            
    private String entrada;
    
    private Long autoridad;
    
    private String usuario;
    
    private String ip;
    
    private String aplicacion;
    
    private String tipoconsulta;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getTipoconsulta() {
        return tipoconsulta;
    }

    public void setTipoconsulta(String tipoconsulta) {
        this.tipoconsulta = tipoconsulta;
    }

    
}
