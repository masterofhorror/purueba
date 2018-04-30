/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.sagir.entities.Solicitud;
import co.com.runt.sagir.entities.TipoTramite;

/**
 *
 * @author dsalamanca
 */
public class TramitesDTO {
    
    private Long tramiteIdTramite;
    private Solicitud solicitud;
    private Long tramiteOrdenTrami;
    private String tramiteEstado;
    private TipoTramite tramiteTipoTrami;

    public TipoTramite getTramiteTipoTrami() {
        return tramiteTipoTrami;
    }

    public void setTramiteTipoTrami(TipoTramite tramiteTipoTrami) {
        this.tramiteTipoTrami = tramiteTipoTrami;
    }

    public Long getTramiteIdTramite() {
        return tramiteIdTramite;
    }

    public void setTramiteIdTramite(Long tramiteIdTramite) {
        this.tramiteIdTramite = tramiteIdTramite;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Long getTramiteOrdenTrami() {
        return tramiteOrdenTrami;
    }

    public void setTramiteOrdenTrami(Long tramiteOrdenTrami) {
        this.tramiteOrdenTrami = tramiteOrdenTrami;
    }

    public String getTramiteEstado() {
        return tramiteEstado;
    }

    public void setTramiteEstado(String tramiteEstado) {
        this.tramiteEstado = tramiteEstado;
    }
    
    
}
