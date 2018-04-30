/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dsalamanca
 */
public class PersonaHQDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private Long personaIdpersona;
    private String personaNrodocume;
    private String personaTipoidentIdtipdoc;
    private Date personaFecharegi;
    private Date personaFechanove;
    private String personaEstapersoNombre;
    private String personaTipopersoNombre;
    private Date personaFechexped;
    private String personaMigrado;
    private Date personaFecmigra;
    private Date personaFechactua;
    private String personaIpinscrip;
    private Long personaAutotransIdauttra;
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
