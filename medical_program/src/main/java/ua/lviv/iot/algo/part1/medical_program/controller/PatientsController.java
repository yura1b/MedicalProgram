package ua.lviv.iot.algo.part1.medical_program.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part1.medical_program.business.HeartbeatTrackerService;
import ua.lviv.iot.algo.part1.medical_program.business.PatientService;
import ua.lviv.iot.algo.part1.medical_program.business.PressureTrackerService;
import ua.lviv.iot.algo.part1.medical_program.business.TemperatureTrackerService;
import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;
import ua.lviv.iot.algo.part1.medical_program.models.Patient;
import ua.lviv.iot.algo.part1.medical_program.models.PressureTracker;
import ua.lviv.iot.algo.part1.medical_program.models.TemperatureTracker;

import java.util.Collection;
import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("/patient")
@RestController
public class PatientsController {

    @Autowired
    private TemperatureTrackerService temperatureTrackerService;

    @Autowired
    private PressureTrackerService pressureTrackerService;

    @Autowired
    private HeartbeatTrackerService heartbeatTrackerService;

    @Autowired
    private PatientService patientService;


    private HeartbeatTracker heartbeatTrackerToAdd = null;

    private PressureTracker pressureTrackerToAdd = null;

    private TemperatureTracker temperatureTrackerToAdd = null;

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Patient>> getAllPatients(){
        Collection<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(patients);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer id){
        Patient patient = patientService.getPatientById(id);
        if(patient != null){
            return ResponseEntity.ok(patient);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient){
        Patient addedPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedPatient);
    }

    @Getter
    public static class ControllerUpdateRequest {
        String name;
        String surname;
        int age;

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable("id") Integer id, @RequestBody PatientsController.ControllerUpdateRequest request) {
        patientService.updatePatient(id, request.getName(), request.getSurname(),request.getAge());
        return ResponseEntity.ok("Patient info updated successfully");
    }

    @PostMapping(path = "/getHeartbeatTrackerInfo/{idTracker}/{idPatient}")
    public ResponseEntity<String> loadHeartbeatInformationToPatientMapById(@PathVariable("idTracker") Integer id,
                                                                                  @PathVariable("idPatient") Integer patientId) {
        Map<Integer, HeartbeatTracker> heartbeatTrackerMap = heartbeatTrackerService.getHeartbeatData();
        try {
            heartbeatTrackerToAdd = heartbeatTrackerMap.get(id);
        } catch (ObjectNotFoundException e){
            ResponseEntity.badRequest();
        }
        patientService.loadHeartbeatInformationToPatientMapById(patientId, id, heartbeatTrackerToAdd);
        return ResponseEntity.ok("HeartbeatTracker all info loaded successfully");
    }
    @PostMapping(path = "/getPressureTrackerInfo/{idTracker}/{idPatient}")
    public ResponseEntity<String> loadPressureInformationToPatientMapById(@PathVariable("idTracker") Integer id,
                                                                           @PathVariable("idPatient") Integer patientId) {
        Map<Integer, PressureTracker> pressureTrackerMap = pressureTrackerService.getPressureData();
        try {
            pressureTrackerToAdd = pressureTrackerMap.get(id);
        } catch (ObjectNotFoundException e){
            ResponseEntity.badRequest();
        }

        patientService.loadPressureInformationToPatientMapById(patientId, id, pressureTrackerToAdd);
        return ResponseEntity.ok("PressureTracker all info loaded successfully");
    }
    @PostMapping(path = "/getTemperatureTrackerInfo/{idTracker}/{idPatient}")
    public ResponseEntity<String> loadTemperatureInformationToPatientMapById(@PathVariable("idTracker") Integer id,
                                                                          @PathVariable("idPatient") Integer patientId) {
        Map<Integer, TemperatureTracker> temperatureTrackerMap = temperatureTrackerService.getTemperatureData();
        try {
            temperatureTrackerToAdd = temperatureTrackerMap.get(id);
        } catch (ObjectNotFoundException e){
            ResponseEntity.badRequest();
        }

        patientService.loadTemperatureInformationToPatientMapById(patientId, id, temperatureTrackerToAdd);
        return ResponseEntity.ok("TemperatureTracker all info loaded successfully");
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Integer id){
        patientService.deletePatient(id);
    }

    @PostMapping(path = "/export")
    public ResponseEntity<String> writeAllObjectsToCSV(@RequestParam("folderpath") String folderpath){
        Map<Integer, Patient> patientMap = patientService.getPatientData();
        try {
            patientService.writeAllObjectsToCSV(patientMap, folderpath);
            return ResponseEntity.ok("All clients files were created");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}
