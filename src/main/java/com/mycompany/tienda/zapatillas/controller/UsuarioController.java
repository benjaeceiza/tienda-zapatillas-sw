package com.mycompany.tienda.zapatillas.controller;

import com.mycompany.tienda.zapatillas.dao.UsuarioDAO;
import com.mycompany.tienda.zapatillas.model.Usuario;
import com.mycompany.tienda.zapatillas.view.login.LoginView;
import com.mycompany.tienda.zapatillas.view.menus.MenuAdminView;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

public void procesarLogin(LoginView vistaLogin) {

        // 1. Sacamos los datos usando los métodos públicos que vos creaste
        String correo = vistaLogin.getCorreo();
        String password = vistaLogin.getContrasenia();

        // Validamos que no estén vacíos
        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vistaLogin, "Por favor, completá todos los campos.");
            return;
        }

        // 2. Le pedimos al DAO que busque el usuario en MySQL
        Usuario usuarioLogueado = usuarioDAO.iniciarSesion(correo, password);

        // 3. Verificamos si existe y qué rol tiene
        if (usuarioLogueado != null) {

            // ==========================================================
            // ¡EL PASO MÁGICO! Guardamos los datos en la memoria global
            // ==========================================================
            com.mycompany.tienda.zapatillas.model.SesionGlobal.idUsuarioActual = usuarioLogueado.getIdUsuario();
            com.mycompany.tienda.zapatillas.model.SesionGlobal.nombreUsuarioActual = usuarioLogueado.getNombre();

            if (usuarioLogueado.getRol().equals("ADMIN")) {
                // ¡Es jefe! Abrimos el menú de Admin
                MenuAdminView menuAdmin = new MenuAdminView();
                menuAdmin.setVisible(true);
                menuAdmin.setLocationRelativeTo(null); // Lo centramos

                // Cerramos la ventana de login
                vistaLogin.dispose();

            } else if (usuarioLogueado.getRol().equals("USER")) { 
                // ¡Es empleado! Abrimos el menú de empleado
                com.mycompany.tienda.zapatillas.view.menus.MenuEmpleadoView menuEmpleado = new com.mycompany.tienda.zapatillas.view.menus.MenuEmpleadoView();
                menuEmpleado.setVisible(true);
                menuEmpleado.setLocationRelativeTo(null); 

                // Cerramos el login
                vistaLogin.dispose();
            }

        } else {
            // Si el DAO devolvió null, le pifió a la clave o al correo
            JOptionPane.showMessageDialog(vistaLogin, "Correo o contraseña incorrectos.", "Error de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void llenarTabla(javax.swing.JTable tabla) {
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo Electrónico");
        modelo.addColumn("Rol"); // Reemplazamos el "Apellido" por el Rol

        java.util.List<Usuario> lista = usuarioDAO.listarUsuarios();
        for (Usuario u : lista) {
            Object[] fila = new Object[4];
            fila[0] = u.getIdUsuario();
            fila[1] = u.getNombre();
            fila[2] = u.getEmail();
            fila[3] = u.getRol().equals("ADMIN") ? "Administrador" : "Empleado"; // Traducción visual
            modelo.addRow(fila);
        }
        tabla.setModel(modelo);
    }

    public void abrirFormularioAgregar(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        com.mycompany.tienda.zapatillas.view.abm.FormularioEmpleadoDialog form = new com.mycompany.tienda.zapatillas.view.abm.FormularioEmpleadoDialog(menu, true);
        form.limpiarFormulario();
        form.setIdUsuarioActual(-1);
        form.setVisible(true);
    }

    public void abrirFormularioModificar(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        int fila = menu.getTablaEmpleados().getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(menu, "Seleccioná un empleado primero.");
            return;
        }

        int id = Integer.parseInt(menu.getTablaEmpleados().getValueAt(fila, 0).toString());
        String nombre = menu.getTablaEmpleados().getValueAt(fila, 1).toString();
        String correo = menu.getTablaEmpleados().getValueAt(fila, 2).toString();
        String rolVisual = menu.getTablaEmpleados().getValueAt(fila, 3).toString(); // "Administrador" o "Empleado"

        // Lo pasamos a formato BD
        String rolBD = rolVisual.equals("Administrador") ? "ADMIN" : "USER";

        com.mycompany.tienda.zapatillas.view.abm.FormularioEmpleadoDialog form = new com.mycompany.tienda.zapatillas.view.abm.FormularioEmpleadoDialog(menu, true);
        form.setFormulario(nombre, correo, rolBD);
        form.setIdUsuarioActual(id);
        form.setVisible(true);
    }

    public void procesarGuardadoEmpleado(com.mycompany.tienda.zapatillas.view.abm.FormularioEmpleadoDialog form, com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        Usuario u = new Usuario();
        u.setNombre(form.getNombre());
        u.setEmail(form.getCorreo());
        u.setPassword(form.getPassword());
        // Traducción para la Base de Datos
        u.setRol(form.getRol().equals("Administrador") ? "ADMIN" : "USER");
        u.setIdUsuario(form.getIdUsuarioActual());

        if (form.getIdUsuarioActual() == -1) {
            if (u.getPassword().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(form, "La contraseña es obligatoria para nuevos empleados.");
                return;
            }
            usuarioDAO.registrarUsuario(u);
        } else {
            usuarioDAO.modificarUsuario(u);
        }

        form.dispose();
        llenarTabla(menu.getTablaEmpleados());
    }

    public void eliminarEmpleado(com.mycompany.tienda.zapatillas.view.menus.MenuAdminView menu) {
        int fila = menu.getTablaEmpleados().getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(menu, "Seleccioná un empleado primero.");
            return;
        }

        int id = Integer.parseInt(menu.getTablaEmpleados().getValueAt(fila, 0).toString());
        String nombre = menu.getTablaEmpleados().getValueAt(fila, 1).toString();

        if (javax.swing.JOptionPane.showConfirmDialog(menu, "¿Eliminar a " + nombre + "?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION) {
            usuarioDAO.eliminarUsuario(id);
            llenarTabla(menu.getTablaEmpleados());
        }
    }
    
public void filtrarTabla(JTable tabla, String busqueda) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);
    
    for (Usuario urs : usuarioDAO.filtrarUsuarios(busqueda)) {
        // Quitamos el setPassword y corregimos la sintaxis
        modelo.addRow(new Object[]{
            urs.getIdUsuario(), 
            urs.getNombre(), 
            urs.getEmail(),
            urs.getRol() // Mostramos solo datos de lectura, no contraseñas
        });
    }
}

}
