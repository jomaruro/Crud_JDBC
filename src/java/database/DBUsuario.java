/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.InterfazBaseDatos;
import modelo.OracleBaseDatos;
import modelo.Usuario;

/**
 *
 * @author ruiz
 */
public class DBUsuario {

    private InterfazBaseDatos iBd;

    public DBUsuario() {
        try {
            iBd = new OracleBaseDatos();
        } catch (Exception e) {
            System.err.println("DBUsuario => " + e.getMessage());
        }
    }
    
    public Usuario dameUsuario(String nombre, String clave) {
        Usuario usuario = new Usuario();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM USUARIO WHERE NOMBRE LIKE ? AND CLAVE LIKE ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, nombre);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                usuario.setNombre(rs.getString("NOMBRE"));
                usuario.setClave(rs.getString("CLAVE"));
                usuario.setNivel(rs.getString("NIVEL"));
                usuario.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                usuario.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
            }
        } catch (SQLException e) {
            System.err.println("Error al leer la tabla USUARIO => " + e.getMessage());
        } finally {
            try { // cierro la conexion con la base de datos
                if (con != null && !con.isClosed()) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la base de datos => " + e.getMessage());
            }            
        }
        return usuario;
    }
    
    public Usuario dameUsuario(int idUsuario) {
        Usuario usuario = new Usuario();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM USUARIO WHERE ID_USUARIO = ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                usuario.setNombre(rs.getString("NOMBRE"));
                usuario.setClave(rs.getString("CLAVE"));
                usuario.setNivel(rs.getString("NIVEL"));
                usuario.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                usuario.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
            }
        } catch (SQLException e) {
            System.err.println("Error al leer la tabla USUARIO => " + e.getMessage());
        } finally {
            try { // cierro la conexion con la base de datos
                if (con != null && !con.isClosed()) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la base de datos => " + e.getMessage());
            }            
        }
        return usuario;
    }
}
