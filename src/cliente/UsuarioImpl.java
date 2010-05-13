package cliente;

public class UsuarioImpl extends UsuarioPOA {
	
	private float _prueba;
	
	@Override
	public String saluda() {
		return "Hola"+_prueba;
	}

	
	public void set() {
		_prueba=new java.util.Random(System.currentTimeMillis()).nextFloat();
	}
}
