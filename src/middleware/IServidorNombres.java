/**********************************************************************************
*  Copyright (C) 2006  Aarón Ruiz Mora              <slown_82@yahoo.es>           *
*                      Pedro Antonio Gutiérrez Peña <pagutierres@uco.es>          *
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
 * Interfaz genérica para el servidor de nombres
 * 
 * @author Pedro Antonio Gutiérrez Peña
 *         Aarón Ruiz Mora
 */

public interface IServidorNombres {
	
	/**
	 * Anotar un sirviente registrado en el servidor de nombres
	 * 
	 * @param referencia Referencia en el middleware del sirviente
	 * @param nombre Nombre con el cual anotamos el sirviente
	 */
	
	public void anotarSirviente(Object referencia, String nombre) throws MiddlewareException;
	
	/**
	 * Localizar a un sirviente en el servidor de nombres y obtener
	 * su referencia
	 * 
	 * @param nombre Nombre del sirviente en el servidor de nombres
	 * @return Object Referencia en el middleware de dicho sirviente
	 */
	
	public Object localizar(String nombre) throws MiddlewareException;
	
	/**
	 * Eliminar un servicio con un nombre concreto del servidor de nombres
	 * 
	 * @param nombre Nombre del sirviente en el servidor de nombres
	 */
	
	public void eliminarServicio(String nombre) throws MiddlewareException;

}
