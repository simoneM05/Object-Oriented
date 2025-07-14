-- ============================
-- 1. UTENTI
-- ============================
CREATE TABLE users (
    email      VARCHAR(255) PRIMARY KEY,
    username   VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    first_name VARCHAR(255)        NOT NULL,
    last_name  VARCHAR(255)        NOT NULL
);

-- ============================
-- 2. ORGANIZZATORI
-- ============================
CREATE TABLE organizers (
    user_email VARCHAR(255) PRIMARY KEY,
    CONSTRAINT fk_organizer_user FOREIGN KEY (user_email) REFERENCES users (email) ON DELETE CASCADE
);

-- ============================
-- 3. HACKATHON
-- ============================
CREATE TABLE hackathons (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    location VARCHAR(255) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    max_participants INT NOT NULL,
    max_team_size INT NOT NULL,
    registration_start TIMESTAMP ,
    registration_end TIMESTAMP ,
    problem_description TEXT,
    organizer_user_email VARCHAR(255) NOT NULL,
    CONSTRAINT fk_hackathon_organizer FOREIGN KEY (organizer_user_email) REFERENCES organizers (user_email) ON DELETE CASCADE
);

-- ============================
-- 4. GIUDICI
-- ============================
CREATE TABLE judges (
    user_email VARCHAR(255) PRIMARY KEY,
    hackathon_id INT NOT NULL,
    CONSTRAINT fk_judge_user FOREIGN KEY (user_email) REFERENCES users (email) ON DELETE CASCADE,
    CONSTRAINT fk_judge_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons (id) ON DELETE CASCADE
);

-- ============================
-- 5. TEAM & PARTECIPANTI
-- ============================
CREATE TABLE teams (
    id           SERIAL PRIMARY KEY,
    team_name    VARCHAR(255) NOT NULL,
    hackathon_id INT          NOT NULL,
    max_members  INT          NOT NULL,
    leader_email VARCHAR(255) NOT NULL,
    CONSTRAINT fk_team_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons (id) ON DELETE CASCADE,
    CONSTRAINT fk_team_leader FOREIGN KEY (leader_email) REFERENCES users (email) ON DELETE SET NULL,
    UNIQUE (team_name, hackathon_id)
);

CREATE TABLE participants (
    user_email   VARCHAR(255) PRIMARY KEY,
    hackathon_id INT NOT NULL,
    team_id      INT,
    CONSTRAINT fk_participant_user FOREIGN KEY (user_email) REFERENCES users (email) ON DELETE CASCADE,
    CONSTRAINT fk_participant_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons (id) ON DELETE CASCADE,
    CONSTRAINT fk_participant_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE SET NULL
);

-- ============================
-- 6. DOCUMENTI
-- ============================
CREATE TABLE documents (
    id             SERIAL PRIMARY KEY,
    document_path  VARCHAR(255) NOT NULL,
    upload_date    TIMESTAMP NOT NULL DEFAULT NOW(),
    team_id        INT NOT NULL,
    CONSTRAINT fk_document_team FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE
);


-- ============================
-- 7. RICHIESTE DI PARTECIPAZIONE
-- ============================
CREATE TABLE requests (
    id                         SERIAL PRIMARY KEY,
    message                    TEXT                          NOT NULL,
    status                     VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
    team_id                    INT                           NOT NULL,
    sender_participant_email   VARCHAR(255)                  NOT NULL,
    receiver_participant_email VARCHAR(255)                  NOT NULL,
    CONSTRAINT fk_request_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE,
    CONSTRAINT fk_request_sender FOREIGN KEY (sender_participant_email) REFERENCES participants (user_email) ON DELETE CASCADE,
    CONSTRAINT fk_request_receiver FOREIGN KEY (receiver_participant_email) REFERENCES participants (user_email) ON DELETE CASCADE
);

-- ============================
-- 8. VOTI
-- ============================
CREATE TABLE votes (
    id               SERIAL PRIMARY KEY,
    score            INT          NOT NULL CHECK (score >= 0 AND score <= 10),
    team_id          INT          NOT NULL,
    judge_user_email VARCHAR(255) NOT NULL,
    hackathon_id     INT          NOT NULL,
    CONSTRAINT fk_vote_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE,
    CONSTRAINT fk_vote_judge FOREIGN KEY (judge_user_email) REFERENCES judges (user_email) ON DELETE CASCADE,
    CONSTRAINT fk_vote_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons (id) ON DELETE CASCADE
);

