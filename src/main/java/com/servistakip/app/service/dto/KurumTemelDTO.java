package com.servistakip.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.KurumTemel} entity.
 */
@Schema(description = "Kurumların Tanımlanacağı Tablodur.\n@author Ömer ALTAN")
public class KurumTemelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 100)
    private String bakanlikAdi;

    @NotNull
    @Size(min = 0, max = 100)
    private String birimAdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBakanlikAdi() {
        return bakanlikAdi;
    }

    public void setBakanlikAdi(String bakanlikAdi) {
        this.bakanlikAdi = bakanlikAdi;
    }

    public String getBirimAdi() {
        return birimAdi;
    }

    public void setBirimAdi(String birimAdi) {
        this.birimAdi = birimAdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KurumTemelDTO)) {
            return false;
        }

        KurumTemelDTO kurumTemelDTO = (KurumTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, kurumTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KurumTemelDTO{" +
            "id=" + getId() +
            ", bakanlikAdi='" + getBakanlikAdi() + "'" +
            ", birimAdi='" + getBirimAdi() + "'" +
            "}";
    }
}
