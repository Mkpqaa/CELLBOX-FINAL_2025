
package Modelo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;     
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.filechooser.FileSystemView;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfGState;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    public int IdVenta(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
    
    public int RegistrarVenta(Venta v){
        String sql = "INSERT INTO ventas (cliente, vendedor, total, fecha) VALUES (?,?,?,?)";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, v.getCliente());
        ps.setString(2, v.getVendedor());
        ps.setDouble(3, v.getTotal());

        // ––– Conversión segura de la fecha –––
        java.util.Date utilDate;
        try {
            utilDate = new SimpleDateFormat("dd/MM/yyyy")
                           .parse(v.getFecha());
        } catch (ParseException pe) {
            // Si falla el parse, usa la fecha actual
            utilDate = new java.util.Date();
            Logger.getLogger(VentaDao.class.getName())
                  .log(Level.WARNING, "Fecha inválida, uso hoy", pe);
        }
        // Asigna al parámetro 4 como java.sql.Date
        ps.setDate(4, new java.sql.Date(utilDate.getTime()));

        ps.execute();
    } catch (SQLException e) {
        System.out.println(e.toString());
    } finally {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    return r;
    }
    
    public int RegistrarDetalle(Detalle Dv){
       String sql = "INSERT INTO detalle (id_pro, cantidad, precio, id_venta) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setInt(4, Dv.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    public void registrarSalidaFIFO(String codigoProducto, int ventaId, Date fechaVenta, String numeroBoleta, int cantidadAVender) throws SQLException {
    Connection conn = cn.getConnection();
    conn.setAutoCommit(false);
    try {
        // 1) Traer lotes (productos.id) con stock > 0, ordenados por fecha de compra
        String sqlSelect = "SELECT id, stock, precio_compra FROM productos WHERE codigo = ? AND stock > 0  ORDER BY fecha_compra ASC";
        PreparedStatement psSel = conn.prepareStatement(sqlSelect);
        psSel.setString(1, codigoProducto);
        ResultSet rs = psSel.executeQuery();
        
        rs.beforeFirst();               // retrocede al inicio en caso de TYPE_SCROLL_INSENSITIVE
        int lotesEncontrados = 0;
        while (rs.next()) lotesEncontrados++;
        System.out.println(">>> Lotes encontrados en FIFO: " + lotesEncontrados);
        // Vuelve al principio para el bucle real
        rs.beforeFirst();

        int restante = cantidadAVender;
        while (rs.next() && restante > 0) {
            int loteId           = rs.getInt("id");
            int stockLote        = rs.getInt("stock");
            BigDecimal precioCompra = rs.getBigDecimal("precio_compra");

            int deducir = Math.min(stockLote, restante);

            // 2) Inserto en detalle apuntando al lote concreto
            String sqlDet = "INSERT INTO detalle (id_pro, cantidad, precio, id_venta) VALUES (?,?,?,?)";
            PreparedStatement psDet = conn.prepareStatement(sqlDet);
            psDet.setInt      (1, loteId);
            psDet.setInt      (2, deducir);
            psDet.setBigDecimal(3, precioCompra);
            psDet.setInt      (4, ventaId);
            psDet.executeUpdate();
            psDet.close();

            // 3) Actualizo stock del lote
            String sqlUpd = "UPDATE productos SET stock = stock - ? WHERE id = ?";
            PreparedStatement psUpd = conn.prepareStatement(sqlUpd);
            psUpd.setInt(1, deducir);
            psUpd.setInt(2, loteId);
            psUpd.executeUpdate();
            psUpd.close();

            restante -= deducir;
        }
        rs.close();
        psSel.close();

        if (restante > 0) {
            throw new SQLException("Stock insuficiente para vender " + cantidadAVender + " unidades.");
        }

        conn.commit();
    } catch (SQLException ex) {
        conn.rollback();
        throw ex;
    } finally {
        conn.setAutoCommit(true);
        conn.close();
    }
}
    public void actualizarNumeroComprobante(int idVenta, String nro) throws SQLException {
    String sql = "UPDATE ventas SET numero_comprobante = ? WHERE id = ?";
    try (Connection c = cn.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setString(1, nro);
        ps.setInt   (2, idVenta);
        ps.executeUpdate();
    }
}
    
    public boolean ActualizarStock(int cant, int id){
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,cant);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List Listarventas(){
       List<Venta> ListaVenta = new ArrayList();
       String sql = "SELECT c.id AS id_cli, c.nombre, v.* FROM clientes c INNER JOIN ventas v ON c.id = v.cliente";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Venta vent = new Venta();
               vent.setId(rs.getInt("id"));
               vent.setNombre_cli(rs.getString("nombre"));
               vent.setVendedor(rs.getString("vendedor"));
               vent.setTotal(rs.getDouble("total"));
               ListaVenta.add(vent);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaVenta;
   }
    public Venta BuscarVenta(int id){
        Venta cl = new Venta();
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setCliente(rs.getInt("cliente"));
                cl.setTotal(rs.getDouble("total"));
                cl.setVendedor(rs.getString("vendedor"));
                cl.setFecha(rs.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }
    class Watermark extends PdfPageEventHelper {
    private final Image logo;

    public Watermark(String rutaLogo) throws DocumentException, IOException {
        logo = Image.getInstance(rutaLogo);
        logo.scaleToFit(400, 400);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContentUnder();
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.3f);
        canvas.saveState();
        canvas.setGState(gs);
        float x = (document.getPageSize().getWidth()  - logo.getScaledWidth())  / 2;
        float y = (document.getPageSize().getHeight() - logo.getScaledHeight()) / 2;
        logo.setAbsolutePosition(x, y);
        try {
            canvas.addImage(logo);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        canvas.restoreState();
    }
}
    public void pdfV(int idventa, int Cliente, double total, String usuario) {
        try {
            Date date = new Date();
            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File salida = new File(url + "venta.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            String logoPath = getClass().getResource("/Img/logo.jpg").toString();
PdfWriter writer = PdfWriter.getInstance(doc, archivo);
writer.setPageEvent(new Watermark(logoPath));
doc.open();
            Image img = Image.getInstance(getClass().getResource("/Img/logo.jpg"));
            //Fecha
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            fecha.add("Vendedor: " + usuario + "\nFolio: " + idventa + "\nFecha: "
                    + new SimpleDateFormat("dd/MM/yyyy").format(date) + "\n\n");
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnWidthsEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(columnWidthsEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            Encabezado.addCell(img);
            Encabezado.addCell("");
            //info empresa
            String config = "SELECT * FROM config";
            String mensaje = "";
            try {
                con = cn.getConnection();
                ps = con.prepareStatement(config);
                rs = ps.executeQuery();
                if (rs.next()) {
                    mensaje = rs.getString("mensaje");
                    Encabezado.addCell("Ruc:    " + rs.getString("ruc") + "\nNombre: " + rs.getString("nombre") + "\nTeléfono: " + rs.getString("telefono") + "\nDirección: " + rs.getString("direccion") + "\n\n");
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            //
            Encabezado.addCell(fecha);
            doc.add(Encabezado);
            //cliente
            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("DATOS DEL CLIENTE" + "\n\n");
            doc.add(cli);

            PdfPTable proveedor = new PdfPTable(3);
            proveedor.setWidthPercentage(100);
            proveedor.getDefaultCell().setBorder(0);
            float[] columnWidthsCliente = new float[]{50f, 25f, 25f};
            proveedor.setWidths(columnWidthsCliente);
            proveedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliNom = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliTel = new PdfPCell(new Phrase("Télefono", negrita));
            PdfPCell cliDir = new PdfPCell(new Phrase("Dirección", negrita));
            cliNom.setBorder(Rectangle.NO_BORDER);
            cliTel.setBorder(Rectangle.NO_BORDER);
            cliDir.setBorder(Rectangle.NO_BORDER);
            proveedor.addCell(cliNom);
            proveedor.addCell(cliTel);
            proveedor.addCell(cliDir);
            String prove = "SELECT * FROM clientes WHERE id = ?";
            try {
                ps = con.prepareStatement(prove);
                ps.setInt(1, Cliente);
                rs = ps.executeQuery();
                if (rs.next()) {
                    proveedor.addCell(rs.getString("nombre"));
                    proveedor.addCell(rs.getString("telefono"));
                    proveedor.addCell(rs.getString("direccion") + "\n\n");
                } else {
                    proveedor.addCell("Publico en General");
                    proveedor.addCell("S/N");
                    proveedor.addCell("S/N" + "\n\n");
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(proveedor);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
            tabla.setWidths(columnWidths);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell c1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell c2 = new PdfPCell(new Phrase("Descripción.", negrita));
            PdfPCell c3 = new PdfPCell(new Phrase("P. unt.", negrita));
            PdfPCell c4 = new PdfPCell(new Phrase("P. Total", negrita));
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);
            String product = "SELECT d.id, d.id_pro,d.id_venta, d.precio, d.cantidad, p.id, p.nombre FROM detalle d INNER JOIN productos p ON d.id_pro = p.id WHERE d.id_venta = ?";
            try {
                ps = con.prepareStatement(product);
                ps.setInt(1, idventa);
                rs = ps.executeQuery();
                while (rs.next()) {
                    double subTotal = rs.getInt("cantidad") * rs.getDouble("precio");
                    tabla.addCell(rs.getString("cantidad"));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("precio"));
                    tabla.addCell(String.valueOf(subTotal));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(tabla);
            int totalUnidades = 0;
String sqlCant = "SELECT SUM(cantidad) FROM detalle WHERE id_venta = ?";
try {
    PreparedStatement psCant = con.prepareStatement(sqlCant);
    psCant.setInt(1, idventa);
    ResultSet rsCant = psCant.executeQuery();
    if (rsCant.next()) {
        totalUnidades = rsCant.getInt(1);
    }
    rsCant.close();
    psCant.close();
} catch (SQLException e) {
    e.printStackTrace();  // o System.out.println(e.toString());
}

// Genera en memoria esa cantidad de IMEIs
List<String> imeis = ImeiUtil.generarImeis(totalUnidades);

// Construye la tabla de IMEIs
Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
Font cellFont   = new Font(Font.FontFamily.COURIER,   10, Font.NORMAL);
PdfPTable tablaImeis = new PdfPTable(2);
tablaImeis.setWidthPercentage(50);
tablaImeis.addCell(new PdfPCell(new Phrase("No.",  headerFont)));
tablaImeis.addCell(new PdfPCell(new Phrase("IMEI", headerFont)));
for (int i = 0; i < imeis.size(); i++) {
    tablaImeis.addCell(new Phrase(String.valueOf(i + 1), cellFont));
    tablaImeis.addCell(new Phrase(imeis.get(i),         cellFont));
}

// Añade título y la tabla de IMEIs
doc.add(new Paragraph("LISTA DE IMEIS", headerFont));
doc.add(tablaImeis);
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total S/: " + total);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelacion \n\n");
            firma.add("------------------------------------\n");
            firma.add("Firma \n");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);
            Paragraph gr = new Paragraph();
            gr.add(Chunk.NEWLINE);
            gr.add(mensaje);
            gr.setAlignment(Element.ALIGN_CENTER);
            doc.add(gr);
            Font smallGray = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.DARK_GRAY);
Paragraph terms = new Paragraph();
terms.add(Chunk.NEWLINE);
terms.add("Términos y Condiciones:\n");
terms.add(new Phrase("1. Esta boleta es válida como comprobante de pago.\n", smallGray));
terms.add(new Phrase("2. Conserve este documento para cualquier reclamo o garantía.\n", smallGray));
terms.add(new Phrase("3. No se aceptan devoluciones pasados 7 días de la compra.\n", smallGray));
terms.add(new Phrase("4. Garantía de fábrica según política del fabricante.\n", smallGray));
terms.setAlignment(Element.ALIGN_JUSTIFIED);
terms.setLeading(10f);
doc.add(terms);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

    
}
