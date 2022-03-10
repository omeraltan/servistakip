package com.servistakip.app.domain;

import com.servistakip.app.domain.enumeration.BaglantiSekli;
import com.servistakip.app.domain.enumeration.ServisDurum;
import com.servistakip.app.domain.enumeration.ServisSekli;
import com.servistakip.app.domain.enumeration.ServisTipi;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Servislerin Tanımlanacağı Ana Tablodur.\n@author Ömer ALTAN
 */
@Entity
@Table(name = "servis_temel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServisTemel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 250)
    @Column(name = "servis_adi", length = 250, nullable = false)
    private String servisAdi;

    @NotNull
    @Size(min = 0, max = 150)
    @Column(name = "servis_url", length = 150, nullable = false)
    private String servisUrl;

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "servis_veri_tip", length = 100, nullable = false)
    private String servisVeriTip;

    @NotNull
    @Size(min = 0, max = 500)
    @Column(name = "aciklamasi", length = 500, nullable = false)
    private String aciklamasi;

    @Lob
    @Column(name = "dosyasi")
    private byte[] dosyasi;

    @Column(name = "dosyasi_content_type")
    private String dosyasiContentType;

    @Lob
    @Column(name = "resmi")
    private byte[] resmi;

    @Column(name = "resmi_content_type")
    private String resmiContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "servis_sekli", nullable = false)
    private ServisSekli servisSekli;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "servis_tipi", nullable = false)
    private ServisTipi servisTipi;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "baglanti_sekli", nullable = false)
    private BaglantiSekli baglantiSekli;

    @Enumerated(EnumType.STRING)
    @Column(name = "servis_durum")
    private ServisDurum servisDurum;

    @Column(name = "anlik_sorgu")
    private String anlikSorgu;

    @Column(name = "yigin_veri")
    private String yiginVeri;

    @Column(name = "gds")
    private String gds;

    @OneToOne
    @JoinColumn(unique = true)
    private ProtokolTemel protokol;

    @ManyToOne
    private KurumTemel bakanlik;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServisTemel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServisAdi() {
        return this.servisAdi;
    }

    public ServisTemel servisAdi(String servisAdi) {
        this.setServisAdi(servisAdi);
        return this;
    }

    public void setServisAdi(String servisAdi) {
        this.servisAdi = servisAdi;
    }

    public String getServisUrl() {
        return this.servisUrl;
    }

    public ServisTemel servisUrl(String servisUrl) {
        this.setServisUrl(servisUrl);
        return this;
    }

    public void setServisUrl(String servisUrl) {
        this.servisUrl = servisUrl;
    }

    public String getServisVeriTip() {
        return this.servisVeriTip;
    }

    public ServisTemel servisVeriTip(String servisVeriTip) {
        this.setServisVeriTip(servisVeriTip);
        return this;
    }

    public void setServisVeriTip(String servisVeriTip) {
        this.servisVeriTip = servisVeriTip;
    }

    public String getAciklamasi() {
        return this.aciklamasi;
    }

    public ServisTemel aciklamasi(String aciklamasi) {
        this.setAciklamasi(aciklamasi);
        return this;
    }

    public void setAciklamasi(String aciklamasi) {
        this.aciklamasi = aciklamasi;
    }

    public byte[] getDosyasi() {
        return this.dosyasi;
    }

    public ServisTemel dosyasi(byte[] dosyasi) {
        this.setDosyasi(dosyasi);
        return this;
    }

    public void setDosyasi(byte[] dosyasi) {
        this.dosyasi = dosyasi;
    }

    public String getDosyasiContentType() {
        return this.dosyasiContentType;
    }

    public ServisTemel dosyasiContentType(String dosyasiContentType) {
        this.dosyasiContentType = dosyasiContentType;
        return this;
    }

    public void setDosyasiContentType(String dosyasiContentType) {
        this.dosyasiContentType = dosyasiContentType;
    }

    public byte[] getResmi() {
        return this.resmi;
    }

    public ServisTemel resmi(byte[] resmi) {
        this.setResmi(resmi);
        return this;
    }

    public void setResmi(byte[] resmi) {
        this.resmi = resmi;
    }

    public String getResmiContentType() {
        return this.resmiContentType;
    }

    public ServisTemel resmiContentType(String resmiContentType) {
        this.resmiContentType = resmiContentType;
        return this;
    }

    public void setResmiContentType(String resmiContentType) {
        this.resmiContentType = resmiContentType;
    }

    public ServisSekli getServisSekli() {
        return this.servisSekli;
    }

    public ServisTemel servisSekli(ServisSekli servisSekli) {
        this.setServisSekli(servisSekli);
        return this;
    }

    public void setServisSekli(ServisSekli servisSekli) {
        this.servisSekli = servisSekli;
    }

    public ServisTipi getServisTipi() {
        return this.servisTipi;
    }

    public ServisTemel servisTipi(ServisTipi servisTipi) {
        this.setServisTipi(servisTipi);
        return this;
    }

    public void setServisTipi(ServisTipi servisTipi) {
        this.servisTipi = servisTipi;
    }

    public BaglantiSekli getBaglantiSekli() {
        return this.baglantiSekli;
    }

    public ServisTemel baglantiSekli(BaglantiSekli baglantiSekli) {
        this.setBaglantiSekli(baglantiSekli);
        return this;
    }

    public void setBaglantiSekli(BaglantiSekli baglantiSekli) {
        this.baglantiSekli = baglantiSekli;
    }

    public ServisDurum getServisDurum() {
        return this.servisDurum;
    }

    public ServisTemel servisDurum(ServisDurum servisDurum) {
        this.setServisDurum(servisDurum);
        return this;
    }

    public void setServisDurum(ServisDurum servisDurum) {
        this.servisDurum = servisDurum;
    }

    public String getAnlikSorgu() {
        return this.anlikSorgu;
    }

    public ServisTemel anlikSorgu(String anlikSorgu) {
        this.setAnlikSorgu(anlikSorgu);
        return this;
    }

    public void setAnlikSorgu(String anlikSorgu) {
        this.anlikSorgu = anlikSorgu;
    }

    public String getYiginVeri() {
        return this.yiginVeri;
    }

    public ServisTemel yiginVeri(String yiginVeri) {
        this.setYiginVeri(yiginVeri);
        return this;
    }

    public void setYiginVeri(String yiginVeri) {
        this.yiginVeri = yiginVeri;
    }

    public String getGds() {
        return this.gds;
    }

    public ServisTemel gds(String gds) {
        this.setGds(gds);
        return this;
    }

    public void setGds(String gds) {
        this.gds = gds;
    }

    public ProtokolTemel getProtokol() {
        return this.protokol;
    }

    public void setProtokol(ProtokolTemel protokolTemel) {
        this.protokol = protokolTemel;
    }

    public ServisTemel protokol(ProtokolTemel protokolTemel) {
        this.setProtokol(protokolTemel);
        return this;
    }

    public KurumTemel getBakanlik() {
        return this.bakanlik;
    }

    public void setBakanlik(KurumTemel kurumTemel) {
        this.bakanlik = kurumTemel;
    }

    public ServisTemel bakanlik(KurumTemel kurumTemel) {
        this.setBakanlik(kurumTemel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServisTemel)) {
            return false;
        }
        return id != null && id.equals(((ServisTemel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServisTemel{" +
            "id=" + getId() +
            ", servisAdi='" + getServisAdi() + "'" +
            ", servisUrl='" + getServisUrl() + "'" +
            ", servisVeriTip='" + getServisVeriTip() + "'" +
            ", aciklamasi='" + getAciklamasi() + "'" +
            ", dosyasi='" + getDosyasi() + "'" +
            ", dosyasiContentType='" + getDosyasiContentType() + "'" +
            ", resmi='" + getResmi() + "'" +
            ", resmiContentType='" + getResmiContentType() + "'" +
            ", servisSekli='" + getServisSekli() + "'" +
            ", servisTipi='" + getServisTipi() + "'" +
            ", baglantiSekli='" + getBaglantiSekli() + "'" +
            ", servisDurum='" + getServisDurum() + "'" +
            ", anlikSorgu='" + getAnlikSorgu() + "'" +
            ", yiginVeri='" + getYiginVeri() + "'" +
            ", gds='" + getGds() + "'" +
            "}";
    }
}
