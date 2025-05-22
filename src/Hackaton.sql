-- Tabella User: Deve essere creata per prima in quanto altre tabelle la referenziano
CREATE TABLE "User" (
    email VARCHAR(255) PRIMARY KEY, -- L'email è una buona chiave primaria per l'utente
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL -- Il username deve essere unico
);

---

CREATE TABLE Hackaton (
    id SERIAL PRIMARY KEY, -- PostgreSQL usa SERIAL per l'autoincremento
    sede VARCHAR(255) NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    participanTnumber INT,
    numMaxForTeam INT,
    startSub DATE,
    endSub DATE,
    problem TEXT, -- Usiamo TEXT per descrizioni più lunghe
    title VARCHAR(255) NOT NULL UNIQUE -- Il titolo di un hackathon dovrebbe essere unico
);

---

CREATE TABLE Team (
    id SERIAL PRIMARY KEY, -- SERIAL per l'autoincremento
    name VARCHAR(255) NOT NULL UNIQUE -- Il nome del team deve essere unico
);

---

CREATE TABLE Judge (
    id SERIAL PRIMARY KEY, -- Ogni giudice ha un proprio ID
    email VARCHAR(255) UNIQUE NOT NULL, -- Email del giudice, unica
    HackatonId INT NOT NULL, -- Colonna per la chiave esterna
    CONSTRAINT fk_judge_hackaton FOREIGN KEY (HackatonId) REFERENCES Hackaton(id)
);

---

CREATE TABLE Organizer (
    id SERIAL PRIMARY KEY, -- Ogni organizzatore ha un proprio ID
    email VARCHAR(255) UNIQUE NOT NULL, -- Email dell'organizzatore, unica
    HackatonId INT NOT NULL, -- Colonna per la chiave esterna
    CONSTRAINT fk_organizer_hackaton FOREIGN KEY (HackatonId) REFERENCES Hackaton(id)
);

---

CREATE TABLE Participant (
    id SERIAL PRIMARY KEY, -- Ogni partecipante ha un proprio ID
    email VARCHAR(255) UNIQUE NOT NULL, -- Email del partecipante, unica
    teamId INT, -- Può essere NULL se un partecipante non è ancora in un team
    hackatonId INT NOT NULL, -- Il partecipante deve essere associato a un hackathon
    CONSTRAINT fk_participant_team FOREIGN KEY (teamId) REFERENCES Team(id),
    CONSTRAINT fk_participant_hackaton FOREIGN KEY (hackatonId) REFERENCES Hackaton(id)
);

---

CREATE TABLE Document (
    id SERIAL PRIMARY KEY, -- Ogni documento ha un proprio ID
    uploadDate DATE NOT NULL, -- 'DATE' è una parola chiave, meglio 'uploadDate'
    documentPath VARCHAR(255) NOT NULL, -- Percorso o nome del file del documento
    teamId INT NOT NULL, -- Il documento deve essere associato a un team
    CONSTRAINT fk_document_team FOREIGN KEY (teamId) REFERENCES Team(id)
);

---

CREATE TABLE Request (
    id SERIAL PRIMARY KEY, -- Ogni richiesta ha un proprio ID
    message TEXT NOT NULL,
    status BOOLEAN DEFAULT FALSE, -- Default a FALSE (in attesa)
    teamId INT NOT NULL, -- La richiesta è fatta da un team
    emailRecive VARCHAR(255) NOT NULL, -- L'email del destinatario della richiesta
    CONSTRAINT fk_request_team FOREIGN KEY (teamId) REFERENCES Team(id),
    CONSTRAINT fk_request_email_receive FOREIGN KEY (emailRecive) REFERENCES "User"(email)
);

---

CREATE TABLE Vote (
    id SERIAL PRIMARY KEY, -- Ogni voto ha un proprio ID
    valore INT NOT NULL,
    teamId INT NOT NULL, -- Il voto è per un team
    judgeId INT NOT NULL, -- Il voto è dato da un giudice
    CONSTRAINT fk_vote_team FOREIGN KEY (teamId) REFERENCES Team(id),
    CONSTRAINT fk_vote_judge FOREIGN KEY (judgeId) REFERENCES Judge(id)
);