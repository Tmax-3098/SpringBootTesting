package com.sketch.TestingApp.repositories;

import com.sketch.TestingApp.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {

    List<Driver> findByTeam(String team);
    List<Driver> findByName(String name);
}
