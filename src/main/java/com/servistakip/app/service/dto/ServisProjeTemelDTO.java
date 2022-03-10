package com.servistakip.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.ServisProjeTemel} entity.
 */
public class ServisProjeTemelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 150)
    private String kullanildigiYer;

    private ServisTemelDTO servis;

    private ProjeTemelDTO proje;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKullanildigiYer() {
        return kullanildigiYer;
    }

    public void setKullanildigiYer(String kullanildigiYer) {
        this.kullanildigiYer = kullanildigiYer;
    }

    public ServisTemelDTO getServis() {
        return servis;
    }

    public void setServis(ServisTemelDTO servis) {
        this.servis = servis;
    }

    public ProjeTemelDTO getProje() {
        return proje;
    }

    public void setProje(ProjeTemelDTO proje) {
        this.proje = proje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServisProjeTemelDTO)) {
            return false;
        }

        ServisProjeTemelDTO servisProjeTemelDTO = (ServisProjeTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servisProjeTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServisProjeTemelDTO{" +
            "id=" + getId() +
            ", kullanildigiYer='" + getKullanildigiYer() + "'" +
            ", servis=" + getServis() +
            ", proje=" + getProje() +
            "}";
    }
}
