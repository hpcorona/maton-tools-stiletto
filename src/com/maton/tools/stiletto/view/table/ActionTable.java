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

import com.maton.tools.stiletto.model.Action;
import com.maton.tools.stiletto.model.Actor;
import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.view.dnd.IDragProvider;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.SourceTransferDefault;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class ActionTable extends DefaultTable<Action> {

	protected Actor actor;

	public ActionTable(Composite parent, int style, Actor actor) {
		super(parent, style);

		this.actor = actor;
		actor.addModelListener(this);

		build();
	}

	public void dispose() {
		actor.removeModelListener(this);
	}

	@Override
	protected Table createTable(Composite parent, int style) {
		table = new Table(parent, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		new SourceTransferDefault(table, TransferType.ACTION, true,
				new IDragProvider() {
					Action action;

					@Override
					public Object drag() {
						return action;
					}

					@Override
					public boolean canDrag() {
						if (table.getSelectionCount() == 0) {
							action = null;
							return false;
						}

						TableItem[] items = table.getSelection();
						action = (Action) items[0].getData();

						return action != null;
					}
				});

		new TargetTransferDefault(table, new Transfer[] {
				TransferType.ANIMATION, TransferType.ACTION },
				new IDropReceiver() {
					@Override
					public void move(Object data, int idx1) {
						int idx0 = actor.indexOf((Action) data);

						actor.moveChild(idx0, idx1);
						table.setSelection(idx1);
					}

					@Override
					public void drop(Control source, Object data, int idx) {
						if (data instanceof Animation) {
							Animation anim = (Animation) data;

							if (idx < 0) {
								Action action = actor.addChild(anim);
								table.select(actor.indexOf(action));
							} else {
								actor.addChild(anim, idx);
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
		return actor.toArray();
	}

	@Override
	protected DefaultColumn<Action>[] getColumns() {
		return new BaseColumn[] { new NameColumn(), new AnimationColumn() };
	}

	static org.eclipse.swt.graphics.Image ANIMATION;

	static {
		ANIMATION = ImageDescriptor.createFromFile(ActionTable.class,
				"game-monitor.png").createImage();
	}

	abstract class BaseColumn extends DefaultColumn<Action> {
		public BaseColumn(String property, String title, int width, int style,
				boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class NameColumn extends BaseColumn {

		public NameColumn() {
			super("name", "Name", 130, SWT.LEFT, true,
					new TextCellEditor(table));
		}

		@Override
		public Object getValue(Action element) {
			return element.getName();
		}

		@Override
		public String getText(Action element) {
			return element.getName();
		}

		@Override
		public void modify(Action element, Object value) {
			element.setName((String) value);
		}

	}

	class AnimationColumn extends BaseColumn {

		public AnimationColumn() {
			super("source", "Animation", 70, SWT.LEFT, false, null);
		}

		@Override
		public Object getValue(Action element) {
			return element.getSource();
		}

		@Override
		public String getText(Action element) {
			return element.getSource().getName();
		}

		@Override
		public void modify(Action element, Object value) {
			element.setSource((Animation) value);
		}

		@Override
		public Image getImage(Action element) {
			return ANIMATION;
		}

	}

	public Actor getActor() {
		return actor;
	}

}
