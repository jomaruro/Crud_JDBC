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
import modelo.Detalle;
import modelo.InterfazBaseDatos;
import modelo.OracleBaseDatos;

/**
 *
 * @author ruiz
 */
public class DBDetalle {

    private InterfazBaseDatos iBd;

    public DBDetalle() {
        try {
            iBd = new OracleBaseDatos();
        } catch (Exception e) {
            System.err.println("DBDetalle => " + e.getMessage());
        }
    }    
    public int dameOrden(int numFactura, String fechaFactura) {
        int orden = 0;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = iBd.getConnection();
            String consulta = "SELECT MAX(ORDEN) AS MAXIMO FROM DETALLE WHERE NUMERO = ? AND FECHA LIKE ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, numFactura);
            ps.setString(2, fechaFactura);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orden = rs.getInt("MAXIMO");
            }
        } catch (SQLException e) {
            System.err.println("DBDetalle - dameOrden - Error al leer la tabla DETALLE => " + e.getMessage());
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
        return orden;
    }
    
    public boolean insertaDetalle(Detalle detalle) {
        boolean exito = false;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = iBd.getConnection();
            String consulta = "INSERT INTO DETALLE (NUMERO, FECHA, ORDEN, ID_ARTICULO, BULTOS, CANTIDAD, PRECIO, FECHA_CREACION) VALUES (?,?,?,?,?,?,?,SYSDATE)";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, detalle.getNumero());
            ps.setString(2, detalle.getFecha());
            ps.setInt(3, detalle.getOrden());
            ps.setInt(4, detalle.getId_articulo());
            ps.setInt(5, detalle.getBultos());
            ps.setInt(6, detalle.getCantidad());
            ps.setDouble(7, detalle.getPrecio());
            ps.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("DBDetalle - insertaDetalle - Error al insertar en la tabla DETALLE => " + e.getMessage());
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
        System.err.println("DBDetalle - insertaDetalle - DETALLE => " + detalle.toString());
        return exito;
    }
    
    public boolean actualizaDetalle(Detalle detalle) {
        boolean exito = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "UPDATE DETALLE SET ID_ARTICULO = ?, BULTOS = ?, CANTIDAD = ?, "
                    + "PRECIO = ?, FECHA_MODIFICACION = SYSDATE WHERE NUMERO = ? AND FECHA = ? AND ORDEN = ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, detalle.getId_articulo());
            ps.setInt(2, detalle.getBultos());
            ps.setInt(3, detalle.getCantidad());            
            ps.setDouble(4, detalle.getPrecio());            
            ps.setInt(5, detalle.getNumero());
            ps.setString(6, detalle.getFecha());
            ps.setInt(7, detalle.getOrden());
            ps.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("DBDetalle - actualizaDetalle - Error al actualizar la tabla DETALLE => " + e.getMessage());
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
        System.err.println("DBDetalle - actualizaDetalle - DETALLE => " + detalle.toString());
        return exito;
    }
    
    public boolean borraDetalle(int numero, String fecha, int orden) {
        boolean exito = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "DELETE FROM DETALLE WHERE NUMERO = ? AND FECHA = ? AND ORDEN = ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(5, numero);
            ps.setString(6, fecha);
            ps.setInt(7, orden);
            ps.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("DBDetalle - borraDetalle - Error al actualizar la tabla DETALLE => " + e.getMessage());
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

    public ArrayList<Detalle> dameDetalles(int numero, String fecha) {
        ArrayList<Detalle> lstDetalles = new ArrayList<>();
        Detalle detalle = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM DETALLE WHERE NUMERO = ? AND FECHA = ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, numero);
            ps.setString(2, fecha);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                detalle = new Detalle();
                detalle.setNumero(rs.getInt("NUMERO"));
                detalle.setFecha(rs.getString("FECHA"));
                detalle.setOrden(rs.getInt("ORDEN"));
                detalle.setId_articulo(rs.getInt("ID_ARTICULO"));
                detalle.setBultos(rs.getInt("BULTOS"));
                detalle.setCantidad(rs.getInt("CANTIDAD"));
                detalle.setPrecio(rs.getDouble("PRECIO"));
                lstDetalles.add(detalle);
            }
            
        } catch (SQLException e) {
            System.err.println("DBDetalle - dameDetalles - Error al leer la tabla DETALLE => " + e.getMessage());
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
        return lstDetalles;
    }
    
    public double dameImporteFactura(int numFactura, String fechaFactura) {
        double importe = 0;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = iBd.getConnection();
            String consulta = "SELECT SUM(CANTIDAD * PRECIO) AS IMPORTE FROM DETALLE WHERE NUMERO = ? AND FECHA LIKE ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, numFactura);
            ps.setString(2, fechaFactura);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                importe = rs.getDouble("IMPORTE");
            }
        } catch (SQLException e) {
            System.err.println("DBDetalle - dameImporte - Error al leer la tabla DETALLE => " + e.getMessage());
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
        System.out.println("DBDetalle - dameImporteFactura - numero -> " + numFactura + " - fecha -> " + fechaFactura + " - importe => " + importe);
        return importe;
    }
}
