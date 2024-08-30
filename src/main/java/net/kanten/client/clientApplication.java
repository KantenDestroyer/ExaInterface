package net.kanten.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;


public class clientApplication extends Application {
    public void start(Stage stage) {
        try{
            String Path = "/Client/login.fxml";
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Path)));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("/Client/Style.css")));
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
    }catch(Exception e){
            System.out.println("Error on -> " + e.getMessage());
            throw new RuntimeException(e);
    }finally {
            System.out.println("Ending Program");
        }

    }
}
