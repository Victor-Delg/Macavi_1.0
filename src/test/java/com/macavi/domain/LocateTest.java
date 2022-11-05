package com.macavi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.macavi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locate.class);
        Locate locate1 = new Locate();
        locate1.setId(1L);
        Locate locate2 = new Locate();
        locate2.setId(locate1.getId());
        assertThat(locate1).isEqualTo(locate2);
        locate2.setId(2L);
        assertThat(locate1).isNotEqualTo(locate2);
        locate1.setId(null);
        assertThat(locate1).isNotEqualTo(locate2);
    }
}
