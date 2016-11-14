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
import javax.persistence.Lob;
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
@Table(name = "proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByProId", query = "SELECT p FROM Proyecto p WHERE p.proId = :proId"),
    @NamedQuery(name = "Proyecto.findByProNombre", query = "SELECT p FROM Proyecto p WHERE p.proNombre = :proNombre"),
    @NamedQuery(name = "Proyecto.findByProRubro", query = "SELECT p FROM Proyecto p WHERE p.proRubro = :proRubro"),
    @NamedQuery(name = "Proyecto.findByProDuracion", query = "SELECT p FROM Proyecto p WHERE p.proDuracion = :proDuracion"),
    @NamedQuery(name = "Proyecto.findByProEstado", query = "SELECT p FROM Proyecto p WHERE p.proEstado = :proEstado"),
    @NamedQuery(name = "Proyecto.findByProDepartamento", query = "SELECT p FROM Proyecto p WHERE p.proDepartamento = :proDepartamento"),
    @NamedQuery(name = "Proyecto.findByProMunicipio", query = "SELECT p FROM Proyecto p WHERE p.proMunicipio = :proMunicipio"),
    @NamedQuery(name = "Proyecto.findByProFechaInicio", query = "SELECT p FROM Proyecto p WHERE p.proFechaInicio = :proFechaInicio"),
    @NamedQuery(name = "Proyecto.findByProFechaFin", query = "SELECT p FROM Proyecto p WHERE p.proFechaFin = :proFechaFin")})
public class Proyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRO_ID")
    private Integer proId;
    @Column(name = "PRO_NOMBRE")
    private String proNombre;
    @Column(name = "PRO_RUBRO")
    private String proRubro;
    @Lob
    @Column(name = "PRO_DESCRIPCION")
    private String proDescripcion;
    @Column(name = "PRO_DURACION")
    private Integer proDuracion;
    @Column(name = "PRO_ESTADO")
    private String proEstado;
    @Column(name = "PRO_DEPARTAMENTO")
    private String proDepartamento;
    @Column(name = "PRO_MUNICIPIO")
    private String proMunicipio;
    @Column(name = "PRO_FECHA_INICIO")
    private String proFechaInicio;
    @Column(name = "PRO_FECHA_FIN")
    private String proFechaFin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoProId")
    private List<Presupuesto> presupuestoList;
    @JoinColumn(name = "CLIENTE_CLI_ID", referencedColumnName = "CLI_ID")
    @ManyToOne(optional = false)
    private Cliente clienteCliId;

    public Proyecto() {
    }

    public Proyecto(Integer proId) {
        this.proId = proId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProRubro() {
        return proRubro;
    }

    public void setProRubro(String proRubro) {
        this.proRubro = proRubro;
    }

    public String getProDescripcion() {
        return proDescripcion;
    }

    public void setProDescripcion(String proDescripcion) {
        this.proDescripcion = proDescripcion;
    }

    public Integer getProDuracion() {
        return proDuracion;
    }

    public void setProDuracion(Integer proDuracion) {
        this.proDuracion = proDuracion;
    }

    public String getProEstado() {
        return proEstado;
    }

    public void setProEstado(String proEstado) {
        this.proEstado = proEstado;
    }

    public String getProDepartamento() {
        return proDepartamento;
    }

    public void setProDepartamento(String proDepartamento) {
        this.proDepartamento = proDepartamento;
    }

    public String getProMunicipio() {
        return proMunicipio;
    }

    public void setProMunicipio(String proMunicipio) {
        this.proMunicipio = proMunicipio;
    }

    public String getProFechaInicio() {
        return proFechaInicio;
    }

    public void setProFechaInicio(String proFechaInicio) {
        this.proFechaInicio = proFechaInicio;
    }

    public String getProFechaFin() {
        return proFechaFin;
    }

    public void setProFechaFin(String proFechaFin) {
        this.proFechaFin = proFechaFin;
    }

    @XmlTransient
    public List<Presupuesto> getPresupuestoList() {
        return presupuestoList;
    }

    public void setPresupuestoList(List<Presupuesto> presupuestoList) {
        this.presupuestoList = presupuestoList;
    }

    public Cliente getClienteCliId() {
        return clienteCliId;
    }

    public void setClienteCliId(Cliente clienteCliId) {
        this.clienteCliId = clienteCliId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proId != null ? proId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.proId == null && other.proId != null) || (this.proId != null && !this.proId.equals(other.proId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return proNombre + " - "+ proEstado;
    }
    
}
