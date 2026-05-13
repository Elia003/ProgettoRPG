package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ListaOggettiTotale;
import it.unicam.cs.mpgc.rpg123022.Oggetto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ListaOggettiTotaleController {
    @FXML private ListView<Oggetto> oggettoListView;

    public void initialize() {
        oggettoListView.getItems().setAll(ListaOggettiTotale.getInstance().getListaOggetti());

        oggettoListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Oggetto oggetto, boolean empty) {
                super.updateItem(oggetto, empty);

                if (empty || oggetto == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(oggetto.toString());

                switch (oggetto.getRarita()) {
                    case COMUNE -> setStyle("-fx-text-fill: black; -fx-background-color: white");
                    case NON_COMUNE -> setStyle("-fx-text-fill: black; -fx-background-color: #7e7d7d;");
                    case RARO -> setStyle("-fx-text-fill: black; -fx-background-color: #0404ff;");
                    case EPICO -> setStyle("-fx-text-fill: black; -fx-background-color: #ba00ba;");
                    case LEGGENDARIO -> setStyle("-fx-text-fill: black; -fx-background-color: gold;");
                }
            }
        });
    }


    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Admin/AdminMainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
