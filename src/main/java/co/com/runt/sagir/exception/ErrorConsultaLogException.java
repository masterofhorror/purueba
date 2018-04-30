package co.com.runt.sagir.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Camilo Alvarez Duran <camilo.alvarez at runt.com.co>
 */
public class ErrorConsultaLogException extends Exception {

    public ErrorConsultaLogException() {
    }

    public ErrorConsultaLogException(String message) {
        super(message);
    }

    public ErrorConsultaLogException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorConsultaLogException(Throwable cause) {
        super(cause);
    }

    public ErrorConsultaLogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getStackAsString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        getCause().printStackTrace(pw);
        try {
            return sw.toString().split("\n")[0];
        } catch (Exception ex) {
            return null;
        }
    }
}
