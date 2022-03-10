package com.servistakip.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServisProjeTemel.
 */
@Entity
@Table(name = "servis_proje_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServisProjeTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 150)
    @Column(name = "kullanildigi_yer", length = 150, nullable = false)
    private String kullanildigiYer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "protokol", "bakanlik" }, allowSetters = true)
    private ServisTemel servis;

    @ManyToOne
    private ProjeTemel proje;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServisProjeTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKullanildigiYer() {
        return this.kullanildigiYer;
    }

    public ServisProjeTemel kullanildigiYer(String kullanildigiYer) {
        this.setKullanildigiYer(kullanildigiYer);
        return this;
    }

    public void setKullanildigiYer(String kullanildigiYer) {
        this.kullanildigiYer = kullanildigiYer;
    }

    public ServisTemel getServis() {
        return this.servis;
    }

    public void setServis(ServisTemel servisTemel) {
        this.servis = servisTemel;
    }

    public ServisProjeTemel servis(ServisTemel servisTemel) {
        this.setServis(servisTemel);
        return this;
    }

    public ProjeTemel getProje() {
        return this.proje;
    }

    public void setProje(ProjeTemel projeTemel) {
        this.proje = projeTemel;
    }

    public ServisProjeTemel proje(ProjeTemel projeTemel) {
        this.setProje(projeTemel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServisProjeTemel)) {
            return false;
        }
        return id != null && id.equals(((ServisProjeTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServisProjeTemel{" +
            "id=" + getId() +
            ", kullanildigiYer='" + getKullanildigiYer() + "'" +
            "}";
    }
}
