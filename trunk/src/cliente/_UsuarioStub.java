package cliente;


/**
* cliente/_UsuarioStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* jueves 6 de mayo de 2010 14H48' CEST
*/

public class _UsuarioStub extends org.omg.CORBA.portable.ObjectImpl implements cliente.Usuario
{

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:cliente/Usuario:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _UsuarioStub
