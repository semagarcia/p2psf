package cliente;


/**
* cliente/vByteHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* sábado 29 de mayo de 2010 12H39' CEST
*/


// Secuencia de bytes que los usuarios devolveran como respuesta a las peticiones de archivos.
public final class vByteHolder implements org.omg.CORBA.portable.Streamable
{
  public byte value[] = null;

  public vByteHolder ()
  {
  }

  public vByteHolder (byte[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = cliente.vByteHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    cliente.vByteHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return cliente.vByteHelper.type ();
  }

}
