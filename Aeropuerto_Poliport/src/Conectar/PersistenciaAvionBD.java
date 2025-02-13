package Conectar;

import java.awt.HeadlessException;
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
 *
 * @author Luis
 */
public class PersistenciaAvionBD {

    String contrasenia = "12345678";
    ConectarMySQL conexion = new ConectarMySQL();
    Connection con = conexion.getConexion("root", contrasenia);
    
    public void registrarAvion(String avionID, String Aerolinea, String modelo){  
        try {
            PreparedStatement pps = con.prepareStatement("INSERT INTO Avion VALUES(?,?,?)");
            pps.setString(1, avionID);
            pps.setString(2, Aerolinea);
            pps.setString(3, modelo);
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
        } catch (Exception ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al ingresar los datos");
        }   
    }

    public void mostrarAviones(JTable paramTabla, int identificador, String condicion){
        
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        
        paramTabla.setRowSorter(OrdenarTabla);
        
        String sql="";
        
        modelo.addColumn("Avion ID");
        modelo.addColumn("Aerolinea");
        modelo.addColumn("Modelo");

        paramTabla.setModel(modelo);
        String [] datos = new String [3];
        Statement st;
        
        try {
           
            st = con.createStatement();
            if(identificador==1){
                sql="SELECT * FROM Avion";
            } else {
                sql="SELECT * FROM Avion WHERE avionID="+condicion+" LIMIT 1";
            }
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                modelo.addRow(datos);               
            }
            paramTabla.setModel(modelo);       
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros, error: "+e.toString());
        }
    }
    
    public void consultarAvion(JTable paramTabla, ArrayList<String> atributosMostrar, String atributoBuscar, String condicion, ArrayList<String> nombresColumnas) {
        
        Iterator i = atributosMostrar.iterator();
        String consulta = "";
        while(i.hasNext()){
            consulta+=i.next()+",";
        }

        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> ordenarTabla = new TableRowSorter<>(modelo);
        paramTabla.setRowSorter(ordenarTabla);
        String sql = "";

        switch (atributosMostrar.size()) {

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
        }

        paramTabla.setModel(modelo);
        String[] datos = new String[atributosMostrar.size()];
        consulta = consulta.substring(0, consulta.length() - 1);

        try {
            sql = "SELECT " + consulta + " FROM Avion WHERE " + atributoBuscar + " LIKE " + condicion;
            PreparedStatement pps = con.prepareStatement(sql);
            ResultSet rs = pps.executeQuery(sql);

            while (rs.next()) {

                for (int j = 0; j < atributosMostrar.size(); j++) {
                    datos[j] = rs.getString(j + 1);
                }
                modelo.addRow(datos);
            }
            paramTabla.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void actualizarAvion(ArrayList<String> atributosActualizar, String atributoActualizar, String condicion) {
    Iterator i = atributosActualizar.iterator();
    String consulta="";
    while (i.hasNext()) {
        consulta += i.next() + ",";
    }
    consulta = consulta.substring(0, consulta.length() - 1);
    String sql = "UPDATE Avion SET " + consulta + " WHERE " + atributoActualizar + " LIKE " + condicion;
        try {
            PreparedStatement pps = con.prepareStatement(sql);
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
        } catch (SQLException ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar actualizar");
        }
    }
    
    public void eliminarAvion(String codigoIDAvion){
        try {
            PreparedStatement pps = con.prepareStatement("DELETE FROM Avion WHERE avionID="+ codigoIDAvion );
            pps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Registro Eliminado Correctamente");
        } catch (HeadlessException | SQLException ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar Eliminar");
        }
    }
    
    public boolean verificacionAvion(String condicion){
        String sql= "SELECT COUNT(*) FROM Avion WHERE avionID = "+condicion ;
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
