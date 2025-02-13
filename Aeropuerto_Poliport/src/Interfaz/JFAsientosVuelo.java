package Interfaz;

import Conectar.Asiento;
import Conectar.ListaAsientos;
import Conectar.PersistenciaAsientosBD;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

/**
 * @author Marck Hernández, Luis Morocho, Michelle Nogales, Andrés Peréz, Ozzy Loachamín
 */
public class JFAsientosVuelo extends javax.swing.JFrame {

    private ListaAsientos asientos = new ListaAsientos();
    private JToggleButton[] asientosBotones;
    private PersistenciaAsientosBD perAsientos = new PersistenciaAsientosBD();
    private int numAsientos=1;
    protected double precioAsiento;
    protected String avionID;
    public int condicion;
    private CompraVuelo frame1;
    
    public JFAsientosVuelo(CompraVuelo frame1){
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(getIconImage());
        this.frame1=frame1;
        listaBotones();
        nombrarBotones();
    }

    public final void listaBotones(){
        asientosBotones = new JToggleButton[] { A01, A02, A03, A04, A05, A06, A07, A08, A09, B01, B02, B03, B04, B05, 
            B06, B07, B08, B09, C03, C04, C05, C06, C07, C08, C09,D03, D04, D05, D06, D07, D08, D09, E01, E02, E03, E04, E05, 
            E06, E07, E08, E09, F01, F02, F03, F04, F05, F06, F07, F08, F09 };
    }
    
    public final void nombrarBotones(){
        int i=65;
        int j=1;
        for(int k=0; k<asientosBotones.length; k++){
            if(j==10){
                j=1;
                i++;
            }
            if(i==67 || i==68){
                if(j==1){j=3;}                    
                       asientosBotones[k].setName(Character.toString(i)+"0"+j);   
                } else {
                asientosBotones[k].setName(Character.toString(i)+"0"+j);
            } 
            j++;
        }
    }
    
    public void asientoEscogido(JToggleButton boton){
        boton.setBorder(new LineBorder(new Color(255, 0, 255),3,true));
    }
    
    public void asientoSeleccionado(JToggleButton boton){
        boton.setBorder(new LineBorder(new Color(102, 102, 102),3,true));
    }
    
    public void asientoDisponible(JToggleButton boton){
        boton.setBorder(new LineBorder(Color.GREEN,3,true));
    }
    
    public void asientoOcupado(JToggleButton boton){
        boton.setBorder(new LineBorder(Color.RED,3,true));
        boton.setSelected(true);
    }
    
    public void categorizarAsientos(String avionID){
        for(JToggleButton aux:asientosBotones){
            if(perAsientos.mostrarAsientosPorAvion(aux.getName(), avionID).equals("Disponible")){
                asientos.addAsientoLibre(perAsientos.mostrarAsientos(aux.getName(), precioAsiento));
            } else if(perAsientos.mostrarAsientosPorAvion(aux.getName(), avionID).equals("Ocupado")){
                asientos.addAsientoOcupado(perAsientos.mostrarAsientos(aux.getName(), precioAsiento));
            } else if(perAsientos.mostrarAsientosPorAvion(aux.getName(), avionID).equals("Seleccionado")) {
                asientos.addAsientoEscogido(perAsientos.mostrarAsientos(aux.getName(),precioAsiento));
            }
        }
    }
    
    public ListaAsientos getAsientos() {
        return asientos;
    }    
    
    public void deshabilitarAsientos(){
        for (JToggleButton boton : asientosBotones) {
            Asiento asiento = new Asiento(boton.getName(), "", "",0.0);
            for(Asiento aux: asientos.getAsientosLibres()){
                if(aux.getNumeroAsiento().equals(asiento.getNumeroAsiento())){
                    boton.setEnabled(false);
                }
            }
        }
    }
    
    public void habilitarAsientos(){
        for (JToggleButton boton : asientosBotones) {
            Asiento asiento = new Asiento(boton.getName(), "", "",0.0);
            for(Asiento aux: asientos.getAsientosLibres()){
                if(aux.getNumeroAsiento().equals(asiento.getNumeroAsiento())){
                    boton.setEnabled(true);
                }
            }
        }
    }
    
