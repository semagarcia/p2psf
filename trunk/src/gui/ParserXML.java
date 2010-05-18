package gui;

import cliente.EstrArchivo;
import cliente.infoArchivo;
import cliente.parteArchivo;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;



/**
 * Clase encargada del parseo y extracción de información desde el fichero
 * XML especificado
 * @author sema
 */
public class ParserXML {

    private Document DOM;
    //private List listadoDescargas = new ArrayList();
    private EstrArchivo[] eas;
    private String nombreFichero;

    // Etiquetas del archivo XML
    private static final String ETIQUETA_ARCHIVO = "archivo";
    private static final String ETIQUETA_NOMBRE = "nombre";
    private static final String ETIQUETA_RUTA = "ruta";
    private static final String ETIQUETA_TAM = "tam";
    private static final String ETIQUETA_CHECKSUM = "checksum";
    private static final String ETIQUETA_SEEDS = "seed";
    private static final String ETIQUETA_PEERS = "peers";
    private static final String ETIQUETA_PARTE = "parte";


    /**
     * Constructor parametrizado. Recibe el nombre del XML a parsear
     * @param nomFich
     */
    ParserXML(String nomFich) {
        nombreFichero = nomFich; // Obtenemos el nombre del fichero xml (la biblioteca)
    }


    /**
     * Esta función se encarga de generar un documento o arbol XML en memoria a
     * partir de un fichero dado
     */
    public void parsearArchivoXML() {
        
        try {
            // Se instancia una factoría para crear el parser
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // Se utiliza la factoría para crear el parser y utilizarlo
            DocumentBuilder db = dbf.newDocumentBuilder();
            DOM = db.parse("src/gui/" + nombreFichero); // Parseamos el archivo XML
        } catch (ParserConfigurationException pce) { pce.printStackTrace();
        } catch (SAXParseException spe) { System.out.println("Excepction SAX Parse: XML mal formado");
        } catch (SAXException se) { se.printStackTrace(); System.out.println("Excepcion SAX");
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }

    
    /**
     * Esta función parsea el archivo XML y extrae los datos de él
     * @param listaFicherosCompartidos para poder acceder al JList
     */
    public void parsearDocumento(javax.swing.DefaultListModel lista, ArrayList<EstrArchivo> eas) {
        Element docEle = DOM.getDocumentElement(); // Obtiene el documento raiz
        // Buscamos el nodo de más alto nivel: <archivo></archivo>
        NodeList nl = docEle.getElementsByTagName(ETIQUETA_ARCHIVO);

        // Creamos un vector de archivos de tantas dimensiones como archivos haya en el XML
        eas = new ArrayList<EstrArchivo>();

        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                // Comprobacion para saber que el item i es un nodo
                if (nl.item(i).getNodeType() == nl.item(i).ELEMENT_NODE) {
                    Element elementoRaiz = (Element) nl.item(i);

                    // Obtenemos el valor de la etiqueta <nombre></nombre>
                    NodeList nodoNombre = elementoRaiz.getElementsByTagName(ETIQUETA_NOMBRE);
                    Element nombreElemento = (Element) nodoNombre.item(0);
                    //archivosUsuario[i].info.nombre = nombreElemento.getFirstChild().getNodeValue();
                    String nombre = nombreElemento.getFirstChild().getNodeValue();

                    // Obtenemos el valor de la etiqueta <ruta></ruta>
                    NodeList nodoRuta = elementoRaiz.getElementsByTagName(ETIQUETA_RUTA);
                    Element rutaElemento = (Element) nodoRuta.item(0);
                    //archivosUsuario[i].info.ruta = rutaElemento.getFirstChild().getNodeValue();
                    String ruta = rutaElemento.getFirstChild().getNodeValue();
                    lista.addElement(ruta); // Con esto se añade a la interfaz

                    // Obtenemos el valor de la etiqueta <tam></tam>
                    NodeList nodoTam = elementoRaiz.getElementsByTagName(ETIQUETA_TAM);
                    Element tamElemento = (Element) nodoTam.item(0);
                    //archivosUsuario[i].info.tam = Long.parseLong(tamElemento.getFirstChild().getNodeValue());
                    long tam = Long.parseLong(tamElemento.getFirstChild().getNodeValue());

                    // Obtenemos el valor de la etiqueta <checksum></checksum>
                    NodeList nodoHash = elementoRaiz.getElementsByTagName(ETIQUETA_CHECKSUM);
                    Element hashElemento = (Element) nodoHash.item(0);
                    //archivosUsuario[i].info.checksum = Long.parseLong(hashElemento.getFirstChild().getNodeValue());
                    long checksum = Long.parseLong(hashElemento.getFirstChild().getNodeValue());

                    // Obtenemos el valor de la etiqueta <seed></seed>
                    NodeList nodoSeed = elementoRaiz.getElementsByTagName(ETIQUETA_SEEDS);
                    Element seedElemento = (Element) nodoSeed.item(0);
                    // Con el campo seed del XML que hacemos?? para que lo recuperamos??
                    
                    // Creamos la información referente al nuevo archivo
                    infoArchivo info = new infoArchivo(ruta, nombre, tam, checksum);

                    // Obtenemos el acceso al nodo cuya etiqueta es <peers></peers>
                    NodeList nodoPeers = elementoRaiz.getElementsByTagName(ETIQUETA_PEERS);
                    Element peerElemento = (Element) nodoPeers.item(0);
                    // Y ahora el valor de las n-subetiquetas que haya <parte></parte>
                    NodeList nodoParte = peerElemento.getElementsByTagName(ETIQUETA_PARTE);

                    // Iteramos por cada uno de los nodos <parte></parte> que hay
                    // Hacemos mas eficiente la consulta hacia el número de partes
                    int numPartes = nodoParte.getLength();
                    parteArchivo[] partes = new parteArchivo[numPartes];
                    
                    for (int j=0; j<numPartes; j=j+2) {
                        // Reservamos memoria para la nueva parte
                        partes[j] = new parteArchivo();
                        // Insertamos el inicio y fin de cada parte
                        partes[j].inicio = Long.parseLong(nodoParte.item(j).getFirstChild().getNodeValue());
                        partes[j].fin = Long.parseLong(nodoParte.item(j+1).getFirstChild().getNodeValue());
                        partes[j].pedido = false;
                        partes[j].descargado = false;
                    }
                    
                    // Reservamos memoria para el archivo i-ésimo
                    eas.add(new EstrArchivo(info, partes));
                    
                    
                } // Fin del if(comprobacion es un nodo)
            } // Fin del for (int i = 0; i < nl.getLength(); i++)
        }
    }
}
