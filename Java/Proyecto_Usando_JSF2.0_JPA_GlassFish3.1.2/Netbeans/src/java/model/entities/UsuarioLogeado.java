/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.entities.Local;

/**
 *
 * @author Matt
 */

public class UsuarioLogeado {

    public UsuarioLogeado() {
    }
    
    private String idusuario;
    private String nomusuario;
    private String nick;
    private String clave;
    private Local idlocal;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Local getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(Local idlocal) {
        this.idlocal = idlocal;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNomusuario() {
        return nomusuario;
    }

    public void setNomusuario(String nomusuario) {
        this.nomusuario = nomusuario;
    }
    
}
