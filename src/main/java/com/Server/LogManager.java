package main.java.com.Server;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LogManager {
    private static FileWriter logWriter;

    public static void initializeLogs(LocalDate date) {
        String logFileName = "logs/ServerLogs_" + date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt";
        try {
            Files.createDirectories(Path.of("logs"));
            logWriter = new FileWriter(logFileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void logEvent(String event) {
        try {
            LocalDate now = LocalDate.now();
            if (!logWriter.toString().contains(now.toString())) {
                initializeLogs(now);
            }

            logWriter.write(event + "\n");
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

