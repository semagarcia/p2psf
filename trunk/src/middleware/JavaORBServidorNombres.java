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

import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * Implementación CORBA de la intefaz IServidorNombres
 *  
 * @author Pedro Antonio Gutiérrez Peña
 *         Aarón Ruiz Mora
 */

public class JavaORBServidorNombres implements IServidorNombres {
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Propiedades
	/////////////////////////////////////////////////////////////////
	
	/** Nodo raíz del contexto */
	protected NamingContext raiz;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------ Constructores
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Contructor que recibe una instancia del ORB de CORBA
	 * 
	 * @param orb ORB del cual se obtendrá el nodo raíz
	 */

	public JavaORBServidorNombres(ORB orb) throws MiddlewareException{
		try {
			this.raiz = 
				NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));
		} catch (InvalidName e) {
			throw new MiddlewareException("JavaORBNameService: Nombre inválido para el Servidor de Nombres");
		} catch (COMM_FAILURE e) {
			throw new MiddlewareException("JavaORBNameService: Fallo de comunicación");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORBNameService: Excepción genérica " + e.getLocalizedMessage());
		} 
	}
	
	/////////////////////////////////////////////////////////////////
	// --------------------- Implementar la interfaz IServidorNombres
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Anotar un sirviente registrado en el servidor de nombres
	 * 
	 * @param referencia Referencia en el middleware del sirviente
	 * @param nombre Nombre con el cual anotamos el sirviente
	 */
	
	public void anotarSirviente(Object referencia, String nombre) throws MiddlewareException{
		
		// Dividir los nombres del contexto ("name0.name1" --> {"name0","name1"}
		String[] names = nombre.split("\\.");
		
		// Componentes del nombre
		NameComponent[] nc = new NameComponent[names.length];
		
		try {
			// Obtener cada NameComponent
			for(int i=0; i<names.length; i++){
				nc[i] = new NameComponent(names[i], "");
			
				// Crear los contextos necesarios
				if(i<(names.length-1)){
					NameComponent[] ncAux = new NameComponent[i+1];
					System.arraycopy(nc, 0, ncAux, 0, i+1);
					raiz.rebind_context(ncAux,raiz.new_context());
				}
			}
			
			// Registrar la referencia en el servidor de nombres
			raiz.rebind(nc,(org.omg.CORBA.Object)referencia);
			
		} catch (NotFound e) {
			throw new MiddlewareException("JavaORBNameService: Nombre no encontrado");
		} catch (CannotProceed e) {
			throw new MiddlewareException("JavaORBNameService: No se puede proceder al acceso");
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			throw new MiddlewareException("JavaORBNameService: Nombre inválido");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORBNameService: Excepción genérica " + e.getLocalizedMessage());
		} 
	}
	
	/**
	 * Localizar a un sirviente en el servidor de nombres y obtener
	 * su referencia
	 * 
	 * @param nombre Nombre del sirviente en el servidor de nombres
	 * @return Object Referencia en el middleware de dicho sirviente
	 */
	
	public Object localizar(String nombre) throws MiddlewareException{
		
		// Dividir los nombres del contexto ("name0.name1" --> {"name0","name1"}
		String[] names = nombre.split("\\.");

		// Componentes del nombre
		NameComponent[] nc = new NameComponent[names.length];

		// Obtener cada NameComponent
		for(int i=0; i<names.length; i++)
			nc[i] = new NameComponent(names[i], "");
		
		try {
			// Localizar el objeto
			return raiz.resolve(nc);
		} catch (NotFound e) {
			throw new MiddlewareException("JavaORBNameService: Nombre no encontrado");
		} catch (CannotProceed e) {
			throw new MiddlewareException("JavaORBNameService: No se puede proceder al acceso");
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			throw new MiddlewareException("JavaORBNameService: Nombre inválido");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORBNameService: Excepción genérica " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Eliminar un servicio con un nombre concreto del servidor de nombres
	 * 
	 * @param nombre Nombre del sirviente en el servidor de nombres
	 */
	
	public void eliminarServicio(String nombre) throws MiddlewareException {
		// Dividir los nombres del contexto ("name0.name1" --> {"name0","name1"}
		String[] names = nombre.split("\\.");
		
		// Componentes del nombre
		NameComponent[] nc = new NameComponent[names.length];
		
		// Obtener cada NameComponent
		for(int i=0; i<names.length; i++)
			nc[i] = new NameComponent(names[i], "");
			
		try {
			// Desregistrar la referencia en el servidor de nombres
			raiz.unbind(nc);
			
		} catch (NotFound e) {
			throw new MiddlewareException("JavaORBNameService: Nombre no encontrado");
		} catch (CannotProceed e) {
			throw new MiddlewareException("JavaORBNameService: No se puede proceder al acceso");
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			throw new MiddlewareException("JavaORBNameService: Nombre inválido");
		}
	}
}
