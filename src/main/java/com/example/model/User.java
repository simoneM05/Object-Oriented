package com.example.model;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private int userId;

    protected User(String firstName, String lastName, String email, String password, String username, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        // if(!isValidUsername(username))
        // {
        // throw new CredenzialiNonValide("Username Non Valido");
        // }
        // else
        // {
        // this.username = username;
        // }

        // if(!isValidPassword(password))
        // {
        // throw new CredenzialiNonValide("Password Non Valido");
        // }
        // else
        // {
        // this.password = password;
        // }
        this.userId= userId;

    }

    protected String getFirstName() {
        return firstName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    protected String getLastName() {
        return lastName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected int getUserid() {
        return userId;
    }

    /*
     * POSSIBILI FUNZIONI PER LA GESTIONE DELLA PASSWORD E PER LO USERNAME
     * public boolean isValidUsername(String username){
     * return username != null &&
     * username.matches("come vogliamo gestire il formato??? boooh");
     * }
     * 
     * public boolean isValidPassword(String password){
     * return password != null && password.length() >= 8 &&
     * password.matches("stessa cosa di sopra");
     * }
     */

    // TODO = funzione per registrarsi alla piattaforma

}