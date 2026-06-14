package com.mycompany.tienda.zapatillas.dao;

import com.mycompany.tienda.zapatillas.conexion.ConexionDB;
import com.mycompany.tienda.zapatillas.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    
    // Método para INSERTAR
    public boolean registrarProducto(Producto prod) {
        String sql = "INSERT INTO productos (marca, modelo, precio, stock) VALUES (?, ?, ?, ?)";
        try {
            java.sql.Connection con = com.mycompany.tienda.zapatillas.conexion.ConexionDB.conectar();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prod.getMarca()); 
            ps.setString(2, prod.getModelo());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    // Método para ACTUALIZAR
   public boolean modificarProducto(Producto prod) {
        // Acá agregamos "marca = ?," para que coincidan los 5 parámetros
        String sql = "UPDATE productos SET marca = ?, modelo = ?, precio = ?, stock = ? WHERE id_producto = ?";
        try {
            java.sql.Connection con = com.mycompany.tienda.zapatillas.conexion.ConexionDB.conectar();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prod.getMarca()); 
            ps.setString(2, prod.getModelo());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getIdProducto());
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al modificar producto: " + e.getMessage());
            return false;
        }
    }
   
   // Método para ELIMINAR
    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try {
            java.sql.Connection con = com.mycompany.tienda.zapatillas.conexion.ConexionDB.conectar();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto); // Le pasamos el ID a borrar
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    // Busca un producto específico por su ID
    public Producto buscarPorId(int id) {
        Producto p = null;
        String sql = "SELECT * FROM productos WHERE id_producto = ?";
        try {
            java.sql.Connection con = com.mycompany.tienda.zapatillas.conexion.ConexionDB.conectar();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return p;
    }
    
    public boolean restarStock(int cantidad, int idProducto) {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";
        try {
            java.sql.Connection con = com.mycompany.tienda.zapatillas.conexion.ConexionDB.conectar();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
    
    public List<Producto> filtrarProductos(String busqueda) {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT * FROM productos WHERE marca LIKE ? OR modelo LIKE ?";
    try (Connection con = ConexionDB.conectar(); 
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, "%" + busqueda + "%"); 
        ps.setString(2, "%" + busqueda + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            // 1. Crear la instancia del objeto
            Producto prod = new Producto();
            
            // 2. Mapear los datos desde el ResultSet al objeto
            prod.setIdProducto(rs.getInt("id_producto"));
            prod.setMarca(rs.getString("marca"));
            prod.setModelo(rs.getString("modelo"));
            prod.setPrecio(rs.getDouble("precio"));
            prod.setStock(rs.getInt("stock"));
            
            // 3. AGREGAR EL OBJETO (la variable 'prod', no la clase 'Producto')
            lista.add(prod); 
        }
    } catch (Exception e) { 
        e.printStackTrace(); 
    }
    return lista;
}
    
}