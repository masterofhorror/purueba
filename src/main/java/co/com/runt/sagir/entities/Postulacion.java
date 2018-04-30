/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "GE_POSTULACI", schema = "RUNTPROD")
@Cacheable
@NamedQueries({
    @NamedQuery(name = "Postulacion.findByCountPlaca", query = "SELECT COUNT(p) FROM Postulacion p WHERE p.nroRegVehic.automotorPlacaNumplaca = :placa")
})
public class Postulacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "POSTULACI_IDENTIFIC")
    private Integer identific;
    @Column(name = "POSTULACI_TIPOPOSTU_NOMBRE")
    private String tipoPostulacionNombre;
    @Column(name = "POSTULACI_PERSONA_IDPERSONA")
    private Integer idPersona;
    @Column(name = "POSTULACI_MUNICPIO_DIVIPOL")
    private Integer divipol;
    @Column(name = "POSTULACI_DIRECCION")
    private String direccion;
    @Column(name = "POSTULACI_TELEFONO")
    private String telefono;
    @Column(name = "POSTULACI_CELULAR")
    private String celular;
    @Column(name = "POSTULACI_EMAIL")
    private String email;
    @OneToOne
    @JoinColumn(name = "POSTULACI_AUTOMOTOR_NROREGVEH")
    private Automotor nroRegVehic;
    @Column(name = "POSTULACI_MARCA_IDMARCA")
    private Integer idMarca;
    @Column(name = "POSTULACI_LINEA_IDLINEA")
    private Integer idLinea;
    @Column(name = "POSTULACI_CLASVEHIC_IDCLASE")
    private Integer idClase;
    @Column(name = "POSTULACI_NUMEJES")
    private Integer numEjes;
    @Column(name = "POSTULACI_COLOR_IDCOLOR")
    private Integer idColor;
    @Column(name = "POSTULACI_CAPACIDAD")
    private Integer capacidad;
    @Column(name = "POSTULACI_TIPOSERVI_IDETIPSER")
    private Integer idTipoServi;
    @Column(name = "POSTULACI_MODELO_ID")
    private Integer modelo;
    @Column(name = "POSTULACI_AUTOTRANS_IDAUTTRA")
    private Integer idauttra;
    @Column(name = "POSTULACI_NUMCHASIS")
    private String numChasis;
    @Column(name = "POSTULACI_NUMSERIE")
    private String numSerie;
    @Column(name = "POSTULACI_NUMMOTOR")
    private String numMotor;
    @Column(name = "POSTULACI_NUMVIN")
    private String numVin;
    @Column(name = "POSTULACI_CONFIGURA_NOMBRE")
    private String configuraNombre;
    @Column(name = "POSTULACI_TIPOCARRO_IDCARROCE")
    private Integer idCarroceria;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POSTULACI_FECHPOSTU")
    private Date fechaPostulacion;
    @Column(name = "POSTULACI_ESTAPOSTU_NOMBRE")
    private String estadoPostulacion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POSTULACI_FECHSUCES")
    private Date fechaSuceso;
    @Column(name = "POSTULACI_IPAT_IDENTIFIC")
    private Integer ipat;
    @Column(name = "POSTULACI_MUNICPIO_DVPLDESIN")
    private Integer dvplDesin;
    @Column(name = "POSTULACI_USUARIO")
    private String usuario;
    @Column(name = "POSTULACI_AUTOTRANS_IDENTMODI")
    private Integer identModi;
    @Column(name = "POSTULACI_IPMODIFIC")
    private String ipModific;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POSTULACI_FECHMODIF")
    private Date fechaModificacion;
    @Column(name = "POSTULACI_NROMODIFI")
    private Integer nroModificacion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POSTULACI_FECHREGIS")
    private Date fechaRegistro;

    public Integer getIdentific() {
        return identific;
    }

    public void setIdentific(Integer identific) {
        this.identific = identific;
    }

    public String getTipoPostulacionNombre() {
        return tipoPostulacionNombre;
    }

    public void setTipoPostulacionNombre(String tipoPostulacionNombre) {
        this.tipoPostulacionNombre = tipoPostulacionNombre;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getDivipol() {
        return divipol;
    }

    public void setDivipol(Integer divipol) {
        this.divipol = divipol;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Automotor getNroRegVehic() {
        return nroRegVehic;
    }

    public void setNroRegVehic(Automotor nroRegVehic) {
        this.nroRegVehic = nroRegVehic;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Integer idLinea) {
        this.idLinea = idLinea;
    }

    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public Integer getNumEjes() {
        return numEjes;
    }

    public void setNumEjes(Integer numEjes) {
        this.numEjes = numEjes;
    }

    public Integer getIdColor() {
        return idColor;
    }

    public void setIdColor(Integer idColor) {
        this.idColor = idColor;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getIdTipoServi() {
        return idTipoServi;
    }

    public void setIdTipoServi(Integer idTipoServi) {
        this.idTipoServi = idTipoServi;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public Integer getIdauttra() {
        return idauttra;
    }

    public void setIdauttra(Integer idauttra) {
        this.idauttra = idauttra;
    }

    public String getNumChasis() {
        return numChasis;
    }

    public void setNumChasis(String numChasis) {
        this.numChasis = numChasis;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getNumMotor() {
        return numMotor;
    }

    public void setNumMotor(String numMotor) {
        this.numMotor = numMotor;
    }

    public String getNumVin() {
        return numVin;
    }

    public void setNumVin(String numVin) {
        this.numVin = numVin;
    }

    public String getConfiguraNombre() {
        return configuraNombre;
    }

    public void setConfiguraNombre(String configuraNombre) {
        this.configuraNombre = configuraNombre;
    }

    public Integer getIdCarroceria() {
        return idCarroceria;
    }

    public void setIdCarroceria(Integer idCarroceria) {
        this.idCarroceria = idCarroceria;
    }

    public Date getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(Date fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public String getEstadoPostulacion() {
        return estadoPostulacion;
    }

    public void setEstadoPostulacion(String estadoPostulacion) {
        this.estadoPostulacion = estadoPostulacion;
    }

    public Date getFechaSuceso() {
        return fechaSuceso;
    }

    public void setFechaSuceso(Date fechaSuceso) {
        this.fechaSuceso = fechaSuceso;
    }

    public Integer getIpat() {
        return ipat;
    }

    public void setIpat(Integer ipat) {
        this.ipat = ipat;
    }

    public Integer getDvplDesin() {
        return dvplDesin;
    }

    public void setDvplDesin(Integer dvplDesin) {
        this.dvplDesin = dvplDesin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getIdentModi() {
        return identModi;
    }

    public void setIdentModi(Integer identModi) {
        this.identModi = identModi;
    }

    public String getIpModific() {
        return ipModific;
    }

    public void setIpModific(String ipModific) {
        this.ipModific = ipModific;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getNroModificacion() {
        return nroModificacion;
    }

    public void setNroModificacion(Integer nroModificacion) {
        this.nroModificacion = nroModificacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    

}
