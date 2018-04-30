/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dsalamanca
 */
@Entity
@Cacheable
@Table(name = "GE_TRAMITE", schema = "RUNTPROD")
@NamedQueries({
    @NamedQuery(name = "Tramites.findByAll", query = "SELECT t FROM Tramites t"),
    @NamedQuery(name = "Tramites.findByPlaca", query = "SELECT t FROM Tramites t WHERE t.solicitud.solicitudPlaca = :placa AND t.tramiteEstado IN ('APROBADO', 'AUTORIZADO') AND t.tramiteTipoTrami.codTipoTrami IN (8, 18, 21, 22, 42, 43, 105, 156, 157, 158, 170, 171, 172, 173, 174, 175, 195, 236)"),
    @NamedQuery(name = "Tramites.findCountByPlaca", query = "SELECT COUNT(t) FROM Tramites t WHERE t.solicitud.solicitudPlaca = :placa AND t.tramiteEstado IN ('APROBADO', 'AUTORIZADO') AND t.tramiteTipoTrami.codTipoTrami IN (8, 18, 21, 22, 42, 43, 105, 156, 157, 158, 170, 171, 172, 173, 174, 175, 195, 236)"),
    @NamedQuery(name = "Tramites.findByConsulta", query = "SELECT t FROM Tramites t WHERE t.solicitud.solicitudPlaca = :placa AND t.tramiteEstado IN ('APROBADO', 'AUTORIZADO')"),
    @NamedQuery(name = "Tramites.findByCountPlaca", query = "SELECT COUNT(t) FROM Tramites t WHERE t.solicitud.solicitudPlaca = :placa AND t.tramiteEstado IN ('APROBADO', 'AUTORIZADO')")
})
public class Tramites implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "TRAMITE_IDTRAMITE")
    private Long tramiteIdTramite;
    
    @JoinColumn(name = "TRAMITE_SOLICITUD_IDENSOLIC")
    @ManyToOne(optional = false)
    private Solicitud solicitud;
    
    @JoinColumn(name = "TRAMITE_TIPOTRAMI_CODTRAMIT")
    @ManyToOne(optional = false)
    private TipoTramite tramiteTipoTrami;
    
    @Column(name = "TRAMITE_ORDETRAMI")
    private Long tramiteOrdenTrami;
    @Column(name = "TRAMITE_ESTATRAMI_NOMBRE")
    private String tramiteEstado;

    public Tramites() {
        //Default constructor
    }

    public Tramites(Long tramiteIdTramite, Solicitud solicitud, TipoTramite tramiteTipoTrami, Long tramiteOrdenTrami, String tramiteEstado) {
        this.tramiteIdTramite = tramiteIdTramite;
        this.solicitud = solicitud;
        this.tramiteTipoTrami = tramiteTipoTrami;
        this.tramiteOrdenTrami = tramiteOrdenTrami;
        this.tramiteEstado = tramiteEstado;
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

    public TipoTramite getTramiteTipoTrami() {
        return tramiteTipoTrami;
    }

    public void setTramiteTipoTrami(TipoTramite tramiteTipoTrami) {
        this.tramiteTipoTrami = tramiteTipoTrami;
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
