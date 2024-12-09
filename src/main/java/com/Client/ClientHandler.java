package main.java.com.Client;

import main.java.com.Models.User;
import main.java.com.Server.LogManager;
import main.java.com.Utils.ProtocolParser;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome to Emergency Coordination Server!");
            String request;

            while ((request = in.readLine()) != null) {
                String response = ProtocolParser.processRequest(request, this);
                out.println(response);
                LogManager.logEvent("Request: " + request + " | Response: " + response);
            }
        } catch (IOException e) {
            LogManager.logEvent("Connection error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LogManager.logEvent("Socket closure error: " + e.getMessage());
            }
        }
    }

    public PrintWriter getOut() {
        return out;
    }
}
