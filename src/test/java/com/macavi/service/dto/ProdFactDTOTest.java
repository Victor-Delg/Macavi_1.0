package com.macavi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.macavi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdFactDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdFactDTO.class);
        ProdFactDTO prodFactDTO1 = new ProdFactDTO();
        prodFactDTO1.setId(1L);
        ProdFactDTO prodFactDTO2 = new ProdFactDTO();
        assertThat(prodFactDTO1).isNotEqualTo(prodFactDTO2);
        prodFactDTO2.setId(prodFactDTO1.getId());
        assertThat(prodFactDTO1).isEqualTo(prodFactDTO2);
        prodFactDTO2.setId(2L);
        assertThat(prodFactDTO1).isNotEqualTo(prodFactDTO2);
        prodFactDTO1.setId(null);
        assertThat(prodFactDTO1).isNotEqualTo(prodFactDTO2);
    }
}
