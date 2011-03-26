package com.maton.tools.stiletto.view.editor;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.view.editor.action.ShowGridAction;
import com.maton.tools.stiletto.view.editor.action.ShowGuideAction;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class ImageEditor extends DefaultEditor implements IGraphicsEditor {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DefaultTable.class, "image.png");

	protected Image image;
	protected Canvas canvas;
	protected boolean showGuide = true;
	protected boolean showGrid = true;

	public ImageEditor(CTabFolder parent, Image img) {
		super(parent);
		image = img;
		item.setText(image.getName());
		item.setData(image);
		item.setImage(icon.createImage());

		build();
	}

	@Override
	protected Control createControl(Composite parent) {
		canvas = new Canvas(parent, SWT.BORDER);

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
		container.redraw();
	}

	protected void paint(Event e) {
		e.gc.setAdvanced(true);
		e.gc.setAntialias(SWT.ON);

		if (showGrid) {
			drawGrid(e.gc);
		}

		if (showGuide) {
			drawGuide(e.gc, 20, 20);
		}

		image.draw(e.gc, 20, 20, 0, 255);
	}

	@Override
	public boolean isShowGrid() {
		return showGrid;
	}

	@Override
	public boolean isShowGuide() {
		return showGuide;
	}

}
