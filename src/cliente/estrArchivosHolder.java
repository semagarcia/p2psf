package cliente;


/**
* cliente/estrArchivosHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* lunes 24 de mayo de 2010 17H42' CEST
*/


// Secuencia de archivos
public final class estrArchivosHolder implements org.omg.CORBA.portable.Streamable
{
  public cliente.EstrArchivo value[] = null;

  public estrArchivosHolder ()
  {
  }

  public estrArchivosHolder (cliente.EstrArchivo[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = cliente.estrArchivosHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    cliente.estrArchivosHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return cliente.estrArchivosHelper.type ();
  }

}
