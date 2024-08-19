package net.kanten.server;

import net.kanten.utils.clearTerminal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
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
        while(true) {
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
    protected class intergrate implements Runnable{
        private ServerSocket socket;
        public intergrate(ServerSocket socket) throws IOException {
            this.socket = socket;
        }
        public void run(){
            try {
                database db = new database();
                Socket server;
                server = this.socket.accept();
                ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(server.getInputStream());
                String clientInput = (String) input.readObject();
                System.out.print(server + "\n");
                switch (clientInput.toLowerCase()) {
                    case "create":
                        output.writeObject("Which User should i Create?");
                        server = this.socket.accept();
                        System.out.println();
                        output.writeObject("done");
                        break;
                    case "delete":
                        output.writeObject("Which User should i delete");

                        break;
                    case "print":
                        output.writeObject("Printing");

                        break;
                    case "help":
                        System.out.print("Help command\n");
                        output.writeObject("these are the commands\ncreate      |   To create a User\nprint     |   to Print users\nhelp        |   To show command list");
                        break;
                    default:
                        System.out.println("Cant nothing do with that command\n");
                        output.writeObject("unknown Command\ntype \"help\" for more information");
                        break;
                }
            } catch (ClassNotFoundException | IOException e) {
                System.out.println(e);
            }
        }
    }
}
