/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "MOVIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimiento.findAll", query = "SELECT m FROM Movimiento m"),
    @NamedQuery(name = "Movimiento.findByNummovimiento", query = "SELECT m FROM Movimiento m WHERE m.nummovimiento = :nummovimiento"),
    @NamedQuery(name = "Movimiento.findByFechamovimiento", query = "SELECT m FROM Movimiento m WHERE m.fechamovimiento = :fechamovimiento"),
    @NamedQuery(name = "Movimiento.findByCantmovimiento", query = "SELECT m FROM Movimiento m WHERE m.cantmovimiento = :cantmovimiento"),
    @NamedQuery(name = "Movimiento.findByIdmovimiento", query = "SELECT m FROM Movimiento m WHERE m.idmovimiento = :idmovimiento"),
    @NamedQuery(name = "Movimiento.findByCodcuenta", query = "SELECT m FROM Movimiento m WHERE m.codcuenta = :codcuenta")})
public class Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "NUMMOVIMIENTO")
    private BigInteger nummovimiento;
    @Column(name = "FECHAMOVIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamovimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANTMOVIMIENTO")
    private BigDecimal cantmovimiento;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMOVIMIENTO")
    private BigDecimal idmovimiento;
    @Size(max = 6)
    @Column(name = "CODCUENTA")
    private String codcuenta;
    @JoinColumn(name = "IDTIPOMOVIMIENTO", referencedColumnName = "IDTIPOMOVIMIENTO")
    @ManyToOne
    private TipoMovimiento idtipomovimiento;
    @JoinColumn(name = "IDLOCAL", referencedColumnName = "IDLOCAL")
    @ManyToOne
    private Local idlocal;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")
    @ManyToOne
    private Cliente idcliente;

    public Movimiento() {
    }

    public Movimiento(BigDecimal idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public BigInteger getNummovimiento() {
        return nummovimiento;
    }

    public void setNummovimiento(BigInteger nummovimiento) {
        this.nummovimiento = nummovimiento;
    }

    public Date getFechamovimiento() {
        return fechamovimiento;
    }

    public void setFechamovimiento(Date fechamovimiento) {
        this.fechamovimiento = fechamovimiento;
    }

    public BigDecimal getCantmovimiento() {
        return cantmovimiento;
    }

    public void setCantmovimiento(BigDecimal cantmovimiento) {
        this.cantmovimiento = cantmovimiento;
    }

    public BigDecimal getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(BigDecimal idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public String getCodcuenta() {
        return codcuenta;
    }

    public void setCodcuenta(String codcuenta) {
        this.codcuenta = codcuenta;
    }

    public TipoMovimiento getIdtipomovimiento() {
        return idtipomovimiento;
    }

    public void setIdtipomovimiento(TipoMovimiento idtipomovimiento) {
        this.idtipomovimiento = idtipomovimiento;
    }

    public Local getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(Local idlocal) {
        this.idlocal = idlocal;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmovimiento != null ? idmovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        if ((this.idmovimiento == null && other.idmovimiento != null) || (this.idmovimiento != null && !this.idmovimiento.equals(other.idmovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.entities.Movimiento[ idmovimiento=" + idmovimiento + " ]";
    }
    
}
