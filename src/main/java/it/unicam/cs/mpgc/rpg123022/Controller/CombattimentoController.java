package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.Builder.personaggi.GuerrieroBuilder;
import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi.ClasseStyleResolver;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Enum.TipoOggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class CombattimentoController {
    private static final double SOGLIA_HP_ROSSO = 0.30;
    private static final String COLORE_HP_ALTA = "#2ecc71";
    private static final String COLORE_HP_BASSA = "#e74c3c";
    private static final String COLORE_RISORSA = "#3498db";
    private static final String PROPRIETA_PROGRESS_BARRA = "progressBarra";

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
    private Label giocatore1LivelloLabel;
    @FXML
    private Label giocatore1HpLabel;
    @FXML
    private Label giocatore1HpValoreLabel;
    @FXML
    private Label giocatore1DifesaLabel;
    @FXML
    private Label giocatore1RisorsaLabel;
    @FXML
    private Label giocatore1RisorsaValoreLabel;
    @FXML
    private Label giocatore1AttaccoLabel;
    @FXML
    private Label giocatore2NomeLabel;
    @FXML
    private Label giocatore2ClasseLabel;
    @FXML
    private Label giocatore2LivelloLabel;
    @FXML
    private Label giocatore2HpLabel;
    @FXML
    private Label giocatore2HpValoreLabel;
    @FXML
    private Label giocatore2DifesaLabel;
    @FXML
    private Label giocatore2RisorsaLabel;
    @FXML
    private Label giocatore2RisorsaValoreLabel;
    @FXML
    private Label giocatore2AttaccoLabel;
    @FXML
    private StackPane giocatore1HpBar;
    @FXML
    private Region giocatore1HpBarFill;
    @FXML
    private StackPane giocatore1RisorsaBar;
    @FXML
    private Region giocatore1RisorsaBarFill;
    @FXML
    private StackPane giocatore2HpBar;
    @FXML
    private Region giocatore2HpBarFill;
    @FXML
    private StackPane giocatore2RisorsaBar;
    @FXML
    private Region giocatore2RisorsaBarFill;
    @FXML
    private VBox giocatore1Card;
    @FXML
    private VBox giocatore2Card;
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
        ClasseStyleResolver.applyRoundedClip(giocatore1Card);
        ClasseStyleResolver.applyRoundedClip(giocatore2Card);
        configuraBarraProgressiva(giocatore1HpBar, giocatore1HpBarFill);
        configuraBarraProgressiva(giocatore2HpBar, giocatore2HpBarFill);
        configuraBarraProgressiva(giocatore1RisorsaBar, giocatore1RisorsaBarFill);
        configuraBarraProgressiva(giocatore2RisorsaBar, giocatore2RisorsaBarFill);
        caricaCombattimentoDemo();

        String colore = "gold";

        giocatore1NomeLabel.setTextFill(Paint.valueOf(colore));
        giocatore1ClasseLabel.setTextFill(Paint.valueOf(colore));
        giocatore1LivelloLabel.setTextFill(Paint.valueOf(colore));
        bloccaLarghezzaLabel(giocatore1HpLabel);
        giocatore1HpLabel.setTextFill(Paint.valueOf(colore));
        bloccaLarghezzaLabel(giocatore1HpValoreLabel);
        giocatore1HpValoreLabel.setTextFill(Paint.valueOf(colore));
        giocatore1DifesaLabel.setTextFill(Paint.valueOf(colore));
        bloccaLarghezzaLabel(giocatore1RisorsaLabel);
        giocatore1RisorsaLabel.setTextFill(Paint.valueOf(colore));
        bloccaLarghezzaLabel(giocatore1RisorsaValoreLabel);
        giocatore1RisorsaValoreLabel.setTextFill(Paint.valueOf(colore));
        giocatore1AttaccoLabel.setTextFill(Paint.valueOf(colore));

        giocatore2NomeLabel.setTextFill(Paint.valueOf(colore));
        giocatore2ClasseLabel.setTextFill(Paint.valueOf(colore));
        giocatore2LivelloLabel.setTextFill(Paint.valueOf(colore));
        bloccaLarghezzaLabel(giocatore2HpLabel);
        giocatore2HpLabel.setTextFill(Paint.valueOf(colore));
        bloccaLarghezzaLabel(giocatore2HpValoreLabel);
        giocatore2HpValoreLabel.setTextFill(Paint.valueOf(colore));
        giocatore2DifesaLabel.setTextFill(Paint.valueOf(colore));
        bloccaLarghezzaLabel(giocatore2RisorsaLabel);
        giocatore2RisorsaLabel.setTextFill(Paint.valueOf(colore));
        bloccaLarghezzaLabel(giocatore2RisorsaValoreLabel);
        giocatore2RisorsaValoreLabel.setTextFill(Paint.valueOf(colore));
        giocatore2AttaccoLabel.setTextFill(Paint.valueOf(colore));

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
                .setNome("Brina")
                .setGenere(Genere.Donna)
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
                giocatore1LivelloLabel,
                giocatore1HpLabel,
                giocatore1HpValoreLabel,
                giocatore1DifesaLabel,
                giocatore1RisorsaLabel,
                giocatore1RisorsaValoreLabel,
                giocatore1AttaccoLabel,
                giocatore1HpBar,
                giocatore1HpBarFill,
                giocatore1RisorsaBar,
                giocatore1RisorsaBarFill
        );

        aggiornaSchedaGiocatore(
                giocatore2,
                statoInizialeGiocatore2,
                giocatore2NomeLabel,
                giocatore2ClasseLabel,
                giocatore2LivelloLabel,
                giocatore2HpLabel,
                giocatore2HpValoreLabel,
                giocatore2DifesaLabel,
                giocatore2RisorsaLabel,
                giocatore2RisorsaValoreLabel,
                giocatore2AttaccoLabel,
                giocatore2HpBar,
                giocatore2HpBarFill,
                giocatore2RisorsaBar,
                giocatore2RisorsaBarFill
        );

        attaccaButton.setDisable(combattimentoTerminato());
        resetButton.setDisable(false);

        giocatore1Card.setStyle(ClasseStyleResolver.buildCardStyle(giocatore1.getClasse(),giocatore1.getGenere()));
        giocatore2Card.setStyle(ClasseStyleResolver.buildCardStyle(giocatore2.getClasse(),giocatore2.getGenere()));
    }

    private void aggiornaSchedaGiocatore(
            Personaggio personaggio,
            StatoPersonaggio statoIniziale,
            Label nomeLabel,
            Label classeLabel,
            Label livelloLabel,
            Label hpLabel,
            Label hpValoreLabel,
            Label difesaLabel,
            Label risorsaLabel,
            Label risorsaValoreLabel,
            Label attaccoLabel,
            StackPane hpBar,
            Region hpBarFill,
            StackPane risorsaBar,
            Region risorsaBarFill
    ) {
        nomeLabel.setText(personaggio.getNome());
        classeLabel.setText("Classe: " + personaggio.getClasse());
        livelloLabel.setText("Livello: " + personaggio.getLivello());
        hpLabel.setText("HP:");
        hpValoreLabel.setText(personaggio.getHp() + "/" + statoIniziale.hp());
        difesaLabel.setText("Difesa: " + personaggio.getDifesa());
        risorsaLabel.setText(personaggio.getRisorsa().getTipo() + ":");
        risorsaValoreLabel.setText(personaggio.getRisorsa().getValore() + "/" + statoIniziale.risorsa());
        attaccoLabel.setText("Attacco: " + personaggio.getAttacco());
        double hpProgress = calcolaProgress(personaggio.getHp(), statoIniziale.hp());
        aggiornaStileHpBar(hpBarFill, hpProgress);
        animaBarraProgressiva(hpBar, hpBarFill, hpProgress);
        double risorsaProgress = calcolaProgress(personaggio.getRisorsa().getValore(), statoIniziale.risorsa());
        aggiornaStileRisorsaBar(risorsaBarFill);
        animaBarraProgressiva(risorsaBar, risorsaBarFill, risorsaProgress);
    }

    private double calcolaProgress(int valoreCorrente, int valoreMassimo) {
        if (valoreMassimo <= 0) {
            return 0;
        }
        return Math.max(0, Math.min(1, (double) valoreCorrente / valoreMassimo));
    }

    private void animaBarraProgressiva(StackPane barra, Region riempimentoBarra, double progress) {
        barra.getProperties().put(PROPRIETA_PROGRESS_BARRA, progress);
        double larghezzaBarra = barra.getWidth();
        double larghezzaCorrente = riempimentoBarra.getPrefWidth() >= 0 ? riempimentoBarra.getPrefWidth() : larghezzaBarra;
        double nuovaLarghezza = larghezzaBarra * progress;

        if (larghezzaBarra <= 0) {
            riempimentoBarra.setPrefWidth(0);
            return;
        }

        Timeline animazioneHp = new Timeline(
                new KeyFrame(
                        Duration.millis(50),
                        new KeyValue(riempimentoBarra.prefWidthProperty(), nuovaLarghezza)
                )
        );
        riempimentoBarra.setPrefWidth(larghezzaCorrente);
        animazioneHp.play();
    }

    private void configuraBarraProgressiva(StackPane barra, Region riempimentoBarra) {
        barra.getProperties().put(PROPRIETA_PROGRESS_BARRA, 1.0);
        riempimentoBarra.setMinWidth(0);
        riempimentoBarra.setMaxWidth(Region.USE_PREF_SIZE);
        barra.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            Object progress = barra.getProperties().get(PROPRIETA_PROGRESS_BARRA);
            double percentualeHp = progress instanceof Double ? (Double) progress : 1.0;
            riempimentoBarra.setPrefWidth(newWidth.doubleValue() * percentualeHp);
        });
    }

    private void aggiornaStileHpBar(Region hpBarFill, double progress) {
        String coloreHp = progress <= SOGLIA_HP_ROSSO ? COLORE_HP_BASSA : COLORE_HP_ALTA;
        hpBarFill.setStyle("-fx-background-color: " + coloreHp + "; -fx-background-radius: 999;");
    }

    private void aggiornaStileRisorsaBar(Region risorsaBarFill) {
        risorsaBarFill.setStyle("-fx-background-color: " + COLORE_RISORSA + "; -fx-background-radius: 999;");
    }

    private void bloccaLarghezzaLabel(Label label) {
        label.setMinWidth(label.getPrefWidth());
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
