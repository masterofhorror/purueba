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
public class ProgramarCarguePK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_PROCESO")
    private Long idProceso;

    @Column(name = "ID_ORDEN")
    private Long idOrden;

    public Long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

}
