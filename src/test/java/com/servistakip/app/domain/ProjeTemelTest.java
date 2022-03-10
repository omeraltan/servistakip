package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjeTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjeTemel.class);
        ProjeTemel projeTemel1 = new ProjeTemel();
        projeTemel1.setId(1L);
        ProjeTemel projeTemel2 = new ProjeTemel();
        projeTemel2.setId(projeTemel1.getId());
        assertThat(projeTemel1).isEqualTo(projeTemel2);
        projeTemel2.setId(2L);
        assertThat(projeTemel1).isNotEqualTo(projeTemel2);
        projeTemel1.setId(null);
        assertThat(projeTemel1).isNotEqualTo(projeTemel2);
    }
}
