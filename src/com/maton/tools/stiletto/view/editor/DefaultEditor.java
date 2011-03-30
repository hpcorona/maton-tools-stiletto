package com.maton.tools.stiletto.view.editor;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
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
	protected static Image GRIDBLACK;
	protected static Color BACKGROUND;

	protected CTabFolder parent;
	protected CTabItem item;
	protected Composite mainContainer;
	protected Composite container;
	protected ToolBarManager toolbar;
	protected Composite extraContainer;
	protected ToolBarManager extraToolbar;

	public DefaultEditor(CTabFolder parent) {
		this.parent = parent;
		item = new CTabItem(parent, SWT.CLOSE);
		item.setData(this);
	}

	public abstract void dispose();

	public abstract Object getData();

	protected abstract Control createControl(Composite parent);

	protected abstract ToolBarManager createToolBarManager(Composite parent);

	protected void createMainContainer() {
		mainContainer = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		mainContainer.setLayout(layout);
	}

	protected void createContainer() {
		container = new Composite(mainContainer, SWT.NONE);
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
	}

	protected boolean createExtraContainer() {
		if (hasExtraControl() == false)
			return false;

		extraContainer = new Composite(mainContainer, SWT.BORDER);
		GridLayout layout = new GridLayout(1, true);
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		extraContainer.setLayout(layout);

		GridData gd = null;

		extraToolbar = createExtraToolBarManager(extraContainer);
		if (toolbar != null) {
			gd = new GridData();
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;
			gd.grabExcessHorizontalSpace = true;
			gd.minimumHeight = 20;
			extraToolbar.getControl().setLayoutData(gd);
			extraToolbar.update(true);
		}

		Control child = createExtraControl(extraContainer);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		child.setLayoutData(gd);

		return true;
	}

	protected boolean hasExtraControl() {
		return false;
	}

	protected ToolBarManager createExtraToolBarManager(Composite parent) {
		return null;
	}

	protected Control createExtraControl(Composite parent) {
		return null;
	}

	protected void build() {
		createMainContainer();

		createContainer();

		GridData gd = null;

		if (createExtraContainer()) {
			gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;
			gd.grabExcessHorizontalSpace = true;
			gd.grabExcessVerticalSpace = true;
			container.setLayoutData(gd);

			gd = new GridData(GridData.VERTICAL_ALIGN_END);
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;
			gd.grabExcessHorizontalSpace = false;
			gd.grabExcessVerticalSpace = true;
			gd.widthHint = 220;
			extraContainer.setLayoutData(gd);
		} else {
			gd = new GridData();
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;
			gd.grabExcessVerticalSpace = true;
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalSpan = 2;
			container.setLayoutData(gd);
		}

		item.setControl(mainContainer);
	}

	public abstract void save();

	static {
		Display display = Display.getCurrent();

		BACKGROUND = new Color(display, 112, 120, 194);

		GRID = new Image(display, 299, 299);
		GRIDBLACK = new Image(display, 299, 299);
		GC gc = new GC(GRID);
		GC gc2 = new GC(GRIDBLACK);
		gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.fillRectangle(0, 0, 299, 299);
		gc2.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		gc2.fillRectangle(0, 0, 299, 299);
		gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
		gc2.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
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
					gc2.fillRectangle(x, y, 30, 30);
				}
				impar = !impar;
			}
			par = !par;
		}
		gc.dispose();
		gc2.dispose();

		Image temp = new Image(display, 301, 301);
		gc = new GC(temp);
		gc.setLineStyle(SWT.LINE_DOT);
		gc.drawLine(0, 150, 300, 150);
		gc.drawLine(150, 0, 150, 300);
		gc.dispose();

		ImageData data = temp.getImageData();
		int color = data.getPixel(0, 0);
		data.transparentPixel = data.palette.getPixel(data.palette
				.getRGB(color));
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
	}

	protected void drawGridBlack(GC gc) {
		Rectangle rect = container.getClientArea();
		for (int x = rect.x; x <= rect.x + rect.width; x += 299) {
			for (int y = rect.y; y <= rect.y + rect.height; y += 299) {
				gc.drawImage(GRIDBLACK, x, y);
			}
		}
	}

	protected void drawGuide(GC gc, int x, int y) {
		gc.drawImage(GUIDE, -150 + x, -150 + y);
	}

	static Color COLOR_SEL_BACKGROUND = new Color(Display.getCurrent(), 50, 50,
			140);
	static Color COLOR_SEL_FOREGROUND = new Color(Display.getCurrent(), 20, 20,
			100);

	protected void drawSelection(GC gc, int x, int y,
			com.maton.tools.stiletto.model.Image img) {
		int w = img.getWidth();
		int h = img.getHeight();
		gc.setAlpha(100);

		gc.setBackground(COLOR_SEL_BACKGROUND);
		gc.fillRectangle(x, y, w, h);

		gc.setAlpha(255);

		gc.setForeground(COLOR_SEL_FOREGROUND);
		gc.drawRectangle(x, y, w, h);
	}

	protected void fill(GC gc, Control canvas) {
		gc.setBackground(BACKGROUND);
		Rectangle bounds = canvas.getBounds();
		gc.fillRectangle(0, 0, bounds.width, bounds.height);
	}

	public CTabItem getItem() {
		return item;
	}

}
