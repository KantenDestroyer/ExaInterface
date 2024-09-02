package net.kanten.utils;

import java.util.Scanner;

public class readInput {
    private final String input;
    public readInput(String prefix){
        Scanner scan = new Scanner(System.in);
        System.out.print(prefix);
        input = scan.nextLine();
    }
    public readInput(){
        Scanner scan = new Scanner(System.in);
        input = scan.nextLine();
    }
    public String get(){
        if(!input.isEmpty()) return input;
        else return "null";
    }
    public boolean isEmpty(){
        return input.isEmpty();
    }
}
