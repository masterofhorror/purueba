/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.common;

import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.AutoridadTransitoHQ;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.ConfArcCar;
import co.com.runt.sagir.entities.HiAutomotor;
import co.com.runt.sagir.entities.LicTto;
import co.com.runt.sagir.entities.LogArchivoFolio;
import co.com.runt.sagir.entities.LogDatosArchivo;
import co.com.runt.sagir.entities.MgActualizacionRna;
import co.com.runt.sagir.entities.TblLicTto;
import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class Casteador {

    public LogDatosArchivo castLogDatosArchivo(final String linea, final String nombreArchivo, final Long idAutoridad, final String tipoArchivo) {
        LogDatosArchivo log = new LogDatosArchivo();
        log.setDatosarchivoEntrada(linea);
        log.setDatosarchivoFecha(new Date());
        log.setDatosarchivoTipoarchivo(tipoArchivo);
        log.setDatosarchivoAutoridad(idAutoridad);
        log.setNombreArchivo(nombreArchivo);
        return log;
    }

    public MgActualizacionRna castMgActualizacionRna(LicTto datos, Automotor automotor, final String estadoVehicActual, final String estadoVehicSolicita, final Integer tipoProceso, final String usuario) {
        MgActualizacionRna actualizacionRna = new MgActualizacionRna();
        actualizacionRna.setCodCarga(Integer.parseInt(datos.getCodCarga()));
        actualizacionRna.setDescripcionEstado(null);
        actualizacionRna.setEstadoProceso(0);
        actualizacionRna.setEstadoVehiculoCargado(estadoVehicActual);
        actualizacionRna.setEstadoVehiculoSolicita(estadoVehicSolicita);
        actualizacionRna.setFechaProceso(new Date());
        actualizacionRna.setIdClaseCargado(0);
        actualizacionRna.setIdClaseSolicita(0);
        actualizacionRna.setIdFuncionario(usuario);
        actualizacionRna.setIdMarcaCargado(0);
        actualizacionRna.setIdMarcaSolicita(0);
        actualizacionRna.setIdServicioAnterior(0);
        actualizacionRna.setIdServicioNuevo(0);
        actualizacionRna.setModeloCargado(0);
        actualizacionRna.setModeloSolicita(0);
        actualizacionRna.setNroChasisCargado(null);
        actualizacionRna.setNroChasisSolicita(null);
        actualizacionRna.setNroMotorCargado(null);
        actualizacionRna.setNroMotorSolicita(null);
        actualizacionRna.setNroRegistroVehiculo(automotor.getAutomotorNroregveh());
        actualizacionRna.setNroSerieCargado(null);
        actualizacionRna.setNroSerieSolicita(null);
        actualizacionRna.setOtCargo(Integer.parseInt(datos.getIdSecretaria()));
        actualizacionRna.setOtSolicita(Integer.parseInt(datos.getIdSecretaria()));
        actualizacionRna.setPlaca(automotor.getAutomotorPlacaNumplaca());
        actualizacionRna.setTipoProceso(tipoProceso);
        return actualizacionRna;
    }

    public HiAutomotor registrarLog(final String datoAnterior, final String datoNuevo, final String nombreCampo, final String usuario, final String placa, final String nroTicket) {
        HiAutomotor hiAutomotor = new HiAutomotor();
        hiAutomotor.setDatoAnterios(datoAnterior);
        hiAutomotor.setDatoNuevo(datoNuevo);
        hiAutomotor.setNombreColumna(nombreCampo);
        hiAutomotor.setFechaModificacion(new Date());
        hiAutomotor.setPlaca(placa);
        hiAutomotor.setTipoActo(nroTicket);
        hiAutomotor.setUsuarioModifica(usuario);
        return hiAutomotor;
    }

    public MgActualizacionRna castMgActualizacionRnaMarcarVehiculo(Automotor automotor, final String observacion, final String usuario) {
        MgActualizacionRna castMg = new MgActualizacionRna();
        castMg.setNroRegistroVehiculo(automotor.getAutomotorNroregveh());
        castMg.setPlaca(automotor.getAutomotorPlacaNumplaca());
        castMg.setOtCargo(0);
        castMg.setOtSolicita(0);
        castMg.setEstadoVehiculoCargado("No aplica");
        castMg.setEstadoVehiculoSolicita("No aplica");
        castMg.setIdClaseCargado(0);
        castMg.setIdClaseSolicita(0);
        castMg.setIdMarcaCargado(0);
        castMg.setIdMarcaSolicita(0);
        castMg.setModeloCargado(0);
        castMg.setModeloSolicita(0);
        castMg.setTipoProceso(6);
        castMg.setEstadoProceso(0);
        castMg.setFechaProceso(new Date());
        castMg.setDescripcionEstado("Marcar vehículo mal reportado - " + observacion);
        castMg.setIdFuncionario(usuario);
        return castMg;
    }

    public TblLicTto castTblLicTto(TblLicTto tto, final String observacion) {
        TblLicTto licTto = new TblLicTto();
        licTto.setArccarNumReg(tto.getArccarNumReg());
        licTto.setArchivoCargue(tto.getArchivoCargue());
        licTto.setCapPasajeros(tto.getCapPasajeros());
        licTto.setCapPasajerosPie(tto.getCapPasajerosPie());
        licTto.setCapToneladas(tto.getCapToneladas());
        licTto.setCilindraje(tto.getCilindraje());
        licTto.setCodCargaTraslado(tto.getCodCargaTraslado());
        licTto.setCodCriterio(tto.getCodCriterio());
        licTto.setDepartamental(tto.getDepartamental());
        licTto.setDesColor(tto.getDesColor());
        licTto.setDesMigrado(tto.getDesMigrado());
        licTto.setDesMigradoCombu(tto.getDesMigradoCombu());
        licTto.setDesMigradoLicTto(tto.getDesMigradoLicTto());
        licTto.setDesMigradoPasaj(tto.getDesMigradoPasaj());
        licTto.setEstadoVehiculo(tto.getEstadoVehiculo());
        licTto.setFechaDocumento(tto.getFechaDocumento());
        licTto.setFechaMatriculaInicial(tto.getFechaMatriculaInicial());
        licTto.setFechaNovedad(tto.getFechaNovedad());
        licTto.setFormatoNvo(tto.getFormatoNvo());
        licTto.setIdCarroceria(tto.getIdCarroceria());
        licTto.setIdClase(tto.getIdClase());
        licTto.setIdColor(tto.getIdColor());
        licTto.setIdColorRunt(tto.getIdColorRunt());
        licTto.setIdCombustible(tto.getIdCombustible());
        licTto.setIdLinea(tto.getIdLinea());
        licTto.setIdLineaRunt(tto.getIdLineaRunt());
        licTto.setIdMarca(tto.getIdMarca());
        licTto.setIdMarcaRunt(tto.getIdMarcaRunt());
        licTto.setIdModalidad(tto.getIdModalidad());
        licTto.setIdSecretariaOrigen(tto.getIdSecretariaOrigen());
        licTto.setIdServicio(tto.getIdServicio());
        licTto.setIndValido(tto.getIndValido());
        licTto.setModelo(tto.getModelo());
        licTto.setNovCodCargant(tto.getNovCodCargant());
        licTto.setNovedad(tto.getNovedad());
        licTto.setNroChasis(tto.getNroChasis());
        licTto.setNroComprobante(tto.getNroComprobante());
        licTto.setNroDocumento(tto.getNroDocumento());
        licTto.setNroLicTto(tto.getNroLicTto());
        licTto.setNroMotor(tto.getNroMotor());
        licTto.setNroPlacaRepetida(tto.getNroPlacaRepetida());
        licTto.setNroSerie(tto.getNroSerie());
        licTto.setOrganismoExpedidor(tto.getOrganismoExpedidor());
        licTto.setOrigenVehiculo(tto.getOrigenVehiculo());
        licTto.setRadicado(tto.getRadicado());
        licTto.setRuntEstado(tto.getRuntEstado());
        licTto.setSirevConsecutivo(tto.getSirevConsecutivo());
        licTto.setSirevMi(tto.getSirevMi());
        licTto.setSirevTipoEspecie(tto.getSirevTipoEspecie());
        licTto.setTipoDocumento(tto.getTipoDocumento());
        licTto.setLicTtoPK(tto.getLicTtoPK());
        licTto.setQa(observacion);
        licTto.setFechaQa(new Date());
        return licTto;
    }
}
