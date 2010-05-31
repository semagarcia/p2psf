package coordinador;


import java.util.ArrayList;
import java.util.Hashtable;
import cliente.parteArchivo;


/**
 * Implementación de los métodos para el objeto de tipo fábrica Archivo. 
 */
public class ArchivoImpl extends ArchivoPOA {
	/**
	 * Constructor de la clase. Inicializa los atributos.
	 * @param nombre Nombre del archivo.
	 * @param tam Tamaño del archivo.
	 * @param checksum Checksum del archivo.
	 */
	public ArchivoImpl(String nombre, long tam, long checksum) {
		_nombre=nombre;
		_tam=tam;
		_checksum=checksum;
		_seeds=new ArrayList<Integer>();
		_peers=new ArrayList<Integer>();
		_partes=new Hashtable<Integer, cliente.parteArchivo[]>();
	}
	
	
	/**
	 * Devuelve el nombre del archivo.
	 * @return Nombre del archivo. 
	 */
	@Override
	public String nombre() {
		return _nombre;
	}

	
	/**
	 * Devuelve el tamaño del archivo.
	 * @return Tamaño del archivo.
	 */
	@Override
	public long tam() {
		return _tam;
	}

	
	/**
	 * Devuelve el checksum del archivo.
	 * @return Checksum del archivo.
	 */
	@Override
	public long checksum() {
		return _checksum;
	}

	
	/**
	 * Eliminar un peer del archivo.
	 * @param id Identificador del peer a eliminar.
	 */
	@Override
	public void eliminarPeer(int id) {
		int i=0;
		boolean encontrado=false;
		
		while(!encontrado && i<_peers.size()) {
			if(_peers.get(i)==id) {
				encontrado=true;
				_peers.remove(i);
			}
			else i++;
		}
	}

	
	/**
	 * Elimina un seed del archivo.
	 * @param id Identificador del seed a eliminar.
	 */
	@Override
	public void eliminarSeed(int id) {
		int i=0;
		boolean encontrado=false;
		
		while(!encontrado && i<_seeds.size()) {
			if(_seeds.get(i)==id) {
				encontrado=true;
				_seeds.remove(i);
			}
			else i++;
		}
	}

	
	/**
	 * Inserta un peer en el archivo.
	 * @param id Identificador del peer a insertar.
	 * @param partes Piezas del archivo que posee el peer.
	 */
	@Override
	public void insertarPeer(int id, cliente.parteArchivo[] partes) {
		_peers.add(id);
		_partes.put(id, partes);
	}

	
	/**
	 * Inserta un seed en el archivo.
	 * @param id Identificador del seed a insertar.
	 */
	@Override
	public void insertarSeed(int id) {
		_seeds.add(id);
	}

	
	/**
	 * Devuelve los identificadores de los peers que poseen éste archivo.
	 * @return Identificadores de los peers del archivo.
	 */
	@Override
	public int[] getPeers() {
		int[] aux=new int[_peers.size()];
		
		for(int i=0;i<_peers.size();i++)
			aux[i]=_peers.get(i);
		
		return aux;
	}


	/**
	 * Devuelve los identificadores de los seeds que poseen éste archivo.
	 * @return Identificadores de los seeds del archivo.
	 */
	@Override
	public int[] getSeeds() {
		int[] aux=new int[_seeds.size()];
		
		for(int i=0;i<_seeds.size();i++)
			aux[i]=_seeds.get(i);
		
		return aux;
	}


	/**
	 * Devuelve las partes que un peer posee de un archivo.
	 * @param id Identificador del peer.
	 * @return Partes del archivo que posee el peer.
	 */
	@Override
	public cliente.parteArchivo[] getPartes(int id) {
		return _partes.get(id);
	}

	
	/**
	 * Actualiza las partes que un usuario posee de un archivo.
	 * @param id Identificador del usuario sobre el cual actualizar las partes.
	 * @param partes Partes del archivo que posee el usuario.
	 */
	@Override
	public void actualizarPartes(int id, parteArchivo[] partes) {		
		if(partes[0].inicio==0 && partes[0].fin==_tam) {
			//El usuario se ha convertido en un seed de este fichero
			eliminarPeer(id);
			insertarSeed(id);
			
		}
		else {
			boolean encontrado=false;
			int i=0;
			
			while(!encontrado && i<_peers.size()) {
				if(_peers.get(i)==id) encontrado=true;
				else i++;
			}
			if(!encontrado) {
				_peers.add(id);
			}
			
			_partes.put(id, partes);
		}
	}
	
	
	/** Nombre del archivo. */
	private String _nombre;
	
	/** Tamaño del archivo. */
	private long _tam;
	
	/** Checksum del archivo. */
	private long _checksum;
	
	/** Seeds que poseen el archivo. */
	private ArrayList<Integer> _seeds;
	
	/** Peers que poseen el archivo. */
	private ArrayList<Integer> _peers;
	
	/** Partes que poseen los peers del archivo. */
	private Hashtable<Integer, cliente.parteArchivo[]> _partes;
}
