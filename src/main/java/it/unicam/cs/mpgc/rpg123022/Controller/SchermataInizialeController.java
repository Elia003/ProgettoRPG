package it.unicam.cs.mpgc.rpg123022.Controller;

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

public class SchermataInizialeController {
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
        Parent root = FXMLLoader.load(getClass().getResource("/creaPrimoPersonaggio.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
