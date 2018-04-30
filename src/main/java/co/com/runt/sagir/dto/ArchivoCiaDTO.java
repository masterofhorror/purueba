/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author APENA
 */
@XmlRootElement
public class ArchivoCiaDTO {

    private String secuenciaValidacion;
    private String tipoCargue;
    private String identificacionSede;
    private String numeroCertificadoCia;
    private String tipoDocumentoAlumno;
    private String numeroDocumentoAlumno;
    private String nombresAlumno;
    private String apellidosAlumno;
    private String tipoSancion;
    private String numeroComparendo;
    private String fechaComparendo;
    private String departamentoComparendo;
    private String ciudadComparendo;
    private String numeroFallo;
    private String fechaFallo;
    private String entidadEmiteFallo;
    private String fechaCurso;
    private String horaInicioCurso;
    private String horaFinCurso;
    private String valorRecaudoCia;
    private String tipoIdentificacionInstructor;
    private String numeroIdentificacionInstructor;
    private String resultadoCurso;
    private String codCarga;
    private Date fechaCarga;
    private String desMigrado;
    private String observacion;
    private String nombreArchivo;
    private String registroControl;
    private String estadoReporte;
    private String numReg;
    private String codSede;
    private String lineaCompleta;

    public String getSecuenciaValidacion() {
        return secuenciaValidacion;
    }

    public void setSecuenciaValidacion(String secuenciaValidacion) {
        this.secuenciaValidacion = secuenciaValidacion;
    }

    public String getTipoCargue() {
        return tipoCargue;
    }

    public void setTipoCargue(String tipoCargue) {
        this.tipoCargue = tipoCargue;
    }

    public String getIdentificacionSede() {
        return identificacionSede;
    }

    public void setIdentificacionSede(String identificacionSede) {
        this.identificacionSede = identificacionSede;
    }

    public String getNumeroCertificadoCia() {
        return numeroCertificadoCia;
    }

    public void setNumeroCertificadoCia(String numeroCertificadoCia) {
        this.numeroCertificadoCia = numeroCertificadoCia;
    }

    public String getTipoDocumentoAlumno() {
        return tipoDocumentoAlumno;
    }

    public void setTipoDocumentoAlumno(String tipoDocumentoAlumno) {
        this.tipoDocumentoAlumno = tipoDocumentoAlumno;
    }

    public String getNumeroDocumentoAlumno() {
        return numeroDocumentoAlumno;
    }

    public void setNumeroDocumentoAlumno(String numeroDocumentoAlumno) {
        this.numeroDocumentoAlumno = numeroDocumentoAlumno;
    }

    public String getNombresAlumno() {
        return nombresAlumno;
    }

    public void setNombresAlumno(String nombresAlumno) {
        this.nombresAlumno = nombresAlumno;
    }

    public String getApellidosAlumno() {
        return apellidosAlumno;
    }

    public void setApellidosAlumno(String apellidosAlumno) {
        this.apellidosAlumno = apellidosAlumno;
    }

    public String getTipoSancion() {
        return tipoSancion;
    }

    public void setTipoSancion(String tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

    public String getNumeroComparendo() {
        return numeroComparendo;
    }

    public void setNumeroComparendo(String numeroComparendo) {
        this.numeroComparendo = numeroComparendo;
    }

    public String getFechaComparendo() {
        return fechaComparendo;
    }

    public void setFechaComparendo(String fechaComparendo) {
        this.fechaComparendo = fechaComparendo;
    }

    public String getDepartamentoComparendo() {
        return departamentoComparendo;
    }

    public void setDepartamentoComparendo(String departamentoComparendo) {
        this.departamentoComparendo = departamentoComparendo;
    }

    public String getCiudadComparendo() {
        return ciudadComparendo;
    }

    public void setCiudadComparendo(String ciudadComparendo) {
        this.ciudadComparendo = ciudadComparendo;
    }

    public String getNumeroFallo() {
        return numeroFallo;
    }

    public void setNumeroFallo(String numeroFallo) {
        this.numeroFallo = numeroFallo;
    }

    public String getFechaFallo() {
        return fechaFallo;
    }

    public void setFechaFallo(String fechaFallo) {
        this.fechaFallo = fechaFallo;
    }

    public String getEntidadEmiteFallo() {
        return entidadEmiteFallo;
    }

    public void setEntidadEmiteFallo(String entidadEmiteFallo) {
        this.entidadEmiteFallo = entidadEmiteFallo;
    }

    public String getFechaCurso() {
        return fechaCurso;
    }

    public void setFechaCurso(String fechaCurso) {
        this.fechaCurso = fechaCurso;
    }

    public String getHoraInicioCurso() {
        return horaInicioCurso;
    }

    public void setHoraInicioCurso(String horaInicioCurso) {
        this.horaInicioCurso = horaInicioCurso;
    }

    public String getHoraFinCurso() {
        return horaFinCurso;
    }

    public void setHoraFinCurso(String horaFinCurso) {
        this.horaFinCurso = horaFinCurso;
    }

    public String getValorRecaudoCia() {
        return valorRecaudoCia;
    }

    public void setValorRecaudoCia(String valorRecaudoCia) {
        this.valorRecaudoCia = valorRecaudoCia;
    }

    public String getTipoIdentificacionInstructor() {
        return tipoIdentificacionInstructor;
    }

    public void setTipoIdentificacionInstructor(String tipoIdentificacionInstructor) {
        this.tipoIdentificacionInstructor = tipoIdentificacionInstructor;
    }

    public String getNumeroIdentificacionInstructor() {
        return numeroIdentificacionInstructor;
    }

    public void setNumeroIdentificacionInstructor(String numeroIdentificacionInstructor) {
        this.numeroIdentificacionInstructor = numeroIdentificacionInstructor;
    }

    public String getResultadoCurso() {
        return resultadoCurso;
    }

    public void setResultadoCurso(String resultadoCurso) {
        this.resultadoCurso = resultadoCurso;
    }

    public String getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(String codCarga) {
        this.codCarga = codCarga;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getDesMigrado() {
        return desMigrado;
    }

    public void setDesMigrado(String desMigrado) {
        this.desMigrado = desMigrado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRegistroControl() {
        return registroControl;
    }

    public void setRegistroControl(String registroControl) {
        this.registroControl = registroControl;
    }

    public String getEstadoReporte() {
        return estadoReporte;
    }

    public void setEstadoReporte(String estadoReporte) {
        this.estadoReporte = estadoReporte;
    }

    public String getNumReg() {
        return numReg;
    }

    public void setNumReg(String numReg) {
        this.numReg = numReg;
    }

    public String getCodSede() {
        return codSede;
    }

    public void setCodSede(String codSede) {
        this.codSede = codSede;
    }

    public String getLineaCompleta() {
        return lineaCompleta;
    }

    public void setLineaCompleta(String lineaCompleta) {
        this.lineaCompleta = lineaCompleta;
    }
    
    
}
