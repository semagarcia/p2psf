package cliente;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;

import coordinador.Archivo;

import middleware.MiddlewareException;

public class Peticion extends Thread {
		
	private String _ruta;
	private Usuario _usuario;
	private String _nombre;
	private parteArchivo _pieza;
	private Semaforo _lanzados;
	private Semaforo _escribir;
	private Semaforo _accederEas;
	private Hashtable<String, EstrArchivo> _eas;
	private Hashtable<Integer, Boolean> _usuarios;
	private int _miId;
	private long _tam;
	private long _checksum;
	private Archivo _arch;
	private int _idUsuario;

	
	/**
	 * Escribe los bytes en el fichero destino comenzando por el byte inicio de dicho fichero 
	 */
	public void escribir(byte[] datos) {
		File destino=new File(_ruta);
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

	
	public Peticion(String ruta, Usuario usu, String nombre, long tam, long checksum, parteArchivo pieza, Semaforo lanzados, Semaforo escribir, Hashtable<Integer, Boolean> usuarios, int idUsuario, int miId, Semaforo accederEas, Hashtable<String, EstrArchivo> eas, Archivo arch) throws MiddlewareException {
		_ruta=ruta;
		_usuario=usu;
		_nombre=nombre;
		_tam=tam;
		_checksum=checksum;
		_pieza=pieza;
		_lanzados=lanzados;
		_escribir=escribir;
		_usuarios=usuarios;
		_idUsuario=idUsuario;
		_miId=miId;
		_accederEas=accederEas;
		_eas=eas;
		_arch=arch;
	}
	
	
	public void run() {
		byte[] parte;
		
		parte=_usuario.solicitarParte(_nombre, _pieza.inicio, _pieza.fin);
		
		_escribir.bajar("run(Peticion)");
		escribir(parte);
		anyadirParte();  //utiliza semaforo EAS
		_pieza.descargado=true;
		_pieza.pedido=false;
		_usuarios.put(_idUsuario, false);
		_escribir.subir("run(Peticion)");

		_lanzados.subir("run(Peticion)");
	}

	
	private void anyadirParte() {
		int i=0, j;
		
		_accederEas.bajar("anyadirParte(Peticion)");
		
		//Anyade el archivo a eas si no existía
		if(_eas.get(_nombre)==null) {
			infoArchivo info=new infoArchivo(_ruta, _nombre, _tam, _checksum);
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
			for(i=0;i<nuevo.length;i++) {
				nuevo[i]=new parteArchivo();
				if(j<anterior.length) {
					if(anterior[j].inicio<=_pieza.inicio) {
						nuevo[i].inicio=anterior[j].inicio;
						nuevo[i].fin=anterior[j].fin;
						j++;
					}
					else {
						nuevo[i].inicio=_pieza.inicio;
						nuevo[i].fin=_pieza.fin;
					}
				}
				else {
					nuevo[i].inicio=_pieza.inicio;
					nuevo[i].fin=_pieza.fin;
				}
			}
			
			ArrayList<parteArchivo> resultado=new ArrayList<parteArchivo>();
			parteArchivo aux;
			
			for(i=0;i<nuevo.length-1;i++) {
				j=i+1;
				boolean salir=false;
				while(j<nuevo.length && !salir) {
					if(nuevo[j].inicio<=nuevo[i].fin)
						j++;
					else
						salir=true;
				}
				aux=new parteArchivo();
				aux.inicio=nuevo[i].inicio;
				aux.fin=nuevo[j-1].fin;
				aux.pedido=false;
				aux.descargado=false;
				resultado.add(aux);
			}
			
			resultado.toArray(_eas.get(_nombre).partes);
		}
		
		_arch.actualizarPartes(_miId, _eas.get(_nombre).partes);
		
		_accederEas.subir("anyadirParte(Peticion)");
	}


	private void mostrarPartes(parteArchivo[] partes) {
		System.out.println("Partes completadas: ");
		for(int i=0;i<partes.length;i++)
			System.out.println(i+"["+partes[i].inicio+"-"+partes[i].fin+"]");		
	}
}
