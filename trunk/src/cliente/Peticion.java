package cliente;

import middleware.Middleware;
import middleware.MiddlewareException;

public class Peticion extends Thread {
	
	public static int Lanzados=0;
	
	private Usuario _usuario;
	private long _inicio;
	private long _fin;
	private Semaforo _lanzados;

	
	public Peticion(String referenciaUsuario, long inicio, long fin, Semaforo lanzados) throws MiddlewareException {
		_usuario=(Usuario) Middleware.interfazSirviente(Usuario.class, referenciaUsuario);
		_inicio=inicio;
		_fin=fin;
		_lanzados=lanzados;
	}
	
	
	public void run() {		
		System.out.println("Lanzado hilo que pide ["+_inicio+"-"+_fin+"]");

		System.out.println(_usuario.saluda());
		
		try {
			Thread.sleep(3000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_lanzados.subir();
	}
}
