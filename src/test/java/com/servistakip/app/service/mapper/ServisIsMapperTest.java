package com.servistakip.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServisIsMapperTest {

    private ServisIsMapper servisIsMapper;

    @BeforeEach
    public void setUp() {
        servisIsMapper = new ServisIsMapperImpl();
    }
}
