package com.maton.tools.stiletto.view.editor;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.base.IModelListener;
import com.maton.tools.stiletto.view.editor.action.ShowGridAction;
import com.maton.tools.stiletto.view.editor.action.ShowGuideAction;
import com.maton.tools.stiletto.view.editor.action.ShowSelectionAction;
import com.maton.tools.stiletto.view.form.FramedForm;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class ImageEditor extends DefaultEditor implements IGraphicsEditor, IBaseEditor, MouseMoveListener, MouseListener, IModelListener {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DefaultTable.class, "image.png");

	protected Image image;
	protected Canvas canvas;
	protected FramedForm form;
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
	protected boolean hasExtraControl() {
		return true;
	}

	@Override
	protected Control createExtraControl(Composite parent) {
		Composite rowComp = new Composite(parent, SWT.NONE);
		rowComp.setLayout(new RowLayout());
		
		form = new FramedForm(rowComp, image, this);
		
		GridLayout layout = new GridLayout();
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		rowComp.setLayout(layout);

		GridData gd = null;
		
		gd = new GridData();
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		form.getContainer().setLayoutData(gd);

		return rowComp;
	}
	
	@Override
	protected ToolBarManager createExtraToolBarManager(Composite parent) {
		ToolBarManager toolbar = new ToolBarManager(SWT.BORDER | SWT.WRAP);
		toolbar.createControl(parent);

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

		image.draw(e.gc, xOffset, yOffset, 0, 0, 0, 255, false, false);

		if (image.isFramed()) {
			e.gc.setForeground(COLOR_FRA_FOREGROUND);
			e.gc.drawLine((int)(xOffset + image.getLeft()), 0, (int)(xOffset + image.getLeft()), canvas.getBounds().height);
			e.gc.drawLine((int)(xOffset + image.getWidth() - image.getRight()), 0, (int)(xOffset + image.getWidth() - image.getRight()), canvas.getBounds().height);
			e.gc.drawLine(0, (int)(yOffset + image.getTop()), canvas.getBounds().width, (int)(yOffset + image.getTop()));
			e.gc.drawLine(0, (int)(yOffset + image.getHeight() - image.getBottom()), canvas.getBounds().width, (int)(yOffset + image.getHeight() - image.getBottom()));
			
			int newWidth = image.getWidth() * 2;
			int newHeight = image.getHeight() * 2;
			
			int offX = image.getWidth() + 25;
			int offY = image.getHeight() + 25;
			
			image.draw(e.gc, xOffset, yOffset + offY, 0, 0, image.getWidth(), newHeight);
			image.draw(e.gc, xOffset + offX, yOffset, 0, 0, newWidth, image.getHeight());
			image.draw(e.gc, xOffset + offX, yOffset + offY, 0, 0, newWidth, newHeight);
		}
		
		if (showSelection) {
			drawSelection(e.gc, xOffset, yOffset, image.getBounds());
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

	@Override
	public void notifyInsert(Object obj, int idx) {
		
	}

	@Override
	public void notifyNew(Object obj) {
		
	}

	@Override
	public void notifyChange(Object obj) {
		refreshGraphics();
	}

	@Override
	public void notifyDelete(Object obj) {
		
	}

}
