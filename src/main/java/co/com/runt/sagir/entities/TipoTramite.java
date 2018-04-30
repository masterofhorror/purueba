/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author dsalamanca
 */
@Entity
@Cacheable
@Table(name = "PA_TIPOTRAMI", schema = "RUNTPROD")
@NamedQueries({})
public class TipoTramite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TIPOTRAMI_CODTRAMIT")
    private String codTipoTrami;
    @Column(name = "TIPOTRAMI_TIPOREGIS_IDTIPOREG")
    private String idTipoReg;
    @Column(name = "TIPOTRAMI_DESCRIPCI")
    private String descripcion;
    @Column(name = "TIPOTRAMI_GENEPAGO")
    private String generaPago;
    @Column(name = "TIPOTRAMI_ESTADO")
    private String estado;
    @Column(name = "TIPOTRAMI_SIGLA")
    private String sigla;
    @Column(name = "TIPOTRAMI_ESTAMCRON")
    private String estaMcRon;
    @Column(name = "TIPOTRAMI_ORDEN")
    private String orden;
    @Column(name = "TIPOTRAMI_SOLICITUD")
    private String solicitud;
    @Column(name = "TIPOTRAMI_MULTILIQU")
    private String multiLiqu;
    @Column(name = "TIPOTRAMI_EJEDESFIS")
    private String ejeDesFis;
    @Column(name = "TIPOTRAMI_METTIPDES")
    private String metTipdes;
    @Column(name = "TIPOTRAMI_REQVALRTM")
    private String reqValRtm;
    @Column(name = "TIPOTRAMI_REVATPCO")
    private String revAtpCo;
    @Column(name = "TIPOTRAMI_CUPROTRAU")
    private String cupRotRau;
    @Column(name = "TIPOTRAMI_REVALRNEC")
    private String revalRnec;

    public String getCodTipoTrami() {
        return codTipoTrami;
    }

    public void setCodTipoTrami(String codTipoTrami) {
        this.codTipoTrami = codTipoTrami;
    }

    public String getIdTipoReg() {
        return idTipoReg;
    }

    public void setIdTipoReg(String idTipoReg) {
        this.idTipoReg = idTipoReg;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGeneraPago() {
        return generaPago;
    }

    public void setGeneraPago(String generaPago) {
        this.generaPago = generaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEstaMcRon() {
        return estaMcRon;
    }

    public void setEstaMcRon(String estaMcRon) {
        this.estaMcRon = estaMcRon;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getMultiLiqu() {
        return multiLiqu;
    }

    public void setMultiLiqu(String multiLiqu) {
        this.multiLiqu = multiLiqu;
    }

    public String getEjeDesFis() {
        return ejeDesFis;
    }

    public void setEjeDesFis(String ejeDesFis) {
        this.ejeDesFis = ejeDesFis;
    }

    public String getMetTipdes() {
        return metTipdes;
    }

    public void setMetTipdes(String metTipdes) {
        this.metTipdes = metTipdes;
    }

    public String getReqValRtm() {
        return reqValRtm;
    }

    public void setReqValRtm(String reqValRtm) {
        this.reqValRtm = reqValRtm;
    }

    public String getRevAtpCo() {
        return revAtpCo;
    }

    public void setRevAtpCo(String revAtpCo) {
        this.revAtpCo = revAtpCo;
    }

    public String getCupRotRau() {
        return cupRotRau;
    }

    public void setCupRotRau(String cupRotRau) {
        this.cupRotRau = cupRotRau;
    }

    public String getRevalRnec() {
        return revalRnec;
    }

    public void setRevalRnec(String revalRnec) {
        this.revalRnec = revalRnec;
    }
    
    
}
