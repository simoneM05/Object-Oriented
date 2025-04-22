🎯 Obiettivo del Progetto
L'obiettivo di questo homework è la realizzazione di un diagramma delle classi UML per un sistema di gestione di una piattaforma dedicata all’organizzazione di hackathon. Gli utenti registrati possono partecipare a competizioni, formare team e collaborare allo sviluppo di soluzioni innovative.

Il diagramma è stato progettato per rappresentare in modo chiaro ed efficace le principali entità, i loro ruoli, le funzionalità chiave e la gestione completa dell’evento.

📐 Classi del Sistema e Motivazioni
🏁 Hackathon
Rappresenta un evento hackathon disponibile sulla piattaforma.

Attributi principali:

Titolo identificativo

Data di inizio e fine

Periodo di iscrizione

Limite massimo di partecipanti

Organizzatore responsabile

👤 UtenteRegistrato
Classe base che rappresenta tutti gli utenti iscritti alla piattaforma. Da questa classe derivano:

Partecipante

Organizzatore

Giudice

Contiene:

Credenziali d’accesso

Informazioni identificative (es. nome, username, password)

Tutti i ruoli sulla piattaforma derivano da questa classe, centralizzando i dati comuni.

🧑‍💻 Partecipante
Estensione di UtenteRegistrato, rappresenta chi prende parte attivamente all'hackathon.

Funzionalità:

Creare team

Inviare documenti con la propria soluzione

Ricevere/inviare richieste di partecipazione ai team

Nota: Un partecipante può entrare in un team già esistente tramite invito, che può accettare o rifiutare.

⚖️ Giudice
Estensione di UtenteRegistrato, ha il compito di valutare i team.

Responsabilità:

Pubblicare il problema dell’evento

Valutare i documenti inviati

Assegnare voti e commenti

🧑‍🏫 Organizzatore
Estensione di UtenteRegistrato, gestisce l’organizzazione generale dell’evento.

Responsabilità:

Selezionare i giudici (tra gli utenti registrati) tramite invito

Aprire le iscrizioni all'hackathon

🤝 Team
Un gruppo di partecipanti che collaborano durante l’evento.

Caratteristiche:

Composto da un numero massimo di membri

Può inviare documenti per la valutazione

Riceve feedback e voti dai giudici

📄 Documento
Rappresenta la soluzione proposta da un team al problema dell’hackathon.

Proprietà:

Creato e aggiornato dal team

Valutato dai giudici

⭐ Voto
Valutazione assegnata dai giudici a un documento.

Contiene:

Punteggio

Commento

✉️ Richiesta
Invito inviato da un partecipante (già in un team) a un altro utente per unirsi al proprio team.

Dettagli:

Include un messaggio motivazionale

Può essere accettata o rifiutata
