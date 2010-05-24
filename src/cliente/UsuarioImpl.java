package cliente;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Hashtable;

public class UsuarioImpl extends UsuarioPOA {
	
	private Hashtable<String, EstrArchivo> _eas;
	private Semaforo _accederEas;
	
	
	public UsuarioImpl(Hashtable<String, EstrArchivo> eas, Semaforo accederEas) {
		super();
		_eas=eas;
		_accederEas=accederEas;
	}

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
}
