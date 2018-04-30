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
 * @author dsalamanca
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaCambioFormatoAntiguoANuevo", entities = {
        @EntityResult(entityClass = LicTto.class, fields = {
            @FieldResult(name = "idSecretaria", column = "IDSECRETARIA"),
            @FieldResult(name = "secretaria", column = "SECRETARIA"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "marca", column = "MARCA"),
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "clase", column = "CLASE"),
            @FieldResult(name = "carroceria", column = "CARROCERIA"),
            @FieldResult(name = "modalidad", column = "MODALIDAD"),
            @FieldResult(name = "cilindraje", column = "CILINDRAJE"),
            @FieldResult(name = "modelo", column = "MODELO"),
            @FieldResult(name = "motor", column = "MOTOR"),
            @FieldResult(name = "serie", column = "SERIE"),
            @FieldResult(name = "chasis", column = "CHASIS"),
            @FieldResult(name = "capacidadToneladas", column = "CAPACIDADTONELADA"),
            @FieldResult(name = "capacidadPasaj", column = "CAPACIDADPASAJ"),
            @FieldResult(name = "estado", column = "ESTADO"),
            @FieldResult(name = "estadoActual", column = "ESTADOACTUAL"),
            @FieldResult(name = "migrado", column = "MIGRADO"),
            @FieldResult(name = "codCarga", column = "CODCARGA"),
            @FieldResult(name = "radicado", column = "RADICADO"),
            @FieldResult(name = "fechaCarga", column = "FECHACARGA"),
            @FieldResult(name = "idFolio", column = "IDFOLIO"),
            @FieldResult(name = "estadoCargue", column = "ESTADOCARGUE"),
            @FieldResult(name = "boletin", column = "BOLETIN"),
            @FieldResult(name = "descClase", column = "DESCCLASE"),
            @FieldResult(name = "descModalidad", column = "DESCMODALIDAD"),
            @FieldResult(name = "indValido", column = "INDVALIDO"),
            @FieldResult(name = "descEstado", column = "DESCESTADO"),
            @FieldResult(name = "tipoCarro", column = "TIPOCARRO"),
            @FieldResult(name = "actualiza", column = "ACTUALIZA"),
            @FieldResult(name = "cambioEstado", column = "CAMBIOESTADO"),
            @FieldResult(name = "cambioFormato", column = "CAMBIOFORMATO"),
            @FieldResult(name = "cambioServicio", column = "CAMBIOSERVICIO"),
            @FieldResult(name = "idServicio", column = "IDSERVICIO")
        })
    })
})
public class LicTto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String boletin;
    private String placa;
    private String idSecretaria;
    private String secretaria;
    private String marca;
    private String linea;
    private String clase;
    private String carroceria;
    private String modalidad;
    private String cilindraje;
    private String modelo;
    private String motor;
    private String serie;
    private String chasis;
    private String capacidadToneladas;
    private String capacidadPasaj;
    private String estado;
    private String estadoActual;
    private String migrado;
    @Id
    private String codCarga;
    private String radicado;
    private String fechaCarga;
    private String idFolio;
    private String estadoCargue;   
    private String descClase;
    private String descModalidad;
    private String indValido;
    private String descEstado;
    private String tipoCarro;
    private String actualiza;
    private String cambioEstado;
    private String cambioFormato;
    private String cambioServicio;
    private String idServicio;
    

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
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

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getCapacidadToneladas() {
        return capacidadToneladas;
    }

    public void setCapacidadToneladas(String capacidadToneladas) {
        this.capacidadToneladas = capacidadToneladas;
    }

    public String getCapacidadPasaj() {
        return capacidadPasaj;
    }

    public void setCapacidadPasaj(String capacidadPasaj) {
        this.capacidadPasaj = capacidadPasaj;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMigrado() {
        return migrado;
    }

    public void setMigrado(String migrado) {
        this.migrado = migrado;
    }

    public String getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(String codCarga) {
        this.codCarga = codCarga;
    }

    public String getRadicado() {
        return radicado;
    }

    public void setRadicado(String radicado) {
        this.radicado = radicado;
    }

    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public String getEstadoCargue() {
        return estadoCargue;
    }

    public void setEstadoCargue(String estadoCargue) {
        this.estadoCargue = estadoCargue;
    }

    public String getBoletin() {
        return boletin;
    }

    public void setBoletin(String boletin) {
        this.boletin = boletin;
    }

    public String getDescClase() {
        return descClase;
    }

    public void setDescClase(String descClase) {
        this.descClase = descClase;
    }

    public String getDescModalidad() {
        return descModalidad;
    }

    public void setDescModalidad(String descModalidad) {
        this.descModalidad = descModalidad;
    }

    public String getIndValido() {
        return indValido;
    }

    public void setIndValido(String indValido) {
        this.indValido = indValido;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    public String getTipoCarro() {
        return tipoCarro;
    }

    public void setTipoCarro(String tipoCarro) {
        this.tipoCarro = tipoCarro;
    }

    public String getActualiza() {
        return actualiza;
    }

    public void setActualiza(String actualiza) {
        this.actualiza = actualiza;
    }

    public String getCambioEstado() {
        return cambioEstado;
    }

    public void setCambioEstado(String cambioEstado) {
        this.cambioEstado = cambioEstado;
    }

    public String getCambioFormato() {
        return cambioFormato;
    }

    public void setCambioFormato(String cambioFormato) {
        this.cambioFormato = cambioFormato;
    }

    public String getCambioServicio() {
        return cambioServicio;
    }

    public void setCambioServicio(String cambioServicio) {
        this.cambioServicio = cambioServicio;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }
    
    

}
