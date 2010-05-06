package cliente;

import middleware.JavaORB;
import middleware.Middleware;
import middleware.MiddlewareException;
import coordinador.Coordinador;
import archivo.Archivo;

public class UsuarioClient {
	static Coordinador coordImpl;
	
	public static void main(String args[]) {
		try {
			//Inicialiación del ORB
			JavaORB middleware = new JavaORB();
			middleware.options = args;
			Middleware.inicializar(middleware);

			// Obtener el sirviente
			coordImpl = (Coordinador) Middleware.interfazSirviente(
					Coordinador.class, Middleware.servidorNombres().localizar("coordinador"));

			Archivo[] vArch=new Archivo[2];
			vArch[0]=new Archivo("uno",0,0);
			vArch[1]=new Archivo("dos",1,1);
			
			// Operar con el servidor
			coordImpl.login(vArch);

			vArch[0]=new Archivo("asd",2,3);
			vArch[1]=new Archivo("qwe",4,4);

			coordImpl.muestra();

			
			System.out.println("usuario sale...");
		}
		catch (MiddlewareException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
