package net.kanten.server;

import net.kanten.utils.Cryptographic;
import net.kanten.utils.clearTerminal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Server {
    protected static database db = new database();
    protected static ObjectOutputStream output;
    protected static ObjectInputStream input;
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
        System.out.println(socket.getInetAddress());
        System.out.println("Server started");
        while (true) {
            //wait for any Client reaction
            try {
                Socket accept = socket.accept();
                if(accept.isConnected()) {
                    System.out.println("Start thread");
                    Thread th = new Thread(new intergrate(accept));
                    th.start();
                } else {
                    accept.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    protected static class intergrate implements Runnable {
        private final Socket accept;

        public intergrate(Socket accept) throws IOException {
            this.accept = accept;
        }

        public void run() {
            try {
                System.out.println("readInputs");
                Cryptographic cry = new Cryptographic();
                output = new ObjectOutputStream(accept.getOutputStream());
                input = new ObjectInputStream(accept.getInputStream());
                Object clientInput = input.readObject();
                String[] clientInformation = (String[]) clientInput;
                String Message;
                System.out.println("Converted object:" + Arrays.toString(clientInformation));
                System.out.print(accept + "\n");
                System.out.println(Arrays.toString(clientInformation));
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
                        Message = "done for " + clientInformation[1] + ",SecretKey is: " + SK;
                        System.out.println(Message);
                        output.writeObject(Message);
                        break;
                    case "createpassword":
                        System.out.println("creating Password");
                            //1:Username ,2:saving Password, 3:Owner, 3:SecretKey, 4:Information
                            db.createPassword(clientInformation[1],clientInformation[2],clientInformation[3],db.getSKFromID(clientInformation[3]));
                            System.out.println(clientInformation.length);
                            output.writeObject("Password created");
                        break;
                    case "giveaccess":
                        System.out.println("Giving Access");
                        db.giveAccess(db.getIDByUsername(clientInformation[1]),clientInformation[2]);
                        System.out.println("gave access");
                        output.writeObject("gave access");
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
                    case "getusers":
                        System.out.println("Users Information:");
                        db.printUser();
                        System.out.println("Sending to Client");
                        output.writeObject(db.getPrintUser());
                        break;
                    case "getpasswords":
                        System.out.println("Password Information:");
                        db.printSPassowrds();
                        System.out.println("Sending to Client");
                        output.writeObject(db.getPintSPassowrds(db.getSKFromID(clientInformation[1])));
                        break;
                    case "getaccess":
                        System.out.println("Access Information:");
                        db.printPAccess();
                        System.out.println("Sending to Client");
                        output.writeObject(db.getPrintPAccess());
                        break;
                    case "getmyid":
                        output.writeObject(db.getIDByUsername(clientInformation[1]));
                        break;
                    case "giveadmin":
                        System.out.println("Setting user to admin");
                        db.setUserToAdmin(clientInformation[1], clientInformation[2]);
                        System.out.println("Granted user "+clientInformation[1]+" Admin permission");
                        output.writeObject("Granted user "+clientInformation[1]+" Admin permission");
                        break;
                    case "revokeadmin":
                        System.out.println("Setting admin to user");
                        db.setUserToAdmin(clientInformation[1],clientInformation[2]);
                        System.out.println("revoked user "+clientInformation[1]+" Admin permission");
                        output.writeObject("revoked user "+clientInformation[1]+" Admin permission");
                        break;
                    case "help":
                        System.out.print("Help command\n");
                        output.writeObject("these are the commands\ncreateuser      |   To create a User\ndeleteuser      |      To delete User\nprint         |   to Print users on server\nhelp        |   To show command list\ngetusers     |       to get user list\ngetpasswords      |       get Passwordlist\ngetaccess     |       show Access list\ngiveadmin     |       grand User Admin permission\nrevokeadmin    |       Remove admin permission from User");
                        break;
                    default:
                        System.out.println("Cant nothing do with that command\n");
                        output.writeObject("unknown Command\ntype \"help\" for more information");
                        break;
                }
                input.close();
                output.close();
                accept.close();
            } catch (ClassNotFoundException | IOException | NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}