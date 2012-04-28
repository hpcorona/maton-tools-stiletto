package com.maton.tools.stiletto.view.table;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.maton.tools.stiletto.model.Resolution;
import com.maton.tools.stiletto.model.ResolutionPool;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.dnd.IDragProvider;
import com.maton.tools.stiletto.view.dnd.SourceTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class ResolutionsTable extends DefaultTable<Resolution> {

	protected ResolutionPool pool;

	public ResolutionsTable(Composite parent, int style, ResolutionPool pool) {
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

		new SourceTransferDefault(table, TransferType.RESOLUTION, false, new IDragProvider() {
			Resolution object;
			
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
				
				object = (Resolution)selections[0].getData();
				
				return object != null;
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
	protected DefaultColumn<Resolution>[] getColumns() {
		return new ResolutionsColumn[] { new NameColumn(table), new ScaleColumn(), new BasedOnColumn(table) };
	}

	@Override
	public void notifyNew(Object obj) {
		super.notifyNew(obj);

		BundleContainer.getInstance().getCurrent().launchEditor(obj);
	}

	static org.eclipse.swt.graphics.Image WIDGET;

	static {
		WIDGET = ImageDescriptor.createFromFile(ResolutionsTable.class, "ui-button.png")
				.createImage();
	}

	abstract class ResolutionsColumn extends DefaultColumn<Resolution> {
		public ResolutionsColumn(String property, String title, int width,
				int style, boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class NameColumn extends ResolutionsColumn {

		public NameColumn(Table parent) {
			super("name", "Resolution Name", 190, SWT.LEFT, true,
					new TextCellEditor(table));

			sorter = new DefaultSorter<Resolution>() {

				@Override
				public int compareElements(Viewer viewer, Resolution t1, Resolution t2) {
					return t1.getName().compareTo(t2.getName());
				}

			};
		}

		@Override
		public Object getValue(Resolution element) {
			return element.getName();
		}

		@Override
		public String getText(Resolution element) {
			return element.getName();
		}

		@Override
		public void modify(Resolution element, Object value) {
			element.setName((String)value);
		}

	}
	
	class ScaleColumn extends ResolutionsColumn {

		public ScaleColumn() {
			super("scale", "Scale", 50, SWT.RIGHT, true, new TextCellEditor(table));
		}

		@Override
		public Object getValue(Resolution element) {
			return String.valueOf(element.getScale());
		}

		@Override
		public String getText(Resolution element) {
			return String.valueOf(element.getScale());
		}

		@Override
		public void modify(Resolution element, Object value) {
			try {
				float scale = Float.parseFloat((String) value);
				element.setScale(scale);
			} catch (Throwable e) {
			}
		}

	}
	
	class BasedOnColumn extends ResolutionsColumn {

		public BasedOnColumn(Table parent) {
			super("basedOn", "Based On", 50, SWT.LEFT, true,
					new TextCellEditor(table));

			sorter = new DefaultSorter<Resolution>() {

				@Override
				public int compareElements(Viewer viewer, Resolution t1, Resolution t2) {
					return t1.getBasedOn().compareTo(t2.getBasedOn());
				}

			};
		}

		@Override
		public Object getValue(Resolution element) {
			return element.getBasedOn();
		}

		@Override
		public String getText(Resolution element) {
			return element.getBasedOn();
		}

		@Override
		public void modify(Resolution element, Object value) {
			element.setBasedOn((String)value);
		}

	}

}
