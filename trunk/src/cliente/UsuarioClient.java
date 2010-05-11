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
			if(_eas.get(eas[i].nombre)==null) {
				_eas.put(eas[i].nombre, eas[i]);
				aux.add(eas[i]);
			}
		}

		if(_id!=-1)
			_coord.anyadirArchivos((EstrArchivo[]) aux.toArray(), _id);
	}
	
	
	public void eliminar(EstrArchivo[] eas) {	
		for(int i=0;i<eas.length;i++) {
			_eas.remove(eas[i].nombre);
		}

		if(_id!=-1)
			_coord.eliminarArchivos(eas, _id);
	}
	
	
	public void conectar(String[] args) throws MiddlewareException {
		// Crea el hilo de escucha
		_hiloServer=new UsuarioServer(args);
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
}

/*
package cliente;


import middleware.JavaORB;
import middleware.Middleware;
import middleware.MiddlewareException;
import coordinador.Archivo;
import coordinador.Coordinador;

public class UsuarioClient {
	static Coordinador coord;
	
	static private int _id;
	static private UsuarioServer _hiloServer;
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		try {
			//Inicialiación del ORB
			JavaORB middleware = new JavaORB();
			middleware.options = args;
			Middleware.inicializar(middleware);

			// Obtener el sirviente
			coord = (Coordinador) Middleware.interfazSirviente(
					Coordinador.class, Middleware.servidorNombres().localizar("coordinador"));

			//Rellenar informacion de ficheros compartidos para enviar al coordinador
			EstrArchivo[] vArch=new EstrArchivo[2];
			vArch[0]=new EstrArchivo("uno",0,0,true);
			vArch[1]=new EstrArchivo("dos",1,1,true);
			
			// Crea el hilo de escucha
			_hiloServer=new UsuarioServer(args);
			_hiloServer.start();

			// Operar con el servidor
			_id=coord.login(vArch, _hiloServer.getRef());
			System.out.println("Id: "+_id);

			
			//simulando escuchas y peticiones
			java.util.Random r=new java.util.Random(System.currentTimeMillis());
			if(r.nextBoolean()) {
				System.out.println("Usuario que solo escucha");
				// Espera para probar intaracciones con el servidor
				Thread.sleep(10000);
			}
			else {
				System.out.println("Usuario que hace peticiones");
				
				Archivo a=coord.buscar("uno");
				if(a!=null) {
					String uCad=a.getSeeds()[0];
					Usuario usu=(Usuario) Middleware.interfazSirviente(Usuario.class, uCad);
					
					System.out.println(usu.saluda());
				}
				else
					System.out.println("El archivo no está.");
			}
			
			
				
				
				
			// Notifica al coordinador su salida
			coord.logout(vArch, _id);
			
			// Para la ejecucion del servidor
			_hiloServer.stop();
		}
		catch (MiddlewareException ex) {
			System.out.println(ex.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
*/