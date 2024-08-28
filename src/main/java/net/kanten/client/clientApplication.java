package net.kanten.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Objects;

public class clientApplication extends Application {
    public void start(Stage stage){
        try{
            FXMLLoader loader = new FXMLLoader();
            String Path = "src/main/resources/Client/login.fxml";
            FileInputStream fxmlStream = new FileInputStream(Path);
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(clientApplication.class.getResource("/styles/Styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
    }catch(Exception e){
            System.out.println("Error on ->" + e.getMessage());
    }}
}
