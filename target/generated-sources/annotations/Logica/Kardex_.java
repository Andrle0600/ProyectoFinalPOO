package Logica;

import Logica.Producto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-12T21:41:14")
@StaticMetamodel(Kardex.class)
public class Kardex_ { 

    public static volatile SingularAttribute<Kardex, Date> fecha;
    public static volatile SingularAttribute<Kardex, Double> costoTotal;
    public static volatile SingularAttribute<Kardex, Integer> id;
    public static volatile SingularAttribute<Kardex, Producto> producto;

}