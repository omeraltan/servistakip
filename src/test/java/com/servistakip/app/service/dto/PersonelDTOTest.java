package com.servistakip.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.servistakip.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonelDTO.class);
        PersonelDTO personelDTO1 = new PersonelDTO();
        personelDTO1.setId(1L);
        PersonelDTO personelDTO2 = new PersonelDTO();
        assertThat(personelDTO1).isNotEqualTo(personelDTO2);
        personelDTO2.setId(personelDTO1.getId());
        assertThat(personelDTO1).isEqualTo(personelDTO2);
        personelDTO2.setId(2L);
        assertThat(personelDTO1).isNotEqualTo(personelDTO2);
        personelDTO1.setId(null);
        assertThat(personelDTO1).isNotEqualTo(personelDTO2);
    }
}
