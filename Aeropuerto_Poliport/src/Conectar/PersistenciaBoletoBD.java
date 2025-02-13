package Conectar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class PersistenciaBoletoBD {
    
    String contrasenia = "12345678";
    ConectarMySQL conexion = new ConectarMySQL();
    Connection con = conexion.getConexion("root", contrasenia);
    
    public void consultarVuelo2(JTable paramTabla,ArrayList<String> atributosMostrar, String atributoBuscar, 
            String atributoBuscar2, String atributoBuscar3, String condicion, String condicion2, String condicion3, 
             ArrayList<String> nombresColumnas) {
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
            sql = "SELECT " + consulta + " FROM Vuelo WHERE " + atributoBuscar + " LIKE " + condicion + " AND " 
                    + atributoBuscar2 + " LIKE " + condicion2 + " AND " + atributoBuscar3 + " LIKE " + condicion3;
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
    
    public void consultarVuelo3(JTable paramTabla,ArrayList<String> atributosMostrar, String atributoBuscar, 
            String atributoBuscar2, String condicion, String condicion2, 
             ArrayList<String> nombresColumnas) {
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
            sql = "SELECT " + consulta + " FROM Vuelo WHERE " + atributoBuscar + " LIKE " + condicion + " AND " 
                    + atributoBuscar2 + " LIKE " + condicion2;
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
    
    public void registrarBoleto(String idBoleto, String idCliente, String idVuelo, String idAsientos){  
        try {
            PreparedStatement pps = con.prepareStatement("INSERT INTO Boleto VALUES(?,?,?,?)");
            pps.setString(1, idBoleto);
            pps.setString(2, idCliente);
            pps.setString(3, idVuelo);
            pps.setString(4, idAsientos);
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
        } catch (Exception ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al ingresar los datos");
        }   
    }
    
}
