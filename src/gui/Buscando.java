/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

/**
 *
 * @author sema
 */
public class Buscando extends Thread {

    private javax.swing.JLabel barraProgreso;
    private javax.swing.JPanel panel;

    Buscando(javax.swing.JLabel barraProgreso, javax.swing.JPanel panel) {
        super();
        this.barraProgreso = barraProgreso;
        this.panel = panel;
    }

    public void run() {
        panel.add(barraProgreso);
        
        barraProgreso.setVisible(true);
        barraProgreso.revalidate();
        barraProgreso.repaint();
    }

    public void parar() {
        panel.remove(barraProgreso);

        barraProgreso.setVisible(false);
        barraProgreso.revalidate();
        barraProgreso.repaint();
        super.stop();
    }

}
