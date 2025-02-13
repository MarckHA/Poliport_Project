package Conectar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class PersistenciaAsientosBD {
    
    ConectarMySQL conexion = new ConectarMySQL();
    Connection con = conexion.getConexion("root", "12345678");
    
    public void crearAsientos(String asientoID, String clase, String tipo){
        try {
            PreparedStatement pps = con.prepareStatement("INSERT INTO Asiento VALUES(?,?,?)");
            pps.setString(1, asientoID);
             pps.setString(2, clase);
            pps.setString(3, tipo);
            pps.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
        } catch (Exception ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al ingresar los datos");
        }
    }   
    
    public void crearAsientosPorAvion( String asientoID, String avionID, String estado){
        try {
            PreparedStatement pps = con.prepareStatement("INSERT INTO AsientosPorAvion VALUES(?,?,?)");
            pps.setString(1, asientoID);
            pps.setString(2, avionID);
            pps.setString(3, estado);
            pps.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
        } catch (Exception ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al ingresar los datos");
        }
    }
    
    public Asiento mostrarAsientos(String asientoID, Double precio){
        String[] datos = new String[3];
        Double costoAsiento;
        Statement st;
        Asiento asiento=null;
        try {
            st = con.createStatement();
            String sql="SELECT * FROM Asiento WHERE asientoID='"+asientoID+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                for(int j=0; j<3; j++){
                    datos[j]=rs.getString(j+1);
                }
            }
            switch (asientoID.substring(1)) {
                case "01":
                case "02":
                    costoAsiento = 1.5*precio;
                    break;
                case "03":
                case "04":
                    costoAsiento = 1.2*precio;
                    break;
                default:
                    costoAsiento = precio;
                    break;
            }
            asiento = new Asiento(datos[0], datos[1], datos[2], costoAsiento);
        } catch (SQLException e) {          
            JOptionPane.showMessageDialog(null, "No se pudo mostrar el estado, error: "+e.toString());
        }
        return asiento;
    }
    
    public String mostrarAsientosPorAvion(String asientoID,String avionID){
        String dato="";
        Statement st;
        try {
            st = con.createStatement();
            String sql="SELECT estado FROM AsientosPorAvion WHERE asientoID='"+asientoID+"' AND avionID='"+avionID+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                dato=rs.getString(1);                 
            }
                               
        } catch (Exception e) {          
            JOptionPane.showMessageDialog(null, "No se pudo mostrar el estado, error: "+e.toString());
        }
        return dato;
    }
    
    public void actualizarAsiento( String asientoActualizar, String avionID, String condicion){
        String sql = "UPDATE AsientosPorAvion SET estado='"+condicion+"' WHERE asientoID= '"+
                asientoActualizar+ "' AND avionID="+avionID ;
        try {
            PreparedStatement pps = con.prepareStatement(sql);
            pps.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
        } catch (SQLException ex) {
             Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar actualizar");
        }
    }
}
