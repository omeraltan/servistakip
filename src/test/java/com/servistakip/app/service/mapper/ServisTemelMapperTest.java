package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServisTemelMapperTest {

    private ServisTemelMapper servisTemelMapper;

    @BeforeEach
    public void setUp() {
        servisTemelMapper = new ServisTemelMapperImpl();
    }
}
