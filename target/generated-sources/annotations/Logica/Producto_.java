package Logica;

import Logica.Kardex;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-12T21:41:14")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> descripcion;
    public static volatile SingularAttribute<Producto, Integer> cantidadSalida;
    public static volatile SingularAttribute<Producto, Double> costoUni;
    public static volatile SingularAttribute<Producto, Integer> id;
    public static volatile ListAttribute<Producto, Kardex> listaKardex;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile SingularAttribute<Producto, Integer> cantidadEntrada;

}