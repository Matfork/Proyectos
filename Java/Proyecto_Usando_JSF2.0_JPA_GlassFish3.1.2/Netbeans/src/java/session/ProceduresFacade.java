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
import model.entities.Movimiento;


@Stateless
public class ProceduresFacade extends AbstractFacade<MovimientoFacade> {
    @PersistenceContext(unitName = "Practica4_TM_BancoEureka2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    public ProceduresFacade() {
        super(MovimientoFacade.class);
    }
    
    
     public void p_registro_movimientos(Movimiento mov) 
     {
         //Integer idTipoMovimiento =  mov.getIdtipomovimiento().getIdtipomovimiento();
            Query query = em.createNativeQuery("{ CALL p_registro_movimientos(?,?,?,?,?) }"); 
            
            query.setParameter(1, mov.getCodcuenta()); 
            query.setParameter(2, mov.getIdcliente().getIdcliente()); 
            query.setParameter(3, mov.getIdtipomovimiento().getIdtipomovimiento()); 
            query.setParameter(4, mov.getIdlocal().getIdlocal()); 
            query.setParameter(5, mov.getCantmovimiento()); 
           
            query.executeUpdate();
        
     }
    
}
