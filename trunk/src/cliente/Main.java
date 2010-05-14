package cliente;

import java.io.File;
import coordinador.Archivo;
import middleware.MiddlewareException;

public class Main {		
	public static void main(String args[]) {
		try {
			UsuarioClient cliente=new UsuarioClient(args);
			
			//Rellenar informacion de ficheros compartidos para enviar al coordinador

			
			
			//simulando escuchas y peticiones
			java.util.Random r=new java.util.Random(System.currentTimeMillis());
			if(r.nextBoolean()) {
				System.out.println("Usuario que solo escucha");

				/*
				//añade archivos
				EstrArchivo[] vArch=new EstrArchivo[2];
				long[] piezas=new long[2];

				//simula tener parte del archivo
				long a=(long)Math.ceil(r.nextFloat()*100), b=(long)Math.ceil(r.nextFloat()*100);
				piezas[0]=Math.min(a, b);
				piezas[1]=Math.max(a, b);
				System.out.println(piezas[0]+"-"+piezas[1]);
				vArch[0]=new EstrArchivo("uno",100,0,piezas);
				vArch[1]=new EstrArchivo("dos",100,0,piezas);
				cliente.anyadir(vArch);
				*/
				
				EstrArchivo[] vArch=new EstrArchivo[1];
				infoArchivo info=new infoArchivo();
				parteArchivo[] piezas=new parteArchivo[1];
				piezas[0]=new parteArchivo();

				File f=new File("pdf.pdf");
				
				info.ruta=f.getAbsolutePath();
				info.nombre=f.getName();
				info.tam=f.length();
				info.checksum=0;
				piezas[0].inicio=0;
				piezas[0].fin=f.length();
				System.out.println(f.getName()+": "+piezas[0].inicio+"-"+piezas[0].fin);
				vArch[0]=new EstrArchivo(info,piezas);
				cliente.anyadir(vArch);
				
				//se conecta
				cliente.conectar(args);
			
				// Espera para probar interacciones con el servidor
				Thread.sleep(1000);
			}
			else {
				System.out.println("Usuario que hace peticiones");
				
				//se conecta
				cliente.conectar(args);
				
				//busca el archivo a descargar
				Archivo a=cliente.buscar("pdf.pdf");

				/*
				//simulamos que el usuario tiene una parte del archivo a descargar
				long[] piezas=new long[6];
				piezas[0]=5;
				piezas[1]=10;
				piezas[2]=50;
				piezas[3]=65;
				piezas[4]=90;
				piezas[5]=95;
				*/
				
				parteArchivo[] piezas=null;

				if(a!=null)
					cliente.descargar(a, piezas, 2, 10000, "/home/fran/Escritorio/copia.pdf");
				else
					System.out.println("El archivo no está o no estas conectado.");
			}
			cliente.desconectar();
		}
		catch (MiddlewareException e1) {
			e1.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
