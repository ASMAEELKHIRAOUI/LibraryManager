package org.example.classes;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public DBConnection() {
    }

    public static void main(String[] args) {
        createDBConnection();
    }

    public static Connection createDBConnection() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/librarymanager";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception var5) {
            var5.getMessage();
        }

        return con;
    }
}
