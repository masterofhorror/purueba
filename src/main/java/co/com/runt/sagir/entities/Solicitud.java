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
@Table(name = "GE_SOLICITUD", schema = "RUNTPROD")
@NamedQueries({
    @NamedQuery(name = "Solicitud.findByPlaca", query = "SELECT s FROM Solicitud s WHERE s.solicitudPlaca = :placa"),
    @NamedQuery(name = "Solicitud.findByTramitesPlaca", query = "SELECT COUNT(s) FROM Solicitud s WHERE s.solicitudPlaca = :placa"),
    @NamedQuery(name = "Solicitud.findByNroSolicitud", query = "SELECT s FROM Solicitud s WHERE s.solicitudIdenSolic = :solicitud")
})
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SOLICITUD_IDENSOLIC")
    private Long solicitudIdenSolic;
    @Column(name = "SOLICITUD_AUTOTRANS_IDAUTTRA")
    private Long solicitudAutotransIdauttra;
    @Column(name = "SOLICITUD_TIPREGSOL_IDREGISOL")
    private Long solicitudTipRegSol;
    @Column(name = "SOLICITUD_PERSONA_IDPERSONA")
    private Long solicitudIdPersona;
    @Column(name = "SOLICITUD_PERSONA_NRODOCUME")
    private String solicitudNroDocumento;
    @Column(name = "SOLICITUD_PERSONA_TIPOIDENT")
    private String solicitudTipoIdent;
    @Column(name = "SOLICITUD_PODEPROPI_IDPODER")
    private Long solicitudIdPoder;
    @Column(name = "SOLICITUD_NROLIQAUT")
    private Long solicitudNroLiqAut;
    @Column(name = "SOLICITUD_PLACA_NUMPLACA")
    private String solicitudPlaca;
    @Column(name = "SOLICITUD_PERSONA_IDENPERSO")
    private Long solicitudIdenPerso;        
    @Column(name = "SOLICITUD_INDISOLIC")
    private String solicitudIndiSolic;
    @Column(name = "SOLICITUD_INDILIQUI")
    private String solicitudIndiliqui;
    @Temporal(TemporalType.DATE)
    @Column(name = "SOLICITUD_FECHREGIS")
    private Date solicitudFechRegis;
    @Temporal(TemporalType.DATE)
    @Column(name = "SOLICITUD_FECHVIGEN")
    private Date solicitudFechVigen;
    @Temporal(TemporalType.DATE)
    @Column(name = "SOLICITUD_FECHEJECU")
    private Date solicitudFechEjecu;
    @Column(name = "SOLICITUD_TIPOSOLIC_NOMBRE")
    private String solicitudTipoSolicitud;
    @Column(name = "SOLICITUD_AUTOTRANS_IDEMPSEDE")
    private Long solicitudAutotransSede;
    @Column(name = "SOLICITUD_INDIINGRE")
    private String solicitudIndiIngre;
    @Column(name = "SOLICITUD_NRODOCREP")
    private String solicitudNroDocRep;
    @Column(name = "SOLICITUD_TIPOIDENT_TIPDOCREP")
    private String solicitudTipoDocRep;
    @Column(name = "SOLICITUD_APRFUNVAH")
    private String solicitudAprFunVah;
    @Column(name = "SOLICITUD_SOLCIUDAD")
    private String solicitudSolCiudad;

    public Solicitud() {
        //Default constructor
    }
    
    public Solicitud(Long solicitudIdenSolic, Long solicitudAutotransIdauttra, Long solicitudTipRegSol, Long solicitudIdPersona, String solicitudNroDocumento, String solicitudTipoIdent, String solicitudPlaca, Date solicitudFechRegis, Date solicitudFechVigen, Date solicitudFechEjecu) {
        this.solicitudIdenSolic = solicitudIdenSolic;
        this.solicitudAutotransIdauttra = solicitudAutotransIdauttra;
        this.solicitudTipRegSol = solicitudTipRegSol;
        this.solicitudIdPersona = solicitudIdPersona;
        this.solicitudNroDocumento = solicitudNroDocumento;
        this.solicitudTipoIdent = solicitudTipoIdent;
        this.solicitudPlaca = solicitudPlaca;
        this.solicitudFechRegis = solicitudFechRegis;
        this.solicitudFechVigen = solicitudFechVigen;
        this.solicitudFechEjecu = solicitudFechEjecu;
    }

    public Long getSolicitudIdenSolic() {
        return solicitudIdenSolic;
    }

    public void setSolicitudIdenSolic(Long solicitudIdenSolic) {
        this.solicitudIdenSolic = solicitudIdenSolic;
    }

    public Long getSolicitudAutotransIdauttra() {
        return solicitudAutotransIdauttra;
    }

    public void setSolicitudAutotransIdauttra(Long solicitudAutotransIdauttra) {
        this.solicitudAutotransIdauttra = solicitudAutotransIdauttra;
    }

    public Long getSolicitudTipRegSol() {
        return solicitudTipRegSol;
    }

    public void setSolicitudTipRegSol(Long solicitudTipRegSol) {
        this.solicitudTipRegSol = solicitudTipRegSol;
    }

    public Long getSolicitudIdPersona() {
        return solicitudIdPersona;
    }

    public void setSolicitudIdPersona(Long solicitudIdPersona) {
        this.solicitudIdPersona = solicitudIdPersona;
    }

    public String getSolicitudNroDocumento() {
        return solicitudNroDocumento;
    }

    public void setSolicitudNroDocumento(String solicitudNroDocumento) {
        this.solicitudNroDocumento = solicitudNroDocumento;
    }

    public String getSolicitudTipoIdent() {
        return solicitudTipoIdent;
    }

    public void setSolicitudTipoIdent(String solicitudTipoIdent) {
        this.solicitudTipoIdent = solicitudTipoIdent;
    }

    public Long getSolicitudIdPoder() {
        return solicitudIdPoder;
    }

    public void setSolicitudIdPoder(Long solicitudIdPoder) {
        this.solicitudIdPoder = solicitudIdPoder;
    }

    public Long getSolicitudNroLiqAut() {
        return solicitudNroLiqAut;
    }

    public void setSolicitudNroLiqAut(Long solicitudNroLiqAut) {
        this.solicitudNroLiqAut = solicitudNroLiqAut;
    }

    public String getSolicitudPlaca() {
        return solicitudPlaca;
    }

    public void setSolicitudPlaca(String solicitudPlaca) {
        this.solicitudPlaca = solicitudPlaca;
    }

    public Long getSolicitudIdenPerso() {
        return solicitudIdenPerso;
    }

    public void setSolicitudIdenPerso(Long solicitudIdenPerso) {
        this.solicitudIdenPerso = solicitudIdenPerso;
    }

    public String getSolicitudIndiSolic() {
        return solicitudIndiSolic;
    }

    public void setSolicitudIndiSolic(String solicitudIndiSolic) {
        this.solicitudIndiSolic = solicitudIndiSolic;
    }

    public String getSolicitudIndiliqui() {
        return solicitudIndiliqui;
    }

    public void setSolicitudIndiliqui(String solicitudIndiliqui) {
        this.solicitudIndiliqui = solicitudIndiliqui;
    }

    public Date getSolicitudFechRegis() {
        return solicitudFechRegis;
    }

    public void setSolicitudFechRegis(Date solicitudFechRegis) {
        this.solicitudFechRegis = solicitudFechRegis;
    }

    public Date getSolicitudFechVigen() {
        return solicitudFechVigen;
    }

    public void setSolicitudFechVigen(Date solicitudFechVigen) {
        this.solicitudFechVigen = solicitudFechVigen;
    }

    public Date getSolicitudFechEjecu() {
        return solicitudFechEjecu;
    }

    public void setSolicitudFechEjecu(Date solicitudFechEjecu) {
        this.solicitudFechEjecu = solicitudFechEjecu;
    }

    public String getSolicitudTipoSolicitud() {
        return solicitudTipoSolicitud;
    }

    public void setSolicitudTipoSolicitud(String solicitudTipoSolicitud) {
        this.solicitudTipoSolicitud = solicitudTipoSolicitud;
    }

    public Long getSolicitudAutotransSede() {
        return solicitudAutotransSede;
    }

    public void setSolicitudAutotransSede(Long solicitudAutotransSede) {
        this.solicitudAutotransSede = solicitudAutotransSede;
    }

    public String getSolicitudIndiIngre() {
        return solicitudIndiIngre;
    }

    public void setSolicitudIndiIngre(String solicitudIndiIngre) {
        this.solicitudIndiIngre = solicitudIndiIngre;
    }

    public String getSolicitudNroDocRep() {
        return solicitudNroDocRep;
    }

    public void setSolicitudNroDocRep(String solicitudNroDocRep) {
        this.solicitudNroDocRep = solicitudNroDocRep;
    }

    public String getSolicitudTipoDocRep() {
        return solicitudTipoDocRep;
    }

    public void setSolicitudTipoDocRep(String solicitudTipoDocRep) {
        this.solicitudTipoDocRep = solicitudTipoDocRep;
    }

    public String getSolicitudAprFunVah() {
        return solicitudAprFunVah;
    }

    public void setSolicitudAprFunVah(String solicitudAprFunVah) {
        this.solicitudAprFunVah = solicitudAprFunVah;
    }

    public String getSolicitudSolCiudad() {
        return solicitudSolCiudad;
    }

    public void setSolicitudSolCiudad(String solicitudSolCiudad) {
        this.solicitudSolCiudad = solicitudSolCiudad;
    } 

}
