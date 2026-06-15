package com.mycompany.tienda.zapatillas.model;

public class Venta {
    
    private int idVenta;
    private int idCliente;
    private int idUsuario; 
    private String fecha;
    private double total;

    public Venta() {
    }

    // Constructor completo 
    public Venta(int idCliente, int idUsuario, String fecha, double total) {
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.total = total;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}