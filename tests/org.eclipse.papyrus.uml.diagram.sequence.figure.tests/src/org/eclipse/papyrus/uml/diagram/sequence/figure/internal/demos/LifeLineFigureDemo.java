package org.eclipse.papyrus.uml.diagram.sequence.figure.internal.demos;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeTypes;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class LifeLineFigureDemo {

	public static void main(String[] args) {
		Display d = new Display();
		final Shell shell = new Shell(d);
		shell.setSize(400, 400);
		shell.setText("LifelineFigure Test");
		shell.setLayout(new GridLayout(1, true));
		Composite parent = new Composite(shell, SWT.BORDER);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.verticalIndent = 5;
		data.horizontalIndent = 5;
		parent.setLayoutData(data);
		Canvas canvas = new Canvas(parent, SWT.NONE);
		parent.setLayout(new FillLayout());
		LightweightSystem lws = new LightweightSystem(canvas);
		
		@SuppressWarnings("restriction")
		Figure contents = new org.eclipse.gmf.runtime.draw2d.ui.internal.graphics.ScalableFreeformLayeredPane(MapModeTypes.IDENTITY_MM);
		ToolbarLayout contentsLayout = new ToolbarLayout(true);
		contentsLayout.setSpacing(5);
		contents.setLayoutManager(contentsLayout);
		LifelineFigure figure = new LifelineFigure();
		figure.setText("LifeLine1");
		
		contents.add(figure);
		lws.setContents(contents);
		shell.open();
		while (!shell.isDisposed())
			while (!d.readAndDispatch())
				d.sleep();
	}
}
