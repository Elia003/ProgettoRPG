package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.Database.PersonaggioDao;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SchermataInizialeController {
    private final PersonaggioDao personaggioDao = new PersonaggioDao();

    @FXML
    private Label titoloLabel;
    @FXML
    private Button iniziaButton;

    public void initialize(){
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
        if(bottoneFont != null && iniziaButton != null) {
            iniziaButton.setFont(bottoneFont);
        }

    }

    public void inizia(ActionEvent event) throws IOException {
        ArmadioPersonaggi armadioPersonaggi = ArmadioPersonaggi.getInstance();
        List<Personaggio> personaggiSalvati = personaggioDao.findAll();

        if (!personaggiSalvati.isEmpty()) {
            armadioPersonaggi.caricaPersonaggi(personaggiSalvati);
            Parent root = FXMLLoader.load(getClass().getResource("/schermataPrincipale.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/creaPrimoPersonaggio.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
