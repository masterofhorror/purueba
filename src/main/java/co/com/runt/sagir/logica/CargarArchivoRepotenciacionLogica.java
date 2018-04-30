/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Casteador;
import co.com.runt.sagir.common.CommonPropietarioLogica;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CambioPropietarioAutomotorDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.ArchivoPropietarioDTO;
import co.com.runt.sagir.dto.CantidadSolicitudesPorPlacaDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.TramitesDTO;
import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.LogDatosArchivo;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.entities.Tramites;
import co.com.runt.sagir.servicios.CargueArchivoService;
import co.com.runt.sagir.utils.TransformacionDozer;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class CargarArchivoRepotenciacionLogica {

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private LogsConsultasLogica guardarLogLogica;

    @EJB
    private Casteador casteador;

    @EJB
    private CambioPropietarioAutomotorDAO cambioPropietarioDAO;

    @Resource
    protected EJBContext context;

    @EJB
    private CargueArchivoService cargueArchivosServices;

    @EJB
    private CommonLogica commonLogica;

    private EntityManager entityManager;

    public MensajeDTO consultaRepotenciacion(final String placa, final InfoUsuarioDTO infoUsuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            List<TramitesDTO> listDTO;
            List<Tramites> solicitud = constanteDAO.solicitudPlaca(placa);
            if (solicitud != null && !solicitud.isEmpty()) {
                listDTO = TransformacionDozer.transformar(solicitud, TramitesDTO.class);
                salida.setCodmensaje(Mensajes.OK);
                salida.setObject(listDTO);
                JsonObject json = new JsonObject();
                json.addProperty("placa", placa);
                String tipoConsulta = Constantes.TIPO_CONSULTA_RNA_PROD;
                Long idauttra = infoUsuario.getIdOrganizacion();
                String datoUsuario = infoUsuario.getLogin();
                LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
                guardarLogLogica.guardarLog(log);
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = "El número de placa ingresado no registra trámites ante RUNT";
                salida.setMensaje(mensaje);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = validaRnrysRnma;
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MensajeDTO cantidadSolicitudesPlaca(final String placa, final InfoUsuarioDTO infoUsuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            Automotor automotor = constanteDAO.consultaVehiculo(placa);
            if (automotor != null) {
                salida.setCodmensaje(Mensajes.OK);
                CantidadSolicitudesPorPlacaDTO cantidadSoli = new CantidadSolicitudesPorPlacaDTO();
                Integer solicitudes = constanteDAO.cantidadSolicitudesPorPlaca(placa);
                cantidadSoli.setPlaca(placa);
                cantidadSoli.setCantidadSolicitudes(solicitudes.toString());
                cantidadSoli.setIdVehiculo(automotor.getAutomotorNroregveh());
                salida.setObject(cantidadSoli);
                JsonObject json = new JsonObject();
                json.addProperty("placa", placa);
                String tipoConsulta = Constantes.TIPO_CONSULTA_RNA_PROD;
                Long idauttra = infoUsuario.getIdOrganizacion();
                String datoUsuario = infoUsuario.getLogin();
                LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
                guardarLogLogica.guardarLog(log);
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = "El vehículo ingresado no se encuentra registrado";
                salida.setMensaje(mensaje);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = validaRnrysRnma;
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MensajeDTO registraMgCarRepotenciacion(final String ruta, final String nombreArc, final Long idAutoridad) {
        MensajeDTO salida = new MensajeDTO();
        List<ArchivoPropietarioDTO> listdto = datosArchivo(ruta, nombreArc, ruta, idAutoridad);
        if (listdto != null && !listdto.isEmpty()) {
            for (ArchivoPropietarioDTO cast : listdto) {
                String tipoArchivo = Constantes.TIPO_ARCHIVO_REPOTENCIACION;
                LogDatosArchivo log = casteador.castLogDatosArchivo(cast.getDatos(), nombreArc, idAutoridad, tipoArchivo);
                boolean registroLog = constanteDAO.guardarLogDatoArchivo(log);
                if (registroLog) {
                    salida.setCodmensaje(Mensajes.OK);
                    salida.setMensaje("Registro de Log correcto");
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    salida.setMensaje("Registro de Log incorrecto");
                }
            }
        }
        return salida;
    }

    public List<ArchivoPropietarioDTO> datosArchivo(final String ruta, final String nombreArc, final String usuario, final Long idSecretaria) {
        List<ArchivoPropietarioDTO> listDTO = new ArrayList<>();
        try {
            byte[] archivo = desfirmaArchivo(ruta, nombreArc, usuario, idSecretaria);
            StringTokenizer st;
            st = new StringTokenizer(new String(archivo, "UTF-8"), "\n");
            if (st.hasMoreElements()) {
                int linea = 0;
                String registro;
                while (st.hasMoreTokens()) {
                    ArchivoPropietarioDTO cast = new ArchivoPropietarioDTO();
                    linea++;
                    registro = (String) st.nextElement();
                    String ot = registro.substring(0, 8);
                    String placa = registro.substring(8, 14);
                    Integer numlineas = linea;
                    String datos = registro;
                    cast.setoT(ot);
                    cast.setPlaca(placa);
                    cast.setLinea(numlineas);
                    cast.setDatos(datos);
                    listDTO.add(cast);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDTO;
    }

    public byte[] desfirmaArchivo(final String ruta, final String nombreArc, final String usuario, final Long idSecretaria) {
        File dir = new File(ruta);
        byte[] data;
        data = convertirArchivo(dir);

        SgCarguearchivos cargue = cambioPropietarioDAO.consultaArchivo(nombreArc, ruta);
        try {
            byte[] contenido = cargueArchivosServices.validarCertificado(usuario, Base64.encodeBase64String(data), idSecretaria);
            if (contenido == null) {
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                cargue.setCarguearchivosEstado(CargueArchivoMensajes.RECHAZADO.toString());
                entityManager.merge(cargue);
                entityManager.flush();
                ut.commit();
            } else {
                return contenido;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return new byte[0];
    }

    public byte[] convertirArchivo(File ficheros) {
        byte[] bFile = new byte[(int) ficheros.length()];

        try (FileInputStream fileInputStream = new FileInputStream(ficheros)) {
            fileInputStream.read(bFile);

            for (int i = 0; i < bFile.length; i++) {
                System.out.print((char) bFile[i]);
            }

            System.out.println("Done");

        } catch (IOException e) {
        }
        return bFile;
    }
}
