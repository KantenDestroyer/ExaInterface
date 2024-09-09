package net.kanten.client;

import net.kanten.utils.clearTerminal;
import net.kanten.utils.readInput;
import net.kanten.utils.showError;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private static final Scanner scan = new Scanner(System.in);
    protected static String clientInput;
    protected static String clientID;
    private static readInput serverAddress;
    public Client(){
        try{
            System.out.println("Enter Address");
            serverAddress = new readInput(">");
            System.out.println("ServerAdress");
            Socket nameclient = new Socket(serverAddress.get(), 500);
            System.out.println("Enter Username:");
            readInput enterUsername = new readInput(">");
            String[] getID = new String[]{"getmyid", enterUsername.get()};
            ObjectOutputStream nameoutput = new ObjectOutputStream(nameclient.getOutputStream());
            ObjectInputStream nameinput = new ObjectInputStream(nameclient.getInputStream());
            System.out.println("Sending to server");
            nameoutput.writeObject(getID);
            clientID = (String) nameinput.readObject();
            nameclient.close();
            nameoutput.close();
            nameinput.close();
        }catch(RuntimeException | IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    public static void run() throws IOException {
        try{
            Thread.sleep(1000);
            System.out.print("Start connection\n");
            Socket client= new Socket(serverAddress.get(), 500);
            System.out.println("Connected");
            Thread.sleep(1000);
            while (true) {
                new clearTerminal();
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
                new clearTerminal();
                System.out.println("Enter Command");
                System.out.print(">");
                String clientOutput = scan.nextLine();
                switch (clientOutput) {
                    case "createuser", "createUser":
                        System.out.print("\nUsername\n");
                        System.out.print(">");
                        readInput cUsername = new readInput(">");
                        System.out.print("\nPassword\n");
                        System.out.print(">");
                        readInput cPassword = new readInput(">");
                        sendingToServer(new String[]{clientOutput, cUsername.get(), cPassword.get()});
                        break;
                    case "giveadmin", "giveAdmin", "deleteuser", "deleteUser":
                        System.out.print("\nID\n");
                        readInput newAdmin = new readInput(">");
                        sendingToServer(new String[]{clientOutput, newAdmin.get()});
                        break;
                    case "createpassword":
                        System.out.println("Enter saving Username");
                        readInput Username = new readInput(">");

                        System.out.println("Enter saving Password");
                        readInput sPassword = new readInput(">");
                        /*
                        System.out.println("Enter Information");
                        readInput Information = new readInput(">");
                        */
                        sendingToServer(new String[]{clientOutput, Username.get(), sPassword.get(), clientID});
                        break;
                    case "exit":
                        System.out.println("Exiting");
                        System.exit(0);
                        break;
                    default:
                        sendingToServer(new String[]{clientOutput});
                        break;
                }
                client.close();
                output.close();
                input.close();
            }
        }catch(InterruptedException | IOException e){
            run();
            new showError(e);
        }
    }
    private static void sendingToServer(String[] Information){
        try {
            output.writeObject(Information);
            System.out.print("\nSending to Server\n");
            clientInput = (String) input.readObject();
            System.out.println(clientInput);
            System.out.print("\n Press enter to continue.....");
            scan.nextLine();
            output.close();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
