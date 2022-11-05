package com.macavi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.macavi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoDniDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDniDTO.class);
        TipoDniDTO tipoDniDTO1 = new TipoDniDTO();
        tipoDniDTO1.setId(1L);
        TipoDniDTO tipoDniDTO2 = new TipoDniDTO();
        assertThat(tipoDniDTO1).isNotEqualTo(tipoDniDTO2);
        tipoDniDTO2.setId(tipoDniDTO1.getId());
        assertThat(tipoDniDTO1).isEqualTo(tipoDniDTO2);
        tipoDniDTO2.setId(2L);
        assertThat(tipoDniDTO1).isNotEqualTo(tipoDniDTO2);
        tipoDniDTO1.setId(null);
        assertThat(tipoDniDTO1).isNotEqualTo(tipoDniDTO2);
    }
}
