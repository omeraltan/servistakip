package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SorunTemelMapperTest {

    private SorunTemelMapper sorunTemelMapper;

    @BeforeEach
    public void setUp() {
        sorunTemelMapper = new SorunTemelMapperImpl();
    }
}
