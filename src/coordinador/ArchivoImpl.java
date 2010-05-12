package coordinador;

import java.util.ArrayList;
import java.util.Hashtable;

public class ArchivoImpl extends ArchivoPOA {
	private String _nombre;
	private int _tam;
	private long _checksum;
	private ArrayList<Integer> _seeds;
	private ArrayList<Integer> _peers;
	private Hashtable<Integer, long[]> _partes;

	public ArchivoImpl(String nombre, int tam, long checksum) {
		_nombre=nombre;
		_tam=tam;
		_checksum=checksum;
		_seeds=new ArrayList<Integer>();
		_peers=new ArrayList<Integer>();
		_partes=new Hashtable<Integer, long[]>();
	}
	
	@Override
	public String nombre() {
		return _nombre;
	}

	@Override
	public int tam() {
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
		
		while(!encontrado && i<_peers.size())
			if(_peers.get(i)==id) {
				encontrado=true;
				_peers.remove(i);
			}
	}

	@Override
	public void eliminarSeed(int id) {
		int i=0;
		boolean encontrado=false;
		
		while(!encontrado && i<_seeds.size())
			if(_seeds.get(i)==id) {
				encontrado=true;
				_seeds.remove(i);
			}
	}

	@Override
	public void insertarPeer(int id, long[] partes) {
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
	public long[] getPartes(int id) {
		return _partes.get(id);
	}
}
