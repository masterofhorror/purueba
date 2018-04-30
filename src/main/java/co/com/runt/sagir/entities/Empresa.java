package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the GE_EMPRESA database table.
 *
 * @author Ccepeda
 */
@Entity
@Table(name = "GE_EMPRESA", schema = "RUNTPROD")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Empresa.findByNit", query = "SELECT e FROM Empresa e WHERE e.persona.personaTipoidentIdtipdoc='N' AND e.persona.personaNrodocume =:nit AND e.persona.personaEstapersoNombre='ACTIVA'"),
    @NamedQuery(name = "Empresa.findById", query = " SELECT e FROM Empresa e WHERE e.persona.personaIdpersona =:idempresa"),
    @NamedQuery(name = "Empresa.findPrincipalByNit", query = "SELECT e FROM Empresa e WHERE e.persona.personaTipoidentIdtipdoc='N' AND e.persona.personaNrodocume =:nit AND e.persona.personaEstapersoNombre='ACTIVA' and e.empresaPrincipal IS NULL"),
    @NamedQuery(name = "Empresa.findSedesByNit", query = "SELECT e FROM Empresa e WHERE e.persona.personaTipoidentIdtipdoc='N' AND e.persona.personaNrodocume =:nit AND e.persona.personaEstapersoNombre='ACTIVA' and e.empresaPrincipal IS NOT NULL"),
    @NamedQuery(name = "Empresa.findByEmpresaPersonaIdpersona", query = "SELECT e FROM Empresa e WHERE e.persona.personaIdpersona = :empresaPersonaIdpersona")
})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @OneToOne
    @JoinColumn(name = "EMPRESA_PERSONA_IDPERSONA", referencedColumnName = "PERSONA_IDPERSONA")
    private PersonaHQ persona;

    @Column(name = "EMPRESA_CANUSUACT")
    private BigDecimal empresaCanusuact;

    @Column(name = "EMPRESA_CANUSUMAX")
    private BigDecimal empresaCanusumax;

    @Column(name = "EMPRESA_CAPIPAGAD")
    private BigDecimal empresaCapipagad;

    @Column(name = "EMPRESA_CERTEXIST")
    private BigDecimal empresaCertexist;

    @Column(name = "EMPRESA_EMPRCOMPE")
    private String empresaEmprcompe;

    @Column(name = "EMPRESA_ESTAEMPRE_NOMBRE")
    private String empresaEstaempreNombre;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPRESA_FECHACTUA")
    private Date empresaFechactua;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPRESA_FECHNOVED")
    private Date empresaFechnoved;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPRESA_FECMIGRA")
    private Date empresaFecmigra;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPRESA_FECREGINI")
    private Date empresaFecregini;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPRESA_FEVEREMER")
    private Date empresaFeveremer;

    @Column(name = "EMPRESA_MAIL")
    private String empresaMail;

    @Column(name = "EMPRESA_MAILCONTA")
    private String empresaMailconta;

    @Column(name = "EMPRESA_MATRMERCA")
    private BigDecimal empresaMatrmerca;

    @Column(name = "EMPRESA_MIGRADO")
    private String empresaMigrado;

    @Column(name = "EMPRESA_MUNICIPIO_IDMUNICIP")
    private BigDecimal empresaMunicipioIdmunicip;

    @Column(name = "EMPRESA_NOMBRCORT")
    private String empresaNombrcort;

    @Column(name = "EMPRESA_PAGINAWEB")
    private String empresaPaginaweb;

    @Column(name = "EMPRESA_PATRLIQUI")
    private BigDecimal empresaPatrliqui;

    @Column(name = "EMPRESA_PENDHABMT")
    private String empresaPendhabmt;

    @Column(name = "EMPRESA_PERCONEXT")
    private String empresaPerconext;

    @Column(name = "EMPRESA_PERCONTEL")
    private String empresaPercontel;

    @Column(name = "EMPRESA_PERSONA")
    private String empresaPersona;

    @Column(name = "EMPRESA_PRESSERVI")
    private String empresaPresservi;

    @Column(name = "EMPRESA_RAZOSOCIA")
    private String empresaRazosocia;

    @Column(name = "EMPRESA_RUT")
    private String empresaRut;

    @Column(name = "EMPRESA_SIGLA")
    private String empresaSigla;

    @Column(name = "EMPRESA_TELINFUSU")
    private String empresaTelinfusu;

    @Column(name = "EMPRESA_TIPOAUTOR_NOMBRE")
    private String empresaTipoautorNombre;

    @Column(name = "EMPRESA_TIPOENTID_NOMBRE")
    private String empresaTipoentidNombre;

    @Column(name = "EMPRESA_TIPOSOCIE_NOMBRE")
    private String empresaTiposocieNombre;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPRESA_VIGENSOCI")
    private Date empresaVigensoci;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPRESA_VIGREGEMP")
    private Date empresaVigregemp;

    @ManyToOne
    @JoinColumn(name = "EMPRESA_EMPRESA_PRINCIPAL")
    private Empresa empresaPrincipal;

    public BigDecimal getEmpresaCanusuact() {
        return this.empresaCanusuact;
    }

    public void setEmpresaCanusuact(BigDecimal empresaCanusuact) {
        this.empresaCanusuact = empresaCanusuact;
    }

    public BigDecimal getEmpresaCanusumax() {
        return this.empresaCanusumax;
    }

    public void setEmpresaCanusumax(BigDecimal empresaCanusumax) {
        this.empresaCanusumax = empresaCanusumax;
    }

    public BigDecimal getEmpresaCapipagad() {
        return this.empresaCapipagad;
    }

    public void setEmpresaCapipagad(BigDecimal empresaCapipagad) {
        this.empresaCapipagad = empresaCapipagad;
    }

    public BigDecimal getEmpresaCertexist() {
        return this.empresaCertexist;
    }

    public void setEmpresaCertexist(BigDecimal empresaCertexist) {
        this.empresaCertexist = empresaCertexist;
    }

    public String getEmpresaEmprcompe() {
        return this.empresaEmprcompe;
    }

    public void setEmpresaEmprcompe(String empresaEmprcompe) {
        this.empresaEmprcompe = empresaEmprcompe;
    }

    public String getEmpresaEstaempreNombre() {
        return this.empresaEstaempreNombre;
    }

    public void setEmpresaEstaempreNombre(String empresaEstaempreNombre) {
        this.empresaEstaempreNombre = empresaEstaempreNombre;
    }

    public Date getEmpresaFechactua() {
        return this.empresaFechactua;
    }

    public void setEmpresaFechactua(Date empresaFechactua) {
        this.empresaFechactua = empresaFechactua;
    }

    public Date getEmpresaFechnoved() {
        return this.empresaFechnoved;
    }

    public void setEmpresaFechnoved(Date empresaFechnoved) {
        this.empresaFechnoved = empresaFechnoved;
    }

    public Date getEmpresaFecmigra() {
        return this.empresaFecmigra;
    }

    public void setEmpresaFecmigra(Date empresaFecmigra) {
        this.empresaFecmigra = empresaFecmigra;
    }

    public Date getEmpresaFecregini() {
        return this.empresaFecregini;
    }

    public void setEmpresaFecregini(Date empresaFecregini) {
        this.empresaFecregini = empresaFecregini;
    }

    public Date getEmpresaFeveremer() {
        return this.empresaFeveremer;
    }

    public void setEmpresaFeveremer(Date empresaFeveremer) {
        this.empresaFeveremer = empresaFeveremer;
    }

    public String getEmpresaMail() {
        return this.empresaMail;
    }

    public void setEmpresaMail(String empresaMail) {
        this.empresaMail = empresaMail;
    }

    public String getEmpresaMailconta() {
        return this.empresaMailconta;
    }

    public void setEmpresaMailconta(String empresaMailconta) {
        this.empresaMailconta = empresaMailconta;
    }

    public BigDecimal getEmpresaMatrmerca() {
        return this.empresaMatrmerca;
    }

    public void setEmpresaMatrmerca(BigDecimal empresaMatrmerca) {
        this.empresaMatrmerca = empresaMatrmerca;
    }

    public String getEmpresaMigrado() {
        return this.empresaMigrado;
    }

    public void setEmpresaMigrado(String empresaMigrado) {
        this.empresaMigrado = empresaMigrado;
    }

    public BigDecimal getEmpresaMunicipioIdmunicip() {
        return this.empresaMunicipioIdmunicip;
    }

    public void setEmpresaMunicipioIdmunicip(BigDecimal empresaMunicipioIdmunicip) {
        this.empresaMunicipioIdmunicip = empresaMunicipioIdmunicip;
    }

    public String getEmpresaNombrcort() {
        return this.empresaNombrcort;
    }

    public void setEmpresaNombrcort(String empresaNombrcort) {
        this.empresaNombrcort = empresaNombrcort;
    }

    public String getEmpresaPaginaweb() {
        return this.empresaPaginaweb;
    }

    public void setEmpresaPaginaweb(String empresaPaginaweb) {
        this.empresaPaginaweb = empresaPaginaweb;
    }

    public BigDecimal getEmpresaPatrliqui() {
        return this.empresaPatrliqui;
    }

    public void setEmpresaPatrliqui(BigDecimal empresaPatrliqui) {
        this.empresaPatrliqui = empresaPatrliqui;
    }

    public String getEmpresaPendhabmt() {
        return this.empresaPendhabmt;
    }

    public void setEmpresaPendhabmt(String empresaPendhabmt) {
        this.empresaPendhabmt = empresaPendhabmt;
    }

    public String getEmpresaPerconext() {
        return this.empresaPerconext;
    }

    public void setEmpresaPerconext(String empresaPerconext) {
        this.empresaPerconext = empresaPerconext;
    }

    public String getEmpresaPercontel() {
        return this.empresaPercontel;
    }

    public void setEmpresaPercontel(String empresaPercontel) {
        this.empresaPercontel = empresaPercontel;
    }

    public String getEmpresaPersona() {
        return this.empresaPersona;
    }

    public void setEmpresaPersona(String empresaPersona) {
        this.empresaPersona = empresaPersona;
    }

    public String getEmpresaPresservi() {
        return this.empresaPresservi;
    }

    public void setEmpresaPresservi(String empresaPresservi) {
        this.empresaPresservi = empresaPresservi;
    }

    public String getEmpresaRazosocia() {
        return this.empresaRazosocia;
    }

    public void setEmpresaRazosocia(String empresaRazosocia) {
        this.empresaRazosocia = empresaRazosocia;
    }

    public String getEmpresaRut() {
        return this.empresaRut;
    }

    public void setEmpresaRut(String empresaRut) {
        this.empresaRut = empresaRut;
    }

    public String getEmpresaSigla() {
        return this.empresaSigla;
    }

    public void setEmpresaSigla(String empresaSigla) {
        this.empresaSigla = empresaSigla;
    }

    public String getEmpresaTelinfusu() {
        return this.empresaTelinfusu;
    }

    public void setEmpresaTelinfusu(String empresaTelinfusu) {
        this.empresaTelinfusu = empresaTelinfusu;
    }

    public String getEmpresaTipoautorNombre() {
        return this.empresaTipoautorNombre;
    }

    public void setEmpresaTipoautorNombre(String empresaTipoautorNombre) {
        this.empresaTipoautorNombre = empresaTipoautorNombre;
    }

    public String getEmpresaTipoentidNombre() {
        return this.empresaTipoentidNombre;
    }

    public void setEmpresaTipoentidNombre(String empresaTipoentidNombre) {
        this.empresaTipoentidNombre = empresaTipoentidNombre;
    }

    public String getEmpresaTiposocieNombre() {
        return this.empresaTiposocieNombre;
    }

    public void setEmpresaTiposocieNombre(String empresaTiposocieNombre) {
        this.empresaTiposocieNombre = empresaTiposocieNombre;
    }

    public Date getEmpresaVigensoci() {
        return this.empresaVigensoci;
    }

    public void setEmpresaVigensoci(Date empresaVigensoci) {
        this.empresaVigensoci = empresaVigensoci;
    }

    public Date getEmpresaVigregemp() {
        return this.empresaVigregemp;
    }

    public void setEmpresaVigregemp(Date empresaVigregemp) {
        this.empresaVigregemp = empresaVigregemp;
    }

    public PersonaHQ getPersona() {
        return persona;
    }

    public void setPersona(PersonaHQ persona) {
        this.persona = persona;
    }

    public Empresa getEmpresaPrincipal() {
        return empresaPrincipal;
    }

    public void setEmpresaPrincipal(Empresa empresaPrincipal) {
        this.empresaPrincipal = empresaPrincipal;
    }

}
