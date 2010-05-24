package cliente;


/**
* cliente/infoArchivoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* lunes 24 de mayo de 2010 17H19' CEST
*/

abstract public class infoArchivoHelper
{
  private static String  _id = "IDL:cliente/infoArchivo:1.0";

  public static void insert (org.omg.CORBA.Any a, cliente.infoArchivo that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static cliente.infoArchivo extract (org.omg.CORBA.Any a)
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
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "ruta",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "nombre",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_longlong);
          _members0[2] = new org.omg.CORBA.StructMember (
            "tam",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_longlong);
          _members0[3] = new org.omg.CORBA.StructMember (
            "checksum",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (cliente.infoArchivoHelper.id (), "infoArchivo", _members0);
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

  public static cliente.infoArchivo read (org.omg.CORBA.portable.InputStream istream)
  {
    cliente.infoArchivo value = new cliente.infoArchivo ();
    value.ruta = istream.read_string ();
    value.nombre = istream.read_string ();
    value.tam = istream.read_longlong ();
    value.checksum = istream.read_longlong ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, cliente.infoArchivo value)
  {
    ostream.write_string (value.ruta);
    ostream.write_string (value.nombre);
    ostream.write_longlong (value.tam);
    ostream.write_longlong (value.checksum);
  }

}