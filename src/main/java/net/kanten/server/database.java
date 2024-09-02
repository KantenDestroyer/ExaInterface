package net.kanten.server;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

import net.kanten.utils.clearTerminal;
import net.kanten.utils.readInput;
import org.mariadb.jdbc.Configuration;
import org.mariadb.jdbc.Driver;

public class database{
    private HashMap<String,String> info = new HashMap<>();
    private String address ="localhost";
    private String database="exa";
    private String user ="root";
    private String password ="544807";
    private String UserTable="Users";
    private String sPasswordTable="sPassword";
    private String pAccessTable ="pAcess";
    private String url;

    public database(){
        new clearTerminal();
        System.out.println("Default-Test Information:");
        System.out.printf("""
                address: %s
                database: %s
                user: %s
                password: %s
                UserTable: %s
                sPasswordTable: %s
                pAccessTable: %s
                """,
                this.address,
                this.database,
                this.user,
                this.password,
                this.UserTable,
                this.sPasswordTable,
                this.pAccessTable
        );
        System.out.println("Wanna Change Information");
        readInput in = new readInput("[Y]ey or [N]o ->");
        String codition = in.get();
        if(codition.equalsIgnoreCase("y") || codition.equalsIgnoreCase("yes")){
            new clearTerminal();
            System.out.println("Press Enter to set Default");

            System.out.println("Enter Address");
            readInput ad = new readInput(">");
            if(!ad.isEmpty()) info.put("address",ad.get());
            else info.put("address", this.address);

            System.out.println("Enter database");
            readInput da = new readInput(">");
            if(!da.isEmpty()) info.put("database",da.get());
            else info.put("database", this.database);

            System.out.println("Enter user");
            readInput us = new readInput(">");
            if(!us.isEmpty()) info.put("user", us.get());
            else info.put("user", this.user);

            System.out.println("Enter password");
            readInput pa = new readInput(">");
            if(!pa.isEmpty())info.put("password", pa.get());
            else info.put("password",this.password);

            System.out.println("Enter UserTable");
            readInput ut = new readInput(">");
            if(!ut.isEmpty())info.put("UserTable",ut.get());
            else info.put("UserTable", this.UserTable);

            System.out.println("Enter sPasswordTable");
            readInput sp = new readInput(">");
            if(!sp.isEmpty())info.put("sPasswordTable",sp.get());
            else info.put("sPasswordTable",this.sPasswordTable);

            System.out.println("Enter pAccessTable");
            readInput at = new readInput(">");
            if(!at.isEmpty())info.put("pAccessTable", at.get());
            else info.put("pAccessTable", this.pAccessTable);

        }else{
            new clearTerminal();
            System.out.println("Set Default Information");
            info.put("address", this.address);
            info.put("database", this.database);
            info.put("user", this.user);
            info.put("password",this.password);
            info.put("UserTable", this.UserTable);
            info.put("sPasswordTable",this.sPasswordTable);
            info.put("pAccessTable", this.pAccessTable);
        }
        System.out.println("Current Information");
        for(String i: info.keySet()){
            System.out.println(i +": "+info.get(i));
        }
        url = String.format("jdbc:mariadb://%s:3306/%s?user=%s&password=%s",info.get("address"),info.get("database"),info.get("user"),info.get("password"));
    }
    public void print() {
        try{
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT ID,Username,Password,SecretKey FROM pwmanager;");
            connect.commit();
            //rocessing SQL-Information
            String header = "\nID  |   Username    |   Password    |   SecretKey";
            String[] body = new String[255];
            int amount = 0;
            System.out.println(header);
            while(result.next()) {
                body[amount] = String.format("%s  |   %s  |   %s  |   %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));
                System.out.printf(body[amount]);
                amount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getPrint() {
        try {
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT ID,Username,Password,SecretKey FROM pwmanager;");
            ResultSet result1 = state.executeQuery("SELECT count(ID) FROM pwmanager;");
            connect.commit();
            //rocessing SQL-Information
            int count = 0;
            if (result1.next()) {
                count = result1.getInt(1);
            }
            String header = "\n ID  |   Username    |   Password    |   SecretKey\n";
            //int count = result1.getInt(1);
            String[] body = new String[count];
            int amount = 0;
            while (result.next()) {
                body[amount] = String.format("%s  |   %s  |   %s  |   %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));
                amount++;
            }
            return header + Arrays.deepToString(body).replaceAll(",","").replace("[", " ").replace("]", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createUser(String ID, String Username,String Password, String SecretKey) throws SQLException {
        try{
            //init
            Connection connect = Driver.connect(Configuration.parse(this.url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create sql
            String sql = "INSERT INTO pwmanager (ID,Username,Password,SecretKey) Values ('" +ID+"','"+Username+"','"+Password+"','"+SecretKey+"');";
            state.execute(sql);
            connect.commit();
            //Processing SQL-Information
            System.out.println("User Erstellt");
        } catch (SQLException e) {
            System.out.println("Error is:"+e.getMessage());
        }
    }
    public void deleteUser(String ID){
        try{
        //init
        Connection connect = Driver.connect(Configuration.parse(this.url));
        connect.setAutoCommit(false);
        Statement state = connect.createStatement();
        //Create sql
        String sql = "DELETE FROM pwmanager WHERE pwmanager.ID = '"+ID+"';";
        boolean create = state.execute(sql);
        connect.commit();
        //Processing SQL-Information
        System.out.println("User Deleted");

        } catch (SQLException e) {
            System.out.println("Error is:"+e.getMessage());
        }
    }
}
