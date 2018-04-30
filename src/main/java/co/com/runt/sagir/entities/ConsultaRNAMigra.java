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
 * @author Dsalamanca
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaRNAMigracion", entities = {
        @EntityResult(entityClass = ConsultaRNAMigra.class, fields = {
            @FieldResult(name = "idSecretari", column = "IDSECRETARI"),
            @FieldResult(name = "descriSecre", column = "DESCRISECRE"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "estadoVehic", column = "ESTADOVEHIC"),
            @FieldResult(name = "estadoCargue", column = "ESTADOCARGUE"),
            @FieldResult(name = "codCarga", column = "CODCARGA"),
            @FieldResult(name = "migrado", column = "MIGRADO"),
            @FieldResult(name = "radicado", column = "RADICADO"),
            @FieldResult(name = "fechCarga", column = "FECHCARGA"),
            @FieldResult(name = "folio", column = "FOLIO"),
            @FieldResult(name = "boletin", column = "BOLETIN"),
            @FieldResult(name = "marca", column = "MARCA"),
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "idClase", column = "IDCLASE"),
            @FieldResult(name = "clase", column = "CLASE"),
            @FieldResult(name = "idCarroceri", column = "IDCARROCERI"),
            @FieldResult(name = "carroceria", column = "CARROCERIA"),
            @FieldResult(name = "idModalidad", column = "IDMODALIDAD"),
            @FieldResult(name = "modalidad", column = "MODALIDAD"),
            @FieldResult(name = "cilindraje", column = "CILINDRAJE"),
            @FieldResult(name = "modelo", column = "MODELO"),
            @FieldResult(name = "motor", column = "MOTOR"),
            @FieldResult(name = "serie", column = "SERIE"),
            @FieldResult(name = "chasis", column = "CHASIS"),
            @FieldResult(name = "toneladas", column = "TONELADAS"),
            @FieldResult(name = "pasajeros", column = "PASAJEROS")})})})
public class ConsultaRNAMigra implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String idSecretari;
    private String descriSecre;
    private String placa;
    private String estadoVehic;
    private String estadoCargue;
    @Id
    private String codCarga;
    private String migrado;
    private String radicado;
    private String fechCarga;
    private String folio;
    private String boletin;
    private String marca;
    private String linea;
    private String idClase;
    private String clase;
    private String idCarroceri;
    private String carroceria;
    private String idModalidad;
    private String modalidad;
    private String cilindraje;
    private String modelo;
    private String motor;
    private String serie;
    private String chasis;
    private String toneladas;
    private String pasajeros;

    public String getIdSecretari() {
        return idSecretari;
    }

    public void setIdSecretari(String idSecretari) {
        this.idSecretari = idSecretari;
    }

    public String getDescriSecre() {
        return descriSecre;
    }

    public void setDescriSecre(String descriSecre) {
        this.descriSecre = descriSecre;
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

    public String getEstadoCargue() {
        return estadoCargue;
    }

    public void setEstadoCargue(String estadoCargue) {
        this.estadoCargue = estadoCargue;
    }

    public String getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(String codCarga) {
        this.codCarga = codCarga;
    }

    public String getMigrado() {
        return migrado;
    }

    public void setMigrado(String migrado) {
        this.migrado = migrado;
    }

    public String getRadicado() {
        return radicado;
    }

    public void setRadicado(String radicado) {
        this.radicado = radicado;
    }

    public String getFechCarga() {
        return fechCarga;
    }

    public void setFechCarga(String fechCarga) {
        this.fechCarga = fechCarga;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getBoletin() {
        return boletin;
    }

    public void setBoletin(String boletin) {
        this.boletin = boletin;
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

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getIdCarroceri() {
        return idCarroceri;
    }

    public void setIdCarroceri(String idCarroceri) {
        this.idCarroceri = idCarroceri;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }

    public String getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(String idModalidad) {
        this.idModalidad = idModalidad;
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
    
    
}
