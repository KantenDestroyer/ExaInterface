package net.kanten.server;

import java.sql.*;
import java.util.Random;

import org.mariadb.jdbc.Configuration;
import org.mariadb.jdbc.Driver;

public class database{
    private final String user ="root";
    private final String passowrd="544807";

    public void print() {
        try{
            //init
            String url = String.format("jdbc:mariadb://localhost:3306/EXA?user=%s&password=%s",this.user,this.passowrd);
            Connection connect = Driver.connect(Configuration.parse(url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT ID,Username,Password,SecretKey FROM pwManager;");
            connect.commit();
            //Processing SQL-Information
            System.out.println("\nID  |   Username    |   Password    |   SecretKey");
            while(result.next()) {
                System.out.printf("%s  |   %s  |   %s  |   %s\n",
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createUser(String ID, String Username,String Password, String SecretKey) throws SQLException {
        try{
            //init
            String url = String.format("jdbc:mariadb://localhost:3306/EXA?user=%s&password=%s",this.user,this.passowrd);
            Connection connect = Driver.connect(Configuration.parse(url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create sql
            String sql = "INSERT INTO pwmanager (ID,Username,Password,SecretKey) Values ('" +ID+"','"+Username+"','"+Password+"','"+SecretKey+"');";
            boolean create = state.execute(sql);
            ResultSet get = state.executeQuery("SELECT ID,Username,Password,SecretKey FROM pwManager;");
            connect.commit();
            //Processing SQL-Information
            System.out.println("User Erstellt");
        } catch (SQLException e) {
            System.out.println("Error is:"+e.getMessage());
        }
    }
    public void deleteUser(String ID, String Username){
        try{
        //init
        String url = String.format("jdbc:mariadb://localhost:3306/EXA?user=%s&password=%s",this.user,this.passowrd);
        Connection connect = Driver.connect(Configuration.parse(url));
        connect.setAutoCommit(false);
        Statement state = connect.createStatement();
        //Create sql
        String sql = "DELETE FROM pwmanager WHERE pwmanager.ID = '"+ID+"' AND pwmanager.Username = '"+Username+"';";
        boolean create = state.execute(sql);
        connect.commit();
        //Processing SQL-Information
        System.out.println("User Deleted");

        } catch (SQLException e) {
            System.out.println("Error is:"+e.getMessage());
        }
    }
}
