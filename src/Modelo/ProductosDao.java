
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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



public class ProductosDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProductos(Productos pro){
        String sql = "INSERT INTO productos (codigo, nombre, marca, proveedor, stock, precio, precio_compra, precio_venta, lote, imagen, fecha_compra, numero_factura) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getMarca());
            ps.setInt(4, pro.getProveedor());
            ps.setInt(5, pro.getStock());
            ps.setDouble(6, pro.getPrecio()); // también puedes usar como precio_venta si lo decides así
            ps.setDouble(7, pro.getPrecio()); // precio_compra, si se usa igual
            ps.setDouble(8, pro.getPrecio()); // precio_venta
            ps.setInt(9, pro.getLote());
            ps.setBytes(10, pro.getImagen());
            ps.setString(11, pro.getFecha_compra());
            ps.setString(12, pro.getNumero_factura());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List ListarProductos(){
       List<Productos> Listapro = new ArrayList();
       String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON pr.id = p.proveedor ORDER BY p.id DESC";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Productos pro = new Productos();
               pro.setId(rs.getInt("id"));
               pro.setCodigo(rs.getString("codigo"));
               pro.setNombre(rs.getString("nombre"));
               pro.setMarca(rs.getString("marca"));
               pro.setProveedor(rs.getInt("id_proveedor"));
               pro.setProveedorPro(rs.getString("nombre_proveedor"));
               pro.setStock(rs.getInt("stock"));
               pro.setPrecio(rs.getDouble("precio"));
               pro.setImagen(rs.getBytes("imagen"));
               Listapro.add(pro);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Listapro;
   }
    
    public boolean EliminarProductos(int id){
       String sql = "DELETE FROM productos WHERE id = ?";
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
    
    public boolean ModificarProductos(Productos pro){
   String sql = "UPDATE productos SET codigo=?, nombre=?, marca=?, proveedor=?, stock=?, precio=? WHERE id=?";
   try {
       // Asegúrate de obtener la conexión
       con = cn.getConnection();

       ps = con.prepareStatement(sql);
       // 1–6 cubren los SET(...)
       ps.setString(1, pro.getCodigo());
       ps.setString(2, pro.getNombre());
       ps.setString(3, pro.getMarca());
       ps.setInt(4, pro.getProveedor());
       ps.setInt(5, pro.getStock());
       ps.setDouble(6, pro.getPrecio());
       // 7 corresponde al WHERE id = ?
       ps.setInt(7, pro.getId());

       ps.executeUpdate();
       return true;
   } catch (SQLException e) {
       System.out.println(e.toString());
       return false;
   } finally {
       try {
           if (ps != null) ps.close();
           if (con != null) con.close();
       } catch (SQLException ex) {
           System.out.println(ex.toString());
       }
   }
}
    
    public Productos BuscarPro(String cod){
        Productos producto = new Productos();
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) producto.setCodigo(rs.getString("codigo"));{
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    public Productos BuscarId(int id){
        Productos pro = new Productos();
        String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON p.proveedor = pr.id WHERE p.id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setMarca(rs.getString("marca")); 
                pro.setProveedor(rs.getInt("proveedor"));
                pro.setProveedorPro(rs.getString("nombre_proveedor"));
                pro.setStock(rs.getInt("stock"));
                pro.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pro; 
    }
    
    public Productos buscarPorCodigo(String codigo) throws SQLException {
    String sql = "SELECT pr.id            AS id_proveedor, " +
        "       pr.nombre        AS nombre_proveedor, " +
        "       p.* " +
        "FROM proveedor pr " +
        "JOIN productos p ON p.proveedor = pr.id " +
        "WHERE p.codigo = ? " +
        "LIMIT 1";
    try (Connection con = cn.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Productos pro = new Productos();
            pro.setId(rs.getInt("id"));
            pro.setCodigo(rs.getString("codigo"));
            pro.setNombre(rs.getString("nombre"));
            pro.setMarca(rs.getString("marca"));
            pro.setProveedor(rs.getInt("proveedor"));
            pro.setProveedorPro(rs.getString("nombre_proveedor"));
            pro.setStock(rs.getInt("stock"));
            pro.setPrecio(rs.getDouble("precio"));
            return pro;
        }
    }
    return null;
}
    public List<String> listarMarcas() {
        List<String> marcas = new ArrayList<>();
        String sql = "SELECT DISTINCT marca FROM productos ORDER BY marca";
        try (Connection c = cn.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                marcas.add(r.getString("marca"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar marcas: " + e);
        }
        return marcas;
    }
    public Proveedor BuscarProveedor(String nombre){
        Proveedor pr = new Proveedor();
        String sql = "SELECT * FROM proveedor WHERE nombre = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pr;
    }
    public Config BuscarDatos(){
        Config conf = new Config();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getString("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setMensaje(rs.getString("mensaje"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    public boolean ModificarDatos(Config conf){
       String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, conf.getRuc());
           ps.setString(2, conf.getNombre());
           ps.setString(3, conf.getTelefono());
           ps.setString(4, conf.getDireccion());
           ps.setString(5, conf.getMensaje());
           ps.setInt(6, conf.getId());
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
   }
     public int obtenerUltimoLote(String codigo) {
        int lote = 0;
        String sql = "SELECT MAX(lote) FROM productos WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                lote = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener lote: " + e.toString());
        }
        return lote;
    }
    public int contarLotes(String codigoProducto) throws SQLException {
    String sql = "SELECT COUNT(*) FROM productos WHERE codigo = ?";
    try (Connection con = cn.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, codigoProducto);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    }
    return 0;
}
    
