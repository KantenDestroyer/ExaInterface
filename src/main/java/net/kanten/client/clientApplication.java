package net.kanten.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.kanten.utils.showError;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;


public class clientApplication extends Application {
    protected FXMLLoader loader;
    @FXML
    protected Parent root;
    protected Scene scene;
    public void start(Stage stage) throws IOException {
        try{
            loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Client/login.fxml")));
            root = loader.load();
            scene = new Scene(root);
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
