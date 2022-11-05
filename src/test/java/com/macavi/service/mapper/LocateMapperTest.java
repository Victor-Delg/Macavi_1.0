package com.macavi.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocateMapperTest {

    private LocateMapper locateMapper;

    @BeforeEach
    public void setUp() {
        locateMapper = new LocateMapperImpl();
    }
}
