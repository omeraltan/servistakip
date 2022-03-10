package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServisProjeTemelMapperTest {

    private ServisProjeTemelMapper servisProjeTemelMapper;

    @BeforeEach
    public void setUp() {
        servisProjeTemelMapper = new ServisProjeTemelMapperImpl();
    }
}
