package net.kanten.client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class clientController {
    public ImageView Logo;
    public Button loginButton;
    public TextField Username;
    public TextField Password;
    public void Login(ActionEvent e){
        System.out.println("Username: "+Username.getCharacters());
        System.out.println("Password: "+Password.getCharacters());
        System.out.println("Logging in");

    }
}
