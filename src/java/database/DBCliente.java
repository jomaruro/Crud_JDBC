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
import modelo.Cliente;
import modelo.InterfazBaseDatos;
import modelo.OracleBaseDatos;

/**
 *
 * @author ruiz
 */
public class DBCliente {

    private InterfazBaseDatos iBd;

    public DBCliente() {
        try {
            iBd = new OracleBaseDatos();
        } catch (Exception e) {
            System.err.println("DBCliente => " + e.getMessage());
        }
    }
    
    public List<Cliente> listaClientes() {
        ArrayList<Cliente> lstArticulos = new ArrayList<>();
        Cliente cliente;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM CLIENTE ORDER BY NOMBRE";
            ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("ID_CLIENTE"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setCif(rs.getString("CIF"));
                cliente.setDireccion(rs.getString("DIRECCION"));
                cliente.setLocalidad(rs.getString("LOCALIDAD"));
                cliente.setProvincia(rs.getString("PROVINCIA"));
                cliente.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                cliente.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
                lstArticulos.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("DBCliente - listaClientes - Error al leer la tabla CLIENTE => " + e.getMessage());
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
    
    public int dameIdCliente() {
        Connection con = null;
        int idCliente = 0;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT MAX(ID_CLIENTE) AS MAXIMO FROM CLIENTE";
            ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idCliente = rs.getInt("MAXIMO");
            }
        } catch (SQLException e) {
            System.err.println("DBCliente - dameIdCliente - Error al leer la tabla CLIENTE => " + e.getMessage());
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
        System.out.println("DBCliente - dameIdCliente - id -> " + idCliente);
        return idCliente;
    }
    
    public boolean insertaCliente(Cliente cliente) {
        boolean exito = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "INSERT INTO CLIENTE (ID_CLIENTE, NOMBRE, CIF, DIRECCION, LOCALIDAD, PROVINCIA, FECHA_CREACION) VALUES (?,?,?,?,?,?,SYSDATE)";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getCif());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getLocalidad());
            ps.setString(6, cliente.getProvincia());
            ps.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("Error al insertar en la tabla CLIENTE => " + e.getMessage());
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
    
    public boolean actualizaCliente(Cliente cliente) {
        boolean exito = false;

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "UPDATE CLIENTE SET NOMBRE = ?, CIF = ?, DIRECCION = ?, LOCALIDAD = ?, PROVINCIA = ?, FECHA_MODIFICACION = SYSDATE WHERE ID_CLIENTE = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getCif());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getLocalidad());
            ps.setString(5, cliente.getProvincia());
            ps.setInt(6, cliente.getIdCliente());
            ps.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la tabla CLIENTE => " + e.getMessage());
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
    
    public Cliente dameCliente(int id_cliente) {
        Cliente cliente = new Cliente();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = iBd.getConnection();
            String consulta = "SELECT * FROM CLIENTE WHERE ID_CLIENTE = ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, id_cliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente.setIdCliente(rs.getInt("ID_CLIENTE"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setCif(rs.getString("CIF"));
                cliente.setDireccion(rs.getString("DIRECCION"));
                cliente.setLocalidad(rs.getString("LOCALIDAD"));
                cliente.setProvincia(rs.getString("PROVINCIA"));
                cliente.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                cliente.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
            }
        } catch (SQLException e) {
            System.err.println("DBCliente - dameCliente - Error al leer la tabla CLIENTE => " + e.getMessage());
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
        return cliente;
    }
}
