package com.maton.tools.stiletto.view.table;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Frame;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.view.dnd.IDragProvider;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.SourceTransferDefault;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class FrameTable extends DefaultTable<Frame> {

	protected Animation animation;

	public FrameTable(Composite parent, int style, Animation animation) {
		super(parent, style);

		this.animation = animation;
		animation.addModelListener(this);

		build();
	}

	public void dispose() {
		animation.removeModelListener(this);
	}

	@Override
	protected Table createTable(Composite parent, int style) {
		table = new Table(parent, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		new SourceTransferDefault(table, TransferType.FRAME, true,
				new IDragProvider() {
					Frame frame;

					@Override
					public Object drag() {
						return frame;
					}

					@Override
					public boolean canDrag() {
						if (table.getSelectionCount() == 0) {
							frame = null;
							return false;
						}

						TableItem[] items = table.getSelection();
						frame = (Frame) items[0].getData();

						return frame != null;
					}
				});

		new TargetTransferDefault(table, new Transfer[] { TransferType.SPRITE,
				TransferType.FRAME }, new IDropReceiver() {
			@Override
			public void move(Object data, int idx1) {
				int idx0 = animation.indexOf((Frame) data);

				animation.moveChild(idx0, idx1);
				table.setSelection(idx1);
			}

			@Override
			public void drop(Control source, Object data, int idx) {
				if (data instanceof Sprite) {
					Sprite spr = (Sprite) data;

					if (idx < 0) {
						Frame frame = animation.addChild(spr);
						table.select(animation.indexOf(frame));
					} else {
						animation.addChild(spr, idx);
						table.select(idx);
					}
				}
			}
		});

		return table;
	}

	@Override
	protected ViewerSorter getDefaultSorter() {
		return null;
	}

	@Override
	public Object[] getElements() {
		return animation.toArray();
	}

	@Override
	protected DefaultColumn<Frame>[] getColumns() {
		return new BaseColumn[] { new SpriteColumn(), new TimeColumn() };
	}

	static org.eclipse.swt.graphics.Image SPRITE;
	static org.eclipse.swt.graphics.Image EMPTY;

	static {
		SPRITE = ImageDescriptor.createFromFile(FrameTable.class, "game.png")
				.createImage();
		EMPTY = ImageDescriptor.createFromFile(FrameTable.class,
				"image-empty.png").createImage();
	}

	abstract class BaseColumn extends DefaultColumn<Frame> {
		public BaseColumn(String property, String title, int width, int style,
				boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class SpriteColumn extends BaseColumn {

		public SpriteColumn() {
			super("source", "Frames", 150, SWT.LEFT, false, null);
		}

		@Override
		public Object getValue(Frame element) {
			return element.getSource();
		}

		@Override
		public String getText(Frame element) {
			return element.getSource().getName();
		}

		@Override
		public void modify(Frame element, Object value) {
			element.setSource((Sprite) value);
		}

		@Override
		public Image getImage(Frame element) {
			return element.getSource().childCount() == 0 ? EMPTY : SPRITE;
		}

	}

	class TimeColumn extends BaseColumn {

		public TimeColumn() {
			super("time", "Time (ms)", 60, SWT.RIGHT, true, new TextCellEditor(
					table));
		}

		@Override
		public Object getValue(Frame element) {
			return String.valueOf(element.getTime());
		}

		@Override
		public String getText(Frame element) {
			return String.valueOf(element.getTime());
		}

		@Override
		public void modify(Frame element, Object value) {
			try {
				int t = Integer.parseInt((String) value);
				element.setTime(t);
			} catch (Throwable e) {
			}
			animation.notifyChange(element);
		}

	}

	public Frame getSelected() {
		if (table.getSelectionCount() == 0) {
			return null;
		}

		return (Frame) table.getSelection()[0].getData();
	}

	public Animation getAnimation() {
		return animation;
	}

}
