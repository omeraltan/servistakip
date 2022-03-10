package com.servistakip.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.ServisIs} entity.
 */
@Schema(description = "Servisi Hazırlayan ve Sorumlusu Bilgileri Tablosu\n@author Ömer ALTAN")
public class ServisIsDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate baslamaTarihi;

    @NotNull
    private LocalDate tamamlamaTarihi;

    private PersonelDTO sorumlu;

    private PersonelDTO yapan;

    private ServisTemelDTO servis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public void setBaslamaTarihi(LocalDate baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public LocalDate getTamamlamaTarihi() {
        return tamamlamaTarihi;
    }

    public void setTamamlamaTarihi(LocalDate tamamlamaTarihi) {
        this.tamamlamaTarihi = tamamlamaTarihi;
    }

    public PersonelDTO getSorumlu() {
        return sorumlu;
    }

    public void setSorumlu(PersonelDTO sorumlu) {
        this.sorumlu = sorumlu;
    }

    public PersonelDTO getYapan() {
        return yapan;
    }

    public void setYapan(PersonelDTO yapan) {
        this.yapan = yapan;
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
        if (!(o instanceof ServisIsDTO)) {
            return false;
        }

        ServisIsDTO servisIsDTO = (ServisIsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servisIsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServisIsDTO{" +
            "id=" + getId() +
            ", baslamaTarihi='" + getBaslamaTarihi() + "'" +
            ", tamamlamaTarihi='" + getTamamlamaTarihi() + "'" +
            ", sorumlu=" + getSorumlu() +
            ", yapan=" + getYapan() +
            ", servis=" + getServis() +
            "}";
    }
}
