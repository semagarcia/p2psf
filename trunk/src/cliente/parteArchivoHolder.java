package cliente;

/**
* cliente/parteArchivoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* viernes 14 de mayo de 2010 11H07' CEST
*/

public final class parteArchivoHolder implements org.omg.CORBA.portable.Streamable
{
  public cliente.parteArchivo value = null;

  public parteArchivoHolder ()
  {
  }

  public parteArchivoHolder (cliente.parteArchivo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = cliente.parteArchivoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    cliente.parteArchivoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return cliente.parteArchivoHelper.type ();
  }

}
