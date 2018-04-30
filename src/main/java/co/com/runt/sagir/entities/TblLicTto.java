/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "LIC_TTO", schema = "MIGRACIONQX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblLicTto.findAll", query = "SELECT l FROM TblLicTto l"),
    @NamedQuery(name = "TblLicTto.findByIdSecretaria", query = "SELECT l FROM TblLicTto l WHERE l.licTtoPK = :idSecretaria"),
    @NamedQuery(name = "TblLicTto.findByNroPlaca", query = "SELECT l FROM TblLicTto l WHERE l.licTtoPK.nroPlaca = :placa"),
    @NamedQuery(name = "TblLicTto.findByPlacaCodCarga", query = "SELECT l FROM TblLicTto l WHERE l.licTtoPK.nroPlaca = :placa AND l.codCriterio IS NULL"),
    @NamedQuery(name = "TblLicTto.findByCountNroPlaca", query = "SELECT COUNT(l) FROM TblLicTto l WHERE l.licTtoPK.nroPlaca = :placa"),
    @NamedQuery(name = "TblLicTto.findByNroPlacaConCarga", query = "SELECT l FROM TblLicTto l WHERE l.licTtoPK.nroPlaca = :placa AND l.licTtoPK.codCarga = :codCarga"),
    @NamedQuery(name = "TblLicTto.findByPlacaCodCargaIdSecretaria", query = "SELECT l FROM TblLicTto l WHERE l.licTtoPK.nroPlaca = :nroPlaca AND l.licTtoPK.codCarga = :codCarga AND l.licTtoPK.idSecretaria = :idSecretaria"),
    @NamedQuery(name = "TblLicTto.findByIdMarca", query = "SELECT l FROM TblLicTto l WHERE l.idMarca = :idMarca"),
    @NamedQuery(name = "TblLicTto.findByIdLinea", query = "SELECT l FROM TblLicTto l WHERE l.idLinea = :idLinea"),
    @NamedQuery(name = "TblLicTto.findByIdClase", query = "SELECT l FROM TblLicTto l WHERE l.idClase = :idClase"),
    @NamedQuery(name = "TblLicTto.findByIdColor", query = "SELECT l FROM TblLicTto l WHERE l.idColor = :idColor"),
    @NamedQuery(name = "TblLicTto.findByIdServicio", query = "SELECT l FROM TblLicTto l WHERE l.idServicio = :idServicio"),
    @NamedQuery(name = "TblLicTto.findByIdCarroceria", query = "SELECT l FROM TblLicTto l WHERE l.idCarroceria = :idCarroceria"),
    @NamedQuery(name = "TblLicTto.findByIdModalidad", query = "SELECT l FROM TblLicTto l WHERE l.idModalidad = :idModalidad"),
    @NamedQuery(name = "TblLicTto.findByCilindraje", query = "SELECT l FROM TblLicTto l WHERE l.cilindraje = :cilindraje"),
    @NamedQuery(name = "TblLicTto.findByModelo", query = "SELECT l FROM TblLicTto l WHERE l.modelo = :modelo"),
    @NamedQuery(name = "TblLicTto.findByNroMotor", query = "SELECT l FROM TblLicTto l WHERE l.nroMotor = :nroMotor"),
    @NamedQuery(name = "TblLicTto.findByNroSerie", query = "SELECT l FROM TblLicTto l WHERE l.nroSerie = :nroSerie"),
    @NamedQuery(name = "TblLicTto.findByNroChasis", query = "SELECT l FROM TblLicTto l WHERE l.nroChasis = :nroChasis"),
    @NamedQuery(name = "TblLicTto.findByCapToneladas", query = "SELECT l FROM TblLicTto l WHERE l.capToneladas = :capToneladas"),
    @NamedQuery(name = "TblLicTto.findByCapPasajeros", query = "SELECT l FROM TblLicTto l WHERE l.capPasajeros = :capPasajeros"),
    @NamedQuery(name = "TblLicTto.findByFechaMatriculaInicial", query = "SELECT l FROM TblLicTto l WHERE l.fechaMatriculaInicial = :fechaMatriculaInicial"),
    @NamedQuery(name = "TblLicTto.findByCapPasajerosPie", query = "SELECT l FROM TblLicTto l WHERE l.capPasajerosPie = :capPasajerosPie"),
    @NamedQuery(name = "TblLicTto.findByIdCombustible", query = "SELECT l FROM TblLicTto l WHERE l.idCombustible = :idCombustible"),
    @NamedQuery(name = "TblLicTto.findByNroComprobante", query = "SELECT l FROM TblLicTto l WHERE l.nroComprobante = :nroComprobante"),
    @NamedQuery(name = "TblLicTto.findByTipoDocumento", query = "SELECT l FROM TblLicTto l WHERE l.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "TblLicTto.findByNroDocumento", query = "SELECT l FROM TblLicTto l WHERE l.nroDocumento = :nroDocumento"),
    @NamedQuery(name = "TblLicTto.findByFechaDocumento", query = "SELECT l FROM TblLicTto l WHERE l.fechaDocumento = :fechaDocumento"),
    @NamedQuery(name = "TblLicTto.findByOrganismoExpedidor", query = "SELECT l FROM TblLicTto l WHERE l.organismoExpedidor = :organismoExpedidor"),
    @NamedQuery(name = "TblLicTto.findByArchivoCargue", query = "SELECT l FROM TblLicTto l WHERE l.archivoCargue = :archivoCargue"),
    @NamedQuery(name = "TblLicTto.findByEstadoVehiculo", query = "SELECT l FROM TblLicTto l WHERE l.estadoVehiculo = :estadoVehiculo"),
    @NamedQuery(name = "TblLicTto.findByDesColor", query = "SELECT l FROM TblLicTto l WHERE l.desColor = :desColor"),
    @NamedQuery(name = "TblLicTto.findByIndValido", query = "SELECT l FROM TblLicTto l WHERE l.indValido = :indValido"),
    @NamedQuery(name = "TblLicTto.findByCodCriterio", query = "SELECT l FROM TblLicTto l WHERE l.codCriterio = :codCriterio"),
    @NamedQuery(name = "TblLicTto.findByDesMigrado", query = "SELECT l FROM TblLicTto l WHERE l.desMigrado = :desMigrado"),
    @NamedQuery(name = "TblLicTto.findByDesMigradoCombu", query = "SELECT l FROM TblLicTto l WHERE l.desMigradoCombu = :desMigradoCombu"),
    @NamedQuery(name = "TblLicTto.findByDesMigradoPasaj", query = "SELECT l FROM TblLicTto l WHERE l.desMigradoPasaj = :desMigradoPasaj"),
    @NamedQuery(name = "TblLicTto.findByIdMarcaRunt", query = "SELECT l FROM TblLicTto l WHERE l.idMarcaRunt = :idMarcaRunt"),
    @NamedQuery(name = "TblLicTto.findByIdLineaRunt", query = "SELECT l FROM TblLicTto l WHERE l.idLineaRunt = :idLineaRunt"),
    @NamedQuery(name = "TblLicTto.findByIdColorRunt", query = "SELECT l FROM TblLicTto l WHERE l.idColorRunt = :idColorRunt"),
    @NamedQuery(name = "TblLicTto.findBySirevMi", query = "SELECT l FROM TblLicTto l WHERE l.sirevMi = :sirevMi"),
    @NamedQuery(name = "TblLicTto.findBySirevTipoEspecie", query = "SELECT l FROM TblLicTto l WHERE l.sirevTipoEspecie = :sirevTipoEspecie"),
    @NamedQuery(name = "TblLicTto.findBySirevConsecutivo", query = "SELECT l FROM TblLicTto l WHERE l.sirevConsecutivo = :sirevConsecutivo"),
    @NamedQuery(name = "TblLicTto.findByRadicado", query = "SELECT l FROM TblLicTto l WHERE l.radicado = :radicado"),
    @NamedQuery(name = "TblLicTto.findByIdSecretariaOrigen", query = "SELECT l FROM TblLicTto l WHERE l.idSecretariaOrigen = :idSecretariaOrigen"),
    @NamedQuery(name = "TblLicTto.findByArccarNumReg", query = "SELECT l FROM TblLicTto l WHERE l.arccarNumReg = :arccarNumReg"),
    @NamedQuery(name = "TblLicTto.findByNovedad", query = "SELECT l FROM TblLicTto l WHERE l.novedad = :novedad"),
    @NamedQuery(name = "TblLicTto.findByFechaNovedad", query = "SELECT l FROM TblLicTto l WHERE l.fechaNovedad = :fechaNovedad"),
    @NamedQuery(name = "TblLicTto.findByNovCodCargant", query = "SELECT l FROM TblLicTto l WHERE l.novCodCargant = :novCodCargant"),
    @NamedQuery(name = "TblLicTto.findByOrigenVehiculo", query = "SELECT l FROM TblLicTto l WHERE l.origenVehiculo = :origenVehiculo"),
    @NamedQuery(name = "TblLicTto.findByRuntEstado", query = "SELECT l FROM TblLicTto l WHERE l.runtEstado = :runtEstado"),
    @NamedQuery(name = "TblLicTto.findByCodCargaTraslado", query = "SELECT l FROM TblLicTto l WHERE l.codCargaTraslado = :codCargaTraslado"),
    @NamedQuery(name = "TblLicTto.findByNroPlacaRepetida", query = "SELECT l FROM TblLicTto l WHERE l.nroPlacaRepetida = :nroPlacaRepetida"),
    @NamedQuery(name = "TblLicTto.findByFormatoNvo", query = "SELECT l FROM TblLicTto l WHERE l.formatoNvo = :formatoNvo"),
    @NamedQuery(name = "TblLicTto.findByDepartamental", query = "SELECT l FROM TblLicTto l WHERE l.departamental = :departamental"),
    @NamedQuery(name = "TblLicTto.findByQa", query = "SELECT l FROM TblLicTto l WHERE l.qa = :qa"),
    @NamedQuery(name = "TblLicTto.findByFechaQa", query = "SELECT l FROM TblLicTto l WHERE l.fechaQa = :fechaQa")
})

