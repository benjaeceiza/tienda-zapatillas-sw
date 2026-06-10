package com.mycompany.tienda.zapatillas.conexion;

import java.sql.*; // Importación obligatoria para usar JDBC 

public class ConexionDB {
    
    // Credenciales de MySQL 
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_zapatillas"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "contraseña"; 

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD); 
            System.out.println("¡Conexión exitosa a la base de datos de zapatillas!");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return con;
    }
}
