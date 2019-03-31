package modelo;

import java.io.Serializable;
import java.util.Date;

public class Detalle implements Serializable {

    private int numero;
    private String fecha;
    private int orden;
    private int id_articulo;
    private int bultos;
    private int cantidad;
    private double precio;
    private Date fechaCreacion;
    private Date fechaModificacion;

    public Detalle() {
    }

    public Detalle(int numero, String fecha, int orden) {
        this.numero = numero;
        this.fecha = fecha;
        this.orden = orden;
    }

    public Detalle(int numero, String fecha, int orden, int id_articulo, int bultos, int cantidad, double precio, Date fechaCreacion, Date fechaModificacion) {
        this.numero = numero;
        this.fecha = fecha;
        this.orden = orden;
        this.id_articulo = id_articulo;
        this.bultos = bultos;
        this.cantidad = cantidad;
        this.precio = precio;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(int id_articulo) {
        this.id_articulo = id_articulo;
    }

    public int getBultos() {
        return bultos;
    }

    public void setBultos(int bultos) {
        this.bultos = bultos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString() {
        return "Detalle{" + "numero=" + numero + ", fecha=" + fecha + ", orden=" + orden + ", id_articulo=" + id_articulo + ", bultos=" + bultos + ", cantidad=" + cantidad + ", precio=" + precio + ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + '}';
    }

    
}
