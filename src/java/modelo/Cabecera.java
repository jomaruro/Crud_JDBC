package modelo;

import java.io.Serializable;
import java.util.Date;

public class Cabecera implements Serializable {

    private int numero;
    private String fecha;
    private int id_usuario;
    private int id_cliente;
    private Date fechaCreacion;
    private Date fechaModificacion;

    public Cabecera() {
    }

    public Cabecera(int numero, String fecha) {
        this.numero = numero;
        this.fecha = fecha;
    }

    public Cabecera(int numero, String fecha, int id_usuario, int id_cliente, Date fechaCreacion, Date fechaModificacion) {
        this.numero = numero;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
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

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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
        return "Cabecera{" + "numero=" + numero + ", fecha=" + fecha + ", id_usuario=" + id_usuario + ", id_cliente=" + id_cliente + ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + '}';
    }

    
}
