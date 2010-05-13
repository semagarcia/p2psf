package cliente;

import java.util.Hashtable;

import middleware.JavaORB;
import middleware.Middleware;
import middleware.MiddlewareException;

public class UsuarioServer extends Thread {

	private UsuarioImpl _usuImpl;

	public UsuarioServer(String[] args, Hashtable eas) {
		try {
			// Inicialiación del ORB
			JavaORB middleware = new JavaORB();
			middleware.options = args;
			Middleware.inicializar(middleware);

			// Crear el Sirviente
			_usuImpl= new UsuarioImpl(eas);
			
_usuImpl.set();
				
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
	
	
	public String getRef() throws MiddlewareException {		
		return Middleware.referenciaCadenificada(_usuImpl);
	}
	
	
	public void run() {
		System.out.println("Usuario a la escucha...");
		Middleware.ejecutar();
	}
}
