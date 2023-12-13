<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="components/header.jsp" %>
    <%@include file="components/bodyparte1.jsp" %>

    <h1>Ingreso de Tuercas</h1>
    <p>Proporcione los datos de las tuercas ingresadas</p>
    <form class="user" action="SvIngresoTuercas" method="POST">
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
                <input type="text" class="form-control form-control-user" id="cantidad" name="cantidadIn"
                       placeholder="Cantidad">
            </div>
        </div>
        
        <div class="form-group row">
            <div class="col-sm-6 mb-3 mb-sm-0">
                <input type="text" class="form-control form-control-user" id="costo" name="costo"
                       placeholder="Costo Unitario (solo número)">
            </div>
            <div class="col-sm-6">
                <input type="text" class="form-control form-control-user" id="cantidad" name="fecha"
                       placeholder="Fecha (AAAA-MM-DD)">
            </div>
        </div>
        <div class="form-group">
            <input type="text" class="form-control form-control-user" id="descripcion" name="descripcion"
                   placeholder="Descripción o Comentario">
        </div>
        <div class="form-group">
            <input type="text" name="cantidadSa" class="invisible" value="0">
        </div>
        <button class="btn btn-primary btn-user btn-block" type="submit">
            Registrar Ingreso
        </button>
    </form>
    <%@include file="components/bodyparte2.jsp" %>