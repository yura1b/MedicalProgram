package ua.lviv.iot.algo.part1.medical_program.business;

import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;
import ua.lviv.iot.algo.part1.medical_program.models.PressureTracker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Getter
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service
public class PressureTrackerService {

    private Map<Integer, PressureTracker> pressureData = new HashMap<>();

    private final AtomicInteger pressureIdCounter = new AtomicInteger();


    public PressureTracker createPressureTracker(PressureTracker pressureTracker) {
        pressureTracker.setId(pressureIdCounter.incrementAndGet());
        pressureData.put(pressureTracker.getId(), pressureTracker);
        return pressureTracker;
    }

    public PressureTracker getPressureById(Integer id) {
        return pressureData.get(id);
    }

    public Collection<PressureTracker> getAllPressureTracker() {
        return pressureData.values();
    }

    public void updatePressureTracker(Integer id, String brand, int year) {
        PressureTracker pressureTracker = pressureData.get(id);
        if (pressureTracker != null) {
            pressureTracker.setYear(year);
            pressureTracker.setBrand(brand);
            pressureData.put(id, pressureTracker);
        } else {
            throw new IllegalArgumentException("PressureTracker not found with ID: " + id);
        }
    }

    public void deletePressureTracker(Integer id) {
        PressureTracker pressureTracker = new PressureTracker();
        pressureData.remove(id, pressureTracker);
    }
}
