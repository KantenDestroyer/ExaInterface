package net.kanten.client;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class overlayController {
    protected Socket client;
    protected ObjectOutputStream output;
    protected ObjectInputStream input;
    public ImageView Logo;
    public Pane root;
    public TableView table;
    public TableColumn name;
    public TableColumn url;
    public TableColumn Username;
    public TableColumn Password;
    public TableColumn Information;
    protected String clientID;
    protected String clientKey;

    public void setInformation(String ID, String Key){
        String[] getKey = new String[]{"getmysk", clientID};
        try {
            client = new Socket("localhost", 500);
            System.out.println("Connected to server.");
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        this.clientID = ID;
        this.clientKey = Key;
        table.fixedCellSizeProperty();
        System.out.println("Name    |   URL     |   Username    |   Password    |   Information");

        //table.setRowFactory();
    }
}
