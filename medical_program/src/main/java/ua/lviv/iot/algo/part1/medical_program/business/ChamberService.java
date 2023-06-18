package ua.lviv.iot.algo.part1.medical_program.business;

import lombok.Getter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.lviv.iot.algo.part1.medical_program.models.Chamber;
import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;
import ua.lviv.iot.algo.part1.medical_program.models.Patient;
import ua.lviv.iot.algo.part1.medical_program.models.PressureTracker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ChamberService {
    private Map<Integer, Chamber> chambers = new HashMap<>();

    private final AtomicInteger chamberIdCounter = new AtomicInteger();

    public Chamber createChamber(Chamber chamber) {
        chamber.setId(chamberIdCounter.incrementAndGet());
        chambers.put(chamber.getId(), chamber);
        return chamber;
    }

    public Chamber getChamberById(Integer id) {
        return chambers.get(id);
    }

    public Collection<Chamber> getAllChamber() {
        return chambers.values();
    }

    public void updateChamber(Integer id, int chamberNumber, int floor, String chamberType) {
        Chamber chamber = chambers.get(id);
        if (chamber != null) {
            chamber.setChamberNumber(chamberNumber);
            chamber.setFloor(floor);
            chamber.setChamberType(chamberType);
            chambers.put(id, chamber);
        } else {
            throw new IllegalArgumentException("Chamber not found with ID: " + id);
        }
    }

    public void deleteChamber(Integer id) {
        Chamber chamber = new Chamber();
        chambers.remove(id, chamber);
    }
    public void loadPatientInformationToChamberMapById(Integer id, Integer patientId, Patient patient){
        Chamber chamber = chambers.get(id);
        if(chamber != null){

            chamber.addPatientToMap(patientId, patient);
            chambers.put(id, chamber);
        } else {
            throw new ObjectNotFoundException(id, "Patient not found with ID: " + id);
        }
    }
}