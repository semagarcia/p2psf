/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
 *
 * @author sema
 */
public class Worker extends SwingWorker {

    private JLabel _label;

    Worker(JLabel label) {
        _label = label;
    }

    @Override
    protected Object doInBackground() throws Exception {
        _label.setVisible(true);
        _label.validate();
        _label.revalidate();
        _label.repaint();
        System.out.println("He pasado por aki");
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }


}
