package com.sketch.TestingApp.controllers;

import com.sketch.TestingApp.dto.DriverDto;
import com.sketch.TestingApp.services.impl.DriverServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DriverController {

    private final DriverServiceImpl driverServiceImpl;

    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("ok");
    }

    @GetMapping("all")
    public ResponseEntity<List<DriverDto>> getAllDrivers(){
        return ResponseEntity.ok(driverServiceImpl.getAllDrivers());

    }

    @GetMapping("{id}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable Long id){
        return ResponseEntity.ok(driverServiceImpl.getDriverById(id));
    }

    @PostMapping
    public ResponseEntity<DriverDto> createDriver(@RequestBody DriverDto driver){
        return ResponseEntity.ok(driverServiceImpl.createDriver(driver));
    }

    @PutMapping("{id}")
    public ResponseEntity<DriverDto> updateDriver(@PathVariable Long id, @RequestBody DriverDto driverDto){
        return ResponseEntity.ok(driverServiceImpl.updateDriver(id, driverDto));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable Long id){
        driverServiceImpl.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
