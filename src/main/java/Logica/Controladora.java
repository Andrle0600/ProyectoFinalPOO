package Logica;

import Persistencia.ControladoraPersis;
import java.util.List;

public class Controladora {
    ControladoraPersis persis=new ControladoraPersis();
    
    //Metodos de Llaves
    public void crearLlave(Llave llave){
        persis.crearLlave(llave);
    }
    //Metodos de Kardex
    public void crearKardex(Kardex kar){
        persis.crearKardex(kar);
    }
    //Metodos de Tuerca
    public void crearTuerca(Tuerca tuerca) {
        persis.crearTuerca(tuerca);
    }
    //Metedos de Producto

    public List<Producto> traerProductos() {
      return persis.traerProductos();
             
    }
    
}
