package de.haube.pkv.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Termin.
 */
@Entity
@Table(name = "termin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Termin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "datum", nullable = false)
    private Instant datum;

    @Column(name = "notiz")
    private String notiz;

    @ManyToOne
    @JsonIgnoreProperties("termins")
    private Arzt arzt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatum() {
        return datum;
    }

    public Termin datum(Instant datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(Instant datum) {
        this.datum = datum;
    }

    public String getNotiz() {
        return notiz;
    }

    public Termin notiz(String notiz) {
        this.notiz = notiz;
        return this;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public Arzt getArzt() {
        return arzt;
    }

    public Termin arzt(Arzt arzt) {
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
        if (!(o instanceof Termin)) {
            return false;
        }
        return id != null && id.equals(((Termin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Termin{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", notiz='" + getNotiz() + "'" +
            "}";
    }
}
