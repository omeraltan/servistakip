package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KlasorTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KlasorTemelDTO.class);
        KlasorTemelDTO klasorTemelDTO1 = new KlasorTemelDTO();
        klasorTemelDTO1.setId(1L);
        KlasorTemelDTO klasorTemelDTO2 = new KlasorTemelDTO();
        assertThat(klasorTemelDTO1).isNotEqualTo(klasorTemelDTO2);
        klasorTemelDTO2.setId(klasorTemelDTO1.getId());
        assertThat(klasorTemelDTO1).isEqualTo(klasorTemelDTO2);
        klasorTemelDTO2.setId(2L);
        assertThat(klasorTemelDTO1).isNotEqualTo(klasorTemelDTO2);
        klasorTemelDTO1.setId(null);
        assertThat(klasorTemelDTO1).isNotEqualTo(klasorTemelDTO2);
    }
}
