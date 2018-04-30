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

/**
 *
 * @author Hmoreno
 */
@Entity
@Table(name = "PA_TIPOFUNCIONALIDAD", schema = "CSWRUNT")
@NamedQueries({
    @NamedQuery(name = "TipoFuncionalidad.findAll", query = "SELECT t FROM TipoFuncionalidad t")})
public class TipoFuncionalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TIPOFUNC_CODIGO")
    private String tipofuncCodigo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TIPOFUNC_NOMBRE")
    private String tipofuncNombre;

    @Size(max = 300)
    @Column(name = "TIPOFUNC_DESCRIPCION")
    private String tipofuncDescripcion;

    public String getTipofuncCodigo() {
        return tipofuncCodigo;
    }

    public void setTipofuncCodigo(String tipofuncCodigo) {
        this.tipofuncCodigo = tipofuncCodigo;
    }

    public String getTipofuncNombre() {
        return tipofuncNombre;
    }

    public void setTipofuncNombre(String tipofuncNombre) {
        this.tipofuncNombre = tipofuncNombre;
    }

    public String getTipofuncDescripcion() {
        return tipofuncDescripcion;
    }

    public void setTipofuncDescripcion(String tipofuncDescripcion) {
        this.tipofuncDescripcion = tipofuncDescripcion;
    }

}
