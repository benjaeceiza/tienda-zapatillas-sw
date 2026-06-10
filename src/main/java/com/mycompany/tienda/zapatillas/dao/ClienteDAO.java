package com.mycompany.tienda.zapatillas.dao;

import com.mycompany.tienda.zapatillas.conexion.ConexionDB;
import com.mycompany.tienda.zapatillas.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrarCliente(Cliente c) {
        String sql = "INSERT INTO clientes (nombre, apellido, email) VALUES (?, ?, ?)";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getEmail());
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }
    
    // Método para MODIFICAR
    public boolean modificarCliente(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, email = ? WHERE id_cliente = ?";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getEmail());
            ps.setInt(4, c.getIdCliente());
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al modificar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para ELIMINAR
    public boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}