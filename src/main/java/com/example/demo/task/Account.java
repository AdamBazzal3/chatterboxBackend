package com.example.demo.task;

public class Account {
    private String id; // Unique identifier for the account
    private String username; // Username for the account
    private String email; // Email associated with the account
    private String password; // Hashed password
    private String displayName; // Name to display in the chat
    private String profilePictureUrl; // URL of the profile picture
    private boolean isActive; // Status of the account (active/inactive)
    private long createdAt; // Timestamp for account creation
    private long lastLogin; // Timestamp for the last login

    public Account(String username, String email, String password, String displayName, String profilePictureUrl, boolean isActive, long createdAt, long lastLogin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.profilePictureUrl = profilePictureUrl;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public Account(String id, String username, String email, String password, String displayName, String profilePictureUrl, boolean isActive, long createdAt, long lastLogin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.profilePictureUrl = profilePictureUrl;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getLastLogin() {
        return lastLogin;
    }
}
