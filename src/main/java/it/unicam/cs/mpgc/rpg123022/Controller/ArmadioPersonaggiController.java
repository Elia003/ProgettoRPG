package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ArmadioPersonaggiController {

    @FXML
    private Label personaggio1NomeLabel;
    @FXML
    private Label personaggio1ClasseLabel;
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

    private final String BASE_STYLE =
            "-fx-background-color: rgba(198, 179, 170, 1);" +
                    "-fx-border-color: black;" +
                    "-fx-border-radius: 11;" +
                    "-fx-border-width: 3;"+
                    "-fx-background-radius: 14;" +
                    "-fx-padding: 18;";

    private final String ATTIVO_STYLE =
            "-fx-background-color: rgba(198, 179, 170, 1) ;" +
                    "-fx-border-color: gold;" +
                    "-fx-border-width: 3;" +
                    "-fx-background-radius: 14;" +
                    "-fx-border-radius: 11;" +
                    "-fx-padding: 18;";



    public void initialize(){
        List<Personaggio> listaPersonaggi = ArmadioPersonaggi.getInstance().getPersonaggi();

        if (listaPersonaggi.isEmpty()) return;

        boxes = List.of(boxP1, boxP2, boxP3);

        resetColori();

        Personaggio p1 = listaPersonaggi.get(0);
        setPersonaggio1(p1);
        setAttivo(boxP1, p1);

        ArmadioPersonaggi.getInstance().setPersonaggioCorrente(p1);


        if (listaPersonaggi.size() > 1) {
            setPersonaggio2(listaPersonaggi.get(1));
        }
        if (listaPersonaggi.size() > 2) {
            setPersonaggio3(listaPersonaggi.get(2));
        }

    }

    private void setPersonaggio1(Personaggio p) {
        personaggio1NomeLabel.setText("Nome: " + p.getNome());
        personaggio1ClasseLabel.setText("Classe: " + p.getClasse());
        personaggio1GenereLabel.setText("Genere: " + p.getGenere());
        personaggio1AttaccoLabel.setText("Attacco: " + p.getAttacco());
        personaggio1DifesaLabel.setText("Difesa: " + p.getDifesa());
        personaggio1RisorsaLabel.setText("Risorsa: " + p.getRisorsa());
        personaggio1HpLabel.setText("HP: " + p.getHp());
    }

    private void setPersonaggio2(Personaggio p) {
        personaggio2NomeLabel.setText("Nome: " + p.getNome());
        personaggio2ClasseLabel.setText("Classe: " + p.getClasse());
        personaggio2GenereLabel.setText("Genere: " + p.getGenere());
        personaggio2AttaccoLabel.setText("Attacco: " + p.getAttacco());
        personaggio2DifesaLabel.setText("Difesa: " + p.getDifesa());
        personaggio2RisorsaLabel.setText("Risorsa: " + p.getRisorsa());
        personaggio2HpLabel.setText("HP: " + p.getHp());
    }

    private void setPersonaggio3(Personaggio p) {
        personaggio3NomeLabel.setText("Nome: " + p.getNome());
        personaggio3ClasseLabel.setText("Classe: " + p.getClasse());
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

    private void resetColori() {
        for (VBox b : boxes) {
            b.setStyle(BASE_STYLE);
        }
    }

    private void setAttivo(VBox boxSelezionato, Personaggio p) {
        resetColori();
        boxSelezionato.setStyle(ATTIVO_STYLE);

        ArmadioPersonaggi.getInstance().setPersonaggioCorrente(p);
    }
}
