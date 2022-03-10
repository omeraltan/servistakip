package com.servistakip.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Sevislerin Metodları burada tanımlanmaktadır.\n@author Ömer ALTAN
 */
@Entity
@Table(name = "metod_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MetodTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "metod_adi", length = 50, nullable = false)
    private String metodAdi;

    @NotNull
    @Column(name = "metod_nu", nullable = false)
    private Integer metodNu;

    @NotNull
    @Size(min = 0, max = 250)
    @Column(name = "metod_aciklamasi", length = 250, nullable = false)
    private String metodAciklamasi;

    @ManyToOne
    @JsonIgnoreProperties(value = { "protokol", "bakanlik" }, allowSetters = true)
    private ServisTemel servis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MetodTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetodAdi() {
        return this.metodAdi;
    }

    public MetodTemel metodAdi(String metodAdi) {
        this.setMetodAdi(metodAdi);
        return this;
    }

    public void setMetodAdi(String metodAdi) {
        this.metodAdi = metodAdi;
    }

    public Integer getMetodNu() {
        return this.metodNu;
    }

    public MetodTemel metodNu(Integer metodNu) {
        this.setMetodNu(metodNu);
        return this;
    }

    public void setMetodNu(Integer metodNu) {
        this.metodNu = metodNu;
    }

    public String getMetodAciklamasi() {
        return this.metodAciklamasi;
    }

    public MetodTemel metodAciklamasi(String metodAciklamasi) {
        this.setMetodAciklamasi(metodAciklamasi);
        return this;
    }

    public void setMetodAciklamasi(String metodAciklamasi) {
        this.metodAciklamasi = metodAciklamasi;
    }

    public ServisTemel getServis() {
        return this.servis;
    }

    public void setServis(ServisTemel servisTemel) {
        this.servis = servisTemel;
    }

    public MetodTemel servis(ServisTemel servisTemel) {
        this.setServis(servisTemel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetodTemel)) {
            return false;
        }
        return id != null && id.equals(((MetodTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetodTemel{" +
            "id=" + getId() +
            ", metodAdi='" + getMetodAdi() + "'" +
            ", metodNu=" + getMetodNu() +
            ", metodAciklamasi='" + getMetodAciklamasi() + "'" +
            "}";
    }
}
