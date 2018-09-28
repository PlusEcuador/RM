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
@Table(name = "Pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")
    , @NamedQuery(name = "Pais.findByPaiId", query = "SELECT p FROM Pais p WHERE p.paiId = :paiId")
    , @NamedQuery(name = "Pais.findByPaiNombre", query = "SELECT p FROM Pais p WHERE p.paiNombre = :paiNombre")
    , @NamedQuery(name = "Pais.findByPaiEstado", query = "SELECT p FROM Pais p WHERE p.paiEstado = :paiEstado")
    , @NamedQuery(name = "Pais.findByPaiFHR", query = "SELECT p FROM Pais p WHERE p.paiFHR = :paiFHR")
    , @NamedQuery(name = "Pais.findByPaiUser", query = "SELECT p FROM Pais p WHERE p.paiUser = :paiUser")
    , @NamedQuery(name = "Pais.findByPaiCodigo", query = "SELECT p FROM Pais p WHERE p.paiCodigo = :paiCodigo")
    , @NamedQuery(name = "Pais.findByPaiInicial", query = "SELECT p FROM Pais p WHERE p.paiInicial = :paiInicial")})
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PaiId")
    private BigDecimal paiId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "PaiNombre")
    private String paiNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PaiEstado")
    private String paiEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PaiFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paiFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PaiUser")
    private String paiUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PaiCodigo")
    private String paiCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PaiInicial")
    private String paiInicial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paiId")
    private List<Provincia> provinciaList;

    public Pais() {
    }

    public Pais(BigDecimal paiId) {
        this.paiId = paiId;
    }

    public Pais(BigDecimal paiId, String paiNombre, String paiEstado, Date paiFHR, String paiUser, String paiCodigo, String paiInicial) {
        this.paiId = paiId;
        this.paiNombre = paiNombre;
        this.paiEstado = paiEstado;
        this.paiFHR = paiFHR;
        this.paiUser = paiUser;
        this.paiCodigo = paiCodigo;
        this.paiInicial = paiInicial;
    }

    public BigDecimal getPaiId() {
        return paiId;
    }

    public void setPaiId(BigDecimal paiId) {
        this.paiId = paiId;
    }

    public String getPaiNombre() {
        return paiNombre;
    }

    public void setPaiNombre(String paiNombre) {
        this.paiNombre = paiNombre;
    }

    public String getPaiEstado() {
        return paiEstado;
    }

    public void setPaiEstado(String paiEstado) {
        this.paiEstado = paiEstado;
    }

    public Date getPaiFHR() {
        return paiFHR;
    }

    public void setPaiFHR(Date paiFHR) {
        this.paiFHR = paiFHR;
    }

    public String getPaiUser() {
        return paiUser;
    }

    public void setPaiUser(String paiUser) {
        this.paiUser = paiUser;
    }

    public String getPaiCodigo() {
        return paiCodigo;
    }

    public void setPaiCodigo(String paiCodigo) {
        this.paiCodigo = paiCodigo;
    }

    public String getPaiInicial() {
        return paiInicial;
    }

    public void setPaiInicial(String paiInicial) {
        this.paiInicial = paiInicial;
    }

    @XmlTransient
    public List<Provincia> getProvinciaList() {
        return provinciaList;
    }

    public void setProvinciaList(List<Provincia> provinciaList) {
        this.provinciaList = provinciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paiId != null ? paiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.paiId == null && other.paiId != null) || (this.paiId != null && !this.paiId.equals(other.paiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Pais[ paiId=" + paiId + " ]";
    }
    
}
