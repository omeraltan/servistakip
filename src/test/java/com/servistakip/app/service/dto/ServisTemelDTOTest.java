package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServisTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServisTemelDTO.class);
        ServisTemelDTO servisTemelDTO1 = new ServisTemelDTO();
        servisTemelDTO1.setId(1L);
        ServisTemelDTO servisTemelDTO2 = new ServisTemelDTO();
        assertThat(servisTemelDTO1).isNotEqualTo(servisTemelDTO2);
        servisTemelDTO2.setId(servisTemelDTO1.getId());
        assertThat(servisTemelDTO1).isEqualTo(servisTemelDTO2);
        servisTemelDTO2.setId(2L);
        assertThat(servisTemelDTO1).isNotEqualTo(servisTemelDTO2);
        servisTemelDTO1.setId(null);
        assertThat(servisTemelDTO1).isNotEqualTo(servisTemelDTO2);
    }
}
