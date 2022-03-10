package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServisIsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServisIs.class);
        ServisIs servisIs1 = new ServisIs();
        servisIs1.setId(1L);
        ServisIs servisIs2 = new ServisIs();
        servisIs2.setId(servisIs1.getId());
        assertThat(servisIs1).isEqualTo(servisIs2);
        servisIs2.setId(2L);
        assertThat(servisIs1).isNotEqualTo(servisIs2);
        servisIs1.setId(null);
        assertThat(servisIs1).isNotEqualTo(servisIs2);
    }
}
