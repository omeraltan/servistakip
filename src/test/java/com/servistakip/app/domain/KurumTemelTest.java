package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KurumTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KurumTemel.class);
        KurumTemel kurumTemel1 = new KurumTemel();
        kurumTemel1.setId(1L);
        KurumTemel kurumTemel2 = new KurumTemel();
        kurumTemel2.setId(kurumTemel1.getId());
        assertThat(kurumTemel1).isEqualTo(kurumTemel2);
        kurumTemel2.setId(2L);
        assertThat(kurumTemel1).isNotEqualTo(kurumTemel2);
        kurumTemel1.setId(null);
        assertThat(kurumTemel1).isNotEqualTo(kurumTemel2);
    }
}
