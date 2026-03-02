package com.sketch.TestingApp.services.impl;

import com.sketch.TestingApp.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "Dev data";
    }
}
