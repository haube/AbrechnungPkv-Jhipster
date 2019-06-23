package de.haube.pkv.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A Rechnung.
 */
@Entity
@Table(name = "rechnung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rechnung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "betrag", precision = 21, scale = 2)
    private BigDecimal betrag;

    @Column(name = "datum_rechnung")
    private Instant datumRechnung;

    @Column(name = "datum_zahlung")
    private Instant datumZahlung;

    @ManyToOne
    @JsonIgnoreProperties("rechnungs")
    private Arzt arzt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public Rechnung betrag(BigDecimal betrag) {
        this.betrag = betrag;
        return this;
    }

    public void setBetrag(BigDecimal betrag) {
        this.betrag = betrag;
    }

    public Instant getDatumRechnung() {
        return datumRechnung;
    }

    public Rechnung datumRechnung(Instant datumRechnung) {
        this.datumRechnung = datumRechnung;
        return this;
    }

    public void setDatumRechnung(Instant datumRechnung) {
        this.datumRechnung = datumRechnung;
    }

    public Instant getDatumZahlung() {
        return datumZahlung;
    }

    public Rechnung datumZahlung(Instant datumZahlung) {
        this.datumZahlung = datumZahlung;
        return this;
    }

    public void setDatumZahlung(Instant datumZahlung) {
        this.datumZahlung = datumZahlung;
    }

    public Arzt getArzt() {
        return arzt;
    }

    public Rechnung arzt(Arzt arzt) {
        this.arzt = arzt;
        return this;
    }

    public void setArzt(Arzt arzt) {
        this.arzt = arzt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rechnung)) {
            return false;
        }
        return id != null && id.equals(((Rechnung) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rechnung{" +
            "id=" + getId() +
            ", betrag=" + getBetrag() +
            ", datumRechnung='" + getDatumRechnung() + "'" +
            ", datumZahlung='" + getDatumZahlung() + "'" +
            "}";
    }
}
