package coordinador;

import java.util.ArrayList;
import java.util.Hashtable;

import cliente.parteArchivo;

public class ArchivoImpl extends ArchivoPOA {
	private String _nombre;
	private long _tam;
	private long _checksum;
	private ArrayList<Integer> _seeds;
	private ArrayList<Integer> _peers;
	private Hashtable<Integer, cliente.parteArchivo[]> _partes;

	public ArchivoImpl(String nombre, long tam, long checksum) {
		_nombre=nombre;
		_tam=tam;
		_checksum=checksum;
		_seeds=new ArrayList<Integer>();
		_peers=new ArrayList<Integer>();
		_partes=new Hashtable<Integer, cliente.parteArchivo[]>();
	}
	
	@Override
	public String nombre() {
		return _nombre;
	}

	@Override
	public long tam() {
		return _tam;
	}

	@Override
	public long checksum() {
		return _checksum;
	}

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

	@Override
	public void insertarPeer(int id, cliente.parteArchivo[] partes) {
		_peers.add(id);
		_partes.put(id, partes);
	}

	@Override
	public void insertarSeed(int id) {
		_seeds.add(id);
	}

	@Override
	public int[] getPeers() {
		int[] aux=new int[_peers.size()];
		
		for(int i=0;i<_peers.size();i++)
			aux[i]=_peers.get(i);
		
		return aux;
	}

	@Override
	public int[] getSeeds() {
		int[] aux=new int[_seeds.size()];
		
		for(int i=0;i<_seeds.size();i++)
			aux[i]=_seeds.get(i);
		
		return aux;
	}

	@Override
	public cliente.parteArchivo[] getPartes(int id) {
		return _partes.get(id);
	}

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
}
