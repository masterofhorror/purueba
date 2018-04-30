/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.autenticacionrunt.MenuDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hmoreno
 */
public class MenuGrupoDTO {

    private Integer id;
    private String nombre;
    private String url;
    private Integer orden;

    private List<MenuDTO> opcionesMenu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuDTO> getOpcionesMenu() {
        return opcionesMenu;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public void setOpcionesMenu(List<MenuDTO> opcionesMenu) {
        this.opcionesMenu = opcionesMenu;
    }

    public void addOpcionMenu(MenuDTO opcionMenu) {
        if (opcionesMenu == null) {
            opcionesMenu = new ArrayList<>();
        }
        opcionesMenu.add(opcionMenu);
    }

}
