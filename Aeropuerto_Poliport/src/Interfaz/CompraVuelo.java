package Interfaz;

import Conectar.Asiento;
import Conectar.ConectarMySQL;
import Conectar.ListaAsientos;
import Conectar.PersistenciaAvionBD;
import Conectar.PersistenciaBoletoBD;
import Conectar.PersistenciaVueloBD;
import java.awt.Toolkit;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class CompraVuelo extends javax.swing.JFrame {

    String contrasenia = "12345678";
    ConectarMySQL conexion = new ConectarMySQL();
    Connection con = conexion.getConexion("root", contrasenia);
    PersistenciaAvionBD perAsientos = new PersistenciaAvionBD();
    PersistenciaVueloBD perVuelos = new PersistenciaVueloBD();
    PersistenciaBoletoBD perBoletos = new PersistenciaBoletoBD();
    ListaAsientos asientosEscogidos;
    JFAsientosVuelo asientosVuelo;
    VentaFinal venta;
    double totalPrecio3raEdad, totalPrecioAdulto, totalPrecioNiño, total;
    int control,numBoletos, bolDescuentos, idBoleto;
    String idVuelo, avionID, aerolinea, modeloAvion, origen, destino, fecha, hora, precio, salida="", cedula ;
    
    
    public CompraVuelo() {
        initComponents();
        this.setLocationRelativeTo(null);
        asientosEscogidos = new ListaAsientos();
        JTavion.setVisible(false);
        jBmostrarVuelos.setEnabled(false);
        jDCfechaVuelo.setMinSelectableDate(new Date());
        jDCfechaVuelo.setEnabled(false);
        jCBvueloDestino.setEnabled(false);
        this.jCBCantidadNiñosMenores2años.setVisible(false);
    }

    public void setAsientosEscogidos(ListaAsientos asientosEscogidos) {
        this.asientosEscogidos = asientosEscogidos;
    }
    
    public void imprimirAsientosEscogidos(){
        if(control==1){
            salida+="Asientos para niños: \n\n"+asientosEscogidos.toString()+"\n";
            numBoletos+=asientosEscogidos.getAsientosSeleccionados().size();
            bolDescuentos+=asientosEscogidos.getAsientosSeleccionados().size();
        } else if (control==2){
            salida+="Asientos para adultos: \n\n"+asientosEscogidos.toString()+"\n";
            numBoletos+=asientosEscogidos.getAsientosSeleccionados().size();
        } else if (control==3){
            salida+="Asientos para adultos mayores: \n\n"+asientosEscogidos.toString()+"\n";
            numBoletos+=asientosEscogidos.getAsientosSeleccionados().size();
            bolDescuentos+=asientosEscogidos.getAsientosSeleccionados().size();
        }
        JTAasientosEscogidos.setText(salida);
    }
    /**
     * Método para leer que el identificador de vuelo tenga 3 dígitos, este método nos ayuda a sacar los datos
     * en el Método KeyReleased de Consultar y Actualizar.
     * @param comprobacion
     * @return 
     */
    private boolean lecturaID(){
        boolean flag=false;
        String patron="^(\\d{3})$";
        if(lecturaRegex(this.jTFIDVuelo.getText(), patron)){
            flag=true;
        } else{
            this.jTFAvionID.setText("");
            this.jTFAerolineaParaCompra.setText("");
            this.jTFHoraVueloParaCompra.setText("");
            this.jTFModeloParaCompra.setText("");
             this.jTFPrecio.setText("");
        }  
        return flag;
    }
    
    
    /**
     * Método para leer que comparar strings mediante expresiones regulares.
     * @param comprobación
     * @param patron
     * @return 
     */
    private boolean lecturaRegex(String comprobación, String patron){
        Pattern pat= Pattern.compile(patron);
        Matcher mat= pat.matcher(comprobación);
        return mat.matches();
    }
    
    /**
     * Método para sacar los datos de un vuelo en base a al identificador de cada vuelo.
     */
    private void sacarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) JTavion.getModel();
        idVuelo = (String) modelo.getValueAt(0, 0);
        avionID = (String) modelo.getValueAt(0, 1);
        fecha = (String) modelo.getValueAt(0, 2);
        origen = (String) modelo.getValueAt(0, 3);
        destino = (String) modelo.getValueAt(0, 4);
        hora = (String) modelo.getValueAt(0, 5);
        precio = (String) modelo.getValueAt(0, 6);
        perAsientos.mostrarAviones(JTavion, 2, avionID);
        DefaultTableModel modeloA = (DefaultTableModel) JTavion.getModel();
        aerolinea = (String) modeloA.getValueAt(0, 1);
        modeloAvion = (String) modeloA.getValueAt(0, 2);
    }
    
    public void precioAsientos(){
        if(control==1){
            for(Asiento aux: asientosEscogidos.getAsientosSeleccionados()){
                totalPrecioNiño+=aux.getPrecio();
            }            
        } else if(control==2){
            for(Asiento aux: asientosEscogidos.getAsientosSeleccionados()){
                totalPrecioAdulto+=aux.getPrecio();
            }            
        }else if (control==3){
            for(Asiento aux: asientosEscogidos.getAsientosSeleccionados()){
                totalPrecio3raEdad+=aux.getPrecio();
            }
        }
    }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTFIDVuelo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTFHoraVueloParaCompra = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTFAerolineaParaCompra = new javax.swing.JTextField();
        jTFModeloParaCompra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCBvueloOrigen = new javax.swing.JComboBox<>();
        jTFAvionID = new javax.swing.JTextField();
        jCBvueloDestino = new javax.swing.JComboBox<>();
        jDCfechaVuelo = new com.toedter.calendar.JDateChooser();
        jTFPrecio = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTVuelosDisponibles = new javax.swing.JTable();
        JTavion = new javax.swing.JTable();
        jBmostrarVuelos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTADescuento = new javax.swing.JTextArea();
        jBDescuento = new javax.swing.JButton();
        JBsiguiente = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jCBCantidadNiñosMenores2años = new javax.swing.JComboBox<>();
        jCBCantidadAdultos = new javax.swing.JComboBox<>();
        jCBCantidadTerceraEdad = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jCBCantidadNiños = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JTAasientosEscogidos = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMPRA DE BOLETOS");
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 153, 153,100));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del vuelo"));

        jLabel5.setText("Aerolinea:");

        jLabel6.setText("Modelo:");

        jLabel4.setText("ID Vuelo :");

        jTFIDVuelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIDVueloActionPerformed(evt);
            }
        });
        jTFIDVuelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFIDVueloKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFIDVueloKeyTyped(evt);
            }
        });

        jLabel7.setText("Vuelo Origen :");

        jLabel8.setText("Vuelo Destino :");

        jLabel9.setText("Hora del vuelo :");

        jTFHoraVueloParaCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFHoraVueloParaCompraKeyPressed(evt);
            }
        });

        jLabel1.setText("Fecha:");

        jLabel2.setText("Escriba el IDVuelo y el AvionID de su preferencia");

        jLabel3.setText("Avion ID:");

        jCBvueloOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una opcion", "Quito", "Guayaquil", "Madrid", "Medellin" }));
        jCBvueloOrigen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBvueloOrigenItemStateChanged(evt);
            }
        });
        jCBvueloOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBvueloOrigenActionPerformed(evt);
            }
        });

        jCBvueloDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una opcion", "Quito", "Guayaquil", "Madrid", "Medellin" }));
        jCBvueloDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBvueloDestinoActionPerformed(evt);
            }
        });

        jDCfechaVuelo.setDateFormatString("d/MM/y");
        jDCfechaVuelo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDCfechaVueloPropertyChange(evt);
            }
        });

        jLabel21.setText("Precio");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCBvueloOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(20, 20, 20))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTFAvionID)
                                    .addComponent(jTFIDVuelo)
                                    .addComponent(jCBvueloDestino, 0, 165, Short.MAX_VALUE)
                                    .addComponent(jDCfechaVuelo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTFAerolineaParaCompra, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTFModeloParaCompra, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel21)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTFHoraVueloParaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                    .addComponent(jTFPrecio))))
                        .addGap(27, 27, 27)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBvueloOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBvueloDestino, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDCfechaVuelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFIDVuelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFAvionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFAerolineaParaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFModeloParaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFHoraVueloParaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, 360));

        jPanel2.setBackground(new java.awt.Color(255, 153, 153,100));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Vuelos Disponibles"));
        jPanel2.setForeground(new java.awt.Color(204, 51, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTVuelosDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTVuelosDisponibles.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTVuelosDisponiblesMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(jTVuelosDisponibles);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 550, 320));

        JTavion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jPanel2.add(JTavion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 480, 300));

        jBmostrarVuelos.setText("Mostrar vuelos disponibles");
        jBmostrarVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBmostrarVuelosActionPerformed(evt);
            }
        });
        jPanel2.add(jBmostrarVuelos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 20, 570, 380));

        jPanel3.setBackground(new java.awt.Color(255, 153, 153,100));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Descuento"));

        jTADescuento.setEditable(false);
        jTADescuento.setColumns(20);
        jTADescuento.setRows(5);
        jScrollPane2.setViewportView(jTADescuento);

        jBDescuento.setText("Ver descuento");
        jBDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDescuentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBDescuento)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 390, 326, 260));

        JBsiguiente.setText("Siguiente >>>");
        JBsiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBsiguienteActionPerformed(evt);
            }
        });
        getContentPane().add(JBsiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 660, 165, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Elija el numero de asientos:");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 436, 240, 20));

        jPanel5.setBackground(new java.awt.Color(255, 153, 153,100));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Boletos"));

        jLabel17.setText("Niño:");

        jLabel18.setText("Adulto");

        jLabel19.setText("Tercera Edad");

        jCBCantidadNiñosMenores2años.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una opcion.", "0", "1", "2", "3", "4", "5", " " }));
        jCBCantidadNiñosMenores2años.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCantidadNiñosMenores2añosActionPerformed(evt);
            }
        });

        jCBCantidadAdultos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una opcion.", "0", "1", "2", "3", "4", "5" }));
        jCBCantidadAdultos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCantidadAdultosActionPerformed(evt);
            }
        });

        jCBCantidadTerceraEdad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una opcion.", "0", "1", "2", "3", "4", "5" }));
        jCBCantidadTerceraEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCantidadTerceraEdadActionPerformed(evt);
            }
        });

        jLabel22.setText("Niños (hasta 2 años):");

        jCBCantidadNiños.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una opcion.", "0", "1", "2", "3", "4", "5", " " }));
        jCBCantidadNiños.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCantidadNiñosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCBCantidadTerceraEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCBCantidadAdultos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCBCantidadNiñosMenores2años, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCBCantidadNiños, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jCBCantidadNiñosMenores2años, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jCBCantidadNiños, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jCBCantidadAdultos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jCBCantidadTerceraEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, -1, 170));

        jPanel6.setBackground(new java.awt.Color(255, 153, 153,100));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Descuentos"));

        jLabel25.setText("Niño:");

        jLabel26.setText("Adulto");

        jLabel27.setText("Tercera Edad (+ 65 )");

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Sin costo");

        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Descuento del 50%");

        jLabel30.setText("Niños hasta 2 años:");

        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Sin descuento");

        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Descuento del 5%");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 7, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29))
                .addContainerGap())
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 520, -1, 130));

        jPanel4.setBackground(new java.awt.Color(255, 153, 153,100));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Asientos Escogidos"));

        JTAasientosEscogidos.setColumns(20);
        JTAasientosEscogidos.setRows(5);
        jScrollPane3.setViewportView(JTAasientosEscogidos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 280, 630));
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 599, 43, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/FondoCompraj.jpg"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-430, -70, 1740, 760));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTFIDVueloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFIDVueloKeyTyped
        char variable = evt.getKeyChar();
        if (Character.isLetter(variable)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null, "Por favor ingrese solo numeros", "Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jTFIDVueloKeyTyped

    private void jTFHoraVueloParaCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFHoraVueloParaCompraKeyPressed
        
    }//GEN-LAST:event_jTFHoraVueloParaCompraKeyPressed

    private void jTVuelosDisponiblesMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTVuelosDisponiblesMouseMoved
        
    }//GEN-LAST:event_jTVuelosDisponiblesMouseMoved

    private void jBmostrarVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBmostrarVuelosActionPerformed
       perVuelos.mostrarVuelos(jTVuelosDisponibles,1, (String) jCBvueloOrigen.getSelectedItem());
        
        ArrayList<String> atributosMostrar = new ArrayList<>();
        ArrayList<String> nombresColumnas = new ArrayList<>();
        
        atributosMostrar.add("vueloID"); nombresColumnas.add("ID del vuelo");
        atributosMostrar.add("avionID"); nombresColumnas.add("ID del avion");
        atributosMostrar.add("Fecha"); nombresColumnas.add("Fecha de salida");
        atributosMostrar.add("Origen"); nombresColumnas.add("Origen");
        atributosMostrar.add("Destino"); nombresColumnas.add("Destino");
        atributosMostrar.add("Hora"); nombresColumnas.add("Hora de salida");
        atributosMostrar.add("Precio"); nombresColumnas.add("Precio Ref.");
       
        String atributoBuscar = "Origen";
        String atributoBuscar2 = "Destino";
        String atributoBuscar3 = "fecha";
        
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        String fechaVuelo = null;
        try {
            fechaVuelo = ff.format(jDCfechaVuelo.getCalendar().getTime());
        } catch (NullPointerException e) {
            //JOptionPane.showMessageDialog(null, "error");
        }
        
        
        String condicion ="'%"+ (String) jCBvueloOrigen.getSelectedItem() + "%'";
        String condicion2 ="'%"+ (String) jCBvueloDestino.getSelectedItem() + "%'";
        String condicion3 ="'%"+ fechaVuelo + "%'";


        if(jCBvueloOrigen.getSelectedIndex() != 0 && jCBvueloDestino.getSelectedIndex() == 0 ){
            perVuelos.consultarVuelo(jTVuelosDisponibles,atributosMostrar, atributoBuscar, condicion, nombresColumnas);
            jDCfechaVuelo.setEnabled(false);
        }
        if(jCBvueloOrigen.getSelectedIndex() != 0 && jCBvueloDestino.getSelectedIndex() != 0 ){
            perBoletos.consultarVuelo3(jTVuelosDisponibles, atributosMostrar, atributoBuscar, atributoBuscar2, condicion, condicion2, nombresColumnas);
            jDCfechaVuelo.setEnabled(true);
        }
        if(jCBvueloOrigen.getSelectedIndex() != 0 && jCBvueloDestino.getSelectedIndex() != 0 && (fechaVuelo != null)){
            perBoletos.consultarVuelo2(jTVuelosDisponibles, atributosMostrar, atributoBuscar, atributoBuscar2, 
                    atributoBuscar3, condicion, condicion2, condicion3, nombresColumnas);
            
        }

    }//GEN-LAST:event_jBmostrarVuelosActionPerformed

    private void jTFIDVueloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFIDVueloKeyReleased
        if(lecturaID()){
            String condicion = "'"+ jTFIDVuelo.getText()+"'" ;
            perVuelos.mostrarVuelos(JTavion, 2, condicion);
            sacarDatos();
            this.jTFAerolineaParaCompra.setText(aerolinea);
            this.jTFHoraVueloParaCompra.setText(hora);
            this.jTFAvionID.setText(avionID);
            this.jTFModeloParaCompra.setText(modeloAvion);
            this.jTFPrecio.setText(precio);
        }   
    }//GEN-LAST:event_jTFIDVueloKeyReleased

    private void JBsiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBsiguienteActionPerformed
        venta = new VentaFinal(salida, asientosEscogidos);
        venta.numBoletos= numBoletos;
        venta.boletosDescuento=bolDescuentos;
        venta.total=total;
        venta.setIdBoleto(idBoleto);
        venta.sacarInformacionCliente(cedula);
        venta.sacarInformacionVuelo(idVuelo);
        venta.informacionBoletos();
        venta.imprimirTotal();
        venta.setVisible(true);
    }//GEN-LAST:event_JBsiguienteActionPerformed

    private void jCBvueloOrigenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBvueloOrigenItemStateChanged
        DefaultComboBoxModel<String> jCBdestinoModel = new DefaultComboBoxModel<>();
        
        if(this.jCBvueloOrigen.getSelectedIndex()!=0){
            for(int i = 0; i < this.jCBvueloOrigen.getItemCount();i++){
                jCBdestinoModel.addElement(this.jCBvueloOrigen.getItemAt(i));
            }
            jCBdestinoModel.removeElement(this.jCBvueloOrigen.getSelectedItem());  
        }else{
            jCBdestinoModel.addElement("Seleccione primero un origen...");
        }
        this.jCBvueloDestino.setModel(jCBdestinoModel);
    }//GEN-LAST:event_jCBvueloOrigenItemStateChanged

    private void jCBvueloOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBvueloOrigenActionPerformed
        if(jCBvueloOrigen.getSelectedIndex()==0){
            Date date = null;
            jBmostrarVuelos.setEnabled(false);
            jCBvueloDestino.setEnabled(false);
            jDCfechaVuelo.setDate(date);
            jDCfechaVuelo.setEnabled(false);
            jBmostrarVuelosActionPerformed(evt);
        }else{
            jBmostrarVuelos.setEnabled(true);
            jCBvueloDestino.setEnabled(true);
            jBmostrarVuelosActionPerformed(evt);
        } 
    }//GEN-LAST:event_jCBvueloOrigenActionPerformed

    private void jCBvueloDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBvueloDestinoActionPerformed
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        
        String fechaVuelo2 = null;
        try {
            fechaVuelo2 = ff.format(jDCfechaVuelo.getCalendar().getTime());
        } catch (NullPointerException e) {

        }
        if(jCBvueloDestino.getSelectedIndex()==0){
            Date date = null;
            jDCfechaVuelo.setDate(date);
            jDCfechaVuelo.setEnabled(false);
            jBmostrarVuelos.doClick();
        }else{
            jDCfechaVuelo.setEnabled(true);
            jBmostrarVuelosActionPerformed(evt);
        }
        if(fechaVuelo2!=null){
            jBmostrarVuelosActionPerformed(evt);
        }
    }//GEN-LAST:event_jCBvueloDestinoActionPerformed

    private void jTFIDVueloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIDVueloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFIDVueloActionPerformed

    private void jBDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDescuentoActionPerformed
        String salida = "";        
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double totalSindescuento = (totalPrecioNiño) + (totalPrecioAdulto) + (totalPrecio3raEdad);
        salida += "Precio sin descuento:\n " + decimalFormat.format(totalSindescuento) + "\n";
        double descuentoTerceraEdad = (totalPrecio3raEdad) * 0.50;
        salida += "El descuento por tercera edad es:\n " + decimalFormat.format(descuentoTerceraEdad) + "\n";
        double descuento = (totalPrecioNiño) * 0.05;
        salida += "El descuento por niños es:\n " + decimalFormat.format(descuento) + "\n";
        total = totalSindescuento - descuentoTerceraEdad - descuento;
        salida += "Precio total:\n " + decimalFormat.format(total) + "\n";
        this.jTADescuento.setText(salida);
    }//GEN-LAST:event_jBDescuentoActionPerformed

    private void jDCfechaVueloPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDCfechaVueloPropertyChange
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        String fechaVuelo2 = null;
        try {
            fechaVuelo2 = ff.format(jDCfechaVuelo.getCalendar().getTime());
        } catch (NullPointerException e) {

        }
        if(fechaVuelo2!=null){
            jBmostrarVuelos.doClick();
        }
    }//GEN-LAST:event_jDCfechaVueloPropertyChange

    private void jCBCantidadNiñosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCantidadNiñosActionPerformed
        if(jCBCantidadNiños.getSelectedIndex()==1 || jCBCantidadNiños.getSelectedIndex()==0){
            control=0;
        } else {
            control = 1;
            asientosVuelo = new JFAsientosVuelo(this);
            asientosVuelo.precioAsiento = Double.parseDouble(precio);
            asientosVuelo.condicion=Integer.parseInt((String) this.jCBCantidadNiños.getSelectedItem());
            asientosVuelo.categorizarAsientos(avionID);
            asientosVuelo.avionID=avionID;
            asientosVuelo.deshabilitarAsientosOcupados();
            asientosVuelo.deshabilitarAsientosEscogidos();
            asientosVuelo.imprimirAsientosEscogidos();
            asientosEscogidos = asientosVuelo.getAsientos();
            asientosVuelo.setVisible(true); 
            
        }
    }//GEN-LAST:event_jCBCantidadNiñosActionPerformed

    private void jCBCantidadAdultosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCantidadAdultosActionPerformed
        if(jCBCantidadAdultos.getSelectedIndex()==1 || jCBCantidadAdultos.getSelectedIndex()==0){
            control=0;
        } else {
            control = 2;
            this.jCBCantidadNiñosMenores2años.setVisible(true);
            asientosVuelo = new JFAsientosVuelo(this);
            asientosVuelo.precioAsiento = Double.parseDouble(precio);
            asientosVuelo.condicion=Integer.parseInt((String) this.jCBCantidadAdultos.getSelectedItem());
            asientosVuelo.categorizarAsientos(avionID);
            asientosVuelo.avionID=avionID;
            asientosVuelo.deshabilitarAsientosOcupados();
            asientosVuelo.deshabilitarAsientosEscogidos();
            asientosVuelo.imprimirAsientosEscogidos();
            asientosEscogidos = asientosVuelo.getAsientos();
            asientosVuelo.setVisible(true); 
        }
    }//GEN-LAST:event_jCBCantidadAdultosActionPerformed

    private void jCBCantidadTerceraEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCantidadTerceraEdadActionPerformed
        if(jCBCantidadTerceraEdad.getSelectedIndex()==1 || jCBCantidadTerceraEdad.getSelectedIndex()==0){
            control=0;
        } else {
            control = 3;
            this.jCBCantidadNiñosMenores2años.setVisible(true);
            asientosVuelo = new JFAsientosVuelo(this);
            asientosVuelo.precioAsiento = Double.parseDouble(precio);
            asientosVuelo.condicion=Integer.parseInt((String) this.jCBCantidadTerceraEdad.getSelectedItem());
            asientosVuelo.categorizarAsientos(avionID);
            asientosVuelo.avionID=avionID;
            asientosVuelo.deshabilitarAsientosOcupados();
            asientosVuelo.deshabilitarAsientosEscogidos();
            asientosVuelo.imprimirAsientosEscogidos();
            asientosEscogidos = asientosVuelo.getAsientos();
            asientosVuelo.setVisible(true);
        }
    }//GEN-LAST:event_jCBCantidadTerceraEdadActionPerformed

    private void jCBCantidadNiñosMenores2añosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCantidadNiñosMenores2añosActionPerformed
        
    }//GEN-LAST:event_jCBCantidadNiñosMenores2añosActionPerformed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        
    }//GEN-LAST:event_formMouseEntered

    public void mostrarActionPerformed(java.awt.event.ActionEvent evt){
        imprimirAsientosEscogidos();
        precioAsientos();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CompraVuelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompraVuelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompraVuelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompraVuelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CompraVuelo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBsiguiente;
    private javax.swing.JTextArea JTAasientosEscogidos;
    private javax.swing.JTable JTavion;
    private javax.swing.JButton jBDescuento;
    private javax.swing.JButton jBmostrarVuelos;
    private javax.swing.JComboBox<String> jCBCantidadAdultos;
    private javax.swing.JComboBox<String> jCBCantidadNiños;
    private javax.swing.JComboBox<String> jCBCantidadNiñosMenores2años;
    private javax.swing.JComboBox<String> jCBCantidadTerceraEdad;
    private javax.swing.JComboBox<String> jCBvueloDestino;
    private javax.swing.JComboBox<String> jCBvueloOrigen;
    private com.toedter.calendar.JDateChooser jDCfechaVuelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTADescuento;
    private javax.swing.JTextField jTFAerolineaParaCompra;
    private javax.swing.JTextField jTFAvionID;
    private javax.swing.JTextField jTFHoraVueloParaCompra;
    private javax.swing.JTextField jTFIDVuelo;
    private javax.swing.JTextField jTFModeloParaCompra;
    private javax.swing.JTextField jTFPrecio;
    private javax.swing.JTable jTVuelosDisponibles;
    // End of variables declaration//GEN-END:variables
}
