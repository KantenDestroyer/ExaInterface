package net.kanten.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class errorController {
    @FXML
    public Label Error;
    public void load(String setError){
        try {
            System.out.println(setError);
            Error.setText("Error is ->"+setError+"\n please contact me on this E-mail: martinoleschuk@gmail.com\n Kontaktiere mich mit auf dieser E-mail: martinoleschuk@gmail.com");
        }catch(Exception e){
            System.out.println("error ist -> "+e.getMessage());
        }
    }
}
