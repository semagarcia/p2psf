package coordinador;


/**
* coordinador/CoordinadorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* lunes 24 de mayo de 2010 17H19' CEST
*/

public interface CoordinadorOperations 
{
  int conectar (cliente.EstrArchivo[] eas, cliente.Usuario usuario);
  void desconectar (cliente.EstrArchivo[] eas, int idUsuario);
  void anyadirArchivos (cliente.EstrArchivo[] eas, int idUsuario);
  void eliminarArchivos (cliente.EstrArchivo[] eas, int idUsuario);
  coordinador.Archivo buscar (String nombre);
  cliente.Usuario getUsuario (int id);
} // interface CoordinadorOperations
