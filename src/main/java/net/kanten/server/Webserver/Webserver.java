package net.kanten.server.Webserver;

import com.sun.net.httpserver.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class Webserver {
    public void runServer() throws IOException {
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", 80);
        HttpServer server = HttpServer.create(address, 0);
        try{
            server.createContext("/", new myHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Webserver started");
        }catch(Exception e){
            System.err.println("Error: " + e);
        }
    }
    public class myHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<head><title>Java Webserver</title></head><body><h1>My Java Page<h1><img src=\"https://c.tenor.com/IYZxiAncud8AAAAC/tenor.gif\"></body>";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream output = exchange.getResponseBody();
            output.write(response.getBytes());
            output.close();
        }
    }
}
