package org.eclipse.papyrus.uml.diagram.sequence.figure.internal.demos;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeTypes;
import org.eclipse.papyrus.uml.diagram.sequence.figure.HeaderFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class HeaderFigureDemo {

	public static void main(String[] args) {
		Display d = new Display();
		final Shell shell = new Shell(d);
		shell.setSize(400, 400);
		shell.setText("HeaderFigure Test");
		shell.setLayout(new GridLayout(1,true));
		Composite parent = new Composite(shell, SWT.BORDER);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.verticalIndent=5;
		data.horizontalIndent=5;
		parent.setLayoutData(data);
		Canvas canvas = new Canvas(parent, SWT.NONE);
		parent.setLayout(new FillLayout());
		LightweightSystem lws = new LightweightSystem(canvas);
		@SuppressWarnings("restriction")
		Figure contents = new org.eclipse.gmf.runtime.draw2d.ui.internal.graphics.ScalableFreeformLayeredPane(MapModeTypes.IDENTITY_MM);
		ConstrainedToolbarLayout contentsLayout = new ConstrainedToolbarLayout(false);
		contentsLayout.setStretchMinorAxis(true);
		contentsLayout.setStretchMajorAxis(true);
		contentsLayout.setSpacing(5);
		contents.setLayoutManager(contentsLayout);
		
		
		Figure figure = new Figure() {
			protected boolean useLocalCoordinates() {
				return true;
			};
		};
		ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout(false);
		layout.setSpacing(5);
		figure.setLayoutManager(layout);

		RectangleFigure rectBefore = new RectangleFigure();
		rectBefore.setBackgroundColor(ColorConstants.black);
		rectBefore.setSize(60, 20);
		
		HeaderFigure headerFigure = new HeaderFigure();
		PointList list = new PointList();
		list.addPoint(0,0);
		list.addPoint(100,0);
		list.addPoint(100,10);
		list.addPoint(90,20);
		list.addPoint(0,20);
		list.addPoint(0,0);
		headerFigure.setPoints(list);
		headerFigure.setText("Test1");
		headerFigure.setSize(20, 20);;
		
		RectangleFigure rectAfter = new RectangleFigure();
		rectAfter.setBackgroundColor(ColorConstants.black);
		rectAfter.setSize(60, 20);
		
		contents.add(figure);
		figure.add(rectBefore);
		figure.add(headerFigure);
		figure.add(rectAfter);
		
		lws.setContents(contents);
		shell.open();
		while (!shell.isDisposed())
			while (!d.readAndDispatch())
				d.sleep();
	}
}
