package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.Oggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


import java.io.IOException;


public class InventarioPersonaggioController {
    @FXML
    private Label titoloLabel;
    @FXML
    private ListView<Oggetto> listaOggettiPersonaggio;


    public void initialize(){
        Personaggio personaggioCorrente = ArmadioPersonaggi.getInstance().getPersonaggioCorrente();

        caricaInventario(personaggioCorrente, listaOggettiPersonaggio);

        setCellFactory(listaOggettiPersonaggio);

        if (personaggioCorrente != null) {
            titoloLabel.setText("Inventario di " + personaggioCorrente.getNome());
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

    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schermataPrincipale.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
