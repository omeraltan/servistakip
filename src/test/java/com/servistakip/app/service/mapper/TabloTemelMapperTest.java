package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabloTemelMapperTest {

    private TabloTemelMapper tabloTemelMapper;

    @BeforeEach
    public void setUp() {
        tabloTemelMapper = new TabloTemelMapperImpl();
    }
}
