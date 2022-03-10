package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KlasorTemelMapperTest {

    private KlasorTemelMapper klasorTemelMapper;

    @BeforeEach
    public void setUp() {
        klasorTemelMapper = new KlasorTemelMapperImpl();
    }
}
