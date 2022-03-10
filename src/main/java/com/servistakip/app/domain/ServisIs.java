package com.servistakip.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Servisi Hazırlayan ve Sorumlusu Bilgileri Tablosu\n@author Ömer ALTAN
 */
@Entity
@Table(name = "servis_is")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServisIs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "baslama_tarihi", nullable = false)
    private LocalDate baslamaTarihi;

    @NotNull
    @Column(name = "tamamlama_tarihi", nullable = false)
    private LocalDate tamamlamaTarihi;

    @OneToOne
    @JoinColumn(unique = true)
    private Personel sorumlu;

    @OneToOne
    @JoinColumn(unique = true)
    private Personel yapan;

    @JsonIgnoreProperties(value = { "protokol", "bakanlik" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ServisTemel servis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServisIs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBaslamaTarihi() {
        return this.baslamaTarihi;
    }

    public ServisIs baslamaTarihi(LocalDate baslamaTarihi) {
        this.setBaslamaTarihi(baslamaTarihi);
        return this;
    }

    public void setBaslamaTarihi(LocalDate baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public LocalDate getTamamlamaTarihi() {
        return this.tamamlamaTarihi;
    }

    public ServisIs tamamlamaTarihi(LocalDate tamamlamaTarihi) {
        this.setTamamlamaTarihi(tamamlamaTarihi);
        return this;
    }

    public void setTamamlamaTarihi(LocalDate tamamlamaTarihi) {
        this.tamamlamaTarihi = tamamlamaTarihi;
    }

    public Personel getSorumlu() {
        return this.sorumlu;
    }

    public void setSorumlu(Personel personel) {
        this.sorumlu = personel;
    }

    public ServisIs sorumlu(Personel personel) {
        this.setSorumlu(personel);
        return this;
    }

    public Personel getYapan() {
        return this.yapan;
    }

    public void setYapan(Personel personel) {
        this.yapan = personel;
    }

    public ServisIs yapan(Personel personel) {
        this.setYapan(personel);
        return this;
    }

    public ServisTemel getServis() {
        return this.servis;
    }

    public void setServis(ServisTemel servisTemel) {
        this.servis = servisTemel;
    }

    public ServisIs servis(ServisTemel servisTemel) {
        this.setServis(servisTemel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServisIs)) {
            return false;
        }
        return id != null && id.equals(((ServisIs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServisIs{" +
            "id=" + getId() +
            ", baslamaTarihi='" + getBaslamaTarihi() + "'" +
            ", tamamlamaTarihi='" + getTamamlamaTarihi() + "'" +
            "}";
    }
}
