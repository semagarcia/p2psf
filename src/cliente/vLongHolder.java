package cliente;


/**
* cliente/vLongHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* miércoles 12 de mayo de 2010 13H29' CEST
*/

public final class vLongHolder implements org.omg.CORBA.portable.Streamable
{
  public long value[] = null;

  public vLongHolder ()
  {
  }

  public vLongHolder (long[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = cliente.vLongHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    cliente.vLongHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return cliente.vLongHelper.type ();
  }

}
