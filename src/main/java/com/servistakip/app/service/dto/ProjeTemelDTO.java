package com.servistakip.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.ProjeTemel} entity.
 */
@Schema(description = "Servisleri Kullanan Projelerin Tanımlandığı Yer\n@author Ömer ALTAN")
public class ProjeTemelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 100)
    private String projedi;

    @Size(min = 0, max = 50)
    private String projeKodu;

    @NotNull
    @Size(min = 0, max = 150)
    private String aciklama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjedi() {
        return projedi;
    }

    public void setProjedi(String projedi) {
        this.projedi = projedi;
    }

    public String getProjeKodu() {
        return projeKodu;
    }

    public void setProjeKodu(String projeKodu) {
        this.projeKodu = projeKodu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjeTemelDTO)) {
            return false;
        }

        ProjeTemelDTO projeTemelDTO = (ProjeTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projeTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjeTemelDTO{" +
            "id=" + getId() +
            ", projedi='" + getProjedi() + "'" +
            ", projeKodu='" + getProjeKodu() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            "}";
    }
}
