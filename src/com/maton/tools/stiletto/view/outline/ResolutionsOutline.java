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

import com.maton.tools.stiletto.model.Resolution;
import com.maton.tools.stiletto.model.ResolutionPool;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.action.DeleteAction;
import com.maton.tools.stiletto.view.action.NewResolutionAction;
import com.maton.tools.stiletto.view.table.DefaultTable;
import com.maton.tools.stiletto.view.table.ResolutionsTable;

public class ResolutionsOutline extends DefaultOutline {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ResolutionsOutline.class, "vise.png");

	protected ResolutionsTable table;
	protected ResolutionPool pool;

	public ResolutionsOutline(ExpandBar parent, int idx, ResolutionPool pool) {
		super(parent, idx);
		this.pool = pool;
		item.setText("Resolutions");
		item.setExpanded(true);
		item.setImage(icon.createImage());
		item.setHeight(150);

		build();
	}

	@Override
	protected Control createControl(Composite parent) {
		table = new ResolutionsTable(parent, DefaultTable.DEFAULT_TABLE_STYLE, pool);

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
		toolbar.add(new DeleteAction<Resolution>(parent.getShell(), BundleContainer
				.getInstance().getCurrent().getBundle(), table));
		toolbar.update(true);

		return table.getTable();
	}

	protected void doubleClick(MouseEvent e) {
		// Only in-place editor for this table
//		TableItem[] items = table.getTable().getSelection();

//		for (int i = 0; i < items.length; i++) {
//			BundleContainer.getInstance().getCurrent()
//					.launchEditor(items[i].getData());
//		}
	}

	@Override
	protected ToolBarManager createToolBarManager(Composite parent) {
		ToolBarManager tbm = new ToolBarManager(SWT.FLAT);
		tbm.createControl(parent);
		tbm.add(new NewResolutionAction(parent.getShell(), pool));

		return tbm;
	}
}
