package com.servistakip.app.service.dto;

import com.servistakip.app.domain.enumeration.BaglantiSekli;
import com.servistakip.app.domain.enumeration.ServisDurum;
import com.servistakip.app.domain.enumeration.ServisSekli;
import com.servistakip.app.domain.enumeration.ServisTipi;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.ServisTemel} entity.
 */
@Schema(description = "Servislerin Tanımlanacağı Ana Tablodur.\n@author Ömer ALTAN")
public class ServisTemelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 250)
    private String servisAdi;

    @NotNull
    @Size(min = 0, max = 150)
    private String servisUrl;

    @NotNull
    @Size(min = 0, max = 100)
    private String servisVeriTip;

    @NotNull
    @Size(min = 0, max = 500)
    private String aciklamasi;

    @Lob
    private byte[] dosyasi;

    private String dosyasiContentType;

    @Lob
    private byte[] resmi;

    private String resmiContentType;

    @NotNull
    private ServisSekli servisSekli;

    @NotNull
    private ServisTipi servisTipi;

    @NotNull
    private BaglantiSekli baglantiSekli;

    private ServisDurum servisDurum;

    private String anlikSorgu;

    private String yiginVeri;

    private String gds;

    private ProtokolTemelDTO protokol;

    private KurumTemelDTO bakanlik;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServisAdi() {
        return servisAdi;
    }

    public void setServisAdi(String servisAdi) {
        this.servisAdi = servisAdi;
    }

    public String getServisUrl() {
        return servisUrl;
    }

    public void setServisUrl(String servisUrl) {
        this.servisUrl = servisUrl;
    }

    public String getServisVeriTip() {
        return servisVeriTip;
    }

    public void setServisVeriTip(String servisVeriTip) {
        this.servisVeriTip = servisVeriTip;
    }

    public String getAciklamasi() {
        return aciklamasi;
    }

    public void setAciklamasi(String aciklamasi) {
        this.aciklamasi = aciklamasi;
    }

    public byte[] getDosyasi() {
        return dosyasi;
    }

    public void setDosyasi(byte[] dosyasi) {
        this.dosyasi = dosyasi;
    }

    public String getDosyasiContentType() {
        return dosyasiContentType;
    }

    public void setDosyasiContentType(String dosyasiContentType) {
        this.dosyasiContentType = dosyasiContentType;
    }

    public byte[] getResmi() {
        return resmi;
    }

    public void setResmi(byte[] resmi) {
        this.resmi = resmi;
    }

    public String getResmiContentType() {
        return resmiContentType;
    }

    public void setResmiContentType(String resmiContentType) {
        this.resmiContentType = resmiContentType;
    }

    public ServisSekli getServisSekli() {
        return servisSekli;
    }

    public void setServisSekli(ServisSekli servisSekli) {
        this.servisSekli = servisSekli;
    }

    public ServisTipi getServisTipi() {
        return servisTipi;
    }

    public void setServisTipi(ServisTipi servisTipi) {
        this.servisTipi = servisTipi;
    }

    public BaglantiSekli getBaglantiSekli() {
        return baglantiSekli;
    }

    public void setBaglantiSekli(BaglantiSekli baglantiSekli) {
        this.baglantiSekli = baglantiSekli;
    }

    public ServisDurum getServisDurum() {
        return servisDurum;
    }

    public void setServisDurum(ServisDurum servisDurum) {
        this.servisDurum = servisDurum;
    }

    public String getAnlikSorgu() {
        return anlikSorgu;
    }

    public void setAnlikSorgu(String anlikSorgu) {
        this.anlikSorgu = anlikSorgu;
    }

    public String getYiginVeri() {
        return yiginVeri;
    }

    public void setYiginVeri(String yiginVeri) {
        this.yiginVeri = yiginVeri;
    }

    public String getGds() {
        return gds;
    }

    public void setGds(String gds) {
        this.gds = gds;
    }

    public ProtokolTemelDTO getProtokol() {
        return protokol;
    }

    public void setProtokol(ProtokolTemelDTO protokol) {
        this.protokol = protokol;
    }

    public KurumTemelDTO getBakanlik() {
        return bakanlik;
    }

    public void setBakanlik(KurumTemelDTO bakanlik) {
        this.bakanlik = bakanlik;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServisTemelDTO)) {
            return false;
        }

        ServisTemelDTO servisTemelDTO = (ServisTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servisTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServisTemelDTO{" +
            "id=" + getId() +
            ", servisAdi='" + getServisAdi() + "'" +
            ", servisUrl='" + getServisUrl() + "'" +
            ", servisVeriTip='" + getServisVeriTip() + "'" +
            ", aciklamasi='" + getAciklamasi() + "'" +
            ", dosyasi='" + getDosyasi() + "'" +
            ", resmi='" + getResmi() + "'" +
            ", servisSekli='" + getServisSekli() + "'" +
            ", servisTipi='" + getServisTipi() + "'" +
            ", baglantiSekli='" + getBaglantiSekli() + "'" +
            ", servisDurum='" + getServisDurum() + "'" +
            ", anlikSorgu='" + getAnlikSorgu() + "'" +
            ", yiginVeri='" + getYiginVeri() + "'" +
            ", gds='" + getGds() + "'" +
            ", protokol=" + getProtokol() +
            ", bakanlik=" + getBakanlik() +
            "}";
    }
}
