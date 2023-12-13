<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="es">
    <%@include file="components/header.jsp" %>
    <%@include file="components/bodyparte1.jsp" %>

    <h1>Salida de Llaves</h1>
    <p>Proporcione los datos de las llaves retiradas. El precio unitario es el correspondiente de tienda.</p>
    <form class="user" action="SvIngresoLlaves" method="POST">
        <div class="form-group row">
            <div class="col-sm-6 mb-3 mb-sm-0">
                <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                       placeholder="Nombre">
            </div>
            <div class="col-sm-6">
                <input type="text" class="form-control form-control-user" id="medida" name="medida"
                       placeholder="Medida">
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-6 mb-3 mb-sm-0">
                <input type="text" class="form-control form-control-user" id="marca" name="marca"
                       placeholder="Marca">
            </div>
            <div class="col-sm-6">
                <input type="text" class="form-control form-control-user" id="cantidad" name="cantidadSa"
                       placeholder="Cantidad">
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-6 mb-3 mb-sm-0">
                <input type="text" class="form-control form-control-user" id="cantidad" name="fecha"
                       placeholder="Fecha (AAAA-MM-DD)">
            </div>
            <div class="col-sm-6">
                <input type="text" class="form-control form-control-user" id="descripcion" name="descripcion"
                       placeholder="Descripción o comentario">
            </div>
        </div>
        <div class="form-group">
            <input type="text" name="cantidadIn" class="invisible" value="0">
        </div>
        <div class="form-group">
            <input type="text" name="costo" class="invisible" value="0">
        </div>
        <button class="btn btn-primary btn-user btn-block" type="submit">
            Registrar Salida
        </button>
    </form>
    <%@include file="components/bodyparte2.jsp" %>
