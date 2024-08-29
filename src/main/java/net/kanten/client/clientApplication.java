package net.kanten.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class clientApplication extends Application {
    public void start(Stage stage) throws Exception{
        //try{
            String Path = "/Client/login.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Path));
            System.out.println("Load:" + loader.getLocation());
            Pane root = (Pane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Path)));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(clientApplication.class.getResource("style/Style.css")).toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
    /*}catch(Exception e){
            System.out.println("Error on -> " + e.getMessage());
            //throw new Exception();
    }*/}
}
