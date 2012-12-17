package controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.entities.Cuenta;
import model.entities.Local;
import model.entities.Movimiento;
import model.entities.Operacion;

@ManagedBean
@SessionScoped
public class Reporte implements Serializable {

    
    public Reporte() {
    }
    
    @EJB
    private session.ReportesFacade reportesFacade;
     
    private Cuenta cuenta;
    private Local local;
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
 
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    ////Reporte de Movimientos
    private List<Movimiento> listaMovimiento=null;

    public List<Movimiento> getListaMovimiento() {
        return listaMovimiento;
    }
    
    public List<Movimiento> listaMovimiento() throws Exception {
       listaMovimiento = null;
       if (listaMovimiento == null) {
        this.listaMovimiento =  reportesFacade.p_reportes_movimiento_cuenta(cuenta.getCodcuenta() );
       //    this.listaMovimiento =  reportesFacade.p_reportes_movimiento_cuenta("C00001" );
       }
       return listaMovimiento;
    }
    
   
    //Reporte de Operaciones
    private List<Operacion> listaOperaciones=null;

    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

     public List<Operacion> listaOperaciones() throws Exception {
       listaOperaciones = null;
       if (listaOperaciones == null) {
        this.listaOperaciones =  reportesFacade.p_reportes_ope_fecha_local(local.getIdlocal(),fecha ) ;
       }
       return listaOperaciones;
    }
    
    

     public String regresar() 
     {
            listaMovimiento=null;
            listaOperaciones = null;
            return "/index";
     }

   
}
