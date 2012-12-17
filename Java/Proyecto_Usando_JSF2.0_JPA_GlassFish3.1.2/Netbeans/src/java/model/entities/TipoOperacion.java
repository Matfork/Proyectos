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
@Table(name = "TIPO_OPERACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoOperacion.findAll", query = "SELECT t FROM TipoOperacion t"),
    @NamedQuery(name = "TipoOperacion.findByIdtipooperacion", query = "SELECT t FROM TipoOperacion t WHERE t.idtipooperacion = :idtipooperacion"),
    @NamedQuery(name = "TipoOperacion.findByTipooperacion", query = "SELECT t FROM TipoOperacion t WHERE t.tipooperacion = :tipooperacion")})
public class TipoOperacion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPOOPERACION")
    private BigDecimal idtipooperacion;
    @Size(max = 30)
    @Column(name = "TIPOOPERACION")
    private String tipooperacion;
    @OneToMany(mappedBy = "idtipooperacion")
    private Collection<Operacion> operacionCollection;

    public TipoOperacion() {
    }

    public TipoOperacion(BigDecimal idtipooperacion) {
        this.idtipooperacion = idtipooperacion;
    }

    public BigDecimal getIdtipooperacion() {
        return idtipooperacion;
    }

    public void setIdtipooperacion(BigDecimal idtipooperacion) {
        this.idtipooperacion = idtipooperacion;
    }

    public String getTipooperacion() {
        return tipooperacion;
    }

    public void setTipooperacion(String tipooperacion) {
        this.tipooperacion = tipooperacion;
    }

    @XmlTransient
    public Collection<Operacion> getOperacionCollection() {
        return operacionCollection;
    }

    public void setOperacionCollection(Collection<Operacion> operacionCollection) {
        this.operacionCollection = operacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipooperacion != null ? idtipooperacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoOperacion)) {
            return false;
        }
        TipoOperacion other = (TipoOperacion) object;
        if ((this.idtipooperacion == null && other.idtipooperacion != null) || (this.idtipooperacion != null && !this.idtipooperacion.equals(other.idtipooperacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + tipooperacion;
    }
    
}
