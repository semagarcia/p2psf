package coordinador;


/**
* coordinador/ArchivoPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* miércoles 12 de mayo de 2010 18H55' CEST
*/


// Creado por el coordinador. Mantiene información de los archivos de la red.
public abstract class ArchivoPOA extends org.omg.PortableServer.Servant
 implements coordinador.ArchivoOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_nombre", new java.lang.Integer (0));
    _methods.put ("_get_tam", new java.lang.Integer (1));
    _methods.put ("_get_checksum", new java.lang.Integer (2));
    _methods.put ("insertarSeed", new java.lang.Integer (3));
    _methods.put ("insertarPeer", new java.lang.Integer (4));
    _methods.put ("eliminarSeed", new java.lang.Integer (5));
    _methods.put ("eliminarPeer", new java.lang.Integer (6));
    _methods.put ("getSeeds", new java.lang.Integer (7));
    _methods.put ("getPeers", new java.lang.Integer (8));
    _methods.put ("getPartes", new java.lang.Integer (9));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // coordinador/Archivo/_get_nombre
       {
         String $result = null;
         $result = this.nombre ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // coordinador/Archivo/_get_tam
       {
         long $result = (long)0;
         $result = this.tam ();
         out = $rh.createReply();
         out.write_longlong ($result);
         break;
       }

       case 2:  // coordinador/Archivo/_get_checksum
       {
         long $result = (long)0;
         $result = this.checksum ();
         out = $rh.createReply();
         out.write_longlong ($result);
         break;
       }

       case 3:  // coordinador/Archivo/insertarSeed
       {
         int id = in.read_long ();
         this.insertarSeed (id);
         out = $rh.createReply();
         break;
       }

       case 4:  // coordinador/Archivo/insertarPeer
       {
         int id = in.read_long ();
         long partes[] = cliente.vLongHelper.read (in);
         this.insertarPeer (id, partes);
         out = $rh.createReply();
         break;
       }

       case 5:  // coordinador/Archivo/eliminarSeed
       {
         int id = in.read_long ();
         this.eliminarSeed (id);
         out = $rh.createReply();
         break;
       }

       case 6:  // coordinador/Archivo/eliminarPeer
       {
         int id = in.read_long ();
         this.eliminarPeer (id);
         out = $rh.createReply();
         break;
       }

       case 7:  // coordinador/Archivo/getSeeds
       {
         int $result[] = null;
         $result = this.getSeeds ();
         out = $rh.createReply();
         coordinador.vIntHelper.write (out, $result);
         break;
       }

       case 8:  // coordinador/Archivo/getPeers
       {
         int $result[] = null;
         $result = this.getPeers ();
         out = $rh.createReply();
         coordinador.vIntHelper.write (out, $result);
         break;
       }

       case 9:  // coordinador/Archivo/getPartes
       {
         int id = in.read_long ();
         long $result[] = null;
         $result = this.getPartes (id);
         out = $rh.createReply();
         cliente.vLongHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:coordinador/Archivo:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Archivo _this() 
  {
    return ArchivoHelper.narrow(
    super._this_object());
  }

  public Archivo _this(org.omg.CORBA.ORB orb) 
  {
    return ArchivoHelper.narrow(
    super._this_object(orb));
  }


} // class ArchivoPOA
