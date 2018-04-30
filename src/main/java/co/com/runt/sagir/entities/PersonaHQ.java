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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ccepeda
 */
@Entity
@Table(name = "TR_PERSONA", schema = "RUNTPROD")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "PersonaHQ.findAll", query = "SELECT p FROM PersonaHQ p"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQIdpersona", query = "SELECT p FROM PersonaHQ p WHERE p.personaIdpersona = :personaIdpersona"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQTipoyNumDoc", query = "SELECT COUNT(p) FROM PersonaHQ p WHERE p.personaTipoidentIdtipdoc =:tdoc and p.personaNrodocume = :ndoc AND p.personaEstapersoNombre IN ('ACTIVA','SIN REGISTRO')"),
    @NamedQuery(name = "PersonaHQ.findByMinimoID", query = "SELECT MIN(p.personaIdpersona) FROM PersonaHQ p WHERE p.personaTipoidentIdtipdoc =:tdoc and p.personaNrodocume = :ndoc AND p.personaEstapersoNombre = :personaEstapersoNombre"),
    @NamedQuery(name = "PersonaHQ.findByPersonaConsultaPersona", query = "SELECT p FROM PersonaHQ p WHERE p.personaTipoidentIdtipdoc =:tdoc and p.personaNrodocume = :ndoc AND p.personaEstapersoNombre IN ('ACTIVA','SIN REGISTRO')"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQNrodocume", query = "SELECT p FROM PersonaHQ p WHERE p.personaNrodocume = :personaNrodocume"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQTipoidentIdtipdoc", query = "SELECT p FROM PersonaHQ p WHERE p.personaTipoidentIdtipdoc = :personaTipoidentIdtipdoc"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQFecharegi", query = "SELECT p FROM PersonaHQ p WHERE p.personaFecharegi = :personaFecharegi"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQFechanove", query = "SELECT p FROM PersonaHQ p WHERE p.personaFechanove = :personaFechanove"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQEstapersoNombre", query = "SELECT p FROM PersonaHQ p WHERE p.personaEstapersoNombre = :personaEstapersoNombre"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQTipopersoNombre", query = "SELECT p FROM PersonaHQ p WHERE p.personaTipopersoNombre = :personaTipopersoNombre"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQFechexped", query = "SELECT p FROM PersonaHQ p WHERE p.personaFechexped = :personaFechexped"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQMigrado", query = "SELECT p FROM PersonaHQ p WHERE p.personaMigrado = :personaMigrado"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQFecmigra", query = "SELECT p FROM PersonaHQ p WHERE p.personaFecmigra = :personaFecmigra"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQFechactua", query = "SELECT p FROM PersonaHQ p WHERE p.personaFechactua = :personaFechactua"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQIpinscrip", query = "SELECT p FROM PersonaHQ p WHERE p.personaIpinscrip = :personaIpinscrip"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQAutotransIdauttra", query = "SELECT p FROM PersonaHQ p WHERE p.personaAutotransIdauttra = :personaAutotransIdauttra"),
    @NamedQuery(name = "PersonaHQ.findByPersonaHQUsuaregis", query = "SELECT p FROM PersonaHQ p WHERE p.personaUsuaregis = :personaUsuaregis")})
