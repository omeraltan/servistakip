package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IrtibatTemelMapperTest {

    private IrtibatTemelMapper irtibatTemelMapper;

    @BeforeEach
    public void setUp() {
        irtibatTemelMapper = new IrtibatTemelMapperImpl();
    }
}
