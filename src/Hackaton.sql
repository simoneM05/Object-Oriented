
-- 1. Tabella users
-- Utenti registrati alla piattaforma.
-- Hanno credenziali d'accesso (username, password) e dati identificativi (nome, cognome, email).
-- Email come chiave primaria perché presumibilmente unica per la registrazione.
CREATE TABLE users (
                       email VARCHAR(255) PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL
);

-- 2. Tabella organizers
-- Ruolo di "Organizzatore" per un utente registrato.
-- "Ogni hackathon... ha un organizzatore specifico (registrato alla piattaforma)."
-- Questo implica una relazione 1:N tra Organizer e Hackathon (un organizzatore può organizzare molti hackathon, ma un hackathon ha un solo organizzatore).
-- user_email è PK e FK per collegarlo all'utente registrato.
CREATE TABLE organizers (
                            user_email VARCHAR(255) PRIMARY KEY, -- Riferimento all'utente registrato
                            CONSTRAINT fk_organizer_user FOREIGN KEY (user_email) REFERENCES users(email)
);

-- 3. Tabella judges
-- Ruolo di "Giudice" per un utente registrato.
-- "L'organizzatore seleziona un gruppo di giudici (selezionati tra gli utenti della piattaforma, invitandoli)."
-- Implica che un giudice può giudicare più hackathon, e un hackathon può avere più giudici (N:M).
-- user_email è PK e FK per collegarlo all'utente registrato.
CREATE TABLE judges (
    user_email VARCHAR(255) PRIMARY KEY, -- Riferimento all'utente registrato
    CONSTRAINT fk_judge_user FOREIGN KEY (user_email) REFERENCES users(email)
);

