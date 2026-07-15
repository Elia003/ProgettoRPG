# Progetto RPG

Applicazione JavaFX per la gestione di personaggi, oggetti e combattimenti in un contesto RPG.
Il programma permette di creare personaggi appartenenti a classi diverse, assegnare oggetti compatibili,
gestire l'inventario e simulare combattimenti tra due personaggi.

Il progetto utilizza un database SQLite per salvare e recuperare i dati principali, come personaggi e oggetti.
L'interfaccia grafica e' realizzata con JavaFX e file FXML.

---

## Come eseguire il progetto

### Prerequisiti

- Java 21
- Gradle, oppure il wrapper Gradle gia' incluso nel progetto

### Istruzioni

```bash
git clone https://github.com/Elia003/ProgettoRPG.git
cd ProgettoRPG
```

### Build del progetto

Su Windows:

```bash
.\gradlew.bat build
```

Su Linux/macOS:

```bash
./gradlew build
```

### Esecuzione

Su Windows:

```bash
.\gradlew.bat run
```

Su Linux/macOS:

```bash
./gradlew run
```

All'avvio vengono aperte due finestre: la schermata principale dell'applicazione e la schermata di amministrazione.

---

## Funzionalita' principali

- Creazione e gestione di personaggi RPG.
- Supporto a piu' classi, tra cui barbaro, druido, guerriero, ladro e mago.
- Gestione di statistiche come punti vita, attacco, difesa, livello e risorsa.
- Inventario con limite di spazio disponibile.
- Oggetti con peso, rarita', consumo, effetti e classi compatibili.
- Equipaggiamento e rimozione degli oggetti con applicazione degli effetti sulle statistiche.
- Simulazione di combattimenti a turni tra due personaggi.
- Persistenza dei dati tramite database SQLite.
- Area amministrativa per la gestione degli oggetti e dei dati del progetto.

---

## Struttura del progetto

Il codice sorgente si trova nella cartella `src/main/java`.
Le schermate grafiche FXML, le immagini e i font si trovano nella cartella `src/main/resources`.

Le principali aree del progetto sono:

- `Personaggi`: contiene le classi dei personaggi e la classe astratta comune.
- `Oggetti`: contiene la gestione degli oggetti e dell'inventario.
- `Aumenti`: contiene gli effetti applicabili alle statistiche dei personaggi.
- `Risorse`: contiene la gestione di mana, stamina e risorse dei personaggi.
- `Database`: contiene la connessione a SQLite, l'inizializzazione dello schema e i DAO.
- `Controller`: contiene i controller JavaFX collegati alle schermate FXML.
- `Builder`: contiene i builder usati per creare personaggi e oggetti.

---

## Uso di strumenti di AI


Durante lo sviluppo sono stati utilizzati strumenti di AI (Chat gpt) come supporto allo studio e alla revisione del codice.
In particolare, l'AI e' stata usata per chiarire alcuni errori di compilazione, comprendere meglio la struttura di
alcune classi e ricevere suggerimenti su possibili miglioramenti, come la modifica del font nei file fxml o la scrittura
di codice ridondante . Il codice finale e' stato comunque letto,
adattato, testato e compreso personalmente.

---

## Nota

Il progetto è stato sviluppato come esercizio di programmazione orientata agli oggetti, con particolare attenzione
alla separazione delle responsabilita' tra modello, interfaccia grafica, persistenza dei dati e logica di gioco.
