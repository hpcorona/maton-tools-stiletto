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

import com.maton.tools.stiletto.model.Widget;
import com.maton.tools.stiletto.model.WidgetState;
import com.maton.tools.stiletto.view.dnd.IDragProvider;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.SourceTransferDefault;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class WidgetStateTable extends DefaultTable<WidgetState> {

	protected Widget widget;

	public WidgetStateTable(Composite parent, int style, Widget widget) {
		super(parent, style);

		this.widget = widget;
		widget.addModelListener(this);

		build();
	}

	public void dispose() {
		widget.removeModelListener(this);
	}

	@Override
	protected Table createTable(Composite parent, int style) {
		table = new Table(parent, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		new SourceTransferDefault(table, TransferType.WIDGETSTATE, true,
				new IDragProvider() {
					WidgetState state;

					@Override
					public Object drag() {
						return state;
					}

					@Override
					public boolean canDrag() {
						if (table.getSelectionCount() == 0) {
							state = null;
							return false;
						}

						TableItem[] items = table.getSelection();
						state = (WidgetState) items[0].getData();

						return state != null;
					}
				});

		new TargetTransferDefault(table, new Transfer[] {
				TransferType.IMAGE, TransferType.WIDGETSTATE },
				new IDropReceiver() {
					@Override
					public void move(Object data, int idx1) {
						int idx0 = widget.indexOf((WidgetState) data);

						widget.moveChild(idx0, idx1);
						table.setSelection(idx1);
					}

					@Override
					public void drop(Control source, Object data, int idx) {
						if (data instanceof com.maton.tools.stiletto.model.Image) {
							com.maton.tools.stiletto.model.Image img = (com.maton.tools.stiletto.model.Image) data;
							if (img.isFramed() == false) {
								return;
							}

							if (idx < 0) {
								WidgetState state = widget.addChild(img);
								table.select(widget.indexOf(state));
							} else {
								widget.addChild(img, idx);
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
		return widget.toArray();
	}

	@Override
	protected DefaultColumn<WidgetState>[] getColumns() {
		return new BaseColumn[] { new NameColumn(), new ImageColumn() };
	}

	static org.eclipse.swt.graphics.Image IMAGE;

	static {
		IMAGE = ImageDescriptor.createFromFile(WidgetStateTable.class,
				"image.png").createImage();
	}

	abstract class BaseColumn extends DefaultColumn<WidgetState> {
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
		public Object getValue(WidgetState element) {
			return element.getName();
		}

		@Override
		public String getText(WidgetState element) {
			return element.getName();
		}

		@Override
		public void modify(WidgetState element, Object value) {
			element.setName((String) value);
		}

	}

	class ImageColumn extends BaseColumn {

		public ImageColumn() {
			super("source", "Image", 70, SWT.LEFT, false, null);
		}

		@Override
		public Object getValue(WidgetState element) {
			return element.getSource();
		}

		@Override
		public String getText(WidgetState element) {
			return element.getSource().getName();
		}

		@Override
		public void modify(WidgetState element, Object value) {
			element.setSource((com.maton.tools.stiletto.model.Image) value);
		}

		@Override
		public Image getImage(WidgetState element) {
			return IMAGE;
		}

	}

	public Widget getWidget() {
		return widget;
	}

}
