/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.MenuGrupo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class MenuGrupoDAO {

    private static final Logger LOGGER = Logger.getLogger(MenuGrupoDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<MenuGrupo> buscarMenusGrupo() {
        try {
            return entityManager.createNamedQuery("MenuGrupo.findActivos").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }

}
