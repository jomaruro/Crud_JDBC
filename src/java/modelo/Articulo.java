package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Articulo implements Serializable {


     private int idArticulo;
     private String nombre;
     private double precio;
     private Date fechaCreacion;
     private Date fechaModificacion;

    public Articulo() {
    }

	
    public Articulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }
    public Articulo(int idArticulo, String nombre, double precio, Date fechaCreacion, Date fechaModificacion, Set detalles) {
       this.idArticulo = idArticulo;
       this.nombre = nombre;
       this.precio = precio;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
   
}
