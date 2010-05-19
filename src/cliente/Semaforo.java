package cliente;

public class Semaforo {
	private int _contador;
	private final int _inicial;
	
	
	public Semaforo(int n) {
		_contador=n;
		_inicial=n;
	}

	
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
		System.out.println("OCUPA: "+_contador+"/"+_inicial);
	}
	
	
	public synchronized void subir() {
		if(_contador==0)
			notifyAll();

		_contador++;
		System.out.println("LIBERA: "+_contador+"/"+_inicial);
	}
	
	
	public int getInicial() {
		return _inicial;
	}
}
