package Logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//Borrar en caso de no funcionar
@DiscriminatorValue("Llave")
//hasta aquí
public class Llave extends Producto implements Serializable {

    private String marca;
    private String medida;

    public Llave() {
    }

    public Llave(String marca, String medida, int id, String nombre, String descripcion, int cantidadEntrada, int cantidadSalida, double costoUni, List<Kardex> listaKardex) {
        super(id, nombre, descripcion, cantidadEntrada, cantidadSalida, costoUni, listaKardex);
        this.marca = marca;
        this.medida = medida;
    }

    

        /* public int getIdLlave() {
        return idLlave;
    }

    public void setIdLlave(int idLlave) {
        this.idLlave = idLlave;
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
