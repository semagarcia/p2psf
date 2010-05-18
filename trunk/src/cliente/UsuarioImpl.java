package cliente;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Hashtable;

public class UsuarioImpl extends UsuarioPOA {
	
	private float _prueba;
	private Hashtable<String, EstrArchivo> _eas;
	private Semaforo _accederEas;
	
	
	public UsuarioImpl(Hashtable<String, EstrArchivo> eas, Semaforo accederEas) {
		super();
		_eas=eas;
		_accederEas=accederEas;
	}
	
	@Override
	public String saluda() {		
		return "Hola"+_prueba;
	}

	
	public void set() {
		_prueba=new java.util.Random(System.currentTimeMillis()).nextFloat();
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
