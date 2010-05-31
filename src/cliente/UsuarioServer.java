package cliente;


import java.util.Hashtable;
import middleware.JavaORB;
import middleware.Middleware;
import middleware.MiddlewareException;


/**
 * Servidor que hace disponible al sirviente del usuario para responder a las peticiones de
 * los demás usuarios.
 */
public class UsuarioServer extends Thread {
	/**
	 * Constructor de la clase. Establece los parámetros iniciales y crea a un sirviente para atender peticiones
	 * mediante CORBA.
	 * @param args Argumentos a pasarle a CORBA.
	 * @param eas Información local de los archivos.
	 * @param accederEas Semáforo para acceder a la información local de los archivos en exclusión mutua.
	 */
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
	

	/**
	 * Devuelve la referencia CORBA del sirviente
	 * @return La referencia CORBA del srviente.
	 * @throws MiddlewareException CORBA puede lanzar una excepción si no encuentra la sirviente.
	 */
	public Usuario getRef() throws MiddlewareException {		
		return (Usuario) Middleware.interfazSirviente(Usuario.class, _usuImpl); 
	}
	

	/**
	 * Pone al servidor a la escucha de peticiones.
	 */
	public void run() {
		Middleware.ejecutar();
	}

	
	/** Referencia del sirviente. */
	private UsuarioImpl _usuImpl;
}