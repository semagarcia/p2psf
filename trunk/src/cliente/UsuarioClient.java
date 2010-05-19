package cliente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import middleware.JavaORB;
import middleware.Middleware;
import middleware.MiddlewareException;
import coordinador.Archivo;
import coordinador.Coordinador;

public class UsuarioClient {
	// Referencia al coordinador
	private Coordinador _coord;
	
	// Identificador del usuario
	private int _id;
	
	// Tabla de ficheros del usuario
	private Hashtable<String, EstrArchivo> _eas;
	
	// Referencia al hilo de escucha del usuario
	private UsuarioServer _hiloServer;
	
	// Array de descargas en curso
	private ArrayList<Downloader> _descargas;
	
	//Semáforo para escribir en la tabla _eas (información local de los archivos)
	private Semaforo _accederEas;

	private String _ipservidor;

	private String _iplocal;

	private int _puerto;

	
	public UsuarioClient(String ipservidor, String iplocal, int puerto) throws MiddlewareException {
		_coord=null;
		_id=-1;
		_eas=new Hashtable<String, EstrArchivo>();
		_descargas=new ArrayList<Downloader>();
		_accederEas=new Semaforo(1);
		_ipservidor=ipservidor;
		_iplocal=iplocal;
		_puerto=puerto;

		String args[]={"-ORBInitialPort",String.valueOf(puerto)};
		
		//Inicialiación del ORB
		JavaORB middleware = new JavaORB();
		middleware.options = args;
		Middleware.inicializar(middleware);

		// Obtener el sirviente
		_coord = (Coordinador) Middleware.interfazSirviente(
				Coordinador.class, Middleware.servidorNombres().localizar("coordinador"));
	}
	
	
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
	
	
	public void eliminar(EstrArchivo[] eas) {
		
		_accederEas.bajar();
		
		for(int i=0;i<eas.length;i++) {
			_eas.remove(eas[i].info.nombre);
		}

		if(_id!=-1)
			_coord.eliminarArchivos(eas, _id);
		
		_accederEas.subir();
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean conectar() {
		boolean conectado=(_id!=-1);
		
		if(!conectado) {
			// Crea el hilo de escucha
			String[] parametros={"-ORBInitialPort",String.valueOf(_puerto)};
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
	
	
	public Archivo buscar(String nombre) {
		Archivo aux=null;
		
		if(_id!=-1)
			aux=_coord.buscar(nombre);

		return aux;
	}

	
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

	public Downloader descargar(Archivo arch, parteArchivo[] partes, int numConex, long tamPieza, String ruta) {
		//lanza el hilo de descarga para el archivo
		Downloader d=new Downloader(arch, partes, numConex, tamPieza, ruta, _coord, _id, _accederEas, _eas);
		d.start();
		_descargas.add(d);
		
		return d;
	}
}