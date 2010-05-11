package coordinador;

import java.util.Collection;
import java.util.Hashtable;

public class ArchivoImpl extends ArchivoPOA {
	private String _nombre;
	private int _tam;
	private long _checksum;
	private Hashtable<Integer, String> _seeds;
	private Hashtable<Integer, String> _peers;

	public ArchivoImpl(String nombre, int tam, long checksum) {
		_nombre=nombre;
		_tam=tam;
		_checksum=checksum;
		_seeds=new Hashtable<Integer, String>();
		_peers=new Hashtable<Integer, String>();
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
		_peers.remove(id);
	}

	@Override
	public void eliminarSeed(int id) {
		_seeds.remove(id);
	}

	@Override
	public void insertarPeer(int id, String ref) {
		_peers.put(id, ref);
	}

	@Override
	public void insertarSeed(int id, String ref) {
		_seeds.put(id, ref);
	}

	@Override
	public String[] getPeers() {
		Collection<String> col=_peers.values();
		String[] peers=new String[col.size()];
		col.toArray(peers);

		return peers;
	}

	@Override
	public String[] getSeeds() {
		Collection<String> col=_seeds.values();
		String[] seeds=new String[col.size()];
		col.toArray(seeds);

		return seeds;
	}


}
