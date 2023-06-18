package ua.lviv.iot.algo.part1.medical_program.controller;

import lombok.Getter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part1.medical_program.business.ChamberService;
import ua.lviv.iot.algo.part1.medical_program.business.HospitalService;
import ua.lviv.iot.algo.part1.medical_program.models.Chamber;
import ua.lviv.iot.algo.part1.medical_program.models.Hospital;

import java.util.Collection;
import java.util.Map;
@Controller
public class HospitalController {

    @Autowired
    private ChamberService chamberService;

    @Autowired
    private HospitalService hospitalService;

    private Chamber chamberToAdd = null;


    @GetMapping("/getAll")
    public ResponseEntity<Collection<Hospital>> getAllHospitals(){
        Collection<Hospital> hospitals = hospitalService.getAllHospitals();
        if (hospitals.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(hospitals);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable Integer id){
        Hospital hospital = hospitalService.getHospitalById(id);
        if(hospital != null){
            return ResponseEntity.ok(hospital);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<Hospital> addHospital(@RequestBody Hospital hospital){
        Hospital adeddHospital = hospitalService.createHospital(hospital);
        return ResponseEntity.status(HttpStatus.CREATED).body(adeddHospital);
    }

    @Getter
    public static class ControllerUpdateRequest {
        String address;
        String name;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateHospital(@PathVariable("id") Integer id, @RequestBody HospitalController.ControllerUpdateRequest request) {
        hospitalService.updateHospital(id, request.getAddress(), request.getName());
        return ResponseEntity.ok("Hospital info updated successfully");
    }

    @PostMapping(path = "/getChambersInfo/{idChamber}/{idHospital}")
    public ResponseEntity<String> loadChambersInformationToHospitalMapById(@PathVariable("idChamber") Integer id,
                                                                         @PathVariable("idHospital") Integer hospitalId) {
        Map<Integer, Chamber> chamberMap = chamberService.getChambers();
        try {
           chamberToAdd = chamberMap.get(id);
        } catch (ObjectNotFoundException e){
            ResponseEntity.badRequest();
        }

        hospitalService.loadChamberInformationToHospitalMapById(id, id,chamberToAdd);
        return ResponseEntity.ok("HeartbeatTracker all info loaded successfully");
    }
    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable Integer id){
        hospitalService.deleteHospital(id);
    }
}
