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
import org.eclipse.swt.widgets.TableItem;

import com.maton.tools.stiletto.model.Widget;
import com.maton.tools.stiletto.model.WidgetPool;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.dnd.IDragProvider;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.SourceTransferDefault;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class WidgetsTable extends DefaultTable<Widget> {

	protected WidgetPool pool;

	public WidgetsTable(Composite parent, int style, WidgetPool pool) {
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

		new SourceTransferDefault(table, TransferType.WIDGET, false,
				new IDragProvider() {
					Widget object;

					@Override
					public Object drag() {
						return object;
					}

					@Override
					public boolean canDrag() {
						TableItem[] selections = table.getSelection();

						if (selections == null || selections.length < 1) {
							object = null;
							return false;
						}

						object = (Widget) selections[0].getData();

						return object != null;
					}
				});

		new TargetTransferDefault(table, TransferType.IMAGE,
				new IDropReceiver() {
					@Override
					public void move(Object data, int idx) {

					}

					@Override
					public void drop(Control source, Object data, int idx) {
						if (data instanceof com.maton.tools.stiletto.model.Image) {
							com.maton.tools.stiletto.model.Image img = (com.maton.tools.stiletto.model.Image) data;
							Widget widget = pool
									.newElement(img.getExportName());
							widget.addChild(img);
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
	protected DefaultColumn<Widget>[] getColumns() {
		return new WidgetsColumn[] { new NameColumn(table) };
	}

	@Override
	public void notifyNew(Object obj) {
		super.notifyNew(obj);

		BundleContainer.getInstance().getCurrent().launchEditor(obj);
	}

	static org.eclipse.swt.graphics.Image WIDGET;

	static {
		WIDGET = ImageDescriptor.createFromFile(WidgetsTable.class, "ui-button.png")
				.createImage();
	}

	abstract class WidgetsColumn extends DefaultColumn<Widget> {
		public WidgetsColumn(String property, String title, int width,
				int style, boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class NameColumn extends WidgetsColumn {

		public NameColumn(Table parent) {
			super("name", "Widget Name", 300, SWT.LEFT, true,
					new TextCellEditor(table));

			sorter = new DefaultSorter<Widget>() {

				@Override
				public int compareElements(Viewer viewer, Widget t1, Widget t2) {
					return t1.getName().compareTo(t2.getName());
				}

			};
		}

		@Override
		public Object getValue(Widget element) {
			return element.getName();
		}

		@Override
		public String getText(Widget element) {
			return element.getName();
		}

		@Override
		public void modify(Widget element, Object value) {
			element.setName((String) value);
		}

		@Override
		public org.eclipse.swt.graphics.Image getImage(Widget element) {
			return WIDGET;
		}

	}

}
