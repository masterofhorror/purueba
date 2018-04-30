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
@Table(name = "EV_PLACA", schema = "RUNTPROD")
@Cacheable
@NamedQueries({
    @NamedQuery(name = "EvPlaca.findByPlaca", query = "SELECT e FROM EvPlaca e WHERE e.placa = :placa")
})
public class EvPlaca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PLACA_NUMPLACA")
    private String placa;
    @Column(name = "PLACA_AUTOTRANS_IDAUTTRA")
    private Long idauttra;
    @Column(name = "PLACA_RANGPLACA_IDRANGO")
    private Long idRango;
    @Column(name = "PLACA_MOTICANCE_IDMOTCAN")
    private Long idMotCan;
    @Column(name = "PLACA_FORMPLACA_IDEFORPLA")
    private Long idForPla;
    @Column(name = "PLACA_SOLICITUD_IDENSOLIC")
    private Long solicitud;
    @Column(name = "PLACA_ESTPLACA_NOMBRE")
    private String estadoPlacaNombre;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLACA_FECHEXPED")
    private Date fechExped;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLACA_FECHNOVED")
    private Date fechNoved;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLACA_FECHVENCI")
    private Date fechVenci;
    @Column(name = "PLACA_AUTOMOTOR_NROREGVEH")
    private Long idVehiculo;
    @Column(name = "PLACA_TIPSERDIP_NOMBRE")
    private String tipSerDip;
    @Column(name = "PLACA_ESTAVEHIC")
    private String estadoVehic;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getIdauttra() {
        return idauttra;
    }

    public void setIdauttra(Long idauttra) {
        this.idauttra = idauttra;
    }

    public Long getIdRango() {
        return idRango;
    }

    public void setIdRango(Long idRango) {
        this.idRango = idRango;
    }

    public Long getIdMotCan() {
        return idMotCan;
    }

    public void setIdMotCan(Long idMotCan) {
        this.idMotCan = idMotCan;
    }

    public Long getIdForPla() {
        return idForPla;
    }

    public void setIdForPla(Long idForPla) {
        this.idForPla = idForPla;
    }

    public Long getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Long solicitud) {
        this.solicitud = solicitud;
    }

    public String getEstadoPlacaNombre() {
        return estadoPlacaNombre;
    }

    public void setEstadoPlacaNombre(String estadoPlacaNombre) {
        this.estadoPlacaNombre = estadoPlacaNombre;
    }

    public Date getFechExped() {
        return fechExped;
    }

    public void setFechExped(Date fechExped) {
        this.fechExped = fechExped;
    }

    public Date getFechNoved() {
        return fechNoved;
    }

    public void setFechNoved(Date fechNoved) {
        this.fechNoved = fechNoved;
    }

    public Date getFechVenci() {
        return fechVenci;
    }

    public void setFechVenci(Date fechVenci) {
        this.fechVenci = fechVenci;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getTipSerDip() {
        return tipSerDip;
    }

    public void setTipSerDip(String tipSerDip) {
        this.tipSerDip = tipSerDip;
    }

    public String getEstadoVehic() {
        return estadoVehic;
    }

    public void setEstadoVehic(String estadoVehic) {
        this.estadoVehic = estadoVehic;
    }

    
}
