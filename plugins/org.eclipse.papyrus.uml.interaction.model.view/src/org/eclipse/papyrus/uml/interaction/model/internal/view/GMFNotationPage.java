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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class GMFNotationPage extends GMFExplorerPage {
	private final AdapterFactory adapterFactory;

	public GMFNotationPage(GraphicalEditor editor) {
		super(editor);
		TransactionalEditingDomain editingDomain = ((DiagramEditor)editor).getEditingDomain();
		adapterFactory = ((AdapterFactoryEditingDomain) editingDomain).getAdapterFactory();
	}

	@Override
	protected EditPart getEditPart(Object obj) {
		EditPartViewer viewer = getEditPartViewer();
		if (getEditPartViewer() == null)
			return null;
		
		return (EditPart)viewer.getEditPartRegistry().get(obj);
	}

	@Override
	protected Object getInputObject(GraphicalEditPart graphicalEP) {
		Object obj = graphicalEP instanceof RootEditPart ? 
				((RootEditPart)graphicalEP).getContents().getModel() : 
				graphicalEP.getModel();
		
		while (!(obj instanceof View)) {
			if (!(obj instanceof EObject))
				return null;
			obj = ((EObject)obj).eContainer();
		}
		return obj == null ? null : ((View)obj).getDiagram();	
	}


	@Override
	protected Object getSelectedObject(GraphicalEditPart graphicalEP) {
		return graphicalEP.getModel();
	}

	@Override
	protected ILabelProvider getExplorerLabelProvider() {
		return new AdapterFactoryLabelProvider(adapterFactory);
	}


	@Override
	protected IContentProvider getExplorerContentProvider() {
		return new AdapterFactoryContentProvider(adapterFactory) {
			@Override
			public Object[] getElements(Object object) {	
				EObject eObj = (EObject)((Object[])object)[0];
				return new Object[] {eObj};
			}			
		};
	}
	
	protected IPropertySource createPropertySource(Object object) {
		return new GMFExplorerPropertySource(object) {

			@Override
			public IPropertyDescriptor[] getPropertyDescriptors() {
				List<IPropertyDescriptor> res =  new ArrayList<IPropertyDescriptor>();				
				if (object instanceof View) {
					View view = (View)object;

					EditPart ep = null;
					if (getEditPartViewer() != null)
						ep = (EditPart)getEditPartViewer().getEditPartRegistry().get(view);
					
					EObject el = null;
					if (view != null)
						el = view.getElement();
					
					res.add(new PropertyDescriptor("Class", GMFExplorerUtils.getClassName(view.getClass())));
					res.add(new PropertyDescriptor("EObject", el));
					res.add(new PropertyDescriptor("EditPart", ep));
					res.add(new PropertyDescriptor("Figure", ep instanceof IGraphicalEditPart ? ((IGraphicalEditPart)ep).getFigure() : null));
					res.add(new PropertyDescriptor("Active", view.isVisible()));
					res.add(new PropertyDescriptor("Persisted", view.eContainer() != null ? ((View)view.eContainer()).getPersistedChildren().contains(view) : null));

					if (object instanceof Edge) {
						Edge c = (Edge)object;					
						res.add(new PropertyDescriptor("Source", c.getSource()));
						res.add(new PropertyDescriptor("SourceAnchor", c.getSourceAnchor()));
						res.add(new PropertyDescriptor("Target", c.getTarget()));
						res.add(new PropertyDescriptor("TargetAnchor", c.getTargetAnchor()));
						res.add(new PropertyDescriptor("Bendpoints", c.getBendpoints()));						
					} else if (object instanceof Node){ 
						Node c = (Node)object;					
						res.add(new PropertyDescriptor("LayoutConstraints", c.getLayoutConstraint()));
					} 
				}
				res.addAll(Arrays.asList(super.getPropertyDescriptors()));
				return res.toArray(new IPropertyDescriptor[0]);
			}
		};
	}

}
