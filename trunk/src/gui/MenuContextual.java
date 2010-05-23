package gui;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
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
    private JPopupMenu _menuPopup;
    private MouseListener _listenerPopup;
    private String _iniciarDescarga = "Iniciar descarga";
    private String _pausarDescarga = "Pausar descarga";
    private String _cancelarDescarga = "Cancelar descarga";
	 private Hashtable<String, Downloader> _descargasActuales;
	 private Hashtable<String, Boolean> _estadoDescargas;
	 //private ArrayList<Boolean> _estadoDescargas;
	 protected int _filaSeleccionada;
	 
	 int i = 0;
  
    public MenuContextual(JTable tabla, Hashtable<String, Downloader> descargasActuales) {
   	 _miTabla = tabla; // Referencia a la tabla descargas
   	 
   	 // Creamos un Hastable para mantener las descargas que están iniciadas o pausadas
   	 //estadoDescargas = new Hashtable<int, boolean>();
   	 
   	 // Añadimos el listener a la tabla
	    _listenerPopup = new ListenerPopup();
	    _miTabla.addMouseListener(_listenerPopup);
   	 
	    // Instanciamos el objeto JPopupMenu
	    _menuPopup = new JPopupMenu(); 
   	 
	    // Primera opcion: iniciar una descarga
	    _iniciar = new JMenuItem(_iniciarDescarga);
   	 //_iniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/iniciar.png")));
   	 _iniciar.addActionListener(new ManejadorEventosMenu(this));
   	 //_iniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/iniciar.png")));
   	 	_iniciar.addActionListener(new ManejadorEventosMenu(this));
	    _menuPopup.add(_iniciar);

	    // Segunda opcion: pausar una descarga
	    _pausar = new JMenuItem(_pausarDescarga);
	    //_pausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/pausar.png")));
	    _pausar.addActionListener(new ManejadorEventosMenu(this));
	    _menuPopup.add(_pausar);
	    
	    // Tercera opcion: cancelar una descarga
	    _cancelar = new JMenuItem(_cancelarDescarga);
	    //_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/cancelar.png")));
	    _cancelar.addActionListener(new ManejadorEventosMenu(this));
	    _menuPopup.add(_cancelar);	    
	    
	    // Para añadir un listener específico a la cabecera	    
	    //_miTabla.getTableHeader().addMouseListener(_listenerPopup);
	    
	    pausar();
	    
	    _descargasActuales=descargasActuales;
	    
	    _estadoDescargas = new Hashtable<String, Boolean>();
	    //_estadoDescargas = new ArrayList<Boolean>();
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
     * @author sema
     */
    class ListenerPopup extends MouseAdapter {
   	 /*public void mousePressed(MouseEvent e) { 
   		 System.out.println("Estoy entrando en el ListenerPopup");
   		 //e.getButton()	 
   		 mostrarMenuPopup(e);
   	 }*/
   	 
   	 /*public void mouseReleased(MouseEvent e) {
   		 System.out.println("Se ha releseado el boton");
   	 }*/
   	 
   	 public void mouseClick(MouseEvent e) {
   		// Si se ha pulsado con el botón derecho del ratón sobre la tabla
   		 if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
   			 _filaSeleccionada=_miTabla.rowAtPoint(e.getPoint());
   			 if(_estadoDescargas.get(_miTabla.getValueAt(_filaSeleccionada, 1))) // Si es true
   				reanudar(); 
   			 else
   				pausar();   				 
   			 _menuPopup.show(e.getComponent(), e.getX(), e.getY());
   		 // Si se ha pulsado con el botón izquierdo del ratón
   		 } else if(e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
             _filaSeleccionada= _miTabla.rowAtPoint(e.getPoint());
             _miTabla.setRowSelectionInterval(_filaSeleccionada, _filaSeleccionada);
   		 }
   	 }

   	/* private void mostrarMenuPopup(MouseEvent e) {
   		 System.out.println("Entrando en la función mostrarMenuPopup");
   		 // Si se ha pulsado con el botón derecho del ratón sobre la tabla
   		 if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
   			 _filaSeleccionada=_miTabla.rowAtPoint(e.getPoint());
   			 if(_estadoDescargas.get(_miTabla.getValueAt(_filaSeleccionada, 1))) { // Si es true
   				reanudar(); 
   			 } else {
   				pausar();   				 
   			 }
   			 _menuPopup.show(e.getComponent(), e.getX(), e.getY());
   		 // Si se ha pulsado con el botón izquierdo del ratón
   		 } else if(e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
             _filaSeleccionada= _miTabla.rowAtPoint(e.getPoint());
             _miTabla.setRowSelectionInterval(_filaSeleccionada, _filaSeleccionada);
   		 }
   	 }*/
    } // Fin de la clase ListenerPopup

    /**
     * Methods
     * @param e
     */
    void realizarAccion(ActionEvent e) {   
   	 System.out.println("La i: " + i);
   	 i++;
   	 
   	 // Se consulta si la descarga i-ésima está descargando o pausando, para mostrar el menu en función de ello   	 
   	 if(e.getActionCommand().equals(_iniciarDescarga)) {
   		 _iniciar.setEnabled(false); // Al pulsar sobre iniciar se deshabilita
   		 _pausar.setEnabled(true); // Y se habilita pausar para poder pausarla
   		 //pausar();
   		 //_descargasActuales.get(_miTabla.getValueAt(_filaSeleccionada,1)).start();
   		 System.out.println("Descargazz iniciada");
   	 } else if(e.getActionCommand().equals(_pausarDescarga)) {
   		 _iniciar.setEnabled(true); // Al pulsar sobre pausar se deshabilita
   		 _pausar.setEnabled(false); // Y se habilita iniciar para poder continuar
   		 //reanudar();
   		 //_descargasActuales.get(_miTabla.getValueAt(_filaSeleccionada,1)).suspend();
   		 System.out.println("Descargazz pausada...");
   	 } else if(e.getActionCommand().equals(_cancelarDescarga)) {
   		 System.out.println("Uy, seguro que quieres cancelarla?");
   	 }
    }

    /**
     * 
     */
	public void reanudar() {
   	 	_iniciar.setEnabled(false);
	    _pausar.setEnabled(true);
	}

    /**
     * 
     */
	public void pausar() {
   	 	_iniciar.setEnabled(true);
	    _pausar.setEnabled(false);
	}
} // Final de la clase MenuContextual

/**
 * Clase que bla, bla... 
 * @author sema
 */
class ManejadorEventosMenu implements ActionListener {
    MenuContextual adaptador;

    ManejadorEventosMenu(MenuContextual adapter) {
   	 this.adaptador = adapter;
    }

    public void actionPerformed(ActionEvent e) {
   	 System.out.println("Evento: " + e.toString());
   	 adaptador.realizarAccion(e);
    }
}