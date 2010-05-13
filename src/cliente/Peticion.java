package cliente;

import java.io.File;
import java.io.RandomAccessFile;
import middleware.Middleware;
import middleware.MiddlewareException;

public class Peticion extends Thread {
		
	private String _ruta;
	private Usuario _usuario;
	private String _nombre;
	private long _inicio;
	private long _fin;
	private Semaforo _lanzados;
	private Semaforo _escribir;

	
	/**
	 * Escribe los bytes en el fichero destino comenzando por el byte inicio de dicho fichero 
	 */
	public void escribir(byte[] datos) {
		File destino=new File(_ruta);
		RandomAccessFile fileOut;
		try {
			fileOut = new RandomAccessFile(destino,"rw");
			fileOut.seek(_inicio);
			fileOut.write(datos);		
			fileOut.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Peticion(String ruta, String referenciaUsuario, String nombre ,long inicio, long fin, Semaforo lanzados, Semaforo escribir) throws MiddlewareException {
		_ruta=ruta;
		_usuario=(Usuario) Middleware.interfazSirviente(Usuario.class, referenciaUsuario);
		_nombre=nombre;
		_inicio=inicio;
		_fin=fin;
		_lanzados=lanzados;
		_escribir=escribir;
	}
	
	
	public void run() {		
		System.out.println("Lanzado hilo que pide ["+_inicio+"-"+_fin+"]");

		System.out.println(_usuario.saluda());

		byte[] parte;
		
		parte=_usuario.solicitarParte(_nombre, _inicio, _fin);
		
		_escribir.bajar();
		escribir(parte);
		_escribir.subir();
		
		_lanzados.subir();
	}
}
