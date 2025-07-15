/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.Combo;
import Modelo.Config;
import Modelo.Detalle;
import Modelo.Eventos;
import Modelo.LoginDAO; 
import Modelo.Productos;
import Modelo.ProductosDao;
import Modelo.Proveedor;
import Modelo.ProveedorDao;
import Modelo.Venta;
import Modelo.VentaDao;
import Modelo.login;
import Reportes.Excel;
import Reportes.Grafico;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 *
 * @author USUARIO
 */
public final class Sistema extends javax.swing.JFrame {
    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    Cliente cl = new Cliente();
    ClienteDao client = new ClienteDao();
    Proveedor pr = new Proveedor();
    ProveedorDao PrDao = new ProveedorDao();
    Productos pro = new Productos();
    ProductosDao proDao = new ProductosDao();
    Venta v = new Venta();
    VentaDao Vdao = new VentaDao();
    Detalle Dv = new Detalle();
    Config conf = new Config();
    Eventos event = new Eventos();
    login lg = new login();
    LoginDAO login = new LoginDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    int item;
    double Totalpagar = 0.00;
    
    private Modelo.Productos currentProducto = new Productos();

    
    public Sistema() {
        initComponents();
         currentProducto = new Productos();
        inicializarMarca();
    }
    
    public Sistema (login priv){
        initComponents();
        inicializarMarca();
        this.setLocationRelativeTo(null);
        Midate.setDate(fechaVenta);
        txtIdCliente.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdPro.setVisible(false);
        txtIdproducto.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdConfig.setVisible(false);
        txtIdCV.setVisible(false);
        ListarConfig();
        if (priv.getRol().equals("Asistente")) {
            btnProductos.setEnabled(false);
            btnProveedor.setEnabled(false);
            LabelVendedor.setText(priv.getNombre());
        }else{
            LabelVendedor.setText(priv.getNombre());
        }
    }
    public void ListarCliente() {
        List<Cliente> ListarCl = client.ListarCliente();
        modelo = (DefaultTableModel) TableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getDni();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getTelefono();
            ob[4] = ListarCl.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableCliente.setModel(modelo);

    }

