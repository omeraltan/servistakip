package com.servistakip.app.domain;

import com.servistakip.app.domain.enumeration.Unvani;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Servislerin Sorumlu Personeli ve Sorun Çözen Personelinin Tanımlanacağı Tablodur.\n@author Ömer ALTAN
 */
@Entity
@Table(name = "personel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Personel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "adi", length = 50, nullable = false)
    private String adi;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "soyadi", length = 50, nullable = false)
    private String soyadi;

    @NotNull
    @Size(min = 0, max = 15)
    @Column(name = "telefonu", length = 15, nullable = false)
    private String telefonu;

    @Size(min = 0, max = 50)
    @Column(name = "eposta", length = 50)
    private String eposta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unvani", nullable = false)
    private Unvani unvani;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Personel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return this.adi;
    }

    public Personel adi(String adi) {
        this.setAdi(adi);
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return this.soyadi;
    }

    public Personel soyadi(String soyadi) {
        this.setSoyadi(soyadi);
        return this;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getTelefonu() {
        return this.telefonu;
    }

    public Personel telefonu(String telefonu) {
        this.setTelefonu(telefonu);
        return this;
    }

    public void setTelefonu(String telefonu) {
        this.telefonu = telefonu;
    }

    public String getEposta() {
        return this.eposta;
    }

    public Personel eposta(String eposta) {
        this.setEposta(eposta);
        return this;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public Unvani getUnvani() {
        return this.unvani;
    }

    public Personel unvani(Unvani unvani) {
        this.setUnvani(unvani);
        return this;
    }

    public void setUnvani(Unvani unvani) {
        this.unvani = unvani;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personel)) {
            return false;
        }
        return id != null && id.equals(((Personel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personel{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", soyadi='" + getSoyadi() + "'" +
            ", telefonu='" + getTelefonu() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", unvani='" + getUnvani() + "'" +
            "}";
    }
}
