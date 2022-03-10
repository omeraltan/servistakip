package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServisProjeTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServisProjeTemel.class);
        ServisProjeTemel servisProjeTemel1 = new ServisProjeTemel();
        servisProjeTemel1.setId(1L);
        ServisProjeTemel servisProjeTemel2 = new ServisProjeTemel();
        servisProjeTemel2.setId(servisProjeTemel1.getId());
        assertThat(servisProjeTemel1).isEqualTo(servisProjeTemel2);
        servisProjeTemel2.setId(2L);
        assertThat(servisProjeTemel1).isNotEqualTo(servisProjeTemel2);
        servisProjeTemel1.setId(null);
        assertThat(servisProjeTemel1).isNotEqualTo(servisProjeTemel2);
    }
}
