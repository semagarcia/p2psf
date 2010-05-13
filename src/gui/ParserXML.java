/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;



/**
 *
 * @author sema
 */
public class ParserXML {

    private Document DOM;
    private List listadoDescargas = new ArrayList();
    private String nombreFichero;

    /*
     * Constructor parametrizado
     */
    ParserXML(String nomFich) {
        nombreFichero = nomFich; // Obtenemos el nombre del fichero xml (la biblioteca)
    }


    /*
     * Esta función se encarga de generar un documento o arbol XML en memoria a
     * partir de un fichero dado
     */
    public void parsearArchivoXML() {
        // Se instancia el objeto DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // Se utiliza el objeto DocumentBuilderFactory para crear un DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();
            DOM = db.parse(nombreFichero); // Parseamos el archivo XML
        } catch (ParserConfigurationException pce) { pce.printStackTrace();
        } catch (SAXException se) { se.printStackTrace();
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }

    
    /*
     * Esta función parsea el archivo XML y extrae los datos de él
     */
    public void parsearDocumento() {
        Element docEle = DOM.getDocumentElement(); // Obtiene el documento raiz

        // Buscamos el nodo de más alto nivel: <archivo></archivo>
        NodeList nl = docEle.getElementsByTagName("archivo");
        if (nl != null && nl.getLength() > 0)
            for (int i = 0; i < nl.getLength(); i++) {
                Element elemento = (Element) nl.item(i); // Devuelve el elemento
                //Archivo a = obtenerDescarga(elemento); // Obtiene el objeto
                //listadoDescargas.add(a); // Se añade a la lista
            }
    }


    /**
     * Esta funcion es para mostrar el fichero por pantalla
     */
    public void imprimirResultados() {
        Iterator it = listadoDescargas.iterator();
        while (it.hasNext())
            System.out.println(it.next().toString());
    }


    /**
     * Devuelve un objeto Archivo generado con todos los datos de un nodo de XML
     * @param elemento
     * @return
     */
    public Archivo obtenerDescarga(Element elemento) {
        int id = Integer.parseInt(elemento.getAttribute("id"));
        String titulo = obtenerTexto(elemento, "titulo");
        String ruta = obtenerTexto(elemento, "ruta");
        int hits = obtenerEntero(elemento, "hits");
        // Crear un objeto Descargas con los datos recibidos
        Archivo a = new Archivo(id, titulo, ruta, hits);
        return a;
    }


    /**
     * Devuelve el valor entero del elemento recibido
     * @param elemento
     * @param nombreEtiqueta
     * @return
     */
    private int obtenerEntero(Element elemento, String nombreEtiqueta) {
        return Integer.parseInt(obtenerTexto(elemento, nombreEtiqueta));
    }
    
    
    /**
     * Devuelve la cadena del elemento recibido
     * @param elemento
     * @param nombreEtiqueta
     * @return
     */
    private String obtenerTexto(Element elemento, String nombreEtiqueta) {
        String texto = null;
        NodeList nl = elemento.getElementsByTagName(nombreEtiqueta);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            texto = el.getFirstChild().getNodeValue();
        }
        return texto;
    }
}
