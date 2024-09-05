package net.kanten.server;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

import net.kanten.utils.clearTerminal;
import net.kanten.utils.readInput;
import org.mariadb.jdbc.Configuration;
import org.mariadb.jdbc.Driver;

import javax.xml.transform.Result;

public class database{
    private final HashMap<String,String> info = new HashMap<>();
    private final String url;

    public database(){
        new clearTerminal();
        System.out.println("Default-Test Information:");
        String address = "localhost";
        String database = "exa";
        String user = "root";
        String password = "544807";
        String userTable = "Users";
        String sPasswordTable = "sPassword";
        String pAccessTable = "pAccess";
        System.out.printf("""
                address: %s
                database: %s
                user: %s
                password: %s
                userTable: %s
                sPasswordTable: %s
                pAccessTable: %s
                """,
                address,
                database,
                user,
                password,
                userTable,
                sPasswordTable,
                pAccessTable
        );
        System.out.println("Wanna Change Information");
        readInput in = new readInput("[Y]ey or [N]o ->");
        String codition = in.get();
        if(codition.equalsIgnoreCase("y") || codition.equalsIgnoreCase("yes")){
            new clearTerminal();
            System.out.println("Press Enter to set Default");
            //Address from database server
            System.out.println("Enter Address");
            readInput ad = new readInput(">");
            if(ad.isEmpty()) info.put("address",ad.get());
            else info.put("address", address);
            //Selected Database
            System.out.println("Enter database");
            readInput da = new readInput(">");
            if(da.isEmpty()) info.put("database",da.get());
            else info.put("database", database);
            //Username to Login
            System.out.println("Enter user");
            readInput us = new readInput(">");
            if(us.isEmpty()) info.put("user", us.get());
            else info.put("user", user);
            //Password to login
            System.out.println("Enter password");
            readInput pa = new readInput(">");
            if(pa.isEmpty())info.put("password", pa.get());
            else info.put("password", password);
            //user Table for logging in
            System.out.println("Enter UserTable");
            readInput ut = new readInput(">");
            if(ut.isEmpty())info.put("userTable",ut.get());
            else info.put("userTable", userTable);
            //Table where the Password are saved
            System.out.println("Enter sPasswordTable");
            readInput sp = new readInput(">");
            if(sp.isEmpty())info.put("sPasswordTable",sp.get());
            else info.put("sPasswordTable", sPasswordTable);
            //
            System.out.println("Enter pAccessTable");
            readInput at = new readInput(">");
            if(at.isEmpty())info.put("pAccessTable", at.get());
            else info.put("pAccessTable", pAccessTable);

        }else{
            new clearTerminal();
            System.out.println("Set Default Information");
            info.put("address", address);
            info.put("database", database);
            info.put("user", user);
            info.put("password", password);
            info.put("userTable", userTable);
            info.put("sPasswordTable", sPasswordTable);
            info.put("pAccessTable", pAccessTable);
        }
        System.out.println("Current Information");
        for(String i: info.keySet()){
            System.out.println(i +": "+info.get(i));
        }
        url = String.format("jdbc:mariadb://%s:3306/%s?user=%s&password=%s",info.get("address"),info.get("database"),info.get("user"),info.get("password"));
    }

