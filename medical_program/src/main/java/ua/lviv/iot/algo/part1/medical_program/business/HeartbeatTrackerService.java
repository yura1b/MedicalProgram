package ua.lviv.iot.algo.part1.medical_program.business;

import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Getter
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HeartbeatTrackerService {

    private Map<Integer, HeartbeatTracker> heartbeatData = new HashMap<>();

    private final AtomicInteger heartbeatIdCounter = new AtomicInteger();

    public HeartbeatTracker createHeartbeatTracker(HeartbeatTracker heartbeatTracker) {
        heartbeatTracker.setId(heartbeatIdCounter.incrementAndGet());
        heartbeatData.put(heartbeatTracker.getId(), heartbeatTracker);
        return heartbeatTracker;
    }

    public HeartbeatTracker getHeartRateById(Integer id) {
        return heartbeatData.get(id);
    }

    public Collection<HeartbeatTracker> getAllHeartbeatTrackers() {
        return heartbeatData.values();
    }

    public void updateHeartbeatTracker(Integer id, String brand, int year) {
        HeartbeatTracker heartbeatTracker = heartbeatData.get(id);
        if (heartbeatTracker != null) {
            heartbeatTracker.setYear(year);
            heartbeatTracker.setBrand(brand);
            heartbeatData.put(id, heartbeatTracker);
        } else {
            throw new IllegalArgumentException("HeartbeatTracker not found with ID: " + id);
        }
    }

    public void deleteHeartbeatTracker(Integer id) {
        HeartbeatTracker heartbeatTracker = new HeartbeatTracker();
        heartbeatData.remove(id, heartbeatTracker);
    }
}