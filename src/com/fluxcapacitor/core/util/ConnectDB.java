package com.fluxcapacitor.core.util;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectDB {
    private Connection connection;
    public void connectToDB(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/login","root","password");
            System.out.println("Connected to database");
        } catch (Exception e) {
            System.out.println("Cannot connect to database");
        }
    }
    public Connection getConnection(){
        return connection;
    }
}
