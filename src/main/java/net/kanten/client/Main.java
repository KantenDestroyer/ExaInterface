package net.kanten.client;

import net.kanten.utils.clearTerminal;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String serverAdress) {
        try{
            Thread.sleep(1000);
            System.out.print("Start connection\n");
            Socket client= new Socket(serverAdress, 500);
            System.out.println("Connected");
            Thread.sleep(1000);
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
                String serverInput = (String) input.readObject();
                System.out.println(serverInput);
                Thread.sleep(1000);
                client.close();
                output.close();
                input.close();
                if(clientOutput.equals("exit"))System.out.println("Exiting"); break;
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
