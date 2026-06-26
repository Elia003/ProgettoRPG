package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.Database.OggettoDao;
import it.unicam.cs.mpgc.rpg123022.Oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ListaOggettiTotaleController {
    private final OggettoDao oggettoDao = new OggettoDao();

    @FXML private ListView<Oggetto> oggettoListView;

    @FXML private Button addOggettoButton;

    public void initialize() {
        oggettoListView.getItems().setAll(oggettoDao.findAll());

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
                applicaStile(oggetto, isSelected());
            }

            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);

                Oggetto oggetto = getItem();
                if (oggetto == null || isEmpty()) {
                    return;
                }

                applicaStile(oggetto, selected);
            }

            private void applicaStile(Oggetto oggetto, boolean selected) {
                String stileBase = switch (oggetto.getRarita()) {
                    case COMUNE -> "-fx-text-fill: black; -fx-background-color: white;";
                    case NON_COMUNE -> "-fx-text-fill: black; -fx-background-color: #7e7d7d;";
                    case RARO -> "-fx-text-fill: black; -fx-background-color: #0404ff;";
                    case EPICO -> "-fx-text-fill: black; -fx-background-color: #ba00ba;";
                    case LEGGENDARIO -> "-fx-text-fill: black; -fx-background-color: gold;";
                };

                if (selected) {
                    setStyle(stileBase + "-fx-border-color: black; -fx-border-width: 3;");
                } else {
                    setStyle(stileBase + "-fx-border-color: transparent; -fx-border-width: 0;");
                }
            }
        });
    }

    public void equipaggia(ActionEvent event) {
        Oggetto oggettoSelezionato = oggettoListView.getSelectionModel().getSelectedItem();

        if (oggettoSelezionato == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText(null);
            alert.setContentText("Seleziona un'oggetto prima ");
            alert.showAndWait();
        }else{

        Personaggio p = ArmadioPersonaggi.getInstance().getPersonaggioCorrente();
        if (!oggettoSelezionato.getClasseCompatibili().contains(p.getClasse())) {
            mostraMessaggio(
                    Alert.AlertType.WARNING,
                    "Classe non compatibile",
                    "Non puoi equipaggiare questo oggetto con la classe " + p.getClasse()
                            + ". Classi compatibili: " + oggettoSelezionato.getClasseCompatibili()
            );
            return;
        }

        int oggettiPrima = p.getInventario().getOggetti().size();
        p.equipaggia(oggettoSelezionato);
        if (p.getInventario().getOggetti().size() > oggettiPrima) {
            mostraMessaggio(
                    Alert.AlertType.INFORMATION,
                    "Equipaggiamento",
                    "Oggetto equipaggiato con successo"
            );
        } else {
            mostraMessaggio(
                    Alert.AlertType.WARNING,
                    "Inventario pieno",
                    "Non puoi equipaggiare questo oggetto: spazio nell'inventario insufficiente"
            );
        }
        oggettoListView.getSelectionModel().clearSelection();

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
