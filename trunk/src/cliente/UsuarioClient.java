package cliente;


import gui.ClienteP2P;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import middleware.JavaORB;
import middleware.Middleware;
import middleware.MiddlewareException;
import coordinador.Archivo;
import coordinador.Coordinador;


/** Clase que contiene los métodos necesarios para comunicarse con el coordinador. */
public class UsuarioClient {
	/**
	 * Constructor de la clase. Crea una instancia de la clase con los datos necesarios para 
	 * comunicarse con el servidor y crear un hilo servidor que escuchará peticiones de otros
	 * usuarios.
	 * @param ipservidor IP en la cual se encuentra el servidor de nombres.
	 * @param iplocal IP de la máquina local.
	 * @param puerto Puerto a través del cual se establecerán las comunicaciones.
	 * @param interfaz Referencia a la interfaz del usuario.
	 * @throws MiddlewareException CORBA puede lanzar una excepción si algún objeto sobre el
	 * cual se hace referencia no existe.
	 */
	public UsuarioClient(String ipservidor, String iplocal, int puerto, ClienteP2P interfaz) throws MiddlewareException {
		_coord=null;
		_id=-1;
		_eas=new Hashtable<String, EstrArchivo>();
		_accederEas=new Semaforo(1);
		_ipservidor=ipservidor;
		_iplocal=iplocal;
		_puerto=puerto;
		_interfaz=interfaz;

		String args[]={"-ORBInitialPort",String.valueOf(_puerto),"-ORBInitialHost",_ipservidor};
		
		//Inicialiación del ORB
		JavaORB middleware = new JavaORB();
		middleware.options = args;
		Middleware.inicializar(middleware);

		// Obtener el sirviente
		_coord = (Coordinador) Middleware.interfazSirviente(
				Coordinador.class, Middleware.servidorNombres().localizar("coordinador"));
	}
	
	
	/**
	 * Añade archivos localmente y en la red para su compartición.
	 * @param eas Estructura de archivos a añadir.
	 */
	public void anyadir(ArrayList<EstrArchivo> eas) {
		ArrayList<EstrArchivo> aux=new ArrayList<EstrArchivo>();
		
		_accederEas.bajar();
		
		for(int i=0;i<eas.size();i++) {
			EstrArchivo e=eas.get(i);
			if(_eas.get(e.info.nombre)==null) {
				_eas.put(e.info.nombre, e);
				aux.add(e);
			}
		}

		if(_id!=-1) {
			EstrArchivo[] enviar=new EstrArchivo[aux.size()];
			aux.toArray(enviar);
			_coord.anyadirArchivos(enviar, _id);
		}
		
		_accederEas.subir();
	}
	

	/**
	 * Elimina archivos localmente y en la red. No elimina los archivos físicos, sólo la
	 * información que la aplicación mantiene de los mismos.
	 * @param eas Estructura de archivos a eliminar.
	 */
	public void eliminar(EstrArchivo[] eas) {
		
		_accederEas.bajar();
		
		for(int i=0;i<eas.length;i++) {
			_eas.remove(eas[i].info.nombre);
		}

		if(_id!=-1)
			_coord.eliminarArchivos(eas, _id);
		
		_accederEas.subir();
	}
	

	/**
	 * Conecta al usuario en la red y envía los archivos que éste comparte.
	 * @return true en caso de haberse conectado correctamente, false en caso contrario.
	 */
	@SuppressWarnings("deprecation")
	public boolean conectar() {
		boolean conectado=(_id!=-1);
		
		if(!conectado) {
			// Crea el hilo de escucha
			String[] parametros={"-ORBInitialPort",String.valueOf(_puerto),"-ORBInitialHost",_ipservidor,"-ORBServerHost",_iplocal};
			_hiloServer=new UsuarioServer(parametros, _eas, _accederEas);
			_hiloServer.start();

			_accederEas.bajar();
			Collection<EstrArchivo> col=_eas.values();
			EstrArchivo[] archivos=new EstrArchivo[col.size()];
			col.toArray(archivos);
			_accederEas.subir();
			
			try {
				_id=_coord.conectar(archivos, _hiloServer.getRef());
				conectado=true;
			}
			catch (MiddlewareException e) {
				_hiloServer.stop();
				_id=-1;
				conectado=false;
			}
		}
		
		return conectado;
	}
	

	/**
	 * Busca un archivo en la red.
	 * @param nombre Nombre del archivo a buscar.
	 * @return La referencia CORBA del archivo encontrado, null en caso de no encontrarlo.
	 */
	public Archivo buscar(String nombre) {
		Archivo aux=null;
		
		if(_id!=-1)
			aux=_coord.buscar(nombre);

		return aux;
	}


	/**
	 * Desconecta al usuario de la red.
	 * @return true en caso de haberse desconectado correctamente, false en caso contrario.
	 */
	@SuppressWarnings("deprecation")
	public boolean desconectar() {
		boolean desconectado=(_id==-1);
		
		if(!desconectado) {
			
			_accederEas.bajar();
			Collection<EstrArchivo> col=_eas.values();
			EstrArchivo[] archivos=new EstrArchivo[col.size()];
			col.toArray(archivos);
			_accederEas.subir();
			
			_coord.desconectar(archivos,_id);
			_hiloServer.stop();
			_id=-1;
			
			desconectado=true;
		}
		
		return desconectado;
	}

	
	/**
	 * Crea un hilo Downloader para que gestione la descarga de un fichero.
	 * @param arch Referencia en CORBA del archivo a descargar.
	 * @param partes Partes del archivo que el usuario ya posee.
	 * @param porcentaje Porcentaje de la descarga que el usuario ya tiene realizado.
	 * @param numConex Número de conexiones máximas por archivo que el usuario puede establecer.
	 * @param tamPieza Tamaño máximo de las piezas a descargar.
	 * @param ruta Ruta donde se almacena el fichero.
	 * @return Hilo Downloader encargado de realizar la descarga.
	 */
	public Downloader descargar(Archivo arch, parteArchivo[] partes, float porcentaje, int numConex, long tamPieza, String ruta) {
		//lanza el hilo de descarga para el archivo
		Downloader d=null;
		
		if(_id!=-1)
			d=new Downloader(arch, partes, porcentaje, numConex, tamPieza, ruta, _coord, _id, _accederEas, _eas, _interfaz);
		
		return d;
	}


	/**
	 * Devuelve el id del usuario en la red.
	 * @return El identificador del usuario en la red o -1 si no está conectado.
	 */
	public int getId() {
		return _id;
	}
	

	/** Referencia al coordinador. */
	private Coordinador _coord;
	
	/** Identificador del usuario. */
	private int _id;
	
	/** Tabla de ficheros del usuario. */
	private Hashtable<String, EstrArchivo> _eas;
	
	/** Referencia al hilo de escucha del usuario. */
	private UsuarioServer _hiloServer;
		
	/** Semáforo para escribir en la tabla _eas (información local de los archivos). */
	private Semaforo _accederEas;

	/** IP en la cual se encuentra el servidor de nombres. */
	private String _ipservidor;

	/** Puerto a través del cual se establecen las conexiones. */
	private int _puerto;

	/** IP de la máquina local. */
	private String _iplocal;

	/** Referencia a la interfaz de usuario. */
	private ClienteP2P _interfaz;
}