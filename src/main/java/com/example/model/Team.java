package com.example.model;

public class Team {
    private int id;
    private String teamName;
    private int hackathonId;
    private int maxMembers;
    private String leaderEmail; // ✅ AGGIUNTO

    // ✅ MODIFICATO: Costruttore completo
    public Team(int id, String teamName, int hackathonId, int maxMembers, String leaderEmail) {
        this.id = id;
        this.teamName = teamName;
        this.hackathonId = hackathonId;
        this.maxMembers = maxMembers;
        this.leaderEmail = leaderEmail;
    }

    // ✅ MODIFICATO: Costruttore per un nuovo team
    public Team(String teamName, int hackathonId, int maxMembers, String leaderEmail) {
        this.teamName = teamName;
        this.hackathonId = hackathonId;
        this.maxMembers = maxMembers;
        this.leaderEmail = leaderEmail;
    }

    public Team() {
    }

    // --- Getters e Setters ---
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

    // ✅ AGGIUNTO: Getter e Setter per leaderEmail
    public String getLeaderEmail() {
        return leaderEmail;
    }

    public void setLeaderEmail(String leaderEmail) {
        this.leaderEmail = leaderEmail;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", hackathonId=" + hackathonId +
                ", maxMembers=" + maxMembers +
                ", leaderEmail='" + leaderEmail + '\'' + // ✅ AGGIUNTO
                '}';
    }
}