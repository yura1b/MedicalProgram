package ua.lviv.iot.algo.part1.medical_program.writter;

import ua.lviv.iot.algo.part1.medical_program.models.HeartbeatTracker;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class HeartbeatTrackerWriter {

    private static final String FILE_EXTENSION = ".csv";

    private static final String FILE_PREFIX = "HeartBeatData:";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            "'" + FILE_PREFIX + "'yyyy-MM-dd'" + FILE_EXTENSION + "'");

    private final String fileName;

    public HeartbeatTrackerWriter(){
        this.fileName = generateFileName();
    }

    private String generateFileName() {
        String currentDataFile = LocalDate.now().format(FORMATTER);
        return currentDataFile;
    }

    public void saveHeartbeatDate(HeartbeatTracker heartbeatTracker){
        try {
            File file = new File(fileName);
            boolean fileExists = file.exists();
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, true));

            if (!fileExists){
                writer.println(heartbeatTracker.getHeaders());
            }

            writer.write(heartbeatTracker.toCSV());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void deleteHeartbeatDate (){
        try {

            HeartbeatTracker heartbeatTracker = new HeartbeatTracker();
            File folder = new File(".");
            File[] files = folder.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
            LocalDate currentDate = LocalDate.now();

            for (File file : files) {
                String fileName = file.getName();
                if (fileName.startsWith(FILE_PREFIX)&& fileName.endsWith(FILE_EXTENSION)){
                    LocalDate fileDate = LocalDate.parse(fileName, FORMATTER);

                    if (fileDate.getMonth().equals(currentDate.getMonth()) && fileDate.getYear() == (currentDate.getYear())){

                        File inputFile = new File(fileName);
                        File outputFile = new File("output.csv");

                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));

                        String currentLine;
                        boolean heartbeatTrackerDeleted = false;

                        while ((currentLine = reader.readLine())!=null){
                            String[] fields = currentLine.split(",");
                            if ((fields.length > 0) && !fields[0].isEmpty()){
                                String idValue = fields[0].trim();
                                if (!idValue.equals("id")){
                                    Integer id = Integer.parseInt(idValue);
                                    if(id.equals(heartbeatTracker.getId())){
                                        heartbeatTrackerDeleted = true;
                                        continue;
                                    }
                                }
                                writer.println(currentLine);

                            }
                            writer.close();
                            reader.close();

                            String inputFileName = inputFile.getName();

                            if (heartbeatTrackerDeleted && inputFile.delete()){
                                if (!outputFile.renameTo(new File(inputFileName))){
                                    System.out.println("Fail to delete song ");
                                }
                                else {
                                    System.out.println("Delete successful");
                                    break;
                                }

                            }

                        }
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readHeartBeatData() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = fileReader.readLine()) != null){

        }


    }
    public void updateHeartbeatData(HeartbeatTracker heartbeatTracker, Integer id){
        File file = new File(fileName);
        try {
            File tempFile = new File("temFileName");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null){
                String[] parts = currentLine.split(",");
                int currentId = Integer.parseInt(parts[0]);
                if(currentId == id ){
                    String updatedLine = id + "," + heartbeatTracker.getBrand()+","+heartbeatTracker.getYear()
                            +","+heartbeatTracker.getHeart_rate();
                    bufferedWriter.write(currentLine);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
            bufferedReader.close();
            if (file.delete()){
                tempFile.renameTo(file);
            }
            else {
                System.out.println("First file delete failed");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
