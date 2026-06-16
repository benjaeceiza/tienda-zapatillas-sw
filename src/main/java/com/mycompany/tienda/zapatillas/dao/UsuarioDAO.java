package com.mycompany.tienda.zapatillas.dao;

import com.mycompany.tienda.zapatillas.conexion.ConexionDB;
import com.mycompany.tienda.zapatillas.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario iniciarSesion(String email, String password) {
        Usuario usr = null;
        // Solo deja iniciar sesión si el usuario está activo (estado = 1)
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ? AND estado = 1";

        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usr = new Usuario();
                usr.setIdUsuario(rs.getInt("id_usuario"));
                usr.setNombre(rs.getString("nombre"));
                usr.setEmail(rs.getString("email"));
                usr.setPassword(rs.getString("password"));
                usr.setRol(rs.getString("rol"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }
        return usr; 
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE estado = 1";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrarUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, email, password, rol) VALUES (?, ?, ?, ?)";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRol());
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean modificarUsuario(Usuario u) {
        boolean cambiaPass = !u.getPassword().isEmpty();
        String sql = cambiaPass
                ? "UPDATE usuarios SET nombre=?, email=?, password=?, rol=? WHERE id_usuario=?"
                : "UPDATE usuarios SET nombre=?, email=?, rol=? WHERE id_usuario=?";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            if (cambiaPass) {
                ps.setString(3, u.getPassword());
                ps.setString(4, u.getRol());
                ps.setInt(5, u.getIdUsuario());
            } else {
                ps.setString(3, u.getRol());
                ps.setInt(4, u.getIdUsuario());
            }
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) { return false; }
    }

    // SOFT DELETE: Cambia el estado a 0
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "UPDATE usuarios SET estado = 0 WHERE id_usuario = ?";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al hacer soft delete del usuario: " + e.getMessage());
            return false;
        }
    }
    
    public List<Usuario> filtrarUsuarios(String busqueda) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE estado = 1 AND (nombre LIKE ? OR email LIKE ?)";
        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + busqueda + "%"); 
            ps.setString(2, "%" + busqueda + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usr = new Usuario();
                usr.setIdUsuario(rs.getInt("id_usuario"));
                usr.setNombre(rs.getString("nombre"));
                usr.setEmail(rs.getString("email"));
                usr.setPassword(rs.getString("password"));
                usr.setRol(rs.getString("rol"));
                lista.add(usr); 
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}