package org.eclipse.papyrus.uml.diagram.sequence.figure.internal.demos;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeTypes;
import org.eclipse.papyrus.uml.diagram.sequence.figure.HeaderFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.InteractionFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.MessageAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.MessageFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SampleDiagramWithMessagesDemo {

	public static void main(String[] args) {
		Display d = new Display();
		final Shell shell = new Shell(d);
		shell.setSize(400, 400);
		shell.setText("SampleDiagram Test");
		shell.setLayout(new org.eclipse.swt.layout.GridLayout(1, true));
		Composite parent = new Composite(shell, SWT.BORDER);
		org.eclipse.swt.layout.GridData data = new org.eclipse.swt.layout.GridData(SWT.FILL, SWT.FILL, true, true);
		data.verticalIndent = 5;
		data.horizontalIndent = 5;
		parent.setLayoutData(data);
		Canvas canvas = new Canvas(parent, SWT.NONE);
		parent.setLayout(new FillLayout());
		LightweightSystem lws = new LightweightSystem(canvas);
		
		@SuppressWarnings("restriction")
		Figure contents = new org.eclipse.gmf.runtime.draw2d.ui.internal.graphics.ScalableFreeformLayeredPane(MapModeTypes.IDENTITY_MM);
		GridLayout layout = new GridLayout(1, false);
		contents.setLayoutManager(layout);
		
		InteractionFigure interactionFigure = new InteractionFigure();
		GridLayout interactionLayout = new GridLayout();
		interactionLayout.numColumns = 1;
		interactionLayout.marginHeight=0;
		interactionLayout.marginWidth=0;
		interactionLayout.verticalSpacing = 5;
		interactionFigure.setLayoutManager(interactionLayout);
		interactionFigure.setBackgroundColor(ColorConstants.white);
		GridData interactionFigureData= new GridData(SWT.FILL, SWT.FILL, true, true);
		layout.setConstraint(interactionFigure, interactionFigureData);
		contents.add(interactionFigure);
		
		HeaderFigure interactionNameFigure = new HeaderFigure();
		interactionNameFigure.setBackgroundColor(ColorConstants.button);
		GridData gridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gridData.heightHint = 30;
		interactionLayout.setConstraint(interactionNameFigure, gridData);
		PointList list = new PointList();
		list.addPoint(0,0);
		list.addPoint(100,0);
		list.addPoint(100,20);
		list.addPoint(90,30);
		list.addPoint(0,30);
		list.addPoint(0,0);
		interactionNameFigure.setPoints(list);
		interactionNameFigure.setText("Interaction1");
		interactionNameFigure.setSize(101, 31);
		interactionNameFigure.setLocation(new Point(0,0));
		interactionFigure.add(interactionNameFigure);
		
		Figure interactionCompartmentFigure = new Figure() ;
		GridData gridData2 = new GridData(SWT.FILL, SWT.TOP, true, true);
		interactionLayout.setConstraint(interactionCompartmentFigure, gridData2);
		ConstrainedToolbarLayout interactionComparmentLayout = new ConstrainedToolbarLayout(true);
		interactionComparmentLayout.setStretchMajorAxis(false);
		interactionComparmentLayout.setStretchMinorAxis(false);
		interactionComparmentLayout.setMinorAlignment(OrderedLayout.ALIGN_TOPLEFT);
		interactionComparmentLayout.setSpacing(5);
		interactionCompartmentFigure.setLayoutManager(interactionComparmentLayout);
		interactionCompartmentFigure.setSize(50, 100);
		interactionFigure.add(interactionCompartmentFigure);
		
		LifelineFigure lifelineFigure1 = new LifelineFigure();
		lifelineFigure1.setBackgroundColor(ColorConstants.button);
		lifelineFigure1.setText("LifeLine1");
		
		LifelineFigure lifelineFigure2= new LifelineFigure();
		lifelineFigure2.setBackgroundColor(ColorConstants.button);
		lifelineFigure2.setText("LifeLine2");
		lifelineFigure2.setLifelineSize(100, 400);
		
		interactionCompartmentFigure.add(lifelineFigure1);
		interactionCompartmentFigure.add(lifelineFigure2);
		
		// add a message
		MessageFigure messageFigure1 = new MessageFigure();
		MessageAnchor sourceAnchor = new MessageAnchor(lifelineFigure1.getBodyFigure(), null);
		sourceAnchor.setDelta(10);
		sourceAnchor.setName("SourceAnchor");
		messageFigure1.setSourceAnchor(sourceAnchor);
		MessageAnchor targetAnchor = new MessageAnchor(lifelineFigure2.getBodyFigure(), sourceAnchor);
		targetAnchor.setDelta(0);
		targetAnchor.setName("targetAnchor");
		messageFigure1.setTargetAnchor(targetAnchor);
		contents.add(messageFigure1);
		
		MessageFigure messageFigure2 = new MessageFigure();
		MessageAnchor sourceAnchor2 = new MessageAnchor(lifelineFigure1.getBodyFigure(), targetAnchor);
		sourceAnchor2.setDelta(20);
		sourceAnchor2.setName("sourceAnchor2");
		messageFigure2.setSourceAnchor(sourceAnchor2);
		MessageAnchor targetAnchor2 = new MessageAnchor(lifelineFigure2.getBodyFigure(), sourceAnchor2);
		targetAnchor2.setDelta(50);
		messageFigure2.setTargetAnchor(targetAnchor2);
		targetAnchor2.setName("targetAnchor2");
		contents.add(messageFigure2);
		
		MessageFigure messageFigure3 = new MessageFigure();
		MessageAnchor sourceAnchor3 = new MessageAnchor(lifelineFigure2.getBodyFigure(), targetAnchor2);
		sourceAnchor3.setDelta(20);
		sourceAnchor3.setName("sourceAnchor3");
		messageFigure3.setSourceAnchor(sourceAnchor3);
		MessageAnchor targetAnchor3 = new MessageAnchor(lifelineFigure1.getBodyFigure(), sourceAnchor3);
		targetAnchor3.setDelta(0);
		messageFigure3.setTargetAnchor(targetAnchor3);
		targetAnchor3.setName("targetAnchor3");
		contents.add(messageFigure3);
		
		lws.setContents(contents);
		shell.open();
		while (!shell.isDisposed())
			while (!d.readAndDispatch())
				d.sleep();
	}
}
