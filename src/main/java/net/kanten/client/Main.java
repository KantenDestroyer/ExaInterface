package net.kanten.client;

import net.kanten.utils.clearTerminal;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static boolean exit = false;
    public static void main(String serverAdress) {
        try{
            Thread.sleep(1000);
            System.out.print("Start connection\n");
            Socket client= new Socket(serverAdress, 500);
            System.out.println("Connected");
            Thread.sleep(1000);
            while(true){
                if(exit)break;
                while(true){
                new clearTerminal();
                System.out.print("Read Outputs\n");
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                System.out.print("Read inputs\n");
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                System.out.print(">");
                Scanner scan = new Scanner(System.in);
                String clientOutput = scan.nextLine();
                System.out.print("\nSending to Server\n");
                output.writeObject(clientOutput);
                switch(clientOutput){
                    case "create":
                        String clientInput = (String) input.readObject();
                        System.out.println(clientInput);
                        String[] userInformation;
                        System.out.print("\nID\n");
                        System.out.print(">");
                        String ID = scan.nextLine();
                        System.out.print("\nUsername\n");
                        System.out.print(">");
                        String Username = scan.nextLine();
                        System.out.print("\nPassword\n");
                        System.out.print(">");
                        String Password = scan.nextLine();
                        userInformation = new String[]{ID, Username, Password};
                        System.out.print("\nSending to Server\n");
                        output.writeObject(userInformation);
                        break;
                    case "delete":

                        break;
                    case "print":

                        break;
                    case "exit":
                        System.out.println("Exiting");
                        exit = true;
                        break;
                }
                Thread.sleep(1000);
                client.close();
                output.close();
                input.close();
            }
            }
        }catch(InterruptedException | ClassNotFoundException | IOException e){
            if(e.getMessage().toLowerCase().startsWith("connection refused")) {
                System.out.println("Connection failed");
            }
            main(serverAdress);
        }
    }
    public static void main(String[] args) {
        new clearTerminal();
        System.out.print("Enter Adresse: ");
        Scanner scaner = new Scanner(System.in);
        String serverAdress = scaner.nextLine();
        main(serverAdress);
    }
}
