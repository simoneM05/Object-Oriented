package com.example.ConnessioneDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {
    private static ConnessioneDatabase instance;
    public Connection connection = null;

    private String nome ="booh"; //Da definire
    private String password = "booh"; // Da definire
    private String url = "jdbc:mysql://localhost:3306/booh"; // stessa cosa
    private String driver = "org.postgresql.Driver";

    // Constructor
    private ConnessioneDatabase() throws SQLException {
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome , password);
        }catch(ClassNotFoundException ex){
            System.out.println("Database connection failed" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDatabase();

        }
        else if(instance.connection.isClosed()){
            instance = new ConnessioneDatabase();
        }
        return instance;
    }
}
