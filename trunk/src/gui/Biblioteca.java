/*
 * Esta clase es la encargada de crear la biblioteca (fichero XML)
 */

package gui;


import java.io.*;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import com.sun.org.apache.xerces.jaxp.DocumentBuilderImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

/**
 *
 * @author sema
 */
public class Biblioteca {

    // Etiquetas del archivo XML
    private static final String ETIQUETA_BIBLIO = "biblioteca";
    private static final String ETIQUETA_ARCHIVO = "archivo";
    private static final String ETIQUETA_NOMBRE = "nombre";
    private static final String ETIQUETA_RUTA = "ruta";
    private static final String ETIQUETA_TAM = "tam";
    private static final String ETIQUETA_CHECKSUM = "checksum";
    private static final String ETIQUETA_SEEDS = "seed";
    private static final String ETIQUETA_PEERS = "peers";
    private static final String ETIQUETA_PARTE = "parte";
    private static final String ETIQUETA_ID = "id";

    // Codificacion
    private static final String XML_VERSION = "1.0";
    private static final String XML_ENCODING = "UTF-8";
    private static final String JAVA_ENCODING = "UTF-8";

    // Nombre del archivo
    private static final String NOMBRE_ARCHIVO_XML = "src/gui/MiBiblioteca.xml";

    // Objetos
    private Document documentoXML = null;
    private Element biblioteca = null;

    /*
     * El constructor crea la estructura genérica del documento XML, con el nodo raíz
     */
    Biblioteca () {
        try {
            // Creamos objeto DocumentBuilderFactory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactoryImpl.newInstance();

            // A partir del objeto DocumentBuilderFactory se crea un objeto DocumentBuilder
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            // Se generar el documento XML
            documentoXML = docBuilder.newDocument();
        } catch (Exception e) { System.out.println("Error : " + e); }
        
        // Creamos el elemento "biblioteca", el que será raiz
        biblioteca = documentoXML.createElement(ETIQUETA_BIBLIO);
        documentoXML.appendChild(biblioteca); // Agregar al documento principal
    }

    /*
     * Esta clase es la que añade toda la información de la biblioteca, es decir,
     * es la que va insertando cada nodo "archivo"
     */
    public void insertarNuevoArchivo(String nombre, String ruta, long tam, long hash,
            boolean seed, long[] partes){
        Element _archivo, _peers;
        Element _nombre, _ruta, _tam, _hash, _seeds, _parte;

        // Podemos también asignar un atributo
        //elemento.setAttribute(ETIQUETA_ID, "" + valorAtributo);

        // Añadimos un nuevo hijo del nodo root (raíz), es decir, un nuevo nodo
        _archivo = documentoXML.createElement(ETIQUETA_ARCHIVO); // Creamos el elemento / Nodo
        biblioteca.appendChild(_archivo); // Añadimos <archivo></archivo>

        // Creamos: <nombre></nombre>
        _nombre = documentoXML.createElement(ETIQUETA_NOMBRE); // El nodo en sí
        _nombre.appendChild(documentoXML.createTextNode(nombre)); // El valor del nodo
        _archivo.appendChild(_nombre); // Lo metemos en el documento XML


        // Creamos <ruta>/ruta/completa/al/archivo</ruta>
        _ruta = documentoXML.createElement(ETIQUETA_RUTA);
        _ruta.appendChild(documentoXML.createTextNode(ruta));
        _archivo.appendChild(_ruta);

        // Creamos <tam>19234252</tam>
        _tam = documentoXML.createElement(ETIQUETA_TAM);
        _tam.appendChild(documentoXML.createTextNode(String.valueOf(tam)));
        _archivo.appendChild(_tam);

        // Creamos <checksum>1927761238461</checksum>
        _hash = documentoXML.createElement(ETIQUETA_CHECKSUM);
        _hash.appendChild(documentoXML.createTextNode(String.valueOf(hash)));
        _archivo.appendChild(_hash);
        
        // Creamos <seed>1</seed>
        _seeds = documentoXML.createElement(ETIQUETA_SEEDS);
        _seeds.appendChild(documentoXML.createTextNode(String.valueOf(seed)));
        _archivo.appendChild(_seeds);

        // Creamos <peers></peers>
        _peers = documentoXML.createElement(ETIQUETA_PEERS); // Creamos el elemento / Nodo
        _archivo.appendChild(_peers); // Añadimos <peers></peers>

        // Ahora insertamos todas las partes que existan. Como mínimo, son dos
        for(int i=0; i<partes.length; i++) {
            _parte = documentoXML.createElement(ETIQUETA_PARTE);
            _parte.appendChild(documentoXML.createTextNode(String.valueOf(String.valueOf(partes[i]))));
            _peers.appendChild(_parte);
        }
    }


    public boolean guardarXML() {
        boolean resultado = true;
        StringWriter escritor = new StringWriter();
        XMLSerializer serializadorXML = new XMLSerializer();
        OutputFormat formatoSalida = new OutputFormat();

        try {
            // Se establecen los formatos de salida
            formatoSalida.setEncoding(XML_ENCODING);
            formatoSalida.setVersion(XML_VERSION); // Versión del XML
            formatoSalida.setIndenting(true);
            formatoSalida.setIndent(3); // Identación activada a 3 espacios

            serializadorXML.setOutputCharStream(escritor); // Buffer intermedio
            serializadorXML.setOutputFormat(formatoSalida); // Se aplica el formato
            serializadorXML.serialize(documentoXML); // Se serializa en un documento XML

            OutputStream fout = new FileOutputStream(NOMBRE_ARCHIVO_XML);
            OutputStream bout = new BufferedOutputStream(fout);
            OutputStreamWriter out = new OutputStreamWriter(bout, JAVA_ENCODING);

            out.write(escritor.toString());
            out.flush(); // Vacía el buffer
            out.close(); // Cierra el flujo
            escritor.close(); // Cierra este otro flujo        
        } catch (UnsupportedEncodingException e) { 
            System.out.println("Error codificacion");
            resultado = false;
        } catch (IOException e) { 
            System.out.println(e.getMessage());
            resultado = false;
        } catch (Exception e) { 
            System.out.println("Error : " + e);
            resultado = false;
        } /*finally {
            
        }*/
        return resultado;
    }
}
