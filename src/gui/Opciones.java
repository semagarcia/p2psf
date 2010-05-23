
package gui;

import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * Ventana que muestra la configuración básica de la aplicación
 * @author sema
 */
public class Opciones extends javax.swing.JDialog {
    private int _tam;
    private int _puerto;
    private int _nConex;
    private String _iplocal;
    private String _ipservidor;
    private ClienteP2P _interfaz;
    private String _rutaDescargas;
    
    /** Creates new form Help */
    public Opciones(JFrame parent, String ipservidor, String iplocal, int puerto, int tam, int nConex, String ruta, ClienteP2P interfaz) {
        super(parent, true); // El true es para hacer modal a la ventana
        
        _ipservidor=ipservidor;
        _iplocal=iplocal;
        _puerto=puerto;
        _tam=tam;
        _nConex=nConex;
        _interfaz=interfaz;
        _rutaDescargas=ruta;
        
        initComponents();

        // Centramos la ventana en la pantalla (Sólo por NetBeans)
        setLocationRelativeTo(null);
    }

    /** 
     * Inicialización de otros componentes
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tituloDialog = new javax.swing.JLabel();
        etiquetaConexMax = new javax.swing.JLabel();
        etiquetaTamBloque = new javax.swing.JLabel();
        cajaConexMax = new javax.swing.JTextField();
        cajaTamBloque = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        etiquetaIPServidor = new javax.swing.JLabel();
        cajaIPServidor = new javax.swing.JTextField();
        etiquetaIPLocal= new javax.swing.JLabel();
        cajaIPLocal = new javax.swing.JTextField();
        etiquetaPuerto = new javax.swing.JLabel();
        cajaPuerto = new javax.swing.JTextField();
        etiquetaRuta= new javax.swing.JLabel();
        cajaRuta = new javax.swing.JTextField();
        botonCancelar = new javax.swing.JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Opciones de configuración de P2PSF");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        tituloDialog.setText("Opciones configurables:");

        etiquetaConexMax.setText("Número máximo de conexiones simultáneas:");

        etiquetaTamBloque.setText("Tamaño de los bloques de petición de partes:");

        cajaConexMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cajaConexMax.setText(String.valueOf(_nConex));
        cajaConexMax.addKeyListener(new java.awt.event.KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER) botonAceptarActionPerformed(null);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
        });

        cajaTamBloque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cajaTamBloque.setText(String.valueOf(_tam));
        cajaTamBloque.addKeyListener(new java.awt.event.KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER) botonAceptarActionPerformed(null);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
        });

        etiquetaIPServidor.setText("Dirección IP del Servidor P2PSF");

        cajaIPServidor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cajaIPServidor.setText(_ipservidor);
        cajaIPServidor.addKeyListener(new java.awt.event.KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER) botonAceptarActionPerformed(null);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
        });

        etiquetaIPLocal.setText("Dirección IP local");

        cajaIPLocal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cajaIPLocal.setText(_iplocal);
        cajaIPLocal.addKeyListener(new java.awt.event.KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER) botonAceptarActionPerformed(null);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
        });

        etiquetaPuerto.setText("Puerto a través del cual se va a conectar");

        cajaPuerto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cajaPuerto.setText(String.valueOf(_puerto));
        cajaPuerto.addKeyListener(new java.awt.event.KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER) botonAceptarActionPerformed(null);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
        });

        etiquetaRuta.setText("Ruta para las descargas");

        cajaRuta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cajaRuta.setText(String.valueOf(_rutaDescargas));
        cajaRuta.addKeyListener(new java.awt.event.KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER) botonAceptarActionPerformed(null);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
        });

        botonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/ok.png"))); // NOI18N
        botonAceptar.setText("¡Aceptar!");
        botonAceptar.addKeyListener(new java.awt.event.KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER) botonAceptarActionPerformed(null);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
        });
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/noOk.png"))); // NOI18N
        botonCancelar.setText("¡Cancelar!");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetaTamBloque)
                    .addComponent(etiquetaConexMax)
                    .addComponent(etiquetaIPServidor)
                    .addComponent(etiquetaIPLocal)
                    .addComponent(etiquetaPuerto)
                    .addComponent(etiquetaRuta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cajaRuta, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(cajaConexMax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(cajaTamBloque, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(cajaIPServidor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(cajaIPLocal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(cajaPuerto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(150, Short.MAX_VALUE)
                .addComponent(botonAceptar)
                .addGap(18, 18, 18)
                .addComponent(botonCancelar)
                .addGap(143, 143, 143))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaConexMax)
                    .addComponent(cajaConexMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaTamBloque)
                    .addComponent(cajaTamBloque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaIPServidor)
                    .addComponent(cajaIPServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaIPLocal)
                    .addComponent(cajaIPLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaPuerto)
                    .addComponent(cajaPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetaRuta))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        pack();
    }

    /**
     * Método que captura los valores introducidos y los devuelve a la interfaz
     * @param evt
     */
    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        // Si es mayor que 0, devolvemos el valor
        if(Integer.parseInt(cajaTamBloque.getText()) > 0)
            _tam = Integer.parseInt(cajaTamBloque.getText());
        else
            _tam = 0;

        // Lo mismo con las conexiones mÃ¡ximas
        if(Integer.parseInt(cajaConexMax.getText()) > 0)
            _nConex = Integer.parseInt(cajaConexMax.getText());
        else
            _nConex = 0;
        
        // Direccion IP del Servidor
        _ipservidor = cajaIPServidor.getText();

        // Direccion IP local
        _iplocal = cajaIPLocal.getText();

        // Puerto
        if(Integer.parseInt(cajaPuerto.getText()) > 0)
           _puerto = Integer.parseInt(cajaPuerto.getText());
        else
           _puerto = 0;

        _rutaDescargas = cajaRuta.getText();
        if(!_rutaDescargas.endsWith("/"))
        	_rutaDescargas=_rutaDescargas.concat("/");
        
        try {
			_interfaz.establecerOpciones(_tam, _nConex, _ipservidor, _iplocal, _puerto, _rutaDescargas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        this.setVisible(false);
        
        if(_interfaz.conectado()) {
        	_interfaz.opcionArchivoLogoutActionPerformed(null);
        	_interfaz.opcionArchivoLoginActionPerformed(null);
        }
    }

    /**
     * Método que deja los valores intactos, es decir, ignora los cambios
     * introducidos en las cajas de texto
     * @param evt
     */
    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        this.setVisible(false);
    }

    // Variables declaration
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JTextField cajaConexMax;
    private javax.swing.JTextField cajaIPServidor;
    private javax.swing.JTextField cajaIPLocal;
    private javax.swing.JTextField cajaPuerto;
    private javax.swing.JTextField cajaRuta;
    private javax.swing.JTextField cajaTamBloque;
    private javax.swing.JLabel etiquetaConexMax;
    private javax.swing.JLabel etiquetaIPServidor;
    private javax.swing.JLabel etiquetaIPLocal;
    private javax.swing.JLabel etiquetaPuerto;
    private javax.swing.JLabel etiquetaRuta;
    private javax.swing.JLabel etiquetaTamBloque;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel tituloDialog;
}
