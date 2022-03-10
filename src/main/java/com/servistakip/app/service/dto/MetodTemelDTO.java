package com.servistakip.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.MetodTemel} entity.
 */
@Schema(description = "Sevislerin Metodları burada tanımlanmaktadır.\n@author Ömer ALTAN")
public class MetodTemelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    private String metodAdi;

    @NotNull
    private Integer metodNu;

    @NotNull
    @Size(min = 0, max = 250)
    private String metodAciklamasi;

    private ServisTemelDTO servis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetodAdi() {
        return metodAdi;
    }

    public void setMetodAdi(String metodAdi) {
        this.metodAdi = metodAdi;
    }

    public Integer getMetodNu() {
        return metodNu;
    }

    public void setMetodNu(Integer metodNu) {
        this.metodNu = metodNu;
    }

    public String getMetodAciklamasi() {
        return metodAciklamasi;
    }

    public void setMetodAciklamasi(String metodAciklamasi) {
        this.metodAciklamasi = metodAciklamasi;
    }

    public ServisTemelDTO getServis() {
        return servis;
    }

    public void setServis(ServisTemelDTO servis) {
        this.servis = servis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetodTemelDTO)) {
            return false;
        }

        MetodTemelDTO metodTemelDTO = (MetodTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metodTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetodTemelDTO{" +
            "id=" + getId() +
            ", metodAdi='" + getMetodAdi() + "'" +
            ", metodNu=" + getMetodNu() +
            ", metodAciklamasi='" + getMetodAciklamasi() + "'" +
            ", servis=" + getServis() +
            "}";
    }
}
