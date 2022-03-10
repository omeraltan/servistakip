package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetodTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetodTemel.class);
        MetodTemel metodTemel1 = new MetodTemel();
        metodTemel1.setId(1L);
        MetodTemel metodTemel2 = new MetodTemel();
        metodTemel2.setId(metodTemel1.getId());
        assertThat(metodTemel1).isEqualTo(metodTemel2);
        metodTemel2.setId(2L);
        assertThat(metodTemel1).isNotEqualTo(metodTemel2);
        metodTemel1.setId(null);
        assertThat(metodTemel1).isNotEqualTo(metodTemel2);
    }
}
