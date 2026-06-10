package com.mycompany.tienda.zapatillas.controller;

import com.mycompany.tienda.zapatillas.dao.ClienteDAO;
import com.mycompany.tienda.zapatillas.model.Cliente;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClienteController {
    
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void llenarTabla(JTable tabla) {
        // Creamos el modelo desde cero para forzar las columnas correctas
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Correo Electrónico");

        List<Cliente> lista = clienteDAO.listarClientes();

        for (Cliente c : lista) {
            Object[] fila = new Object[4];
            fila[0] = c.getIdCliente();
            fila[1] = c.getNombre();
            fila[2] = c.getApellido();
            fila[3] = c.getEmail();
            modelo.addRow(fila);
        }
        
        tabla.setModel(modelo);
    }
    
    // Abre el formulario vacío
    public void abrirFormularioAgregar(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog form = new com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog(menu, true);
        form.limpiarFormulario();
        form.setIdClienteActual(-1); 
        form.setVisible(true);
    }

    // Lee la tabla y abre el formulario lleno
    public void abrirFormularioModificar(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        // Asegurate de tener este getter creado en tu MenuAdminView
        int fila = menu.getClienteTableModel().getRowCount() > 0 ? menu.getTablaClientes().getSelectedRow() : -1; 
        
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(menu, "Seleccioná un cliente de la tabla primero.");
            return;
        }

        int id = Integer.parseInt(menu.getTablaClientes().getValueAt(fila, 0).toString());
        String nombre = menu.getTablaClientes().getValueAt(fila, 1).toString();
        String apellido = menu.getTablaClientes().getValueAt(fila, 2).toString();
        String correo = menu.getTablaClientes().getValueAt(fila, 3).toString();

        com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog form = new com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog(menu, true);
        form.setFormulario(nombre, apellido, correo);
        form.setIdClienteActual(id); 
        form.setVisible(true);
    }

    // Guarda los datos (Insert o Update)
    public void procesarGuardado(com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog form, com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        Cliente c = new Cliente();
        c.setNombre(form.getNombre());
        c.setApellido(form.getApellido());
        c.setEmail(form.getCorreo());
        c.setIdCliente(form.getIdClienteActual());

        if (form.getIdClienteActual() == -1) {
            clienteDAO.registrarCliente(c);
        } else {
            clienteDAO.modificarCliente(c);
        }

        form.dispose(); 
        llenarTabla(menu.getTablaClientes()); // Refresca la tabla
    }

    // Procesa la eliminación
    public void eliminarCliente(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        int fila = menu.getTablaClientes().getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(menu, "Seleccioná un cliente de la tabla primero.");
            return;
        }

        int id = Integer.parseInt(menu.getTablaClientes().getValueAt(fila, 0).toString());
        String nombre = menu.getTablaClientes().getValueAt(fila, 1).toString();

        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(menu, 
                "¿Estás seguro que querés eliminar al cliente " + nombre + "?", 
                "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            clienteDAO.eliminarCliente(id);
            llenarTabla(menu.getTablaClientes());
        }
    }
}