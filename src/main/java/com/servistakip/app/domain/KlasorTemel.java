package com.servistakip.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Belgelerin Yerinin Tanımlanacağı Tablodur.\n@author Ömer ALTAN
 */
@Entity
@Table(name = "klasor_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KlasorTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "odasi", length = 50, nullable = false)
    private String odasi;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "dolabi", length = 50, nullable = false)
    private String dolabi;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "klasoru", length = 50, nullable = false)
    private String klasoru;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "fihrist_sirasi", length = 50, nullable = false)
    private String fihristSirasi;

    @JsonIgnoreProperties(value = { "protokol", "bakanlik" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ServisTemel servis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KlasorTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOdasi() {
        return this.odasi;
    }

    public KlasorTemel odasi(String odasi) {
        this.setOdasi(odasi);
        return this;
    }

    public void setOdasi(String odasi) {
        this.odasi = odasi;
    }

    public String getDolabi() {
        return this.dolabi;
    }

    public KlasorTemel dolabi(String dolabi) {
        this.setDolabi(dolabi);
        return this;
    }

    public void setDolabi(String dolabi) {
        this.dolabi = dolabi;
    }

    public String getKlasoru() {
        return this.klasoru;
    }

    public KlasorTemel klasoru(String klasoru) {
        this.setKlasoru(klasoru);
        return this;
    }

    public void setKlasoru(String klasoru) {
        this.klasoru = klasoru;
    }

    public String getFihristSirasi() {
        return this.fihristSirasi;
    }

    public KlasorTemel fihristSirasi(String fihristSirasi) {
        this.setFihristSirasi(fihristSirasi);
        return this;
    }

    public void setFihristSirasi(String fihristSirasi) {
        this.fihristSirasi = fihristSirasi;
    }

    public ServisTemel getServis() {
        return this.servis;
    }

    public void setServis(ServisTemel servisTemel) {
        this.servis = servisTemel;
    }

    public KlasorTemel servis(ServisTemel servisTemel) {
        this.setServis(servisTemel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KlasorTemel)) {
            return false;
        }
        return id != null && id.equals(((KlasorTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KlasorTemel{" +
            "id=" + getId() +
            ", odasi='" + getOdasi() + "'" +
            ", dolabi='" + getDolabi() + "'" +
            ", klasoru='" + getKlasoru() + "'" +
            ", fihristSirasi='" + getFihristSirasi() + "'" +
            "}";
    }
}
