package gui;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import javax.swing.DefaultListModel; 
import javax.swing.JProgressBar;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import cliente.Downloader;
import cliente.EstrArchivo;
import cliente.UsuarioClient;
import cliente.infoArchivo;
import cliente.parteArchivo;
import coordinador.Archivo;


/**
 * Interfaz Gráfica Principal de la Aplicación P2PSF
 */
public class ClienteP2P extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private Buscando _hiloBusqueda;
	private Hashtable<Integer,Archivo> _tablaResBusqueda;
	private Hashtable<String,Downloader> _descargasActuales;
	private Hashtable<EstrArchivo, JProgressBar> _descargasPendientes;
    private int _tamBloque;
    private int _conexionesMaximas;
    private String _ipservidor;
    private String _iplocal;
    private int _puerto;
	private String _rutaDescargas;
    private String _nombreBiblioteca;
    private boolean _conectado = false;
    private MenuContextual _menuContextual;
    private DefaultTableModel _modeloTablaDescargas = new DefaultTableModel();
    private DefaultListModel _modeloListaRecursos = new DefaultListModel();
    private DefaultTableModel _modeloTablaBusqueda = new DefaultTableModel();
    private UsuarioClient _cliente;
    private ArrayList<EstrArchivo> _easTmp;
    
    /** Constructor del ClienteP2P */
    public ClienteP2P() {
    	_hiloBusqueda = null;
    	_tablaResBusqueda=new Hashtable<Integer,Archivo>();
    	_descargasActuales=new Hashtable<String,Downloader>();
    	_descargasPendientes=new Hashtable<EstrArchivo, JProgressBar>();
    	_easTmp=new ArrayList<EstrArchivo>();
    	
    	try {
			cargarOpciones();
		}
    	catch (IOException e1) {
			e1.printStackTrace();
		}  	
    	    	
        // Inicialización de componentes: interfaz, escuchadores, manejadores...
        initComponents();

        // Desactiva el boton para desconectarse y el de búsqueda
    	iconDesconectar.setEnabled(false);
    	botonBuscar.setEnabled(false);
        
        // Centramos en la pantalla la ventana de la aplicación (sólo por NetBeans)
        setLocationRelativeTo(null);

        // Por defecto, el nombre de la biblioteca es "MiBiblioteca.xml";
        _nombreBiblioteca = "MiBiblioteca.xml";

        // Por defecto, la animación para buscar archivos está oculta
        _loaderBuscando.setVisible(false);

        // Para el JList especificar que posee Mi ListCellRenderer modificado
        ListCellRenderer _renderer = new MiListaRenderer();
        _listaFicherosCompartidos.setModel(_modeloListaRecursos);
        _listaFicherosCompartidos.setCellRenderer(_renderer);

        // Para especificar la cabecera de la tabla Resultados
        _tablaResultados.setModel(_modeloTablaBusqueda);
        Object [] headersResultados = {"Nombre Archivo", "Tamaño", "Nº Seeds", "Nº Peers", "Checksum"};
        _modeloTablaBusqueda.setColumnIdentifiers(headersResultados);

        // Para especificar todo lo relativo a la tabla de las descargas
        // Asignamos la cabecera de la tabla
        _modeloTablaDescargas.setColumnIdentifiers(new Object []{"Archivo", "Ruta al archivo", "Porcentaje Completado", "Conexiones actuales"});
        _tablaDescargas.setModel(_modeloTablaDescargas); // Indicamos qué modelo va a utilizar la tabla        
        // Hacemos que la tercera columna (la de )
        //_tablaDescargas.getColumn("Progress").setCellRenderer(new MiBarraProgreso());
        _tablaDescargas.getColumn("Porcentaje Completado").setCellRenderer(new MiBarraProgreso());
        
        // Implementación del menú contextual para la tabla de descargas
        _menuContextual = new MenuContextual(_tablaDescargas, _descargasActuales, this);
        
        // Al iniciar la aplicación se cargan desde el fichero XML, la lista
        // de ficheros/recursos compartidos
        cargarBiblioteca();
    }
    
    /**
     * Meétodo que limpia la tabla de los resultados de la búsqueda
     */
	public void limpiarResultadosBusqueda() {
        _tablaResBusqueda.clear();
        for(int i=0;i<_modeloTablaBusqueda.getRowCount();i++)
        	_modeloTablaBusqueda.removeRow(i);		
	}

    /** Otras inicializaciones */
    private void initComponents() {
        panelPpal = new javax.swing.JPanel();
        pestanyasApp = new javax.swing.JTabbedPane();
        pestaniaBiblioteca = new javax.swing.JPanel();
        panelMiBiblio = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        _listaFicherosCompartidos = new javax.swing.JList();
        panelAniadirFichero = new javax.swing.JPanel();
        anyadirRecurso = new javax.swing.JLabel();
        quitarRecurso = new javax.swing.JLabel();
        actualizarBiblioteca = new javax.swing.JLabel();
        panelBusqueda = new javax.swing.JPanel();
        panelNuevaBusqueda = new javax.swing.JPanel();
        labelFicheroBuscar = new javax.swing.JLabel();
        cajaTextoNomFich = new javax.swing.JTextField();
        botonBuscar = new javax.swing.JButton();
        _loaderBuscando = new javax.swing.JLabel();
        panelResultadosBusqueda = new javax.swing.JPanel();
        scrollPaneResultados = new javax.swing.JScrollPane();
        _tablaResultados = new javax.swing.JTable();
        pestaniaDescargas = new javax.swing.JPanel();
        panelDescargas = new javax.swing.JPanel();
        scrollPaneDescargas = new javax.swing.JScrollPane();
        _tablaDescargas = new javax.swing.JTable();
        panelInferiorIzqdo = new javax.swing.JPanel();
        iconConectar = new javax.swing.JButton();
        iconDesconectar = new javax.swing.JButton();
        separador1 = new javax.swing.JToolBar.Separator();
        iconAyuda = new javax.swing.JButton();
        iconSalir = new javax.swing.JButton();
        panelInferiorDrcho = new javax.swing.JPanel();
        valorEstadoActual = new javax.swing.JLabel();
        menuPpal = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        opcionArchivoLogin = new javax.swing.JMenuItem();
        opcionArchivoLogout = new javax.swing.JMenuItem();
        opcionArchivoOpciones = new javax.swing.JMenuItem();
        separadorArchivo = new javax.swing.JPopupMenu.Separator();
        opcionArchivoSalir = new javax.swing.JMenuItem();
        menuP2PSF = new javax.swing.JMenu();
        opcionP2PBiblioteca = new javax.swing.JMenuItem();
        opcionP2PBusqueda = new javax.swing.JMenuItem();
        opcionP2PDescargas = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        ayudaAyuda = new javax.swing.JMenuItem();
        ayudaAcercaDe = new javax.swing.JMenuItem();

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cliente de redes P2P");
        setMinimumSize(new java.awt.Dimension(400, 600));
        setResizable(false);
        
        addWindowListener(new java.awt.event.WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println(arg0.toString());
				javax.swing.JOptionPane.showMessageDialog(rootPane, "Hey!");
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {}      	  
        });

        panelPpal.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelMiBiblio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Ficheros compartidos actualmente (Mi Biblioteca)"));

        _listaFicherosCompartidos.setModel(new javax.swing.AbstractListModel() {

			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(_listaFicherosCompartidos);

        javax.swing.GroupLayout panelMiBiblioLayout = new javax.swing.GroupLayout(panelMiBiblio);
        panelMiBiblio.setLayout(panelMiBiblioLayout);
        panelMiBiblioLayout.setHorizontalGroup(
            panelMiBiblioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiBiblioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMiBiblioLayout.setVerticalGroup(
            panelMiBiblioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiBiblioLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelAniadirFichero.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Opciones de compartición"));

        anyadirRecurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/anyadirFichero.png"))); // NOI18N
        anyadirRecurso.setText("Añadir un nuevo recurso a \"Mi Biblioteca\"");
        anyadirRecurso.setToolTipText("Añade un nuevo fichero (o ficheros) del disco local a Mi Biblioteca");
        anyadirRecurso.addMouseListener(new java.awt.event.MouseAdapter() {
            // Función que se activa cuando se hace un click sobre el botón/label
      	   public void mouseClicked(java.awt.event.MouseEvent evt) {
                anyadirRecursoMouseClicked(evt);
            }
            // Función que se activa cuando se sitúa el cursor encima del botón/label
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            // Función que se activa cuando se saca el cursor de encima del botón/label
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        quitarRecurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/quitarFichero.png"))); // NOI18N
        quitarRecurso.setText("Quitar los recursos seleccionados");
        quitarRecurso.setToolTipText("Quita el o los recursos compartidos que se encuentren seleccionados en \"Mi Biblioteca\"");
        quitarRecurso.addMouseListener(new java.awt.event.MouseAdapter() {
       	   // Función que se activa al pulsar sobre el botón/label
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quitarRecursoMouseClicked(evt);
            }
            // Función que se activa cuando se sitúa el cursor encima del botón/label
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            // Función que se activa cuando se saca el cursor de encima del botón/label
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        actualizarBiblioteca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/actualizarBiblioteca.png"))); // NOI18N
        actualizarBiblioteca.setText("Actualizar \"Mi Biblioteca\" en la red P2PSF");
        actualizarBiblioteca.setToolTipText("Hace efectivos los cambios realizados en \"Mi Biblioteca\" para la red P2P");
        actualizarBiblioteca.addMouseListener(new java.awt.event.MouseAdapter() {
            // Función que se activa al pulsar sobre el botón/label
      	   public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarBibliotecaMouseClicked(evt);
            }
      	   // Función que se activa cuando se sitúa el cursor encima del botón/label
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            // Función que se activa cuando se saca el cursor de encima del botón/label
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        javax.swing.GroupLayout panelAniadirFicheroLayout = new javax.swing.GroupLayout(panelAniadirFichero);
        panelAniadirFichero.setLayout(panelAniadirFicheroLayout);
        panelAniadirFicheroLayout.setHorizontalGroup(
            panelAniadirFicheroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAniadirFicheroLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(panelAniadirFicheroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAniadirFicheroLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(actualizarBiblioteca))
                    .addComponent(quitarRecurso)
                    .addComponent(anyadirRecurso))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panelAniadirFicheroLayout.setVerticalGroup(
            panelAniadirFicheroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAniadirFicheroLayout.createSequentialGroup()
                .addComponent(anyadirRecurso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quitarRecurso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(actualizarBiblioteca)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pestaniaBibliotecaLayout = new javax.swing.GroupLayout(pestaniaBiblioteca);
        pestaniaBiblioteca.setLayout(pestaniaBibliotecaLayout);
        pestaniaBibliotecaLayout.setHorizontalGroup(
            pestaniaBibliotecaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestaniaBibliotecaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMiBiblio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAniadirFichero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pestaniaBibliotecaLayout.setVerticalGroup(
            pestaniaBibliotecaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestaniaBibliotecaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestaniaBibliotecaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelAniadirFichero, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMiBiblio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pestanyasApp.addTab("Biblioteca de archivos compartidos", pestaniaBiblioteca);

        panelNuevaBusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Realizar nueva búsqueda"));

        labelFicheroBuscar.setText("Nombre del fichero a buscar:");

        cajaTextoNomFich.addKeyListener(new java.awt.event.KeyAdapter() {
        	public void keyReleased(java.awt.event.KeyEvent evt) {
        		cajaTextoNomFichKeyReleased(evt);
        	}
        });
        
        botonBuscar.setText("¡Búscalo!");
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        _loaderBuscando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/buscando.gif"))); // NOI18N
        _loaderBuscando.setText("Buscando...");

        javax.swing.GroupLayout panelNuevaBusquedaLayout = new javax.swing.GroupLayout(panelNuevaBusqueda);
        panelNuevaBusqueda.setLayout(panelNuevaBusquedaLayout);
        panelNuevaBusquedaLayout.setHorizontalGroup(
            panelNuevaBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNuevaBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFicheroBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cajaTextoNomFich, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonBuscar)
                .addGap(18, 18, 18)
                .addComponent(_loaderBuscando)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        panelNuevaBusquedaLayout.setVerticalGroup(
            panelNuevaBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNuevaBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNuevaBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFicheroBuscar)
                    .addComponent(cajaTextoNomFich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscar)
                    .addComponent(_loaderBuscando))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        panelResultadosBusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Resultados de la búsqueda"));

        _tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Archivo", "Tamaño", "Checksum", "Nº Seeds", "Nº Peers"
            }
        ));
        _tablaResultados.setEnabled(false);
        scrollPaneResultados.setViewportView(_tablaResultados);

        javax.swing.GroupLayout panelResultadosBusquedaLayout = new javax.swing.GroupLayout(panelResultadosBusqueda);
        panelResultadosBusqueda.setLayout(panelResultadosBusquedaLayout);
        panelResultadosBusquedaLayout.setHorizontalGroup(
            panelResultadosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadosBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 889, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelResultadosBusquedaLayout.setVerticalGroup(
            panelResultadosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadosBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelBusquedaLayout = new javax.swing.GroupLayout(panelBusqueda);
        panelBusqueda.setLayout(panelBusquedaLayout);
        panelBusquedaLayout.setHorizontalGroup(
            panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelResultadosBusqueda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelNuevaBusqueda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelBusquedaLayout.setVerticalGroup(
            panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelNuevaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelResultadosBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pestanyasApp.addTab("Búsquedas", panelBusqueda);

        panelDescargas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Descargas"));

        _tablaDescargas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Archivo", "Ruta", "Completado"
            }
        ));
        _tablaDescargas.setEnabled(false);
        scrollPaneDescargas.setViewportView(_tablaDescargas);

        javax.swing.GroupLayout panelDescargasLayout = new javax.swing.GroupLayout(panelDescargas);
        panelDescargas.setLayout(panelDescargasLayout);
        panelDescargasLayout.setHorizontalGroup(
            panelDescargasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDescargasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneDescargas, javax.swing.GroupLayout.DEFAULT_SIZE, 889, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelDescargasLayout.setVerticalGroup(
            panelDescargasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDescargasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneDescargas, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pestaniaDescargasLayout = new javax.swing.GroupLayout(pestaniaDescargas);
        pestaniaDescargas.setLayout(pestaniaDescargasLayout);
        pestaniaDescargasLayout.setHorizontalGroup(
            pestaniaDescargasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestaniaDescargasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDescargas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pestaniaDescargasLayout.setVerticalGroup(
            pestaniaDescargasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestaniaDescargasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDescargas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pestanyasApp.addTab("Descargas", pestaniaDescargas);

        javax.swing.GroupLayout panelPpalLayout = new javax.swing.GroupLayout(panelPpal);
        panelPpal.setLayout(panelPpalLayout);
        panelPpalLayout.setHorizontalGroup(
            panelPpalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pestanyasApp, javax.swing.GroupLayout.PREFERRED_SIZE, 965, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelPpalLayout.setVerticalGroup(
            panelPpalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPpalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestanyasApp, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelInferiorIzqdo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Acciones comunes")));

        iconConectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/conectar.png"))); // NOI18N
        iconConectar.setToolTipText("Conectarse a la red P2PSF");
        iconConectar.setFocusable(false);
        iconConectar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconConectar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        iconConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconConectarActionPerformed(evt);
            }
        });

        iconDesconectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/desconectar.png"))); // NOI18N
        iconDesconectar.setToolTipText("Desconectarse de la red P2PSF");
        iconDesconectar.setFocusable(false);
        iconDesconectar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconDesconectar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        iconDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconDesconectarActionPerformed(evt);
            }
        });

        iconAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/ayuda.png"))); // NOI18N
        iconAyuda.setToolTipText("Ayuda");
        iconAyuda.setFocusable(false);
        iconAyuda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconAyuda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        iconAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconAyudaActionPerformed(evt);
            }
        });

        iconSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/salir.png"))); // NOI18N
        iconSalir.setToolTipText("Salir de la aplicación... ¿Seguro?");
        iconSalir.setFocusable(false);
        iconSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        iconSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInferiorIzqdoLayout = new javax.swing.GroupLayout(panelInferiorIzqdo);
        panelInferiorIzqdo.setLayout(panelInferiorIzqdoLayout);
        panelInferiorIzqdoLayout.setHorizontalGroup(
            panelInferiorIzqdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInferiorIzqdoLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(iconConectar)
                .addGap(18, 18, 18)
                .addComponent(iconDesconectar)
                .addGap(18, 18, 18)
                .addComponent(separador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(iconAyuda)
                .addGap(18, 18, 18)
                .addComponent(iconSalir)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        panelInferiorIzqdoLayout.setVerticalGroup(
            panelInferiorIzqdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInferiorIzqdoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInferiorIzqdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(iconSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconDesconectar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(separador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInferiorDrcho.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Estado de la aplicación"));

        valorEstadoActual.setText("Desconectado - En espera");

        javax.swing.GroupLayout panelInferiorDrchoLayout = new javax.swing.GroupLayout(panelInferiorDrcho);
        panelInferiorDrcho.setLayout(panelInferiorDrchoLayout);
        panelInferiorDrchoLayout.setHorizontalGroup(
            panelInferiorDrchoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInferiorDrchoLayout.createSequentialGroup()
                .addGap(46, 46, 46)   // Modificación para el panel inferior derecho
                .addComponent(valorEstadoActual, 190, 190, 190)
                .addContainerGap(240, Short.MAX_VALUE))
        );
        panelInferiorDrchoLayout.setVerticalGroup(
            panelInferiorDrchoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInferiorDrchoLayout.createSequentialGroup()
                .addComponent(valorEstadoActual, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuArchivo.setText("Archivo");

        opcionArchivoLogin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        opcionArchivoLogin.setText("Conectar...");
        opcionArchivoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionArchivoLoginActionPerformed(evt);
            }
        });
        menuArchivo.add(opcionArchivoLogin);

        opcionArchivoLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        opcionArchivoLogout.setText("Desconectar...");
        opcionArchivoLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionArchivoLogoutActionPerformed(evt);
            }
        });
        menuArchivo.add(opcionArchivoLogout);

        opcionArchivoOpciones.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        opcionArchivoOpciones.setText("Opciones");
        opcionArchivoOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionArchivoOpcionesActionPerformed(evt);
            }
        });
        menuArchivo.add(opcionArchivoOpciones);
        menuArchivo.add(separadorArchivo);

        opcionArchivoSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        opcionArchivoSalir.setText("Salir");
        opcionArchivoSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionArchivoSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(opcionArchivoSalir);

        menuPpal.add(menuArchivo);

        menuP2PSF.setText("P2PSF");

        opcionP2PBiblioteca.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        opcionP2PBiblioteca.setText("Mi Biblioteca");
        opcionP2PBiblioteca.setToolTipText("Muestra la ventana correspondiente a los archivos actualmente compartidos");
        opcionP2PBiblioteca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionP2PBibliotecaActionPerformed(evt);
            }
        });
        menuP2PSF.add(opcionP2PBiblioteca);

        opcionP2PBusqueda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        opcionP2PBusqueda.setText("Nueva Búsqueda");
        opcionP2PBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionP2PBusquedaActionPerformed(evt);
            }
        });
        menuP2PSF.add(opcionP2PBusqueda);

        opcionP2PDescargas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        opcionP2PDescargas.setText("Mis descargas");
        opcionP2PDescargas.setToolTipText("Muestra todas las descargas realizadas");
        opcionP2PDescargas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionP2PDescargasActionPerformed(evt);
            }
        });
        menuP2PSF.add(opcionP2PDescargas);

        menuPpal.add(menuP2PSF);

        menuAyuda.setText("Ayuda");

        ayudaAyuda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        ayudaAyuda.setText("Ayuda");
        ayudaAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ayudaAyudaActionPerformed(evt);
            }
        });
        menuAyuda.add(ayudaAyuda);

        ayudaAcercaDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        ayudaAcercaDe.setText("Acerca de...");
        ayudaAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ayudaAcercaDeActionPerformed(evt);
            }
        });
        menuAyuda.add(ayudaAcercaDe);

        menuPpal.add(menuAyuda);

        setJMenuBar(menuPpal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelInferiorIzqdo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInferiorDrcho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panelPpal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPpal, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelInferiorDrcho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInferiorIzqdo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        
        
        // Captura un doble click sobre la lista de resultados de la búsqueda
        _tablaResultados.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e){
                if(e.getClickCount() == 2){
                    int fila = _tablaResultados.rowAtPoint(e.getPoint());
                    if ((fila > -1)) {
                    	anyadirDescarga(_tablaResBusqueda.get(fila));
                    	pestanyasApp.setSelectedIndex(2);
                    }
                }
            }
        });

        // Captura un click sobre la lista de resultados de la búsqueda
        _tablaResultados.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = _tablaResultados.rowAtPoint(e.getPoint());
                if ((fila > -1))
                    _tablaResultados.setRowSelectionInterval(fila, fila);
            }
        });

        pack();
    }

    /**
     * Método que añade una nueva descarga
     * @param archivo Estructura Archivo que se debe añadir como nueva descarga
     */
    private void anyadirDescarga(Archivo archivo) {
    	if(_conectado) {
    		parteArchivo[] partes=new parteArchivo[1];
    		partes[0]=new parteArchivo(0,0,false,false);
    		nuevaDescarga(archivo, partes, 0);
    		}
	}

    /**
     * Capturador del evento de la tecla enter sobre el campo de búsqueda de archivos
     * @param evt
     */
	private void cajaTextoNomFichKeyReleased(KeyEvent evt) {
    	if(evt.getKeyCode()==KeyEvent.VK_ENTER)
    		botonBuscarActionPerformed(null);
	}

	/**
     * Esta función será la encargada de enviar al coordinador la lista de archivos
     * que posee el usuario localmente y recibe el identificador que éste le envía
     * @param evt
     */
    public void opcionArchivoLoginActionPerformed(java.awt.event.ActionEvent evt) {
    	if(!_conectado) {
    		try {
    			_cliente=new UsuarioClient(_ipservidor,_iplocal,_puerto,this);
    			_conectado = _cliente.conectar();
    			_cliente.anyadir(_easTmp);
    			
    			//Actualiza el id del usuario en las descargas actuales
    			Enumeration<Downloader> descargas = _descargasActuales.elements();
    			while(descargas.hasMoreElements()) {
    				descargas.nextElement().actualizarId(_cliente.getId());
    			}
    			
    			//Recorre las descargas pendientes para crear los hilos downloader
    			Enumeration<EstrArchivo> keys = _descargasPendientes.keys();
    			Archivo a;
    			Downloader d;
    			EstrArchivo e;
    			while(keys.hasMoreElements()) {
    				e=keys.nextElement();
    				a=_cliente.buscar(e.info.nombre);
    				if(a!=null) {
    					long sum=0;
    					for(int i=0;i<e.partes.length;i++) {
    						sum+=e.partes[i].fin-e.partes[i].inicio;
    					}
    					float porcentaje=sum*100/e.info.tam;
    					d=_cliente.descargar(a, e.partes, porcentaje, _conexionesMaximas, _tamBloque, e.info.ruta, _descargasPendientes.remove(e));
    					_descargasActuales.put(e.info.ruta, d);
    				}
    			}
    		}
    		catch(Exception e) {
    			javax.swing.JOptionPane.showMessageDialog(this,
    					"Error: No se puede encontrar el servidor." +
    					" Cambie los parámetros de conexión en el menú" +
    					" \"Opciones\" e inténtelo de nuevo.","Error de conexión", javax.swing.JOptionPane.ERROR_MESSAGE);
    		}

    		if(_conectado) {
    			cambiarEstado("");
    			iconConectar.setEnabled(false);
    			iconDesconectar.setEnabled(true);
    			botonBuscar.setEnabled(true);
        	
    			//Deshabilitar acciones descargas
    			_menuContextual.mostrarParado();
    			cambiarEstado("");
    		}
    	}
    }

    /**
     * Cuando se pulse sobre el icono de conectar se llamará a su correspondiente
     * función (opcionArchivoLoginActionPerformed), el manejador del evento.
     * @param evt
     */
    private void iconConectarActionPerformed(java.awt.event.ActionEvent evt) {
        opcionArchivoLoginActionPerformed(evt);
    }

    /**
     * Cuando se quiera desconectar el usuario, debe comunicarselo al coordinador
     * @param evt
     */
    public void opcionArchivoLogoutActionPerformed(java.awt.event.ActionEvent evt) {
    	if(_conectado) {
    		
    		//Para los hilos Downloader
    		Enumeration<String> rutas= _descargasActuales.keys();
    		Downloader d;
    		String ruta;
    		while(rutas.hasMoreElements()) {
    			ruta=rutas.nextElement();
    			d=_descargasActuales.get(ruta);
    			_descargasActuales.put(ruta, d.parar());
    			//Deshabilitar acciones descargas
    			_menuContextual.parar(d.ruta);
    		}
    		
    		_conectado = !_cliente.desconectar();
    		_cliente=null;
    	
    		if(!_conectado) {
    			cambiarEstado("");
    			iconConectar.setEnabled(true);
    			iconDesconectar.setEnabled(false);
    			botonBuscar.setEnabled(false);
        	
    			limpiarResultadosBusqueda();
    		}
    		else cambiarEstado("Error al desconectar.");
    	}
    }


	/**
     * Cuando se pulse sobre el icono de desconectar se llamará a su correspondiente
     * función (opcionArchivoLogoutActionPerformed), el manejador del evento.
     * @param evt
     */
    private void iconDesconectarActionPerformed(java.awt.event.ActionEvent evt) {
        this.opcionArchivoLogoutActionPerformed(evt);
    }

    /**
     * Muestra la pestaña correspondiente a "Mi Biblioteca"
     * @param evt
     */
    private void opcionP2PBibliotecaActionPerformed(java.awt.event.ActionEvent evt) {
        pestanyasApp.setSelectedIndex(0);
    }

    /**
     * Muestra la pestaña correspondiente a "Busquedas"
     * @param evt
     */
    private void opcionP2PBusquedaActionPerformed(java.awt.event.ActionEvent evt) {
        pestanyasApp.setSelectedIndex(1);
    }

    /**
     * Muestra la pestaña correspondiente a Descargas
     * @param evt
     */
    private void opcionP2PDescargasActionPerformed(java.awt.event.ActionEvent evt) {
        pestanyasApp.setSelectedIndex(2);
    }

    /**
     * Método que eliminar todos los resultados de la búsqueda de un archivo
     * @param evt
     */
    public void opcionP2PLimpiarResultadosActionPerformed(java.awt.event.ActionEvent evt) {
        for (int i=_modeloTablaBusqueda.getRowCount()-1; i>=0; i--)
            _modeloTablaBusqueda.removeRow(i);
    }

    /**
     * Muestra la ventana típica de ayuda
     * @param evt
     */
    private void ayudaAyudaActionPerformed(java.awt.event.ActionEvent evt) {
        // Cuando se pulse sobre la ayuda se abre dicha ventana
        Ayuda a = new Ayuda(this, true); // La instanciamos. Será modal
        a.setVisible(true); // Y la mostramos
    }

    /**
     * Muestra los créditos del programa. Información acerca del mismo.
     * @param evt
     */
    private void ayudaAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {
        // Cuando se pulse sobre la ayuda se abre dicha ventana
        AcercaDe ad = new AcercaDe(this, true); // La instanciamos. Será modal
        ad.setVisible(true); // Y la mostramos
    }

    /**
     * Método que llama a la función encargada de mostrar la ayuda
     * @param evt
     */
    private void iconAyudaActionPerformed(java.awt.event.ActionEvent evt) {
        this.ayudaAyudaActionPerformed(evt);
    }

    /**
     * Método que llama la función encargada de mostrar el "acerca de"
     * @param evt
     */
    private void iconSalirActionPerformed(java.awt.event.ActionEvent evt) {
        // Si ha pulsado sobre el icono de salir se llama a la función correspondiente
        this.opcionArchivoSalirActionPerformed(evt);
    }

    /**
     * Método encargado de cerrar la aplicación. Para ello, antes lo notifica
     * al coordinador y luego cierra la aplicación.
     * @param evt
     */
    private void opcionArchivoSalirActionPerformed(java.awt.event.ActionEvent evt) {
        // 1 - Decirle al coordinador que nos vamos
        opcionArchivoLogoutActionPerformed(evt);

        // 2 - Cerrar la aplicación
        System.exit(0);
    }

    /**
     * Cuando se pulse sobre el botón "Búscalo" se instancia el hilo encargado
     * de la visualización y ocultamiento de la animación
     * @param evt
     */
    @SuppressWarnings("deprecation")
	private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {
    	if(_conectado) {
    		if(_hiloBusqueda!=null)
    			_hiloBusqueda.stop();            	
        	
    		_hiloBusqueda = new Buscando(this, _loaderBuscando, _cliente, _tablaResBusqueda);
    		
    		_hiloBusqueda.setNombre(cajaTextoNomFich.getText());
    		_hiloBusqueda.start(); // Lanza el hilo
    	}
    }

    /**
     * Método encargado de abrir una ventana (JFileChooser) para escoger los
     * archivos (permite selección múltiple) de nuevos archivos que van a ser
     * compartidos por el usuario
     * @param evt
     */
    private void anyadirRecursoMouseClicked(java.awt.event.MouseEvent evt) {
        // Cuando se pulse en el botón Añadir Recurso
        int opc = 0;
        File [] ficheros;

        // Creamos una ventana para que el usuario escoja su(s) fichero(s)
        javax.swing.JFileChooser anyadir = new javax.swing.JFileChooser();
        anyadir.setMultiSelectionEnabled(true); // Multiselección
        anyadir.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);

        opc = anyadir.showOpenDialog(this); // Se muestra el diálogo

        EstrArchivo eA;
        infoArchivo iA;
        parteArchivo[] pA;
        
        // Ahora comprobamos si ha pulsado sobre aceptar
        if (opc == javax.swing.JFileChooser.APPROVE_OPTION) {
            ficheros = anyadir.getSelectedFiles(); // Obtenemos los ficheros seleccionados
            //String [] fich = new String[ficheros.length];
            for (int i=0; i<ficheros.length; i++) {
            	iA=getInfoArchivo(ficheros[i]);
            	pA=new parteArchivo[1];
            	pA[0]=new parteArchivo();
            	pA[0].inicio=0;
            	pA[0].fin=iA.tam;
            	pA[0].pedido=false;
            	pA[0].descargado=false;
            	eA=new EstrArchivo(iA,pA);
            	_easTmp.add(eA);
                _modeloListaRecursos.addElement(ficheros[i].getPath());
            }
        }
    }
    

    /**
     * Función que obtiene la informacion de un fichero
     * @param file
     * @return
     */
    private infoArchivo getInfoArchivo(File file) {
   	 	infoArchivo aux = new infoArchivo();
        CheckedInputStream suma = null;

        try { // Se lee el fichero y se va creando el checksum
        	FileInputStream f = new FileInputStream(file);
            suma = new CheckedInputStream(f, new CRC32());
            BufferedInputStream in = new BufferedInputStream(suma);

          	while (in.read() != -1) {  }
            in.close(); // Cerramos el fichero
        }
        catch (IOException ex) {
      	  javax.swing.JOptionPane.showMessageDialog(this, 
      			  "¡Error al calcular el checksum de \""+file.getName()+"\"!");
        }

        // Asignamos la información del archivo
        aux.nombre=file.getName();
        aux.ruta=file.getPath();
        aux.checksum=suma.getChecksum().getValue();
        aux.tam=file.length();    	

        return aux;
	}

	/**
     * Cuando se pulse sobre el botón eliminar se evalúa si hay recursos seleccionados,
     * y en caso de existir, se eliminan. Si no, se muestra un aviso
     * @param evt
     */
    private void quitarRecursoMouseClicked(java.awt.event.MouseEvent evt) {
        // Obtenemos el indice que ocupan los elementos que se han seleccionado
        int [] indices = _listaFicherosCompartidos.getSelectedIndices();
        String fichero; 
        String nombre=new String();
               
        // Si no hay ningun elemento seleccionado
        if (indices.length == 0)
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No ha seleccionado ningún elemento para eliminar");
        
        // Eliminamos desde el final para evitar problemas con los índices
        for (int i=indices.length - 1; i>=0; i--) {
            fichero=(String) _modeloListaRecursos.getElementAt(indices[i]);
            nombre=fichero.substring(fichero.lastIndexOf("/")+1,fichero.length());
            int j=0;
            while(j<_easTmp.size()) {
            	if(_easTmp.get(j).info.nombre.equals(nombre)) {
            		_easTmp.remove(j);
            		j=_easTmp.size();
            	} else j++;
            }
            _modeloListaRecursos.removeElementAt(indices[i]);
        }
    }

    /**
     * Cuando se pulse sobre el botón de actualizar la bibioteca se examinan los
     * archivos que hay en la lista y se genera un XML con dicha información
     * @param evt
     */
    void actualizarBibliotecaMouseClicked(java.awt.event.MouseEvent evt) {
    	String resultado="";
    	
    	if(almacenarBiblioteca()) // Comprobamos que se ha guardado correctamente
    	    resultado+="¡Archivo \""+_nombreBiblioteca+"\" generado correctamente!";
        else // Error al guardar el archivo
            resultado+="Error al generar el archivo \""+_nombreBiblioteca+"\"";
        
        if(_conectado)
        	_cliente.anyadir(_easTmp);
        // Mostramos el mensaje mediante una ventanita
        javax.swing.JOptionPane.showMessageDialog(this,resultado);
    }

    private boolean almacenarBiblioteca() {
    	Biblioteca bib = new Biblioteca(); // Instanciamos el objeto Biblioteca
        boolean resultado=false;
        
        // Recorremos todos los elementos de la lista y los metemos en el xml
        for (int i=0; i<_easTmp.size(); i++) {
            bib.insertarNuevoArchivo(_easTmp.get(i));
        }

        if(bib.guardarXML()) // Archivo guardado correctamente
            resultado=true;

		return resultado;
	}

	/**
     * Método que muestra la ventana de configuración de la aplicación
     * @param evt
     */
    private void opcionArchivoOpcionesActionPerformed(java.awt.event.ActionEvent evt) {
        Opciones opciones = new Opciones(this, _ipservidor, _iplocal, _puerto, _tamBloque, _conexionesMaximas, _rutaDescargas, this);
        opciones.setVisible(true);
    }

    /**
     * Método que muestra un mensaje en la barra de estado. Si se llama con la
     * cadena vacía, se asignará el mensaje de estado por defecto.
     * @param estado Mensaje a mostrar en la barra de estado
     */
    public void cambiarEstado(String estado) {
        if (!estado.equals("")) // Si el estado contiene algo
            valorEstadoActual.setText(estado);
        else // Si la cadena está vacía, se pone el estado por defecto
            if (_conectado)
                valorEstadoActual.setText("Conectado");
            else
                valorEstadoActual.setText("Desconectado");
    }

    /**
     * Función que carga en la aplicación todos los ficheros compartidos por el usuario
     */
    private void cargarBiblioteca() {
        ParserXML parser = new ParserXML(_nombreBiblioteca);

        // Parseamos primero el archivo XML para obtener el arbol cargado en memoria
        	try {
				parser.parsearArchivoXML();
			} catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(this, "Error, Excepcion IOException");
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				javax.swing.JOptionPane.showMessageDialog(this, "Error, Excepcion ParserConfigurationException");
				e.printStackTrace();
			} catch (SAXException e) {
				// Cuando el XML esté mal formado, se borra y se sale de la aplicación
				javax.swing.JOptionPane.showMessageDialog(this, 
						"¡Error grave! El fichero biblioteca está mal formado. Reinicie la aplicación.");	
				File xmlBiblio = new File(_nombreBiblioteca);
				// Si no se ha borrado correctamente el XML, se le pide que al usuario que lo haga él
				if(!xmlBiblio.delete())
					javax.swing.JOptionPane.showMessageDialog(this, 
							"Por favor, borre manualmente el archivo \"" + _nombreBiblioteca + "\"");	
				System.exit(0); // Salimos de la aplicación
			}
        
        // Despues parseamos el arbol XML para extraer la informacion y meterla en el ArrayList
			_easTmp = parser.parsearDocumento(_modeloListaRecursos, this);
        
        if(_conectado) // Y si está conectado, lo añadimos
        	_cliente.anyadir(_easTmp);
    }
    
    /**
     * Método que añade una nueva fila a la tabla de búsqueda
     * @param n Nombre del fichero encontrado
     * @param t Tamaño del fichero encontrado
     * @param c Checksum del fichero encontrado
     * @param s Número de seeds del fichero encontrado
     * @param p Número de peers del fichero encontrado
     */
    public void nuevoResultado(String n, long t, long c, int s, int p) {
   	 // El orden correcto es: nombre, tamaño, seeds, peers, checksum
        _modeloTablaBusqueda.addRow(new Object[]{n, t, s, p, c});
    }

    /**
     * Método que elimina la fila i-ésima de la tabla resultados
     * @param fila Número de fila a eliminar
     */
    public void eliminarResultado(int fila) {
        _modeloTablaBusqueda.removeRow(fila);
    }

    /**
     * Método que añade una descarga de las buscadas
     * @param a Objeto Archivo correspondiente al archivo a descargar
     * @param r Ruta donde el archivo está siendo o será almacenado
     * @param c Porcentaje completado
     */
    public void nuevaDescarga(Archivo a, parteArchivo[] partes, float p) {
   		String ruta=_rutaDescargas+a.nombre();
   		Downloader d;

   		JProgressBar barra = crearBarraDescarga(p, String.valueOf((int)p)+"%");
   		_modeloTablaDescargas.addRow(new Object[]{a.nombre(), ruta, barra, 0});
		_menuContextual.nuevoEstado(ruta);
   		d=_cliente.descargar(a, partes, p, _conexionesMaximas, _tamBloque, ruta, barra);
		_descargasActuales.put(ruta, d);
    }
    
    //Metodo para anyadir las descargas activas al iniciar la aplicación
	public void nuevaDescarga(EstrArchivo e, float p) {
		String ruta=_rutaDescargas+e.info.nombre;
   		Downloader d;

   		JProgressBar barra=crearBarraDescarga(p, String.valueOf((int)p)+"%");
   		_modeloTablaDescargas.addRow(new Object[]{e.info.nombre, ruta, barra, 0});
		_menuContextual.nuevoEstado(ruta);
		Archivo a=null;
		if(conectado())
			a=_cliente.buscar(e.info.nombre);
		if(a!=null) {
			d=_cliente.descargar(a, e.partes, p, _conexionesMaximas, _tamBloque, ruta, barra);
			_descargasActuales.put(ruta, d);
		}
		else
			_descargasPendientes.put(e,barra);
	}

    /**
     * Método que elimina la fila i-ésima de la tabla descargas
     * @param fila Numero de fila a eliminar
     */
    public void eliminarDescarga(int fila) {
        _modeloTablaDescargas.removeRow(fila);
        _tablaDescargas.setModel(_modeloTablaDescargas);
    }

    /**
     * Método que busca el archivo de configuración (opciones.txt), lo carga y lo lee.
     * Si no existe, crea un nuevo con unos valores por defecto
     * @throws IOException
     */
    private void cargarOpciones() throws IOException {
        Scanner entrada;
        
        try {
			entrada=new Scanner(new File("opciones.txt"));
	        _tamBloque=Integer.valueOf(entrada.nextLine());
	        _conexionesMaximas=Integer.valueOf(entrada.nextLine());
	        _ipservidor=entrada.nextLine();
	        _iplocal=entrada.nextLine();
	        _puerto=Integer.valueOf(entrada.nextLine());
	        _rutaDescargas=entrada.nextLine();

	        File f=new File(_rutaDescargas);
	        _rutaDescargas=f.getAbsolutePath();
	        if(!_rutaDescargas.endsWith("/"))
	        	_rutaDescargas=_rutaDescargas.concat("/");
	        
	        
	        if(!f.isDirectory())
	        	if(!f.mkdir()) {
	    	        javax.swing.JOptionPane.showMessageDialog(this,
	    	        		"El directorio de descargas no puede crearse. Escoja otro.",
	    	        		"Error de configuración", javax.swing.JOptionPane.ERROR_MESSAGE);
	            	opcionArchivoOpcionesActionPerformed(null);
	        	}
	        
	        entrada.close();
		} catch (FileNotFoundException e) {
        	//Si no encuentra el fichero de configuración muestra el diálogo para introducir
        	//las opciones con unos parámetros por defecto.
	        _tamBloque=512000;
	        _conexionesMaximas=10;
	        _ipservidor="127.0.0.1";
	        _iplocal="127.0.0.1";
	        _puerto=2000;
	        _rutaDescargas="Descargas/";
	        javax.swing.JOptionPane.showMessageDialog(this,
	        		"Debe establecer las opciones de configuración",
	        		"Error de configuración", javax.swing.JOptionPane.ERROR_MESSAGE);
        	opcionArchivoOpcionesActionPerformed(null);
		}
	}
    
    /**
     * Método que asigna los valores de tamaño y conexiones maximas
     * @param t El tamaño de los bloques a descargar (tamaño de las partes)
     * @param c El número de conexiones máximas
     * @throws IOException 
     */
    public void establecerOpciones(int t, int c, String ipservidor, String iplocal, int puerto, String rutaDescargas) throws IOException {
        _tamBloque = t;
        _conexionesMaximas = c;
        _ipservidor=ipservidor;
        _iplocal=iplocal;
        _puerto=puerto;
        _rutaDescargas=rutaDescargas;
        
        File f=new File(_rutaDescargas);
        if(!f.isDirectory()) f.mkdir();
        
        PrintWriter salida;
        
        salida=new PrintWriter(new FileWriter("opciones.txt"));
        salida.write(_tamBloque+"\n");
        salida.write(_conexionesMaximas+"\n");
        salida.write(_ipservidor+"\n");
        salida.write(_iplocal+"\n");
        salida.write(_puerto+"\n");
        salida.write(_rutaDescargas+"\n");
        salida.flush();
        salida.close();
    }

    /**
     * Método que crea una nueva barra de progreso personalizada
     * @param maximo Valor máximo de la barra de progreso
     * @param porcentaje Establece el porcentaje que tendrá la barra
     * @param informacion Muestra esta cadena de texto dentro de la barra
     * @return
     */
    public JProgressBar crearBarraDescarga(float porcentaje, String informacion) {
        JProgressBar progressBar = new JProgressBar(0, 100);

        progressBar.setValue((int)porcentaje); // Porcentaje actual
        progressBar.setString(informacion); // Información a mostrar
        progressBar.setStringPainted(true); // Mostrar información en la barra

        return progressBar;
    }

    /**
     * Método que, dado el nombre de un archivo que se está descargando, ha
     * recibido una nueva parte y debe actualizar su barra de progreso con la
     * cantidad de bytes recibidos (en proporción al tamaño total).
     * @param archivo Archivo del que se ha obtenido una nueva parte
     * @param tamParte La longitud de la parte obtenida
     */
    public void actualizarDescarga(String archivo, int porcentaje) {
        JProgressBar aux = null;
        boolean encontrado=false;
        int i=0;
        
System.out.println("actualizarDescarga("+archivo+","+porcentaje+")");
        
        while(!encontrado && i<_modeloTablaDescargas.getRowCount()) {
            if(archivo.equals(_modeloTablaDescargas.getValueAt(i, 0))) {
            	encontrado=true;
                aux = (JProgressBar) _modeloTablaDescargas.getValueAt(i, 2);
                aux.setString(porcentaje+"%");
                aux.setValue(porcentaje);
                _tablaDescargas.repaint();
            }
            else
            	i++;
        }
    }
    
    /**
     * Método que devuelve el valor del atributo "conectado"
     * @return true si está conectado a la red P2P y false en caso contrario
     */
    public boolean conectado() {
 		return _conectado;
 	 }

 	/**
 	 * Mueve la descarga finalizada a compartidos
 	 */
 	public synchronized void finalizarDescarga(EstrArchivo archivo) {
 		boolean encontrado=false;
 		int i=0;
 		 		
 		_descargasActuales.remove(archivo.info.ruta);
 		
 		while(!encontrado && i<_modeloTablaDescargas.getRowCount()) {
             if(archivo.info.nombre.equals(_modeloTablaDescargas.getValueAt(i, 0))) encontrado=true;
             else i++;
         }
 		if(encontrado)
 			eliminarDescarga(i);
 		
 		_modeloListaRecursos.addElement(archivo.info.ruta);
 		
 		almacenarDescarga(archivo);
 	}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteP2P().setVisible(true);
            }
        });
    }
    
    // Variables declaration
    private javax.swing.JList _listaFicherosCompartidos;
    private javax.swing.JLabel _loaderBuscando;
    private javax.swing.JTable _tablaDescargas;
    private javax.swing.JTable _tablaResultados;
    private javax.swing.JLabel actualizarBiblioteca;
    private javax.swing.JLabel anyadirRecurso;
    private javax.swing.JMenuItem ayudaAcercaDe;
    private javax.swing.JMenuItem ayudaAyuda;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JTextField cajaTextoNomFich;
    private javax.swing.JButton iconAyuda;
    private javax.swing.JButton iconConectar;
    private javax.swing.JButton iconDesconectar;
    private javax.swing.JButton iconSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelFicheroBuscar;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuP2PSF;
    private javax.swing.JMenuBar menuPpal;
    private javax.swing.JMenuItem opcionArchivoLogin;
    private javax.swing.JMenuItem opcionArchivoLogout;
    private javax.swing.JMenuItem opcionArchivoOpciones;
    private javax.swing.JMenuItem opcionArchivoSalir;
    private javax.swing.JMenuItem opcionP2PBiblioteca;
    private javax.swing.JMenuItem opcionP2PBusqueda;
    private javax.swing.JMenuItem opcionP2PDescargas;
    private javax.swing.JPanel panelAniadirFichero;
    private javax.swing.JPanel panelBusqueda;
    private javax.swing.JPanel panelDescargas;
    private javax.swing.JPanel panelInferiorDrcho;
    private javax.swing.JPanel panelInferiorIzqdo;
    private javax.swing.JPanel panelMiBiblio;
    private javax.swing.JPanel panelNuevaBusqueda;
    private javax.swing.JPanel panelPpal;
    private javax.swing.JPanel panelResultadosBusqueda;
    private javax.swing.JPanel pestaniaBiblioteca;
    private javax.swing.JPanel pestaniaDescargas;
    private javax.swing.JTabbedPane pestanyasApp;
    private javax.swing.JLabel quitarRecurso;
    private javax.swing.JScrollPane scrollPaneDescargas;
    private javax.swing.JScrollPane scrollPaneResultados;
    private javax.swing.JToolBar.Separator separador1;
    private javax.swing.JPopupMenu.Separator separadorArchivo;
    private javax.swing.JLabel valorEstadoActual;
    // End of variables declaration//GEN-END:variables

	public void almacenarDescarga(EstrArchivo estrArchivo) {
		boolean encontrado=false;
		int i=0;
		while(!encontrado && i<_easTmp.size()) {
			if(_easTmp.get(i).info.ruta.equals(estrArchivo.info.ruta)) {
				encontrado=true;
				_easTmp.remove(i);
			}
			else i++;
		}
		_easTmp.add(estrArchivo);
		almacenarBiblioteca();
	}

	public synchronized void sumarConexiones(String ruta, int num) {
		boolean encontrado=false;
		int i=0;
		while(!encontrado && i<_modeloTablaDescargas.getRowCount()) {
			if(_modeloTablaDescargas.getValueAt(i, 1).equals(ruta))
				encontrado=true;
			else
				i++;
		}

		int conex;
		if(encontrado) {
			conex=(Integer)_modeloTablaDescargas.getValueAt(i, 3)+num;
			_modeloTablaDescargas.setValueAt(conex, i, 3);
		}
	}
}
