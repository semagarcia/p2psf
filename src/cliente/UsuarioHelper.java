package cliente;


/**
* cliente/UsuarioHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* lunes 24 de mayo de 2010 17H19' CEST
*/

abstract public class UsuarioHelper
{
  private static String  _id = "IDL:cliente/Usuario:1.0";

  public static void insert (org.omg.CORBA.Any a, cliente.Usuario that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static cliente.Usuario extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (cliente.UsuarioHelper.id (), "Usuario");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static cliente.Usuario read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_UsuarioStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, cliente.Usuario value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static cliente.Usuario narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof cliente.Usuario)
      return (cliente.Usuario)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      cliente._UsuarioStub stub = new cliente._UsuarioStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static cliente.Usuario unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof cliente.Usuario)
      return (cliente.Usuario)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      cliente._UsuarioStub stub = new cliente._UsuarioStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
