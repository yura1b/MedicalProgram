package ua.lviv.iot.algo.part1.medical_program.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestMapping;
import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;
import ua.lviv.iot.algo.part1.medical_program.models.Patient;
import ua.lviv.iot.algo.part1.medical_program.models.PressureTracker;
import ua.lviv.iot.algo.part1.medical_program.models.TemperatureTracker;
import ua.lviv.iot.algo.part1.medical_program.writter.PatientWriter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service
public class PatientService {
    private final PatientWriter writer = new PatientWriter();

    private Map<Integer, Patient> patientData = new HashMap<>();

    private final AtomicInteger patientIdCounter = new AtomicInteger();


    public Patient createPatient(Patient patient) {
        patient.setId(patientIdCounter.incrementAndGet());
        patientData.put(patient.getId(), patient);
        return patient;
    }

    public Patient getPatientById(Integer id) {
        return patientData.get(id);
    }

    public Collection<Patient> getAllPatients() {
        return patientData.values();
    }

    public void updatePatient(Integer id, String name, String surname, int age) {
        Patient patient = patientData.get(id);
        if (patientData != null) {
            patient.setName(name);
            patient.setSurname(surname);
            patient.setAge(age);
            patientData.put(id, patient);
        } else {
            throw new IllegalArgumentException("Patient not found with ID: " + id);
        }
    }
    public void writeAllObjectsToCSV(Map<Integer, Patient> patientMap, String filePath) throws IOException {
        writer.writeAllObjectsToCSV(patientMap, filePath);
    }

    public void deletePatient(Integer id) {
        Patient patient = new Patient();
        patientData.remove(id, patient);
    }
    public void loadHeartbeatInformationToPatientMapById(Integer id, Integer heartRateId, HeartbeatTracker heartbeatTracker){
        Patient patient = patientData.get(id);
        if(patient != null){
            patient.addHeartbeatTrackerToMap(heartRateId, heartbeatTracker);
            patientData.put(id, patient);
        } else {
            throw new ObjectNotFoundException(id, "HeartbeatTracker not found with ID: " + id);
        }
    }
    public void loadPressureInformationToPatientMapById(Integer id, Integer pressureId, PressureTracker pressureTracker){
        Patient patient = patientData.get(id);
        if(patient != null){
            patient.addPressureTrackerToMap(pressureId, pressureTracker);
            patientData.put(id, patient);
        } else {
            throw new ObjectNotFoundException(id, "PressureTracker not found with ID: " + id);
        }
    }
    public void loadTemperatureInformationToPatientMapById(Integer id, Integer pressureId, TemperatureTracker temperatureTracker){
        Patient patient = patientData.get(id);
        if(patient != null){
            patient.addTemperatureTrackerToMap(pressureId, temperatureTracker);
            patientData.put(id, patient);
        } else {
            throw new ObjectNotFoundException(id, "TemperatureTracker not found with ID: " + id);
        }
    }
}
