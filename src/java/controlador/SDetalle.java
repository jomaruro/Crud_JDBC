/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import database.DBArticulo;
import database.DBCabecera;
import database.DBCliente;
import database.DBDetalle;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Articulo;
import modelo.Cabecera;
import modelo.Cliente;
import modelo.Detalle;
import modelo.Usuario;

/**
 *
 * @author ruiz
 */
@WebServlet(name = "SDetalle", urlPatterns = {"/SDetalle"})
public class SDetalle extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd;
        HttpSession ss = request.getSession();
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("accion").equals("") || request.getParameter("accion") == null) {
            rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } else if (request.getParameter("accion").equals("insertar")) {
            Usuario usuario = (Usuario) ss.getAttribute("logueado");
            if (usuario != null) {
                Detalle detalle = new Detalle();
                Cabecera cabecera = new Cabecera();
                DBDetalle dbDetalle = new DBDetalle();

                int orden = 0;
                int numFactura = Integer.parseInt(request.getParameter("numFactura"));
                String fechaFactura = request.getParameter("fechaFactura");
                System.out.println("SDetalle - insertar - numFactura   -> " + numFactura);
                System.out.println("SDetalle - insertar - fechaFactura -> " + fechaFactura);
                orden = dbDetalle.dameOrden(numFactura, fechaFactura);
                orden++;

                detalle.setNumero(numFactura);
                detalle.setFecha(fechaFactura);
                detalle.setOrden(orden);
                
                System.out.println("SDetalle - insertar - Detalle      -> " + detalle.toString());
                
                Articulo articulo = new Articulo();
                DBArticulo dbArticulo = new DBArticulo();
                articulo = dbArticulo.dameArticulo(Integer.parseInt(request.getParameter("id_articulo")));
                detalle.setId_articulo(articulo.getIdArticulo());
                detalle.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                detalle.setPrecio(Double.parseDouble(request.getParameter("precio")));
                
                if (dbDetalle.insertaDetalle(detalle)) {
                    System.out.println("Inserción correcta de detalle");
                } else {
                    System.out.println("Inserción INcorrecta de detalle");
                }

                DBCabecera dbCab = new DBCabecera();
                cabecera.setNumero(numFactura);
                cabecera.setFecha(fechaFactura);
                System.out.println("SDetalle - insertar - setNumero   -> " + cabecera.getNumero());
                System.out.println("SDetalle - insertar - setFecha    -> " + cabecera.getFecha());
                cabecera = dbCab.dameFactura(cabecera.getNumero(), cabecera.getFecha());
                System.out.println("SDetaller - insertar - cabecera1 -> " + cabecera.toString());
                Cliente cliente = new Cliente();
                DBCliente dbCliente = new DBCliente();
                cliente = dbCliente.dameCliente(cabecera.getId_cliente());

                ArrayList<Detalle> lstDetalles = new ArrayList<Detalle>();
                lstDetalles = dbDetalle.dameDetalles(cabecera.getNumero(), cabecera.getFecha());

                DBArticulo dbArt = new DBArticulo();
                List<Articulo> lstArticulo = dbArt.listaArticulos();

                ss.setAttribute("logueado", usuario);
                ss.setAttribute("cabecera", cabecera);
                ss.setAttribute("cliente", cliente);
                ss.setAttribute("detalles", lstDetalles);
                ss.setAttribute("articulos", lstArticulo);
                System.out.println("SDetalle - insertar - cabecera2 -> " + cabecera.toString());
                rd = request.getRequestDispatcher("detalles/editaDetalles.jsp");
                rd.forward(request, response);
            } else {
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }

        } else if (request.getParameter("accion").equals("editar")) {
            Usuario usuario = (Usuario) ss.getAttribute("logueado");
            if (usuario != null) {
                Detalle detalle = new Detalle();
                detalle.setNumero(Integer.parseInt(request.getParameter("numFactura")));
                detalle.setFecha(request.getParameter("fechaFactura"));
                detalle.setOrden(Integer.parseInt(request.getParameter("orden")));
                detalle.setId_articulo(Integer.parseInt(request.getParameter("id_articulo")));
                detalle.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                detalle.setPrecio(Double.parseDouble(request.getParameter("precio")));

                DBDetalle dbDetalle = new DBDetalle();
                if (dbDetalle.actualizaDetalle(detalle)) {
                    System.out.println("Actualización correcta de detalle");
                } else {
                    System.err.println("Actualización INcorrecta de detalle");
                }
            } else {
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }

        } else if (request.getParameter("accion").equals("borrar")) {
            Usuario usuario = (Usuario) ss.getAttribute("logueado");
            if (usuario != null) {
                Detalle detalle = new Detalle();
                detalle.setNumero(Integer.parseInt(request.getParameter("numFactura")));
                detalle.setFecha(request.getParameter("fechaFactura"));
                detalle.setOrden(Integer.parseInt(request.getParameter("orden")));

                DBDetalle dbDet = new DBDetalle();
                if (dbDet.borraDetalle(detalle.getNumero(), detalle.getFecha(), detalle.getOrden())) {
                    System.out.println("Linea borrada correctamente");
                    PrintWriter salida = null;
                    try {
                        salida = response.getWriter();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    salida.println("OK");
                } else {
                    System.out.println("Linea No borrada");
                }
            } else {
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
