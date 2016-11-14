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
@Table(name = "renta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Renta.findAll", query = "SELECT r FROM Renta r"),
    @NamedQuery(name = "Renta.findByRenId", query = "SELECT r FROM Renta r WHERE r.renId = :renId"),
    @NamedQuery(name = "Renta.findByRenTotal", query = "SELECT r FROM Renta r WHERE r.renTotal = :renTotal"),
    @NamedQuery(name = "Renta.findByRenFecha", query = "SELECT r FROM Renta r WHERE r.renFecha = :renFecha")})
public class Renta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REN_ID")
    private Integer renId;
    @Column(name = "REN_TOTAL")
    private String renTotal;
    @Column(name = "REN_FECHA")
    private String renFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rentaRenId")
    private List<Iva> ivaList;

    public Renta() {
    }

    public Renta(Integer renId) {
        this.renId = renId;
    }

    public Integer getRenId() {
        return renId;
    }

    public void setRenId(Integer renId) {
        this.renId = renId;
    }

    public String getRenTotal() {
        return renTotal;
    }

    public void setRenTotal(String renTotal) {
        this.renTotal = renTotal;
    }

    public String getRenFecha() {
        return renFecha;
    }

    public void setRenFecha(String renFecha) {
        this.renFecha = renFecha;
    }

    @XmlTransient
    public List<Iva> getIvaList() {
        return ivaList;
    }

    public void setIvaList(List<Iva> ivaList) {
        this.ivaList = ivaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (renId != null ? renId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Renta)) {
            return false;
        }
        Renta other = (Renta) object;
        if ((this.renId == null && other.renId != null) || (this.renId != null && !this.renId.equals(other.renId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return renFecha;
    }
    
}
