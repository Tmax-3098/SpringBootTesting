package com.sketch.TestingApp.controllers;

import com.sketch.TestingApp.TestContainerConfiguration;
import com.sketch.TestingApp.dto.DriverDto;
import com.sketch.TestingApp.entities.Driver;
import com.sketch.TestingApp.repositories.DriverRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


class DriverControllerTestIT extends AbstractIntegrationTest {

    private Driver testDriver;
    private DriverDto testdDriverDto;

    @BeforeEach
    void setUp(){
        testDriver = Driver.builder()
                .name("Test Driver")
                .team("Test Team")
                .exp(1)
                .number(1)
                .build();

        driverRepo.deleteAll();

    }

    @Test
    void getDriverById_success(){
        Driver savedDriver = driverRepo.save(testDriver);
        webTestClient.get()
                .uri("/{id}", savedDriver.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(DriverDto.class)
                .isEqualTo(modelMapper.map(savedDriver, DriverDto.class));

    }

    @Test
    void getDriverById_fail(){
        webTestClient.get()
                .uri("/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createDriver_success(){
        webTestClient.post()
                .uri("/")
                .bodyValue(modelMapper.map(testDriver, DriverDto.class))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(testDriver.getName())
                .jsonPath("$.team").isEqualTo(testDriver.getTeam());
    }

    @Test
    void createDriver_fail(){
        Driver savedDriver = driverRepo.save(testDriver);
        webTestClient.post()
                .uri("/")
                .bodyValue(modelMapper.map(testDriver, DriverDto.class))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void updateDriver_success(){
        Driver savedDriver = driverRepo.save(testDriver);
        testDriver.setTeam("New Team");
        webTestClient.put()
                .uri("/{id}", savedDriver.getId())
                .bodyValue(modelMapper.map(testDriver, DriverDto.class))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.team").isEqualTo(testDriver.getTeam());
    }

    @Test
    void updateDriver_fail(){
        webTestClient.put()
                .uri("/1")
                .bodyValue(modelMapper.map(testDriver, DriverDto.class))
                .exchange()
                .expectStatus().isNotFound();
    }








}