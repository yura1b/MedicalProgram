package ua.lviv.iot.algo.part1.medical_program.business;

import lombok.Getter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.lviv.iot.algo.part1.medical_program.models.Chamber;
import ua.lviv.iot.algo.part1.medical_program.models.Hospital;
import ua.lviv.iot.algo.part1.medical_program.models.Patient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HospitalService {

    private Map<Integer, Hospital> hospitals = new HashMap<>();

    private final AtomicInteger hospitalIdCounter = new AtomicInteger();

    public Hospital createHospital(Hospital hospital) {
        hospital.setId(hospitalIdCounter.incrementAndGet());
        hospitals.put(hospital.getId(), hospital);
        return hospital;
    }

    public Hospital getHospitalById(Integer id) {
        return hospitals.get(id);
    }

    public Collection<Hospital> getAllHospitals() {
        return hospitals.values();
    }

    public void updateHospital(Integer id,  String address, String name) {
        Hospital hospital = hospitals.get(id);
        if (hospital != null) {
            hospital.setAddress(address);
            hospital.setName(name);
            hospitals.put(id, hospital);
        } else {
            throw new IllegalArgumentException("Chamber not found with ID: " + id);
        }
    }

    public void deleteHospital(Integer id) {
        Hospital hospital = new Hospital();
        hospitals.remove(id, hospital);
    }
    public void loadChamberInformationToHospitalMapById(Integer id, Integer hospitalId, Chamber chamber){
        Hospital hospital = hospitals.get(id);
        if(hospital != null){
            hospital.addChamberToMap(hospitalId, chamber);
            hospitals.put(id, hospital);
        } else {
            throw new ObjectNotFoundException(id, "Chamber not found with ID: " + id);
        }
    }

}
