/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.entities.TipoOperacion;

/**
 *
 * @author Matt
 */
@Stateless
public class TipoOperacionFacade extends AbstractFacade<TipoOperacion> {
    @PersistenceContext(unitName = "Practica4_TM_BancoEureka2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoOperacionFacade() {
        super(TipoOperacion.class);
    }
    
}
