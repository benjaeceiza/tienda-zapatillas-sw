package com.mycompany.tienda.zapatillas.reportes;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

public class ReporteVentasPDF {

    public void generarReporteDesdeFilas(List<Object[]> filas, String rutaArchivo) {
        
        // 1. Lo sacamos del paréntesis del try. Instanciamos normal.
        Document documento = new Document();
        
        try {
            // 2. Creamos el archivo físico explicitly
            FileOutputStream ficheroPdf = new FileOutputStream(rutaArchivo);
            PdfWriter.getInstance(documento, ficheroPdf);
            
            // Abrimos el documento para empezar a inyectar contenido
            documento.open();

            // Título
            Paragraph titulo = new Paragraph("Reporte de Ventas",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            // Tabla: Ticket ID, Fecha, Cliente, Vendedor, Total
            PdfPTable tabla = new PdfPTable(new float[]{1.2f, 2f, 3f, 2.5f, 1.5f});
            tabla.setWidthPercentage(100);
            
            String[] headers = {"Ticket", "Fecha", "Cliente", "Vendedor", "Total"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
            }

            double totalGeneral = 0.0;
            
            // 3. Blindaje anti-roturas: Revisamos que la lista no esté vacía
            if (filas != null && !filas.isEmpty()) {
                for (Object[] fila : filas) {
                    // Evitamos que un "null" puro rompa la generación del PDF
                    tabla.addCell(fila[0] != null ? String.valueOf(fila[0]) : "-");
                    tabla.addCell(fila[1] != null ? String.valueOf(fila[1]) : "-");
                    tabla.addCell(fila[2] != null ? String.valueOf(fila[2]) : "-");
                    tabla.addCell(fila[3] != null ? String.valueOf(fila[3]) : "-");
                    
                    double total = 0.0;
                    try {
                        if (fila[4] != null) {
                            total = Double.parseDouble(String.valueOf(fila[4]));
                        }
                    } catch (Exception ex) {
                        // Ignorar errores de conversión
                    }
                    tabla.addCell(String.format("$ %.2f", total));
                    totalGeneral += total;
                }
            } else {
                // Si justo no hay ventas en ese filtro, mostramos un mensaje prolijo
                PdfPCell celdaVacia = new PdfPCell(new Phrase("No hay ventas registradas en este período."));
                celdaVacia.setColspan(5);
                celdaVacia.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celdaVacia);
            }

            documento.add(tabla);

         // Total general al pie
            documento.add(new Paragraph(" "));
            Paragraph resumen = new Paragraph("Total general: " + String.format("$ %.2f", totalGeneral),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            resumen.setAlignment(Element.ALIGN_RIGHT);
            documento.add(resumen);

            // AGREGÁ ESTO PARA DEPURAR:
            System.out.println("¡Llegué al final! Guardando el documento...");

            // EL PASO VITAL:
            documento.close();
            
            // AGREGÁ ESTO TAMBIÉN:
            System.out.println("¡PDF cerrado correctamente por iText!");    
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }
}