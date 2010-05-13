package coordinador;


/**
* coordinador/_CoordinadorStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* jueves 13 de mayo de 2010 17H56' CEST
*/

public class _CoordinadorStub extends org.omg.CORBA.portable.ObjectImpl implements coordinador.Coordinador
{

  public int conectar (cliente.EstrArchivo[] eas, String usuario)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("conectar", true);
                cliente.estrArchivosHelper.write ($out, eas);
                $out.write_string (usuario);
                $in = _invoke ($out);
                int $result = $in.read_long ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return conectar (eas, usuario        );
            } finally {
                _releaseReply ($in);
            }
  } // conectar

  public void desconectar (cliente.EstrArchivo[] eas, int idUsuario)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("desconectar", true);
                cliente.estrArchivosHelper.write ($out, eas);
                $out.write_long (idUsuario);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                desconectar (eas, idUsuario        );
            } finally {
                _releaseReply ($in);
            }
  } // desconectar

  public void anyadirArchivos (cliente.EstrArchivo[] eas, int idUsuario)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("anyadirArchivos", true);
                cliente.estrArchivosHelper.write ($out, eas);
                $out.write_long (idUsuario);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                anyadirArchivos (eas, idUsuario        );
            } finally {
                _releaseReply ($in);
            }
  } // anyadirArchivos

  public void eliminarArchivos (cliente.EstrArchivo[] eas, int idUsuario)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("eliminarArchivos", true);
                cliente.estrArchivosHelper.write ($out, eas);
                $out.write_long (idUsuario);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                eliminarArchivos (eas, idUsuario        );
            } finally {
                _releaseReply ($in);
            }
  } // eliminarArchivos

  public coordinador.Archivo buscar (String nombre)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("buscar", true);
                $out.write_string (nombre);
                $in = _invoke ($out);
                coordinador.Archivo $result = coordinador.ArchivoHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return buscar (nombre        );
            } finally {
                _releaseReply ($in);
            }
  } // buscar

  public String getReferencia (int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getReferencia", true);
                $out.write_long (id);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getReferencia (id        );
            } finally {
                _releaseReply ($in);
            }
  } // getReferencia

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:coordinador/Coordinador:1.0"};

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
} // class _CoordinadorStub
