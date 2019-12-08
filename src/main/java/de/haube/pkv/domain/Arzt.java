package de.haube.pkv.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Arzt.
 */
@Entity
@Table(name = "arzt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Arzt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "strasse")
    private String strasse;

    @Column(name = "hausnummer")
    private String hausnummer;

    @Column(name = "plz")
    private String plz;

    @Column(name = "ort")
    private String ort;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "telefon_2")
    private String telefon2;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "web")
    private String web;

    @OneToMany(mappedBy = "arzt")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Termin> termines = new HashSet<>();

    @OneToMany(mappedBy = "arzt")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rechnung> rechnungens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Arzt name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrasse() {
        return strasse;
    }

    public Arzt strasse(String strasse) {
        this.strasse = strasse;
        return this;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public Arzt hausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
        return this;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPlz() {
        return plz;
    }

    public Arzt plz(String plz) {
        this.plz = plz;
        return this;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public Arzt ort(String ort) {
        this.ort = ort;
        return this;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getTelefon() {
        return telefon;
    }

    public Arzt telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getTelefon2() {
        return telefon2;
    }

    public Arzt telefon2(String telefon2) {
        this.telefon2 = telefon2;
        return this;
    }

    public void setTelefon2(String telefon2) {
        this.telefon2 = telefon2;
    }

    public String getFax() {
        return fax;
    }

    public Arzt fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public Arzt email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public Arzt web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Set<Termin> getTermines() {
        return termines;
    }

    public Arzt termines(Set<Termin> termins) {
        this.termines = termins;
        return this;
    }

    public Arzt addTermine(Termin termin) {
        this.termines.add(termin);
        termin.setArzt(this);
        return this;
    }

    public Arzt removeTermine(Termin termin) {
        this.termines.remove(termin);
        termin.setArzt(null);
        return this;
    }

    public void setTermines(Set<Termin> termins) {
        this.termines = termins;
    }

    public Set<Rechnung> getRechnungens() {
        return rechnungens;
    }

    public Arzt rechnungens(Set<Rechnung> rechnungs) {
        this.rechnungens = rechnungs;
        return this;
    }

    public Arzt addRechnungen(Rechnung rechnung) {
        this.rechnungens.add(rechnung);
        rechnung.setArzt(this);
        return this;
    }

    public Arzt removeRechnungen(Rechnung rechnung) {
        this.rechnungens.remove(rechnung);
        rechnung.setArzt(null);
        return this;
    }

    public void setRechnungens(Set<Rechnung> rechnungs) {
        this.rechnungens = rechnungs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arzt)) {
            return false;
        }
        return id != null && id.equals(((Arzt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Arzt{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", strasse='" + getStrasse() + "'" +
            ", hausnummer='" + getHausnummer() + "'" +
            ", plz='" + getPlz() + "'" +
            ", ort='" + getOrt() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", telefon2='" + getTelefon2() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", web='" + getWeb() + "'" +
            "}";
    }
}
