/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;
import model.entities.Usuario;
import model.entities.UsuarioLogeado;

@ManagedBean
@SessionScoped
public class LoginBean  implements Serializable {
    
    @EJB
    private session.LoginDao loginDao;
    
    private String nick;
    private String clave;
    private UsuarioLogeado usuarioLogeado= new UsuarioLogeado();

    public UsuarioLogeado getUsuarioLogeado() {
        usuarioLogeado =  (UsuarioLogeado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(UsuarioLogeado usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;         
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
      
    public LoginBean() {
    }
    
    private Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuarios) {
        this.usuario = usuarios;
    }
        
    public String verificarLogin(){ 
        
        try{
            usuario = loginDao.p_verificarLogin(nick, clave);
           
            if (usuario != null){
                System.out.println(usuario.getIdusuario()+ " " + usuario.getNick() +  " " + usuario.getNomusuario() + " " + usuario.getIdlocal().getIdlocal()+  "  " + usuario.getIdlocal().getNomlocal());
               
                 this.usuarioLogeado.setIdlocal(usuario.getIdlocal());
                 this.usuarioLogeado.setNick(usuario.getNick());                 
                 this.usuarioLogeado.setIdusuario(usuario.getIdusuario());
                 this.usuarioLogeado.setNomusuario(usuario.getNomusuario());
                
                //Creamos la session vigente
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("sessionUsuario", usuarioLogeado);
                
                return "index";
            }else{
               FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Datos incorrectos:","El usuario o contraseña ingresados son incorrectos");
               FacesContext.getCurrentInstance().addMessage(null, msg);             
            }
	} catch(Exception e){
           System.out.println(e.getMessage());
           FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error Inesperado: ",e.getMessage());
           FacesContext.getCurrentInstance().addMessage(null, msg);             
	}
        
        return "login_1";
        
    }   
    
    
      public String irCerrarSession(){         
            //Destruimos la session
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "/login_1";        
    }    
    
        public String irAPrincipal(){         
            //Destruimos la session            
            return "/index";        
    } 
    

    
     //Utilidades
    private Map param(){
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getRequestParameterMap();
    }
}
