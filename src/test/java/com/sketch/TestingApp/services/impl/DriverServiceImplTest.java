package com.sketch.TestingApp.services.impl;

import com.sketch.TestingApp.TestContainerConfiguration;
import com.sketch.TestingApp.dto.DriverDto;
import com.sketch.TestingApp.entities.Driver;
import com.sketch.TestingApp.exceptions.ResourceNotFoundException;
import com.sketch.TestingApp.repositories.DriverRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @Mock
    private DriverRepo driverRepo;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private DriverServiceImpl driverService;

    private Driver mockDriver;

    @BeforeEach
    void setUp() {
        mockDriver = Driver.builder()
                .id(1L)
                .name("testdriver")
                .team("testteam")
                .exp(1)
                .number(1)
                .build();

    }

    @Test
    void testGetAllDrivers_WhenADriverExist(){
        when(driverRepo.findAll()).thenReturn(List.of(mockDriver));
        assertThat(driverService.getAllDrivers()).isNotEmpty();
        assertThat(driverService.getAllDrivers()).isNotNull();
    }

    @Test
    void testGetAllDrivers_WhenDriverDoesNotExist(){
        when(driverRepo.findAll()).thenReturn(List.of());
        assertThat(driverService.getAllDrivers()).isNotNull();
        assertThat(driverService.getAllDrivers()).isEmpty();
    }

    @Test
    void testGetDriverById_WhenIdIdCorrect(){
        Long id = mockDriver.getId();
        //ASSIGN
        when(driverRepo.findById(id)).thenReturn(Optional.of(mockDriver)); //stubbing

        //ACT

        DriverDto driverDto = driverService.getDriverById(id);

        //ASSERT
        assertThat(driverDto).isNotNull();
        assertThat(driverDto.getId()).isEqualTo(id);
        assertThat(driverDto.getName()).isEqualTo(mockDriver.getName());
        verify(driverRepo, times(1)).findById(id);
    }


    @Test
    void testGetDriverById_WhenIdNotCorrect(){
        when(driverRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> driverService.getDriverById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Driver does not exist with id 1");
        verify(driverRepo, times(1)).findById(1L);

    }

    @Test
    void testCreateDriver_WhenDriverIsValid(){

        when(driverRepo.findByName(anyString())).thenReturn(List.of());
        when(driverRepo.save(any(Driver.class))).thenReturn(mockDriver);

        DriverDto driverDto = driverService.createDriver(modelMapper.map(mockDriver, DriverDto.class));

        ArgumentCaptor<Driver> captor = ArgumentCaptor.forClass(Driver.class);
        verify(driverRepo).save(captor.capture());

        Driver driver = captor.getValue();
        assertThat(driver.getName()).isEqualTo(mockDriver.getName());

        assertThat(driverDto).isNotNull();
        assertThat(driverDto.getName()).isEqualTo(mockDriver.getName());
        verify(driverRepo).save(any(Driver.class));

    }

    @Test
    void testCreateDriver_WhenDriverIsNotValid(){
        when(driverRepo.findByName(anyString())).thenReturn(List.of(mockDriver));

        assertThatThrownBy(()-> driverService.createDriver(modelMapper.map(mockDriver, DriverDto.class)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("driver already exists with name "+mockDriver.getName());
        verify(driverRepo,times(1)).findByName(mockDriver.getName());
        verify(driverRepo, never()).save(any(Driver.class));
    }

    @Test
    void testUpdateDriver_WhenDriverIdNotFound(){
        when(driverRepo.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> driverService.updateDriver(1L, modelMapper.map(mockDriver, DriverDto.class)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Driver does not exists with id 1");
        verify(driverRepo, times(1)).existsById(1L);
        verify(driverRepo, never()).save(any(Driver.class));
    }

    @Test
    void testUpdateDriver_WhenDriverIdIsValid(){
        when(driverRepo.existsById(anyLong())).thenReturn(true);
        when(driverRepo.save(any(Driver.class))).thenReturn(mockDriver);

        mockDriver.setTeam("updatedteam");
        mockDriver.setName("updatedName");

        DriverDto driverDto = driverService.updateDriver(1L, modelMapper.map(mockDriver, DriverDto.class));
        assertThat(driverDto).isEqualTo(modelMapper.map(mockDriver, DriverDto.class));
        verify(driverRepo, times(1)).save(any(Driver.class));
    }

    @Test
    void testDeleteDriver_WhenDriverDoesNotExist(){
        when(driverRepo.existsById(anyLong())).thenReturn(false);
        assertThatThrownBy(() -> driverService.deleteDriver(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Driver does not exists with id 1");
        verify(driverRepo, times(1)).existsById(1L);
    }

    @Test
    void testDeleteDriver_WhenDriverExists(){
        when(driverRepo.existsById(anyLong())).thenReturn(true);
        assertThatCode(() -> driverService.deleteDriver(1L))
                .doesNotThrowAnyException();
        verify(driverRepo, times(1)).deleteById(1L);
    }

}