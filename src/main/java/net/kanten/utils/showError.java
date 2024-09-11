package net.kanten.utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.kanten.client.errorController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class showError {
    public showError(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));

        try{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Error.fxml"));
                Parent root = loader.load();
                errorController eC = loader.getController();
                eC.load(sw.toString());
                Scene scene = new Scene(root);
                scene.getStylesheets().add(String.valueOf(getClass().getResource("/Client/Style.css")));
                stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/client/assets/exatrekt-icon.png"))));
                stage.setScene(scene);
                stage.setTitle("Error, Please contact Admin");
                stage.show();
            }catch(IOException ignored){
                System.out.println("Error on GUI");
                System.out.println("If not here error -> " + sw);
            }
        }catch(Exception nothing){
        Platform.startup(new Runnable() {
            @Override
            public void run() {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Error.fxml"));
                    Parent root = loader.load();
                    errorController eC = loader.getController();
                    eC.load(sw.toString());
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(String.valueOf(getClass().getResource("/Client/Style.css")));
                    stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/client/assets/exatrekt-icon.png"))));
                    stage.setScene(scene);
                    stage.setTitle("Error, Please contact Admin");
                    stage.show();
                }catch(IOException ignored){
                    System.out.println("Error on GUI");
                    System.out.println("If not here error -> " + sw);
                }
            }
        });}
    }
}
