package com.mycompany.tienda.zapatillas;

// Importamos tu nueva vista de Login
import com.mycompany.tienda.zapatillas.view.login.LoginView;

public class TiendaZapatillas {

    public static void main(String[] args) {
        // Ejecutamos la interfaz gráfica de forma segura en el hilo de Swing
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Instanciamos la vista del Login y la hacemos visible
                LoginView login = new LoginView();
                login.setVisible(true);
                login.setLocationRelativeTo(null); // Esto la centra en la pantalla
            }
        });
    }
}