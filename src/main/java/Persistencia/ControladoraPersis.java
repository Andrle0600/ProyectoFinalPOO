package Persistencia;

import Logica.Kardex;
import Logica.Llave;
import Logica.Producto;
import Logica.Tuerca;
import java.util.List;

public class ControladoraPersis {
    KardexJpaController kardexJpa=new KardexJpaController();
    ProductoJpaController productoJpa=new ProductoJpaController();
    TuercaJpaController tuercaJpa=new TuercaJpaController();
    LlaveJpaController llaveJpa=new LlaveJpaController();
    
    //Metodos para Llaves
    public void crearLlave(Llave llave){
        llaveJpa.create(llave);
    }

    //Metodos para kardex
    public void crearKardex(Kardex kar) {
        kardexJpa.create(kar);
    }
    
    //Metodos para Tuercas
    public void crearTuerca(Tuerca tuerca) {
        tuercaJpa.create(tuerca);
    }
    
    //Metodos para Productos
    public List<Producto> traerProductos() {
        return productoJpa.findProductoEntities();
    }


}
