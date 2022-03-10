package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetodTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetodTemelDTO.class);
        MetodTemelDTO metodTemelDTO1 = new MetodTemelDTO();
        metodTemelDTO1.setId(1L);
        MetodTemelDTO metodTemelDTO2 = new MetodTemelDTO();
        assertThat(metodTemelDTO1).isNotEqualTo(metodTemelDTO2);
        metodTemelDTO2.setId(metodTemelDTO1.getId());
        assertThat(metodTemelDTO1).isEqualTo(metodTemelDTO2);
        metodTemelDTO2.setId(2L);
        assertThat(metodTemelDTO1).isNotEqualTo(metodTemelDTO2);
        metodTemelDTO1.setId(null);
        assertThat(metodTemelDTO1).isNotEqualTo(metodTemelDTO2);
    }
}
