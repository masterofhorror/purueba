package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the RA_AUTOMOTOR database table.
 *
 */
@Entity
@Cacheable(false)
@Table(name = "RA_AUTOMOTOR", schema = "RUNTPROD")
@NamedQueries({
    @NamedQuery(name = "Automotor.findById", query = "SELECT a FROM Automotor a WHERE a.automotorNroregveh=:idautomotor"),
    @NamedQuery(name = "Automotor.findByPlaca", query = "SELECT a FROM Automotor a WHERE a.automotorPlacaNumplaca=:placa"),// AND a.automotorEstavehicNombre='ACTIVO'"),
    @NamedQuery(name = "Automotor.findByCountPlaca", query = "SELECT COUNT(a) FROM Automotor a WHERE a.automotorPlacaNumplaca=:placa"),
    @NamedQuery(name = "Automotor.findByCountVehiculoRNTC", query = "SELECT COUNT(a) FROM Automotor a WHERE a.automotorPlacaNumplaca=:placa AND a.automotoridClase IN (4, 8)"),
    @NamedQuery(name = "Automotor.findByMotorVin", query = "SELECT a FROM Automotor a WHERE a.automotorNromotor=:motor AND a.automotorIdveunint=:vin AND a.automotorEstavehicNombre='ACTIVO'"),
    @NamedQuery(name = "Automotor,findByValidaRnrys", query = "SELECT COUNT(a) FROM Automotor a WHERE a.automotorPlacaNumplaca =:placa AND a.automotoridClase IN (24, 41)"),
    @NamedQuery(name = "Automotor,findByValidaRnma", query = "SELECT COUNT(a) FROM Automotor a WHERE a.automotorPlacaNumplaca =:placa AND a.automotoridClase IN (56,57,52,41,42,43,44,45,53,54,24,25)"),
    @NamedQuery(name = "Automotor.findByMotorVinSinEstado", query = "SELECT a FROM Automotor a "
            + "WHERE a.automotorIdveunint=:vin AND (:motor is null or :motor = '' or a.automotorNromotor=:motor)"),
    @NamedQuery(name = "Automotor.findByAutomotorNroregveh", query = "SELECT a FROM Automotor a WHERE a.automotorNroregveh = :automotorNroregveh")

})
public class Automotor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SeqId", sequenceName = "RUNTPROD.RA_AUTOMOTOR_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "SeqId", strategy = GenerationType.SEQUENCE)
    @Column(name = "AUTOMOTOR_NROREGVEH")
    private long automotorNroregveh;

    @Column(name = "AUTOMOTOR_ANOFABRIC")
    private BigDecimal automotorAnofabric;
    
    @Column(name = "AUTOMOTOR_CLASVEHIC_IDCLASE")
    private Integer automotoridClase;

    @Column(name = "AUTOMOTOR_ANTICLASI_NOMBRE")
    private String automotorAnticlasiNombre;

    @Column(name = "AUTOMOTOR_ANTIDECLA")
    private String automotorAntidecla;

    @JoinColumn(name = "AUTOMOTOR_AUTOTRANS_IDAUTTRA")
    @OneToOne
    private AutoridadTransitoHQ automotorAutotransIdauttra;

    @Column(name = "AUTOMOTOR_CILINDRAJ")
    private BigDecimal automotorCilindraj;

    @Column(name = "AUTOMOTOR_ENSENAZA")
    private String automotorEnsenaza;

    @Column(name = "AUTOMOTOR_ESTAMIGR")
    private String automotorEstamigr;

    @Column(name = "AUTOMOTOR_ESTAREPOS")
    private String automotorEstarepos;

    @Column(name = "AUTOMOTOR_ESTAVEHIC_NOMBRE")
    private String automotorEstavehicNombre;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTOMOTOR_FECACEDEC")
    private Date automotorFecacedec;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTOMOTOR_FECHACTUA")
    private Date automotorFechactua;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTOMOTOR_FECHCANCE")
    private Date automotorFechcance;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTOMOTOR_FECHREGIS")
    private Date automotorFechregis;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTOMOTOR_FECMIGRA")
    private Date automotorFecmigra;

    @Column(name = "AUTOMOTOR_GRUPO_IDGRUPO")
    private String automotorGrupoIdgrupo;

    @Column(name = "AUTOMOTOR_IDVEUNINT")
    private String automotorIdveunint;

    @Column(name = "AUTOMOTOR_IMPORTADO_NOMBRE")
    private String automotorImportadoNombre;

    @Column(name = "AUTOMOTOR_MIGRADO")
    private String automotorMigrado;

    @Column(name = "AUTOMOTOR_MODASERVI_IDEMODSER")
    private Integer automotorModaserviIdemodser;
    
    @JoinColumn(name = "AUTOMOTOR_TIPOSERVI_IDETIPSER")
    @OneToOne
    private TipoServicio tipoServicio;

    @Column(name = "AUTOMOTOR_MODELO")
    private BigDecimal automotorModelo;

    @Column(name = "AUTOMOTOR_MOTIVO_NOMBRE")
    private String automotorMotivoNombre;

    @Column(name = "AUTOMOTOR_NIVESERVI_IDNIVSER")
    private BigDecimal automotorNiveserviIdnivser;

    @Column(name = "AUTOMOTOR_NROCHASIS")
    private String automotorNrochasis;

    @Column(name = "AUTOMOTOR_NROMOTOR")
    private String automotorNromotor;

    @Column(name = "AUTOMOTOR_NROREGCHA")
    private String automotorNroregcha;

    @Column(name = "AUTOMOTOR_NROREGMOT")
    private String automotorNroregmot;

    @Column(name = "AUTOMOTOR_NROREGPLA")
    private String automotorNroregpla;

    @Column(name = "AUTOMOTOR_NROREGSER")
    private String automotorNroregser;

    @Column(name = "AUTOMOTOR_NROREGVIN")
    private String automotorNroregvin;

    @Column(name = "AUTOMOTOR_NROSERIE")
    private String automotorNroserie;

    @Column(name = "AUTOMOTOR_NUEVMODEL")
    private BigDecimal automotorNuevmodel;

    @Column(name = "AUTOMOTOR_NUMCONWS")
    private BigDecimal automotorNumconws;

    @Column(name = "AUTOMOTOR_ORIGREGIS_IDEORGREG")
    private BigDecimal automotorOrigregisIdeorgreg;

    @Column(name = "AUTOMOTOR_PAIS_IDPAISORI")
    private BigDecimal automotorPaisIdpaisori;

    @Column(name = "AUTOMOTOR_PLACA_NUMPLACA")
    private String automotorPlacaNumplaca;

    @Column(name = "AUTOMOTOR_PLAQUETA")
    private String automotorPlaqueta;

    @Column(name = "AUTOMOTOR_RADIACCIO_IDRADIO")
    private BigDecimal automotorRadiaccioIdradio;

    @Column(name = "AUTOMOTOR_REPOTENCI")
    private String automotorRepotenci;

    @Column(name = "AUTOMOTOR_SEGUESTAD")
    private String automotorSeguestad;

    @Column(name = "AUTOMOTOR_TIPACTREM_NOMBRE")
    private String automotorTipactremNombre;

    @Column(name = "AUTOMOTOR_TIPOMOTOR_IDTIPMOT")
    private BigDecimal automotorTipomotorIdtipmot;

    @Column(name = "AUTOMOTOR_UNIMEDCIL_ID")
    private BigDecimal automotorUnimedcilId;

    @Column(name = "AUTOMOTOR_VALDOCEXI")
    private String automotorValdocexi;

    public Integer getIdClase() {
        return automotoridClase;
    }

    public Integer getAutomotoridClase() {
        return automotoridClase;
    }

    public void setAutomotoridClase(Integer automotoridClase) {
        this.automotoridClase = automotoridClase;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public void setIdClase(Integer idClase) {
        this.automotoridClase = idClase;
    }

    public long getAutomotorNroregveh() {
        return this.automotorNroregveh;
    }

    public void setAutomotorNroregveh(long automotorNroregveh) {
        this.automotorNroregveh = automotorNroregveh;
    }

    public BigDecimal getAutomotorAnofabric() {
        return this.automotorAnofabric;
    }

    public void setAutomotorAnofabric(BigDecimal automotorAnofabric) {
        this.automotorAnofabric = automotorAnofabric;
    }

    public String getAutomotorAnticlasiNombre() {
        return this.automotorAnticlasiNombre;
    }

    public void setAutomotorAnticlasiNombre(String automotorAnticlasiNombre) {
        this.automotorAnticlasiNombre = automotorAnticlasiNombre;
    }

    public String getAutomotorAntidecla() {
        return this.automotorAntidecla;
    }

    public void setAutomotorAntidecla(String automotorAntidecla) {
        this.automotorAntidecla = automotorAntidecla;
    }

    public AutoridadTransitoHQ getAutomotorAutotransIdauttra() {
        return this.automotorAutotransIdauttra;
    }

    public void setAutomotorAutotransIdauttra(AutoridadTransitoHQ automotorAutotransIdauttra) {
        this.automotorAutotransIdauttra = automotorAutotransIdauttra;
    }

    public BigDecimal getAutomotorCilindraj() {
        return this.automotorCilindraj;
    }

    public void setAutomotorCilindraj(BigDecimal automotorCilindraj) {
        this.automotorCilindraj = automotorCilindraj;
    }

    public String getAutomotorEnsenaza() {
        return this.automotorEnsenaza;
    }

    public void setAutomotorEnsenaza(String automotorEnsenaza) {
        this.automotorEnsenaza = automotorEnsenaza;
    }

    public String getAutomotorEstamigr() {
        return this.automotorEstamigr;
    }

    public void setAutomotorEstamigr(String automotorEstamigr) {
        this.automotorEstamigr = automotorEstamigr;
    }

    public String getAutomotorEstarepos() {
        return this.automotorEstarepos;
    }

    public void setAutomotorEstarepos(String automotorEstarepos) {
        this.automotorEstarepos = automotorEstarepos;
    }

    public String getAutomotorEstavehicNombre() {
        return this.automotorEstavehicNombre;
    }

    public void setAutomotorEstavehicNombre(String automotorEstavehicNombre) {
        this.automotorEstavehicNombre = automotorEstavehicNombre;
    }

    public Date getAutomotorFecacedec() {
        return this.automotorFecacedec == null ? null : new Date(this.automotorFecacedec.getTime());
    }

    public void setAutomotorFecacedec(Date automotorFecacedec) {
        this.automotorFecacedec = new Date(automotorFecacedec.getTime());
    }

    public Date getAutomotorFechactua() {
        return this.automotorFechactua == null ? null : new Date(this.automotorFechactua.getTime());
    }

    public void setAutomotorFechactua(Date automotorFechactua) {
        this.automotorFechactua = new Date(automotorFechactua.getTime());
    }

    public Date getAutomotorFechcance() {
        return this.automotorFechcance == null ? null : new Date(this.automotorFechcance.getTime());
    }

    public void setAutomotorFechcance(Date automotorFechcance) {
        this.automotorFechcance = new Date(automotorFechcance.getTime());
    }

    public Date getAutomotorFechregis() {
        return this.automotorFechregis == null ? null : new Date(this.automotorFechregis.getTime());
    }

    public void setAutomotorFechregis(Date automotorFechregis) {
        this.automotorFechregis = new Date(automotorFechregis.getTime());
    }

    public Date getAutomotorFecmigra() {
        return this.automotorFecmigra == null ? null : new Date(this.automotorFecmigra.getTime());
    }

    public void setAutomotorFecmigra(Date automotorFecmigra) {
        this.automotorFecmigra = new Date(automotorFecmigra.getTime());
    }

    public String getAutomotorGrupoIdgrupo() {
        return this.automotorGrupoIdgrupo;
    }

    public void setAutomotorGrupoIdgrupo(String automotorGrupoIdgrupo) {
        this.automotorGrupoIdgrupo = automotorGrupoIdgrupo;
    }

    public String getAutomotorIdveunint() {
        return this.automotorIdveunint;
    }

    public void setAutomotorIdveunint(String automotorIdveunint) {
        this.automotorIdveunint = automotorIdveunint;
    }

    public String getAutomotorImportadoNombre() {
        return this.automotorImportadoNombre;
    }

    public void setAutomotorImportadoNombre(String automotorImportadoNombre) {
        this.automotorImportadoNombre = automotorImportadoNombre;
    }

    public String getAutomotorMigrado() {
        return this.automotorMigrado;
    }

    public void setAutomotorMigrado(String automotorMigrado) {
        this.automotorMigrado = automotorMigrado;
    }

    public Integer getAutomotorModaserviIdemodser() {
        return this.automotorModaserviIdemodser;
    }

    public void setAutomotorModaserviIdemodser(Integer automotorModaserviIdemodser) {
        this.automotorModaserviIdemodser = automotorModaserviIdemodser;
    }

    public BigDecimal getAutomotorModelo() {
        return this.automotorModelo;
    }

    public void setAutomotorModelo(BigDecimal automotorModelo) {
        this.automotorModelo = automotorModelo;
    }

    public String getAutomotorMotivoNombre() {
        return this.automotorMotivoNombre;
    }

    public void setAutomotorMotivoNombre(String automotorMotivoNombre) {
        this.automotorMotivoNombre = automotorMotivoNombre;
    }

    public BigDecimal getAutomotorNiveserviIdnivser() {
        return this.automotorNiveserviIdnivser;
    }

    public void setAutomotorNiveserviIdnivser(BigDecimal automotorNiveserviIdnivser) {
        this.automotorNiveserviIdnivser = automotorNiveserviIdnivser;
    }

    public String getAutomotorNrochasis() {
        return this.automotorNrochasis;
    }

    public void setAutomotorNrochasis(String automotorNrochasis) {
        this.automotorNrochasis = automotorNrochasis;
    }

    public String getAutomotorNromotor() {
        return this.automotorNromotor;
    }

    public void setAutomotorNromotor(String automotorNromotor) {
        this.automotorNromotor = automotorNromotor;
    }

    public String getAutomotorNroregcha() {
        return this.automotorNroregcha;
    }

    public void setAutomotorNroregcha(String automotorNroregcha) {
        this.automotorNroregcha = automotorNroregcha;
    }

    public String getAutomotorNroregmot() {
        return this.automotorNroregmot;
    }

    public void setAutomotorNroregmot(String automotorNroregmot) {
        this.automotorNroregmot = automotorNroregmot;
    }

    public String getAutomotorNroregpla() {
        return this.automotorNroregpla;
    }

    public void setAutomotorNroregpla(String automotorNroregpla) {
        this.automotorNroregpla = automotorNroregpla;
    }

    public String getAutomotorNroregser() {
        return this.automotorNroregser;
    }

    public void setAutomotorNroregser(String automotorNroregser) {
        this.automotorNroregser = automotorNroregser;
    }

    public String getAutomotorNroregvin() {
        return this.automotorNroregvin;
    }

    public void setAutomotorNroregvin(String automotorNroregvin) {
        this.automotorNroregvin = automotorNroregvin;
    }

    public String getAutomotorNroserie() {
        return this.automotorNroserie;
    }

    public void setAutomotorNroserie(String automotorNroserie) {
        this.automotorNroserie = automotorNroserie;
    }

    public BigDecimal getAutomotorNuevmodel() {
        return this.automotorNuevmodel;
    }

    public void setAutomotorNuevmodel(BigDecimal automotorNuevmodel) {
        this.automotorNuevmodel = automotorNuevmodel;
    }

    public BigDecimal getAutomotorNumconws() {
        return this.automotorNumconws;
    }

    public void setAutomotorNumconws(BigDecimal automotorNumconws) {
        this.automotorNumconws = automotorNumconws;
    }

    public BigDecimal getAutomotorOrigregisIdeorgreg() {
        return this.automotorOrigregisIdeorgreg;
    }

    public void setAutomotorOrigregisIdeorgreg(BigDecimal automotorOrigregisIdeorgreg) {
        this.automotorOrigregisIdeorgreg = automotorOrigregisIdeorgreg;
    }

    public BigDecimal getAutomotorPaisIdpaisori() {
        return this.automotorPaisIdpaisori;
    }

    public void setAutomotorPaisIdpaisori(BigDecimal automotorPaisIdpaisori) {
        this.automotorPaisIdpaisori = automotorPaisIdpaisori;
    }

    public String getAutomotorPlacaNumplaca() {
        return this.automotorPlacaNumplaca;
    }

    public void setAutomotorPlacaNumplaca(String automotorPlacaNumplaca) {
        this.automotorPlacaNumplaca = automotorPlacaNumplaca;
    }

    public String getAutomotorPlaqueta() {
        return this.automotorPlaqueta;
    }

    public void setAutomotorPlaqueta(String automotorPlaqueta) {
        this.automotorPlaqueta = automotorPlaqueta;
    }

    public BigDecimal getAutomotorRadiaccioIdradio() {
        return this.automotorRadiaccioIdradio;
    }

    public void setAutomotorRadiaccioIdradio(BigDecimal automotorRadiaccioIdradio) {
        this.automotorRadiaccioIdradio = automotorRadiaccioIdradio;
    }

    public String getAutomotorRepotenci() {
        return this.automotorRepotenci;
    }

    public void setAutomotorRepotenci(String automotorRepotenci) {
        this.automotorRepotenci = automotorRepotenci;
    }

    public String getAutomotorSeguestad() {
        return this.automotorSeguestad;
    }

    public void setAutomotorSeguestad(String automotorSeguestad) {
        this.automotorSeguestad = automotorSeguestad;
    }

    public String getAutomotorTipactremNombre() {
        return this.automotorTipactremNombre;
    }

    public void setAutomotorTipactremNombre(String automotorTipactremNombre) {
        this.automotorTipactremNombre = automotorTipactremNombre;
    }

    public BigDecimal getAutomotorTipomotorIdtipmot() {
        return this.automotorTipomotorIdtipmot;
    }

    public void setAutomotorTipomotorIdtipmot(BigDecimal automotorTipomotorIdtipmot) {
        this.automotorTipomotorIdtipmot = automotorTipomotorIdtipmot;
    }

    public BigDecimal getAutomotorUnimedcilId() {
        return this.automotorUnimedcilId;
    }

    public void setAutomotorUnimedcilId(BigDecimal automotorUnimedcilId) {
        this.automotorUnimedcilId = automotorUnimedcilId;
    }

    public String getAutomotorValdocexi() {
        return this.automotorValdocexi;
    }

    public void setAutomotorValdocexi(String automotorValdocexi) {
        this.automotorValdocexi = automotorValdocexi;
    }

}
