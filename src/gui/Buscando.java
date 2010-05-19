/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.Cursor;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import coordinador.Archivo;

import cliente.UsuarioClient;

/**
 *
 * @author sema
 */
public class Buscando extends Thread {

    private javax.swing.JLabel barraProgreso;
    private gui.ClienteP2P interfaz;
    
    private UsuarioClient _cliente;
    private String _nombre;
    private Hashtable<Integer,Archivo> _tablaResBusqueda;
	private DefaultTableModel _modeloTablaBusqueda;

    /**
     * Constructor parametrizado del hilo "Buscando". Este recibe las referencias
     * @param aplicacionPpal Reperencia al objeto ClientP2P
     * @param loaderBuscando Referencia al objeto _loaderBuscando de la interfaz
     */
    Buscando(ClienteP2P aplicacionPpal, JLabel loaderBuscando, UsuarioClient cliente, Hashtable<Integer,Archivo> tablaResBusqueda) {
        super();
        this.interfaz = aplicacionPpal;
        this.barraProgreso = loaderBuscando;
        _cliente=cliente;
        _tablaResBusqueda=tablaResBusqueda;
    }

    
    public void setNombre(String nombre) {
    	_nombre=nombre;
    }
    
    /**
     * Método que se ejecuta al realizar hilo.start()
     */
    public void run() {

        //interfaz.opcionP2PLimpiarResultadosActionPerformed(new ActionEvent);

        interfaz.cambiarEstado("Buscando coincidencias..."); // Informar de la búsqueda
        interfaz.setCursor(new Cursor(Cursor.WAIT_CURSOR)); // Pone el cursor en "loading"
        barraProgreso.setVisible(true); // Pone visible la animación
        Archivo resultado;
        interfaz.limpiarResultadosBusqueda();

        // Comunicación con el coordinador
        resultado=_cliente.buscar(_nombre);
        if(resultado!=null) {
        	interfaz.nuevoResultado(resultado.nombre(), resultado.tam(), resultado.checksum(), resultado.getSeeds().length, resultado.getPeers().length);
        	
        	int i=0;  //Para implementar posteriormente la obtencion de varios archivos
        	_tablaResBusqueda.put(i, resultado);
        }

        // Cuando se termina la comunicación con el coordinador (es decir, se ha
        // recibido todos los datos de él), se oculta la animación
        parar();
    }

    /**
     * Método que se ejecuta al llamar a hilo.parar()
     */
    public void parar() {
        interfaz.cambiarEstado("Búsqueda terminada..."); // Informamos del hecho
        barraProgreso.setVisible(false); // Ocultamos la animación
        interfaz.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Pone el puntero en normal
//        try {
//            Thread.sleep(9000); // Establecemos un tiempo prudencial para mostrar el mensaje en el estado
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Buscando.class.getName()).log(Level.SEVERE, null, ex);
//        }
        interfaz.cambiarEstado(""); // Y ponemos el texto por defecto
    }

}
