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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class GMFEditPartPage extends GMFExplorerPage {
	
	public GMFEditPartPage(GraphicalEditor editor) {
		super(editor);
	}

	@Override
	protected EditPart getEditPart(Object obj) {
		return (EditPart)obj;
	}

	/* (non-Javadoc)
	 * @see com.ericsson.papyrus.nwa.devtools.views.GMFExplorerView#getInputObject(org.eclipse.gef.GraphicalEditPart)
	 */
	@Override
	protected Object getInputObject(GraphicalEditPart graphicalEP) {
		return graphicalEP.getRoot();
			
	}


	/* (non-Javadoc)
	 * @see com.ericsson.papyrus.nwa.devtools.views.GMFExplorerView#getSelectedObject(org.eclipse.gef.GraphicalEditPart)
	 */
	@Override
	protected Object getSelectedObject(GraphicalEditPart graphicalEP) {
		return graphicalEP;
	}

	/* (non-Javadoc)
	 * @see com.ericsson.papyrus.nwa.devtools.views.GMFExplorerView#getExplorerLabelProvider()
	 */
	@Override
	protected LabelProvider getExplorerLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return GMFExplorerUtils.getClassName(element.getClass()); 
			}
			
		};
	}


	/* (non-Javadoc)
	 * @see com.ericsson.papyrus.nwa.devtools.views.GMFExplorerView#getExplorerContentProvider()
	 */
	@Override
	protected IContentProvider getExplorerContentProvider() {
		return new ITreeContentProvider() {

			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement instanceof Object[]) {
					return (Object[]) inputElement;
				} 
				return null;
			}

			@Override
			public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {}

			@Override
			public void dispose() {}

			@Override
			public boolean hasChildren(final Object element) {
				return getChildren(element).length != 0;
			}

			@Override
			public Object getParent(final Object element) {
				if (element instanceof EditPart) {
					return ((EditPart) element).getParent();
				}
				return null;
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof EditPart) {
					return ((EditPart) parentElement).getChildren().toArray();
				}
				return new Object[0];
			}
		};
	}

	protected IPropertySource createPropertySource(Object object) {
		return new GMFExplorerPropertySource(object) {

			@Override
			public IPropertyDescriptor[] getPropertyDescriptors() {
				List<IPropertyDescriptor> res =  new ArrayList<IPropertyDescriptor>();				
				if (object instanceof GraphicalEditPart) {
					GraphicalEditPart ep = (GraphicalEditPart)object;
					View view = null;
					if (ep != null && ep.getModel() instanceof View)
						view = (View)ep.getModel();
					
					EObject el = null;
					if (view != null)
						el = view.getElement();
					
					res.add(new PropertyDescriptor("Class", GMFExplorerUtils.getClassName(object.getClass())));
					res.add(new PropertyDescriptor("EObject", el));
					res.add(new PropertyDescriptor("View", view));
					res.add(new PropertyDescriptor("Figure", ep.getFigure()));
					res.add(new PropertyDescriptor("Content Pane", ep.getContentPane()));
					res.add(new PropertyDescriptor("Active", ep.isActive()));
					res.add(new PropertyDescriptor("Selectable", ep.isSelectable()));
					
					if (object instanceof ConnectionEditPart) {
						ConnectionEditPart c = (ConnectionEditPart)object;					
						res.add(new PropertyDescriptor("Source", c.getSource()));
						res.add(new PropertyDescriptor("Target", c.getTarget()));
					} 			
				}
				res.addAll(Arrays.asList(super.getPropertyDescriptors()));
				return res.toArray(new IPropertyDescriptor[0]);
			}
		};
	}
}
