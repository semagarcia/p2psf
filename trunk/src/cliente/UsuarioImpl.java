package cliente;


import java.io.File;
import java.io.RandomAccessFile;
import java.util.Hashtable;


/**
 * Implementación del sirviente UsuarioImpl, encargado de dar respuesta a las solicitudes
 * de otros usuarios.
 */
public class UsuarioImpl extends UsuarioPOA {
	/**
	 * Constructor de la clase.
	 * @param eas Información local de los archivos que el usuario comparte.
	 * @param accederEas Semáforo para acceder a la información de los archivos local en exclusión mutua.
	 */
	public UsuarioImpl(Hashtable<String, EstrArchivo> eas, Semaforo accederEas) {
		super();
		_eas=eas;
		_accederEas=accederEas;
	}

	
	/**
	 * Devuelve la secuencia de bytes que contiene un archivo desde inicio hasta fin.
	 * @param nombre Nombre del archivo a descargar.
	 * @param inicio Comienzo de la secuencia de bytes a devolver dentro del archivo.
	 * @param fin Fin de la secuencia de bytes a devolver dentro del archivo.
	 * @return La secuencia de bytes solicitados.
	 */
	@Override
	public byte[] solicitarParte(String nombre, long inicio, long fin) {
		
		_accederEas.bajar();
		File fichero=new File(_eas.get(nombre).info.ruta);
		_accederEas.subir();
		
		int tam=(int)(fin-inicio);
		byte[] b=null;
		RandomAccessFile fileIn;

		try {
			fileIn = new RandomAccessFile(fichero,"r");
		
			if(tam>0) {
				b=new byte[tam];
				fileIn.seek(inicio);			
				fileIn.read(b);
			}
			fileIn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

	
	/** Información local de los archivos. */
	private Hashtable<String, EstrArchivo> _eas;
	
	/** Semáforo para acceder a la información local de los archivos en exclusión mutua. */
	private Semaforo _accederEas;
}
