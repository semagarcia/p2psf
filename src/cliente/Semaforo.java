package cliente;


/**
 * Implementación de un semáforo. 
 */
public class Semaforo {
	/**
	 * Constructor de la clase. Establece el número de veces que se puede bajar el semáforo inicialmente.
	 * @param n Número de veces que se puede bajar el semáforo al comenzar.
	 */
	public Semaforo(int n) {
		_contador=n;
		_inicial=n;
	}


	/**
	 * Baja el semáforo.
	 */
	public synchronized void bajar() {
		while(_contador==0) {
			try {
				wait();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		_contador--;
	}
	

	/**
	 * Sube el semáforo.
	 */
	public synchronized void subir() {
		if(_contador==0) {
			notifyAll();
			}

		_contador++;
	}
	

	/**
	 * Devuelve el número de accesos(bajadas) con el cual se creó el semáforo.
	 * @return Número de accesos iniciales.
	 */
	public int getInicial() {
		return _inicial;
	}


	/**
	 * Devuelve el número actual de accesos(bajadas) posibles.
	 * @return Número de accesos posibles actualmente.
	 */
	public int getActual() {
		return _contador;
	}
	
	
	/** Contador de accesos(bajadas) posibles. */
	private int _contador;
	
	/** Valor inicial de accesos posibles con el cual se creó el semáforo. */
	private final int _inicial;

}
