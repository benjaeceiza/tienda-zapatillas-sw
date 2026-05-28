package com.mycompany.tienda.zapatillas.controller;

import com.mycompany.tienda.zapatillas.dao.UsuarioDAO;

public class UsuarioController {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Método vacío para conectar la VistaLogin con el UsuarioDAO
    public void intentarLogin(String email, String password) {
        // TODO: Recibir datos de la vista, llamar al DAO y decidir si abre la ventana principal
    }
}