package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonelMapperTest {

    private PersonelMapper personelMapper;

    @BeforeEach
    public void setUp() {
        personelMapper = new PersonelMapperImpl();
    }
}
