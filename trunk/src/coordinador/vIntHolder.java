package coordinador;


/**
* coordinador/vIntHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* lunes 24 de mayo de 2010 17H19' CEST
*/


//Definición de array de int
public final class vIntHolder implements org.omg.CORBA.portable.Streamable
{
  public int value[] = null;

  public vIntHolder ()
  {
  }

  public vIntHolder (int[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = coordinador.vIntHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    coordinador.vIntHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return coordinador.vIntHelper.type ();
  }

}
