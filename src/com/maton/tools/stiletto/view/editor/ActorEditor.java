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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.maton.tools.stiletto.model.Action;
import com.maton.tools.stiletto.model.Actor;
import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Frame;
import com.maton.tools.stiletto.model.base.IModelListener;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;
import com.maton.tools.stiletto.view.editor.action.DelayStartAction;
import com.maton.tools.stiletto.view.editor.action.DeleteActionAction;
import com.maton.tools.stiletto.view.editor.action.LoopAction;
import com.maton.tools.stiletto.view.editor.action.PlayAction;
import com.maton.tools.stiletto.view.editor.action.ShowGridAction;
import com.maton.tools.stiletto.view.editor.action.ShowGuideAction;
import com.maton.tools.stiletto.view.outline.ActorsOutline;
import com.maton.tools.stiletto.view.table.ActionTable;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class ActorEditor extends DefaultEditor implements IGraphicsEditor,
		IModelListener, MouseListener, MouseMoveListener, IBaseEditor,
		IAnimationPreviewer {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ActorsOutline.class, "dummy.png");

	public static final int TIME_INC = 5;

	protected Actor actor;
	protected Animation animation;
	protected Canvas canvas;
	protected boolean showGuide = true;
	protected boolean showGrid = true;
	protected boolean play = true;
	protected boolean delayStart = false;
	protected boolean loop = true;
	protected int xOffset, yOffset;
	protected ActionTable table;
	protected MouseTracker tracker;
	protected Runnable updater;
	protected boolean disposeAll = false;
	protected int currentTime = 0;
	protected int prevIdx = -1;
	protected PlayAction playAction;
	protected LoopAction loopAction;
	protected DelayStartAction delayAction;

	public ActorEditor(CTabFolder parent, Actor actor) {
		super(parent);
		this.actor = actor;
		item.setText(actor.getName());
		item.setImage(icon.createImage());
		xOffset = 100;
		yOffset = 200;

		actor.addModelListener(this);

		build();

		updater = new Runnable() {
			@Override
			public void run() {
				int nextInc = TIME_INC;
				if (item.isDisposed()) {
					Display.getCurrent().timerExec(-1, this);
					return;
				}
				if (play && item.isShowing()) {
					nextInc = updateAnimation();
				}

				if (disposeAll == false) {
					Display.getCurrent().timerExec(nextInc, this);
				}
			}
		};
		Display.getCurrent().timerExec(TIME_INC * 10, updater);
	}

	public void dispose() {
		disposeAll = true;
		Display.getCurrent().timerExec(-1, updater);
		actor.removeModelListener(this);
	}

	public Object getData() {
		return actor;
	}

	@Override
	protected Control createControl(Composite parent) {
		canvas = new Canvas(parent, SWT.NO_BACKGROUND);

		canvas.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				paint(event);
			}
		});

		canvas.addMouseMoveListener(this);
		canvas.addMouseListener(this);
		tracker = new MouseTracker(this);

		new TargetTransferDefault(canvas, TransferType.ANIMATION,
				new IDropReceiver() {

					@Override
					public void move(Object data, int idx) {
					}

					@Override
					public void drop(Control source, Object data, int idx) {
						if (data instanceof Animation) {
							Action action = actor.addChild((Animation) data);
							table.getViewer().getTable()
									.setSelection(actor.indexOf(action));

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
		toolbar.add(new Separator());
		toolbar.add(playAction = new PlayAction(this));
		toolbar.add(loopAction = new LoopAction(this));
		toolbar.add(delayAction = new DelayStartAction(this));

		return toolbar;
	}

	@Override
	protected boolean hasExtraControl() {
		return true;
	}

	@Override
	protected Control createExtraControl(Composite parent) {
		table = new ActionTable(parent, DefaultTable.DEFAULT_TABLE_STYLE,
				actor);

		extraToolbar.add(new DeleteActionAction(table, actor));
		extraToolbar.update(true);

		table.getTable().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Action action = table.getSelected();
				
				current = null;
				currentTime = 0;
				prevIdx = -1;
				if (action != null) {
					animation = action.getSource();
					refreshGraphics();
				} else {
					animation = null;
					refreshGraphics();
				}
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
				Action action = table.getSelected();

				if (action == null) {
					return;
				}

				BundleContainer.getInstance().getCurrent()
						.launchEditor(action.getSource());
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
		container.redraw();
	}

	protected void paint(Event e) {
		if (showGrid) {
			drawGrid(e.gc);
		}

		if (showGuide) {
			drawGuide(e.gc, xOffset, yOffset);
		}

		if (current != null) {
			current.getSource().draw(e.gc, xOffset, yOffset, 0, 255);
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

	Frame current = null;

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

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		if (!this.play && play) {
			currentTime = 0;
			prevIdx = -1;
			current = null;
		}
		this.play = play;
		playAction.setChecked(play);
	}

	public boolean isDelayStart() {
		return delayStart;
	}

	public void setDelayStart(boolean delayStart) {
		this.delayStart = delayStart;
		delayAction.setChecked(delayStart);
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
		loopAction.setChecked(loop);
	}

	public int updateAnimation() {
		if (animation == null) {
			prevIdx = -1;
			current = null;
			currentTime = 0;
			
			return TIME_INC;
		}
		int newIdx = animation.getFrameForTime(currentTime);

		currentTime += TIME_INC;

		if (newIdx == prevIdx) {
			return TIME_INC;
		}

		if (newIdx == -1) {
			current = null;
			if (prevIdx != -1) {
				refreshGraphics();
			}
			prevIdx = -1;
			return TIME_INC;
		}

		if (newIdx > prevIdx) {
			current = animation.getChild(newIdx);
			prevIdx = newIdx;
			refreshGraphics();
			return TIME_INC;
		}

		if (!loop) {
			setPlay(false);
			return TIME_INC;
		}

		if (delayStart) {
			currentTime = 0;
			prevIdx = -1;
			return 1000; // 1 segundo de delay
		}

		current = animation.getChild(newIdx);
		prevIdx = newIdx;
		refreshGraphics();
		return TIME_INC;
	}

}
