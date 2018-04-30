/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "TEMPORARY_BOLETIN_RNA_CONSOLID", schema = "CSWSAGIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BoletinRNA.findAll", query = "SELECT b FROM BoletinRNA b"),
    @NamedQuery(name = "BoletinRNA.findByRegCodCarga", query = "SELECT b FROM BoletinRNA b WHERE b.regCodCarga = :regCodCarga"),
    @NamedQuery(name = "BoletinRNA.findByCountCodCarga", query = "SELECT COUNT(b) FROM BoletinRNA b WHERE b.regCodCarga = :regCodCarga"),
    @NamedQuery(name = "BoletinRNA.findByRegVehiculosReportados", query = "SELECT b FROM BoletinRNA b WHERE b.regVehiculosReportados = :regVehiculosReportados"),
    @NamedQuery(name = "BoletinRNA.findByRegPropietarReportados", query = "SELECT b FROM BoletinRNA b WHERE b.regPropietarReportados = :regPropietarReportados"),
    @NamedQuery(name = "BoletinRNA.findByRegVehiculosAprobados", query = "SELECT b FROM BoletinRNA b WHERE b.regVehiculosAprobados = :regVehiculosAprobados"),
    @NamedQuery(name = "BoletinRNA.findByRegPropietarAprobados", query = "SELECT b FROM BoletinRNA b WHERE b.regPropietarAprobados = :regPropietarAprobados"),
    @NamedQuery(name = "BoletinRNA.findByRegVehiculosRechazados", query = "SELECT b FROM BoletinRNA b WHERE b.regVehiculosRechazados = :regVehiculosRechazados"),
    @NamedQuery(name = "BoletinRNA.findByRegPropietarRechazados", query = "SELECT b FROM BoletinRNA b WHERE b.regPropietarRechazados = :regPropietarRechazados"),
    @NamedQuery(name = "BoletinRNA.findByNovedadesVehiculos", query = "SELECT b FROM BoletinRNA b WHERE b.novedadesVehiculos = :novedadesVehiculos"),
    @NamedQuery(name = "BoletinRNA.findByNovedadesPropietarios", query = "SELECT b FROM BoletinRNA b WHERE b.novedadesPropietarios = :novedadesPropietarios"),
    @NamedQuery(name = "BoletinRNA.findByConsolVehiculosReportados", query = "SELECT b FROM BoletinRNA b WHERE b.consolVehiculosReportados = :consolVehiculosReportados"),
    @NamedQuery(name = "BoletinRNA.findByConsolPropietarReportados", query = "SELECT b FROM BoletinRNA b WHERE b.consolPropietarReportados = :consolPropietarReportados"),
    @NamedQuery(name = "BoletinRNA.findByConsolVehiculosAprobados", query = "SELECT b FROM BoletinRNA b WHERE b.consolVehiculosAprobados = :consolVehiculosAprobados"),
    @NamedQuery(name = "BoletinRNA.findByConsolPropietarAprobados", query = "SELECT b FROM BoletinRNA b WHERE b.consolPropietarAprobados = :consolPropietarAprobados"),
    @NamedQuery(name = "BoletinRNA.findByConsolVehiculosRechazados", query = "SELECT b FROM BoletinRNA b WHERE b.consolVehiculosRechazados = :consolVehiculosRechazados"),
    @NamedQuery(name = "BoletinRNA.findByConsolPropietarRechazados", query = "SELECT b FROM BoletinRNA b WHERE b.consolPropietarRechazados = :consolPropietarRechazados")})
