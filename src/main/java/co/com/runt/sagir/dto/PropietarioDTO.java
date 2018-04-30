/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.PersonaHQ;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author dsalamanca
 */
public class PropietarioDTO extends Automotor{
    
    private long propietarIdpropiet;
    private String propietarEstadoNombre;
    private Date propietarFecfinpro;
    private Date propietarFechactua;
    private Date propietarFecinipro;
    private Date propietarFecmigra;
    private String propietarMigrado;
    private BigDecimal propietarPorcpropi;
    private String propietarProindivi;
    private String propietarProsegest;
    private int propietarTipopropiCodtippro;
    private Automotor raAutomotor;
    private PersonaHQ trPersona;

    public long getPropietarIdpropiet() {
        return propietarIdpropiet;
    }

    public void setPropietarIdpropiet(long propietarIdpropiet) {
        this.propietarIdpropiet = propietarIdpropiet;
    }

    public String getPropietarEstadoNombre() {
        return propietarEstadoNombre;
    }

    public void setPropietarEstadoNombre(String propietarEstadoNombre) {
        this.propietarEstadoNombre = propietarEstadoNombre;
    }

    public Date getPropietarFecfinpro() {
        return propietarFecfinpro;
    }

    public void setPropietarFecfinpro(Date propietarFecfinpro) {
        this.propietarFecfinpro = propietarFecfinpro;
    }

    public Date getPropietarFechactua() {
        return propietarFechactua;
    }

    public void setPropietarFechactua(Date propietarFechactua) {
        this.propietarFechactua = propietarFechactua;
    }

    public Date getPropietarFecinipro() {
        return propietarFecinipro;
    }

    public void setPropietarFecinipro(Date propietarFecinipro) {
        this.propietarFecinipro = propietarFecinipro;
    }

    public Date getPropietarFecmigra() {
        return propietarFecmigra;
    }

    public void setPropietarFecmigra(Date propietarFecmigra) {
        this.propietarFecmigra = propietarFecmigra;
    }

    public String getPropietarMigrado() {
        return propietarMigrado;
    }

    public void setPropietarMigrado(String propietarMigrado) {
        this.propietarMigrado = propietarMigrado;
    }

    public BigDecimal getPropietarPorcpropi() {
        return propietarPorcpropi;
    }

    public void setPropietarPorcpropi(BigDecimal propietarPorcpropi) {
        this.propietarPorcpropi = propietarPorcpropi;
    }

    public String getPropietarProindivi() {
        return propietarProindivi;
    }

    public void setPropietarProindivi(String propietarProindivi) {
        this.propietarProindivi = propietarProindivi;
    }

    public String getPropietarProsegest() {
        return propietarProsegest;
    }

    public void setPropietarProsegest(String propietarProsegest) {
        this.propietarProsegest = propietarProsegest;
    }

    public int getPropietarTipopropiCodtippro() {
        return propietarTipopropiCodtippro;
    }

    public void setPropietarTipopropiCodtippro(int propietarTipopropiCodtippro) {
        this.propietarTipopropiCodtippro = propietarTipopropiCodtippro;
    }

    public Automotor getRaAutomotor() {
        return raAutomotor;
    }

    public void setRaAutomotor(Automotor raAutomotor) {
        this.raAutomotor = raAutomotor;
    }

    public PersonaHQ getTrPersona() {
        return trPersona;
    }

    public void setTrPersona(PersonaHQ trPersona) {
        this.trPersona = trPersona;
    }
    
    
}
