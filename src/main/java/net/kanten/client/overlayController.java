package net.kanten.client;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class overlayController {
    public ImageView Logo;
    public Pane root;
    protected String clientID;
    protected String clientKey;

    public void setInformation(String ID, String Key){
        this.clientID = ID;
        this.clientKey = Key;
    }

}
