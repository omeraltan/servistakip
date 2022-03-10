package com.servistakip.app.service.dto;

import com.servistakip.app.domain.enumeration.SureDurum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.ProtokolTemel} entity.
 */
@Schema(description = "Servislerin Protokollerinin Tanımlanacağı Ana Tablodur.\n@author Ömer ALTAN")
public class ProtokolTemelDTO implements Serializable {

    private Long id;

    @NotNull
    private SureDurum sureDurum;

    private LocalDate sureBitisTarihi;

    @NotNull
    @Size(min = 0, max = 250)
    private String protokolNu;

    @NotNull
    @Size(min = 0, max = 250)
    private String olurYaziNu;

    @NotNull
    private LocalDate protokolImzaTarihi;

    @Size(min = 0, max = 500)
    private String protokolAciklamasi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SureDurum getSureDurum() {
        return sureDurum;
    }

    public void setSureDurum(SureDurum sureDurum) {
        this.sureDurum = sureDurum;
    }

    public LocalDate getSureBitisTarihi() {
        return sureBitisTarihi;
    }

    public void setSureBitisTarihi(LocalDate sureBitisTarihi) {
        this.sureBitisTarihi = sureBitisTarihi;
    }

    public String getProtokolNu() {
        return protokolNu;
    }

    public void setProtokolNu(String protokolNu) {
        this.protokolNu = protokolNu;
    }

    public String getOlurYaziNu() {
        return olurYaziNu;
    }

    public void setOlurYaziNu(String olurYaziNu) {
        this.olurYaziNu = olurYaziNu;
    }

    public LocalDate getProtokolImzaTarihi() {
        return protokolImzaTarihi;
    }

    public void setProtokolImzaTarihi(LocalDate protokolImzaTarihi) {
        this.protokolImzaTarihi = protokolImzaTarihi;
    }

    public String getProtokolAciklamasi() {
        return protokolAciklamasi;
    }

    public void setProtokolAciklamasi(String protokolAciklamasi) {
        this.protokolAciklamasi = protokolAciklamasi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProtokolTemelDTO)) {
            return false;
        }

        ProtokolTemelDTO protokolTemelDTO = (ProtokolTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, protokolTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProtokolTemelDTO{" +
            "id=" + getId() +
            ", sureDurum='" + getSureDurum() + "'" +
            ", sureBitisTarihi='" + getSureBitisTarihi() + "'" +
            ", protokolNu='" + getProtokolNu() + "'" +
            ", olurYaziNu='" + getOlurYaziNu() + "'" +
            ", protokolImzaTarihi='" + getProtokolImzaTarihi() + "'" +
            ", protokolAciklamasi='" + getProtokolAciklamasi() + "'" +
            "}";
    }
}
