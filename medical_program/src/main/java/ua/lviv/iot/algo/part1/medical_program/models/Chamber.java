package ua.lviv.iot.algo.part1.medical_program.models;

import lombok.*;

import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Chamber {

    private HashMap <Integer, Patient> patientsChamber = new HashMap<>();

    public void addPatientToMap(Integer idOfPatient, Patient patient){
        patientsChamber.put(idOfPatient, patient);
    }

    private Integer id;

    private int chamberNumber;

    private int floor;

    private String chamberType;

}
