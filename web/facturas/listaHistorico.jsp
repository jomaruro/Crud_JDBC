<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="modelo.*"%>
<%@ page import="database.*"%>
<%
    DBCliente dbCli = new DBCliente();
    DBDetalle dbDet = new DBDetalle();
    DBUsuario dbUsu = new DBUsuario();
    
    ArrayList<Cabecera> lstCabecera = (ArrayList<Cabecera>) session.getAttribute("cabeceras");

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
        <title>Crud JDBC</title>
    </head>
    <body>
        <div class="container">
            <%@ include file="../include/menu_alto.jsp"%>
            <div class="form-group">
                <% if (lstCabecera.size() > 0) {%>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col">Número</th>
                            <th scope="col">Cliente</th>
                            <th scope="col">Importe</th>
                            <th scope="col">Usuario</th>
                        </tr>
                    </thead>
                    <tbody>

                        <%Iterator<Cabecera> itr = lstCabecera.iterator();
                        while (itr.hasNext()) {
                            Cabecera cabecera = (Cabecera) itr.next();
                            double importe = 0;
                            importe = dbDet.dameImporteFactura(cabecera.getNumero(), cabecera.getFecha());%>
                            <tr>
                                <td>
                                    <%=cabecera.getNumero()%>
                                </td>
                                <td>
                                    <%Cliente cli = dbCli.dameCliente(cabecera.getId_cliente());%>
                                    <%=cli.getNombre()%>
                                </td>
                                <td class="numeros">
                                    <%=importe%>
                                </td>
                                <td>
                                    <%Usuario vendedor = dbUsu.dameUsuario(cabecera.getId_usuario());%>
                                    <%=vendedor.getNombre()%>
                                </td>
                            </tr>
                        <%} %>
                    </tbody>
                </table>
                <%} else { %>
                    <p>No hay facturas que mostrar</p>
                <%}%>
            </div>
        </div>
    </body>
</html>