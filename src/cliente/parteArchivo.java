package cliente;


/**
* cliente/parteArchivo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* sábado 29 de mayo de 2010 12H39' CEST
*/

public final class parteArchivo implements org.omg.CORBA.portable.IDLEntity
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public long inicio = (long)0;
  public long fin = (long)0;
  public boolean pedido = false;
  public boolean descargado = false;

  public parteArchivo ()
  {
  } // ctor

  public parteArchivo (long _inicio, long _fin, boolean _pedido, boolean _descargado)
  {
    inicio = _inicio;
    fin = _fin;
    pedido = _pedido;
    descargado = _descargado;
  } // ctor

} // class parteArchivo
