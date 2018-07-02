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

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class GMFFigurePage extends GMFExplorerPage {
	
	public GMFFigurePage(GraphicalEditor editor) {
		super(editor);
	}

	@Override
	protected EditPart getEditPart(Object obj) {
		EditPartViewer viewer = getEditPartViewer();
		if (getEditPartViewer() == null)
			return null;
		
		return (EditPart)viewer.getVisualPartMap().get(obj);
	}
	
	@Override
	protected Object getInputObject(GraphicalEditPart graphicalEP) {
		IFigure root = graphicalEP.getFigure();
		while (root != null && root.getParent() != null)
			root = root.getParent();
		return root;	
	}


	@Override
	protected Object getSelectedObject(GraphicalEditPart graphicalEP) {
		return graphicalEP.getFigure();
	}


	@Override
	protected LabelProvider getExplorerLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return GMFExplorerUtils.getClassName(element.getClass()); 
			}
			
		};
	}

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
				if (element instanceof IFigure) {
					return ((IFigure) element).getParent();
				}
				return null;
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof IFigure) {
					return ((IFigure) parentElement).getChildren().toArray();
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
				if (object instanceof Figure) {
					IFigure f = (IFigure)object;
					EditPart ep = (EditPart)getEditPartViewer().getVisualPartMap().get(object);
					View view = null;
					if (ep != null && ep.getModel() instanceof View)
						view = (View)ep.getModel();
					
					EObject el = null;
					if (view != null)
						el = view.getElement();
					
					res.add(new PropertyDescriptor("Class", GMFExplorerUtils.getClassName(object.getClass())));
					res.add(new PropertyDescriptor("EObject", el));
					res.add(new PropertyDescriptor("View", view));
					res.add(new PropertyDescriptor("EditPart", ep));
					res.add(new PropertyDescriptor("Visible", f.isVisible()));
					
					if (object instanceof Connection) {
						Connection c = (Connection)object;					
						res.add(new PropertyDescriptor("Source Anchor", c.getSourceAnchor()));
						res.add(new PropertyDescriptor("Target Anchor", c.getTargetAnchor()));
						res.add(new PropertyDescriptor("Routing Constraint", c.getRoutingConstraint()));
					} else if (object instanceof Figure) { 
						Rectangle absBounds = f.getBounds().getCopy();
						f.translateToAbsolute(absBounds);
						res.add(new PropertyDescriptor("Coord Syst", f.isCoordinateSystem()));
						res.add(new PropertyDescriptor("Bounds", f.getBounds()));
						res.add(new PropertyDescriptor("Bounds (abs.)", absBounds));
						res.add(new PropertyDescriptor("Client Area", f.getClientArea()));
						res.add(new PropertyDescriptor("Border", f.getBorder()));					
					}				
				}
				res.addAll(Arrays.asList(super.getPropertyDescriptors()));
				return res.toArray(new IPropertyDescriptor[0]);
			}
		};
	}
}
