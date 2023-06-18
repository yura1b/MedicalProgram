package ua.lviv.iot.algo.part1.medical_program.writter;

import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;
import ua.lviv.iot.algo.part1.medical_program.models.Patient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PatientWriter {


        private static final String HEADERS_FOR_PATIENT = "Name, Surname, Age, Id, TemperatureTrackerId, PressureTrackerId, HeartbeatTrackerId";
        private static final String HEADERS_FOR_HEARTBEAT = "id, heart_rate, year, brand";
        private static final String HEADERS_FOR_PRESSURE = "id, pressure, year, brand";
        private static final String HEADERS_FOR_TEMPERATURE = "id, temperature, year, brand";

        public void writeAllObjectsToCSV(Map<Integer, Patient> patientMap, String filePath) throws IOException {
            DateTimeFormatter formatterFile = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate currentDate = LocalDate.now();

            for (Patient patient : patientMap.values()) {
                String filename = patient.getName() + "-" + currentDate.format(formatterFile) + ".csv";
                String pathToFile = filePath + "/" + filename;
                BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));

                writer.write(HEADERS_FOR_PATIENT);

                String line = String.format("%d,%s,%s,%d", patient.getId(), patient.getName(),
                        patient.getSurname(), patient.getAge());
                writer.newLine();
                writer.write(line);
                writer.newLine();
                writer.write("Patients HeartBeatTrackers:");
                writer.newLine();
                writer.write(HEADERS_FOR_HEARTBEAT);
                writer.newLine();
                for (HeartbeatTracker heartbeatTracker : patient.getHeartbeatPatient().values()) {
                    String lineTwo = String.format("%d,%d,%s,%d",
                            heartbeatTracker.getId(),heartbeatTracker.getYear(), heartbeatTracker.getBrand(),
                            heartbeatTracker.getHeart_rate());
                    writer.newLine();
                    writer.write(lineTwo);
                    writer.newLine();

                }
                writer.close();
            }
        }
    }

