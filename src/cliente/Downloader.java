package cliente;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import middleware.MiddlewareException;
import coordinador.Archivo;
import coordinador.Coordinador;

public class Downloader extends Thread {
	private Archivo _arch;
	private long _tamPieza;
	private final String _ruta;
	private ArrayList<parteArchivo> _descargar;
	private Coordinador _coord;
	private Semaforo _lanzados;
	private Semaforo _escribir;
	private Semaforo _accederEas;
	private Hashtable<String, EstrArchivo> _eas;
	private Hashtable<Integer, Boolean> _seedsSolicitados, _peersSolicitados;
	private int _miId;
	

	// Añade los rangos a descargar en el array _descargar respetando el tamaño máximo de pieza
	private void anotarDescarga(long inicio, long fin) {
		long finActual=inicio+_tamPieza;
		parteArchivo aux;

		while(finActual<fin) {
			aux=new parteArchivo(inicio,finActual,false,false);
			_descargar.add(aux);
			inicio=finActual;
			finActual+=_tamPieza;
		}
		
		aux=new parteArchivo(inicio,fin,false,false);
		_descargar.add(aux);
	}
	
	
	
	// Anota las partes a descargar
	private void calcularDescargas(parteArchivo[] partes) {
		long inicioActual=0;
		boolean descartar=(partes!=null);
		int i=0;
		
		while(descartar) {
			//Descartar partes
			if(inicioActual<partes[i].inicio) {
				//Añadir desde inicioActual hasta partes[i]
				anotarDescarga(inicioActual,partes[i].inicio);
				inicioActual=partes[i].fin;
				i++;
				if(i>=partes.length) descartar=false;
			}
			else {
				//Descartar desde _partes[i] hasta _partes[i+1]
				inicioActual=partes[i].fin;
				i++;
				if(i>=partes.length) descartar=false;
			}
		}
		//Añadir ultima parte del archivo
		if(inicioActual<_arch.tam())
			anotarDescarga(inicioActual,_arch.tam());
	}


	private boolean buscarPieza(int indicePeer, int indiceDescarga) {
		boolean aux=false;
		parteArchivo pieza=_descargar.get(indiceDescarga);
		parteArchivo[] piezasPeer=_arch.getPartes(indicePeer);
		int i=0;
		
		boolean encontradoSuperior=false;
		while(!encontradoSuperior && i<piezasPeer.length)
			if(pieza.fin>piezasPeer[i].fin) encontradoSuperior=true;
			else i++;
		
		if(i<piezasPeer.length)
			if(pieza.inicio>=piezasPeer[i].inicio)
				aux=true;
		
		return aux;
	}
	

	// Devuelve true si quedan piezas por pedir, false en caso contrario
	private boolean pedir() {
		boolean aux=false;
		int i=0;

		// Hay que ejecutarlo en exclusión mutua, ya que podría eliminarse una pieza a descargar
		while(!aux && i<_descargar.size()) {
			if(!_descargar.get(i).pedido && !_descargar.get(i).descargado) aux=true;
			else i++;
		}
		
		return aux;
	}
	
	
	private Peticion crearHilo(int idUsuario, parteArchivo pieza, Hashtable<Integer, Boolean> usuarios) {
		Peticion p=null;
		
		try {
			p=new Peticion(_ruta,_coord.getUsuario(idUsuario), _arch.nombre(), _arch.tam(), _arch.checksum(), pieza,_lanzados, _escribir, usuarios, idUsuario, _miId, _accederEas, _eas, _arch);
		}
		catch (MiddlewareException e) {
			e.printStackTrace();
		}
		
		return p;
	}

	
	// Constructor de la clase. Almacena la información necesaria para comenzar la descarga.
	public Downloader(Archivo arch, parteArchivo[] partes, int numConex, long tamPieza, String ruta, Coordinador coord, int miId, Semaforo accederEas, Hashtable<String, EstrArchivo> eas) {
		super();
		_arch=arch;
		_lanzados=new Semaforo(numConex);
		_escribir=new Semaforo(1);
		_tamPieza=tamPieza;
		_ruta=ruta;
		_descargar=new ArrayList<parteArchivo>();
		_coord=coord;
		_miId=miId;
		_seedsSolicitados=new Hashtable<Integer, Boolean>();
		_peersSolicitados=new Hashtable<Integer, Boolean>();
		_accederEas=accederEas;
		_eas=eas;
		
		calcularDescargas(partes);
	}
	

