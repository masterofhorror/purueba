/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.common;

import co.com.runt.sagir.dao.CambioPropietarioAutomotorDAO;
import co.com.runt.sagir.dao.CargueArchivoDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.ArchivoPropietarioDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.SgCarguearchivosDTO;
import co.com.runt.sagir.entities.ArcCar;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.ConfArcCar;
import co.com.runt.sagir.entities.LogArchivoFolio;
import co.com.runt.sagir.entities.LogDatosArchivo;
import co.com.runt.sagir.entities.OrganismosTransitoMigrunt;
import co.com.runt.sagir.entities.SgCarguearchivos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class CommonPropietarioLogica {

    private static final Logger LOG = Logger.getLogger(CommonPropietarioLogica.class.getName());

    @EJB
    private CambioPropietarioAutomotorDAO cambioPropietarioDAO;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private CargueArchivoDAO cargueArchivoDAO;

    @EJB
    private Casteador casteador;

    @EJB
    private CargueArchivosPropService cargueArchivosServices;

    @Resource
    protected EJBContext context;

    private EntityManager entityManager;

    public MensajeDTO registraLogDatosArchivo(final String ruta, final String nombreArc, final Long idAutoridad) {
        MensajeDTO salida = new MensajeDTO();
        List<ArchivoPropietarioDTO> listDTO = datosArchivoPropietario(ruta, nombreArc, ruta);
        if (listDTO != null && !listDTO.isEmpty()) {
            for (ArchivoPropietarioDTO cast : listDTO) {
                String tipoArchivo = Constantes.TIPO_ARCHIVO_PROPIETARIO;
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

    public Integer poblaTablaArcCar(final String rutaArchivo, final String nombreArc, final Long idOt, ConfArcCar confArcCar, Carga carga, final String usuario) {
        List<ArchivoPropietarioDTO> listDTO = datosArchivoPropietario(rutaArchivo, nombreArc, usuario);
        if (listDTO != null && !listDTO.isEmpty()) {
            for (ArchivoPropietarioDTO prop : listDTO) {
                String placa = prop.getPlaca();
                ArcCar arcCar = new ArcCar();
                arcCar.setTipoArc(1);
                arcCar.setCodestado(0);
                arcCar.setNomarc(rutaArchivo);
                arcCar.setDeserr(null);
                arcCar.setNomot(idOt.toString());
                String datosArchivo = prop.getDatos();
                arcCar.setDesreg(datosArchivo);
                arcCar.setCodProceso(confArcCar);
                arcCar.setFecProceso(new Date());
                arcCar.setCodCarga(carga.getCodCarga());
                String identificador = placa;
                arcCar.setIdentificador(identificador);
                arcCar.setObservacion(null);
                arcCar.setCodCargaError(1);
                cargueArchivoDAO.registrarArcCar(arcCar);
            }
            return 1;
        }
        return -1;
    }

    public String consultarSecretaria(final String ruta, final String nombreArc, final String usuario) {
        String oT = "";
        try {
            String nombreArchivo = nombreArc;
            //Expresión regular, que valida que elnombre del archivo cumpla con las condiciones
            Pattern expRegular = Pattern.compile(CargueArchivoCommon.EXPRESION_REGULAR_NOMBRE_ARCHIVO_PROPIETARIOS);
            //Se pasa el nombre del archivo por la expresión regular
            Matcher input = expRegular.matcher(nombreArchivo);
            //Se valdia que el nombre del archivo cumpla la expresión regular
            if (input.find()) {
                //Se guarda el grupo 1, de la empresión regular
                String idSecretaria = input.group(1);
                if (idSecretaria != null) {
                    byte[] archivo = desfirmaArchivo(ruta, nombreArc, usuario, idSecretaria);
                    StringTokenizer st;
                    st = new StringTokenizer(new String(archivo, Constantes.UTF8), "\n");
                    if (st == null) {
                        throw new RuntimeException("Archivo mal formado o vacio");
                    }
                    if (st.hasMoreElements()) {
                        try {
                            String registro;
                            while (st.hasMoreTokens()) {
                                registro = (String) st.nextElement();
                                oT = registro.substring(0, 8);
                            }

                        } catch (Exception e) {
                            LOG.log(Level.SEVERE, e.getMessage());
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oT;
    }

    public List<ArchivoPropietarioDTO> datosArchivoPropietario(final String ruta, final String nombreArc, final String usuario) {
        List<ArchivoPropietarioDTO> listDTO = new ArrayList<>();
        try {
            String nombreArchivo = nombreArc;
            //Expresión regular, que valida que elnombre del archivo cumpla con las condiciones
            Pattern expRegular = Pattern.compile(CargueArchivoCommon.EXPRESION_REGULAR_NOMBRE_ARCHIVO_PROPIETARIOS);
            //Se pasa el nombre del archivo por la expresión regular
            Matcher input = expRegular.matcher(nombreArchivo);
            //Se valdia que el nombre del archivo cumpla la expresión regular
            if (input.find()) {
                //Se guarda el grupo 1, de la empresión regular
                String idSecretaria = input.group(1);
                if (idSecretaria != null) {
                    byte[] archivo = desfirmaArchivo(ruta, nombreArc, usuario, idSecretaria);
                    StringTokenizer st;
                    st = new StringTokenizer(new String(archivo, Constantes.UTF8), "\n");
                    if (st == null) {
                        throw new RuntimeException("Archivo mal formado o vacio");
                    }
                    if (st.hasMoreElements()) {
                        try {
                            int linea = 0;
                            String registro;
                            while (st.hasMoreTokens()) {
                                ArchivoPropietarioDTO cast = new ArchivoPropietarioDTO();
                                linea++;
                                registro = (String) st.nextElement();
                                String ot = registro.substring(0, 8);
                                String placa = registro.substring(8, 14);
                                Integer numLineas = linea;
                                String datos = registro;
                                cast.setoT(ot);
                                cast.setPlaca(placa);
                                cast.setLinea(numLineas);
                                cast.setDatos(datos);
                                listDTO.add(cast);
                            }

                        } catch (Exception e) {
                            LOG.log(Level.SEVERE, e.getMessage());
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDTO;
    }

    public byte[] desfirmaArchivo(final String ruta, final String nombreArc, final String usuario, final String idSecretaria) {
        try {
            File dir = new File(ruta);
            byte[] data;
            data = convertirArchivo(dir);

            if (data == null) {
                LOG.log(Level.SEVERE, "Error en el archivo...");
            }

            SgCarguearchivos cargue = cambioPropietarioDAO.consultaArchivo(nombreArc, ruta);
            try {
                try {
                    byte[] contenido = cargueArchivosServices.validarCertificado(usuario, Base64.encodeBase64String(data), Long.parseLong(idSecretaria));
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
                    Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception ex) {
                Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
        }

        return new byte[0];
    }

    public byte[] convertirArchivo(File ficheros) {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) ficheros.length()];

        try {

            fileInputStream = new FileInputStream(ficheros);
            fileInputStream.read(bFile);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return bFile;
    }

    public String numeroRegistros(final String ruta) {
        String i = "";
        String aux;
        FileReader lectorArchivo = null;
        try {
            File dir = new File(ruta);
            lectorArchivo = new FileReader(dir);
            BufferedReader br = new BufferedReader(lectorArchivo);
            try {
                while ((aux = br.readLine()) != null) {
                    i = aux;
                }
            } catch (IOException ex) {
                Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (lectorArchivo != null) {
                try {
                    lectorArchivo.close();
                } catch (IOException ex) {
                    Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return i;
    }

    /**
     * Metodo que retorna el listado de los archivos que tiene el Ot pendientes
     *
     * @param usuario
     * @return
     */
    public List<SgCarguearchivosDTO> consultarCarguesPendientes(final InfoUsuarioDTO usuario) {
        //Se debe recuperar el cÃ³digo Idautra del metodo de login para que filtre por OT
        String idautra = usuario.getIdOrganizacion().toString();
        Calendar fechaAnterior = Calendar.getInstance();
        fechaAnterior.add(Calendar.DATE, -1);
        Date fecha = fechaAnterior.getTime();
        List<SgCarguearchivosDTO> listCargues = new ArrayList<>();
        List<SgCarguearchivos> lista = cambioPropietarioDAO.consultaEstadoCargue(idautra, fecha);
        if (!lista.isEmpty()) {
            for (SgCarguearchivos sc : lista) {
                SgCarguearchivosDTO cast = new SgCarguearchivosDTO();
                cast.setCarguearchivosId(sc.getCarguearchivosId());
                String nroTicket = sc.getCarguearchivosDatos();
                int index = nroTicket.indexOf(Constantes.INC);
                if(index > 0){
                    int finalSubstring = index + 15;
                    cast.setCarguearchivosDatos(nroTicket.substring(index, finalSubstring));
                }
                cast.setCarguearchivosNombreDatos(sc.getCarguearchivosNombreDatos());
                cast.setCarguearchivosFecha(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(sc.getCarguearchivosFecha()));
                cast.setCarguearchivosEstado(sc.getCarguearchivosEstado());
                listCargues.add(cast);
            }
        }
        return listCargues;
    }

    public OrganismosTransitoMigrunt obtenerAutoridadHQ(final String ruta, final String nombreArc, final String usuario) {
        OrganismosTransitoMigrunt autoridad = new OrganismosTransitoMigrunt();
        try {
            String nombreArchivo = nombreArc;
            //Expresión regular, que valida que elnombre del archivo cumpla con las condiciones
            Pattern expRegular = Pattern.compile(CargueArchivoCommon.EXPRESION_REGULAR_NOMBRE_ARCHIVO_PROPIETARIOS);
            //Se pasa el nombre del archivo por la expresión regular
            Matcher input = expRegular.matcher(nombreArchivo);
            //Se valdia que el nombre del archivo cumpla la expresión regular
            if (input.find()) {
                //Se guarda el grupo 1, de la empresión regular
                String idSecretaria = input.group(1);
                if (idSecretaria != null) {
                    byte[] archivo = desfirmaArchivo(ruta, nombreArc, usuario, idSecretaria);
                    StringTokenizer st;
                    st = new StringTokenizer(new String(archivo, Constantes.UTF8), "\n");
                    if (st == null) {
                        throw new RuntimeException("Archivo mal formado o vacio");
                    }
                    if (st.hasMoreElements()) {
                        String registro;
                        while (st.hasMoreTokens()) {
                            registro = (String) st.nextElement();
                            Long idOt = Long.parseLong(registro.substring(0, 8));
                            autoridad.setIdSecretaria(idOt);
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return autoridad;
    }
    
    public Carga castCargaPropietario(OrganismosTransitoMigrunt idOtAutoridad, final String idFolio) {
        Carga carga = new Carga();
        carga.setIdSecretaria(idOtAutoridad);
        carga.setFechaCarga(new Date());
        carga.setTipoRegistro(Integer.parseInt(Constantes.TIPO_REGISTRO_RNA));
        carga.setIdFolio(idFolio);
        carga.setCodEstado(0);
        carga.setIdBoletin(null);
        carga.setArchmigraIdentific(Long.parseLong(Constantes.ARCMIGRAIDENTIFIC));
        return carga;
    }

    public LogArchivoFolio castLogArchivFolioPropietario(final String nombreArchivo, final String idFolio) {
        LogArchivoFolio archivoFolio = new LogArchivoFolio();
        archivoFolio.setIdFolio(idFolio);
        archivoFolio.setNomArcOriginal(nombreArchivo);
        archivoFolio.setCodEstado(0);
        return archivoFolio;
    }

    public ConfArcCar castConfArcCarPropietario(final String rutaArchivo, final Long idOt, final Carga carga, final String nombreArchivo) {
        ConfArcCar confArcCar = new ConfArcCar();
        confArcCar.setCodEstandar(4);
        confArcCar.setNomArchivo(rutaArchivo);
        confArcCar.setCodEstado(0);
        confArcCar.setIdSecretaria(idOt);
        confArcCar.setTipoArchivo(1);
        confArcCar.setFecProceso(new Date());
        confArcCar.setDesError(null);
        confArcCar.setNumRegleidos(0);
        confArcCar.setCodBoletin(0);
        confArcCar.setCodCarga(carga);
        confArcCar.setCantError(0);
        confArcCar.setNomArcOriginal(nombreArchivo);
        confArcCar.setTipoRegistro(1);
        return confArcCar;
    }
}
