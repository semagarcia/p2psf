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

	
	public UsuarioClient(String args[]) throws MiddlewareException {
		_coord=null;
		_id=-1;
		_eas=new Hashtable<String, EstrArchivo>();

		//Inicialiación del ORB
		JavaORB middleware = new JavaORB();
		middleware.options = args;
		Middleware.inicializar(middleware);

		// Obtener el sirviente
		_coord = (Coordinador) Middleware.interfazSirviente(
				Coordinador.class, Middleware.servidorNombres().localizar("coordinador"));
	}
	
	
	public void anyadir(EstrArchivo[] eas) {
		ArrayList<EstrArchivo> aux=new ArrayList<EstrArchivo>();
		
		for(int i=0;i<eas.length;i++) {
			if(_eas.get(eas[i].info.nombre)==null) {
				_eas.put(eas[i].info.nombre, eas[i]);
				aux.add(eas[i]);
			}
		}

		if(_id!=-1)
			_coord.anyadirArchivos((EstrArchivo[]) aux.toArray(), _id);
	}
	
	
	public void eliminar(EstrArchivo[] eas) {	
		for(int i=0;i<eas.length;i++) {
			_eas.remove(eas[i].info.nombre);
		}

		if(_id!=-1)
			_coord.eliminarArchivos(eas, _id);
	}
	
	
	public void conectar(String[] args) throws MiddlewareException {
		// Crea el hilo de escucha
		_hiloServer=new UsuarioServer(args, _eas);
		_hiloServer.start();

		Collection<EstrArchivo> col=_eas.values();
		EstrArchivo[] archivos=new EstrArchivo[col.size()];
		col.toArray(archivos);
		_id=_coord.conectar(archivos, _hiloServer.getRef());
	}
	
	
	public Archivo buscar(String nombre) {
		Archivo aux=null;
		
		if(_id!=-1)
			aux=_coord.buscar(nombre);

		return aux;
	}

	
	@SuppressWarnings("deprecation")
	public void desconectar() {
		if(_id!=-1) {
			Collection<EstrArchivo> col=_eas.values();
			EstrArchivo[] archivos=new EstrArchivo[col.size()];
			col.toArray(archivos);
			_coord.desconectar(archivos,_id);
			_hiloServer.stop();
			_id=-1;
		}
	}

	public void descargar(Archivo arch, parteArchivo[] partes, int numConex, long tamPieza, String ruta) {
		//lanza el hilo de descarga para el archivo
		Downloader d=new Downloader(arch, partes, numConex, tamPieza, ruta, _coord);
		d.start();
	}
}