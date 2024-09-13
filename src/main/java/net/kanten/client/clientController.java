package net.kanten.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.kanten.utils.showError;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class clientController {
    public ImageView Logo;
    public Button loginButton;
    public TextField Username;
    public PasswordField Password;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    protected String clientID;
    protected String clientKey;
    protected String clientAUTH;
    @FXML
    public Parent root;
    public Stage stage;
    public Scene scene;
    public void Login(ActionEvent e) throws IOException {
        Socket client = null;
        try {

            //GetID

            String[] getID = new String[]{"getmyid", String.valueOf(Username.getCharacters())};

            System.out.println("Attempting to connect to server...");
            client = new Socket("localhost", 500);
            System.out.println("Connected to server.");

            ObjectOutputStream nameoutput = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream nameinput = new ObjectInputStream(client.getInputStream());

            System.out.println("Sending getID request to server");
            nameoutput.writeObject(getID);
            nameoutput.flush();

            System.out.println("Waiting for server response...");
            clientID = (String) nameinput.readObject();
            System.out.println("Received clientID: " + clientID);

            client.close();
            nameinput.close();
            nameoutput.close();


            //getKey

            String[] getKey = new String[]{"getmysk", clientID};

            client = new Socket("localhost", 500);
            System.out.println("Connected to server.");

            nameoutput = new ObjectOutputStream(client.getOutputStream());
            nameinput = new ObjectInputStream(client.getInputStream());

            System.out.println("Sending login request to server");
            nameoutput.writeObject(getKey);
            nameoutput.flush();

            System.out.println("Waiting for server response...");
            clientKey = (String) nameinput.readObject();
            System.out.println("Received clientKey: " + clientKey);

            client.close();
            nameinput.close();
            nameoutput.close();

            //AUTHENTICATION

            String[] getAUTH = new String[]{"login", String.valueOf(Username.getCharacters()), String.valueOf(Password.getCharacters())};

            client = new Socket("localhost", 500);
            System.out.println("Connected to server.");

            nameoutput = new ObjectOutputStream(client.getOutputStream());
            nameinput = new ObjectInputStream(client.getInputStream());

            System.out.println("Sending login request to server");
            nameoutput.writeObject(getAUTH);
            nameoutput.flush();

            System.out.println("Waiting for server response...");
            clientAUTH = (String) nameinput.readObject();
            System.out.println("Received clientAUTH: " + clientAUTH);

            client.close();
            nameinput.close();
            nameoutput.close();

            System.out.println("Username: " + Username.getCharacters());
            System.out.println("Password: " + Password.getCharacters());

            if(Boolean.parseBoolean(clientAUTH)){
                System.out.println("SK: "+clientKey);
                System.out.println("ID: "+clientID);
                System.out.println("Logged in");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/userOverlay.fxml"));
            root = loader.load();

            overlayController oC = loader.getController();
            oC.setInformation(clientID,clientKey);

            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            }else{
                System.out.println("bad Login");
            }

        } catch (Exception err) {
            new showError(err);
        } finally {
            if (client != null && !client.isClosed()) {
                client.close();
            }
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }
}
