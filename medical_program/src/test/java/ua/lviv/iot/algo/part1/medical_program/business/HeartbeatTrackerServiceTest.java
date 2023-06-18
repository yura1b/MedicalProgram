package ua.lviv.iot.algo.part1.medical_program.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class HeartbeatTrackerServiceTest {
    private HeartbeatTrackerService heartbeatTrackerService;

    @BeforeEach
    void setup() {
        heartbeatTrackerService = new HeartbeatTrackerService();
    }
    @Test
    void testCreateHeartbeatTracker() {

        HeartbeatTracker heartbeatTracker = new HeartbeatTracker();

        HeartbeatTracker createdHeartbeatTracker = heartbeatTrackerService.createHeartbeatTracker(heartbeatTracker);

        assertNotNull(createdHeartbeatTracker.getId());
        assertEquals(1, heartbeatTrackerService.getHeartbeatData().size());
        assertEquals(createdHeartbeatTracker, heartbeatTrackerService.getHeartbeatData().get(createdHeartbeatTracker.getId()));
    }












}