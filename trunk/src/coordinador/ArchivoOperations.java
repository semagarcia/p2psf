package coordinador;


/**
* coordinador/ArchivoOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* viernes 14 de mayo de 2010 11H07' CEST
*/


// Creado por el coordinador. Mantiene información de los archivos de la red.
public interface ArchivoOperations 
{
  String nombre ();
  long tam ();
  long checksum ();
  void insertarSeed (int id);
  void insertarPeer (int id, cliente.parteArchivo[] partes);
  void eliminarSeed (int id);
  void eliminarPeer (int id);
  int[] getSeeds ();
  int[] getPeers ();
  cliente.parteArchivo[] getPartes (int id);
} // interface ArchivoOperations
