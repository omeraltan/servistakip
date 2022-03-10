package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProtokolTemelMapperTest {

    private ProtokolTemelMapper protokolTemelMapper;

    @BeforeEach
    public void setUp() {
        protokolTemelMapper = new ProtokolTemelMapperImpl();
    }
}
