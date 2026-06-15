package com.mycompany.tienda.zapatillas.view.menus;

import java.awt.event.ActionListener;
import com.mycompany.tienda.zapatillas.controller.ClienteController;
import com.mycompany.tienda.zapatillas.controller.ProductoController;
import com.mycompany.tienda.zapatillas.controller.VentaController;
/**
 *
 * @author Usuario
 */
public class MenuEmpleadoView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuEmpleadoView.class.getName());

    public MenuEmpleadoView() {
        initComponents();
        
        PanelPrincipalEmpleados.add(panelDashboardEmpleado, "dashboard");
    

    // Agregamos los paneles con los nombres correctos
        PanelPrincipalEmpleados.add(panelInventario, "inventario");
        PanelPrincipalEmpleados.add(panelClientes, "clientes");
        PanelPrincipalEmpleados.add(panelVentas, "venta");
    
    // Si quieres que al arrancar se vea el dashboard o inventario:
      mostrarPanel("dashboard");
    
    }
    
    private void mostrarPanel(String nombrePanel) {
    java.awt.CardLayout cl = (java.awt.CardLayout) (PanelPrincipalEmpleados.getLayout());
    cl.show(PanelPrincipalEmpleados, nombrePanel);
}
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDashboardEmpleado = new javax.swing.JPanel();
        BtnMenuInventario = new javax.swing.JButton();
        BtnMenuClientes = new javax.swing.JButton();
        BtnMenuVenta = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollBar1 = new javax.swing.JScrollBar();
        PanelPrincipalEmpleados = new javax.swing.JPanel();
        panelInventario = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaInventario = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        panelClientes = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btnAgregarCliente = new javax.swing.JButton();
        btnModificarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        txtBuscarClientes = new javax.swing.JTextField();
        panelVentas = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmbClientes = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cmbProductos = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnAgregarCarrito = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        btnConfirmarVenta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCarrito = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        BtnInventario = new javax.swing.JMenuItem();
        BtnClientes = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        BtnNuevaVenta = new javax.swing.JMenuItem();

        BtnMenuInventario.setText("Inventario");
        BtnMenuInventario.addActionListener(this::BtnMenuInventarioActionPerformed);

        BtnMenuClientes.setText("Clientes");
        BtnMenuClientes.addActionListener(this::BtnMenuClientesActionPerformed);

        BtnMenuVenta.setText("Venta");
        BtnMenuVenta.addActionListener(this::BtnMenuVentaActionPerformed);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Menu Empleado");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Bienvenido!!");

        javax.swing.GroupLayout panelDashboardEmpleadoLayout = new javax.swing.GroupLayout(panelDashboardEmpleado);
        panelDashboardEmpleado.setLayout(panelDashboardEmpleadoLayout);
        panelDashboardEmpleadoLayout.setHorizontalGroup(
            panelDashboardEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDashboardEmpleadoLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(panelDashboardEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelDashboardEmpleadoLayout.createSequentialGroup()
                        .addGroup(panelDashboardEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelDashboardEmpleadoLayout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(jLabel3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDashboardEmpleadoLayout.createSequentialGroup()
                                .addComponent(BtnMenuInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnMenuClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnMenuVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        panelDashboardEmpleadoLayout.setVerticalGroup(
            panelDashboardEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDashboardEmpleadoLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel4)
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addGap(36, 36, 36)
                .addGroup(panelDashboardEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnMenuVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnMenuClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnMenuInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelPrincipalEmpleados.setLayout(new java.awt.CardLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("INVENTARIO");

        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Marca", "Modelo", "Precio", "Stock"
            }
        ));
        jScrollPane4.setViewportView(tablaInventario);

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelInventarioLayout = new javax.swing.GroupLayout(panelInventario);
        panelInventario.setLayout(panelInventarioLayout);
        panelInventarioLayout.setHorizontalGroup(
            panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelInventarioLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        panelInventarioLayout.setVerticalGroup(
            panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        PanelPrincipalEmpleados.add(panelInventario, "card2");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("LISTADO DE CLIENTES");

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellido", "Correo Electrónico"
            }
        ));
        jScrollPane2.setViewportView(tablaClientes);

        btnAgregarCliente.setText("Agregar");
        btnAgregarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarCliente.addActionListener(this::btnAgregarClienteActionPerformed);

        btnModificarCliente.setText("Modificar");
        btnModificarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarCliente.addActionListener(this::btnModificarClienteActionPerformed);

        btnEliminarCliente.setText("Eliminar");
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(this::btnEliminarClienteActionPerformed);

        txtBuscarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClientesKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClientesLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btnAgregarCliente)
                        .addGap(51, 51, 51)
                        .addComponent(btnModificarCliente)
                        .addGap(41, 41, 41)
                        .addComponent(btnEliminarCliente))
                    .addGroup(panelClientesLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelClientesLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarCliente)
                    .addComponent(btnModificarCliente)
                    .addComponent(btnEliminarCliente))
                .addGap(18, 18, 18))
        );

        PanelPrincipalEmpleados.add(panelClientes, "card3");

        jLabel7.setText("Cliente");

        cmbClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbClientes.addActionListener(this::cmbClientesActionPerformed);

        jLabel8.setText("Producto");

        cmbProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbProductos.addActionListener(this::cmbProductosActionPerformed);

        jLabel9.setText("Cantidad");

        txtCantidad.addActionListener(this::txtCantidadActionPerformed);

        btnAgregarCarrito.setText("Cargar");
        btnAgregarCarrito.addActionListener(this::btnAgregarCarritoActionPerformed);

        lblTotal.setText("Total: $ 0.00");

        btnConfirmarVenta.setText("CONFIRMAR VENTA");
        btnConfirmarVenta.addActionListener(this::btnConfirmarVentaActionPerformed);

        tablaCarrito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Prod", "Modelo", "Cantidad", "Precio Unitario", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(tablaCarrito);

        javax.swing.GroupLayout panelVentasLayout = new javax.swing.GroupLayout(panelVentas);
        panelVentas.setLayout(panelVentasLayout);
        panelVentasLayout.setHorizontalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelVentasLayout.createSequentialGroup()
                                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelVentasLayout.createSequentialGroup()
                                        .addComponent(cmbProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(panelVentasLayout.createSequentialGroup()
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAgregarCarrito)))
                                .addGap(18, 18, 18))
                            .addGroup(panelVentasLayout.createSequentialGroup()
                                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(lblTotal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btnConfirmarVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        panelVentasLayout.setVerticalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(2, 2, 2)
                        .addComponent(cmbProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarCarrito))
                        .addGap(38, 38, 38)
                        .addComponent(lblTotal)
                        .addGap(31, 31, 31)
                        .addComponent(btnConfirmarVenta)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelPrincipalEmpleados.add(panelVentas, "card4");

        jMenu1.setText("Gestion");

        BtnInventario.setText("Inventario");
        BtnInventario.addActionListener(this::BtnInventarioActionPerformed);
        jMenu1.add(BtnInventario);

        BtnClientes.setText("Clientes");
        BtnClientes.addActionListener(this::BtnClientesActionPerformed);
        jMenu1.add(BtnClientes);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Caja");

        BtnNuevaVenta.setText("Nueva Venta");
        BtnNuevaVenta.addActionListener(this::BtnNuevaVentaActionPerformed);
        jMenu2.add(BtnNuevaVenta);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipalEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipalEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnMenuInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenuInventarioActionPerformed
       mostrarPanel("inventario");
    new ProductoController().llenarTabla(this.tablaInventario);
    }//GEN-LAST:event_BtnMenuInventarioActionPerformed

    private void BtnMenuClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenuClientesActionPerformed
        mostrarPanel("clientes");
    new ClienteController().llenarTabla(this.tablaClientes);
    }//GEN-LAST:event_BtnMenuClientesActionPerformed

    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
       com.mycompany.tienda.zapatillas.controller.ClienteController controlador = new com.mycompany.tienda.zapatillas.controller.ClienteController();
        // Pasamos 'this' (ventana) y 'tablaClientes' (el componente JTable)
        controlador.abrirFormularioAgregar(this, this.tablaClientes);
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void btnModificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarClienteActionPerformed
        com.mycompany.tienda.zapatillas.controller.ClienteController controlador = 
    new com.mycompany.tienda.zapatillas.controller.ClienteController();

    // 1. Abrimos el formulario (el programa se pausa aquí hasta que cierres el diálogo)
    controlador.abrirFormularioModificar(this, this.tablaClientes);
    
    // 2. UNA VEZ CERRADO EL DIÁLOGO, refrescamos la tabla inmediatamente
    controlador.llenarTabla(this.tablaClientes);
    }//GEN-LAST:event_btnModificarClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        com.mycompany.tienda.zapatillas.controller.ClienteController controlador = new com.mycompany.tienda.zapatillas.controller.ClienteController();
        controlador.eliminarCliente(this, this.tablaClientes);
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void cmbClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbClientesActionPerformed

    private void cmbProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbProductosActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnAgregarCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCarritoActionPerformed
        new com.mycompany.tienda.zapatillas.controller.VentaController().agregarAlCarrito(
        this, 
        this.tablaCarrito, 
        this.cmbClientes, 
        this.cmbProductos, 
        this.txtCantidad, 
        this.lblTotal
    );
    }//GEN-LAST:event_btnAgregarCarritoActionPerformed

    private void btnConfirmarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarVentaActionPerformed
       new com.mycompany.tienda.zapatillas.controller.VentaController().confirmarVenta(
        this, 
        this.tablaCarrito, 
        this.cmbClientes, 
        this.lblTotal
    );
    }//GEN-LAST:event_btnConfirmarVentaActionPerformed

    private void BtnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInventarioActionPerformed
        mostrarPanel("inventario");
        new ProductoController().llenarTabla(this.tablaInventario);
    }//GEN-LAST:event_BtnInventarioActionPerformed

    private void BtnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClientesActionPerformed
        mostrarPanel("clientes");
        new ClienteController().llenarTabla(this.tablaClientes);
    }//GEN-LAST:event_BtnClientesActionPerformed

    private void BtnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevaVentaActionPerformed
        mostrarPanel("venta"); // Cambiado de "Venta" a "venta"
    new com.mycompany.tienda.zapatillas.controller.VentaController().inicializarCaja(
        this.tablaCarrito, this.lblTotal, this.txtCantidad, this.cmbClientes, this.cmbProductos
    );
    }//GEN-LAST:event_BtnNuevaVentaActionPerformed

    private void BtnMenuVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenuVentaActionPerformed
        mostrarPanel("venta");
        new com.mycompany.tienda.zapatillas.controller.VentaController().inicializarCaja(
        this.tablaCarrito, 
        this.lblTotal, 
        this.txtCantidad, 
        this.cmbClientes, 
        this.cmbProductos
    );
    }//GEN-LAST:event_BtnMenuVentaActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
     if (txtBuscar.getText().isEmpty()) {
        new com.mycompany.tienda.zapatillas.controller.ProductoController().llenarTabla(tablaInventario);
    } else {
        // Si hay texto, filtramos
        new com.mycompany.tienda.zapatillas.controller.ProductoController().filtrarTabla(tablaInventario, txtBuscar.getText());
    }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClientesKeyReleased
        String filtro = txtBuscarClientes.getText();
    new com.mycompany.tienda.zapatillas.controller.ClienteController().filtrarTabla(tablaClientes, filtro);
    }//GEN-LAST:event_txtBuscarClientesKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MenuEmpleadoView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem BtnClientes;
    private javax.swing.JMenuItem BtnInventario;
    private javax.swing.JButton BtnMenuClientes;
    private javax.swing.JButton BtnMenuInventario;
    private javax.swing.JButton BtnMenuVenta;
    private javax.swing.JMenuItem BtnNuevaVenta;
    private javax.swing.JPanel PanelPrincipalEmpleados;
    private javax.swing.JButton btnAgregarCarrito;
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnConfirmarVenta;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnModificarCliente;
    private javax.swing.JComboBox<String> cmbClientes;
    private javax.swing.JComboBox<String> cmbProductos;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelDashboardEmpleado;
    private javax.swing.JPanel panelInventario;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JTable tablaCarrito;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaInventario;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscarClientes;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}

