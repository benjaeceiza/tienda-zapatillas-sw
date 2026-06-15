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
    public void abrirFormularioAgregar(javax.swing.JFrame padre, javax.swing.JTable tabla) {
    com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog form = 
        new com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog(padre, true);
    form.limpiarFormulario();
    form.setIdClienteActual(-1); 
    form.setVisible(true);
    llenarTabla(tabla); // Esto refresca la tabla al cerrar
}

    // Lee la tabla y abre el formulario lleno
    public void abrirFormularioModificar(javax.swing.JFrame padre, javax.swing.JTable tabla) {
    
    // Obtenemos la fila de la tabla que nos pasaron
    int fila = tabla.getSelectedRow();
    
    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(padre, "Seleccioná un cliente de la tabla primero.");
        return;
    }

    int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
    String nombre = tabla.getValueAt(fila, 1).toString();
    String apellido = tabla.getValueAt(fila, 2).toString();
    String correo = tabla.getValueAt(fila, 3).toString();

    com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog form = 
        new com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog(padre, true);
    form.setFormulario(nombre, apellido, correo);
    form.setIdClienteActual(id); 
    form.setVisible(true);
}

    // Guarda los datos (Insert o Update)
   public void procesarGuardado(com.mycompany.tienda.zapatillas.view.abm.FormularioClienteDialog form) {
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

    form.dispose(); // Solo cierra el formulario
}

    // Procesa la eliminación
    public void eliminarCliente(javax.swing.JFrame padre, javax.swing.JTable tabla) {
    // Usamos 'tabla' en lugar de 'menu.getTablaClientes()'
    int fila = tabla.getSelectedRow();
    
    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(padre, "Seleccioná un cliente de la tabla primero.");
        return;
    }

    // Obtenemos los datos desde la tabla que recibimos
    int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
    String nombre = tabla.getValueAt(fila, 1).toString();

    int confirmacion = javax.swing.JOptionPane.showConfirmDialog(padre, 
            "¿Estás seguro que querés eliminar al cliente " + nombre + "?", 
            "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
        clienteDAO.eliminarCliente(id);
        // Refrescamos la tabla que recibimos
        llenarTabla(tabla);
    }
}
    
public void filtrarTabla(JTable tabla, String busqueda) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);
    
    for (Cliente c : clienteDAO.filtrarClientes(busqueda)) {
        modelo.addRow(new Object[]{
            c.getIdCliente(), 
            c.getNombre(), 
            c.getApellido(), 
            c.getEmail()
        });
    }
}
    
}