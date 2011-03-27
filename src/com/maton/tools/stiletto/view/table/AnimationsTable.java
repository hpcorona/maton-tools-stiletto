package com.maton.tools.stiletto.view.table;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.AnimationPool;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class AnimationsTable extends DefaultTable<Animation> {

	protected AnimationPool pool;

	public AnimationsTable(Composite parent, int style, AnimationPool pool) {
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

		new TargetTransferDefault(table, TransferType.SPRITE,
				new IDropReceiver() {
					@Override
					public void move(Object data, int idx) {

					}

					@Override
					public void drop(Control source, Object data, int idx) {
						if (data instanceof Sprite) {
							Sprite sprite = (Sprite) data;
							Animation animation = pool.newAnimation(sprite
									.getName());
							animation.addFrame(sprite);
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
	protected DefaultColumn<Animation>[] getColumns() {
		return new BaseColumn[] { new NameColumn(table) };
	}

	@Override
	public void notifyNew(Object obj) {
		super.notifyNew(obj);

		BundleContainer.getInstance().getCurrent().launchEditor(obj);
	}

	static org.eclipse.swt.graphics.Image ANIMATION;

	static {
		ANIMATION = ImageDescriptor.createFromFile(AnimationsTable.class,
				"game-monitor.png").createImage();
	}

	abstract class BaseColumn extends DefaultColumn<Animation> {
		public BaseColumn(String property, String title, int width, int style,
				boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class NameColumn extends BaseColumn {

		public NameColumn(Table parent) {
			super("name", "Name", 300, SWT.LEFT, true, new TextCellEditor(
					parent));

			sorter = new DefaultSorter<Animation>() {

				@Override
				public int compareElements(Viewer viewer, Animation t1,
						Animation t2) {
					return t1.getName().compareTo(t2.getName());
				}

			};
		}

		@Override
		public Object getValue(Animation element) {
			return element.getName();
		}

		@Override
		public String getText(Animation element) {
			return element.getName();
		}

		@Override
		public void modify(Animation element, Object value) {
			element.setName((String) value);
		}

		@Override
		public org.eclipse.swt.graphics.Image getImage(Animation element) {
			return ANIMATION;
		}

	}

}
