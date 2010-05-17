
package gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import javax.swing.DefaultListModel;
import javax.swing.JProgressBar;
import javax.swing.ListCellRenderer;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;


/**
 * Interfaz Gráfica Principal de la Aplicación P2PSF
 * @author sema
 */
public class ClienteP2P extends javax.swing.JFrame {

    private Buscando _hilo;
    private int _tamBloque;
    private int _conexionesMaximas;
    private String _nombreBiblioteca;
    private boolean _conectado = false;
    private DefaultTableModel _dm;
    private DefaultListModel _modeloListaRecursos = new DefaultListModel();
    private DefaultTableModel _modeloTablaBusqueda = new DefaultTableModel();
    private DefaultTableModel _modeloTablaDescargas = new DefaultTableModel();
    //private TableColumn columnaBarraProgreso;
    //private TablaPorcentaje miTabla = new TablaPorcentaje();
    
    
    /** Creates new form ClienteP2P */
    public ClienteP2P() {
        // Inicialización de componentes: interfaz, escuchadores, manejadores...
        initComponents();

        // Selección y establecimiento de la apariencia de la aplicación
        /*try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ClienteP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ClienteP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ClienteP2P.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        // Centramos en la pantalla la ventana de la aplicación (sólo por NetBeans)
        setLocationRelativeTo(null);

        // Por defecto, el nombre de la biblioteca es "MiBiblioteca.xml";
        _nombreBiblioteca = "MiBiblioteca.xml";

        // Al iniciar la aplicación se cargan desde el fichero XML, la lista
        // de ficheros/recursos compartidos
        cargarBiblioteca();

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
        _dm = new DefaultTableModel(); // Instanciamos el modelo por defecto
        // Asignamos la cabecera de la tabla
        _dm.setColumnIdentifiers(new Object []{"Archivo", "Ruta al archivo", "Porcentaje Completado"});
        _tablaDescargas.setModel(_dm); // Indicamos qué modelo va a utilizar la tabla        
        // Hacemos que la tercera columna (la de )
        //_tablaDescargas.getColumn("Progress").setCellRenderer(new MiBarraProgreso());
        _tablaDescargas.getColumn("Porcentaje Completado").setCellRenderer(new MiBarraProgreso());
    }

    // Código autogenerado
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        separadorP2PSF = new javax.swing.JPopupMenu.Separator();
        opcionP2PLimpiarResultados = new javax.swing.JMenuItem();
        opcionP2PLimpiarDescargas = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        ayudaAyuda = new javax.swing.JMenuItem();
        ayudaAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente de redes P2P");
        setMinimumSize(new java.awt.Dimension(400, 600));
        setResizable(false);

        panelPpal.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelMiBiblio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Ficheros compartidos actualmente (Mi Biblioteca)"));

        _listaFicherosCompartidos.setModel(new javax.swing.AbstractListModel() {
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                anyadirRecursoMouseClicked(evt);
            }
        });

        quitarRecurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/quitarFichero.png"))); // NOI18N
        quitarRecurso.setText("Quitar los recursos seleccionados");
        quitarRecurso.setToolTipText("Quita el o los recursos compartidos que se encuentren seleccionados en \"Mi Biblioteca\"");
        quitarRecurso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quitarRecursoMouseClicked(evt);
            }
        });

        actualizarBiblioteca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/actualizarBiblioteca.png"))); // NOI18N
        actualizarBiblioteca.setText("Actualizar \"Mi Biblioteca\" en la red P2PSF");
        actualizarBiblioteca.setToolTipText("Hace efectivos los cambios realizados en \"Mi Biblioteca\" para la red P2P");
        actualizarBiblioteca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarBibliotecaMouseClicked(evt);
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
                .addGap(46, 46, 46)
                .addComponent(valorEstadoActual)
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

        opcionArchivoLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
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
        menuP2PSF.add(separadorP2PSF);

        opcionP2PLimpiarResultados.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        opcionP2PLimpiarResultados.setText("Limpiar Resultados");
        opcionP2PLimpiarResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionP2PLimpiarResultadosActionPerformed(evt);
            }
        });
        menuP2PSF.add(opcionP2PLimpiarResultados);

        opcionP2PLimpiarDescargas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        opcionP2PLimpiarDescargas.setText("Limpiar Descargas");
        opcionP2PLimpiarDescargas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionP2PLimpiarDescargasActionPerformed(evt);
            }
        });
        menuP2PSF.add(opcionP2PLimpiarDescargas);

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Esta función será la encargada de enviar al coordinador la lista de archivos
     * que posee el usuario localmente y recibe el identificador que éste le envía
     * @param evt
     */
    private void opcionArchivoLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionArchivoLoginActionPerformed
        // Se crea por un lado la barra de progreso personalizado
        _dm.addRow(new Object[]{"Prueba", "95%", crearBarraDescarga(100, 95, "95%")});
        _dm.addRow(new Object[]{"Prueba2", "5%", crearBarraDescarga(100, 5, "5%")});
        _dm.addRow(new Object[]{"Prueba3", "55%", crearBarraDescarga(100, 55, "55%")});
          /*try {
           for (int row = 0; row < _tablaDescargas.getModel().getRowCount(); row++) {
            JProgressBar jp = (JProgressBar) _tablaDescargas.getModel().getValueAt(row, 2);
            for (int i = 0; i <= 100; i++) {
             jp.setValue(i);
             jp.setString(i + "%");
             // p1.set
             Thread.sleep(100);
             _tablaDescargas.repaint();
            }
           }
          } catch (Exception e) {
           e.printStackTrace();
          }*/
        _conectado = true;

    }//GEN-LAST:event_opcionArchivoLoginActionPerformed

