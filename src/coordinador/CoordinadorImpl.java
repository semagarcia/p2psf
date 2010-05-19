package coordinador;

import java.util.Collection;
import java.util.Hashtable;

import middleware.Middleware;
import middleware.MiddlewareException;
import cliente.EstrArchivo;
import cliente.Usuario;

public class CoordinadorImpl extends CoordinadorPOA {
	private Hashtable<Integer, Usuario> _usuarios;
	private Hashtable<String, Archivo> _archivos;
	private Hashtable<String, ArchivoImpl> _archivosImpl;
	private int _idActual;
	

	//Crea una instancia de Archivo y la hace pública en CORBA
	private Archivo crearArchivo(EstrArchivo ea) {
		Archivo resultado=null;
		
		try {
			ArchivoImpl sirviente=new ArchivoImpl(ea.info.nombre, ea.info.tam, ea.info.checksum);

			Middleware.registrar(sirviente);
			resultado=(Archivo) Middleware.interfazSirviente(Archivo.class, sirviente);
			
			_archivos.put(ea.info.nombre, resultado);
			_archivosImpl.put(ea.info.nombre, sirviente);
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
			System.out.print("Archivo "+i+": "+eas[i].info.nombre+", "+eas[i].info.tam+" bytes, "+eas[i].info.checksum+"...");

			Archivo aux=buscar(eas[i].info.nombre);
			if(aux==null) aux=crearArchivo(eas[i]);

			if((eas[i].partes[0].fin-eas[i].partes[0].inicio)==eas[i].info.tam)
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
			System.out.print("Archivo "+i+": "+eas[i].info.nombre+", "+eas[i].info.tam+" bytes, "+eas[i].info.checksum+"...");

			Archivo aux=buscar(eas[i].info.nombre);
			if(aux!=null) {
				if(eas[i].partes[0].fin-eas[i].partes[0].inicio==eas[i].info.tam) {
					
				System.out.println("ELIMINANDO SEED");
					aux.eliminarSeed(idUsuario);
				System.out.println("SEED ELIMINADO");
				}
				else {
				System.out.println("ELIMINANDO PEER");
					aux.eliminarPeer(idUsuario);
				}
			
				System.out.println("eliminado usuario.");
				
				if(aux.getPeers().length==0 && aux.getSeeds().length==0) {
					try {
						Middleware.desregistrar(_archivosImpl.remove(_archivos.remove(aux.nombre()).nombre()));
					}
					catch (MiddlewareException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("No quedan usuarios con este archivo, archivo eliminado.");
				}
			}
		}		
	}


	// Inicializa la tabla de usuarios y archivos. Inicializa los identificadores a 0.
	public CoordinadorImpl() {
		_usuarios=new Hashtable<Integer, Usuario>();
		_archivos=new Hashtable<String, Archivo>();
		_archivosImpl=new Hashtable<String, ArchivoImpl>();
		_idActual=0;
	}
	

	// Almacena la información del usuario que entra y los archivos que comparte.
	@Override
	public int conectar(EstrArchivo[] eas, cliente.Usuario usuario) {
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
	public Usuario getUsuario(int id) {
		return _usuarios.get(id);
	}

}