	// Comienza a descargar el archivo lanzando un hilo por cada petición.
	public void run() {
		try {

			int i=0;
		
			while(pedir()) {
				actualizarUsuarios();			
				lanzarPeticiones();
				i++;
			}
		}
		catch (org.omg.CORBA.OBJECT_NOT_EXIST e1) {
			System.out.println("OBJECT_NOT_EXIST");
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	
	private void lanzarPeticiones() {
		Enumeration<Integer> keys;
		int usuario, i;
		ArrayList<Peticion> hilos=new ArrayList<Peticion>();
		
		System.out.println("bajando");
		_escribir.bajar();
		System.out.println("bajado");

		//Crear hilos
		
		// Limpiar descargas ya realizadas
		for(i=0;i<_descargar.size();i++) {
			if(_descargar.get(i).descargado)
				_descargar.remove(i);
		}
		
		
		for(i=0;i<_descargar.size();i++) {
			//buscar pieza en los peers
			keys=_peersSolicitados.keys();
			while((!_descargar.get(i).pedido || !_descargar.get(i).descargado) && keys.hasMoreElements()) {
				usuario=keys.nextElement();
				if(!_peersSolicitados.get(usuario)) {
					if(buscarPieza(usuario,i)) {
						_descargar.get(i).pedido=true;
						_peersSolicitados.put(usuario,true);
						hilos.add(crearHilo(usuario,_descargar.get(i),_peersSolicitados));
					}
				}
			}
			
			//lanzar seed si no se ha lanzado un peer
			if(!_descargar.get(i).pedido && !_descargar.get(i).descargado) {
				keys=_seedsSolicitados.keys();
				while((!_descargar.get(i).pedido && !_descargar.get(i).descargado) && keys.hasMoreElements()) {
					usuario=keys.nextElement();
					if(!_seedsSolicitados.get(usuario)) {
						_descargar.get(i).pedido=true;
						_seedsSolicitados.put(usuario, true);						
						hilos.add(crearHilo(usuario,_descargar.get(i),_seedsSolicitados));
					}
				}
			}
		}
		
		_escribir.subir();
		
		// Lanzar hilos
		for(i=0;i<hilos.size();i++) {
			_lanzados.bajar();
			hilos.get(i).start();
		}

	}



	// Actualiza las tablas de usuarios
	private void actualizarUsuarios() throws Exception {
		int[] seeds=_arch.getSeeds();
		int[] peers=_arch.getPeers();
		int key, i;
		
		// Eliminar usuarios que no estén
		Enumeration<Integer> aux=_seedsSolicitados.keys();
		while(aux.hasMoreElements()) {
			key=aux.nextElement();
			if(!buscar(key,seeds))
				_seedsSolicitados.remove(key);
		}
		aux=_peersSolicitados.keys();
		while(aux.hasMoreElements()) {
			key=aux.nextElement();
			if(!buscar(key,peers))
				_peersSolicitados.remove(key);
		}
		
		//Añadir usuarios nuevos
		for(i=0;i<seeds.length;i++) {
			if(!_seedsSolicitados.containsKey(seeds[i]))
				_seedsSolicitados.put(seeds[i], false);
		}
		for(i=0;i<peers.length;i++) {
			if(!_peersSolicitados.containsKey(peers[i]) && peers[i]!=_miId)
				_peersSolicitados.put(peers[i], false);
		}		
	}



	private boolean buscar(int valor, int[] vector) {
		boolean encontrado=false;
		int i=0;
		
		while(!encontrado && i<vector.length)
			if(valor==vector[i]) encontrado=true;
			else i++;
		
		return encontrado;
	}



	//solo para depuracion
	private void mostrarDescargar() {
		parteArchivo aux;

		System.out.println("Hay que descargar las siguientes piezas: ");
		for(int i=0;i<_descargar.size();i++) {
			aux=_descargar.get(i);
			System.out.println(aux.inicio+"-"+aux.fin+":"+i+";"+aux.pedido+","+aux.descargado);
		}
	}
	
	
	//solo para depuracion
	private void mostrarUsuarios() {
		System.out.print("Seeds: ");
		Enumeration<Integer> k=_seedsSolicitados.keys();
		while(k.hasMoreElements()) System.out.print(k.nextElement()+" ");
		System.out.print("\nPeers: ");
		k=_peersSolicitados.keys();
		while(k.hasMoreElements()) System.out.print(k.nextElement()+" ");
		System.out.print("\n");
	}
}
