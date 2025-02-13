package Conectar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class PersistenciaVueloBD {
    
    String contrasenia = "12345678";
    ConectarMySQL conexion = new ConectarMySQL();
    Connection con = conexion.getConexion("root", contrasenia);
    
    public void registrarVuelo(int IdVuelo, String AvionID,  String Fecha, String VueloOrigen, String VueloDestino, String HoraDelVuelo, Double Precio) {
        try {
            PreparedStatement pps = con.prepareStatement("INSERT INTO Vuelo VALUES(?,?,?,?,?,?,?) ");
            pps.setInt(1, IdVuelo);
            pps.setString(2, AvionID);
            pps.setString(3, Fecha);
            pps.setString(4, VueloOrigen);
            pps.setString(5, VueloDestino);
            pps.setString(6, HoraDelVuelo);      
            pps.setDouble(7, Precio);  
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
        } catch (SQLException ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al ingresar los datos");
        }
    }
    
    public void mostrarVuelos(JTable paramTablaVuelos, int identificador, String condicion){

        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        
        paramTablaVuelos.setRowSorter(OrdenarTabla);
        
        String sql="";

        
        modelo.addColumn("ID del vuelo");
        modelo.addColumn("ID del avión");
        modelo.addColumn("Fecha de salida");
        modelo.addColumn("Origen");
        modelo.addColumn("Destino");
        modelo.addColumn("Hora de salida");
        modelo.addColumn("Precio");
        
        paramTablaVuelos.setModel(modelo);
        String [] datos = new String [7];
        Statement st;
        try {
            st = con.createStatement();
            if(identificador==1){
                sql="SELECT * FROM Vuelo;";
            } else {
                sql= "SELECT * FROM Vuelo WHERE vueloID="+condicion+" LIMIT 1";
            }
            
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                modelo.addRow(datos);
            }
            paramTablaVuelos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros, error: "+e.toString());
        }
    }
    
    public void consultarVuelo(JTable paramTabla,ArrayList<String> atributosMostrar, String atributoBuscar, String condicion, ArrayList<String> nombresColumnas) {
        Iterator i = atributosMostrar.iterator();
        String consulta = "";
        while (i.hasNext()) {
            consulta += i.next() + ",";
        }

        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        
        paramTabla.setRowSorter(OrdenarTabla);
        
        String sql="";
        
        switch(atributosMostrar.size()){
            case 1:
                modelo.addColumn(nombresColumnas.get(0));
                break;
            case 2:
                modelo.addColumn(nombresColumnas.get(0));
                modelo.addColumn(nombresColumnas.get(1));
                break;
            case 3:
                modelo.addColumn(nombresColumnas.get(0));
                modelo.addColumn(nombresColumnas.get(1));
                modelo.addColumn(nombresColumnas.get(2));
                break;
            case 4:
                modelo.addColumn(nombresColumnas.get(0));
                modelo.addColumn(nombresColumnas.get(1));
                modelo.addColumn(nombresColumnas.get(2));
                modelo.addColumn(nombresColumnas.get(3));
                break;
            case 5:
                modelo.addColumn(nombresColumnas.get(0));
                modelo.addColumn(nombresColumnas.get(1));
                modelo.addColumn(nombresColumnas.get(2));
                modelo.addColumn(nombresColumnas.get(3));
                modelo.addColumn(nombresColumnas.get(4));
                break;
            case 6:
                modelo.addColumn(nombresColumnas.get(0));
                modelo.addColumn(nombresColumnas.get(1));
                modelo.addColumn(nombresColumnas.get(2));
                modelo.addColumn(nombresColumnas.get(3));
                modelo.addColumn(nombresColumnas.get(4));
                modelo.addColumn(nombresColumnas.get(5));
                break;
            case 7:
                modelo.addColumn(nombresColumnas.get(0));
                modelo.addColumn(nombresColumnas.get(1));
                modelo.addColumn(nombresColumnas.get(2));
                modelo.addColumn(nombresColumnas.get(3));
                modelo.addColumn(nombresColumnas.get(4));
                modelo.addColumn(nombresColumnas.get(5));
                modelo.addColumn(nombresColumnas.get(6));
                break;
        }
        
        paramTabla.setModel(modelo);
        String [] datos = new String [atributosMostrar.size()];
        
        consulta = consulta.substring(0, consulta.length() - 1);
    
        PreparedStatement pps;
        ResultSet rs;

        try {
            sql = "SELECT " + consulta + " FROM Vuelo WHERE " + atributoBuscar + " LIKE " + condicion;
            pps = con.prepareStatement(sql);
            rs = pps.executeQuery(sql);
          
            while (rs.next()) {
                for (int j = 0; j < atributosMostrar.size(); j++) {
                    datos[j]= rs.getString(j+1);                
                }
                modelo.addRow(datos);
            }           
            paramTabla.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizarVuelo (ArrayList<String> atributosActualizar, String atributoActualizar, String condicion){
        Iterator i = atributosActualizar.iterator();
        String consulta = "";
        while (i.hasNext()) {
            consulta += i.next() + ",";
        }
        consulta = consulta.substring(0, consulta.length() - 1);
        String sql = "UPDATE Vuelo SET " + consulta + " WHERE " + atributoActualizar + "=" + condicion;
        try {
            PreparedStatement pps = con.prepareStatement(sql);
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
        } catch (SQLException ex) {
             Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar actualizar");
        }
    }  
    
    public void eliminarVuelo(String codigoIDVuelo){
        try {

            PreparedStatement pps = con.prepareStatement("DELETE FROM Vuelo WHERE vueloID="+ codigoIDVuelo);
            pps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Registro Eliminado Correctamente");
        } catch (SQLException ex) {
            
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar Eliminar");
        
        }
    } 
    /**
     * Método para verificar que existe almenos un fila correspondiente al identificador de vuelo ingresado.
     * @param condicion
     * @return verdadero si existe, falso si no.
     */
    public boolean verificacionVuelo(String condicion){
        String sql= "SELECT COUNT(*) FROM Vuelo WHERE vueloID = "+condicion ;
        boolean flag=false;
        ResultSet rs;
        try {
            PreparedStatement pps = con.prepareStatement(sql);
            rs=pps.executeQuery();
            rs.next();
            int cuenta = rs.getInt(1);
            if(cuenta>0){
                flag=true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"error");
        }
        return flag;
    }
}
