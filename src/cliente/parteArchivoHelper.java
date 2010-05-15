package cliente;


/**
* cliente/parteArchivoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* sábado 15 de mayo de 2010 05H21' CEST
*/

abstract public class parteArchivoHelper
{
  private static String  _id = "IDL:cliente/parteArchivo:1.0";

  public static void insert (org.omg.CORBA.Any a, cliente.parteArchivo that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static cliente.parteArchivo extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_longlong);
          _members0[0] = new org.omg.CORBA.StructMember (
            "inicio",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_longlong);
          _members0[1] = new org.omg.CORBA.StructMember (
            "fin",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[2] = new org.omg.CORBA.StructMember (
            "pedido",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[3] = new org.omg.CORBA.StructMember (
            "descargado",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (cliente.parteArchivoHelper.id (), "parteArchivo", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static cliente.parteArchivo read (org.omg.CORBA.portable.InputStream istream)
  {
    cliente.parteArchivo value = new cliente.parteArchivo ();
    value.inicio = istream.read_longlong ();
    value.fin = istream.read_longlong ();
    value.pedido = istream.read_boolean ();
    value.descargado = istream.read_boolean ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, cliente.parteArchivo value)
  {
    ostream.write_longlong (value.inicio);
    ostream.write_longlong (value.fin);
    ostream.write_boolean (value.pedido);
    ostream.write_boolean (value.descargado);
  }

}
