package cliente;

/**
* cliente/infoArchivoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* lunes 24 de mayo de 2010 17H19' CEST
*/

public final class infoArchivoHolder implements org.omg.CORBA.portable.Streamable
{
  public cliente.infoArchivo value = null;

  public infoArchivoHolder ()
  {
  }

  public infoArchivoHolder (cliente.infoArchivo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = cliente.infoArchivoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    cliente.infoArchivoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return cliente.infoArchivoHelper.type ();
  }

}
