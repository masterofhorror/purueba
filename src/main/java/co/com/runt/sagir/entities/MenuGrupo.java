/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hmoreno
 */
@Entity
@Cacheable(false)
@Table(name = "PA_MENU_GRUPO")
@NamedQueries({
    @NamedQuery(name = "MenuGrupo.findActivos", query = "SELECT m FROM MenuGrupo m WHERE m.menuGrupoEstado = 'ACTIVO' ORDER BY m.menuGrupoOrden ASC")})
public class MenuGrupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MENU_GRUPO_ID")
    private Integer menuGrupoId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MENU_GRUPO_NOMBRE")
    private String menuGrupoNombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MENU_GRUPO_ESTADO")
    private String menuGrupoEstado;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MENU_GRUPO_ORDEN")
    private Integer menuGrupoOrden;

    @OneToMany(cascade = {}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "PA_MENU_GRUPO_FUNCIONALIDAD",
            joinColumns = @JoinColumn(name = "MENU_GRUPO_ID"),
            inverseJoinColumns = @JoinColumn(name = "TIPOFUNCIONALIDAD_ID"))
    private List<TipoFuncionalidad> funcionalidades;

    public Integer getMenuGrupoId() {
        return menuGrupoId;
    }

    public void setMenuGrupoId(Integer menuGrupoId) {
        this.menuGrupoId = menuGrupoId;
    }

    public String getMenuGrupoNombre() {
        return menuGrupoNombre;
    }

    public void setMenuGrupoNombre(String menuGrupoNombre) {
        this.menuGrupoNombre = menuGrupoNombre;
    }

    public String getMenuGrupoEstado() {
        return menuGrupoEstado;
    }

    public void setMenuGrupoEstado(String menuGrupoEstado) {
        this.menuGrupoEstado = menuGrupoEstado;
    }

    public Integer getMenuGrupoOrden() {
        return menuGrupoOrden;
    }

    public void setMenuGrupoOrden(Integer menuGrupoOrden) {
        this.menuGrupoOrden = menuGrupoOrden;
    }

    public List<TipoFuncionalidad> getFuncionalidades() {
        return funcionalidades;
    }

    public void setFuncionalidades(List<TipoFuncionalidad> funcionalidades) {
        this.funcionalidades = funcionalidades;
    }

}
