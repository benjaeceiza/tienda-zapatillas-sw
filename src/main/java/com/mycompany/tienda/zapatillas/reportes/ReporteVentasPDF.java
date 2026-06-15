package com.mycompany.tienda.zapatillas.reportes;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

public class ReporteVentasPDF {

    public void generarReporteDesdeFilas(List<Object[]> filas, String rutaArchivo) {
        try (com.lowagie.text.Document documento = new com.lowagie.text.Document()) {
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
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
            for (Object[] fila : filas) {
                tabla.addCell(String.valueOf(fila[0]));
                tabla.addCell(String.valueOf(fila[1]));
                tabla.addCell(String.valueOf(fila[2]));
                tabla.addCell(String.valueOf(fila[3]));
                double total = 0.0;
                try {
                    total = Double.parseDouble(String.valueOf(fila[4]));
                } catch (Exception ex) {
                    // ignore parse errors
                }
                tabla.addCell(String.format("$ %.2f", total));
                totalGeneral += total;
            }

            documento.add(tabla);

            // Total general al pie
            documento.add(new Paragraph(" "));
            Paragraph resumen = new Paragraph("Total general: " + String.format("$ %.2f", totalGeneral),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            resumen.setAlignment(Element.ALIGN_RIGHT);
            documento.add(resumen);

            documento.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }
}
