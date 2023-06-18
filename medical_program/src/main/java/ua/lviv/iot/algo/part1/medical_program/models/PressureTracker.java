package ua.lviv.iot.algo.part1.medical_program.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString (callSuper = true)
public class PressureTracker {
    private Integer id;
    private double pressure;
    private int year;
    private String brand;


    @JsonIgnore
    public String getHeaders(){
        return "pressure" + "year" + "brand";
    }

    @JsonIgnore
    public String toCSV(){
        return pressure + year + brand;
    }
}
