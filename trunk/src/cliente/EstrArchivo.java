package cliente;


/**
* cliente/EstrArchivo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* lunes 24 de mayo de 2010 17H19' CEST
*/

public final class EstrArchivo implements org.omg.CORBA.portable.IDLEntity
{
  public cliente.infoArchivo info = null;
  public cliente.parteArchivo partes[] = null;

  public EstrArchivo ()
  {
  } // ctor

  public EstrArchivo (cliente.infoArchivo _info, cliente.parteArchivo[] _partes)
  {
    info = _info;
    partes = _partes;
  } // ctor

} // class EstrArchivo