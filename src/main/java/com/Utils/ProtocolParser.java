package main.java.com.Utils;

import main.java.com.Client.ClientHandler;
import main.java.com.Server.AuthenticationManager;

public class ProtocolParser {
    public static String processRequest(String request, Object handler) {
        String[] parts = request.split(" ", 2);
        String command = parts[0].toUpperCase();

        switch (command) {
            case "REGISTER":
                return handleRegister(parts);
            case "LOGIN":
                return handleLogin(parts);
            case "LOGOUT":
                return handleLogout(parts);
            default:
                return "Unknown command.";
        }
    }

    private static String handleRegister(String[] parts) {
        if (parts.length < 2) return "Usage: REGISTER <username>:<password>";
        String[] credentials = parts[1].split(":");
        if (credentials.length != 2) return "Invalid format.";

        return AuthenticationManager.registerUser(credentials[0], credentials[1]);
    }

    private static String handleLogin(String[] parts) {
        if (parts.length < 2) return "Usage: LOGIN <username>:<password>";
        String[] credentials = parts[1].split(":");
        if (credentials.length != 2) return "Invalid format.";

        return AuthenticationManager.loginUser(credentials[0], credentials[1]);
    }

    private static String handleLogout(String[] parts) {
        if (parts.length < 2) return "Usage: LOGOUT <token>";
        return AuthenticationManager.logoutUser(parts[1]);
    }
}

