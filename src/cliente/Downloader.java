package cliente;

import java.util.ArrayList;
import coordinador.Archivo;

public class Downloader extends Thread {
	private Archivo _arch;
	private int _numConex;
	private long _tamPieza;
	private ArrayList<long[]> _descargar;


	// Añade los rangos a descargar en el array _descargar respetando el tamaño máximo de pieza
	private void anotarDescarga(long inicio, long fin) {
		long finActual=inicio+_tamPieza;
		long[] aux;

		while(finActual<fin) {
			aux=new long[2];
			aux[0]=inicio;
			aux[1]=finActual;
			_descargar.add(aux);
			inicio=finActual;
			finActual+=_tamPieza;
		}
		
		aux=new long[2];
		aux[0]=inicio;
		aux[1]=fin;
		_descargar.add(aux);
	}


	// Constructor de la clase. Almacena la información necesaria para comenzar la descarga.
	public Downloader(Archivo arch, long[] partes, int numConex, long tamPieza) {
		super();
		_arch=arch;
		_numConex=numConex;
		_tamPieza=tamPieza;
		_descargar=new ArrayList<long[]>();
		long inicioActual=0;
		boolean descartar=(partes!=null);
		
		// Anota las partes a descargar
		int i=0;
		while(descartar) {
			//Descartar partes
			if(inicioActual<partes[i]) {
				//Añadir desde inicioActual hasta partes[i]
				anotarDescarga(inicioActual,partes[i]);
				inicioActual=partes[i+1];
				i+=2;
				if(i>=partes.length) descartar=false;
			}
			else {
				//Descartar desde _partes[i] hasta _partes[i+1]
				inicioActual=partes[i+1];
				i+=2;
				if(i>=partes.length) descartar=false;
			}
		}
		//Añadir ultima parte del archivo
		if(inicioActual<_arch.tam())
			anotarDescarga(inicioActual,_arch.tam());
	}
	

	// Comienza a descargar el archivo lanzando un hilo por cada petición.
	public void run() {
		long[] aux;
		for(int i=0;i<_descargar.size();i++) {
			aux=_descargar.get(i);
			System.out.println(aux[0]+"-"+aux[1]+":"+i);
		}
		//Peticion[] peticiones=new Peticion[(_numConex<=numUsuarios?_numConex:numUsuarios)];
		
		
		//obtener piezas de los peers e ir creando cada hilo peticion
		//realizar periciones restantes a los seeds
		
		//esperar finalizacion de hilos y comenzar de nuevo

	}
}
