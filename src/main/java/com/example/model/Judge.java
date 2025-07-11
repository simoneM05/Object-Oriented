package com.example.model;

import com.example.dao.HackathonDAO;
import com.example.daoimp.HackathonDaoImpl;

public class Judge extends User {

    private HackathonDAO hackathonDAO;

    public Judge(String email, String username, String password, String first_name, String last_name) {
        super(email, username, password, first_name, last_name);
        this.hackathonDAO = new HackathonDaoImpl(); // o passalo nel costruttore per inversione dipendenze
    }

    public boolean setProblem(Hackathon hackathon, String problemDescription) {
        hackathon.setProblemDescription(problemDescription);
        // salva nel db
        return hackathonDAO.updateProblemDescription(hackathon.getId(), problemDescription);
    }
}
