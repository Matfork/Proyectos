/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import controllers.LoginBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.entities.Local;
import model.entities.Usuario;
import oracle.jdbc.OracleTypes;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.queries.DataReadQuery;
import org.eclipse.persistence.queries.StoredProcedureCall;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;
/**
 *
 * @author Matt
 */
 

@Stateless
public class LoginDao extends AbstractFacade<LoginBean> {
     @PersistenceContext(unitName = "Practica4_TM_BancoEureka2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public LoginDao() {
        super(LoginBean.class);
    }
    

    public Usuario p_verificarLogin(String usuario, String clave) 
    {
        Usuario u = null;
        Local l = null;
        

            JpaEntityManager jpaEntityManager = JpaHelper.getEntityManager(em);
            Session session = jpaEntityManager.getActiveSession();
            DataReadQuery queryRead = new DataReadQuery();  
            
            
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("p_verificarLogin");              
            call.addNamedArgument("p_nick", "p1");                  
            call.addNamedArgument("p_contrasenia", "p2");           
            call.addNamedOutputArgument("p_recordset", "resultados_cursor", OracleTypes.CURSOR);  
                                                                                           
                                                                                           
            
            queryRead.setCall(call);
            queryRead.addArgument("p1", String.class);              
            queryRead.addArgument("p2", String.class);

            
            List<Object> queryArgs = new ArrayList<Object>();  
            queryArgs.add(usuario);  
            queryArgs.add(clave); 
            queryRead.bindAllParameters();  

           
            
           
            List rs = (List) session.executeQuery(queryRead, queryArgs);  
            List<DatabaseRecord> lista = new ArrayList<DatabaseRecord>();  
            
              for(int i=0; i<rs.size(); i++){                
                DatabaseRecord record = (DatabaseRecord) rs.get(i);
                
                  lista = (List<DatabaseRecord>) record.get("resultados_cursor");
                
                  for (DatabaseRecord dr : lista) {
                       String codigo = (String)dr.get("IDUSUARIO");
                       String nombre = (String)dr.get("NOMUSUARIO");
                       String nick = (String)dr.get("NICK");
                       BigDecimal idLocal = (BigDecimal)dr.get("IDLOCAL");
                       String nomLocal = (String)dr.get("NOMLOCAL");
                       
                    
                      u=new Usuario();
                      l=new Local();
                      
                      l.setIdlocal(idLocal);
                      l.setNomlocal(nomLocal); 
                      
                      u.setIdusuario(codigo);
                      u.setNomusuario(nombre);
                      u.setNick(nick);
                      u.setIdlocal(l);
                  }
              }
           
         
        
           return u;
    }
 
}
