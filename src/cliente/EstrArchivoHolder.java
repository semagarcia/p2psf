package cliente;

/**
* cliente/EstrArchivoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* martes 11 de mayo de 2010 21H59' CEST
*/

public final class EstrArchivoHolder implements org.omg.CORBA.portable.Streamable
{
  public cliente.EstrArchivo value = null;

  public EstrArchivoHolder ()
  {
  }

  public EstrArchivoHolder (cliente.EstrArchivo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = cliente.EstrArchivoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    cliente.EstrArchivoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return cliente.EstrArchivoHelper.type ();
  }

}
