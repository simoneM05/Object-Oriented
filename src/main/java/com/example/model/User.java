// Object-Oriented/src/main/java/com/example/model/User.java
package com.example.model;

public class User {
    private int userId; // Mantenuto per compatibilità, ma email sarà la PK nel DB
    private String firstName; // Corrisponde a first_name nel DB
    private String lastName; // Corrisponde a last_name nel DB
    private String email; // Corrisponde a email PRIMARY KEY nel DB
    private String username; // Corrisponde a username UNIQUE NOT NULL nel DB
    private String password; // Corrisponde a password NOT NULL nel DB

    // Costruttore completo che include userId (se usato nel sistema)
    public User(int userId, String firstName, String lastName, String email, String username, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Costruttore senza userId (per nuovi utenti, l'ID verrà gestito dal DAO/DB)
    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}