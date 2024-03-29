package com.maton.tools.stiletto.view.editor;

import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Positioned;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.base.IModelListener;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;
import com.maton.tools.stiletto.view.editor.action.AlignAction;
import com.maton.tools.stiletto.view.editor.action.DeleteImageAction;
import com.maton.tools.stiletto.view.editor.action.ShowGridAction;
import com.maton.tools.stiletto.view.editor.action.ShowGuideAction;
import com.maton.tools.stiletto.view.editor.action.ShowSelectionAction;
import com.maton.tools.stiletto.view.editor.action.ToolAction;
import com.maton.tools.stiletto.view.form.PositionedForm;
import com.maton.tools.stiletto.view.table.DefaultTable;
import com.maton.tools.stiletto.view.table.PositionedTable;

public class SpriteEditor extends DefaultEditor implements IGraphicsEditor,
		IModelListener, MouseListener, MouseMoveListener, IBaseEditor {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DefaultTable.class, "game.png");

	protected Sprite sprite;
	protected Canvas canvas;
	protected boolean showGuide = true;
	protected boolean showGrid = true;
	protected boolean showSelection = false;
	protected int xOffset, yOffset;
	protected PositionedTable table;
	protected MouseTracker tracker;
	protected ToolAction panTool;
	protected ToolAction moveTool;
	protected ToolType tool;
	protected PositionedForm form;

	public SpriteEditor(CTabFolder parent, Sprite sprite) {
		super(parent);
		this.sprite = sprite;
		item.setText(sprite.getName());
		item.setImage(icon.createImage());
		xOffset = 100;
		yOffset = 200;

		sprite.addModelListener(this);

		build();
	}

	public void dispose() {
		sprite.removeModelListener(this);
		form.dispose();
	}
	
	public Object getData() {
		return sprite;
	}

	@Override
	protected Control createControl(Composite parent) {
		canvas = new Canvas(parent, SWT.BORDER | SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND);

		canvas.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				paint(event);
			}
		});

		canvas.addMouseMoveListener(this);
		canvas.addMouseListener(this);
		tracker = new MouseTracker(this);

		new TargetTransferDefault(canvas, new Transfer[] { TransferType.IMAGE,
				TransferType.POSITIONEDIMG }, new IDropReceiver() {

			@Override
			public void move(Object data, int idx) {
			}

			@Override
			public void drop(Control source, Object data, int idx) {
				if (data instanceof Image) {
					Positioned img = sprite.addChild((Image) data);
					table.getViewer().getTable().setSelection(sprite.indexOf(img));
					
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
		toolbar.add(new ShowSelectionAction(this));
		toolbar.add(new Separator());
		toolbar.add(panTool = new ToolAction(this, ToolType.Pan));
		toolbar.add(moveTool = new ToolAction(this, ToolType.Move));

		switchTool(ToolType.Pan);

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
		
		table = new PositionedTable(rowComp, DefaultTable.DEFAULT_TABLE_STYLE,
				sprite);

		extraToolbar.add(new DeleteImageAction(table, sprite));
		extraToolbar.add(new Separator());
		extraToolbar.add(new AlignAction(table, AlignAction.RIGHT));
		extraToolbar.add(new AlignAction(table, AlignAction.CENTER));
		extraToolbar.add(new AlignAction(table, AlignAction.LEFT));
		extraToolbar.add(new AlignAction(table, AlignAction.BOTTOM));
		extraToolbar.add(new AlignAction(table, AlignAction.MIDDLE));
		extraToolbar.add(new AlignAction(table, AlignAction.TOP));
		extraToolbar.update(true);
		
		table.getTable().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				current = table.getSelected();
				form.setCurrent(current);
				refreshGraphics();
			}
		});
		
		form = new PositionedForm(rowComp, sprite);
		
		
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
		table.getTable().setLayoutData(gd);

		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.heightHint = 150;
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

		sprite.draw(e.gc, xOffset, yOffset, 0, 0, 0, 255, false, false);
		
		if (showSelection) {
			if (current != null) {
				Rectangle rect = current.getBounds();
				drawSelection(e.gc, xOffset + current.getX(), yOffset + current.getY(), rect);
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

	Positioned current = null;

	@Override
	public void mouseDown(MouseEvent e) {
		current = table.getSelected();
		tracker.mouseDown(e.x, e.y);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		tracker.mouseUp();
	}

	@Override
	public void switchTool(ToolType tool) {
		this.tool = tool;

		panTool.setChecked(false);
		moveTool.setChecked(false);

		if (tool == ToolType.Pan) {
			panTool.setChecked(true);
		} else {
			moveTool.setChecked(true);
		}
	}

	@Override
	public void move(int x, int y) {
		if (tool == ToolType.Pan) {
			xOffset += x;
			yOffset += y;
			refreshGraphics();
		} else {
			if (current == null) {
				return;
			}

			current.setX(current.getX() + x);
			current.setY(current.getY() + y);
			sprite.notifyChange(current);
		}
	}

	@Override
	public void setShowSelection(boolean showSelection) {
		this.showSelection = showSelection;
	}

	@Override
	public boolean isShowSelection() {
		return showSelection;
	}

}
