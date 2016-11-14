/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author freddy ayala
 */
@Entity
@Table(name = "costos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Costos.findAll", query = "SELECT c FROM Costos c"),
    @NamedQuery(name = "Costos.findByCosId", query = "SELECT c FROM Costos c WHERE c.cosId = :cosId"),
    @NamedQuery(name = "Costos.findByCosNombre", query = "SELECT c FROM Costos c WHERE c.cosNombre = :cosNombre"),
    @NamedQuery(name = "Costos.findByCosValor", query = "SELECT c FROM Costos c WHERE c.cosValor = :cosValor"),
    @NamedQuery(name = "Costos.findByCosPagoIva", query = "SELECT c FROM Costos c WHERE c.cosPagoIva = :cosPagoIva"),
    @NamedQuery(name = "Costos.findByCosPagoCuenta", query = "SELECT c FROM Costos c WHERE c.cosPagoCuenta = :cosPagoCuenta"),
    @NamedQuery(name = "Costos.findByCosFecha", query = "SELECT c FROM Costos c WHERE c.cosFecha = :cosFecha")})
public class Costos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COS_ID")
    private Integer cosId;
    @Column(name = "COS_NOMBRE")
    private String cosNombre;
    @Lob
    @Column(name = "COS_DESCRIPCION")
    private String cosDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COS_VALOR")
    private Double cosValor;
    @Column(name = "COS_PAGO_IVA")
    private Double cosPagoIva;
    @Column(name = "COS_PAGO_CUENTA")
    private Double cosPagoCuenta;
    @Column(name = "COS_FECHA")
    private String cosFecha;
    @JoinColumn(name = "IVA_IVA_ID", referencedColumnName = "IVA_ID")
    @ManyToOne(optional = false)
    private Iva ivaIvaId;

    public Costos() {
    }

    public Costos(Integer cosId) {
        this.cosId = cosId;
    }

    public Integer getCosId() {
        return cosId;
    }

    public void setCosId(Integer cosId) {
        this.cosId = cosId;
    }

    public String getCosNombre() {
        return cosNombre;
    }

    public void setCosNombre(String cosNombre) {
        this.cosNombre = cosNombre;
    }

    public String getCosDescripcion() {
        return cosDescripcion;
    }

    public void setCosDescripcion(String cosDescripcion) {
        this.cosDescripcion = cosDescripcion;
    }

    public Double getCosValor() {
        return cosValor;
    }

    public void setCosValor(Double cosValor) {
        this.cosValor = cosValor;
    }

    public Double getCosPagoIva() {
        return cosPagoIva;
    }

    public void setCosPagoIva(Double cosPagoIva) {
        this.cosPagoIva = cosPagoIva;
    }

    public Double getCosPagoCuenta() {
        return cosPagoCuenta;
    }

    public void setCosPagoCuenta(Double cosPagoCuenta) {
        this.cosPagoCuenta = cosPagoCuenta;
    }

    public String getCosFecha() {
        return cosFecha;
    }

    public void setCosFecha(String cosFecha) {
        this.cosFecha = cosFecha;
    }

    public Iva getIvaIvaId() {
        return ivaIvaId;
    }

    public void setIvaIvaId(Iva ivaIvaId) {
        this.ivaIvaId = ivaIvaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cosId != null ? cosId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Costos)) {
            return false;
        }
        Costos other = (Costos) object;
        if ((this.cosId == null && other.cosId != null) || (this.cosId != null && !this.cosId.equals(other.cosId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.SistemaControlRentaIVAFx.Entities.Costos[ cosId=" + cosId + " ]";
    }
    
}
