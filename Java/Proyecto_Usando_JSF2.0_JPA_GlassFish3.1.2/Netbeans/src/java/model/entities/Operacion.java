/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Matt
 */
@Entity
@Table(name = "OPERACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operacion.findAll", query = "SELECT o FROM Operacion o"),
    @NamedQuery(name = "Operacion.findByIdoperacion", query = "SELECT o FROM Operacion o WHERE o.idoperacion = :idoperacion"),
    @NamedQuery(name = "Operacion.findByFechaoperacion", query = "SELECT o FROM Operacion o WHERE o.fechaoperacion = :fechaoperacion"),
    @NamedQuery(name = "Operacion.findByCodcuenta", query = "SELECT o FROM Operacion o WHERE o.codcuenta = :codcuenta")})
public class Operacion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDOPERACION")
    private BigDecimal idoperacion;
    @Column(name = "FECHAOPERACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaoperacion;
    @Size(max = 6)
    @Column(name = "CODCUENTA")
    private String codcuenta;
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    @ManyToOne
    private Usuario idusuario;
    @JoinColumn(name = "IDTIPOOPERACION", referencedColumnName = "IDTIPOOPERACION")
    @ManyToOne
    private TipoOperacion idtipooperacion;
    @JoinColumn(name = "IDLOCAL", referencedColumnName = "IDLOCAL")
    @ManyToOne
    private Local idlocal;

    public Operacion() {
    }

    public Operacion(BigDecimal idoperacion) {
        this.idoperacion = idoperacion;
    }

    public BigDecimal getIdoperacion() {
        return idoperacion;
    }

    public void setIdoperacion(BigDecimal idoperacion) {
        this.idoperacion = idoperacion;
    }

    public Date getFechaoperacion() {
        return fechaoperacion;
    }

    public void setFechaoperacion(Date fechaoperacion) {
        this.fechaoperacion = fechaoperacion;
    }

    public String getCodcuenta() {
        return codcuenta;
    }

    public void setCodcuenta(String codcuenta) {
        this.codcuenta = codcuenta;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public TipoOperacion getIdtipooperacion() {
        return idtipooperacion;
    }

    public void setIdtipooperacion(TipoOperacion idtipooperacion) {
        this.idtipooperacion = idtipooperacion;
    }

    public Local getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(Local idlocal) {
        this.idlocal = idlocal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idoperacion != null ? idoperacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operacion)) {
            return false;
        }
        Operacion other = (Operacion) object;
        if ((this.idoperacion == null && other.idoperacion != null) || (this.idoperacion != null && !this.idoperacion.equals(other.idoperacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.entities.Operacion[ idoperacion=" + idoperacion + " ]";
    }
    
}
