package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KurumTemelMapperTest {

    private KurumTemelMapper kurumTemelMapper;

    @BeforeEach
    public void setUp() {
        kurumTemelMapper = new KurumTemelMapperImpl();
    }
}