public class TblLicTto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TblLicTtoPK licTtoPK;
    @Column(name = "ID_MARCA")
    private String idMarca;
    @Column(name = "ID_LINEA")
    private String idLinea;
    @Column(name = "ID_CLASE")
    private Long idClase;
    @Column(name = "ID_COLOR")
    private Long idColor;
    @Column(name = "ID_SERVICIO")
    private Long idServicio;
    @Column(name = "ID_CARROCERIA")
    private Long idCarroceria;
    @Column(name = "ID_MODALIDAD")
    private Long idModalidad;
    @Column(name = "CILINDRAJE")
    private Long cilindraje;
    @Column(name = "MODELO")
    private Long modelo;
    @Column(name = "NRO_MOTOR")
    private String nroMotor;
    @Column(name = "NRO_SERIE")
    private String nroSerie;
    @Column(name = "NRO_CHASIS")
    private String nroChasis;
    @Column(name = "CAP_TONELADAS")
    private Long capToneladas;
    @Column(name = "CAP_PASAJEROS")
    private Long capPasajeros;
    @Column(name = "FECHA_MATRICULA_INICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMatriculaInicial;
    @Column(name = "CAP_PASAJEROS_PIE")
    private Long capPasajerosPie;
    @Column(name = "ID_COMBUSTIBLE")
    private Long idCombustible;
    @Column(name = "NRO_COMPROBANTE")
    private String nroComprobante;
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    @Column(name = "NRO_DOCUMENTO")
    private String nroDocumento;
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "ORGANISMO_EXPEDIDOR")
    private String organismoExpedidor;
    @Column(name = "ARCHIVO_CARGUE")
    private String archivoCargue;
    @Column(name = "NRO_LIC_TTO")
    private String nroLicTto;
    @Column(name = "ESTADO_VEHICULO")
    private String estadoVehiculo;
    @Column(name = "DES_COLOR")
    private String desColor;
    @Column(name = "IND_VALIDO")
    private Short indValido;
    @Column(name = "COD_CRITERIO")
    private Long codCriterio;
    @Column(name = "DES_MIGRADO")
    private String desMigrado;
    @Column(name = "DES_MIGRADO_COMBU")
    private String desMigradoCombu;
    @Column(name = "DES_MIGRADO_PASAJ")
    private String desMigradoPasaj;
    @Column(name = "ID_MARCA_RUNT")
    private Long idMarcaRunt;
    @Column(name = "ID_LINEA_RUNT")
    private Long idLineaRunt;
    @Column(name = "ID_COLOR_RUNT")
    private Long idColorRunt;
    @Column(name = "DES_MIGRADO_LIC_TTO")
    private String desMigradoLicTto;
    @Column(name = "SIREV_MI")
    private Long sirevMi;
    @Column(name = "SIREV_TIPO_ESPECIE")
    private Short sirevTipoEspecie;
    @Column(name = "SIREV_CONSECUTIVO")
    private Integer sirevConsecutivo;
    @Column(name = "RADICADO")
    private String radicado;
    @Column(name = "ID_SECRETARIA_ORIGEN")
    private Long idSecretariaOrigen;
    @Column(name = "ARCCAR_NUM_REG")
    private Long arccarNumReg;
    @Column(name = "NOVEDAD")
    private String novedad;
    @Column(name = "FECHA_NOVEDAD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNovedad;
    @Column(name = "NOV_COD_CARGANT")
    private Long novCodCargant;
    @Column(name = "ORIGEN_VEHICULO")
    private String origenVehiculo;
    @Column(name = "RUNT_ESTADO")
    private String runtEstado;
    @Column(name = "COD_CARGA_TRASLADO")
    private Long codCargaTraslado;
    @Column(name = "NRO_PLACA_REPETIDA")
    private String nroPlacaRepetida;
    @Column(name = "FORMATO_NVO")
    private String formatoNvo;
    @Column(name = "DEPARTAMENTAL")
    private String departamental;
    @Column(name = "QA")
    private String qa;
    @Column(name = "FECHA_QA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaQa;

    public TblLicTto() {
        //Constructor vacio
    }

    public TblLicTto(TblLicTtoPK licTtoPK) {
        this.licTtoPK = licTtoPK;
    }

    public TblLicTto(BigInteger idSecretaria, String nroPlaca, Integer codCarga) {
        this.licTtoPK = new TblLicTtoPK(idSecretaria, nroPlaca, codCarga);
    }

    public TblLicTtoPK getLicTtoPK() {
        return licTtoPK;
    }

    public void setLicTtoPK(TblLicTtoPK licTtoPK) {
        this.licTtoPK = licTtoPK;
    }

    public String getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(String idMarca) {
        this.idMarca = idMarca;
    }

    public String getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(String idLinea) {
        this.idLinea = idLinea;
    }

    public Long getIdClase() {
        return idClase;
    }

    public void setIdClase(Long idClase) {
        this.idClase = idClase;
    }

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdCarroceria() {
        return idCarroceria;
    }

    public void setIdCarroceria(Long idCarroceria) {
        this.idCarroceria = idCarroceria;
    }

    public Long getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(Long idModalidad) {
        this.idModalidad = idModalidad;
    }

    public Long getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(Long cilindraje) {
        this.cilindraje = cilindraje;
    }

    public Long getModelo() {
        return modelo;
    }

    public void setModelo(Long modelo) {
        this.modelo = modelo;
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

    public String getNroChasis() {
        return nroChasis;
    }

    public void setNroChasis(String nroChasis) {
        this.nroChasis = nroChasis;
    }

    public Long getCapToneladas() {
        return capToneladas;
    }

    public void setCapToneladas(Long capToneladas) {
        this.capToneladas = capToneladas;
    }

    public Long getCapPasajeros() {
        return capPasajeros;
    }

    public void setCapPasajeros(Long capPasajeros) {
        this.capPasajeros = capPasajeros;
    }

    public Date getFechaMatriculaInicial() {
        return fechaMatriculaInicial;
    }

    public void setFechaMatriculaInicial(Date fechaMatriculaInicial) {
        this.fechaMatriculaInicial = fechaMatriculaInicial;
    }

    public Long getCapPasajerosPie() {
        return capPasajerosPie;
    }

    public void setCapPasajerosPie(Long capPasajerosPie) {
        this.capPasajerosPie = capPasajerosPie;
    }

    public Long getIdCombustible() {
        return idCombustible;
    }

    public void setIdCombustible(Long idCombustible) {
        this.idCombustible = idCombustible;
    }

    public String getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(String nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getOrganismoExpedidor() {
        return organismoExpedidor;
    }

    public void setOrganismoExpedidor(String organismoExpedidor) {
        this.organismoExpedidor = organismoExpedidor;
    }

    public String getArchivoCargue() {
        return archivoCargue;
    }

    public void setArchivoCargue(String archivoCargue) {
        this.archivoCargue = archivoCargue;
    }

    public String getNroLicTto() {
        return nroLicTto;
    }

    public void setNroLicTto(String nroLicTto) {
        this.nroLicTto = nroLicTto;
    }

    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(String estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public String getDesColor() {
        return desColor;
    }

    public void setDesColor(String desColor) {
        this.desColor = desColor;
    }

    public Short getIndValido() {
        return indValido;
    }

    public void setIndValido(Short indValido) {
        this.indValido = indValido;
    }

    public Long getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(Long codCriterio) {
        this.codCriterio = codCriterio;
    }

    public String getDesMigrado() {
        return desMigrado;
    }

    public void setDesMigrado(String desMigrado) {
        this.desMigrado = desMigrado;
    }

    public String getDesMigradoCombu() {
        return desMigradoCombu;
    }

    public void setDesMigradoCombu(String desMigradoCombu) {
        this.desMigradoCombu = desMigradoCombu;
    }

    public String getDesMigradoPasaj() {
        return desMigradoPasaj;
    }

    public void setDesMigradoPasaj(String desMigradoPasaj) {
        this.desMigradoPasaj = desMigradoPasaj;
    }

    public Long getIdMarcaRunt() {
        return idMarcaRunt;
    }

    public void setIdMarcaRunt(Long idMarcaRunt) {
        this.idMarcaRunt = idMarcaRunt;
    }

    public Long getIdLineaRunt() {
        return idLineaRunt;
    }

    public void setIdLineaRunt(Long idLineaRunt) {
        this.idLineaRunt = idLineaRunt;
    }

    public Long getIdColorRunt() {
        return idColorRunt;
    }

    public void setIdColorRunt(Long idColorRunt) {
        this.idColorRunt = idColorRunt;
    }

    public String getDesMigradoLicTto() {
        return desMigradoLicTto;
    }

    public void setDesMigradoLicTto(String desMigradoLicTto) {
        this.desMigradoLicTto = desMigradoLicTto;
    }

    public Long getSirevMi() {
        return sirevMi;
    }

    public void setSirevMi(Long sirevMi) {
        this.sirevMi = sirevMi;
    }

    public Short getSirevTipoEspecie() {
        return sirevTipoEspecie;
    }

    public void setSirevTipoEspecie(Short sirevTipoEspecie) {
        this.sirevTipoEspecie = sirevTipoEspecie;
    }

    public Integer getSirevConsecutivo() {
        return sirevConsecutivo;
    }

    public void setSirevConsecutivo(Integer sirevConsecutivo) {
        this.sirevConsecutivo = sirevConsecutivo;
    }

    public String getRadicado() {
        return radicado;
    }

    public void setRadicado(String radicado) {
        this.radicado = radicado;
    }

    public Long getIdSecretariaOrigen() {
        return idSecretariaOrigen;
    }

    public void setIdSecretariaOrigen(Long idSecretariaOrigen) {
        this.idSecretariaOrigen = idSecretariaOrigen;
    }

    public Long getArccarNumReg() {
        return arccarNumReg;
    }

    public void setArccarNumReg(Long arccarNumReg) {
        this.arccarNumReg = arccarNumReg;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public Long getNovCodCargant() {
        return novCodCargant;
    }

    public void setNovCodCargant(Long novCodCargant) {
        this.novCodCargant = novCodCargant;
    }

    public String getOrigenVehiculo() {
        return origenVehiculo;
    }

    public void setOrigenVehiculo(String origenVehiculo) {
        this.origenVehiculo = origenVehiculo;
    }

    public String getRuntEstado() {
        return runtEstado;
    }

    public void setRuntEstado(String runtEstado) {
        this.runtEstado = runtEstado;
    }

    public Long getCodCargaTraslado() {
        return codCargaTraslado;
    }

    public void setCodCargaTraslado(Long codCargaTraslado) {
        this.codCargaTraslado = codCargaTraslado;
    }

    public String getNroPlacaRepetida() {
        return nroPlacaRepetida;
    }

    public void setNroPlacaRepetida(String nroPlacaRepetida) {
        this.nroPlacaRepetida = nroPlacaRepetida;
    }

    public String getFormatoNvo() {
        return formatoNvo;
    }

    public void setFormatoNvo(String formatoNvo) {
        this.formatoNvo = formatoNvo;
    }

    public String getDepartamental() {
        return departamental;
    }

    public void setDepartamental(String departamental) {
        this.departamental = departamental;
    }

    public String getQa() {
        return qa;
    }

    public void setQa(String qa) {
        this.qa = qa;
    }

    public Date getFechaQa() {
        return fechaQa;
    }

    public void setFechaQa(Date fechaQa) {
        this.fechaQa = fechaQa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (licTtoPK != null ? licTtoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) { 
        if (!(object instanceof TblLicTto)) {
            return false;
        }
        TblLicTto other = (TblLicTto) object;
        if ((this.licTtoPK == null && other.licTtoPK != null) || (this.licTtoPK != null && !this.licTtoPK.equals(other.licTtoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mapeoGmartinez.LicTto[ licTtoPK=" + licTtoPK + " ]";
    }

}
