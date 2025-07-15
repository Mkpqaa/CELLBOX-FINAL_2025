
package Modelo;


public class Productos {
    private int id;
    private String codigo;
    private String nombre;
    private String marca;
    private int proveedor;
    private String proveedorPro;
    private int stock;
    private double precio;
    private byte[] imagen;
    private String fecha_compra;
    private int lote;
    private String numero_factura;
    
    
    
    public Productos(){
        
    }

    public Productos(int id, String codigo, String nombre, String marca, int proveedor, String proveedorPro, int stock, double precio) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.proveedor = proveedor;
        this.proveedorPro = proveedorPro;
        this.stock = stock;
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public byte[] getImagen() {
    return imagen;
    }
    
    public void setImagen(byte[] imagen) {
    this.imagen = imagen;
    }
    
    public String getFecha_compra() {
    return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
    this.fecha_compra = fecha_compra;
    }
    
    public int getLote() {
    return lote;
    }

    public void setLote(int lote) {
    this.lote = lote;
    }

    public String getNumero_factura() {
    return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
    this.numero_factura = numero_factura;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    public String getProveedorPro() {
        return proveedorPro;
    }

    public void setProveedorPro(String proveedorPro) {
        this.proveedorPro = proveedorPro;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

   
}
