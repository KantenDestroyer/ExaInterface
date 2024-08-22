package net.kanten.server;

import java.sql.*;
import java.util.Arrays;

import org.mariadb.jdbc.Configuration;
import org.mariadb.jdbc.Driver;

public class database{
    private final String user ="root";
    private final String passowrd="544807";
    String url = String.format("jdbc:mariadb://localhost:3306/exa?user=%s&password=%s",this.user,this.passowrd);

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
            boolean create = state.execute(sql);
            ResultSet get = state.executeQuery("SELECT ID,Username,Password,SecretKey FROM pwmanager;");
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