-- 4. Tabella hackathons
-- Informazioni su ogni hackathon.
-- Ha un organizzatore specifico.
CREATE TABLE hackathons (
                            id SERIAL PRIMARY KEY,
                            title VARCHAR(255) UNIQUE NOT NULL, -- Titolo identificativo
                            location VARCHAR(255) NOT NULL, -- Sede
                            start_date TIMESTAMP NOT NULL, -- Intervallo di tempo (inizio)
                            end_date TIMESTAMP NOT NULL, -- Intervallo di tempo (fine)
                            max_participants INT NOT NULL, -- Numero massimo di iscritti
                            max_team_size INT NOT NULL, -- Dimensione massima del team
                            registration_start TIMESTAMP NOT NULL, -- Apertura registrazioni
                            registration_end TIMESTAMP NOT NULL, -- Chiusura registrazioni (2 giorni prima dell'evento)
                            problem_description TEXT, -- Descrizione del problema (pubblicata dai giudici)
                            organizer_user_email VARCHAR(255) NOT NULL, -- L'organizzatore specifico
                            CONSTRAINT fk_hackathon_organizer FOREIGN KEY (organizer_user_email) REFERENCES organizers(user_email)
);

-- 5. Tabella participants
-- Ruolo di "Partecipante" per un utente registrato.
-- Un utente si registra per l'Hackathon di sua scelta.
-- Può formare team o unirsi a team.
-- user_email è PK e FK per collegarlo all'utente registrato.
CREATE TABLE participants (
                              user_email VARCHAR(255) PRIMARY KEY, -- Riferimento all'utente registrato
                              hackathon_id INT NOT NULL, -- A quale hackathon partecipa
                              team_id INT, -- Può essere NULL se non ancora in un team
                              CONSTRAINT fk_participant_user FOREIGN KEY (user_email) REFERENCES users(email),
                              CONSTRAINT fk_participant_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons(id)
    -- FK a teams verrà aggiunta dopo la creazione della tabella teams
);

-- 6. Tabella teams
-- Gruppo di partecipanti che collaborano per risolvere il problema.
-- Associato a un hackathon specifico.
-- Ogni team ha un massimo di membri.
CREATE TABLE teams (
                       id SERIAL PRIMARY KEY,
                       team_name VARCHAR(255) NOT NULL UNIQUE, -- Nome del team
                       hackathon_id INT NOT NULL, -- Il team è per uno specifico hackathon
                       max_members INT NOT NULL, -- Massimo di membri che possono comporre il team
                       CONSTRAINT fk_team_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons(id)
);

-- Aggiunge la Foreign Key a `teams` nella tabella `participants` (ora che `teams` esiste)
ALTER TABLE participants
    ADD CONSTRAINT fk_participant_team FOREIGN KEY (team_id) REFERENCES teams(id);


-- 7. Tabella documents
-- Aggiornamenti sui "progressi" caricati dai team.
-- Contiene la possibile risoluzione al problema.
CREATE TABLE documents (
                           id SERIAL PRIMARY KEY,
                           document_path VARCHAR(255) NOT NULL, -- Percorso o riferimento al file del documento
                           upload_date TIMESTAMP NOT NULL, -- Quando il documento è stato caricato
                           team_id INT NOT NULL, -- Il team che ha caricato il documento
                           CONSTRAINT fk_document_team FOREIGN KEY (team_id) REFERENCES teams(id)
);

-- 8. Tabella requests
-- Richieste tra partecipanti per unirsi a un team.
-- Include un messaggio motivazionale.
-- Ha uno stato (accettata/rifiutata/in attesa).
CREATE TABLE requests (
                          id SERIAL PRIMARY KEY,
                          message TEXT NOT NULL, -- Messaggio motivazionale
                          status VARCHAR(50) DEFAULT 'PENDING' NOT NULL, -- PENDING, ACCEPTED, REJECTED
                          team_id INT NOT NULL, -- Il team a cui si riferisce la richiesta
                          sender_participant_email VARCHAR(255) NOT NULL, -- Il partecipante che invia la richiesta
                          receiver_participant_email VARCHAR(255) NOT NULL, -- L'utente che può accettare o rifiutare la richiesta
                          CONSTRAINT fk_request_team FOREIGN KEY (team_id) REFERENCES teams(id),
                          CONSTRAINT fk_request_sender FOREIGN KEY (sender_participant_email) REFERENCES participants(user_email),
                          CONSTRAINT fk_request_receiver FOREIGN KEY (receiver_participant_email) REFERENCES participants(user_email)
);

-- 9. Tabella votes
-- Voto assegnato dai giudici a ciascun team.
-- Il voto può essere in un range da 0 a 10.
CREATE TABLE votes (
                       id SERIAL PRIMARY KEY,
                       score INT NOT NULL CHECK (score >= 0 AND score <= 10), -- Voto da 0 a 10
                       team_id INT NOT NULL, -- Il team che riceve il voto
                       judge_user_email VARCHAR(255) NOT NULL, -- Il giudice che assegna il voto
                       hackathon_id INT NOT NULL, -- L'hackathon a cui si riferisce il voto (per chiarezza e coerenza)
                       CONSTRAINT fk_vote_team FOREIGN KEY (team_id) REFERENCES teams(id),
                       CONSTRAINT fk_vote_judge FOREIGN KEY (judge_user_email) REFERENCES judges(user_email),
                       CONSTRAINT fk_vote_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons(id)
);

-- 10. Tabella di giunzione judge_hackathons (per relazione Many-to-Many)
-- Un giudice può giudicare più hackathon, e un hackathon ha più giudici.
-- "L'organizzatore seleziona un gruppo di giudici (selezionati tra gli utenti della piattaforma, invitandoli)."
CREATE TABLE judge_hackathons (
                                  judge_user_email VARCHAR(255) NOT NULL,
                                  hackathon_id INT NOT NULL,
                                  PRIMARY KEY (judge_user_email, hackathon_id), -- PK composta per unicità della coppia
                                  CONSTRAINT fk_jh_judge FOREIGN KEY (judge_user_email) REFERENCES judges(user_email),
                                  CONSTRAINT fk_jh_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons(id)
);