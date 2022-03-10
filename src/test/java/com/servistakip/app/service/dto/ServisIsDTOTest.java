package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServisIsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServisIsDTO.class);
        ServisIsDTO servisIsDTO1 = new ServisIsDTO();
        servisIsDTO1.setId(1L);
        ServisIsDTO servisIsDTO2 = new ServisIsDTO();
        assertThat(servisIsDTO1).isNotEqualTo(servisIsDTO2);
        servisIsDTO2.setId(servisIsDTO1.getId());
        assertThat(servisIsDTO1).isEqualTo(servisIsDTO2);
        servisIsDTO2.setId(2L);
        assertThat(servisIsDTO1).isNotEqualTo(servisIsDTO2);
        servisIsDTO1.setId(null);
        assertThat(servisIsDTO1).isNotEqualTo(servisIsDTO2);
    }
}
