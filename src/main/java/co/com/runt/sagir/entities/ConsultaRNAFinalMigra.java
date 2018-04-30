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
    @SqlResultSetMapping(name = "consultaRNAFinalMigra", entities = {
        @EntityResult (entityClass = ConsultaRNAFinalMigra.class, fields = {
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "idOt", column = "IDOT"),
            @FieldResult(name = "nombreOt", column = "NOMBREOT"),
            @FieldResult(name = "idVehiculo", column = "IDVEHICULO"),            
            @FieldResult(name = "migrado", column = "MIGRADO"),
            @FieldResult(name = "fecMigra", column = "FECMIGRA"),
            @FieldResult(name = "clase", column = "CLASE"),
            @FieldResult(name = "color", column = "COLOR"),
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "marca", column = "MARCA"),
            @FieldResult(name = "modaservi", column = "MODASERVI"),
            @FieldResult(name = "carroceria", column = "CARROCERIA"),
            @FieldResult(name = "modelo", column = "MODELO"),
            @FieldResult(name = "nroChasis", column = "NROCHASIS"),
            @FieldResult(name = "nroMotor", column = "NROMOTOR"),
            @FieldResult(name = "nroSerie", column = "NROSERIE"),
            @FieldResult(name = "cilindraje", column = "CILINDRAJE"),
            @FieldResult(name = "estadoVehic", column = "ESTADOVEHIC")
        })
    })
})
public class ConsultaRNAFinalMigra implements Serializable{
    
     private static final long serialVersionUID = 1L;

    @Id
    private String idOt;
    private String nombreOt;
    private String idVehiculo;
    private String placa;
    private String migrado;
    private String fecMigra;
    private String clase;
    private String color;
    private String linea;
    private String marca;
    private String modaservi;
    private String carroceria;
    private String modelo;
    private String nroChasis;
    private String nroMotor;
    private String nroSerie;
    private String cilindraje;
    private String estadoVehic;

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

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModaservi() {
        return modaservi;
    }

    public void setModaservi(String modaservi) {
        this.modaservi = modaservi;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    public String getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getEstadoVehic() {
        return estadoVehic;
    }

    public void setEstadoVehic(String estadoVehic) {
        this.estadoVehic = estadoVehic;
    }
    
    
}
