package gui;


import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * Clase que redefine el comportamiento de la tabla en su tercera columna, es
 * decir, la columna que contiene la barra de progreso
 */
public class MiBarraProgreso implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
            return (JProgressBar) value;
    }
}