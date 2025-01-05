package com.example.demo.account;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(
        name = "account",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "account_email_unique",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "account_username_unique",
                        columnNames = "username"
                )
        }
)
public class Account {
    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id; // Unique identifier for the account

    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String username; // Username for the account

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email; // Email associated with the account

    @Column(
            name = "password",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String password; // Hashed password

    @Column(
            name = "display_name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String fullName; // Name to display in the chat

    @Column(
            name = "profile_picture_url",
            columnDefinition = "TEXT"
    )
    private String profilePictureUrl; // URL of the profile picture

    @Column(
            name = "is_active",
            nullable = false

    )
    private boolean isActive = true;// Status of the account (active/inactive)

    @Column(
            name = "created_at",
            nullable = false
    )
    private long createdAt; // Timestamp for account creation

    @Column(
            name = "last_login"
    )
    private long lastLogin; // Timestamp for the last login

    public Account(){

    }

    public Account(String username,
                   String email,
                   String fullName,
                   String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;

        this.createdAt = new Date().getTime();
    }

    public Account(String username,
                   String email,
                   String password,
                   String fullName,
                   String profilePictureUrl,
                   boolean isActive,
                   long lastLogin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.profilePictureUrl = profilePictureUrl;
        this.isActive = isActive;
        this.lastLogin = lastLogin;

        this.createdAt = new Date().getTime();
    }

    public Account(long id,
                   String username,
                   String email,
                   String password,
                   String fullName,
                   String profilePictureUrl,
                   boolean isActive,
                   long createdAt,
                   long lastLogin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public long getId() {
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

    public String getFullName() {
        return fullName;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", displayName='" + fullName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
