package Logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//borrar en casod de no funcionar
@DiscriminatorColumn(name="tipo_producto",discriminatorType = DiscriminatorType.STRING)
//hasta aquí
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidadEntrada;
    private int cantidadSalida;
    private double costoUni;
    @OneToMany(mappedBy = "producto")
    private List<Kardex> listaKardex;

    public Producto() {
    }

    public Producto(int id, String nombre, String descripcion, int cantidadEntrada, int cantidadSalida, double costoUni, List<Kardex> listaKardex) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadEntrada = cantidadEntrada;
        this.cantidadSalida = cantidadSalida;
        this.costoUni = costoUni;
        this.listaKardex = listaKardex;
    }

    public double getCostoUni() {
        return costoUni;
    }

    public void setCostoUni(double costoUni) {
        this.costoUni = costoUni;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Kardex> getListaKardex() {
        return listaKardex;
    }

    public void setListaKardex(List<Kardex> listaKardex) {
        this.listaKardex = listaKardex;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadEntrada() {
        return cantidadEntrada;
    }

    public void setCantidadEntrada(int cantidadEntrada) {
        this.cantidadEntrada = cantidadEntrada;
    }

    public int getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(int cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }
    
    

}
