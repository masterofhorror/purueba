package co.com.runt.sagir.utils;

import co.com.runt.sagir.common.Constantes;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * La Class FiltroAutenticacion.
 *
 * @author Hmoreno
 */
@WebFilter(filterName = "FiltroAutenticacion", urlPatterns = {"*.html"})
public class FiltroAutenticacion implements Filter {

    /**
     * La constante LOGGER.
     */
    private final static Logger LOGGER = Logger.getLogger(FiltroAutenticacion.class.getName());

    /**
     * La constante PATH_HTML.
     */
    private static final String PATH_HTML = "index.html";

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // Do nothing override
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            String navegador = httpRequest.getHeader("USER-AGENT");

            if (navegador.contains("Chrome") || navegador.contains("Firefox")) {
                String path = httpRequest.getRequestURI();

                LOGGER.log(Level.INFO, "<<FiltroAutenticacion>> Ruta acceso: {0}", path);

                if (path.contains(PATH_HTML)) {
                    if (httpRequest.getSession().getAttribute(Constantes.SESSION_USUARIO) == null) {
                        LOGGER.log(Level.INFO, "Session terminada ...");
                        httpResponse.sendRedirect("#/login");
                    }
                }
            } else {
                Cookie[] cookies = httpRequest.getCookies();
                String cookie = null;
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if (c.getName().compareTo("ObSSOCookie") == 0) {
                            cookie = c.getValue();
                        }
                    }
                }
                if (cookie != null) {
                    generarError(httpRequest, httpResponse, true);
                } else {
                    generarError(httpRequest, httpResponse, false);
                }
            }
        } catch (Exception e) {
            httpResponse.sendRedirect("#/login");
        }
        chain.doFilter(httpRequest, response);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Do nothing override
    }

    private void generarError(HttpServletRequest httpRequest, HttpServletResponse httpResponse, final boolean esHq) throws IOException, ServletException {
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String page;
        if (esHq) {
            page = "content/error/hq.html";
        } else {
            page = "content/error/runt.html";
        }

        RequestDispatcher view = httpRequest.getRequestDispatcher(page);
        view.forward(httpRequest, httpResponse);
    }

}
