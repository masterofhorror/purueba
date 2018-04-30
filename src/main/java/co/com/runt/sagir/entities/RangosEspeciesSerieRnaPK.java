/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Hmoreno
 */
@Embeddable
public class RangosEspeciesSerieRnaPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PLACA")
    private String placa;

    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

}
