package cliente;

import gui.ClienteP2P;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;

import coordinador.Archivo;

import middleware.MiddlewareException;

public class Peticion extends Thread {
		
	private Usuario _usuario;
	private String _nombre;
	private parteArchivo _pieza;
	private Downloader _downloader;
	private Hashtable<String, EstrArchivo> _eas;
	private Hashtable<Integer, Boolean> _usuarios;
	private int _miId;
	private long _tam;
	private long _checksum;
	private Archivo _arch;
	private int _idUsuario;
	private ClienteP2P _interfaz;

	
	/**
	 * Escribe los bytes en el fichero destino comenzando por el byte inicio de dicho fichero 
	 */
	public void escribir(byte[] datos) {
		File destino=new File(_downloader.ruta);
		RandomAccessFile fileOut;
		try {
			fileOut = new RandomAccessFile(destino,"rw");
			fileOut.seek(_pieza.inicio);
			fileOut.write(datos);		
			fileOut.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Peticion(Usuario usu, parteArchivo pieza, Downloader downloader, Hashtable<Integer, Boolean> usuarios,
			int idUsuario, int miId, Hashtable<String, EstrArchivo> eas, Archivo arch, ClienteP2P interfaz) throws MiddlewareException {
		_downloader=downloader;
		_usuario=usu;
		_arch=arch;
		_nombre=_arch.nombre();
		_tam=_arch.tam();
		_checksum=_arch.checksum();
		_pieza=pieza;
		_usuarios=usuarios;
		_idUsuario=idUsuario;
		_miId=miId;
		_eas=eas;
		_interfaz=interfaz;
	}
	
	
	public void run() {
		byte[] parte;
		
		System.out.println("PIDO "+_pieza.inicio+"-"+_pieza.fin+_idUsuario);
		
		try {
			parte=_usuario.solicitarParte(_nombre, _pieza.inicio, _pieza.fin);
		}
		catch(Exception e) {
			parte=null;
			System.out.println("\n\n\nEXCEPCION CAPTURADA\n\n\n");
			e.printStackTrace();
		}
		
		_downloader.escribir.bajar();
		
		if(parte!=null) {
			escribir(parte);
			anyadirParte();
			_downloader.addPorcentaje((float)(_pieza.fin-_pieza.inicio)*100/_tam);
System.out.println("Se ha sumado "+(float)(_pieza.fin-_pieza.inicio)*100/_tam);
			_pieza.descargado=true;
			_pieza.pedido=false;
			_usuarios.put(_idUsuario, false);
		}
		else {
			_pieza.descargado=false;
			_pieza.pedido=false;
			_usuarios.put(_idUsuario, false);
		}
		
		//Despierta al hilo Downloader si no lo está ya.
		if(_downloader.esperar.getActual()==0)
			_downloader.esperar.subir();
		_downloader.escribir.subir();
		_downloader.lanzados.subir();
		
		_interfaz.sumarConexiones(_downloader.ruta, -1);
	}

	
	private void anyadirParte() {
		int i=0, j;
		
		_downloader.accederEas.bajar();
		
		//Anyade el archivo a eas si no existía
		if(_eas.get(_nombre)==null) {
			infoArchivo info=new infoArchivo(_downloader.ruta, _nombre, _tam, _checksum);
			EstrArchivo nuevo=new EstrArchivo(info, null);
			_eas.put(info.nombre, nuevo);
		}	
		
		
		parteArchivo[] nuevo;
		parteArchivo[] anterior=_eas.get(_nombre).partes;				
		if(anterior==null) {
			// si no tenia parte, inserta la nueva directamente
			nuevo=new parteArchivo[1];
			nuevo[0]=new parteArchivo();
			nuevo[0].inicio=_pieza.inicio;
			nuevo[0].fin=_pieza.fin;
			nuevo[0].pedido=false;
			nuevo[0].descargado=false;

			_eas.get(_nombre).partes=nuevo;
		}
		else {
			// si tenia partes, las ordena y une partes contiguas
			nuevo=new parteArchivo[anterior.length+1];
			j=0;
			boolean insertada=false;
			for(i=0;i<nuevo.length;i++) {
				nuevo[i]=new parteArchivo();
				if(j<anterior.length) {
					if(anterior[j].inicio<=_pieza.inicio || insertada) {
						nuevo[i].inicio=anterior[j].inicio;
						nuevo[i].fin=anterior[j].fin;
						j++;
					}
					else {
						nuevo[i].inicio=_pieza.inicio;
						nuevo[i].fin=_pieza.fin;
						insertada=true;
					}
				}
				else {
					nuevo[i].inicio=_pieza.inicio;
					nuevo[i].fin=_pieza.fin;
				}
			}
			
			ArrayList<parteArchivo> resultado=new ArrayList<parteArchivo>();
			parteArchivo aux;
			i=0;
			while(i<nuevo.length) {
				aux=new parteArchivo();
				aux.inicio=nuevo[i].inicio;
				aux.fin=nuevo[i].fin;
				aux.pedido=false;
				aux.descargado=false;
				boolean salir=false;
				i++;
				while(!salir && i<nuevo.length) {
					if(aux.fin>=nuevo[i].inicio) {
						aux.fin=nuevo[i].fin;
						i++;
					}
					else
						salir=true;
				}
				resultado.add(aux);
			}

			parteArchivo[] resFinal=new parteArchivo[resultado.size()];
			for(i=0;i<resFinal.length;i++)
				resFinal[i]=resultado.get(i);
				
			_eas.get(_nombre).partes=resFinal;
		}

		_arch.actualizarPartes(_miId, _eas.get(_nombre).partes);
		
		_downloader.accederEas.subir();
	}


	private void mostrarPartes(parteArchivo[] partes) {
		System.out.println("Partes completadas: ");
		for(int i=0;i<partes.length;i++)
			System.out.println(i+"["+partes[i].inicio+"-"+partes[i].fin+"]");		
	}
}
