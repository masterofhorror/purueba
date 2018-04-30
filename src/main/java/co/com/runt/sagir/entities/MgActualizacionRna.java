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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@Cacheable
@Table(name = "MG_ACTUALIZACION_RNA", schema = "GMARTINEZ")
@NamedQueries({
    @NamedQuery(name = "MgActualizacionRna.findByValida", query = "SELECT COUNT(m) FROM MgActualizacionRna m WHERE m.placa = :placa AND m.otCargo = :ot AND m.tipoProceso = :tipoProceso AND m.estadoProceso IN (0, 1)"),
    @NamedQuery(name = "MgActualizacionRna.findByValidaCambio", query = "SELECT COUNT(m) FROM MgActualizacionRna m WHERE m.placa = :placa AND m.tipoProceso = :tipoProceso AND m.estadoProceso = 1"),
    @NamedQuery(name = "MgActualizacionRna.findByPlaca", query = "SELECT COUNT(m) FROM MgActualizacionRna m WHERE m.placa = :placa AND m.tipoProceso = :tipoProceso AND m.estadoProceso = 0")
})
public class MgActualizacionRna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "NROREGVEH_MIGRA")
    private Long nroRegistroVehiculo;
    @Column(name = "NUMERO_PLACA")
    private String placa;
    @Column(name = "OT_CARGADO")
    private Integer otCargo;
    @Column(name = "OT_SOLICITA")
    private Integer otSolicita;
    @Column(name = "ESTADO_VEHICULO_CARGADO")
    private String estadoVehiculoCargado;
    @Column(name = "ESTADO_VEHICULO_SOLICITA")
    private String estadoVehiculoSolicita;
    @Column(name = "IDCLASE_CARGADO")
    private Integer idClaseCargado;
    @Column(name = "IDCLASE_SOLICITA")
    private Integer idClaseSolicita;
    @Column(name = "IDMARCA_CARGADO")
    private Integer idMarcaCargado;
    @Column(name = "IDMARCA_SOLICITA")
    private Integer idMarcaSolicita;
    @Column(name = "MODELO_CARGADO")
    private Integer modeloCargado;
    @Column(name = "MODELO_SOLICITA")
    private Integer modeloSolicita;
    @Column(name = "NROMOTOR_CARGADO")
    private String nroMotorCargado;
    @Column(name = "NROMOTOR_SOLICITA")
    private String nroMotorSolicita;
    @Column(name = "NROSERIE_CARGADO")
    private String nroSerieCargado;
    @Column(name = "NROSERIE_SOLICITA")
    private String nroSerieSolicita;
    @Column(name = "NROCHASIS_CARGADO")
    private String nroChasisCargado;
    @Column(name = "NROCHASIS_SOLICITA")
    private String nroChasisSolicita;
    @Column(name = "ID_SERVICIO_ANTERIOR")
    private Integer idServicioAnterior;
    @Column(name = "ID_SERVICIO_NUEVO")
    private Integer idServicioNuevo;
    @Column(name = "TIPO_PROCESO")
    private Integer tipoProceso;
    @Column(name = "ESTADO_PROCESO")
    private Integer estadoProceso;
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_PROCESO")
    private Date fechaProceso;
    @Column(name = "DESCRIPCION_ESTADO")
    private String descripcionEstado;
    @Column(name = "ID_FUNCIONARIO")
    private String idFuncionario;
    @Column(name = "COD_CARGA")
    private Integer codCarga;

    public MgActualizacionRna() {
        //Constructor vacio
    }

    public MgActualizacionRna(Long nroRegistroVehiculo, String placa, Integer codCarga) {
        this.nroRegistroVehiculo = nroRegistroVehiculo;
        this.placa = placa;
        this.codCarga = codCarga;
    }

    public Long getNroRegistroVehiculo() {
        return nroRegistroVehiculo;
    }

    public void setNroRegistroVehiculo(Long nroRegistroVehiculo) {
        this.nroRegistroVehiculo = nroRegistroVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getOtCargo() {
        return otCargo;
    }

    public void setOtCargo(Integer otCargo) {
        this.otCargo = otCargo;
    }

    public Integer getOtSolicita() {
        return otSolicita;
    }

    public void setOtSolicita(Integer otSolicita) {
        this.otSolicita = otSolicita;
    }

    public String getEstadoVehiculoCargado() {
        return estadoVehiculoCargado;
    }

    public void setEstadoVehiculoCargado(String estadoVehiculoCargado) {
        this.estadoVehiculoCargado = estadoVehiculoCargado;
    }

    public String getEstadoVehiculoSolicita() {
        return estadoVehiculoSolicita;
    }

    public void setEstadoVehiculoSolicita(String estadoVehiculoSolicita) {
        this.estadoVehiculoSolicita = estadoVehiculoSolicita;
    }

    public Integer getIdClaseCargado() {
        return idClaseCargado;
    }

    public void setIdClaseCargado(Integer idClaseCargado) {
        this.idClaseCargado = idClaseCargado;
    }

    public Integer getIdClaseSolicita() {
        return idClaseSolicita;
    }

    public void setIdClaseSolicita(Integer idClaseSolicita) {
        this.idClaseSolicita = idClaseSolicita;
    }

    public Integer getIdMarcaCargado() {
        return idMarcaCargado;
    }

    public void setIdMarcaCargado(Integer idMarcaCargado) {
        this.idMarcaCargado = idMarcaCargado;
    }

    public Integer getIdMarcaSolicita() {
        return idMarcaSolicita;
    }

    public void setIdMarcaSolicita(Integer idMarcaSolicita) {
        this.idMarcaSolicita = idMarcaSolicita;
    }

    public Integer getModeloCargado() {
        return modeloCargado;
    }

    public void setModeloCargado(Integer modeloCargado) {
        this.modeloCargado = modeloCargado;
    }

    public Integer getModeloSolicita() {
        return modeloSolicita;
    }

    public void setModeloSolicita(Integer modeloSolicita) {
        this.modeloSolicita = modeloSolicita;
    }

    public String getNroMotorCargado() {
        return nroMotorCargado;
    }

    public void setNroMotorCargado(String nroMotorCargado) {
        this.nroMotorCargado = nroMotorCargado;
    }

    public String getNroMotorSolicita() {
        return nroMotorSolicita;
    }

    public void setNroMotorSolicita(String nroMotorSolicita) {
        this.nroMotorSolicita = nroMotorSolicita;
    }

    public String getNroSerieCargado() {
        return nroSerieCargado;
    }

    public void setNroSerieCargado(String nroSerieCargado) {
        this.nroSerieCargado = nroSerieCargado;
    }

    public String getNroSerieSolicita() {
        return nroSerieSolicita;
    }

    public void setNroSerieSolicita(String nroSerieSolicita) {
        this.nroSerieSolicita = nroSerieSolicita;
    }

    public String getNroChasisCargado() {
        return nroChasisCargado;
    }

    public void setNroChasisCargado(String nroChasisCargado) {
        this.nroChasisCargado = nroChasisCargado;
    }

    public String getNroChasisSolicita() {
        return nroChasisSolicita;
    }

    public void setNroChasisSolicita(String nroChasisSolicita) {
        this.nroChasisSolicita = nroChasisSolicita;
    }

    public Integer getIdServicioAnterior() {
        return idServicioAnterior;
    }

    public void setIdServicioAnterior(Integer idServicioAnterior) {
        this.idServicioAnterior = idServicioAnterior;
    }

    public Integer getIdServicioNuevo() {
        return idServicioNuevo;
    }

    public void setIdServicioNuevo(Integer idServicioNuevo) {
        this.idServicioNuevo = idServicioNuevo;
    }

    public Integer getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(Integer tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public Integer getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(Integer estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }
    
    
    

}
