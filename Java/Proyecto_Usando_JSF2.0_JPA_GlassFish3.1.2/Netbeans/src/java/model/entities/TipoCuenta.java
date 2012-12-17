/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Matt
 */
@Entity
@Table(name = "TIPO_CUENTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCuenta.findAll", query = "SELECT t FROM TipoCuenta t"),
    @NamedQuery(name = "TipoCuenta.findByIdtipocuenta", query = "SELECT t FROM TipoCuenta t WHERE t.idtipocuenta = :idtipocuenta"),
    @NamedQuery(name = "TipoCuenta.findByTipocuenta", query = "SELECT t FROM TipoCuenta t WHERE t.tipocuenta = :tipocuenta")})
public class TipoCuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPOCUENTA")
    private BigDecimal idtipocuenta;
    @Size(max = 20)
    @Column(name = "TIPOCUENTA")
    private String tipocuenta;
    @OneToMany(mappedBy = "idtipocuenta")
    private Collection<Cuenta> cuentaCollection;

    public TipoCuenta() {
    }

    public TipoCuenta(BigDecimal idtipocuenta) {
        this.idtipocuenta = idtipocuenta;
    }

    public BigDecimal getIdtipocuenta() {
        return idtipocuenta;
    }

    public void setIdtipocuenta(BigDecimal idtipocuenta) {
        this.idtipocuenta = idtipocuenta;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    @XmlTransient
    public Collection<Cuenta> getCuentaCollection() {
        return cuentaCollection;
    }

    public void setCuentaCollection(Collection<Cuenta> cuentaCollection) {
        this.cuentaCollection = cuentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocuenta != null ? idtipocuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCuenta)) {
            return false;
        }
        TipoCuenta other = (TipoCuenta) object;
        if ((this.idtipocuenta == null && other.idtipocuenta != null) || (this.idtipocuenta != null && !this.idtipocuenta.equals(other.idtipocuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + tipocuenta;
    }
    
}
