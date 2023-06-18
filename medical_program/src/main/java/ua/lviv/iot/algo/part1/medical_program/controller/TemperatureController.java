package ua.lviv.iot.algo.part1.medical_program.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part1.medical_program.business.PressureTrackerService;
import ua.lviv.iot.algo.part1.medical_program.business.TemperatureTrackerService;
import ua.lviv.iot.algo.part1.medical_program.models.PressureTracker;
import ua.lviv.iot.algo.part1.medical_program.models.TemperatureTracker;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
@RequestMapping("/temperature")
@RestController
public class TemperatureController {

    @Autowired
    private TemperatureTrackerService temperatureTrackerService;


    @GetMapping("/getAll")
    public ResponseEntity<Collection<TemperatureTracker>> getAllTemperatureTrackers(){
        Collection<TemperatureTracker> temperatureTrackers = temperatureTrackerService.getAllTemperatureTracker();
        if (temperatureTrackers.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(temperatureTrackers);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemperatureTracker> getTemperatureById(@PathVariable Integer id){
        TemperatureTracker temperatureTracker = temperatureTrackerService.getTemperatureById(id);
        if(temperatureTracker != null){
            return ResponseEntity.ok(temperatureTracker);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<TemperatureTracker> addTemperatureTracker(@RequestBody TemperatureTracker temperatureTracker){
        TemperatureTracker addedTemperatureTracker = temperatureTrackerService.createTemperatureTracker(temperatureTracker);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTemperatureTracker);
    }
    @Getter
    public static class ControllerUpdateRequest {
        int year;
        String brand;

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTemperatureTracker(@PathVariable("id") Integer id, @RequestBody TemperatureController.ControllerUpdateRequest request) {
        temperatureTrackerService.updateTemperatureTracker(id, request.getBrand(), request.getYear());
        return ResponseEntity.ok("Tracker updated successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteTemperatureTracker(@PathVariable Integer id){
        temperatureTrackerService.deleteTemperatureTracker(id);
    }
}
