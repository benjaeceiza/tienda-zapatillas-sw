package com.mycompany.tienda.zapatillas.dao;

import com.mycompany.tienda.zapatillas.conexion.ConexionDB;
import com.mycompany.tienda.zapatillas.model.Venta;
import com.mycompany.tienda.zapatillas.model.DetalleVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // =========================================================================
    // 1. MÉTODO PARA EL HISTORIAL (Trae los datos cruzados con JOIN para la tabla)
    // =========================================================================
    public List<Object[]> listarVentas() {
        List<Object[]> lista = new ArrayList<>();
        // Usamos CONCAT para juntar Nombre y Apellido del cliente en una sola columna
        String sql = "SELECT v.id_venta, v.fecha, CONCAT(c.nombre, ' ', c.apellido) AS cliente, u.nombre AS vendedor, v.total " +
                     "FROM ventas v " +
                     "JOIN clientes c ON v.id_cliente = c.id_cliente " +
                     "JOIN usuarios u ON v.id_usuario = u.id_usuario " +
                     "ORDER BY v.fecha DESC";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id_venta");
                fila[1] = rs.getString("fecha");
                fila[2] = rs.getString("cliente");
                fila[3] = rs.getString("vendedor");
                fila[4] = rs.getDouble("total");
                lista.add(fila);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al listar ventas en el DAO: " + e.getMessage());
            e.printStackTrace(); // Esto te canta la posta en la consola si algo falla
        }
        return lista;
    }

    // =========================================================================
    // 2. REGRASTRAR VENTA (Guarda la cabecera del ticket y devuelve el ID generado)
    // =========================================================================
    public int registrarVenta(Venta v) {
        int idVentaGenerada = -1;
        String sql = "INSERT INTO ventas (id_cliente, id_usuario, total) VALUES (?, ?, ?)";
        try {
            Connection con = ConexionDB.conectar();
            // Le pedimos a la BD que nos devuelva las claves generadas automáticamente (id_venta)
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, v.getIdCliente());
            ps.setInt(2, v.getIdUsuario()); 
            ps.setDouble(3, v.getTotal());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idVentaGenerada = rs.getInt(1); // Rescatamos el número de recibo recién creado
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al registrar cabecera de venta: " + e.getMessage());
            e.printStackTrace();
        }
        return idVentaGenerada;
    }

    // =========================================================================
    // 3. REGISTRAR DETALLE (Guarda cada renglón de las zapatillas que se compraron)
    // =========================================================================
    public boolean registrarDetalle(DetalleVenta dv) {
        String sql = "INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, dv.getIdVenta());
            ps.setInt(2, dv.getIdProducto());
            ps.setInt(3, dv.getCantidad());
            ps.setDouble(4, dv.getPrecioUnitario());
            ps.setDouble(5, dv.getSubtotal());
            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar detalle de venta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}