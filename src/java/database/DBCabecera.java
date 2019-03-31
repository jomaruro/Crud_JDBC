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
import java.util.List;
import modelo.Cabecera;
import modelo.InterfazBaseDatos;
import modelo.OracleBaseDatos;
import modelo.Usuario;

/**
 *
 * @author ruiz
 */
public class DBCabecera {

    private InterfazBaseDatos iBd;

    public DBCabecera() {
        try {
            iBd = new OracleBaseDatos();
        } catch (Exception e) {
            System.err.println("DBCabecera => " + e.getMessage());
        }
    }
    
    public List<Cabecera> listaFacturas(String hoy, Usuario usuario) {
        ArrayList<Cabecera> lstCabeceras = new ArrayList<>();
        Cabecera cabecera;
        Connection con = null;
        PreparedStatement ps = null;
        String consulta;
        try {
            con = iBd.getConnection();
            if (usuario.getNivel().equals("ALTO")) {
                consulta = "SELECT * FROM CABECERA WHERE FECHA = ?";
                ps = con.prepareStatement(consulta);
                ps.setString(1, hoy);
            } else {
                consulta = "SELECT * FROM CABECERA WHERE FECHA = ? AND ID_USUARIO = ?";
                ps = con.prepareStatement(consulta);
                ps.setString(1, hoy);
                ps.setInt(2, usuario.getIdUsuario());
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cabecera = new Cabecera();
                cabecera.setNumero(rs.getInt("NUMERO"));
                cabecera.setFecha(rs.getString("FECHA"));
                cabecera.setId_usuario(rs.getInt("ID_USUARIO"));
                cabecera.setId_cliente(rs.getInt("ID_CLIENTE"));
                cabecera.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                cabecera.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
                lstCabeceras.add(cabecera);
            }
        } catch (SQLException e) {
            System.err.println("DBCabecera - listaFacturas - Error al leer la tabla CABECERA => " + e.getMessage());
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
        return lstCabeceras;
    }

    public List<Cabecera> listaHistorico() {
        ArrayList<Cabecera> lstCabeceras = new ArrayList<>();
        Cabecera cabecera;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM CABECERA ORDER BY TO_DATE(FECHA, 'dd/mm/yyyy') DESC";
            ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cabecera = new Cabecera();
                cabecera.setNumero(rs.getInt("NUMERO"));
                cabecera.setFecha(rs.getString("FECHA"));
                cabecera.setId_usuario(rs.getInt("ID_USUARIO"));
                cabecera.setId_cliente(rs.getInt("ID_CLIENTE"));
                cabecera.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                cabecera.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
                lstCabeceras.add(cabecera);
            }
        } catch (SQLException e) {
            System.err.println("DBCabecera - listaHistorico - Error al leer la tabla CABECERA => " + e.getMessage());
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
        return lstCabeceras;
    }

    public int dameNumFactura() {
        int numFactura = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT MAX(NUMERO) AS MAXIMO FROM CABECERA WHERE EXTRACT(YEAR FROM FECHA_CREACION) LIKE EXTRACT(YEAR FROM SYSDATE)";
            ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numFactura = rs.getInt("MAXIMO");
            }
        } catch (SQLException e) {
            System.err.println("DBCabecera - dameNumFactura - Error al leer la tabla CABECERA => " + e.getMessage());
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
        return numFactura;
    }
    
    public boolean insertaCabecera(Cabecera cabecera) {
        boolean exito = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "INSERT INTO CABECERA (NUMERO, FECHA, ID_USUARIO, ID_CLIENTE, FECHA_CREACION) VALUES (?,?,?,?,SYSDATE)";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, cabecera.getNumero());
            ps.setString(2, cabecera.getFecha());
            ps.setInt(3, cabecera.getId_usuario());
            ps.setInt(4, cabecera.getId_cliente());
            ps.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("DBCabecera - insertaCabecera - Error al insertar en la tabla CABECERA => " + e.getMessage());
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
    
    public Cabecera dameFactura(int numero, String fecha) {
        Cabecera cabecera = new Cabecera();

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM CABECERA WHERE NUMERO = ? AND EXTRACT(YEAR FROM FECHA_CREACION) LIKE EXTRACT(YEAR FROM SYSDATE)";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cabecera = new Cabecera();
                cabecera.setNumero(rs.getInt("NUMERO"));
                cabecera.setFecha(rs.getString("FECHA"));
                cabecera.setId_usuario(rs.getInt("ID_USUARIO"));
                cabecera.setId_cliente(rs.getInt("ID_CLIENTE"));
                cabecera.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                cabecera.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
            }
        } catch (SQLException e) {
            System.err.println("DBCabecera - dameFactura - Error al leer la tabla CABECERA => " + e.getMessage());
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
        return cabecera;
    }
}
