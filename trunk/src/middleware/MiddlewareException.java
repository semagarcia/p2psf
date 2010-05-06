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
 * Excepci�n gen�rica de IMiddleware
 *  
 * @author Pedro Antonio Guti�rrez Pe�a
 *         Aar�n Ruiz Mora
 */

public class MiddlewareException extends Exception {
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Propiedades
	/////////////////////////////////////////////////////////////////
	
	/** N�mero de serie generado por eclipse*/
	private static final long serialVersionUID = -3058446004412448380L;
	
	/** Mensaje de la excepci�n */
	private String message;
	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------ Constructores
	/////////////////////////////////////////////////////////////////

	/**
	 * Constructor que recibe el mensaje de la excepci�n
	 * 
	 * @param message String Mensaje de la excepci�n
	 */
	
	public MiddlewareException(String message) {
		super();
		
		this.message = message;
	}
	
	/////////////////////////////////////////////////////////////////
	// --------------------------- Establecer y recuperar propiedades
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Devuelve el mensaje de la excepci�n
	 * 
	 * @return String Mensaje de la excepci�n
	 */
	
	public String getMessage() {
		return message;
	}

	/**
	 * Establece el mensaje de la excepci�n
	 * 
	 * @param message Mensaje de la excepci�n
	 */
	
	public void setMessage(String message) {
		this.message = message;
	}	

}
