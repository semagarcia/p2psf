package coordinador;


/**
* coordinador/ArchivoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* sábado 29 de mayo de 2010 12H39' CEST
*/


// Creado por el coordinador. Mantiene información de los archivos de la red.
abstract public class ArchivoHelper
{
  private static String  _id = "IDL:coordinador/Archivo:1.0";

  public static void insert (org.omg.CORBA.Any a, coordinador.Archivo that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static coordinador.Archivo extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (coordinador.ArchivoHelper.id (), "Archivo");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static coordinador.Archivo read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ArchivoStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, coordinador.Archivo value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static coordinador.Archivo narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof coordinador.Archivo)
      return (coordinador.Archivo)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      coordinador._ArchivoStub stub = new coordinador._ArchivoStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static coordinador.Archivo unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof coordinador.Archivo)
      return (coordinador.Archivo)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      coordinador._ArchivoStub stub = new coordinador._ArchivoStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
