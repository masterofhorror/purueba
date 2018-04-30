/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.RangosEspeciesRnaConsolDAO;
import co.com.runt.sagir.dao.RangosEspeciesSerieRnaDAO;
import co.com.runt.sagir.dto.AsignacionRangosDTO;
import co.com.runt.sagir.dto.BusquedaAsignacionRangosDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.RangosEspeciesSerieRnaDTO;
import co.com.runt.sagir.entities.RangosEspeciesRnaConsol;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

/**
 *
 * @author Hmoreno
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AsignacionRangosLogica {

    private static final Logger LOGGER = Logger.getLogger(AsignacionRangosLogica.class.getSimpleName());

    @EJB
    private RangosEspeciesSerieRnaDAO rangosEspeciesSerieRnaDAO;

    @EJB
    private RangosEspeciesRnaConsolDAO rangosEspeciesRnaConsolDAO;

    @Resource
    protected EJBContext context;

    public Long getTotalItems(final BusquedaAsignacionRangosDTO busquedaAsignacionRangos) {
        return rangosEspeciesSerieRnaDAO.buscarTotalPorFiltros(busquedaAsignacionRangos);
    }

    public List<RangosEspeciesSerieRnaDTO> getItems(final BusquedaAsignacionRangosDTO busquedaAsignacionRangos) {
        return TransformacionDozer.transformar(rangosEspeciesSerieRnaDAO.buscarPorFiltros(busquedaAsignacionRangos), RangosEspeciesSerieRnaDTO.class);
    }

    public MensajeDTO guardar(AsignacionRangosDTO entrada, InfoUsuarioDTO usuario) {
        MensajeDTO respuesta = new MensajeDTO();
        respuesta.setCodmensaje(Mensajes.ERROR);

        try {
            Long maximo = rangosEspeciesRnaConsolDAO.buscarMaximo() == null ? 0L : rangosEspeciesRnaConsolDAO.buscarMaximo();

            UserTransaction userTransaction = context.getUserTransaction();

            RangosEspeciesRnaConsol rangosEspeciesRnaConsol = new RangosEspeciesRnaConsol();
            rangosEspeciesRnaConsol.setConsecutivo(maximo + 1);
            rangosEspeciesRnaConsol.setIdSecretaria1(entrada.getOrganismoTransito().getIdSecretaria());
            rangosEspeciesRnaConsol.setCarroMoto(Integer.parseInt(entrada.getEspecieVenal()));
            rangosEspeciesRnaConsol.setDesde(entrada.getDesde());
            rangosEspeciesRnaConsol.setHasta(entrada.getHasta());
            rangosEspeciesRnaConsol.setFecha(new SimpleDateFormat(Constantes.FORMATO_FECHA).parse(entrada.getFechaAsignacion()));
            rangosEspeciesRnaConsol.setResolucion(entrada.getResolucion());
            rangosEspeciesRnaConsol.setFechaEntrega(new Date());
            rangosEspeciesRnaConsol.setObservaciones(entrada.getObservaciones());

            userTransaction.begin();
            rangosEspeciesRnaConsolDAO.save(rangosEspeciesRnaConsol);
            userTransaction.commit();

            rangosEspeciesRnaConsolDAO.callRangosRnaExcel(rangosEspeciesRnaConsol.getIdSecretaria1());
            rangosEspeciesRnaConsolDAO.callRangosRna(rangosEspeciesRnaConsol.getIdSecretaria1(), rangosEspeciesRnaConsol.getObservaciones());
            rangosEspeciesRnaConsolDAO.callSrc0020206520048(rangosEspeciesRnaConsol.getIdSecretaria1(), rangosEspeciesRnaConsol.getObservaciones());
            rangosEspeciesRnaConsolDAO.callMigracionEvPlaca(rangosEspeciesRnaConsol.getIdSecretaria1());

            respuesta.setCodmensaje(Mensajes.OK);
            respuesta.setMensaje("Rango registrado correctamente");
        } catch (Exception e) {
            respuesta.setMensaje("Error registrando el rango. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error realizando el rango.", e);
        }
        return respuesta;
    }

}
