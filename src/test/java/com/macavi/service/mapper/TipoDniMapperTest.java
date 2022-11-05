package com.macavi.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoDniMapperTest {

    private TipoDniMapper tipoDniMapper;

    @BeforeEach
    public void setUp() {
        tipoDniMapper = new TipoDniMapperImpl();
    }
}
