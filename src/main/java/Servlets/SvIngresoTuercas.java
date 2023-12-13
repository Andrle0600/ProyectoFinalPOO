package Servlets;

import Logica.Controladora;
import Logica.Kardex;
import Logica.Producto;
import Logica.Tuerca;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SvIngresoTuercas", urlPatterns = {"/SvIngresoTuercas"})
public class SvIngresoTuercas extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        List<Producto> listaProductos = new ArrayList<>();

        listaProductos = control.traerProductos();

        List<Producto> listaTuercas = filtrarTuercas(listaProductos);

        HttpSession misesion = request.getSession();
        misesion.setAttribute("listaTuercas", listaTuercas);
        response.sendRedirect("kardextuercas.jsp");
    }

    private List<Producto> filtrarTuercas(List<Producto> listaProductos) {
        List<Producto> productosFiltrados = new ArrayList<>();

        for (Producto producto : listaProductos) {
            if (producto instanceof Tuerca) {
                productosFiltrados.add(producto);
            }
        }

        return productosFiltrados;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Pedir todos los datos del formulario
        String nombre = request.getParameter("nombre");
        String marca = request.getParameter("marca");
        String medida = request.getParameter("medida");
        String descripcion = request.getParameter("descripcion");
        int cantidadIn = Integer.parseInt(request.getParameter("cantidadIn"));
        int cantidadSa = Integer.parseInt(request.getParameter("cantidadSa"));
        double costo = Double.parseDouble(request.getParameter("costo"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        /*proceso para transformar en fecha y si no es posible
        mandar a mi pantalla de error (se evita mostrar fallos internos al usuario)*/
        Date fecha = null;
        try {
            fecha = dateFormat.parse(request.getParameter("fecha"));
        } catch (ParseException ex) {
            Logger.getLogger(SvIngresoLlaves.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("invalido.jsp");
        }
        //Creo un objeto tuerca y asigno los valores obtenidos del formulario
        Tuerca tuerca = new Tuerca();
        tuerca.setNombre(nombre);
        tuerca.setMarca(marca);
        tuerca.setMedida(medida);
        tuerca.setDescripcion(descripcion);
        tuerca.setCantidadEntrada(cantidadIn);
        tuerca.setCantidadSalida(cantidadSa);
        tuerca.setCostoUni(costo);
        //Pasar el objeto tuerca como parametro para mandarlo a la BD
        control.crearTuerca(tuerca);
        //desterminar un costo total dependiendo si es Ingreso o Salida
        double costoT = 0;
        if(cantidadIn==0/*Si la cantida de ingreso es cero, entonces es una salida*/){
            costoT=cantidadSa*costo;
        }else{
            costoT=cantidadIn*costo;
        }
        //crear un objeto de Kardex e ingresar valores
        Kardex kar = new Kardex();
        kar.setFecha(fecha);
        kar.setProducto(tuerca);
        kar.setCostoTotal(costoT);
        //Pasar como parametro el kardex y mandar sus datos a bd
        control.crearKardex(kar);
        //redireccionar a la pagina principal
        response.sendRedirect("principal.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
