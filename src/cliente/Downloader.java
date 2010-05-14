package cliente;

import java.util.ArrayList;

import middleware.MiddlewareException;
import coordinador.Archivo;
import coordinador.Coordinador;

public class Downloader extends Thread {
	private Archivo _arch;
	private long _tamPieza;
	private final String _ruta;
	private ArrayList<parteArchivo> _descargar;
	private Coordinador _coord;
	private boolean[] _pedidos;  //true si la _descarga[i] ha sido solicitada
	private boolean[] _peersSolicitados;  //true si existe un hilo pidiendole al peer[i]
	private boolean[] _seedsSolicitados;  //true si existe un hilo pidiendole al seed[i]
	private Semaforo _lanzados;
	private Semaforo _escribir;
	

	// Añade los rangos a descargar en el array _descargar respetando el tamaño máximo de pieza
	private void anotarDescarga(long inicio, long fin) {
		long finActual=inicio+_tamPieza;
		parteArchivo aux;

		while(finActual<fin) {
			aux=new parteArchivo(inicio,finActual);
			_descargar.add(aux);
			inicio=finActual;
			finActual+=_tamPieza;
		}
		
		aux=new parteArchivo(inicio,fin);
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
		
		//Inicializa el vector _pedidos
		_pedidos=new boolean[_descargar.size()];
		for(i=0; i<_pedidos.length;i++)
			_pedidos[i]=false;
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
		
		while(!aux && i<_pedidos.length) {
			if(!_pedidos[i]) aux=true;
			else i++;
		}
		
		return aux;
	}
	
	
	private Peticion lanzarHilo(int idUsuario, parteArchivo pieza) {
		Peticion p=null;
		
		_lanzados.bajar();
		
		try {
			p=new Peticion(_ruta,_coord.getUsuario(idUsuario), _arch.nombre(), pieza,_lanzados,_escribir);
			p.start();
		}
		catch (MiddlewareException e) {
			e.printStackTrace();
		}
		
		return p;
	}

	
	// Constructor de la clase. Almacena la información necesaria para comenzar la descarga.
	public Downloader(Archivo arch, parteArchivo[] partes, int numConex, long tamPieza, String ruta, Coordinador coord) {
		super();
		_arch=arch;
		_lanzados=new Semaforo(numConex);
		_escribir=new Semaforo(1);
		_tamPieza=tamPieza;
		_ruta=ruta;
		_descargar=new ArrayList<parteArchivo>();
		_coord=coord;
		
		calcularDescargas(partes);
	}
	

	// Comienza a descargar el archivo lanzando un hilo por cada petición.
	public void run() {
		parteArchivo aux;
		int[] seeds=_arch.getSeeds();
		int[] peers=_arch.getPeers();
		_peersSolicitados=new boolean[peers.length];
		_seedsSolicitados=new boolean[seeds.length];		
		
		for(int i=0;i<peers.length;i++) _peersSolicitados[i]=false;
		for(int i=0;i<seeds.length;i++) _seedsSolicitados[i]=false;		

		System.out.println("Hay que descargar las siguientes piezas: ");
		for(int i=0;i<_descargar.size();i++) {
			aux=_descargar.get(i);
			System.out.println(aux.inicio+"-"+aux.fin+":"+i);
		}
		System.out.println("Conexiones: "+_lanzados.getInicial()+", Usuarios: "+(seeds.length+peers.length));
		
		
		System.out.println("Lanzando hilos...");
		ArrayList<Peticion> hilos=new ArrayList<Peticion>();
		Peticion ultimoHilo;
		
		for(int i=0;i<_descargar.size();i++) {
			//buscar pieza en los peers
			int j=0;
			while(!_pedidos[i] && j<peers.length) {
				if(!_peersSolicitados[j]) {
					if(buscarPieza(peers[j],i)) {
						System.out.println("peers["+j+"]:"+_descargar.get(i).inicio+"-"+_descargar.get(i).fin);
						_pedidos[i]=true;
						_peersSolicitados[j]=true;
						ultimoHilo=lanzarHilo(peers[j],_descargar.get(i));
						if(ultimoHilo!=null) hilos.add(ultimoHilo);
					}
					else j++;
				}
				else j++;
			}
			
			//lanzar seed si no se ha lanzado un peer
			if(!_pedidos[i]) {
				j=0;
				while(!_pedidos[i] && j<seeds.length)
					if(_seedsSolicitados[j]) j++;
					else {
						System.out.println("seeds["+j+"]:"+_descargar.get(i).inicio+"-"+_descargar.get(i).fin);
						_pedidos[i]=true;
						_seedsSolicitados[j]=true;
						ultimoHilo=lanzarHilo(seeds[j],_descargar.get(i));
						if(ultimoHilo!=null) hilos.add(ultimoHilo);
					}
			}
		}
	}
	
}
