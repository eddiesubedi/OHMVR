package com.fluxcapacitor.core.util;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectDB {
    private Connection connection;
    public void connectToDB(String db){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+db,"root","password");
            System.out.println("Connected to "+db);
        } catch (Exception e) {
            System.out.println("Cannot connect to "+db+" database");
        }
    }
    public Connection getConnection(){
        return connection;
    }
}
