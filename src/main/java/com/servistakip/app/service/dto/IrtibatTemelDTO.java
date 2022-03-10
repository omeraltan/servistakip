package com.servistakip.app.service.dto;

import com.servistakip.app.domain.enumeration.Unvani;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.IrtibatTemel} entity.
 */
@Schema(description = "Alınan Servislerin, Personel İrtibat Bilgilerinin Tanımlanacağı Ana Tablodur.\n@author Ömer ALTAN")
public class IrtibatTemelDTO implements Serializable {

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

    @NotNull
    private Integer tcnu;

    @NotNull
    @Size(min = 0, max = 50)
    private String eposta;

    @NotNull
    private Unvani unvani;

    private KurumTemelDTO kurum;

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

    public Integer getTcnu() {
        return tcnu;
    }

    public void setTcnu(Integer tcnu) {
        this.tcnu = tcnu;
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

    public KurumTemelDTO getKurum() {
        return kurum;
    }

    public void setKurum(KurumTemelDTO kurum) {
        this.kurum = kurum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IrtibatTemelDTO)) {
            return false;
        }

        IrtibatTemelDTO irtibatTemelDTO = (IrtibatTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, irtibatTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IrtibatTemelDTO{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", soyadi='" + getSoyadi() + "'" +
            ", telefonu='" + getTelefonu() + "'" +
            ", tcnu=" + getTcnu() +
            ", eposta='" + getEposta() + "'" +
            ", unvani='" + getUnvani() + "'" +
            ", kurum=" + getKurum() +
            "}";
    }
}
