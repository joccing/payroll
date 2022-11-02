package com.example.payroll;

import com.example.payroll.Controllers.ApiControllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PayrollApplicationTests {

    @Autowired
    private ApiControllers controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
