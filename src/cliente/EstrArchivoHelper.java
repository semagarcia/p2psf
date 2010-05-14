package cliente;


/**
* cliente/EstrArchivoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* viernes 14 de mayo de 2010 11H07' CEST
*/

abstract public class EstrArchivoHelper
{
  private static String  _id = "IDL:cliente/EstrArchivo:1.0";

  public static void insert (org.omg.CORBA.Any a, cliente.EstrArchivo that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static cliente.EstrArchivo extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = cliente.infoArchivoHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "info",
            _tcOf_members0,
            null);
          _tcOf_members0 = cliente.parteArchivoHelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (cliente.vPartesHelper.id (), "vPartes", _tcOf_members0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "partes",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (cliente.EstrArchivoHelper.id (), "EstrArchivo", _members0);
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

  public static cliente.EstrArchivo read (org.omg.CORBA.portable.InputStream istream)
  {
    cliente.EstrArchivo value = new cliente.EstrArchivo ();
    value.info = cliente.infoArchivoHelper.read (istream);
    value.partes = cliente.vPartesHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, cliente.EstrArchivo value)
  {
    cliente.infoArchivoHelper.write (ostream, value.info);
    cliente.vPartesHelper.write (ostream, value.partes);
  }

}
