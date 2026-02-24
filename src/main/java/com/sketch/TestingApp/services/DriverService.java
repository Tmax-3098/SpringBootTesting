package com.sketch.TestingApp.services;

import com.sketch.TestingApp.dto.DriverDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DriverService {

    List<DriverDto> getAllDrivers();
    DriverDto getDriverById(Long id);
    DriverDto createDriver(DriverDto driverDto);
    DriverDto updateDriver(Long id, DriverDto driverDto);
    void deleteDriver(Long id);

}
