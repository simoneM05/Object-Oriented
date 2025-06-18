
package com.example.model;

public class Request {
    private int id; // Corrisponde a id SERIAL PRIMARY KEY nel DB
    private String message; // Corrisponde a message TEXT NOT NULL
    private String status; // Corrisponde a status VARCHAR(50) DEFAULT 'PENDING' NOT NULL
    // Useremo stringhe come "PENDING", "ACCEPTED", "REJECTED"
    private int teamId; // Corrisponde a team_id INT NOT NULL
    private String senderUserEmail; // Corrisponde a sender_participant_email VARCHAR(255) NOT NULL
    private String receiverUserEmail; // Corrisponde a receiver_participant_email VARCHAR(255) NOT NULL

    // Costruttore completo
    public Request(int id, String message, String status, int teamId, String senderUserEmail, String receiverUserEmail) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.teamId = teamId;
        this.senderUserEmail = senderUserEmail;
        this.receiverUserEmail = receiverUserEmail;
    }

    // Costruttore per una nuova richiesta (senza ID e con stato PENDING di default)
    public Request(String message, int teamId, String senderUserEmail, String receiverUserEmail) {
        this.message = message;
        this.status = "PENDING"; // Stato iniziale
        this.teamId = teamId;
        this.senderUserEmail = senderUserEmail;
        this.receiverUserEmail = receiverUserEmail;
    }

    // Costruttore vuoto
    public Request() {
    }

    // --- Getter e Setter ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getSenderUserEmail() {
        return senderUserEmail;
    }

    public void setSenderUserEmail(String senderUserEmail) {
        this.senderUserEmail = senderUserEmail;
    }

    public String getReceiverUserEmail() {
        return receiverUserEmail;
    }

    public void setReceiverUserEmail(String receiverUserEmail) {
        this.receiverUserEmail = receiverUserEmail;
    }

    // Metodi per facilitare la gestione dello stato
    public void accept() {
        this.status = "ACCEPTED";
    }

    public void reject() {
        this.status = "REJECTED";
    }

    public boolean isPending() {
        return "PENDING".equals(this.status);
    }

    public boolean isAccepted() {
        return "ACCEPTED".equals(this.status);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", teamId=" + teamId +
                ", senderUserEmail='" + senderUserEmail + '\'' +
                ", receiverUserEmail='" + receiverUserEmail + '\'' +
                '}';
    }
}