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
        try (Document documento = new Document(PageSize.A4.rotate(), 36, 36, 54, 36)) {
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Encabezado (sin color explícito en la fuente)
            Paragraph titulo = new Paragraph("Reporte de Inventario",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            // Tabla: ID, Marca, Modelo, Stock, Precio
            PdfPTable tabla = new PdfPTable(new float[]{1f, 2f, 3f, 1.2f, 1.5f});
            tabla.setWidthPercentage(100f);

            String[] headers = {"ID", "Marca", "Modelo", "Stock", "Precio ($)"};
            Color headerBg = new Color(211, 211, 211); // java.awt.Color light gray
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
                cell.setBackgroundColor(headerBg); // usa java.awt.Color
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6f);
                tabla.addCell(cell);
            }

            // Filas
            for (Producto p : productos) {
                tabla.addCell(String.valueOf(p.getIdProducto()));
                tabla.addCell(p.getMarca() == null ? "" : p.getMarca());
                tabla.addCell(p.getModelo() == null ? "" : p.getModelo());
                tabla.addCell(String.valueOf(p.getStock()));
                tabla.addCell(String.format("%.2f", p.getPrecio()));
            }

            documento.add(tabla);

            // Resumen al pie: total de ítems y valor total del inventario
            int totalItems = productos.stream().mapToInt(Producto::getStock).sum();
            double valorTotal = productos.stream().mapToDouble(p -> p.getStock() * p.getPrecio()).sum();

            documento.add(new Paragraph(" "));
            Paragraph resumen = new Paragraph(
                    "Total unidades en inventario: " + totalItems + "    Valor total estimado: $ " + String.format("%.2f", valorTotal),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            resumen.setAlignment(Element.ALIGN_RIGHT);
            documento.add(resumen);

            documento.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando PDF de inventario: " + e.getMessage(), e);
        }
    }
}
