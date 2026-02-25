package com.sketch.TestingApp.services.impl;

import com.sketch.TestingApp.dto.DriverDto;
import com.sketch.TestingApp.entities.Driver;
import com.sketch.TestingApp.exceptions.ResourceNotFoundException;
import com.sketch.TestingApp.repositories.DriverRepo;
import com.sketch.TestingApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {

    private final DriverRepo repo;
    private final ModelMapper mapper;

    public List<DriverDto> getAllDrivers(){
        return repo.findAll().stream().map(
                driver -> mapper.map(driver, DriverDto.class)).toList();
    }

    public DriverDto getDriverById(Long id){
        log.info("fetch driver with id {}", id);
        Driver driver = repo.findById(id).orElseThrow(() ->{
                log.error("driver with id {} not found", id);
                return new ResourceNotFoundException("Driver does not exist with id "+id);
        });
        log.info("successfully fetched driver with id {}", id);
        return mapper.map(driver, DriverDto.class);
    }

    public DriverDto createDriver(DriverDto driverDto){
        List<Driver> drivers = repo.findByName(driverDto.getName());
        if(!drivers.isEmpty()){
            log.info("driver already exists with name {}", driverDto.getName());
            throw new RuntimeException("driver already exists with name "+driverDto.getName());
        }
        Driver newDriver = mapper.map(driverDto, Driver.class);
        Driver driver = repo.save(newDriver);
        return mapper.map(driver, DriverDto.class);
    }

    public DriverDto updateDriver(Long id, DriverDto driverDto){
        if(!repo.existsById(id)){
            throw new ResourceNotFoundException("Driver does not exists with id "+id);
        }
        Driver driver = mapper.map(driverDto, Driver.class);
        driver.setId(id);
        return mapper.map(repo.save(driver), DriverDto.class);
    }

    public void deleteDriver(Long id){
        if(!repo.existsById(id)){
            throw new ResourceNotFoundException("Driver does not exists with id "+id);
        }
        repo.deleteById(id);
    }
}
