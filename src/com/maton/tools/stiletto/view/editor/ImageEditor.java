package com.maton.tools.stiletto.view.editor;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.view.editor.action.ShowGridAction;
import com.maton.tools.stiletto.view.editor.action.ShowGuideAction;
import com.maton.tools.stiletto.view.editor.action.ShowSelectionAction;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class ImageEditor extends DefaultEditor implements IGraphicsEditor, IBaseEditor, MouseMoveListener, MouseListener {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DefaultTable.class, "image.png");

	protected Image image;
	protected Canvas canvas;
	protected boolean showGuide = true;
	protected boolean showGrid = true;
	protected boolean showSelection = false;
	protected int xOffset = 20, yOffset = 20;
	protected MouseTracker tracker;

	public ImageEditor(CTabFolder parent, Image img) {
		super(parent);
		image = img;
		item.setText(image.getName());
		item.setImage(icon.createImage());

		build();
	}
	
	public void dispose() {
		
	}
	
	public Object getData() {
		return image;
	}

	@Override
	protected Control createControl(Composite parent) {
		canvas = new Canvas(parent, SWT.BORDER | SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND);

		canvas.addMouseMoveListener(this);
		canvas.addMouseListener(this);
		tracker = new MouseTracker(this);
		
		canvas.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				paint(event);
			}
		});

		return canvas;
	}

	@Override
	protected ToolBarManager createToolBarManager(Composite parent) {
		ToolBarManager toolbar = new ToolBarManager(SWT.BORDER | SWT.WRAP);
		toolbar.createControl(parent);
		toolbar.add(new ShowGuideAction(this));
		toolbar.add(new ShowGridAction(this));
		toolbar.add(new ShowSelectionAction(this));

		return toolbar;
	}

	@Override
	public void save() {
		// No save on images
	}

	@Override
	public void setShowGuide(boolean showGuide) {
		this.showGuide = showGuide;
	}

	@Override
	public void setShowGrid(boolean showGrid) {
		this.showGrid = showGrid;
	}

	@Override
	public void refreshGraphics() {
		canvas.setRedraw(true);
		canvas.redraw();
	}

	protected void paint(Event e) {
		fill(e.gc, canvas);

		if (showGrid) {
			drawGrid(e.gc);
		}

		if (showGuide) {
			drawGuide(e.gc, xOffset, yOffset);
		}

		image.draw(e.gc, xOffset, yOffset, 0, 255);
		
		if (showSelection) {
			drawSelection(e.gc, xOffset, yOffset, image);
		}
	}

	@Override
	public boolean isShowGrid() {
		return showGrid;
	}

	@Override
	public boolean isShowGuide() {
		return showGuide;
	}

	public boolean isShowSelection() {
		return showSelection;
	}

	public void setShowSelection(boolean showSelection) {
		this.showSelection = showSelection;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		
	}

	@Override
	public void mouseDown(MouseEvent e) {
		tracker.mouseDown(e.x, e.y);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		tracker.mouseUp();
	}

	@Override
	public void mouseMove(MouseEvent e) {
		tracker.mouseMove(e.x, e.y);
	}

	@Override
	public void switchTool(ToolType tool) {
		// Solo tiene Pan
	}

	@Override
	public void move(int x, int y) {
		xOffset += x;
		yOffset += y;
		refreshGraphics();
	}

}
