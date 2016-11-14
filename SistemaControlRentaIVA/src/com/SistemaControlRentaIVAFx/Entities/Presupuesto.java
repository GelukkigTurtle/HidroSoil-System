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
@Table(name = "presupuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presupuesto.findAll", query = "SELECT p FROM Presupuesto p"),
    @NamedQuery(name = "Presupuesto.findByPreId", query = "SELECT p FROM Presupuesto p WHERE p.preId = :preId"),
    @NamedQuery(name = "Presupuesto.findByPreCombustible", query = "SELECT p FROM Presupuesto p WHERE p.preCombustible = :preCombustible"),
    @NamedQuery(name = "Presupuesto.findByPreMateriales", query = "SELECT p FROM Presupuesto p WHERE p.preMateriales = :preMateriales"),
    @NamedQuery(name = "Presupuesto.findByPreEquipo", query = "SELECT p FROM Presupuesto p WHERE p.preEquipo = :preEquipo"),
    @NamedQuery(name = "Presupuesto.findByPreManoObra", query = "SELECT p FROM Presupuesto p WHERE p.preManoObra = :preManoObra"),
    @NamedQuery(name = "Presupuesto.findByPreViaticos", query = "SELECT p FROM Presupuesto p WHERE p.preViaticos = :preViaticos"),
    @NamedQuery(name = "Presupuesto.findByPreTotalVenta", query = "SELECT p FROM Presupuesto p WHERE p.preTotalVenta = :preTotalVenta"),
    @NamedQuery(name = "Presupuesto.findByPreTotalCompra", query = "SELECT p FROM Presupuesto p WHERE p.preTotalCompra = :preTotalCompra"),
    @NamedQuery(name = "Presupuesto.findByPreGananciaNeta", query = "SELECT p FROM Presupuesto p WHERE p.preGananciaNeta = :preGananciaNeta"),
    @NamedQuery(name = "Presupuesto.findByPreUtilidadNeta", query = "SELECT p FROM Presupuesto p WHERE p.preUtilidadNeta = :preUtilidadNeta"),
    @NamedQuery(name = "Presupuesto.findByPreOpCredito", query = "SELECT p FROM Presupuesto p WHERE p.preOpCredito = :preOpCredito"),
    @NamedQuery(name = "Presupuesto.findByPreOpContado", query = "SELECT p FROM Presupuesto p WHERE p.preOpContado = :preOpContado"),
    @NamedQuery(name = "Presupuesto.findByPrePrima", query = "SELECT p FROM Presupuesto p WHERE p.prePrima = :prePrima"),
    @NamedQuery(name = "Presupuesto.findByPreNumCuotas", query = "SELECT p FROM Presupuesto p WHERE p.preNumCuotas = :preNumCuotas"),
    @NamedQuery(name = "Presupuesto.findByPreValCuota", query = "SELECT p FROM Presupuesto p WHERE p.preValCuota = :preValCuota"),
    @NamedQuery(name = "Presupuesto.findByPrePagoRenta", query = "SELECT p FROM Presupuesto p WHERE p.prePagoRenta = :prePagoRenta"),
    @NamedQuery(name = "Presupuesto.findByPrePagoIva", query = "SELECT p FROM Presupuesto p WHERE p.prePagoIva = :prePagoIva"),
    @NamedQuery(name = "Presupuesto.findByPrePagoCuenta", query = "SELECT p FROM Presupuesto p WHERE p.prePagoCuenta = :prePagoCuenta")})
