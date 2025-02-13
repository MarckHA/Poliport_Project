package Conectar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class ListaAsientos {
    
    protected List<Asiento> asientosLibres;
    protected List<Asiento> asientosOcupados;
    protected List<Asiento> asientosSeleccionados;
    protected List<Asiento> asientosEscogidos;
    PersistenciaAsientosBD per = new PersistenciaAsientosBD();
   
    
    public ListaAsientos() {
        asientosLibres = new ArrayList<>();
        asientosOcupados = new ArrayList<>();
        asientosSeleccionados = new ArrayList<>();
        asientosEscogidos = new ArrayList<>();
    }
    
    public List<Asiento> getAsientosSeleccionados() {
        return asientosSeleccionados;
    }

    public List<Asiento> getAsientosOcupados() {
        return asientosOcupados;
    }
    
    public List<Asiento> getAsientosLibres() {
        return asientosLibres;
    }

    public List<Asiento> getAsientosEscogidos() {
        return asientosEscogidos;
    }
    
    public void addAsientoLibre(Asiento asiento){
        asientosLibres.add(asiento);
    }
    
    public void addAsientoOcupado(Asiento asiento){
        asientosOcupados.add(asiento);
    }

    public void addAsientoEscogido(Asiento asiento){
        asientosEscogidos.add(asiento);
    }
    
    public void ordenarAsientos(List<Asiento> lista){
        Collections.sort(lista, new ComparadorDeAsientos());
    }
    
    public int busquedaAsiento(Asiento seleccionado, List<Asiento> lista){
        ordenarAsientos(lista);
        return Collections.binarySearch(lista, seleccionado, new ComparadorDeAsientos());
    }
    
   public void añadirSeleccion(Asiento seleccionado){
        int posicion = busquedaAsiento(seleccionado, asientosLibres);
        if(posicion!=-1){
                asientosSeleccionados.add(asientosLibres.get(posicion));
                asientosEscogidos.add(asientosLibres.get(posicion));
                asientosLibres.remove(posicion);
       }
   }
   
   public void quitarSeleccion(Asiento seleccionado){
       int posicion = busquedaAsiento(seleccionado, asientosSeleccionados);
       if(posicion!=-1){
           asientosLibres.add(asientosSeleccionados.get(posicion));
           asientosEscogidos.remove(posicion);
           asientosSeleccionados.remove(posicion);
       }
   }
   
   public String impresionAsientosEscogidos(){
       List<Asiento> copiaEscogidos = new ArrayList<>(asientosEscogidos);
        StringBuilder salida = new StringBuilder();
        for (Asiento aux : copiaEscogidos) {
            salida.append(aux.toString()).append("\n");
        }
        return salida.toString();
   }

    @Override
    public String toString() {
        List<Asiento> copiaSeleccionados = new ArrayList<>(asientosSeleccionados);
        StringBuilder salida = new StringBuilder();
        for (Asiento aux : copiaSeleccionados) {
            salida.append(aux.toString()).append("\n");
        }
        return salida.toString();
    }
    
    public void crearAsientos(){
        for(int i=65; i<71;i++){
            String texto = controlAsiento(i);
            for (int j=1 ; j<10 ; j++){
                String clase = controlClase(j);
                if(i==67 || i==68){
                    if(j>2){
                        per.crearAsientos(Character.toString(i)+"0"+j, clase, texto);
                    }
                } else if( (i==66 || i==69) && j<3) {
                    per.crearAsientos(Character.toString(i)+"0"+j, clase, "Pasillo");
                } else {
                    per.crearAsientos(Character.toString(i)+"0"+j, clase, texto);
                }
            }
        }
    }
    
    public void crearAsientosMySQL(){
        for(int i=65; i<71;i++){
            String texto = controlAsiento(i);
            for (int j=1 ; j<10 ; j++){
                String clase = controlClase(j);
                if(i==67 || i==68){
                    if(j>2){
                        per.crearAsientosPorAvion(Character.toString(i)+"0"+j, "420", "Disponible");
                    }
                } else if( (i==66 || i==69) && j<3) {
                    per.crearAsientosPorAvion(Character.toString(i)+"0"+j, "420", "Disponible");
                } else {
                    per.crearAsientosPorAvion(Character.toString(i)+"0"+j, "420",  "Disponible");
                }
            }
        }
    }
    
    public String controlAsiento(int i){
        String texto="";
        switch(i){
                case 65,70:
                    texto="Ventana";
                    break;
                case 66,69:
                    texto="Entre pasillo y ventana";
                    break;
                case 67,68:
                    texto="Pasillo";
                    break;
            }
        return texto;
    }
    
    public String controlClase(int j){
        String clase="";
        switch(j){
                case 1,2:
                    clase="Primera Clase";
                    break;
                case 3,4:
                    clase="Clase Ejecutiva";
                    break;
                default:
                    clase="Clase económica";
                    break;
            }
        return clase;
    }
}
