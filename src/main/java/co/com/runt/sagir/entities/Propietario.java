package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the TR_PROPIETAR database table.
 *
 */
@Entity
@Table(name = "TR_PROPIETAR", schema = "RUNTPROD")
@NamedQueries({
    @NamedQuery(name = "Propietario.findById", query = "SELECT w FROM Propietario w WHERE w.propietarIdpropiet =:idpropietario"),
    @NamedQuery(name = "Propietario.findByPlaca", query = "SELECT w FROM Propietario w WHERE w.raAutomotor.automotorPlacaNumplaca =:placa and  w.propietarEstadoNombre='ACTIVO'"),
    @NamedQuery(name = "Propietario.findByPropietario", query = "SELECT w FROM Propietario w WHERE w.raAutomotor.automotorNroregveh =:idautomotor"),
    @NamedQuery(name = "Propietario.updateByPlaca", query = "UPDATE Propietario w SET w.propietarEstadoNombre = 'INACTIVO' WHERE w.raAutomotor.automotorPlacaNumplaca =:placa and  w.propietarEstadoNombre='ACTIVO'"),
    @NamedQuery(name = "Propietario.findByNroRegVeh", query = "SELECT w FROM Propietario w WHERE w.raAutomotor.automotorNroregveh =:nroRegVeh and  w.propietarEstadoNombre='ACTIVO'")
})

public class Propietario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator (name = "RUNTPROD.TR_PROPIETAR_SEQ", initialValue = 1, sequenceName = "RUNTPROD.TR_PROPIETAR_SEQ")
    @GeneratedValue (generator = "RUNTPROD.TR_PROPIETAR_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "PROPIETAR_IDPROPIET")
    private long propietarIdpropiet;

    @Column(name = "PROPIETAR_ESTADO_NOMBRE")
    private String propietarEstadoNombre;

    @Temporal(TemporalType.DATE)
    @Column(name = "PROPIETAR_FECFINPRO")
    private Date propietarFecfinpro;

    @Temporal(TemporalType.DATE)
    @Column(name = "PROPIETAR_FECHACTUA")
    private Date propietarFechactua;

    @Temporal(TemporalType.DATE)
    @Column(name = "PROPIETAR_FECINIPRO")
    private Date propietarFecinipro;

    @Temporal(TemporalType.DATE)
    @Column(name = "PROPIETAR_FECMIGRA")
    private Date propietarFecmigra;

    @Column(name = "PROPIETAR_MIGRADO")
    private String propietarMigrado;

    @Column(name = "PROPIETAR_PORCPROPI")
    private String propietarPorcpropi;

    @Column(name = "PROPIETAR_PROINDIVI")
    private String propietarProindivi;

    @Column(name = "PROPIETAR_PROSEGEST")
    private String propietarProsegest;

    @Column(name = "PROPIETAR_TIPOPROPI_CODTIPPRO")
    private String propietarTipopropiCodtippro;

    @ManyToOne
    @JoinColumn(name = "PROPIETAR_AUTOMOTOR_NOREGVEHI")
    private Automotor raAutomotor;

    @ManyToOne
    @JoinColumn(name = "PROPIETAR_PERSONA_IDPERSONA")
    private PersonaHQ trPersona;

    public long getPropietarIdpropiet() {
        return this.propietarIdpropiet;
    }

    public void setPropietarIdpropiet(long propietarIdpropiet) {
        this.propietarIdpropiet = propietarIdpropiet;
    }

    public String getPropietarEstadoNombre() {
        return this.propietarEstadoNombre;
    }

    public void setPropietarEstadoNombre(String propietarEstadoNombre) {
        this.propietarEstadoNombre = propietarEstadoNombre;
    }

    public Date getPropietarFecfinpro() {
        return this.propietarFecfinpro;
    }

    public void setPropietarFecfinpro(Date propietarFecfinpro) {
        this.propietarFecfinpro = propietarFecfinpro;
    }

    public Date getPropietarFechactua() {
        return this.propietarFechactua;
    }

    public void setPropietarFechactua(Date propietarFechactua) {
        this.propietarFechactua = propietarFechactua;
    }

    public Date getPropietarFecinipro() {
        return this.propietarFecinipro;
    }

    public void setPropietarFecinipro(Date propietarFecinipro) {
        this.propietarFecinipro = propietarFecinipro;
    }

    public Date getPropietarFecmigra() {
        return this.propietarFecmigra;
    }

    public void setPropietarFecmigra(Date propietarFecmigra) {
        this.propietarFecmigra = propietarFecmigra;
    }

    public String getPropietarMigrado() {
        return this.propietarMigrado;
    }

    public void setPropietarMigrado(String propietarMigrado) {
        this.propietarMigrado = propietarMigrado;
    }

    public String getPropietarPorcpropi() {
        return this.propietarPorcpropi;
    }

    public void setPropietarPorcpropi(String propietarPorcpropi) {
        this.propietarPorcpropi = propietarPorcpropi;
    }

    public String getPropietarProindivi() {
        return this.propietarProindivi;
    }

    public void setPropietarProindivi(String propietarProindivi) {
        this.propietarProindivi = propietarProindivi;
    }

    public String getPropietarProsegest() {
        return this.propietarProsegest;
    }

    public void setPropietarProsegest(String propietarProsegest) {
        this.propietarProsegest = propietarProsegest;
    }

    public String getPropietarTipopropiCodtippro() {
        return this.propietarTipopropiCodtippro;
    }

    public void setPropietarTipopropiCodtippro(String propietarTipopropiCodtippro) {
        this.propietarTipopropiCodtippro = propietarTipopropiCodtippro;
    }

    public Automotor getRaAutomotor() {
        return this.raAutomotor;
    }

    public void setRaAutomotor(Automotor raAutomotor) {
        this.raAutomotor = raAutomotor;
    }

    public PersonaHQ getTrPersona() {
        return this.trPersona;
    }

    public void setTrPersona(PersonaHQ trPersona) {
        this.trPersona = trPersona;
    }

}
