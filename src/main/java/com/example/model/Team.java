
package com.example.model;


// import java.util.List; // Se vuoi mantenere la lista di membri qui

public class Team {
    private int id; // Corrisponde a id SERIAL PRIMARY KEY nel DB
    private String teamName; // Corrisponde a team_name VARCHAR(255) NOT NULL UNIQUE
    private int hackathonId; // Corrisponde a hackathon_id INT NOT NULL
    private int maxMembers; // Corrisponde a max_members INT NOT NULL

    // Costruttore completo
    public Team(int id, String teamName, int hackathonId, int maxMembers) {
        this.id = id;
        this.teamName = teamName;
        this.hackathonId = hackathonId;
        this.maxMembers = maxMembers;

    }

    // Costruttore per creare un nuovo team (senza ID, gestito dal DB)
    public Team(String teamName, int hackathonId, int maxMembers) {
        this.teamName = teamName;
        this.hackathonId = hackathonId;
        this.maxMembers = maxMembers;

    }

    // Costruttore vuoto
    public Team() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }



    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", hackathonId=" + hackathonId +
                ", maxMembers=" + maxMembers +
                '}';
    }
}