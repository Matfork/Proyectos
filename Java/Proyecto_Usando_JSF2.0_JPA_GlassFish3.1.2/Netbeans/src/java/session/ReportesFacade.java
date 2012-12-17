/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;
import controllers.Reporte;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.entities.*;
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
public class ReportesFacade extends AbstractFacade<Reporte> {
    @PersistenceContext(unitName = "Practica4_TM_BancoEureka2PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ReportesFacade() {
        super(Reporte.class);
    }


     public List<Movimiento> p_reportes_movimiento_cuenta(String codCuenta) 
     {
        try{
           List<Movimiento> listaMovimiento = new ArrayList<Movimiento>();
           Movimiento mov = null;
        
            //DECLARACION DE INSTANCIAS NECESARIAS PARA EL USO DE ECLIPSE.LINK
            JpaEntityManager jpaEntityManager = JpaHelper.getEntityManager(em);
            Session session = jpaEntityManager.getActiveSession();
            DataReadQuery queryRead = new DataReadQuery();  
            
            //DECLARAMOS EL NOMBRE DEL PROCEDURE Y ESPECIFICAMOS SUS CAMPOS
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("p_reportes_movimiento_cuenta");              
            call.addNamedArgument("p_codCuenta", "p1");               
            call.addNamedOutputArgument("p_recordset", "resultados_cursor", OracleTypes.CURSOR); 
            
            queryRead.setCall(call);
            queryRead.addArgument("p1", String.class);   
            
            List<Object> queryArgs = new ArrayList<Object>();  
            queryArgs.add(codCuenta);              
            queryRead.bindAllParameters();  

            List rs = (List) session.executeQuery(queryRead, queryArgs);  //Ejecutamos el procedure, se devuelve en una lista la informacion
            List<DatabaseRecord> lista = new ArrayList<DatabaseRecord>();  
            
              for(int i=0; i<rs.size(); i++){                
                DatabaseRecord record = (DatabaseRecord) rs.get(i);
                
                  lista = (List<DatabaseRecord>) record.get("resultados_cursor");
                
                  for (DatabaseRecord dr : lista) {                       
                      mov=new Movimiento();
                      
                      Local l = new Local();
                      l.setIdlocal((BigDecimal)dr.get("IDLOCAL"));
                      l.setNomlocal((String)dr.get("NOMLOCAL"));
                      
                      Cliente cl = new Cliente();
                      cl.setIdcliente((String)dr.get("IDCLIENTE"));
                      cl.setNombrecompleto((String)dr.get("NOMBRECOMPLETO"));
                      
                      TipoMovimiento tm = new TipoMovimiento();
                      tm.setIdtipomovimiento((BigDecimal) dr.get("IDTIPOMOVIMIENTO"));
                      tm.setTipomovimiento((String)dr.get("TIPOMOV"));
                      
                      mov.setIdcliente(cl);
                      mov.setIdlocal(l);
                      mov.setIdtipomovimiento(tm);
                      
                      mov.setNummovimiento( ((BigDecimal)dr.get("NUMMOV")).toBigInteger() );
                      mov.setIdmovimiento((BigDecimal)dr.get("IDMOVIMIENTO"));
                      mov.setCantmovimiento((BigDecimal)dr.get("CANTMOVIMIENTO"));
                      mov.setCodcuenta((String)dr.get("CODCUENTA"));
                      mov.setFechamovimiento((Date)dr.get("FECHAMOVIMIENTO"));
                      
                      listaMovimiento.add(mov);
                  }
               
              }
          return  listaMovimiento;   
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
     }
     
     
     public List<Operacion> p_reportes_ope_fecha_local(BigDecimal idLocal, Date fechaBusqueda) 
     {
        try{
             try{
           List<Operacion> listaOperacion = new ArrayList<Operacion>();
           Operacion op = null;
        
            //DECLARACION DE INSTANCIAS NECESARIAS PARA EL USO DE ECLIPSE.LINK
            JpaEntityManager jpaEntityManager = JpaHelper.getEntityManager(em);
            Session session = jpaEntityManager.getActiveSession();
            DataReadQuery queryRead = new DataReadQuery();  
            
            //DECLARAMOS EL NOMBRE DEL PROCEDURE Y ESPECIFICAMOS SUS CAMPOS
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("p_reportes_ope_fecha_local");              
            call.addNamedArgument("p_fechaOperacion", "p1");               
            call.addNamedArgument("p_idLocal", "p2");               
            call.addNamedOutputArgument("p_recordset", "resultados_cursor", OracleTypes.CURSOR); 
            
            queryRead.setCall(call);
            queryRead.addArgument("p1", Date.class);   
            queryRead.addArgument("p2", BigDecimal.class);   
            
            List<Object> queryArgs = new ArrayList<Object>();  
            queryArgs.add(fechaBusqueda);
            queryArgs.add(idLocal);    
            queryRead.bindAllParameters();  

              Object rs =  session.executeQuery(queryRead, queryArgs); 
                // Treating the results  

                Vector<DatabaseRecord> vResultado = new Vector<DatabaseRecord>(); //es necesario el Vector, ya que asi lo trabaja el EclipseLink

                Object o = new  Object();
                Enumeration<DatabaseRecord> records = ((Vector<DatabaseRecord>) rs).elements();  
                while (records.hasMoreElements()) {  
                        DatabaseRecord record = records.nextElement();  
                        vResultado = (Vector<DatabaseRecord>) record.get("resultados_cursor");  
                }  

                List<DatabaseRecord> listaFuera = new ArrayList<DatabaseRecord>();  
                        listaFuera.addAll(vResultado);  

                for (DatabaseRecord dr : listaFuera) {
                      Local l = new Local();
                      TipoOperacion to = new TipoOperacion();
                      op = new Operacion();
                      
                      l.setIdlocal((BigDecimal)dr.get("CODLOCAL"));
                      l.setNomlocal((String) dr.get("NOMLOCAL")); 
                      
                      to.setIdtipooperacion((BigDecimal)dr.get("CODTIPO"));
                      to.setTipooperacion((String) dr.get("TIOP"));
                      
                      op.setIdoperacion((BigDecimal)dr.get("IDOP"));                      
                      op.setIdusuario( new Usuario((String)dr.get("IDUS")));
                      op.setCodcuenta((String)dr.get("CODCUENTA"));
                      op.setFechaoperacion((Date)dr.get("FECHA"));
                      
                      op.setIdtipooperacion(to);
                      op.setIdlocal(l);
                      
                      listaOperacion.add(op);
                } 
          return  listaOperacion;   
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        }catch(Exception e){
            return null;
        }
     }
    
}
