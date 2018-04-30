/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author APENA
 */
@Entity
@Table(name = "CIA_CARGUE", schema = "MIGRACIONQX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CiaCargue.findAll", query = "SELECT c FROM CiaCargue c"),
    @NamedQuery(name = "CiaCargue.findBySecValidacion", query = "SELECT c FROM CiaCargue c WHERE c.ciaCarguePK.secValidacion = :secValidacion"),
    @NamedQuery(name = "CiaCargue.findByTipoCargue", query = "SELECT c FROM CiaCargue c WHERE c.tipoCargue = :tipoCargue"),
    @NamedQuery(name = "CiaCargue.findByNroIdentificacionSede", query = "SELECT c FROM CiaCargue c WHERE c.ciaCarguePK.nroIdentificacionSede = :nroIdentificacionSede"),
    @NamedQuery(name = "CiaCargue.findByNroCertificado", query = "SELECT c FROM CiaCargue c WHERE c.ciaCarguePK.nroCertificado = :nroCertificado"),
    @NamedQuery(name = "CiaCargue.findByTipoDocAlumno", query = "SELECT c FROM CiaCargue c WHERE c.tipoDocAlumno = :tipoDocAlumno"),
    @NamedQuery(name = "CiaCargue.findByNroDocAlumno", query = "SELECT c FROM CiaCargue c WHERE c.nroDocAlumno = :nroDocAlumno"),
    @NamedQuery(name = "CiaCargue.findByNombreAlumno", query = "SELECT c FROM CiaCargue c WHERE c.nombreAlumno = :nombreAlumno"),
    @NamedQuery(name = "CiaCargue.findByApellidoAlumno", query = "SELECT c FROM CiaCargue c WHERE c.apellidoAlumno = :apellidoAlumno"),
    @NamedQuery(name = "CiaCargue.findByTipoSancion", query = "SELECT c FROM CiaCargue c WHERE c.tipoSancion = :tipoSancion"),
    @NamedQuery(name = "CiaCargue.findByNroComparendo", query = "SELECT c FROM CiaCargue c WHERE c.nroComparendo = :nroComparendo"),
    @NamedQuery(name = "CiaCargue.findByFechaComparendo", query = "SELECT c FROM CiaCargue c WHERE c.fechaComparendo = :fechaComparendo"),
    @NamedQuery(name = "CiaCargue.findByCodDepartComparendo", query = "SELECT c FROM CiaCargue c WHERE c.codDepartComparendo = :codDepartComparendo"),
    @NamedQuery(name = "CiaCargue.findByCodCiudadComparendo", query = "SELECT c FROM CiaCargue c WHERE c.codCiudadComparendo = :codCiudadComparendo"),
    @NamedQuery(name = "CiaCargue.findByNroFallo", query = "SELECT c FROM CiaCargue c WHERE c.nroFallo = :nroFallo"),
    @NamedQuery(name = "CiaCargue.findByFechaFallo", query = "SELECT c FROM CiaCargue c WHERE c.fechaFallo = :fechaFallo"),
    @NamedQuery(name = "CiaCargue.findByNombreEntidadFallo", query = "SELECT c FROM CiaCargue c WHERE c.nombreEntidadFallo = :nombreEntidadFallo"),
    @NamedQuery(name = "CiaCargue.findByFechaCurso", query = "SELECT c FROM CiaCargue c WHERE c.fechaCurso = :fechaCurso"),
    @NamedQuery(name = "CiaCargue.findByHoraIniCurso", query = "SELECT c FROM CiaCargue c WHERE c.horaIniCurso = :horaIniCurso"),
    @NamedQuery(name = "CiaCargue.findByHoraFinCurso", query = "SELECT c FROM CiaCargue c WHERE c.horaFinCurso = :horaFinCurso"),
    @NamedQuery(name = "CiaCargue.findByValorRecaudoCia", query = "SELECT c FROM CiaCargue c WHERE c.valorRecaudoCia = :valorRecaudoCia"),
    @NamedQuery(name = "CiaCargue.findByTipoDocInstructor", query = "SELECT c FROM CiaCargue c WHERE c.tipoDocInstructor = :tipoDocInstructor"),
    @NamedQuery(name = "CiaCargue.findByNroDocInstructor", query = "SELECT c FROM CiaCargue c WHERE c.nroDocInstructor = :nroDocInstructor"),
    @NamedQuery(name = "CiaCargue.findByResultadoCurso", query = "SELECT c FROM CiaCargue c WHERE c.resultadoCurso = :resultadoCurso"),
    @NamedQuery(name = "CiaCargue.findByCodCarga", query = "SELECT c FROM CiaCargue c WHERE c.ciaCarguePK.codCarga = :codCarga"),
    @NamedQuery(name = "CiaCargue.findByFechaCarga", query = "SELECT c FROM CiaCargue c WHERE c.fechaCarga = :fechaCarga"),
    @NamedQuery(name = "CiaCargue.findByDesMigrado", query = "SELECT c FROM CiaCargue c WHERE c.desMigrado = :desMigrado"),
    @NamedQuery(name = "CiaCargue.findByObservacion", query = "SELECT c FROM CiaCargue c WHERE c.observacion = :observacion"),
    @NamedQuery(name = "CiaCargue.findByNombreArchivo", query = "SELECT c FROM CiaCargue c WHERE c.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "CiaCargue.findByRegistroControl", query = "SELECT c FROM CiaCargue c WHERE c.registroControl = :registroControl"),
    @NamedQuery(name = "CiaCargue.findByEstadoReporte", query = "SELECT c FROM CiaCargue c WHERE c.estadoReporte = :estadoReporte"),
    @NamedQuery(name = "CiaCargue.findByNumReg", query = "SELECT c FROM CiaCargue c WHERE c.numReg = :numReg"),
    @NamedQuery(name = "CiaCargue.findByCodSede", query = "SELECT c FROM CiaCargue c WHERE c.codSede = :codSede")})
