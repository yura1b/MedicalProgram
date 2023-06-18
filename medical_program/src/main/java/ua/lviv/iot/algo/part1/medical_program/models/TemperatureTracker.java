package ua.lviv.iot.algo.part1.medical_program.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString (callSuper = true)

public class TemperatureTracker{
    private Integer id;
    private double body_temperature;
    private int year;
    private String brand;

    @JsonIgnore
    public String getHeaders(){
        return "body temperature" + "year" + "brand";
    }

    @JsonIgnore
    public String toCSV(){
        return body_temperature + year + brand;
    }
}