    public void ListarProveedor() {
        List<Proveedor> ListarPr = PrDao.ListarProveedor();
        modelo = (DefaultTableModel) TableProveedor.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRuc();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);

    }
    public void ListarUsuarios() {
        List<login> Listar = login.ListarUsuarios();
        modelo = (DefaultTableModel) TableUsuarios.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getCorreo();
            ob[3] = Listar.get(i).getRol();
            modelo.addRow(ob);
        }
        TableUsuarios.setModel(modelo);

    }
    public void ListarProductos() {
        List<Productos> ListarPro = proDao.ListarProductos();
        modelo = (DefaultTableModel) TableProducto.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedorPro();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            ob[6] = ListarPro.get(i).getMarca();
            modelo.addRow(ob);
        }
        TableProducto.setModel(modelo);

    }

    public void ListarConfig() {
        conf = proDao.BuscarDatos();
        txtIdConfig.setText("" + conf.getId());
        txtRucConfig.setText("" + conf.getRuc());
        txtNombreConfig.setText("" + conf.getNombre());
        txtTelefonoConfig.setText("" + conf.getTelefono());
        txtDireccionConfig.setText("" + conf.getDireccion());
        txtMensaje.setText("" + conf.getMensaje());
    }

    public void ListarVentas() {
        modelo = (DefaultTableModel) TableVentas.getModel();
    modelo.setRowCount(0);

    // 2) Obtengo la lista de ventas de la BBDD
    List<Venta> lista = Vdao.Listarventas();
    Object[] ob = new Object[4];
    for (Venta v : lista) {
        ob[0] = v.getId();
        ob[1] = v.getNombre_cli();
        ob[2] = v.getVendedor();
        ob[3] = v.getTotal();
        modelo.addRow(ob);
        }


    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel43 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        LabelVendedor = new javax.swing.JLabel();
        tipo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        btnBackup = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        BtnCatalogo = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        btnEliminarventa = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtRucVenta = new javax.swing.JTextField();
        txtNombreClienteventa = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtIdCV = new javax.swing.JTextField();
        txtIdPro = new javax.swing.JTextField();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        btnGenerarVenta = new javax.swing.JButton();
        btnGraficar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDirecionCliente = new javax.swing.JTextField();
        txtIdCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        btnEvaluacionCrediticiaActionPerformed = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNombreproveedor = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        btnguardarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        txtIdproducto = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtDesPro = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtCantPro = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtPrecioPro = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        cbxProveedorPro = new javax.swing.JComboBox<>();
        btnGuardarpro = new javax.swing.JButton();
        btnEditarpro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnNuevoPro = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        btnExcelPro = new javax.swing.JButton();
        cbxMarcaPro = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtCantidadLote1 = new javax.swing.JTextField();
        btnImagenesProducto = new javax.swing.JButton();
        fechaCompraPicker = new com.toedter.calendar.JDateChooser();
        lblRutaImagen = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btndevolucion = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtNombreConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        txtMensaje = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtRucConfig = new javax.swing.JTextField();
        txtIdConfig = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnIniciar = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableUsuarios = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        btnrespaldar = new javax.swing.JToggleButton();
        jLabel46 = new javax.swing.JLabel();
        btnbackup = new javax.swing.JToggleButton();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setBackground(new java.awt.Color(204, 255, 102));
        jLabel43.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 48)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 0));
        jLabel43.setText("PUNTO DE VENTA CELLBOX");
        jLabel43.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(204, 0, 0))); // NOI18N
        getContentPane().add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, -1, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/FONDOACTUALIAZDO.png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -70, 1080, 170));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        btnNuevaVenta.setBackground(new java.awt.Color(0, 0, 0));
        btnNuevaVenta.setForeground(new java.awt.Color(255, 255, 51));
        btnNuevaVenta.setText("NUEVA VENTA");
        btnNuevaVenta.setAutoscrolls(true);
        btnNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevaVenta.setFocusable(false);
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(0, 0, 0));
        btnClientes.setForeground(new java.awt.Color(255, 255, 51));
        btnClientes.setText("CLIENTES");
        btnClientes.setAutoscrolls(true);
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClientes.setFocusable(false);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProveedor.setBackground(new java.awt.Color(0, 0, 0));
        btnProveedor.setForeground(new java.awt.Color(255, 255, 51));
        btnProveedor.setText("PROVEEDORES");
        btnProveedor.setAutoscrolls(true);
        btnProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProveedor.setFocusable(false);
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(0, 0, 0));
        btnProductos.setForeground(new java.awt.Color(255, 255, 51));
        btnProductos.setText("COMPRA");
        btnProductos.setAutoscrolls(true);
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProductos.setFocusable(false);
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductosMouseClicked(evt);
            }
        });
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(0, 0, 0));
        btnVentas.setForeground(new java.awt.Color(255, 255, 51));
        btnVentas.setText("REGISTRO DE COMPRAS/VENTAS");
        btnVentas.setAutoscrolls(true);
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVentas.setFocusable(false);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(153, 153, 153));
        btnConfig.setFont(new java.awt.Font("Dialog", 0, 8)); // NOI18N
        btnConfig.setForeground(new java.awt.Color(255, 255, 255));
        btnConfig.setText("INFORMACION DE NUESTRA EMPRESA");
        btnConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnConfig.setFocusable(false);
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        LabelVendedor.setForeground(new java.awt.Color(255, 255, 255));
        LabelVendedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tipo.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(153, 153, 153));
        jButton1.setFont(new java.awt.Font("Dialog", 0, 8)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("CONFIGURACION DE USUARIIOS");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N

        btnBackup.setBackground(new java.awt.Color(51, 204, 0));
        btnBackup.setFont(new java.awt.Font("Eras Demi ITC", 0, 18)); // NOI18N
        btnBackup.setForeground(new java.awt.Color(0, 0, 0));
        btnBackup.setText("BACKUP");
        btnBackup.setAutoscrolls(true);
        btnBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackupActionPerformed(evt);
            }
        });

        btnInventario.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnInventario.setText("KARDEX");
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        BtnCatalogo.setFont(new java.awt.Font("Elephant", 0, 14)); // NOI18N
        BtnCatalogo.setText("CATALOGO");
        BtnCatalogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatalogoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(LabelVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(jLabel41)
                .addGap(80, 80, 80))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVentas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBackup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tipo))
                    .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnCatalogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(LabelVendedor)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(tipo)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInventario)
                .addGap(67, 67, 67))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 210, 610));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setAutoscrolls(true);

        jPanel2.setBackground(new java.awt.Color(51, 51, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Código");
        jLabel3.setAutoscrolls(true);
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Descripción");
        jLabel4.setAutoscrolls(true);
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Cant");
        jLabel5.setAutoscrolls(true);
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Precio");
        jLabel6.setAutoscrolls(true);
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Stock Disponible");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCodigoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 102, 30));

        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtDescripcionVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 191, 30));

        txtCantidadVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVentaActionPerformed(evt);
            }
        });
        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCantidadVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 40, 30));

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioVentaActionPerformed(evt);
            }
        });
        jPanel2.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 80, 30));

        txtStockDisponible.setEditable(false);
        txtStockDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockDisponibleActionPerformed(evt);
            }
        });
        jPanel2.add(txtStockDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 79, 30));

        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIPCIÓN", "CANTIDAD", "PRECIO U.", "PRECIO TOTAL", "CÓDIGO"
            }
        ));
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(60);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(40);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 843, 220));

        btnEliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarventaActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminarventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 30, -1, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Dni/Ruc");
        jLabel8.setAutoscrolls(true);
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nombre:");
        jLabel9.setAutoscrolls(true);
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, -1, -1));

        txtRucVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtRucVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 116, 30));

        txtNombreClienteventa.setEditable(false);
        jPanel2.add(txtNombreClienteventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 169, 30));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/money.png"))); // NOI18N
        jLabel10.setText("Total a Pagar:");
        jLabel10.setAutoscrolls(true);
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 350, -1, -1));

        LabelTotal.setBackground(new java.awt.Color(255, 255, 255));
        LabelTotal.setForeground(new java.awt.Color(255, 255, 255));
        LabelTotal.setText("-----");
        jPanel2.add(LabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 350, -1, 30));
        jPanel2.add(txtIdCV, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, -1, -1));

        txtIdPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProActionPerformed(evt);
            }
        });
        jPanel2.add(txtIdPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, -1, -1));
        jPanel2.add(Midate, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 210, 30));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Seleccionar:");
        jLabel11.setAutoscrolls(true);
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        btnGenerarVenta.setBackground(new java.awt.Color(0, 0, 0));
        btnGenerarVenta.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 18)); // NOI18N
        btnGenerarVenta.setForeground(new java.awt.Color(0, 204, 0));
        btnGenerarVenta.setText("GENERAR VENTA");
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });
        jPanel2.add(btnGenerarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 350, -1, -1));

        btnGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/report.png"))); // NOI18N
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGraficar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 50, 30));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Cooper Black", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 51));
        jButton2.setText("VENTA EN CUOTAS");
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, 140, 30));

        jTabbedPane1.addTab("1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(51, 102, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Registro Cliente"));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Dni/Ruc:");

        txtDniCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniClienteActionPerformed(evt);
            }
        });
        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Nombre:");

        txtTelefonoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoClienteActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Télefono:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Dirección:");

        btnGuardarCliente.setText("GUARDAR");
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setText("EDITAR");
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setText("ELIMINAR");
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setText("AÑADIR/AGREGAR");
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI/RUC", "NOMBRE", "TELÉFONO", "DIRECCIÓN"
            }
        ));
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(0).setMinWidth(1);
            TableCliente.getColumnModel().getColumn(0).setPreferredWidth(1);
            TableCliente.getColumnModel().getColumn(0).setMaxWidth(1);
            TableCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        btnEvaluacionCrediticiaActionPerformed.setText("EVALUACION CREDITICA");
        btnEvaluacionCrediticiaActionPerformed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvaluacionCrediticiaActionPerformedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(43, 43, 43)
                        .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel13))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDirecionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(50, 50, 50)
                                .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(btnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(63, 63, 63)
                                        .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(46, 46, 46)
                                        .addComponent(btnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 22, Short.MAX_VALUE))))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(btnEvaluacionCrediticiaActionPerformed, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(16, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel14))
                    .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDirecionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarCliente)
                    .addComponent(btnGuardarCliente)
                    .addComponent(btnNuevoCliente))
                .addGap(29, 29, 29)
                .addComponent(btnEvaluacionCrediticiaActionPerformed)
                .addGap(30, 30, 30))
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, -10, 690, 430));

        jTabbedPane1.addTab("2", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(51, 51, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Proveedor"));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Ruc:");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Nombre:");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Teléfono:");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Dirección:");

        btnguardarProveedor.setText("GUARDAR");
        btnguardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarProveedorActionPerformed(evt);
            }
        });

        btnEditarProveedor.setText("EDITAR");
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setText("LIMPIAR");
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setText("ELIMINAR");
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELÉFONO", "DIRECCIÓN"
            }
        ));
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setMinWidth(1);
            TableProveedor.getColumnModel().getColumn(0).setPreferredWidth(1);
            TableProveedor.getColumnModel().getColumn(0).setMaxWidth(1);
            TableProveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            TableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableProveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(btnguardarProveedor)
                                .addGap(36, 36, 36))
                            .addComponent(btnEditarProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(btnEliminarProveedor))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(561, 561, 561))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(125, 125, 125)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addGap(32, 32, 32)
                                        .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(29, 29, 29)
                                        .addComponent(txtNombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(18, 18, 18)
                                .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtNombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65))
                    .addComponent(txtIdProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnguardarProveedor)
                            .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 860, 430));

        jTabbedPane1.addTab("3", jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 0, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCIÓN", "PROVEEDOR", "STOCK", "PRECIO", "MARCA"
            }
        ));
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProducto.getColumnModel().getColumn(3).setPreferredWidth(60);
            TableProducto.getColumnModel().getColumn(4).setPreferredWidth(40);
            TableProducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, 490, 370));
        jPanel5.add(txtIdproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 25, -1, -1));

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Producto"));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Código:");

        txtCodigoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Nombre:");

        txtDesPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesProActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Cantidad:");

        txtCantPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantProActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Precio:");

        txtPrecioPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioProActionPerformed(evt);
            }
        });
        txtPrecioPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProKeyTyped(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Proveedor:");

        cbxProveedorPro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProveedorProItemStateChanged(evt);
            }
        });
        cbxProveedorPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProveedorProActionPerformed(evt);
            }
        });

        btnGuardarpro.setText("GUARDAR");
        btnGuardarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarproActionPerformed(evt);
            }
        });

        btnEditarpro.setText("editar");
        btnEditarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarproActionPerformed(evt);
            }
        });

        btnEliminarPro.setText("ELIMINAR");
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });

        btnNuevoPro.setText("nuevo");
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Marca:");

        btnExcelPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo.png"))); // NOI18N
        btnExcelPro.setToolTipText("");
        btnExcelPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelProActionPerformed(evt);
            }
        });

        cbxMarcaPro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxMarcaPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMarcaProActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Imagen:");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Fecha:");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Lote:");

        txtCantidadLote1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadLote1ActionPerformed(evt);
            }
        });
        txtCantidadLote1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadLote1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadLote1KeyTyped(evt);
            }
        });

        btnImagenesProducto.setText("IMG");
        btnImagenesProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagenesProductoActionPerformed(evt);
            }
        });

        lblRutaImagen.setBackground(new java.awt.Color(255, 255, 255));
        lblRutaImagen.setForeground(new java.awt.Color(255, 255, 255));
        lblRutaImagen.setText("....");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel51))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(lblRutaImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnImagenesProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31))
                                    .addComponent(txtCantidadLote1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel25))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxMarcaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(txtDesPro, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCantPro, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel50))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxProveedorPro, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fechaCompraPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnExcelPro, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnGuardarpro)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarpro)
                        .addGap(33, 33, 33)
                        .addComponent(btnEliminarPro)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txtDesPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxMarcaPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24)
                        .addComponent(txtCantPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(cbxProveedorPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel50)
                                    .addComponent(lblRutaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel51)
                                    .addComponent(txtCantidadLote1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(fechaCompraPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnImagenesProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExcelPro, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardarpro)
                        .addComponent(btnEditarpro)
                        .addComponent(btnEliminarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevoPro)))
                .addGap(74, 74, 74))
        );

        jPanel5.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 590, 380));

        jTabbedPane1.addTab("4", jPanel5);

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVentas.setBackground(new java.awt.Color(0, 0, 0));
        TableVentas.setForeground(new java.awt.Color(255, 255, 102));
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL", "TIPO"
            }
        ));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 766, 220));

        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf.png"))); // NOI18N
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });
        jPanel6.add(btnPdfVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        txtIdVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdVentaActionPerformed(evt);
            }
        });
        jPanel6.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 46, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Historial Ventas");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 280, -1));

        btndevolucion.setFont(new java.awt.Font("Gill Sans Ultra Bold", 1, 24)); // NOI18N
        btndevolucion.setText("DEVOLUCIONES");
        jPanel6.add(btndevolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 250, -1));

        jTabbedPane1.addTab("5", jPanel6);

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel27.setText("RUC");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, -1));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel28.setText("NOMBRE");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, -1, -1));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel29.setText("TELÉFONO");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 170, -1, -1));

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setText("DIRECCIÓN");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, -1, -1));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel31.setText("MENSAJE");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, -1, -1));
        jPanel7.add(txtNombreConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 220, 30));
        jPanel7.add(txtTelefonoConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, 218, 30));
        jPanel7.add(txtDireccionConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 147, 30));
        jPanel7.add(txtMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 400, 30));

        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });
        jPanel7.add(btnActualizarConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 320, -1, 35));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText(" NOSOTROS");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        jPanel8.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 420, 310));
        jPanel7.add(txtIdConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 340, 24, -1));

        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Todo esto ira en el reporte de venta, como mensaje final de cada compra");
        jPanel7.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 420, 150));

        jTabbedPane1.addTab("6", jPanel7);

        jPanel12.setBackground(new java.awt.Color(0, 204, 204));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));

        jLabel33.setFont(new java.awt.Font("Elephant", 2, 18)); // NOI18N
        jLabel33.setText("REGISTRO");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 255));
        jLabel34.setText("Documento");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 255));
        jLabel35.setText("Password");

        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        btnIniciar.setBackground(new java.awt.Color(0, 0, 204));
        btnIniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciar.setText("Registrar");
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 255));
        jLabel36.setText("Nombre:");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 255));
        jLabel37.setText("Rol:");

        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Asistente" }));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel33))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel36)
                                .addComponent(jLabel35)
                                .addComponent(jLabel34)
                                .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                .addComponent(txtPass)
                                .addComponent(jLabel37)
                                .addComponent(txtNombre)
                                .addComponent(cbxRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addGap(2, 2, 2)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 280, 380));

        TableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Correo", "Rol"
            }
        ));
        jScrollPane6.setViewportView(TableUsuarios);
        if (TableUsuarios.getColumnModel().getColumnCount() > 0) {
            TableUsuarios.getColumnModel().getColumn(0).setMinWidth(1);
            TableUsuarios.getColumnModel().getColumn(0).setPreferredWidth(1);
            TableUsuarios.getColumnModel().getColumn(0).setMaxWidth(1);
        }

        jPanel12.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 540, 380));

        jTabbedPane1.addTab("7", jPanel12);

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.setForeground(new java.awt.Color(0, 0, 0));

        jLabel44.setBackground(new java.awt.Color(0, 0, 0));
        jLabel44.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(153, 0, 204));
        jLabel44.setText("BASE DE DATOS");

        btnrespaldar.setBackground(new java.awt.Color(0, 0, 0));
        btnrespaldar.setFont(new java.awt.Font("Dialog", 3, 24)); // NOI18N
        btnrespaldar.setForeground(new java.awt.Color(255, 0, 51));
        btnrespaldar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/android-chrome-192x192.png"))); // NOI18N
        btnrespaldar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnrespaldar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnrespaldar.setIconTextGap(2);
        btnrespaldar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrespaldarActionPerformed(evt);
            }
        });

        btnbackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/android-chrome-192x192.png"))); // NOI18N
        btnbackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbackupActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 255));
        jLabel47.setText("RESTAURAR BASE DE DATOS");

        jLabel48.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 255));
        jLabel48.setText("HACER BACKUP");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel44)
                        .addContainerGap())
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(btnbackup)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(104, 104, 104)
                                .addComponent(btnrespaldar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57))))))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(116, 116, 116)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(523, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel44)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel47)
                        .addGap(12, 12, 12)
                        .addComponent(btnrespaldar, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jLabel46))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btnbackup)))
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(156, 156, 156)
                    .addComponent(jLabel48)
                    .addContainerGap(252, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("tab8", jPanel14);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ENTRADA", "SALIDA", "", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab9", jPanel15);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 860, 460));

        jLabel38.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        getContentPane().add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 70, -1, -1));

        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        jLabel39.setText("jLabel39");
        getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        jLabel40.setText("SISTEMA DE VENTAS- CELLBOX");
        getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, -1, 32));

        jLabel21.setText("jLabel21");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
        LimpiarTable();
        ListarUsuarios();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTable();
        ListarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListarProductos();
        jTabbedPane1.setSelectedIndex(3);
        btnEditarpro.setEnabled(false);
        btnEliminarPro.setEnabled(false);
        btnGuardarpro.setEnabled(true);
        LimpiarProductos();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseClicked
        // TODO add your handling code here:
        cbxProveedorPro.removeAllItems();
        llenarProveedor();
    }//GEN-LAST:event_btnProductosMouseClicked

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        LimpiarProveedor();
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        LimpiarCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void btnBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackupActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(7);
        
    }//GEN-LAST:event_btnBackupActionPerformed

    private void btnbackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackupActionPerformed
        Backup dialog = new Backup();
        dialog.setLocationRelativeTo(this);  // opcional, lo centra respecto a tu ventana
        dialog.setVisible(true);
    }//GEN-LAST:event_btnbackupActionPerformed

    private void btnrespaldarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrespaldarActionPerformed
        Restaurar dlg = new Restaurar(this, true);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }//GEN-LAST:event_btnrespaldarActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if(txtNombre.getText().equals("") || txtCorreo.getText().equals("") || txtPass.getPassword().equals("")){
            JOptionPane.showMessageDialog(null, "Todo los campos son requeridos");
        }else{
            String correo = txtCorreo.getText();
            String pass = String.valueOf(txtPass.getPassword());
            String nom = txtNombre.getText();
            String rol = cbxRol.getSelectedItem().toString();
            lg.setNombre(nom);
            lg.setCorreo(correo);
            lg.setPass(pass);
            lg.setRol(rol);
            login.Registrar(lg);
            JOptionPane.showMessageDialog(null, "Usuario Registrado");
            LimpiarTable();
            ListarUsuarios();
            nuevoUsuario();
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText())) {
            conf.setRuc(txtRucConfig.getText());
            conf.setNombre(txtNombreConfig.getText());
            conf.setTelefono(txtTelefonoConfig.getText());
            conf.setDireccion(txtDireccionConfig.getText());
            conf.setMensaje(txtMensaje.getText());
            conf.setId(Integer.parseInt(txtIdConfig.getText()));
            proDao.ModificarDatos(conf);
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificado");
            ListarConfig();
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed

        if(txtIdVenta.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }else{
            v = Vdao.BuscarVenta(Integer.parseInt(txtIdVenta.getText()));
            Vdao.pdfV(v.getId(), v.getCliente(), v.getTotal(), v.getVendedor());
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        // TODO add your handling code here:
        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(TableVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnExcelProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelProActionPerformed
        // TODO add your handling code here:
        Excel.reporte();
    }//GEN-LAST:event_btnExcelProActionPerformed

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        // TODO add your handling code here:
        LimpiarProductos();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdproducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdproducto.getText());
                proDao.EliminarProductos(id);
                LimpiarTable();
                LimpiarProductos();
                ListarProductos();
                btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnEditarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarproActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdproducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtCodigoPro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPrecioPro.getText())||!"".equals(cbxMarcaPro.getSelectedItem())) {
                pro.setCodigo(txtCodigoPro.getText());
                pro.setNombre(txtDesPro.getText());
                pro.setMarca((String) cbxMarcaPro.getSelectedItem());
                Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
                pro.setProveedor(itemP.getId());
                pro.setStock(Integer.parseInt(txtCantPro.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
                pro.setId(Integer.parseInt(txtIdproducto.getText()));
                proDao.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                LimpiarTable();
                ListarProductos();
                LimpiarProductos();
                cbxProveedorPro.removeAllItems();
                llenarProveedor();
                btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarproActionPerformed

    private void btnGuardarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarproActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCodigoPro.getText()) && 
        !"".equals(txtDesPro.getText()) && 
        cbxProveedorPro.getSelectedItem() != null && 
        !"".equals(txtCantPro.getText()) && 
        !"".equals(txtPrecioPro.getText()) &&
        cbxMarcaPro.getSelectedItem() != null) {

        Productos pro = currentProducto;
        ProductosDao proDao = new ProductosDao();

        pro.setCodigo(txtCodigoPro.getText());
        pro.setNombre(txtDesPro.getText());
        pro.setMarca((String) cbxMarcaPro.getSelectedItem());

        Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
        pro.setProveedor(itemP.getId());

        pro.setStock(Integer.parseInt(txtCantPro.getText()));
        pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));

        // 📅 Fecha de compra
        Date fechaDate = fechaCompraPicker.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaCompra = dateFormat.format(fechaDate);
        pro.setFecha_compra(fechaCompra);

        // 🖼 Imagen
        // 🔢 Lote
        int ultimoLote = proDao.obtenerUltimoLote(pro.getCodigo());
        int nuevoLote = ultimoLote + 1;
        pro.setLote(nuevoLote);

        // Mostrar lote en interfaz
        txtCantidadLote1.setEditable(false);
        txtCantidadLote1.setText(String.valueOf(nuevoLote));

        // 🧾 Número de factura (opcional)
        int totalFacturas = proDao.contarFacturas();
        String facturaGenerada = String.format("FAC-%04d", totalFacturas + 1);
        pro.setNumero_factura(facturaGenerada);

        // ✅ Registrar producto
        if (proDao.RegistrarProductos(pro)) {
            JOptionPane.showMessageDialog(null, "Producto registrado con lote y factura");
             proDao.pdfCompra(pro);

            LimpiarTable();
            ListarProductos();
            LimpiarProductos();
            cbxProveedorPro.removeAllItems();
            llenarProveedor();
            btnEditarpro.setEnabled(false);
            btnEliminarPro.setEnabled(false);
            btnGuardarpro.setEnabled(true);
            currentProducto = new Productos();
        lblRutaImagen.setText(""); // limpia la ruta mostrada   
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto");
        }

    } else {
        JOptionPane.showMessageDialog(null, "Los campos están vacíos");
    }
    }//GEN-LAST:event_btnGuardarproActionPerformed

    private void cbxProveedorProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProveedorProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProActionPerformed

    private void cbxProveedorProItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProveedorProItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProItemStateChanged

    private void txtPrecioProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtPrecioProKeyTyped

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        // TODO add your handling code here:
        btnEditarpro.setEnabled(true);
        btnEliminarPro.setEnabled(true);
        btnGuardarpro.setEnabled(true);
        int fila = TableProducto.rowAtPoint(evt.getPoint());
        txtIdproducto.setText(TableProducto.getValueAt(fila, 0).toString());
        pro = proDao.BuscarId(Integer.parseInt(txtIdproducto.getText()));
        txtCodigoPro.setText(pro.getCodigo());
        txtDesPro.setText(pro.getNombre());
        txtCantPro.setText("" + pro.getStock());
        txtPrecioPro.setText("" + pro.getPrecio());
        cbxProveedorPro.setSelectedItem(new Combo(pro.getProveedor(), pro.getProveedorPro()));
    }//GEN-LAST:event_TableProductoMouseClicked

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        // TODO add your handling code here:
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        btnguardarProveedor.setEnabled(false);
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtNombreproveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarProveedor();
        btnEditarProveedor.setEnabled(false);
        btnEliminarProveedor.setEnabled(false);
        btnguardarProveedor.setEnabled(true);
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreproveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
                pr.setRuc(txtRucProveedor.getText());
                pr.setNombre(txtNombreproveedor.getText());
                pr.setTelefono(txtTelefonoProveedor.getText());
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                PrDao.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnguardarProveedor.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnguardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreproveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
            pr.setRuc(txtRucProveedor.getText());
            pr.setNombre(txtNombreproveedor.getText());
            pr.setTelefono(txtTelefonoProveedor.getText());
            pr.setDireccion(txtDireccionProveedor.getText());
            PrDao.RegistrarProveedor(pr);
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
            LimpiarTable();
            ListarProveedor();
            LimpiarProveedor();
            btnEditarProveedor.setEnabled(false);
            btnEliminarProveedor.setEnabled(false);
            btnguardarProveedor.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos esta vacios");
        }
    }//GEN-LAST:event_btnguardarProveedorActionPerformed

    private void btnEvaluacionCrediticiaActionPerformedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvaluacionCrediticiaActionPerformedActionPerformed
        try {
            // 1. Solicitar ingreso mensual
            String ingresoStr = JOptionPane.showInputDialog(this,
                "Ingrese el ingreso mensual del cliente:",
                "Evaluación Crediticia - Paso 1",
                JOptionPane.QUESTION_MESSAGE
            );
            if (ingresoStr == null) return;
            double ingresoMensual = Double.parseDouble(ingresoStr);
            if (ingresoMensual <= 0) throw new NumberFormatException();

            // 2. Solicitar total del producto
            String totalProductoStr = JOptionPane.showInputDialog(this,
                "Ingrese el total del producto o productos seleccionado:",
                "Evaluación Crediticia - Paso 2",
                JOptionPane.QUESTION_MESSAGE
            );
            if (totalProductoStr == null) return;
            double totalProducto = Double.parseDouble(totalProductoStr);
            if (totalProducto <= 0) throw new NumberFormatException();

            // 3. Solicitar cantidad de cuotas (máximo 18)
            int cuotas = 0;
            while (true) {
                String cuotasStr = JOptionPane.showInputDialog(this,
                    "¿En cuántas cuotas desea financiar? (Máximo 18 cuotas)",
                    "Evaluación Crediticia - Paso 3",
                    JOptionPane.QUESTION_MESSAGE
                );
                if (cuotasStr == null) return;
                try {
                    cuotas = Integer.parseInt(cuotasStr);
                    if (cuotas > 0 && cuotas <= 18) break;
                    else JOptionPane.showMessageDialog(this,
                        "Ingrese un número de cuotas válido (entre 1 y 18).",
                        "Cuotas inválidas",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Ingrese un número entero válido para cuotas.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            // 4. Determinar el porcentaje adicional según el número de cuotas
            double porcentajeExtra;
            if (cuotas >= 1 && cuotas <= 6) {
                porcentajeExtra = 0.05;
            } else if (cuotas >= 7 && cuotas <= 12) {
                porcentajeExtra = 0.10;
            } else { // 13 a 18
                porcentajeExtra = 0.15;
            }

            // 5. Calcular el total con el porcentaje extra
            double totalConInteres = totalProducto * (1 + porcentajeExtra);

            // 6. Calcular cuota mensual
            double cuotaMensual = totalConInteres / cuotas;

            // 7. Calcular 30% del ingreso mensual
            double maxCuotaPermitida = ingresoMensual * 0.3;

            // 8. Validar aprobación y mostrar resultados
            String resultado;
            int tipoMensaje;
            String detalleInteres = (int)(porcentajeExtra * 100) + "%";
            if (cuotaMensual > maxCuotaPermitida) {
                resultado = "<html><b>Crédito NO aprobado</b><br>"
                + "La cuota calculada (<b>S/ " + String.format("%.2f", cuotaMensual) + "</b>) "
                + "supera el 30% del ingreso mensual (<b>S/ " + String.format("%.2f", maxCuotaPermitida) + "</b>).<br>"
                + "Monto total financiado: S/ " + String.format("%.2f", totalConInteres) + " (incluye " + detalleInteres + " de interés)<br>"
                + "Número de cuotas: " + cuotas + "</html>";
                tipoMensaje = JOptionPane.WARNING_MESSAGE;
            } else {
                resultado = "<html><b>Crédito Aprobado</b><br>"
                + "La cuota mensual será de: <b>S/ " + String.format("%.2f", cuotaMensual) + "</b><br>"
                + "Monto total financiado: S/ " + String.format("%.2f", totalConInteres) + " (incluye " + detalleInteres + " de interés)<br>"
                + "Número de cuotas: " + cuotas + "<br>"
                + "Límite máximo de cuota permitido: S/ " + String.format("%.2f", maxCuotaPermitida) + "</html>";
                tipoMensaje = JOptionPane.INFORMATION_MESSAGE;
            }

            JOptionPane.showMessageDialog(this,
                resultado,
                "Resultado de Evaluación Crediticia",
                tipoMensaje
            );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "<html><b>Error:</b><br>Debe ingresar valores numéricos positivos y válidos.</html>",
                "Datos Incorrectos",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnEvaluacionCrediticiaActionPerformedActionPerformed

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        // TODO add your handling code here:
        btnEditarCliente.setEnabled(true);
        btnEliminarCliente.setEnabled(true);
        btnGuardarCliente.setEnabled(false);
        int fila = TableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtDniCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(TableCliente.getValueAt(fila, 3).toString());
        txtDirecionCliente.setText(TableCliente.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        LimpiarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnGuardarCliente.setEnabled(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                client.EliminarCliente(id);
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText())) {
                cl.setDni(txtDniCliente.getText());
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDirecionCliente.getText());
                cl.setId(Integer.parseInt(txtIdCliente.getText()));
                client.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Modificado");
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDirecionCliente.getText())) {
            cl.setDni(txtDniCliente.getText());
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(txtTelefonoCliente.getText());
            cl.setDireccion(txtDirecionCliente.getText());
            client.RegistrarCliente(cl);
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
            LimpiarTable();
            LimpiarCliente();
            ListarCliente();
            btnEditarCliente.setEnabled(false);
            btnEliminarCliente.setEnabled(false);
            btnGuardarCliente.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void txtTelefonoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoClienteActionPerformed

    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniClienteKeyTyped

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        // TODO add your handling code here:
        Grafico.Graficar(fechaActual);
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        String[] opciones = {"BOLETA", "FACTURA"};
    int seleccion = JOptionPane.showOptionDialog(
        this,
        "Seleccione el tipo de comprobante:",
        "Tipo de Venta",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        opciones,
        opciones[0]
    );
    if (seleccion < 0) return;

    // 1) Insertar la cabecera de la venta
    RegistrarVenta();
    int ventaId = Vdao.IdVenta();

    // 2) Calcular y guardar el número de comprobante
    String tipo    = (seleccion == 0) ? "BOLETA" : "FACTURA";
    String nroComp = tipo + "-" + ventaId;
    try {
        Vdao.actualizarNumeroComprobante(ventaId, nroComp);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "No se pudo guardar número de comprobante:\n" + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 3) Por cada ítem en la tabla, proceso FIFO con ese nroComp
    for (int i = 0; i < TableVenta.getRowCount(); i++) {
        String codigo    = TableVenta.getValueAt(i, 5).toString();
        int cantidad     = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
        Date fechaVenta  = new Date();
        try {
            Vdao.registrarSalidaFIFO(codigo, ventaId, fechaVenta, nroComp, cantidad);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al procesar FIFO para " + codigo + ":\n" + ex.getMessage(),
                "Error de Venta", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // 4) Generar PDF
    TotalPagar();
    double total     = Totalpagar;
    int clienteId    = Integer.parseInt(txtIdCV.getText());
    String usuario   = LabelVendedor.getText();
    Vdao.pdfV(ventaId, clienteId, total, usuario);

    // 5) Refrescar historial
    ((DefaultTableModel) TableVentas.getModel()).setRowCount(0);
    ListarVentas();
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void txtRucVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucVentaKeyTyped

    private void txtRucVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtRucVenta.getText())) {
                int dni = Integer.parseInt(txtRucVenta.getText());
                cl = client.Buscarcliente(dni);
                if (cl.getNombre() != null) {
                    txtNombreClienteventa.setText("" + cl.getNombre());
                    txtIdCV.setText("" + cl.getId());
                } else {
                    txtRucVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtRucVentaKeyPressed

    private void btnEliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarventaActionPerformed
        // TODO add your handling code here:
        modelo = (DefaultTableModel) TableVenta.getModel();
        modelo.removeRow(TableVenta.getSelectedRow());
        TotalPagar();
        txtCodigoVenta.requestFocus();
    }//GEN-LAST:event_btnEliminarventaActionPerformed

    private void txtPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioVentaActionPerformed

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String sCant  = txtCantidadVenta.getText().trim();
        String sPrecio= txtPrecioVenta.getText().trim();

        // 1) Valido que cantidad y precio no estén vacíos
        if (sCant.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cantidad.", "Atención", JOptionPane.WARNING_MESSAGE);
            txtCantidadVenta.requestFocus();
            return;
        }
        if (sPrecio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta el precio del producto.", "Atención", JOptionPane.WARNING_MESSAGE);
            txtPrecioVenta.requestFocus();
            return;
        }

        try {
            int id          = Integer.parseInt(txtIdPro.getText().trim());
            String desc     = txtDescripcionVenta.getText().trim();
            int cant        = Integer.parseInt(sCant);
            double precio   = Double.parseDouble(sPrecio);
            double total    = cant * precio;
            int stock       = Integer.parseInt(txtStockDisponible.getText().trim());

            if (stock < cant) {
                JOptionPane.showMessageDialog(this,
                    "Stock no disponible.\nStock disponible: " + stock,
                    "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DefaultTableModel tmp = (DefaultTableModel) TableVenta.getModel();
            // Evito duplicados
            for (int i = 0; i < tmp.getRowCount(); i++) {
                if (tmp.getValueAt(i, 1).equals(desc)) {
                    JOptionPane.showMessageDialog(this,
                        "El producto ya está registrado",
                        "Atención", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            // Agrego la fila
            Object[] fila = new Object[6];
            fila[0] = id;
            fila[1] = desc;
            fila[2] = cant;
            fila[3] = precio;
            fila[4] = total;
            fila[5] = txtCodigoVenta.getText().trim();
            tmp.addRow(fila);

            // Actualizo totales y limpio
            TotalPagar();
            LimparVenta();
            txtCodigoVenta.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Formato numérico inválido:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        if (!"".equals(txtCodigoVenta.getText())) {
            String cod = txtCodigoVenta.getText();
            pro = proDao.BuscarPro(cod);
            if (pro.getNombre() != null) {
                txtIdPro.setText("" + pro.getId());
                txtDescripcionVenta.setText(pro.getNombre());
                txtPrecioVenta.setText("" + pro.getPrecio());
                
                // ← Aquí usamos el método que suma todos los lotes
                int stockTotal = 0;
                try {
                    stockTotal = proDao.obtenerStockTotal(cod);
                } catch (SQLException ex) {
                    Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtStockDisponible.setText("" + stockTotal);
                
                txtCantidadVenta.requestFocus();
            } else {
                LimparVenta();
                txtCodigoVenta.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
            txtCodigoVenta.requestFocus();
        }
    }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtDniClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniClienteActionPerformed

    private void txtCantidadVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaActionPerformed

    private void txtCantidadLote1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadLote1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadLote1ActionPerformed

    private void txtCantidadLote1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadLote1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadLote1KeyPressed

    private void txtCantidadLote1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadLote1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadLote1KeyTyped

    private void txtIdProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProActionPerformed

    private void btnImagenesProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagenesProductoActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Selecciona una imagen");

    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        lblRutaImagen.setText(file.getAbsolutePath());  // opcional

        try {
            byte[] imagenBytes = java.nio.file.Files.readAllBytes(file.toPath());
            // 3) Guarda los bytes en el objeto inicializado
            currentProducto.setImagen(imagenBytes);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "No se pudo leer la imagen:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }
    }//GEN-LAST:event_btnImagenesProductoActionPerformed

    private void cbxMarcaProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMarcaProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxMarcaProActionPerformed

    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void txtDesProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesProActionPerformed

    private void txtCantProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantProActionPerformed

    private void txtPrecioProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioProActionPerformed

    private void BtnCatalogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatalogoActionPerformed
         Catalogo dlg = new Catalogo(this, true);
    // Ajusta tamaño
    dlg.setSize(900, 600);
    // Centra respecto a la ventana actual
    dlg.setLocationRelativeTo(this);
    // Muestra el diálogo (modal)
    dlg.setVisible(true);
    }//GEN-LAST:event_BtnCatalogoActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
         KARDEX kardexDialog = new KARDEX(this, true);
    kardexDialog.setLocationRelativeTo(this);  // centra sobre la ventana
    kardexDialog.setVisible(true);
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void txtStockDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockDisponibleActionPerformed

    private void txtIdVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdVentaActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCatalogo;
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JLabel LabelVendedor;
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableUsuarios;
    private javax.swing.JTable TableVenta;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnBackup;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEditarpro;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarventa;
    private javax.swing.JButton btnEvaluacionCrediticiaActionPerformed;
    private javax.swing.JButton btnExcelPro;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarpro;
    private javax.swing.JButton btnImagenesProducto;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnVentas;
    private javax.swing.JToggleButton btnbackup;
    private javax.swing.JButton btndevolucion;
    private javax.swing.JButton btnguardarProveedor;
    private javax.swing.JToggleButton btnrespaldar;
    private javax.swing.JComboBox<String> cbxMarcaPro;
    private javax.swing.JComboBox<Object> cbxProveedorPro;
    private javax.swing.JComboBox<String> cbxRol;
    private com.toedter.calendar.JDateChooser fechaCompraPicker;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblRutaImagen;
    private javax.swing.JLabel tipo;
    private javax.swing.JTextField txtCantPro;
    private javax.swing.JTextField txtCantidadLote1;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDesPro;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDirecionCliente;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtIdCV;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdPro;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdproducto;
    private javax.swing.JTextField txtMensaje;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteventa;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreproveedor;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtRucVenta;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables
    private void LimpiarCliente() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDirecionCliente.setText("");
    }

    private void LimpiarProveedor() {
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtNombreproveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
    }

    private void LimpiarProductos() {
        txtIdPro.setText("");
        txtCodigoPro.setText("");
        cbxProveedorPro.setSelectedItem(null);
        txtDesPro.setText("");
        txtCantPro.setText("");
        txtPrecioPro.setText("");
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }

    private void LimparVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtIdVenta.setText("");
    }

    private void RegistrarVenta() {
        int cliente = Integer.parseInt(txtIdCV.getText());
        String vendedor = LabelVendedor.getText();
        double monto = Totalpagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        Vdao.RegistrarVenta(v);
    }

    private void RegistrarDetalle() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            Vdao.RegistrarDetalle(Dv);

        }
        int cliente = Integer.parseInt(txtIdCV.getText());
        Vdao.pdfV(id, cliente, Totalpagar, LabelVendedor.getText());
    }

    private void ActualizarStock() {
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int id = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            pro = proDao.BuscarId(id);
            int StockActual = pro.getStock() - cant;
            Vdao.ActualizarStock(StockActual, id);

        }
    }

    private void LimpiarTableVenta() {
        tmp = (DefaultTableModel) TableVenta.getModel();
        int fila = TableVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    private void LimpiarClienteventa() {
        txtRucVenta.setText("");
        txtNombreClienteventa.setText("");
        txtIdCV.setText("");
    }
    private void nuevoUsuario(){
        txtNombre.setText("");
        txtCorreo.setText("");
        txtPass.setText("");
    }
    private void llenarProveedor(){
        List<Proveedor> lista = PrDao.ListarProveedor();
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            cbxProveedorPro.addItem(new Combo(id, nombre));
        }
    }
     private void inicializarMarca() {
    // 1) Hazlo editable para que monte su propio editor interno
    cbxMarcaPro.setEditable(true);

    // 2) Carga los ítems desde la BD
    List<String> marcas = proDao.listarMarcas();
    cbxMarcaPro.removeAllItems();
    for (String m : marcas) {
        cbxMarcaPro.addItem(m);
    }
}
}
