package it.unicam.cs.mpgc.rpg123022.Controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordVisibleField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox showPasswordCheckBox;

    private final String username;
    private final String chiave;

    public AdminController() {
        this.username = "admin";
        this.chiave = "password";
    }

    public void initialize(){
        passwordVisibleField.setDisable(false);
        passwordVisibleField.setManaged(false);

        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());
    }
    @FXML
    private void togglePasswordVisible(){
        boolean mostra = showPasswordCheckBox.isSelected();

        passwordVisibleField.setVisible(mostra);
        passwordVisibleField.setManaged(mostra);

        passwordField.setVisible(!mostra);
        passwordField.setManaged(!mostra);
    }

    public void controllo(ActionEvent event) throws IOException {
        String usernameInserito = usernameField.getText();
        String passwordInserita = passwordField.getText();


        if(usernameInserito.isEmpty() || passwordInserita.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENZIONE!");
            alert.setHeaderText(null);
            alert.setContentText("Compila tutti i campi");
            alert.showAndWait();
        }else {

            if (usernameInserito.equals(username) && passwordInserita.equals(chiave)) {
                Parent root = FXMLLoader.load(getClass().getResource("/Admin/adminMainPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Username o Password errati");
                alert.showAndWait();
            }

        }
    }

    @FXML
    public void exit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
