package co.com.runt.sagir.utils;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * La Class UtilsCache.
 *
 * @author  Hmoreno
 */
public class UtilsCache {

	/**
	 * Manejador del control de cache, permite definir un cache control para servicios en un tiempo definido Metodo que
	 * permite:.
	 *
	 * @param respuesta            Objeto que debe devolver el metodos o servicio al que se le aplique
	 * @param tiempo            tiempo que durara el cache para ese servicio en milisegundos, se toma de UtilCacheConstantes
	 * @param etagValue            eTag para el cache control
	 * @param requestRest            Request javax.ws.rs.core.Request
	 * @return objeto Creado el 13/02/2017 Return: Response
	 */
	public static Response colocarCache(Object respuesta, int tiempo, String etagValue, Request requestRest) {
		CacheControl cc = new CacheControl();
		cc.setMaxAge(tiempo);
		cc.setPrivate(true);

		EntityTag etag = new EntityTag(etagValue);
		ResponseBuilder builder = requestRest.evaluatePreconditions(etag);

		// cached resource did change -> serve updated content
		if (builder == null) {
			builder = Response.ok(respuesta);
			builder.tag(etag);
		}

		builder.cacheControl(cc);
		return builder.build();
	}

}
