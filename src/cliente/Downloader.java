package cliente;

import coordinador.Archivo;

public class Downloader extends Thread {
	private Archivo _arch;
	private int _numConex;
	private long _tamPieza;
	

	public Downloader(Archivo arch, int numConex, long tamPieza) {
		_arch=arch;
		_numConex=numConex;
		_tamPieza=tamPieza;
	}
	
	
	public void run() {
		int[] peers=_arch.getPeers();
		int[] seeds=_arch.getSeeds();
		int numUsuarios=peers.length+seeds.length;
		
		Peticion[] peticiones=new Peticion[(_numConex<=numUsuarios?_numConex:numUsuarios)];
		
		//obtener piezas de los peers e ir creando cada hilo peticion
		//realizar periciones restantes a los seeds
		
		//esperar finalizacion de hilos y comenzar de nuevo

	}
	
}