public class CiaCargue implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CiaCarguePK ciaCarguePK;
    
    @Size(max = 50)
    @Column(name = "TIPO_CARGUE")
    private String tipoCargue;
    @Size(max = 50)
    @Column(name = "TIPO_DOC_ALUMNO")
    private String tipoDocAlumno;
    @Size(max = 50)
    @Column(name = "NRO_DOC_ALUMNO")
    private String nroDocAlumno;
    @Size(max = 150)
    @Column(name = "NOMBRE_ALUMNO")
    private String nombreAlumno;
    @Size(max = 150)
    @Column(name = "APELLIDO_ALUMNO")
    private String apellidoAlumno;
    @Size(max = 50)
    @Column(name = "TIPO_SANCION")
    private String tipoSancion;
    @Size(max = 50)
    @Column(name = "NRO_COMPARENDO")
    private String nroComparendo;
    @Size(max = 50)
    @Column(name = "FECHA_COMPARENDO")
    private String fechaComparendo;
    @Size(max = 50)
    @Column(name = "COD_DEPART_COMPARENDO")
    private String codDepartComparendo;
    @Size(max = 50)
    @Column(name = "COD_CIUDAD_COMPARENDO")
    private String codCiudadComparendo;
    @Size(max = 50)
    @Column(name = "NRO_FALLO")
    private String nroFallo;
    @Size(max = 50)
    @Column(name = "FECHA_FALLO")
    private String fechaFallo;
    @Size(max = 400)
    @Column(name = "NOMBRE_ENTIDAD_FALLO")
    private String nombreEntidadFallo;
    @Size(max = 50)
    @Column(name = "FECHA_CURSO")
    private String fechaCurso;
    @Size(max = 50)
    @Column(name = "HORA_INI_CURSO")
    private String horaIniCurso;
    @Size(max = 50)
    @Column(name = "HORA_FIN_CURSO")
    private String horaFinCurso;
    @Size(max = 50)
    @Column(name = "VALOR_RECAUDO_CIA")
    private String valorRecaudoCia;
    @Size(max = 50)
    @Column(name = "TIPO_DOC_INSTRUCTOR")
    private String tipoDocInstructor;
    @Size(max = 50)
    @Column(name = "NRO_DOC_INSTRUCTOR")
    private String nroDocInstructor;
    @Size(max = 50)
    @Column(name = "RESULTADO_CURSO")
    private String resultadoCurso;
    @Column(name = "FECHA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCarga;
    @Size(max = 500)
    @Column(name = "DES_MIGRADO")
    private String desMigrado;
    @Size(max = 500)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Size(max = 500)
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Size(max = 50)
    @Column(name = "REGISTRO_CONTROL")
    private String registroControl;
    @Size(max = 15)
    @Column(name = "ESTADO_REPORTE")
    private String estadoReporte;
    @Column(name = "NUM_REG")
    private BigInteger numReg;
    @Column(name = "COD_SEDE")
    private BigInteger codSede;

    public CiaCargue() {
        //Default constructor
    }

    public CiaCargue(CiaCarguePK ciaCarguePK) {
        this.ciaCarguePK = ciaCarguePK;
    }

    public CiaCargue(String secValidacion, String nroIdentificacionSede, String nroCertificado, BigInteger codCarga) {
        this.ciaCarguePK = new CiaCarguePK(secValidacion, nroIdentificacionSede, nroCertificado, codCarga);
    }

    public CiaCarguePK getCiaCarguePK() {
        return ciaCarguePK;
    }

    public void setCiaCarguePK(CiaCarguePK ciaCarguePK) {
        this.ciaCarguePK = ciaCarguePK;
    }

    public String getTipoCargue() {
        return tipoCargue;
    }

    public void setTipoCargue(String tipoCargue) {
        this.tipoCargue = tipoCargue;
    }

    public String getTipoDocAlumno() {
        return tipoDocAlumno;
    }

    public void setTipoDocAlumno(String tipoDocAlumno) {
        this.tipoDocAlumno = tipoDocAlumno;
    }

    public String getNroDocAlumno() {
        return nroDocAlumno;
    }

    public void setNroDocAlumno(String nroDocAlumno) {
        this.nroDocAlumno = nroDocAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getApellidoAlumno() {
        return apellidoAlumno;
    }

    public void setApellidoAlumno(String apellidoAlumno) {
        this.apellidoAlumno = apellidoAlumno;
    }

    public String getTipoSancion() {
        return tipoSancion;
    }

    public void setTipoSancion(String tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

    public String getNroComparendo() {
        return nroComparendo;
    }

    public void setNroComparendo(String nroComparendo) {
        this.nroComparendo = nroComparendo;
    }

    public String getFechaComparendo() {
        return fechaComparendo;
    }

    public void setFechaComparendo(String fechaComparendo) {
        this.fechaComparendo = fechaComparendo;
    }

    public String getCodDepartComparendo() {
        return codDepartComparendo;
    }

    public void setCodDepartComparendo(String codDepartComparendo) {
        this.codDepartComparendo = codDepartComparendo;
    }

    public String getCodCiudadComparendo() {
        return codCiudadComparendo;
    }

    public void setCodCiudadComparendo(String codCiudadComparendo) {
        this.codCiudadComparendo = codCiudadComparendo;
    }

    public String getNroFallo() {
        return nroFallo;
    }

    public void setNroFallo(String nroFallo) {
        this.nroFallo = nroFallo;
    }

    public String getFechaFallo() {
        return fechaFallo;
    }

    public void setFechaFallo(String fechaFallo) {
        this.fechaFallo = fechaFallo;
    }

    public String getNombreEntidadFallo() {
        return nombreEntidadFallo;
    }

    public void setNombreEntidadFallo(String nombreEntidadFallo) {
        this.nombreEntidadFallo = nombreEntidadFallo;
    }

    public String getFechaCurso() {
        return fechaCurso;
    }

    public void setFechaCurso(String fechaCurso) {
        this.fechaCurso = fechaCurso;
    }

    public String getHoraIniCurso() {
        return horaIniCurso;
    }

    public void setHoraIniCurso(String horaIniCurso) {
        this.horaIniCurso = horaIniCurso;
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

    public String getTipoDocInstructor() {
        return tipoDocInstructor;
    }

    public void setTipoDocInstructor(String tipoDocInstructor) {
        this.tipoDocInstructor = tipoDocInstructor;
    }

    public String getNroDocInstructor() {
        return nroDocInstructor;
    }

    public void setNroDocInstructor(String nroDocInstructor) {
        this.nroDocInstructor = nroDocInstructor;
    }

    public String getResultadoCurso() {
        return resultadoCurso;
    }

    public void setResultadoCurso(String resultadoCurso) {
        this.resultadoCurso = resultadoCurso;
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

    public BigInteger getNumReg() {
        return numReg;
    }

    public void setNumReg(BigInteger numReg) {
        this.numReg = numReg;
    }

    public BigInteger getCodSede() {
        return codSede;
    }

    public void setCodSede(BigInteger codSede) {
        this.codSede = codSede;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciaCarguePK != null ? ciaCarguePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CiaCargue)) {
            return false;
        }
        CiaCargue other = (CiaCargue) object;
        return !((this.ciaCarguePK == null && other.ciaCarguePK != null) || (this.ciaCarguePK != null && !this.ciaCarguePK.equals(other.ciaCarguePK)));
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.CiaCargue[ ciaCarguePK=" + ciaCarguePK + " ]";
    }
    
}
