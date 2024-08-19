package net.kanten.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONReader {
    public void JSONParser(File jsonFile) throws FileNotFoundException {
        if (jsonFile.canRead()){
            try {
                FileReader fileReader = new FileReader(jsonFile.getName());
            }catch(Exception e){
                System.err.println("Error: "+e);
            }finally {
                System.out.println("!!Done!!");
            }
        }
    }
}
