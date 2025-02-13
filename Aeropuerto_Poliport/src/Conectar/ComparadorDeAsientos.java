package Conectar;

import java.util.Comparator;

/**
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class ComparadorDeAsientos implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Asiento asientoLibre, asientoSeleccionado;
        asientoLibre = (Asiento) o1;
        asientoSeleccionado = (Asiento) o2;
        return (asientoLibre.getNumeroAsiento().compareTo(asientoSeleccionado.getNumeroAsiento()));
    }
    
}
