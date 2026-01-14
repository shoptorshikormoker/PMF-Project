package com.seu.pfmfx.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
        private static final String DB_HOST = "localhost";
        private static final String DB_PORT = "3306";
        private static final String DB_NAME = "pfmx_db";
        private static final String DB_USER = "root";
        private static final String DB_PASS = "1234";
        private static final String DB_URL ="jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

        private static Connection connection;
        private static ConnectionProvider singleton = new ConnectionProvider();

        private ConnectionProvider(){
            try{
                connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            } catch (SQLException ex) {
                IO.println("Failed to connect");
                ex.printStackTrace();

            }


        }
        public static ConnectionProvider getSingleton() {

            return singleton;
        }
        public Connection getConnection() {
            return connection;
        }




    }


