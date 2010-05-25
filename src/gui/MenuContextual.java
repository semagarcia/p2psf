package gui;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;

import cliente.Downloader;

/**
 * Clase que crea el menú Popup (contextual) sobre la tabla de descargas
 * @author sema
 */
public class MenuContextual extends JFrame {
    private JTable _miTabla;
    private JMenuItem _iniciar;
    private JMenuItem _pausar;
    private JMenuItem _cancelar;
	private JSeparator _separador;
	private JMenuItem _comprobar;
    private JPopupMenu _menuPopup;
    private MouseListener _listenerPopup;
    private String _iniciarDescarga = "Iniciar descarga";
    private String _pausarDescarga = "Pausar descarga";
    private String _cancelarDescarga = "Cancelar descarga";
	private String _comprobarDescarga= "Comprobar descarga";
	private Hashtable<String, Downloader> _descargasActuales;
	private Hashtable<String, Boolean> _estadoDescargas;
	private int _filaSeleccionada;
	private ClienteP2P _interfaz;
	 
  
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
	    
	    _separador = new JSeparator();
	    _menuPopup.add(_separador);	    

	    // Cuarta opcion: comprobar disponibilidad
	    _comprobar = new JMenuItem(_comprobarDescarga);
	    _comprobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/cancelar.png")));
	    _comprobar.addActionListener(new ManejadorEventosMenu(this));
	    _menuPopup.add(_comprobar);	    

	    
	    // Para añadir un listener específico a la cabecera	    
	    //_miTabla.getTableHeader().addMouseListener(_listenerPopup);
	    
	    mostrarParado();
	    _descargasActuales=descargasActuales;
    }
    
    public void nuevoEstado(String ruta) {
   	// Añadimos un nuevo elemento para la nueva descarga
   	 _estadoDescargas.put(ruta, false); 
    }
    
    public void quitarEstado(String ruta) {
   	 //_estadoDescargas.remove();
    }

    /**
     * Clase interna que bla, bla, bla...
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
   				 if(estado==null) { // Si es true
   					 mostrarComprobado();
   				 }
   				 else if(estado==true) {
   					 mostrarIniciado();
   				 }
   				 else {
   					 mostrarParado();   				 
   				 }
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
     * Methods
     * @param e
     */
    void realizarAccion(ActionEvent e) {
   		 // Se consulta si la descarga i-ésima está descargando o pausando, para mostrar el menu en función de ello   	 
   		 if(e.getActionCommand().equals(_iniciarDescarga)) {
   			 mostrarIniciado();
   			 _estadoDescargas.put((String)_miTabla.getValueAt(_filaSeleccionada, 1),true);
   			 _descargasActuales.get(_miTabla.getValueAt(_filaSeleccionada,1)).start();
   		 } else if(e.getActionCommand().equals(_pausarDescarga)) {
   			 mostrarParado();
   			 
System.out.println("DOWNLOADER ESTA VIVO: "+_descargasActuales.get(_miTabla.getValueAt(_filaSeleccionada,1)).isAlive());

   			 _estadoDescargas.put((String)_miTabla.getValueAt(_filaSeleccionada, 1),false);
   			 _descargasActuales.put((String)_miTabla.getValueAt(_filaSeleccionada,1),_descargasActuales.get(_miTabla.getValueAt(_filaSeleccionada,1)).parar());
   		 } else if(e.getActionCommand().equals(_cancelarDescarga)) {
   			 System.out.println("Uy, seguro que quieres cancelarla?");
   		 }
   	 	}

    /**
     * 
     */
	public void mostrarDesconectado() {
   	 	_iniciar.setEnabled(false);
	    _pausar.setEnabled(false);
	    _comprobar.setEnabled(false);
	}

	public void mostrarParado() {
   	 	_iniciar.setEnabled(true);
	    _pausar.setEnabled(false);
	    _comprobar.setEnabled(false);
	}
	
	public void mostrarIniciado() {
   	 	_iniciar.setEnabled(false);
	    _pausar.setEnabled(true);
	    _comprobar.setEnabled(false);
	}

	private void mostrarComprobado() {
	 	_iniciar.setEnabled(false);
	    _pausar.setEnabled(false);
	    if(_interfaz.conectado())
	    	_comprobar.setEnabled(true);
	    else
	    	_comprobar.setEnabled(false);
	}

	public void parar(String ruta) {
		_estadoDescargas.put(ruta, false);
	}

} // Final de la clase MenuContextual

/**
 * Clase que bla, bla... 
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