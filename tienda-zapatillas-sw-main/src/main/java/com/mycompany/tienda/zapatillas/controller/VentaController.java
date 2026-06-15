package com.mycompany.tienda.zapatillas.controller;

import com.mycompany.tienda.zapatillas.dao.ClienteDAO;
import com.mycompany.tienda.zapatillas.dao.ProductoDAO;
import com.mycompany.tienda.zapatillas.dao.VentaDAO;
import com.mycompany.tienda.zapatillas.reportes.ReporteVentasPDF;
import com.mycompany.tienda.zapatillas.model.Cliente;
import com.mycompany.tienda.zapatillas.model.Producto;
import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class VentaController {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private VentaDAO ventaDAO = new VentaDAO();

    // 1. Método para preparar la pantalla cuando entras a la Caja
    public void inicializarCaja(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        // Configuramos la tabla del carrito vacía
        DefaultTableModel modeloCarrito = new DefaultTableModel();
        modeloCarrito.addColumn("ID Prod");
        modeloCarrito.addColumn("Modelo");
        modeloCarrito.addColumn("Cantidad");
        modeloCarrito.addColumn("Precio Unit.");
        modeloCarrito.addColumn("Subtotal");
        menu.getTablaCarrito().setModel(modeloCarrito);

        // Reiniciamos componentes visuales
        menu.getLblTotal().setText("Total: $ 0.00");
        menu.getTxtCantidad().setText("");

        // ¡IMPORTANTE! Volvemos a habilitar el combo de clientes para la nueva venta
        menu.getCmbClientes().setEnabled(true);

        // Cargamos los clientes con un PLACEHOLDER en el índice 0
        menu.getCmbClientes().removeAllItems();
        menu.getCmbClientes().addItem("0 - Seleccioná un cliente..."); // <--- Opción por defecto
        List<Cliente> clientes = clienteDAO.listarClientes();
        for (Cliente c : clientes) {
            menu.getCmbClientes().addItem(c.getIdCliente() + " - " + c.getNombre() + " " + c.getApellido());
        }

        // Cargamos los productos con un PLACEHOLDER en el índice 0
        menu.getCmbProductos().removeAllItems();
        menu.getCmbProductos().addItem("0 - Seleccioná un producto..."); // <--- Opción por defecto
        List<Producto> productos = productoDAO.listarProductos();
        for (Producto p : productos) {
            if (p.getStock() > 0) {
                menu.getCmbProductos().addItem(p.getIdProducto() + " - " + p.getMarca() + " " + p.getModelo() + " (Stock: " + p.getStock() + ")");
            }
        }
    }

    // 2. Método para sumar zapatillas a la tabla del carrito
    public void agregarAlCarrito(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        try {
            // REGLA 1: Validar que no hayan dejado los placeholders por defecto
            if (menu.getCmbClientes().getSelectedIndex() == 0) {
                javax.swing.JOptionPane.showMessageDialog(menu, "Por favor, seleccioná un cliente válido para el recibo.");
                return;
            }
            if (menu.getCmbProductos().getSelectedIndex() == 0) {
                javax.swing.JOptionPane.showMessageDialog(menu, "Por favor, seleccioná un producto válido para agregar.");
                return;
            }

            // Validamos cantidad
            String cantTxt = menu.getTxtCantidad().getText();
            if (cantTxt.isEmpty() || Integer.parseInt(cantTxt) <= 0) {
                javax.swing.JOptionPane.showMessageDialog(menu, "Ingresá una cantidad válida mayor a 0.");
                return;
            }
            int cantidadPedida = Integer.parseInt(cantTxt);

            // Extraemos el ID del producto
            String prodSeleccionado = menu.getCmbProductos().getSelectedItem().toString();
            int idProducto = Integer.parseInt(prodSeleccionado.split(" - ")[0]);

            // Buscamos el producto real en la BD
            Producto p = productoDAO.buscarPorId(idProducto);
            if (p == null) {
                javax.swing.JOptionPane.showMessageDialog(menu, "Error: Producto no encontrado.");
                return;
            }

            // Verificamos stock
            if (cantidadPedida > p.getStock()) {
                javax.swing.JOptionPane.showMessageDialog(menu, "No hay stock suficiente. Solo quedan " + p.getStock() + " pares.");
                return;
            }

            // REGLA 3: Bloquear el combo de clientes para que no lo cambien a mitad de la venta
            menu.getCmbClientes().setEnabled(false);

            // Calculamos el subtotal de esta zapatilla
            double subtotal = cantidadPedida * p.getPrecio();

            // Rescatamos el modelo de la tabla visual directamente
            DefaultTableModel modelo = (DefaultTableModel) menu.getTablaCarrito().getModel();

            // Agregamos la fila al carrito
            Object[] fila = new Object[5];
            fila[0] = p.getIdProducto();
            fila[1] = p.getMarca() + " " + p.getModelo();
            fila[2] = cantidadPedida;
            fila[3] = p.getPrecio();
            fila[4] = subtotal;
            modelo.addRow(fila);

            // Recalculamos el total sumando toda la columna "Subtotal" (índice 4)
            double totalVenta = 0.0;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                totalVenta += Double.parseDouble(modelo.getValueAt(i, 4).toString());
            }

            // Actualizamos el cartel del Total
            menu.getLblTotal().setText("Total: $ " + String.format("%.2f", totalVenta).replace(",", "."));

            // REGLA 2: Limpiamos el producto y la cantidad, pero dejamos intacto el cliente
            menu.getCmbProductos().setSelectedIndex(0);
            menu.getTxtCantidad().setText("");

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(menu, "Error al agregar al carrito. Revisá los datos.");
        }
    }

    // 3. Método maestro: GUARDA LA VENTA, LOS DETALLES Y RESTA STOCK
    public void confirmarVenta(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        DefaultTableModel modelo = (DefaultTableModel) menu.getTablaCarrito().getModel();

        if (modelo.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(menu, "El carrito está vacío. Agregá productos primero.");
            return;
        }

        // Sacamos el ID del cliente del ComboBox que quedó bloqueado
        String clienteSeleccionado = menu.getCmbClientes().getSelectedItem().toString();
        int idCliente = Integer.parseInt(clienteSeleccionado.split(" - ")[0]);

        // Calculamos el total leyendo la etiqueta visual
        String textoTotal = menu.getLblTotal().getText().replace("Total: $ ", "");
        double total = Double.parseDouble(textoTotal);

        // 1. Armamos la Venta Principal
        com.mycompany.tienda.zapatillas.model.Venta v = new com.mycompany.tienda.zapatillas.model.Venta();
        v.setIdCliente(idCliente);
        v.setIdUsuario(1); // ACÁ PONEMOS 1 (Admin) POR AHORA
        v.setTotal(total);

        // La guardamos y nos devuelve el número de ticket
        int idVentaGenerada = ventaDAO.registrarVenta(v);

        if (idVentaGenerada != -1) {
            // 2. Guardamos los detalles y restamos el stock fila por fila
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idProducto = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                double precioUnitario = Double.parseDouble(modelo.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(modelo.getValueAt(i, 4).toString());

                // Guardamos el detalle en MySQL
                com.mycompany.tienda.zapatillas.model.DetalleVenta dv = new com.mycompany.tienda.zapatillas.model.DetalleVenta();
                dv.setIdVenta(idVentaGenerada);
                dv.setIdProducto(idProducto);
                dv.setCantidad(cantidad);
                dv.setPrecioUnitario(precioUnitario);
                dv.setSubtotal(subtotal);
                ventaDAO.registrarDetalle(dv);

                // Descontamos el stock
                productoDAO.restarStock(cantidad, idProducto);
            }

            javax.swing.JOptionPane.showMessageDialog(menu, "¡Venta registrada con éxito! Número de recibo: " + idVentaGenerada);
            // Limpiamos todo para el próximo cliente
            inicializarCaja(menu);
        } else {
            javax.swing.JOptionPane.showMessageDialog(menu, "Hubo un error al intentar guardar la venta.");
        }
    }

    // 4. Método para llenar el Historial
    public void llenarTablaHistorial(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Ticket ID");
        modelo.addColumn("Fecha");
        modelo.addColumn("Cliente");
        modelo.addColumn("Vendedor");
        modelo.addColumn("Total");

        // Trae los datos haciendo el JOIN en la base de datos
        List<Object[]> lista = ventaDAO.listarVentas();
        for (Object[] fila : lista) {
            modelo.addRow(fila);
        }

        menu.getTablaHistorial().setModel(modelo);
    }

    // 5. Método para generar reporte PDF desde la vista (botón)
    public void generarReporteVentasDesdeVista(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        // 1) Obtener datos desde el DAO
        List<Object[]> filas = ventaDAO.listarVentas();

        if (filas == null || filas.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(menu, "No hay ventas para generar el reporte.");
            return;
        }

        // 2) Pedir ruta al usuario
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte de ventas");
        chooser.setSelectedFile(new File("reporte_ventas.pdf"));
        int userSelection = chooser.showSaveDialog(menu);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // usuario canceló
        }
        String ruta = chooser.getSelectedFile().getAbsolutePath();
        if (!ruta.toLowerCase().endsWith(".pdf")) {
            ruta += ".pdf";
        }

        // 3) Generar PDF
        try {
            ReporteVentasPDF reporte = new ReporteVentasPDF();
            reporte.generarReporteDesdeFilas(filas, ruta);

            // 4) Intentar abrir el PDF automáticamente
            try {
                File file = new File(ruta);
                if (file.exists() && Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            } catch (Exception openEx) {
                System.out.println("No se pudo abrir el PDF automáticamente: " + openEx.getMessage());
            }

            javax.swing.JOptionPane.showMessageDialog(menu, "Reporte generado en: " + ruta);
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(menu, "Error generando el PDF: " + e.getMessage());
        }
    }
}
