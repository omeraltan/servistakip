package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SorunTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SorunTemelDTO.class);
        SorunTemelDTO sorunTemelDTO1 = new SorunTemelDTO();
        sorunTemelDTO1.setId(1L);
        SorunTemelDTO sorunTemelDTO2 = new SorunTemelDTO();
        assertThat(sorunTemelDTO1).isNotEqualTo(sorunTemelDTO2);
        sorunTemelDTO2.setId(sorunTemelDTO1.getId());
        assertThat(sorunTemelDTO1).isEqualTo(sorunTemelDTO2);
        sorunTemelDTO2.setId(2L);
        assertThat(sorunTemelDTO1).isNotEqualTo(sorunTemelDTO2);
        sorunTemelDTO1.setId(null);
        assertThat(sorunTemelDTO1).isNotEqualTo(sorunTemelDTO2);
    }
}
