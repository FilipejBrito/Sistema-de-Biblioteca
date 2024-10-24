package org.SistemaBiblioteca.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConecta {

    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection conectar(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
