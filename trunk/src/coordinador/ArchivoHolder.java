package coordinador;

/**
* coordinador/ArchivoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* jueves 13 de mayo de 2010 17H56' CEST
*/


// Creado por el coordinador. Mantiene información de los archivos de la red.
public final class ArchivoHolder implements org.omg.CORBA.portable.Streamable
{
  public coordinador.Archivo value = null;

  public ArchivoHolder ()
  {
  }

  public ArchivoHolder (coordinador.Archivo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = coordinador.ArchivoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    coordinador.ArchivoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return coordinador.ArchivoHelper.type ();
  }

}
