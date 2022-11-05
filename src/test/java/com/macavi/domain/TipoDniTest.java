package com.macavi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.macavi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoDniTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDni.class);
        TipoDni tipoDni1 = new TipoDni();
        tipoDni1.setId(1L);
        TipoDni tipoDni2 = new TipoDni();
        tipoDni2.setId(tipoDni1.getId());
        assertThat(tipoDni1).isEqualTo(tipoDni2);
        tipoDni2.setId(2L);
        assertThat(tipoDni1).isNotEqualTo(tipoDni2);
        tipoDni1.setId(null);
        assertThat(tipoDni1).isNotEqualTo(tipoDni2);
    }
}
