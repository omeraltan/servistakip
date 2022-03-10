package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TabloTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabloTemelDTO.class);
        TabloTemelDTO tabloTemelDTO1 = new TabloTemelDTO();
        tabloTemelDTO1.setId(1L);
        TabloTemelDTO tabloTemelDTO2 = new TabloTemelDTO();
        assertThat(tabloTemelDTO1).isNotEqualTo(tabloTemelDTO2);
        tabloTemelDTO2.setId(tabloTemelDTO1.getId());
        assertThat(tabloTemelDTO1).isEqualTo(tabloTemelDTO2);
        tabloTemelDTO2.setId(2L);
        assertThat(tabloTemelDTO1).isNotEqualTo(tabloTemelDTO2);
        tabloTemelDTO1.setId(null);
        assertThat(tabloTemelDTO1).isNotEqualTo(tabloTemelDTO2);
    }
}
