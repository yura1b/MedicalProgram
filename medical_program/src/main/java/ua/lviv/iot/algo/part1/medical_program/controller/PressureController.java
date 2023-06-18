package ua.lviv.iot.algo.part1.medical_program.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part1.medical_program.business.PressureTrackerService;
import ua.lviv.iot.algo.part1.medical_program.models.PressureTracker;

import java.util.Collection;

@AllArgsConstructor
@RequestMapping("/pressure")
@RestController
public class PressureController {

    @Autowired
    private PressureTrackerService pressureTrackerService;


    @GetMapping("/getAll")
    public ResponseEntity<Collection<PressureTracker>> getAllPressureTrackers(){
        Collection<PressureTracker> pressureTrackers = pressureTrackerService.getAllPressureTracker();
        if (pressureTrackers.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(pressureTrackers);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PressureTracker> getPressureById(@PathVariable Integer id){
        PressureTracker pressureTracker = pressureTrackerService.getPressureById(id);
        if(pressureTracker != null){
            return ResponseEntity.ok(pressureTracker);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<PressureTracker> addPressureTracker(@RequestBody PressureTracker pressureTracker){
        PressureTracker addedPressureTracker = pressureTrackerService.createPressureTracker(pressureTracker);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedPressureTracker);
    }
    @Getter
    public static class ControllerUpdateRequest {
        int year;
        String brand;

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePressureTracker(@PathVariable("id") Integer id, @RequestBody PressureController.ControllerUpdateRequest request) {
        pressureTrackerService.updatePressureTracker(id, request.getBrand(), request.getYear());
        return ResponseEntity.ok("Tracker updated successfully");
    }

    @DeleteMapping("/{id}")
    public void deletePressureTracker(@PathVariable Integer id){
        pressureTrackerService.deletePressureTracker(id);
    }
}

