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
    @SqlResultSetMapping(name = "consultaRNASagir", entities = {
        @EntityResult(entityClass = ConsultaRNA.class, fields = {
            @FieldResult(name = "idOt", column = "IDOT"),
            @FieldResult(name = "nombreOt", column = "NOMBREOT"),
            @FieldResult(name = "idVehiculo", column = "IDVEHICULO"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "estadoVehic", column = "ESTADOVEHIC"),
            @FieldResult(name = "clase", column = "CLASE"),
            @FieldResult(name = "servicio", column = "SERVICIO"),
            @FieldResult(name = "fechaMatricula", column = "FECHAMATRICULA"),
            @FieldResult(name = "migrado", column = "MIGRADO"),
            @FieldResult(name = "fecMigra", column = "FECMIGRA"),
            @FieldResult(name = "modelo", column = "MODELO"),
            @FieldResult(name = "marca", column = "MARCA"),
            @FieldResult(name = "carroceria", column = "CARROCERIA"),
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "carga", column = "CARGA"),
            @FieldResult(name = "peso", column = "PESO"),
            @FieldResult(name = "nroChasis", column = "NROCHASIS"),
            @FieldResult(name = "nroMotor", column = "NROMOTOR"),
            @FieldResult(name = "nroSerie", column = "NROSERIE"),
            @FieldResult(name = "modaServi", column = "MODASERVI"),
            @FieldResult(name = "combustible", column = "COMBUSTIBLE")
        })
    })
})
public class ConsultaRNA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String idVehiculo;
    private String idOt;
    private String nombreOt;
    private String placa;
    private String estadoVehic;
    private String clase;
    private String servicio;
    private String fechaMatricula;
    private String migrado;
    private String fecMigra;
    private String modelo;
    private String marca;
    private String carroceria;
    private String linea;
    private String carga;
    private String peso;
    private String nroChasis;
    private String nroMotor;
    private String nroSerie;
    private String modaServi;
    private String combustible;

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getIdOt() {
        return idOt;
    }

    public void setIdOt(String idOt) {
        this.idOt = idOt;
    }

    public String getNombreOt() {
        return nombreOt;
    }

    public void setNombreOt(String nombreOt) {
        this.nombreOt = nombreOt;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstadoVehic() {
        return estadoVehic;
    }

    public void setEstadoVehic(String estadoVehic) {
        this.estadoVehic = estadoVehic;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(String fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getMigrado() {
        return migrado;
    }

    public void setMigrado(String migrado) {
        this.migrado = migrado;
    }

    public String getFecMigra() {
        return fecMigra;
    }

    public void setFecMigra(String fecMigra) {
        this.fecMigra = fecMigra;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getNroChasis() {
        return nroChasis;
    }

    public void setNroChasis(String nroChasis) {
        this.nroChasis = nroChasis;
    }

    public String getNroMotor() {
        return nroMotor;
    }

    public void setNroMotor(String nroMotor) {
        this.nroMotor = nroMotor;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getModaServi() {
        return modaServi;
    }

    public void setModaServi(String modaServi) {
        this.modaServi = modaServi;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    
}
