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
import com.maton.tools.stiletto.model.Widget;
import com.maton.tools.stiletto.model.WidgetState;
import com.maton.tools.stiletto.model.base.IModelListener;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;
import com.maton.tools.stiletto.view.editor.action.DeleteWidgetStateAction;
import com.maton.tools.stiletto.view.editor.action.ShowGridAction;
import com.maton.tools.stiletto.view.editor.action.ShowGuideAction;
import com.maton.tools.stiletto.view.outline.WidgetsOutline;
import com.maton.tools.stiletto.view.table.DefaultTable;
import com.maton.tools.stiletto.view.table.WidgetStateTable;

public class WidgetEditor extends DefaultEditor implements IGraphicsEditor,
		IModelListener, MouseListener, MouseMoveListener, IBaseEditor {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			WidgetsOutline.class, "ui-button.png");

	public static final int TIME_INC = 5;

	protected Widget widget;
	protected Image image;
	protected Canvas canvas;
	protected boolean showGuide = true;
	protected boolean showGrid = true;
	protected int xOffset, yOffset;
	protected WidgetStateTable table;
	protected MouseTracker tracker;
	protected boolean disposeAll = false;

	public WidgetEditor(CTabFolder parent, Widget widget) {
		super(parent);
		this.widget = widget;
		item.setText(widget.getName());
		item.setImage(icon.createImage());
		xOffset = 100;
		yOffset = 200;

		widget.addModelListener(this);

		build();
	}

	public void dispose() {
		disposeAll = true;
		widget.removeModelListener(this);
	}

	public Object getData() {
		return widget;
	}

	@Override
	protected Control createControl(Composite parent) {
		canvas = new Canvas(parent, SWT.NO_BACKGROUND | SWT.BORDER | SWT.DOUBLE_BUFFERED);

		canvas.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				paint(event);
			}
		});

		canvas.addMouseMoveListener(this);
		canvas.addMouseListener(this);
		tracker = new MouseTracker(this);

		new TargetTransferDefault(canvas, TransferType.IMAGE,
				new IDropReceiver() {

					@Override
					public void move(Object data, int idx) {
					}

					@Override
					public void drop(Control source, Object data, int idx) {
						if (data instanceof Image) {
							if (((Image) data).isFramed() == false) {
								return;
							}
							
							WidgetState state = widget.addChild((Image) data);
							table.getViewer().getTable()
									.setSelection(widget.indexOf(state));

							refreshGraphics();
						}
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
	protected boolean hasExtraControl() {
		return true;
	}

	@Override
	protected Control createExtraControl(Composite parent) {
		table = new WidgetStateTable(parent, DefaultTable.DEFAULT_TABLE_STYLE,
				widget);

		extraToolbar.add(new DeleteWidgetStateAction(table, widget));
		extraToolbar.update(true);

		table.getTable().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				WidgetState state = table.getSelected();
				
				image = null;
				if (state != null) {
					image = state.getSource();
				}
				refreshGraphics();
			}
		});

		table.getTable().addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				WidgetState state = table.getSelected();

				if (state == null) {
					return;
				}

				BundleContainer.getInstance().getCurrent()
						.launchEditor(state.getSource());
			}
		});

		return table.getTable();
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

		if (image != null) {
			image.draw(e.gc, xOffset, yOffset, 0, 0, 0, 255, false, false);
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
	public void notifyNew(Object obj) {
		refreshGraphics();
	}

	@Override
	public void notifyChange(Object obj) {
		refreshGraphics();
	}

	@Override
	public void notifyDelete(Object obj) {
		refreshGraphics();
	}

	@Override
	public void notifyInsert(Object obj, int idx) {
		refreshGraphics();
	}

	@Override
	public void mouseMove(MouseEvent e) {
		tracker.mouseMove(e.x, e.y);
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
	public void switchTool(ToolType tool) {
	}

	@Override
	public void move(int x, int y) {
		xOffset += x;
		yOffset += y;
		refreshGraphics();
	}

	@Override
	public void setShowSelection(boolean showSelection) {
	}

	@Override
	public boolean isShowSelection() {
		return false;
	}

}
