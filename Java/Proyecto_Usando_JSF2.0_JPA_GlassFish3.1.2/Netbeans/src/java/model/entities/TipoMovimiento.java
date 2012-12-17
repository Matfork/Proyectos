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
@Table(name = "TIPO_MOVIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMovimiento.findAll", query = "SELECT t FROM TipoMovimiento t"),
    @NamedQuery(name = "TipoMovimiento.findByIdtipomovimiento", query = "SELECT t FROM TipoMovimiento t WHERE t.idtipomovimiento = :idtipomovimiento"),
    @NamedQuery(name = "TipoMovimiento.findByTipomovimiento", query = "SELECT t FROM TipoMovimiento t WHERE t.tipomovimiento = :tipomovimiento")})
public class TipoMovimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPOMOVIMIENTO")
    private BigDecimal idtipomovimiento;
    @Size(max = 30)
    @Column(name = "TIPOMOVIMIENTO")
    private String tipomovimiento;
    @OneToMany(mappedBy = "idtipomovimiento")
    private Collection<Movimiento> movimientoCollection;

    public TipoMovimiento() {
    }

    public TipoMovimiento(BigDecimal idtipomovimiento) {
        this.idtipomovimiento = idtipomovimiento;
    }

    public BigDecimal getIdtipomovimiento() {
        return idtipomovimiento;
    }

    public void setIdtipomovimiento(BigDecimal idtipomovimiento) {
        this.idtipomovimiento = idtipomovimiento;
    }

    public String getTipomovimiento() {
        return tipomovimiento;
    }

    public void setTipomovimiento(String tipomovimiento) {
        this.tipomovimiento = tipomovimiento;
    }

    @XmlTransient
    public Collection<Movimiento> getMovimientoCollection() {
        return movimientoCollection;
    }

    public void setMovimientoCollection(Collection<Movimiento> movimientoCollection) {
        this.movimientoCollection = movimientoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipomovimiento != null ? idtipomovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMovimiento)) {
            return false;
        }
        TipoMovimiento other = (TipoMovimiento) object;
        if ((this.idtipomovimiento == null && other.idtipomovimiento != null) || (this.idtipomovimiento != null && !this.idtipomovimiento.equals(other.idtipomovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + tipomovimiento;
    }
    
}
