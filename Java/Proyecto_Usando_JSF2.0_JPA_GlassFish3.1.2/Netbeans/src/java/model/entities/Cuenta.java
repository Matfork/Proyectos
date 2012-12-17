/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Matt
 */
@Entity
@Table(name = "CUENTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByIdcuenta", query = "SELECT c FROM Cuenta c WHERE c.idcuenta = :idcuenta"),
    @NamedQuery(name = "Cuenta.findBySaldo", query = "SELECT c FROM Cuenta c WHERE c.saldo = :saldo"),
    @NamedQuery(name = "Cuenta.findByClavecuenta", query = "SELECT c FROM Cuenta c WHERE c.clavecuenta = :clavecuenta"),
    @NamedQuery(name = "Cuenta.findByFlagestado", query = "SELECT c FROM Cuenta c WHERE c.flagestado = :flagestado"),
    @NamedQuery(name = "Cuenta.findByCodcuenta", query = "SELECT c FROM Cuenta c WHERE c.codcuenta = :codcuenta")})
public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCUENTA")
    private BigDecimal idcuenta;
    @Column(name = "SALDO")
    private BigDecimal saldo;
    @Column(name = "CLAVECUENTA")
    private Short clavecuenta;
    @Column(name = "FLAGESTADO")
    private Character flagestado;
    @Size(max = 6)
    @Column(name = "CODCUENTA")
    private String codcuenta;
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    @ManyToOne
    private Usuario idusuario;
    @JoinColumn(name = "IDTIPOCUENTA", referencedColumnName = "IDTIPOCUENTA")
    @ManyToOne
    private TipoCuenta idtipocuenta;
    @JoinColumn(name = "IDLOCAL", referencedColumnName = "IDLOCAL")
    @ManyToOne
    private Local idlocal;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")
    @ManyToOne
    private Cliente idcliente;

    public Cuenta() {
    }

    public Cuenta(BigDecimal idcuenta) {
        this.idcuenta = idcuenta;
    }

    public BigDecimal getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(BigDecimal idcuenta) {
        this.idcuenta = idcuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Short getClavecuenta() {
        return clavecuenta;
    }

    public void setClavecuenta(Short clavecuenta) {
        this.clavecuenta = clavecuenta;
    }

    public Character getFlagestado() {
        return flagestado;
    }

    public void setFlagestado(Character flagestado) {
        this.flagestado = flagestado;
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

    public TipoCuenta getIdtipocuenta() {
        return idtipocuenta;
    }

    public void setIdtipocuenta(TipoCuenta idtipocuenta) {
        this.idtipocuenta = idtipocuenta;
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
        hash += (idcuenta != null ? idcuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.idcuenta == null && other.idcuenta != null) || (this.idcuenta != null && !this.idcuenta.equals(other.idcuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" +codcuenta;
    }
    
}
