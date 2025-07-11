package com.example.model;

public class Team {
    private int id;
    private String name;
    private int hackathonId;

    public Team(int id, String name, int hackathonId) {
        this.id = id;
        this.name = name;
        this.hackathonId = hackathonId;
    }

    public Team(String name, int hackathonId) {
        this(0, name, hackathonId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hackathonId=" + hackathonId +
                '}';
    }
}
