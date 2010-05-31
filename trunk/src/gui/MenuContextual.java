package gui;


import java.awt.event.*;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import cliente.Downloader;


/**
 * Clase que crea el menú Popup (contextual) sobre la tabla de descargas
 */
public class MenuContextual extends JFrame {
	/**
	 * Clase para mostrar un menú contextual cuando se pulsa con el botón derecho sobre una descarga.
	 * @param tabla Tabla donde se muestran las descargas.
	 * @param descargasActuales Nombre y hilos Downloader de las descargas actuales.
	 * @param interfaz Referencia a la interfaz de usuario.
	 */
    public MenuContextual(JTable tabla, Hashtable<String, Downloader> descargasActuales, ClienteP2P interfaz) {
    	_interfaz=interfaz;
   	 	_miTabla = tabla; // Referencia a la tabla descargas
   	 
   	 	// Creamos un Hastable para mantener las descargas que están iniciadas o pausadas
   	 	_estadoDescargas = new Hashtable<String, Boolean>();
   	 
   	 	// Añadimos el listener a la tabla
	    _listenerPopup = new ListenerPopup();
	    _miTabla.addMouseListener(_listenerPopup);
   	 
	    // Instanciamos el objeto JPopupMenu
	    _menuPopup = new JPopupMenu(); 
   	 
	    // Primera opcion: iniciar una descarga
	    _iniciar = new JMenuItem(_iniciarDescarga);
   	 	_iniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/iniciar.png")));
   	 	_iniciar.addActionListener(new ManejadorEventosMenu(this));
	    _menuPopup.add(_iniciar);

	    // Segunda opcion: pausar una descarga
	    _pausar = new JMenuItem(_pausarDescarga);
	    _pausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/pausar.png")));
	    _pausar.addActionListener(new ManejadorEventosMenu(this));
	    _menuPopup.add(_pausar);
	    
	    // Tercera opcion: cancelar una descarga
	    _cancelar = new JMenuItem(_cancelarDescarga);
	    _cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/cancelar.png")));
	    _cancelar.addActionListener(new ManejadorEventosMenu(this));
	    _menuPopup.add(_cancelar);	    
	    
	    // Para añadir un listener específico a la cabecera	    
	    //_miTabla.getTableHeader().addMouseListener(_listenerPopup);
	    
	    mostrarParado();
	    _descargasActuales=descargasActuales;
    }
    

    /**
     * Almacena el false en el estado de la nueva descarga.
     * @param ruta Ruta de la descarga.
     */
    public void nuevoEstado(String ruta) {
   	// Añadimos un nuevo elemento para la nueva descarga
   	 _estadoDescargas.put(ruta, false); 
    }
    
    
    /**
     * Clase interna que captura los eventos del ratón.
     */
    class ListenerPopup extends MouseAdapter {
   	 public void mousePressed(MouseEvent e) { 
   		 mostrarMenuPopup(e);
   	 }
   	 
   	 private void mostrarMenuPopup(MouseEvent e) {
   		 // Si se ha pulsado con el botón derecho del ratón sobre la tabla
   		 if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
   		   	 if(_interfaz.conectado()) {
   				 _filaSeleccionada=_miTabla.rowAtPoint(e.getPoint());
   				 Boolean estado=_estadoDescargas.get(_miTabla.getValueAt(_filaSeleccionada, 1));
   				 if(estado==true) mostrarIniciado();
   				 else mostrarParado();
   				 
   				 _menuPopup.show(e.getComponent(), e.getX(), e.getY());
   				 // Si se ha pulsado con el botón izquierdo del ratón
   			 	}
   		   	 else {
   		   		 mostrarDesconectado();
   				 _menuPopup.show(e.getComponent(), e.getX(), e.getY());
   		   	 }
   			 } else if(e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
             _filaSeleccionada= _miTabla.rowAtPoint(e.getPoint());
             _miTabla.setRowSelectionInterval(_filaSeleccionada, _filaSeleccionada);
   		 }
   	 }
    } // Fin de la clase ListenerPopup

    
    /**
     * Método que ejecuta la acción seleccionada.
     * @param e Evento capturado.
     */
    public void realizarAccion(ActionEvent e) {
   		 // Se consulta si la descarga i-ésima está descargando o pausando, para mostrar el menu en función de ello   	 
   		 if(e.getActionCommand().equals(_iniciarDescarga)) {
   			 mostrarIniciado();
   			 _estadoDescargas.put((String)_miTabla.getValueAt(_filaSeleccionada, 1),true);
   			 _descargasActuales.get(_miTabla.getValueAt(_filaSeleccionada,1)).start();
   		 } else if(e.getActionCommand().equals(_pausarDescarga)) {
   			 mostrarParado();
   			 
   			 _estadoDescargas.put((String)_miTabla.getValueAt(_filaSeleccionada, 1),false);
   			 _descargasActuales.put((String)_miTabla.getValueAt(_filaSeleccionada,1),_descargasActuales.get(_miTabla.getValueAt(_filaSeleccionada,1)).parar());
   		 } else if(e.getActionCommand().equals(_cancelarDescarga)) {
   			 if(javax.swing.JOptionPane.showConfirmDialog(_interfaz,
   					 "¿Está seguro de que desa cancelar la descarga?")==javax.swing.JOptionPane.OK_OPTION) {
   				 String ruta=(String)_miTabla.getValueAt(_filaSeleccionada,1);
   				 _interfaz.cancelarDescarga(ruta,_filaSeleccionada);
   				 _estadoDescargas.remove(ruta);
   			 }
   				 
   		 }
   	 	}


    /**
     * Actualiza las opciones a mostrar a las que se deben mostrar cuando el usuario esta desconectado.
     */
	public void mostrarDesconectado() {
   	 	_iniciar.setEnabled(false);
	    _pausar.setEnabled(false);
	}

	
	/**
     * Actualiza las opciones a mostrar a las que se deben mostrar cuando la descarga está pausada.
     */
	public void mostrarParado() {
   	 	_iniciar.setEnabled(true);
	    _pausar.setEnabled(false);
	}
	

	/**
     * Actualiza las opciones a mostrar a las que se deben mostrar cuando la descarga está activa.
     */
	public void mostrarIniciado() {
   	 	_iniciar.setEnabled(false);
	    _pausar.setEnabled(true);
	}


	/**
	 * Establece el estado de la descarga a parado(false).
	 * @param ruta Ruta de la descarga.
	 */
	public void parar(String ruta) {
		_estadoDescargas.put(ruta, false);
	}

	
	private JTable _miTabla;
    private JMenuItem _iniciar;
    private JMenuItem _pausar;
    private JMenuItem _cancelar;
    private JPopupMenu _menuPopup;
    private MouseListener _listenerPopup;
    private String _iniciarDescarga = "Iniciar descarga";
    private String _pausarDescarga = "Pausar descarga";
    private String _cancelarDescarga = "Cancelar descarga";
	private Hashtable<String, Downloader> _descargasActuales;
	private Hashtable<String, Boolean> _estadoDescargas;
	private int _filaSeleccionada;
	private ClienteP2P _interfaz;
	 
	private static final long serialVersionUID = 1L;
} // Final de la clase MenuContextual


/**
 * Clase que maneja las acciones del ratón. 
 */
class ManejadorEventosMenu implements ActionListener {
    MenuContextual adaptador;

    ManejadorEventosMenu(MenuContextual adapter) {
   	 this.adaptador = adapter;
    }

    public void actionPerformed(ActionEvent e) {
   	 adaptador.realizarAccion(e);
    }
}