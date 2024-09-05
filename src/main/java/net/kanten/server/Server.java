package net.kanten.server;

import net.kanten.utils.Cryptographic;
import net.kanten.utils.clearTerminal;
import net.kanten.utils.readInput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

public class Server {
    static database db = new database();
    public void run() {
        new clearTerminal();
        //INIT
        int port = 500;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(socket.getLocalSocketAddress());
        System.out.println("Server started");
        //open here Server Inputs & Outputs
        while (true) {
            //wait for any Client reaction
            try {
                //if server get a client, continue with a new thread
                Thread th = new Thread(new intergrate(socket));
                th.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected static class intergrate implements Runnable {
        private final ServerSocket socket;

        public intergrate(ServerSocket socket) throws IOException {
            this.socket = socket;
        }

        public void run() {
            try {
                Cryptographic cry = new Cryptographic();
                Socket socket;
                socket = this.socket.accept();
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Object clientInput = input.readObject();
                String[] clientInformation = (String[]) clientInput;
                String Message;
                System.out.println("Converted object:" + Arrays.toString(clientInformation));
                System.out.print(socket + "\n");
                //TODO: Update Commands
                switch (clientInformation[0].toLowerCase()) {
                    case "createuser":
                        System.out.println("generated Secret Key:");
                        String SK = Cryptographic.convertSecretKeyToString(cry.createKey());
                        System.out.println("User Information:");
                        System.out.println("Username: " + clientInformation[1]);
                        System.out.println("Password: " + clientInformation[2]);
                        System.out.println("SecretKey: " + SK);
                        System.out.println("Create User");
                        db.createUser(clientInformation[1], clientInformation[2], SK);
                        Message = "done for " + clientInformation[2] + ",SecretKey is: " + SK;
                        System.out.println(Message);
                        output.writeObject(Message);
                        break;
                    case "deleteuser":
                        System.out.println("Delete User");
                        System.out.println("Information:");
                        System.out.println("ID: " + clientInformation[1]);
                        System.out.println("Deleting User");
                        db.deleteUser(clientInformation[1]);
                        Message = "User Deleted";
                        output.writeObject(Message);
                        break;
                    case "print":
                        System.out.println("Print Database");
                        System.out.println("Users Information:");
                        db.printUser();
                        db.printSPassowrds();
                        db.printPAccess();
                        System.out.println("Sending to Client");
                        output.writeObject("look server");
                        break;
                    case "getuser":
                        System.out.println("Users Information:");
                        db.printUser();
                        System.out.println("Sending to Client");
                        output.writeObject(db.getPrintUser());
                        break;
                    case "getpasswords":
                        System.out.println("Users Information:");
                        db.printSPassowrds();
                        System.out.println("Sending to Client");
                        output.writeObject(db.getPintSPassowrds());
                        break;
                    case "getaccess":
                        System.out.println("Users Information:");
                        db.printPAccess();
                        System.out.println("Sending to Client");
                        output.writeObject(db.getPrintPAccess());
                        break;
                    case "help":
                        System.out.print("Help command\n");
                        output.writeObject("these are the commands\ncreate      |   To create a User\ndelete      |      To delete User\nprint         |   to Print users\nhelp        |   To show command list");
                        break;
                    default:
                        System.out.println("Cant nothing do with that command\n");
                        output.writeObject("unknown Command\ntype \"help\" for more information");
                        break;
                }
            } catch (ClassNotFoundException | IOException | NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}