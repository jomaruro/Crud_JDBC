package modelo;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {

    private int idUsuario;
    private String nombre;
    private String clave;
    private String nivel;
    private Date fechaCreacion;
    private Date fechaModificacion;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    public Usuario(int idUsuario, String nombre, String clave, String nivel, Date fechaCreacion, Date fechaModificacion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.clave = clave;
        this.nivel = nivel;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public int getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNivel() {
        return this.nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
