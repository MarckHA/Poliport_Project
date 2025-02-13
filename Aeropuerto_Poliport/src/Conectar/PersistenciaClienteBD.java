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
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class PersistenciaClienteBD {
    
    String contrasenia = "12345678";
    ConectarMySQL conexion = new ConectarMySQL();
    Connection con = conexion.getConexion("root", contrasenia);
    
    public void registrarCliente(String Cedula, String Nombre, String fechaNacim, String Sexo){  
        try {
            PreparedStatement pps = con.prepareStatement("INSERT INTO Cliente VALUES(?,?,?,?)");
            pps.setString(1, Cedula);
            pps.setString(2, Nombre);
            pps.setString(3, fechaNacim);
            pps.setString(4, Sexo);
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
        } catch (Exception ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al ingresar los datos");
        }   
    }
    
    public void mostrarClientes(JTable paramTabla, int identificador, String condicion){
        
        ConectarMySQL objetoConexion = new ConectarMySQL();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        
        paramTabla.setRowSorter(OrdenarTabla);
        
        String sql="";
        
        modelo.addColumn("Cédula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Fecha Nacim.");
        modelo.addColumn("Sexo");

        paramTabla.setModel(modelo);
        String [] datos = new String [4];
        Statement st;
        
        try {
           
            st = objetoConexion.getConexion("root",contrasenia).createStatement();
            if(identificador==1){
                sql="SELECT * FROM Cliente;";
            } else {
                sql="SELECT * FROM Cliente WHERE clienteID="+condicion+" LIMIT 1";
            }
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                modelo.addRow(datos);               
            }
            paramTabla.setModel(modelo);       
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros, error: "+e.toString());
        }
    }
    
    public void consultarCliente(JTable paramTabla,ArrayList<String> atributosMostrar, String atributoBuscar, String condicion, ArrayList<String> nombresColumnas){
        Iterator i = atributosMostrar.iterator();
        String consulta = "";
        
        while (i.hasNext()) {
            consulta += i.next() + ",";
        }
        
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<>(modelo);
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
        }
        
        paramTabla.setModel(modelo);
        String [] datos = new String [atributosMostrar.size()];
        consulta = consulta.substring(0, consulta.length() - 1);
        
        PreparedStatement pps;
        ResultSet rs;

        try {
            sql = "SELECT " + consulta + " FROM Cliente WHERE " + atributoBuscar + " LIKE " + condicion;
            pps = con.prepareStatement(sql);
            rs = pps.executeQuery(sql);
            while (rs.next()) {
                for(int j=0; j< atributosMostrar.size(); j++ ){
                    datos[j]=rs.getString(j+1);
                }
                modelo.addRow(datos);
            }
            paramTabla.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizarCliente(ArrayList<String> atributosActualizar, String atributoActualizar, String condicion) {
    Iterator i = atributosActualizar.iterator();
    String consulta="";
    while (i.hasNext()) {
        consulta += i.next() + ",";
    }
    consulta = consulta.substring(0, consulta.length() - 1);
    String sql = "UPDATE Cliente SET " + consulta + " WHERE " + atributoActualizar + " LIKE " + condicion;
        try {
            PreparedStatement pps = con.prepareStatement(sql);
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
        } catch (SQLException ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar actualizar");
        }
    }
    
    public void eliminarCliente(String codigoCedula){
        try {
            PreparedStatement pps = con.prepareStatement("DELETE FROM Cliente WHERE clienteID="+ codigoCedula );
            pps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Registro Eliminado Correctamente");
        } catch (HeadlessException | SQLException ex) {
            Logger.getLogger(ConectarMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar Eliminar");
        }
    }
    /**
     * Método para verificar que existe almenos un fila correspondiente al número de cédula ingresado.
     * @param con
     * @param condicion
     * @return verdadero si existe, falso si no.
     */
    public boolean verificacionCliente(String condicion){
        String sql= "SELECT COUNT(*) FROM Cliente WHERE clienteID = "+condicion ;
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
