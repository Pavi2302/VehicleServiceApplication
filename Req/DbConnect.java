package Req;

import java.sql.*;
public class DbConnect {
    public static Connection getconnection() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vmanagement?autoReconnect=true&useSSL=false", "root", "nathan");
            return con;
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return null;
    }
}
