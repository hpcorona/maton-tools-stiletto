package com.maton.tools.stiletto.view.table;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.ImagePool;
import com.maton.tools.stiletto.view.dnd.DragRequester;
import com.maton.tools.stiletto.view.dnd.SourceTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class ImagesTable extends DefaultTable<Image> {

	protected ImagePool pool;

	public ImagesTable(Composite parent, int style, ImagePool pool) {
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
		table.getVerticalBar().setEnabled(true);
		table.getVerticalBar().setVisible(true);
		table.setDragDetect(true);

		new SourceTransferDefault(table, TransferType.IMAGE, false, new DragRequester() {
			Image object;
			
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
				
				object = (Image)selections[0].getData();
				
				return object != null;
			}
		});

		return table;
	}

	@Override
	protected ViewerSorter getDefaultSorter() {
		return columns[1].getSorter();
	}

	@Override
	public Object[] getElements() {
		return pool.toArray();
	}

	@Override
	protected DefaultColumn<Image>[] getColumns() {
		return new ImagesColumn[] { new ExportColumn(table),
				new NameColumn(table), new ExportNameColumn(table) };
	}

	static org.eclipse.swt.graphics.Image CHECK;
	static org.eclipse.swt.graphics.Image UNCHECK;
	static org.eclipse.swt.graphics.Image LOADED;
	static org.eclipse.swt.graphics.Image NOTLOADED;

	static {
		CHECK = ImageDescriptor.createFromFile(ExportColumn.class,
				"ui-check-box.png").createImage();
		UNCHECK = ImageDescriptor.createFromFile(ExportColumn.class,
				"ui-check-box-uncheck.png").createImage();
		LOADED = ImageDescriptor
				.createFromFile(ExportColumn.class, "image.png").createImage();
		NOTLOADED = ImageDescriptor.createFromFile(ExportColumn.class,
				"image--exclamation.png").createImage();
	}

	abstract class ImagesColumn extends DefaultColumn<Image> {
		public ImagesColumn(String property, String title, int width,
				int style, boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class ExportColumn extends ImagesColumn {

		public ExportColumn(Table parent) {
			super("export", "", 24, SWT.CENTER, true, new CheckboxCellEditor(
					parent));

			sorter = new DefaultSorter<Image>() {

				@Override
				public int compareElements(Viewer viewer, Image img1, Image img2) {
					if (img1.isExport() && img2.isExport() == false) {
						return -1;
					} else if (img1.isExport() == img2.isExport()) {
						return 0;
					} else {
						return 1;
					}
				}

			};
		}

		@Override
		public void configureColumn(TableColumn column) {
			super.configureColumn(column);

			column.setResizable(false);
			column.setMoveable(false);
		}

		@Override
		public Object getValue(Image element) {
			return new Boolean(element.isExport());
		}

		@Override
		public String getText(Image element) {
			return element.isExport() ? "This image will always be exported"
					: "This image will only be exported if needed";
		}

		@Override
		public void modify(Image element, Object value) {
			element.setExport((Boolean) value);
		}

		@Override
		public org.eclipse.swt.graphics.Image getImage(Image element) {
			if (element.isExport()) {
				return CHECK;
			} else {
				return UNCHECK;
			}
		}

	}

	class NameColumn extends ImagesColumn {

		public NameColumn(Table parent) {
			super("name", "Image Name", 158, SWT.LEFT | SWT.FILL, false,
					new TextCellEditor(table));

			sorter = new DefaultSorter<Image>() {

				@Override
				public int compareElements(Viewer viewer, Image t1, Image t2) {
					return t1.getName().compareTo(t2.getName());
				}

			};
		}

		@Override
		public Object getValue(Image element) {
			return element.getName();
		}

		@Override
		public String getText(Image element) {
			return element.getName();
		}

		@Override
		public void modify(Image element, Object value) {
			// Nada, no se puede editar
		}

		@Override
		public org.eclipse.swt.graphics.Image getImage(Image element) {
			if (element.isLoaded()) {
				return LOADED;
			} else {
				return NOTLOADED;
			}
		}

	}

	class ExportNameColumn extends ImagesColumn {

		public ExportNameColumn(Table parent) {
			super("exportName", "Export Name", 130, SWT.LEFT, true,
					new TextCellEditor(table));

			sorter = new DefaultSorter<Image>() {

				@Override
				public int compareElements(Viewer viewer, Image t1, Image t2) {
					return t1.getExportName().compareTo(t2.getExportName());
				}

			};
		}

		@Override
		public Object getValue(Image element) {
			return element.getExportName();
		}

		@Override
		public String getText(Image element) {
			return element.getExportName();
		}

		@Override
		public void modify(Image element, Object value) {
			element.setExportName((String) value);
		}

	}

}
