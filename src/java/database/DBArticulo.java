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
import java.util.ArrayList;
import modelo.Articulo;
import modelo.InterfazBaseDatos;
import modelo.OracleBaseDatos;

/**
 *
 * @author ruiz
 */
public class DBArticulo {

    private InterfazBaseDatos iBd;

    public DBArticulo() {
        try {
            iBd = new OracleBaseDatos();
        } catch (Exception e) {
            System.err.println("DBArticulo => " + e.getMessage());
        }
    }
        
    public ArrayList<Articulo> listaArticulos() {
        ArrayList<Articulo> lstArticulos = new ArrayList<>();
        Articulo articulo;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM ARTICULO ORDER BY NOMBRE";
            ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articulo = new Articulo();
                articulo.setIdArticulo(rs.getInt("ID_ARTICULO"));
                articulo.setNombre(rs.getString("NOMBRE"));
                articulo.setPrecio(rs.getDouble("PRECIO"));
                articulo.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                articulo.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
                lstArticulos.add(articulo);
            }
        } catch (SQLException e) {
            System.err.println("DBArticulo - listaArticulos - Error al leer la tabla Articulos => " + e.getMessage());
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
        return lstArticulos;
    }
    
    public int dameIdArticulo() {
        Connection con = null;
        int idEquipo = 0;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT MAX(ID_ARTICULO) AS MAXIMO FROM ARTICULO";
            ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idEquipo = rs.getInt("MAXIMO");
            }
        } catch (SQLException e) {
            System.err.println("DBArticulo - dameIdArticulo - Error al leer la tabla ARTICULO => " + e.getMessage());
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
        return idEquipo;
    }

    public boolean insertaArticulo(Articulo articulo) {
        boolean exito = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "INSERT INTO ARTICULO (ID_ARTICULO, NOMBRE, PRECIO, FECHA_CREACION) VALUES (?,?,?,SYSDATE)";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, articulo.getIdArticulo());
            ps.setString(2, articulo.getNombre());
            ps.setDouble(3, articulo.getPrecio());
            ps.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("DBArticulo - insertaArticulo - Error al insertar en la tabla ARTICULO => " + e.getMessage());
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
        return exito;
    }

    public boolean actualizaArticulo(Articulo articulo) {
        boolean exito = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "UPDATE ARTICULO SET NOMBRE = ?, PRECIO = ?, FECHA_MODIFICACION = SYSDATE WHERE ID_ARTICULO = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, articulo.getNombre());
            ps.setDouble(2, articulo.getPrecio());
            ps.setInt(3, articulo.getIdArticulo());
            ps.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("DBArticulo - actualizaArticulo - Error al actualizar la tabla ARTICULO => " + e.getMessage());
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
        return exito;
    }

    public double damePrecio(int idArticulo) {
        double precio = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT PRECIO FROM ARTICULO WHERE ID_ARTICULO = ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, idArticulo);
//            ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                precio = rs.getDouble("PRECIO");
            }
        } catch (SQLException e) {
            System.err.println("DBArticulo - damePrecio - Error al leer la tabla ARTICULO => " + e.getMessage());
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
        return precio;
    }

    public Articulo dameArticulo(int idArticulo) {
        Articulo articulo = new Articulo();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM ARTICULO WHERE ID_ARTICULO = ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, idArticulo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articulo.setIdArticulo(rs.getInt("ID_ARTICULO"));
                articulo.setNombre(rs.getString("NOMBRE"));
                articulo.setPrecio(rs.getDouble("PRECIO"));
                articulo.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                articulo.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
            }
        } catch (SQLException e) {
            System.err.println("DBArticulo - dameArticulo - Error al leer la tabla ARTICULO => " + e.getMessage());
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
        return articulo;
    }
}
