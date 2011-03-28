package com.maton.tools.stiletto.view.table;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.model.FontPool;
import com.maton.tools.stiletto.view.BundleContainer;

public class FontsTable extends DefaultTable<Font> {

	protected FontPool pool;

	public FontsTable(Composite parent, int style, FontPool pool) {
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
	protected DefaultColumn<Font>[] getColumns() {
		return new BaseColumn[] { new NameColumn(table) };
	}

	@Override
	public void notifyNew(Object obj) {
		super.notifyNew(obj);

		BundleContainer.getInstance().getCurrent().launchEditor(obj);
	}

	static Image FONT;

	static {
		FONT = ImageDescriptor.createFromFile(FontsTable.class,
				"ui-label-link.png").createImage();
	}

	abstract class BaseColumn extends DefaultColumn<Font> {
		public BaseColumn(String property, String title, int width, int style,
				boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class NameColumn extends BaseColumn {

		public NameColumn(Table parent) {
			super("name", "Name", 300, SWT.LEFT, true, new TextCellEditor(
					parent));

			sorter = new DefaultSorter<Font>() {

				@Override
				public int compareElements(Viewer viewer, Font t1, Font t2) {
					return t1.getName().compareTo(t2.getName());
				}

			};
		}

		@Override
		public Object getValue(Font element) {
			return element.getName();
		}

		@Override
		public String getText(Font element) {
			return element.getName();
		}

		@Override
		public void modify(Font element, Object value) {
			element.setName((String) value);
		}

		@Override
		public org.eclipse.swt.graphics.Image getImage(Font element) {
			return FONT;
		}

	}

}
