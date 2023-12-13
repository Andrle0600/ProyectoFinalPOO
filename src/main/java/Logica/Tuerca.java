    package Logica;

import java.util.List;
import javax.persistence.Entity;

@Entity
public class Tuerca extends Producto {

    // private int idTuerca;
    private String marca;
    private String medida;

    public Tuerca() {
    }

    public Tuerca(String marca, String medida, int id, String nombre, String descripcion, int cantidadEntrada, int cantidadSalida, double costoUni, List<Kardex> listaKardex) {
        super(id, nombre, descripcion, cantidadEntrada, cantidadSalida, costoUni, listaKardex);
        this.marca = marca;
        this.medida = medida;
    }

    

    /* public int getIdTuerca() {
        return idTuerca;
    }

    public void setIdTuerca(int idTuerca) {
        this.idTuerca = idTuerca;
    }*/
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

}
