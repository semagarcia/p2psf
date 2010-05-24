package cliente;

import java.util.Hashtable;

import middleware.JavaORB;
import middleware.Middleware;
import middleware.MiddlewareException;

public class UsuarioServer extends Thread {

	private UsuarioImpl _usuImpl;

	public UsuarioServer(String[] args, Hashtable<String, EstrArchivo> eas, Semaforo accederEas) {
		try {
			// Inicialiación del ORB
			JavaORB middleware = new JavaORB();
			middleware.options = args;
			Middleware.inicializar(middleware);

			// Crear el Sirviente
			_usuImpl= new UsuarioImpl(eas, accederEas);
			
			// Obtener una referencia CORBA del Sirviente
			Object ref = Middleware.interfazSirviente(Usuario.class,_usuImpl);

			// Añadir la referencia CORBA al servidor de nombres
			Middleware.servidorNombres().anotarSirviente(ref, "usuario");
		}

		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}		
	}
	
	
	public Usuario getRef() throws MiddlewareException {		
		return (Usuario) Middleware.interfazSirviente(Usuario.class, _usuImpl); 
	}
	
	
	public void run() {
		System.out.println("Usuario a la escucha...");
		Middleware.ejecutar();
	}


	public void setId(int id) {
		_usuImpl.setId(id);
	}
}
