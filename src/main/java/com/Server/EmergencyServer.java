package main.java.com.Server;

import main.java.com.Client.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class EmergencyServer {
    private static final int PORT = 12345;
    private static LocalDate currentLogDate;

    public static void main(String[] args) {
        currentLogDate = LocalDate.now();
        LogManager.initializeLogs(currentLogDate);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LogManager.logEvent("New connection from: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            LogManager.logEvent("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
