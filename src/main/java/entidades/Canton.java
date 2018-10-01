/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Calendar;
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
@Table(name = "Canton")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Canton.findAll", query = "SELECT c FROM Canton c")
    , @NamedQuery(name = "Canton.findByCanId", query = "SELECT c FROM Canton c WHERE c.canId = :canId")
    , @NamedQuery(name = "Canton.findByCanNombre", query = "SELECT c FROM Canton c WHERE c.canNombre = :canNombre")
    , @NamedQuery(name = "Canton.findByCanEstado", query = "SELECT c FROM Canton c WHERE c.canEstado = :canEstado")
    , @NamedQuery(name = "Canton.findByCanFHR", query = "SELECT c FROM Canton c WHERE c.canFHR = :canFHR")
    , @NamedQuery(name = "Canton.findByCanUser", query = "SELECT c FROM Canton c WHERE c.canUser = :canUser")
    , @NamedQuery(name = "Canton.findByCanCodigo", query = "SELECT c FROM Canton c WHERE c.canCodigo = :canCodigo")
    , @NamedQuery(name = "Canton.findByCanInicial", query = "SELECT c FROM Canton c WHERE c.canInicial = :canInicial")})
public class Canton implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CanId")
    private Long canId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "CanNombre")
    private String canNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CanEstado")
    private String canEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CanFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date canFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CanUser")
    private String canUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CanCodigo")
    private String canCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CanInicial")
    private String canInicial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "canId")
    private List<Ciudad> ciudadList;
    @JoinColumn(name = "ProId", referencedColumnName = "ProId")
    @ManyToOne(optional = false)
    private Provincia proId;

    public Canton() {
        this.canFHR=Calendar.getInstance().getTime();
    }

    public Canton(Long canId) {
        this.canId = canId;
    }

    public Canton(Long canId, String canNombre, String canEstado, Date canFHR, String canUser, String canCodigo, String canInicial) {
        this.canId = canId;
        this.canNombre = canNombre;
        this.canEstado = canEstado;
        this.canFHR = canFHR;
        this.canUser = canUser;
        this.canCodigo = canCodigo;
        this.canInicial = canInicial;
    }

    public Long getCanId() {
        return canId;
    }

    public void setCanId(Long canId) {
        this.canId = canId;
    }

    public String getCanNombre() {
        return canNombre;
    }

    public void setCanNombre(String canNombre) {
        this.canNombre = canNombre;
    }

    public String getCanEstado() {
        return canEstado;
    }

    public void setCanEstado(String canEstado) {
        this.canEstado = canEstado;
    }

    public Date getCanFHR() {
        return canFHR;
    }

    public void setCanFHR(Date canFHR) {
        this.canFHR = canFHR;
    }

    public String getCanUser() {
        return canUser;
    }

    public void setCanUser(String canUser) {
        this.canUser = canUser;
    }

    public String getCanCodigo() {
        return canCodigo;
    }

    public void setCanCodigo(String canCodigo) {
        this.canCodigo = canCodigo;
    }

    public String getCanInicial() {
        return canInicial;
    }

    public void setCanInicial(String canInicial) {
        this.canInicial = canInicial;
    }

    @XmlTransient
    public List<Ciudad> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<Ciudad> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public Provincia getProId() {
        return proId;
    }

    public void setProId(Provincia proId) {
        this.proId = proId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (canId != null ? canId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canton)) {
            return false;
        }
        Canton other = (Canton) object;
        if ((this.canId == null && other.canId != null) || (this.canId != null && !this.canId.equals(other.canId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Canton[ canId=" + canId + " ]";
    }
    
}