    /**
     * Cuando se pulse sobre el icono de conectar se llamará a su correspondiente
     * función (opcionArchivoLoginActionPerformed), el manejador del evento.
     * @param evt
     */
    private void iconConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iconConectarActionPerformed
        opcionArchivoLoginActionPerformed(evt);
    }//GEN-LAST:event_iconConectarActionPerformed

    /**
     * Cuando se quiera desconectar el usuario, debe comunicarselo al coordinador
     * @param evt
     */
    private void opcionArchivoLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionArchivoLogoutActionPerformed
        actualizarDescarga("Prueba2", 30);
        _conectado = false;
    }//GEN-LAST:event_opcionArchivoLogoutActionPerformed

    /**
     * Cuando se pulse sobre el icono de desconectar se llamará a su correspondiente
     * función (opcionArchivoLogoutActionPerformed), el manejador del evento.
     * @param evt
     */
    private void iconDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iconDesconectarActionPerformed
        this.opcionArchivoLogoutActionPerformed(evt);
    }//GEN-LAST:event_iconDesconectarActionPerformed

    /**
     * Muestra la pestaña correspondiente a "Mi Biblioteca"
     * @param evt
     */
    private void opcionP2PBibliotecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionP2PBibliotecaActionPerformed
        pestanyasApp.setSelectedIndex(0);
    }//GEN-LAST:event_opcionP2PBibliotecaActionPerformed

    /**
     * Muestra la pestaña correspondiente a "Busquedas"
     * @param evt
     */
    private void opcionP2PBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionP2PBusquedaActionPerformed
        pestanyasApp.setSelectedIndex(1);
    }//GEN-LAST:event_opcionP2PBusquedaActionPerformed

    /**
     * Muestra la pestaña correspondiente a Descargas
     * @param evt
     */
    private void opcionP2PDescargasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionP2PDescargasActionPerformed
        pestanyasApp.setSelectedIndex(2);
    }//GEN-LAST:event_opcionP2PDescargasActionPerformed

    /**
     * Método que eliminar todos los resultados de la búsqueda de un archivo
     * @param evt
     */
    public void opcionP2PLimpiarResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionP2PLimpiarResultadosActionPerformed
        System.out.println("Tam: " + _modeloTablaBusqueda.getRowCount());
        for (int i=_modeloTablaBusqueda.getRowCount()-1; i>=0; i--)
            _modeloTablaBusqueda.removeRow(i);
    }//GEN-LAST:event_opcionP2PLimpiarResultadosActionPerformed

    private void opcionP2PLimpiarDescargasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionP2PLimpiarDescargasActionPerformed
        nuevaDescarga("nuevaDescarga", "Ruta/al/archivo/nuevaDescarga", 1746172833, 0);
    }//GEN-LAST:event_opcionP2PLimpiarDescargasActionPerformed

    /**
     * Muestra la ventana típica de ayuda
     * @param evt
     */
    private void ayudaAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ayudaAyudaActionPerformed
        // Cuando se pulse sobre la ayuda se abre dicha ventana
        Ayuda a = new Ayuda(this, true); // La instanciamos. Será modal
        a.setVisible(true); // Y la mostramos
    }//GEN-LAST:event_ayudaAyudaActionPerformed

    /**
     * Muestra los créditos del programa. Información acerca del mismo.
     * @param evt
     */
    private void ayudaAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ayudaAcercaDeActionPerformed
        // Cuando se pulse sobre la ayuda se abre dicha ventana
        AcercaDe ad = new AcercaDe(this, true); // La instanciamos. Será modal
        ad.setVisible(true); // Y la mostramos
    }//GEN-LAST:event_ayudaAcercaDeActionPerformed

    /**
     * Método que llama a la función encargada de mostrar la ayuda
     * @param evt
     */
    private void iconAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iconAyudaActionPerformed
        this.ayudaAyudaActionPerformed(evt);
    }//GEN-LAST:event_iconAyudaActionPerformed

    /**
     * Método que llama la función encargada de mostrar el "acerca de"
     * @param evt
     */
    private void iconSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iconSalirActionPerformed
        // Si ha pulsado sobre el icono de salir se llama a la función correspondiente
        this.opcionArchivoSalirActionPerformed(evt);
    }//GEN-LAST:event_iconSalirActionPerformed

    /**
     * Método encargado de cerrar la aplicación. Para ello, antes lo notifica
     * al coordinador y luego cierra la aplicación.
     * @param evt
     */
    private void opcionArchivoSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionArchivoSalirActionPerformed
        // 1 - Decirle al coordinador que nos vamos
        opcionArchivoLogoutActionPerformed(evt);

        // 2 - Cerrar la aplicación
        System.exit(0);
    }//GEN-LAST:event_opcionArchivoSalirActionPerformed

    /**
     * Cuando se pulse sobre el botón "Búscalo" se instancia el hilo encargado
     * de la visualización y ocultamiento de la animación
     * @param evt
     */
    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        // Cuando se pulse en el botón de buscar
        _hilo = new Buscando(this, _loaderBuscando);
        _hilo.start(); // Lanza el hilo
    }//GEN-LAST:event_botonBuscarActionPerformed

    /**
     * Método encargado de abrir una ventana (JFileChooser) para escoger los
     * archivos (permite selección múltiple) de nuevos archivos que van a ser
     * compartidos por el usuario
     * @param evt
     */
    private void anyadirRecursoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_anyadirRecursoMouseClicked
        // Cuando se pulse en el botón Añadir Recurso
        int opc = 0;
        File [] ficheros;

        // Creamos una ventana para que el usuario escoja su(s) fichero(s)
        javax.swing.JFileChooser anyadir = new javax.swing.JFileChooser();
        anyadir.setMultiSelectionEnabled(true); // Multiselección
        anyadir.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);

        opc = anyadir.showOpenDialog(this); // Se muestra el diálogo

        // Ahora comprobamos si ha pulsado sobre aceptar
        if (opc == javax.swing.JFileChooser.APPROVE_OPTION) {
            ficheros = anyadir.getSelectedFiles(); // Obtenemos los ficheros seleccionados
            //String [] fich = new String[ficheros.length];
            for (int i=0; i<ficheros.length; i++) {
                //fich[i] = ficheros[i].getPath();
                //modeloListaRecursos.addElement(fich[i]);
                _modeloListaRecursos.addElement(ficheros[i].getPath());
            }
        }
    }//GEN-LAST:event_anyadirRecursoMouseClicked

    /**
     * Cuando se pulse sobre el botón eliminar se evalúa si hay recursos seleccionados,
     * y en caso de existir, se eliminan. Si no, se muestra un aviso
     * @param evt
     */
    private void quitarRecursoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitarRecursoMouseClicked
        // Obtenemos el indice que ocupan los elementos que se han seleccionado
        int [] indices = _listaFicherosCompartidos.getSelectedIndices();

        // Si no hay ningun elemento seleccionado
        if (indices.length == 0)
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No ha seleccionado ningún elemento para eliminar");

        // Eliminamos desde el final para evitar problemas con los índices
        for (int i=indices.length - 1; i>=0; i--)
            _modeloListaRecursos.removeElementAt(indices[i]);
    }//GEN-LAST:event_quitarRecursoMouseClicked

    /**
     * Cuando se pulse sobre el botón de actualizar la bibioteca se examinan los
     * archivos que hay en la lista y se genera un XML con dicha información
     * @param evt
     */
    private void actualizarBibliotecaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarBibliotecaMouseClicked
        Biblioteca bib = new Biblioteca(); // Instanciamos el objeto Biblioteca
        int numeroRecursos = _modeloListaRecursos.size(); // Hacemos más eficiente la consulta
        String [] nombres = new String[numeroRecursos]; // Vector para los nombres
        String [] rutas = new String[numeroRecursos]; // Vector para las rutas
        long [] tams = new long[numeroRecursos]; // Vector para los tamaños
        long [] hashs = new long[numeroRecursos]; // Vector para los checksum
        long [][] partes = new long[numeroRecursos][2]; // Para las partes, por defecto solo dos
        
        // Recorremos todos los elementos de la lista y los metemos en el xml
        for (int i=0; i<numeroRecursos; i++) {
            rutas[i] = _modeloListaRecursos.getElementAt(i).toString(); // Ruta completa
            // El nombre se debe extraer de la ruta completa
            nombres[i] = rutas[i].substring(rutas[i].lastIndexOf("/"));
            tams[i] = calcularTamanyo(rutas[i]); // Calculamos el tamaño
            hashs[i] = calcularChecksum(rutas[i]); // Calculamos el checksum
            partes[i][0] = 0; partes[i][1] = tams[i];
            bib.insertarNuevoArchivo(nombres[i], rutas[i], tams[i], hashs[i], true, partes[i]);
        }

        // Comprobamos que se ha guardado correctamente
        if(bib.guardarXML()) // Archivo guardado correctamente
            javax.swing.JOptionPane.showMessageDialog(this,
                    "¡Archivo \"Mi Biblioteca.xml\" generado correctamente!");
        else // Error al guardar el archivo
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error al generar el archivo \"Mi Biblioteca.xml\"");
    }//GEN-LAST:event_actualizarBibliotecaMouseClicked

    /**
     * Método que muestra la ventana de configuración de la aplicación
     * @param evt
     */
    private void opcionArchivoOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionArchivoOpcionesActionPerformed
        Opciones opciones = new Opciones(this, this);
        opciones.setVisible(true);
    }//GEN-LAST:event_opcionArchivoOpcionesActionPerformed

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
        parser.parsearArchivoXML();
        
        // Despues lo parseamos el arbol XML para extraer la informacion
        // Aki debemos capturar las excepciones, y si se lanza la expcecion SAXParseException
        // es debido a un XML mal formado. Opciones adicionales:
        parser.parsearDocumento(_modeloListaRecursos);
    }

    /**
     * Función que calcula el la suma de verificación del fichero pasado como argumento
     * @param fichero Ruta hacia el archivo para calcularle el hash
     * @return Devuelve el hash de dicho fichero pasado como argumento
     */
    private long calcularChecksum(String fichero) {
        CheckedInputStream suma = null;

        try {
            FileInputStream f = new FileInputStream(fichero);
            suma = new CheckedInputStream(f, new CRC32());
            BufferedInputStream in = new BufferedInputStream(suma);

            try { 
                while (in.read() != -1) {  }
                in.close(); // Cerramos el fichero
            } catch (IOException ex) {
                Logger.getLogger(ClienteP2P.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteP2P.class.getName()).log(Level.SEVERE, null, ex);
        }

        return suma.getChecksum().getValue();
    }

    /**
     * Función que calcula el tamaño de un fichero pasado como argumento
     * @param fichero Indica la ruta al fichero
     * @return El tamaño del fichero pasado como argumento
     */
    private long calcularTamanyo(String fichero) {
        File ficheroTam = new File(fichero); // Abrimos el fichero
        return ficheroTam.length(); // Devolvemos el tamaño
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
        _modeloTablaBusqueda.addRow(new Object[]{n, t, c, s, p});
    }

    /**
     * Método que elimina la fila i-ésima de la tabla resultados
     * @param fila Número de fila a eliminar
     */
    public void eliminarResultado(int fila) {
        _modeloTablaBusqueda.removeRow(fila);
    }

    /**
     * Método que añade una nueva fila a la tabla de descargas
     * @param n Nombre del archivo que se está descargando
     * @param r Ruta al archivo que se está descargando
     * @param t Tamaño total del nuevo fichero que se está descargando
     * @param d Porcentaje completado (Revisar) -> Por defecto 0??
     */
    public void nuevaDescarga(String n, String r, long t, long d) {
        //_modeloTablaDescargas.addRow(new Object[]{n, r, desc});
        _dm.addRow(new Object[]{n, r, crearBarraDescarga((int)t, 0, "0%")});
    }

    /**
     * Método que elimina la fila i-ésima de la tabla descargas
     * @param fila Numero de fila a eliminar
     */
    public void eliminarDescarga(int fila) {
        _modeloTablaDescargas.removeRow(fila);
    }

    /**
     * Método que asigna los valores de tamaño y conexiones maximas
     * @param t El tamaño de los bloques a descargar (tamaño de las partes)
     * @param c El número de conexiones máximas
     */
    public void establecerOpciones(int t, int c) {
        _tamBloque = t;
        _conexionesMaximas = c;
    }

    /**
     * Método que crea una nueva barra de progreso personalizada
     * @param maximo Valor máximo de la barra de progreso
     * @param porcentaje Establece el porcentaje que tendrá la barra
     * @param informacion Muestra esta cadena de texto dentro de la barra
     * @return
     */
    public JProgressBar crearBarraDescarga(int maximo, int porcentaje, String informacion) {
        JProgressBar progressBar = new JProgressBar(0, maximo);

        progressBar.setValue(porcentaje); // Porcentaje actual
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
    public void actualizarDescarga(String archivo, long tamParte) {
        JProgressBar aux = null;

        for(int i=0; i<_dm.getRowCount(); i++) {
            if(archivo.equals(_dm.getValueAt(i, 0))) {
                aux = (JProgressBar) _dm.getValueAt(i, 2);
                int valor = aux.getValue() + (int)tamParte;
                aux.setString(String.valueOf(valor) + "%");
                aux.setValue(aux.getValue() + (int)tamParte);
                _tablaDescargas.repaint();
            }
        }
        //System.out.println("Nombre: " + _dm.getValueAt(i, 0));
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

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JMenuItem opcionP2PLimpiarDescargas;
    public javax.swing.JMenuItem opcionP2PLimpiarResultados;
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
    private javax.swing.JPopupMenu.Separator separadorP2PSF;
    private javax.swing.JLabel valorEstadoActual;
    // End of variables declaration//GEN-END:variables

}
