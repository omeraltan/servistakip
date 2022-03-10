package com.servistakip.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Kurumların Tanımlanacağı Tablodur.\n@author Ömer ALTAN
 */
@Entity
@Table(name = "kurum_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KurumTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "bakanlik_adi", length = 100, nullable = false)
    private String bakanlikAdi;

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "birim_adi", length = 100, nullable = false)
    private String birimAdi;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KurumTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBakanlikAdi() {
        return this.bakanlikAdi;
    }

    public KurumTemel bakanlikAdi(String bakanlikAdi) {
        this.setBakanlikAdi(bakanlikAdi);
        return this;
    }

    public void setBakanlikAdi(String bakanlikAdi) {
        this.bakanlikAdi = bakanlikAdi;
    }

    public String getBirimAdi() {
        return this.birimAdi;
    }

    public KurumTemel birimAdi(String birimAdi) {
        this.setBirimAdi(birimAdi);
        return this;
    }

    public void setBirimAdi(String birimAdi) {
        this.birimAdi = birimAdi;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KurumTemel)) {
            return false;
        }
        return id != null && id.equals(((KurumTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KurumTemel{" +
            "id=" + getId() +
            ", bakanlikAdi='" + getBakanlikAdi() + "'" +
            ", birimAdi='" + getBirimAdi() + "'" +
            "}";
    }
}
