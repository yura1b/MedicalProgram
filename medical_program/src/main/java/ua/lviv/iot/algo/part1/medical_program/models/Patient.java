package ua.lviv.iot.algo.part1.medical_program.models;
import lombok.*;

import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    private HashMap<Integer, HeartbeatTracker > heartbeatPatient = new HashMap<>();
    private HashMap<Integer, PressureTracker> pressurePatient = new HashMap<>();
    private HashMap<Integer, TemperatureTracker> temperaturePatient = new HashMap<>();

    public void addHeartbeatTrackerToMap(Integer idOfHeartBeatTracker, HeartbeatTracker heartbeatTracker){
        heartbeatPatient.put(idOfHeartBeatTracker, heartbeatTracker);
    }

    public void addPressureTrackerToMap(Integer idOfPressureTracker, PressureTracker pressureTracker){
        pressurePatient.put(idOfPressureTracker, pressureTracker);
    }

    public void addTemperatureTrackerToMap(Integer idOfTemperatureTracker, TemperatureTracker temperatureTracker){
        temperaturePatient.put(idOfTemperatureTracker, temperatureTracker);
    }

    private String name;

    private String surname;

    private int age;

    private Integer id;

    private int temperatureTrackerId;

    private int pressureTrackerId;

    private int heartbeatTrackerId;

}

