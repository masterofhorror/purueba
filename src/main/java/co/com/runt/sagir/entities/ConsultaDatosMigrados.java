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
 * @author APENA
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaDatosMigrados", entities = {
        @EntityResult(entityClass = ConsultaDatosMigrados.class, fields = {
            @FieldResult(name = "idCarga", column = "ID_CARGA"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "secretaria", column = "SECRETARIA"),
            @FieldResult(name = "marca", column = "MARCA"),
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "clase", column = "CLASE"),
            @FieldResult(name = "color", column = "COLOR"),
            @FieldResult(name = "servicio", column = "SERVICIO"),
            @FieldResult(name = "carroceria", column = "CARROCERIA"),
            @FieldResult(name = "cilindraje", column = "CILINDRAJE"),
            @FieldResult(name = "modelo", column = "MODELO"),
            @FieldResult(name = "numMotor", column = "NUM_MOTOR"),
            @FieldResult(name = "numSerie", column = "NUM_SERIE"),
            @FieldResult(name = "numChasis", column = "NUM_CHASIS"),
            @FieldResult(name = "toneladas", column = "TONELADAS"),
            @FieldResult(name = "pasajeros", column = "PASAJEROS"),
            @FieldResult(name = "estado", column = "ESTADO"),
            @FieldResult(name = "matriculaInicial", column = "MATRICULA_INICIAL"),
            @FieldResult(name = "fechaCarga", column = "FECHA_CARGA"),
            @FieldResult(name = "combustible", column = "COMBUSTIBLE"),
            @FieldResult(name = "estadoCargue", column = "ESTADO_CARGUE")
        })
    })
})
public class ConsultaDatosMigrados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String idCarga;
    private String placa;
    private String secretaria;
    private String marca;
    private String linea;
    private String clase;
    private String color;
    private String servicio;
    private String carroceria;
    private String cilindraje;
    private String modelo;
    private String numMotor;
    private String numSerie;
    private String numChasis;
    private String toneladas;
    private String pasajeros;
    private String estado;
    private String matriculaInicial;
    private String fechaCarga;
    private String combustible;
    private String estadoCargue;

    public String getIdCarga() {
        return idCarga;
    }

    public void setIdCarga(String idCarga) {
        this.idCarga = idCarga;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }

    public String getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumMotor() {
        return numMotor;
    }

    public void setNumMotor(String numMotor) {
        this.numMotor = numMotor;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getNumChasis() {
        return numChasis;
    }

    public void setNumChasis(String numChasis) {
        this.numChasis = numChasis;
    }

    public String getToneladas() {
        return toneladas;
    }

    public void setToneladas(String toneladas) {
        this.toneladas = toneladas;
    }

    public String getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(String pasajeros) {
        this.pasajeros = pasajeros;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMatriculaInicial() {
        return matriculaInicial;
    }

    public void setMatriculaInicial(String matriculaInicial) {
        this.matriculaInicial = matriculaInicial;
    }

    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public String getEstadoCargue() {
        return estadoCargue;
    }

    public void setEstadoCargue(String estadoCargue) {
        this.estadoCargue = estadoCargue;
    }

    
}
