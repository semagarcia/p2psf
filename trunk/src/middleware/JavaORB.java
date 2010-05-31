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

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/**
 * Implementaci�n CORBA de la intefaz IMiddleware
 *  
 * @author Pedro Antonio Guti�rrez Pe�a
 *         Aar�n Ruiz Mora
 */

public class JavaORB implements IMiddleware {
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Propiedades
	/////////////////////////////////////////////////////////////////

	/** Opciones de inicializaci�n */
	public String options[];
	
	/** Propiedades de inicializaci�n */
	public Properties props = new Properties();
	
	/** Adaptador de objetos portable (Portable Object Adaptor) */
	protected POA poa;
	
	/** Agente de petici�n de objetos (Object Request Broker) */
	protected org.omg.CORBA.ORB orb;
	
	/** Servidor de nombres */
	protected JavaORBServidorNombres servidorNombres;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------- IImplementar la interfaz IMiddleware
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Inicializar el middleware
	 * 
	 */

	public void inicializar() throws MiddlewareException{

		try {
			
			// Obtener el ORB
			orb = org.omg.CORBA.ORB.init(options, props);

			// Obtener la referencia al servicio "RootPOA"
			poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			
			// POA creado con las pol�ticas por defecto:
			// Thread Policy: ORB_CTRL_MODEL
		    // Lifespan Policy: TRANSIENT
		    // Object Id Uniqueness Policy: UNIQUE_ID
		    // Id Assignment Policy: SYSTEM_ID
		    // Servant Retention Policy: RETAIN
		    // Request Processing Policy: USE_ACTIVE_OBJECT_MAP_ONLY
		    // Implicit Activation Policy: IMPLICIT_ACTIVATION			
			
			// Activar el POA
			poa.the_POAManager().activate();
			
			// Activar e inicializar el servidor de nombres
			servidorNombres = new JavaORBServidorNombres(orb);
		} 
		catch (AdapterInactive e) {
			throw new MiddlewareException("JavaORB: Adaptador de objetos inactivo");
		}		
		catch (InvalidName e) {
			throw new MiddlewareException("JavaORB: Nombre de POA inv�lido");
		}
		catch (COMM_FAILURE e) {
			throw new MiddlewareException("JavaORB: Fallo de comunicaci�n");
		}
		catch (SystemException e) {
			throw new MiddlewareException("JavaORB: Excepci�n gen�rica " + e.getLocalizedMessage());
		}
		catch (MiddlewareException e) {
			throw e;
		}
	}

	/**
	 * Detener el middleware
	 * 
	 */
	
	public void detener() throws MiddlewareException{
		// Para la implementaci�n de CORBA en JAVAORB no es necesario hacer nada
	}
	
	/**
	 * Ejecutar el middleware
	 * 
	 */
	
	public void ejecutar(){
		orb.run();
	}
	
	/**
	 * Registrar un nuevo sirviente en el middleware
	 * 
	 * @param sirviente Sirviente que se va registrar
	 */
	
