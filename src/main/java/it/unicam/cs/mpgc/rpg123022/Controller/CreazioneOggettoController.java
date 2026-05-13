package it.unicam.cs.mpgc.rpg123022.Controller;

import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaAttacco;
import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaDifesa;
import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaRisorsa;
import it.unicam.cs.mpgc.rpg123022.Aumenti.Effetto;
import it.unicam.cs.mpgc.rpg123022.Builder.OggettoBuilder;
import it.unicam.cs.mpgc.rpg123022.Enum.*;
import it.unicam.cs.mpgc.rpg123022.ListaOggettiTotale;
import it.unicam.cs.mpgc.rpg123022.Oggetto;
import it.unicam.cs.mpgc.rpg123022.Risorse.Risorsa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreazioneOggettoController {
    @FXML private TextField nomeOggettoField;
    @FXML private ChoiceBox<TipoOggetto> tipoChoiceBox;
    @FXML private ChoiceBox<PesoOggetto> pesoChoiceBox;
    @FXML private TextField consumoField;
    @FXML private CheckBox magoCheckBox;
    @FXML private CheckBox barbaroCheckBox;
    @FXML private CheckBox druidoCheckBox;
    @FXML private CheckBox ladroCheckBox;
    @FXML private CheckBox guerrieroCheckBox;
    @FXML private ChoiceBox<Rarita> raritaChoiceBox;
    @FXML private CheckBox aumentoAttaccoCheckBox;
    @FXML private CheckBox aumentoDifesaCheckBox;
    @FXML private CheckBox aumentoRisorsaCheckBox;
    @FXML private TextField buffAttaccoField;
    @FXML private TextField buffDifesaField;
    @FXML private TextField buffRisorsaField;
    @FXML private ChoiceBox<TipoRisorsa> risorsaChoiceBox;


    public void initialize(){
        tipoChoiceBox.getItems().addAll(TipoOggetto.values());
        pesoChoiceBox.getItems().addAll(PesoOggetto.values());
        raritaChoiceBox.getItems().addAll(Rarita.values());
        risorsaChoiceBox.getItems().addAll(TipoRisorsa.values());

        aumentoAttaccoCheckBox.setSelected(false);
        aumentoDifesaCheckBox.setSelected(false);
        aumentoRisorsaCheckBox.setSelected(false);

        magoCheckBox.setSelected(false);
        barbaroCheckBox.setSelected(false);
        druidoCheckBox.setSelected(false);
        ladroCheckBox.setSelected(false);
        guerrieroCheckBox.setSelected(false);

    }

    @FXML
    private void toggleBuffAttacco() {
        boolean selezionato = aumentoAttaccoCheckBox.isSelected();
        buffAttaccoField.setVisible(selezionato);
        buffAttaccoField.setManaged(selezionato);
    }

    @FXML
    private void toggleBuffDifesa() {
        boolean selezionato = aumentoDifesaCheckBox.isSelected();
        buffDifesaField.setVisible(selezionato);
        buffDifesaField.setManaged(selezionato);
    }

    @FXML
    private void toggleBuffrisorsa() {
        boolean selezionato = aumentoRisorsaCheckBox.isSelected();
        buffRisorsaField.setVisible(selezionato);
        buffRisorsaField.setManaged(selezionato);
        risorsaChoiceBox.setVisible(selezionato);
        risorsaChoiceBox.setManaged(selezionato);
    }

    public void creaOggetto(ActionEvent event) throws IOException {
        String nomeOggetto = nomeOggettoField.getText();
        int consumo = Integer.parseInt(consumoField.getText());
        TipoOggetto tipoOggetto = tipoChoiceBox.getValue();
        PesoOggetto pesoOggetto = pesoChoiceBox.getValue();
        Rarita rarita = raritaChoiceBox.getValue();
        Set<Classe> classiCompatibili = new HashSet<Classe>();
        List<Effetto> effetti = new ArrayList<Effetto>();

        if(magoCheckBox.isSelected()){
            classiCompatibili.add(Classe.MAGO);
        }

        if(barbaroCheckBox.isSelected()){
            classiCompatibili.add(Classe.BARBARO);
        }

        if(druidoCheckBox.isSelected()){
            classiCompatibili.add(Classe.DRUIDO);
        }

        if(ladroCheckBox.isSelected()){
            classiCompatibili.add(Classe.LADRO);
        }

        if(guerrieroCheckBox.isSelected()){
            classiCompatibili.add(Classe.GUERRIERO);
        }

        if (aumentoAttaccoCheckBox.isSelected() && !buffAttaccoField.getText().isBlank()) {
            int valoreAttacco = Integer.parseInt(buffAttaccoField.getText());
            effetti.add(new AumentaAttacco(valoreAttacco));
        }
        if(aumentoDifesaCheckBox.isSelected() && !buffDifesaField.getText().isBlank()){
            int valoreDifesa = Integer.parseInt(buffDifesaField.getText());
            effetti.add(new AumentaDifesa(valoreDifesa));
        }
        if (aumentoRisorsaCheckBox.isSelected() && !buffRisorsaField.getText().isBlank()) {
            int valoreRisorsa = Integer.parseInt(buffRisorsaField.getText());
            TipoRisorsa tipoRisorsa = risorsaChoiceBox.getValue();
            effetti.add(new AumentaRisorsa(valoreRisorsa, tipoRisorsa));
        }

        Oggetto oggetto = buildOggetto(nomeOggetto,tipoOggetto,pesoOggetto,classiCompatibili,effetti,consumo,rarita);
        ListaOggettiTotale.getInstance().aggiungiOggetto(oggetto);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oggetto");
        alert.setHeaderText(null);
        alert.setContentText("Oggetto "+ oggetto.getNome() +" creato con successo");
        alert.showAndWait();

        //Ritorno alla schermata iniziale dopo aver creato un oggetto
        Parent root = FXMLLoader.load(getClass().getResource("/Admin/adminMainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();


    }

    public Oggetto buildOggetto(String nome, TipoOggetto tipo, PesoOggetto peso,
                               Set<Classe> classiCompatibili, List<Effetto> effetti,int consumo,Rarita rarita){

        Oggetto o = new OggettoBuilder().setNome(nome)
                .setTipo(tipo)
                .setPeso(peso)
                .addClassiCompatibili(classiCompatibili.toArray(new Classe[0]))
                .addEffetto(effetti.toArray(new Effetto[0]))
                .setConsumo(consumo)
                .setRarita(rarita)
                .build();

        return o;

    }

}
