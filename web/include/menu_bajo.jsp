<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="modelo.*"%>
<%
    Usuario logueado = new Usuario();
    if (session.getAttribute("logueado") != null) {
        logueado = ((Usuario) session.getAttribute("logueado"));
    } else {
        response.sendRedirect("index.jsp");
    }
%>
<nav class="nav-site navbar navbar-default" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#nav-site-collapse">
                <span class="glyphicon glyphicon-menu-hamburger"></span>
            </button>
            <a href="#" class="navbar-brand">Crud JDBC</a>
        </div>
        <div class="collapse navbar-collapse" id="nav-site-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="SCabecera?accion=hoy">Facturas de hoy</a></li>
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <%=logueado.getNombre()%>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="SLogin?accion=salir">Salir</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>