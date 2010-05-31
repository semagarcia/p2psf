/**********************************************************************************
*  Copyright (C) 2006  Aar�n Ruiz Mora              <slown_82@yahoo.es>           *
*                      Pedro Antonio Guti�rrez Pe�a <pagutierres@uco.es>          *
*                                                                                 *
* This program is free software; you can redistribute it and/or                   *
* modify it under the terms of the GNU General Public License                     *
* as published by the Free Software Foundation; either version 2                  *
* of the License, or (at your option) any later version.                          *
*                                                                                 *
* This program is distributed in the hope that it will be useful,                 *
* but WITHOUT ANY WARRANTY; without even the implied warranty of                  *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                   *
* GNU General Public License for more details.                                    *
*                                                                                 *
* You should have received a copy of the GNU General Public License               *
* along with this program; if not, write to the Free Software                     *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. *
**********************************************************************************/

package middleware;

/**
 * Clase est�tica para proveer acceso al Middleware
 * (Singleton)
 *  
 * @author Pedro Antonio Guti�rrez Pe�a
 *         Aar�n Ruiz Mora
 */

public abstract class Middleware {
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Propiedades
	/////////////////////////////////////////////////////////////////
	
	/** Middleware al cual se provee acceso */
	private static IMiddleware middleware;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------- M�todos p�blicos
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Inicializar el middleware
	 * 
	 * @param middle Referencia al middleware que se va a utilizar
	 */
	
	public static void inicializar (IMiddleware middle) throws MiddlewareException{
		middleware = middle;
		middleware.inicializar();
	}

	/**
	 * Detener el middleware
	 * 
	 */
	
	public static void detener() throws MiddlewareException{
		middleware.detener();
	}
	
	/**
	 * Ejecutar el middleware
	 * 
	 */
	
	public static void ejecutar(){
		middleware.ejecutar();
	}
	
	/**
	 * Registrar un nuevo sirviente en el middleware
	 * 
	 * @param sirviente Sirviente que se va registrar
	 */
	
	public static void registrar(Object sirviente) throws MiddlewareException{
		middleware.registrar(sirviente);
	}
	
	/**
	 * Desregistrar un sirviente del middleware registrado anteriormente
	 * 
	 * @param sirviente Sirviente que se va a desregistrar
	 */
	
	public static void desregistrar(Object sirviente) throws MiddlewareException{
		middleware.desregistrar(sirviente);
	}
	
	/**
	 * Obtener la interfaz correspondiente a un sirviente registrado
	 * en el middleware
	 * NOTA: No es necesario pasar la referencia propia del middleware
	 *       (se puede utilizar la referencia del sirviente directamente)
	 * 
	 * @param claseSirviente Clase de la interfaz sirviente que se pretende
	 *                       obtener
	 * @param referencia Referencia al sirviente registrado
	 * @return Object Interfaz sirviente
	 */
	
	@SuppressWarnings("unchecked")
	public static Object interfazSirviente(Class claseSirviente, Object referencia) throws MiddlewareException{
		return middleware.interfazSirviente(claseSirviente, referencia);
	}
	
	/**
	 * Obtener la interfaz correspondiente a una referencia catenificada
	 * de un sirviente registrado en el middleware
	 * 
	 * @param claseSirviente Clase de la interfaz sirviente que se pretende
	 *                       obtener
	 * @param referencia Referencia catenificada al sirviente registrado
	 * @return Object Interfaz sirviente
	 */
	
	@SuppressWarnings("unchecked")
	public static Object interfazSirviente(Class claseSirviente, String referencia) throws MiddlewareException{
		return middleware.interfazSirviente(claseSirviente, referencia);		
	}

	/**
	 * Obtener la referencia propia del middleware de un sirviente registrado 
	 * a partir de su referencia
	 * 
	 * @param sirviente Referencia al sirviente registrado
	 * @return Object Referencia propia del middleware para ese sirviente
	 */
	
	public static Object referencia(Object sirviente) throws MiddlewareException{
		return middleware.referencia(sirviente);
	}

	/**
	 * Obtener la referencia cadenificada de un sirviente registrado 
	 * a partir de su referencia
	 * 
	 * @param sirviente Referencia al sirviente registrado
	 * @return String Referencia cadenificada para ese sirviente
	 */
	
	public static String referenciaCadenificada(Object sirviente) throws MiddlewareException{
		return middleware.referenciaCadenificada(sirviente);		
	}
	
	/**
	 * Obtener el servidor de nombres del middleware
	 * 
	 * @return INameService Servidor de nombres del middleware
	 */
	
	public static IServidorNombres servidorNombres() throws MiddlewareException{
		return middleware.servidorNombres();		
	}
}
