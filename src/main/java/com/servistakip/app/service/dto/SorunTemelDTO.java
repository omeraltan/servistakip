package com.servistakip.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.SorunTemel} entity.
 */
@Schema(description = "Sorunların ve Çözümlerinin Tanımlanacağı Tablodur.\n@author Ömer ALTAN")
public class SorunTemelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 500)
    private String sorunAciklamasi;

    @NotNull
    private LocalDate sorunTarihi;

    @Size(min = 0, max = 500)
    private String cozumAciklamasi;

    private LocalDate cozumTarihi;

    private ServisTemelDTO servis;

    private PersonelDTO bulan;

    private PersonelDTO cozen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSorunAciklamasi() {
        return sorunAciklamasi;
    }

    public void setSorunAciklamasi(String sorunAciklamasi) {
        this.sorunAciklamasi = sorunAciklamasi;
    }

    public LocalDate getSorunTarihi() {
        return sorunTarihi;
    }

    public void setSorunTarihi(LocalDate sorunTarihi) {
        this.sorunTarihi = sorunTarihi;
    }

    public String getCozumAciklamasi() {
        return cozumAciklamasi;
    }

    public void setCozumAciklamasi(String cozumAciklamasi) {
        this.cozumAciklamasi = cozumAciklamasi;
    }

    public LocalDate getCozumTarihi() {
        return cozumTarihi;
    }

    public void setCozumTarihi(LocalDate cozumTarihi) {
        this.cozumTarihi = cozumTarihi;
    }

    public ServisTemelDTO getServis() {
        return servis;
    }

    public void setServis(ServisTemelDTO servis) {
        this.servis = servis;
    }

    public PersonelDTO getBulan() {
        return bulan;
    }

    public void setBulan(PersonelDTO bulan) {
        this.bulan = bulan;
    }

    public PersonelDTO getCozen() {
        return cozen;
    }

    public void setCozen(PersonelDTO cozen) {
        this.cozen = cozen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SorunTemelDTO)) {
            return false;
        }

        SorunTemelDTO sorunTemelDTO = (SorunTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sorunTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SorunTemelDTO{" +
            "id=" + getId() +
            ", sorunAciklamasi='" + getSorunAciklamasi() + "'" +
            ", sorunTarihi='" + getSorunTarihi() + "'" +
            ", cozumAciklamasi='" + getCozumAciklamasi() + "'" +
            ", cozumTarihi='" + getCozumTarihi() + "'" +
            ", servis=" + getServis() +
            ", bulan=" + getBulan() +
            ", cozen=" + getCozen() +
            "}";
    }
}
