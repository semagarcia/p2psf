package coordinador;


/**
* coordinador/CoordinadorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* sábado 29 de mayo de 2010 12H39' CEST
*/


// Sirviente que permite a los usuarios conectarse a la red, desconectarse y recuperar informacion sobre los archivos.
abstract public class CoordinadorHelper
{
  private static String  _id = "IDL:coordinador/Coordinador:1.0";

  public static void insert (org.omg.CORBA.Any a, coordinador.Coordinador that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static coordinador.Coordinador extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (coordinador.CoordinadorHelper.id (), "Coordinador");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static coordinador.Coordinador read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_CoordinadorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, coordinador.Coordinador value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static coordinador.Coordinador narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof coordinador.Coordinador)
      return (coordinador.Coordinador)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      coordinador._CoordinadorStub stub = new coordinador._CoordinadorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static coordinador.Coordinador unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof coordinador.Coordinador)
      return (coordinador.Coordinador)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      coordinador._CoordinadorStub stub = new coordinador._CoordinadorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
