package com.servistakip.app.service.dto;

import com.servistakip.app.domain.enumeration.Unvani;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.Personel} entity.
 */
@Schema(description = "Servislerin Sorumlu Personeli ve Sorun Çözen Personelinin Tanımlanacağı Tablodur.\n@author Ömer ALTAN")
public class PersonelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    private String adi;

    @NotNull
    @Size(min = 0, max = 50)
    private String soyadi;

    @NotNull
    @Size(min = 0, max = 15)
    private String telefonu;

    @Size(min = 0, max = 50)
    private String eposta;

    @NotNull
    private Unvani unvani;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getTelefonu() {
        return telefonu;
    }

    public void setTelefonu(String telefonu) {
        this.telefonu = telefonu;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public Unvani getUnvani() {
        return unvani;
    }

    public void setUnvani(Unvani unvani) {
        this.unvani = unvani;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonelDTO)) {
            return false;
        }

        PersonelDTO personelDTO = (PersonelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonelDTO{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", soyadi='" + getSoyadi() + "'" +
            ", telefonu='" + getTelefonu() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", unvani='" + getUnvani() + "'" +
            "}";
    }
}
