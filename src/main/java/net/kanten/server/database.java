package net.kanten.server;

import java.sql.*;
import org.mariadb.jdbc.Configuration;
import org.mariadb.jdbc.Driver;

public class database{

    public void print() {
        try{
            //init
            String user ="root";
            String passowrd="544807";
            String url = String.format("jdbc:mariadb://localhost:3306/EXA?user=%s&password=%s",user,passowrd);
            Connection connect = Driver.connect(Configuration.parse(url));
            connect.setAutoCommit(false);
            Statement state = connect.createStatement();
            //Create GET
            ResultSet result = state.executeQuery("SELECT ID,Username,Password,SecretKey FROM pwManager;");
            connect.commit();
            //Processing SQL-Information
            while(result.next()) {
                System.out.println("ID  |   Username    |   Password    |   SecretKey");
                System.out.printf("%s  |   %s  |   %s  |   %s",
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

    }
}
