package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IrtibatTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IrtibatTemel.class);
        IrtibatTemel irtibatTemel1 = new IrtibatTemel();
        irtibatTemel1.setId(1L);
        IrtibatTemel irtibatTemel2 = new IrtibatTemel();
        irtibatTemel2.setId(irtibatTemel1.getId());
        assertThat(irtibatTemel1).isEqualTo(irtibatTemel2);
        irtibatTemel2.setId(2L);
        assertThat(irtibatTemel1).isNotEqualTo(irtibatTemel2);
        irtibatTemel1.setId(null);
        assertThat(irtibatTemel1).isNotEqualTo(irtibatTemel2);
    }
}
