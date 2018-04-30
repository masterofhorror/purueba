/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Dsalamanca
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.com.runt.sagir.common.CargueArchivosPropService.class);
        resources.add(co.com.runt.sagir.servicios.AsignacionRangosServicio.class);
        resources.add(co.com.runt.sagir.servicios.AutenticacionServicio.class);
        resources.add(co.com.runt.sagir.servicios.BoletinCambioPropietarioService.class);
        resources.add(co.com.runt.sagir.servicios.CambioEstadoVehiculoService.class);
        resources.add(co.com.runt.sagir.servicios.CambioFormatoPlacaService.class);
        resources.add(co.com.runt.sagir.servicios.CambioResidenciaService.class);
        resources.add(co.com.runt.sagir.servicios.CambioServicioService.class);
        resources.add(co.com.runt.sagir.servicios.CargarRepotenciacionService.class);
        resources.add(co.com.runt.sagir.servicios.CargueArchivoCiaService.class);
        resources.add(co.com.runt.sagir.servicios.CargueArchivoService.class);
        resources.add(co.com.runt.sagir.servicios.CargueArchivoTemporalService.class);
        resources.add(co.com.runt.sagir.servicios.CargueCiaService.class);
        resources.add(co.com.runt.sagir.servicios.CarguePuntualService.class);
        resources.add(co.com.runt.sagir.servicios.CommonService.class);
        resources.add(co.com.runt.sagir.servicios.ConsultaDatosMigradosService.class);
        resources.add(co.com.runt.sagir.servicios.ConsultaDeclaImportService.class);
        resources.add(co.com.runt.sagir.servicios.ConsultaRNAMigraService.class);
        resources.add(co.com.runt.sagir.servicios.ConsultaRNAService.class);
        resources.add(co.com.runt.sagir.servicios.ConsultaRNCMigraService.class);
        resources.add(co.com.runt.sagir.servicios.ConsultaRangoPlacasService.class);
        resources.add(co.com.runt.sagir.servicios.EstadoArchivoService.class);
        resources.add(co.com.runt.sagir.servicios.ExportService.class);
        resources.add(co.com.runt.sagir.servicios.GsonMessageBodyHandler.class);
        resources.add(co.com.runt.sagir.servicios.ListarBoletinService.class);
        resources.add(co.com.runt.sagir.servicios.MarcarVehiculoMalCargadoService.class);
        resources.add(co.com.runt.sagir.servicios.ModificacionesRealizadasServicio.class);
        resources.add(co.com.runt.sagir.servicios.ProcesarDatosArchivoService.class);
        resources.add(co.com.runt.sagir.servicios.ProcesoMigracionRNAService.class);
        resources.add(co.com.runt.sagir.servicios.ProcesoMigracionRNCService.class);
        resources.add(co.com.runt.sagir.servicios.ProcesoMigracionServicio.class);
        resources.add(co.com.runt.sagir.servicios.ProgramacionCargueServicio.class);
        resources.add(co.com.runt.sagir.servicios.ReprocesarRNAService.class);
        resources.add(co.com.runt.sagir.servicios.SesionServicio.class);
        resources.add(co.com.runt.sagir.servicios.UsuarioServicio.class);
    }

}
