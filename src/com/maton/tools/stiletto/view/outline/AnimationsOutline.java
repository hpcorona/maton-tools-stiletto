package com.maton.tools.stiletto.view.outline;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class AnimationsOutline extends DefaultOutline {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			AnimationsOutline.class, "game-monitor.png");

	protected Table table;
	protected TableColumn column;

	public AnimationsOutline(ExpandBar parent, int idx) {
		super(parent, idx);
		item.setText("Animations");
		item.setExpanded(true);
		item.setImage(icon.createImage());
		item.setHeight(150);
	}

	@Override
	protected Control createControl(Composite parent) {
		table = new Table(parent, SWT.CHECK | SWT.MULTI | SWT.BORDER
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);

		column = new TableColumn(table, SWT.NONE);
		column.setText("Image name");

		TableItem itm = new TableItem(table, SWT.NONE);
		itm.setText(1, "Hola");

		return table;
	}

	@Override
	protected ToolBarManager createToolBarManager(Composite parent) {
		ToolBarManager tbm = new ToolBarManager(SWT.FLAT);
		tbm.createControl(parent);
		// TODO Add Actions

		return tbm;
	}
}
