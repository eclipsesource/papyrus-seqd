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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class GMFExplorerPropertySource implements IPropertySource {

	public GMFExplorerPropertySource(Object object) {
		super();
		this.object = object;
	}

	@Override
	public Object getEditableValue() {
		return GMFExplorerUtils.getText(object);
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (object == null || GMFExplorerUtils.isPrimitiveOrWrapperClass(object.getClass()))
			return new IPropertyDescriptor[0];
		
		Class<?> cls = object.getClass();
		if (cls.isArray()) {
			IPropertyDescriptor[] desc = new IPropertyDescriptor[Array.getLength(object)];
			for (int i = 0; i<desc.length; i++) {
				desc[i] = new PropertyDescriptor("["+i+"]", Array.get(object, i));
			}
			return desc;
		} else if (object instanceof Collection) {
			Collection<?> it = (Collection<?>)object;
			IPropertyDescriptor[] desc = new IPropertyDescriptor[it.size()];
			int i = 0;
			for (Object obj : it) {
				desc[i] = new PropertyDescriptor("["+i+"]", obj);
				i++;
			}
			return desc;
		} 
		
		List<Field> fs = new ArrayList<Field>(getFields(object.getClass(), new LinkedHashSet<Field>()));
		Collections.sort(fs, (a,b)-> a.getName().compareToIgnoreCase(b.getName()));
		
		int i=0;
		IPropertyDescriptor[] res = new IPropertyDescriptor[fs.size()];		
		for (Field f : fs) {
			f.setAccessible(true);
			try {
				res[i] = new PropertyDescriptor(f.getName(), f.get(object));
			} catch (Exception e) {
				res[i] = new PropertyDescriptor(f.getName(), "ERROR: " + e.getMessage());
			}
			i++;
		}
		return res;
	}

	@Override
	public Object getPropertyValue(Object id) {
		return ((PropertyDescriptor)id).value;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}

	private Set<Field> getFields(Class<?> cls, Set<Field> l) {
		if (cls == Object.class)
			return l;
		for (Field f : cls.getDeclaredFields()) {
			if (f.isEnumConstant() || Modifier.isStatic(f.getModifiers()))
				continue;
			l.add(f);
		}
		return getFields(cls.getSuperclass(),l);
	}

	protected static class PropertyDescriptor implements IPropertyDescriptor {
		private ILabelProvider labelProvider = null;
		PropertyDescriptor(String name, Object value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public CellEditor createPropertyEditor(Composite parent) {
			return null;
		}

		@Override
		public String getCategory() {
			return null;
		}

		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public String getDisplayName() {
			return name;
		}

		@Override
		public String[] getFilterFlags() {
			return null;
		}

		@Override
		public Object getHelpContextIds() {
			return null;
		}

		@Override
		public Object getId() {
			return this;
		}

		@Override
		public ILabelProvider getLabelProvider() {
			if (labelProvider == null) {
				labelProvider = new LabelProvider() {
					@Override
					public String getText(Object element) {
						return GMFExplorerUtils.getText(element);
					}		
				};
			}
			return labelProvider;
		}

		@Override
		public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
			return anotherProperty == this;
		}

		private String name;
		private Object value;
	}
	
	protected Object object;
}
