// Object-Oriented/src/main/java/com/example/model/Organizer.java
package com.example.model;

public class Organizer extends User { // Estende User


    public Organizer(int userId, String firstName, String lastName, String email, String username, String password) {
        super(userId, firstName, lastName, email, username, password);
    }


    public Organizer(String firstName, String lastName, String email, String username, String password) {
        super(firstName, lastName, email, username, password);
    }

    // Costruttore vuoto
    public Organizer() {
        super();
    }

    // Non ci sono metodi o attributi specifici per Organizer, oltre a quelli ereditati da User,
    // secondo la traccia. Le sue funzionalità (selezionare giudici, aprire registrazioni)
    // sono azioni che compie attraverso il Controller/DAO, non attributi intrinseci.
}