    public void deshabilitarAsientosOcupados() {
        for (JToggleButton boton : asientosBotones) {
            Asiento asiento = new Asiento(boton.getName(), "", "",0.0);
            for(Asiento aux: asientos.getAsientosOcupados()){
                if(aux.getNumeroAsiento().equals(asiento.getNumeroAsiento())){
                    boton.setEnabled(false);
                    asientoOcupado(boton);
                }
            }
        }
    }
    
    public void habilitarAsientosEscogidos(){
        for (JToggleButton boton : asientosBotones) {
            Asiento asiento = new Asiento(boton.getName(), "", "",0.0);
            for(Asiento aux: asientos.getAsientosEscogidos()){
                if(aux.getNumeroAsiento().equals(asiento.getNumeroAsiento())){
                    boton.setEnabled(true);
                    asientoEscogido(boton);
                }
            }
        }
    }
    
    public void deshabilitarAsientosEscogidos(){
        for (JToggleButton boton : asientosBotones) {
            Asiento asiento = new Asiento(boton.getName(), "", "",0.0);
            for(Asiento aux: asientos.getAsientosEscogidos()){
                if(aux.getNumeroAsiento().equals(asiento.getNumeroAsiento())){
                    boton.setEnabled(false);
                    asientoEscogido(boton);
                }
            }
        }
    }
    
    public void accionAsiento(JToggleButton boton){
            Asiento asientoAux = new Asiento(boton.getName(), "", "",0.0);
            if(boton.isSelected()){
                if(numAsientos<condicion){
                    asientoSeleccionado(boton);
                    asientos.añadirSeleccion(asientoAux);
                    JTAasientosSeleccionados.setText(asientos.toString());
                    numAsientos++;
                } else if(numAsientos==condicion){
                    asientoSeleccionado(boton);
                    asientos.añadirSeleccion(asientoAux);
                    JTAasientosSeleccionados.setText(asientos.toString());
                    deshabilitarAsientos();
                }
            } else {
                habilitarAsientos();
                if(asientos.getAsientosSeleccionados().size()==condicion){
                    asientoDisponible(boton);
                    asientos.quitarSeleccion(asientoAux);
                    JTAasientosSeleccionados.setText(asientos.toString());
                } else if(asientos.getAsientosSeleccionados().size()<condicion){
                    numAsientos--;
                    asientoDisponible(boton);
                    asientos.quitarSeleccion(asientoAux);
                    JTAasientosSeleccionados.setText(asientos.toString());
                }
            }  
    }
    
    public void imprimirAsientosEscogidos(){
        this.JTAasientosEscogidos.setText(asientos.impresionAsientosEscogidos());
    }

