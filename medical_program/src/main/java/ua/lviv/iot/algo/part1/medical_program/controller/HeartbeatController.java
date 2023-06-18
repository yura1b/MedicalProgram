package ua.lviv.iot.algo.part1.medical_program.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part1.medical_program.business.HeartbeatTrackerService;
import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
@RequestMapping("/heartbeat")
@RestController
public class HeartbeatController {

    @Autowired
    private HeartbeatTrackerService heartbeatTrackerService;


    @GetMapping("/getAll")
    public ResponseEntity<Collection <HeartbeatTracker>> getAllHeartbeatTrackers(){
        Collection<HeartbeatTracker> heartbeatTrackers = heartbeatTrackerService.getAllHeartbeatTrackers();
        if (heartbeatTrackers.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(heartbeatTrackers);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeartbeatTracker> getHeartBeatById(@PathVariable Integer id){
        HeartbeatTracker heartbeatTracker = heartbeatTrackerService.getHeartRateById(id);
        if(heartbeatTracker != null){
            return ResponseEntity.ok(heartbeatTracker);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<HeartbeatTracker> addHeartbeatTracker(@RequestBody HeartbeatTracker heartbeatTracker){
        HeartbeatTracker addedHeartBeatTracker = heartbeatTrackerService.createHeartbeatTracker(heartbeatTracker);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedHeartBeatTracker);
    }
    @Getter
    public static class ControllerUpdateRequest {
        int year;
        String brand;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateHeartbeatTracker(@PathVariable("id") Integer id, @RequestBody HeartbeatController.ControllerUpdateRequest request) {
        heartbeatTrackerService.updateHeartbeatTracker(id, request.getBrand(), request.getYear());
        return ResponseEntity.ok("Tracker updated successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteHeartbeatTracker(@PathVariable Integer id){
        heartbeatTrackerService.deleteHeartbeatTracker(id);
    }
}
