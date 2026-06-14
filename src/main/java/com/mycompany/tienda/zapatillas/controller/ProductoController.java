package com.mycompany.tienda.zapatillas.controller;

import com.mycompany.tienda.zapatillas.conexion.ConexionDB;
import com.mycompany.tienda.zapatillas.dao.ProductoDAO;
import com.mycompany.tienda.zapatillas.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProductoController {

    private ProductoDAO productoDAO = new ProductoDAO();

    public void llenarTabla(JTable tabla) {
        // 1. Obtenemos el modelo de la tabla que vos ya diseñaste en la interfaz
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        // 2. Limpiamos la tabla por si ya tenía datos de prueba (los null que te pone NetBeans)
        modelo.setRowCount(0);

        // 3. Le pedimos la lista de zapatillas al DAO
        List<Producto> lista = productoDAO.listarProductos();

        // 4. Recorremos la lista y vamos agregando las filas
        for (Producto prod : lista) {
            Object[] fila = new Object[5];
            fila[0] = prod.getIdProducto();
            fila[1] = prod.getMarca();
            fila[2] = prod.getModelo();
            fila[3] = prod.getPrecio();
            fila[4] = prod.getStock();

            modelo.addRow(fila); // Ahora sí, agregamos la fila al modelo existente
        }
    }

    // Abre el formulario vacío para Crear
    public void abrirFormularioAgregar(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        com.mycompany.tienda.zapatillas.view.abm.FormularioProductoDialog form = new com.mycompany.tienda.zapatillas.view.abm.FormularioProductoDialog(menu, true);
        form.limpiarFormulario();
        form.setIdProductoActual(-1); // -1 significa que es una zapatilla nueva
        form.setVisible(true);
    }

    // Abre el formulario con los datos cargados para Modificar
    public void abrirFormularioModificar(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        int fila = menu.getTablaInventario().getSelectedRow();
        
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(menu, "Seleccioná una zapatilla de la tabla primero.");
            return;
        }

        try {
            // Leemos las 5 columnas de la fila seleccionada
            int id = Integer.parseInt(menu.getTablaInventario().getValueAt(fila, 0).toString());
            String marca = menu.getTablaInventario().getValueAt(fila, 1).toString();
            String modelo = menu.getTablaInventario().getValueAt(fila, 2).toString();
            double precio = Double.parseDouble(menu.getTablaInventario().getValueAt(fila, 3).toString());
            int stock = Integer.parseInt(menu.getTablaInventario().getValueAt(fila, 4).toString());

            com.mycompany.tienda.zapatillas.view.abm.FormularioProductoDialog form = new com.mycompany.tienda.zapatillas.view.abm.FormularioProductoDialog(menu, true);
            
            form.setFormulario(marca, modelo, stock, precio);
            form.setIdProductoActual(id); 
            form.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error al cargar datos al formulario: " + e.getMessage());
        }
    }

    // Guarda los datos cuando apretan "Guardar" en el Dialog
    public void procesarGuardado(com.mycompany.tienda.zapatillas.view.abm.FormularioProductoDialog form, com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        try {
            com.mycompany.tienda.zapatillas.model.Producto p = new com.mycompany.tienda.zapatillas.model.Producto();
            p.setMarca(form.getMarca()); 
            p.setModelo(form.getModelo()); 
            p.setPrecio(Double.parseDouble(form.getPrecio()));
            p.setStock(Integer.parseInt(form.getStock()));
            p.setIdProducto(form.getIdProductoActual());

            if (form.getIdProductoActual() == -1) {
                productoDAO.registrarProducto(p); // Llama al INSERT
            } else {
                productoDAO.modificarProducto(p); // Llama al UPDATE
            }

            form.dispose(); // Cierra el popup
            llenarTabla(menu.getTablaInventario()); // Refresca el inventario de fondo
            
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(form, "Por favor, ingresá números válidos en Precio y Stock.");
        }
    }
    
    // Método que procesa el clic en "Eliminar"
    public void eliminarProducto(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        int fila = menu.getTablaInventario().getSelectedRow();
        
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(menu, "Seleccioná una zapatilla de la tabla primero para eliminarla.");
            return;
        }

        // Sacamos el ID (columna 0) y el Modelo (columna 2) para el cartelito
        int id = Integer.parseInt(menu.getTablaInventario().getValueAt(fila, 0).toString());
        String modelo = menu.getTablaInventario().getValueAt(fila, 2).toString();

        // Ventana de confirmación para no borrar por accidente
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(menu, 
                "¿Estás seguro que querés eliminar las '" + modelo + "' del inventario?", 
                "Confirmar Eliminación", 
                javax.swing.JOptionPane.YES_NO_OPTION, 
                javax.swing.JOptionPane.WARNING_MESSAGE);

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            // Mandamos a borrar a MySQL
            boolean exito = productoDAO.eliminarProducto(id);
            
            if (exito) {
                javax.swing.JOptionPane.showMessageDialog(menu, "Zapatilla eliminada correctamente.");
                llenarTabla(menu.getTablaInventario()); // Refrescamos la tabla al toque
            } else {
                javax.swing.JOptionPane.showMessageDialog(menu, "Hubo un error al intentar eliminar la zapatilla.");
            }
        }
    }
    
   public void filtrarTabla(JTable tabla, String busqueda) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0); // Limpias la tabla
    
    // Llamamos al DAO y recorremos la lista
    for (Producto p : productoDAO.filtrarProductos(busqueda)) {
        // CORRECCIÓN: Asegúrate de llamar a getIdProducto() si así se llama en tu modelo
        modelo.addRow(new Object[]{
            p.getIdProducto(), 
            p.getMarca(), 
            p.getModelo(), 
            p.getPrecio(), 
            p.getStock()
        });
    }
}
}