	public void registrar(Object sirviente) throws MiddlewareException{
		
		// Hacer un sirviente accesible a trav�s de la red
		try {
			poa.activate_object((Servant) sirviente);
		} catch (ServantAlreadyActive e) {
			throw new MiddlewareException("JavaORB: Sirviente ya activado");
		} catch (WrongPolicy e) {
			throw new MiddlewareException("JavaORB: Pol�tica de POA no incorrecta");
		} catch (COMM_FAILURE e) {
			throw new MiddlewareException("JavaORB: Fallo de comunicaci�n");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORB: Excepci�n gen�rica " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Desregistrar un objeto del middleware registrado anteriormente
	 * 
	 * @param sirviente Objeto que se va a desregistrar
	 */
	
	public void desregistrar(Object sirviente) throws MiddlewareException{
		
		try {
			poa.deactivate_object(poa.servant_to_id((Servant) sirviente));
		} catch (ObjectNotActive e) {
			throw new MiddlewareException("JavaORB: Objeto inactivo");
		} catch (WrongPolicy e) {
			throw new MiddlewareException("JavaORB: Pol�tica de POA no incorrecta");
		} catch (ServantNotActive e) {
			throw new MiddlewareException("JavaORB: Sirviente inactivo");
		} catch (COMM_FAILURE e) {
			throw new MiddlewareException("JavaORB: Fallo de comunicaci�n");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORB: Excepci�n gen�rica " + e.getLocalizedMessage());
		}
		
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
	public Object interfazSirviente (Class claseSirviente, Object referencia) throws MiddlewareException{
		
		try {
			org.omg.CORBA.Object referenciaCORBA = null;
			
			// Si la referencia no es un objeto CORBA, debemos obtener
			// el objeto CORBA del sirviente
			if(!(referencia instanceof org.omg.CORBA.Object)){
				try {
					referenciaCORBA = poa.servant_to_reference((Servant)referencia);
				} catch (ServantNotActive e) {
					throw new MiddlewareException("JavaORB: Sirviente inactivo");
				} catch (WrongPolicy e) {
					throw new MiddlewareException("JavaORB: Pol�tica de POA no incorrecta");
				}
			}
			else
				referenciaCORBA = (org.omg.CORBA.Object) referencia;
			
			// Obtener la clase Helper
			Class claseHelper = Class.forName(claseSirviente.getCanonicalName() + "Helper");
			
			// Invocar al m�todo narrow
			Class[] tipoParametros = {org.omg.CORBA.Object.class};
			Object[] parametros = {referenciaCORBA};
			return claseHelper.getDeclaredMethod("narrow", tipoParametros).invoke(null, parametros);
			
		} catch (COMM_FAILURE e) {
			throw new MiddlewareException("JavaORB: Fallo de comunicaci�n");
		} catch (ClassNotFoundException e) {
			throw new MiddlewareException("JavaORB: Clase Helper no encontrada");
		} catch (SecurityException e) {
			throw new MiddlewareException("JavaORB: Problema de seguridad");
		} catch (NoSuchMethodException e) {
			throw new MiddlewareException("JavaORB: M�todo narrow(org.omg.CORBA.Object) no encontrado");
		} catch (IllegalArgumentException e) {
			throw new MiddlewareException("JavaORB: Argumento incorrecto para el m�todo narrow");
		} catch (IllegalAccessException e) {
			throw new MiddlewareException("JavaORB: Acceso ilegal al m�todo narrow");
		} catch (InvocationTargetException e) {
			throw new MiddlewareException("JavaORB: Excepci�n en la invocaci�n destino");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORB: Excepci�n gen�rica " + e.getLocalizedMessage());
		}
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
	public Object interfazSirviente(Class claseSirviente, String referencia) throws MiddlewareException{
		try {
			// Obtener la clase Helpe
			Class claseHelper = Class.forName(claseSirviente.getCanonicalName() + "Helper");

			// Invocar al m�todo narrow
			Class[] tipoParametros = {org.omg.CORBA.Object.class};
			Object[] parametros = {orb.string_to_object(referencia)};
			return claseHelper.getDeclaredMethod("narrow", tipoParametros).invoke(null, parametros);
			
		} catch (COMM_FAILURE e) {
			throw new MiddlewareException("JavaORB: Fallo de comunicaci�n");
		} catch (ClassNotFoundException e) {
			throw new MiddlewareException("JavaORB: Clase Helper no encontrada");
		} catch (SecurityException e) {
			throw new MiddlewareException("JavaORB: Problema de seguridad");
		} catch (NoSuchMethodException e) {
			throw new MiddlewareException("JavaORB: M�todo narrow(org.omg.CORBA.Object) no encontrado");
		} catch (IllegalArgumentException e) {
			throw new MiddlewareException("JavaORB: Argumento incorrecto para el m�todo narrow");
		} catch (IllegalAccessException e) {
			throw new MiddlewareException("JavaORB: Acceso ilegal al m�todo narrow");
		} catch (InvocationTargetException e) {
			throw new MiddlewareException("JavaORB: Excepci�n en la invocaci�n destino");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORB: Excepci�n gen�rica " + e.getLocalizedMessage());
		}
	}

	/**
	 * Obtener la referencia propia del middleware de un sirviente registrado 
	 * a partir de su referencia
	 * 
	 * @param sirviente Referencia al sirviente registrado
	 * @return Object Referencia propia del middleware para ese sirviente
	 */
	
	public Object referencia(Object sirviente) throws MiddlewareException{
		try {
			return poa.servant_to_reference((Servant)sirviente);
		} catch (COMM_FAILURE e) {
			throw new MiddlewareException("JavaORB: Fallo de comunicaci�n");
		} catch (ServantNotActive e) {
			throw new MiddlewareException("JavaORB: Sirviente inactivo");
		} catch (WrongPolicy e) {
			throw new MiddlewareException("JavaORB: Pol�tica de POA no incorrecta");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORB: Excepci�n gen�rica " + e.getLocalizedMessage());
		}
	}
	

	/**
	 * Obtener la referencia cadenificada de un sirviente registrado 
	 * a partir de su referencia
	 * 
	 * @param sirviente Referencia al sirviente registrado
	 * @return String Referencia cadenificada para ese sirviente
	 */
	
	public String referenciaCadenificada(Object sirviente) throws MiddlewareException{
		try {
			return orb.object_to_string(poa.servant_to_reference((Servant)sirviente));
		} catch (COMM_FAILURE e) {
			throw new MiddlewareException("JavaORB: Fallo de comunicaci�n");
		} catch (ServantNotActive e) {
			throw new MiddlewareException("JavaORB: Sirviente inactivo");
		} catch (WrongPolicy e) {
			throw new MiddlewareException("JavaORB: Pol�tica de POA no incorrecta");
		} catch (SystemException e) {
			throw new MiddlewareException("JavaORB: Excepci�n gen�rica " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Obtener el servidor de nombres del middleware
	 * 
	 * @return INameService Servidor de nombres del middleware
	 */
	
	public IServidorNombres servidorNombres() throws MiddlewareException{
		if(servidorNombres==null)
			throw new MiddlewareException("JavaORB: Servidor de nombres no disponible");
		return servidorNombres;
	}
}
