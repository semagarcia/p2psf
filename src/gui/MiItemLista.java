/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

/**
 *
 * @author sema
 */
class MiListaRenderer implements ListCellRenderer {
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index,
                                  boolean isSelected, boolean cellHasFocus) {
    Icon extension = null;
    String archivo = null;

    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list,
                                    value, index, isSelected, cellHasFocus);

    if (value instanceof Object[]) {
        //Object values[] = (Object[]) value;
        //icono = new ImageIcon(getClass().getResource("/gui/images/conectar.png"));

        // Obtenemos la imagen que le corresponde a la extensión de cada archivo
        extension = new ImageIcon(getClass().getResource(evaluarExtension((String) value)));
        //archivo = (String) values[1];
        archivo = (String) value;
        System.out.println("Mira: " + archivo);
    }

    // Si empieza a meter texto extraño, poner al if un else con esto: theText = "";

    //if (!isSelected) {
      //renderer.setForeground(theForeground);
    //}

    // Si por lo que sea no se ha podido establecer el icono y el recurso existe
    if (extension != null) { // AKI SE DEBE INSERTAR UNA IMAGEN DE ARCHIVO DESCONOCIDO!!!!!!!!!!!!!!!!!!!!!!!!!
      renderer.setIcon(extension);
    }

    renderer.setText(archivo); // o también, algo más eficiente, renderer.setText((String) value);
    return renderer;
  }

  public String evaluarExtension(String nombre) {

    // ¿¿¿ IMPLEMENTAR MEDIANTE UN HASHTABLE ???

    if(nombre.endsWith(".asc")) // ActionScript
        return "/gui/images/ext_asc.png";
    else if(nombre.endsWith(".avi")|| nombre.endsWith(".mpg")) // Video
        return "/gui/images/ext_avi.png";
    else if(nombre.endsWith(".c")) // Código fuente C
        return "/gui/images/ext_c.png";
    else if(nombre.endsWith(".c++") || nombre.endsWith(".cpp")) // Código fuente C++
        return "/gui/images/ext_c++.png";
    else if(nombre.endsWith(".doc") || nombre.endsWith(".odt")) // Docuemento de word
        return "/gui/images/ext_doc.png";
    else if(nombre.endsWith(".fla")) // Archivos flash
        return "/gui/images/ext_fla.png";
    else if(nombre.endsWith(".h")) // Archivo de cabecera
        return "/gui/images/ext_h.png";
    else if(nombre.endsWith(".html") || nombre.endsWith(".htm")) // Documento HTML
        return "/gui/images/ext_html.png";
    else if(nombre.endsWith(".png") || nombre.endsWith(".jpg") || nombre.endsWith(".gif")) // Imagen
        return "/gui/images/ext_image.png";
    else if(nombre.endsWith(".java")) // Código fuente Java
        return "/gui/images/ext_java.png";
    else if(nombre.endsWith(".mp3")) // Música
        return "/gui/images/ext_mp3.png";
    else if(nombre.endsWith(".php")) // Código fuente PHP
        return "/gui/images/ext_php.png";
    else if(nombre.endsWith(".ppt")) // Presentación
        return "/gui/images/ext_ppt.png";
    else if(nombre.endsWith(".txt")) // Documento de texto plano
        return "/gui/images/ext_txt.png";
    else if(nombre.endsWith(".xls"))
        return "/gui/images/ext_xls.png";
    else if(nombre.endsWith(".rar") || nombre.endsWith(".zip") || nombre.endsWith(".tar.gz"))
        return "/gui/images/ext_zip.png";
    else if(nombre.endsWith(".pdf")) // Documento pdf
        return "/gui/images/ext_pdf.png";
    else // Y si no reconoce el tipo de archivo, muestra uno en blanco
        return "/gui/images/ext_null.png";
  }
}


public class MiItemLista {
    static ImageIcon a;
    static ImageIcon b;
    static ImageIcon c;
    static ImageIcon d;

    MiItemLista() {
       a = new ImageIcon(getClass().getResource("/gui/images/conectar.png"));
       b = new ImageIcon(getClass().getResource("/gui/images/desconectar.png"));
       c = new ImageIcon(getClass().getResource("/gui/images/next2.png"));
       d = new ImageIcon(getClass().getResource("/gui/images/share.png"));

    }

    public static void main(String args[]) {
        Object elements[][] = {
            { new javax.swing.JLabel(a).getIcon(), "A"},
            { new javax.swing.JLabel(b).getIcon(), "B"},
            { new javax.swing.JLabel(c).getIcon(), "C"},
            { new javax.swing.JLabel(d).getIcon(), "D"}};

        JFrame frame = new JFrame("Complex Renderer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ListCellRenderer renderer = new MiListaRenderer();
        JList lista = new JList(elements);
        lista.setCellRenderer(renderer);

        JScrollPane scrollPane = new JScrollPane(lista);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}