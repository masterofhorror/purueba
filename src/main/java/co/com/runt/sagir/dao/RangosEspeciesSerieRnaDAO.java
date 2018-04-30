/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.dto.BusquedaAsignacionRangosDTO;
import co.com.runt.sagir.entities.RangosEspeciesSerieRna;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class RangosEspeciesSerieRnaDAO {

    private static final Logger LOGGER = Logger.getLogger(RangosEspeciesSerieRnaDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    public Long buscarTotalPorFiltros(final BusquedaAsignacionRangosDTO filtro) {
        try {
            CriteriaQuery cq = createCriteria(filtro, true);

            return (Long) entityManager.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return 0L;
    }

    public List<RangosEspeciesSerieRna> buscarPorFiltros(final BusquedaAsignacionRangosDTO filtro) {
        try {
            CriteriaQuery cq = createCriteria(filtro, false);

            return entityManager.createQuery(cq).setFirstResult(filtro.getOffset()).setMaxResults(filtro.getLimit()).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }

    private CriteriaQuery createCriteria(final BusquedaAsignacionRangosDTO filtro, final boolean count) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery cq = cb.createQuery();
            Root<RangosEspeciesSerieRna> rangosEspeciesSerieRna = cq.from(RangosEspeciesSerieRna.class);

            List<Predicate> restrictions = new ArrayList<>();
            if (filtro.getPlaca() != null) {
                restrictions.add(cb.equal(rangosEspeciesSerieRna.get("id").get("placa"), filtro.getPlaca()));
            }
            if (filtro.getOrganismoTransito() != null) {
                restrictions.add(cb.equal(rangosEspeciesSerieRna.get("id").get("idSecretaria"), filtro.getOrganismoTransito().getIdSecretaria()));
            }
            if (filtro.getFecha() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = sdf.parse(filtro.getFecha());

                restrictions.add(cb.greaterThanOrEqualTo(rangosEspeciesSerieRna.<Date>get("fechaCargue"), fecha));
            }
            Predicate[] predicates = new Predicate[restrictions.size()];
            predicates = restrictions.toArray(predicates);

            if (count) {
                cq.select(cb.count(rangosEspeciesSerieRna));
            } else {
                cq.select(rangosEspeciesSerieRna);
            }

            cq.where(predicates);

            return cq;
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return null;
    }

}
