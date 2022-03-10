package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjeTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjeTemelDTO.class);
        ProjeTemelDTO projeTemelDTO1 = new ProjeTemelDTO();
        projeTemelDTO1.setId(1L);
        ProjeTemelDTO projeTemelDTO2 = new ProjeTemelDTO();
        assertThat(projeTemelDTO1).isNotEqualTo(projeTemelDTO2);
        projeTemelDTO2.setId(projeTemelDTO1.getId());
        assertThat(projeTemelDTO1).isEqualTo(projeTemelDTO2);
        projeTemelDTO2.setId(2L);
        assertThat(projeTemelDTO1).isNotEqualTo(projeTemelDTO2);
        projeTemelDTO1.setId(null);
        assertThat(projeTemelDTO1).isNotEqualTo(projeTemelDTO2);
    }
}
