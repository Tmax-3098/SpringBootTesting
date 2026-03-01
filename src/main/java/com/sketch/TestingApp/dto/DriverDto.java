package com.sketch.TestingApp.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDto {

    private Long id;

    private String name;
    private String team;
    private Integer exp;
    private Integer number;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DriverDto driverDto = (DriverDto) o;
        return Objects.equals(id, driverDto.id) && Objects.equals(name, driverDto.name) && Objects.equals(team, driverDto.team) && Objects.equals(exp, driverDto.exp) && Objects.equals(number, driverDto.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, team, exp, number);
    }


}
