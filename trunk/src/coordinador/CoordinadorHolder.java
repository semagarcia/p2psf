package coordinador;

/**
* coordinador/CoordinadorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* lunes 24 de mayo de 2010 17H42' CEST
*/

public final class CoordinadorHolder implements org.omg.CORBA.portable.Streamable
{
  public coordinador.Coordinador value = null;

  public CoordinadorHolder ()
  {
  }

  public CoordinadorHolder (coordinador.Coordinador initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = coordinador.CoordinadorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    coordinador.CoordinadorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return coordinador.CoordinadorHelper.type ();
  }

}
