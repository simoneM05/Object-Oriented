// Object-Oriented/src/main/java/com/example/model/Document.java
package com.example.model;

import java.time.LocalDateTime; // Usa LocalDateTime per TIMESTAMP nel DB

public class Document {
    private int id; // Corrisponde a id SERIAL PRIMARY KEY nel DB
    private String documentPath; // Corrisponde a document_path VARCHAR(255) NOT NULL
    private LocalDateTime uploadDate; // Corrisponde a upload_date TIMESTAMP NOT NULL
    private int teamId; // Corrisponde a team_id INT NOT NULL

    // Costruttore completo
    public Document(int id, String documentPath, LocalDateTime uploadDate, int teamId) {
        this.id = id;
        this.documentPath = documentPath;
        this.uploadDate = uploadDate;
        this.teamId = teamId;
    }

    // Costruttore per nuovo documento (senza ID)
    public Document(String documentPath, LocalDateTime uploadDate, int teamId) {
        this.documentPath = documentPath;
        this.uploadDate = uploadDate;
        this.teamId = teamId;
    }

    // Costruttore vuoto
    public Document() {
    }

    // --- Getter e Setter ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", documentPath='" + documentPath + '\'' +
                ", uploadDate=" + uploadDate +
                ", teamId=" + teamId +
                '}';
    }
}