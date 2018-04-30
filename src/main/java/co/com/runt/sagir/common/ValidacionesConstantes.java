/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.common;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class ValidacionesConstantes {

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(ValidacionesConstantes.class.getSimpleName());

    /**
     * Metodo que valida si el codigo de Idautra existe o no en la base de datos
     *
     * @param idOt
     * @return
     */
    public Integer validaIdOt(final Long idOt) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("AutoridadTransitoHQ.findById")
                    .setParameter("idautoridad", idOt)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    /**
     * Metodo que valida si el codigo de Divipol existe o no en la base de datos
     *
     * @param idCiudad
     * @return
     */
    public Integer validaIdCiudad(final Long idCiudad) {
        try {
            return Integer.parseInt(entityManager.createNamedQuery("PaMunicpio.findByMunicpioDivipol")
                    .setParameter("municpioDivipol", idCiudad)
                    .getSingleResult().toString());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    /**
     * Función que permite castear un String a Date en el sigueinte formato:
     * 10082015 ddMMyyyy to dd/MM/yyyy
     *
     * @param fecha
     * @return
     */
    public String convertStringToDateFormatThreeString(final String fecha) {
        String dia = fecha.substring(0, 2);
        String mes = fecha.substring(2, 4);
        String anio = fecha.substring(4, 8);
        StringBuilder fechaF = new StringBuilder();
        fechaF.append(dia).append("/").append(mes).append("/").append(anio);
        return fechaF.toString();
    }

    /**
     * Función que sirve para castear un string a Date.
     *
     * @param fecha String que contiene la fecha.
     * @return Date
     */
    public static Date convertStringToDateBD(final String fecha) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
        Date date = null;
        try {
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return date;
    }

    /**
     * Función que permite castear un String a Date en el sigueinte formato:
     * 10082015 ddMMyyyy to dd/MM/yyyy
     *
     * @param fecha
     * @return
     */
    public String convertStringToDate(final String fecha) {
        String anio = fecha.substring(0, 4);
        String mes = fecha.substring(4, 6);
        String dia = fecha.substring(6, 8);
        StringBuilder fechaF = new StringBuilder();
        fechaF.append(dia).append("/").append(mes).append("/").append(anio);
        return fechaF.toString();
    }

    public String convertDatetoString(Date fecha) {
        Format formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(fecha);
    }
}
