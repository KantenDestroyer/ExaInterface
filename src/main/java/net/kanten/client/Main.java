package net.kanten.client;

import net.kanten.utils.clearTerminal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner caner = new Scanner(System.in);
        new clearTerminal();
        System.out.println("[S]erver or [G]ui?");
        System.out.print(">");
        String chose = caner.nextLine();
        switch(chose.toLowerCase()){
            case "s":
                new clearTerminal();
                System.out.print("Enter Address: ");
                String serverAddress = caner.nextLine();
                Master.run(serverAddress);
                break;
            case "g":
                System.out.println("Start GUI");
                break;
            default:
                System.out.println("wrong argument");
                break;
        }
    }
}
