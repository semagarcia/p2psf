package cliente;


/**
* cliente/infoArchivo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* viernes 14 de mayo de 2010 11H07' CEST
*/

public final class infoArchivo implements org.omg.CORBA.portable.IDLEntity
{
  public String ruta = null;

  // SE PUEDE QUITAR DE AQUÍ E IMPLEMENTARLO EN EL INTERIOR UNA VEZ GENERADO EL CODIGO
  public String nombre = null;
  public long tam = (long)0;
  public long checksum = (long)0;

  public infoArchivo ()
  {
  } // ctor

  public infoArchivo (String _ruta, String _nombre, long _tam, long _checksum)
  {
    ruta = _ruta;
    nombre = _nombre;
    tam = _tam;
    checksum = _checksum;
  } // ctor

} // class infoArchivo
