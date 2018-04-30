/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.utils;

import co.com.runt.clienteserviciosfirma.ClienteServiciosFirma;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaDTO;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaInParamDTO;
import co.com.runt.sagir.common.CommonPropietarioLogica;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.RegistrosArchivoDTO;
import co.com.runt.sagir.entities.AutoridadTransitoHQ;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class ArchivosUtil {

    public static final String VALIDAR_FIRMA = "http://weblogicInterno/ServiciosFirma/webresources/validacionFirma";

    private static final Logger LOG = Logger.getLogger(ArchivosUtil.class.getName());

    @EJB
    private ConstanteDAO constanteDAO;

    @Resource
    protected EJBContext context;

    public String descomprimirArchivo(String pathZip, String pathDescomprido) {
        String mensaje = null;
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(pathZip))) {
            File pathTmp = new File(pathDescomprido, "");
            if (!pathTmp.delete()) {
                pathTmp.delete();
            }
            pathTmp.mkdir();
            String nombreArchivo;
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                nombreArchivo = FilenameUtils.getName(ze.getName());
                File f = new File(pathTmp, nombreArchivo);
                FileOutputStream fos = new FileOutputStream(f);
                try {

                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }

                    ze = zis.getNextEntry();
                } catch (IOException e) {
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
            return pathTmp.getAbsolutePath();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivosUtil.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = "Error al descomprimir el archivo";
        } catch (IOException ex) {
            Logger.getLogger(ArchivosUtil.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = "Error al descomprimir el archivo";
        }
        return mensaje;
    }

    public byte[] desfirmaArchivo(final String ruta, final String nombreArchivo, final String usuario, final Long idSecretaria) {
        try {
            File dir = new File(ruta);
            byte[] data;
            data = convertirArchivo(dir);

            if (data == null) {
                LOG.log(Level.SEVERE, "Error en el archivo...");
            }
            try {
                byte[] contenido = validarCertificado(usuario, Base64.encodeBase64String(data), idSecretaria);
                if (contenido == null) {

                } else {
                    return contenido;
                }
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getLocalizedMessage());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
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
            LOG.log(Level.SEVERE, e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(ArchivosUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return bFile;
    }

    public byte[] validarCertificado(String usuario, String firma, Long idSecretaria) throws Exception {
        ClienteServiciosFirma cliente = new ClienteServiciosFirma(VALIDAR_FIRMA);
        String parametroUsuario = constanteDAO.getByGrupoNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.VALIDACION_USUARIO).getConstanteValor();
        ValidacionFirmaInParamDTO entrada = new ValidacionFirmaInParamDTO();
        AutoridadTransitoHQ organismo = constanteDAO.consultaOT(idSecretaria);
        String nitOrganismo = organismo.getEmpresaPersona().getPersona().getPersonaNrodocume();
        entrada.setCedulaUsuario(nitOrganismo);
        entrada.setDatosFirmados(firma);
        entrada.setParametro(parametroUsuario);
        ValidacionFirmaDTO respuesta = cliente.validarFirmaParam(entrada);
        if (respuesta == null) {
            throw new RegistroException("Error al validar la firma para el usuario: " + usuario);
        }
        return respuesta.getDatos();
    }

    public List<RegistrosArchivoDTO> datosArchivo(final byte[] datosArchivo) {
        List<RegistrosArchivoDTO> listDTO = new ArrayList<>();
        try {
            StringTokenizer st;
            st = new StringTokenizer(new String(datosArchivo, "UTF-8"), "\n");
            if (st == null) {
                throw new RuntimeException("Archivo mal formado o vacio");
            }
            if (st.hasMoreElements()) {
                try {
                    int linea = 0;
                    String registro;
                    while (st.hasMoreTokens()) {
                        RegistrosArchivoDTO cast = new RegistrosArchivoDTO();
                        linea++;
                        registro = (String) st.nextElement();
                        Integer numLineas = linea;
                        String datos = registro;
                        cast.setLinea(numLineas);
                        cast.setDato(datos);
                        listDTO.add(cast);
                    }

                } catch (Exception e) {
                    LOG.log(Level.SEVERE, e.getMessage());
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommonPropietarioLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDTO;
    }

    public boolean deleteFile(String delpath) {
        File file = new File(delpath);
        if (!file.isDirectory()) {
            LOG.log(Level.INFO, "1");
            file.delete();
        } else if (file.isDirectory()) {
            LOG.log(Level.INFO, "2");
            File[] fileList = file.listFiles();
            for (File delfile : fileList) {
                if (!delfile.isDirectory()) {
                    LOG.log(Level.INFO, "La ruta relativa = {0}", delfile.getPath());
                    LOG.log(Level.INFO, "Ruta absoluta = {0}", delfile.getAbsolutePath());
                    LOG.log(Level.INFO, "Nombre de archivo = {0}", delfile.getName());
                    delfile.delete();
                    LOG.log(Level.INFO, "Borrar el archivo de éxito");
                } else if (delfile.isDirectory()) {
                    deleteFile(delfile.getPath());
                }
            }
            file.delete();
        }
        return true;
    }
}
