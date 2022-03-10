package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SorunTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SorunTemel.class);
        SorunTemel sorunTemel1 = new SorunTemel();
        sorunTemel1.setId(1L);
        SorunTemel sorunTemel2 = new SorunTemel();
        sorunTemel2.setId(sorunTemel1.getId());
        assertThat(sorunTemel1).isEqualTo(sorunTemel2);
        sorunTemel2.setId(2L);
        assertThat(sorunTemel1).isNotEqualTo(sorunTemel2);
        sorunTemel1.setId(null);
        assertThat(sorunTemel1).isNotEqualTo(sorunTemel2);
    }
}
