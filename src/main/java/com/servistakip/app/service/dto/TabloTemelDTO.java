package com.servistakip.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.servistakip.app.domain.TabloTemel} entity.
 */
@Schema(description = "Log Tablolarının Tanımlanacağı Tablodur.\n@author Ömer ALTAN")
public class TabloTemelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 100)
    private String gercekVeritabani;

    @NotNull
    @Size(min = 0, max = 100)
    private String gercekLogTablo;

    @Size(min = 0, max = 500)
    private String gercekaciklamasi;

    @NotNull
    @Size(min = 0, max = 100)
    private String testVeritabani;

    @NotNull
    @Size(min = 0, max = 100)
    private String testLogTablo;

    @Size(min = 0, max = 500)
    private String testaciklamasi;

    private ServisTemelDTO servis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGercekVeritabani() {
        return gercekVeritabani;
    }

    public void setGercekVeritabani(String gercekVeritabani) {
        this.gercekVeritabani = gercekVeritabani;
    }

    public String getGercekLogTablo() {
        return gercekLogTablo;
    }

    public void setGercekLogTablo(String gercekLogTablo) {
        this.gercekLogTablo = gercekLogTablo;
    }

    public String getGercekaciklamasi() {
        return gercekaciklamasi;
    }

    public void setGercekaciklamasi(String gercekaciklamasi) {
        this.gercekaciklamasi = gercekaciklamasi;
    }

    public String getTestVeritabani() {
        return testVeritabani;
    }

    public void setTestVeritabani(String testVeritabani) {
        this.testVeritabani = testVeritabani;
    }

    public String getTestLogTablo() {
        return testLogTablo;
    }

    public void setTestLogTablo(String testLogTablo) {
        this.testLogTablo = testLogTablo;
    }

    public String getTestaciklamasi() {
        return testaciklamasi;
    }

    public void setTestaciklamasi(String testaciklamasi) {
        this.testaciklamasi = testaciklamasi;
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
        if (!(o instanceof TabloTemelDTO)) {
            return false;
        }

        TabloTemelDTO tabloTemelDTO = (TabloTemelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tabloTemelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TabloTemelDTO{" +
            "id=" + getId() +
            ", gercekVeritabani='" + getGercekVeritabani() + "'" +
            ", gercekLogTablo='" + getGercekLogTablo() + "'" +
            ", gercekaciklamasi='" + getGercekaciklamasi() + "'" +
            ", testVeritabani='" + getTestVeritabani() + "'" +
            ", testLogTablo='" + getTestLogTablo() + "'" +
            ", testaciklamasi='" + getTestaciklamasi() + "'" +
            ", servis=" + getServis() +
            "}";
    }
}
