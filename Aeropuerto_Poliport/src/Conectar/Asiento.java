package Conectar;

/**
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class Asiento {
    
    protected String numeroAsiento;
    protected String tipoAsiento;
    protected String lugarAsiento;
    protected Double precio;

    public Asiento() {
    }

    public Asiento(String numeroAsiento, String tipoAsiento, String lugarAsiento, Double precio) {
        this.numeroAsiento = numeroAsiento;
        this.tipoAsiento = tipoAsiento;
        this.lugarAsiento = lugarAsiento;
        this.precio = precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    @Override
    public String toString() {
        return "Asiento: " + numeroAsiento + "\nTipo: " + tipoAsiento + "\nLugar: " + lugarAsiento +
                "\nPrecio sin descuento: " + String.format("%.2f",(Object) precio);
    }
}
