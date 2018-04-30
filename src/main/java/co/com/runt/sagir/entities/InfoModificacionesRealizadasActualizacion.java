/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

/**
 *
 * @author Hmoreno
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaModificacionesRealizadasActualizacion",
            entities = {
                @EntityResult(entityClass = InfoModificacionesRealizadasActualizacion.class,
                        fields = {
                            @FieldResult(name = "id", column = "id")
                            ,
                            @FieldResult(name = "placa", column = "numero_placa")
                            ,
                            @FieldResult(name = "otCargado", column = "ot_cargado")
                            ,
                            @FieldResult(name = "otSolicita", column = "ot_solicita")
                            ,
                            @FieldResult(name = "estadoVehiculoCargado", column = "estado_vehiculo_cargado")
                            ,
                            @FieldResult(name = "estadoVehiculoSolicita", column = "estado_vehiculo_solicita")
                            ,
                            @FieldResult(name = "fechaProceso", column = "fecha_proceso")
                            ,
                            @FieldResult(name = "estado", column = "descripcion_estado")
                            ,
                            @FieldResult(name = "funcionario", column = "id_funcionario")
                            ,
                            @FieldResult(name = "nombre", column = "umi_nombres")
                            ,
                            @FieldResult(name = "primerApellido", column = "umi_apellido1")
                            ,
                            @FieldResult(name = "segundoApellido", column = "umi_apellido2")
                        })
            })
})
public class InfoModificacionesRealizadasActualizacion implements Serializable {

    @Id
    private Long id;

    private String placa;
    private Long otCargado;
    private Long otSolicita;
    private String estadoVehiculoCargado;
    private String estadoVehiculoSolicita;
    private String fechaProceso;
    private String estado;
    private Long funcionario;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getOtCargado() {
        return otCargado;
    }

    public void setOtCargado(Long otCargado) {
        this.otCargado = otCargado;
    }

    public Long getOtSolicita() {
        return otSolicita;
    }

    public void setOtSolicita(Long otSolicita) {
        this.otSolicita = otSolicita;
    }

    public String getEstadoVehiculoCargado() {
        return estadoVehiculoCargado;
    }

    public void setEstadoVehiculoCargado(String estadoVehiculoCargado) {
        this.estadoVehiculoCargado = estadoVehiculoCargado;
    }

    public String getEstadoVehiculoSolicita() {
        return estadoVehiculoSolicita;
    }

    public void setEstadoVehiculoSolicita(String estadoVehiculoSolicita) {
        this.estadoVehiculoSolicita = estadoVehiculoSolicita;
    }

    public String getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(String fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Long funcionario) {
        this.funcionario = funcionario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

}
