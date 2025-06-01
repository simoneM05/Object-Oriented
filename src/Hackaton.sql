-- Creazione del tipo ENUM per i ruoli utente
CREATE TYPE user_role AS ENUM ('partecipant', 'organizer', 'judge');

-- Tabella hackathon
CREATE TABLE hackathon (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    sede VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_sub_date DATE,
    end_sub_date DATE,
    participant_number INT,
    num_max_for_team INT,
    problem TEXT,
    -- Constraint per validare le date
    CONSTRAINT chk_hackathon_dates CHECK (end_date >= start_date),
    CONSTRAINT chk_sub_dates CHECK (end_sub_date >= start_sub_date )
);

-- Tabella team (rimossa la ridondanza hackathonId)
CREATE TABLE team (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabella document
CREATE TABLE document (
    id SERIAL PRIMARY KEY,
    document_path VARCHAR(255) NOT NULL,
    -- upload_date DATE DEFAULT CURRENT_DATE,
    teamId INT NOT NULL,
    CONSTRAINT fk_document_team FOREIGN KEY (teamId) REFERENCES team(id) ON DELETE CASCADE
);

-- Tabella user (corretta la doppia chiave primaria)
CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    role user_role NOT NULL
);

-- Tabella per ruoli specifici legati a un hackathon
CREATE TABLE hackathon_role (
    userId INT NOT NULL,
    hackathonId INT NOT NULL,
    role_type VARCHAR(50) NOT NULL CHECK (role_type IN ('organizer', 'judge')),
    PRIMARY KEY (userId, hackathonId, role_type),
    CONSTRAINT fk_hackathonrole_user FOREIGN KEY (userId) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_hackathonrole_hackathon FOREIGN KEY (hackathonId) REFERENCES hackathon(id) ON DELETE CASCADE
);

-- Tabella participant (corretto errore di battitura nel constraint)
CREATE TABLE participant (
    userId INT NOT NULL,
    hackathonId INT NOT NULL,
    teamId INT,
    PRIMARY KEY (userId, hackathonId),
    CONSTRAINT fk_participant_user FOREIGN KEY (userId) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_participant_hackathon FOREIGN KEY (hackathonId) REFERENCES hackathon(id) ON DELETE CASCADE,
    CONSTRAINT fk_participant_team FOREIGN KEY (teamId) REFERENCES team(id) ON DELETE SET NULL
);

-- Tabella vote (aggiunto constraint per evitare voti duplicati)
CREATE TABLE vote (
    id SERIAL PRIMARY KEY,
    valore INT NOT NULL CHECK (valore >= 1 AND valore <= 10),
    teamId INT NOT NULL,
    judgeUserId INT NOT NULL,
    hackathonId INT NOT NULL, -- Aggiunto per maggiore controllo
    -- Constraint per evitare che lo stesso giudice voti più volte lo stesso team nello stesso hackathon
    UNIQUE(teamId, judgeUserId, hackathonId),
    CONSTRAINT fk_vote_team FOREIGN KEY (teamId) REFERENCES team(id) ON DELETE CASCADE,
    CONSTRAINT fk_vote_judge FOREIGN KEY (judgeUserId) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_vote_hackathon FOREIGN KEY (hackathonId) REFERENCES hackathon(id) ON DELETE CASCADE
);

-- Tabella request (aggiunto hackathonId per contesto)
CREATE TABLE request (
    id SERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    status BOOLEAN NOT NULL DEFAULT FALSE,
    requestingTeamId INT NOT NULL,
    recipientUserId INT NOT NULL,
    hackathonId INT NOT NULL, -- Aggiunto per contestualizzare la richiesta
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Utile per tracciare quando è stata creata
    CONSTRAINT fk_request_team FOREIGN KEY (requestingTeamId) REFERENCES team(id) ON DELETE CASCADE,
    CONSTRAINT fk_request_user FOREIGN KEY (recipientUserId) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_request_hackathon FOREIGN KEY (hackathonId) REFERENCES hackathon(id) ON DELETE CASCADE
);