package com.sketch.TestingApp.controllers;

import com.sketch.TestingApp.TestContainerConfiguration;
import com.sketch.TestingApp.repositories.DriverRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public class AbstractIntegrationTest {
    @Autowired
    protected WebTestClient webTestClient;


    @Autowired
    protected DriverRepo driverRepo;

    @Autowired
    protected ModelMapper modelMapper;
}
