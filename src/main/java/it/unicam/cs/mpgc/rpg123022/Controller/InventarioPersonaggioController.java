package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.Oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.io.IOException;


public class InventarioPersonaggioController {
    @FXML
    private Label titoloLabel;
    @FXML
    private ListView<Oggetto> listaOggettiPersonaggio;


    public void initialize(){
        Font titoloFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/MedievalSharp-Bold.ttf"),
                40
        );

        Personaggio personaggioCorrente = ArmadioPersonaggi.getInstance().getPersonaggioCorrente();

        caricaInventario(personaggioCorrente, listaOggettiPersonaggio);

        setCellFactory(listaOggettiPersonaggio);

        if (personaggioCorrente != null) {
            titoloLabel.setText("Inventario di " + personaggioCorrente.getNome());
        }

        if (titoloFont != null && titoloLabel != null) {
            titoloLabel.setFont(titoloFont);
        }
    }

    private void caricaInventario(Personaggio p, ListView<Oggetto> listView) {
        if (p != null &&
                p.getInventario() != null &&
                p.getInventario().getOggetti() != null) {

            listView.getItems().setAll(p.getInventario().getOggetti());
        }
    }

    private void setCellFactory(ListView<Oggetto> listView) {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Oggetto item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });
    }

    @FXML
    private void rimuoviOggetto(ActionEvent event) {
        Oggetto oggettoSelezionato = listaOggettiPersonaggio.getSelectionModel().getSelectedItem();

        if (oggettoSelezionato == null) {
            mostraMessaggio(
                    Alert.AlertType.WARNING,
                    "Oggetto non selezionato",
                    "Seleziona un oggetto da rimuovere"
            );
        }else{
            Personaggio p = ArmadioPersonaggi.getInstance().getPersonaggioCorrente();

            if (p != null && p.getInventario().getOggetti().contains(oggettoSelezionato)) {
                p.rimuovi(oggettoSelezionato);
                listaOggettiPersonaggio.getItems().remove(oggettoSelezionato);
                listaOggettiPersonaggio.getSelectionModel().clearSelection();

                mostraMessaggio(
                        Alert.AlertType.INFORMATION,
                        "Oggetto rimosso",
                        "Oggetto " + oggettoSelezionato.getNome()
                                + " rimosso dall'inventario con successo "
                );
            }
        }
    }

    private void mostraMessaggio(Alert.AlertType tipo, String titolo, String messaggio) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schermataPrincipale.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
