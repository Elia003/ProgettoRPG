package it.unicam.cs.mpgc.rpg123022.Controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMainPageController {

    public void apriSchermataCreazioneOggetto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Admin/creazioneOggetto.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void apriSchermataListaOggetti(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Admin/listaOggettiTotale.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
