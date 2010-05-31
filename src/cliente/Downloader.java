package cliente;


import gui.ClienteP2P;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import middleware.MiddlewareException;
import coordinador.Archivo;
import coordinador.Coordinador;


/**
 * Clase que gestiona la descarga de un fichero.
 */
public class Downloader extends Thread {
	
	/** Ruta de descarga del fichero. */
	public final String ruta;
	
	/** Semaforo que controla el número de hilos lanzados. */
	public Semaforo lanzados;
	
	/** Semaforo que controla el acceso a la escritura en el fichero. */
	public Semaforo escribir;
	
	/** Semaforo que controla el acceso a la información de archivos local. */
	public Semaforo accederEas;
	
	/** Semaforo para que el hilo Downloader no esté en espera activa. */
	public Semaforo esperar;

	
	
	/**
	 * Constructor de la clase. Almacena la información necesaria para comenzar la descarga. 
	 * @param arch Referencia al archivo en CORBA.
	 * @param partes Partes del archivo que ya posee el usuario.
	 * @param porcentaje Porcentaje de la descarga completado.
	 * @param numConex Número de conexiones máximas que pueden establecerse por fichero.
	 * @param tamPieza Tamaño de la pieza a descargar por petición.
	 * @param ruta Ruta donde se decargará el fichero.
	 * @param coord Referencia al coordinador en CORBA.
	 * @param miId Identificador del usuario que realiza la descarga.
	 * @param accederEas Semaforo para acceder a la información local de archivos.
	 * @param eas Información local de archivos.
	 * @param interfaz Referencia a la interfaz de usuario.
	 */
	public Downloader(Archivo arch, parteArchivo[] partes, float porcentaje, int numConex, long tamPieza, String ruta, Coordinador coord, int miId, Semaforo accederEas, Hashtable<String, EstrArchivo> eas, ClienteP2P interfaz) {
		super();
		_arch=arch;
		_nombre=_arch.nombre();
		lanzados=new Semaforo(numConex);
		escribir=new Semaforo(1);
		esperar=new Semaforo(1);
		_esperarDownloader=new Semaforo(1);
		_tamPieza=tamPieza;
		this.ruta=ruta;
		_descargar=new ArrayList<parteArchivo>();
		_coord=coord;
		_miId=miId;
		_seedsSolicitados=new Hashtable<Integer, Boolean>();
		_peersSolicitados=new Hashtable<Integer, Boolean>();
		this.accederEas=accederEas;
		_eas=eas;
		_interfaz=interfaz;
		_parar=false;
		_porcentaje=new Float(porcentaje);
		
		calcularDescargas(partes);
	}
	
