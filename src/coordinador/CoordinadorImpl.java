package coordinador;

import java.util.Collection;
import java.util.Hashtable;

import middleware.Middleware;
import cliente.EstrArchivo;

public class CoordinadorImpl extends CoordinadorPOA {
	private Hashtable<Integer, String> _usuarios;
	private Hashtable<String, Archivo> _archivos;
	private int _idActual;
	

	//Crea una instancia de Archivo y la hace pública en CORBA
	private Archivo crearArchivo(EstrArchivo ea) {
		Archivo resultado=null;
		
		try {
			ArchivoImpl sirviente=new ArchivoImpl(ea.nombre, 0,0);

			Middleware.registrar(sirviente);
			resultado=(Archivo) Middleware.interfazSirviente(Archivo.class, sirviente);
			
			_archivos.put(ea.nombre, resultado);
		}
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		return resultado;
	}


	// Añade la información de los archivos del usuario
	public void anyadirArchivos(EstrArchivo[] eas, int idUsuario) {
		System.out.println("El usuario dispone de "+eas.length+" archivos.");
		for(int i=0;i<eas.length;i++) {
			System.out.print("Archivo "+i+": "+eas[i].nombre+", "+eas[i].tam+" bytes, "+eas[i].checksum+"...");

			Archivo aux=buscar(eas[i].nombre);
			if(aux==null) {
				aux=crearArchivo(eas[i]);
				_archivos.put(eas[i].nombre, aux);
			}

			if(eas[i].partes[1]==eas[i].tam)
				aux.insertarSeed(idUsuario);
			else
				aux.insertarPeer(idUsuario, eas[i].partes);
			
			System.out.println("almacenado.");
		}
	}
	

	// Elimina al usuario de los archivos que deja de compartir
	public void eliminarArchivos(EstrArchivo[] eas, int idUsuario) {
		System.out.println("El usuario dispone de "+eas.length+" archivos.");
		for(int i=0;i<eas.length;i++) {
			System.out.print("Archivo "+i+": "+eas[i].nombre+", "+eas[i].tam+" bytes, "+eas[i].checksum+"...");

			Archivo aux=buscar(eas[i].nombre);
			if(aux!=null) {
				if(eas[i].partes[1]==eas[i].tam)
					aux.eliminarSeed(idUsuario);
				else
					aux.eliminarPeer(idUsuario);
			
				System.out.println("eliminado usuario.");
				
				if(aux.getPeers().length==0 && aux.getSeeds().length==0) {
					_archivos.remove(aux.nombre());
					//eliminar referencia en CORBA?????
					System.out.println("No quedan usuarios con este archivo, archivo eliminado.");
					}
			}
		}		
	}


	// Inicializa la tabla de usuarios y archivos. Inicializa los identificadores a 0.
	public CoordinadorImpl() {
		_usuarios=new Hashtable<Integer, String>();
		_archivos=new Hashtable<String, Archivo>();
		_idActual=0;
	}
	

	// Almacena la información del usuario que entra y los archivos que comparte.
	@Override
	public int conectar(EstrArchivo[] eas, String usuario) {
		// Almacena la información del usuario
		_idActual++;		
		_usuarios.put(_idActual, usuario);
		
		System.out.println("Usuario entra: "+_idActual);
		
		// Almacena la información de los archivos que comparte el usuario
		anyadirArchivos(eas, _idActual);

		mostrarInfoDebug();
		
		return _idActual;
		}


	//ELIMINAR ESTE METODO - Solo para pruebas
	private void mostrarInfoDebug() {
		System.out.println("Usuarios: "+_usuarios.size());

		Collection<Archivo> col=_archivos.values();
		Archivo[] ars=new Archivo[col.size()];
		col.toArray(ars);

		for(int i=0;i<ars.length;i++) {
			System.out.println("\tArchivo "+ars[i].nombre()+", "+ars[i].tam()+" bytes, "+ars[i].checksum());
			System.out.println("\t\tSeeds: "+ars[i].getSeeds().length);
			System.out.println("\t\tPeers: "+ars[i].getPeers().length);
		}
	}


	// Devuelve el archivo con el nombre indicado, null si no existe.
	@Override
	public Archivo buscar(String nombre) {
		return _archivos.get(nombre);
	}

	
	// Elimina al usuario de la tabla de usuarios y actualiza la información de los archivos que compartía.
	@Override
	public void desconectar(EstrArchivo[] eas, int idUsuario) {
		System.out.println("Usuario "+idUsuario+" quiere salir...");
		//eliminar al usuario de los archivos
		_usuarios.remove(idUsuario);
		
		System.out.println("Eliminando sus archivos...");
		//actualizar archivos
		eliminarArchivos(eas,idUsuario);

		System.out.println("Usuario sale: "+idUsuario);
		
		mostrarInfoDebug();
		
	}


	@Override
	public String getReferencia(int id) {
		return _usuarios.get(id);
	}

}
