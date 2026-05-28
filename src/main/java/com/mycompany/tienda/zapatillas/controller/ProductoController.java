package com.mycompany.tienda.zapatillas.controller;

import com.mycompany.tienda.zapatillas.dao.ProductoDAO;
import com.mycompany.tienda.zapatillas.model.Producto;
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
}