package com.example.controller;

import com.example.dao.HackathonDAO;
import com.example.dao.UserDAO;
import com.example.daoimp.HackathonDaoImpl;
import com.example.daoimp.PartecipantDaoImpl;
import com.example.daoimp.UserDaoImpl;
import com.example.model.User;
import com.example.model.Hackathon;
import com.example.model.Partecipant;
import com.example.model.Role;

import java.util.List;
import java.util.Optional;

public class ControllerGui {

    private UserDAO userDAO = new UserDaoImpl();
    private PartecipantDaoImpl PartecipantDAO = new PartecipantDaoImpl();
    private HackathonDAO hackathonDAO = new HackathonDaoImpl(); // <-- aggiungi questo

    public List<Hackathon> getAllHackathons() {
        return hackathonDAO.findAll();
    }

    public Optional<Hackathon> getHackathonByTitle(String title) {
        return hackathonDAO.findByTitle(title);
    }

    public void updateHackathon(Hackathon hackathon) {
        hackathonDAO.update(hackathon);
    }

    public void deleteHackathon(Hackathon hackathon) {
        hackathonDAO.delete(hackathon.getId());
    }

    public void createHackathon(Hackathon hackathon) {
        hackathonDAO.save(hackathon);
    }

    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email).orElse(null);
    }

    public boolean savePartecipant(Partecipant p) {
        PartecipantDAO.save(p);
        return true;
    }

    public User login(String email, String password) {
        Optional<User> userOpt = userDAO.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                System.out.println("Login riuscito per " + email);
                return user; // torna l'intero User
            }
        }
        System.out.println("Login fallito per " + email);
        return null;
    }

    public boolean register(String email, String username, String password, String firstName, String lastName) {
        if (userDAO.findByEmail(email).isPresent()) {
            System.out.println("Email già registrata");
            return false;
        }

        User newUser = new User(email, username, password, firstName, lastName);
        boolean saved = userDAO.save(newUser);

        if (saved) {
            System.out.println("Registrazione riuscita");
        } else {
            System.out.println("Registrazione fallita");
        }

        return saved;
    }
}