public class BoletinRNA implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REG_COD_CARGA")
    private BigDecimal regCodCarga;
    @Column(name = "REG_VEHICULOS_REPORTADOS")
    private BigInteger regVehiculosReportados;
    @Column(name = "REG_PROPIETAR_REPORTADOS")
    private BigInteger regPropietarReportados;
    @Column(name = "REG_VEHICULOS_APROBADOS")
    private BigInteger regVehiculosAprobados;
    @Column(name = "REG_PROPIETAR_APROBADOS")
    private BigInteger regPropietarAprobados;
    @Column(name = "REG_VEHICULOS_RECHAZADOS")
    private BigInteger regVehiculosRechazados;
    @Column(name = "REG_PROPIETAR_RECHAZADOS")
    private BigInteger regPropietarRechazados;
    @Column(name = "NOVEDADES_VEHICULOS")
    private BigInteger novedadesVehiculos;
    @Column(name = "NOVEDADES_PROPIETARIOS")
    private BigInteger novedadesPropietarios;
    @Column(name = "CONSOL_VEHICULOS_REPORTADOS")
    private BigInteger consolVehiculosReportados;
    @Column(name = "CONSOL_PROPIETAR_REPORTADOS")
    private BigInteger consolPropietarReportados;
    @Column(name = "CONSOL_VEHICULOS_APROBADOS")
    private BigInteger consolVehiculosAprobados;
    @Column(name = "CONSOL_PROPIETAR_APROBADOS")
    private BigInteger consolPropietarAprobados;
    @Column(name = "CONSOL_VEHICULOS_RECHAZADOS")
    private BigInteger consolVehiculosRechazados;
    @Column(name = "CONSOL_PROPIETAR_RECHAZADOS")
    private BigInteger consolPropietarRechazados;

    public BoletinRNA() {
        //Default constructor
    }

    public BoletinRNA(BigDecimal regCodCarga) {
        this.regCodCarga = regCodCarga;
    }

    public BigDecimal getRegCodCarga() {
        return regCodCarga;
    }

    public void setRegCodCarga(BigDecimal regCodCarga) {
        this.regCodCarga = regCodCarga;
    }

    public BigInteger getRegVehiculosReportados() {
        return regVehiculosReportados;
    }

    public void setRegVehiculosReportados(BigInteger regVehiculosReportados) {
        this.regVehiculosReportados = regVehiculosReportados;
    }

    public BigInteger getRegPropietarReportados() {
        return regPropietarReportados;
    }

    public void setRegPropietarReportados(BigInteger regPropietarReportados) {
        this.regPropietarReportados = regPropietarReportados;
    }

    public BigInteger getRegVehiculosAprobados() {
        return regVehiculosAprobados;
    }

    public void setRegVehiculosAprobados(BigInteger regVehiculosAprobados) {
        this.regVehiculosAprobados = regVehiculosAprobados;
    }

    public BigInteger getRegPropietarAprobados() {
        return regPropietarAprobados;
    }

    public void setRegPropietarAprobados(BigInteger regPropietarAprobados) {
        this.regPropietarAprobados = regPropietarAprobados;
    }

    public BigInteger getRegVehiculosRechazados() {
        return regVehiculosRechazados;
    }

    public void setRegVehiculosRechazados(BigInteger regVehiculosRechazados) {
        this.regVehiculosRechazados = regVehiculosRechazados;
    }

    public BigInteger getRegPropietarRechazados() {
        return regPropietarRechazados;
    }

    public void setRegPropietarRechazados(BigInteger regPropietarRechazados) {
        this.regPropietarRechazados = regPropietarRechazados;
    }

    public BigInteger getNovedadesVehiculos() {
        return novedadesVehiculos;
    }

    public void setNovedadesVehiculos(BigInteger novedadesVehiculos) {
        this.novedadesVehiculos = novedadesVehiculos;
    }

    public BigInteger getNovedadesPropietarios() {
        return novedadesPropietarios;
    }

    public void setNovedadesPropietarios(BigInteger novedadesPropietarios) {
        this.novedadesPropietarios = novedadesPropietarios;
    }

    public BigInteger getConsolVehiculosReportados() {
        return consolVehiculosReportados;
    }

    public void setConsolVehiculosReportados(BigInteger consolVehiculosReportados) {
        this.consolVehiculosReportados = consolVehiculosReportados;
    }

    public BigInteger getConsolPropietarReportados() {
        return consolPropietarReportados;
    }

    public void setConsolPropietarReportados(BigInteger consolPropietarReportados) {
        this.consolPropietarReportados = consolPropietarReportados;
    }

    public BigInteger getConsolVehiculosAprobados() {
        return consolVehiculosAprobados;
    }

    public void setConsolVehiculosAprobados(BigInteger consolVehiculosAprobados) {
        this.consolVehiculosAprobados = consolVehiculosAprobados;
    }

    public BigInteger getConsolPropietarAprobados() {
        return consolPropietarAprobados;
    }

    public void setConsolPropietarAprobados(BigInteger consolPropietarAprobados) {
        this.consolPropietarAprobados = consolPropietarAprobados;
    }

    public BigInteger getConsolVehiculosRechazados() {
        return consolVehiculosRechazados;
    }

    public void setConsolVehiculosRechazados(BigInteger consolVehiculosRechazados) {
        this.consolVehiculosRechazados = consolVehiculosRechazados;
    }

    public BigInteger getConsolPropietarRechazados() {
        return consolPropietarRechazados;
    }

    public void setConsolPropietarRechazados(BigInteger consolPropietarRechazados) {
        this.consolPropietarRechazados = consolPropietarRechazados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regCodCarga != null ? regCodCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BoletinRNA)) {
            return false;
        }
        BoletinRNA other = (BoletinRNA) object;
        return !((this.regCodCarga == null && other.regCodCarga != null) || (this.regCodCarga != null && !this.regCodCarga.equals(other.regCodCarga)));
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.BoletinRNA[ regCodCarga=" + regCodCarga + " ]";
    }
    
}
