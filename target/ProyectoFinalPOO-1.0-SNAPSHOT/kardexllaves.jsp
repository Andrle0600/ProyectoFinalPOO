<%@page import="java.util.List"%>
<%@page import="Logica.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="components/header.jsp" %>
    <%@include file="components/bodyparte1.jsp" %>

    <div class="container-fluid">

        <!-- Page Heading -->
        <h1 class="h3 mb-2 text-gray-800" style="text-align: center"><i class="fas fa-fw fa-wrench"></i>
            <span>Llaves  </span><i class="fas fa-fw fa-wrench"></i></h1>
        <p class="mb-4" style="text-align: center">Información sobre ingresos y salidas del producto llave.</p>
        <!-- DataTales Example -->
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary" style="text-align: center">Tabla Kardex</h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>

                            <tr>
                                <th rowspan="2" style="text-align: center; vertical-align: middle">Tipo de Operación</th>
                                <th colspan="3" style="text-align: center;vertical-align: middle">ENTRADAS</th>
                                <th colspan="3" style="text-align: center;vertical-align: middle">SALIDAS</th>
                                <th colspan="3" style="text-align: center;vertical-align: middle">SALDOS</th>
                            </tr>
                            <tr>

                                <th style="text-align: center; vertical-align: middle">Cantidad</th>
                                <th style="text-align: center; vertical-align: middle">Costo U.</th>
                                <th style="text-align: center; vertical-align: middle">Costo T.</th>
                                <th style="text-align: center; vertical-align: middle">Cantidad</th>
                                <th style="text-align: center; vertical-align: middle">Costo U.</th>
                                <th style="text-align: center; vertical-align: middle">Costo T.</th>
                                <th style="text-align: center; vertical-align: middle">Cantidad</th>
                                <th style="text-align: center; vertical-align: middle">Costo U.</th>
                                <th style="text-align: center; vertical-align: middle">Costo T.</th>

                            </tr>
                            <%
                                int a = 50;
                                double b = 20;
                                double c = 1000;
                                int totalCom=0;
                                int totalVen=0;
                                double costoCom=0;
                                double costoVen=0;
                            %>

                            </tr>
                        </thead>
                        
                        <%
                            List<Producto> listaLlaves = (List<Producto>) request.getSession().getAttribute("listaLlaves");
                        %>
                        <tbody>
                            <tr>
                                <td style="text-align: center; vertical-align: middle">SALDO INICIAL</th>
                                <td style="text-align: center; vertical-align: middle"></th>
                                <td style="text-align: center; vertical-align: middle"></th>
                                <td style="text-align: center; vertical-align: middle"></th>
                                <td style="text-align: center; vertical-align: middle"></th>
                                <td style="text-align: center; vertical-align: middle"></th>
                                <td style="text-align: center; vertical-align: middle"></th>
                                <td style="text-align: center; vertical-align: middle"><%=a%></th>
                                <td style="text-align: center; vertical-align: middle"><%=b%></th>
                                <td style="text-align: center; vertical-align: middle"><%=c%></th>
                            <tr>
                                <% if (listaLlaves != null && !listaLlaves.isEmpty()) { %>
                                <% for (int i = 0; i < listaLlaves.size(); i++) { %>
                                <%Producto pro = listaLlaves.get(i);%>
                                <% if (pro.getCantidadEntrada() > 0) {%>
                                <td style="text-align: center; vertical-align: middle">COMPRA</td>
                                <td style="text-align: center; vertical-align: middle"><%= pro.getCantidadEntrada()%></td>
                                <td style="text-align: center; vertical-align: middle"><%= pro.getCostoUni()%></td>
                                <td style="text-align: center; vertical-align: middle"><%= pro.getCantidadEntrada() * pro.getCostoUni()%></td>
                                <td style="text-align: center; vertical-align: middle"></td>
                                <td style="text-align: center; vertical-align: middle"></td>
                                <td style="text-align: center; vertical-align: middle"></td>
                                <td style="text-align: center; vertical-align: middle"><%= a + pro.getCantidadEntrada()%></td>
                                <td style="text-align: center; vertical-align: middle"><%=String.format("%.2f", (c + pro.getCantidadEntrada() * pro.getCostoUni()) / (a + pro.getCantidadEntrada()))%></td>
                                <td style="text-align: center; vertical-align: middle"><%=String.format("%.2f", c + pro.getCantidadEntrada() * pro.getCostoUni())%></td>

                                <% b = (c + pro.getCantidadEntrada() * pro.getCostoUni()) / (a + pro.getCantidadEntrada());%>
                                <%c += pro.getCantidadEntrada() * pro.getCostoUni();%>
                                <%a += pro.getCantidadEntrada();%>
                                <%totalCom+=pro.getCantidadEntrada();%>
                                <%costoCom+=pro.getCantidadEntrada() * pro.getCostoUni();%>
                                <% } else {%>
                                <td style="text-align: center; vertical-align: middle">VENTA</td>
                                <td style="text-align: center; vertical-align: middle"></td>
                                <td style="text-align: center; vertical-align: middle"></td>
                                <td style="text-align: center; vertical-align: middle"></td>
                                <!-- SALIDAS -->
                                <td style="text-align: center; vertical-align: middle"><%= pro.getCantidadSalida()%></td><!-- OK -->
                                <td style="text-align: center; vertical-align: middle"><%= String.format("%.2f", b)%></td>
                                <td style="text-align: center; vertical-align: middle"><%= String.format("%.2f", pro.getCantidadSalida() * b)%></td>
                                <!-- SALDOS -->
                                <td style="text-align: center; vertical-align: middle"><%= a - pro.getCantidadSalida()%></td>
                                <td style="text-align: center; vertical-align: middle"><%=String.format("%.2f", (c - pro.getCantidadSalida() * b) / (a - pro.getCantidadSalida()))%></td>
                                <td style="text-align: center; vertical-align: middle"><%=String.format("%.2f", c - pro.getCantidadSalida() * b)%></td>
                                <%a -= pro.getCantidadSalida();%>
                                <%c -= pro.getCantidadSalida() * b;%>
                                <%totalVen+=pro.getCantidadSalida();%>
                                <%costoVen+=pro.getCantidadSalida() * b;%>
                                <% } %>
                            </tr>



                            <% } %>
                            <% } else { %>
                            <tr>
                                <td colspan="10" style="text-align: center;">No hay datos disponibles</td>
                            </tr>
                            <% }%>
                            <tr>
                                <td colspan="10" style="text-align: center; color: white">hlfdkgshdl</td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align: center; vertical-align: middle">TOTALES</th>
                                <th style="text-align: center; vertical-align: middle"><%=totalCom%></th>
                                <th style="text-align: center; vertical-align: middle"></th>
                                <th style="text-align: center; vertical-align: middle"><%=String.format("%.2f", costoCom)%></th>
                                <th style="text-align: center; vertical-align: middle"><%=totalVen%></th>
                                <th style="text-align: center; vertical-align: middle"></th>
                                <th style="text-align: center; vertical-align: middle"><%=String.format("%.2f", costoVen)%></th>
                                <th style="text-align: center; vertical-align: middle"><%=a%></th>
                                <th style="text-align: center; vertical-align: middle"></th>
                                <th style="text-align: center; vertical-align: middle"><%=String.format("%.2f", c)%></th>

                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>

    </div>

    <%if (a > 200) {%>
    <div style="text-align: center">
        <label class="btn btn-success btn-icon-split" >
            <span class="icon text-white-50">
                <i class="fas fa-check"></i>
            </span>
            <span class="text">Inventario Adecuado</span>
        </label>
    </div>
    <div class="text" style="text-align: center">Inventario adecuado</div>

    <%} else if (a < 50) {%>
    <div style="text-align: center">
        <a class="btn btn-danger btn-icon-split">
            <span class="icon text-white-50">
                <i class="fas fa-exclamation-triangle"></i>
            </span>
            <span class="text">Inventario Insuficiente</span>
        </a>
    </div>
    <div class="text" style="text-align: center">Inventario en peligro de agotarse</div>
    <%} else {%>
    <div style="text-align: center">
        <a class="btn btn-warning btn-icon-split">
            <span class="icon text-white-50">
                <i class="fas fa-info-circle"></i>
            </span>
            <span class="text">Revisar Inventario</span>
        </a>

    </div>
    <div class="text" style="text-align: center">Inventario podría ser insuficiente</div>

    <%}%>

    <%@include file="components/bodyparte2.jsp" %>