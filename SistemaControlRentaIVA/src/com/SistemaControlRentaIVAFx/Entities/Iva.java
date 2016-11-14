/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author freddy ayala
 */
@Entity
@Table(name = "iva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Iva.findAll", query = "SELECT i FROM Iva i"),
    @NamedQuery(name = "Iva.findByIvaId", query = "SELECT i FROM Iva i WHERE i.ivaId = :ivaId"),
    @NamedQuery(name = "Iva.findByIvaTotal", query = "SELECT i FROM Iva i WHERE i.ivaTotal = :ivaTotal"),
    @NamedQuery(name = "Iva.findByIvaFecha", query = "SELECT i FROM Iva i WHERE i.ivaFecha = :ivaFecha"),
    @NamedQuery(name = "Iva.findByIvaPagoCuenta", query = "SELECT i FROM Iva i WHERE i.ivaPagoCuenta = :ivaPagoCuenta")})
public class Iva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IVA_ID")
    private Integer ivaId;
    @Column(name = "IVA_TOTAL")
    private String ivaTotal;
    @Column(name = "IVA_FECHA")
    private String ivaFecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "IVA_PAGO_CUENTA")
    private Double ivaPagoCuenta;
    @JoinColumn(name = "RENTA_REN_ID", referencedColumnName = "REN_ID")
    @ManyToOne(optional = false)
    private Renta rentaRenId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ivaIvaId")
    private List<Presupuesto> presupuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ivaIvaId")
    private List<Costos> costosList;

    public Iva() {
    }

    public Iva(Integer ivaId) {
        this.ivaId = ivaId;
    }

    public Integer getIvaId() {
        return ivaId;
    }

    public void setIvaId(Integer ivaId) {
        this.ivaId = ivaId;
    }

    public String getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(String ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public String getIvaFecha() {
        return ivaFecha;
    }

    public void setIvaFecha(String ivaFecha) {
        this.ivaFecha = ivaFecha;
    }

    public Double getIvaPagoCuenta() {
        return ivaPagoCuenta;
    }

    public void setIvaPagoCuenta(Double ivaPagoCuenta) {
        this.ivaPagoCuenta = ivaPagoCuenta;
    }

    public Renta getRentaRenId() {
        return rentaRenId;
    }

    public void setRentaRenId(Renta rentaRenId) {
        this.rentaRenId = rentaRenId;
    }

    @XmlTransient
    public List<Presupuesto> getPresupuestoList() {
        return presupuestoList;
    }

    public void setPresupuestoList(List<Presupuesto> presupuestoList) {
        this.presupuestoList = presupuestoList;
    }

    @XmlTransient
    public List<Costos> getCostosList() {
        return costosList;
    }

    public void setCostosList(List<Costos> costosList) {
        this.costosList = costosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivaId != null ? ivaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iva)) {
            return false;
        }
        Iva other = (Iva) object;
        if ((this.ivaId == null && other.ivaId != null) || (this.ivaId != null && !this.ivaId.equals(other.ivaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ivaFecha;
    }
    
}
