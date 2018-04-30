/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@Cacheable
@Table(name = "RA_REGNACCAR", schema = "MIGRACIONQX")
@NamedQueries({
    @NamedQuery(name = "Regnaccar.findByCountPlaca", query = "SELECT COUNT(r) FROM Regnaccar r WHERE r.placa = :placa")
})
public class Regnaccar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "REGNACCAR_IDENTIFIC")
    private Integer identific;
    @Column(name = "REGNACCAR_PLACA")
    private String placa;
    @Column(name = "REGNACCAR_CLASVEHIC")
    private String clasVehic;
    @Column(name = "REGNACCAR_MODELO")
    private Integer modelo;
    @Column(name = "REGNACCAR_MARCA")
    private String marca;
    @Column(name = "REGNACCAR_LINEA")
    private String linea;
    @Column(name = "REGNACCAR_TARJETA")
    private String tarjeta;
    @Column(name = "REGNACCAR_FECHEXPED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExped;
    @Column(name = "REGNACCAR_NUMEJES")
    private Integer numEjes;
    @Column(name = "REGNACCAR_CARROCERI")
    private String carroceri;
    @Column(name = "REGNACCAR_LLANTAS")
    private Integer llantas;
    @Column(name = "REGNACCAR_ANCHO")
    private Integer ancho;
    @Column(name = "REGNACCAR_ALTO")
    private Integer alto;
    @Column(name = "REGNACCAR_LARGO")
    private Integer largo;
    @Column(name = "REGNACCAR_COLADIANT")
    private String colAdiant;
    @Column(name = "REGNACCAR_COLADPOST")
    private String colAdPost;
    @Column(name = "REGNACCAR_PBV")
    private Integer pbv;
    @Column(name = "REGNACCAR_TARA")
    private String tara;
    @Column(name = "REGNACCAR_DISTAEJES")
    private Integer disEjes;
    @Column(name = "REGNACCAR_IDCLASE")
    private Integer idClase;

    public Integer getIdentific() {
        return identific;
    }

    public void setIdentific(Integer identific) {
        this.identific = identific;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getClasVehic() {
        return clasVehic;
    }

    public void setClasVehic(String clasVehic) {
        this.clasVehic = clasVehic;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
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

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Date getFechaExped() {
        return fechaExped;
    }

    public void setFechaExped(Date fechaExped) {
        this.fechaExped = fechaExped;
    }

    public Integer getNumEjes() {
        return numEjes;
    }

    public void setNumEjes(Integer numEjes) {
        this.numEjes = numEjes;
    }

    public String getCarroceri() {
        return carroceri;
    }

    public void setCarroceri(String carroceri) {
        this.carroceri = carroceri;
    }

    public Integer getLlantas() {
        return llantas;
    }

    public void setLlantas(Integer llantas) {
        this.llantas = llantas;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getAlto() {
        return alto;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    public Integer getLargo() {
        return largo;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
    }

    public String getColAdiant() {
        return colAdiant;
    }

    public void setColAdiant(String colAdiant) {
        this.colAdiant = colAdiant;
    }

    public String getColAdPost() {
        return colAdPost;
    }

    public void setColAdPost(String colAdPost) {
        this.colAdPost = colAdPost;
    }

    public Integer getPbv() {
        return pbv;
    }

    public void setPbv(Integer pbv) {
        this.pbv = pbv;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public Integer getDisEjes() {
        return disEjes;
    }

    public void setDisEjes(Integer disEjes) {
        this.disEjes = disEjes;
    }

    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }
    
    
}
