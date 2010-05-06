package coordinador;

import middleware.Middleware;
import archivo.Archivo;

public class CoordinadorImpl extends CoordinadorPOA {
	private Archivo[] _as;
	
	@Override
	public void login(Archivo[] as) {
		System.out.println(as.length);
		for(int i=0;i<as.length;i++)
			System.out.println(as[i].nombre+as[i].tam+as[i].checksum);

		_as=as;
	}

	@Override
	public void muestra() {
		System.out.println(_as.length);
		for(int i=0;i<_as.length;i++)
			System.out.println(_as[i].nombre+_as[i].tam+_as[i].checksum);
		
	}


}
