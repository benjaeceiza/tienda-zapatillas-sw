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

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        try {
            Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            // SOLO TRAE LOS ACTIVOS
            ResultSet rs = st.executeQuery("SELECT * FROM productos WHERE estado = 1");

            while (rs.next()) {
                Producto prod = new Producto();
                prod.setIdProducto(rs.getInt("id_producto"));
                prod.setMarca(rs.getString("marca"));
                prod.setModelo(rs.getString("modelo"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setStock(rs.getInt("stock"));
                lista.add(prod);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error en el DAO al listar: " + e.getMessage());
        }
        return lista;
    }
    
    public boolean registrarProducto(Producto prod) {
        String sql = "INSERT INTO productos (marca, modelo, precio, stock) VALUES (?, ?, ?, ?)";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prod.getMarca()); 
            ps.setString(2, prod.getModelo());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

   public boolean modificarProducto(Producto prod) {
        String sql = "UPDATE productos SET marca = ?, modelo = ?, precio = ?, stock = ? WHERE id_producto = ?";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prod.getMarca()); 
            ps.setString(2, prod.getModelo());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getIdProducto());
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
   
   // SOFT DELETE: Cambia el estado a 0
    public boolean eliminarProducto(int idProducto) {
        String sql = "UPDATE productos SET estado = 0 WHERE id_producto = ?";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al hacer soft delete del producto: " + e.getMessage());
            return false;
        }
    }
    
    public Producto buscarPorId(int id) {
        Producto p = null;
        // Acá lo buscamos igual aunque esté "borrado", porque el historial lo necesita leer
        String sql = "SELECT * FROM productos WHERE id_producto = ?";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
            }
            con.close();
        } catch (Exception e) {}
        return p;
    }
    
    public boolean restarStock(int cantidad, int idProducto) {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) { return false; }
    }
    
    public List<Producto> filtrarProductos(String busqueda) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE estado = 1 AND (marca LIKE ? OR modelo LIKE ?)";
        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + busqueda + "%"); 
            ps.setString(2, "%" + busqueda + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto prod = new Producto();
                prod.setIdProducto(rs.getInt("id_producto"));
                prod.setMarca(rs.getString("marca"));
                prod.setModelo(rs.getString("modelo"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setStock(rs.getInt("stock"));
                lista.add(prod); 
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}