    public int obtenerStockTotal(String codigoProducto) throws SQLException {
    String sql = "SELECT SUM(stock) AS total FROM productos WHERE codigo = ?";
    try (Connection con = cn.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, codigoProducto);
        System.out.println(">> SQL stock total: codigo = '" + codigoProducto + "'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println(">> stock total result set: " + rs.getInt("total"));
            int t = rs.getInt("total");
            return rs.wasNull() ? 0 : t;
        }
    }
    return 0;
}
     
    public int contarFacturas() {
        int total = 0;
        String sql = "SELECT COUNT(DISTINCT numero_factura) FROM productos";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al contar facturas: " + e.toString());
        }
        return total;
    }
   public void pdfCompra(Productos producto) {
    try {
        Date date = new Date();
        String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        FileOutputStream archivo = new FileOutputStream(new File(url + File.separator + "compra_" + producto.getCodigo() + ".pdf"));
        Document doc = new Document();
        PdfWriter.getInstance(doc, archivo);
        doc.open();

        Image img = Image.getInstance(getClass().getResource("/Img/logo.jpg"));
        img.scaleToFit(100, 100);
        img.setAlignment(Element.ALIGN_LEFT);
        doc.add(img);

        Paragraph encabezado = new Paragraph("FACTURA DE COMPRA", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK));
        encabezado.setAlignment(Element.ALIGN_CENTER);
        doc.add(encabezado);

        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Número de Factura: " + producto.getNumero_factura()));
        doc.add(new Paragraph("Fecha de Compra: " + producto.getFecha_compra()));
        doc.add(new Paragraph("Proveedor ID: " + producto.getProveedor()));
        doc.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{20f, 30f, 15f, 15f, 20f});
        tabla.addCell("Código");
        tabla.addCell("Producto");
        tabla.addCell("Marca");
        tabla.addCell("Cantidad");
        tabla.addCell("Precio Unitario");

        tabla.addCell(producto.getCodigo());
        tabla.addCell(producto.getNombre());
        tabla.addCell(producto.getMarca());
        tabla.addCell(String.valueOf(producto.getStock()));
        tabla.addCell(String.format("S/ %.2f", producto.getPrecio()));

        doc.add(tabla);

        Paragraph total = new Paragraph("\nTOTAL: S/ " + (producto.getStock() * producto.getPrecio()));
        total.setAlignment(Element.ALIGN_RIGHT);
        doc.add(total);

        doc.add(new Paragraph("\nLote Nro: " + producto.getLote()));
        doc.add(new Paragraph("Imagen: " + producto.getImagen()));

        doc.add(new Paragraph("\nGracias por su compra."));

        doc.close();
        archivo.close();

        Desktop.getDesktop().open(new File(url + File.separator + "compra_" + producto.getCodigo() + ".pdf"));
    } catch (Exception e) {
        System.out.println("Error al generar PDF de compra: " + e.toString());
    }
}
}
