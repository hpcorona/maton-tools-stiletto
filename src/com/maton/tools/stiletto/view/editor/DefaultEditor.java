package com.maton.tools.stiletto.view.editor;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public abstract class DefaultEditor {

	protected static Image GUIDE;
	protected static Image GRID;

	protected CTabFolder parent;
	protected CTabItem item;
	protected Composite container;
	protected ToolBarManager toolbar;

	public DefaultEditor(CTabFolder parent) {
		this.parent = parent;
		item = new CTabItem(parent, SWT.CLOSE);
		item.setData(this);
	}

	protected abstract Control createControl(Composite parent);

	protected abstract ToolBarManager createToolBarManager(Composite parent);

	protected void build() {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		container.setLayout(layout);

		GridData gd = null;

		toolbar = createToolBarManager(container);
		if (toolbar != null) {
			gd = new GridData();
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;
			gd.grabExcessHorizontalSpace = true;
			gd.minimumHeight = 20;
			toolbar.getControl().setLayoutData(gd);
			toolbar.update(true);
		}

		Control child = createControl(container);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		child.setLayoutData(gd);

		item.setControl(container);
	}

	public abstract void save();

	static {
		Display display = Display.getCurrent();
		GRID = new Image(display, 299, 299);
		GC gc = new GC(GRID);
		gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.fillRectangle(0, 0, 299, 299);
		gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
		boolean par = false;
		boolean impar = false;
		for (int x = 0; x < 300; x += 30) {
			if (par) {
				impar = true;
			} else {
				impar = false;
			}
			for (int y = 0; y < 300; y += 30) {
				if (impar) {
					gc.fillRectangle(x, y, 30, 30);
				}
				impar = !impar;
			}
			par = !par;
		}
		gc.dispose();

		Image temp = new Image(display, 301, 301);
		gc = new GC(temp);
		gc.setLineStyle(SWT.LINE_DOT);
		gc.drawLine(0, 150, 300, 150);
		gc.drawLine(150, 0, 150, 300);
		gc.dispose();

		ImageData data = temp.getImageData();
		int color = data.getPixel(0, 0);
		data.transparentPixel = data.palette
				.getPixel(data.palette.getRGB(color));
		temp.dispose();
		
		GUIDE = new Image(display, data);
	}
	
	protected void drawGrid(GC gc) {
		Rectangle rect = container.getClientArea();
		for (int x = rect.x; x <= rect.x + rect.width; x += 299) {
			for (int y = rect.y; y <= rect.y + rect.height; y += 299) {
				gc.drawImage(GRID, x, y);
			}
		}
		gc.drawImage(GRID, 0, 0);
	}
	
	protected void drawGuide(GC gc, int x, int y) {
		gc.drawImage(GUIDE, -150 + x, -150 + y);
	}
	
	public CTabItem getItem() {
		return item;
	}
	
}
