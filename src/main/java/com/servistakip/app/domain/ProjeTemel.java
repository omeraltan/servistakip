package com.servistakip.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Servisleri Kullanan Projelerin Tanımlandığı Yer\n@author Ömer ALTAN
 */
@Entity
@Table(name = "proje_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjeTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "projedi", length = 100, nullable = false)
    private String projedi;

    @Size(min = 0, max = 50)
    @Column(name = "proje_kodu", length = 50)
    private String projeKodu;

    @NotNull
    @Size(min = 0, max = 150)
    @Column(name = "aciklama", length = 150, nullable = false)
    private String aciklama;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjeTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjedi() {
        return this.projedi;
    }

    public ProjeTemel projedi(String projedi) {
        this.setProjedi(projedi);
        return this;
    }

    public void setProjedi(String projedi) {
        this.projedi = projedi;
    }

    public String getProjeKodu() {
        return this.projeKodu;
    }

    public ProjeTemel projeKodu(String projeKodu) {
        this.setProjeKodu(projeKodu);
        return this;
    }

    public void setProjeKodu(String projeKodu) {
        this.projeKodu = projeKodu;
    }

    public String getAciklama() {
        return this.aciklama;
    }

    public ProjeTemel aciklama(String aciklama) {
        this.setAciklama(aciklama);
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjeTemel)) {
            return false;
        }
        return id != null && id.equals(((ProjeTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjeTemel{" +
            "id=" + getId() +
            ", projedi='" + getProjedi() + "'" +
            ", projeKodu='" + getProjeKodu() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            "}";
    }
}
