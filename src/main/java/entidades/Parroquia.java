/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Parroquia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parroquia.findAll", query = "SELECT p FROM Parroquia p")
    , @NamedQuery(name = "Parroquia.findByParId", query = "SELECT p FROM Parroquia p WHERE p.parId = :parId")
    , @NamedQuery(name = "Parroquia.findByParNombre", query = "SELECT p FROM Parroquia p WHERE p.parNombre = :parNombre")
    , @NamedQuery(name = "Parroquia.findByParEstado", query = "SELECT p FROM Parroquia p WHERE p.parEstado = :parEstado")
    , @NamedQuery(name = "Parroquia.findByParFHR", query = "SELECT p FROM Parroquia p WHERE p.parFHR = :parFHR")
    , @NamedQuery(name = "Parroquia.findByParUser", query = "SELECT p FROM Parroquia p WHERE p.parUser = :parUser")
    , @NamedQuery(name = "Parroquia.findByParCodigo", query = "SELECT p FROM Parroquia p WHERE p.parCodigo = :parCodigo")
    , @NamedQuery(name = "Parroquia.findByParInicial", query = "SELECT p FROM Parroquia p WHERE p.parInicial = :parInicial")
    , @NamedQuery(name = "Parroquia.findByParSecuencial", query = "SELECT p FROM Parroquia p WHERE p.parSecuencial = :parSecuencial")})
public class Parroquia implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ParId")
    private BigDecimal parId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ParNombre")
    private String parNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "ParEstado")
    private String parEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ParFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ParUser")
    private String parUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ParCodigo")
    private String parCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ParInicial")
    private String parInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ParSecuencial")
    private short parSecuencial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parId")
    private List<Zona> zonaList;
    @JoinColumn(name = "CiuId", referencedColumnName = "CiuId")
    @ManyToOne(optional = false)
    private Ciudad ciuId;

    public Parroquia() {
    }

    public Parroquia(BigDecimal parId) {
        this.parId = parId;
    }

    public Parroquia(BigDecimal parId, String parNombre, String parEstado, Date parFHR, String parUser, String parCodigo, String parInicial, short parSecuencial) {
        this.parId = parId;
        this.parNombre = parNombre;
        this.parEstado = parEstado;
        this.parFHR = parFHR;
        this.parUser = parUser;
        this.parCodigo = parCodigo;
        this.parInicial = parInicial;
        this.parSecuencial = parSecuencial;
    }

    public BigDecimal getParId() {
        return parId;
    }

    public void setParId(BigDecimal parId) {
        this.parId = parId;
    }

    public String getParNombre() {
        return parNombre;
    }

    public void setParNombre(String parNombre) {
        this.parNombre = parNombre;
    }

    public String getParEstado() {
        return parEstado;
    }

    public void setParEstado(String parEstado) {
        this.parEstado = parEstado;
    }

    public Date getParFHR() {
        return parFHR;
    }

    public void setParFHR(Date parFHR) {
        this.parFHR = parFHR;
    }

    public String getParUser() {
        return parUser;
    }

    public void setParUser(String parUser) {
        this.parUser = parUser;
    }

    public String getParCodigo() {
        return parCodigo;
    }

    public void setParCodigo(String parCodigo) {
        this.parCodigo = parCodigo;
    }

    public String getParInicial() {
        return parInicial;
    }

    public void setParInicial(String parInicial) {
        this.parInicial = parInicial;
    }

    public short getParSecuencial() {
        return parSecuencial;
    }

    public void setParSecuencial(short parSecuencial) {
        this.parSecuencial = parSecuencial;
    }

    @XmlTransient
    public List<Zona> getZonaList() {
        return zonaList;
    }

    public void setZonaList(List<Zona> zonaList) {
        this.zonaList = zonaList;
    }

    public Ciudad getCiuId() {
        return ciuId;
    }

    public void setCiuId(Ciudad ciuId) {
        this.ciuId = ciuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parId != null ? parId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parroquia)) {
            return false;
        }
        Parroquia other = (Parroquia) object;
        if ((this.parId == null && other.parId != null) || (this.parId != null && !this.parId.equals(other.parId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Parroquia[ parId=" + parId + " ]";
    }
    
}
