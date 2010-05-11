package cliente;

import coordinador.Archivo;
import middleware.Middleware;
import middleware.MiddlewareException;

public class Main {		
	public static void main(String args[]) {
		try {
			UsuarioClient cliente=new UsuarioClient(args);
			
			//Rellenar informacion de ficheros compartidos para enviar al coordinador
			EstrArchivo[] vArch=new EstrArchivo[2];
			vArch[0]=new EstrArchivo("uno",0,0,true);
			vArch[1]=new EstrArchivo("dos",1,1,true);
			cliente.anyadir(vArch);
			
			vArch=new EstrArchivo[1];
			vArch[0]=new EstrArchivo("dos",0,0,true);
			cliente.eliminar(vArch);

			vArch=new EstrArchivo[2];
			vArch[0]=new EstrArchivo("tres",0,0,true);
			vArch[1]=new EstrArchivo("cuatro",1,1,true);
			cliente.anyadir(vArch);
			
			cliente.conectar(args);

			vArch=new EstrArchivo[1];
			vArch[0]=new EstrArchivo("tres",0,0,true);
			cliente.eliminar(vArch);

			
			
			//simulando escuchas y peticiones
			java.util.Random r=new java.util.Random(System.currentTimeMillis());
			if(r.nextBoolean()) {
				System.out.println("Usuario que solo escucha");
				// Espera para probar intaracciones con el servidor
				Thread.sleep(10000);
			}
			else {
				System.out.println("Usuario que hace peticiones");
				
				Archivo a=cliente.buscar("uno");
				if(a!=null) {
					String uCad=a.getSeeds()[0];
					Usuario usu=(Usuario) Middleware.interfazSirviente(Usuario.class, uCad);
					
					System.out.println(usu.saluda());
				}
				else
					System.out.println("El archivo no está.");
			}
			cliente.desconectar();
		}
		catch (MiddlewareException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
