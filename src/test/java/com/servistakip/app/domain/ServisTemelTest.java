package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServisTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServisTemel.class);
        ServisTemel servisTemel1 = new ServisTemel();
        servisTemel1.setId(1L);
        ServisTemel servisTemel2 = new ServisTemel();
        servisTemel2.setId(servisTemel1.getId());
        assertThat(servisTemel1).isEqualTo(servisTemel2);
        servisTemel2.setId(2L);
        assertThat(servisTemel1).isNotEqualTo(servisTemel2);
        servisTemel1.setId(null);
        assertThat(servisTemel1).isNotEqualTo(servisTemel2);
    }
}
