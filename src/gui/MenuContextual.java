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
	protected int _filaSeleccionada;
  
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
	    
	    desconectar();
	    
	    _descargasActuales=descargasActuales;
    }

    /**
     * Clase interna que bla, bla, bla...
     * Dependiendo del SO y otras caracteristicas, puede que se llama a mousePressed o mouseReleased
     * @author sema
     */
    class ListenerPopup extends MouseAdapter {
   	 public void mousePressed(MouseEvent e) { mostrarMenuPopup(e); }

   	 //public void mouseReleased(MouseEvent e) { mostrarMenuPopup(e); }

   	 private void mostrarMenuPopup(MouseEvent e) {
   		 if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
   			 _filaSeleccionada=_miTabla.rowAtPoint(e.getPoint());
   			 _menuPopup.show(e.getComponent(), e.getX(), e.getY());
   		 } else if(e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
             _filaSeleccionada= _miTabla.rowAtPoint(e.getPoint());
             _miTabla.setRowSelectionInterval(_filaSeleccionada, _filaSeleccionada);
   		 }
   	 }
    } // Fin de la clase ListenerPopup

    void realizarAccion(ActionEvent e) {   	 
   	 // Se consulta si la descarga i-ésima está descargando o pausando, para mostrar el menu en función de ello
   	 
   	 
   	 if(e.getActionCommand().equals(_iniciarDescarga)) {
   		 _iniciar.setEnabled(false); // Al pulsar sobre iniciar se deshabilita
   		 _pausar.setEnabled(true); // Y se habilita pausar para poder pausarla
   		 
   		 _descargasActuales.get(_miTabla.getValueAt(_filaSeleccionada,1)).start();
   		 
   	 } else if(e.getActionCommand().equals(_pausarDescarga)) {
   		 _iniciar.setEnabled(true); // Al pulsar sobre pausar se deshabilita
   		 _pausar.setEnabled(false); // Y se habilita iniciar para poder continuar
   		 System.out.println("Descarga pausada...");
   	 } else if(e.getActionCommand().equals(_cancelarDescarga)) {
   		 System.out.println("Uy, seguro que quieres cancelarla?");
   	 }
    }

    /**
     * 
     */
	public void conectar() {
   	 	_iniciar.setEnabled(true);
	    _pausar.setEnabled(false);
	}

    /**
     * 
     */
	public void desconectar() {
   	 	_iniciar.setEnabled(false);
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
   	 adaptador.realizarAccion(e);
    }
}