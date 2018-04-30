package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the GE_AUTOTRANS database table.
 *
 */
@Entity
@Table(name = "GE_AUTOTRANS", schema = "RUNTPROD")
@NamedQueries({
    @NamedQuery(name = "AutoridadTransitoHQ.findByIdPersona", query = "SELECT w FROM AutoridadTransitoHQ w WHERE w.autotransIdauttra = :idpersona"),
    @NamedQuery(name = "AutoridadTransitoHQ.findById", query = "SELECT count(w) FROM AutoridadTransitoHQ w WHERE w.autotransIdauttra = :idautoridad"),
    @NamedQuery(name = "AutoridadTransitoHQ.findByAutotransIdauttra", query = "SELECT a FROM AutoridadTransitoHQ a WHERE a.autotransIdauttra = :idSecretaria")
})
public class AutoridadTransitoHQ implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AUTOTRANS_IDAUTTRA")
    private Long autotransIdauttra;

    @Column(name = "AUTOTRANS_AREAINFLU_NOMBRE")
    private String autotransAreainfluNombre;

    @Column(name = "AUTOTRANS_CAJINCPRO")
    private String autotransCajincpro;

    @Column(name = "AUTOTRANS_CLASAUTOR_NOMBRE")
    private String autotransClasautorNombre;

    @Column(name = "AUTOTRANS_ESTADO")
    private String autotransEstado;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTOTRANS_FECHFINAL")
    private Date autotransFechfinal;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTOTRANS_FECHINICI")
    private Date autotransFechinici;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTOTRANS_FECRESCLA")
    private Date autotransFecrescla;

    @Column(name = "AUTOTRANS_IAC")
    private String autotransIac;

    @Column(name = "AUTOTRANS_INDIPOBLA")
    private BigDecimal autotransIndipobla;

    @Column(name = "AUTOTRANS_MUNICIPIO_DIVIPOL")
    private BigDecimal autotransMunicipioDivipol;

    @Column(name = "AUTOTRANS_NRORESCLA")
    private String autotransNrorescla;

    @Column(name = "AUTOTRANS_PARQAUTOM")
    private BigDecimal autotransParqautom;

    @Column(name = "AUTOTRANS_PRESUPUES")
    private BigDecimal autotransPresupues;

    @Column(name = "AUTOTRANS_TIPOAT")
    private String autotransTipoat;

    @Column(name = "AUTOTRANS_TIPOSISTE_ID")
    private String autotransTiposisteId;

    @Column(name = "AUTOTRANS_VALIIP")
    private String autotransValiip;
    
    @JoinColumn(name = "AUTOTRANS_EMPRESA_PERSONA")
    @OneToOne
    private Empresa empresaPersona;

    public Long getAutotransIdauttra() {
        return this.autotransIdauttra;
    }

    public void setAutotransIdauttra(Long autotransIdauttra) {
        this.autotransIdauttra = autotransIdauttra;
    }

    public String getAutotransAreainfluNombre() {
        return this.autotransAreainfluNombre;
    }

    public void setAutotransAreainfluNombre(String autotransAreainfluNombre) {
        this.autotransAreainfluNombre = autotransAreainfluNombre;
    }

    public String getAutotransCajincpro() {
        return this.autotransCajincpro;
    }

    public void setAutotransCajincpro(String autotransCajincpro) {
        this.autotransCajincpro = autotransCajincpro;
    }

    public String getAutotransClasautorNombre() {
        return this.autotransClasautorNombre;
    }

    public void setAutotransClasautorNombre(String autotransClasautorNombre) {
        this.autotransClasautorNombre = autotransClasautorNombre;
    }

    public String getAutotransEstado() {
        return this.autotransEstado;
    }

    public void setAutotransEstado(String autotransEstado) {
        this.autotransEstado = autotransEstado;
    }

    public Date getAutotransFechfinal() {
        return this.autotransFechfinal;
    }

    public void setAutotransFechfinal(Date autotransFechfinal) {
        this.autotransFechfinal = autotransFechfinal;
    }

    public Date getAutotransFechinici() {
        return this.autotransFechinici;
    }

    public void setAutotransFechinici(Date autotransFechinici) {
        this.autotransFechinici = autotransFechinici;
    }

    public Date getAutotransFecrescla() {
        return this.autotransFecrescla;
    }

    public void setAutotransFecrescla(Date autotransFecrescla) {
        this.autotransFecrescla = autotransFecrescla;
    }

    public String getAutotransIac() {
        return this.autotransIac;
    }

    public void setAutotransIac(String autotransIac) {
        this.autotransIac = autotransIac;
    }

    public BigDecimal getAutotransIndipobla() {
        return this.autotransIndipobla;
    }

    public void setAutotransIndipobla(BigDecimal autotransIndipobla) {
        this.autotransIndipobla = autotransIndipobla;
    }

    public BigDecimal getAutotransMunicipioDivipol() {
        return this.autotransMunicipioDivipol;
    }

    public void setAutotransMunicipioDivipol(BigDecimal autotransMunicipioDivipol) {
        this.autotransMunicipioDivipol = autotransMunicipioDivipol;
    }

    public String getAutotransNrorescla() {
        return this.autotransNrorescla;
    }

    public void setAutotransNrorescla(String autotransNrorescla) {
        this.autotransNrorescla = autotransNrorescla;
    }

    public BigDecimal getAutotransParqautom() {
        return this.autotransParqautom;
    }

    public void setAutotransParqautom(BigDecimal autotransParqautom) {
        this.autotransParqautom = autotransParqautom;
    }

    public BigDecimal getAutotransPresupues() {
        return this.autotransPresupues;
    }

    public void setAutotransPresupues(BigDecimal autotransPresupues) {
        this.autotransPresupues = autotransPresupues;
    }

    public String getAutotransTipoat() {
        return this.autotransTipoat;
    }

    public void setAutotransTipoat(String autotransTipoat) {
        this.autotransTipoat = autotransTipoat;
    }

    public String getAutotransTiposisteId() {
        return this.autotransTiposisteId;
    }

    public void setAutotransTiposisteId(String autotransTiposisteId) {
        this.autotransTiposisteId = autotransTiposisteId;
    }

    public String getAutotransValiip() {
        return this.autotransValiip;
    }

    public void setAutotransValiip(String autotransValiip) {
        this.autotransValiip = autotransValiip;
    }

    public Empresa getEmpresaPersona() {
        return empresaPersona;
    }

    public void setEmpresaPersona(Empresa empresaPersona) {
        this.empresaPersona = empresaPersona;
    }
    

}
