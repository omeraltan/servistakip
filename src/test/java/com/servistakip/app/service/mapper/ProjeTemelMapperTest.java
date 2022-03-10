package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjeTemelMapperTest {

    private ProjeTemelMapper projeTemelMapper;

    @BeforeEach
    public void setUp() {
        projeTemelMapper = new ProjeTemelMapperImpl();
    }
}
