package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KurumTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KurumTemelDTO.class);
        KurumTemelDTO kurumTemelDTO1 = new KurumTemelDTO();
        kurumTemelDTO1.setId(1L);
        KurumTemelDTO kurumTemelDTO2 = new KurumTemelDTO();
        assertThat(kurumTemelDTO1).isNotEqualTo(kurumTemelDTO2);
        kurumTemelDTO2.setId(kurumTemelDTO1.getId());
        assertThat(kurumTemelDTO1).isEqualTo(kurumTemelDTO2);
        kurumTemelDTO2.setId(2L);
        assertThat(kurumTemelDTO1).isNotEqualTo(kurumTemelDTO2);
        kurumTemelDTO1.setId(null);
        assertThat(kurumTemelDTO1).isNotEqualTo(kurumTemelDTO2);
    }
}
