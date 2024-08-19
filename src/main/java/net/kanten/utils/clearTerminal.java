package net.kanten.utils;

import java.util.ArrayList;
import java.util.List;

public class clearTerminal {
    private String getOperatingSystem() {
        String os = System.getProperty("os.name");
        // System.out.println("Using System Property: " + os);
        return os;
    }
    public clearTerminal()  {
        try{
            if(getOperatingSystem().toLowerCase().startsWith("win")){
                ProcessBuilder pb = new ProcessBuilder("cmd","/c","cls");
                Process p = pb.inheritIO().start();
                p.waitFor();
            }else{
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process p = pb.inheritIO().start();
                p.waitFor();
            }
        }catch(Exception e){
            System.err.print("Error: " + e);
        }
    }
}
