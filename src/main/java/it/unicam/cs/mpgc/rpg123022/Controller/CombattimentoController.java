package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.Builder.personaggi.GuerrieroBuilder;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Enum.TipoOggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class CombattimentoController {
    @FXML
    private Label titoloLabel;
    @FXML
    private Label roundLabel;
    @FXML
    private Label turnoLabel;
    @FXML
    private Label statoLabel;
    @FXML
    private Label giocatore1NomeLabel;
    @FXML
    private Label giocatore1ClasseLabel;
    @FXML
    private Label giocatore1HpLabel;
    @FXML
    private Label giocatore1DifesaLabel;
    @FXML
    private Label giocatore1RisorsaLabel;
    @FXML
    private Label giocatore1AttaccoLabel;
    @FXML
    private Label giocatore2NomeLabel;
    @FXML
    private Label giocatore2ClasseLabel;
    @FXML
    private Label giocatore2HpLabel;
    @FXML
    private Label giocatore2DifesaLabel;
    @FXML
    private Label giocatore2RisorsaLabel;
    @FXML
    private Label giocatore2AttaccoLabel;
    @FXML
    private ProgressBar giocatore1HpBar;
    @FXML
    private ProgressBar giocatore2HpBar;
    @FXML
    private TextArea logArea;
    @FXML
    private Button attaccaButton;
    @FXML
    private Button resetButton;

    private Personaggio giocatore1;
    private Personaggio giocatore2;
    private Personaggio giocatoreCorrente;
    private int round;

    private StatoPersonaggio statoInizialeGiocatore1;
    private StatoPersonaggio statoInizialeGiocatore2;

    @FXML
    public void initialize() {
        caricaCombattimentoDemo();
    }

    @FXML
    private void onAttacca() {
        if (!combattimentoDisponibile()) {
            appendLog("Combattimento non disponibile.");
            return;
        }

        if (combattimentoTerminato()) {
            appendLog("Il combattimento e' gia terminato. Premi Reset per ricominciare.");
            aggiornaInterfaccia();
            return;
        }

        eseguiTurno();
        aggiornaInterfaccia();
    }

    @FXML
    private void onReset() {
        if (!combattimentoDisponibile()) {
            caricaCombattimentoDemo();
            return;
        }

        statoInizialeGiocatore1.ripristina();
        statoInizialeGiocatore2.ripristina();
        round = 0;
        giocatoreCorrente = ThreadLocalRandom.current().nextBoolean() ? giocatore1 : giocatore2;

        logArea.clear();
        appendLog("Combattimento ripristinato.");
        appendLog("Inizia " + giocatoreCorrente.getNome() + ".");
        aggiornaInterfaccia();
    }

    @FXML
    private void onCaricaDemo() {
        caricaCombattimentoDemo();
    }

    public void setCombattenti(Personaggio giocatore1, Personaggio giocatore2) {
        if (giocatore1 == null || giocatore2 == null) {
            this.giocatore1 = null;
            this.giocatore2 = null;
            this.statoInizialeGiocatore1 = null;
            this.statoInizialeGiocatore2 = null;
            this.round = 0;
            this.giocatoreCorrente = null;
            logArea.clear();
            appendLog("Combattimento non disponibile.");
            aggiornaInterfaccia();
            return;
        }

        this.giocatore1 = giocatore1;
        this.giocatore2 = giocatore2;
        this.statoInizialeGiocatore1 = new StatoPersonaggio(giocatore1);
        this.statoInizialeGiocatore2 = new StatoPersonaggio(giocatore2);
        this.round = 0;
        this.giocatoreCorrente = ThreadLocalRandom.current().nextBoolean() ? giocatore1 : giocatore2;

        logArea.clear();
        appendLog("Combattimento pronto tra " + giocatore1.getNome() + " e " + giocatore2.getNome() + ".");
        appendLog("Inizia " + giocatoreCorrente.getNome() + ".");
        aggiornaInterfaccia();
    }

    private void caricaCombattimentoDemo() {
        ArmadioPersonaggi armadio = ArmadioPersonaggi.getInstance();
        Personaggio giocatoreCorrente = armadio.getPersonaggioCorrente();

        if (giocatoreCorrente == null) {
            setCombattenti(null, null);
            return;
        }

        Personaggio avversarioDemo = new GuerrieroBuilder()
                .setId(99)
                .setNome("Brina")
                .setGenere(Genere.Donna)
                .setColoreCapelli("Rossi")
                .build();

        setCombattenti(giocatoreCorrente, avversarioDemo);
    }

    private void eseguiTurno() {
        round++;

        Personaggio attaccante = giocatoreCorrente;
        Personaggio difensore = giocatoreCorrente == giocatore1 ? giocatore2 : giocatore1;
        int risorsaPrima = attaccante.getRisorsa().getValore();
        int costoAttacco = calcolaCostoAttacco(attaccante);

        attaccante.getRisorsa().recupera(1);

        appendLog("");
        appendLog("Round " + round);
        appendLog("Turno di " + attaccante.getNome() + " contro " + difensore.getNome() + ".");
        appendLog("Risorsa: " + risorsaPrima + " -> " + attaccante.getRisorsa().getValore() + " dopo recupero.");

        if (!attaccante.getRisorsa().consuma(costoAttacco)) {
            appendLog(attaccante.getNome() + " non ha abbastanza " + nomeRisorsa(attaccante) + " per attaccare.");
            appendLog("Turno saltato.");
            prossimoTurno();
            return;
        }

        int dannoBase = attaccante.getAttacco();
        int dannoRimanente = dannoBase;
        int difesaPrima = difensore.getDifesa();
        int hpPrima = difensore.getHp();

        appendLog(attaccante.getNome() + " spende " + costoAttacco + " punti " + nomeRisorsa(attaccante) + ".");
        appendLog("Danno base inflitto: " + dannoBase + ".");

        if (difensore.getDifesa() > 0) {
            if (dannoRimanente <= difensore.getDifesa()) {
                difensore.setDifesa(difensore.getDifesa() - dannoRimanente);
                dannoRimanente = 0;
            } else {
                dannoRimanente -= difensore.getDifesa();
                difensore.setDifesa(0);
            }
        }

        if (dannoRimanente > 0) {
            difensore.setHp(Math.max(0, difensore.getHp() - dannoRimanente));
        }

        appendLog("Difesa " + difensore.getNome() + ": " + difesaPrima + " -> " + difensore.getDifesa() + ".");
        appendLog("HP " + difensore.getNome() + ": " + hpPrima + " -> " + difensore.getHp() + ".");

        if (difensore.getHp() <= 0) {
            appendLog("Vincitore: " + attaccante.getNome() + " (" + attaccante.getClasse() + ").");
            return;
        }

        prossimoTurno();
        appendLog("Prossimo turno: " + giocatoreCorrente.getNome() + ".");
    }

    private boolean combattimentoDisponibile() {
        return giocatore1 != null && giocatore2 != null;
    }

    private boolean combattimentoTerminato() {
        return giocatore1.getHp() <= 0 || giocatore2.getHp() <= 0;
    }

    private int calcolaCostoAttacco(Personaggio personaggio) {
        int consumoArmi = personaggio.getInventario().getOggetti().stream()
                .filter(oggetto -> oggetto.getTipo() == TipoOggetto.ARMA)
                .mapToInt(oggetto -> oggetto.getConsumo())
                .sum();

        return consumoArmi > 0 ? consumoArmi : 2;
    }

    private void prossimoTurno() {
        giocatoreCorrente = giocatoreCorrente == giocatore1 ? giocatore2 : giocatore1;
    }

    private void aggiornaInterfaccia() {
        if (!combattimentoDisponibile()) {
            titoloLabel.setText("Combattimento non inizializzato");
            roundLabel.setText("Round: -");
            turnoLabel.setText("Turno: -");
            statoLabel.setText("Stato: in attesa");
            attaccaButton.setDisable(true);
            resetButton.setDisable(false);
            return;
        }

        titoloLabel.setText(giocatore1.getNome() + " vs " + giocatore2.getNome());
        roundLabel.setText("Round: " + round);
        turnoLabel.setText("Turno: " + giocatoreCorrente.getNome());
        statoLabel.setText("Stato: " + (combattimentoTerminato() ? "terminato" : "in corso"));

        aggiornaSchedaGiocatore(
                giocatore1,
                statoInizialeGiocatore1,
                giocatore1NomeLabel,
                giocatore1ClasseLabel,
                giocatore1HpLabel,
                giocatore1DifesaLabel,
                giocatore1RisorsaLabel,
                giocatore1AttaccoLabel,
                giocatore1HpBar
        );

        aggiornaSchedaGiocatore(
                giocatore2,
                statoInizialeGiocatore2,
                giocatore2NomeLabel,
                giocatore2ClasseLabel,
                giocatore2HpLabel,
                giocatore2DifesaLabel,
                giocatore2RisorsaLabel,
                giocatore2AttaccoLabel,
                giocatore2HpBar
        );

        attaccaButton.setDisable(combattimentoTerminato());
        resetButton.setDisable(false);
    }

    private void aggiornaSchedaGiocatore(
            Personaggio personaggio,
            StatoPersonaggio statoIniziale,
            Label nomeLabel,
            Label classeLabel,
            Label hpLabel,
            Label difesaLabel,
            Label risorsaLabel,
            Label attaccoLabel,
            ProgressBar hpBar
    ) {
        nomeLabel.setText(personaggio.getNome());
        classeLabel.setText("Classe: " + personaggio.getClasse());
        hpLabel.setText("HP: " + personaggio.getHp() + "/" + statoIniziale.hp());
        difesaLabel.setText("Difesa: " + personaggio.getDifesa());
        risorsaLabel.setText(
                personaggio.getRisorsa().getTipo() + ": "
                        + personaggio.getRisorsa().getValore() + "/" + statoIniziale.risorsa()
        );
        attaccoLabel.setText("Attacco: " + personaggio.getAttacco());
        hpBar.setProgress(calcolaProgress(personaggio.getHp(), statoIniziale.hp()));
    }

    private double calcolaProgress(int valoreCorrente, int valoreMassimo) {
        if (valoreMassimo <= 0) {
            return 0;
        }
        return Math.max(0, Math.min(1, (double) valoreCorrente / valoreMassimo));
    }

    private String nomeRisorsa(Personaggio personaggio) {
        return personaggio.getRisorsa().getTipo().name().toLowerCase();
    }

    private void appendLog(String messaggio) {
        if (logArea.getText().isEmpty()) {
            logArea.appendText(messaggio);
        } else {
            logArea.appendText(System.lineSeparator() + messaggio);
        }
    }

    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schermataPrincipale.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private static class StatoPersonaggio {
        private final Personaggio personaggio;
        private final int hp;
        private final int difesa;
        private final int attacco;
        private final int risorsa;

        private StatoPersonaggio(Personaggio personaggio) {
            this.personaggio = personaggio;
            this.hp = personaggio.getHp();
            this.difesa = personaggio.getDifesa();
            this.attacco = personaggio.getAttacco();
            this.risorsa = personaggio.getRisorsa().getValore();
        }

        private Personaggio personaggio() {
            return personaggio;
        }

        private int hp() {
            return hp;
        }

        private int difesa() {
            return difesa;
        }

        private int attacco() {
            return attacco;
        }

        private int risorsa() {
            return risorsa;
        }

        private void ripristina() {
            personaggio.setHp(hp);
            personaggio.setDifesa(difesa);
            personaggio.setAttacco(attacco);
            personaggio.getRisorsa().setValore(risorsa);
        }
    }


}
