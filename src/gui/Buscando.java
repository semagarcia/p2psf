/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author sema
 */
public class Buscando extends Thread {

    private javax.swing.JLabel barraProgreso;
    private gui.ClienteP2P interfaz;

    /**
     * Constructor parametrizado del hilo "Buscando". Este recibe las referencias
     * @param aplicacionPpal Reperencia al objeto ClientP2P
     * @param loaderBuscando Referencia al objeto _loaderBuscando de la interfaz
     */
    Buscando(ClienteP2P aplicacionPpal, JLabel loaderBuscando) {
        super();
        this.interfaz = aplicacionPpal;
        this.barraProgreso = loaderBuscando;
    }

    /**
     * Método que se ejecuta al realizar hilo.start()
     */
    public void run() {

        //interfaz.opcionP2PLimpiarResultadosActionPerformed(new ActionEvent);

        interfaz.cambiarEstado("Buscando coincidencias..."); // Informar de la búsqueda
        interfaz.setCursor(new Cursor(Cursor.WAIT_CURSOR)); // Pone el cursor en "loading"
        barraProgreso.setVisible(true); // Pone visible la animación

        // Comunicación con el coordinador
        try {
            Thread.sleep(3000); // Simulamos la búsqueda
            interfaz.nuevoResultado("hola.txt", 42034, 1862738, 5, 2);
            Thread.sleep(1000); // Simulamos la búsqueda
            interfaz.nuevoResultado("hola2.txt", 4205, 734128, 2, 5);
        } catch (InterruptedException ex) {
            Logger.getLogger(Buscando.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            Thread.sleep(9000); // Establecemos un tiempo prudencial
        } catch (InterruptedException ex) {
            Logger.getLogger(Buscando.class.getName()).log(Level.SEVERE, null, ex);
        }
        interfaz.cambiarEstado(""); // Y ponemos el texto por defecto
    }

}
