package com.maton.tools.stiletto.view.table;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

import com.maton.tools.stiletto.model.Actor;
import com.maton.tools.stiletto.model.ActorPool;
import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class ActorsTable extends DefaultTable<Actor> {

	protected ActorPool pool;

	public ActorsTable(Composite parent, int style, ActorPool pool) {
		super(parent, style);

		this.pool = pool;
		pool.addModelListener(this);

		build();
	}

	@Override
	protected Table createTable(Composite parent, int style) {
		table = new Table(parent, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		new TargetTransferDefault(table, TransferType.ANIMATION,
				new IDropReceiver() {
					@Override
					public void move(Object data, int idx) {

					}

					@Override
					public void drop(Control source, Object data, int idx) {
						if (data instanceof Animation) {
							Animation anim = (Animation) data;
							Actor actor = pool.newElement(anim.getName());
							actor.addChild(anim);
						}
					}
				});

		return table;
	}

	@Override
	protected ViewerSorter getDefaultSorter() {
		return columns[0].getSorter();
	}

	@Override
	public Object[] getElements() {
		return pool.toArray();
	}

	@Override
	protected DefaultColumn<Actor>[] getColumns() {
		return new BaseColumn[] { new NameColumn(table) };
	}

	@Override
	public void notifyNew(Object obj) {
		super.notifyNew(obj);

		BundleContainer.getInstance().getCurrent().launchEditor(obj);
	}

	static Image ACTOR;

	static {
		ACTOR = ImageDescriptor.createFromFile(ActorsTable.class, "dummy.png")
				.createImage();
	}

	abstract class BaseColumn extends DefaultColumn<Actor> {
		public BaseColumn(String property, String title, int width, int style,
				boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class NameColumn extends BaseColumn {

		public NameColumn(Table parent) {
			super("name", "Name", 300, SWT.LEFT, true, new TextCellEditor(
					parent));

			sorter = new DefaultSorter<Actor>() {

				@Override
				public int compareElements(Viewer viewer, Actor t1, Actor t2) {
					return t1.getName().compareTo(t2.getName());
				}

			};
		}

		@Override
		public Object getValue(Actor element) {
			return element.getName();
		}

		@Override
		public String getText(Actor element) {
			return element.getName();
		}

		@Override
		public void modify(Actor element, Object value) {
			element.setName((String) value);
		}

		@Override
		public org.eclipse.swt.graphics.Image getImage(Actor element) {
			return ACTOR;
		}

	}

}
