package net.kanten.client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import net.kanten.utils.showError;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class clientController {
    public ImageView Logo;
    public Button loginButton;
    public TextField Username;
    public PasswordField Password;
    public Pane root;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    public void Login(ActionEvent e) throws IOException, ClassNotFoundException {
        try (Socket client = new Socket("localhost", 500)) {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        }catch(Exception err){
            new showError(err);
        }
        System.out.println("Username: "+Username.getCharacters());
        System.out.println("Password: "+Password.getCharacters());
        System.out.println("Logging in");
        System.out.println("Get information");
        output.writeObject(new String[]{"getusers"});
        System.out.println("Waiting for Information");
        String[] getInformation = (String[]) input.readObject();
        System.out.println(Arrays.toString(getInformation));
    }
}
