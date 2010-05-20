package cliente;

public class Semaforo {
	private int _contador;
	private final int _inicial;
	
	public Semaforo(int n) {
		_contador=n;
		_inicial=n;
	}

	
	public synchronized void bajar(String cadena) {
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
	
	
	public synchronized void subir(String cadena) {
		if(_contador==0) {
			notifyAll();
			}

		_contador++;
	}
	
	
	public int getInicial() {
		return _inicial;
	}
}
