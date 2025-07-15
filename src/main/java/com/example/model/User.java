package com.example.model;

public class User {

    protected String email;
    protected String username;
    protected String password;
    protected String first_name;
    protected String last_name;
    protected Role role; // "organizer", "judge", "user"

    public User(String email, String username, String password, String first_name, String last_name) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = Role.USER;
    }

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    // Nuovo costruttore con ruolo
    public User(String email, String username, String password, String first_name, String last_name, Role role) {
        this(email, username, password, first_name, last_name);
        this.role = role;
    }

    // Getter e setter per role
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}