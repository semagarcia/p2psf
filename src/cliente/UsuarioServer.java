package cliente;

import middleware.JavaORB;
import middleware.Middleware;
import middleware.MiddlewareException;

public class UsuarioServer extends Thread {
		
	private Object _usuImpl;

	public UsuarioServer(String[] args) {
		try {
			// Inicialiación del ORB
			JavaORB middleware = new JavaORB();
			middleware.options = args;
			Middleware.inicializar(middleware);

			// Crear el Sirviente
			_usuImpl = new UsuarioImpl();

			// Obtener una referencia CORBA del Sirviente
			Object ref = Middleware.interfazSirviente(Usuario.class,
					_usuImpl);

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
		
		System.out.println("Usuario sale ...");
	}
}
