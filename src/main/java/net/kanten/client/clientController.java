package net.kanten.client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
    public TextField Password;
    public Pane root;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public clientController() throws IOException {
        try (Socket client = new Socket("10.147.20.1", 500)) {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        }catch(Exception e){
            new showError(e);
        }
    }
    public void Login(ActionEvent e) throws IOException, ClassNotFoundException {
        System.out.println("Username: "+Username.getCharacters());
        System.out.println("Password: "+Password.getCharacters());
        System.out.println("Logging in");
        System.out.println("Get information");
        String[] information = new String[]{"getusers"};
        output.writeObject(information);
        System.out.println("Waiting for Information");
        String[] getInformation = (String[]) input.readObject();
        System.out.println(Arrays.toString(getInformation));
        output.close();
        input.close();
    }
}
