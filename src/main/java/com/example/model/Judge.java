package com.example.model;

import com.example.dao.HackathonDAO;
import com.example.daoimp.HackathonDaoImpl;

public class Judge extends User {

    private HackathonDAO hackathonDAO;
    private int hackathonId;

    public Judge(User user, int hackathonId) {
        super(user.email, user.username, user.password, user.first_name, user.last_name);
        this.hackathonDAO = new HackathonDaoImpl();
        this.hackathonId = hackathonId;
    }

    public Judge(String email, int hackathonId) {
        super(email, "", "", "", ""); // username, password, first_name, last_name vuoti
        this.hackathonDAO = new HackathonDaoImpl();
        this.hackathonId = hackathonId;
    }

    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    public boolean setProblem(String problemDescription) {

        Hackathon hackathon = hackathonDAO.findById(hackathonId)
                .orElseThrow(() -> new IllegalArgumentException("Hackathon not found with ID: " + hackathonId));

        hackathon.setProblemDescription(problemDescription);
        // salva nel db
        return hackathonDAO.updateProblemDescription(hackathon.getId(), problemDescription);
    }
}
