package com.servistakip.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.KlasorTemel} entity.
 */
@Schema(description = "Belgelerin Yerinin Tanımlanacağı Tablodur.\n@author Ömer ALTAN")
public class KlasorTemelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    private String odasi;

    @NotNull
    @Size(min = 0, max = 50)
    private String dolabi;

    @NotNull
    @Size(min = 0, max = 50)
    private String klasoru;

    @NotNull
    @Size(min = 0, max = 50)
    private String fihristSirasi;

    private ServisTemelDTO servis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOdasi() {
        return odasi;
    }

    public void setOdasi(String odasi) {
        this.odasi = odasi;
    }

    public String getDolabi() {
        return dolabi;
    }

    public void setDolabi(String dolabi) {
        this.dolabi = dolabi;
    }

    public String getKlasoru() {
        return klasoru;
    }

    public void setKlasoru(String klasoru) {
        this.klasoru = klasoru;
    }

    public String getFihristSirasi() {
        return fihristSirasi;
    }

    public void setFihristSirasi(String fihristSirasi) {
        this.fihristSirasi = fihristSirasi;
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
        if (!(o instanceof KlasorTemelDTO)) {
            return false;
        }

        KlasorTemelDTO klasorTemelDTO = (KlasorTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, klasorTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KlasorTemelDTO{" +
            "id=" + getId() +
            ", odasi='" + getOdasi() + "'" +
            ", dolabi='" + getDolabi() + "'" +
            ", klasoru='" + getKlasoru() + "'" +
            ", fihristSirasi='" + getFihristSirasi() + "'" +
            ", servis=" + getServis() +
            "}";
    }
}
