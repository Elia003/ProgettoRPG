package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.Database.OggettoDao;
import it.unicam.cs.mpgc.rpg123022.Database.PersonaggioDao;
import it.unicam.cs.mpgc.rpg123022.Oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DatabaseViewController {
    @FXML
    private ComboBox<String> vistaComboBox;

    @FXML
    private TextField ricercaField;

    @FXML
    private Label statoLabel;

    @FXML
    private TableView<RigaDatabase> risultatiTableView;

    @FXML
    private TableColumn<RigaDatabase, String> categoriaColumn;

    @FXML
    private TableColumn<RigaDatabase, String> idColumn;

    @FXML
    private TableColumn<RigaDatabase, String> nomeColumn;

    @FXML
    private TableColumn<RigaDatabase, String> dettaglioColumn;

    private final PersonaggioDao personaggioDao = new PersonaggioDao();
    private final OggettoDao oggettoDao = new OggettoDao();

    @FXML
    public void initialize() {
        vistaComboBox.getItems().addAll("Personaggi", "Oggetti");
        vistaComboBox.setValue("Personaggi");
        risultatiTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        categoriaColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().categoria()));
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().id()));
        nomeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().nome()));
        dettaglioColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().dettaglio()));

        aggiornaVista();
    }

    @FXML
    public void aggiornaVista() {
        try {
            String vistaSelezionata = vistaComboBox.getValue();
            String filtro = ricercaField.getText() == null ? "" : ricercaField.getText().trim().toLowerCase();

            ObservableList<RigaDatabase> righe = FXCollections.observableArrayList();

            if ("Oggetti".equals(vistaSelezionata)) {
                List<Oggetto> oggetti = oggettoDao.findAll();
                for (Oggetto oggetto : oggetti) {
                    if (filtro.isBlank() || oggetto.getNome().toLowerCase().contains(filtro)) {
                        righe.add(new RigaDatabase(
                                "Oggetto",
                                "-",
                                oggetto.getNome(),
                                "Tipo: " + oggetto.getTipo()
                                        + " | Rarita: " + oggetto.getRarita()
                                        + " | Effetti: " + oggetto.getEffetto()
                        ));
                    }
                }
            } else {
                List<Personaggio> personaggi = personaggioDao.findAll();
                for (Personaggio personaggio : personaggi) {
                    if (filtro.isBlank() || personaggio.getNome().toLowerCase().contains(filtro)) {
                        righe.add(new RigaDatabase(
                                "Personaggio",
                                String.valueOf(personaggio.getId()),
                                personaggio.getNome(),
                                "Classe: " + personaggio.getClasse()
                                        + " | Livello: " + personaggio.getLivello()
                                        + " | Risorsa: " + personaggio.getRisorsa()
                        ));
                    }
                }
            }

            risultatiTableView.setItems(righe);
            statoLabel.setText("Risultati trovati: " + righe.size());
        } catch (Exception e) {
            risultatiTableView.setItems(FXCollections.observableArrayList());
            statoLabel.setText("Errore nel caricamento del database");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore database");
            alert.setHeaderText("Impossibile caricare i dati");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void pulisciRicerca() {
        ricercaField.clear();
        aggiornaVista();
    }

    @FXML
    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Admin/adminMainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public record RigaDatabase(String categoria, String id, String nome, String dettaglio) {
    }
}
