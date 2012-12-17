/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.entities.Cliente;
import model.entities.Movimiento;


@Stateless
public class ClienteProceduresFacade extends AbstractFacade<ClienteFacade> {
    @PersistenceContext(unitName = "Practica4_TM_BancoEureka2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    public ClienteProceduresFacade() {
        super(ClienteFacade.class);
    }
    
    
     public void p_insert_cliente(Cliente cl) 
     {
         
            Query query = em.createNativeQuery("{ CALL p_insert_cliente(?) }"); 
            
            query.setParameter(1, cl.getNombrecompleto()); 
            query.executeUpdate();
        
     }
    
}
