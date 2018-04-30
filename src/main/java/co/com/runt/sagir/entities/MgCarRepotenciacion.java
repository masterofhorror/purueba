/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author dsalamanca
 */
@Entity
@Cacheable
@Table(name = "MG_CAR_REPOTENCIACION", schema = "MIGRACIONQX")
@NamedQueries({})
public class MgCarRepotenciacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "COD_CARGA")
    private Long codCarga;
    @Column(name = "NRO_REGISTRO")
    private Integer nroregistro;
    @Column(name = "REGISTRO")
    private String registro;
    @Column(name = "COD_ESTADO")
    private Integer codEstado;

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

    public Integer getNroregistro() {
        return nroregistro;
    }

    public void setNroregistro(Integer nroregistro) {
        this.nroregistro = nroregistro;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }
    
    

}