-- ============================
-- 9. DATI DI ESEMPIO
-- ============================

-- UTENTI
INSERT INTO users (email, username, password, first_name, last_name)
VALUES 
  ('alice.smith@example.com', 'alice_organizer', 'pass_alice', 'Alice', 'Smith'),
  ('bob.johnson@example.com', 'bob_participant', 'pass_bob', 'Bob', 'Johnson'),
  ('charlie.brown@example.com', 'charlie_part', 'pass_charlie', 'Charlie', 'Brown'),
  ('diana.miller@example.com', 'diana_judge', 'pass_diana', 'Diana', 'Miller'),
  ('eve.davis@example.com', 'eve_organizer', 'pass_eve', 'Eve', 'Davis'),
  ('frank.green@example.com', 'frank_part', 'pass_frank', 'Frank', 'Green');

-- ORGANIZZATORI
INSERT INTO organizers (user_email)
VALUES ('alice.smith@example.com'), ('eve.davis@example.com');

-- HACKATHON
INSERT INTO hackathons (
  title, location, start_date, end_date, max_participants, max_team_size,
  registration_start, registration_end, problem_description, organizer_user_email
)
VALUES
  ('Future Tech Challenge 2025', 'Online', '2025-09-01 09:00:00', '2025-09-03 17:00:00', 100, 5, '2025-08-01 00:00:00', '2025-08-25 23:59:59', 'Develop innovative solutions for sustainable energy.', 'alice.smith@example.com'),
  ('AI Innovation Summit', 'Milan', '2025-10-15 10:00:00', '2025-10-17 18:00:00', 80, 4, '2025-09-01 00:00:00', '2025-10-05 23:59:59', 'Create AI-powered tools for personalized learning.', 'eve.davis@example.com'),
  ('Web Dev Showcase', 'Rome', '2025-11-20 09:00:00', '2025-11-22 16:00:00', 120, 3, '2025-10-10 00:00:00', '2025-11-15 23:59:59', 'Build responsive and accessible web applications.', 'alice.smith@example.com');

-- GIUDICI
INSERT INTO judges (user_email, hackathon_id)
VALUES 
  ('diana.miller@example.com', 1);


-- TEAM
INSERT INTO teams (team_name, hackathon_id, max_members, leader_email)
VALUES 
  ('Eco Innovators', 1, 5, 'bob.johnson@example.com'),
  ('Neural Coders', 2, 4, 'frank.green@example.com'),
  ('Pixel Pioneers', 3, 3, 'charlie.brown@example.com'),
  ('Green Minds', 1, 5, 'charlie.brown@example.com');

-- PARTECIPANTI
INSERT INTO participants (user_email, hackathon_id, team_id)
VALUES 
  ('bob.johnson@example.com', 1, 1),
  ('charlie.brown@example.com', 1, 1),
  ('frank.green@example.com', 2, 2);

-- DOCUMENTI
INSERT INTO documents (document_path, upload_date, team_id)
VALUES 
  ('/documents/eco_innovators_proposal.pdf', '2025-08-20 15:00:00', 1),
  ('/documents/neural_coders_design.docx', '2025-09-25 10:00:00', 2),
  ('/documents/eco_innovators_presentation.pptx', '2025-09-03 10:00:00', 1);

-- RICHIESTE
INSERT INTO requests (message, status, team_id, sender_participant_email, receiver_participant_email)
VALUES 
  ('Vorrei unirmi al vostro team per il Future Tech Challenge. Sono bravo in backend!', 'PENDING', 4, 'bob.johnson@example.com', 'charlie.brown@example.com'),
  ('Ciao! Mi piacerebbe contribuire con le mie skill di frontend al team Eco Innovators.', 'ACCEPTED', 1, 'frank.green@example.com', 'bob.johnson@example.com');

-- VOTI
INSERT INTO votes (score, team_id, judge_user_email, hackathon_id)
VALUES 
  (8, 1, 'diana.miller@example.com', 1),
  (9, 2, 'diana.miller@example.com', 2),
  (7, 3, 'diana.miller@example.com', 3);
