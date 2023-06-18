package ua.lviv.iot.algo.part1.medical_program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;
import ua.lviv.iot.algo.part1.medical_program.models.Patient;
import ua.lviv.iot.algo.part1.medical_program.writter.HeartbeatTrackerWriter;

@SpringBootApplication
@ComponentScan({"ua.lviv.iot.algo.part1.medical_program.business", "ua.lviv.iot.algo.part1.medical_program.controller"})
public class MedicalProgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalProgramApplication.class, args);
		HeartbeatTrackerWriter writer = new HeartbeatTrackerWriter();
		HeartbeatTracker heartbeatTracker = new HeartbeatTracker(1, 245, 2004, "sony");
		writer.saveHeartbeatDate(heartbeatTracker);



	}

}
