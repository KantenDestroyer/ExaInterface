package net.kanten.client;

import javafx.application.Application;
import net.kanten.utils.clearTerminal;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main() throws IOException {
        Scanner caner = new Scanner(System.in);
        new clearTerminal();
        System.out.println("[S]erver or [G]ui?");
        System.out.print(">");
        String chose = caner.nextLine();
        switch(chose.toLowerCase()){
            case "s":
                new clearTerminal();
                new Client();
                Client.run();
                break;
            case "g":
                System.out.println("Start GUI");
                Application.launch(clientApplication.class);
                break;
            default:
                System.out.println("wrong argument");
                break;
        }
    }
    public static void main(String[] args) throws IOException {
        if(args[0].equalsIgnoreCase("client") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("-c"))main();
        Application.launch(clientApplication.class, args);
    }
}
