package com.maton.tools.stiletto.view.editor;

import org.eclipse.jface.action.Separator;
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

import com.maton.tools.stiletto.model.CharMetric;
import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.view.editor.action.RefreshAction;
import com.maton.tools.stiletto.view.editor.action.ShowGridAction;
import com.maton.tools.stiletto.view.editor.action.ShowGuideAction;
import com.maton.tools.stiletto.view.form.FontForm;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class FontEditor extends DefaultEditor implements IGraphicsEditor,
		IBaseEditor, MouseMoveListener, MouseListener {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DefaultTable.class, "ui-label-link.png");

	protected Font font;
	protected Canvas canvas;
	protected boolean showGuide = true;
	protected boolean showGrid = true;
	protected int xOffset = 20, yOffset = 100;
	protected MouseTracker tracker;
	protected FontForm form;

	public FontEditor(CTabFolder parent, Font font) {
		super(parent);
		this.font = font;
		item.setText(font.getName());
		item.setImage(icon.createImage());

		build();
	}

	public void dispose() {
		form.dispose();
	}

	public Object getData() {
		return font;
	}

	@Override
	protected Control createControl(Composite parent) {
		canvas = new Canvas(parent, SWT.BORDER);

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
		toolbar.add(new Separator());
		toolbar.add(new RefreshAction(this));

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
		} else {
			drawGridBlack(e.gc);
		}

		if (showGuide) {
			drawGuide(e.gc, xOffset, yOffset);
		}

		int maxw = canvas.getBounds().width - 100;
		CharMetric cm = new CharMetric();
		int x = 0;
		int y = 0;
		int max = 0;
		for (int i = 0; i < font.getCharacters().length(); i++) {
			font.draw(e.gc, xOffset + x, yOffset + y, font.getCharacters()
					.charAt(i), cm);
			x += cm.xadvance + 20;
			
			max = Math.max(cm.height, max);
			
			if (x > maxw) {
				y += max + 20;
				max = 0;
				x = 0;
			}
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

	@Override
	public void setShowSelection(boolean showSelection) {
	}

	@Override
	public boolean isShowSelection() {
		return false;
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
	protected boolean hasExtraControl() {
		return true;
	}

	@Override
	protected ToolBarManager createExtraToolBarManager(Composite parent) {
		ToolBarManager toolbarManager = new ToolBarManager();
		toolbarManager.createControl(parent);

		return toolbarManager;
	}

	@Override
	protected Control createExtraControl(Composite parent) {
		form = new FontForm(parent, font);

		return form.getContainer();
	}

}