    //Printer
    public void printUser() {
        try{
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT ID,Username,Password,SecretKey,Role FROM " + info.get("userTable")+";");
            connect.commit();
            //rocessing SQL-Information
            String header = "ID  |   Username    |   Password    |   SecretKey    |   Role";
            String[] body = new String[255];
            int amount = 0;
            System.out.println("\n"+info.get("userTable"));
            System.out.println(header);
            while(result.next()) {
                body[amount] = String.format("%s  |   %s  |   %s  |   %s    | %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5));
                System.out.printf(body[amount]);
                amount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void printSPassowrds(){
        try{
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT pID,sUsername,sPassword,information,owner FROM " + info.get("sPasswordTable")+";");
            connect.commit();
            //rocessing SQL-Information
            String header = "pID  |     sUsername   |   sPassword   |   information    |   Owner";
            String[] body = new String[255];
            int amount = 0;
            System.out.println("\n"+info.get("sPasswordTable"));
            System.out.println(header);
            while(result.next()) {
                body[amount] = String.format("%s  |   %s  |   %s  |   %s  |   %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5));
                System.out.printf(body[amount]);
                amount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void printPAccess(){
        try{
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT ID,pID FROM " + info.get("pAccessTable")+";");
            connect.commit();
            //rocessing SQL-Information
            String header = "ID  |   pID";
            String[] body = new String[255];
            int amount = 0;
            System.out.println("\n"+info.get("pAccessTable"));
            System.out.println(header);
            while(result.next()) {
                body[amount] = String.format("%s  |   %s\n",
                        result.getString(1),
                        result.getString(2));
                System.out.printf(body[amount]);
                amount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //gett
    public String getPrintUser() {
        try {
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT ID,Username,Password,SecretKey,Role FROM " + info.get("userTable")+";");
            ResultSet result1 = state.executeQuery("SELECT count(id) FROM " + info.get("userTable")+";");
            connect.commit();
            //rocessing SQL-Information
            int count = 0;
            if (result1.next()) {
                count = result1.getInt(1);
            }
            String header = "\n ID  |   Username    |   Password    |   SecretKey   |   Role\n";
            //int count = result1.getInt(1);
            String[] body = new String[count];
            int amount = 0;
            while (result.next()) {
                body[amount] = String.format("%s  |   %s  |   %s  |   %s    | %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5));
                amount++;
            }
            for(String i: body){
                System.out.println(i);
            }
            return header + Arrays.deepToString(body).replaceAll(",","").replace("[", " ").replace("]", "");
        } catch (SQLException e) {
            return "error";
        }
    }
    public String getPrintPAccess(){
        try {
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT ID, pID FROM " + info.get("pAccessTable")+";");
            ResultSet result1 = state.executeQuery("SELECT count(ID) FROM " + info.get("pAccessTable")+";");
            connect.commit();
            //rocessing SQL-Information
            int count = 0;
            if (result1.next()) {
                count = result1.getInt(1);
            }
            String header = "\nID  |   pID \n";
            //int count = result1.getInt(1);
            String[] body = new String[count];
            int amount = 0;
            while (result.next()) {
                body[amount] = String.format("%s  |   %s \n",
                        result.getString(1),
                        result.getString(2));
                amount++;
            }
            return header + Arrays.deepToString(body).replaceAll(",","").replace("[", " ").replace("]", "");
        } catch (SQLException e) {
            return "error";
        }
    }

    public String getPintSPassowrds(){
        try {
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT pID,sUsername,sPassword,information,owner FROM " + info.get("sPasswordTable")+";");
            ResultSet result1 = state.executeQuery("SELECT count(pID) FROM " + info.get("sPasswordTable")+";");
            connect.commit();
            //rocessing SQL-Information
            int count = 0;
            if (result1.next()) {
                count = result1.getInt(1);
            }
            String header = "\n pID  |   SPassword    |   Owner\n";
            //int count = result1.getInt(1);
            String[] body = new String[count];
            int amount = 0;
            while (result.next()) {
                body[amount] = String.format("%s  |   %s  |   %s  |   %s  |   %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5));
                amount++;
            }
            return header + Arrays.deepToString(body).replaceAll(",","").replace("[", " ").replace("]", "");
        } catch (SQLException e) {
            return "error";
        }
    }

    //creating
    public void createUser(String ID, String Username,String Password, String SecretKey) throws SQLException {
        try{
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create sql
            String sql = "INSERT INTO "+info.get("userTable")+" (ID,Username,Password,SecretKey,Role) Values ('" +ID+"','"+Username+"','"+Password+"','"+SecretKey+"','user');";
            state.execute(sql);
            connect.commit();
            //Processing SQL-Information
            System.out.println("User Erstellt");
        } catch (SQLException e) {
            System.out.println("Error is: "+e.getMessage());
        }
    }
    public void createPassword(String Username, String PW, int owner, String information) {
        try {
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            String sqlAmount = "SELECT count(pID) FROM "+ info.get("sPasswordTable")+";";
            ResultSet e = state.executeQuery(sqlAmount);
            int amount = 0;
            if(e.next()) {
                amount = e.getInt(1);
            }
            System.out.println(amount);
            String sql = "INSERT INTO "+ info.get("sPasswordTable")+" (pID, sUsername, sPassword, information, owner) values ('"+amount+"','"+Username+"','"+PW+"','"+information+"','"+owner+"');";
            state.execute(sql);
            connect.commit();
            System.out.println("Password saved");
        }catch(SQLException e){
            System.out.println("Error is: "+e.getMessage());
        }
    }
    public void createPassword(String Username, String PW, int owner) {
        try {
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            String sqlAmount = "SELECT count(pID) FROM "+ info.get("sPasswordTable")+";";
            ResultSet e = state.executeQuery(sqlAmount);
            int amount = 0;
            if(e.next()) {
                amount = e.getInt(1);
            }
            System.out.println(amount);
            String sql = "INSERT INTO "+ info.get("sPasswordTable")+" (pID, sUsername, sPassword, owner) values ('"+amount+"','"+Username+"','"+PW+"','"+owner+"');";
            state.execute(sql);
            connect.commit();
            System.out.println("Password saved");
        }catch(SQLException e){
            System.out.println("Error is: "+e.getMessage());
        }
    }
    public void giveAccess(String from, String to){
        try{
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create sql
            String sql = "INSERT INTO "+info.get("pAccessTable")+" (ID, pID) Values ('"+from+"','"+to+"');";
            state.execute(sql);
            connect.commit();
            //Processing SQL-Information
            System.out.println("Zugang geben");
        } catch (SQLException e) {
            System.out.println("Error is: "+e.getMessage());
        }
    }


    //Deleting
    public void deleteUser(String ID){
        try{
        //init
        Connection connect = Driver.connect(Configuration.parse(this.url));
        connect.setAutoCommit(false);
        Statement state = connect.createStatement();
        //Create sql
        String sql = "DELETE FROM "+info.get("userTable")+" WHERE "+info.get("userTable")+".ID = '"+ID+"';";
        state.execute(sql);
        connect.commit();
        //Processing SQL-Information
        System.out.println("User Deleted");

        } catch (SQLException e) {
            System.out.println("Error is: "+e.getMessage());
        }
    }
}
