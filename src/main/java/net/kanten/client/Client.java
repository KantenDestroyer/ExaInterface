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
    protected static String clientAUTH;
    protected static String clientKey;
    private static readInput serverAddress;
    public Client() {
        try {
            System.out.println("Enter Address");
            serverAddress = new readInput(">");

            System.out.println("ServerAddress: " + serverAddress.get());

            System.out.println("Enter Username:");
            readInput enterUsername = new readInput(">");

            System.out.println("Enter Password:");
            readInput enterPassword = new readInput(">");

            //GetID

            String[] getID = new String[]{"getmyid", enterUsername.get()};

            System.out.println("Attempting to connect to server...");
            Socket nameclient = new Socket(serverAddress.get(), 500);
            System.out.println("Connected to server.");
            
            ObjectOutputStream nameoutput = new ObjectOutputStream(nameclient.getOutputStream());
            ObjectInputStream nameinput = new ObjectInputStream(nameclient.getInputStream());

            System.out.println("Sending getID request to server");
            nameoutput.writeObject(getID);
            nameoutput.flush();
            
            System.out.println("Waiting for server response...");
            clientID = (String) nameinput.readObject();
            System.out.println("Received clientID: " + clientID);

            nameclient.close();
            nameinput.close();
            nameoutput.close();


            //getKey

            String[] getKey = new String[]{"getmysk", clientID};

            nameclient = new Socket(serverAddress.get(), 500);
            System.out.println("Connected to server.");

            nameoutput = new ObjectOutputStream(nameclient.getOutputStream());
            nameinput = new ObjectInputStream(nameclient.getInputStream());

            System.out.println("Sending login request to server");
            nameoutput.writeObject(getKey);
            nameoutput.flush();

            System.out.println("Waiting for server response...");
            clientKey = (String) nameinput.readObject();
            System.out.println("Received clientKey: " + clientKey);

            nameclient.close();
            nameinput.close();
            nameoutput.close();

            //AUTHENTICATION

            String[] getAUTH = new String[]{"login", enterUsername.get(), enterPassword.get()};

            nameclient = new Socket(serverAddress.get(), 500);
            System.out.println("Connected to server.");

            nameoutput = new ObjectOutputStream(nameclient.getOutputStream());
            nameinput = new ObjectInputStream(nameclient.getInputStream());

            System.out.println("Sending login request to server");
            nameoutput.writeObject(getAUTH);
            nameoutput.flush();
            
            System.out.println("Waiting for server response...");
            clientAUTH = (String) nameinput.readObject();
            System.out.println("Received clientAUTH: " + clientAUTH);

            nameclient.close();
            nameinput.close();
            nameoutput.close();
            if(Boolean.parseBoolean(clientAUTH)){
                System.out.println("login granted");
            }else{
                System.out.println("bad login");
                System.exit(0);
            }
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            throw new RuntimeException("Failed to connect to server: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
            throw new RuntimeException("Error in data received from server: " + e.getMessage(), e);
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
                switch (clientOutput.toLowerCase()) {
                    case "createuser":
                        System.out.print("\nUsername\n");
                        readInput cUsername = new readInput(">");
                        System.out.print("\nPassword\n");
                        readInput cPassword = new readInput(">");
                        sendingToServer(new String[]{clientOutput, cUsername.get(), cPassword.get()});
                        break;
                    case "giveadmin":
                        System.out.print("\nID\n");
                        readInput newAdmin = new readInput(">");
                        sendingToServer(new String[]{clientOutput, newAdmin.get(),clientID});
                        break;
                    case "deleteuser":
                        System.out.print("\nID\n");
                        readInput dU = new readInput(">");
                        sendingToServer(new String[]{clientOutput, dU.get()});
                        break;
                    case "createpassword":
                        System.out.println("Enter saving Username");
                        readInput Username = new readInput(">");

                        System.out.println("Enter saving Password");
                        readInput sPassword = new readInput(">");

                        sendingToServer(new String[]{clientOutput, Username.get(), sPassword.get(), clientID});
                        break;
                    case "exit":
                        System.out.println("Exiting");
                        client.close();
                        output.close();
                        input.close();
                        System.exit(0);
                        break;
                    case "getpasswords", "revokeadmin":
                        sendingToServer(new String[]{clientOutput,clientID});
                        break;
                    default:
                        sendingToServer(new String[]{clientOutput});
                        break;
                }
                output.close();
                input.close();
            }
        } catch(InterruptedException | IOException e){
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
