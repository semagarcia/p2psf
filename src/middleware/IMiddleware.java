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
 * Interfaz gen�rica para el Middleware
 * 
 * @author Pedro Antonio Guti�rrez Pe�a
 *         Aar�n Ruiz Mora
 */

public interface IMiddleware {
	
	/**
	 * Inicializar el middleware
	 * 
	 */
	
	public void inicializar () throws MiddlewareException;
	
	/**
	 * Detener el middleware
	 * 
	 */
	
	public void detener() throws MiddlewareException;
	
	/**
	 * Ejecutar el middleware
	 * 
	 */
	
	public void ejecutar();
	
	/**
	 * Registrar un nuevo sirviente en el middleware
	 * 
	 * @param sirviente Sirviente que se va registrar
	 */
	
	public void registrar(Object sirviente) throws MiddlewareException;
	
	/**
	 * Desregistrar un objeto del middleware registrado anteriormente
	 * NOTA: No es necesario pasar la referencia del sirviente
	 *       (se puede utilizar la referencia del middleware directamente)
	 *       
	 * @param objeto Objeto que se va a desregistrar
	 */
	
	public void desregistrar(Object objeto) throws MiddlewareException;
	
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
	public Object interfazSirviente(Class claseSirviente, Object referencia) throws MiddlewareException;
	
	/**
	 * Obtener la interfaz correspondiente a una referencia cadenificada
	 * de un sirviente registrado en el middleware
	 * 
	 * @param referencia Referencia cadenificada al sirviente registrado
	 * @param claseSirviente Clase de la interfaz sirviente que se pretende
	 *                       obtener
	 * @return Object Interfaz sirviente
	 */
	
	@SuppressWarnings("unchecked")
	public Object interfazSirviente(Class claseSirviente, String referencia) throws MiddlewareException;

	/**
	 * Obtener la referencia propia del middleware de un sirviente registrado 
	 * a partir de su referencia
	 * 
	 * @param sirviente Referencia al sirviente registrado
	 * @return Object Referencia propia del middleware para ese sirviente
	 */
	
	public Object referencia(Object sirviente) throws MiddlewareException;

	/**
	 * Obtener la referencia cadenificada de un sirviente registrado 
	 * a partir de su referencia
	 * 
	 * @param sirviente Referencia al sirviente registrado
	 * @return String Referencia catenificada para ese sirviente
	 */
	
	public String referenciaCadenificada(Object servant) throws MiddlewareException;
	
	/**
	 * Obtener el servidor de nombres del middleware
	 * 
	 * @return INameService Servidor de nombres del middleware
	 */
	
	public IServidorNombres servidorNombres() throws MiddlewareException;	
}
