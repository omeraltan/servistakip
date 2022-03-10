package com.servistakip.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Log Tablolarının Tanımlanacağı Tablodur.\n@author Ömer ALTAN
 */
@Entity
@Table(name = "tablo_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TabloTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "gercek_veritabani", length = 100, nullable = false)
    private String gercekVeritabani;

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "gercek_log_tablo", length = 100, nullable = false)
    private String gercekLogTablo;

    @Size(min = 0, max = 500)
    @Column(name = "gercekaciklamasi", length = 500)
    private String gercekaciklamasi;

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "test_veritabani", length = 100, nullable = false)
    private String testVeritabani;

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "test_log_tablo", length = 100, nullable = false)
    private String testLogTablo;

    @Size(min = 0, max = 500)
    @Column(name = "testaciklamasi", length = 500)
    private String testaciklamasi;

    @JsonIgnoreProperties(value = { "protokol", "bakanlik" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ServisTemel servis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TabloTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGercekVeritabani() {
        return this.gercekVeritabani;
    }

    public TabloTemel gercekVeritabani(String gercekVeritabani) {
        this.setGercekVeritabani(gercekVeritabani);
        return this;
    }

    public void setGercekVeritabani(String gercekVeritabani) {
        this.gercekVeritabani = gercekVeritabani;
    }

    public String getGercekLogTablo() {
        return this.gercekLogTablo;
    }

    public TabloTemel gercekLogTablo(String gercekLogTablo) {
        this.setGercekLogTablo(gercekLogTablo);
        return this;
    }

    public void setGercekLogTablo(String gercekLogTablo) {
        this.gercekLogTablo = gercekLogTablo;
    }

    public String getGercekaciklamasi() {
        return this.gercekaciklamasi;
    }

    public TabloTemel gercekaciklamasi(String gercekaciklamasi) {
        this.setGercekaciklamasi(gercekaciklamasi);
        return this;
    }

    public void setGercekaciklamasi(String gercekaciklamasi) {
        this.gercekaciklamasi = gercekaciklamasi;
    }

    public String getTestVeritabani() {
        return this.testVeritabani;
    }

    public TabloTemel testVeritabani(String testVeritabani) {
        this.setTestVeritabani(testVeritabani);
        return this;
    }

    public void setTestVeritabani(String testVeritabani) {
        this.testVeritabani = testVeritabani;
    }

    public String getTestLogTablo() {
        return this.testLogTablo;
    }

    public TabloTemel testLogTablo(String testLogTablo) {
        this.setTestLogTablo(testLogTablo);
        return this;
    }

    public void setTestLogTablo(String testLogTablo) {
        this.testLogTablo = testLogTablo;
    }

    public String getTestaciklamasi() {
        return this.testaciklamasi;
    }

    public TabloTemel testaciklamasi(String testaciklamasi) {
        this.setTestaciklamasi(testaciklamasi);
        return this;
    }

    public void setTestaciklamasi(String testaciklamasi) {
        this.testaciklamasi = testaciklamasi;
    }

    public ServisTemel getServis() {
        return this.servis;
    }

    public void setServis(ServisTemel servisTemel) {
        this.servis = servisTemel;
    }

    public TabloTemel servis(ServisTemel servisTemel) {
        this.setServis(servisTemel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TabloTemel)) {
            return false;
        }
        return id != null && id.equals(((TabloTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TabloTemel{" +
            "id=" + getId() +
            ", gercekVeritabani='" + getGercekVeritabani() + "'" +
            ", gercekLogTablo='" + getGercekLogTablo() + "'" +
            ", gercekaciklamasi='" + getGercekaciklamasi() + "'" +
            ", testVeritabani='" + getTestVeritabani() + "'" +
            ", testLogTablo='" + getTestLogTablo() + "'" +
            ", testaciklamasi='" + getTestaciklamasi() + "'" +
            "}";
    }
}
