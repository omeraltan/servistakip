package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProtokolTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProtokolTemelDTO.class);
        ProtokolTemelDTO protokolTemelDTO1 = new ProtokolTemelDTO();
        protokolTemelDTO1.setId(1L);
        ProtokolTemelDTO protokolTemelDTO2 = new ProtokolTemelDTO();
        assertThat(protokolTemelDTO1).isNotEqualTo(protokolTemelDTO2);
        protokolTemelDTO2.setId(protokolTemelDTO1.getId());
        assertThat(protokolTemelDTO1).isEqualTo(protokolTemelDTO2);
        protokolTemelDTO2.setId(2L);
        assertThat(protokolTemelDTO1).isNotEqualTo(protokolTemelDTO2);
        protokolTemelDTO1.setId(null);
        assertThat(protokolTemelDTO1).isNotEqualTo(protokolTemelDTO2);
    }
}
