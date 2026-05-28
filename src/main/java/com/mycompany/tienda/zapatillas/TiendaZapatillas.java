package com.mycompany.tienda.zapatillas;

// Importamos la clase de la conexion
import com.mycompany.tienda.zapatillas.conexion.ConexionDB;

public class TiendaZapatillas {

    public static void main(String[] args) {
        System.out.println("Iniciando sistema... Probando conexión:");
        
        // Llamamos al metodo conectar
        ConexionDB.conectar();
    }
}