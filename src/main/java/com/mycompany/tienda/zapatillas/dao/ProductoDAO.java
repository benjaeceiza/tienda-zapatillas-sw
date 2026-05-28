package com.mycompany.tienda.zapatillas.dao;

import com.mycompany.tienda.zapatillas.conexion.ConexionDB;
import com.mycompany.tienda.zapatillas.model.Producto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Trae una lista con los productos desde la DB
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        
        try {
            Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM productos");

            while (rs.next()) {
                Producto prod = new Producto();
                prod.setIdProducto(rs.getInt("id_producto"));
                prod.setMarca(rs.getString("marca"));
                prod.setModelo(rs.getString("modelo"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setStock(rs.getInt("stock"));
                // prod.setRutaImagen(rs.getString("ruta_imagen")); // Lo dejamos comentado por ahora si no lo usás en la tabla
                
                lista.add(prod); // Guardamos la zapatilla en la lista
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error en el DAO al listar: " + e.getMessage());
        }
        
        return lista;
    }
}