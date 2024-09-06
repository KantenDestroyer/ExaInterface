package net.kanten.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.kanten.utils.showError;

import java.io.IOException;
import java.util.Objects;


public class clientApplication extends Application {
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Client/login.fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("/Client/Style.css")));
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Client/assets/exatrekt-icon.png"))));
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
    }catch(Exception e){
            new showError(e);
    }
    }
}
