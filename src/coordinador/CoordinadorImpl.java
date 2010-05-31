package coordinador;


import java.util.Collection;
import java.util.Hashtable;
import middleware.Middleware;
import middleware.MiddlewareException;
import cliente.EstrArchivo;
import cliente.Usuario;


/**
 * Implementación del sirviente coordinador. 
 */
public class CoordinadorImpl extends CoordinadorPOA {
	/**
	 * Añade la información de los archivos del usuario
	 * @param eas Estructura de los archivos a añadir del usuario.
	 * @param idUsuario Identificador del usuario.
	 */
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
	

	/**
	 * Elimina al usuario de los archivos que deja de compartir.
	 * @param eas Estructura de los archivos a añadir del usuario.
	 * @param idUsuario Identificador del usuario.
	 */
	public void eliminarArchivos(EstrArchivo[] eas, int idUsuario) {
		System.out.println("El usuario dispone de "+eas.length+" archivos.");
		for(int i=0;i<eas.length;i++) {
			Archivo aux=buscar(eas[i].info.nombre);
			if(aux!=null) {
				if(eas[i].partes[0].fin-eas[i].partes[0].inicio==eas[i].info.tam) {
					aux.eliminarSeed(idUsuario);
				}
				else {
					aux.eliminarPeer(idUsuario);
				}
			
				if(aux.getPeers().length==0 && aux.getSeeds().length==0) {
					try {
						Middleware.desregistrar(_archivosImpl.remove(_archivos.remove(aux.nombre()).nombre()));
					}
					catch (MiddlewareException e) {
						e.printStackTrace();
					}
				}
			}
		}		
	}


	/**
	 * Constructor de la clase. Inicializa la tabla de usuarios y archivos. Inicializa los identificadores a 0. 
	 */
	public CoordinadorImpl() {
		_usuarios=new Hashtable<Integer, Usuario>();
		_archivos=new Hashtable<String, Archivo>();
		_archivosImpl=new Hashtable<String, ArchivoImpl>();
		_idActual=0;
	}
	

	/**
	 * Almacena la información del usuario que entra y los archivos que comparte.
	 * @param eas Estructura de los archivos que posee el usuario.
	 * @param Usuario Usuario que se conecta.
	 * @return Identificador del usuario que se acaba de conectar.
	 */
	@Override
	public int conectar(EstrArchivo[] eas, cliente.Usuario usuario) {
		// Almacena la información del usuario
		_idActual++;		
		_usuarios.put(_idActual, usuario);
		
		// Almacena la información de los archivos que comparte el usuario
		anyadirArchivos(eas, _idActual);

		// Muestra información sobre la red
		mostrarInformacion();
		
		return _idActual;
		}


	/**
	 * Muestra información sobre los archivos compartidos y los usuarios de los mismos.
	 */
	private void mostrarInformacion() {
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


	/**
	 * Devuelve el archivo con el nombre indicado, null si no existe.
	 * @param nombre Nombre del archivo a buscar.
	 * @return Archivo encontrado, null en caso de no existir.
	 */
	@Override
	public Archivo buscar(String nombre) {
		return _archivos.get(nombre);
	}

	
	/**
	 * Elimina al usuario de la tabla de usuarios y actualiza la información de los archivos que compartía.
	 * @param eas Estructura de los archivos a añadir del usuario.
	 * @param idUsuario Identificador del usuario.
	 */
	@Override
	public void desconectar(EstrArchivo[] eas, int idUsuario) {
		//eliminar al usuario de los archivos
		_usuarios.remove(idUsuario);
		
		//actualizar archivos
		eliminarArchivos(eas,idUsuario);

		//muestra información sobre la red
		mostrarInformacion();		
	}


	/**
	 * Devuelve la referencia del usuario.
	 * @param id Identificador del usuario.
	 * @return Usuario con el identificador id. 
	 */
	@Override
	public Usuario getUsuario(int id) {
		return _usuarios.get(id);
	}


	/**
	 * Crea una instancia de Archivo y la hace pública en CORBA
	 * @param ea Información sobre el archivo.
	 * @return Referencia al Archivo creado, null en caso de no haberlo creado.
	 */
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


	/** Identificadores y referencias de los usuarios conectados.*/
	private Hashtable<Integer, Usuario> _usuarios;
	
	/** Nombres y referencias de los archivos en la red. */
	private Hashtable<String, Archivo> _archivos;
	
	/** Nombre y referencias a los sirvientes de los archivos en la red. */
	private Hashtable<String, ArchivoImpl> _archivosImpl;
	
	/** Identificador del último usuario que se conectó. */
	private int _idActual;
}
