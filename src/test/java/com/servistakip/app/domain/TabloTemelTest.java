package com.servistakip.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TabloTemelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabloTemel.class);
        TabloTemel tabloTemel1 = new TabloTemel();
        tabloTemel1.setId(1L);
        TabloTemel tabloTemel2 = new TabloTemel();
        tabloTemel2.setId(tabloTemel1.getId());
        assertThat(tabloTemel1).isEqualTo(tabloTemel2);
        tabloTemel2.setId(2L);
        assertThat(tabloTemel1).isNotEqualTo(tabloTemel2);
        tabloTemel1.setId(null);
        assertThat(tabloTemel1).isNotEqualTo(tabloTemel2);
    }
}
