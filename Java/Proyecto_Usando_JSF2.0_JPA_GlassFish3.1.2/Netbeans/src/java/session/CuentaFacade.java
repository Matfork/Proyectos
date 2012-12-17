/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.entities.Cuenta;

/**
 *
 * @author Matt
 */
@Stateless
public class CuentaFacade extends AbstractFacade<Cuenta> {
    @PersistenceContext(unitName = "Practica4_TM_BancoEureka2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentaFacade() {
        super(Cuenta.class);
    }
    
    
      public void p_apertura_cuenta(Cuenta cu) 
     {
         System.out.println("Ingreso al procedure");
         
            Query query = em.createNativeQuery("{ CALL p_apertura_cuenta(?,?,?,?,?) }"); 
            
            query.setParameter(1, cu.getIdtipocuenta().getIdtipocuenta()); 
            query.setParameter(2, cu.getIdcliente().getIdcliente()); 
            query.setParameter(3, cu.getIdusuario().getIdlocal().getIdlocal()); 
            query.setParameter(4, cu.getIdusuario().getIdusuario()); 
            query.setParameter(5, cu.getSaldo()); 
            
            query.executeUpdate();
        
     }
      
       public void p_cierre_cuenta(Cuenta cl) 
     {
            Query query = em.createNativeQuery("{ CALL p_cierre_cuenta(?) }"); 
            
            query.setParameter(1, cl.getCodcuenta());              
            
            query.executeUpdate();
        
     }
}
