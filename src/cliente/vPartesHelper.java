package cliente;


/**
* cliente/vPartesHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* viernes 14 de mayo de 2010 11H07' CEST
*/

abstract public class vPartesHelper
{
  private static String  _id = "IDL:cliente/vPartes:1.0";

  public static void insert (org.omg.CORBA.Any a, cliente.parteArchivo[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static cliente.parteArchivo[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = cliente.parteArchivoHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (cliente.vPartesHelper.id (), "vPartes", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static cliente.parteArchivo[] read (org.omg.CORBA.portable.InputStream istream)
  {
    cliente.parteArchivo value[] = null;
    int _len0 = istream.read_long ();
    value = new cliente.parteArchivo[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = cliente.parteArchivoHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, cliente.parteArchivo[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      cliente.parteArchivoHelper.write (ostream, value[_i0]);
  }

}
