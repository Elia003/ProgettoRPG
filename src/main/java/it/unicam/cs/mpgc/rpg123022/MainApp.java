package it.unicam.cs.mpgc.rpg123022;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(MainApp.class.getResourceAsStream("/fonts/MedievalSharp-Bold.ttf"), 24);

        FXMLLoader loaderIniziale = new FXMLLoader(MainApp.class.getResource("/schermataPrincipale.fxml"));
        Parent root1 = loaderIniziale.load();

        Scene sceneBegin = new Scene(root1);

        stage.setTitle("Progetto RPG");
        stage.setScene(sceneBegin);
        stage.setMinWidth(960);
        stage.setMinHeight(620);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