        @Override
    public Image getIconImage(){
        Image logo = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagen/LogoForm.png"));
        return logo;
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
        A01 = new javax.swing.JToggleButton();
        B01 = new javax.swing.JToggleButton();
        E01 = new javax.swing.JToggleButton();
        F01 = new javax.swing.JToggleButton();
        E02 = new javax.swing.JToggleButton();
        F02 = new javax.swing.JToggleButton();
        F03 = new javax.swing.JToggleButton();
        F04 = new javax.swing.JToggleButton();
        B02 = new javax.swing.JToggleButton();
        A02 = new javax.swing.JToggleButton();
        E03 = new javax.swing.JToggleButton();
        E04 = new javax.swing.JToggleButton();
        D03 = new javax.swing.JToggleButton();
        C03 = new javax.swing.JToggleButton();
        B03 = new javax.swing.JToggleButton();
        A03 = new javax.swing.JToggleButton();
        D04 = new javax.swing.JToggleButton();
        C04 = new javax.swing.JToggleButton();
        B04 = new javax.swing.JToggleButton();
        A04 = new javax.swing.JToggleButton();
        F05 = new javax.swing.JToggleButton();
        F06 = new javax.swing.JToggleButton();
        F07 = new javax.swing.JToggleButton();
        F08 = new javax.swing.JToggleButton();
        F09 = new javax.swing.JToggleButton();
        E05 = new javax.swing.JToggleButton();
        D05 = new javax.swing.JToggleButton();
        C05 = new javax.swing.JToggleButton();
        B05 = new javax.swing.JToggleButton();
        A05 = new javax.swing.JToggleButton();
        E06 = new javax.swing.JToggleButton();
        D06 = new javax.swing.JToggleButton();
        C06 = new javax.swing.JToggleButton();
        B06 = new javax.swing.JToggleButton();
        A06 = new javax.swing.JToggleButton();
        E07 = new javax.swing.JToggleButton();
        D07 = new javax.swing.JToggleButton();
        C07 = new javax.swing.JToggleButton();
        B07 = new javax.swing.JToggleButton();
        A07 = new javax.swing.JToggleButton();
        E08 = new javax.swing.JToggleButton();
        D08 = new javax.swing.JToggleButton();
        C08 = new javax.swing.JToggleButton();
        B08 = new javax.swing.JToggleButton();
        A08 = new javax.swing.JToggleButton();
        E09 = new javax.swing.JToggleButton();
        D09 = new javax.swing.JToggleButton();
        C09 = new javax.swing.JToggleButton();
        B09 = new javax.swing.JToggleButton();
        A09 = new javax.swing.JToggleButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        JTAasientosEscogidos = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTAasientosSeleccionados = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        JBaceptar = new javax.swing.JButton();
        JBcancelar = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JTAasientosSeleccionados1 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Asientos a escoger");

        jPanel1.setPreferredSize(new java.awt.Dimension(1600, 850));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        A01.setBackground(new java.awt.Color(255, 204, 153));
        A01.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A01.setName("A01"); // NOI18N
        A01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A01ActionPerformed(evt);
            }
        });
        jPanel1.add(A01, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 297, 28, 31));

        B01.setBackground(new java.awt.Color(255, 204, 153));
        B01.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B01ActionPerformed(evt);
            }
        });
        jPanel1.add(B01, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 256, 28, 31));

        E01.setBackground(new java.awt.Color(255, 204, 153));
        E01.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E01ActionPerformed(evt);
            }
        });
        jPanel1.add(E01, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 166, 28, 31));

        F01.setBackground(new java.awt.Color(255, 204, 153));
        F01.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        F01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F01ActionPerformed(evt);
            }
        });
        jPanel1.add(F01, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 125, 28, 32));

        E02.setBackground(new java.awt.Color(255, 204, 153));
        E02.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E02ActionPerformed(evt);
            }
        });
        jPanel1.add(E02, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 166, 28, 31));

        F02.setBackground(new java.awt.Color(255, 204, 153));
        F02.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        F02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F02ActionPerformed(evt);
            }
        });
        jPanel1.add(F02, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 125, 28, 32));

        F03.setBackground(new java.awt.Color(142, 114, 75));
        F03.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 255, 0)));
        F03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F03ActionPerformed(evt);
            }
        });
        jPanel1.add(F03, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 122, 30, 27));

        F04.setBackground(new java.awt.Color(142, 114, 75));
        F04.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 255, 0)));
        F04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F04ActionPerformed(evt);
            }
        });
        jPanel1.add(F04, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 122, 30, 27));

        B02.setBackground(new java.awt.Color(255, 204, 153));
        B02.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B02ActionPerformed(evt);
            }
        });
        jPanel1.add(B02, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 256, 28, 31));

        A02.setBackground(new java.awt.Color(255, 204, 153));
        A02.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A02ActionPerformed(evt);
            }
        });
        jPanel1.add(A02, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 297, 28, 31));

        E03.setBackground(new java.awt.Color(142, 114, 75));
        E03.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E03ActionPerformed(evt);
            }
        });
        jPanel1.add(E03, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 152, 30, 27));

        E04.setBackground(new java.awt.Color(142, 114, 75));
        E04.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E04ActionPerformed(evt);
            }
        });
        jPanel1.add(E04, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 152, 30, 27));

        D03.setBackground(new java.awt.Color(142, 114, 75));
        D03.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        D03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D03ActionPerformed(evt);
            }
        });
        jPanel1.add(D03, new org.netbeans.lib.awtextra.AbsoluteConstraints(581, 182, 30, 27));

        C03.setBackground(new java.awt.Color(142, 114, 75));
        C03.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        C03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C03ActionPerformed(evt);
            }
        });
        jPanel1.add(C03, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 247, 30, 27));

        B03.setBackground(new java.awt.Color(142, 114, 75));
        B03.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B03ActionPerformed(evt);
            }
        });
        jPanel1.add(B03, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 277, 30, 27));

        A03.setBackground(new java.awt.Color(142, 114, 75));
        A03.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A03ActionPerformed(evt);
            }
        });
        jPanel1.add(A03, new org.netbeans.lib.awtextra.AbsoluteConstraints(581, 306, 30, 27));

        D04.setBackground(new java.awt.Color(142, 114, 75));
        D04.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        D04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D04ActionPerformed(evt);
            }
        });
        jPanel1.add(D04, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 182, 30, 27));

        C04.setBackground(new java.awt.Color(142, 114, 75));
        C04.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        C04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C04ActionPerformed(evt);
            }
        });
        jPanel1.add(C04, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 247, 30, 27));

        B04.setBackground(new java.awt.Color(142, 114, 75));
        B04.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B04ActionPerformed(evt);
            }
        });
        jPanel1.add(B04, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 277, 30, 27));

        A04.setBackground(new java.awt.Color(142, 114, 75));
        A04.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A04ActionPerformed(evt);
            }
        });
        jPanel1.add(A04, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 306, 30, 27));

        F05.setBackground(new java.awt.Color(60, 141, 196));
        F05.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        F05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F05ActionPerformed(evt);
            }
        });
        jPanel1.add(F05, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 124, 30, 27));

        F06.setBackground(new java.awt.Color(60, 141, 196));
        F06.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        F06.setName("F06"); // NOI18N
        F06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F06ActionPerformed(evt);
            }
        });
        jPanel1.add(F06, new org.netbeans.lib.awtextra.AbsoluteConstraints(995, 124, 30, 27));

        F07.setBackground(new java.awt.Color(60, 141, 196));
        F07.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        F07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F07ActionPerformed(evt);
            }
        });
        jPanel1.add(F07, new org.netbeans.lib.awtextra.AbsoluteConstraints(1055, 124, 30, 27));

        F08.setBackground(new java.awt.Color(60, 141, 196));
        F08.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        F08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F08ActionPerformed(evt);
            }
        });
        jPanel1.add(F08, new org.netbeans.lib.awtextra.AbsoluteConstraints(1115, 124, 30, 27));

        F09.setBackground(new java.awt.Color(60, 141, 196));
        F09.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        F09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F09ActionPerformed(evt);
            }
        });
        jPanel1.add(F09, new org.netbeans.lib.awtextra.AbsoluteConstraints(1175, 124, 30, 27));

        E05.setBackground(new java.awt.Color(60, 141, 196));
        E05.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E05ActionPerformed(evt);
            }
        });
        jPanel1.add(E05, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 153, 30, 27));

        D05.setBackground(new java.awt.Color(60, 141, 196));
        D05.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        D05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D05ActionPerformed(evt);
            }
        });
        jPanel1.add(D05, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 182, 30, 27));

        C05.setBackground(new java.awt.Color(60, 141, 196));
        C05.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        C05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C05ActionPerformed(evt);
            }
        });
        jPanel1.add(C05, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 246, 30, 27));

        B05.setBackground(new java.awt.Color(60, 141, 196));
        B05.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B05ActionPerformed(evt);
            }
        });
        jPanel1.add(B05, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 275, 30, 27));

        A05.setBackground(new java.awt.Color(60, 141, 196));
        A05.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A05ActionPerformed(evt);
            }
        });
        jPanel1.add(A05, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 306, 30, 27));

        E06.setBackground(new java.awt.Color(60, 141, 196));
        E06.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E06.setName("E06"); // NOI18N
        E06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E06ActionPerformed(evt);
            }
        });
        jPanel1.add(E06, new org.netbeans.lib.awtextra.AbsoluteConstraints(995, 153, 30, 27));

        D06.setBackground(new java.awt.Color(60, 141, 196));
        D06.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        D06.setName("D06"); // NOI18N
        D06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D06ActionPerformed(evt);
            }
        });
        jPanel1.add(D06, new org.netbeans.lib.awtextra.AbsoluteConstraints(995, 182, 30, 27));

        C06.setBackground(new java.awt.Color(60, 141, 196));
        C06.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        C06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C06ActionPerformed(evt);
            }
        });
        jPanel1.add(C06, new org.netbeans.lib.awtextra.AbsoluteConstraints(995, 246, 30, 27));

        B06.setBackground(new java.awt.Color(60, 141, 196));
        B06.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B06ActionPerformed(evt);
            }
        });
        jPanel1.add(B06, new org.netbeans.lib.awtextra.AbsoluteConstraints(995, 275, 30, 27));

        A06.setBackground(new java.awt.Color(60, 141, 196));
        A06.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A06ActionPerformed(evt);
            }
        });
        jPanel1.add(A06, new org.netbeans.lib.awtextra.AbsoluteConstraints(995, 306, 30, 27));

        E07.setBackground(new java.awt.Color(60, 141, 196));
        E07.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E07ActionPerformed(evt);
            }
        });
        jPanel1.add(E07, new org.netbeans.lib.awtextra.AbsoluteConstraints(1055, 153, 30, 27));

        D07.setBackground(new java.awt.Color(60, 141, 196));
        D07.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        D07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D07ActionPerformed(evt);
            }
        });
        jPanel1.add(D07, new org.netbeans.lib.awtextra.AbsoluteConstraints(1055, 182, 30, 27));

        C07.setBackground(new java.awt.Color(60, 141, 196));
        C07.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        C07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C07ActionPerformed(evt);
            }
        });
        jPanel1.add(C07, new org.netbeans.lib.awtextra.AbsoluteConstraints(1055, 246, 30, 27));

        B07.setBackground(new java.awt.Color(60, 141, 196));
        B07.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B07ActionPerformed(evt);
            }
        });
        jPanel1.add(B07, new org.netbeans.lib.awtextra.AbsoluteConstraints(1055, 275, 30, 27));

        A07.setBackground(new java.awt.Color(60, 141, 196));
        A07.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A07ActionPerformed(evt);
            }
        });
        jPanel1.add(A07, new org.netbeans.lib.awtextra.AbsoluteConstraints(1055, 306, 30, 27));

        E08.setBackground(new java.awt.Color(60, 141, 196));
        E08.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E08ActionPerformed(evt);
            }
        });
        jPanel1.add(E08, new org.netbeans.lib.awtextra.AbsoluteConstraints(1115, 153, 30, 27));

        D08.setBackground(new java.awt.Color(60, 141, 196));
        D08.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        D08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D08ActionPerformed(evt);
            }
        });
        jPanel1.add(D08, new org.netbeans.lib.awtextra.AbsoluteConstraints(1115, 182, 30, 27));

        C08.setBackground(new java.awt.Color(60, 141, 196));
        C08.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        C08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C08ActionPerformed(evt);
            }
        });
        jPanel1.add(C08, new org.netbeans.lib.awtextra.AbsoluteConstraints(1115, 246, 30, 27));

        B08.setBackground(new java.awt.Color(60, 141, 196));
        B08.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B08ActionPerformed(evt);
            }
        });
        jPanel1.add(B08, new org.netbeans.lib.awtextra.AbsoluteConstraints(1115, 275, 30, 27));

        A08.setBackground(new java.awt.Color(60, 141, 196));
        A08.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A08ActionPerformed(evt);
            }
        });
        jPanel1.add(A08, new org.netbeans.lib.awtextra.AbsoluteConstraints(1115, 306, 30, 27));

        E09.setBackground(new java.awt.Color(60, 141, 196));
        E09.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        E09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E09ActionPerformed(evt);
            }
        });
        jPanel1.add(E09, new org.netbeans.lib.awtextra.AbsoluteConstraints(1175, 153, 30, 27));

        D09.setBackground(new java.awt.Color(60, 141, 196));
        D09.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        D09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D09ActionPerformed(evt);
            }
        });
        jPanel1.add(D09, new org.netbeans.lib.awtextra.AbsoluteConstraints(1175, 182, 30, 27));

        C09.setBackground(new java.awt.Color(60, 141, 196));
        C09.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        C09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C09ActionPerformed(evt);
            }
        });
        jPanel1.add(C09, new org.netbeans.lib.awtextra.AbsoluteConstraints(1175, 246, 30, 27));

        B09.setBackground(new java.awt.Color(60, 141, 196));
        B09.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        B09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B09ActionPerformed(evt);
            }
        });
        jPanel1.add(B09, new org.netbeans.lib.awtextra.AbsoluteConstraints(1175, 275, 30, 27));

        A09.setBackground(new java.awt.Color(60, 141, 196));
        A09.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        A09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A09ActionPerformed(evt);
            }
        });
        jPanel1.add(A09, new org.netbeans.lib.awtextra.AbsoluteConstraints(1175, 306, 30, 27));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 255), 3, true));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 380, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 204, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(448, 380, 20, 20));

        jPanel3.setBackground(new java.awt.Color(142, 114, 75));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(583, 380, -1, -1));

        jPanel4.setBackground(new java.awt.Color(60, 141, 196));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(718, 380, -1, -1));

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 380, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 3, true));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 380, -1, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Primera Clase");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, 110, 20));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Clase Ejecutiva");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 380, 100, 20));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Clase Económica");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 380, 120, 20));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Disponible");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 380, 90, 20));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Ocupado");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 380, 60, 20));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Escogidos");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 380, 80, 20));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 380, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Seleccionado");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 380, 80, 20));

        jPanel14.setBackground(new java.awt.Color(109, 174, 230,60));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Asientos escogidos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        JTAasientosEscogidos.setEditable(false);
        JTAasientosEscogidos.setColumns(20);
        JTAasientosEscogidos.setRows(5);
        jScrollPane4.setViewportView(JTAasientosEscogidos);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 430, 490, 210));

        jPanel5.setBackground(new java.awt.Color(109, 174, 230,60));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Asientos seleccionados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        JTAasientosSeleccionados.setEditable(false);
        JTAasientosSeleccionados.setColumns(20);
        JTAasientosSeleccionados.setRows(5);
        jScrollPane2.setViewportView(JTAasientosSeleccionados);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, 530, 210));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Fondo_Avion_Asientos.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1505, 414));

        JBaceptar.setBackground(new java.awt.Color(0, 204, 153));
        JBaceptar.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        JBaceptar.setText("Aceptar");
        JBaceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBaceptarActionPerformed(evt);
            }
        });
        jPanel1.add(JBaceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, 125, 35));

        JBcancelar.setBackground(new java.awt.Color(0, 153, 153));
        JBcancelar.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        JBcancelar.setText("Cancelar");
        JBcancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBcancelarActionPerformed(evt);
            }
        });
        jPanel1.add(JBcancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 530, 125, 35));

        jPanel13.setBackground(new java.awt.Color(109, 174, 230,60));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Asientos seleccionados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        JTAasientosSeleccionados1.setEditable(false);
        JTAasientosSeleccionados1.setColumns(20);
        JTAasientosSeleccionados1.setRows(5);
        jScrollPane3.setViewportView(JTAasientosSeleccionados1);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, 370, 210));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/fondoAsientosAvion.jpg"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 660));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1505, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JBaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBaceptarActionPerformed
        for(Asiento aux: asientos.getAsientosSeleccionados()){
            perAsientos.actualizarAsiento(aux.getNumeroAsiento(), avionID, "Seleccionado");
        }
        frame1.mostrarActionPerformed(evt);
        this.setVisible(false);
    }//GEN-LAST:event_JBaceptarActionPerformed

    private void A06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A06ActionPerformed
        accionAsiento(A06);
    }//GEN-LAST:event_A06ActionPerformed

    private void B06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B06ActionPerformed
        accionAsiento(B06);
    }//GEN-LAST:event_B06ActionPerformed

    private void C06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C06ActionPerformed
        accionAsiento(C06);
    }//GEN-LAST:event_C06ActionPerformed

    private void D06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D06ActionPerformed
        accionAsiento(D06);
    }//GEN-LAST:event_D06ActionPerformed

    private void E06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E06ActionPerformed
        accionAsiento(E06);
    }//GEN-LAST:event_E06ActionPerformed

    private void F06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F06ActionPerformed
        accionAsiento(F06);
    }//GEN-LAST:event_F06ActionPerformed

    private void A01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A01ActionPerformed
        accionAsiento(A01);
    }//GEN-LAST:event_A01ActionPerformed

    private void A02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A02ActionPerformed
        accionAsiento(A02);
    }//GEN-LAST:event_A02ActionPerformed

    private void A03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A03ActionPerformed
        accionAsiento(A03);
    }//GEN-LAST:event_A03ActionPerformed

    private void A04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A04ActionPerformed
         accionAsiento(A04);
    }//GEN-LAST:event_A04ActionPerformed

    private void A05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A05ActionPerformed
        accionAsiento(A05);
    }//GEN-LAST:event_A05ActionPerformed

    private void A07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A07ActionPerformed
        accionAsiento(A07);
    }//GEN-LAST:event_A07ActionPerformed

    private void A08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A08ActionPerformed
        accionAsiento(A08);
    }//GEN-LAST:event_A08ActionPerformed

    private void A09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A09ActionPerformed
        accionAsiento(A09);
    }//GEN-LAST:event_A09ActionPerformed

    private void B01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B01ActionPerformed
        accionAsiento(B01);
    }//GEN-LAST:event_B01ActionPerformed

    private void B02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B02ActionPerformed
        accionAsiento(B02);
    }//GEN-LAST:event_B02ActionPerformed

    private void B03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B03ActionPerformed
        accionAsiento(B03);
    }//GEN-LAST:event_B03ActionPerformed

    private void B04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B04ActionPerformed
        accionAsiento(B04);
    }//GEN-LAST:event_B04ActionPerformed

    private void B05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B05ActionPerformed
        accionAsiento(B05);
    }//GEN-LAST:event_B05ActionPerformed

    private void B07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B07ActionPerformed
        accionAsiento(B07);
    }//GEN-LAST:event_B07ActionPerformed

    private void B08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B08ActionPerformed
        accionAsiento(B08);
    }//GEN-LAST:event_B08ActionPerformed

    private void B09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B09ActionPerformed
        accionAsiento(B09);
    }//GEN-LAST:event_B09ActionPerformed

    private void C03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C03ActionPerformed
        accionAsiento(C03);
    }//GEN-LAST:event_C03ActionPerformed

    private void C04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C04ActionPerformed
        accionAsiento(C04);
    }//GEN-LAST:event_C04ActionPerformed

    private void C05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C05ActionPerformed
        accionAsiento(C05);
    }//GEN-LAST:event_C05ActionPerformed

    private void C07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C07ActionPerformed
        accionAsiento(C07);
    }//GEN-LAST:event_C07ActionPerformed

    private void C08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C08ActionPerformed
        accionAsiento(C08);
    }//GEN-LAST:event_C08ActionPerformed

    private void C09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C09ActionPerformed
        accionAsiento(C09);
    }//GEN-LAST:event_C09ActionPerformed

    private void D03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D03ActionPerformed
        accionAsiento(D03);
    }//GEN-LAST:event_D03ActionPerformed

    private void D04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D04ActionPerformed
        accionAsiento(D04);
    }//GEN-LAST:event_D04ActionPerformed

    private void D05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D05ActionPerformed
        accionAsiento(D05);
    }//GEN-LAST:event_D05ActionPerformed

    private void D07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D07ActionPerformed
        accionAsiento(D07);
    }//GEN-LAST:event_D07ActionPerformed

    private void D08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D08ActionPerformed
        accionAsiento(D08);
    }//GEN-LAST:event_D08ActionPerformed

    private void D09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D09ActionPerformed
        accionAsiento(D09);
    }//GEN-LAST:event_D09ActionPerformed

    private void E01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E01ActionPerformed
        accionAsiento(E01);
    }//GEN-LAST:event_E01ActionPerformed

    private void E02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E02ActionPerformed
        accionAsiento(E02);
    }//GEN-LAST:event_E02ActionPerformed

    private void E03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E03ActionPerformed
        accionAsiento(E03);
    }//GEN-LAST:event_E03ActionPerformed

    private void E04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E04ActionPerformed
        accionAsiento(E04);
    }//GEN-LAST:event_E04ActionPerformed

    private void E05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E05ActionPerformed
        accionAsiento(E05);
    }//GEN-LAST:event_E05ActionPerformed

    private void E07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E07ActionPerformed
        accionAsiento(E07);
    }//GEN-LAST:event_E07ActionPerformed

    private void E08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E08ActionPerformed
        accionAsiento(E08);
    }//GEN-LAST:event_E08ActionPerformed

    private void E09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E09ActionPerformed
        accionAsiento(E09);
    }//GEN-LAST:event_E09ActionPerformed

    private void F01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F01ActionPerformed
        accionAsiento(F01);
    }//GEN-LAST:event_F01ActionPerformed

    private void F02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F02ActionPerformed
        accionAsiento(F02);
    }//GEN-LAST:event_F02ActionPerformed

    private void F03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F03ActionPerformed
        accionAsiento(F03);
    }//GEN-LAST:event_F03ActionPerformed

    private void F04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F04ActionPerformed
        accionAsiento(F04);
    }//GEN-LAST:event_F04ActionPerformed

    private void F05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F05ActionPerformed
        accionAsiento(F05);
    }//GEN-LAST:event_F05ActionPerformed

    private void F07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F07ActionPerformed
        accionAsiento(F07);
    }//GEN-LAST:event_F07ActionPerformed

    private void F08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F08ActionPerformed
        accionAsiento(F08);
    }//GEN-LAST:event_F08ActionPerformed

    private void F09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F09ActionPerformed
        accionAsiento(F09);
    }//GEN-LAST:event_F09ActionPerformed

    private void JBcancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBcancelarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_JBcancelarActionPerformed

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
            java.util.logging.Logger.getLogger(JFAsientosVuelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFAsientosVuelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFAsientosVuelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFAsientosVuelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                    new JFAsientosVuelo(null).setVisible(true);
            }
        });
    }

    public void addToggleButtons(){
                
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton A01;
    private javax.swing.JToggleButton A02;
    private javax.swing.JToggleButton A03;
    private javax.swing.JToggleButton A04;
    private javax.swing.JToggleButton A05;
    private javax.swing.JToggleButton A06;
    private javax.swing.JToggleButton A07;
    private javax.swing.JToggleButton A08;
    private javax.swing.JToggleButton A09;
    private javax.swing.JToggleButton B01;
    private javax.swing.JToggleButton B02;
    private javax.swing.JToggleButton B03;
    private javax.swing.JToggleButton B04;
    private javax.swing.JToggleButton B05;
    private javax.swing.JToggleButton B06;
    private javax.swing.JToggleButton B07;
    private javax.swing.JToggleButton B08;
    private javax.swing.JToggleButton B09;
    private javax.swing.JToggleButton C03;
    private javax.swing.JToggleButton C04;
    private javax.swing.JToggleButton C05;
    private javax.swing.JToggleButton C06;
    private javax.swing.JToggleButton C07;
    private javax.swing.JToggleButton C08;
    private javax.swing.JToggleButton C09;
    private javax.swing.JToggleButton D03;
    private javax.swing.JToggleButton D04;
    private javax.swing.JToggleButton D05;
    private javax.swing.JToggleButton D06;
    private javax.swing.JToggleButton D07;
    private javax.swing.JToggleButton D08;
    private javax.swing.JToggleButton D09;
    private javax.swing.JToggleButton E01;
    private javax.swing.JToggleButton E02;
    private javax.swing.JToggleButton E03;
    private javax.swing.JToggleButton E04;
    private javax.swing.JToggleButton E05;
    private javax.swing.JToggleButton E06;
    private javax.swing.JToggleButton E07;
    private javax.swing.JToggleButton E08;
    private javax.swing.JToggleButton E09;
    private javax.swing.JToggleButton F01;
    private javax.swing.JToggleButton F02;
    private javax.swing.JToggleButton F03;
    private javax.swing.JToggleButton F04;
    private javax.swing.JToggleButton F05;
    private javax.swing.JToggleButton F06;
    private javax.swing.JToggleButton F07;
    private javax.swing.JToggleButton F08;
    private javax.swing.JToggleButton F09;
    private javax.swing.JButton JBaceptar;
    private javax.swing.JButton JBcancelar;
    private javax.swing.JTextArea JTAasientosEscogidos;
    private javax.swing.JTextArea JTAasientosSeleccionados;
    private javax.swing.JTextArea JTAasientosSeleccionados1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
