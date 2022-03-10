package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IrtibatTemelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IrtibatTemelDTO.class);
        IrtibatTemelDTO irtibatTemelDTO1 = new IrtibatTemelDTO();
        irtibatTemelDTO1.setId(1L);
        IrtibatTemelDTO irtibatTemelDTO2 = new IrtibatTemelDTO();
        assertThat(irtibatTemelDTO1).isNotEqualTo(irtibatTemelDTO2);
        irtibatTemelDTO2.setId(irtibatTemelDTO1.getId());
        assertThat(irtibatTemelDTO1).isEqualTo(irtibatTemelDTO2);
        irtibatTemelDTO2.setId(2L);
        assertThat(irtibatTemelDTO1).isNotEqualTo(irtibatTemelDTO2);
        irtibatTemelDTO1.setId(null);
        assertThat(irtibatTemelDTO1).isNotEqualTo(irtibatTemelDTO2);
    }
}
