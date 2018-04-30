/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author APENA
 */
@Entity
@Table(name = "RANGOS_PLACAS_CONSULTA", schema = "MIGRACIONQX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RangosPlacasConsulta.findAll", query = "SELECT r FROM RangosPlacasConsulta r"),
    @NamedQuery(name = "RangosPlacasConsulta.findByPlaca", query = "SELECT r FROM RangosPlacasConsulta r WHERE r.placa = :placa"),
    @NamedQuery(name = "RangosPlacasConsulta.findByPlacaMAX", query = "SELECT MAX(r.placa) FROM RangosPlacasConsulta r"),
    @NamedQuery(name = "RangosPlacasConsulta.findByPlacaMIN", query = "SELECT MIN(r.placa) FROM RangosPlacasConsulta r")})
public class RangosPlacasConsulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "PLACA")
    private String placa;

    public RangosPlacasConsulta() {
        //Default constructor
    }

    public RangosPlacasConsulta(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (placa != null ? placa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RangosPlacasConsulta)) {
            return false;
        }
        RangosPlacasConsulta other = (RangosPlacasConsulta) object;
        if ((this.placa == null && other.placa != null) || (this.placa != null && !this.placa.equals(other.placa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.RangosPlacasConsulta[ placa=" + placa + " ]";
    }
    
}
