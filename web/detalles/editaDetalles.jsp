<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="modelo.*"%>
<%@ page import="database.*"%>
<%
    DBCliente dbCli = new DBCliente();
    Cliente cliente = new Cliente();
    
    Cabecera cabecera = (Cabecera) session.getAttribute("cabecera");
    ArrayList<Detalle> lstDetalles = (ArrayList<Detalle>) session.getAttribute("detalles");
    ArrayList<Articulo> lstArticulos = (ArrayList<Articulo>) session.getAttribute("articulos");

    Usuario usuario = new Usuario();
    if (session.getAttribute("logueado") != null) {
        usuario = ((Usuario) session.getAttribute("logueado"));
    } else {
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.css" media="screen" />
        <link rel="stylesheet" href="css/estilos.css" type="text/css" media="screen" />

        <script src="plugins/jQuery/jquery-2.1.4.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="js/fnDetalles.js"></script>
        <title>Edita Detalle - CRUD JDBC</title>
    </head>
    <body>
        <div class="container">
            <%@ include file="../include/menu_alto.jsp"%>
            <form id="formFactura" action="SDetalle" method="post">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label for="numFactura">Número Factura</label>
                                <input class="form-control" type="text" id="numFactura" name="numFactura" value="<%=cabecera.getNumero()%>" readonly="readonly" />
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label for="id_cliente">Cliente</label> 
                                <%cliente = dbCli.dameCliente(cabecera.getId_cliente());%>
                                <input class="form-control" type="text" id="cliente" name="cliente" value="<%=cliente.getNombre()%>" readonly="readonly" />

                            </div>
                        </div>
                        <div class="col-sm-3">
                            <label for="fecha">Fecha</label>
                            <input class="form-control" type="text" id="fechaFactura" name="fechaFactura" value="<%=cabecera.getFecha()%>" readonly="readonly" />
                        </div>
                    </div>
                </div>

                <table class="table table-striped table-hover">
                    <tr>
                        <th>Artículo</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                        <th>Importe</th>
                        <th colspan="2">Acciones</th>
                    </tr>
                    <tr>
                        <td>
                            <select id="id_articulo" class="form-control" name="id_articulo">
                                <option value="0"> -- Elige Artículo -- </option>
                                <%Iterator<Articulo> itr = lstArticulos.iterator();
                                    while (itr.hasNext()) {
                                        Articulo articulo = (Articulo) itr.next();%>
                                <option value="<%=articulo.getIdArticulo()%>"><%=articulo.getNombre()%></option>
                                <%} %>
                            </select>
                        </td>
                        <td>
                            <input id="cantidad" class="form-control numeros" name="cantidad" type="number" value="1" /> 
                        </td>
                        <td>
                            <input id="precio" class="form-control numeros" name="precio" type="number" step=".01" value="0.0"/>
                        </td>
                        <td id="impLinea">
                            <input id="importe" class="form-control numeros" name="importe" type="text" value="0.0" readonly="readonly" />
                        </td>
                        <td class="acciones">
                            <button type="submit" class="btn btn-primary glyphicon glyphicon-floppy-disk" name="accion" value="insertar" title="Guardar Artículo"></button>
                        </td>
                        <td class="acciones">
                            <button type="reset" class="btn btn-danger glyphicon glyphicon-remove" title="Cancelar"></button>
                        </td>
                    </tr>
                    <%Iterator itrDetalle = lstDetalles.iterator();
                    DBArticulo dbArt = new DBArticulo();
                    DecimalFormat df = new DecimalFormat("###.##");
                    while (itrDetalle.hasNext()) {
                        Detalle detalle = (Detalle) itrDetalle.next();%>
                        <tr id="linea<%=detalle.getOrden()%>" data-id="<%=detalle.getOrden()%>" >
                            <td>
                                <input type="hidden" id="id_articulo<%=detalle.getOrden()%>" name="id_articulo<%=detalle.getOrden()%>"value="<%=detalle.getId_articulo()%>" />
                                <%Articulo articulo = dbArt.dameArticulo(detalle.getId_articulo());%>
                                <input class="form-control" type="text" id="nombre<%=detalle.getOrden()%>" 
                                       name="nombre<%=detalle.getOrden()%>" 
                                       value="<%=articulo.getNombre()%>" 
                                       readonly="readonly" />
                            </td>
                            <td>
                                <input class="form-control numeros edtCantidad" type="number" id="cantidad<%=detalle.getOrden()%>" name="cantidad<%=detalle.getOrden()%>" value="<%=detalle.getCantidad()%>" data-orden="<%=detalle.getOrden()%>" value="<%=detalle.getCantidad()%>" />
                            </td>
                            <td>
                                <input class="form-control numeros edtPrecio" type="number" id="precio<%=detalle.getOrden()%>" name="precio<%=detalle.getOrden()%>" data-orden="<%=detalle.getOrden()%>" step=".01"  value="<%=detalle.getPrecio()%>" />
                            </td>
                            <td>
                                <%double importe = detalle.getCantidad() * detalle.getPrecio();%>
                                <input type="text" class="form-control numeros" id="importe<%=detalle.getOrden()%>" name="importe" readonly="readonly" value="<%=df.format(importe)%>" />
                            </td>
                            <td class="acciones">
                                <a href="#" data-orden="<%=detalle.getOrden()%>" data-num_factura="<%=detalle.getNumero()%>" data-fecha_factura="<%=detalle.getFecha()%>" class="btn btn-primary btnEditar"  title="Editar Linea">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                </a>
                            </td>
                            <td class="acciones">
                                <a href="#" data-orden="<%=detalle.getOrden()%>" data-num_factura="<%=detalle.getNumero()%>" data-fecha_factura="<%=detalle.getFecha()%>" class="btn btn-danger btnBorrar"  title="Borrar Linea">
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                </a>
                            </td>
                        </tr>
                    <%}%>
                </table>
            </form>
        </div>
    </body>
</html>