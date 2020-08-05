/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cf170065
 */
@Entity
@Table(name = "zahtevi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zahtevi.findAll", query = "SELECT z FROM Zahtevi z"),
    @NamedQuery(name = "Zahtevi.findById", query = "SELECT z FROM Zahtevi z WHERE z.id = :id"),
    @NamedQuery(name = "Zahtevi.findByJmbg", query = "SELECT z FROM Zahtevi z WHERE z.jmbg = :jmbg"),
    @NamedQuery(name = "Zahtevi.findByIme", query = "SELECT z FROM Zahtevi z WHERE z.ime = :ime"),
    @NamedQuery(name = "Zahtevi.findByPrezime", query = "SELECT z FROM Zahtevi z WHERE z.prezime = :prezime"),
    @NamedQuery(name = "Zahtevi.findByImeMajke", query = "SELECT z FROM Zahtevi z WHERE z.imeMajke = :imeMajke"),
    @NamedQuery(name = "Zahtevi.findByPrezimeMajke", query = "SELECT z FROM Zahtevi z WHERE z.prezimeMajke = :prezimeMajke"),
    @NamedQuery(name = "Zahtevi.findByImeOca", query = "SELECT z FROM Zahtevi z WHERE z.imeOca = :imeOca"),
    @NamedQuery(name = "Zahtevi.findByPrezimeOca", query = "SELECT z FROM Zahtevi z WHERE z.prezimeOca = :prezimeOca"),
    @NamedQuery(name = "Zahtevi.findByPol", query = "SELECT z FROM Zahtevi z WHERE z.pol = :pol"),
    @NamedQuery(name = "Zahtevi.findByDatumRodjenja", query = "SELECT z FROM Zahtevi z WHERE z.datumRodjenja = :datumRodjenja"),
    @NamedQuery(name = "Zahtevi.findByNacionalnost", query = "SELECT z FROM Zahtevi z WHERE z.nacionalnost = :nacionalnost"),
    @NamedQuery(name = "Zahtevi.findByProfesija", query = "SELECT z FROM Zahtevi z WHERE z.profesija = :profesija"),
    @NamedQuery(name = "Zahtevi.findByBracnoStanje", query = "SELECT z FROM Zahtevi z WHERE z.bracnoStanje = :bracnoStanje"),
    @NamedQuery(name = "Zahtevi.findByOpstinaPrebivalista", query = "SELECT z FROM Zahtevi z WHERE z.opstinaPrebivalista = :opstinaPrebivalista"),
    @NamedQuery(name = "Zahtevi.findByUlicaPrebivalista", query = "SELECT z FROM Zahtevi z WHERE z.ulicaPrebivalista = :ulicaPrebivalista"),
    @NamedQuery(name = "Zahtevi.findByBrojPrebivalista", query = "SELECT z FROM Zahtevi z WHERE z.brojPrebivalista = :brojPrebivalista"),
    @NamedQuery(name = "Zahtevi.findByStatus", query = "SELECT z FROM Zahtevi z WHERE z.status = :status")})
public class Zahtevi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "JMBG")
    private String jmbg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ime")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "prezime")
    private String prezime;
    @Size(max = 15)
    @Column(name = "imeMajke")
    private String imeMajke;
    @Size(max = 15)
    @Column(name = "prezimeMajke")
    private String prezimeMajke;
    @Size(max = 15)
    @Column(name = "imeOca")
    private String imeOca;
    @Size(max = 15)
    @Column(name = "prezimeOca")
    private String prezimeOca;
    @Size(max = 15)
    @Column(name = "pol")
    private String pol;
    @Size(max = 15)
    @Column(name = "datumRodjenja")
    private String datumRodjenja;
    @Size(max = 15)
    @Column(name = "nacionalnost")
    private String nacionalnost;
    @Size(max = 15)
    @Column(name = "profesija")
    private String profesija;
    @Size(max = 15)
    @Column(name = "bracnoStanje")
    private String bracnoStanje;
    @Size(max = 15)
    @Column(name = "opstinaPrebivalista")
    private String opstinaPrebivalista;
    @Size(max = 15)
    @Column(name = "ulicaPrebivalista")
    private String ulicaPrebivalista;
    @Size(max = 15)
    @Column(name = "brojPrebivalista")
    private String brojPrebivalista;
    @Size(max = 15)
    @Column(name = "status")
    private String status;

    public Zahtevi() {
    }

    public Zahtevi(String id) {
        this.id = id;
    }

    public Zahtevi(String id, String jmbg, String ime, String prezime) {
        this.id = id;
        this.jmbg = jmbg;
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getImeMajke() {
        return imeMajke;
    }

    public void setImeMajke(String imeMajke) {
        this.imeMajke = imeMajke;
    }

    public String getPrezimeMajke() {
        return prezimeMajke;
    }

    public void setPrezimeMajke(String prezimeMajke) {
        this.prezimeMajke = prezimeMajke;
    }

    public String getImeOca() {
        return imeOca;
    }

    public void setImeOca(String imeOca) {
        this.imeOca = imeOca;
    }

    public String getPrezimeOca() {
        return prezimeOca;
    }

    public void setPrezimeOca(String prezimeOca) {
        this.prezimeOca = prezimeOca;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getNacionalnost() {
        return nacionalnost;
    }

    public void setNacionalnost(String nacionalnost) {
        this.nacionalnost = nacionalnost;
    }

    public String getProfesija() {
        return profesija;
    }

    public void setProfesija(String profesija) {
        this.profesija = profesija;
    }

    public String getBracnoStanje() {
        return bracnoStanje;
    }

    public void setBracnoStanje(String bracnoStanje) {
        this.bracnoStanje = bracnoStanje;
    }

    public String getOpstinaPrebivalista() {
        return opstinaPrebivalista;
    }

    public void setOpstinaPrebivalista(String opstinaPrebivalista) {
        this.opstinaPrebivalista = opstinaPrebivalista;
    }

    public String getUlicaPrebivalista() {
        return ulicaPrebivalista;
    }

    public void setUlicaPrebivalista(String ulicaPrebivalista) {
        this.ulicaPrebivalista = ulicaPrebivalista;
    }

    public String getBrojPrebivalista() {
        return brojPrebivalista;
    }

    public void setBrojPrebivalista(String brojPrebivalista) {
        this.brojPrebivalista = brojPrebivalista;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zahtevi)) {
            return false;
        }
        Zahtevi other = (Zahtevi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paket.Zahtevi[ id=" + id + " ]";
    }
    
}
