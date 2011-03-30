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

import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.base.Positioned;
import com.maton.tools.stiletto.view.dnd.IDragProvider;
import com.maton.tools.stiletto.view.dnd.IDropReceiver;
import com.maton.tools.stiletto.view.dnd.SourceTransferDefault;
import com.maton.tools.stiletto.view.dnd.TargetTransferDefault;
import com.maton.tools.stiletto.view.dnd.TransferType;

public class PositionedTable extends
		DefaultTable<Positioned<com.maton.tools.stiletto.model.Image>> {

	protected Sprite sprite;

	public PositionedTable(Composite parent, int style, Sprite sprite) {
		super(parent, style);

		this.sprite = sprite;
		sprite.addModelListener(this);

		build();
	}

	public void dispose() {
		sprite.removeModelListener(this);
	}

	@Override
	protected Table createTable(Composite parent, int style) {
		table = new Table(parent, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		new SourceTransferDefault(table, TransferType.POSITIONEDIMG, true,
				new IDragProvider() {
					Positioned<com.maton.tools.stiletto.model.Image> image;

					@Override
					public Object drag() {
						return image;
					}

					@SuppressWarnings("unchecked")
					@Override
					public boolean canDrag() {
						if (table.getSelectionCount() == 0) {
							image = null;
							return false;
						}

						TableItem[] items = table.getSelection();
						image = (Positioned<com.maton.tools.stiletto.model.Image>) items[0]
								.getData();

						return image != null;
					}
				});

		new TargetTransferDefault(table, new Transfer[] { TransferType.IMAGE,
				TransferType.POSITIONEDIMG }, new IDropReceiver() {
			@SuppressWarnings("unchecked")
			@Override
			public void move(Object data, int idx1) {
				int idx0 = sprite
						.indexOf((Positioned<com.maton.tools.stiletto.model.Image>) data);

				sprite.moveChild(idx0, idx1);
				table.setSelection(idx1);
			}

			@Override
			public void drop(Control source, Object data, int idx) {
				if (data instanceof com.maton.tools.stiletto.model.Image) {
					com.maton.tools.stiletto.model.Image img = (com.maton.tools.stiletto.model.Image) data;

					if (idx < 0) {
						Positioned<com.maton.tools.stiletto.model.Image> pos = sprite
								.addChild(img);
						table.select(sprite.indexOf(pos));
					} else {
						sprite.addChild(img, idx);
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
		return sprite.toArray();
	}

	@Override
	protected DefaultColumn<Positioned<com.maton.tools.stiletto.model.Image>>[] getColumns() {
		return new BaseColumn[] { new ImageColumn(), new XColumn(),
				new YColumn() };
	}

	static org.eclipse.swt.graphics.Image IMAGE;

	static {
		IMAGE = ImageDescriptor.createFromFile(PositionedTable.class,
				"image.png").createImage();
	}

	abstract class BaseColumn extends
			DefaultColumn<Positioned<com.maton.tools.stiletto.model.Image>> {
		public BaseColumn(String property, String title, int width, int style,
				boolean editable, CellEditor editor) {
			super(property, title, width, style, editable, editor);
		}
	}

	class ImageColumn extends BaseColumn {

		public ImageColumn() {
			super("source", "Images", 150, SWT.LEFT, false, null);
		}

		@Override
		public Object getValue(
				Positioned<com.maton.tools.stiletto.model.Image> element) {
			return element.getSource();
		}

		@Override
		public String getText(
				Positioned<com.maton.tools.stiletto.model.Image> element) {
			return element.getSource().getName();
		}

		@Override
		public void modify(
				Positioned<com.maton.tools.stiletto.model.Image> element,
				Object value) {
			element.setSource((com.maton.tools.stiletto.model.Image) value);
		}

		@Override
		public Image getImage(
				Positioned<com.maton.tools.stiletto.model.Image> element) {
			return IMAGE;
		}

	}

	class XColumn extends BaseColumn {

		public XColumn() {
			super("x", "X", 30, SWT.RIGHT, true, new TextCellEditor(table));
		}

		@Override
		public Object getValue(
				Positioned<com.maton.tools.stiletto.model.Image> element) {
			return String.valueOf(element.getX());
		}

		@Override
		public String getText(
				Positioned<com.maton.tools.stiletto.model.Image> element) {
			return String.valueOf(element.getX());
		}

		@Override
		public void modify(
				Positioned<com.maton.tools.stiletto.model.Image> element,
				Object value) {
			try {
				int x = Integer.parseInt((String) value);
				element.setX(x);
			} catch (Throwable e) {
			}
			sprite.notifyChange(element);
		}

	}

	class YColumn extends BaseColumn {

		public YColumn() {
			super("y", "Y", 30, SWT.RIGHT, true, new TextCellEditor(table));
		}

		@Override
		public Object getValue(
				Positioned<com.maton.tools.stiletto.model.Image> element) {
			return String.valueOf(element.getY());
		}

		@Override
		public String getText(
				Positioned<com.maton.tools.stiletto.model.Image> element) {
			return String.valueOf(element.getY());
		}

		@Override
		public void modify(
				Positioned<com.maton.tools.stiletto.model.Image> element,
				Object value) {
			try {
				int y = Integer.parseInt((String) value);
				element.setY(y);
			} catch (Throwable e) {
			}
			sprite.notifyChange(element);
		}

	}

	public Sprite getSprite() {
		return sprite;
	}

}
