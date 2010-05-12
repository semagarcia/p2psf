package cliente;

import coordinador.Archivo;
import middleware.Middleware;
import middleware.MiddlewareException;

public class Main {		
	public static void main(String args[]) {
		try {
			UsuarioClient cliente=new UsuarioClient(args);
			
			//Rellenar informacion de ficheros compartidos para enviar al coordinador
/*			EstrArchivo[] vArch=new EstrArchivo[2];
			long[] piezas=new long[2];
			piezas[0]=0;
			piezas[1]=0;
			vArch[0]=new EstrArchivo("uno",0,0,piezas);
			vArch[1]=new EstrArchivo("dos",1,1,piezas);
			cliente.anyadir(vArch);
			
			vArch=new EstrArchivo[1];
			vArch[0]=new EstrArchivo("dos",0,0,piezas);
			cliente.eliminar(vArch);

			vArch=new EstrArchivo[2];
			vArch[0]=new EstrArchivo("tres",0,0,piezas);
			vArch[1]=new EstrArchivo("cuatro",1,1,piezas);
			cliente.anyadir(vArch);
			
			cliente.conectar(args);

			vArch=new EstrArchivo[1];
			vArch[0]=new EstrArchivo("tres",0,0,piezas);
			cliente.eliminar(vArch);
*/
			
			
			//simulando escuchas y peticiones
			java.util.Random r=new java.util.Random(System.currentTimeMillis());
			if(r.nextBoolean()) {
				System.out.println("Usuario que solo escucha");

				//añade archivos
				EstrArchivo[] vArch=new EstrArchivo[2];
				long[] piezas=new long[2];
				piezas[0]=0;
				piezas[1]=100;
				vArch[0]=new EstrArchivo("uno",100,0,piezas);
				vArch[1]=new EstrArchivo("dos",100,0,piezas);
				cliente.anyadir(vArch);
				
				//se conecta
				cliente.conectar(args);
			
				// Espera para probar interacciones con el servidor
				Thread.sleep(10000);
			}
			else {
				System.out.println("Usuario que hace peticiones");
				
				//se conecta
				cliente.conectar(args);
				
				//busca el archivo a descargar
				Archivo a=cliente.buscar("uno");

				//simulamos que el usuario tiene una parte del archivo a descargar
				long[] piezas=new long[6];
				piezas[0]=5;
				piezas[1]=10;
				piezas[2]=50;
				piezas[3]=65;
				piezas[4]=90;
				piezas[5]=95;
				

				if(a!=null) {
					//lanza el hilo de descarga para el archivo
					Downloader d=new Downloader(a, piezas, 10, 10);
					d.start();
					/*
					String uCad=cliente.getReferencia(a.getSeeds()[0]);
					Usuario usu=(Usuario) Middleware.interfazSirviente(Usuario.class, uCad);
					
					System.out.println(usu.saluda());
					*/
				}
				else
					System.out.println("El archivo no está o no estas conectado.");
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
