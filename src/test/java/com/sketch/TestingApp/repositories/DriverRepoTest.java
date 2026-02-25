package com.sketch.TestingApp.repositories;

import com.sketch.TestingApp.TestContainerConfiguration;
import com.sketch.TestingApp.entities.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverRepoTest {

    @Autowired
    private DriverRepo repo;

    private Driver driver;

    @BeforeEach
    void setUp(){
        driver = Driver.builder()
                .name("Carlos")
                .team("williams")
                .exp(10)
                .number(45)
                .build();
    }

    @Test
    void testFindByTeam_whenTeamIsValid_thenReturnDrivers() {
        //Arrange
        repo.save(driver);
        //Act
        List<Driver> driverList = repo.findByTeam("williams");
        //Assert
        assertThat(driverList).isNotEmpty();
        assertThat(driverList).isNotNull();
        assertThat(driverList.get(0).getTeam()).isEqualTo("williams");
    }

    @Test
    void testFindByTeam_whenTeamIsNotValid_thenReturnEmptyList(){

        repo.save(driver);
        List<Driver> driverList = repo.findByTeam("redbull");
        assertThat(driverList).isNotNull();
        assertThat(driverList).isEmpty();

    }
}