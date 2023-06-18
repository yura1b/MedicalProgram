package ua.lviv.iot.algo.part1.medical_program.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString (callSuper = true)
public class HeartbeatTracker{
    private Integer id;
    private int heart_rate;
    private int year;
    private String brand;


    public String getHeaders(){
        return "heart rate" + "year" + "brand";
    }


    public String toCSV(){
        return heart_rate + year + brand;
    }




}
