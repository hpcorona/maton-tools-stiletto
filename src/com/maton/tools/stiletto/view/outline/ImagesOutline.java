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

import com.maton.tools.stiletto.model.ImagePool;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.action.ImportAction;
import com.maton.tools.stiletto.view.action.RefreshPoolAction;
import com.maton.tools.stiletto.view.table.DefaultTable;
import com.maton.tools.stiletto.view.table.ImagesTable;

public class ImagesOutline extends DefaultOutline {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ImagesOutline.class, "images-stack.png");

	protected ImagesTable table;
	protected ImagePool pool;

	public ImagesOutline(ExpandBar parent, int idx, ImagePool pool) {
		super(parent, idx);
		this.pool = pool;
		item.setText("Images");
		item.setExpanded(true);
		item.setImage(icon.createImage());
		item.setHeight(150);

		build();
	}

	@Override
	protected Control createControl(Composite parent) {
		table = new ImagesTable(parent, DefaultTable.DEFAULT_TABLE_STYLE, pool);

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
		tbm.add(new RefreshPoolAction(pool));
		tbm.add(new Separator());
		tbm.add(new ImportAction(parent.getShell(), pool));

		return tbm;
	}
}
