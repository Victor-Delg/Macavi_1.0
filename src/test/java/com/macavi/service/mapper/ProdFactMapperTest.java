package com.macavi.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdFactMapperTest {

    private ProdFactMapper prodFactMapper;

    @BeforeEach
    public void setUp() {
        prodFactMapper = new ProdFactMapperImpl();
    }
}
