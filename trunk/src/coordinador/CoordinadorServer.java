package coordinador;

import middleware.JavaORB;
import middleware.Middleware;

public class CoordinadorServer {
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

		System.out.println("CoordinadorServer Sale ...");

	}
}
