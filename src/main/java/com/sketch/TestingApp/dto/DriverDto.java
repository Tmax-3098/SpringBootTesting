package com.sketch.TestingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {

    private Long id;

    private String name;
    private String team;
    private Integer exp;
    private Integer number;
}
