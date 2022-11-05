package com.macavi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.macavi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocateDTO.class);
        LocateDTO locateDTO1 = new LocateDTO();
        locateDTO1.setId(1L);
        LocateDTO locateDTO2 = new LocateDTO();
        assertThat(locateDTO1).isNotEqualTo(locateDTO2);
        locateDTO2.setId(locateDTO1.getId());
        assertThat(locateDTO1).isEqualTo(locateDTO2);
        locateDTO2.setId(2L);
        assertThat(locateDTO1).isNotEqualTo(locateDTO2);
        locateDTO1.setId(null);
        assertThat(locateDTO1).isNotEqualTo(locateDTO2);
    }
}
