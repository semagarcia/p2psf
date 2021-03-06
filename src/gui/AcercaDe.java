package gui;


/**
 * Clase que representa a la ventana "AcercaDe" de la aplicación principal
 */
public class AcercaDe extends javax.swing.JDialog {
	/** Creates new form Help */
    public AcercaDe(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // Centramos la ventana en la pantalla (Sólo por NetBeans)
        setLocationRelativeTo(null);
    }

    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AcercaDe dialog = new AcercaDe(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    
    /** 
	  *  Método que hace la inicializacion de varios componentes
    */
   private void initComponents() {
       panelImagen = new javax.swing.JPanel();
       logoCORBA = new javax.swing.JLabel();
       panelCreditos = new javax.swing.JPanel();
       linea1 = new javax.swing.JLabel();
       linea2 = new javax.swing.JLabel();
       linea3 = new javax.swing.JLabel();
       linea4 = new javax.swing.JLabel();
       linea5 = new javax.swing.JLabel();
       linea6 = new javax.swing.JLabel();
       linea7 = new javax.swing.JLabel();
       botonVolver = new javax.swing.JButton();

       setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
       setTitle("Acerca de Sticky");

       panelImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

       logoCORBA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
       logoCORBA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/logoCORBA.gif"))); // NOI18N

       javax.swing.GroupLayout panelImagenLayout = new javax.swing.GroupLayout(panelImagen);
       panelImagen.setLayout(panelImagenLayout);
       panelImagenLayout.setHorizontalGroup(
           panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addComponent(logoCORBA)
       );
       panelImagenLayout.setVerticalGroup(
           panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addComponent(logoCORBA)
       );

       panelCreditos.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

       linea1.setText("Práctica de Sistemas Operativos Distribuídos");

       linea2.setText("..:: Cliente para red P2P Distribuído ::..");

       linea3.setText("1º Ingeniería Superior en Informática");

       linea4.setText("Curso Académico 2009 / 2010");

       linea5.setText(" Creado por:");

       linea6.setText("José Manuel García García");

       linea7.setText("Francisco Javier Navas Torres");

       botonVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/next2.png"))); // NOI18N
       botonVolver.setText("¡Volver!");
       botonVolver.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               botonVolverActionPerformed(evt);
           }
       });

       javax.swing.GroupLayout panelCreditosLayout = new javax.swing.GroupLayout(panelCreditos);
       panelCreditos.setLayout(panelCreditosLayout);
       panelCreditosLayout.setHorizontalGroup(
           panelCreditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(panelCreditosLayout.createSequentialGroup()
               .addGroup(panelCreditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(panelCreditosLayout.createSequentialGroup()
                       .addContainerGap()
                       .addComponent(linea1))
                   .addGroup(panelCreditosLayout.createSequentialGroup()
                       .addGap(114, 114, 114)
                       .addComponent(botonVolver))
                   .addGroup(panelCreditosLayout.createSequentialGroup()
                       .addContainerGap()
                       .addGroup(panelCreditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addComponent(linea5)
                           .addGroup(panelCreditosLayout.createSequentialGroup()
                               .addGap(56, 56, 56)
                               .addComponent(linea6))))
                   .addGroup(panelCreditosLayout.createSequentialGroup()
                       .addGap(33, 33, 33)
                       .addGroup(panelCreditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                           .addComponent(linea3)
                           .addComponent(linea2))))
               .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCreditosLayout.createSequentialGroup()
               .addContainerGap(61, Short.MAX_VALUE)
               .addComponent(linea4)
               .addGap(58, 58, 58))
           .addGroup(panelCreditosLayout.createSequentialGroup()
               .addGap(59, 59, 59)
               .addComponent(linea7)
               .addContainerGap(66, Short.MAX_VALUE))
       );
       panelCreditosLayout.setVerticalGroup(
           panelCreditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(panelCreditosLayout.createSequentialGroup()
               .addGap(19, 19, 19)
               .addComponent(linea1)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
               .addComponent(linea2)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addComponent(linea3)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addComponent(linea4)
               .addGap(14, 14, 14)
               .addComponent(linea5)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
               .addComponent(linea6)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
               .addComponent(linea7)
               .addGap(18, 18, 18)
               .addComponent(botonVolver)
               .addContainerGap())
       );

       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
       getContentPane().setLayout(layout);
       layout.setHorizontalGroup(
           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(layout.createSequentialGroup()
               .addContainerGap()
               .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addComponent(panelCreditos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
       );
       layout.setVerticalGroup(
           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(layout.createSequentialGroup()
               .addContainerGap()
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                   .addComponent(panelCreditos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                   .addComponent(panelImagen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
       );

       pack();
   }

   
   /**
    * Método que oculta la ventana cuando se pulsa en el botón volver
    * @param evt
    */
   private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {
       // Cuando se pulse sobre el botón Volver se oculta esta ventana
       this.setVisible(false);
   }


    // Variables declaration
    private javax.swing.JButton botonVolver;
    private javax.swing.JLabel linea1;
    private javax.swing.JLabel linea2;
    private javax.swing.JLabel linea3;
    private javax.swing.JLabel linea4;
    private javax.swing.JLabel linea5;
    private javax.swing.JLabel linea6;
    private javax.swing.JLabel linea7;
    private javax.swing.JLabel logoCORBA;
    private javax.swing.JPanel panelCreditos;
    private javax.swing.JPanel panelImagen;

    private static final long serialVersionUID = 1L;
}
