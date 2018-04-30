package co.com.runt.sagir.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * La Class FiltroCache.
 *
 * @author Hmoreno
 */
@WebFilter(displayName = "fltroCache", urlPatterns = "*")
public class FiltroCache implements Filter {

    /**
     * La constante ARCHIVOS_EXCLUIDOS.
     */
    private final static String[] ARCHIVOS_EXCLUIDOS = {"json", "pdf", "xls", "js", "png", "css", "jpg", "gif", "map",
        "woff"};

    /**
     * Init.
     *
     * @param filterConfig el filter config
     * @throws ServletException la excepcion servlet exception
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Do filter.
     *
     * @param request el request
     * @param response el response
     * @param chain el chain
     * @throws IOException Ocurrio una I/O exception.
     * @throws ServletException la excepcion servlet exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String uri = ((HttpServletRequest) request).getRequestURI();
        boolean cache = false;
        // validamos si la url termina en algunos de las extensiones excluidas
        for (String string : ARCHIVOS_EXCLUIDOS) {
            if (uri.toLowerCase().endsWith(string.toLowerCase())) {
                cache = true;
                break;
            }
        }
        if (!cache) {
            ((HttpServletResponse) response).setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            ((HttpServletResponse) response).setHeader("Pragma", "no-cache");
            ((HttpServletResponse) response).setHeader("Expires", "0");
        }
        chain.doFilter(request, response);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