	/**
	 * Constructor de la clase. Almacena la información necesaria para comenzar la descarga.
	 * Este constructor está pensado para cuando se ha parado la descarga y se quiere almacenar el
	 * estado de la misma para poder continuarla, debido a que los hilos no pueden llamar a su método
	 * start() dos veces. Por este motivo, el mecanismo es realizar una copia del estado de la descarga
	 * y volver a lanzar un nuevo hilo con dicha información.
	 * @param arch Referencia al archivo en CORBA.
	 * @param numConex Número de conexiones máximas que pueden establecerse por fichero.
	 * @param ruta Ruta donde se decargará el fichero.
	 * @param tamPieza Tamaño de la pieza a descargar por petición.
	 * @param coord Referencia al coordinador en CORBA.
	 * @param miId Identificador del usuario que realiza la descarga.
	 * @param eas Información local de archivos.
	 * @param accederEas Semaforo para acceder a la información local de archivos.
	 * @param interfaz Referencia a la interfaz de usuario.
	 * @param porcentaje Porcentaje de la descarga completado.
	 * @param descargar Piezas que quedan por descargar del fichero.
	 */
	public Downloader(Archivo arch, int numConex, String ruta, long tamPieza, Coordinador coord, int miId, Hashtable<String,EstrArchivo> eas, Semaforo accederEas, Float porcentaje, ClienteP2P interfaz, ArrayList<parteArchivo> descargar) {
		super();
		_arch=arch;
		_nombre=_arch.nombre();
		lanzados=new Semaforo(numConex);
		escribir=new Semaforo(1);
		esperar=new Semaforo(1);
		_esperarDownloader=new Semaforo(1);
		_tamPieza=tamPieza;
		this.ruta=ruta;
		_coord=coord;
		_miId=miId;
		_seedsSolicitados=new Hashtable<Integer, Boolean>();
		_peersSolicitados=new Hashtable<Integer, Boolean>();
		this.accederEas=accederEas;
		_eas=eas;
		_porcentaje=porcentaje;
		_interfaz=interfaz;
		_parar=false;
		_descargar=descargar;
		}
	
	
	/**
	 * Comienza a descargar el archivo lanzando un hilo por cada petición.
	 */
	public void run() {
		_esperarDownloader.bajar();
		try {

			while(pedir() && !_parar) {
				esperar.bajar();  //Evita una espera activa. Espera a que finalice el primer hilo peticion lanzado en la iteracion anterior.

				try {
					actualizarUsuarios();
					lanzarPeticiones();
					}
				catch (org.omg.CORBA.OBJECT_NOT_EXIST e1) {
					ArrayList<EstrArchivo> array=new ArrayList<EstrArchivo>();
					array.add(_eas.get(_nombre));
					_interfaz.cliente.anyadir(array);
					_arch=_interfaz.cliente.buscar(_nombre);
					esperar.subir();
				}
			}
			
			//Espera a que finalicen todos los hilos
			for(int j=0;j<lanzados.getInicial();j++)
				lanzados.bajar();

			if(!pedir()) {  //Descarga finalizada
				//Mueve la descarga a compartidos en la interfaz
				accederEas.bajar();
				_interfaz.finalizarDescarga(_eas.get(_nombre));
				accederEas.subir();
			}
			else {
				//Almacena el estado de la descarga
				accederEas.bajar();
				_interfaz.almacenarDescarga(_eas.get(_nombre));
				accederEas.subir();
			}
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		
		_esperarDownloader.subir();
	}


	/**
	 * Para la descarga actual y devuelve un nuevo hilo Downloader, que contiene la información
	 * de la descarga que se ha parado, preparado para reanudar la descarga mediante su método
	 * start().
	 * @return Hilo Downloader con la información actual de la descarga y preparado para
	 * reiniciar la descarga mediante su método start().
	 */
	public Downloader parar() {
		_parar=true;
		
		_esperarDownloader.bajar();
		
		return copia();
	}

	
	/**
	 * Para la descarga actual.
	 */
	public void matar() {
		_parar=true;
		_esperarDownloader.bajar();
	}
	

	/**
	 * Añade l% al porcentaje actual de la descarga.
	 * @param l Porcentaje a añadir.
	 */
	public void addPorcentaje(float l) {
		_porcentaje+=l;
		_interfaz.actualizarDescarga(_nombre, Math.round(_porcentaje));
	}


	/**
	 * Establece el id del usuario que realiza la descarga.
	 * @param id Identificador del usuario que realiza la descarga.
	 */
	public void actualizarId(int id) {
		_miId=id;
	}
	
	/**
	 * Añade los rangos a descargar en el array _descargar respetando el tamaño máximo de pieza.
	 * @param inicio Posición de inicio de la pieza a descargar.
	 * @param fin Posición final de la pieza a descargar.
	 */
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
	
	
	/** 
	 * Anota las partes a descargar.
	 * @param partes Partes del archivo que el usuario ya posee.
	 */
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


	/**
	 * Comprueba si un usuario posee la pieza indicada.
	 * @param idPeer Identificador del usuario 
	 * @param indiceDescarga Indice local de la pieza a comprobar
	 * @return true si el usuario dispone de la pieza, false en caso contrario.
	 */
	private boolean buscarPieza(int idPeer, int indiceDescarga) {
		boolean encontrado=false;
		parteArchivo pieza=_descargar.get(indiceDescarga);
		parteArchivo[] piezasPeer=_arch.getPartes(idPeer);
		int i=0;
		
		while(i<piezasPeer.length && !encontrado) {
			if(pieza.inicio>=piezasPeer[i].inicio && pieza.fin<=piezasPeer[i].fin)
				encontrado=true;
			else i++;
		}
		
		return encontrado;
	}
	
	
	/**
	 * Devuelve si quedan piezas por pedir o no.
	 * @return true en caso de que queden piezas por pedir, false en caso contrario.
	 */
	private boolean pedir() {
		boolean aux=false;
		int i=0;

		// Hay que ejecutarlo en exclusión mutua, ya que podría eliminarse una pieza a descargar
		while(!aux && i<_descargar.size()) {
			if(!_descargar.get(i).descargado) aux=true;
			else i++;
		}
		
		return aux;
	}
	

	/**
	 * Crea un hilo petición
	 * @param idUsuario Identificador del usuario al cual se le realizará la petición.
	 * @param pieza Pieza a descargar.
	 * @param usuarios Diccionario que almacena a que usuarios se les han realizado peticiones
	 * actualmente.
	 * @return Hilo Peticion creado.
	 */
	private Peticion crearHilo(int idUsuario, parteArchivo pieza, Hashtable<Integer, Boolean> usuarios) {
		Peticion p=null;
		
		try {
			p=new Peticion(_coord.getUsuario(idUsuario), pieza, this, usuarios, idUsuario, _miId, _eas, _arch, _interfaz);
		}
		catch (MiddlewareException e) {
			e.printStackTrace();
		}
		
		return p;
	}

	
	/**
	 * Crea los hilos Peticion y los lanza.
	 */
	private void lanzarPeticiones() {
		Enumeration<Integer> keys;
		int usuario, i;
		ArrayList<Peticion> hilos=new ArrayList<Peticion>();
		
		escribir.bajar();

		//Crear hilos
		
		// Limpiar descargas ya realizadas
		for(i=0;i<_descargar.size();i++) {
			if(_descargar.get(i).descargado)
				_descargar.remove(i);
		}
		
		
		for(i=0;i<_descargar.size();i++) {
			//buscar pieza en los peers
			keys=_peersSolicitados.keys();
			while(!_descargar.get(i).pedido && !_descargar.get(i).descargado && keys.hasMoreElements()) {
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
		
		escribir.subir();
		
		// Lanzar hilos
		for(i=0;i<hilos.size();i++) {
			lanzados.bajar();
			hilos.get(i).start();
		}
		//Establece el numero de conexiones actuales en la interfaz
		_interfaz.sumarConexiones(ruta, i);
		
		//Si no se ha lanzado ningún hilo, hay que realizar otra iteracion
		if(hilos.size()==0) {
			esperar.subir();
			try {
				Thread.sleep(3000);  //Espera 3 segundos antes de volver a comprobar.
			}
			catch (InterruptedException e) {
			}
		}
	}



	/**
	 * Actualiza las tablas de usuarios del archivo.
	 * @throws Exception CORBA puede lanzar una excepción si el archivo deja de existir por
	 * algún motivo.
	 */
	private void actualizarUsuarios() throws Exception {
		int[] seeds=_arch.getSeeds();
		int[] peers=_arch.getPeers();
		int key, i;

		
		// Eliminar usuarios que no estén, y a sí mismo
		Enumeration<Integer> aux=_seedsSolicitados.keys();
		while(aux.hasMoreElements()) {
			key=aux.nextElement();
			if(!buscar(key,seeds) || key==_miId)
				_seedsSolicitados.remove(key);
		}
		aux=_peersSolicitados.keys();
		while(aux.hasMoreElements()) {
			key=aux.nextElement();
			if(!buscar(key,peers) || key==_miId)
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


	/**
	 * Busca un valor en un vector.
	 * @param valor Valor a buscar.
	 * @param vector Vector en el cual se realiza la búsqueda.
	 * @return true en caso de encontrar el valor en el vector, false en caso contrario.
	 */
	private boolean buscar(int valor, int[] vector) {
		boolean encontrado=false;
		int i=0;
		
		while(!encontrado && i<vector.length)
			if(valor==vector[i]) encontrado=true;
			else i++;
		
		return encontrado;
	}


	/**
	 * Devuelve un nuevo hilo Downloader con la misma información que él mismo.
	 * @return Hilo Downloader con la información actual de la descarga y preparado para
	 * reiniciar la descarga mediante su método start().
	 */
	private Downloader copia() {
		return new Downloader(_arch, this.lanzados.getInicial(), ruta, _tamPieza, _coord, _miId, _eas, accederEas, _porcentaje, _interfaz, _descargar);
	}


	
	/** Referencia al archivo en CORBA. */
	private Archivo _arch;
	
	/** Tamaño máximo de las piezas. */
	private long _tamPieza;
	
	/** Vector de piezas a descargar. */
	private ArrayList<parteArchivo> _descargar;
	
	/** Referencia al coordinador en CORBA. */
	private Coordinador _coord;
	
	/** Información local de los archivos. */
	private Hashtable<String, EstrArchivo> _eas;
	
	/** Información sobre a que usuarios se les ha solicitado alguna parte y a cuales no. */
	private Hashtable<Integer, Boolean> _seedsSolicitados, _peersSolicitados;
	
	/** Identificador del usuario que realiza la descarga. */
	private int _miId;
	
	/** Porcentaje actual de la descarga. */
	private float _porcentaje;
	
	/** Referencia a la interfaz de usuario, necesaria para actualizar la información mostrada de la descarga. */
	private ClienteP2P _interfaz;
	
	/** Nombre del fichero a descargar. */
	private String _nombre;
	
	/** Variable para parar éste el hilo. */
	private boolean _parar;
	
	/** Semaforo para esperar a que el hilo Downloader finalize su ejecución, necesario si queremos
	 realizar una copiar de dicho hilo con información real y actualizada. */
	private Semaforo _esperarDownloader;
}