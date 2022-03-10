package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServisProjeTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServisProjeTemelDTO.class);
        ServisProjeTemelDTO servisProjeTemelDTO1 = new ServisProjeTemelDTO();
        servisProjeTemelDTO1.setId(1L);
        ServisProjeTemelDTO servisProjeTemelDTO2 = new ServisProjeTemelDTO();
        assertThat(servisProjeTemelDTO1).isNotEqualTo(servisProjeTemelDTO2);
        servisProjeTemelDTO2.setId(servisProjeTemelDTO1.getId());
        assertThat(servisProjeTemelDTO1).isEqualTo(servisProjeTemelDTO2);
        servisProjeTemelDTO2.setId(2L);
        assertThat(servisProjeTemelDTO1).isNotEqualTo(servisProjeTemelDTO2);
        servisProjeTemelDTO1.setId(null);
        assertThat(servisProjeTemelDTO1).isNotEqualTo(servisProjeTemelDTO2);
    }
}
