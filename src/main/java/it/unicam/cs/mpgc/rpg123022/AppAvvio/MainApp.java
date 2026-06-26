package it.unicam.cs.mpgc.rpg123022.AppAvvio;

import it.unicam.cs.mpgc.rpg123022.Database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseManager.initializeDatabase();
        Font.loadFont(MainApp.class.getResourceAsStream("/fonts/MedievalSharp-Bold.ttf"), 24);

        Parent rootMain = FXMLLoader.load(getClass().getResource("/schermataIniziale.fxml"));
        primaryStage.setScene(new Scene(rootMain));
        primaryStage.setTitle("Progetto RPG");
        primaryStage.setMinWidth(960);
        primaryStage.setMinHeight(620);
        primaryStage.show();

        Stage adminStage = new Stage();
        Parent rootAdmin = FXMLLoader.load(getClass().getResource("/Admin/admin.fxml"));
        adminStage.setScene(new Scene(rootAdmin));
        adminStage.setTitle("Admin");
        adminStage.setMinWidth(960);
        adminStage.setMinHeight(620);
        adminStage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}
