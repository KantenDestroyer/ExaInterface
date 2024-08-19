package net.kanten.server;

import net.kanten.server.Webserver.Webserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Main extends Thread{
    public static void main(String chose) throws IOException {
        chose = chose.toLowerCase();
        switch(chose){
            case"webserver":
                Webserver web = new Webserver();
                web.runServer();
                break;
            case"server":
                ServerSocket socker = new ServerSocket(501);
                Server server = new Server();
                new Server().run();
            break;
            default:
                System.out.println("server or webserver");
            break;
        }
    }
    public static void main(String[] args) throws IOException {
        if(args.length != 0)main(args[0]);
        else System.out.println("webserver or server");
    }
}