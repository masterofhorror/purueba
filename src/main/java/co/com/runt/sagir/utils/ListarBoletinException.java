/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author dsalamanca
 */
public class ListarBoletinException extends Exception{
    
    /**
     *
     * @param message
     */
    public ListarBoletinException(String message) {
        super(message);
    }

    /**
     *
     * @param throwable
     */
    public ListarBoletinException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param message
     * @param throwable
     */
    public ListarBoletinException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     *
     * @return
     */
    public String getStackAsString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        getCause().printStackTrace(pw);
        try {
            return sw.toString().length() > 3000 ? sw.toString().substring(0, 3000) : sw.toString();
        } catch (Exception ex) {
            return "Eror generando ListarBoletinException";
        }
    }
    
}
