package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi;
import it.unicam.cs.mpgc.rpg123022.Builder.personaggi.*;
import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreazionePersonaggioController {
    private final ArmadioPersonaggi armadioPersonaggi = ArmadioPersonaggi.getInstance();

    @FXML
    private TextField nomeField;
    @FXML
    private TextField coloreCapelliField;
    @FXML
    private ChoiceBox<Classe> classeChoiceBox;
    @FXML
    private ChoiceBox<Genere> genereChoiceBox;

    public void initialize(){
        classeChoiceBox.getItems().addAll(Classe.values());
        genereChoiceBox.getItems().addAll(Genere.values());
    }

    public void createPersonaggio(ActionEvent event) throws IOException {
        String nome = nomeField.getText();
        String coloreCapelli = coloreCapelliField.getText();
        Classe classe = classeChoiceBox.getValue();
        Genere genere = genereChoiceBox.getValue();

        if(nome.isEmpty() ||  coloreCapelli.isEmpty() || genere == null || classe == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Compila tutti i campi");
            alert.showAndWait();

        } else if (!armadioPersonaggi.puoCreareNuovoPersonaggio()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Limite raggiunto");
            alert.setHeaderText(null);
            alert.setContentText("Puoi creare al massimo " + ArmadioPersonaggi.MAX_PERSONAGGI + " personaggi.");
            alert.showAndWait();
        }else{
        Personaggio personaggio = creaPersonaggio(nome, coloreCapelli, classe, genere);
        if (!armadioPersonaggi.aggiungiPersonaggio(personaggio)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Limite raggiunto");
            alert.setHeaderText(null);
            alert.setContentText("Puoi creare al massimo " + ArmadioPersonaggi.MAX_PERSONAGGI + " personaggi.");
            alert.showAndWait();
            return;
        }


        //Alert per la corretta creazione di un personaggio
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(classe + " creato con successo. Personaggi creati: "
                + armadioPersonaggi.getNumeroPersonaggi() + "/" + ArmadioPersonaggi.MAX_PERSONAGGI);
        alert.showAndWait();
        //Ritorno alla schermata iniziale dopo aver creato un personaggio
        Parent root = FXMLLoader.load(getClass().getResource("/schermataPrincipale.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();}

    }

    private Personaggio creaPersonaggio(String nome,String coloreCapelli,Classe classe, Genere genere) {
        int id = armadioPersonaggi.getProssimoId();

        return switch (classe){
            case MAGO -> new MagoBuilder()
                    .setId(id)
                    .setNome(nome)
                    .setGenere(genere)
                    .setColoreCapelli(coloreCapelli)
                    .build();

            case GUERRIERO -> new GuerrieroBuilder()
                    .setId(id)
                    .setNome(nome)
                    .setGenere(genere)
                    .setColoreCapelli(coloreCapelli)
                    .build();

            case BARBARO -> new BarbaroBuilder()
                    .setId(id)
                    .setNome(nome)
                    .setGenere(genere)
                    .setColoreCapelli(coloreCapelli)
                    .build();

            case DRUIDO -> new DruidoBuilder()
                    .setId(id)
                    .setNome(nome)
                    .setGenere(genere)
                    .setColoreCapelli(coloreCapelli)
                    .build();

            case LADRO -> new LadroBuilder()
                    .setId(id)
                    .setNome(nome)
                    .setGenere(genere)
                    .setColoreCapelli(coloreCapelli)
                    .build();
        };
    }

    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schermataPrincipale.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
