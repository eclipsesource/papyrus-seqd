/*****************************************************************************
 * (c) Copyright 2016 Telefonaktiebolaget LM Ericsson
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Antonio Campesino (Ericsson) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.model.internal.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GMFExplorerUtils {
	 private static Set<Class<?>> PRIMITIVE_WRAPPERS = new HashSet<Class<?>>(Arrays.asList(
		Boolean.class,
		Character.class,
		Byte.class,
		Short.class,
		Integer.class,
		Long.class,
		Float.class,
		Double.class,
		Void.class));
	 
	public static boolean isPrimitiveOrWrapperClass(Class<?> cls) {
		return cls.isPrimitive() || PRIMITIVE_WRAPPERS.contains(cls);
	}


	public static String getClassName(Class<?> cls) {
		return cls.isAnonymousClass() ? 
				cls.getName().substring(cls.getName().lastIndexOf('.')+1) :
				cls.getSimpleName()	;
	}
 
	public static String getText(Object val) {
		if (val == null)
			return "null";
		if (val.getClass().isArray()) {
			if (!isPrimitiveOrWrapperClass(val.getClass().getComponentType()))
				return Arrays.deepToString((Object[])val);
			try {
				if (val.getClass().getComponentType() == char.class)
					return String.valueOf((char[])val);
				return (String)Arrays.class.getMethod("toString", val.getClass()).invoke(null, val);
			} catch (Exception e) {}
			return val.toString();
		}

		if (val instanceof Collection) {
			return getText(((Collection<?>)val).toArray());
		}
		
		Class<?> cls = val.getClass();
		if (isPrimitiveOrWrapperClass(cls) || val instanceof String) {
			if (cls == char.class) {
				return "'"+val+"' ("+Integer.toHexString((int)val)+")";
			}
			return val.toString();
		}
		
		String str = val.toString();
		if (!str.startsWith(cls.getName()+"@")) {
			String className = getClassName(cls); 
			if (!str.startsWith(cls.getName()) && !str.startsWith(className)) {
				return className + str;
			}
			return str;
		}
		
		return getClassName(cls);		
	}

}
