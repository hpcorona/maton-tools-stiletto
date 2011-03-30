package com.maton.tools.stiletto.view.outline;

import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.TableItem;

import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.model.FontPool;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.action.DeleteAction;
import com.maton.tools.stiletto.view.action.NewFontAction;
import com.maton.tools.stiletto.view.table.DefaultTable;
import com.maton.tools.stiletto.view.table.FontsTable;

public class FontsOutline extends DefaultOutline {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			FontsOutline.class, "ui-label-link.png");

	protected FontsTable table;
	protected FontPool pool;

	public FontsOutline(ExpandBar parent, int idx, FontPool pool) {
		super(parent, idx);
		this.pool = pool;
		item.setText("Fonts");
		item.setExpanded(true);
		item.setImage(icon.createImage());
		item.setHeight(150);

		build();
	}

	@Override
	protected Control createControl(Composite parent) {
		table = new FontsTable(parent, DefaultTable.DEFAULT_TABLE_STYLE, pool);

		table.getTable().addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				doubleClick(e);
			}
		});

		toolbar.add(new Separator());
		toolbar.add(new DeleteAction<Font>(parent.getShell(), BundleContainer
				.getInstance().getCurrent().getBundle(), table));
		toolbar.update(true);

		return table.getTable();
	}

	protected void doubleClick(MouseEvent e) {
		TableItem[] items = table.getTable().getSelection();

		for (int i = 0; i < items.length; i++) {
			BundleContainer.getInstance().getCurrent()
					.launchEditor(items[i].getData());
		}
	}

	@Override
	protected ToolBarManager createToolBarManager(Composite parent) {
		ToolBarManager tbm = new ToolBarManager(SWT.FLAT);
		tbm.createControl(parent);
		tbm.add(new NewFontAction(parent.getShell(), pool));

		return tbm;
	}
}
