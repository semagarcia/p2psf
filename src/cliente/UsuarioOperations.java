package cliente;


/**
* cliente/UsuarioOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* sábado 29 de mayo de 2010 12H39' CEST
*/


// Sirviente que proporciona partes de un archivo a los usuarios que se la solicitan.
public interface UsuarioOperations 
{

  // Método que devuelve la parte solicitada de un archivo.
  byte[] solicitarParte (String nombre, long inicio, long fin);
} // interface UsuarioOperations