public class PersonaHQ implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator (name = "RUNTPROD.TR_PERSONA_SEQ", initialValue = 1, sequenceName = "RUNTPROD.TR_PERSONA_SEQ")
    @GeneratedValue (generator = "RUNTPROD.TR_PERSONA_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "PERSONA_IDPERSONA")
    private Long personaIdpersona;

    @Column(name = "PERSONA_NRODOCUME")
    private String personaNrodocume;

    @Column(name = "PERSONA_TIPOIDENT_IDTIPDOC")
    private String personaTipoidentIdtipdoc;

    @Column(name = "PERSONA_FECHAREGI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date personaFecharegi;

    @Column(name = "PERSONA_FECHANOVE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date personaFechanove;

    @Column(name = "PERSONA_ESTAPERSO_NOMBRE")
    private String personaEstapersoNombre;

    @Column(name = "PERSONA_TIPOPERSO_NOMBRE")
    private String personaTipopersoNombre;

    @Column(name = "PERSONA_FECHEXPED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date personaFechexped;

    @Column(name = "PERSONA_MIGRADO")
    private String personaMigrado;

    @Column(name = "PERSONA_FECMIGRA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date personaFecmigra;

    @Column(name = "PERSONA_FECHACTUA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date personaFechactua;

    @Column(name = "PERSONA_IPINSCRIP")
    private String personaIpinscrip;

    @Column(name = "PERSONA_AUTOTRANS_IDAUTTRA")
    private Long personaAutotransIdauttra;

    @Column(name = "PERSONA_USUAREGIS")
    private String personaUsuaregis;


    public Long getPersonaIdpersona() {
        return personaIdpersona;
    }

    public void setPersonaIdpersona(Long personaIdpersona) {
        this.personaIdpersona = personaIdpersona;
    }

    public String getPersonaNrodocume() {
        return personaNrodocume;
    }

    public void setPersonaNrodocume(String personaNrodocume) {
        this.personaNrodocume = personaNrodocume;
    }

    public String getPersonaTipoidentIdtipdoc() {
        return personaTipoidentIdtipdoc;
    }

    public void setPersonaTipoidentIdtipdoc(String personaTipoidentIdtipdoc) {
        this.personaTipoidentIdtipdoc = personaTipoidentIdtipdoc;
    }

    public Date getPersonaFecharegi() {
        return personaFecharegi;
    }

    public void setPersonaFecharegi(Date personaFecharegi) {
        this.personaFecharegi = personaFecharegi;
    }

    public Date getPersonaFechanove() {
        return personaFechanove;
    }

    public void setPersonaFechanove(Date personaFechanove) {
        this.personaFechanove = personaFechanove;
    }

    public String getPersonaEstapersoNombre() {
        return personaEstapersoNombre;
    }

    public void setPersonaEstapersoNombre(String personaEstapersoNombre) {
        this.personaEstapersoNombre = personaEstapersoNombre;
    }

    public String getPersonaTipopersoNombre() {
        return personaTipopersoNombre;
    }

    public void setPersonaTipopersoNombre(String personaTipopersoNombre) {
        this.personaTipopersoNombre = personaTipopersoNombre;
    }

    public Date getPersonaFechexped() {
        return personaFechexped;
    }

    public void setPersonaFechexped(Date personaFechexped) {
        this.personaFechexped = personaFechexped;
    }

    public String getPersonaMigrado() {
        return personaMigrado;
    }

    public void setPersonaMigrado(String personaMigrado) {
        this.personaMigrado = personaMigrado;
    }

    public Date getPersonaFecmigra() {
        return personaFecmigra;
    }

    public void setPersonaFecmigra(Date personaFecmigra) {
        this.personaFecmigra = personaFecmigra;
    }

    public Date getPersonaFechactua() {
        return personaFechactua;
    }

    public void setPersonaFechactua(Date personaFechactua) {
        this.personaFechactua = personaFechactua;
    }

    public String getPersonaIpinscrip() {
        return personaIpinscrip;
    }

    public void setPersonaIpinscrip(String personaIpinscrip) {
        this.personaIpinscrip = personaIpinscrip;
    }

    public Long getPersonaAutotransIdauttra() {
        return personaAutotransIdauttra;
    }

    public void setPersonaAutotransIdauttra(Long personaAutotransIdauttra) {
        this.personaAutotransIdauttra = personaAutotransIdauttra;
    }

    public String getPersonaUsuaregis() {
        return personaUsuaregis;
    }

    public void setPersonaUsuaregis(String personaUsuaregis) {
        this.personaUsuaregis = personaUsuaregis;
    }
}