public class Presupuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRE_ID")
    private Integer preId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRE_COMBUSTIBLE")
    private Double preCombustible;
    @Column(name = "PRE_MATERIALES")
    private Double preMateriales;
    @Column(name = "PRE_EQUIPO")
    private Double preEquipo;
    @Column(name = "PRE_MANO_OBRA")
    private Double preManoObra;
    @Column(name = "PRE_VIATICOS")
    private Double preViaticos;
    @Column(name = "PRE_TOTAL_VENTA")
    private Double preTotalVenta;
    @Column(name = "PRE_TOTAL_COMPRA")
    private Double preTotalCompra;
    @Column(name = "PRE_GANANCIA_NETA")
    private Double preGananciaNeta;
    @Column(name = "PRE_UTILIDAD_NETA")
    private Double preUtilidadNeta;
    @Column(name = "PRE_OP_CREDITO")
    private Boolean preOpCredito;
    @Column(name = "PRE_OP_CONTADO")
    private Boolean preOpContado;
    @Column(name = "PRE_PRIMA")
    private Double prePrima;
    @Column(name = "PRE_NUM_CUOTAS")
    private Integer preNumCuotas;
    @Column(name = "PRE_VAL_CUOTA")
    private Double preValCuota;
    @Column(name = "PRE_PAGO_RENTA")
    private Double prePagoRenta;
    @Column(name = "PRE_PAGO_IVA")
    private Double prePagoIva;
    @Column(name = "PRE_PAGO_CUENTA")
    private Double prePagoCuenta;
    @JoinColumn(name = "PROYECTO_PRO_ID", referencedColumnName = "PRO_ID")
    @ManyToOne(optional = false)
    private Proyecto proyectoProId;
    @JoinColumn(name = "IVA_IVA_ID", referencedColumnName = "IVA_ID")
    @ManyToOne(optional = false)
    private Iva ivaIvaId;

    public Presupuesto() {
    }

    public Presupuesto(Integer preId) {
        this.preId = preId;
    }

    public Integer getPreId() {
        return preId;
    }

    public void setPreId(Integer preId) {
        this.preId = preId;
    }

    public Double getPreCombustible() {
        return preCombustible;
    }

    public void setPreCombustible(Double preCombustible) {
        this.preCombustible = preCombustible;
    }

    public Double getPreMateriales() {
        return preMateriales;
    }

    public void setPreMateriales(Double preMateriales) {
        this.preMateriales = preMateriales;
    }

    public Double getPreEquipo() {
        return preEquipo;
    }

    public void setPreEquipo(Double preEquipo) {
        this.preEquipo = preEquipo;
    }

    public Double getPreManoObra() {
        return preManoObra;
    }

    public void setPreManoObra(Double preManoObra) {
        this.preManoObra = preManoObra;
    }

    public Double getPreViaticos() {
        return preViaticos;
    }

    public void setPreViaticos(Double preViaticos) {
        this.preViaticos = preViaticos;
    }

    public Double getPreTotalVenta() {
        return preTotalVenta;
    }

    public void setPreTotalVenta(Double preTotalVenta) {
        this.preTotalVenta = preTotalVenta;
    }

    public Double getPreTotalCompra() {
        return preTotalCompra;
    }

    public void setPreTotalCompra(Double preTotalCompra) {
        this.preTotalCompra = preTotalCompra;
    }

    public Double getPreGananciaNeta() {
        return preGananciaNeta;
    }

    public void setPreGananciaNeta(Double preGananciaNeta) {
        this.preGananciaNeta = preGananciaNeta;
    }

    public Double getPreUtilidadNeta() {
        return preUtilidadNeta;
    }

    public void setPreUtilidadNeta(Double preUtilidadNeta) {
        this.preUtilidadNeta = preUtilidadNeta;
    }

    public Boolean getPreOpCredito() {
        return preOpCredito;
    }

    public void setPreOpCredito(Boolean preOpCredito) {
        this.preOpCredito = preOpCredito;
    }

    public Boolean getPreOpContado() {
        return preOpContado;
    }

    public void setPreOpContado(Boolean preOpContado) {
        this.preOpContado = preOpContado;
    }

    public Double getPrePrima() {
        return prePrima;
    }

    public void setPrePrima(Double prePrima) {
        this.prePrima = prePrima;
    }

    public Integer getPreNumCuotas() {
        return preNumCuotas;
    }

    public void setPreNumCuotas(Integer preNumCuotas) {
        this.preNumCuotas = preNumCuotas;
    }

    public Double getPreValCuota() {
        return preValCuota;
    }

    public void setPreValCuota(Double preValCuota) {
        this.preValCuota = preValCuota;
    }

    public Double getPrePagoRenta() {
        return prePagoRenta;
    }

    public void setPrePagoRenta(Double prePagoRenta) {
        this.prePagoRenta = prePagoRenta;
    }

    public Double getPrePagoIva() {
        return prePagoIva;
    }

    public void setPrePagoIva(Double prePagoIva) {
        this.prePagoIva = prePagoIva;
    }

    public Double getPrePagoCuenta() {
        return prePagoCuenta;
    }

    public void setPrePagoCuenta(Double prePagoCuenta) {
        this.prePagoCuenta = prePagoCuenta;
    }

    public Proyecto getProyectoProId() {
        return proyectoProId;
    }

    public void setProyectoProId(Proyecto proyectoProId) {
        this.proyectoProId = proyectoProId;
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
        hash += (preId != null ? preId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Presupuesto)) {
            return false;
        }
        Presupuesto other = (Presupuesto) object;
        if ((this.preId == null && other.preId != null) || (this.preId != null && !this.preId.equals(other.preId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return proyectoProId.toString() ;
    }
    
}
