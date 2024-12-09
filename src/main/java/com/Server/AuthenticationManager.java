package main.java.com.Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.Models.Role;
import main.java.com.Models.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class AuthenticationManager {
    private static final String USERS_FILE = "src/DB/Users.json";
    private static final Map<String, User> activeSessions = new HashMap<>();
    private static final Gson gson = new Gson();
    private static List<User> registeredUsers = new ArrayList<>();

    static {
        loadUsersFromFile();
    }

    public static String registerUser(String username, String password) {
        // Check if username is already taken
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                return "Error: Username is already taken.";
            }
        }

        // Hash password and register user
        String passwordHash = hashPassword(password);
        User newUser = new User(username, passwordHash, Role.LOW);
        registeredUsers.add(newUser);
        saveUsersToFile();

        return "User registered successfully!";
    }

    public static String loginUser(String username, String password) {
        // Find user by username
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                // Validate password
                if (user.getPasswordHash().equals(hashPassword(password))) {
                    // Generate session token
                    String token = UUID.randomUUID().toString();
                    activeSessions.put(token, user);
                    return "Login successful! Token: " + token;
                } else {
                    return "Error: Incorrect password.";
                }
            }
        }
        return "Error: User not found.";
    }

    public static User getUserByToken(String token) {
        return activeSessions.get(token);
    }

    public static void logoutUser(String token) {
        activeSessions.remove(token);
    }

    private static void loadUsersFromFile() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            registeredUsers = gson.fromJson(reader, userListType);
            if (registeredUsers == null) {
                registeredUsers = new ArrayList<>();
            }
        } catch (IOException e) {
            registeredUsers = new ArrayList<>();
        }
    }

    private static void saveUsersToFile() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(registeredUsers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}

