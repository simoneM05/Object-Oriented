package com.example.dao;

import com.example.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    boolean save(User user);

    boolean update(User user);

    boolean deleteByEmail(String email);

}

// Riassunto del
// lavoro fatto
// finora Hai
// definito la
// struttura del
// database PostgreSQL
// con tutte
// le tabelle
// per gestire
// utenti,organizzatori,giudici,hackathon,team,partecipanti,documenti,
// richieste e
// voti.

// Tabelle con
// chiavi primarie, esterne
// e vincoli.

// Inserito anche
// un set
// di dati
// di esempio.

// Mi hai
// chiesto di
// aiutarti a
// implementare le
// DAO in
// Java per
// interagire con
// questo database.

// Ti ho
// confermato che
// la cosa
// migliore è
// creare interfacce

// DAO (senza implementazioni per ora), così puoi strutturare bene il codice e
// implementare le query quando vuoi.

// Mi hai fornito il modello User in Java con attributi e metodi getter/setter,
// così posso fare un esempio concreto di DAO.

// Ti ho fornito l’interfaccia DAO per User, con i metodi essenziali
// (findByEmail, findByUsername, findAll, save, update, deleteByEmail).

// Passi successivi consigliati
// Inviami le altre classi modello per fare le interfacce DAO di tutti gli altri
// enti (Hackathon, Team, Judge, Organizer, etc.).

// Oppure posso anticiparti un template generico per tutte le DAO basato sulla
// struttura del DB.

// Quando vuoi, possiamo anche partire con le implementazioni concrete.

// Se nella prossima chat vuoi riprendere il lavoro, basta che mi mandi
// “Riprendiamo dalle DAO” e ti ritroviamo tutto da qui, senza perdere tempo.