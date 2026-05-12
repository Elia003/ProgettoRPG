package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.ClasseStyleResolver;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ArmadioPersonaggiController {
    private static final String EMPTY_STYLE =
            "-fx-background-color: rgba(198, 179, 170, 1);" +
                    "-fx-border-color: black;" +
                    "-fx-border-radius: 11;" +
                    "-fx-border-width: 3;" +
                    "-fx-background-radius: 14;" +
                    "-fx-padding: 18;";

    @FXML
    private Label personaggio1NomeLabel;
    @FXML
    private Label personaggio1ClasseLabel;
    @FXML
    private Label personaggio1LivelloLabel;
    @FXML
    private Label personaggio1HpLabel;
    @FXML
    private Label personaggio1DifesaLabel;
    @FXML
    private Label personaggio1RisorsaLabel;
    @FXML
    private Label personaggio1AttaccoLabel;
    @FXML
    private Label personaggio1GenereLabel;
    @FXML
    private Label personaggio2NomeLabel;
    @FXML
    private Label personaggio2ClasseLabel;
    @FXML
    private Label personaggio2LivelloLabel;
    @FXML
    private Label personaggio2HpLabel;
    @FXML
    private Label personaggio2DifesaLabel;
    @FXML
    private Label personaggio2RisorsaLabel;
    @FXML
    private Label personaggio2AttaccoLabel;
    @FXML
    private Label personaggio2GenereLabel;
    @FXML
    private Label personaggio3NomeLabel;
    @FXML
    private Label personaggio3ClasseLabel;
    @FXML
    private Label personaggio3LivelloLabel;
    @FXML
    private Label personaggio3HpLabel;
    @FXML
    private Label personaggio3DifesaLabel;
    @FXML
    private Label personaggio3RisorsaLabel;
    @FXML
    private Label personaggio3AttaccoLabel;
    @FXML
    private Label personaggio3GenereLabel;
    @FXML
    private Label titoloLabel;
    @FXML
    private VBox boxP1;
    @FXML
    private VBox boxP2;
    @FXML
    private VBox boxP3;

    private List<VBox> boxes;

    public void initialize(){
        boxes = List.of(boxP1, boxP2, boxP3);
        ClasseStyleResolver.applyRoundedClip(boxP1);
        ClasseStyleResolver.applyRoundedClip(boxP2);
        ClasseStyleResolver.applyRoundedClip(boxP3);

        Font titoloFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/MedievalSharp-Bold.ttf"),
                60
        );

        if (titoloFont != null && titoloLabel != null) {
            titoloLabel.setFont(titoloFont);
        }

        aggiornaVista();
    }

    private void setPersonaggio1(Personaggio p) {
        boxP1.setStyle(ClasseStyleResolver.buildCardStyle(p.getClasse()));
        personaggio1NomeLabel.setText("Nome: " + p.getNome());
        personaggio1ClasseLabel.setText("Classe: " + p.getClasse());
        personaggio1LivelloLabel.setText("Livello: " + p.getLivello());
        personaggio1GenereLabel.setText("Genere: " + p.getGenere());
        personaggio1AttaccoLabel.setText("Attacco: " + p.getAttacco());
        personaggio1DifesaLabel.setText("Difesa: " + p.getDifesa());
        personaggio1RisorsaLabel.setText("Risorsa: " + p.getRisorsa());
        personaggio1HpLabel.setText("HP: " + p.getHp());
    }

    private void setPersonaggio2(Personaggio p) {
        boxP2.setStyle(ClasseStyleResolver.buildCardStyle(p.getClasse()));
        personaggio2NomeLabel.setText("Nome: " + p.getNome());
        personaggio2ClasseLabel.setText("Classe: " + p.getClasse());
        personaggio2LivelloLabel.setText("Livello: " + p.getLivello());
        personaggio2GenereLabel.setText("Genere: " + p.getGenere());
        personaggio2AttaccoLabel.setText("Attacco: " + p.getAttacco());
        personaggio2DifesaLabel.setText("Difesa: " + p.getDifesa());
        personaggio2RisorsaLabel.setText("Risorsa: " + p.getRisorsa());
        personaggio2HpLabel.setText("HP: " + p.getHp());
    }

    private void setPersonaggio3(Personaggio p) {
        boxP3.setStyle(ClasseStyleResolver.buildCardStyle(p.getClasse()));
        personaggio3NomeLabel.setText("Nome: " + p.getNome());
        personaggio3ClasseLabel.setText("Classe: " + p.getClasse());
        personaggio3LivelloLabel.setText("Livello: " + p.getLivello());
        personaggio3GenereLabel.setText("Genere: " + p.getGenere());
        personaggio3AttaccoLabel.setText("Attacco: " + p.getAttacco());
        personaggio3DifesaLabel.setText("Difesa: " + p.getDifesa());
        personaggio3RisorsaLabel.setText("Risorsa: " + p.getRisorsa());
        personaggio3HpLabel.setText("HP: " + p.getHp());
    }

    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schermataPrincipale.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void impostaPersonaggio1Attivo() {
        Personaggio p = ArmadioPersonaggi.getInstance().getPersonaggi().get(0);
        setAttivo(boxP1, p);
    }
    public void impostaPersonaggio2Attivo() {
        Personaggio p = ArmadioPersonaggi.getInstance().getPersonaggi().get(1);
        setAttivo(boxP2, p);
    }
    public void impostaPersonaggio3Attivo() {
        Personaggio p = ArmadioPersonaggi.getInstance().getPersonaggi().get(2);
        setAttivo(boxP3, p);
    }

    public void eliminaPersonaggio1() {
        eliminaPersonaggio(0);
    }

    public void eliminaPersonaggio2() {
        eliminaPersonaggio(1);
    }

    public void eliminaPersonaggio3() {
        eliminaPersonaggio(2);
    }

    private void resetColori() {
        List<Personaggio> personaggi = ArmadioPersonaggi.getInstance().getPersonaggi();
        for (int i = 0; i < boxes.size(); i++) {
            VBox box = boxes.get(i);
            if (i < personaggi.size()) {
                box.setStyle(ClasseStyleResolver.buildCardStyle(personaggi.get(i).getClasse()));
            } else {
                box.setStyle(EMPTY_STYLE);
            }
        }
    }

    private void setAttivo(VBox boxSelezionato, Personaggio p) {
        resetColori();
        boxSelezionato.setStyle(ClasseStyleResolver.buildCardStyle(p.getClasse(), true));

        ArmadioPersonaggi.getInstance().setPersonaggioCorrente(p);
    }

    private void eliminaPersonaggio(int indice) {
        List<Personaggio> personaggi = ArmadioPersonaggi.getInstance().getPersonaggi();
        if (indice >= personaggi.size()) {
            return;
        }

        Personaggio personaggio = personaggi.get(indice);
        ArmadioPersonaggi.getInstance().rimuoviPersonaggio(personaggio);
        aggiornaVista();
    }

    private void aggiornaVista() {
        List<Personaggio> listaPersonaggi = ArmadioPersonaggi.getInstance().getPersonaggi();
        resetCampi();
        resetColori();

        if (listaPersonaggi.isEmpty()) {
            return;
        }

        setPersonaggio1(listaPersonaggi.get(0));
        if (listaPersonaggi.size() > 1) {
            setPersonaggio2(listaPersonaggi.get(1));
        }
        if (listaPersonaggi.size() > 2) {
            setPersonaggio3(listaPersonaggi.get(2));
        }

        Personaggio corrente = ArmadioPersonaggi.getInstance().getPersonaggioCorrente();
        if (corrente == null) {
            corrente = listaPersonaggi.get(0);
            ArmadioPersonaggi.getInstance().setPersonaggioCorrente(corrente);
        }

        if (corrente == listaPersonaggi.get(0)) {
            setAttivo(boxP1, corrente);
        } else if (listaPersonaggi.size() > 1 && corrente == listaPersonaggi.get(1)) {
            setAttivo(boxP2, corrente);
        } else if (listaPersonaggi.size() > 2 && corrente == listaPersonaggi.get(2)) {
            setAttivo(boxP3, corrente);
        } else {
            setAttivo(boxP1, listaPersonaggi.get(0));
        }
    }

    private void resetCampi() {
        personaggio1NomeLabel.setText("Nome: -");
        personaggio1ClasseLabel.setText("Classe: -");
        personaggio1LivelloLabel.setText("Livello: -");
        personaggio1GenereLabel.setText("Genere: -");
        personaggio1AttaccoLabel.setText("Attacco: -");
        personaggio1DifesaLabel.setText("Difesa: -");
        personaggio1RisorsaLabel.setText("Risorsa: -");
        personaggio1HpLabel.setText("HP: -");

        personaggio2NomeLabel.setText("Nome: -");
        personaggio2ClasseLabel.setText("Classe: -");
        personaggio2LivelloLabel.setText("Livello: -");
        personaggio2GenereLabel.setText("Genere: -");
        personaggio2AttaccoLabel.setText("Attacco: -");
        personaggio2DifesaLabel.setText("Difesa: -");
        personaggio2RisorsaLabel.setText("Risorsa: -");
        personaggio2HpLabel.setText("HP: -");

        personaggio3NomeLabel.setText("Nome: -");
        personaggio3ClasseLabel.setText("Classe: -");
        personaggio3LivelloLabel.setText("Livello: -");
        personaggio3GenereLabel.setText("Genere: -");
        personaggio3AttaccoLabel.setText("Attacco: -");
        personaggio3DifesaLabel.setText("Difesa: -");
        personaggio3RisorsaLabel.setText("Risorsa: -");
        personaggio3HpLabel.setText("HP: -");
    }


}
