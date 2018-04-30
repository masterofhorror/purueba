/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "reprocesarRna", entities = {
        @EntityResult(entityClass = ReprocesarRna.class, fields = {
            @FieldResult(name = "codCarga", column = "CODCARGA"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "estado", column = "ESTADO"),
            @FieldResult(name = "fechaProceso", column = "FECHAPROCESO"),
            @FieldResult(name = "migrado", column = "MIGRADO")
        })
    })
})
public class ReprocesarRna implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long codCarga;
    @Id
    private String placa;
    private String estado;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    private String migrado;

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getMigrado() {
        return migrado;
    }

    public void setMigrado(String migrado) {
        this.migrado = migrado;
    }
    
    
    
}
