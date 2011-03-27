package com.maton.tools.stiletto.view.table;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.SpritePool;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.dnd.IDragProvider;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.SourceTransferDefault;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class SpritesTable extends DefaultTable<Sprite> {

	protected SpritePool pool;

	public SpritesTable(Composite parent, int style, SpritePool pool) {
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

		new SourceTransferDefault(table, TransferType.SPRITE, false,
				new IDragProvider() {
					Sprite object;

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

						object = (Sprite) selections[0].getData();

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
							Sprite sprite = pool.newSprite(img.getExportName());
							sprite.setRendered(false);
							sprite.addImage(img);
						}
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
	protected DefaultColumn<Sprite>[] getColumns() {
		return new SpritesColumn[] { new RenderedColumn(table),
				new NameColumn(table), new ExportNameColumn(table) };
	}

	@Override
	public void notifyNew(Object obj) {
		super.notifyNew(obj);

		BundleContainer.getInstance().getCurrent().launchEditor(obj);
	}

	static org.eclipse.swt.graphics.Image CHECK;
	static org.eclipse.swt.graphics.Image UNCHECK;
	static org.eclipse.swt.graphics.Image SPRITE;
	static org.eclipse.swt.graphics.Image RENDER;
	static org.eclipse.swt.graphics.Image NORENDER;

	static {
		CHECK = ImageDescriptor.createFromFile(SpritesTable.class,
				"ui-check-box.png").createImage();
		UNCHECK = ImageDescriptor.createFromFile(SpritesTable.class,
				"ui-check-box-uncheck.png").createImage();
		SPRITE = ImageDescriptor.createFromFile(SpritesTable.class, "game.png")
				.createImage();
		RENDER = ImageDescriptor.createFromFile(SpritesTable.class,
				"image-blur.png").createImage();
		NORENDER = ImageDescriptor.createFromFile(SpritesTable.class,
				"images-stack.png").createImage();
	}

	abstract class SpritesColumn extends DefaultColumn<Sprite> {
		public SpritesColumn(String property, String title, int width,
				int style, boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class RenderedColumn extends SpritesColumn {

		public RenderedColumn(Table parent) {
			super("rendered", "", 24, SWT.CENTER, true, new CheckboxCellEditor(
					parent));

			sorter = new DefaultSorter<Sprite>() {

				@Override
				public int compareElements(Viewer viewer, Sprite t1, Sprite t2) {
					if (t1.isRendered() && t2.isRendered() == false) {
						return -1;
					} else if (t1.isRendered() == t2.isRendered()) {
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
		public Object getValue(Sprite element) {
			return new Boolean(element.isRendered());
		}

		@Override
		public String getText(Sprite element) {
			return element.isRendered() ? "This sprite will consolidate all images into a single one"
					: "All images used by this sprite will be exported separately";
		}

		@Override
		public void modify(Sprite element, Object value) {
			element.setRendered((Boolean) value);
		}

		@Override
		public org.eclipse.swt.graphics.Image getImage(Sprite element) {
			if (element.isRendered()) {
				return CHECK;
			} else {
				return UNCHECK;
			}
		}

	}

	class NameColumn extends SpritesColumn {

		public NameColumn(Table parent) {
			super("name", "Sprite Name", 158, SWT.LEFT, true,
					new TextCellEditor(table));

			sorter = new DefaultSorter<Sprite>() {

				@Override
				public int compareElements(Viewer viewer, Sprite t1, Sprite t2) {
					return t1.getName().compareTo(t2.getName());
				}

			};
		}

		@Override
		public Object getValue(Sprite element) {
			return element.getName();
		}

		@Override
		public String getText(Sprite element) {
			return element.getName();
		}

		@Override
		public void modify(Sprite element, Object value) {
			element.setName((String) value);
		}

		@Override
		public org.eclipse.swt.graphics.Image getImage(Sprite element) {
			return SPRITE;
		}

	}

	class ExportNameColumn extends SpritesColumn {

		public ExportNameColumn(Table parent) {
			super("exportName", "Image Name", 130, SWT.LEFT, true,
					new TextCellEditor(table));

			sorter = new DefaultSorter<Sprite>() {

				@Override
				public int compareElements(Viewer viewer, Sprite t1, Sprite t2) {
					return t1.getImageName().compareTo(t2.getImageName());
				}

			};
		}

		@Override
		public Object getValue(Sprite element) {
			return element.getImageName();
		}

		@Override
		public String getText(Sprite element) {
			return element.isRendered() ? element.getImageName() : "";
		}

		@Override
		public void modify(Sprite element, Object value) {
			element.setImageName((String) value);
		}

		@Override
		public boolean canModify(Sprite element) {
			return element.isRendered();
		}

		@Override
		public Image getImage(Sprite element) {
			return element.isRendered() ? RENDER : NORENDER;
		}
	}

}
