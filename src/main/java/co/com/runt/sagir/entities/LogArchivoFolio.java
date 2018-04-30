/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dsalamanca
 */

@Entity
@Table(name = "LOG_ARCHIVO_FOLIO", schema = "MIGRACIONQX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogArchivoFolio.findAll", query = "SELECT l FROM LogArchivoFolio l"),
    @NamedQuery(name = "LogArchivoFolio.findBynomArcOriginal", query = "SELECT l FROM LogArchivoFolio l WHERE l.nomArcOriginal = :nomArcOriginal"),
    @NamedQuery(name = "LogArchivoFolio.findByidFolio", query = "SELECT l FROM LogArchivoFolio l WHERE l.idFolio = :idFolio"),
    @NamedQuery(name = "LogArchivoFolio.findByidFolioYnomArcOriginal", query = "SELECT l FROM LogArchivoFolio l WHERE l.idFolio = :idFolio AND l.nomArcOriginal = :nomArcOriginal")
})
public class LogArchivoFolio implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "NOM_ARC_ORIGINAL")
    private String nomArcOriginal;
    @Column(name = "ID_FOLIO")
    private String idFolio;
    @Column(name = "COD_ESTADO") 
    private Integer codEstado;

    public LogArchivoFolio() {
        //Constructor vacio
    }
    
     public LogArchivoFolio(String nomArcOriginal, String idFolio, Integer codEstado) {
        this.nomArcOriginal = nomArcOriginal;
        this.idFolio = idFolio;
        this.codEstado = codEstado;
    }

    public String getNomArcOriginal() {
        return nomArcOriginal;
    }

    public void setNomArcOriginal(String nomArcOriginal) {
        this.nomArcOriginal = nomArcOriginal;
    }

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }
    
    
    
    
    
}
