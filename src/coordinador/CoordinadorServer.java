package coordinador;


import middleware.JavaORB;
import middleware.Middleware;


/**
 * Implementación del servidor Coordinador.
 */
public class CoordinadorServer {
	/**
	 * Crea una instancia del sirviente, lo hace público en CORBA y se pone a la escucha.
	 * @param args Argumentos pasados.
	 */
	public static void main(String args[]) {
		try {
			// Inicialiación del ORB
			JavaORB middleware = new JavaORB();
			middleware.options = args;
			Middleware.inicializar(middleware);

			// Crear el Sirviente
			CoordinadorImpl coordImpl = new CoordinadorImpl();

			// Obtener una referencia CORBA del Sirviente
			Object ref = Middleware.interfazSirviente(Coordinador.class,
					coordImpl);

			// Añadir la referencia CORBA al servidor de nombres
			Middleware.servidorNombres().anotarSirviente(ref, "coordinador");
			
			System.out.println("CoordinadorServer listo y esperando...");
			
			Middleware.ejecutar();
		}

		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
	}
}
