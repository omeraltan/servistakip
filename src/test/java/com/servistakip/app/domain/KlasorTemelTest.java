package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KlasorTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KlasorTemel.class);
        KlasorTemel klasorTemel1 = new KlasorTemel();
        klasorTemel1.setId(1L);
        KlasorTemel klasorTemel2 = new KlasorTemel();
        klasorTemel2.setId(klasorTemel1.getId());
        assertThat(klasorTemel1).isEqualTo(klasorTemel2);
        klasorTemel2.setId(2L);
        assertThat(klasorTemel1).isNotEqualTo(klasorTemel2);
        klasorTemel1.setId(null);
        assertThat(klasorTemel1).isNotEqualTo(klasorTemel2);
    }
}
