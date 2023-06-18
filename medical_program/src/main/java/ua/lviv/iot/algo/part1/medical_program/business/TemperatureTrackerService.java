package ua.lviv.iot.algo.part1.medical_program.business;

import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.lviv.iot.algo.part1.medical_program.models.TemperatureTracker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Getter
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service
public class TemperatureTrackerService {

    private Map<Integer, TemperatureTracker> temperatureData = new HashMap<>();

    private final AtomicInteger temperatureIdCounter = new AtomicInteger();


    public TemperatureTracker createTemperatureTracker(TemperatureTracker temperatureTracker) {
        temperatureTracker.setId(temperatureIdCounter.incrementAndGet());
        temperatureData.put(temperatureTracker.getId(), temperatureTracker);
        return temperatureTracker;
    }

    public TemperatureTracker getTemperatureById(Integer id) {
        return temperatureData.get(id);
    }

    public Collection<TemperatureTracker> getAllTemperatureTracker() {
        return temperatureData.values();
    }

    public void updateTemperatureTracker(Integer id, String brand, int year) {
        TemperatureTracker temperatureTracker = temperatureData.get(id);
        if (temperatureTracker != null) {
            temperatureTracker.setYear(year);
            temperatureTracker.setBrand(brand);
            temperatureData.put(id, temperatureTracker);
        } else {
            throw new IllegalArgumentException("TemperatureTracker not found with ID: " + id);
        }
    }

    public void deleteTemperatureTracker(Integer id) {
        TemperatureTracker temperatureTracker = new TemperatureTracker();
        temperatureData.remove(id, temperatureTracker);
    }
}
