package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProtokolTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProtokolTemel.class);
        ProtokolTemel protokolTemel1 = new ProtokolTemel();
        protokolTemel1.setId(1L);
        ProtokolTemel protokolTemel2 = new ProtokolTemel();
        protokolTemel2.setId(protokolTemel1.getId());
        assertThat(protokolTemel1).isEqualTo(protokolTemel2);
        protokolTemel2.setId(2L);
        assertThat(protokolTemel1).isNotEqualTo(protokolTemel2);
        protokolTemel1.setId(null);
        assertThat(protokolTemel1).isNotEqualTo(protokolTemel2);
    }
}
