package ua.lviv.iot.algo.part1.medical_program.controller;

import lombok.Getter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part1.medical_program.business.*;
import ua.lviv.iot.algo.part1.medical_program.models.*;

import java.util.Collection;
import java.util.Map;
@RequestMapping("/chamber")
@Controller
public class ChamberController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ChamberService chamberService;

    private Patient patientToAdd = null;


    @GetMapping("/getAll")
    public ResponseEntity<Collection<Chamber>> getAllChambers(){
        Collection<Chamber> chambers = chamberService.getAllChamber();
        if (chambers.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(chambers);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chamber> getChamberById(@PathVariable Integer id){
        Chamber chamber = chamberService.getChamberById(id);
        if(chamber != null){
            return ResponseEntity.ok(chamber);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<Chamber> addChamber(@RequestBody Chamber chamber){
        Chamber addedChamber = chamberService.createChamber(chamber);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedChamber);
    }

    @Getter
    public static class ControllerUpdateRequest {
        int chamberNumber;
        int floor;
        String chamberType;


    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateChamber(@PathVariable("id") Integer id, @RequestBody ChamberController.ControllerUpdateRequest request) {
        chamberService.updateChamber(id, request.getChamberNumber(), request.getFloor(),request.getChamberType());
        return ResponseEntity.ok("Chamber info updated successfully");
    }

    @PostMapping(path = "/getPatientsInfo/{idPatient}/{idChamber}")
    public ResponseEntity<String> loadPatientInformationToChamberMapById(@PathVariable("idPatient") Integer id,
                                                                           @PathVariable("idChamber") Integer chamberId) {
        Map<Integer, Patient> patientMap = patientService.getPatientData();
        try {
            patientToAdd = patientMap.get(id);
        } catch (ObjectNotFoundException e){
            ResponseEntity.badRequest();
        }

        chamberService.loadPatientInformationToChamberMapById(id, id,patientToAdd);
        return ResponseEntity.ok("HeartbeatTracker all info loaded successfully");
    }
    @DeleteMapping("/{id}")
    public void deleteChamber(@PathVariable Integer id){
        chamberService.deleteChamber(id);
    }
}