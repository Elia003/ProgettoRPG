package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataInizialeController {
    @FXML
    private Label titoloLabel;
    @FXML
    private Button singlePlayerButton;
    @FXML
    private Button multiPlayerButton;
    @FXML
    private Button creazionePersonaggioButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button inventarioButton;
    @FXML
    private Button buildsButton;



    @FXML
    public void initialize() {
        Font titoloFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/MedievalSharp-Bold.ttf"),
                115
        );
        Font bottoneFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/MedievalSharp-Bold.ttf"),
                26
        );

        if (titoloFont != null && titoloLabel != null) {
            titoloLabel.setFont(titoloFont);
        }

        if (bottoneFont != null && singlePlayerButton != null) {
            singlePlayerButton.setFont(bottoneFont);
        }

        if (bottoneFont != null && multiPlayerButton != null) {
            multiPlayerButton.setFont(bottoneFont);
        }

        if (bottoneFont != null && creazionePersonaggioButton != null) {
            creazionePersonaggioButton.setFont(bottoneFont);
        }

        if (bottoneFont != null && exitButton != null) {
            exitButton.setFont(bottoneFont);
        }

        if (bottoneFont != null && buildsButton != null) {
            buildsButton.setFont(bottoneFont);
        }

        if (bottoneFont != null && inventarioButton != null) {
            inventarioButton.setFont(bottoneFont);
        }
    }

    @FXML
    public void apriSchermataSingleplayer(ActionEvent event) throws IOException {
        if (ArmadioPersonaggi.getInstance().getNumeroPersonaggi() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Crea almeno un personaggio prima di iniziare in singleplayer.");
            alert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/combattimento.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    public void apriSchermataMultiplayer() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ATTENZIONE!!");
        alert.setHeaderText(null);
        alert.setContentText("Presto disponibile...");
        alert.showAndWait();
    }
    @FXML
    public void apriSchermataCreazionePersonaggio(ActionEvent event) throws IOException {
        Parent root =FXMLLoader.load(getClass().getResource("/creazionePersonaggio.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void exit(){
        Platform.exit();
    }

    public void apriSchermataBuilds(ActionEvent event) throws IOException {
        if (ArmadioPersonaggi.getInstance().getNumeroPersonaggi() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Crea almeno un personaggio prima");
            alert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/armadioPersonaggi.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void apriSchermataInventario(ActionEvent event) throws IOException {
        if (ArmadioPersonaggi.getInstance().getNumeroPersonaggi() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Impossibile visualizzare l'inventario non hai nessun personaggio creato");
            alert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/inventarioPersonaggio.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
