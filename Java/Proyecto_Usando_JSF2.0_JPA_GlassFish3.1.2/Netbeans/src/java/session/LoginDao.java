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
 
//mi fuente para cursores -> http://ronaldoblanc.blogspot.com.br/2012/05/jpa-eclipselink-and-complex-parameters.html

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
    
    //DEBEMOS DE IMPORTAR LAS LIB DE ECLIPSELINK QUE VIENE POR DEFECTO CON EL GLASSFISH
    public Usuario p_verificarLogin(String usuario, String clave) 
    {
        Usuario u = null;
        Local l = null;
        
            //DECLARACION DE INSTANCIAS NECESARIAS PARA EL USO DE ECLIPSE.LINK
            JpaEntityManager jpaEntityManager = JpaHelper.getEntityManager(em);
            Session session = jpaEntityManager.getActiveSession();
            DataReadQuery queryRead = new DataReadQuery();  
            
            //DECLARAMOS EL NOMBRE DEL PROCEDURE Y ESPECIFICAMOS SUS CAMPOS
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("p_verificarLogin");              //Nombre del Procedure
            call.addNamedArgument("p_nick", "p1");                  //el primer argumento es el el nombre del primer parametro que se declaro en el procedure
            call.addNamedArgument("p_contrasenia", "p2");           //el primer argumento es el el nombre del segundo parametro que se declaro en el procedure
            call.addNamedOutputArgument("p_recordset", "resultados_cursor", OracleTypes.CURSOR);  //primer argumento -> es el nombre del parametro de cursor dentro del procedure
                                                                                           //segundo argumento -> nombre de la variable que usaremos para ingresar a los valores devueltos
                                                                                           //Para el OracleTypes se debe de importar la lib ojdb6 
            // DEFINIMOS EL TIPO DE DATOS DE LOS ARGUMENTOS
            queryRead.setCall(call);
            queryRead.addArgument("p1", String.class);              //aqui se define el tipo de datos que se pasara al procedure, tener en cuenta que el alias es el mismo que los declarados lineas arriba
            queryRead.addArgument("p2", String.class);

            // AGREGANDO ARGUMENTOS
            List<Object> queryArgs = new ArrayList<Object>();  
            queryArgs.add(usuario);  
            queryArgs.add(clave); 
            queryRead.bindAllParameters();  

           // EJECUCION Y RETRIBUCION DE DATOS
            
           // PRIMERA FORMA MEDIENTE EL USO DE LISTAS, IMPLICA USAR DOS FOR
            List rs = (List) session.executeQuery(queryRead, queryArgs);  //Ejecutamos el procedure, se devuelve en una lista la informacion
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
                       
                    // System.out.println("SE OBTUVO: " + codigo + " " + nombre + "  " + nick +  " "+ idLocal );                      
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
           
         
            // SEGUNDA FORMA MEDIENTE EL USO DE VECTORES Y LISTAS
     /*      
               Object rs =  session.executeQuery(queryRead, queryArgs);  //Ejecutamos el procedure, se devuelve en object la informacion
                // Treating the results  

                Vector<DatabaseRecord> vResultado = new Vector<DatabaseRecord>(); //es necesario el Vector, ya que asi lo trabaja el EclipseLink

                Object o = new  Object();
                Enumeration<DatabaseRecord> records = ((Vector<DatabaseRecord>) rs).elements();  
                while (records.hasMoreElements()) {  
                        DatabaseRecord record = records.nextElement();  
                        // retorno = (String) record.get("NOMBRE_DE_VARIABLE_OUT");   //si hubiera otro out parameter, este se capturaria defrente ya que no tendira necesidad de un vector
                                                                                    // siempre y cuando lo que se devuelva sea un valor puntual y no un cursor
                        vResultado = (Vector<DatabaseRecord>) record.get("resultados_cursor");  
                }  

                List<DatabaseRecord> listaFuera = new ArrayList<DatabaseRecord>();  
                        listaFuera.addAll(vResultado);  

                for (DatabaseRecord dr : listaFuera) {
                      u=new Usuario();
                      l=new Local();
                      
                      l.setIdlocal((BigDecimal)dr.get("IDLOCAL"));
                      l.setNomlocal((String) dr.get("NOMLOCAL")); 
                      
                      u.setIdusuario((String)dr.get("IDUSUARIO"));
                      u.setNomusuario((String)dr.get("NOMUSUARIO"));
                      u.setNick((String)dr.get("NICK"));
                      u.setIdlocal(l);

                      System.out.println("SE OBTUVO: " + dr.get("IDUSUARIO") + " " + dr.get("IDLOCAL") + "  " + dr.get("NOMUSUARIO") +  " "+ dr.get("NICK") );
                } 
          */   
           return u;
    }
 
}
