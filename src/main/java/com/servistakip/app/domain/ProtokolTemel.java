package com.servistakip.app.domain;

import com.servistakip.app.domain.enumeration.SureDurum;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Servislerin Protokollerinin Tanımlanacağı Ana Tablodur.\n@author Ömer ALTAN
 */
@Entity
@Table(name = "protokol_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProtokolTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sure_durum", nullable = false)
    private SureDurum sureDurum;

    @Column(name = "sure_bitis_tarihi")
    private LocalDate sureBitisTarihi;

    @NotNull
    @Size(min = 0, max = 250)
    @Column(name = "protokol_nu", length = 250, nullable = false)
    private String protokolNu;

    @NotNull
    @Size(min = 0, max = 250)
    @Column(name = "olur_yazi_nu", length = 250, nullable = false)
    private String olurYaziNu;

    @NotNull
    @Column(name = "protokol_imza_tarihi", nullable = false)
    private LocalDate protokolImzaTarihi;

    @Size(min = 0, max = 500)
    @Column(name = "protokol_aciklamasi", length = 500)
    private String protokolAciklamasi;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProtokolTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SureDurum getSureDurum() {
        return this.sureDurum;
    }

    public ProtokolTemel sureDurum(SureDurum sureDurum) {
        this.setSureDurum(sureDurum);
        return this;
    }

    public void setSureDurum(SureDurum sureDurum) {
        this.sureDurum = sureDurum;
    }

    public LocalDate getSureBitisTarihi() {
        return this.sureBitisTarihi;
    }

    public ProtokolTemel sureBitisTarihi(LocalDate sureBitisTarihi) {
        this.setSureBitisTarihi(sureBitisTarihi);
        return this;
    }

    public void setSureBitisTarihi(LocalDate sureBitisTarihi) {
        this.sureBitisTarihi = sureBitisTarihi;
    }

    public String getProtokolNu() {
        return this.protokolNu;
    }

    public ProtokolTemel protokolNu(String protokolNu) {
        this.setProtokolNu(protokolNu);
        return this;
    }

    public void setProtokolNu(String protokolNu) {
        this.protokolNu = protokolNu;
    }

    public String getOlurYaziNu() {
        return this.olurYaziNu;
    }

    public ProtokolTemel olurYaziNu(String olurYaziNu) {
        this.setOlurYaziNu(olurYaziNu);
        return this;
    }

    public void setOlurYaziNu(String olurYaziNu) {
        this.olurYaziNu = olurYaziNu;
    }

    public LocalDate getProtokolImzaTarihi() {
        return this.protokolImzaTarihi;
    }

    public ProtokolTemel protokolImzaTarihi(LocalDate protokolImzaTarihi) {
        this.setProtokolImzaTarihi(protokolImzaTarihi);
        return this;
    }

    public void setProtokolImzaTarihi(LocalDate protokolImzaTarihi) {
        this.protokolImzaTarihi = protokolImzaTarihi;
    }

    public String getProtokolAciklamasi() {
        return this.protokolAciklamasi;
    }

    public ProtokolTemel protokolAciklamasi(String protokolAciklamasi) {
        this.setProtokolAciklamasi(protokolAciklamasi);
        return this;
    }

    public void setProtokolAciklamasi(String protokolAciklamasi) {
        this.protokolAciklamasi = protokolAciklamasi;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProtokolTemel)) {
            return false;
        }
        return id != null && id.equals(((ProtokolTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProtokolTemel{" +
            "id=" + getId() +
            ", sureDurum='" + getSureDurum() + "'" +
            ", sureBitisTarihi='" + getSureBitisTarihi() + "'" +
            ", protokolNu='" + getProtokolNu() + "'" +
            ", olurYaziNu='" + getOlurYaziNu() + "'" +
            ", protokolImzaTarihi='" + getProtokolImzaTarihi() + "'" +
            ", protokolAciklamasi='" + getProtokolAciklamasi() + "'" +
            "}";
    }
}
