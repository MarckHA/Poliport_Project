package Conectar;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class ConectarMySQL {
    public static final String URL = "jdbc:mysql://localhost:3306/primeraBaseDeDatos";
    public String USER;
    public String CLAVE;
    	
    public Connection getConexion(String Usuario, String Clave){
        Connection con = null;
        USER=Usuario;
        CLAVE=Clave;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            if(con!=null){
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"No se pudo establecer la conexión."+"\n"
                    + "Usuario, contraseña o base de datos incorrectas","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: " + e.getMessage());
        }
        return con;
    }
}
