package net.kanten;

import net.kanten.server.database;
import net.kanten.utils.Cryptographic;
import net.kanten.utils.clearTerminal;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

//This Main Class is only for Test Purpose!!!
public class Main {
    static Cryptographic cry = new Cryptographic();
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, SQLException, ClassNotFoundException {
        if(args.length != 0){
            String argument = args[0].toLowerCase();
            switch(argument) {
                case "-s":
                case "--server":
                    net.kanten.server.Main.main("server");
                    break;
                case "-ws":
                case "--web-server":
                    net.kanten.server.Main.main("webserver");
                    break;
                case "-c":
                case "--client":
                    new clearTerminal();
                    Scanner scaner = new Scanner(System.in);
                    System.out.print("Enter Adresse: ");
                    String serverAdress = scaner.nextLine();
                    net.kanten.client.Main.main(serverAdress);
                    break;
                case "e":
                    cry.setKeyByString("q3Gwy8qBtaUvCjWpR/95heO9g6RXsLi0ieVpc9SfOBk=");
                    String keyString = Cryptographic.convertSecretKeyToString(cry.getKey());
                    System.out.println(keyString);
                    System.out.println(keyString.length());
                    System.out.println(cry.getKey());
                    System.out.println(Cryptographic.decrypt("o2cMnxr6l1V6X1lGLk4X+g==", cry.getKey()));
                    break;
                case "d":
                    database db =new database();
                    //db.createUser("1","kanten","Iokl544807",Cryptographic.convertSecretKeyToString(cry.createKey()));
                    db.print();
                    break;
                default:
                    System.out.println("\n Its a Master-build argument are:\n");
                    System.out.println(" -s     --server    |" + "to start the Main Server");
                    System.out.println(" -ws    --web-server|" + "to start the Main WebServer");
                    System.out.println(" -c     --client    |" + "to start the Main Client");
                    break;
            }
        }else {
            System.out.println("\n Its a Master-build argument are:\n");
            System.out.println(" -s     --server    |" + "to start the Main Server");
            System.out.println(" -ws    --web-server|" + "to start the Main WebServer");
            System.out.println(" -c     --client    |" + "to start the Main Client");
        }
    }
}