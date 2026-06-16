package com.mycompany.tienda.zapatillas.reportes;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;
import com.mycompany.tienda.zapatillas.model.Producto;

public class ReporteInventarioPDF {

    public void generarReporteInventario(List<Producto> productos, String rutaArchivo) {
        
        // 1. Instanciamos el documento AFUERA del try (para que no se cierre de prepo)
        Document documento = new Document(PageSize.A4.rotate(), 36, 36, 54, 36);
        
        try {
            // 2. Creamos el archivo físico
            FileOutputStream ficheroPdf = new FileOutputStream(rutaArchivo);
            PdfWriter.getInstance(documento, ficheroPdf);
            
            // Abrimos el documento para meterle el contenido
            documento.open();

            // Encabezado
            Paragraph titulo = new Paragraph("Reporte de Inventario",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            // Tabla: ID, Marca, Modelo, Stock, Precio
            PdfPTable tabla = new PdfPTable(new float[]{1f, 2f, 3f, 1.2f, 1.5f});
            tabla.setWidthPercentage(100f);

            String[] headers = {"ID", "Marca", "Modelo", "Stock", "Precio ($)"};
            Color headerBg = new Color(211, 211, 211);
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
                cell.setBackgroundColor(headerBg);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6f);
                tabla.addCell(cell);
            }

            int totalItems = 0;
            double valorTotal = 0.0;

            // 3. Blindaje anti-roturas por si el inventario llega vacío
            if (productos != null && !productos.isEmpty()) {
                for (Producto p : productos) {
                    tabla.addCell(String.valueOf(p.getIdProducto()));
                    tabla.addCell(p.getMarca() == null ? "-" : p.getMarca());
                    tabla.addCell(p.getModelo() == null ? "-" : p.getModelo());
                    tabla.addCell(String.valueOf(p.getStock()));
                    tabla.addCell(String.format("%.2f", p.getPrecio()));
                    
                    // Sumamos para el resumen del pie de página
                    totalItems += p.getStock();
                    valorTotal += (p.getStock() * p.getPrecio());
                }
            } else {
                // Si filtraron y no hay nada, avisamos prolijamente
                PdfPCell celdaVacia = new PdfPCell(new Phrase("No hay productos para mostrar."));
                celdaVacia.setColspan(5);
                celdaVacia.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celdaVacia);
            }

            documento.add(tabla);

            // Resumen al pie
            documento.add(new Paragraph(" "));
            Paragraph resumen = new Paragraph(
                    "Total unidades en inventario: " + totalItems + "    Valor total estimado: $ " + String.format("%.2f", valorTotal),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            resumen.setAlignment(Element.ALIGN_RIGHT);
            documento.add(resumen);

            // 4. EL PASO VITAL: Cerramos el documento a mano al final de todo
            documento.close();
            
            // Avisamos por consola que todo salió joya
            System.out.println("¡PDF de Inventario cerrado y generado correctamente!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando PDF de inventario: " + e.getMessage(), e);
        }
    }
}