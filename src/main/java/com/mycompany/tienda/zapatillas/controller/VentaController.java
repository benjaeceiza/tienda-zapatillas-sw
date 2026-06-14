package com.mycompany.tienda.zapatillas.controller;

import com.mycompany.tienda.zapatillas.dao.*;
import com.mycompany.tienda.zapatillas.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.util.List;


public class VentaController {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private VentaDAO ventaDAO = new VentaDAO();

    // 1. Método para preparar la pantalla (Universal)
    public void inicializarCaja(JTable tablaCarrito, JLabel lblTotal, JTextField txtCantidad, JComboBox cmbClientes, JComboBox cmbProductos) {
        DefaultTableModel modeloCarrito = new DefaultTableModel();
        modeloCarrito.addColumn("ID Prod");
        modeloCarrito.addColumn("Modelo");
        modeloCarrito.addColumn("Cantidad");
        modeloCarrito.addColumn("Precio Unit.");
        modeloCarrito.addColumn("Subtotal");
        tablaCarrito.setModel(modeloCarrito);

        lblTotal.setText("Total: $ 0.00");
        txtCantidad.setText("");
        cmbClientes.setEnabled(true);

        cmbClientes.removeAllItems();
        cmbClientes.addItem("0 - Seleccioná un cliente...");
        for (Cliente c : clienteDAO.listarClientes()) {
            cmbClientes.addItem(c.getIdCliente() + " - " + c.getNombre() + " " + c.getApellido());
        }

        cmbProductos.removeAllItems();
        cmbProductos.addItem("0 - Seleccioná un producto...");
        for (Producto p : productoDAO.listarProductos()) {
            if (p.getStock() > 0) {
                cmbProductos.addItem(p.getIdProducto() + " - " + p.getMarca() + " " + p.getModelo() + " (Stock: " + p.getStock() + ")");
            }
        }
    }

    // 2. Método para sumar zapatillas al carrito
    public void agregarAlCarrito(Component parent, JTable tablaCarrito, JComboBox cmbClientes, JComboBox cmbProductos, JTextField txtCantidad, JLabel lblTotal) {
        try {
            if (cmbClientes.getSelectedIndex() == 0 || cmbProductos.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(parent, "Por favor, seleccioná un cliente y producto válidos.");
                return;
            }

            String cantTxt = txtCantidad.getText();
            if (cantTxt.isEmpty() || Integer.parseInt(cantTxt) <= 0) {
                JOptionPane.showMessageDialog(parent, "Ingresá una cantidad válida mayor a 0.");
                return;
            }
            int cantidadPedida = Integer.parseInt(cantTxt);

            int idProducto = Integer.parseInt(cmbProductos.getSelectedItem().toString().split(" - ")[0]);
            Producto p = productoDAO.buscarPorId(idProducto);

            if (p == null || cantidadPedida > p.getStock()) {
                JOptionPane.showMessageDialog(parent, "No hay stock suficiente. Quedan " + (p != null ? p.getStock() : 0) + " pares.");
                return;
            }

            cmbClientes.setEnabled(false);
            double subtotal = cantidadPedida * p.getPrecio();
            DefaultTableModel modelo = (DefaultTableModel) tablaCarrito.getModel();
            modelo.addRow(new Object[]{p.getIdProducto(), p.getMarca() + " " + p.getModelo(), cantidadPedida, p.getPrecio(), subtotal});

            recalcularTotal(tablaCarrito, lblTotal);
            cmbProductos.setSelectedIndex(0);
            txtCantidad.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error al agregar al carrito.");
        }
    }

    // 3. Método para confirmar venta
    public void confirmarVenta(Component parent, JTable tablaCarrito, JComboBox cmbClientes, JLabel lblTotal) {
        DefaultTableModel modelo = (DefaultTableModel) tablaCarrito.getModel();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parent, "El carrito está vacío.");
            return;
        }

        int idCliente = Integer.parseInt(cmbClientes.getSelectedItem().toString().split(" - ")[0]);
        double total = Double.parseDouble(lblTotal.getText().replace("Total: $ ", "").replace(",", "."));

        Venta v = new Venta();
        v.setIdCliente(idCliente);
        v.setIdUsuario(1); // Mantenemos ID 1 por ahora
        v.setTotal(total);

        int idVentaGenerada = ventaDAO.registrarVenta(v);
        if (idVentaGenerada != -1) {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                DetalleVenta dv = new DetalleVenta();
                dv.setIdVenta(idVentaGenerada);
                dv.setIdProducto(Integer.parseInt(modelo.getValueAt(i, 0).toString()));
                dv.setCantidad(Integer.parseInt(modelo.getValueAt(i, 2).toString()));
                dv.setPrecioUnitario(Double.parseDouble(modelo.getValueAt(i, 3).toString()));
                dv.setSubtotal(Double.parseDouble(modelo.getValueAt(i, 4).toString()));
                ventaDAO.registrarDetalle(dv);
                productoDAO.restarStock(dv.getCantidad(), dv.getIdProducto());
            }
            JOptionPane.showMessageDialog(parent, "¡Venta registrada! Ticket: " + idVentaGenerada);
            // Re-inicializamos pasando componentes
            inicializarCaja(tablaCarrito, lblTotal, new JTextField(), cmbClientes, new JComboBox<>()); 
        }
    }

    // 4. Método para llenar el Historial
    public void llenarTablaHistorial(JTable tablaHistorial) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Ticket ID", "Fecha", "Cliente", "Vendedor", "Total"}, 0);
        for (Object[] fila : ventaDAO.listarVentas()) {
            modelo.addRow(fila);
        }
        tablaHistorial.setModel(modelo);
    }

    // Método auxiliar para lógica interna
    private void recalcularTotal(JTable tabla, JLabel lblTotal) {
        double total = 0.0;
        for (int i = 0; i < tabla.getRowCount(); i++) {
            total += Double.parseDouble(tabla.getValueAt(i, 4).toString());
        }
        lblTotal.setText("Total: $ " + String.format("%.2f", total).replace(",", "."));
    }
}