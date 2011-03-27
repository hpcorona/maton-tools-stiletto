package com.maton.tools.stiletto.view.outline;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.TableItem;

import com.maton.tools.stiletto.model.ActorPool;
import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.action.NewActorAction;
import com.maton.tools.stiletto.view.table.ActorsTable;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class ActorsOutline extends DefaultOutline {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ActorsOutline.class, "dummy.png");

	protected ActorsTable table;
	protected ActorPool pool;

	public ActorsOutline(ExpandBar parent, int idx, ActorPool pool) {
		super(parent, idx);
		this.pool = pool;
		item.setText("Actors");
		item.setExpanded(true);
		item.setImage(icon.createImage());
		item.setHeight(150);

		build();
	}

	@Override
	protected Control createControl(Composite parent) {
		table = new ActorsTable(parent, DefaultTable.DEFAULT_TABLE_STYLE, pool);

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
		tbm.add(new NewActorAction(parent.getShell(), pool));

		return tbm;
	}
}
