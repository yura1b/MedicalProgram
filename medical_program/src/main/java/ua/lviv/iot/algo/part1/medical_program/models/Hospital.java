package ua.lviv.iot.algo.part1.medical_program.models;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString (callSuper = true)
@RequestMapping("/hospital")

public class Hospital {
    private HashMap<Integer, Chamber> chambersHospital = new HashMap<>();

    public void addChamberToMap(Integer idOfChamber, Chamber chamber){
        chambersHospital.put(idOfChamber, chamber);
    }
    private Integer id;

    private String address;

    private String name;
}