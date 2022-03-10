package com.servistakip.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Sorunların ve Çözümlerinin Tanımlanacağı Tablodur.\n@author Ömer ALTAN
 */
@Entity
@Table(name = "sorun_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SorunTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 500)
    @Column(name = "sorun_aciklamasi", length = 500, nullable = false)
    private String sorunAciklamasi;

    @NotNull
    @Column(name = "sorun_tarihi", nullable = false)
    private LocalDate sorunTarihi;

    @Size(min = 0, max = 500)
    @Column(name = "cozum_aciklamasi", length = 500)
    private String cozumAciklamasi;

    @Column(name = "cozum_tarihi")
    private LocalDate cozumTarihi;

    @ManyToOne
    @JsonIgnoreProperties(value = { "protokol", "bakanlik" }, allowSetters = true)
    private ServisTemel servis;

    @ManyToOne
    private Personel bulan;

    @ManyToOne
    private Personel cozen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SorunTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSorunAciklamasi() {
        return this.sorunAciklamasi;
    }

    public SorunTemel sorunAciklamasi(String sorunAciklamasi) {
        this.setSorunAciklamasi(sorunAciklamasi);
        return this;
    }

    public void setSorunAciklamasi(String sorunAciklamasi) {
        this.sorunAciklamasi = sorunAciklamasi;
    }

    public LocalDate getSorunTarihi() {
        return this.sorunTarihi;
    }

    public SorunTemel sorunTarihi(LocalDate sorunTarihi) {
        this.setSorunTarihi(sorunTarihi);
        return this;
    }

    public void setSorunTarihi(LocalDate sorunTarihi) {
        this.sorunTarihi = sorunTarihi;
    }

    public String getCozumAciklamasi() {
        return this.cozumAciklamasi;
    }

    public SorunTemel cozumAciklamasi(String cozumAciklamasi) {
        this.setCozumAciklamasi(cozumAciklamasi);
        return this;
    }

    public void setCozumAciklamasi(String cozumAciklamasi) {
        this.cozumAciklamasi = cozumAciklamasi;
    }

    public LocalDate getCozumTarihi() {
        return this.cozumTarihi;
    }

    public SorunTemel cozumTarihi(LocalDate cozumTarihi) {
        this.setCozumTarihi(cozumTarihi);
        return this;
    }

    public void setCozumTarihi(LocalDate cozumTarihi) {
        this.cozumTarihi = cozumTarihi;
    }

    public ServisTemel getServis() {
        return this.servis;
    }

    public void setServis(ServisTemel servisTemel) {
        this.servis = servisTemel;
    }

    public SorunTemel servis(ServisTemel servisTemel) {
        this.setServis(servisTemel);
        return this;
    }

    public Personel getBulan() {
        return this.bulan;
    }

    public void setBulan(Personel personel) {
        this.bulan = personel;
    }

    public SorunTemel bulan(Personel personel) {
        this.setBulan(personel);
        return this;
    }

    public Personel getCozen() {
        return this.cozen;
    }

    public void setCozen(Personel personel) {
        this.cozen = personel;
    }

    public SorunTemel cozen(Personel personel) {
        this.setCozen(personel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SorunTemel)) {
            return false;
        }
        return id != null && id.equals(((SorunTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SorunTemel{" +
            "id=" + getId() +
            ", sorunAciklamasi='" + getSorunAciklamasi() + "'" +
            ", sorunTarihi='" + getSorunTarihi() + "'" +
            ", cozumAciklamasi='" + getCozumAciklamasi() + "'" +
            ", cozumTarihi='" + getCozumTarihi() + "'" +
            "}";
    }
}
