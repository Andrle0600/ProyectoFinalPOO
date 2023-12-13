package Servlets;

import Logica.Controladora;
import Logica.Kardex;
import Logica.Llave;
import Logica.Producto;
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

/**
 *
 * @author 15-CW1024
 */
@WebServlet(name = "SvIngresoLlaves", urlPatterns = {"/SvIngresoLlaves"})
public class SvIngresoLlaves extends HttpServlet {

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

        List<Producto> listaLlaves = filtrarLlaves(listaProductos);

        HttpSession misesion = request.getSession();
        misesion.setAttribute("listaLlaves", listaLlaves);
        response.sendRedirect("kardexllaves.jsp");
    }

    private List<Producto> filtrarLlaves(List<Producto> listaProductos) {
        List<Producto> productosFiltrados = new ArrayList<>();

        for (Producto producto : listaProductos) {
            if (producto instanceof Llave) {
                productosFiltrados.add(producto);
            }
        }

        return productosFiltrados;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String nombre = request.getParameter("nombre");
        String marca = request.getParameter("marca");
        String medida = request.getParameter("medida");
        String descripcion = request.getParameter("descripcion");
        int cantidadIn = Integer.parseInt(request.getParameter("cantidadIn"));
        int cantidadSa = Integer.parseInt(request.getParameter("cantidadSa"));
        double costo = Double.parseDouble(request.getParameter("costo"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = dateFormat.parse(request.getParameter("fecha"));
        } catch (ParseException ex) {
            Logger.getLogger(SvIngresoLlaves.class.getName()).log(Level.SEVERE, null, ex);
        }
        Llave llave = new Llave();
        llave.setCantidadEntrada(cantidadIn);
        llave.setCantidadSalida(cantidadSa);
        llave.setDescripcion(descripcion);
        llave.setMarca(marca);
        llave.setMedida(medida);
        llave.setNombre(nombre);
        llave.setCostoUni(costo);
        control.crearLlave(llave);
        double costoT = 0;
        if (cantidadIn == 0/*Si la cantida de ingreso es cero, entonces es una salida*/) {
            costoT = cantidadSa * costo;
        } else {
            costoT = cantidadIn * costo;
        }
        Kardex kar = new Kardex();
        kar.setProducto(llave);
        kar.setCostoTotal(costoT);
        kar.setFecha(fecha);
        control.crearKardex(kar);
        response.sendRedirect("principal.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
