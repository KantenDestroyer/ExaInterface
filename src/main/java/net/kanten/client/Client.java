package net.kanten.client;

import net.kanten.utils.clearTerminal;
import net.kanten.utils.showError;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void run(String serverAddress) throws IOException {
        try{
            Thread.sleep(1000);
            System.out.print("Start connection\n");
            Socket client= new Socket(serverAddress, 500);
            System.out.println("Connected");
            Thread.sleep(1000);
            while (true) {
                new clearTerminal();
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                System.out.println("Enter Command");
                System.out.print(">");

                Scanner scan = new Scanner(System.in);
                String clientOutput = scan.nextLine();
                String[] userInformation;
                String clientInput;
                switch (clientOutput) {
                    case "createuser", "createUser":
                        System.out.print("\nUsername\n");
                        System.out.print(">");
                        String cUsername = scan.nextLine();
                        System.out.print("\nPassword\n");
                        System.out.print(">");
                        String cPassword = scan.nextLine();
                        System.out.print("\nSending to Server\n");
                        userInformation = new String[]{clientOutput, cUsername, cPassword};
                        output.writeObject(userInformation);
                        clientInput = (String) input.readObject();
                        System.out.println(clientInput);
                        System.out.print("\n Press enter to continue.....");
                        scan.nextLine();
                        break;
                    case "giveadmin", "giveAdmin":

                        break;
                    case "deleteuser", "deleteUser":
                        System.out.print("\nID\n");
                        System.out.print(">");
                        String dID = scan.nextLine();
                        System.out.print("\nSending to Server\n");
                        userInformation = new String[]{clientOutput, dID};
                        output.writeObject(userInformation);
                        clientInput = (String) input.readObject();
                        System.out.println(clientInput);
                        System.out.print("\n Press enter to continue.....");
                        scan.nextLine();
                        break;
                    case "exit":
                        System.out.println("Exiting");
                        System.exit(0);
                        break;
                    default:
                        System.out.print("\nSending to Server\n");
                        userInformation = new String[]{clientOutput};
                        output.writeObject(userInformation);
                        clientInput = (String) input.readObject();
                        System.out.println(clientInput);
                        System.out.print("\n Press enter to continue.....");
                        scan.nextLine();
                        break;
                }
                client.close();
                output.close();
                input.close();
            }
        }catch(InterruptedException | ClassNotFoundException | IOException e){
            run(serverAddress);
            new showError(e);
        }
    }
}
