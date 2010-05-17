package gui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Esta clase es la encargada de modificar el comportamiento por defecto de los
 * items del objeto JList, donde en lugar de ser sólo texto, es texto+imagen
 * @author sema
 */
class MiListaRenderer implements ListCellRenderer {
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index,
                                  boolean isSelected, boolean cellHasFocus) {

        // Instanciamos un JLabel que nos permite texto + imagen
        JLabel renderer = (JLabel) defaultRenderer.
                getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);

        // Indicamos que el texto permanece invariable
        renderer.setText(value.toString()); 
        // El icono que le acompaña será el correspondiente a su extensión
        renderer.setIcon(new ImageIcon(getClass().getResource(evaluarExtension(value.toString()))));

        // Devolvemos ese JLabel modificado a nuestras necesidades
        return renderer;

  }

    /**
     * Función que devuelve la ruta a la imagen correspondiente a la extensión
     * del archivo que se está mostrando
     * @param nombre Nombre del archivo a evaluar, sólo se toma su extensión
     * @return rutaImagen Devuelve la imagen que debe acompañar a ese archivo
     */
    public String evaluarExtension(String nombre) {
        if(nombre.endsWith(".asc")) // ActionScript
            return "/gui/extensions/ext_asc.png";
        else if(nombre.endsWith(".avi")|| nombre.endsWith(".mpg")) // Video
            return "/gui/extensions/ext_avi.png";
        else if(nombre.endsWith(".c")) // Código fuente C
            return "/gui/extensions/ext_c.png";
        else if(nombre.endsWith(".c++") || nombre.endsWith(".cpp")) // Código fuente C++
            return "/gui/extensions/ext_c++.png";
        else if(nombre.endsWith(".doc") || nombre.endsWith(".odt")) // Docuemento de word
            return "/gui/extensions/ext_doc.png";
        else if(nombre.endsWith(".fla")) // Archivos flash
            return "/gui/extensions/ext_fla.png";
        else if(nombre.endsWith(".h")) // Archivo de cabecera
            return "/gui/extensions/ext_h.png";
        else if(nombre.endsWith(".html") || nombre.endsWith(".htm")) // Documento HTML
            return "/gui/extensions/ext_html.png";
        else if(nombre.endsWith(".png") || nombre.endsWith(".jpg") || nombre.endsWith(".gif")) // Imagen
            return "/gui/extensions/ext_image.png";
        else if(nombre.endsWith(".java")) // Código fuente Java
            return "/gui/extensions/ext_java.png";
        else if(nombre.endsWith(".mp3")) // Música
            return "/gui/extensions/ext_mp3.png";
        else if(nombre.endsWith(".php")) // Código fuente PHP
            return "/gui/extensions/ext_php.png";
        else if(nombre.endsWith(".ppt")) // Presentación
            return "/gui/extensions/ext_ppt.png";
        else if(nombre.endsWith(".txt")) // Documento de texto plano
            return "/gui/extensions/ext_txt.png";
        else if(nombre.endsWith(".xls"))
            return "/gui/extensions/ext_xls.png";
        else if(nombre.endsWith(".rar") || nombre.endsWith(".zip") || nombre.endsWith(".tar.gz"))
            return "/gui/extensions/ext_zip.png";
        else if(nombre.endsWith(".pdf")) // Documento pdf
            return "/gui/extensions/ext_pdf.png";
        else // Y si no reconoce el tipo de archivo, muestra uno en blanco
            return "/gui/extensions/ext_null.png";
  }
}