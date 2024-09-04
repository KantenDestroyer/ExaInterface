package net.kanten;

import javafx.application.Application;
import net.kanten.client.clientApplication;
import net.kanten.server.database;
import net.kanten.utils.Cryptographic;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
//import java.util.Random;
import java.util.Scanner;

//This Main Class is only for Test Purpose!!!
public class Main {
    static Cryptographic cry = new Cryptographic();
    //static Random rnd = new Random();
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, SQLException {
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
                    net.kanten.client.Main.main();
                    break;
                case "e":
                    System.out.print("d to Decrypt, e to encrypt:");
                    String chose = scan.nextLine();
                    if(chose.equalsIgnoreCase("d")) {
                        System.out.print("Enter a SecretKey:");
                        String SecretKey = scan.nextLine();
                        cry.setKeyByString(SecretKey);//"q3Gwy8qBtaUvCjWpR/95heO9g6RXsLi0ieVpc9SfOBk=" <- Example SK
                        String keyString = Cryptographic.convertSecretKeyToString(cry.getKey());
                        System.out.println("the key:"+keyString);
                        System.out.println("key length:"+keyString.length());
                        System.out.println("key address:"+cry.getKey());
                        System.out.println("Raw key:"+ Arrays.toString(cry.getRawKey()));
                        System.out.print("\nEnter a cryptedtext to decrypt it:");
                        String cryptedText = scan.nextLine();
                        System.out.println(Cryptographic.decrypt(cryptedText, cry.getKey()));//"o2cMnxr6l1V6X1lGLk4X+g==" <- Example crypted text
                    }else if(chose.equalsIgnoreCase("e")){
                        System.out.print("Enter a SecretKey (no input Generate one):");
                        String SK = scan.nextLine();
                        if(!SK.isEmpty()) {
                            cry.setKeyByString(SK);
                        }else{
                            cry.setKeyBySecretKey(cry.createKey());
                        }
                        String keyString = Cryptographic.convertSecretKeyToString(cry.getKey());
                        System.out.println("the key:"+keyString);
                        System.out.println("key length:"+keyString.length());
                        System.out.println("key address:"+cry.getKey());
                        System.out.println("Raw key:"+ Arrays.toString(cry.getRawKey()));
                        System.out.print("\nEnter a Text to encrypt it:");
                        String Text = scan.nextLine();
                        System.out.println(Cryptographic.encrypt(Text, cry.getKey()));
                    }else{
                        System.out.println("wrong input");
                    }
                    break;
                case "d":
                    database db =new database();
                    db.printUser();
                    /*
                    db.createUser("1","kanten","Iokl544807",Cryptographic.convertSecretKeyToString(cry.createKey()));
                    db.print();
                    db.deleteUser("1");
                    System.out.println(db.getPrint());
                    */
                    break;
                case "--gui":
                case "-g":
                    System.out.println("Start GUI");
                    Application.launch(clientApplication.class, args);
                    break;
                default:
                    System.out.println("\n Its a Master-build argument are:\n");
                    System.out.println(" -s     --server    |" + "to start the Main Server");
                    System.out.println(" -ws    --web-server|" + "to start the Main WebServer");
                    System.out.println(" -c     --client    |" + "to start the Main Client");
                    System.out.println(" -g     --gui       |" + "to start gui");
                    System.out.println(" e                  |" + "to test Cryptographic");
                    System.out.println(" d                  |" + "to test database");
                    Application.launch(clientApplication.class, args);
                    break;
            }
        }else {
            System.out.println("\n Its a Master-build argument are:\n");
            System.out.println(" -s     --server    |" + "to start the Main Server");
            System.out.println(" -ws    --web-server|" + "to start the Main WebServer");
            System.out.println(" -c     --client    |" + "to start the Main Client");
            System.out.println(" -g     --gui       |" + "to start gui");
            System.out.println(" e                  |" + "to test Cryptographic");
            System.out.println(" d                  |" + "to test database");
            Application.launch(clientApplication.class, args);
        }
    }
}