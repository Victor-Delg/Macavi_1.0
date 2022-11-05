package com.macavi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.macavi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdFactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdFact.class);
        ProdFact prodFact1 = new ProdFact();
        prodFact1.setId(1L);
        ProdFact prodFact2 = new ProdFact();
        prodFact2.setId(prodFact1.getId());
        assertThat(prodFact1).isEqualTo(prodFact2);
        prodFact2.setId(2L);
        assertThat(prodFact1).isNotEqualTo(prodFact2);
        prodFact1.setId(null);
        assertThat(prodFact1).isNotEqualTo(prodFact2);
    }
}
