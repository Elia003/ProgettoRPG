package it.unicam.cs.mpgc.rpg123022.Controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AdminMainPageController {

    public void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
