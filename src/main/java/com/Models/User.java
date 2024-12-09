package main.java.com.Models;

import java.util.UUID;

public class User {
    private String username;
    private String passwordHash;
    private Role role;

    public User(String username, String passwordHash, Role role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}


